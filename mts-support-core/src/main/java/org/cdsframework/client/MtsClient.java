/**
 * The MTS support core project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
package org.cdsframework.client;

import org.cdsframework.client.support.GeneralMGRClient;
import org.cdsframework.base.BaseClient;
import org.cdsframework.dto.AdminDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.ejb.remote.*;
import org.cdsframework.enumeration.ExceptionReason;
import org.cdsframework.util.DateUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;
import java.util.Properties;
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.cdsframework.util.DTOUtils;

/**
 * Provides a convenience client for communication with the middle tier.
 *
 * @author HLN Consulting, LLC
 */
public class MtsClient {

    private final LogUtils logger;
    public static final String DEFAULT_JNDI_ROOT = "java:global/mts";

    private final String adminJNDIReference = DTOUtils.getJndiReferenceURI(AdminDTO.class);
    private final String securityJNDIReference = DTOUtils.getJndiReferenceURI(SessionDTO.class);

    private final String mtsInitialHost;
    private final String mtsInitialPort;
    private final String mtsJndiRoot;

    private String username = null;
    private String password = null;
    private String appName = null;

    private SessionDTO session;

    private AdminMGRRemote adminManager;
    private SecurityMGRRemote securityManager;

    private final String factoryInitial = "com.sun.enterprise.naming.SerialInitContextFactory";
    private final String factoryUrlPkgs = "com.sun.enterprise.naming";
    private final String factoryState = "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl";

    private InitialContext ctx;

    /**
     * Initializes the client connection parameters.
     *
     * @param mtsInitialHost the EJB client connection host name.
     * @param mtsInitialPort the EJB client connection TCP/IP port.
     * @throws MtsException
     * @throws NotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws NamingException
     */
    public MtsClient(String mtsInitialHost, String mtsInitialPort)
            throws MtsException, NotFoundException, AuthenticationException, AuthorizationException, NamingException {
        this(mtsInitialHost, mtsInitialPort, DEFAULT_JNDI_ROOT);
    }

    /**
     * Initializes the client connection parameters.
     *
     * @param mtsInitialHost the EJB client connection host name.
     * @param mtsInitialPort the EJB client connection TCP/IP port.
     * @param mtsJndiRoot
     * @throws MtsException
     * @throws NotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws NamingException
     */
    public MtsClient(String mtsInitialHost, String mtsInitialPort, String mtsJndiRoot)
            throws MtsException, NotFoundException, AuthenticationException, AuthorizationException, NamingException {
        logger = LogUtils.getLogger(MtsClient.class);
        this.mtsInitialHost = mtsInitialHost;
        this.mtsInitialPort = mtsInitialPort;
        this.mtsJndiRoot = mtsJndiRoot;
        init();
    }

    /**
     * Initializes the client connection parameters.
     *
     * @param username
     * @param password
     * @param appName
     * @param mtsInitialHost the EJB client connection host name.
     * @param mtsInitialPort the EJB client connection TCP/IP port.
     * @throws MtsException
     * @throws NotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws NamingException
     */
    public MtsClient(String username,
            String password,
            String appName,
            String mtsInitialHost,
            String mtsInitialPort)
            throws MtsException, NotFoundException, AuthenticationException, AuthorizationException, NamingException {
        this(username, password, appName, mtsInitialHost, mtsInitialPort, DEFAULT_JNDI_ROOT);
    }

    /**
     * Initializes the client connection parameters.
     *
     * @param username
     * @param password
     * @param appName
     * @param mtsInitialHost the EJB client connection host name.
     * @param mtsInitialPort the EJB client connection TCP/IP port.
     * @param mtsJndiRoot
     * @throws MtsException
     * @throws NotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws NamingException
     */
    public MtsClient(String username,
            String password,
            String appName,
            String mtsInitialHost,
            String mtsInitialPort,
            String mtsJndiRoot)
            throws MtsException, NotFoundException, AuthenticationException, AuthorizationException, NamingException {
        logger = LogUtils.getLogger(MtsClient.class);
        this.username = username;
        this.password = password;
        this.mtsJndiRoot = mtsJndiRoot;
        this.appName = appName;
        this.mtsInitialHost = mtsInitialHost;
        this.mtsInitialPort = mtsInitialPort;
        init();
    }

