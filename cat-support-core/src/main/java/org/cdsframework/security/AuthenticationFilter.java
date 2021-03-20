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
package org.cdsframework.security;

import org.cdsframework.util.LogUtils;
//import org.cdsframework.util.StringUtils;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.util.FlashUtils;
import org.cdsframework.util.JsfUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
public class AuthenticationFilter implements Filter {

    private final static LogUtils logger = LogUtils.getLogger(AuthenticationFilter.class);
    @Inject
    private UserSession userSession;
    @Inject
    private LoginMGR loginMGR;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain chain) throws IOException, ServletException {
        final String METHODNAME = "doFilter ";
        long start = System.nanoTime();
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
            response.addHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");

            logger.info(METHODNAME, "userSession.isAuthenticated()=", userSession.isAuthenticated());

            Map<String, String[]> parameterMap = request.getParameterMap();
            logger.debug(METHODNAME, "parameterMap=", parameterMap);
            if (!userSession.isAuthenticated() && parameterMap.containsKey("sessionId")) {
                try {
                    String sessionId = parameterMap.get("sessionId")[0];
                    loginMGR.loginWithSessionId(sessionId);
                    UserDTO userDTO = userSession.getUserDTO();
                    if (userDTO != null) {
                        logger.info(userDTO.getUsername(), " successfully logged in via sessionId: ", sessionId);
                    }
                } catch (MtsException | AuthenticationException | AuthorizationException e) {
                    logger.error(e);
                }
            }

            if (!userSession.isAuthenticated()) {
                logger.info(METHODNAME, "redirect to login");

                if (request.getSession() != null) {
                    Object WELD_S_HASH = request.getSession().getAttribute("WELD_S_HASH");
                    logger.info(METHODNAME, "WELD_S_HASH=", WELD_S_HASH);
                    if (WELD_S_HASH == null) {
                        FlashUtils.addFlashErrorMessageKey(request, response, "sessionTimedOut");
                    }
                }

                // debug stuff
//                boolean partialAjax = false;
//                boolean xmlHttpRequest = false;
//                String facesRequest = request.getHeader("faces-request");
//                String xRequestedWith = request.getHeader("x-requested-with");
//                if (!StringUtils.isEmpty(facesRequest)) {
//                    partialAjax = facesRequest.equalsIgnoreCase("partial/ajax");
//                }
//                if (!StringUtils.isEmpty(xRequestedWith)) {
//                    xmlHttpRequest = xRequestedWith.equalsIgnoreCase("XMLHttpRequest");
//                }
                // logRequest(request);
//                logger.debug(METHODNAME, "partialAjax=", partialAjax);
//                logger.debug(METHODNAME, "xmlHttpRequest=", xmlHttpRequest);
                // initialize context
                FacesContext facesContext = JsfUtils.getFacesContext(request, response);
                logger.debug(METHODNAME, "facesContext=", facesContext);
                ExternalContext externalContext = facesContext != null ? facesContext.getExternalContext() : null;
                logger.debug(METHODNAME, "externalContext=", externalContext);
//                if (partialAjax) {
//                    logger.debug(METHODNAME, "partialAjax");
//                } else {
//                    logger.debug(METHODNAME, "not partialAjax");
//                }
                // navigate to the login page
                if (facesContext != null) {
                    Application application = facesContext.getApplication();
                    logger.debug(METHODNAME, "application=", application);
                    if (application != null) {
                        NavigationHandler navigationHandler = application.getNavigationHandler();
                        logger.debug(METHODNAME, "navigationHandler=", navigationHandler);
                        if (navigationHandler != null) {
                            navigationHandler.handleNavigation(facesContext, null, "login");
                        }
                    }
                    facesContext.renderResponse();
                } else {
                    logger.error(METHODNAME, "facesContext == null!");
                }
            } else {
                logger.debug(METHODNAME, "chain.doFilter");
                chain.doFilter(servletRequest, servletResponse);
            }
        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, start);                                                                            
        }
    }

    private void logRequest(HttpServletRequest request) {
        final String METHODNAME = "logRequest ";

        Enumeration<String> attributes = request.getAttributeNames();
        while (attributes.hasMoreElements()) {
            logger.info(METHODNAME, "attib=", request.getAttribute(attributes.nextElement()));
        }
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logger.info(METHODNAME, "headerName=", headerName);
            logger.info(METHODNAME, "header=", request.getHeader(headerName));
        }
        Enumeration<String> parmNames = request.getParameterNames();
        while (parmNames.hasMoreElements()) {
            logger.info(METHODNAME, "parm=", request.getParameter(parmNames.nextElement()));
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
