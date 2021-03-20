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

import org.cdsframework.base.MessageArgCallback;
import org.cdsframework.listeners.StartupListener;
import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.client.support.AdminMGRClient;
import org.cdsframework.enumeration.Environment;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.util.BeanUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.enumeration.SourceMethod;

@Named
@ApplicationScoped
public class CatApplication implements Serializable {

    private static final long serialVersionUID = 5769508239630646249L;

    @Inject
    private Mts mts;
    private String MTS_SRC_SYSTEM_ID;
    private String MTS_BUILD_INFO;
    private Environment MTS_ENV;
    private String environmentText;
    private String environmentHeaderStyle;
    private final static LogUtils logger = LogUtils.getLogger(CatApplication.class);
    private Map<String, String> themes;
    private Map<Class<? extends BaseDTO>, MessageArgCallback> messageArgCallbackMap;
    private final static String CAT_HEADER_UNKNOWN_STYLECLASS = "cat-header-unk";
    private final static String CAT_HEADER_DEV_STYLECLASS = "cat-header-dev";
    private final static String CAT_HEADER_UAT_STYLECLASS = "cat-header-uat";
    private final static String CAT_HEADER_PROD_STYLECLASS = "cat-header-prod";

    public CatApplication() {
        final String METHODNAME = "CatApplication constructor ";
        logger.debug(METHODNAME);
    }

