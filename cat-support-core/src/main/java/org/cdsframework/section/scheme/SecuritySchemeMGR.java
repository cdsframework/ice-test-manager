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
package org.cdsframework.section.scheme;

import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.SecuritySchemeDTO;
import org.cdsframework.dto.SecuritySchemeRelMapDTO;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.lookup.SchemeDTOList;
import org.cdsframework.lookup.UserSecuritySchemePermissionMapList;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class SecuritySchemeMGR extends BaseModule<SecuritySchemeDTO> {

    private static final long serialVersionUID = -3714098117404728651L;
    @Inject
    private SchemeDTOList schemeDTOList;
    @Inject
    private UserSecuritySchemePermissionMapList userSecuritySchemePermissionMapList;

    @Override
    protected void initialize() {
        setLazy(true);
        setAssociatedList(schemeDTOList);
        setInitialQueryClass("FindAll");
        setSaveImmediately(true);
    }

    @Override
    public void registerTabComponents() {
        getTabService().registeredUIComponent(0, this);
    }

    @Override
    protected void preMarkAssociatedDTOListDirty() {
        userSecuritySchemePermissionMapList.setDirty(true);
    }

    public List<SecuritySchemeDTO> getSchemeDTOList() throws CatException {
        final String METHODNAME = "getSchemeDTOList ";
        List<SecuritySchemeDTO> result = new ArrayList<SecuritySchemeDTO>();
        SecuritySchemeDTO parentDTO = getParentDTO();
        if (schemeDTOList != null) {
            if (parentDTO != null) {
                for (SecuritySchemeDTO securitySchemeDTO : schemeDTOList.getAll()) {
                    if (securitySchemeDTO != null && securitySchemeDTO.getSchemeId() != null) {
                        List<SecuritySchemeDTO> dependncyList = getDependncyList(securitySchemeDTO);
                        if (!dependncyList.contains(securitySchemeDTO)
                                && !dependncyList.contains(parentDTO)
                                && !securitySchemeDTO.getSchemeId().equals(parentDTO.getSchemeId())) {
                            result.add(securitySchemeDTO);
                        }
                    }
                }
            } else {
                logger.info(METHODNAME, "parentDTO was null!");
            }
        } else {
            throw new CatException(METHODNAME + "schemeDTOList was null!");
        }
        return result;
    }

    private List<SecuritySchemeDTO> getDependncyList(SecuritySchemeDTO securitySchemeDTO) {
        List<SecuritySchemeDTO> result = new ArrayList<SecuritySchemeDTO>();
        if (securitySchemeDTO != null) {
            for (SecuritySchemeRelMapDTO securitySchemeRelMapDTO : securitySchemeDTO.getSecuritySchemeRelMapDTOs()) {
                if (securitySchemeRelMapDTO != null) {
                    SecuritySchemeDTO relatedSecuritySchemeDTO = securitySchemeRelMapDTO.getRelatedSecuritySchemeDTO();
                    if (relatedSecuritySchemeDTO != null) {
                        result.add(relatedSecuritySchemeDTO);
                        result.addAll(getDependncyList(relatedSecuritySchemeDTO));
                    }
                }
            }
        }
        return result;
    }

}
