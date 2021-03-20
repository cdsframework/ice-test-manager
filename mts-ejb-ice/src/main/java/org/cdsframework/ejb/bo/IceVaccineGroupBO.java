/**
 * The MTS ice EJB project is the base framework for the CDS Framework Middle Tier Service.
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
package org.cdsframework.ejb.bo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.cdsframework.base.BaseBO;
import org.cdsframework.cds.util.MarshalUtils;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsCodeOpenCdsConceptRelDTO;
import org.cdsframework.dto.ConceptDeterminationMethodDTO;
import org.cdsframework.dto.IceDiseaseDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
import org.cdsframework.dto.IceVaccineGroupVaccineRelDTO;
import org.cdsframework.dto.IceVaccineGroupVersionRelDTO;
import org.cdsframework.dto.OpenCdsConceptDTO;
import org.cdsframework.dto.OpenCdsConceptRelDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.ejb.local.PropertyMGRLocal;
import org.cdsframework.enumeration.CoreErrorCode;
import org.cdsframework.enumeration.Operation;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.CdsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.factory.CdsCodeDTOObjectFactory;
import org.cdsframework.group.FindAll;
import org.cdsframework.group.SimpleExchange;
import org.cdsframework.util.BrokenRule;
import org.cdsframework.util.FileUtils;
import org.cdsframework.util.support.data.ice.vaccinegroup.IceVaccineGroupSpecificationFile;
import org.opencds.vmr.v1_0.schema.CD;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
public class IceVaccineGroupBO extends BaseBO<IceVaccineGroupDTO> {

    @EJB
    private PropertyMGRLocal propertyMGRLocal;
    @EJB
    private OpenCdsConceptBO openCdsConceptBO;
    @EJB
    private ConceptDeterminationMethodBO conceptDeterminationMethodBO;
    @EJB
    private IceVaccineGroupVaccineRelBO iceVaccineGroupVaccineRelBO;

    @Override
    protected void processEnd(
            IceVaccineGroupDTO baseDTO,
            List<Class> childClassDTOs,
            Operation operation,
            Class queryClass,
            SessionDTO sessionDTO,
            PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException {
        // add or updates aren't refreshed - tack on the vaccine relationship list
        if (operation == Operation.ADD || operation == Operation.UPDATE) {
            IceVaccineGroupVaccineRelDTO iceVaccineGroupVaccineRelDTO = new IceVaccineGroupVaccineRelDTO();
            CdsCodeDTO vaccineGroup = new CdsCodeDTO();
            vaccineGroup.setCodeId(baseDTO.getGroupId());
            iceVaccineGroupVaccineRelDTO.setVaccineGroup(vaccineGroup);
            List<IceVaccineGroupVaccineRelDTO> vaccineRelList = iceVaccineGroupVaccineRelBO.findByQueryListMain(
                    iceVaccineGroupVaccineRelDTO,
                    IceVaccineGroupVaccineRelDTO.VaccineByVersionDiseaseRelationship.class,
                    new ArrayList<Class>(),
                    sessionDTO,
                    propertyBagDTO);
            baseDTO.setChildrenDTOs(IceVaccineGroupVaccineRelDTO.VaccineByVersionDiseaseRelationship.class, (List) vaccineRelList);
        }
    }

    @Override
    public Map<String, byte[]> exportData(IceVaccineGroupDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "exportData ";
        if (queryClass == SimpleExchange.class) {
            return simpleExchangeExport(baseDTO, queryClass, sessionDTO, propertyBagDTO);
        } else {
            throw new ValidationException(CoreErrorCode.ParameterCanNotBeNull,
                    logger.error(METHODNAME, "Unsupported queryClass: ", queryClass));
        }
    }

    private Map<String, byte[]> simpleExchangeExport(IceVaccineGroupDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "simpleExchangeExport ";
        //TODO: add support for a version specific export.
        Map<String, byte[]> fileMap = new HashMap<String, byte[]>();
//        String defaultConceptDeterminationMethodId = propertyMGRLocal.get("CDS_DEFAULT_CONCEPT_DETERMINATION_METHOD", String.class);
//        ConceptDeterminationMethodDTO defaultConceptDeterminationMethodDTO = new ConceptDeterminationMethodDTO();
//        defaultConceptDeterminationMethodDTO.setCodeId(defaultConceptDeterminationMethodId);
//        defaultConceptDeterminationMethodDTO = conceptDeterminationMethodBO.findByPrimaryKeyMain(defaultConceptDeterminationMethodDTO, new ArrayList<Class>(), sessionDTO, new PropertyBagDTO());
        if (baseDTO != null) {
            List<IceVaccineGroupDTO> iceVaccineGroupDTOs = new ArrayList<IceVaccineGroupDTO>();
            if ("ALL".equalsIgnoreCase(baseDTO.getVaccineGroup().getCodeId())) {
                List<IceVaccineGroupDTO> findByQueryList = findByQueryListMain(baseDTO, FindAll.class, getDtoChildClasses(), sessionDTO, new PropertyBagDTO());
                if (findByQueryList != null) {
                    iceVaccineGroupDTOs.addAll(findByQueryList);
                } else {
                    logger.error(METHODNAME, "iceVaccineGroupDTOs is null!");
                }
            } else {
                IceVaccineGroupDTO iceVaccineGroupDTO = findByPrimaryKeyMain(baseDTO, getDtoChildClasses(), sessionDTO, new PropertyBagDTO());
                if (iceVaccineGroupDTO != null) {
                    iceVaccineGroupDTOs.add(iceVaccineGroupDTO);
                } else {
                    logger.error(METHODNAME, "iceVaccineGroupDTO is null!");
                }
            }
            logger.info("Found iceVaccineGroupDTOs: ", iceVaccineGroupDTOs.size());
            try {
                for (IceVaccineGroupDTO iceVaccineGroupDTO : iceVaccineGroupDTOs) {
                    if (iceVaccineGroupDTO != null) {
                        CdsCodeDTO cdsCodeDTO = iceVaccineGroupDTO.getVaccineGroup();
                        if (cdsCodeDTO != null) {
                            IceVaccineGroupSpecificationFile iceVaccineGroupSpecificationFile = new IceVaccineGroupSpecificationFile();

                            // set vaccineGroup group
                            CD vaccineGroup = CdsCodeDTOObjectFactory.getCDFromCdsCodeDTO(cdsCodeDTO);
                            iceVaccineGroupSpecificationFile.setVaccineGroup(vaccineGroup);

                            // set priority
                            iceVaccineGroupSpecificationFile.setPriority(BigInteger.valueOf(iceVaccineGroupDTO.getPriority()));

                            // set versions
                            for (IceVaccineGroupVersionRelDTO iceVaccineGroupVersionRelDTO : iceVaccineGroupDTO.getIceVaccineGroupVersionRelDTOs()) {
                                iceVaccineGroupSpecificationFile.getCdsVersions().add(iceVaccineGroupVersionRelDTO.getCdsVersionDTO().getOpenCdsVersionIdentifier());
                            }

                            // set disease immunities
                            List<CD> diseaseImmunities = iceVaccineGroupSpecificationFile.getDiseaseImmunities();
                            for (IceDiseaseDTO item : iceVaccineGroupDTO.getRelatedDiseases()) {
                                CD disease = CdsCodeDTOObjectFactory.getCDFromCdsCodeDTO(item.getDiseaseCdsCodeDTO());
                                diseaseImmunities.add(disease);
                            }
                            // set vaccines
                            List<CD> relatedVaccines = iceVaccineGroupSpecificationFile.getRelatedVaccines();
                            for (CdsCodeDTO item : iceVaccineGroupDTO.getRelatedVaccines()) {
                                CD vaccine = CdsCodeDTOObjectFactory.getCDFromCdsCodeDTO(item);
                                relatedVaccines.add(vaccine);
                            }

                            List<CdsCodeOpenCdsConceptRelDTO> cdsCodeOpenCdsConceptRelDTOs = cdsCodeDTO.getCdsCodeOpenCdsConceptRelDTOs();
                            // set opencds concept relationships and primary OpenCDS concept
                            if (!cdsCodeOpenCdsConceptRelDTOs.isEmpty()) {
                                logger.info(METHODNAME, "looking for related opencds concepts: ", cdsCodeOpenCdsConceptRelDTOs);

                                List<OpenCdsConceptDTO> relatedOpenCdsConceptDTOs = new ArrayList<OpenCdsConceptDTO>();
                                List<ConceptDeterminationMethodDTO> relatedConceptDeterminationMethodDTOs = new ArrayList<ConceptDeterminationMethodDTO>();
//                                relatedConceptDeterminationMethodDTOs.add(defaultConceptDeterminationMethodDTO);

                                for (IceVaccineGroupVersionRelDTO iceVaccineGroupVersionRelDTO : iceVaccineGroupDTO.getIceVaccineGroupVersionRelDTOs()) {
                                    if (iceVaccineGroupVersionRelDTO != null
                                            && iceVaccineGroupVersionRelDTO.getCdsVersionDTO() != null
                                            && !iceVaccineGroupVersionRelDTO.getCdsVersionDTO().getCdsVersionConceptDeterminationMethodRelDTOs().isEmpty()) {
                                        for (ConceptDeterminationMethodDTO conceptDeterminationMethodDTO : iceVaccineGroupVersionRelDTO.getCdsVersionDTO().getConceptDeterminationMethodDTOs()) {
                                            relatedConceptDeterminationMethodDTOs.add(conceptDeterminationMethodDTO);
                                        }
                                    }
                                }

                                logger.info(METHODNAME, "related concept determination methods: ", relatedConceptDeterminationMethodDTOs);

                                // iterate over the vaccineGroup CdsCodeDTO relationships to OpenCdsConceptDTOs
                                // and build a list of OpenCdsConcptDTOs.
                                for (CdsCodeOpenCdsConceptRelDTO cdsCodeOpenCdsConceptRelDTO : cdsCodeOpenCdsConceptRelDTOs) {
                                    logger.info("Considering relation: ", cdsCodeOpenCdsConceptRelDTO);
                                    String openCdsCodeId = cdsCodeOpenCdsConceptRelDTO.getOpenCdsCodeId();
                                    OpenCdsConceptDTO openCdsConceptDTO = new OpenCdsConceptDTO();
                                    openCdsConceptDTO.setCodeId(openCdsCodeId);
                                    try {
                                        openCdsConceptDTO
                                                = openCdsConceptBO.findByPrimaryKeyMain(
                                                        openCdsConceptDTO,
                                                        openCdsConceptBO.getDtoChildClasses(),
                                                        sessionDTO,
                                                        new PropertyBagDTO());
                                    } catch (NotFoundException e) {
                                        continue;
                                    }
                                    // throw an exception if there are:
                                    //    - more than one related concepts
                                    //    - no related concepts
                                    if (openCdsConceptDTO.getOpenCdsConceptRelDTOs().isEmpty()) {
                                        throw new ValidationException(new BrokenRule(CoreErrorCode.ValueNotFound,
                                                "There is no concept mapping for vaccine group: "
                                                + cdsCodeDTO.getDisplayName()
                                                + " (" + cdsCodeDTO.getCode() + ")"));
                                    }
                                    if (openCdsConceptDTO.getOpenCdsConceptRelDTOs().size() > 1) {
                                        throw new ValidationException(new BrokenRule(CoreErrorCode.TooMany,
                                                "There are more than one concept mappings for vaccine group: "
                                                + cdsCodeDTO.getDisplayName()
                                                + " (" + cdsCodeDTO.getCode() + ")"));
                                    }
                                    for (OpenCdsConceptRelDTO openCdsConceptRelDTO : openCdsConceptDTO.getOpenCdsConceptRelDTOs()) {
                                        if (cdsCodeDTO.equals(openCdsConceptRelDTO.getCdsCodeDTO())
                                                && relatedConceptDeterminationMethodDTOs.contains(openCdsConceptRelDTO.getConceptDeterminationMethodDTO())) {
                                            if (!relatedOpenCdsConceptDTOs.contains(openCdsConceptDTO)) {
                                                relatedOpenCdsConceptDTOs.add(openCdsConceptDTO);
                                            }
                                            break;
                                        }
                                    }
                                }

                                if (iceVaccineGroupDTO.getPrimaryOpenCdsConceptDTO() != null) {
                                    OpenCdsConceptDTO primaryOpenCdsConceptDTO = iceVaccineGroupDTO.getPrimaryOpenCdsConceptDTO();
                                    CD primaryOpenCdsConcept = new CD();
                                    primaryOpenCdsConcept.setCode(primaryOpenCdsConceptDTO.getCode());
                                    primaryOpenCdsConcept.setDisplayName(primaryOpenCdsConceptDTO.getDisplayName());
                                    iceVaccineGroupSpecificationFile.setPrimaryOpenCdsConcept(primaryOpenCdsConcept);
                                }

                                // set opencds memberships
                                List<CD> openCdsMemberships = iceVaccineGroupSpecificationFile.getOpenCdsMemberships();
                                for (OpenCdsConceptDTO item : relatedOpenCdsConceptDTOs) {
                                    CD openCdsConcept = new CD();
                                    openCdsConcept.setCode(item.getCode());
                                    openCdsConcept.setDisplayName(item.getDisplayName());
                                    openCdsMemberships.add(openCdsConcept);
                                }
                            }
                            byte[] marshalledXml = MarshalUtils.marshalObject(iceVaccineGroupSpecificationFile);
                            fileMap.put(iceVaccineGroupDTO.getVaccineGroupCode() + ".xml", marshalledXml);
                        }
                    } else {
                        logger.error(METHODNAME, "iceVaccineGroupDTO is null!");
                    }

                }
                String xsd = FileUtils.getStringFromJarFile("iceVaccineGroupSpecificationFile.xsd");
                fileMap.put("vaccineGroupSpecificationFile.xsd", xsd.getBytes());
            } catch (CdsException e) {
                logger.error(e);
                throw new MtsException(e.getMessage());
            }
        } else {
            logger.error(METHODNAME, "baseDTO was null!");
        }
        return fileMap;
    }
}
