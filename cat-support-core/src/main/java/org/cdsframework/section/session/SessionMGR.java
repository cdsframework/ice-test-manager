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
package org.cdsframework.section.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.faces.view.ViewScoped; 
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.enumeration.PermissionType;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.lookup.UserSecuritySchemePermissionMapList;
import org.cdsframework.security.UserSecuritySchemePermissionMap;
import org.cdsframework.util.UtilityMGR;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class SessionMGR extends BaseModule<SessionDTO> {
    private static final long serialVersionUID = 3533201950444109324L;

    @Inject
    private UserSecuritySchemePermissionMapList userSecuritySchemePermissionMapList;

    @Override
    protected void initialize() {
        setInitialQueryClass("FindAll");
        setSaveImmediately(true);
    }
//
//    @Override
//    protected List<SessionDTO> mgrFindByQueryList(SessionDTO searchCriteriaDTO, String queryClass, List<Class> childClassDTOs)
//            throws Exception {
//        List<SessionDTO> results = super.mgrFindByQueryList(searchCriteriaDTO, queryClass, childClassDTOs);
//        List<SessionDTO> newResults = new ArrayList<SessionDTO>();
//        for (SessionDTO sessionDTO : results) {
//            if (!"INTERNAL".equals(sessionDTO.getAppDTO().getAppName())) {
//                newResults.add(sessionDTO);
//            }
//        }
//        return newResults;
//    }

    public List<Entry<String, List<PermissionType>>> getParentDTOPermAllowClasses() throws MtsException {
        List<Entry<String, List<PermissionType>>> result = new ArrayList<Entry<String, List<PermissionType>>>();
        SessionDTO parentDTO = getParentDTO();
        if (parentDTO != null) {
            UserDTO userDTO = parentDTO.getUserDTO();
            if (userDTO != null) {
                if (userDTO.getUserId() != null) {
                    UserSecuritySchemePermissionMap userSecuritySchemePermissionMap = userSecuritySchemePermissionMapList.get(userDTO.getUserId());
                    if (userSecuritySchemePermissionMap != null) {
                        result.addAll(userSecuritySchemePermissionMap.getPermissionAllowMap().entrySet());
                    } else {
                        logger.error("userSecuritySchemePermissionMapDTO is null!");
                    }
                } else {
                    logger.error("userId is null!");
                }
            } else {
                logger.error("userDTO is null!");
            }
        } else {
            logger.error("sessionDTO is null!");
        }
        return result;
    }

    public List<Entry<String, List<PermissionType>>> getParentDTOPermDenyClasses() throws MtsException {
        List<Entry<String, List<PermissionType>>> result = new ArrayList<Entry<String, List<PermissionType>>>();
        SessionDTO parentDTO = getParentDTO();
        if (parentDTO != null) {
            UserDTO userDTO = parentDTO.getUserDTO();
            if (userDTO != null) {
                if (userDTO.getUserId() != null) {
                    UserSecuritySchemePermissionMap userSecuritySchemePermissionMap = userSecuritySchemePermissionMapList.get(userDTO.getUserId());
                    if (userSecuritySchemePermissionMap != null) {
                        result.addAll(userSecuritySchemePermissionMap.getPermissionDenyMap().entrySet());
                    } else {
                        logger.error("userSecuritySchemePermissionMapDTO is null!");
                    }
                } else {
                    logger.error("userId is null!");
                }
            } else {
                logger.error("userDTO is null!");
            }
        } else {
            logger.error("sessionDTO is null!");
        }
        return result;
    }

    public String getPermList(List<PermissionType> permList) {
        String result = "";
        List<String> stringList = new ArrayList<String>();
        for (PermissionType perm : permList) {
            stringList.add(perm.toString());
        }
        if (!stringList.isEmpty()) {
            result = UtilityMGR.getStringFromArray(stringList);
        }
        return result;
    }
}
