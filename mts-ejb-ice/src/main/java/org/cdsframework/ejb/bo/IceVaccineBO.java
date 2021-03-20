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
import org.cdsframework.dto.IceVaccineComponentDTO;
import org.cdsframework.dto.IceVaccineComponentDiseaseRelDTO;
import org.cdsframework.dto.IceVaccineComponentRelDTO;
import org.cdsframework.dto.IceVaccineDTO;
import org.cdsframework.dto.IceVaccineGroupVaccineRelDTO;
import org.cdsframework.dto.IceVaccineVersionRelDTO;
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
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.factory.CdsCodeDTOObjectFactory;
import org.cdsframework.group.FindAll;
import org.cdsframework.group.SimpleExchange;
import org.cdsframework.util.FileUtils;
import org.cdsframework.util.support.data.ice.vaccine.IceVaccineSpecificationFile;
import org.cdsframework.util.support.data.ice.vaccine.VaccineComponent;
import org.opencds.vmr.v1_0.schema.CD;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
public class IceVaccineBO extends BaseBO<IceVaccineDTO> {

    @EJB
    private IceVaccineComponentBO iceVaccineComponentBO;
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
            IceVaccineDTO baseDTO,
            List<Class> childClassDTOs,
            Operation operation,
            Class queryClass,
            SessionDTO sessionDTO,
            PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException {
        // add or updates aren't refreshed - tack on the vaccine group relationship list
        if (operation == Operation.ADD || operation == Operation.UPDATE) {
            IceVaccineGroupVaccineRelDTO iceVaccineGroupVaccineRelDTO = new IceVaccineGroupVaccineRelDTO();
            CdsCodeDTO vaccine = new CdsCodeDTO();
            vaccine.setCodeId(baseDTO.getVaccineId());
            iceVaccineGroupVaccineRelDTO.setVaccine(vaccine);
            List<IceVaccineGroupVaccineRelDTO> vaccineGroupRelList = iceVaccineGroupVaccineRelBO.findByQueryListMain(
                    iceVaccineGroupVaccineRelDTO,
                    IceVaccineGroupVaccineRelDTO.VaccineGroupByVersionDiseaseRelationship.class,
                    new ArrayList<Class>(),
                    sessionDTO,
                    propertyBagDTO);
            baseDTO.setChildrenDTOs(IceVaccineGroupVaccineRelDTO.VaccineGroupByVersionDiseaseRelationship.class, (List) vaccineGroupRelList);
        }
    }

//    @Override
//    protected void preAdd(IceVaccineDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws
//            ConstraintViolationException, NotFoundException, MtsException, ValidationException, AuthenticationException,
//            AuthorizationException {
//        if (baseDTO != null) {
//            CdsCodeDTO vaccine = baseDTO.getVaccine();
//            if (vaccine != null) {
//                String codeId = vaccine.getCodeId();
//                if (codeId != null) {
//                    boolean componentFound = false;
//                    for (IceVaccineComponentDTO iceVaccineComponentDTO : baseDTO.getVaccineComponents()) {
//                        if (iceVaccineComponentDTO != null && codeId.equals(iceVaccineComponentDTO.getVaccineComponent().getCodeId())) {
//                            componentFound = true;
//                            break;
//                        }
//                    }
//                    if (!componentFound) {
//                        IceVaccineComponentDTO iceVaccineComponentDTO = new IceVaccineComponentDTO();
//                        try {
//                            iceVaccineComponentDTO.setVaccineComponent(vaccine);
//                            iceVaccineComponentDTO = iceVaccineComponentBO.findByQueryMain(iceVaccineComponentDTO, IceVaccineComponentDTO.ByCodeId.class, new ArrayList<Class>(), sessionDTO, propertyBagDTO);
//                        } catch (NotFoundException e) {
//                            iceVaccineComponentDTO = null;
//                        }
//                        if (iceVaccineComponentDTO != null) {
//                            IceVaccineComponentRelDTO iceVaccineComponentRelDTO = new IceVaccineComponentRelDTO();
//                            iceVaccineComponentRelDTO.setIceVaccineComponentDTO(iceVaccineComponentDTO);
//                            baseDTO.addOrUpdateChildDTO(iceVaccineComponentRelDTO);
//                        }
//                    }
//                }
//            }
//        }
//    }