    /**
     * Initializes the static client connection fields.
     *
     * @throws MtsException
     * @throws NotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     */
    public final void init()
            throws MtsException, NotFoundException, AuthenticationException, AuthorizationException {

        long startMS = System.currentTimeMillis();

        if (logger.isDebugEnabled()) {
            logger.debug(MtsClient.class.getSimpleName() + " - init(): entering at " + DateUtils.getLogDate());
        }

        if (!arePropertiesSet()) {
            String msg = MtsClient.class.getSimpleName() + " - init(): properties not initialized.";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        try {
            if (ctx != null) {
                adminManager = null;
                securityManager = null;
                ctx.close();
                ctx = null;
            }
            ctx = getCtx();

            adminManager = (AdminMGRRemote) ctx.lookup(String.format("%s/%s", mtsJndiRoot, adminJNDIReference));
            securityManager = (SecurityMGRRemote) ctx.lookup(String.format("%s/%s", mtsJndiRoot, securityJNDIReference));

            if (logger.isDebugEnabled()) {
                logger.debug(MtsClient.class.getSimpleName() + " - init(): srcSystemId='" + adminManager.getSrcSystemId() + "'.");
                logger.debug(MtsClient.class.getSimpleName() + " - init(): middle tier build info=" + adminManager.getBuildInfo());
                logger.debug(MtsClient.class.getSimpleName() + " - init(): SUCCESS!");
            }
            try {
                if (username != null && password != null && appName != null) {
                    session = securityManager.login(username, password, appName);
                }
            } catch (EJBException e) {
                throw new MtsException("Remote service unavailable", e);
            }
            if (logger.isDebugEnabled() && session != null) {
                logger.debug(MtsClient.class.getSimpleName() + " - init(): session='" + session.getSessionId() + "'.");
            }
        } catch (NamingException ne) {
            throw new MtsException(logger.error(MtsClient.class.getSimpleName() + " - init(): Context name lookup error." + ne.getMessage()), ne);
        } finally {
            long elapsedMS = System.currentTimeMillis() - startMS;
            if (logger.isDebugEnabled()) {
                logger.debug(MtsClient.class.getSimpleName() + " - init(): elapsedMS=" + elapsedMS);
            }
        }
    }

    public InitialContext getCtx() throws NamingException {
        InitialContext initialContext;
        if (ctx == null) {
            if (logger.isDebugEnabled()) {
                logger.debug(MtsClient.class.getSimpleName() + " - init(): username='" + username + "'.");
                logger.debug(MtsClient.class.getSimpleName() + " - init(): password='******************'.");
                logger.debug(MtsClient.class.getSimpleName() + " - init(): appName='" + appName + "'.");
                logger.debug(MtsClient.class.getSimpleName() + " - init(): mtsInitialHost='" + mtsInitialHost + "'.");
                logger.debug(MtsClient.class.getSimpleName() + " - init(): mtsInitialPort='" + mtsInitialPort + "'.");
            }

            Properties env = new Properties();
            env.put("org.omg.CORBA.ORBInitialHost", mtsInitialHost);
            env.put("org.omg.CORBA.ORBInitialPort", mtsInitialPort);
            env.put("java.naming.factory.initial", factoryInitial);
            env.put("java.naming.factory.url.pkgs", factoryUrlPkgs);
            env.put("java.naming.factory.state", factoryState);
            initialContext = new InitialContext(env);
        } else {
            initialContext = ctx;
        }
        return initialContext;
    }

    /**
     * Evaluates the initialized state of the class.
     *
     * @returns the initialized state of the class.
     */
    private boolean arePropertiesSet() {
        return !StringUtils.isEmpty(mtsInitialHost) && !StringUtils.isEmpty(mtsInitialPort);
    }

    /**
     * Terminates the client connection.
     *
     * @throws MtsException
     */
    public void terminate() throws MtsException {
        // Logout
        try {
            securityManager.logout(session);
            adminManager = null;
            securityManager = null;
            ctx.close();
            ctx = null;
        } catch (AuthenticationException e) {
            throw new MtsException(e.getMessage(), e);
        } catch (NamingException e) {
            throw new MtsException(e.getMessage(), e);
        }
    }

    public SessionDTO getProxiedUserSession(String username)
            throws AuthenticationException, AuthorizationException, MtsException, NotFoundException {
        SessionDTO proxiedUserSession = null;
        try {
            proxiedUserSession = securityManager.getProxiedUserSession(username, session);
        } catch (AuthenticationException e) {
            if (e.getReason() == ExceptionReason.SESSION_EXPIRED || e.getReason() == ExceptionReason.MISSING_ENTRY) {
                init();
                proxiedUserSession = securityManager.getProxiedUserSession(username, session);
            }
        }
        return proxiedUserSession;
    }

    public SessionDTO getSession() {
        return session;
    }

    public void setSession(SessionDTO session) {
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public String getMtsJndiRoot() {
        return mtsJndiRoot;
    }

    public <T extends BaseClient> T getManager(Class<T> managerType) throws MtsException {
        T manager = null;
        try {
            manager = managerType.newInstance();
            manager.setMtsClient(this);
            manager.init();
        } catch (InstantiationException e) {
            throw new MtsException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new MtsException(e.getMessage(), e);
        } catch (NamingException e) {
            throw new MtsException(e.getMessage(), e);
        }
        return manager;
    }

    public GeneralMGRClient getGeneralMGR() throws MtsException {
        return getManager(GeneralMGRClient.class);
    }

    public void checkSession() throws MtsException, NotFoundException, AuthenticationException, AuthorizationException {
        try {
            securityManager.isSessionValid(session);
        } catch (MtsException e) {
            init();
        }
    }
}
