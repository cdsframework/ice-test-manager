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
package org.cdsframework.handlers;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.PropertyNotFoundException;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.CatAbortException;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.IntegrityViolationException;
import org.cdsframework.exceptions.UncaughtSQLException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.message.MessageMGR;
import org.cdsframework.util.BeanUtils;
import org.cdsframework.util.BrokenRule;
import org.cdsframework.util.FlashUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;
import org.cdsframework.util.UtilityMGR;
//import org.jboss.seam.international.status.builder.BundleKey;

/**
 *
 * @author HLN Consulting, LLC
 */
public class DefaultExceptionHandler extends ExceptionHandlerWrapper {

    private static final LogUtils logger = LogUtils.getLogger(DefaultExceptionHandler.class);
    private final ExceptionHandler wrapped;
    private static MessageMGR messageMGR;

    public DefaultExceptionHandler(ExceptionHandler wrapped) {
        final String METHODNAME = "DefaultExceptionHandler ";
        this.wrapped = wrapped;
        try {
            messageMGR = BeanUtils.getBean(MessageMGR.class);
        } catch (NamingException e) {
            logger.error(METHODNAME, "MessageMGR not found!");
        }
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    public static boolean handleException(Throwable throwable, Class messageBundleClass) {
        return handleException(throwable, messageBundleClass, false);
    }

    public static boolean handleException(Throwable throwable, Class messageBundleClass, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException(Throwable throwable, Class messageBundleClass) ";
        if (messageMGR == null) {
            try {
                messageMGR = BeanUtils.getBean(MessageMGR.class);
            } catch (NamingException e) {
                logger.error(METHODNAME, "MessageMGR not found!");
            }
        }
        if (messageMGR == null) {
            throw new IllegalStateException("MessageMGR not found!");
        }
        logger.info(METHODNAME, "messageBundleClass=", messageBundleClass);
        if (messageBundleClass == null) {
            messageMGR.setMessageBundle(DefaultExceptionHandler.class);
        } else {
            messageMGR.setMessageBundle(messageBundleClass);
        }
        boolean remove = true;
        // ConstraintViolationException
        if (checkForException(throwable, ConstraintViolationException.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, ConstraintViolationException.class), outsideLifeCycle);

            // DataIntegrityViolationException
        } else if (checkForException(throwable, IntegrityViolationException.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, IntegrityViolationException.class), outsideLifeCycle);

            // SQLIntegrityConstraintViolationException
        } else if (checkForException(throwable, SQLIntegrityConstraintViolationException.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, SQLIntegrityConstraintViolationException.class), outsideLifeCycle);

