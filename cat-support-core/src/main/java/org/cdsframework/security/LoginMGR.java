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

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.application.Mts;
import org.cdsframework.client.support.SecurityMGRClient;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.enumeration.PermissionType;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.lookup.UserSecuritySchemePermissionMapList;
import org.cdsframework.message.MessageMGR;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@RequestScoped
public class LoginMGR implements Serializable {

    private static final long serialVersionUID = -43544388376928896L;

    private final static LogUtils logger = LogUtils.getLogger(LoginMGR.class);
    @Inject
    private Mts mts;
    @Inject
    private UserSession userSession;
    @Inject
    private MessageMGR messageMGR;
    @Inject
    private UserSecuritySchemePermissionMapList userSecuritySchemePermissionMapList;

    public LoginMGR() {
        final String METHODNAME = "LoginMGR Constructor ";
        logger.logInit(METHODNAME);
    }

    public void login(ActionEvent actionEvent) throws AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "login ";
        boolean authenticated;
        UserDTO userDTO = userSession.getUserDTO();
        if (userDTO.getPasswordHash() == null) {
            userDTO.setPasswordHash("");
        }
        if (userDTO.getUsername() == null) {
            userDTO.setUsername("");
        }
        logger.info(userDTO.getUsername(), " attempting to login...");

        // intialize the context
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext != null ? facesContext.getExternalContext() : null;
        Flash flash = externalContext != null ? externalContext.getFlash() : null;

