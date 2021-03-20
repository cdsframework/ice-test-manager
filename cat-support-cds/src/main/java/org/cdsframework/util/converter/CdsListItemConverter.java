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
import org.cdsframework.dto.CdsListDTO;
import org.cdsframework.dto.CdsListItemDTO;
import org.cdsframework.lookup.CdsListDTOList;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@RequestScoped
public class CdsListItemConverter extends BaseDTOConverter<CdsListItemDTO> {

    @Inject
    private CdsListDTOList cdsListDTOList;

    public CdsListItemConverter() {
        super(CdsListItemConverter.class);
    }

    @Override
    protected String getStringFromObject(CdsListItemDTO item) {
        return item != null ? item.getItemId() : null;
    }

    @Override
    protected void initialize() {
        // do nothing
    }

    @Override
    protected CdsListItemDTO getObjectFromString(String inputString) {
        final String METHODNAME = "getObjectFromString ";
        CdsListItemDTO result = null;
        if (inputString != null) {
            for (CdsListDTO cdsListDTO : cdsListDTOList.getAll()) {
                for (CdsListItemDTO cdsListItemDTO : cdsListDTO.getCdsListItemDTOs()) {
                    if (inputString.equals(cdsListItemDTO.getItemId())) {
                        result = cdsListItemDTO;
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
