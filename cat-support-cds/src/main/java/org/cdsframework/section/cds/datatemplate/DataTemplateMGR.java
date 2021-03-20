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
package org.cdsframework.section.cds.datatemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.DataModelClassDTO;
import org.cdsframework.dto.DataModelClassNodeDTO;
import org.cdsframework.dto.DataTemplateDTO;
import org.cdsframework.dto.DataTemplateNodeRelDTO;
import org.cdsframework.lookup.DataModelClassNodeDTOList;
import org.cdsframework.lookup.DataTemplateDTOList;
import org.cdsframework.util.comparator.DataModelClassNodeComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class DataTemplateMGR extends BaseModule<DataTemplateDTO> {

    private static final long serialVersionUID = -6730087768732781236L;
    @Inject
    DataModelClassNodeDTOList dataModelClassNodeDTOList;
    @Inject
    private DataTemplateDTOList dataTemplateDTOList;
    @Inject
    private DataTemplateNodeRelMGR dataTemplateNodeRelMGR;
    
    @Override
    protected void initialize() {
        setLazy(true);
        setInitialQueryClass("FindAll");
        setSaveImmediately(true);
        setAssociatedList(dataTemplateDTOList);
        registerChild(DataTemplateNodeRelDTO.ByTemplateId.class, dataTemplateNodeRelMGR);
    }
    
    @Override
    public void registerTabComponents() {
        final String METHODNAME = "registerTabLazyLoadComponents ";
        logger.info(METHODNAME);
        // Register the Components
        getTabService().registeredUIComponent(0, dataTemplateNodeRelMGR);
    }    

    public void updateChildNodes() {
        final String METHODNAME = "updateChildNodes ";
        logger.info(METHODNAME, "called!");
        DataTemplateDTO parentDTO = getParentDTO();
        if (parentDTO != null) {
            logger.info(METHODNAME, "parentDTO: ", parentDTO);
            DataModelClassDTO rootClass = parentDTO.getRootClass();
            if (rootClass != null) {
                logger.info(METHODNAME, "rootClass: ", rootClass);

                List<DataTemplateNodeRelDTO> dataTemplateNodeRelDTOs = parentDTO.getDataTemplateNodeRelDTOs();
                Iterator<DataTemplateNodeRelDTO> iterator = dataTemplateNodeRelDTOs.iterator();
                while (iterator.hasNext()) {
                    DataTemplateNodeRelDTO item = iterator.next();
                    if (item.isNew()) {
                        iterator.remove();
                        logger.info(METHODNAME, "Completely removing: ", item);
                    } else {
                        item.delete();
                        logger.info(METHODNAME, "Marking deleted: ", item);
                    }
                }

                List<DataModelClassNodeDTO> childNodes = new ArrayList<DataModelClassNodeDTO>();
                dataModelClassNodeDTOList.addClassNodes(rootClass, childNodes, true, false, null, null);
                Collections.sort(childNodes, new DataModelClassNodeComparator());
                logger.info(METHODNAME, "childNodes: ", childNodes);
                boolean hasChoices = false;
                for (DataModelClassNodeDTO item : childNodes) {
                    logger.info("Adding child node: ", item);
                    if (item.isChoice()) {
                        hasChoices = true;
                    } else {
                        DataTemplateNodeRelDTO newRel = new DataTemplateNodeRelDTO();
                        newRel.setDataModelClassNodeDTO(item);
                        newRel.setNodePath(item.getName());
                        parentDTO.addOrUpdateChildDTO(newRel);
                    }
                }

                dataTemplateNodeRelMGR.setDataTableMGR(parentDTO.getChildrenDTOs(DataTemplateNodeRelDTO.ByTemplateId.class, DataTemplateNodeRelDTO.class));
                if (hasChoices) {
                    getMessageMGR().displayInfoMessage("The template root class contains choices. Please add a new template node and make your selection.");
                }

            } else {
                logger.info(METHODNAME, "rootClass was null!");
            }
        } else {
            logger.info(METHODNAME, "parentDTO was null!");
        }

    }

}
