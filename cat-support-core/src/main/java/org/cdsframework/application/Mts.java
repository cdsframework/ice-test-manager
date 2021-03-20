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
package org.cdsframework.application;

import org.cdsframework.listeners.StartupListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.naming.NamingException;
import org.cdsframework.base.BaseClient;
import org.cdsframework.client.MtsClient;
import org.cdsframework.client.support.GeneralMGRClient;
import org.cdsframework.client.support.SecurityMGRClient;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ApplicationScoped
public class Mts {

    private static final LogUtils logger = LogUtils.getLogger(Mts.class);
    private MtsClient mtsClient;
    private Map<Class<? extends BaseClient>, BaseClient> cachedManagers = new HashMap<Class<? extends BaseClient>, BaseClient>();
    private String mtsApp;
    private String mtsUsername;
    private String mtsPassword;
    private String mtsHostname;
    private String mtsPort;
    private String mtsJndiRoot;

    public Mts() {
    }

    @PostConstruct
    protected void initialize() {
        final String METHODNAME = "initialize ";
        logger.logBegin(METHODNAME);
        try {
            // initialize instanceProperties
            Properties instanceProperties = StartupListener.getInstanceProperties();
            mtsApp = instanceProperties.getProperty("MTS_APP");
            mtsUsername = instanceProperties.getProperty("MTS_USERNAME");
            mtsPassword = instanceProperties.getProperty("MTS_PASSWORD");
            mtsHostname = instanceProperties.getProperty("MTS_HOST");
            mtsPort = instanceProperties.getProperty("MTS_PORT");
            mtsJndiRoot = instanceProperties.getProperty("MTS_JNDI_ROOT");
            if (mtsJndiRoot == null || mtsJndiRoot.trim().isEmpty()) {
                mtsJndiRoot = MtsClient.DEFAULT_JNDI_ROOT;
                logger.warn(METHODNAME, "MTS_JNDI_ROOT was not defined - using default.");
            }
            logger.info("MTS_APP: ", mtsApp);
            logger.info("MTS_USERNAME: ", mtsUsername);
            logger.info("MTS_HOST: ", mtsHostname);
            logger.info("MTS_PORT: ", mtsPort);
            logger.info("MTS_JNDI_ROOT: ", mtsJndiRoot);
            initializeMtsConnection();
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    private void initializeMtsConnection()
            throws MtsException, NotFoundException, AuthenticationException, AuthorizationException, NamingException {
        final String METHODNAME = "initializeMtsConnection ";
        logger.logBegin(METHODNAME);
        logger.info("Initializing MTS connection...");
        cachedManagers = new HashMap<Class<? extends BaseClient>, BaseClient>();
        mtsClient = new MtsClient(mtsUsername, mtsPassword, mtsApp, mtsHostname, mtsPort, mtsJndiRoot);
    }

    public String getMtsApp() {
        return mtsApp;
    }

    public MtsClient getMtsClient() throws MtsException {
        if (mtsClient == null) {
            try {
                initializeMtsConnection();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        if (mtsClient != null) {
            return mtsClient;
        } else {
            throw new MtsException("Unable to connect to middle tier service");
        }
    }

    public <S extends BaseClient> S getManager(Class<S> managerType) throws MtsException {
        logger.trace("Attempting to retrieve ", managerType.getSimpleName());
        S mgrClient = (S) cachedManagers.get(managerType);
        if (mgrClient == null) {
            logger.trace(managerType.getSimpleName(), " not in manager cache - retrieving...");
            mgrClient = getMtsClient().getManager(managerType);
            cachedManagers.put(managerType, mgrClient);
        } else {
            logger.trace("getting ", managerType.getSimpleName(), " from cache...");
        }
        return mgrClient;
    }

    public SessionDTO getSession() throws MtsException {
        return getMtsClient().getSession();
    }

    public GeneralMGRClient getGeneralMGR() throws MtsException {
        return getManager(GeneralMGRClient.class);
    }

    public void checkSession()
            throws MtsException, NotFoundException, AuthenticationException, AuthorizationException, NamingException {
        SecurityMGRClient securityMGRClient = getManager(SecurityMGRClient.class);
        if (!securityMGRClient.isSessionValid(mtsClient.getSession())) {
            initializeMtsConnection();
        }
    }
}
