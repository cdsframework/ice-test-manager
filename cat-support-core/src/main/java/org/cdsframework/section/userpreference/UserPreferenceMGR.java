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
package org.cdsframework.section.userpreference;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.application.CatApplication;
import org.cdsframework.application.Mts;
import org.cdsframework.client.support.GeneralMGRClient;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.lookup.UserDTOList;
import org.cdsframework.message.MessageMGR;
import org.cdsframework.security.UserSession;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.UtilityMGR;
import org.primefaces.event.CloseEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@SessionScoped
public class UserPreferenceMGR implements Serializable {
    private static final long serialVersionUID = -8054165300312322543L;

    protected LogUtils logger;
    @Inject
    protected UserSession userSession;
    @Inject
    protected MessageMGR messageMGR;
    @Inject
    protected CatApplication catApplication;
    @Inject
    private Mts mts;
    @Inject
    private UserDTOList userDTOList;

    @PostConstruct
    public void postConstructor() {
        final String METHODNAME = "postConstructor ";
        logger = LogUtils.getLogger(getClass());
        logger.logBegin(METHODNAME);
        try {
            messageMGR.setMessageBundle(getClass());
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    public String getTheme() {
        String result = "redmond";
        if (getUserDTO() != null && getUserDTO().getUserPreferenceDTOMap().get("catTheme") != null) {
            result = getUserDTO().getUserPreferenceDTOMap().get("catTheme").getValue();
        }
        return result;
    }

    public UserDTO getUserDTO() {
        final String METHODNAME = "getUserDTO ";
        logger.debug(METHODNAME, "called!");
        UserDTO result = userSession.getUserDTO();
        logger.debug(METHODNAME, "result: ", result);

        if (userSession.getUserDTO() != null && userSession.getUserDTO().getUserId() != null) {
            result = userDTOList.get(userSession.getUserDTO().getUserId());
        }

        logger.debug(METHODNAME, "result: ", result);
        return result;
    }

    public void closeMain(CloseEvent event) {
        final String METHODNAME = "closeMain ";
        logger.logBegin(METHODNAME);
        try {
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    public void saveMain(ActionEvent actionEvent) {
        final String METHODNAME = "saveMain ";
        logger.logBegin(METHODNAME);
        boolean saved = false;
        try {
            GeneralMGRClient generalMGR = mts.getGeneralMGR();
            if (userSession.getUserDTO() != null && getUserDTO() != null) {
                if (userSession.getUserDTO().getUserId() != null && getUserDTO().getUserId() != null) {
                    if (userSession.getUserDTO().getUserId().equals(getUserDTO().getUserId())) {
                        generalMGR.save(getUserDTO(), mts.getSession(), new PropertyBagDTO());
                        saved = true;
                        messageMGR.displayInfoMessage("Your user preferences have been updated.");
                        userDTOList.setDirty(true);
                    } else {
                        throw new CatException("userIds were not equal! " + userSession.getUserDTO().getUserId() + " - " + getUserDTO().getUserId());
                    }
                } else {
                    throw new CatException("userId was null! " + userSession.getUserDTO().getUserId() + " - " + getUserDTO().getUserId());
                }
            } else {
                throw new CatException("userDTO was null! " + userSession.getUserDTO() + " - " + getUserDTO());
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            UtilityMGR.setCallbackParam("saved", saved);
            logger.logEnd(METHODNAME);
        }
    }
}
