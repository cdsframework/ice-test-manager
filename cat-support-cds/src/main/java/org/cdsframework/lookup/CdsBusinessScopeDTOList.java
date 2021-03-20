/**
 * CAT CDS support plugin project.
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
 *
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see https://www.hln.com/services/open-source/ or send
 * correspondence to ice@hln.com.
 */
package org.cdsframework.lookup;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.cdsframework.base.BaseDTOList;
import org.cdsframework.dto.CdsBusinessScopeDTO;
import org.cdsframework.dto.CdsVersionConceptDeterminationMethodRelDTO;
import org.cdsframework.dto.CdsVersionDTO;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ApplicationScoped
public class CdsBusinessScopeDTOList extends BaseDTOList<CdsBusinessScopeDTO> {

    private static final long serialVersionUID = -389468784388304358L;

    @Override
    protected void initialize() throws Exception {
        addChildDtoClass(CdsVersionDTO.class);
        addChildDtoClass(CdsVersionConceptDeterminationMethodRelDTO.class);
    }

    public List<CdsVersionDTO> getAllCdsVersionDTOs() {
        List<CdsVersionDTO> result = new ArrayList<CdsVersionDTO>();
        for (CdsBusinessScopeDTO item : getAll()) {
            result.addAll(item.getCdsVersionDTOs());
        }
        return result;
    }

    public CdsVersionDTO getCdsVersionDTO(String businessId, String scopingEntityId, String version) {
        final String METHODNAME = "getCdsVersionDTO ";
        CdsVersionDTO result = null;
        if (businessId != null && scopingEntityId != null && version != null) {
            for (CdsBusinessScopeDTO item : getAll()) {
                for (CdsVersionDTO cdsVersionDTO : item.getCdsVersionDTOs()) {
                    logger.info(METHODNAME, "processing businessScopeId: ", item.getBusinessScopeId());
                    logger.info(METHODNAME, "processing scopingEntityId: ", item.getScopingEntityId());
                    logger.info(METHODNAME, "processing version: ", cdsVersionDTO.getVersion());
                    if (businessId.equals(item.getBusinessId()) && scopingEntityId.equals(item.getScopingEntityId()) && version.equals(cdsVersionDTO.getVersion())) {
                        result = cdsVersionDTO;
                        break;
                    }
                }
                if (result != null) {
                    break;
                }
            }
        } else {
            logger.error(METHODNAME, "businessId or scopingEntityId or version was null!");
        }
        if (result == null) {
            logger.error(METHODNAME, "result was null for: ", businessId, " - ", scopingEntityId, " - ", version);
        }
        return result;
    }

    public CdsVersionDTO getCdsVersionDTO(String primaryKey) {
        final String METHODNAME = "getCdsVersionDTO ";
        CdsVersionDTO result = null;
        if (primaryKey != null) {
            for (CdsBusinessScopeDTO item : getAll()) {
                for (CdsVersionDTO cdsVersionDTO : item.getCdsVersionDTOs()) {
                    if (primaryKey.equals(cdsVersionDTO.getVersionId())) {
                        result = cdsVersionDTO;
                        break;
                    }
                }
                if (result != null) {
                    break;
                }
            }
        } else {
            logger.error(METHODNAME, "primaryKey was null!");
        }
        if (result == null) {
            logger.error(METHODNAME, "result was null for: ", primaryKey);
        }
        return result;
    }

    /**
     * Retrieve a CdsBusinessScopeDTO that matches the supplied businessId and scopingEntityId.
     *
     * @param cdsBusinessScopeDTO
     * @return
     */
    public CdsBusinessScopeDTO getCdsBusinessScopeDTOByBusinessIdScopingEntityId(CdsBusinessScopeDTO cdsBusinessScopeDTO) {
        final String METHODNAME = "getCdsBusinessScopeDTOByBusinessIdScopingEntityId ";
        CdsBusinessScopeDTO result = null;
        if (cdsBusinessScopeDTO != null) {
            result = getCdsBusinessScopeDTOByBusinessIdScopingEntityId(cdsBusinessScopeDTO.getBusinessId(), cdsBusinessScopeDTO.getScopingEntityId());
        } else {
            logger.error(METHODNAME, "cdsBusinessScopeDTO was null!");
        }
        if (result == null) {
            logger.error(METHODNAME, "result was null for: ", cdsBusinessScopeDTO);
        }
        return result;
    }

    /**
     * Retrieve a CdsBusinessScopeDTO that matches the supplied businessId and scopingEntityId.
     *
     * @param businessId
     * @param scopingEntityId
     * @return
     */
    public CdsBusinessScopeDTO getCdsBusinessScopeDTOByBusinessIdScopingEntityId(String businessId, String scopingEntityId) {
        final String METHODNAME = "getCdsBusinessScopeDTOByBusinessIdScopingEntityId ";
        CdsBusinessScopeDTO result = null;
        if (businessId != null) {
            if (scopingEntityId != null) {
                logger.info(METHODNAME, "businessId=", businessId);
                logger.info(METHODNAME, "scopingEntityId=", scopingEntityId);
                for (CdsBusinessScopeDTO item : getAll()) {
                    if (businessId.equals(item.getBusinessId()) && scopingEntityId.equals(item.getScopingEntityId())) {
                        result = item;
                        break;
                    }
                }
            } else {
                logger.error(METHODNAME, "scopingEntityId was null!");
            }
        } else {
            logger.error(METHODNAME, "businessId was null!");
        }
        if (result == null) {
            logger.error(METHODNAME, "result was null for: ", businessId, " and ", scopingEntityId);
        }
        return result;
    }

}
