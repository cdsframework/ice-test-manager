/**
 * The MTS support core project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
package org.cdsframework.base;

import org.cdsframework.client.MtsClient;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.ejb.remote.GeneralMGRRemote;
import org.cdsframework.enumeration.ExceptionReason;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.NoSuchEJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.cdsframework.util.ClassUtils;

/**
 *
 * @param <T>
 * @author HLN Consulting, LLC
 */
public abstract class BaseClient<T extends BaseRemote> {

    protected final LogUtils logger;
    protected final Class clientClass;
    protected Integer retryCount = 5;
    protected Integer sleepTime = 1000;
    protected MtsClient mtsClient;
    protected String remoteJNDIReference;
    protected T clientManager;

    public BaseClient() {
        this.clientClass = BaseClient.class;
        logger = LogUtils.getLogger(BaseClient.class);
    }

    public BaseClient(Class logClass) {
        this.clientClass = logClass;
        logger = LogUtils.getLogger(logClass);
    }

    public void setMtsClient(MtsClient mtsClient) {
        this.mtsClient = mtsClient;
    }

    public void init() throws NamingException {
        logger.debug("Initializing: ", remoteJNDIReference);
        InitialContext initialContext = mtsClient.getCtx();
        clientManager = (T) initialContext.lookup(String.format("%s/%s", mtsClient.getMtsJndiRoot(), remoteJNDIReference));
    }

    public T getClientManager() {
        return clientManager;
    }

    /**
     *
     * @return
     */
    public Integer getRetryCount() {
        return retryCount;
    }

    /**
     *
     * @param retryCount
     */
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    /**
     *
     * @return
     */
    public Integer getSleepTime() {
        return sleepTime;
    }

    /**
     *
     * @param sleepTime
     */
    public void setSleepTime(Integer sleepTime) {
        this.sleepTime = sleepTime;
    }

    /**
     * Get the value of remoteJNDIReference
     *
     * @return the value of remoteJNDIReference
     */
    public String getRemoteJNDIReference() {
        return remoteJNDIReference;
    }

    /**
     * Set the value of remoteJNDIReference
     *
     * @param remoteJNDIReference new value of remoteJNDIReference
     */
    public void setRemoteJNDIReference(String remoteJNDIReference) {
        logger.debug("Setting remote JNDI reference to: ", remoteJNDIReference);
        this.remoteJNDIReference = remoteJNDIReference;
    }

    private SessionDTO getSessionFromArgs(Object[] args) {
        SessionDTO sessionDTO = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof SessionDTO) {
                sessionDTO = (SessionDTO) args[i];
            }
        }
        return sessionDTO;
    }
