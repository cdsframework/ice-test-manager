/**
 * CAT Core support plugin project.
 *
 * Copyright (C) 2016 New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * Contributions by HLN Consulting, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. You should have received a copy of the GNU Lesser
 * General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/> for more details.
 *
 * The above-named contributors (HLN Consulting, LLC) are also licensed by the
 * New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * to have (without restriction, limitation, and warranty) complete irrevocable
 * access and rights to this project.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; THE SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see
 * https://www.hln.com/services/open-source/ or send correspondence to
 * ice@hln.com.
 */
package org.cdsframework.message;

import java.io.Serializable;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;
import javax.inject.Named;
import org.cdsframework.listeners.StartupListener;
import org.cdsframework.message.Message.MessageType;
import org.cdsframework.section.property.SystemPropertyMGR;
import org.primefaces.context.RequestContext;

/**
 *
 * @author HLN Consulting LLC
 */
@Named
public class MessageMGR implements Serializable {
    
    private static final LogUtils logger = LogUtils.getLogger(MessageMGR.class);
    private static final long serialVersionUID = -4963439109635472399L;
    private String messageBundle = "application";
    private String pluginId;
    @Inject
    protected SystemPropertyMGR systemPropertyMGR;
    
    public void setMessageBundle(String messageBundle) {
        logger.debug("setMessageBundle ", messageBundle);
        this.messageBundle = messageBundle;
    }
    
    public void setMessageBundle(Class messageBundleClass) {
        if (messageBundleClass != null) {
            logger.debug("setMessageBundle ", messageBundleClass.getCanonicalName());
        }
        
        try {
            setPluginId(messageBundleClass);
            setMessageBundle(getMessageBundleFromClass(messageBundleClass));
        } catch (Exception e) {
            logger.error(e);
        }
    }
    
    public String getMessageBundle() {
        logger.debug("getMessageBundle ", messageBundle);
        return messageBundle;
    }
    
    public static String getMessageBundleFromClass(Class messageBundleClass) throws CatException {
        final String METHODNAME = "getMessageBundleFromClass ";
        String result = null;
        if (messageBundleClass != null) {
            logger.debug("getMessageBundleFromClass ", messageBundleClass.getCanonicalName());
            String objectName = messageBundleClass.getName();
            String simpleName = messageBundleClass.getSimpleName();
            int index = objectName.indexOf(simpleName);
            result = objectName.substring(0, index) + "message";
        } else {
            throw new CatException(METHODNAME + "messageBundleClass was null!");
        }
        logger.debug(METHODNAME, result);
        return result;
    }

