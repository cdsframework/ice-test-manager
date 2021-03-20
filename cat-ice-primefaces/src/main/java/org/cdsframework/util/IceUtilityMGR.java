/**
 * CAT ICE support plugin project.
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
package org.cdsframework.util;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.IceVaccineComponentDTO;
import org.cdsframework.dto.IceVaccineDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ApplicationScoped
public class IceUtilityMGR implements Serializable {

    private static final LogUtils logger = LogUtils.getLogger(IceUtilityMGR.class);
    private static final long serialVersionUID = 4688868872112326349L;

    public static String getCodedElementLabel(Object dto) {
        final String METHODNAME = "getIceVaccineDTOLabel ";
        logger.debug(METHODNAME, "incoming: ", dto);
        String result = null;
        if (dto != null) {
            String code = null;
            String name = null;
            if (dto instanceof IceVaccineDTO) {
                IceVaccineDTO iceVaccineDTO = (IceVaccineDTO) dto;
                logger.debug(METHODNAME, "IceVaccineDTO: ", iceVaccineDTO);
                code = iceVaccineDTO.getVaccine() != null ? iceVaccineDTO.getVaccine().getCode() : null;
                name = iceVaccineDTO.getVaccineName();
            } else if (dto instanceof IceVaccineGroupDTO) {
                IceVaccineGroupDTO iceVaccineGroupDTO = (IceVaccineGroupDTO) dto;
                logger.debug(METHODNAME, "IceVaccineGroupDTO: ", iceVaccineGroupDTO);
                code = iceVaccineGroupDTO.getVaccineGroupCode();
                name = iceVaccineGroupDTO.getVaccineGroupName();
            } else if (dto instanceof IceVaccineComponentDTO) {
                IceVaccineComponentDTO iceVaccineComponentDTO = (IceVaccineComponentDTO) dto;
                logger.debug(METHODNAME, "IceVaccineComponentDTO: ", iceVaccineComponentDTO);
                code = iceVaccineComponentDTO.getVaccineComponent() != null ? iceVaccineComponentDTO.getVaccineComponent().getCode() : null;
                name = iceVaccineComponentDTO.getVaccineComponent() != null ? iceVaccineComponentDTO.getVaccineComponent().getDisplayName() : null;
            } else if (dto instanceof CdsCodeDTO) {
                CdsCodeDTO cdsCodeDTO = (CdsCodeDTO) dto;
                logger.debug(METHODNAME, "CdsCodeDTO: ", cdsCodeDTO);
                code = cdsCodeDTO.getCode();
                name = cdsCodeDTO.getDisplayName();
            } else {
                throw new IllegalArgumentException(METHODNAME + "Unsupported class type: " + dto.getClass());
            }
            result = String.format("%s (%s)", name, code);
        }
        return result;
    }
}