//
//    private Object[] fixArgs(Object[] args) {
//        Object[] newArgs = new Object[args.length];
//        for (int i = 0; i < args.length; i++) {
//            if (args[i] instanceof SessionDTO && ((SessionDTO) args[i]).getUserDTO().getUsername().equals(mtsClient.getUsername())) {
//                if (logger.isDebugEnabled()) {
//                    logger.debug("replacing old session in args with new one: old - "
//                            + ((SessionDTO) args[i]).getSessionId()
//                            + " new - " + mtsClient.getSession().getSessionId());
//                }
//                newArgs[i] = mtsClient.getSession();
//            } else {
//                if (logger.isDebugEnabled()) {
//                    logger.debug("keeping arg " + i + ": " + args[i]);
//                }
//                newArgs[i] = args[i];
//            }
//        }
//        return newArgs;
//    }

    private void reconnect() throws MtsException {
        logger.error("reconnecting...");
        try {
            Thread.sleep(sleepTime);
            mtsClient.init();
            init();
        } catch (NamingException ne) {
            throw new MtsException(ne.getMessage(), ne);
        } catch (NotFoundException nfe) {
            throw new MtsException(nfe.getMessage(), nfe);
        } catch (AuthenticationException ae) {
            throw new MtsException(ae.getMessage(), ae);
        } catch (AuthorizationException ae) {
            throw new MtsException(ae.getMessage(), ae);
        } catch (InterruptedException ie) {
            throw new MtsException(ie.getMessage(), ie);
        }

    }

    protected Object invoke(String methodName, Object... args) throws MtsException {
        if (logger.isDebugEnabled()) {
            logger.info("methodName: " + methodName);
            logger.info("arglist size: " + args.length);
        }
        return invoke(retryCount, methodName, args);
    }

    private Object invoke(Integer retry, String methodName, Object... args) throws MtsException {
        if (clientManager == null) {
            reconnect();
            if (clientManager == null) {
                throw new MtsException("clientManager was null: ");
            }
        }

        if (logger.isDebugEnabled()) {
            if (!"authenticate".equals(methodName) && !"login".equals(methodName)) {
                logger.info("invoke called with retry: ", retry, 
                        " - manager: ", clientManager.getClass().getSimpleName(), 
                        " - method: ", methodName, " ", 
                        StringUtils.getStringFromArray(", ", args));
            } else {
                logger.info("invoke called with retry: " + retry
                        + " - manager: " + clientManager.getClass().getSimpleName()
                        + " - method: " + methodName);
            }
        }

        List<Class<?>> baseClassArgs = new LinkedList<Class<?>>();
        List<Class<?>> classArgs = new LinkedList<Class<?>>();
        int position = 0;
        for (Object item : args) {
            logger.debug("arg position=", position);
            position++;
            if (item instanceof ArrayList) {
                classArgs.add((Class<?>) List.class);
                baseClassArgs.add((Class<?>) List.class);
                logger.debug("arg type ArrayList");
            } else if (item != null
                    && item.getClass() != null
                    && !(item instanceof SessionDTO)
                    && BaseDTO.class.isAssignableFrom(item.getClass())) {
                baseClassArgs.add(BaseDTO.class);
                classArgs.add((Class<?>) item.getClass());
                logger.debug("arg type BaseDTO.class");
            } else if (position == 1 && item instanceof SessionDTO) {
                baseClassArgs.add(BaseDTO.class);
                classArgs.add((Class<?>) item.getClass());
            } else {
                if (item != null) {
                    logger.debug("item.getClass().getSimpleName()=", item.getClass().getSimpleName());
                } else {
                    logger.debug("item is null");
                }
                try {
                    baseClassArgs.add((Class<?>) item.getClass());
                    classArgs.add((Class<?>) item.getClass());
                } catch (NullPointerException npe) {
                    throw new MtsException("Null parameters are not allowed. " + methodName + "(" + clientManager.getClass().getSimpleName() + ")", npe);
                }
            }
        }
        if (!(clientManager instanceof GeneralMGRRemote) && "findByPrimaryKey".equals(methodName) && classArgs.get(0).getSuperclass() != BaseDTO.class) {
            classArgs.remove(0);
            baseClassArgs.remove(0);
            classArgs.add(0, Object.class);
            baseClassArgs.add(0, Object.class);
        }
        try {
            Method m = null;
            try {
                m = clientManager.getClass().getMethod(methodName, classArgs.toArray(new Class<?>[0]));
            } catch (NoSuchMethodException nsme) {
                m = clientManager.getClass().getMethod(methodName, baseClassArgs.toArray(new Class<?>[0]));
            }
            return m.invoke(clientManager, args);
        } catch (NoSuchMethodException nsme) {
            StringBuilder sb = new StringBuilder();
            sb.append("ORIGINAL METHOD: ").append(methodName).append("(");
            for (Class<?> arg : baseClassArgs) {
                sb.append("Class: ").append(arg).append(", ");
            }
            sb.append(") <<<>>> ");
            for (Method m : clientManager.getClass().getDeclaredMethods()) {
                logger.debug("m.getName()=" + m.getName());
                for (Class<?> arg : m.getParameterTypes()) {
                    logger.debug("Class: " + arg.getSimpleName() + ", ");
                }
                if (m.getName().equals(methodName)) {
                    sb.append("Method: ").append(m.getName()).append("(");
                    for (Class<?> arg : m.getParameterTypes()) {
                        sb.append("Class ").append(arg.getSimpleName()).append(", ");
                    }
                    sb.append("); --- ");
                }
            }
            throw new MtsException(nsme.getMessage() + " === " + clientManager.getClass().getSimpleName() + " === " + sb.toString(), nsme);
        } catch (IllegalAccessException iae) {
            throw new MtsException(iae.getMessage(), iae);
        } catch (InvocationTargetException ite) {
            // Do not print out stack trace for NotLQChildException
            if (ite.getCause().getClass().getCanonicalName().equalsIgnoreCase("mci.lead.NotLQChildException") ||
               ite.getCause().getClass().getCanonicalName().equalsIgnoreCase("org.cdsframework.exceptions.NotFoundException")) {
                logger.error("invoke InvocationTargetException manager: "
                        + clientManager.getClass().getSimpleName()
                        + " - method: " + methodName + " - cause:"
                        + ite.getCause().getClass().getSimpleName());
            }
            else {
            logger.error("invoke InvocationTargetException manager: "
                    + clientManager.getClass().getSimpleName()
                    + " - method: " + methodName + " - cause:"
                    + ite.getCause().getClass().getSimpleName(), ite);
            }
            if (ite.getCause() instanceof AuthenticationException) {
                logger.error("AuthenticationException reason: " + ((AuthenticationException) ite.getCause()).getReason());
            }
            if (((ite.getCause() instanceof NoSuchEJBException)
                    || (ite.getCause() instanceof AuthenticationException && (((AuthenticationException) ite.getCause()).getReason() == ExceptionReason.SESSION_EXPIRED
                    || ((AuthenticationException) ite.getCause()).getReason() == ExceptionReason.MISSING_ENTRY))) && retry > 0) {
                logger.error(ite.getCause().getClass().getSimpleName() + ": reconnecting...");
                SessionDTO sessionDTO = this.getSessionFromArgs(args);
                logger.debug("Session from args: ", sessionDTO);
                if (sessionDTO != null) {
                    logger.debug("UserDTO from args session: ", sessionDTO.getUserDTO());
                    if (sessionDTO.getUserDTO() != null) {
                        logger.debug("Username from args session UserDTO: ", sessionDTO.getUserDTO().getUsername());
                    }
                } else {
                    sessionDTO = mtsClient.getSession();
                    logger.debug("sessionDTO: ", sessionDTO);
                    if (sessionDTO != null) {
                        logger.debug("UserDTO from mtsClient session: ", sessionDTO.getUserDTO());
                        if (sessionDTO.getUserDTO() != null) {
                            logger.debug("Username from mtsClient session UserDTO: ", sessionDTO.getUserDTO().getUsername());
                        }
                    }
                }
                if (mtsClient != null
                        && mtsClient.getUsername() != null
                        && sessionDTO != null
                        && sessionDTO.getUserDTO() != null
                        && sessionDTO.getUserDTO().getUsername() != null) {
                    if (mtsClient.getUsername().equals(sessionDTO.getUserDTO().getUsername())) {
                        // regular app session - recreate a new one and replace the old properties...
                        reconnect();
                        sessionDTO.setProxy(mtsClient.getSession().isProxy());
                        sessionDTO.setAppDTO(mtsClient.getSession().getAppDTO());
                        sessionDTO.setSessionId(mtsClient.getSession().getSessionId());
                        sessionDTO.setUserDTO(mtsClient.getSession().getUserDTO());
                    } else if (sessionDTO.isProxy()) {
                        try {
                            // if it is a proxy session - get a new one and replace the old properties...
                            reconnect();
                            SessionDTO proxiedUserSession = mtsClient.getProxiedUserSession(sessionDTO.getUserDTO().getUsername());
                            sessionDTO.setProxy(proxiedUserSession.isProxy());
                            sessionDTO.setAppDTO(proxiedUserSession.getAppDTO());
                            sessionDTO.setSessionId(proxiedUserSession.getSessionId());
                            sessionDTO.setUserDTO(proxiedUserSession.getUserDTO());
                        } catch (AuthenticationException e) {
                            logger.error("AuthenticationException on proxy session get: ", e);
                        } catch (AuthorizationException e) {
                            logger.error("AuthorizationException on proxy session get: ", e);
                        } catch (NotFoundException e) {
                            logger.error("NotFoundException on proxy session get: ", e);
                        }
                    }
                } else {
                    logger.error("MtsClient: ", mtsClient);
                    if (mtsClient != null) {
                        logger.error("mtsClient.getUsername(): ", mtsClient.getUsername());
                    } else {
                        logger.error("MtsClient is null!!!");
                    }
                    logger.error("sessionDTO: ", sessionDTO);
                    if (sessionDTO != null) {
                        logger.error("sessionDTO.getUserDTO(): ", sessionDTO.getUserDTO());
                        if (sessionDTO.getUserDTO() != null) {
                            logger.error("sessionDTO.getUserDTO().getUsername(): ", sessionDTO.getUserDTO().getUsername());
                        } else {
                            logger.error("sessionDTO.getUserDTO() is null!!!");
                        }
                    } else {
                        logger.error("sessionDTO is null!!!");
                    }
                    if (ite.getCause() instanceof AuthenticationException) {
                        throw new MtsException("An AuthenticationException has occurred",ite);
                    }
                }
                return invoke((retry - 1), methodName, args);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Default InvocationTargetException handling: " + ite.getMessage()
                        + " - " + ite.getCause().getMessage()
                        + " - " + ite.getCause().getClass().getSimpleName());
            }
            throw new MtsException(ite.getMessage(), ite);
        } catch (UnsupportedOperationException uoe) {
            throw new MtsException(uoe.getMessage(), uoe);
        }
    }

    /**
     *
     * @param methodName
     * @param args
     * @return
     */
    protected Object mtsInvokeNE(String methodName, Object... args) {
        try {
            return invoke(methodName, args);
        } catch (MtsException e) {
            throw new RuntimeException("This shouldn't have happened.", e);
        }
    }

    /**
     *
     * @param methodName
     * @param args
     * @return
     * @throws NotFoundException
     * @throws MtsException
     */
    protected Object mtsInvokeNFC(String methodName, Object... args)
            throws NotFoundException, MtsException {
        try {
            return invoke(methodName, args);
        } catch (MtsException e) {
            if (e.getCause() != null && e.getCause() instanceof InvocationTargetException && e.getCause().getCause() != null) {
                if (e.getCause().getCause() instanceof NotFoundException) {
                    throw (NotFoundException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof MtsException) {
                    throw (MtsException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof EJBException) {
                    throw (EJBException) e.getCause().getCause();
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        }
    }

    /**
     *
     * @param methodName
     * @param args
     * @return
     * @throws MtsException
     * @throws AuthenticationException
     * @throws AuthorizationException
     */
    protected Object mtsInvokeAAE(String methodName, Object... args)
            throws MtsException, AuthenticationException, AuthorizationException {
        try {
            return invoke(methodName, args);
        } catch (MtsException e) {
            if (e.getCause() != null && e.getCause() instanceof InvocationTargetException && e.getCause().getCause() != null) {
                if (e.getCause().getCause() instanceof AuthenticationException) {
                    throw (AuthenticationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof AuthorizationException) {
                    throw (AuthorizationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof MtsException) {
                    throw (MtsException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof EJBException) {
                    throw (EJBException) e.getCause().getCause();
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        }
    }

    /**
     *
     * @param methodName
     * @param args
     * @return
     * @throws MtsException
     * @throws AuthenticationException
     */
    protected Object mtsInvokeAE(String methodName, Object... args)
            throws MtsException, AuthenticationException {
        try {
            return invoke(methodName, args);
        } catch (MtsException e) {
            if (e.getCause() != null && e.getCause() instanceof InvocationTargetException && e.getCause().getCause() != null) {
                if (e.getCause().getCause() instanceof AuthenticationException) {
                    throw (AuthenticationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof MtsException) {
                    throw (MtsException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof EJBException) {
                    throw (EJBException) e.getCause().getCause();
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        }
    }

    /**
     *
     * @param methodName
     * @param args
     * @return
     * @throws MtsException
     */
    protected Object mtsInvokeCE(String methodName, Object... args)
            throws MtsException {
        try {
            return invoke(methodName, args);
        } catch (MtsException e) {
            if (e.getCause() != null && e.getCause() instanceof InvocationTargetException && e.getCause().getCause() != null) {
                if (e.getCause().getCause() instanceof MtsException) {
                    throw (MtsException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof EJBException) {
                    throw (EJBException) e.getCause().getCause();
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        }
    }

    /**
     *
     * @param methodName
     * @param args
     * @return
     * @throws ValidationException
     * @throws NotFoundException
     * @throws MtsException
     * @throws AuthenticationException
     * @throws AuthorizationException
     */
    protected Object mtsInvokeFind(String methodName, Object... args)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        try {
            return invoke(methodName, args);
        } catch (MtsException e) {
            if (e.getCause() != null && e.getCause() instanceof InvocationTargetException && e.getCause().getCause() != null) {
                if (e.getCause().getCause() instanceof ValidationException) {
                    throw (ValidationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof NotFoundException) {
                    throw (NotFoundException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof MtsException) {
                    throw (MtsException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof AuthenticationException) {
                    throw (AuthenticationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof AuthorizationException) {
                    throw (AuthorizationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof EJBException) {
                    throw (EJBException) e.getCause().getCause();
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        }
    }

    /**
     *
     * @param methodName
     * @param args
     * @return
     * @throws ValidationException
     * @throws NotFoundException
     * @throws MtsException
     * @throws AuthenticationException
     * @throws AuthorizationException
     */
    protected Object mtsInvokeFindObject(String methodName, Object... args)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        try {
            return invoke(methodName, args);
        } catch (MtsException e) {
            if (e.getCause() != null && e.getCause() instanceof InvocationTargetException && e.getCause().getCause() != null) {
                if (e.getCause().getCause() instanceof ValidationException) {
                    throw (ValidationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof NotFoundException) {
                    throw (NotFoundException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof MtsException) {
                    throw (MtsException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof AuthenticationException) {
                    throw (AuthenticationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof AuthorizationException) {
                    throw (AuthorizationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof EJBException) {
                    throw (EJBException) e.getCause().getCause();
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        }
    }

    /**
     *
     * @param methodName
     * @param args
     * @return
     * @throws ValidationException
     * @throws NotFoundException
     * @throws MtsException
     * @throws AuthenticationException
     * @throws AuthorizationException
     */
    protected Object mtsInvokeFindObjectList(String methodName, Object... args)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        try {
            return invoke(methodName, args);
        } catch (MtsException e) {
            if (e.getCause() != null && e.getCause() instanceof InvocationTargetException && e.getCause().getCause() != null) {
                if (e.getCause().getCause() instanceof ValidationException) {
                    throw (ValidationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof NotFoundException) {
                    throw (NotFoundException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof MtsException) {
                    throw (MtsException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof AuthenticationException) {
                    throw (AuthenticationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof AuthorizationException) {
                    throw (AuthorizationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof EJBException) {
                    throw (EJBException) e.getCause().getCause();
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        }
    }

    /**
     *
     * @param methodName
     * @param args
     * @return
     * @throws ValidationException
     * @throws NotFoundException
     * @throws MtsException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws ConstraintViolationException
     */
    protected Object mtsInvokeDelete(String methodName, Object... args)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException, ConstraintViolationException {
        try {
            return invoke(methodName, args);
        } catch (MtsException e) {
            if (e.getCause() != null && e.getCause() instanceof InvocationTargetException && e.getCause().getCause() != null) {
                if (e.getCause().getCause() instanceof ValidationException) {
                    throw (ValidationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof NotFoundException) {
                    throw (NotFoundException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof ConstraintViolationException) {
                    throw (ConstraintViolationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof MtsException) {
                    throw (MtsException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof AuthenticationException) {
                    throw (AuthenticationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof AuthorizationException) {
                    throw (AuthorizationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof EJBException) {
                    throw (EJBException) e.getCause().getCause();
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        }
    }

    /**
     *
     * @param methodName
     * @param args
     * @return
     * @throws ValidationException
     * @throws NotFoundException
     * @throws ConstraintViolationException
     * @throws MtsException
     * @throws AuthenticationException
     * @throws AuthorizationException
     */
    protected Object mtsInvokeAddUpdate(String methodName, Object... args)
            throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException, AuthorizationException {
        try {
            return invoke(methodName, args);
        } catch (MtsException e) {
            logger.debug("GOT MtsException cause: " + e.getCause());
            if (e.getCause() != null && e.getCause() instanceof InvocationTargetException && e.getCause().getCause() != null) {
                logger.debug("GOT InvocationTargetException cause: " + e.getCause().getCause());
                if (e.getCause().getCause() instanceof ValidationException) {
                    throw (ValidationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof NotFoundException) {
                    throw (NotFoundException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof ConstraintViolationException) {
                    throw (ConstraintViolationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof MtsException) {
                    throw (MtsException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof AuthenticationException) {
                    throw (AuthenticationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof AuthorizationException) {
                    throw (AuthorizationException) e.getCause().getCause();
                } else if (e.getCause().getCause() instanceof EJBException) {
                    throw (EJBException) e.getCause().getCause();
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        }
    }
}