    /**
     * Attempt to set the appropriate message bundle for the given message key.
     * Check for the existence of a message in this order: package, plugin,
     * default
     *
     * @param messageKey
     */
    private boolean selectMessageBundle(String messageKey) {
        final String METHODNAME = "selectMessageBundle ";
        logger.debug("selectMessageBundle ", messageKey);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        if (facesContext != null) {
            // see if the key exists in the current message bundle and use the current message bundle if it does
            if (getMessageBundle() != null) {
                try {
                    ResourceBundle bundle = ResourceBundle.getBundle(getMessageBundle(), facesContext.getViewRoot().getLocale());
                    if (bundle != null && bundle.containsKey(messageKey)) {
                        logger.debug(METHODNAME, "found message key in supplied resource: ", getMessageBundle(), " - ", messageKey);
                        return true;
                    } else {
                        logger.debug(METHODNAME, "missing message key from supplied resource: ", getMessageBundle(), " - ", messageKey);
                    }
                } catch (MissingResourceException e) {
                    logger.debug(METHODNAME, "missing supplied resource: ", getMessageBundle(), " - ", e.getMessage());
                }
            }

            // see if the key exists at the plugin message bundle and use the plugin message bundle if it does
            if (getPluginId() != null) {
                String pluginMessageBundle = "org.cdsframework.plugin." + getPluginId().trim().toLowerCase() + ".message";
                try {
                    ResourceBundle bundle = ResourceBundle.getBundle(pluginMessageBundle, facesContext.getViewRoot().getLocale());
                    if (bundle != null && bundle.containsKey(messageKey)) {
                        logger.debug(METHODNAME, "found message key in plugin resource: ", pluginMessageBundle, " - ", messageKey);
                        setMessageBundle(pluginMessageBundle);
                        return true;
                    } else {
                        logger.debug(METHODNAME, "missing message key from plugin resource: ", pluginMessageBundle, " - ", messageKey);
                    }
                } catch (MissingResourceException e) {
                    logger.debug(METHODNAME, "missing plugin resource: ", pluginMessageBundle, " - ", e.getMessage());
                }
            }

            // see if the key exists at the native war plugin message bundle and use the plugin message bundle if it does
            if (getPluginId() != null) {
                String pluginMessageBundle = "WEB-INF.classes.org.cdsframework.plugin." + getPluginId().trim().toLowerCase() + ".message";
                try {
                    ResourceBundle bundle = ResourceBundle.getBundle(pluginMessageBundle, facesContext.getViewRoot().getLocale());
                    if (bundle != null && bundle.containsKey(messageKey)) {
                        logger.debug(METHODNAME, "found message key in plugin resource: ", pluginMessageBundle, " - ", messageKey);
                        setMessageBundle(pluginMessageBundle);
                        return true;
                    } else {
                        logger.debug(METHODNAME, "missing message key from plugin resource: ", pluginMessageBundle, " - ", messageKey);
                    }
                } catch (MissingResourceException e) {
                    logger.debug(METHODNAME, "missing plugin resource: ", pluginMessageBundle, " - ", e.getMessage());
                }
            }

            // otherwise drop back to the DefaultExceptionHandler's message bundle
            try {
                String defaultMessageBundle = MessageMGR.getMessageBundleFromClass(DefaultExceptionHandler.class);
                try {
                    ResourceBundle bundle = ResourceBundle.getBundle(defaultMessageBundle, facesContext.getViewRoot().getLocale());
                    if (bundle != null && bundle.containsKey(messageKey)) {
                        setMessageBundle(defaultMessageBundle);
                        logger.debug(METHODNAME, "found message key in default resource: ", defaultMessageBundle, " - ", messageKey);
                        return true;
                    } else {
                        logger.debug(METHODNAME, "missing message key from default resource: ", defaultMessageBundle, " - ", messageKey);
                    }
                } catch (MissingResourceException e) {
                    logger.debug(METHODNAME, "missing default resource: ", defaultMessageBundle, " - ", e.getMessage());
                }
            } catch (CatException e) {
                logger.error(e);
            }
        } else {
            logger.error(METHODNAME, "facesContext is null!");
        }
        
        return false;
        
    }

    /**
     * Get the value of pluginId from a message bundle class
     *
     * @return the value of pluginId
     */
    public String getPluginId() {
        logger.debug("getPluginId ", pluginId);
        return pluginId;
    }

    /**
     * Set the value of pluginId from a class reference. The expectation here is
     * that the resource is in a jar that has the plugin name in the third
     * position delimited by dashes. It is null otherwise. i.e.:
     * mts-support-core, cat-support-ice, etc...
     *
     * @param messageBundleClass
     * @return the value of pluginId
     */
    private String setPluginId(Class messageBundleClass) {
        if (getClass().getClassLoader() != null
                && messageBundleClass != null
                && messageBundleClass.getCanonicalName() != null) {
            logger.debug("setPluginId messageBundleClass.getCanonicalName()=", messageBundleClass.getCanonicalName());
            URL resource = getClass().getClassLoader().getResource(messageBundleClass.getCanonicalName().replaceAll("\\.", "/") + ".class");
            if (resource != null
                    && resource.getPath() != null) {
                String[] splitItems = resource.getPath().split("!")[0].split("/");
                if (splitItems.length > 0) {
                    logger.debug("setPluginId splitItems=", Arrays.toString(splitItems));
                    String[] newSplitItems = splitItems[splitItems.length - 1].split("-");
                    logger.debug("setPluginId newSplitItems=", Arrays.toString(newSplitItems));
                    if (newSplitItems.length > 3) {
                        logger.debug("pluginId: ", newSplitItems[2], " - for: ", messageBundleClass);
                        setPluginId(newSplitItems[2]);
                    } else {
                        splitItems = new String[]{splitItems[splitItems.length - 3]};
                        logger.debug("pluginId: ", splitItems[0], " - for: ", messageBundleClass);
                        List<String> scopes = systemPropertyMGR.getScopes();
                        if (scopes.contains(splitItems[0])) {
                            setPluginId(splitItems[0]);
                        }
                    }
                }
            }
        }
        return pluginId;
    }

    /**
     * Set the value of pluginId
     *
     * @param pluginId new value of pluginId
     */
    public void setPluginId(String pluginId) {
        logger.debug("setPluginId ", pluginId);
        this.pluginId = pluginId;
    }
    
