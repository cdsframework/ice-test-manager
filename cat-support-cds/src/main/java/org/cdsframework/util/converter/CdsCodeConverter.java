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
package org.cdsframework.util.converter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTOConverter;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsCodeSystemDTO;
import org.cdsframework.lookup.CdsCodeSystemDTOList;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@RequestScoped
public class CdsCodeConverter extends BaseDTOConverter<CdsCodeDTO> {

    @Inject
    private CdsCodeSystemDTOList cdsCodeSystemDTOList;

    public CdsCodeConverter() {
        super(CdsCodeConverter.class);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected String getStringFromObject(CdsCodeDTO item) {
        return item != null ? item.getCodeId() : null;
    }

    @Override
    protected CdsCodeDTO getObjectFromString(String inputString) {
        final String METHODNAME = "getObjectFromString ";
        CdsCodeDTO result = null;
        if (inputString != null) {
            for (CdsCodeSystemDTO cdsCodeSystemDTO : cdsCodeSystemDTOList.getAll()) {
                for (CdsCodeDTO cdsCodeDTO : cdsCodeSystemDTO.getCdsCodeDTOs()) {
                    if (inputString.equals(cdsCodeDTO.getCodeId())) {
                        result = cdsCodeDTO;
                        break;
                    }
                }
                if (result != null) {
                    break;
                }
            }
        } else {
            logger.warn(METHODNAME, "inputString was null!");
        }
        return result;
    }

}
