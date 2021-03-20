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
import org.cdsframework.dto.CdsCodeOpenCdsConceptRelDTO;
import org.cdsframework.dto.OpenCdsConceptDTO;
import org.cdsframework.dto.OpenCdsConceptRelDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.handlers.DefaultExceptionHandler;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ApplicationScoped
public class OpenCdsConceptDTOList extends BaseDTOList<OpenCdsConceptDTO> {

    private static final long serialVersionUID = 111512033604012419L;

    @Override
    protected void initialize() throws Exception {
        addChildDtoClass(OpenCdsConceptRelDTO.class);
    }

    /**
     * Return a CdsCodeDTOs list of related concepts.
     *
     * @param cdsCodeDTO
     * @return
     */
    public List<OpenCdsConceptDTO> getCdsCodeDTOOpenCdsConceptDTOs(CdsCodeDTO cdsCodeDTO) {
        final String METHODNAME = "getCdsCodeDTOOpenCdsConceptDTOs ";
        List<OpenCdsConceptDTO> result = new ArrayList<OpenCdsConceptDTO>();
        if (cdsCodeDTO != null) {
            try {
                PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                propertyBagDTO.setQueryClass("ByOpenCdsCodeMapping");
                CdsCodeOpenCdsConceptRelDTO queryDTO = new CdsCodeOpenCdsConceptRelDTO();
                queryDTO.setCodeId(cdsCodeDTO.getCodeId());
                List<CdsCodeOpenCdsConceptRelDTO> cdsCodeOpenCdsConceptRelDTOs = getMts().getGeneralMGR().findByQueryList(queryDTO, getSessionDTO(), propertyBagDTO);
                for (CdsCodeOpenCdsConceptRelDTO item : cdsCodeOpenCdsConceptRelDTOs) {
                    OpenCdsConceptDTO openCdsConceptDTO = get(item.getOpenCdsCodeId());
                    if (openCdsConceptDTO != null) {
                        if (!result.contains(openCdsConceptDTO)) {
                            result.add(openCdsConceptDTO);
                        }
                    } else {
                        logger.error(METHODNAME, "openCdsConceptDTO is null: ", item.getOpenCdsCodeId());
                    }
                }
            } catch (Exception e) {
                DefaultExceptionHandler.handleException(e, null);
            }
        } else {
            logger.error(METHODNAME, "cdsCodeDTO is null!");
        }
        return result;
    }
}
