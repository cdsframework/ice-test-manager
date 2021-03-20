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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.cdsframework.base.BaseDTOList;
import org.cdsframework.dto.DataModelClassDTO;
import org.cdsframework.dto.DataModelClassNodeDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.enumeration.DataModelClassType;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.util.comparator.DataModelClassNodeComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ApplicationScoped
public class DataModelClassNodeDTOList extends BaseDTOList<DataModelClassNodeDTO> {

    private static final long serialVersionUID = 7541033678893419203L;
    private Map<DataModelClassDTO, List<DataModelClassNodeDTO>> NODE_PULLDOWN_CACHE = new HashMap<DataModelClassDTO, List<DataModelClassNodeDTO>>();

    @Override
    public void setDirty(boolean dirty) {
        super.setDirty(dirty);
        NODE_PULLDOWN_CACHE = new HashMap<DataModelClassDTO, List<DataModelClassNodeDTO>>();
    }

    public void retrieveTieredClassNodes(DataModelClassDTO dataModelClassDTO) {
        if (dataModelClassDTO != null) {
            List<DataModelClassNodeDTO> childDTOs = dataModelClassDTO.getChildrenDTOs(DataModelClassNodeDTO.ByClassId.class, DataModelClassNodeDTO.class);
            addClassNodes(dataModelClassDTO, childDTOs, false, false, null, null);
            Collections.sort(childDTOs, new DataModelClassNodeComparator());
            dataModelClassDTO.setChildrenDTOs(DataModelClassNodeDTO.ByClassId.class, (List) childDTOs);
            retrieveTieredClassNodes(dataModelClassDTO.getDataModelSuperClassDTO());
        }
    }

    public List<DataModelClassNodeDTO> nodesByDataModelClass(DataModelClassDTO dataModelClass) {
        final String METHODNAME = "nodesByDataModelClass ";
        List<DataModelClassNodeDTO> result = NODE_PULLDOWN_CACHE.get(dataModelClass);
        if (result == null) {
            result = new ArrayList<DataModelClassNodeDTO>();
            addClassNodes(dataModelClass, result, true, true, null, null);
            Collections.sort(result, new DataModelClassNodeComparator());
            NODE_PULLDOWN_CACHE.put(dataModelClass, result);
        }
        return result;
    }

    public void addClassNodes(
            DataModelClassDTO dataModelClassDTO,
            List<DataModelClassNodeDTO> childNodes,
            boolean includeSuperClassNodes,
            boolean includeNestedClassNodes,
            String namePrefix,
            List<Object> recursionCheck) {
        final String METHODNAME = "addClassNodes ";
        if (recursionCheck == null) {
            recursionCheck = new ArrayList<Object>();
        }
        try {
            if (dataModelClassDTO != null) {
//                List<Class<? extends BaseDTO>> childQueryClasses = new ArrayList<Class<? extends BaseDTO>>();
//                childQueryClasses.add(DataModelClassNodeDTO.class);
                PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
//                propertyBagDTO.setChildClassDTOs(childQueryClasses);
                propertyBagDTO.setQueryClass("ByClassId");
                DataModelClassNodeDTO dataModelClassNodeDTO = new DataModelClassNodeDTO();
                dataModelClassNodeDTO.setClassId(dataModelClassDTO.getClassId());
                List<DataModelClassNodeDTO> dataModelClassNodeDTOs = getGeneralMGRClient().findByQueryList(dataModelClassNodeDTO, getSessionDTO(), propertyBagDTO);
                for (DataModelClassNodeDTO item : dataModelClassNodeDTOs) {
                    if (recursionCheck.contains(item.getDataModelClassDTO())) {
                        logger.info(METHODNAME, "recursion detected, skipping node: ", item.getName());
                        continue;
                    }
                    recursionCheck.add(item.getDataModelClassDTO());
                    if (namePrefix != null) {
                        item.setName(String.format("%s/%s", namePrefix, item.getName()));
                    }
                    childNodes.add(item);
                    if (item.getClassType() == DataModelClassType.Complex && includeNestedClassNodes) {
                        addClassNodes(item.getDataModelClassDTO(), childNodes, true, includeNestedClassNodes, item.getName(), recursionCheck);
                    }
                    recursionCheck.remove(item.getDataModelClassDTO());
                }
                if (includeSuperClassNodes) {
                    addClassNodes(dataModelClassDTO.getDataModelSuperClassDTO(), childNodes, includeSuperClassNodes, includeNestedClassNodes, namePrefix, recursionCheck);
                }
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }
}
