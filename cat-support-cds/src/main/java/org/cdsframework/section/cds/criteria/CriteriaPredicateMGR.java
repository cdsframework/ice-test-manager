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
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.cdsframework.base.BasePredicateMGR;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BasePredicateDTO;
import org.cdsframework.dto.CriteriaDTO;
import org.cdsframework.dto.CriteriaPredicateDTO;
import org.cdsframework.dto.CriteriaPredicatePartDTO;
import org.cdsframework.dto.CriteriaResourceParamDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.util.comparator.CriteriaPredicateComparator;
import org.cdsframework.util.enumeration.PrePost;
import org.cdsframework.util.enumeration.SourceMethod;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class CriteriaPredicateMGR extends BasePredicateMGR<CriteriaPredicateDTO, CriteriaPredicatePartDTO> {

    private static final long serialVersionUID = 1944417712112766000L;
    @Inject
    private CriteriaPredicatePartMGR criteriaPredicatePartMGR;
    @Inject
    private CriteriaMGR criteriaMGR;

    @Override
    public void initializeNodeComparator() {
        nodeComparator = new CriteriaPredicateComparator();
    }

    @Override
    public void initializePredicateMGR() {
        addOnRowSelectChildClassDTO(CriteriaPredicateDTO.class);
        registerChild(CriteriaPredicatePartDTO.ByPredicateId.class, criteriaPredicatePartMGR);
        setSaveImmediately(true);
    }

    @Override
    public void registerTabComponents() {
        getTabService().registeredUIComponent(0, this);
        getTabService().registeredUIComponent(0, criteriaPredicatePartMGR);
    }

    @Override
    public CriteriaPredicateDTO getFullPredicateDTO(CriteriaPredicateDTO dto) {
        final String METHODNAME = "getFullPredicateDTO ";
        logger.debug(METHODNAME, "dto=", dto);
//        long start = System.nanoTime();
        CriteriaPredicateDTO result = dto;
        try {
            int indexOf = getFullPredicateList().indexOf(dto);
            if (indexOf == -1) {
                PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                List<Class<? extends BaseDTO>> childClasses = new ArrayList<Class<? extends BaseDTO>>();
                childClasses.add(CriteriaPredicateDTO.class);
                childClasses.add(CriteriaPredicatePartDTO.class);
                childClasses.add(CriteriaResourceParamDTO.class);
                propertyBagDTO.setChildClassDTOs(childClasses);
                result = getGeneralMGR().findByPrimaryKey(dto, getSessionDTO(), propertyBagDTO);
                getFullPredicateList().add(result);
            } else {
                logger.debug(METHODNAME, "got dto from the cache");
                result = getFullPredicateList().get(indexOf);
            }
        } catch (Exception e) {
            onExceptionMain(SourceMethod.refresh, e);
        } finally {
//            logger.logDuration(METHODNAME, start);
        }
        return result;
    }

    @Override
    public void prePostOperation(String queryClass, BaseDTO baseDTO, PrePost prePost, boolean status) {
        final String METHODNAME = "prePostOperation ";
        switch (prePost) {
//            case PreInlineDelete:
//            case PreDelete:
//                if (baseDTO != null) {
//                    logger.info(METHODNAME, "baseDTO=", baseDTO);
//                    baseDTO.setChildDTOMap(new HashMap());
//                }
//                break;
            case PreInlineSave:
            case PreSave:
                saveTier3Selections();
                break;
            case PostSave:
                if (status) {
                    CriteriaPredicateDTO criteriaPredicateDTO = (CriteriaPredicateDTO) baseDTO;
                    List<BasePredicateDTO> dtoList = (List) getDataTableMGR().getDtoList();
                    if (!StringUtils.isEmpty(criteriaPredicateDTO.getParentPredicateId())) {
                        if (dtoList.contains(criteriaPredicateDTO)) {
                            logger.debug(METHODNAME, "removing: ", criteriaPredicateDTO);
                            dtoList.remove(criteriaPredicateDTO);
                        }
                        addPredicateDTOToTree(dtoList, criteriaPredicateDTO);
                    } else if (!dtoList.contains(criteriaPredicateDTO)) {
                        logger.debug(METHODNAME, "adding: ", criteriaPredicateDTO);
                        dtoList.add(criteriaPredicateDTO);
                    }
                }
                setPredicatesDirty(true);
                break;
            case PostDelete:
                if (status) {
                    CriteriaPredicateDTO criteriaPredicateDTO = (CriteriaPredicateDTO) baseDTO;
                    List<BasePredicateDTO> dtoList = (List) getDataTableMGR().getDtoList();
                    removePredicateDTOFromTree(dtoList, criteriaPredicateDTO);
                }
                setPredicatesDirty(true);
                break;
            case PostAdd:
                try {
                    CriteriaPredicateDTO parentDTO = getParentDTO();
                    if (parentDTO != null) {
                        CriteriaPredicateDTO criteriaPredicateDTO = new CriteriaPredicateDTO();
                        PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                        List<? extends BasePredicateDTO> peerPredicateDTOs;
                        logger.debug(METHODNAME, "selectedCriteriaPredicateDTO: ", selectedCriteriaPredicateDTO);
                        if (selectedCriteriaPredicateDTO == null) {
                            CriteriaDTO criteriaDTO = criteriaMGR.getParentDTO();
                            criteriaPredicateDTO.setCriteriaId(criteriaDTO.getCriteriaId());
                            propertyBagDTO.setQueryClass("MaxOrderByCriteriaId");
                            peerPredicateDTOs = (List) criteriaDTO.getCriteriaPredicateDTOs();
                        } else {
                            criteriaPredicateDTO.setPredicateId(selectedCriteriaPredicateDTO.getPredicateId());
                            propertyBagDTO.setQueryClass("MaxOrderByPredicateId");
                            peerPredicateDTOs = selectedCriteriaPredicateDTO.getPredicateDTOs();
                        }
                        logger.debug(METHODNAME, "peerPredicateDTOs: ", peerPredicateDTOs);
                        Integer nextPredicateOrder;
                        try {
                            nextPredicateOrder = getGeneralMGR().findObjectByQuery(criteriaPredicateDTO, getSessionDTO(), Integer.class, propertyBagDTO);
                        } catch (NotFoundException e) {
                            nextPredicateOrder = 1;
                        }
                        logger.debug(METHODNAME,
                                "got order #: ", nextPredicateOrder);
                        if (nextPredicateOrder
                                == null) {
                            nextPredicateOrder = 0;
                        }
                        for (BasePredicateDTO item : peerPredicateDTOs) {
                            logger.debug(METHODNAME, "comparing ", nextPredicateOrder, " to ", item.getPredicateOrder());
                            if (item.getPredicateOrder() >= nextPredicateOrder) {
                                nextPredicateOrder = item.getPredicateOrder() + 1;
                            }
                        }
                        if (nextPredicateOrder
                                == 0) {
                            nextPredicateOrder = 1;
                        }

                        logger.debug(METHODNAME,
                                "final order number: ", nextPredicateOrder);
                        parentDTO.setPredicateOrder(nextPredicateOrder);
                    }
                } catch (Exception e) {
                    onExceptionMain(SourceMethod.addMain, e);
                }
                break;
        }
        // have to run this after the above code - not before...
        super.prePostOperation(queryClass, baseDTO, prePost, status);
    }

    @Override
    public boolean isOverridable(CriteriaPredicatePartDTO predicatePart) {
        return true;
    }

}
