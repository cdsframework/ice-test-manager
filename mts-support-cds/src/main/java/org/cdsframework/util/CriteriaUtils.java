/**
 * The MTS support cds project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
 *
 * Copyright (C) 2016 New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * Contributions by HLN Consulting, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. You should have received a copy of the GNU Lesser
 * General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/> for more details.
 *
 * The above-named contributors (HLN Consulting, LLC) are also licensed by the
 * New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * to have (without restriction, limitation, and warranty) complete irrevocable
 * access and rights to this project.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; THE
 *
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING, BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR
 * OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see
 * https://www.hln.com/services/open-source/ or send correspondence to
 * ice@hln.com.
 */
package org.cdsframework.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.cdsframework.base.BasePredicateDTO;
import org.cdsframework.base.BasePredicatePartConceptDTO;
import org.cdsframework.base.BasePredicatePartDTO;
import org.cdsframework.base.BasePredicateSourcePartDTO;
import org.cdsframework.base.BaseSourcePredicateDTO;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CriteriaDTO;
import org.cdsframework.dto.CriteriaDataTemplateRelDTO;
import org.cdsframework.dto.CriteriaDataTemplateRelNodeDTO;
import org.cdsframework.dto.CriteriaPredicateDTO;
import org.cdsframework.dto.CriteriaPredicatePartDTO;
import org.cdsframework.dto.CriteriaResourceDTO;
import org.cdsframework.dto.CriteriaResourceParamDTO;
import org.cdsframework.dto.DataModelClassDTO;
import org.cdsframework.dto.DataModelClassNodeDTO;
import org.cdsframework.dto.DataTemplateDTO;
import org.cdsframework.dto.DataTemplateNodeRelDTO;
import org.cdsframework.dto.OpenCdsConceptDTO;
import org.cdsframework.dto.OpenCdsConceptRelDTO;
import org.cdsframework.enumeration.ConceptSelectionType;
import org.cdsframework.enumeration.CriteriaPredicateType;
import org.cdsframework.enumeration.DataModelClassType;
import org.cdsframework.enumeration.DetectedModelElementType;
import org.cdsframework.enumeration.PredicatePartType;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.interfaces.CriteriaInterface;
import org.cdsframework.interfaces.PredicateInterface;
import org.cdsframework.interfaces.PredicatePartConceptInterface;
import org.cdsframework.interfaces.PredicatePartInterface;
import org.cdsframework.util.comparator.CriteriaPredicatePartComparator;
import org.cdsframework.util.comparator.CriteriaPredicateComparator;
import org.cdsframework.util.support.data.cds.criterion.ConceptSelectionTypeEnum;
import org.cdsframework.util.support.data.cds.criterion.CriteriaMethodEnum;
import org.cdsframework.util.support.data.cds.criterion.CriteriaResourceTypeEnum;
import org.cdsframework.util.support.data.cds.criterion.CriteriaTypeEnum;
import org.cdsframework.util.support.data.cds.criterion.Criterion;
import org.cdsframework.util.support.data.cds.criterion.DataModelClassTypeEnum;
import org.cdsframework.util.support.data.cds.criterion.Predicate;
import org.cdsframework.util.support.data.cds.criterion.PredicateConjunctionEnum;
import org.cdsframework.util.support.data.cds.criterion.PredicatePart;
import org.cdsframework.util.support.data.cds.criterion.PredicatePartConceptSelection;
import org.cdsframework.util.support.data.cds.criterion.PredicatePartTypeEnum;
import org.cdsframework.util.support.data.cds.criterion.PredicateTypeEnum;
import org.cdsframework.util.support.data.cds.criterion.Resource;
import org.cdsframework.util.support.data.cds.criterion.ResourceParameter;
import org.opencds.vmr.v1_0.schema.IVLTS;
import org.opencds.vmr.v1_0.schema.PQ;

/**
 *
 * @author HLN Consulting, LLC
 */
public class CriteriaUtils {

    private static final LogUtils logger = LogUtils.getLogger(CriteriaUtils.class);
    private static final CriteriaPredicatePartComparator predicatePartComparator = new CriteriaPredicatePartComparator();