    @Override
    protected void validateAddOrUpdate(
            IceVaccineDTO baseDTO,
            Operation operation,
            Class queryClass,
            List<Class> validationClasses,
            SessionDTO sessionDTO,
            PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, ConstraintViolationException, MtsException,
            AuthenticationException, AuthorizationException {
        // TODO: add check to make sure a duplicate vaccine doesn't already exists for the versions in question
    }

    @Override
    public Map<String, byte[]> exportData(IceVaccineDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "exportData ";
        if (queryClass == SimpleExchange.class) {
            return simpleExchangeExport(baseDTO, queryClass, sessionDTO, propertyBagDTO);
        } else {
            throw new ValidationException(CoreErrorCode.ParameterCanNotBeNull,
                    logger.error(METHODNAME, "Unsupported queryClass: ", queryClass));
        }
    }

    private Map<String, byte[]> simpleExchangeExport(IceVaccineDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "simpleExchangeExport ";
//        String defaultConceptDeterminationMethodId = propertyMGRLocal.get("CDS_DEFAULT_CONCEPT_DETERMINATION_METHOD", String.class);
//        ConceptDeterminationMethodDTO defaultConceptDeterminationMethodDTO = new ConceptDeterminationMethodDTO();
//        defaultConceptDeterminationMethodDTO.setCodeId(defaultConceptDeterminationMethodId);
//        defaultConceptDeterminationMethodDTO = conceptDeterminationMethodBO.findByPrimaryKeyMain(defaultConceptDeterminationMethodDTO, new ArrayList<Class>(), sessionDTO, new PropertyBagDTO());
        Map<String, byte[]> fileMap = new HashMap<String, byte[]>();
        if (baseDTO != null) {
            List<IceVaccineDTO> iceVaccineDTOs = new ArrayList<IceVaccineDTO>();
            if (baseDTO.getVaccine() != null && "ALL".equalsIgnoreCase(baseDTO.getVaccine().getCodeId())) {
                List<IceVaccineDTO> findByQueryList = findByQueryListMain(baseDTO, FindAll.class, getDtoChildClasses(), sessionDTO, new PropertyBagDTO());
                if (findByQueryList != null) {
                    iceVaccineDTOs.addAll(findByQueryList);
                } else {
                    logger.error(METHODNAME, "iceVaccineDTOs is null!");
                }
            } else {
                IceVaccineDTO iceVaccineDTO = findByPrimaryKeyMain(baseDTO, getDtoChildClasses(), sessionDTO, new PropertyBagDTO());
                if (iceVaccineDTO != null) {
                    iceVaccineDTOs.add(iceVaccineDTO);
                } else {
                    logger.error(METHODNAME, "iceVaccineDTO is null!");
                }
            }
            logger.info("Found iceVaccineDTOs: ", iceVaccineDTOs.size());
            try {
                for (IceVaccineDTO iceVaccineDTO : iceVaccineDTOs) {
                    if (iceVaccineDTO != null) {
                        CdsCodeDTO cdsCodeDTO = iceVaccineDTO.getVaccine();
                        if (cdsCodeDTO != null) {
                            IceVaccineSpecificationFile iceVaccineSpecificationFile = new IceVaccineSpecificationFile();
                            // set vaccine
                            CD vaccine = CdsCodeDTOObjectFactory.getCDFromCdsCodeDTO(cdsCodeDTO);
                            iceVaccineSpecificationFile.setVaccine(vaccine);

                            // set vaccine groups
                            for (IceVaccineGroupVaccineRelDTO iceVaccineGroupVaccineRelDTO : iceVaccineDTO.getIceVaccineGroupVaccineRelDTOs()) {
                                CD cdFromCdsCodeDTO = CdsCodeDTOObjectFactory.getCDFromCdsCodeDTO(iceVaccineGroupVaccineRelDTO.getVaccineGroup());
                                iceVaccineSpecificationFile.getVaccineGroups().add(cdFromCdsCodeDTO);
                            }

                            // set versions
                            for (IceVaccineVersionRelDTO iceVaccineVersionRelDTO : iceVaccineDTO.getIceVaccineVersionRelDTOs()) {
                                iceVaccineSpecificationFile.getCdsVersions().add(iceVaccineVersionRelDTO.getCdsVersionDTO().getOpenCdsVersionIdentifier());
                            }

                            iceVaccineSpecificationFile.setLicensedMaximumAgeForUse(iceVaccineDTO.getLicensedMaxAgeForUse());
                            iceVaccineSpecificationFile.setLicensedMinimumAgeForUse(iceVaccineDTO.getLicensedMinAgeForUse());
                            iceVaccineSpecificationFile.setLiveVirusVaccine(iceVaccineDTO.isLiveVirus());
                            iceVaccineSpecificationFile.setTradeName(iceVaccineDTO.getTradeName());
                            iceVaccineSpecificationFile.setVaccineActive(iceVaccineDTO.isActive());
                            iceVaccineSpecificationFile.setValidMaximumAgeForUse(iceVaccineDTO.getValidMaxAgeForUse());
                            iceVaccineSpecificationFile.setValidMinimumAgeForUse(iceVaccineDTO.getValidMinAgeForUse());
                            iceVaccineSpecificationFile.setMinimumDateForUse(iceVaccineDTO.getMinDateForUse());
                            iceVaccineSpecificationFile.setMaximumDateForUse(iceVaccineDTO.getMaxDateForUse());

                            // set manufacturer
                            CD manufacturerCode = CdsCodeDTOObjectFactory.getCDFromCdsCodeDTO(iceVaccineDTO.getManufacturerCode());
                            iceVaccineSpecificationFile.setManufacturerCode(manufacturerCode);

                            // set vaccine components
                            List<VaccineComponent> vaccineComponents = iceVaccineSpecificationFile.getVaccineComponents();
                            List<IceVaccineComponentDTO> components = iceVaccineDTO.getVaccineComponents();
                            for (IceVaccineComponentDTO item : components) {
                                VaccineComponent vaccineComponent = new VaccineComponent();
                                CD component = CdsCodeDTOObjectFactory.getCDFromCdsCodeDTO(item.getVaccineComponent());
                                vaccineComponent.setVaccineComponentCode(component);
                                List<CD> diseaseCodes = vaccineComponent.getDiseaseCodes();
                                for (IceVaccineComponentDiseaseRelDTO iceVaccineComponentDiseaseRelDTO : item.getIceVaccineComponentDiseaseRelDTOs()) {
                                    CD diseaseCode = CdsCodeDTOObjectFactory.getCDFromCdsCodeDTO(iceVaccineComponentDiseaseRelDTO.getIceDiseaseDTO().getDiseaseCdsCodeDTO());
                                    diseaseCodes.add(diseaseCode);
                                }
                                vaccineComponents.add(vaccineComponent);
                            }

                            List<CdsCodeOpenCdsConceptRelDTO> cdsCodeOpenCdsConceptRelDTOs = cdsCodeDTO.getCdsCodeOpenCdsConceptRelDTOs();
                            // set opencds concept relationships and primary OpenCDS concept
                            if (!cdsCodeOpenCdsConceptRelDTOs.isEmpty()) {
                                logger.info(METHODNAME, "looking for related opencds concepts");

                                List<OpenCdsConceptDTO> relatedOpenCdsConceptDTOs = new ArrayList<OpenCdsConceptDTO>();
                                List<ConceptDeterminationMethodDTO> relatedConceptDeterminationMethodDTOs = new ArrayList<ConceptDeterminationMethodDTO>();
//                                relatedConceptDeterminationMethodDTOs.add(defaultConceptDeterminationMethodDTO);

                                for (IceVaccineVersionRelDTO iceVaccineVersionRelDTO : iceVaccineDTO.getIceVaccineVersionRelDTOs()) {
                                    if (iceVaccineVersionRelDTO != null
                                            && iceVaccineVersionRelDTO.getCdsVersionDTO() != null
                                            && !iceVaccineVersionRelDTO.getCdsVersionDTO().getCdsVersionConceptDeterminationMethodRelDTOs().isEmpty()) {
                                        for (ConceptDeterminationMethodDTO conceptDeterminationMethodDTO : iceVaccineVersionRelDTO.getCdsVersionDTO().getConceptDeterminationMethodDTOs()) {
                                            relatedConceptDeterminationMethodDTOs.add(conceptDeterminationMethodDTO);
                                        }
                                    }
                                }

                                logger.info(METHODNAME, "related concept determination methods: ", relatedConceptDeterminationMethodDTOs);

                                // iterate over the vaccine CdsCodeDTO relationships to OpenCdsConceptDTOs
                                // and build a list of OpenCdsConcptDTOs and set the primaryOpenCdsConceptDTO if found.
                                for (CdsCodeOpenCdsConceptRelDTO cdsCodeOpenCdsConceptRelDTO : cdsCodeOpenCdsConceptRelDTOs) {
                                    logger.info("Considering relation: ", cdsCodeOpenCdsConceptRelDTO);
                                    String openCdsCodeId = cdsCodeOpenCdsConceptRelDTO.getOpenCdsCodeId();
                                    OpenCdsConceptDTO openCdsConceptDTO = new OpenCdsConceptDTO();
                                    openCdsConceptDTO.setCodeId(openCdsCodeId);
                                    try {
                                        openCdsConceptDTO = openCdsConceptBO.findByPrimaryKeyMain(openCdsConceptDTO, openCdsConceptBO.getDtoChildClasses(), sessionDTO, new PropertyBagDTO());
                                    } catch (NotFoundException e) {
                                        continue;
                                    }
                                    // build the versioned list of related OpenCdsConceptDTOs
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

                                if (iceVaccineDTO.getPrimaryOpenCdsConceptDTO() != null) {
                                    OpenCdsConceptDTO primaryOpenCdsConceptDTO = iceVaccineDTO.getPrimaryOpenCdsConceptDTO();
                                    CD primaryOpenCdsConcept = new CD();
                                    primaryOpenCdsConcept.setCode(primaryOpenCdsConceptDTO.getCode());
                                    primaryOpenCdsConcept.setDisplayName(primaryOpenCdsConceptDTO.getDisplayName());
                                    iceVaccineSpecificationFile.setPrimaryOpenCdsConcept(primaryOpenCdsConcept);
                                }

                                // set opencds memberships
                                List<CD> openCdsMemberships = iceVaccineSpecificationFile.getOpenCdsMemberships();
                                for (OpenCdsConceptDTO item : relatedOpenCdsConceptDTOs) {
                                    CD openCdsConcept = new CD();
                                    openCdsConcept.setCode(item.getCode());
                                    openCdsConcept.setDisplayName(item.getDisplayName());
                                    openCdsMemberships.add(openCdsConcept);
                                }
                            }

                            byte[] marshalledXml = MarshalUtils.marshalObject(iceVaccineSpecificationFile);
                            fileMap.put((iceVaccineDTO.getVaccine() != null ? iceVaccineDTO.getVaccine().getCode() : "unknown")
                                    + ".xml", marshalledXml);
                        }
                    } else {
                        logger.error(METHODNAME, "iceVaccineDTO is null!");
                    }

                }
                String xsd = FileUtils.getStringFromJarFile("iceVaccineSpecificationFile.xsd");
                fileMap.put("vaccineSpecificationFile.xsd", xsd.getBytes());
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
