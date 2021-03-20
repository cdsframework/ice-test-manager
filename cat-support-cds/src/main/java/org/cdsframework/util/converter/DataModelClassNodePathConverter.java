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
import org.cdsframework.dto.DataModelClassNodeDTO;
import org.cdsframework.lookup.DataModelClassNodeDTOList;
import org.cdsframework.util.support.DeepCopy;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@RequestScoped
public class DataModelClassNodePathConverter extends BaseDTOConverter<DataModelClassNodeDTO> {

    @Inject
    private DataModelClassNodeDTOList dataModelClassNodeDTOList;

    public DataModelClassNodePathConverter() {
        super(DataModelClassNodePathConverter.class);
    }

    @Override
    protected void initialize() {
        setBaseDTOList(dataModelClassNodeDTOList);
    }

    @Override
    protected DataModelClassNodeDTO getObjectFromString(String inputString) {
        final String METHODNAME = "getObjectFromString ";
        logger.debug(METHODNAME, "inputString: ", inputString);
        DataModelClassNodeDTO dataModelClassNodeDTO = null;
        String id = null;
        String nodePath = null;
        if (inputString != null) {
            String[] items = inputString.split("\\|", 2);
            id = items[0].trim();
            if (items.length > 1) {
                nodePath = items[1].trim();
            }
        }
        logger.debug(METHODNAME, "id: ", id);
        logger.debug(METHODNAME, "nodePath: ", nodePath);
        if (id != null) {
            dataModelClassNodeDTO = super.getObjectFromString(id);
            if (dataModelClassNodeDTO != null) {
                dataModelClassNodeDTO = DeepCopy.copy(dataModelClassNodeDTO);
                logger.debug(METHODNAME, "dataModelClassNodeDTO: ", dataModelClassNodeDTO);
                dataModelClassNodeDTO.setName(nodePath);
            } else {
                logger.warn(METHODNAME, "dataModelClassNodeDTO was null for: ", id);
            }
        }
        return dataModelClassNodeDTO;
    }

    @Override
    protected String getStringFromObject(DataModelClassNodeDTO item) {
        logger.debug("getStringFromObject item: ", item);
        return item != null ? String.format("%s|%s", item.getNodeId(), item.getName()) : null;
    }
}