    /**
     * Export the supplied implementation of the CriteriaInterface.
     *
     * @param criteriaImpl
     * @return
     */
    public static Criterion exportCriteria(CriteriaInterface criteriaImpl) {
        final String METHODNAME = "exportCriteria ";
        Criterion result;
        if (criteriaImpl != null) {
            result = new Criterion();
            result.setId(criteriaImpl.getCriteriaId());
            if (criteriaImpl.getCriteriaType() != null) {
                result.setCriteriaType(CriteriaTypeEnum.fromValue(criteriaImpl.getCriteriaType().name()));
            }
            if (criteriaImpl.getMethod() != null) {
                result.setCriteriaMethod(CriteriaMethodEnum.fromValue(criteriaImpl.getMethod().name()));
            }
            result.setDescription(criteriaImpl.getDescription());
            result.setName(criteriaImpl.getName());
            // add the predicates
            List<Predicate> predicates = result.getPredicates();
            List<PredicateInterface> predicateDTOs = criteriaImpl.getPredicateDTOs();
            CriteriaPredicateComparator criteriaPredicateComparator = new CriteriaPredicateComparator();
            Collections.sort(predicateDTOs, criteriaPredicateComparator);
            for (PredicateInterface item : criteriaImpl.getPredicateDTOs()) {
                predicates.add(exportPredicate(item));
            }
        } else {
            throw new IllegalArgumentException("criteriaImpl is null!");
        }
        return result;
    }

    /**
     * Export the supplied implementation of the PredicateInterface.
     *
     * @param predicateImpl
     * @return
     */
    public static Predicate exportPredicate(PredicateInterface predicateImpl) {
        final String METHODNAME = "exportPredicate ";
        Predicate result;
        if (predicateImpl != null) {
            result = new Predicate();
            result.setId(predicateImpl.getPredicateId());
            result.setDescription(predicateImpl.getDescription());
            if (predicateImpl.getPredicateType() != null) {
                result.setPredicateType(PredicateTypeEnum.fromValue(predicateImpl.getPredicateType().name()));
            }
            result.setOrder(predicateImpl.getPredicateOrder());
            result.setLabel(predicateImpl.getLabel());
            if (predicateImpl.getPredicateConjunction() != null) {
                result.setPredicateConjunction(PredicateConjunctionEnum.fromValue(predicateImpl.getPredicateConjunction().name()));
            }

            // this could lead to recursion
            CriteriaDTO predicateCriteriaDTO = predicateImpl.getPredicateCriteriaDTO();
            if (predicateCriteriaDTO != null) {
                // TODO: somehow avoid recursion
                result.setPredicateCriterion(exportCriteria(predicateCriteriaDTO));
            }

            // add the parts
            List<PredicatePart> predicateParts = result.getPredicateParts();
            List<? extends BasePredicatePartDTO> predicatePartDTOs = predicateImpl.getPredicatePartDTOs();
            CriteriaPredicatePartComparator criteriaPredicatePartComparator = new CriteriaPredicatePartComparator();
            Collections.sort(predicatePartDTOs, criteriaPredicatePartComparator);
            for (PredicatePartInterface item : predicatePartDTOs) {
                predicateParts.add(exportPredicatePart(item));
            }

            // add the predicates if this is a group predicate
            List<Predicate> groupPredicates = result.getPredicates();
            List<? extends BasePredicateDTO> predicateDTOs = predicateImpl.getPredicateDTOs();
            CriteriaPredicateComparator criteriaPredicateComparator = new CriteriaPredicateComparator();
            Collections.sort(predicateDTOs, criteriaPredicateComparator);
            for (PredicateInterface item : predicateDTOs) {
                groupPredicates.add(exportPredicate(item));
            }

        } else {
            throw new IllegalArgumentException("predicateImpl is null!");
        }
        return result;
    }

    /**
     * Export the supplied implementation of the PredicatePartInterface.
     *
     * @param predicatePartImpl
     * @return
     */
    public static PredicatePart exportPredicatePart(PredicatePartInterface predicatePartImpl) {
        final String METHODNAME = "exportPredicatePart ";
        PredicatePart result;
        if (predicatePartImpl != null) {
            result = new PredicatePart();
            result.setAlias(predicatePartImpl.getPartAlias());
            if (predicatePartImpl.getConceptSelectionType() != null) {
                result.setConceptSelectionType(ConceptSelectionTypeEnum.fromValue(predicatePartImpl.getConceptSelectionType().name()));
            }
            result.setDataInputBoolean(predicatePartImpl.isDataInputBoolean());
            if (predicatePartImpl.getDataInputClassType() != null) {
                result.setDataInputClassType(DataModelClassTypeEnum.fromValue(predicatePartImpl.getDataInputClassType().name()));
            }
            result.setDataInputDate1(predicatePartImpl.getDataInputDate1());
            result.setDataInputDate2(predicatePartImpl.getDataInputDate2());
            result.setDataInputNumeric(predicatePartImpl.getDataInputNumeric());
            if (predicatePartImpl.getCriteriaDataTemplateRelNodeDTO() != null) {
                result.setDataModelElementNodePath(predicatePartImpl.getCriteriaDataTemplateRelNodeDTO().getNodePath());
            }
            result.setId(predicatePartImpl.getPartId());
            result.setIdExtension(predicatePartImpl.getDefaultIdentifierExtension());
            result.setIdRoot(predicatePartImpl.getDefaultIdentifierRoot());
            result.setLabel(predicatePartImpl.getLabel());
            result.setOrder(predicatePartImpl.getPredicatePartOrder());
            result.setOverridable(predicatePartImpl.isOverridable());
            if (predicatePartImpl.getPartType() != null) {
                result.setPartType(PredicatePartTypeEnum.fromValue(predicatePartImpl.getPartType().name()));
            }
            result.setResource(exportResource(predicatePartImpl.getCriteriaResourceDTO()));
            if (result.getResource() != null && result.getResource().getCriteriaResourceType() != null) {
                result.setResourceType(result.getResource().getCriteriaResourceType());
            }
            result.setSelectedResourceParameter(exportResourceParameter(predicatePartImpl.getCriteriaResourceParamDTO()));
            result.setText(predicatePartImpl.getText());

            result.setFunctionEnd(predicatePartImpl.isFunctionEnd());
            result.setParameterEnd(predicatePartImpl.isParameterEnd());

            List<PredicatePartConceptSelection> partSelections = result.getPartSelections();
            List<? extends BasePredicatePartConceptDTO> predicatePartConceptDTOs = predicatePartImpl.getPredicatePartConceptDTOs();
            for (PredicatePartConceptInterface item : predicatePartConceptDTOs) {
                partSelections.add(exportPredicatePartConceptSelection(item));
            }
        } else {
            throw new IllegalArgumentException("predicateImpl is null!");
        }
        return result;
    }

