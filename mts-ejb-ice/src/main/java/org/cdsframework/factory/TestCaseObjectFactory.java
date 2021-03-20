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
package org.cdsframework.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.xml.datatype.DatatypeConfigurationException;
import org.cdsframework.cds.util.CdsObjectFactory;
import org.cdsframework.dto.IceTestEvaluationDTO;
import org.cdsframework.dto.IceTestEventComponentDTO;
import org.cdsframework.dto.IceTestEventDTO;
import org.cdsframework.dto.IceTestImmunityDTO;
import org.cdsframework.dto.IceTestProposalDTO;
import org.cdsframework.dto.IceTestRecommendationDTO;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceTestGroupDTO;
import org.cdsframework.dto.IceTestSuiteDTO;
import org.cdsframework.dto.IceTestVaccineGroupRelDTO;
import org.cdsframework.dto.IceVaccineDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.ejb.bo.IceTestGroupBO;
import org.cdsframework.ejb.bo.IceTestSuiteBO;
import org.cdsframework.enumeration.OffsetSource;
import org.cdsframework.enumeration.TestCasePropertyType;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.CdsException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.ice.testcase.TestCaseWrapper;
import org.cdsframework.interfaces.OffsetBasedEventType;
import org.cdsframework.util.AuthenticationUtils;
import org.cdsframework.util.DateUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.CdsDateUtils;
import org.cdsframework.util.support.cds.Config;
import org.cdsframework.util.support.data.cds.testcase.TestCase;
import org.opencds.vmr.v1_0.schema.CD;
import org.opencds.vmr.v1_0.schema.SubstanceAdministrationEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TestCaseObjectFactory {

    private final static LogUtils logger = LogUtils.getLogger(TestCaseObjectFactory.class);
    @EJB
    private IceTestSuiteBO iceTestSuiteBO;
    @EJB
    private IceTestGroupBO iceTestGroupBO;
    @EJB
    private IceTestDTOObjectFactory iceTestDTOObjectFactory;

    public TestCase getTestCaseFromIceTestDTO(IceTestDTO iceTestDTO) throws MtsException {
        final String METHODNAME = "getTestCaseFromIceTestDTO ";
        logger.logBegin(METHODNAME);
        if (iceTestDTO == null) {
            throw new MtsException(METHODNAME + "iceTestDTO was null!");
        }
        TestCaseWrapper testCaseWrapper = getTestCaseWrapperFromIceTestDTO(iceTestDTO);
        return testCaseWrapper.getTestCase();
    }

    /**
     *
     * @param iceTestDTO
     * @return
     * @throws MtsException
     */
    public TestCaseWrapper getTestCaseWrapperFromIceTestDTO(IceTestDTO iceTestDTO) throws MtsException {
        final String METHODNAME = "getTestCaseWrapperFromIceTestDTO ";
        logger.logBegin(METHODNAME);
        if (iceTestDTO == null) {
            throw new MtsException(METHODNAME + "iceTestDTO was null!");
        }
        TestCaseWrapper testCase = new TestCaseWrapper();
        try {

            setMetadataOnTestCase(iceTestDTO, testCase);
            setSuiteDetailsOnTestCase(iceTestDTO, testCase);
            setGroupDetailsOnTestCase(iceTestDTO, testCase);
            setUserDetailsOnTestCase(iceTestDTO, testCase);
            setImmunityDetailsOnTestCase(iceTestDTO, testCase);
            setImmunizationDetailsOnTestCase(iceTestDTO, testCase);
            setProposalDetailsOnTestCase(iceTestDTO, testCase);

        } catch (DatatypeConfigurationException e) {
            logger.error(e);
        } finally {
            logger.logEnd(METHODNAME);
        }
        return testCase;
    }

    /**
     * Set the user last mod id, last mod datetime, create id and create
     * datetime on the test case.
     *
     * @param iceTestDTO
     * @param testCase
     * @throws MtsException
     */
    private void setUserDetailsOnTestCase(IceTestDTO iceTestDTO, TestCaseWrapper testCase) throws MtsException {
        final String METHODNAME = "setUserDetailsOnTestCase ";

        if (testCase == null) {
            throw new MtsException(METHODNAME + "testCaseWrapper is null!");
        }

        if (iceTestDTO != null) {
            // set createId
            try {
                UserDTO userDTO = iceTestDTOObjectFactory.getUserCache().get(iceTestDTO.getCreateId());
                if (userDTO != null) {
                    testCase.setCreateId(userDTO.getUsername());
                }
            } catch (ExecutionException e) {
                logger.error(e);
            }

            // set createDatetime
            testCase.setCreateDatetime(iceTestDTO.getCreateDatetime());

            // set lastModId
            try {
                UserDTO userDTO = iceTestDTOObjectFactory.getUserCache().get(iceTestDTO.getLastModId());
                if (userDTO != null) {
                    testCase.setLastModId(userDTO.getUsername());
                }
            } catch (ExecutionException e) {
                logger.error(e);
            }

            // set lastModDatetime
            testCase.setLastModDatetime(iceTestDTO.getLastModDatetime());
        } else {
            logger.error(METHODNAME, "iceTestDTO was null!");
        }
    }

    /**
     * Set the suite name, last mod id, last mod datetime, create id and create
     * datetime on the test case.
     *
     * @param iceTestDTO
     * @param testCase
     * @throws MtsException
     */
    private void setSuiteDetailsOnTestCase(IceTestDTO iceTestDTO, TestCaseWrapper testCase) throws MtsException {
        final String METHODNAME = "setSuiteDetailsOnTestCase ";

        if (testCase == null) {
            throw new MtsException(METHODNAME + "testCaseWrapper is null!");
        }

        if (iceTestDTO != null) {
            // set suite details
            IceTestSuiteDTO iceTestSuiteDTO = new IceTestSuiteDTO();
            iceTestSuiteDTO.setSuiteId(iceTestDTO.getSuiteId());
            try {
                iceTestSuiteDTO = iceTestSuiteBO.findByPrimaryKeyMain(
                        iceTestSuiteDTO,
                        new ArrayList<Class>(),
                        AuthenticationUtils.getInternalSessionDTO(),
                        new PropertyBagDTO());
            } catch (ValidationException e) {
                logger.error(e);
            } catch (NotFoundException e) {
                logger.error(e);
            } catch (AuthenticationException e) {
                logger.error(e);
            } catch (AuthorizationException e) {
                logger.error(e);
            }
            if (iceTestSuiteDTO != null) {

                // set suite name
                testCase.setSuiteName(iceTestSuiteDTO.getName());

                // set suiteCreateId
                try {
                    UserDTO userDTO = iceTestDTOObjectFactory.getUserCache().get(iceTestSuiteDTO.getCreateId());
                    if (userDTO != null) {
                        testCase.setSuiteCreateId(userDTO.getUsername());
                    }
                } catch (ExecutionException e) {
                    logger.error(e);
                }

                // set suiteCreateDatetime
                testCase.setSuiteCreateDatetime(iceTestSuiteDTO.getCreateDatetime());

                // set suiteLastModId
                try {
                    UserDTO userDTO = iceTestDTOObjectFactory.getUserCache().get(iceTestSuiteDTO.getLastModId());
                    if (userDTO != null) {
                        testCase.setSuiteLastModId(userDTO.getUsername());
                    }
                } catch (ExecutionException e) {
                    logger.error(e);
                }

                // set suiteLastModDatetime
                testCase.setSuiteLastModDatetime(iceTestSuiteDTO.getLastModDatetime());
            }
        } else {
            logger.error(METHODNAME, "iceTestDTO was null!");
        }
    }

    /**
     * Set the group name, last mod id, last mod datetime, create id and create
     * datetime on the test case.
     *
     * @param iceTestDTO
     * @param testCase
     * @throws MtsException
     */
    private void setGroupDetailsOnTestCase(IceTestDTO iceTestDTO, TestCaseWrapper testCase) throws MtsException {
        final String METHODNAME = "setGroupDetailsOnTestCase ";

        if (testCase == null) {
            throw new MtsException(METHODNAME + "testCaseWrapper is null!");
        }

        if (iceTestDTO != null) {
            // set group details
            IceTestGroupDTO iceTestGroupDTO = new IceTestGroupDTO();
            iceTestGroupDTO.setGroupId(iceTestDTO.getGroupId());
            try {
                iceTestGroupDTO = iceTestGroupBO.findByPrimaryKeyMain(iceTestGroupDTO, new ArrayList<Class>(), AuthenticationUtils.getInternalSessionDTO(), new PropertyBagDTO());
            } catch (ValidationException e) {
                logger.error(e);
            } catch (NotFoundException e) {
                logger.error(e);
            } catch (AuthenticationException e) {
                logger.error(e);
            } catch (AuthorizationException e) {
                logger.error(e);
            }
            if (iceTestGroupDTO != null) {

                // set group name
                testCase.setGroupName(iceTestGroupDTO.getName());

                // set groupCreateId
                try {
                    UserDTO userDTO = iceTestDTOObjectFactory.getUserCache().get(iceTestGroupDTO.getCreateId());
                    if (userDTO != null) {
                        testCase.setGroupCreateId(userDTO.getUsername());
                    }
                } catch (ExecutionException e) {
                    logger.error(e);
                }

                // set groupCreateDatetime
                testCase.setGroupCreateDatetime(iceTestGroupDTO.getCreateDatetime());

                // set groupLastModId
                try {
                    UserDTO userDTO = iceTestDTOObjectFactory.getUserCache().get(iceTestGroupDTO.getLastModId());
                    if (userDTO != null) {
                        testCase.setGroupLastModId(userDTO.getUsername());
                    }
                } catch (ExecutionException e) {
                    logger.error(e);
                }

                // set groupLastModDatetime
                testCase.setGroupLastModDatetime(iceTestGroupDTO.getLastModDatetime());
            }
        } else {
            logger.error(METHODNAME, "iceTestDTO was null!");
        }
    }

    /**
     * Set the immunity details on the test case.
     *
     * @param iceTestDTO
     * @param testCase
     * @throws MtsException
     */
    private void setImmunityDetailsOnTestCase(IceTestDTO iceTestDTO, TestCaseWrapper testCase) throws MtsException {
        final String METHODNAME = "setImmunityDetailsOnTestCase ";

        if (testCase == null) {
            throw new MtsException(METHODNAME + "testCaseWrapper is null!");
        }

        if (iceTestDTO != null) {

            // process disease immunity
            for (IceTestImmunityDTO iceTestImmunityDTO : iceTestDTO.getChildrenDTOs(IceTestImmunityDTO.ByTestId.class, IceTestImmunityDTO.class)) {
                try {

                    // determine the observation event time
                    Date observationEventTime = CdsDateUtils.getOffsetBasedEventDate(iceTestImmunityDTO, iceTestDTO, new ArrayList<OffsetBasedEventType>());

                    testCase.addImmunityObservationResult(
                            observationEventTime, null,
                            iceTestImmunityDTO.getImmunityFocus().getCode(),
                            iceTestImmunityDTO.getImmunityFocus().getCodeSystem(),
                            iceTestImmunityDTO.getImmunityValue().getCode(),
                            iceTestImmunityDTO.getImmunityValue().getCodeSystem(),
                            "IS_IMMUNE",
                            Config.getCodeSystemOid("IMMUNITY_INTERPRETATION"));
                } catch (Exception e) {
                    logger.error(e);
                    throw new MtsException(METHODNAME + "Error processing disease/immunity for "
                            + iceTestImmunityDTO.getImmunityFocus().getCode()
                            + " of type "
                            + iceTestImmunityDTO.getImmunityValue().getCode()
                            + ": "
                            + e.getMessage());
                }
            }
        } else {
            logger.error(METHODNAME, "iceTestDTO was null!");
        }
    }

    /**
     * Set the immunization details on the test case.
     *
     * @param iceTestDTO
     * @param testCase
     * @throws MtsException
     */
    private void setImmunizationDetailsOnTestCase(IceTestDTO iceTestDTO, TestCaseWrapper testCase) throws MtsException {
        final String METHODNAME = "setImmunizationDetailsOnTestCase ";

        if (testCase == null) {
            throw new MtsException(METHODNAME + "testCaseWrapper is null!");
        }

        if (iceTestDTO != null) {
            // process immunizations
            List<IceTestEventDTO> childrenDTOs = iceTestDTO.getChildrenDTOs(IceTestEventDTO.ByTestId.class, IceTestEventDTO.class);
            for (IceTestEventDTO iceTestEventDTO : childrenDTOs) {
                // make sure substance is a supported vaccine

                // determine administration date
                Date administrationTime = CdsDateUtils.getOffsetBasedEventDate(iceTestEventDTO, iceTestDTO, new ArrayList<OffsetBasedEventType>());

                List<IceTestEventComponentDTO> components = iceTestEventDTO.getChildrenDTOs(IceTestEventComponentDTO.ByEventId.class, IceTestEventComponentDTO.class);
                List<SubstanceAdministrationEvent> componentEvents = new ArrayList<SubstanceAdministrationEvent>();
                // process components
                for (IceTestEventComponentDTO iceTestEventComponentDTO : components) {

                    String evaluationValidityType = iceTestEventComponentDTO.getEvaluationValue() != null ? iceTestEventComponentDTO.getEvaluationValue().getCode() : null;
                    String evaluationValidityTypeOid = iceTestEventComponentDTO.getEvaluationValue() != null ? iceTestEventComponentDTO.getEvaluationValue().getCodeSystem() : null;

                    if ("IGNORE".equalsIgnoreCase(evaluationValidityType)) {
                        logger.warn(METHODNAME, "Skipping immunization component CVX "
                                + iceTestEventComponentDTO.getIceVaccineComponentDTO().getVaccineComponent().getCode()
                                + " for immunization event "
                                + iceTestEventDTO.getEventId()
                                + ": component marked as IGNORED");
                        continue;
                    }

                    List<CD> reasons = new ArrayList<CD>();
                    for (IceTestEvaluationDTO iceTestEvaluationDTO : iceTestEventComponentDTO.getChildrenDTOs(IceTestEvaluationDTO.ByEventComponentId.class, IceTestEvaluationDTO.class)) {
                        if (iceTestEvaluationDTO.getEvaluationInterpretation() != null) {
                            CD cd = CdsObjectFactory.getCD(iceTestEvaluationDTO.getEvaluationInterpretation().getCode(), iceTestEvaluationDTO.getEvaluationInterpretation().getCodeSystem());
                            reasons.add(cd);
                        }
                    }

                    try {
                        SubstanceAdministrationEvent componentEvent = testCase.getEvaluationSubstanceAdministrationEvent(
                                iceTestEventComponentDTO.getIceVaccineComponentDTO().getVaccineComponent().getCode(),
                                iceTestEventComponentDTO.getIceVaccineComponentDTO().getVaccineComponent().getCodeSystem(),
                                administrationTime,
                                evaluationValidityType,
                                evaluationValidityTypeOid,
                                iceTestEventComponentDTO.getIceVaccineGroupDTO().getVaccineGroup().getCode(),
                                iceTestEventComponentDTO.getIceVaccineGroupDTO().getVaccineGroup().getCodeSystem(),
                                reasons);
                        componentEvents.add(componentEvent);
                    } catch (CdsException e) {
                        logger.error(e);
                        throw new MtsException(METHODNAME
                                + "Error processing immunization component CVX "
                                + iceTestEventComponentDTO.getIceVaccineComponentDTO().getVaccineComponent().getCode()
                                + " for immunization event "
                                + iceTestEventDTO.getEventId()
                                + ": "
                                + e.getMessage());
                    }

                }

                String substanceCode;
                String substanceCodeOid;
                if (iceTestEventDTO.getIceVaccineDTO() != null && iceTestEventDTO.getIceVaccineDTO().getVaccine() != null) {
                    substanceCode = iceTestEventDTO.getIceVaccineDTO().getVaccine().getCode();
                    substanceCodeOid = iceTestEventDTO.getIceVaccineDTO().getVaccine().getCodeSystem();
                } else {
                    substanceCode = null;
                    substanceCodeOid = null;
                }

                try {
                    testCase.addSubstanceAdministrationEvent(
                            substanceCode,
                            substanceCodeOid,
                            administrationTime,
                            String.valueOf(iceTestEventDTO.getEventId()),
                            Config.getCodeSystemOid("ADMINISTRATION_ID"),
                            componentEvents);
                } catch (CdsException e) {
                    logger.error(e);
                    throw new MtsException(METHODNAME
                            + "Error processing immunization event CVX "
                            + ((iceTestEventDTO.getIceVaccineDTO() != null && iceTestEventDTO.getIceVaccineDTO().getVaccine() != null)
                            ? iceTestEventDTO.getIceVaccineDTO().getVaccine().getCode()
                            : null)
                            + " for immunization event "
                            + iceTestEventDTO.getEventId()
                            + ": "
                            + e.getMessage());
                }
            }
        } else {
            logger.error(METHODNAME, "iceTestDTO was null!");
        }
    }

    /**
     * Set the proposal details on the test case.
     *
     * @param iceTestDTO
     * @param testCase
     * @throws MtsException
     */
    private void setProposalDetailsOnTestCase(IceTestDTO iceTestDTO, TestCaseWrapper testCase) throws MtsException {
        final String METHODNAME = "setProposalDetailsOnTestCase ";

        if (testCase == null) {
            throw new MtsException(METHODNAME + "testCaseWrapper is null!");
        }

        if (iceTestDTO != null) {
            // process proposals
            for (IceTestProposalDTO iceTestProposalDTO : iceTestDTO.getChildrenDTOs(IceTestProposalDTO.ByTestId.class, IceTestProposalDTO.class)) {
                // make sure substance is a supported vaccine group
                IceVaccineGroupDTO vaccineGroup = iceTestProposalDTO.getIceVaccineGroupDTO();
                IceVaccineDTO vaccine = iceTestProposalDTO.getIceVaccineDTO();
                String groupSeq = null;
                String groupOid = null;
                String groupName = null;
                String vacCode = null;
                String vacCodeOid = null;
                String focus = null;
                String focusOid = null;
                logger.info(METHODNAME, "IceTestProposalDTO vaccineGroup: ", vaccineGroup);
                logger.info(METHODNAME, "IceTestProposalDTO vaccine: ", vaccine);
                if (vaccine != null && vaccineGroup != null && vaccineGroup.getVaccineGroup() != null) {
                    groupSeq = vaccineGroup.getVaccineGroup().getCode();
                    groupOid = vaccineGroup.getVaccineGroup().getCodeSystem();
                    groupName = vaccineGroup.getVaccineGroupName();
                    vacCode = vaccine.getVaccine() != null ? vaccine.getVaccine().getCode() : null;
                    vacCodeOid = vaccine.getVaccine() != null ? vaccine.getVaccine().getCodeSystem(): null;
                    focus = groupSeq;
                    focusOid = groupOid;
                } else if (vaccineGroup != null && vaccineGroup.getVaccineGroup() != null) {
                    groupSeq = vaccineGroup.getVaccineGroup().getCode();
                    groupOid = vaccineGroup.getVaccineGroup().getCodeSystem();
                    groupName = vaccineGroup.getVaccineGroupName();
                    focus = groupSeq;
                    focusOid = groupOid;
                } else {
                    logger.warn(METHODNAME, "No vaccine group or substance set in proposal.");
                }
                logger.info(METHODNAME, "addSubstanceAdministrationProposal groupSeq: ", groupSeq);
                logger.info(METHODNAME, "addSubstanceAdministrationProposal groupOid: ", groupOid);
                logger.info(METHODNAME, "addSubstanceAdministrationProposal groupName: ", groupName);
                logger.info(METHODNAME, "addSubstanceAdministrationProposal vacCode: ", vacCode);
                logger.info(METHODNAME, "addSubstanceAdministrationProposal vacCodeOid: ", vacCodeOid);
                logger.info(METHODNAME, "addSubstanceAdministrationProposal focus: ", focus);
                logger.info(METHODNAME, "addSubstanceAdministrationProposal focusOid: ", focusOid);

                List<CD> interpretations = new ArrayList<CD>();
                for (IceTestRecommendationDTO iceTestRecommendationDTO : iceTestProposalDTO.getIceTestRecommendationDTOs()) {
                    CD cd = CdsObjectFactory.getCD(iceTestRecommendationDTO.getRecommendationInterpretation().getCode(), iceTestRecommendationDTO.getRecommendationInterpretation().getCodeSystem());
                    interpretations.add(cd);
                }

                // determine dates
                logger.info(METHODNAME, "iceTestProposalDTO.getOffsetSource()=", iceTestProposalDTO.getOffsetSource());
                OffsetSource originalOffsetSource = iceTestProposalDTO.getOffsetSource();
                iceTestProposalDTO.setOffsetSource(OffsetSource.EARLIEST);
                Date earliestDate = CdsDateUtils.getOffsetBasedEventDate(iceTestProposalDTO, iceTestDTO, new ArrayList<OffsetBasedEventType>());
                iceTestProposalDTO.setOffsetSource(OffsetSource.LATEST);
                Date latestDate = CdsDateUtils.getOffsetBasedEventDate(iceTestProposalDTO, iceTestDTO, new ArrayList<OffsetBasedEventType>());
                iceTestProposalDTO.setOffsetSource(OffsetSource.OVERDUE);
                Date overdueDate = CdsDateUtils.getOffsetBasedEventDate(iceTestProposalDTO, iceTestDTO, new ArrayList<OffsetBasedEventType>());
                iceTestProposalDTO.setOffsetSource(OffsetSource.RECOMMENDED);
                Date recommendedDate = CdsDateUtils.getOffsetBasedEventDate(iceTestProposalDTO, iceTestDTO, new ArrayList<OffsetBasedEventType>());
                iceTestProposalDTO.setOffsetSource(originalOffsetSource);
                logger.info(METHODNAME, "earliestDate=", earliestDate);
                logger.info(METHODNAME, "latestDate=", latestDate);
                logger.info(METHODNAME, "overdueDate=", overdueDate);
                logger.info(METHODNAME, "recommendedDate=", recommendedDate);

                try {
                    testCase.addSubstanceAdministrationProposal(
                            groupSeq,
                            groupOid,
                            vacCode,
                            vacCodeOid,
                            recommendedDate,
                            overdueDate,
                            earliestDate,
                            latestDate,
                            focus,
                            focusOid,
                            iceTestProposalDTO.getRecommendationValue().getCode(),
                            iceTestProposalDTO.getRecommendationValue().getCodeSystem(),
                            interpretations);
                } catch (Exception e) {
                    logger.error(e);
                    throw new MtsException(METHODNAME
                            + "Error processing recommendation for "
                            + groupName
                            + ": "
                            + e.getMessage());
                }
            }
        } else {
            logger.error(METHODNAME, "iceTestDTO was null!");
        }
    }

    /**
     * Set the meta data on the test case.
     *
     * @param iceTestDTO
     * @param testCase
     * @throws MtsException
     */
    private void setMetadataOnTestCase(IceTestDTO iceTestDTO, TestCaseWrapper testCase) throws DatatypeConfigurationException,
            MtsException {
        final String METHODNAME = "setMetadataOnTestCase ";

        if (testCase == null) {
            throw new MtsException(METHODNAME + "testCaseWrapper is null!");
        }

        if (iceTestDTO != null) {

            testCase.setEncodedName(iceTestDTO.getEncodedName());
            if (iceTestDTO.isOffsetBased()) {
                testCase.setExecutiondate(new Date());
            } else {
                testCase.setExecutiondate(iceTestDTO.getExecutionDate());
            }
            testCase.addProperty("offsetBased", String.valueOf(iceTestDTO.isOffsetBased()), TestCasePropertyType.BOOLEAN);
            testCase.addProperty("ageOffset", iceTestDTO.getOffset(), TestCasePropertyType.STRING);
            testCase.setName(iceTestDTO.getName());
            testCase.setNotes(iceTestDTO.getNotes());
            testCase.setIgnore(iceTestDTO.isIgnore());
            testCase.setRuletotest(iceTestDTO.getRuleToTest());

            // set vaccine group focus
            for (IceTestVaccineGroupRelDTO iceTestVaccineGroupRelDTO : iceTestDTO.getIceTestVaccineGroupRelDTOs()) {
                if (iceTestVaccineGroupRelDTO != null) {
                    String vaccineGroupName = iceTestVaccineGroupRelDTO.getIceVaccineGroupDTO() != null ? iceTestVaccineGroupRelDTO.getIceVaccineGroupDTO().getVaccineGroupName() : null;
                    String vaccineGroupCode = iceTestVaccineGroupRelDTO.getIceVaccineGroupDTO() != null ? iceTestVaccineGroupRelDTO.getIceVaccineGroupDTO().getVaccineGroupCode() : null;
                    if (vaccineGroupName != null) {
                        if (vaccineGroupCode != null) {
                            vaccineGroupName += " (" + vaccineGroupCode + ")";
                        }
                        testCase.addProperty("vaccineGroup", vaccineGroupName, TestCasePropertyType.STRING);
                    }
                } else {
                    throw new MtsException("iceTestVaccineGroupRelDTO is null!");
                }
            }

            // set dob
            try {
                if (iceTestDTO.isOffsetBased()) {
                    testCase.setPatientBirthTime(DateUtils.incrementDateFromString(new Date(), iceTestDTO.getOffset(), true));
                } else {
                    testCase.setPatientBirthTime(iceTestDTO.getDob());
                }
            } catch (CdsException e) {
                logger.error(e);
                throw new MtsException("Error processing date of birth "
                        + iceTestDTO.getDob()
                        + ": "
                        + e.getMessage());
            }

            // set gender
            String genderCode = null;
            String genderCodeOid = null;
            if (iceTestDTO.getGender() != null) {
                genderCode = iceTestDTO.getGender().getCode();
                genderCodeOid = iceTestDTO.getGender().getCodeSystem();
            } else {
                logger.warn(METHODNAME, "missing gender code!");
            }
            try {
                if (genderCode != null) {
                    testCase.setPatientGender(genderCode, genderCodeOid);
                }
            } catch (CdsException e) {
                logger.error(e);
                throw new MtsException("Error processing gender "
                        + iceTestDTO.getGender().getCode()
                        + ": "
                        + e.getMessage());
            }

            // set patient id
            try {
                testCase.setPatientId(String.valueOf(iceTestDTO.getTestId()));
            } catch (CdsException e) {
                logger.error(e);
                throw new MtsException("Error processing patient id "
                        + iceTestDTO.getTestId()
                        + ": "
                        + e.getMessage());
            }
        } else {
            logger.error(METHODNAME, "iceTestDTO was null!");
        }
    }
}
