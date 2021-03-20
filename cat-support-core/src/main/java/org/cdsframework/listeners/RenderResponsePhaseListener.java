/**
 * CAT Core support plugin project.
 *
 * Copyright (C) 2016 New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * Contributions by HLN Consulting, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. You should have received a copy of the GNU Lesser
 * General Public License along with this program. If not, see <http://www.gnu.org/licenses/> for more
 * details.
 *
 * The above-named contributors (HLN Consulting, LLC) are also licensed by the New York City
 * Department of Health and Mental Hygiene, Bureau of Immunization to have (without restriction,
 * limitation, and warranty) complete irrevocable access and rights to this project.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; THE
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see https://www.hln.com/services/open-source/ or send
 * correspondence to ice@hln.com.
 */
package org.cdsframework.listeners;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.cdsframework.security.UserSession;
import org.cdsframework.util.FlashUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author HLN Consulting, LLC
 */
public class RenderResponsePhaseListener implements PhaseListener {

    final static private LogUtils logger = LogUtils.getLogger(RenderResponsePhaseListener.class);
    private static final long serialVersionUID = 4897450776941813947L;

    @Override
    public void afterPhase(PhaseEvent event) {
        final String METHODNAME = "afterPhase ";
        FacesContext facesContext = event.getFacesContext();
        List<FacesMessage> messageList = facesContext.getMessageList();
        if (!messageList.isEmpty()) {
            for (FacesMessage item : messageList) {
                if (!item.isRendered()) {
                    logger.error(event.getPhaseId(), ": queued unrendered message in messageList after render response!");
                    logger.error(METHODNAME, "item.getDetail()=", item.getDetail());
                    logger.error(METHODNAME, "item.getSeverity()=", item.getSeverity());
                    logger.error(METHODNAME, "item.getSummary()=", item.getSummary());
                }
            }
        }
    }

    private UserSession getUserSession(FacesContext facesContext) {
        final String METHODNAME = "getUserSession ";
        return facesContext.getApplication().evaluateExpressionGet(facesContext, "#{userSession}", UserSession.class);
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        //        FacesContext facesContext = event.getFacesContext();
        //        List<FacesMessage> messageList = facesContext.getMessageList();
        //        logger.info(event.getPhaseId(), ": before messageList: ", messageList);

        final String METHODNAME = "beforePhase ";

        // Get the UserSession
        UserSession userSession = getUserSession(event.getFacesContext());
        if (userSession != null) {
            Map<Integer, Future<String>> futureJobs = userSession.getFutureJobs();
            if (!futureJobs.isEmpty()) {
                Iterator<Map.Entry<Integer, Future<String>>> futureJobIterator = futureJobs.entrySet().iterator();
                while (futureJobIterator.hasNext()) {
                    Map.Entry<Integer, Future<String>> futureJobEntry = futureJobIterator.next();
                    Future<String> futureJob = futureJobEntry.getValue();
                    Integer jobId = futureJobEntry.getKey();
                    if (futureJob.isDone()) {
                        String message = null;
                        try {
                            message = "Job Id: " + jobId + " " + futureJob.get();
                        } catch (InterruptedException ex) {
                            message = ExceptionUtils.getRootCauseMessage(ex);
                            logger.error(METHODNAME + "An InterruptedException has occurred; Message: " + ex.getMessage(), ex);
                        } catch (ExecutionException ex) {
                            message = ExceptionUtils.getRootCauseMessage(ex);
                            logger.error(METHODNAME + "An ExecutionException has occurred; Message: " + ex.getMessage(), ex);
                        } finally {
                            futureJobIterator.remove();
                            FacesContext facesContext = event.getFacesContext();
                            if (!StringUtils.isEmpty(message)) {
                                facesContext.addMessage(null, new FacesMessage("Future", message));
                            }
                        }
                    }
                }
            }
        }
        alterRenderIds(event);
        processFlashScope();
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    /**
     * Process messages from the flash scope. Used by filters for persisting messages across a redirect.
     */
    private void processFlashScope() {
        final String METHODNAME = "processFlashScope ";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext != null ? facesContext.getExternalContext() : null;
        HttpServletRequest request = externalContext != null ? (HttpServletRequest) externalContext.getRequest() : null;
        HttpServletResponse response = externalContext != null ? (HttpServletResponse) externalContext.getResponse() : null;
        FlashUtils.processFlashScope(request, response);
    }

    private void alterRenderIds(PhaseEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (event != null
                && event.getFacesContext() != null
                && requestContext != null
                && requestContext.isAjaxRequest()) {
            FacesContext facesContext = event.getFacesContext();
            List<FacesMessage> messageList = facesContext.getMessageList();
            PartialViewContext partialViewContext = facesContext.getPartialViewContext();
            if (messageList != null
                    && partialViewContext != null) {
                Collection<String> renderIds = partialViewContext.getRenderIds();
                if (renderIds != null) {
                    if (renderIds.contains(StartupListener.MESSAGE_DISPLAY_ID)) {
                        // if there are no messages to display then remove the growl id from the render id list
                        // this is required if there are back to back ajax requests and a message is getting removed from display
                        // by an empty message list getting rendered due to the autoUpdate feature.
                        if (messageList.isEmpty()) {
                            logger.debug("removing growl from render ids - nothing to update.");
                            renderIds.remove(StartupListener.MESSAGE_DISPLAY_ID);
                        }
                    } else // if there are messages to display then make sure growl id is added
                    {
                        if (!messageList.isEmpty()) {
                            logger.debug("adding growl from render ids - messages exist.");
                            renderIds.add(StartupListener.MESSAGE_DISPLAY_ID);
                        }
                    }
                }
            }
        }
    }

    private void logExternalContext(PhaseEvent event) {
        final String METHODNAME = "logExternalContext ";
        logger.info(METHODNAME + "sessionMap");
        logMap(event.getFacesContext().getExternalContext().getSessionMap());
        logger.info(METHODNAME + "requestMap");
        logMap(event.getFacesContext().getExternalContext().getRequestMap());

        Enumeration<String> enumStrings = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getAttributeNames();
        while (enumStrings.hasMoreElements()) {
            logger.info(METHODNAME + "nextElement=" + enumStrings.nextElement());
        }
    }

    private void logMap(Map<String, Object> objectMap) {
        final String METHODNAME = "logMap ";
        for (Map.Entry<String, Object> objectEntry : objectMap.entrySet()) {
            logger.info(METHODNAME + "objectEntry.getKey()=" + objectEntry.getKey());
            logger.info(METHODNAME + "objectEntry.getValue()=" + objectEntry.getValue());
        }
    }
}