    @PostConstruct
    protected void initialize() {
        final String METHODNAME = "initialize ";
        logger.logBegin(METHODNAME);

        try {
            // Get the FacesContext and initialize the upload tmp directory
            FacesContext fc = FacesContext.getCurrentInstance();
            String uploadDirectory = fc.getExternalContext().getInitParameter("UPLOAD_DIRECTORY");
            messageArgCallbackMap = BeanUtils.getPluginMessageArgCallbackMap();
            try {
                logger.info("Making tmp upload dirs: " + (new File(uploadDirectory)).mkdirs());
            } catch (Exception e) {
                logger.error(e);
            }
            logger.info("CAT Upload Directory: ", uploadDirectory);

            themes = new TreeMap<String, String>();
            themes.put("Redmond", "redmond");
            themes.put("South-Street", "south-street");
            themes.put("Sunny", "sunny");

            init();
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    private void init() {
        final String METHODNAME = "init ";
        // Initialize Constants

        try {
            AdminMGRClient adminMgr = mts.getManager(AdminMGRClient.class);
            MTS_SRC_SYSTEM_ID = adminMgr.getSrcSystemId();
            MTS_BUILD_INFO = adminMgr.getBuildInfo();
            MTS_ENV = adminMgr.getEnvironment();
        } catch (MtsException e) {
            MTS_SRC_SYSTEM_ID = "unavailable";
            MTS_BUILD_INFO = "unavailable";
            MTS_ENV = Environment.UNSET;
            DefaultExceptionHandler.handleException(e, getClass());
        }

        logger.info(METHODNAME, "MTS_SRC_SYSTEM_ID=", MTS_SRC_SYSTEM_ID);
        logger.info(METHODNAME, "MTS_BUILD_INFO=", MTS_BUILD_INFO);
        logger.info(METHODNAME, "MTS_ENV=", MTS_ENV);

        checkSrcSystemId();

        switch (MTS_ENV) {
            case DEV:
                environmentText = "Development";
                environmentHeaderStyle = CAT_HEADER_DEV_STYLECLASS;
                break;
            case UAT:
                environmentText = "User Acceptance Testing";
                environmentHeaderStyle = CAT_HEADER_UAT_STYLECLASS;
                break;
            case PROD:
                environmentText = "Production";
                environmentHeaderStyle = CAT_HEADER_PROD_STYLECLASS;
                break;
            default:
                environmentText = "UNSET";
                environmentHeaderStyle = CAT_HEADER_UNKNOWN_STYLECLASS;
                break;
        }

        logger.info(METHODNAME, "environmentText=", environmentText);
        logger.info(METHODNAME, "environmentHeaderStyle=", environmentHeaderStyle);

    }

    private void checkSrcSystemId() {
        final String METHODNAME = "checkSrcSystemId ";
        String expectedSrcSystemId = StartupListener.CAT_SRC_SYSTEM_ID;
        expectedSrcSystemId = expectedSrcSystemId.toUpperCase();
        if (expectedSrcSystemId != null) {
            if (MTS_SRC_SYSTEM_ID != null) {
                if (MTS_SRC_SYSTEM_ID.equalsIgnoreCase(expectedSrcSystemId)) {
                    logger.info(METHODNAME, "Source System ID validation was successful: ", expectedSrcSystemId);
                } else {
                    logger.error(METHODNAME, "Source System ID validation was unsuccessful: expected - ", expectedSrcSystemId, "; got - ", MTS_SRC_SYSTEM_ID);
                }
            } else {
                logger.error(METHODNAME, "MTS source system ID was null!");
            }
        } else {
            logger.error(METHODNAME, "Expected source system ID was null! Please add the SRC_SYSTEM_ID System property.");
        }
    }

    public Map<String, String> getThemes() {
        return themes;
    }

    public void setThemes(Map<String, String> themes) {
        this.themes = themes;
    }

    public String getEnvironmentHeaderStyle() {
        final String METHODNAME = "getEnvironmentHeaderStyle ";
        if (CAT_HEADER_UNKNOWN_STYLECLASS.equals(environmentHeaderStyle)) {
            init();
        }
        return environmentHeaderStyle;
    }

    public void setEnvironmentHeaderStyle(String environmentHeaderStyle) {
        this.environmentHeaderStyle = environmentHeaderStyle;
    }

    public String getEnvironmentText() {
        return environmentText;
    }

    public void setEnvironmentText(String environmentText) {
        this.environmentText = environmentText;
    }

    public String getCAT_VERSION() {
        return StartupListener.CAT_VERSION;
    }

    public String getCAT_BUILD_CHANGESET() {
        return StartupListener.CAT_BUILD_CHANGESET;
    }

    public String getCAT_BUILD_TIMESTAMP() {
        return StartupListener.CAT_BUILD_TIMESTAMP;
    }

    public String getCAT_PLUGIN_LIST() {
        return StartupListener.CAT_PLUGIN_LIST;
    }

    public String getMTS_BUILD_INFO() {
        return MTS_BUILD_INFO;
    }

    public boolean isDev() {
        return MTS_ENV == Environment.DEV;
    }

    public Environment getMTS_ENV() {
        return MTS_ENV;
    }

    public String getMTS_SRC_SYSTEM_ID() {
        return MTS_SRC_SYSTEM_ID;
    }

    public String getMessageDisplayId() {
        return StartupListener.MESSAGE_DISPLAY_ID;
    }

    public String getMessageDisplayWidgetVar() {
        return StartupListener.MESSAGE_DISPLAY_WIDGET_VAR;
    }

    public Map<Class<? extends BaseDTO>, MessageArgCallback> getMessageArgCallbackMap() {
        return messageArgCallbackMap;
    }

    public <T extends BaseDTO> Object[] getDtoMessageArgs(Class<T> dtoClass, T dto, BaseModule module, SourceMethod sourceMethod) throws Exception {
        final String METHODNAME = "getMessageArgs ";
        Object[] messageArgs;
        if (dto != null) {
            MessageArgCallback messageArgCallback = messageArgCallbackMap.get(dtoClass);
            if (messageArgCallback != null) {
                messageArgs = messageArgCallback.getMessageArgs(dto, module, sourceMethod);
            } else {
                logger.warn(METHODNAME, "messageArgCallback was null!", " class: ", dtoClass);
                messageArgs = new Object[]{dto.getPrimaryKey()};
            }
        } else {
            logger.warn(METHODNAME, "dto was null!", " class: ", dtoClass, "; module: ", module, "; sourceMethod: ", sourceMethod);
            messageArgs = new Object[]{};
        }
        return messageArgs;
    }
}