    /**
     * Export the supplied implementation of the PredicatePartConceptInterface.
     *
     * @param predicatePartConceptImpl
     * @return
     */
    public static PredicatePartConceptSelection exportPredicatePartConceptSelection(PredicatePartConceptInterface predicatePartConceptImpl) {
        final String METHODNAME = "exportPredicatePartConceptSelection ";
        PredicatePartConceptSelection result;
        if (predicatePartConceptImpl != null) {
            result = new PredicatePartConceptSelection();
            CdsCodeDTO cdsCodeDTO = predicatePartConceptImpl.getCdsCodeDTO();
            if (cdsCodeDTO != null) {
                result.setId(cdsCodeDTO.getCodeId());
                result.setCode(cdsCodeDTO.getCode());
                result.setDisplayName(cdsCodeDTO.getDisplayName());
                result.setCodeSystemId(cdsCodeDTO.getCodeSystemId());
                result.setCodeSystemOid(cdsCodeDTO.getCodeSystem());
                result.setCodeSystemName(cdsCodeDTO.getCodeSystemName());
            }
            OpenCdsConceptDTO openCdsConceptDTO = predicatePartConceptImpl.getOpenCdsConceptDTO();
            if (openCdsConceptDTO != null) {
                result.setId(openCdsConceptDTO.getCodeId());
                result.setCode(openCdsConceptDTO.getCode());
                result.setDisplayName(openCdsConceptDTO.getDisplayName());
                List<String> conceptDeterminationMethods = result.getConceptDeterminationMethods();
                for (OpenCdsConceptRelDTO item : openCdsConceptDTO.getOpenCdsConceptRelDTOs()) {
                    if (item.getConceptDeterminationMethodDTO() != null
                            && !conceptDeterminationMethods.contains(item.getConceptDeterminationMethodDTO().getCode())) {
                        conceptDeterminationMethods.add(item.getConceptDeterminationMethodDTO().getCode());
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("predicatePartConceptImpl is null!");
        }
        return result;
    }

    /**
     * Export the supplied CriteriaResourceDTO.
     *
     * @param criteriaResourceDTO
     * @return
     */
    public static Resource exportResource(CriteriaResourceDTO criteriaResourceDTO) {
        final String METHODNAME = "exportResource ";
        Resource result;
        if (criteriaResourceDTO != null) {
            result = new Resource();
            if (criteriaResourceDTO.getResourceType() != null) {
                result.setCriteriaResourceType(CriteriaResourceTypeEnum.fromValue(criteriaResourceDTO.getResourceType().name()));
            }
            result.setDescription(criteriaResourceDTO.getDescription());
            result.setName(criteriaResourceDTO.getName());
            result.setLabel(criteriaResourceDTO.getLabel());
            result.setId(criteriaResourceDTO.getResourceId());
        } else {
            result = null;
            logger.info(METHODNAME, "criteriaResourceDTO is null!");
        }
        return result;
    }

    /**
     * Export the supplied CriteriaResourceParamDTO.
     *
     * @param criteriaResourceParamDTO
     * @return
     */
    public static ResourceParameter exportResourceParameter(CriteriaResourceParamDTO criteriaResourceParamDTO) {
        final String METHODNAME = "exportResource ";
        ResourceParameter result;
        if (criteriaResourceParamDTO != null) {
            result = new ResourceParameter();
            if (criteriaResourceParamDTO.getClassType() != null) {
                result.setClassType(DataModelClassTypeEnum.fromValue(criteriaResourceParamDTO.getClassType().name()));
            }
            result.setDescription(criteriaResourceParamDTO.getDescription());
            result.setId(criteriaResourceParamDTO.getParamId());
            result.setName(criteriaResourceParamDTO.getName());
        } else {
            result = null;
            logger.info(METHODNAME, "criteriaResourceParamDTO is null!");
        }
        return result;
    }

    public static void mapClinicalStatements(
            CriteriaDTO criteriaDTO,
            BaseSourcePredicateDTO baseSourcePredicateDTO,
            Map<CriteriaDataTemplateRelDTO, Map<DetectedModelElementType, List<Object>>> clinicalStatementMap)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "mapClinicalStatements ";

        if (criteriaDTO == null) {
            throw new IllegalArgumentException(logger.error(METHODNAME, "criteriaDTO is null!"));
        }
        if (baseSourcePredicateDTO == null) {
            throw new IllegalArgumentException(logger.error(METHODNAME, "testCaseCriteriaPredicateDTO is null!"));
        }
        if (clinicalStatementMap == null) {
            throw new IllegalArgumentException(logger.error(METHODNAME, "clinicalStatementMap is null!"));
        }

        CriteriaPredicateDTO sourcePredicateDTO = baseSourcePredicateDTO.getSourcePredicateDTO();
        if (sourcePredicateDTO == null) {
            throw new IllegalArgumentException(logger.error(METHODNAME, "sourcePredicateDTO is null!"));
        }

        CriteriaPredicateType predicateType = sourcePredicateDTO.getPredicateType();
        logger.debug(METHODNAME, "predicateType=", predicateType);

        if (predicateType == CriteriaPredicateType.Predicate) {
            logger.debug(METHODNAME, "========================================================================");
            logger.debug(METHODNAME, "baseSourcePredicateDTO.getLabel()=", baseSourcePredicateDTO.getLabel());
            Map<DetectedModelElementType, List<Object>> detectedModelElementTypeMap = null;
            DetectedModelElementType detectedModelElementType = null;
            List<? extends BasePredicatePartConceptDTO> concepts = null;
            PQ quantity = null;
            IVLTS time = null;
            List<BasePredicateSourcePartDTO> basePredicatePartDTOs = (List) baseSourcePredicateDTO.getPredicatePartDTOs();
            Collections.sort(basePredicatePartDTOs, predicatePartComparator);

            for (BasePredicateSourcePartDTO basePredicateSourcePartDTO : basePredicatePartDTOs) {
                if (basePredicateSourcePartDTO != null) {
                    logger.debug(METHODNAME, "------------------------------------------------------------------------");
                    logger.debug(METHODNAME, "basePredicateSourcePartDTO.getLabel()=", basePredicateSourcePartDTO.getLabel());
                    CriteriaPredicatePartDTO sourcePredicatePartDTO = basePredicateSourcePartDTO.getSourcePredicatePartDTO();
                    if (sourcePredicatePartDTO != null) {
                        PredicatePartType partType = sourcePredicatePartDTO.getPartType();
                        logger.debug(METHODNAME, "partType=", partType);
                        logger.debug(METHODNAME, "detectedModelElementTypeMap=", detectedModelElementTypeMap);
                        logger.debug(METHODNAME, "clinicalStatementMap=", clinicalStatementMap);
                        if (partType == PredicatePartType.ModelElement) {
                            logger.debug(METHODNAME, "processing ModelElement type!");
                            CriteriaDataTemplateRelNodeDTO criteriaDataTemplateRelNodeDTO = sourcePredicatePartDTO.getCriteriaDataTemplateRelNodeDTO();
                            logger.debug(METHODNAME, "criteriaDataTemplateRelNodeDTO=", criteriaDataTemplateRelNodeDTO);
                            if (criteriaDataTemplateRelNodeDTO != null) {
                                logger.debug(METHODNAME, "criteriaDataTemplateRelNodeDTO.getNodePath()=", criteriaDataTemplateRelNodeDTO.getNodePath());
                                if (criteriaDataTemplateRelNodeDTO.getNodePath() != null) {
                                    detectedModelElementTypeMap = getCriteriaDataTemplateModelElementTypeMap(criteriaDataTemplateRelNodeDTO, clinicalStatementMap);
                                    logger.debug(METHODNAME, "detectedModelElementTypeMap=", detectedModelElementTypeMap);

                                    DataTemplateNodeRelDTO dataTemplateNodeRelDTO = criteriaDataTemplateRelNodeDTO.getDataTemplateNodeRelDTO();

                                    if (dataTemplateNodeRelDTO != null) {
                                        logger.debug(METHODNAME, "dataTemplateNodeRelDTO.getNodePath()=", dataTemplateNodeRelDTO.getNodePath());

                                        if (detectedModelElementTypeMap == null) {
                                            if (dataTemplateNodeRelDTO.getNodePath() != null) {

                                                detectedModelElementTypeMap = getDataTemplateModelElementTypeMap(dataTemplateNodeRelDTO, clinicalStatementMap);
                                                logger.debug(METHODNAME, "detectedModelElementTypeMap=", detectedModelElementTypeMap);

                                                logger.debug(METHODNAME, "found ModelElement: ", dataTemplateNodeRelDTO.getNodePath(), " - looking for root class");
                                                logger.debug(METHODNAME, "criteriaDTO.getCriteriaDataTemplateRelDTOs(): ", criteriaDTO.getCriteriaDataTemplateRelDTOs());
                                            } else {
                                                logger.error(METHODNAME, "dataTemplateNodeRelDTO.getNodePath() is null!");
                                            }
                                        }

                                        detectedModelElementType = geDetectedModelElementType(criteriaDTO, criteriaDataTemplateRelNodeDTO);

                                        if (detectedModelElementType != null) {
                                            logger.debug(METHODNAME, "found match for detectedModelElementType: ", detectedModelElementType);
                                        } else {
                                            DataModelClassNodeDTO dataModelClassNodeDTO = dataTemplateNodeRelDTO.getDataModelClassNodeDTO();
                                            logger.debug(METHODNAME, "dataModelClassNodeDTO.getName()=", dataModelClassNodeDTO.getName());
                                            DataModelClassType classType = dataModelClassNodeDTO.getClassType();
                                            logger.debug(METHODNAME, "classType=", classType);
                                            DataModelClassDTO dataModelClassDTO = dataModelClassNodeDTO.getDataModelClassDTO();
                                            logger.debug(METHODNAME, "dataModelClassDTO=", dataModelClassDTO.getName());
                                        }

                                    } else {
                                        logger.error(METHODNAME, "dataTemplateNodeRelDTO is null!");
                                    }

                                } else {
                                    // if it is a model element it should have a node path
                                    logger.error(METHODNAME, "criteriaDataTemplateRelNodeDTO.getNodePath() is null!");
                                }
                            } else {
                                logger.debug(METHODNAME, "criteriaDataTemplateRelNodeDTO is null!");
                            }
                        } else if (partType == PredicatePartType.DataInput) {
                            logger.debug(METHODNAME, "processing DataInput type!");
                            DataModelClassType dataInputClassType = sourcePredicatePartDTO.getDataInputClassType();
                            logger.debug(METHODNAME, "dataInputClassType=", dataInputClassType);
                            if (dataInputClassType != null) {
                                switch (dataInputClassType) {
                                    case Quantity:
                                        quantity = new PQ();
                                        quantity.setValue(basePredicateSourcePartDTO.getDataInputNumeric() != null ? basePredicateSourcePartDTO.getDataInputNumeric().doubleValue() : 0);
                                        quantity.setUnit(basePredicateSourcePartDTO.getText());
                                        break;
                                    case Date:
                                        time = new IVLTS();
                                        String isoDateFormat = DateUtils.getISODateFormat(basePredicateSourcePartDTO.getDataInputDate1());
                                        time.setLow(isoDateFormat);
                                        time.setHigh(isoDateFormat);
                                        break;
                                    case DateRange:
                                        time = new IVLTS();
                                        time.setLow(DateUtils.getISODateFormat(basePredicateSourcePartDTO.getDataInputDate1()));
                                        time.setHigh(DateUtils.getISODateFormat(basePredicateSourcePartDTO.getDataInputDate2()));
                                        break;
                                    case Concept:
                                        concepts = basePredicateSourcePartDTO.getPredicatePartConceptDTOs();
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                logger.debug(METHODNAME, "DataInput type but dataInputClassType is null!");
                            }
                        }
                    } else {
                        logger.warn(METHODNAME, "sourcePredicatePartDTO is null!");
                    }
                } else {
                    logger.warn(METHODNAME, "testCaseCriteriaPredicatePartDTO is null!");
                }
            }

            logger.debug(METHODNAME, "~~~~~~~~~~~~~~~~~~part iteration result~~~~~~~~~~~~~~~~~~~~~");
            logger.debug(METHODNAME, "detectedModelElementTypeMap=", detectedModelElementTypeMap);
            logger.debug(METHODNAME, "detectedModelElementType=", detectedModelElementType);
            logger.debug(METHODNAME, "quantity=", quantity);
            logger.debug(METHODNAME, "time=", time);
            logger.debug(METHODNAME, "concepts=", concepts);
            logger.debug(METHODNAME, "~~~~~~~~~~~~~~~~~~~~part iteration end~~~~~~~~~~~~~~~~~~~~~~");

            if (logger.isDebugEnabled()) {
                logResult(concepts, quantity, time);
            }
            if (detectedModelElementTypeMap != null && detectedModelElementType != null && ((concepts != null && !concepts.isEmpty()) || quantity != null || time != null)) {
                int i = 0;
                if (concepts != null && !concepts.isEmpty()) {
                    i++;
                }
                if (quantity != null) {
                    i++;
                }
                if (time != null) {
                    i++;
                }
                if (i > 1) {
                    throw new ValidationException("Only one item may be valued: " + concepts + " - " + quantity + " - " + time);
                }
                List<Object> mapValue = detectedModelElementTypeMap.get(detectedModelElementType);
                if (mapValue == null) {
                    mapValue = new ArrayList<Object>();
                    detectedModelElementTypeMap.put(detectedModelElementType, mapValue);
                }
                if (quantity != null) {
                    mapValue.add(quantity);
                    logger.debug(METHODNAME, "detectedModelElementType=", detectedModelElementType, "; quantity=", quantity);
                } else if (time != null) {
                    mapValue.add(time);
                    logger.debug(METHODNAME, "detectedModelElementType=", detectedModelElementType, "; time=", time);
                } else if (concepts != null) {
                    for (BasePredicatePartConceptDTO item : concepts) {
                        CdsCodeDTO cdsCodeDTO = item.getCdsCodeDTO();
                        OpenCdsConceptDTO openCdsConceptDTO = item.getOpenCdsConceptDTO();
                        if (cdsCodeDTO != null) {
                            mapValue.add(cdsCodeDTO);
                            logger.debug(METHODNAME, "detectedModelElementType=", detectedModelElementType, "; cdsCodeDTO=", cdsCodeDTO);
                        } else if (openCdsConceptDTO != null) {
                            mapValue.add(openCdsConceptDTO);
                            logger.debug(METHODNAME, "detectedModelElementType=", detectedModelElementType, "; openCdsConceptDTO=", openCdsConceptDTO);
                        } else {
                            logger.warn(METHODNAME, "BasePredicatePartConceptDTO item has no code or concept selected!");
                        }
                    }
                }
            }
        } else if (predicateType == CriteriaPredicateType.PredicateGroup) {
            List<BaseSourcePredicateDTO> predicateDTOs = (List) baseSourcePredicateDTO.getPredicateDTOs();
            logger.debug(METHODNAME, "processing predicate group: ", predicateDTOs);
            for (BaseSourcePredicateDTO item : predicateDTOs) {
                mapClinicalStatements(criteriaDTO, item, clinicalStatementMap);
            }
        }
    }

    private static Map<DetectedModelElementType, List<Object>> getCriteriaDataTemplateModelElementTypeMap(
            CriteriaDataTemplateRelNodeDTO criteriaDataTemplateRelNodeDTO,
            Map<CriteriaDataTemplateRelDTO, Map<DetectedModelElementType, List<Object>>> clinicalStatementMap) {
        final String METHODNAME = "getCriteriaDataTemplateModelElementTypeMap ";
        Map<DetectedModelElementType, List<Object>> detectedModelElementTypeMap = null;
        for (CriteriaDataTemplateRelDTO criteriaDataTemplateRelDTO : clinicalStatementMap.keySet()) {
            logger.debug(METHODNAME, "Comparing1: ", criteriaDataTemplateRelNodeDTO.getNodePath(), " with ", criteriaDataTemplateRelDTO.getLabel() + ".");
            if (criteriaDataTemplateRelNodeDTO.getNodePath().startsWith(criteriaDataTemplateRelDTO.getLabel() + ".")) {
                detectedModelElementTypeMap = clinicalStatementMap.get(criteriaDataTemplateRelDTO);
                break;
            }
        }
        return detectedModelElementTypeMap;
    }

    private static Map<DetectedModelElementType, List<Object>> getDataTemplateModelElementTypeMap(DataTemplateNodeRelDTO dataTemplateNodeRelDTO,
            Map<CriteriaDataTemplateRelDTO, Map<DetectedModelElementType, List<Object>>> clinicalStatementMap) {
        final String METHODNAME = "getDataTemplateModelElementTypeMap ";
        Map<DetectedModelElementType, List<Object>> detectedModelElementTypeMap = null;

        for (CriteriaDataTemplateRelDTO criteriaDataTemplateRelDTO : clinicalStatementMap.keySet()) {
            logger.debug(METHODNAME, "Comparing2: ", dataTemplateNodeRelDTO.getNodePath(), " with ", criteriaDataTemplateRelDTO.getLabel() + ".");
            if (dataTemplateNodeRelDTO.getNodePath().startsWith(criteriaDataTemplateRelDTO.getLabel() + ".")) {
                detectedModelElementTypeMap = clinicalStatementMap.get(criteriaDataTemplateRelDTO);
                break;
            }
        }
        return detectedModelElementTypeMap;
    }

    /**
     * Determine the type of the CriteriaDataTemplateRelNodeDTO but looking it
     * up in the DetectedModelElementType enumeration.
     *
     * @param criteriaDTO
     * @param criteriaDataTemplateRelNodeDTO
     * @return
     */
    private static DetectedModelElementType geDetectedModelElementType(CriteriaDTO criteriaDTO,
            CriteriaDataTemplateRelNodeDTO criteriaDataTemplateRelNodeDTO) {
        final String METHODNAME = "geDetectedModelElementType ";
        DetectedModelElementType result = null;
        DataTemplateNodeRelDTO dataTemplateNodeRelDTO = criteriaDataTemplateRelNodeDTO.getDataTemplateNodeRelDTO();

        for (CriteriaDataTemplateRelDTO criteriaDataTemplateRelDTO : criteriaDTO.getCriteriaDataTemplateRelDTOs()) {
            DataTemplateDTO dataTemplateDTO = criteriaDataTemplateRelDTO.getDataTemplateDTO();
            DataModelClassDTO rootClass = dataTemplateDTO.getRootClass();
            String className = rootClass.getClassName();
            logger.debug(METHODNAME, "Considering: ", className);
            logger.debug(METHODNAME, "criteriaDataTemplateRelDTO.getLabel()=", criteriaDataTemplateRelDTO.getLabel());
            List<CriteriaDataTemplateRelNodeDTO> criteriaDataTemplateRelNodeDTOs = criteriaDataTemplateRelDTO.getCriteriaDataTemplateRelNodeDTOs();
            logger.debug(METHODNAME, "criteriaDataTemplateRelNodeDTO.getLabel()=", criteriaDataTemplateRelNodeDTO.getLabel());
            logger.debug(METHODNAME, "criteriaDataTemplateRelNodeDTOs.contains(criteriaDataTemplateRelNodeDTO)=", criteriaDataTemplateRelNodeDTOs.contains(criteriaDataTemplateRelNodeDTO));
            if (criteriaDataTemplateRelNodeDTOs.contains(criteriaDataTemplateRelNodeDTO)) {
                for (DetectedModelElementType modelElementType : DetectedModelElementType.values()) {
                    logger.debug(METHODNAME, "comparing root class ", className, " to ", modelElementType.getClinicalStatementClass().getSimpleName(),
                            " and ", dataTemplateNodeRelDTO.getNodePath(), " to ", modelElementType.getMatchString());
                    if (className.contains(modelElementType.getClinicalStatementClass().getSimpleName())
                            && dataTemplateNodeRelDTO.getNodePath().contains(modelElementType.getMatchString())) {
                        result = modelElementType;
                        break;
                    }
                }
            }
        }
        if (result == null) {
            logger.error(METHODNAME, "DetectedModelElementType null: check to see if ",
                    dataTemplateNodeRelDTO.getNodePath(),
                    " has been configured in the enumeration DetectedModelElementType");
        }
        return result;
    }

    public static void cleanUpPartConceptSelections(BasePredicatePartDTO baseDTO) {
        final String METHODNAME = "cleanUpPartConceptSelections ";
        logger.trace(METHODNAME, "baseDTO.getDataInputClassType()=", baseDTO.getDataInputClassType());
        logger.trace(METHODNAME, "baseDTO.getConceptSelectionType()=", baseDTO.getConceptSelectionType());
        if (baseDTO.getDataInputClassType() == DataModelClassType.Concept) {
            if (baseDTO.getConceptSelectionType() == ConceptSelectionType.Concept) {
                CriteriaUtils.deleteSelections(baseDTO.getPredicatePartConceptDTOs(), ConceptSelectionType.Code);
            } else if (baseDTO.getConceptSelectionType() == ConceptSelectionType.Code) {
                CriteriaUtils.deleteSelections(baseDTO.getPredicatePartConceptDTOs(), ConceptSelectionType.Concept);
            }
        }
    }

    public static void deleteSelections(List<? extends BasePredicatePartConceptDTO> predicatePartConceptDTOs, ConceptSelectionType conceptSelectionType) {
        for (BasePredicatePartConceptDTO item : predicatePartConceptDTOs) {
            if ((!item.isNew() && conceptSelectionType == ConceptSelectionType.Code && item.getCdsCodeDTO() != null && item.getCdsCodeDTO().getCodeId() != null)
                    || (!item.isNew() && conceptSelectionType == ConceptSelectionType.Concept && item.getOpenCdsConceptDTO() != null && item.getOpenCdsConceptDTO().getCodeId() != null)) {
                item.delete(true);
            }
        }
    }

    public static String getPartLabel(PredicatePartInterface part) {
        String result;

        // if the art is null return an error string
        if (part == null) {
            result = "ERROR: part is null!";
            return result;
        }
        String partAlias = part.getPartAlias();
        String text = part.getText();
        CriteriaDataTemplateRelNodeDTO criteriaDataTemplateRelNodeDTO = part.getCriteriaDataTemplateRelNodeDTO();
        CriteriaResourceDTO criteriaResourceDTO = part.getCriteriaResourceDTO();
        DataModelClassType dataInputClassType = part.getDataInputClassType();
        ConceptSelectionType conceptSelectionType = part.getConceptSelectionType();
        String concatenatedConceptSelectionType = part.getConcatenatedConceptSelectionType("code");
        Date dataInputDate1 = part.getDataInputDate1();
        Date dataInputDate2 = part.getDataInputDate2();
        String defaultIdentifierRoot = part.getDefaultIdentifierRoot();
        String defaultIdentifierExtension = part.getDefaultIdentifierExtension();
        BigDecimal dataInputNumeric = part.getDataInputNumeric();
        if (StringUtils.isEmpty(partAlias)) {
            switch (part.getPartType()) {
                case Text:
                    if (!StringUtils.isEmpty(text)) {
                        result = text;
                    } else {
                        result = "(Text is empty!)";
                    }
                    break;
                case ModelElement:
                    if (criteriaDataTemplateRelNodeDTO != null) {
                        result = criteriaDataTemplateRelNodeDTO.getLabel();
                    } else {
                        result = "(CriteriaDataTemplateRelNodeDTO not selected!)";
                    }
                    break;
                case Resource:
                    if (criteriaResourceDTO != null) {
                        if (!part.isFunctionEnd()) {
                            result = criteriaResourceDTO.getLabel();
                        } else {
                            result = "";
                        }
                    } else {
                        result = "(CriteriaResourceDTO not selected!)";
                    }
                    break;
                case DataInput:
                    result = dataInputClassType != null ? (dataInputClassType.name().toLowerCase() + " input field: ") : "dataInputClassType not set! ";
                    if (dataInputClassType != null) {
                        switch (dataInputClassType) {
                            case Boolean:
                                result += part.isDataInputBoolean();
                                break;
                            case Concept:
                                result += (conceptSelectionType != null
                                        ? conceptSelectionType
                                        + " - "
                                        + (concatenatedConceptSelectionType != null
                                                ? concatenatedConceptSelectionType
                                                : "unselected")
                                        : "");
                                break;
                            case Date:
                                result += (dataInputDate1 != null ? dataInputDate1 : "unset");
                                break;
                            case DateRange:
                                result += "low - "
                                        + (dataInputDate1 != null ? dataInputDate1 : "unset")
                                        + "; high - "
                                        + (dataInputDate2 != null ? dataInputDate2 : "unset");
                                break;
                            case Identifier:
                                result += "root - "
                                        + (defaultIdentifierRoot != null ? defaultIdentifierRoot : "unset")
                                        + "; extension - "
                                        + (defaultIdentifierExtension != null ? defaultIdentifierExtension : "unset");
                                break;
                            case Numeric:
                                result += (dataInputNumeric != null ? dataInputNumeric : "unset");
                                break;
                            case Quantity:
                                result += "qty -"
                                        + (dataInputNumeric != null ? dataInputNumeric : "unset")
                                        + "; unit - "
                                        + (text != null ? text : "unset");
                                break;
                            case String:
                                result += (text != null ? text : "unset");
                                break;
                            default:
                                result = "not implemented!";
                        }
                    } else {
                        result = "error: dataInput class not set";
                    }
                    break;
                default:
                    result = "not implemented!";
            }
        } else {
            result = partAlias;
        }
        if (part.isFunctionEnd()) {
            result += ")";
        }
        if (part.isParameterEnd()) {
            result += ", ";
        }
        return result;
    }

    private static void logResult(List<? extends BasePredicatePartConceptDTO> concepts, PQ quantity, IVLTS time) {
        final String METHODNAME = "logResult ";
        if (concepts == null) {
            logger.debug(METHODNAME, "found concepts: none");
        } else {
            for (BasePredicatePartConceptDTO item : concepts) {
                CdsCodeDTO cdsCodeDTO = item.getCdsCodeDTO();
                OpenCdsConceptDTO openCdsConceptDTO = item.getOpenCdsConceptDTO();
                if (cdsCodeDTO != null) {
                    logger.debug(METHODNAME, "found code item: ", cdsCodeDTO.getLabel());
                } else if (openCdsConceptDTO != null) {
                    logger.debug(METHODNAME, "found concept item: ", openCdsConceptDTO.getLabel());
                } else {
                    logger.warn(METHODNAME, "found item with no concept or code!");
                }
            }
        }
        if (quantity == null) {
            logger.debug(METHODNAME, "found quantity: none");
        } else {
            logger.debug(METHODNAME, "found quantity: ", quantity.getValue(), " ", quantity.getUnit());
        }
        if (time == null) {
            logger.debug(METHODNAME, "found time: none");
        } else {
            logger.debug(METHODNAME, "found time: ", time.getHigh(), " ", time.getLow());
        }
    }

}
