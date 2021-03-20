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
package org.cdsframework.section.cds.criteria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.datatable.DataTableInterface;
import org.cdsframework.dto.CriteriaDataTemplateRelDTO;
import org.cdsframework.dto.CriteriaDataTemplateRelNodeDTO;
import org.cdsframework.dto.DataTemplateDTO;
import org.cdsframework.dto.DataTemplateNodeRelDTO;
import org.cdsframework.ui.services.DataAccessInterface;
import org.cdsframework.ui.services.DataAccessService;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.enumeration.PrePost;
import org.cdsframework.util.enumeration.SourceMethod;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class CriteriaDataTemplateRelMGR extends BaseModule<CriteriaDataTemplateRelDTO> {

    private static final long serialVersionUID = 4476664177723416855L;

    @Override
    protected void initialize() {
        setLazy(true);
        setBaseHeader("Criteria Data Template Relationship");
        setSaveImmediately(true);
    }

    @Override
    public void registerTabComponents() {
        final String METHODNAME = "registerTabLazyLoadComponents ";
        logger.debug(METHODNAME);
        // Register the Components
        getTabService().registeredUIComponent(0, this);
    }

    @Override
    public void prePostOperation(String queryClass, BaseDTO baseDTO, PrePost prePost, boolean status) {
        final String METHODNAME = "prePostOperation ";
        super.prePostOperation(queryClass, baseDTO, prePost, status);
        switch (prePost) {
            case PreInlineSave:
                logger.info(METHODNAME, "PreInlineSave: ", baseDTO);
                if (baseDTO == null && !status) {
                    // remove all dtos marked deleted before the same fires
                    DataAccessInterface dataAccessInterface = getInlineDataTableService().getDataAccessInterface();
                    DataTableInterface<BaseDTO> dataTableInterface = dataAccessInterface.getDataTableInterface(CriteriaDataTemplateRelNodeDTO.class.getCanonicalName());
                    // determine which dtos need deleting and then delete them from a separate list to avoid concurrent list exception.
                    List<BaseDTO> nodeDtos = dataTableInterface.getDtoList();
                    List<BaseDTO> deletedList = new ArrayList<BaseDTO>();
                    for (BaseDTO dto : nodeDtos) {
                        if (dto.isDeleted()) {
                            deletedList.add(dto);
                        } else {
                            CriteriaDataTemplateRelNodeDTO criteriaDataTemplateRelNodeDTO = (CriteriaDataTemplateRelNodeDTO) dto;
                            criteriaDataTemplateRelNodeDTO.setNodePath(String.format("%s.%s", getParentDTO().getLabel(), criteriaDataTemplateRelNodeDTO.getDataTemplateNodeRelDTO().getNodePath()));
                        }
                    }
                    for (BaseDTO dto : deletedList) {
                        logger.debug(METHODNAME, "processing ", dto);
                        if (dto.isDeleted()) {
                            try {
                                logger.debug(METHODNAME, "deleting ", dto);
                                getInlineDataTableService().delete(true, dto);
                            } catch (Exception e) {
                                onExceptionMain(SourceMethod.deleteMain, e);
                            }
                        }
                    }
                    logger.debug(METHODNAME, "finished");
                }
                break;
            case PreSave:
                logger.info(METHODNAME, "PreSave: ", baseDTO);
                if (baseDTO instanceof CriteriaDataTemplateRelNodeDTO) {
                    CriteriaDataTemplateRelNodeDTO criteriaDataTemplateRelNodeDTO = (CriteriaDataTemplateRelNodeDTO) baseDTO;
                    criteriaDataTemplateRelNodeDTO.setNodePath(String.format("%s.%s", getParentDTO().getLabel(), criteriaDataTemplateRelNodeDTO.getDataTemplateNodeRelDTO().getNodePath()));
                }
                break;
        }
    }

    public void updateChildNodes() {
        final String METHODNAME = "updateChildNodes ";
        logger.debug(METHODNAME, "called!");
        CriteriaDataTemplateRelDTO parentDTO = getParentDTO();
        if (parentDTO != null) {
            try {
                logger.debug(METHODNAME, "parentDTO: ", parentDTO);
                DataTemplateDTO dataTemplateDTO = parentDTO.getDataTemplateDTO();
                if (parentDTO.getLabel() == null) {
                    parentDTO.setLabel(dataTemplateDTO.getTitle());
                }
                logger.debug(METHODNAME, "dataTemplateDTO: ", dataTemplateDTO);
                if (dataTemplateDTO != null) {
                    logger.debug(METHODNAME, "dataTemplateDTO.getName(): ", dataTemplateDTO.getName());

                    parentDTO.getCriteriaDataTemplateRelNodeDTOs();

                    DataAccessInterface dataAccessInterface = getInlineDataTableService().getDataAccessInterface();
                    DataTableInterface<BaseDTO> dataTableInterface = dataAccessInterface.getDataTableInterface(CriteriaDataTemplateRelNodeDTO.class.getCanonicalName());
                    List<CriteriaDataTemplateRelNodeDTO> criteriaDataTemplateRelNodeDTOs = (List) dataTableInterface.getDtoList();
                    logger.debug("criteriaDataTemplateRelNodeDTOs=", criteriaDataTemplateRelNodeDTOs);

                    Iterator<CriteriaDataTemplateRelNodeDTO> iterator = criteriaDataTemplateRelNodeDTOs.iterator();
                    while (iterator.hasNext()) {
                        CriteriaDataTemplateRelNodeDTO item = iterator.next();
                        logger.debug("Removing: ", item.getLabel());
                        if (item.isNew()) {
                            iterator.remove();
                            logger.debug(METHODNAME, "Completely removing: ", item);
                        } else {
                            item.delete();
                            logger.debug(METHODNAME, "Marking deleted: ", item);
                        }
                    }

//                    logger.debug(METHODNAME, "DataTemplateNodeRelDTOs: ", dataTemplateDTO.getDataTemplateNodeRelDTOs());
                    for (DataTemplateNodeRelDTO item : dataTemplateDTO.getDataTemplateNodeRelDTOs()) {
                        logger.debug("Adding child node: ", item);
                        CriteriaDataTemplateRelNodeDTO newRel = new CriteriaDataTemplateRelNodeDTO();
                        newRel.setDataTemplateNodeRelDTO(item);
                        newRel.setNodePath(item.getNodePath());
                        DataAccessService.add(newRel, dataAccessInterface, CriteriaDataTemplateRelNodeDTO.class.getCanonicalName());
                    }

                } else {
                    logger.debug(METHODNAME, "dataTemplateDTO was null!");
                }
                setUpdateIds(METHODNAME, UtilityMGR.getUIComponentUpdateId("dataContainer", false));
            } catch (Exception e) {
                onExceptionMain(SourceMethod.addMain, e);
            }
        } else {
            logger.debug(METHODNAME, "parentDTO was null!");
        }
    }

}
