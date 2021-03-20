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
package org.cdsframework.ui.services;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import org.cdsframework.base.BaseModule;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;
import org.primefaces.behavior.ajax.AjaxBehavior;
import org.primefaces.component.calendar.Calendar;

/**
 *
 * @author HLN Consulting, LLC
 */
public class PostRenderService {

    private static LogUtils logger = LogUtils.getLogger(PostRenderService.class);
    private static final String DATE_SELECT_STRING = "dateSelect";

    public static void postRenderUIComponent(BaseModule module, UIComponent uiComponent) {
//        if (module.isSaveImmediately()) {
        try {
            postRenderUIComponent(module.getFormEditUpdateId(), uiComponent);
        } catch (Exception e) {
            logger.error(e);
        }
//        }
    }

    private static void postRenderUIComponent(String formClientId, UIComponent uiComponent) {
        final String METHODNAME = "postRenderUIComponent ";
        long startTime = System.nanoTime();

        try {
            if (uiComponent != null) {
                if (uiComponent instanceof Calendar) {

                    ValueExpression valueExpression = uiComponent.getValueExpression("value");
//                    logger.debug(METHODNAME, "valueExpression=", valueExpression);
//                    logger.debug(METHODNAME, "uiComponent.getParent().getClass().getCanonicalName()=", uiComponent.getParent().getClass().getCanonicalName());

                    if (valueExpression != null) {
                        boolean byPassSetOnChange = false;

                        // check to see if id ends with bypass suffix
                        if (uiComponent.getClientId().endsWith(UIConstants.ID_SUFFIX_BYPASS)) {
                            byPassSetOnChange = true;
                        }

                        if (!byPassSetOnChange) {
                            // check the composite component for the bypass style class
                            UIComponent compositeComponentParent = UIComponent.getCompositeComponentParent(uiComponent);
                            if (compositeComponentParent != null) {
                                for (Map.Entry<String, Object> entry : compositeComponentParent.getAttributes().entrySet()) {
                                    Object value = entry.getValue();
                                    if (value != null && value instanceof String) {
//                                    logger.info(METHODNAME, "compositeComponentParent ", entry.getKey(), " = ", entry.getValue());
                                        if (((String) value).contains(UIConstants.STYLE_CLASS_BYPASS)) {
                                            byPassSetOnChange = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if (!byPassSetOnChange) {
                            Object oStyleClass = uiComponent.getAttributes().get("styleClass");
//                        logger.info(METHODNAME, "oStyleClass=", oStyleClass);
                            // Flag to bypass setOnChange logic
                            if (oStyleClass != null && ((String) oStyleClass).contains(UIConstants.STYLE_CLASS_BYPASS)) {
                                byPassSetOnChange = true;
                            }
                        }
                        //logger.info(METHODNAME, byPassSetOnChange);
                        if (!byPassSetOnChange) {
//                                logger.debug(METHODNAME, "formClientId=", formClientId);
//                                logger.debug(METHODNAME, "uiComponent.getClientId()=", uiComponent.getClientId());
                            Calendar calendar = (Calendar) uiComponent;
//                                Object value = calendar.getValue();
//                                logger.info(METHODNAME, "value=", value);
                            setAjaxBehavior(calendar, formClientId);

                            // Restore value, necessary for Save option 1 as the UI allows the 
                            // end user the freedom to change views when edit values are present
                            // (ie. Edit a value on Tab 1, click on Tab 2, 
                            // Return to Tab 1 edited value is lost
                            calendar.setValueExpression("value", valueExpression);
                        }
                    }
                }

                Iterator<UIComponent> facetsAndChildren = uiComponent.getFacetsAndChildren();
                while (facetsAndChildren.hasNext()) {
                    postRenderUIComponent(formClientId, facetsAndChildren.next());
                }
            }
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, startTime);                                                                            
        }
    }

    private static ClientBehavior setAjaxBehavior(Calendar calendar, String formClientId) {
        final String METHODNAME = "setAjaxBehavior ";
        AjaxBehavior ajaxBehavior;
        String event = null;

        Set<String> keySet = calendar.getClientBehaviors().keySet();

        // if the key set size is greater than 1 then log an error and if it contains "dateSelect" then use that otherwise
        // if the key set is 0 then use dateSelect
        // else use the first ajax behavior in the list
        if (keySet.size() > 1) {
            logger.error(METHODNAME + "more than one client behavior is not allowed on a calendar component: " + keySet);
            if (keySet.contains(DATE_SELECT_STRING)) {
                event = DATE_SELECT_STRING;
            }
        } else if (keySet.isEmpty()) {
            event = DATE_SELECT_STRING;
        } else {
            event = keySet.toArray(new String[]{})[0];
        }

        // if focus or change ajax behaviors were used then warn the developer that something might be amiss
        if ("focus".equals(event) || "change".equals(event)) {
            logger.error(METHODNAME, "focus and change are not supported on calendar widgets. You may want to implement the bypass on change style.");
        }

        logger.debug(METHODNAME, "event=", event);

        // get the behavior
        List<ClientBehavior> clientBevahiors = calendar.getClientBehaviors().get(event);
        logger.debug(METHODNAME, "clientBevahiors=", clientBevahiors);

        if (clientBevahiors == null) {
            // Add on change support
            ajaxBehavior = new AjaxBehavior();
            ajaxBehavior.setProcess(getProcess(null));
            ajaxBehavior.setOncomplete(getOnComplete(formClientId, null));
            calendar.addClientBehavior(event, ajaxBehavior);
        } else {
            ajaxBehavior = (AjaxBehavior) clientBevahiors.get(0);
            // Logic to handle calendar widget, Modify first one
            ajaxBehavior.setProcess(getProcess(ajaxBehavior.getProcess()));
            ajaxBehavior.setOncomplete(getOnComplete(formClientId, ajaxBehavior.getOncomplete()));
        }

        return ajaxBehavior;
    }

    private static String getProcess(String prevProcess) {
        final String METHODNAME = "getProcess ";
        logger.debug(METHODNAME, "prevProcess=", prevProcess);

        String process;
        if (!StringUtils.isEmpty(prevProcess)) {
            // Strip @none, cant have @none with @this
            if (prevProcess.contains("@none")) {
                prevProcess = prevProcess.replace("@none", "").trim();
            }
            if (!StringUtils.isEmpty(prevProcess)) {
                if (!prevProcess.contains("@this")) {
                    prevProcess += " @this";
                }
            } else {
                prevProcess = "@this";
            }

            process = prevProcess;
        } else {
            process = "@this";
        }
        logger.debug(METHODNAME, "process=", process);

        return process;
    }

    private static String getOnComplete(String formClientId, String prevOnComplete) {
        final String METHODNAME = "getOnComplete ";

        String onComplete = UIConstants.FORM_CHANGED_JS + "('" + formClientId + "', true);";
        logger.debug(METHODNAME, "prevOnComplete=", prevOnComplete);

        if (prevOnComplete != null) {
            String[] onCompleteList = prevOnComplete.split(" ");
            for (String onCompleteItem : onCompleteList) {
                // Strip because it doesnt work
                if (!onCompleteItem.contains("RC")) {
                    onComplete += " " + onCompleteItem;
                }
            }
        }
        logger.debug(METHODNAME, "onComplete=", onComplete);

        return onComplete;
    }

}