    public ResourceBundle getResourceBundle() {
        final String METHODNAME = "getResourceBundle ";
        ResourceBundle bundle = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            bundle = ResourceBundle.getBundle(messageBundle, facesContext.getViewRoot().getLocale());
        } else {
            logger.error(METHODNAME, "facesContext is null!");
        }
        logger.debug("getResourceBundle ", bundle);
        return bundle;
    }
    
    public String getResourceBundleKeyValue(String messageKey) {
        logger.debug("getResourceBundleKeyValue ", messageKey);
        String result = null;
        try {
            selectMessageBundle(messageKey);
            ResourceBundle resourceBundle = getResourceBundle();
            if (resourceBundle != null) {
                result = getResourceBundle().getString(messageKey);
            }
            logger.debug("getResourceBundle().getString(messageKey)=", result);
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }
    
    public void displayInfo(String messageKey) {
        displayMessage(FacesMessage.SEVERITY_INFO, false, getResourceBundleKeyValue(messageKey));
    }
    
    public void displayInfo(String messageKey, Object... args) {
        displayMessage(FacesMessage.SEVERITY_INFO, false, getResourceBundleKeyValue(messageKey), args);
    }
    
    public void displayInfoMessage(String message) {
        displayMessage(FacesMessage.SEVERITY_INFO, false, message);
    }
    
    public void displayInfoMessage(String message, Object... args) {
        displayMessage(FacesMessage.SEVERITY_INFO, false, message, args);
    }
    
    public void displayWarn(String messageKey) {
        displayMessage(FacesMessage.SEVERITY_WARN, false, getResourceBundleKeyValue(messageKey));
    }
    
    public void displayWarn(String messageKey, Object... args) {
        displayMessage(FacesMessage.SEVERITY_WARN, false, getResourceBundleKeyValue(messageKey), args);
    }
    
    public void displayWarnMessage(String message) {
        displayMessage(FacesMessage.SEVERITY_WARN, false, message);
    }
    
    public void displayWarnMessage(String message, Object... args) {
        displayMessage(FacesMessage.SEVERITY_WARN, false, message, args);
    }
    
    public void displayError(String messageKey) {
        displayMessage(FacesMessage.SEVERITY_ERROR, false, getResourceBundleKeyValue(messageKey));
    }
    
    public void displayError(String messageKey, Object... args) {
        displayMessage(FacesMessage.SEVERITY_ERROR, false, getResourceBundleKeyValue(messageKey), args);
    }
    
    public void displayErrorMessage(String message) {
        displayMessage(FacesMessage.SEVERITY_ERROR, false, message);
    }
    
    public void displayErrorMessage(String message, Object... args) {
        displayMessage(FacesMessage.SEVERITY_ERROR, false, message, args);
    }
    
    public void displayErrorMessageKey(boolean outsideLifeCycle, String messageKey, String reason, Object... args) {
        displayErrorMessageBundle(messageBundle, messageKey, reason, outsideLifeCycle, args);
    }
    
    private void displayErrorMessageBundle(String messageBundle, String messageKey, String reason, boolean outsideLifeCycle, Object... args) {
        final String METHODNAME = "displayErrorMessageBundle ";
        logger.debug(METHODNAME, messageBundle, "; ", messageKey, "; ", reason, "; ", args != null ? Arrays.asList(args) : null);
        //
        // This works by locating the messageKey in the message bundle, see message.properties
        // If the message key is defined in the message.properties, the text associated with the
        // message key will be rendered
        //
        // If the message key isn't defined, the default text will be the reason if reason exists or the
        // MessageKey needs to be defined text.
        //
        setMessageBundle(messageBundle);
        boolean foundMessage = selectMessageBundle(messageKey);
        String message = null;
        if (foundMessage) {
            message = getResourceBundleKeyValue(messageKey);
        }

        // Add message key to args
        args = addMessageKeyToArgs(messageKey, args);

        // Generate default text
        String defaultText = "MessageKey (" + messageKey + ") needs to be defined; values {" + concatArgs(args) + "}.";

        // If reason is supplied, use reason for default text
        if (!StringUtils.isEmpty(reason)) {
            String period = ".";
            if (reason.substring(reason.length() - 1).equals(".")) {
                period = "";
            }
            defaultText = reason + " (" + messageKey + ")" + period;
        }
        
        if (message != null) {
            message = getMessageFormat(message, args);
        }
        
        if (StringUtils.isEmpty(message)) {
            message = defaultText;
        }
        
        logger.debug(METHODNAME, "about to displayErrorMessage message=", message);
        displayMessage(FacesMessage.SEVERITY_ERROR, outsideLifeCycle, message);
    }
    
    private void displayMessage(Severity severity, boolean outsideLifeCycle, String message, Object... args) {
        final String METHODNAME = "displayMessage ";
        logger.debug(METHODNAME, message, "; ", args != null ? Arrays.asList(args) : null);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            String messageFormat;
            if (args != null) {
                messageFormat = getMessageFormat(message, args);
            } else {
                messageFormat = message;
            }
            if (outsideLifeCycle) {
                String js = String.format("PF('%s').renderMessage({'summary':'', 'detail':'%s', 'severity':'%s'});",
                        StartupListener.MESSAGE_DISPLAY_WIDGET_VAR,
                        messageFormat, getStringSeverity(severity));
                RequestContext.getCurrentInstance().execute(js);
            } else {
                facesContext.addMessage(null, getFacesMessage(severity, null, messageFormat));
            }
        } else {
            logger.error(METHODNAME, message, "; ", args != null ? Arrays.asList(args) : null);
            logger.error(METHODNAME, "facesContext is null!");
        }
    }
    
    public void displayMessage(Message message) {
        logger.debug("displayMessage ", message);
        
        String messageKey = message.getMessageKey();
        String messageDisplay = message.getMessageDisplay();
        Object[] messageArguments = (String[]) message.getMessageArguments();
        
        if (null != message.getMessageType()) {
            if (!StringUtils.isEmpty(messageKey)) {
                displayMessage(getSeverity(message.getMessageType()), false, getResourceBundleKeyValue(messageKey), messageArguments);
            } else if (!StringUtils.isEmpty(messageDisplay)) {
                displayMessage(getSeverity(message.getMessageType()), false, messageDisplay, messageArguments);
            }
        }
    }

    /**
     * Get String from Severity.
     *
     * @param severity
     * @return
     */
    private String getStringSeverity(Severity severity) {
        final String METHODNAME = "getStringSeverity ";
        logger.debug(METHODNAME, "severity=", severity);
        if (severity != null) {
            return severity.toString().split(" ")[0].toLowerCase();
        } else {
            return "error";
        }
    }

    /**
     * Convert MessageType into Severity.
     *
     * @param messageType
     * @return
     */
    private Severity getSeverity(MessageType messageType) {
        final String METHODNAME = "getSeverity ";
        logger.debug(METHODNAME, "messageType=", messageType);
        if (messageType != null) {
            
            switch (messageType) {
                case Info:
                    return FacesMessage.SEVERITY_INFO;
                case Fatal:
                    return FacesMessage.SEVERITY_FATAL;
                case Warn:
                    return FacesMessage.SEVERITY_WARN;
                case Error:
                    return FacesMessage.SEVERITY_ERROR;
                default:
                    return FacesMessage.SEVERITY_ERROR;
            }
        } else {
            return FacesMessage.SEVERITY_ERROR;
        }
    }
    
    private String concatArgs(Object... args) {
        String arguments = "";
        if (args.length > 0) {
            String delimiter = "";
            for (Object object : args) {
                if (object != null) {
                    arguments += delimiter + object.toString();
                    delimiter = " ";
                }
            }
        }
        return arguments;
    }
    
    private Object[] addMessageKeyToArgs(String messageKey, Object... argsIn) {
        final String METHODNAME = "addMessageKeyToArgs ";

        // Add messageKey to args
        Object[] argsOut = new Object[1];
        argsOut[0] = messageKey;

        // Are there any existing args ?
        if (argsIn != null && argsIn.length > 0) {
            // resize argsOut array
            argsOut = new Object[argsIn.length + 1];
            argsOut[0] = messageKey;
            int counter = 0;
            for (Object arg : argsIn) {
                counter++;
                argsOut[counter] = arg;
            }
        }
        if (logger.isDebugEnabled()) {
            for (Object arg : argsOut) {
                logger.debug(METHODNAME + "arg=" + arg);
            }
        }
        
        return argsOut;
    }
    
    private String getMessageFormat(String message, Object... args) {
        final String METHODNAME = "getMessageFormat ";
        try {
            return MessageFormat.format(message, args);
        } catch (Exception e) {
            logger.error(METHODNAME, "message=", message);
            logger.error(METHODNAME, "args=", args != null ? Arrays.asList(args) : null);
            logger.error(METHODNAME, e);
            return message;
        }
    }
    
    private FacesMessage getFacesMessage(Severity severity, String summary, String detail) {
        final String METHODNAME = "getFacesMessage ";
        try {
            return new FacesMessage(severity, summary, detail);
        } catch (Exception e) {
            logger.error(METHODNAME, "severity=", severity);
            logger.error(METHODNAME, "summary=", summary);
            logger.error(METHODNAME, "detail=", detail);
            return new FacesMessage(severity, summary, "There was an error producing the FacesMessage - see log for details.");
        }
    }
    
}