            // UncategorizedSQLException
        } else if (checkForException(throwable, UncaughtSQLException.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, UncaughtSQLException.class), outsideLifeCycle);

            // MtsException
        } else if (checkForException(throwable, MtsException.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, MtsException.class), outsideLifeCycle);

            // NonexistentConversationException
        } else if (checkForException(throwable, NonexistentConversationException.class)) {
            handleException(getException(throwable, NonexistentConversationException.class), outsideLifeCycle);

            // AuthenticationException
        } else if (checkForException(throwable, AuthenticationException.class)) {
            handleException(getException(throwable, AuthenticationException.class), outsideLifeCycle);

            // AuthorizationException
        } else if (checkForException(throwable, AuthorizationException.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, AuthorizationException.class), outsideLifeCycle);

            // ViewExpiredException
        } else if (checkForException(throwable, ViewExpiredException.class)) {
            handleException(getException(throwable, ViewExpiredException.class), outsideLifeCycle);

            // ValidationException
        } else if (checkForException(throwable, ValidationException.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, ValidationException.class), outsideLifeCycle);

            // CatException
        } else if (checkForException(throwable, CatException.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, CatException.class), outsideLifeCycle);

            // CatAbortException
        } else if (checkForException(throwable, CatAbortException.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, CatAbortException.class), outsideLifeCycle);

            // AbortProcessingException
        } else if (checkForException(throwable, AbortProcessingException.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, AbortProcessingException.class), outsideLifeCycle);

            // PropertyNotFoundException
        } else if (checkForException(throwable, PropertyNotFoundException.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, PropertyNotFoundException.class), outsideLifeCycle);

            // InvocationTargetException
        } else if (checkForException(throwable, InvocationTargetException.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, InvocationTargetException.class), outsideLifeCycle);

        // NoSuchMethodError
        } else if (checkForException(throwable, NoSuchMethodError.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, NoSuchMethodError.class), outsideLifeCycle);

        // IllegalArgumentException
        } else if (checkForException(throwable, IllegalArgumentException.class)) {
            logger.error(METHODNAME, throwable);
            handleException(getException(throwable, IllegalArgumentException.class), outsideLifeCycle);
            
            // Unhandled
        } else {
            handleUnhandledException(throwable, outsideLifeCycle);
            remove = false;
        }
        return remove;
    }

    @Override
    public void handle() throws FacesException {
        final String METHODNAME = "handle ";
        Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        logger.debug(METHODNAME + "Begin handling queue");
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable throwable = context.getException();
            logExceptionStack(throwable, 1);
            if (handleException(throwable, null)) {
                i.remove();
            }
        }
        logger.debug(METHODNAME + "End handling queue");
        getWrapped().handle();
    }

    private void logExceptionStack(Throwable t, int position) {
        if (t != null) {
            logger.info("EXCEPTION ENCOUNTERED: ", t.getClass(), " at position ", position, ": ", t.getMessage());
            int next = position + 1;
            logExceptionStack(t.getCause(), next);
        }
    }

    private static boolean checkForException(Throwable t, Class exceptionType) {
        if (t == null) {
            return false;
        }
        if (t.getClass() == exceptionType) {
            return true;
        } else {
            return checkForException(t.getCause(), exceptionType);
        }
    }

    private static <T> T getException(Throwable t, Class<T> exceptionType) {
        if (t == null) {
            return null;
        }
        if (t.getClass() == exceptionType) {
            return (T) t;
        } else {
            return getException(t.getCause(), exceptionType);
        }
    }

    private static void handleException(PropertyNotFoundException t, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException - PropertyNotFoundException: ";
        if (messageMGR != null) {
            messageMGR.displayErrorMessageKey(outsideLifeCycle, "PropertyNotFoundException", null, t.getMessage());
        } else {
            logger.error(METHODNAME, t);
        }
    }

    private static void handleException(NoSuchMethodError t, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException - NoSuchMethodError: ";
        if (messageMGR != null) {
            messageMGR.displayErrorMessageKey(outsideLifeCycle, "NoSuchMethodError", null, t.getMessage());
        } else {
            logger.error(METHODNAME, t);
        }
    }

    private static void handleException(AuthorizationException t, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException - AuthorizationException: ";
        if (messageMGR != null) {
            messageMGR.displayErrorMessageKey(outsideLifeCycle, "AuthorizationException", t.getReason().toString(), t.getMessage());
        } else {
            logger.error(METHODNAME, t);
        }
    }

    private static void handleException(IllegalArgumentException t, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException - IllegalArgumentException: ";
        if (messageMGR != null) {
            messageMGR.displayErrorMessageKey(outsideLifeCycle, "IllegalArgumentException", null, t.getMessage());
        } else {
            logger.error(METHODNAME, t);
        }
    }
    
    private static void handleException(AbortProcessingException t, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException - AbortProcessingException: ";
        if (messageMGR != null) {
            messageMGR.displayErrorMessageKey(outsideLifeCycle, "AbortProcessingException", null, t.getMessage());
        } else {
            logger.error(METHODNAME, t);
        }
    }

    private static void handleException(InvocationTargetException t, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException - InvocationTargetException: ";
        if (messageMGR != null) {
            messageMGR.displayErrorMessageKey(outsideLifeCycle, "InvocationTargetException", null, t.getMessage());
        } else {
            logger.error(METHODNAME, t);
        }
    }

    private static void handleException(IntegrityViolationException t, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException - IntegrityViolationException: ";
        if (messageMGR != null) {
            messageMGR.displayErrorMessageKey(outsideLifeCycle, "DataIntegrityViolationException", null, t.getMessage());
        } else {
            logger.error(METHODNAME, t);
        }
    }

    private static void handleException(SQLIntegrityConstraintViolationException t, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException - SQLIntegrityConstraintViolationException: ";
        if (messageMGR != null) {
            messageMGR.displayErrorMessageKey(outsideLifeCycle, "SQLIntegrityConstraintViolationException", null, t.getMessage());
        } else {
            logger.error(METHODNAME, t);
        }
    }

    private static void handleException(MtsException e, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException(MtsException) ";
        if (messageMGR != null) {
            messageMGR.displayErrorMessageKey(outsideLifeCycle, "MtsException", null, e.getMessage());
        } else {
            logger.error(METHODNAME, e);
        }
    }

    private static void handleException(CatException e, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException(CatException) ";
        if (messageMGR != null) {
            messageMGR.displayErrorMessageKey(outsideLifeCycle, "CatException", null, e.getMessage());
        } else {
            logger.error(METHODNAME, e);
        }
    }

    private static void handleException(CatAbortException t, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException - CatAbortException: ";
        if (messageMGR != null) {
            messageMGR.displayErrorMessageKey(outsideLifeCycle, "CatAbortException", null, t.getMessage());
        } else {
            logger.error(METHODNAME, t);
        }
    }

    private static void handleException(ValidationException t, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException - ValidationException: ";
        List<BrokenRule> brokenRules = t.getBrokenRules();
        for (BrokenRule brokenRule : brokenRules) {
            // the message bundle if it exists
            String messageBundle = brokenRule.getMessageBundle();
            if (messageBundle != null && !messageBundle.endsWith(".message")) {
                messageBundle += ".message";
            }
            // message key is used as a key to the resource bundle
            String messageKey = brokenRule.getMessageKey();
            // Reason for the error
            String reason = brokenRule.getReason();
            // Values contain the pure data
            Object[] values = brokenRule.getValues();
            logger.info(METHODNAME, "broken rule instance messageBundle: ", messageBundle);
            logger.info(METHODNAME, "broken rule instance messageKey: ", messageKey);
            logger.info(METHODNAME, "broken rule instance reason: ", reason);
            logger.info(METHODNAME, "broken rule instance UUID: ", brokenRule.getUuid());
            
            logger.info(METHODNAME, "broken rule instance values: ", values == null ? "null" : Arrays.asList(values));
//                if (messageBundle != null && messageKey != null) {
////                    messageMGR.displayErrorMessageKey(new BundleKey(messageBundle, messageKey), "ValidationException", values);
//                } else {
            // Display the message
            messageMGR.displayErrorMessageKey(outsideLifeCycle, messageKey, reason, values);
//                }
        }
    }

    private static void handleException(AuthenticationException e, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException(AuthenticationException) ";
        logger.error(METHODNAME, "An authentication error occurred: ", e.getMessage());
        logger.error(METHODNAME, "Authentication error reason: ", e.getReason());
        handleNavigation("login", "loginFailed");
    }

    private static void handleException(ViewExpiredException t, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException - ViewExpiredException: ";
        handleNavigation("viewExpired", "viewExpired");
    }

    private static void handleException(NonexistentConversationException t, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException - NonexistentConversationException: ";
        handleNavigation("viewExpired", "viewExpired");
    }

    private static void handleException(UncaughtSQLException t, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException - UncaughtSQLException: ";
        Map<String, String> errorMap = UtilityMGR.parseUncategorizedSQLException(t.getMessage());
        if (messageMGR != null) {
            messageMGR.displayErrorMessageKey(outsideLifeCycle, "UncategorizedSQLException", null, errorMap.get("error"));
        } else {
            logger.error(METHODNAME, t);
        }
    }

    private static void handleException(ConstraintViolationException e, boolean outsideLifeCycle) {
        final String METHODNAME = "handleException(ConstraintViolationException) ";
        String errorMessage = "An ConstraintViolationException has occurred: " + e.getMessage();
        String constraintKey = e.getConstraintKey();
        String tableName = e.getTableName();
        String uniqueConstraintText = "duplicate key value violates unique constraint \"";
        String foreignKeyConstraintText = "violates foreign key constraint \"";
        if (errorMessage.contains(uniqueConstraintText)) {
            String[] split = errorMessage.split(uniqueConstraintText);
            if (split.length > 1) {
                constraintKey = String.format("%s.%s", tableName, split[1].split("\"")[0].toUpperCase());
            }
        } else if (errorMessage.contains(foreignKeyConstraintText)) {
            logger.info(METHODNAME, "found foreign key constraint violation");
            String[] split = errorMessage.split(foreignKeyConstraintText);
            logger.info(METHODNAME, "split size: ", split.length);
            if (split.length > 1) {
                constraintKey = String.format("%s.%s", tableName, split[1].split("\"")[0].toUpperCase());
            }
        }

        if (StringUtils.isEmpty(constraintKey)) {
            constraintKey = "ConstraintViolationException";
        }
        errorMessage += " ConstraintKey="
                + constraintKey
                + " Note: To replace message, place key in your plugin or class package message.properties file.";
        logger.error(METHODNAME, errorMessage);
        messageMGR.displayErrorMessageKey(outsideLifeCycle, constraintKey, null, e.getMessage());
    }

    private static void handleUnhandledException(Throwable throwable, boolean outsideLifeCycle) {
        final String METHODNAME = "handleUnhandledException(Throwable) ";
        logger.error(METHODNAME, throwable);
        String messageKey = "UnhandledException";

        // Get the rootCause
        Throwable rootCause = UtilityMGR.getRootCause(throwable);

        // Get the first 5 stack trace elements
        int counter = 0;
        ArrayList<Object> values = new ArrayList<Object>();
        values.add(rootCause.toString());
        for (StackTraceElement stackTraceElement : rootCause.getStackTrace()) {
            values.add(stackTraceElement);
            counter++;
            if (counter >= 5) {
                break;
            }
        }
        if (messageMGR != null) {
            messageMGR.setMessageBundle(DefaultExceptionHandler.class);
            messageMGR.displayErrorMessageKey(outsideLifeCycle, messageKey, null, values);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            if (facesContext != null) {
                Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
                if (requestMap != null) {
                    requestMap.put("errorInfo", rootCause);
                    requestMap.put("stackTraceInfo", UtilityMGR.getStackTrace(rootCause));
                }
            }
        }
    }

    public static void handleNavigation(String outcome, String messageKey) {
        final String METHODNAME = "handleNavigation ";
        logger.debug(METHODNAME, "outcome=", outcome);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        logger.debug(METHODNAME, "facesContext=", facesContext);
        // navigate to the outcome
        if (facesContext != null) {
            ExternalContext externalContext = facesContext.getExternalContext();
            if (externalContext != null) {
                FlashUtils.addFlashErrorMessageKey(
                        (HttpServletRequest) externalContext.getRequest(),
                        (HttpServletResponse) externalContext.getResponse(),
                        messageKey);
            }
            Application application = facesContext.getApplication();
            logger.info(METHODNAME, "application=", application);
            if (application != null) {
                NavigationHandler navigationHandler = application.getNavigationHandler();
                logger.info(METHODNAME, "navigationHandler=", navigationHandler);
                if (navigationHandler != null) {
                    navigationHandler.handleNavigation(facesContext, null, outcome);
                }
            }
            facesContext.renderResponse();
        }
    }

}
