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
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsCodeSystemDTO;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ApplicationScoped
public class CdsCodeSystemDTOList extends BaseDTOList<CdsCodeSystemDTO> {

    private static final long serialVersionUID = -322063688998251332L;

    @Override
    protected void initialize() throws Exception {
        addChildDtoClass(CdsCodeDTO.class);
    }

    /**
     * Get the Code System from a supplied OID.
     *
     * @param oid
     * @return
     */
    public CdsCodeSystemDTO getCdsCodeSystemDTOByOid(String oid) {
        final String METHODNAME = "getCdsCodeSystemDTOByOid ";
        CdsCodeSystemDTO result = null;
        if (oid != null && !oid.trim().isEmpty()) {
            for (CdsCodeSystemDTO item : getAll()) {
                if (oid.trim().equalsIgnoreCase(item.getOid())) {
                    result = item;
                    break;
                }
            }
        } else {
            logger.error(METHODNAME, "OID value was null or empty: ", oid);
        }
        if (result == null) {
            logger.error(METHODNAME, "CdsCodeSystemDTO not found for: ", oid);
        }
        return result;
    }

    /**
     * Get a Code System from a supplied name.
     *
     * @param name
     * @return
     */
    public CdsCodeSystemDTO getCdsCodeSystemDTOByName(String name) {
        final String METHODNAME = "getCdsCodeSystemDTOByName ";
        CdsCodeSystemDTO result = null;
        if (name != null && !name.trim().isEmpty()) {
            for (CdsCodeSystemDTO item : getAll()) {
                if (name.trim().equalsIgnoreCase(item.getName())) {
                    result = item;
                    break;
                }
            }
        } else {
            logger.error(METHODNAME, "Name value was null or empty: ", name);
        }
        if (result == null) {
            logger.error(METHODNAME, "CdsCodeSystemDTO not found for: ", name);
        }
        return result;
    }

    /**
     * Get a list of Codes belonging to a Code System via a supplied Code System ID.
     *
     * @param codeSystemId
     * @return
     */
    public List<CdsCodeDTO> getCodeDTOsByCodeSystemId(String codeSystemId) {
        final String METHODNAME = "getCodeDTOsByCodeSystemId ";
        List<CdsCodeDTO> result = new ArrayList<CdsCodeDTO>();
        if (codeSystemId != null && !codeSystemId.trim().isEmpty()) {
            for (CdsCodeSystemDTO item : getAll()) {
                if (codeSystemId.trim().equalsIgnoreCase(item.getCodeSystemId())) {
                    result = item.getCdsCodeDTOs();
                    break;
                }
            }
        } else {
            logger.error(METHODNAME, "codeSystemId value was null or empty: ", codeSystemId);
        }
        if (result.isEmpty()) {
            logger.error(METHODNAME, "Zero results returned for: ", codeSystemId);
        }
        return result;
    }

    /**
     * Get a list of Codes belonging to a Code System via a supplied OID.
     *
     * @param oid
     * @return
     */
    public List<CdsCodeDTO> getCodeDTOsByOid(String oid) {
        final String METHODNAME = "getCodeDTOsByOid ";
        List<CdsCodeDTO> result = new ArrayList<CdsCodeDTO>();
        CdsCodeSystemDTO cdsCodeSystemDTO = getCdsCodeSystemDTOByOid(oid);
        if (cdsCodeSystemDTO != null) {
            result = cdsCodeSystemDTO.getCdsCodeDTOs();
        }
        if (result.isEmpty()) {
            logger.error(METHODNAME, "Zero results returned for: ", oid);
        }
        return result;
    }
}
