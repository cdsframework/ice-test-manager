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
import java.util.Collections;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.base.BasePredicatePartConceptDTO;
import org.cdsframework.base.BasePredicatePartDTO;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsCodeSystemDTO;
import org.cdsframework.dto.CdsListDTO;
import org.cdsframework.dto.CriteriaDTO;
import org.cdsframework.dto.CriteriaDataTemplateRelDTO;
import org.cdsframework.dto.CriteriaDataTemplateRelNodeDTO;
import org.cdsframework.dto.CriteriaPredicateDTO;
import org.cdsframework.dto.CriteriaPredicatePartConceptDTO;
import org.cdsframework.dto.CriteriaPredicatePartDTO;
import org.cdsframework.dto.CriteriaPredicatePartRelDTO;
import org.cdsframework.dto.CriteriaResourceParamDTO;
import org.cdsframework.dto.OpenCdsConceptDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.ValueSetDTO;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.util.StringUtils;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.comparator.CriteriaDataTemplateRelNodeComparator;
import org.cdsframework.util.comparator.CriteriaPredicatePartComparator;
import org.cdsframework.util.enumeration.PrePost;
import org.cdsframework.util.enumeration.SourceMethod;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class CriteriaPredicatePartMGR extends BaseModule<CriteriaPredicatePartDTO> {

    private static final long serialVersionUID = -4248559360190468649L;
    @Inject
    private CriteriaDataTemplateRelMGR criteriaDataTemplateRelMGR;
    @Inject
    private CriteriaPredicateMGR criteriaPredicateMGR;

    @Override
    protected void initialize() {
        setSaveImmediately(true);
        addOnRowSelectChildClassDTO(CriteriaResourceParamDTO.class);
    }

    @Override
    public void registerTabComponents() {
        final String METHODNAME = "registerTabLazyLoadComponents ";
        logger.info(METHODNAME);
        // Register the Components
        getTabService().registeredUIComponent(0, this);
    }

    @Override
    public void resetField(BaseDTO baseDTO, String fieldName) {
        super.resetField(baseDTO, fieldName);
        final String METHODNAME = "resetField ";

        if (baseDTO instanceof CriteriaPredicateDTO) {
            CriteriaPredicateDTO criteriaPredicateDTO = (CriteriaPredicateDTO) baseDTO;
            if (fieldName.equalsIgnoreCase("predicateCriteriaDTO")) {
                criteriaPredicateDTO.setPredicateCriteriaDTO(null);
            }
//        } else if (baseDTO instanceof CriteriaPredicatePartDTO) {
//            CriteriaPredicatePartDTO criteriaPredicatePartDTO = (CriteriaPredicatePartDTO) baseDTO;
//            if (fieldName.equalsIgnoreCase("defaultCdsCode")) {
//                criteriaPredicatePartDTO.setDefaultCdsCodeDTO(null);
//            } else if (fieldName.equalsIgnoreCase("defaultOpenCDSConcept")) {
//                criteriaPredicatePartDTO.setDefaultOpenCdsConceptDTO(null);
//            }
        } else if (baseDTO instanceof CriteriaPredicatePartRelDTO) {
            CriteriaPredicatePartRelDTO criteriaPredicatePartRelDTO = (CriteriaPredicatePartRelDTO) baseDTO;
            if (fieldName.equalsIgnoreCase("cdsCodeSystemDTO")) {
                criteriaPredicatePartRelDTO.setCdsCodeSystemDTO(null);
            } else if (fieldName.equalsIgnoreCase("cdsListDTO")) {
                criteriaPredicatePartRelDTO.setCdsListDTO(null);
            } else if (fieldName.equalsIgnoreCase("cdsCodeDTO")) {
                criteriaPredicatePartRelDTO.setCdsCodeDTO(null);
            } else if (fieldName.equalsIgnoreCase("openCdsConceptDTO")) {
                criteriaPredicatePartRelDTO.setOpenCdsConceptDTO(null);
            } else if (fieldName.equalsIgnoreCase("valueSetDTO")) {
                criteriaPredicatePartRelDTO.setValueSetDTO(null);
            }
        }
    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onPopupSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        logger.info(METHODNAME, "baseDTO=", (baseDTO != null ? baseDTO.getClass().getSimpleName() : baseDTO));
        BaseDTO selectedDTO = getSelectedDTO();
        logger.info(METHODNAME, "selectedDTO=", (selectedDTO != null ? selectedDTO.getClass().getSimpleName() : selectedDTO));

        if (baseDTO instanceof CriteriaDTO) {
            CriteriaDTO criteriaDTO = (CriteriaDTO) baseDTO;
            if (selectedDTO instanceof CriteriaPredicateDTO) {
                logger.info(METHODNAME, "calling setPredicateCriteriaDTO on selectedDTO");
                CriteriaPredicateDTO criteriaPredicateDTO = (CriteriaPredicateDTO) selectedDTO;
                criteriaPredicateDTO.setPredicateCriteriaDTO(criteriaDTO);
            }
        } else if (baseDTO instanceof CdsCodeDTO) {
            CdsCodeDTO cdsCodeDTO = (CdsCodeDTO) baseDTO;
//            if (selectedDTO instanceof CriteriaPredicatePartDTO) {
//                // TODO: fix this if we implement a multiple selection allowance flag
//                logger.info(METHODNAME, "calling setDefaultCdsCodeDTO on selectedDTO");
//                CriteriaPredicatePartDTO criteriaPredicatePartDTO = (CriteriaPredicatePartDTO) selectedDTO;
//                criteriaPredicatePartDTO.setDefaultCdsCodeDTO(cdsCodeDTO);
//                logger.info(METHODNAME, "criteriaPredicatePartDTO.getDefaultCdsCodeDTO()=", criteriaPredicatePartDTO.getDefaultCdsCodeDTO());
//            } else
            if (selectedDTO instanceof CriteriaPredicatePartRelDTO) {
                CriteriaPredicatePartRelDTO criteriaPredicatePartRelDTO = (CriteriaPredicatePartRelDTO) selectedDTO;
                criteriaPredicatePartRelDTO.setCdsCodeDTO(cdsCodeDTO);
            } else if (selectedDTO instanceof CriteriaPredicatePartConceptDTO) {
                logger.info(METHODNAME, "calling setDefaultCdsCodeDTO on selectedDTO");
                CriteriaPredicatePartConceptDTO criteriaPredicatePartConceptDTO = (CriteriaPredicatePartConceptDTO) selectedDTO;
                criteriaPredicatePartConceptDTO.setCdsCodeDTO(cdsCodeDTO);
                logger.info(METHODNAME, "criteriaPredicatePartConceptDTO.getCdsCodeDTO()=", criteriaPredicatePartConceptDTO.getCdsCodeDTO());
            }
//            else {
//                logger.info(METHODNAME, "calling setDefaultCdsCodeDTO on parent");
//                getParentDTO().setDefaultCdsCodeDTO(cdsCodeDTO);
//            }
        } else if (baseDTO instanceof OpenCdsConceptDTO) {
            OpenCdsConceptDTO openCdsConceptDTO = (OpenCdsConceptDTO) baseDTO;
//            if (selectedDTO instanceof CriteriaPredicatePartDTO) {
//                logger.info(METHODNAME, "calling setDefaultOpenCdsConceptDTO on selectedDTO");
//                CriteriaPredicatePartDTO criteriaPredicatePartDTO = (CriteriaPredicatePartDTO) selectedDTO;
//                criteriaPredicatePartDTO.setDefaultOpenCdsConceptDTO(openCdsConceptDTO);
//                logger.info(METHODNAME, "criteriaPredicatePartDTO.getDefaultOpenCdsConceptDTO()=", criteriaPredicatePartDTO.getDefaultOpenCdsConceptDTO());
//            } else
            if (selectedDTO instanceof CriteriaPredicatePartRelDTO) {
                CriteriaPredicatePartRelDTO criteriaPredicatePartRelDTO = (CriteriaPredicatePartRelDTO) selectedDTO;
                criteriaPredicatePartRelDTO.setOpenCdsConceptDTO(openCdsConceptDTO);
            } else if (selectedDTO instanceof CriteriaPredicatePartConceptDTO) {
                logger.info(METHODNAME, "calling setOpenCdsConceptDTO on selectedDTO");
                CriteriaPredicatePartConceptDTO criteriaPredicatePartConceptDTO = (CriteriaPredicatePartConceptDTO) selectedDTO;
                criteriaPredicatePartConceptDTO.setOpenCdsConceptDTO(openCdsConceptDTO);
                logger.info(METHODNAME, "criteriaPredicatePartConceptDTO.getOpenCdsConceptDTO()=", criteriaPredicatePartConceptDTO.getOpenCdsConceptDTO());
            }
//            else {
//                logger.info(METHODNAME, "calling setDefaultOpenCdsConceptDTO on parent");
//                getParentDTO().setDefaultOpenCdsConceptDTO(openCdsConceptDTO);
//            }
        } else if (baseDTO instanceof CdsCodeSystemDTO) {
            CdsCodeSystemDTO cdsCodeSystemDTO = (CdsCodeSystemDTO) baseDTO;
            if (selectedDTO instanceof CriteriaPredicatePartRelDTO) {
                CriteriaPredicatePartRelDTO criteriaPredicatePartRelDTO = (CriteriaPredicatePartRelDTO) selectedDTO;
                criteriaPredicatePartRelDTO.setCdsCodeSystemDTO(cdsCodeSystemDTO);
            }
        } else if (baseDTO instanceof ValueSetDTO) {
            ValueSetDTO valueSetDTO = (ValueSetDTO) baseDTO;
            if (selectedDTO instanceof CriteriaPredicatePartRelDTO) {
                CriteriaPredicatePartRelDTO criteriaPredicatePartRelDTO = (CriteriaPredicatePartRelDTO) selectedDTO;
                criteriaPredicatePartRelDTO.setValueSetDTO(valueSetDTO);
            }
        } else if (baseDTO instanceof CdsListDTO) {
            CdsListDTO cdsListDTO = (CdsListDTO) baseDTO;
            if (selectedDTO instanceof CriteriaPredicatePartRelDTO) {
                CriteriaPredicatePartRelDTO criteriaPredicatePartRelDTO = (CriteriaPredicatePartRelDTO) selectedDTO;
                criteriaPredicatePartRelDTO.setCdsListDTO(cdsListDTO);
            }
        }

        // Clear out selected DTO
        setSelectedDTO(null);

    }

    public String getCriteriaPredicatePartDTOLabel(CriteriaPredicatePartDTO dto) {
        final String METHODNAME = "getCriteriaPredicatePartDTOLabel ";
        long start = System.nanoTime();
        String result = "[missing label]";
        try {
            PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
            propertyBagDTO.setQueryClass("GetLabel");
            result = getGeneralMGR().findObjectByQuery(dto, getSessionDTO(), String.class, propertyBagDTO);
        } catch (Exception e) {
            onExceptionMain(SourceMethod.refresh, e);
        } finally {
            logger.logDuration(METHODNAME, start);
        }
        return result;
    }
//
//    public List<CdsCodeDTO> getCdsCodeSelectItems(CriteriaPredicatePartDTO criteriaPredicatePartDTO) {
//        final String METHODNAME = "getCdsCodeSelectItems ";
//        logger.debug(METHODNAME, "criteriaPredicatePartDTO=", criteriaPredicatePartDTO);
//        List<CdsCodeDTO> result = new ArrayList<CdsCodeDTO>();
//        if (criteriaPredicatePartDTO != null) {
//            logger.debug(METHODNAME, "criteriaPredicatePartDTO.getCriteriaPredicatePartRelDTOs()=", criteriaPredicatePartDTO.getPredicatePartRelDTOs());
//            try {
//                List<BaseDTO> dtoList = (List) criteriaPredicatePartDTO.getPredicatePartRelDTOs();
//                if (dtoList.isEmpty()) {
//                    DataTableInterface<BaseDTO> dataTableInterface = getInlineDataTableService().getDataAccessInterface().getDataTableInterface(CriteriaPredicatePartRelDTO.class.getCanonicalName());
//                    if (dataTableInterface != null) {
//                        dtoList = dataTableInterface.getDtoList();
//                    }
//                }
//                logger.debug(METHODNAME, "dtoList=", dtoList);
//                for (BaseDTO baseDTO : dtoList) {
//                    CriteriaPredicatePartRelDTO item = (CriteriaPredicatePartRelDTO) baseDTO;
//                    if (!item.isDeleted()) {
//                        if (item.getConstraintType() == ConceptConstraintType.CodeSystem
//                                || item.getConstraintType() == ConceptConstraintType.ValueSet
//                                || (item.getConstraintType() == ConceptConstraintType.List
//                                && item.getCdsListDTO() != null
//                                && (item.getCdsListDTO().getListType() == CdsListType.CODE_SYSTEM
//                                || item.getCdsListDTO().getListType() == CdsListType.VALUE_SET))) {
//                            for (CdsCodeDTO cdsCodeDTO : getRelationshipCdsCodeDTOs(item)) {
//                                if (!result.contains(cdsCodeDTO)) {
//                                    result.add(cdsCodeDTO);
//                                }
//                            }
//                        } else {
//                            logger.warn(METHODNAME, "inappropriate call to method. part relationship is not code based.");
//                        }
//                    }
//                }
//                Collections.sort(result, new CdsCodeComparator());
//            } catch (Exception e) {
//                DefaultExceptionHandler.handleException(e, getClass());
//            }
//        } else {
//            logger.error(METHODNAME, "criteriaPredicatePartDTO was null!");
//        }
//        return result;
//    }
//
//    public List<OpenCdsConceptDTO> getOpenCdsConceptSelectItems(CriteriaPredicatePartDTO criteriaPredicatePartDTO) {
//        final String METHODNAME = "getOpenCdsConceptSelectItems ";
//        logger.debug(METHODNAME, "criteriaPredicatePartDTO=", criteriaPredicatePartDTO);
//        List<OpenCdsConceptDTO> result = new ArrayList<OpenCdsConceptDTO>();
//        if (criteriaPredicatePartDTO != null) {
//            try {
//                logger.debug(METHODNAME, "criteriaPredicatePartDTO.getCriteriaPredicatePartRelDTOs()=", criteriaPredicatePartDTO.getPredicatePartRelDTOs());
//                List<BaseDTO> dtoList = (List) criteriaPredicatePartDTO.getPredicatePartRelDTOs();
//                if (dtoList.isEmpty()) {
//                    DataTableInterface<BaseDTO> dataTableInterface = getInlineDataTableService().getDataAccessInterface().getDataTableInterface(CriteriaPredicatePartRelDTO.class.getCanonicalName());
//                    if (dataTableInterface != null) {
//                        dtoList = dataTableInterface.getDtoList();
//                    }
//                }
//                logger.debug(METHODNAME, "dtoList=", dtoList);
//                for (BaseDTO baseDTO : dtoList) {
//                    CriteriaPredicatePartRelDTO item = (CriteriaPredicatePartRelDTO) baseDTO;
//                    logger.debug(METHODNAME, "item=", item);
//                    if (!item.isDeleted()) {
//                        if (item.getConstraintType() == ConceptConstraintType.CodeSystem
//                                || item.getConstraintType() == ConceptConstraintType.ValueSet
//                                || (item.getConstraintType() == ConceptConstraintType.List
//                                && item.getCdsListDTO() != null
//                                && (item.getCdsListDTO().getListType() == CdsListType.CODE_SYSTEM
//                                || item.getCdsListDTO().getListType() == CdsListType.VALUE_SET))) {
//                            List<CdsCodeDTO> cdsCodeSelectItems = getRelationshipCdsCodeDTOs(item);
//                            for (OpenCdsConceptDTO openCdsConceptDTO : openCdsConceptDTOList.getAll()) {
//                                for (OpenCdsConceptRelDTO openCdsConceptRelDTO : openCdsConceptDTO.getOpenCdsConceptRelDTOs()) {
//                                    if (cdsCodeSelectItems.contains(openCdsConceptRelDTO.getCdsCodeDTO())) {
//                                        if (!result.contains(openCdsConceptDTO)) {
//                                            result.add(openCdsConceptDTO);
//                                        }
//                                        break;
//                                    }
//                                }
//                            }
//                        } else if (item.getConstraintType() == ConceptConstraintType.List
//                                && item.getCdsListDTO() != null
//                                && (item.getCdsListDTO().getListType() == CdsListType.AD_HOC_CONCEPT
//                                || item.getCdsListDTO().getListType() == CdsListType.CONCEPT)) {
//                            CdsListDTO cdsListDTO = cdsListDTOList.get(item.getCdsListDTO().getListId());
//                            for (CdsListItemDTO listItem : cdsListDTO.getCdsListItemDTOs()) {
//                                if (!result.contains(listItem.getOpenCdsConceptDTO())) {
//                                    result.add(listItem.getOpenCdsConceptDTO());
//                                }
//                            }
//                        } else {
//                            logger.warn(METHODNAME, "inappropriate call to method. part relationship type is unhandled.");
//                        }
//                    }
//                }
//                Collections.sort(result, new OpenCdsConceptComparator());
//
//            } catch (Exception e) {
//                DefaultExceptionHandler.handleException(e, getClass());
//            }
//        } else {
//            logger.error(METHODNAME, "criteriaPredicatePartDTO was null!");
//        }
//        return result;
//    }
//
//    private List<CdsCodeDTO> getRelationshipCdsCodeDTOs(CriteriaPredicatePartRelDTO criteriaPredicatePartRelDTO) {
//        final String METHODNAME = "getRelationshipCdsCodeDTOs ";
//        List<CdsCodeDTO> result = new ArrayList<CdsCodeDTO>();
////            logger.info(METHODNAME, "openCdsConceptRelDTO: ", criteriaPredicatePartRelDTO);
////            logger.info(METHODNAME, "openCdsConceptRelDTO.getCdsCodeSystemDTO(): ", criteriaPredicatePartRelDTO.getCdsCodeSystemDTO());
////            logger.info(METHODNAME, "openCdsConceptRelDTO.getCdsListDTO(): ", criteriaPredicatePartRelDTO.getCdsListDTO());
////            logger.info(METHODNAME, "openCdsConceptRelDTO.getValueSetDTO(): ", criteriaPredicatePartRelDTO.getValueSetDTO());
////            logger.info(METHODNAME, "openCdsConceptRelDTO.getConstraintType(): ", criteriaPredicatePartRelDTO.getConstraintType());
////            logger.info(METHODNAME, "openCdsConceptRelDTO.getPartId(): ", criteriaPredicatePartRelDTO.getPartId());
////            logger.info(METHODNAME, "openCdsConceptRelDTO.getRelId(): ", criteriaPredicatePartRelDTO.getRelId());
//        if (criteriaPredicatePartRelDTO != null) {
//
//            logger.debug(METHODNAME, "item.getConstraintType(): ", criteriaPredicatePartRelDTO.getConstraintType());
//            if (null != criteriaPredicatePartRelDTO.getConstraintType()) {
//                switch (criteriaPredicatePartRelDTO.getConstraintType()) {
//                    case CodeSystem:
//                        logger.debug(METHODNAME, "processing CodeSystem: ", criteriaPredicatePartRelDTO.getCdsCodeSystemDTO());
//                        if (criteriaPredicatePartRelDTO.getCdsCodeSystemDTO() != null) {
//                            CdsCodeSystemDTO cdsCodeSystemDTO = cdsCodeSystemDTOList.get(criteriaPredicatePartRelDTO.getCdsCodeSystemDTO().getCodeSystemId());
//                            if (cdsCodeSystemDTO != null) {
//                                result.addAll(cdsCodeSystemDTO.getCdsCodeDTOs());
//                            } else {
//                                logger.warn(METHODNAME, "cdsCodeSystemDTO was null!");
//                            }
//                        } else {
//                            logger.warn(METHODNAME, "item.getCdsCodeSystemDTO() was null!");
//                        }
//                        break;
//                    case List:
//                        logger.debug(METHODNAME, "processing List: ", criteriaPredicatePartRelDTO.getCdsListDTO());
//                        if (criteriaPredicatePartRelDTO.getCdsListDTO() != null) {
//                            CdsListDTO cdsListDTO = cdsListDTOList.get(criteriaPredicatePartRelDTO.getCdsListDTO().getListId());
//                            if (cdsListDTO != null) {
//                                if ((cdsListDTO.getCdsCodeSystemDTO() != null || cdsListDTO.getValueSetDTO() != null) && !cdsListDTO.isConceptBased()) {
//                                    for (CdsListItemDTO cdsListItemDTO : cdsListDTO.getCdsListItemDTOs()) {
//                                        if (cdsListItemDTO.getCdsCodeDTO() != null) {
//                                            if (!result.contains(cdsListItemDTO.getCdsCodeDTO())) {
//                                                result.add(cdsListItemDTO.getCdsCodeDTO());
//                                            }
//                                        }
//                                    }
//                                }
//                            } else {
//                                logger.warn(METHODNAME, "cdsListDTO was null!");
//                            }
//                        } else {
//                            logger.warn(METHODNAME, "item.getCdsListDTO() was null!");
//                        }
//                        break;
//                    case ValueSet:
//                        logger.debug(METHODNAME, "processing ValueSet: ", criteriaPredicatePartRelDTO.getValueSetDTO());
//                        if (criteriaPredicatePartRelDTO.getValueSetDTO() != null) {
//                            ValueSetDTO valueSetDTO = valueSetDTOList.get(criteriaPredicatePartRelDTO.getValueSetDTO().getValueSetId());
//                            if (valueSetDTO != null) {
//                                for (CdsCodeDTO cdsCodeDTO : valueSetDTO.getCdsCodeDTOs()) {
//                                    if (!result.contains(cdsCodeDTO)) {
//                                        result.add(cdsCodeDTO);
//                                    }
//                                }
//                            } else {
//                                logger.warn(METHODNAME, "valueSetDTO was null!");
//                            }
//                        } else {
//                            logger.warn(METHODNAME, "item.getValueSetDTO() was null!");
//                        }
//                        break;
//                    default:
//                        break;
//                }
//            }
//            logger.debug(METHODNAME, "going into another round!");
//        }
//        return result;
//    }

    public List<CriteriaDataTemplateRelNodeDTO> getCriteriaDataTemplateRelNodeDTOs() {
        final String METHODNAME = "getCriteriaDataTemplateRelNodeDTOs ";
        List<CriteriaDataTemplateRelDTO> dtoList = criteriaDataTemplateRelMGR.getDataTableMGR().getDtoList();
        List<CriteriaDataTemplateRelNodeDTO> result = new ArrayList<CriteriaDataTemplateRelNodeDTO>();
        PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
        propertyBagDTO.setQueryClass("ByParenRelId");
        if (dtoList != null) {
            for (CriteriaDataTemplateRelDTO item : dtoList) {
                CriteriaDataTemplateRelNodeDTO criteriaDataTemplateRelNodeDTO = new CriteriaDataTemplateRelNodeDTO();
                criteriaDataTemplateRelNodeDTO.setParentRelId(item.getRelId());
                try {
                    List<CriteriaDataTemplateRelNodeDTO> nodes = getMts().getGeneralMGR().findByQueryList(criteriaDataTemplateRelNodeDTO, getSessionDTO(), propertyBagDTO);
                    for (CriteriaDataTemplateRelNodeDTO node : nodes) {
                        result.add(node);
                    }
                } catch (Exception e) {
                    DefaultExceptionHandler.handleException(e, getClass());
                }
            }
        }
        Collections.sort(result, new CriteriaDataTemplateRelNodeComparator());
        return result;
    }

    @Override
    public void prePostOperation(String queryClass, BaseDTO baseDTO, PrePost prePost, boolean status) {
        final String METHODNAME = "prePostOperation ";
        logger.info(METHODNAME, "queryClass=", queryClass, " baseDTO=", baseDTO, " prePost=", prePost, " status=", status);

        super.prePostOperation(queryClass, baseDTO, prePost, status);
        switch (prePost) {
            case PostInlineDelete:
            case PostInlineSave:
                if (status) {
                    logger.info(METHODNAME, "about to refresh tree");
                    criteriaPredicateMGR.setPredicatesDirty(true);
                    criteriaPredicateMGR.updateTreeTableMain();
                }
                break;
            case PostSave:
                if (status) {
                    CriteriaPredicatePartComparator criteriaPredicatePartComparator = new CriteriaPredicatePartComparator();
                    CriteriaPredicateDTO parentDTO = criteriaPredicateMGR.getParentDTO();
                    Collections.sort(parentDTO.getPredicatePartDTOs(), criteriaPredicatePartComparator);
                    List<CriteriaPredicatePartDTO> dtoList = getDataTableMGR().getDtoList();
                    Collections.sort(dtoList, criteriaPredicatePartComparator);
                    setUpdateIds(METHODNAME, getDataTableUpdateId());
                    criteriaPredicateMGR.setPredicatesDirty(true);
                    criteriaPredicateMGR.updateTreeTableMain();
                }
                break;
            case PostDelete:
                if (status) {
                    criteriaPredicateMGR.setPredicatesDirty(true);
                    setUpdateIds(METHODNAME, getDataTableUpdateId());
                }
                break;
            case PostAdd:
                try {
                    CriteriaPredicatePartDTO criteriaPredicatePartDTO = (CriteriaPredicatePartDTO) baseDTO;
                    CriteriaPredicateDTO criteriaPredicateDTO = criteriaPredicateMGR.getParentDTO();
                    if (criteriaPredicateDTO != null && criteriaPredicateDTO.getPredicateId() != null) {
                        CriteriaPredicatePartDTO searchDTO = new CriteriaPredicatePartDTO();
                        searchDTO.setPredicateId(criteriaPredicateDTO.getPredicateId());
                        PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                        propertyBagDTO.setQueryClass("MaxOrderByPredicateId");
                        Integer nextPredicatePartOrder;
                        try {
                            nextPredicatePartOrder = getGeneralMGR().findObjectByQuery(searchDTO, getSessionDTO(), Integer.class, propertyBagDTO);
                        } catch (NotFoundException e) {
                            nextPredicatePartOrder = 1;
                        }
                        logger.debug(METHODNAME, "got order #: ", nextPredicatePartOrder);
                        if (nextPredicatePartOrder == null) {
                            nextPredicatePartOrder = 0;
                        }
                        for (BasePredicatePartDTO item : criteriaPredicateDTO.getPredicatePartDTOs()) {
                            logger.debug(METHODNAME, "comparing ", nextPredicatePartOrder, " to ", item.getPredicatePartOrder());
                            if (item.getPredicatePartOrder() >= nextPredicatePartOrder) {
                                nextPredicatePartOrder = item.getPredicatePartOrder() + 1;
                            }
                        }
                        if (nextPredicatePartOrder == 0) {
                            nextPredicatePartOrder = 1;
                        }
                        logger.debug(METHODNAME, "final order number: ", nextPredicatePartOrder);
                        criteriaPredicatePartDTO.setPredicatePartOrder(nextPredicatePartOrder);
                    }
                } catch (Exception e) {
                    onExceptionMain(SourceMethod.addMain, e);
                }
                break;
        }
    }

    public void onNodePromote(CriteriaPredicatePartDTO criteriaPredicatePartDTO) throws Exception {
        setParentDTO(criteriaPredicatePartDTO);
        onNodeMove(criteriaPredicatePartDTO, true);
    }

    public void onNodeDemote(CriteriaPredicatePartDTO criteriaPredicatePartDTO) throws Exception {
        setParentDTO(criteriaPredicatePartDTO);
        onNodeMove(criteriaPredicatePartDTO, false);
    }

    private void onNodeMove(CriteriaPredicatePartDTO criteriaPredicatePartDTO, boolean promote) throws Exception {
        final String METHODNAME = "onNodeMove ";
        logger.debug(METHODNAME, "criteriaPredicatePartDTO: ", criteriaPredicatePartDTO);
        logger.debug(METHODNAME, "promote: ", promote);

        if (criteriaPredicatePartDTO != null) {

            logger.debug(METHODNAME, "starting order: ", criteriaPredicatePartDTO.getPredicatePartOrder());
            List<CriteriaPredicatePartDTO> peerDTOs = getDataTableMGR().getDtoList();
            List<CriteriaPredicatePartDTO> changedDTOs = new ArrayList<CriteriaPredicatePartDTO>();
            Collections.sort(peerDTOs, new CriteriaPredicatePartComparator());
            int c = 1;
            for (CriteriaPredicatePartDTO item : peerDTOs) {
                item.setPredicatePartOrder(c++);
            }
            logger.debug(METHODNAME, "peerDTOs: ", peerDTOs);
            int indexOf = peerDTOs.indexOf(criteriaPredicatePartDTO);
            logger.debug(METHODNAME, "indexOf: ", indexOf);

            if (indexOf > -1) {
                CriteriaPredicatePartDTO peerDTO;
                try {
                    // swap order value with peer
                    if (promote) {
                        peerDTO = peerDTOs.get(indexOf - 1);
                    } else {
                        peerDTO = peerDTOs.get(indexOf + 1);
                    }

                    int predicatePartOrder = peerDTO.getPredicatePartOrder();
                    logger.debug(METHODNAME, "swapping ", criteriaPredicatePartDTO.getPredicatePartOrder(), " with ", predicatePartOrder);
                    peerDTO.setPredicatePartOrder(criteriaPredicatePartDTO.getPredicatePartOrder());
                    criteriaPredicatePartDTO.setPredicatePartOrder(predicatePartOrder);
                    Collections.sort(peerDTOs, new CriteriaPredicatePartComparator());
                    changedDTOs.add(peerDTO);
                    changedDTOs.add(criteriaPredicatePartDTO);

                    setSaveUpdateIds();
                } catch (IndexOutOfBoundsException e) {
                    logger.debug(METHODNAME, "index was out of bounds");
                }
            } else {
                logger.debug(METHODNAME, "indexOf was less than 1: ", indexOf);

            }

            // save the changes
            for (CriteriaPredicatePartDTO item : changedDTOs) {
                setParentDTO(item);
                saveMain(UtilityMGR.getEmulatedActionEvent(), false);
            }
            criteriaPredicateMGR.updateTreeTableMain();
        } else {
            logger.debug(METHODNAME, "criteriaPredicatePartDTO is null!");
        }
    }

    private boolean showConceptCodeEditor = false;

    public boolean isShowConceptCodeEditor() {
        return showConceptCodeEditor;
    }

    public void setShowConceptCodeEditor(boolean showConceptCodeEditor) {
        this.showConceptCodeEditor = showConceptCodeEditor;
    }

    public void showConceptCodeEditor(CriteriaPredicatePartDTO criteriaPredicatePartDTO) {
        final String METHODNAME = "showConceptCodeEditor ";
        logger.info(METHODNAME, "criteriaPredicatePartDTO=", criteriaPredicatePartDTO);

        if (criteriaPredicatePartDTO != null) {
            setShowConceptCodeEditor(true);
            onRowSelectMain(UtilityMGR.getEmulatedSelectEvent(criteriaPredicatePartDTO));
            String editDialogWidgetVar = "PF('" + getEditDialogWidgetVar("edit") + "').show()";
            String updateIds = getOnRowSelectUpdateIds();

            logger.info(METHODNAME, "editDialogWidgetVar=", editDialogWidgetVar);
            if (!StringUtils.isEmpty(editDialogWidgetVar)) {
                RequestContext.getCurrentInstance().execute(editDialogWidgetVar);
            }
            if (updateIds != null) {
                setUpdateIds(METHODNAME, updateIds);
            }
        }

        List<? extends BasePredicatePartConceptDTO> predicatePartConceptDTOs = criteriaPredicatePartDTO.getPredicatePartConceptDTOs();
        for (BasePredicatePartConceptDTO criteriaPredicatePartConceptDTO : predicatePartConceptDTOs) {
            logger.info(METHODNAME, "criteriaPredicatePartConceptDTO.getDTOStates()=", criteriaPredicatePartConceptDTO.getDTOStates());
        }

    }

    @Override
    protected void postClose(CloseEvent event) {
        setShowConceptCodeEditor(false);
    }

}