        // Call the Security Manager to login
        SecurityMGRClient securityMGRClient = getSecurityMGRClient();
        if (securityMGRClient != null) {
            try {
                authenticated = securityMGRClient.authenticate(userDTO.getUsername(), userDTO.getPasswordHash(), mts.getMtsApp());
            } catch (AuthenticationException e) {
                logger.error(METHODNAME, e.getMessage());
                authenticated = false;
            } catch (AuthorizationException e) {
                logger.error(METHODNAME, e.getMessage());
                authenticated = false;
            } catch (MtsException e) {
                logger.error(METHODNAME, e);
                authenticated = false;
            } catch (Exception e) {
                logger.error(METHODNAME, e);
                authenticated = false;
            }

            login(authenticated);

        } else {
            // persist the message across the redirect
            if (flash != null) {
                flash.setKeepMessages(true);
            }
            messageMGR.displayError("mtsLoginUnavailable");
            logger.warn(METHODNAME + "securityMGRClient was null!");
        }
    }

    public void loginWithSessionId(String sessionId) throws AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "loginWithSessionId ";
        boolean sessionValid;
        logger.info(METHODNAME, "sessionId=", sessionId);

        // intialize the context
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext != null ? facesContext.getExternalContext() : null;
        Flash flash = externalContext != null ? externalContext.getFlash() : null;

        // Call the Security Manager to login with session ID
        SecurityMGRClient securityMGRClient = getSecurityMGRClient();
        if (securityMGRClient != null) {
            try {

                // check if the session ID is valid
                SessionDTO sessionDTO = new SessionDTO();
                sessionDTO.setSessionId(sessionId);
                sessionValid = securityMGRClient.isSessionValid(sessionDTO);
                logger.info(METHODNAME, "sessionValid=", sessionValid);

                if (sessionValid) {
                    sessionDTO = securityMGRClient.findByPrimaryKey(sessionId, mts.getSession());
                    UserDTO userDTO = sessionDTO.getUserDTO();
                    sessionDTO = securityMGRClient.getProxiedUserSession(userDTO.getUsername(), mts.getSession());
                    userSession.setSessionDTO(sessionDTO);
                    userDTO = userSession.getUserDTO();
                    logger.info(userDTO.getUsername(), " successfully logged in via sessionId: ", sessionId);
                }
            } catch (AuthenticationException e) {
                logger.error(METHODNAME, e.getMessage());
                sessionValid = false;
            } catch (AuthorizationException e) {
                logger.error(METHODNAME, e.getMessage());
                sessionValid = false;
            } catch (MtsException | NotFoundException | ValidationException e) {
                logger.error(METHODNAME, e);
                sessionValid = false;
            } catch (Exception e) {
                logger.error(METHODNAME, e);
                sessionValid = false;
            }

            logger.info(METHODNAME, "sessionValid=", sessionValid);
            login(sessionValid);

        } else {
            logger.error(METHODNAME, "securityMGRClient was null!");
            // persist the message across the redirect
            if (flash != null) {
                flash.setKeepMessages(true);
            }
            messageMGR.displayError("mtsLoginUnavailable");
        }
    }

    private void login(boolean authenticated) throws AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "login ";
        UserDTO userDTO = userSession.getUserDTO();

        // intialize the context
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext != null ? facesContext.getExternalContext() : null;
        Flash flash = externalContext != null ? externalContext.getFlash() : null;

        // Call the Security Manager to login
        SecurityMGRClient securityMGRClient = getSecurityMGRClient();
        if (securityMGRClient != null) {

            if (authenticated) {
                SessionDTO sessionDTO = securityMGRClient.getProxiedUserSession(userDTO.getUsername(), mts.getSession());
                if (sessionDTO != null) {
                    userSession.setSessionDTO(sessionDTO);
                    userDTO = userSession.getUserDTO();
                    logger.info(userDTO.getUsername(), " successfully logged in...");
                    if (logger.isDebugEnabled()) {
                        if (userDTO.getUserId() != null) {
                            UserSecuritySchemePermissionMap userSecuritySchemePermissionMap = userSecuritySchemePermissionMapList.get(userDTO.getUserId());
                            if (userSecuritySchemePermissionMap != null) {
                                Map<String, List<PermissionType>> permissionAllowMap = userSecuritySchemePermissionMap.getPermissionAllowMap();
                                Map<String, List<PermissionType>> permissionDenyMap = userSecuritySchemePermissionMap.getPermissionDenyMap();
                                for (String key : permissionDenyMap.keySet()) {
                                    logger.info(METHODNAME, "found deny perm on ", userDTO.getUsername(), " - ", key, " - ", permissionDenyMap.get(key));
                                }
                                for (String key : permissionAllowMap.keySet()) {
                                    logger.info(METHODNAME, "found allow perm on ", userDTO.getUsername(), " - ", key, " - ", permissionAllowMap.get(key));
                                }
                            } else {
                                logger.error(METHODNAME + "userSecuritySchemePermissionMapDTO is null!");
                            }
                        } else {
                            logger.error(METHODNAME + "userDTO.getUserId() is null!");
                        }
                    }

                    // If login is successful, clear the message list and navigate to the welcome page.
                    if (facesContext != null) {
                        List<FacesMessage> messageList = facesContext.getMessageList();
                        if (messageList != null && !messageList.isEmpty()) {
                            try {
                                messageList.clear();
                            } catch (UnsupportedOperationException e) {
                                // happens - ignore
                            }
                        }
                        Application application = facesContext.getApplication();
                        logger.debug(METHODNAME, "application=", application);
                        if (application != null) {
                            NavigationHandler navigationHandler = application.getNavigationHandler();
                            logger.debug(METHODNAME, "navigationHandler=", navigationHandler);
                            if (navigationHandler != null) {
                                navigationHandler.handleNavigation(facesContext, null, "welcome");
                            }
                        }
                        facesContext.renderResponse();
                    } else {
                        logger.error(METHODNAME, "facesContext == null!");
                    }
                } else {
                    // persist the message across the redirect
                    if (flash != null) {
                        flash.setKeepMessages(true);
                    }
                    messageMGR.displayError("loginFailed");
                    logger.warn(METHODNAME + "SessionDTO was null!");
                }
            } else {
                // persist the message across the redirect
                if (flash != null) {
                    flash.setKeepMessages(true);
                }
                messageMGR.displayError("loginFailed");
                logger.warn(METHODNAME + userDTO.getUsername(), " failed log in...");
            }
        } else {
            // persist the message across the redirect
            if (flash != null) {
                flash.setKeepMessages(true);
            }
            messageMGR.displayError("mtsLoginUnavailable");
            logger.warn(METHODNAME + "securityMGRClient was null!");
        }
    }

    public void checkLoggedIn(ActionEvent actionEvent) {
        final String METHODNAME = "checkLoggedIn ";
        if (userSession.isAuthenticated()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext != null ? facesContext.getExternalContext() : null;
            // navigate to the welcome page
            if (facesContext != null) {
                Application application = facesContext.getApplication();
                logger.debug(METHODNAME, "application=", application);
                if (application != null) {
                    NavigationHandler navigationHandler = application.getNavigationHandler();
                    logger.debug(METHODNAME, "navigationHandler=", navigationHandler);
                    if (navigationHandler != null) {
                        navigationHandler.handleNavigation(facesContext, null, "welcome");
                    }
                }
                facesContext.renderResponse();
            } else {
                logger.error(METHODNAME, "facesContext == null!");
            }
        }
    }

    public void logout(ActionEvent actionEvent) throws AuthenticationException, MtsException {
        final String METHODNAME = "logout ";

        SecurityMGRClient securityMGRClient = getSecurityMGRClient();
        // initialize the context
        FacesContext facesContext = FacesContext.getCurrentInstance();
        logger.debug(METHODNAME, "facesContext=", facesContext);
        ExternalContext externalContext = facesContext != null ? facesContext.getExternalContext() : null;
        logger.debug(METHODNAME, "externalContext=", externalContext);

        // persist the message across the redirect
        Flash flash = externalContext != null ? externalContext.getFlash() : null;
        if (flash != null) {
            flash.setKeepMessages(true);
        }

        if (securityMGRClient != null) {
            securityMGRClient.logout(userSession.getSessionDTO());
            userSession.invalidate();

            logger.info(userSession.getUserDTO().getUsername(), " logged out...");
            messageMGR.displayInfo("loggedOut");

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
            messageMGR.displayError("mtsLoginUnavailable");
            logger.warn("securityMGRClient was null!");
        }
    }

    private SecurityMGRClient getSecurityMGRClient() throws MtsException {
        SecurityMGRClient securityMGRClient = null;
        try {
            mts.checkSession();
            securityMGRClient = mts.getManager(SecurityMGRClient.class);
        } catch (Exception e) {
            logger.error(e);
        }
        return securityMGRClient;
    }
}
