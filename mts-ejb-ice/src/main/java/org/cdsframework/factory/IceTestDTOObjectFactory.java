/**
 * The MTS ice EJB project is the base framework for the CDS Framework Middle Tier Service.
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
package org.cdsframework.factory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsVersionDTO;
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
import org.cdsframework.dto.IceVaccineComponentDTO;
import org.cdsframework.dto.IceVaccineComponentRelDTO;
import org.cdsframework.dto.IceVaccineDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.ejb.bo.UserBO;
import org.cdsframework.enumeration.ProposalType;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.CdsException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.ice.testcase.TestCaseWrapper;
import org.cdsframework.util.AuthenticationUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.UuidUtils;
import org.cdsframework.util.support.cds.Config;
import org.cdsframework.util.support.data.cds.testcase.TestCase;
import org.cdsframework.util.support.data.cds.testcase.TestCaseProperty;
import org.opencds.support.util.DateUtils;
import org.opencds.vmr.v1_0.schema.CD;
import org.opencds.vmr.v1_0.schema.ObservationResult;
import org.opencds.vmr.v1_0.schema.RelatedClinicalStatement;
import org.opencds.vmr.v1_0.schema.SubstanceAdministrationEvent;
import org.opencds.vmr.v1_0.schema.SubstanceAdministrationProposal;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class IceTestDTOObjectFactory {

    private final static LogUtils logger = LogUtils.getLogger(IceTestDTOObjectFactory.class);
    @EJB
    private UserBO userBO;

    private final LoadingCache<String, UserDTO> userCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<String, UserDTO>() {
                @Override
                public UserDTO load(String key) throws ValidationException, MtsException, NotFoundException, AuthenticationException, AuthorizationException {
                    UserDTO result;
                    try {
                        result = userBO.getUserDTOByIdOrName(key, AuthenticationUtils.getInternalSessionDTO());
                    } catch (NotFoundException e) {
                        result = new UserDTO();
                        result.setUsername(key);
                        result.setUserId(key);
                    }
                    if (result == null) {
                        result = new UserDTO();
                        result.setUsername(key);
                        result.setUserId(key);
                    }
                    return result;
                }
            });

    public LoadingCache<String, UserDTO> getUserCache() {
        return userCache;
    }

    public IceTestDTO getIceTestDTOFromTestCase(
            TestCase testCase,
            IceTestSuiteDTO testSuiteDTO,
            IceTestGroupDTO testGroupDTO,
            CdsVersionDTO cdsVersionDTO,
            SessionDTO sessionDTO) throws
            MtsException, ValidationException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "getIceTestDTOFromTestCase ";
        logger.logBegin(METHODNAME);
        if (testCase == null) {
            throw new MtsException(METHODNAME + "testCase was null!");
        }
        IceTestDTO iceTestDTO = null;
        TestCaseWrapper testCaseWrapper = new TestCaseWrapper(testCase);
        try {
            iceTestDTO = getIceTestDTOFromTestCaseWrapper(testCaseWrapper, testSuiteDTO, testGroupDTO, cdsVersionDTO, null, sessionDTO);
        } catch (ParseException e) {
            throw new MtsException(e.getMessage());
        }
        return iceTestDTO;
    }

    public IceTestDTO getIceTestDTOFromTestCaseWrapper(
            TestCaseWrapper testCase,
            IceTestSuiteDTO iceTestSuiteDTO,
            IceTestGroupDTO iceTestGroupDTO,
            CdsVersionDTO cdsVersionDTO,
            StringBuilder notes,
            SessionDTO sessionDTO)
            throws MtsException, ParseException, ValidationException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "getIceTestDTOFromTestCaseWrapper ";
        logger.logBegin(METHODNAME);
        IceTestDTO testDTO = new IceTestDTO();
        testDTO.setGroupId(iceTestGroupDTO.getGroupId());
        testDTO.setIgnore(false);
        try {

            setMetadataOnIceTestDTO(testDTO, testCase, cdsVersionDTO);
            setUserDetailsOnIceTestDTO(testDTO, testCase, sessionDTO);
            setImmunityDetailsOnIceTestDTO(testDTO, testCase);
            setProposalDetailsOnIceTestDTO(testDTO, testCase, cdsVersionDTO);
            setImmunizationDetailsOnIceTestDTO(testDTO, testCase, cdsVersionDTO);
            setGroupDetailsOnIceTestGroupDTO(iceTestGroupDTO, testCase, cdsVersionDTO, sessionDTO);
            setSuiteDetailsOnIceTestSuiteDTO(iceTestSuiteDTO, testCase, cdsVersionDTO, sessionDTO);
            runSanityChecks(testDTO, testCase);

        } catch (CdsException e) {
            logger.error("IceException: ", e.getMessage());
            logger.error(e);
            throw new MtsException(e.getMessage());
        } catch (NotFoundException e) {
            logger.error("Bad cache lookup: ", e.getMessage());
            throw new MtsException(e.getMessage(), e);
        } finally {
            logger.logEnd(METHODNAME);
        }

        return testDTO;
    }

    /**
     * Check that the data was properly converted from test case to ice test dto
     */
    private void runSanityChecks(IceTestDTO iceTestDTO, TestCaseWrapper testCase) throws MtsException, CdsException {

        // sanity checks
        if (testCase.getImmunityObservationResults().size() != iceTestDTO.getChildrenDTOs(IceTestImmunityDTO.ByTestId.class).size()) {
            throw new MtsException("The number of incoming immunity observations differed from the added IceTestImmunityDTO instances. DTO Count: "
                    + iceTestDTO.getChildrenDTOs(IceTestImmunityDTO.ByTestId.class).size() + " vs " + testCase.getImmunityObservationResults().size()
                    + " import count");
        }
        if (testCase.getSubstanceAdministrationProposals().size() != iceTestDTO.getChildrenDTOs(IceTestProposalDTO.ByTestId.class).size()) {
            throw new MtsException("The number of incoming proposals differed from the added IceTestProposalDTO instances. DTO Count: "
                    + iceTestDTO.getChildrenDTOs(IceTestProposalDTO.ByTestId.class).size() + " vs " + testCase.getSubstanceAdministrationProposals().size()
                    + " import count");
        }
        if (testCase.getSubstanceAdministrationEvents().size() != iceTestDTO.getChildrenDTOs(IceTestEventDTO.ByTestId.class).size()) {
            throw new MtsException("The number of incoming events differed from the added IceTestEventDTO instances. DTO Count: "
                    + iceTestDTO.getChildrenDTOs(IceTestEventDTO.ByTestId.class).size() + " vs " + testCase.getSubstanceAdministrationEvents().size()
                    + " import count");
        }

        if (logger.isDebugEnabled()) {
            logger.info("Imported test: ", testCase.getName(), ":");
            logger.info(" - # of immunities from test: ", testCase.getImmunityObservationResults().size());
            logger.info(" - # of immunities from testDTO: ", iceTestDTO.getChildrenDTOs(IceTestImmunityDTO.ByTestId.class).size());
            logger.info(" - # of proposals from test: ", testCase.getSubstanceAdministrationProposals().size());
            logger.info(" - # of proposals from testDTO: ", iceTestDTO.getChildrenDTOs(IceTestProposalDTO.ByTestId.class).size());
            logger.info(" - # of events from test: ", testCase.getSubstanceAdministrationEvents().size());
            logger.info(" - # of events from testDTO: ", iceTestDTO.getChildrenDTOs(IceTestEventDTO.ByTestId.class).size());
        }
    }

    /**
     * Set the user last mod id, last mod datetime, create id and create
     * datetime on the test case.
     *
     * @param iceTestDTO
     * @param testCase
     * @throws MtsException
     */
    private void setUserDetailsOnIceTestDTO(IceTestDTO iceTestDTO, TestCaseWrapper testCase, SessionDTO sessionDTO) throws
            MtsException, ParseException, NotFoundException, ValidationException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "setUserDetailsOnIceTestDTO ";

        if (iceTestDTO == null) {
            throw new MtsException(METHODNAME + "iceTestDTO is null!");
        }

        if (testCase != null) {

            try {
                // set the create datetime and create id
                Date createDatetime = testCase.getCreateDatetime();
                if (createDatetime != null) {
                    logger.debug(METHODNAME, "setting testcase createDatetime: ", createDatetime);
                    iceTestDTO.setCreateDatetime(createDatetime);
                } else {
                    logger.debug(METHODNAME, "setting new createDatetime: ", new Date());
                    iceTestDTO.setCreateDatetime(new Date());
                }

                if (testCase.getCreateId() != null) {
                    UserDTO userDTO = userCache.get(testCase.getCreateId());
                    logger.debug(METHODNAME, "setting testcase createId: ", testCase.getCreateId());
                    iceTestDTO.setCreateId(userDTO.getUserId());
                } else {
                    logger.debug(METHODNAME, "setting new createId: ", sessionDTO.getUserDTO().getUsername());
                    iceTestDTO.setCreateId(sessionDTO.getUserDTO().getUserId());
                }

                // set the last mod datetime and last mod id
                Date lastModDatetime = testCase.getLastModDatetime();
                if (lastModDatetime != null) {
                    logger.debug(METHODNAME, "setting testcase lastModDatetime: ", lastModDatetime);
                    iceTestDTO.setLastModDatetime(lastModDatetime);
                } else {
                    logger.debug(METHODNAME, "setting new lastModDatetime: ", new Date());
                    iceTestDTO.setLastModDatetime(new Date());
                }

                if (testCase.getLastModId() != null) {
                    UserDTO userDTO = userCache.get(testCase.getLastModId());
                    logger.debug(METHODNAME, "setting testcase lastModId: ", testCase.getLastModId());
                    iceTestDTO.setLastModId(userDTO.getUserId());
                } else {
                    logger.debug(METHODNAME, "setting new lastModId: ", sessionDTO.getUserDTO().getUsername());
                    iceTestDTO.setLastModId(sessionDTO.getUserDTO().getUserId());
                }
            } catch (ExecutionException e) {
                throw new MtsException(e.getMessage(), e);
            }
        } else {
            logger.error(METHODNAME, "testCase was null!");
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
    private void setSuiteDetailsOnIceTestSuiteDTO(IceTestSuiteDTO iceTestSuiteDTO, TestCaseWrapper testCase, CdsVersionDTO cdsVersionDTO, SessionDTO sessionDTO)
            throws MtsException, ParseException, ValidationException, NotFoundException, AuthenticationException,
            AuthorizationException {
        final String METHODNAME = "setSuiteDetailsOnIceTestSuiteDTO ";

        if (iceTestSuiteDTO != null) {
            if (testCase != null) {

                try {
                    // set the create datetime and create id
                    Date suiteCreateDatetime = testCase.getSuiteCreateDatetime();
                    if (suiteCreateDatetime != null) {
                        logger.debug(METHODNAME, "setting testcase suiteCreateDatetime: ", suiteCreateDatetime);
                        iceTestSuiteDTO.setCreateDatetime(suiteCreateDatetime);
                    } else {
                        logger.debug(METHODNAME, "setting new suiteCreateDatetime: ", new Date());
                        iceTestSuiteDTO.setCreateDatetime(new Date());
                    }
                    if (testCase.getSuiteCreateId() != null) {
                        UserDTO userDTO = userCache.get(testCase.getSuiteCreateId());
                        logger.debug(METHODNAME, "setting testcase suiteCreateId: ", testCase.getSuiteCreateId());
                        iceTestSuiteDTO.setCreateId(userDTO.getUserId());
                    } else {
                        logger.debug(METHODNAME, "setting new suiteCreateId: ", sessionDTO.getUserDTO().getUsername());
                        iceTestSuiteDTO.setCreateId(sessionDTO.getUserDTO().getUserId());
                    }

                    // set the last mod datetime and last mod id
                    Date suiteLastModDatetime = testCase.getSuiteLastModDatetime();
                    if (suiteLastModDatetime != null) {
                        logger.debug(METHODNAME, "setting testcase suiteLastModDatetime: ", suiteLastModDatetime);
                        iceTestSuiteDTO.setLastModDatetime(suiteLastModDatetime);
                    } else {
                        logger.debug(METHODNAME, "setting new suiteLastModDatetime: ", new Date());
                        iceTestSuiteDTO.setLastModDatetime(new Date());
                    }
                    if (testCase.getSuiteLastModId() != null) {
                        UserDTO userDTO = userCache.get(testCase.getSuiteLastModId());
                        logger.debug(METHODNAME, "setting testcase suiteLastModId: ", testCase.getSuiteLastModId());
                        iceTestSuiteDTO.setLastModId(userDTO.getUserId());
                    } else {
                        logger.debug(METHODNAME, "setting new suiteLastModId: ", sessionDTO.getUserDTO().getUsername());
                        iceTestSuiteDTO.setLastModId(sessionDTO.getUserDTO().getUserId());
                    }
                } catch (ExecutionException e) {
                    throw new MtsException(e.getMessage(), e);
                }
            } else {
                logger.error(METHODNAME, "testCase was null!");
            }
        } else {
            logger.error(METHODNAME, "iceTestSuiteDTO was null!");
        }
    }

    /**
     * Set the group name, last mod id, last mod datetime, create id and create
     * datetime on the test case.
     *
     * @param iceTestGroupDTO
     * @param testCase
     * @throws MtsException
     */
    private void setGroupDetailsOnIceTestGroupDTO(IceTestGroupDTO iceTestGroupDTO, TestCaseWrapper testCase, CdsVersionDTO cdsVersionDTO, SessionDTO sessionDTO)
            throws
            MtsException, ParseException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "setGroupDetailsOnIceTestGroupDTO ";

        if (iceTestGroupDTO != null) {
            if (testCase != null) {

                try {
                    // set the create datetime and create id
                    Date groupCreateDatetime = testCase.getGroupCreateDatetime();
                    if (groupCreateDatetime != null) {
                        logger.debug(METHODNAME, "setting testcase groupCreateDatetime: ", groupCreateDatetime);
                        iceTestGroupDTO.setCreateDatetime(groupCreateDatetime);
                    } else {
                        logger.debug(METHODNAME, "setting new groupCreateDatetime: ", new Date());
                        iceTestGroupDTO.setCreateDatetime(new Date());
                    }
                    if (testCase.getGroupCreateId() != null) {
                        UserDTO userDTO = userCache.get(testCase.getGroupCreateId());
                        logger.debug(METHODNAME, "setting testcase groupCreateId: ", testCase.getGroupCreateId());
                        iceTestGroupDTO.setCreateId(userDTO.getUserId());
                    } else {
                        logger.debug(METHODNAME, "setting new groupCreateId: ", sessionDTO.getUserDTO().getUsername());
                        iceTestGroupDTO.setCreateId(sessionDTO.getUserDTO().getUserId());
                    }

                    // set the last mod datetime and last mod id
                    Date groupLastModDatetime = testCase.getGroupLastModDatetime();
                    if (groupLastModDatetime != null) {
                        logger.debug(METHODNAME, "setting testcase groupLastModDatetime: ", groupLastModDatetime);
                        iceTestGroupDTO.setLastModDatetime(groupLastModDatetime);
                    } else {
                        logger.debug(METHODNAME, "setting new groupLastModDatetime: ", new Date());
                        iceTestGroupDTO.setLastModDatetime(new Date());
                    }
                    if (testCase.getGroupLastModId() != null) {
                        UserDTO userDTO = userCache.get(testCase.getGroupLastModId());
                        logger.debug(METHODNAME, "setting testcase groupLastModId: ", testCase.getGroupLastModId());
                        iceTestGroupDTO.setLastModId(userDTO.getUserId());
                    } else {
                        logger.debug(METHODNAME, "setting new groupLastModId: ", sessionDTO.getUserDTO().getUsername());
                        iceTestGroupDTO.setLastModId(sessionDTO.getUserDTO().getUserId());
                    }
                } catch (ExecutionException e) {
                    throw new MtsException(e.getMessage(), e);
                }
            } else {
                logger.error(METHODNAME, "testCase was null!");
            }
        } else {
            logger.error(METHODNAME, "iceTestGroupDTO was null!");
        }
    }

    /**
     * Set the immunity details on the test case.
     *
     * @param iceTestDTO
     * @param testCase
     * @throws MtsException
     */
    private void setImmunityDetailsOnIceTestDTO(IceTestDTO iceTestDTO, TestCaseWrapper testCase) throws MtsException, ParseException, CdsException {
        final String METHODNAME = "setImmunityDetailsOnIceTestDTO ";

        if (iceTestDTO == null) {
            throw new MtsException(METHODNAME + "iceTestDTO is null!");
        }

        if (testCase != null) {
            for (ObservationResult r : testCase.getImmunityObservationResults()) {
                IceTestImmunityDTO cdsOR = new IceTestImmunityDTO();
                cdsOR.setImmunityFocus(CdsCodeDTOObjectFactory.getCdsCodeDTO(Config.getCodeSystemOid("DISEASE"), r.getObservationFocus().getCode()));
                cdsOR.setImmunityValue(CdsCodeDTOObjectFactory.getCdsCodeDTO(Config.getCodeSystemOid("IMMUNITY_VALUE"), r.getObservationValue().getConcept().getCode()));
                cdsOR.setObservationEventTime(DateUtils.parseISODateFormat(r.getObservationEventTime().getHigh()));
                iceTestDTO.addOrUpdateChildDTO(cdsOR);
            }
        } else {
            logger.error(METHODNAME, "testCase was null!");
        }
    }

    /**
     * Set the immunization details on the test case.
     *
     * @param iceTestDTO
     * @param testCase
     * @param cdsVersionDTO
     * @throws MtsException
     */
    private void setImmunizationDetailsOnIceTestDTO(IceTestDTO iceTestDTO, TestCaseWrapper testCase, CdsVersionDTO cdsVersionDTO)
            throws MtsException, ParseException, NotFoundException, CdsException {
        final String METHODNAME = "setImmunizationDetailsOnIceTestDTO ";

        if (iceTestDTO == null) {
            throw new MtsException(METHODNAME + "iceTestDTO is null!");
        }

        if (testCase != null) {
            int cCount = 0;
            int eCount = 0;
            for (SubstanceAdministrationEvent sae : testCase.getSubstanceAdministrationEvents()) {

                IceTestEventDTO iceTestEventDTO = new IceTestEventDTO();
                iceTestDTO.addOrUpdateChildDTO(iceTestEventDTO);
                logger.trace("Number of Events in object: ", iceTestDTO.getChildrenDTOs(IceTestEventDTO.ByTestId.class).size());
                logger.trace("Adding event: ", sae.getSubstance().getSubstanceCode().getCode(), " to ", iceTestDTO.getName());
                Date administrationTime = DateUtils.parseISODateFormat(sae.getAdministrationTimeInterval().getHigh());
                iceTestEventDTO.setAdministrationTime(administrationTime);
                IceVaccineDTO eventVaccine = IceVaccineObjectFactory.getIceVaccineDTO(sae.getSubstance().getSubstanceCode().getCode(), cdsVersionDTO);
                if (eventVaccine == null) {
                    throw new MtsException("Event substance code not found in vaccine map: " + sae.getSubstance().getSubstanceCode().getCode());
                }

                iceTestEventDTO.setIceVaccineDTO(eventVaccine);
                for (RelatedClinicalStatement rcs : sae.getRelatedClinicalStatement()) {
                    cCount++;
                    IceTestEventComponentDTO iceTestEventComponentDTO = new IceTestEventComponentDTO();
                    iceTestEventDTO.addOrUpdateChildDTO(iceTestEventComponentDTO);
                    SubstanceAdministrationEvent csae = rcs.getSubstanceAdministrationEvent();
                    Date componentAdministrationTime = DateUtils.parseISODateFormat(csae.getAdministrationTimeInterval().getHigh());
                    if (administrationTime.getTime() != componentAdministrationTime.getTime()) {
                        throw new MtsException("Event and event component times must be the same: " + administrationTime + " - " + componentAdministrationTime);
                    }
                    IceVaccineComponentDTO componentVaccine = IceVaccineObjectFactory.getIceVaccineComponentDTO(csae.getSubstance().getSubstanceCode().getCode(), eventVaccine, iceTestDTO.getIceTestVaccineGroupRelDTOs(), cdsVersionDTO);
                    if (componentVaccine == null) {
                        throw new MtsException("Event component substance code not found in vaccine map: " + csae.getSubstance().getSubstanceCode().getCode());
                    }
                    iceTestEventComponentDTO.setIceVaccineComponentDTO(componentVaccine);

                    List<RelatedClinicalStatement> relatedClinicalStatements = csae.getRelatedClinicalStatement();
                    if (relatedClinicalStatements.size() > 1) {
                        throw new MtsException("relatedClinicalStatements size was greater than 1: " + relatedClinicalStatements.size());
                    }

                    CdsCodeDTO evaluationValue = null;
                    if (csae.getIsValid().isValue()) {
                        evaluationValue = CdsCodeDTOObjectFactory.getCdsCodeDTO(Config.getCodeSystemOid("EVALUATION_VALUE"), "VALID");
                    }
                    for (RelatedClinicalStatement crcs : relatedClinicalStatements) {

                        ObservationResult observationResult = crcs.getObservationResult();
//                        String componentGroupSeq = String.valueOf(componentVaccine.getVaccineGroup());
//                        if (!componentGroupSeq.equalsIgnoreCase(observationResult.getObservationFocus().getCode())
//                                && !componentVaccine.getVaccine().equalsIgnoreCase(observationResult.getObservationFocus().getCode())) {
//                            throw new MtsException("Observation focus not equal to vaccine group sequence: "
//                                    + crcs.getObservationResult().getObservationFocus().getCode()
//                                    + " - "
//                                    + componentGroupSeq);
//                        }

                        evaluationValue = CdsCodeDTOObjectFactory.getCdsCodeDTO(Config.getCodeSystemOid("EVALUATION_VALUE"), observationResult.getObservationValue().getConcept().getCode());

                        logger.debug("Setting iceTestEventComponentDTO validity from: ", evaluationValue.getCode());

                        logger.debug("Setting iceTestEventComponentDTO group focus from: ", observationResult.getObservationFocus().getCode());
                        IceVaccineGroupDTO groupFocus = IceVaccineObjectFactory.getIceVaccineGroupDTO(observationResult.getObservationFocus().getCode(), cdsVersionDTO);
                        if (iceTestEventComponentDTO.getIceVaccineGroupDTO() != null) {
                            throw new MtsException("Component group focus already set! Existing: "
                                    + iceTestEventComponentDTO.getIceVaccineGroupDTO().getVaccineGroupCode()
                                    + " Setting: "
                                    + groupFocus.getVaccineGroupCode());
                        }
                        iceTestEventComponentDTO.setIceVaccineGroupDTO(groupFocus);

                        for (CD interpretation : observationResult.getInterpretation()) {
                            eCount++;
                            IceTestEvaluationDTO iceTestEvaluationDTO = new IceTestEvaluationDTO();
                            iceTestEvaluationDTO.setEvaluationInterpretation(CdsCodeDTOObjectFactory.getCdsCodeDTO(Config.getCodeSystemOid("EVALUATION_INTERPRETATION"), interpretation.getCode()));
                            iceTestEventComponentDTO.addOrUpdateChildDTO(iceTestEvaluationDTO);
                        }
                    }
                    iceTestEventComponentDTO.setEvaluationValue(evaluationValue);
                }

                // see if we need to add components...
                List<IceTestEventComponentDTO> iceTestEventComponentDTOs = iceTestEventDTO.getIceTestEventComponentDTOs();
                List<IceVaccineComponentRelDTO> iceVaccineComponentRelDTOs = eventVaccine.getIceVaccineComponentRelDTOs();
                if (iceVaccineComponentRelDTOs.size() > iceTestEventComponentDTOs.size() && iceTestEventComponentDTOs.size() == 1) {
                    List<IceVaccineComponentDTO> iceVaccineComponentDTOs = new ArrayList<IceVaccineComponentDTO>();
                    List<IceVaccineComponentDTO> existingIceVaccineComponentDTOs = new ArrayList<IceVaccineComponentDTO>();
                    for (IceVaccineComponentRelDTO iceVaccineComponentRelDTO : eventVaccine.getIceVaccineComponentRelDTOs()) {
                        iceVaccineComponentDTOs.add(iceVaccineComponentRelDTO.getIceVaccineComponentDTO());
                    }
                    IceTestEventComponentDTO existingIceTestEventComponentDTO = iceTestEventComponentDTOs.get(0);
                    existingIceVaccineComponentDTOs.add(existingIceTestEventComponentDTO.getIceVaccineComponentDTO());
                    for (IceVaccineComponentDTO iceVaccineComponentDTO : iceVaccineComponentDTOs) {
                        if (!existingIceVaccineComponentDTOs.contains(iceVaccineComponentDTO)) {
                            cCount++;
                            // create a new ice test vaccine component and mirror the reasons et al
                            IceTestEventComponentDTO missingIceTestEventComponentDTO = new IceTestEventComponentDTO();
                            iceTestEventDTO.addOrUpdateChildDTO(missingIceTestEventComponentDTO);

                            missingIceTestEventComponentDTO.setIceVaccineComponentDTO(iceVaccineComponentDTO);
                            IceVaccineGroupDTO groupResult = IceVaccineObjectFactory.getIceVaccineGroupDTOFromComponent(iceVaccineComponentDTO, cdsVersionDTO);
                            missingIceTestEventComponentDTO.setIceVaccineGroupDTO(groupResult);
                            missingIceTestEventComponentDTO.setEvaluationValue(CdsCodeDTOObjectFactory.getCdsCodeDTO(Config.getCodeSystemOid("EVALUATION_VALUE"), "IGNORE"));
//                            for (IceTestEvaluationDTO existingIceTestEvaluationDTO : existingIceTestEventComponentDTO.getIceTestEvaluationDTOs()) {
//                                eCount++;
//                                IceTestEvaluationDTO iceTestEvaluationDTO = new IceTestEvaluationDTO();
//                                iceTestEvaluationDTO.setEvaluationInterpretation(CdsCodeDTOObjectFactory.getCdsCodeDTO(Config.getCodeSystemOid("EVALUATION_INTERPRETATION"), "IGNORE"));
//                                missingIceTestEventComponentDTO.addOrUpdateChildDTO(iceTestEvaluationDTO);
//                            }
                        }
                    }
                }
            }

            int evaluationCount = 0;
            int componentCount = 0;
            for (IceTestEventDTO e : iceTestDTO.getChildrenDTOs(IceTestEventDTO.ByTestId.class, IceTestEventDTO.class)) {
                for (BaseDTO baseDTO2 : e.getChildrenDTOs(IceTestEventComponentDTO.ByEventId.class)) {
                    IceTestEventComponentDTO ce = (IceTestEventComponentDTO) baseDTO2;
                    componentCount++;
                    evaluationCount += ce.getChildrenDTOs(IceTestEvaluationDTO.ByEventComponentId.class).size();
                }
            }

            if (evaluationCount != eCount) {
                throw new MtsException("The number of incoming evaluations differed from the added IceTestEvaluationDTO instances. DTO Count: "
                        + evaluationCount
                        + " vs "
                        + eCount
                        + " import count");
            }
            if (componentCount != cCount) {
                throw new MtsException("The number of incoming event components differed from the added IceTestEventComponentDTO instances. DTO Count: "
                        + componentCount
                        + " vs "
                        + cCount
                        + " import count");
            }
        } else {
            logger.error(METHODNAME, "testCase was null!");
        }
    }

    /**
     * Set the proposal details on the test case.
     *
     * @param iceTestDTO
     * @param testCase
     * @throws MtsException
     */
    private void setProposalDetailsOnIceTestDTO(IceTestDTO iceTestDTO, TestCaseWrapper testCase, CdsVersionDTO cdsVersionDTO) throws
            MtsException, ParseException, CdsException, NotFoundException {
        final String METHODNAME = "setProposalDetailsOnIceTestDTO ";

        if (iceTestDTO == null) {
            throw new MtsException(METHODNAME + "iceTestDTO is null!");
        }

        if (testCase != null) {
            int rCount = 0;
            for (SubstanceAdministrationProposal sap : testCase.getSubstanceAdministrationProposals()) {

                IceTestProposalDTO iceTestProposalDTO = new IceTestProposalDTO();
                iceTestDTO.addOrUpdateChildDTO(iceTestProposalDTO);
                if (logger.isTraceEnabled()) {
                    logger.trace("sap: ", sap);
                    logger.trace("sap.getProposedAdministrationTimeInterval(): ", sap.getProposedAdministrationTimeInterval());
                }
                if (sap.getProposedAdministrationTimeInterval() != null) {
                    iceTestProposalDTO.setRecommendedDate(DateUtils.parseISODateFormat(sap.getProposedAdministrationTimeInterval().getLow()));
                    iceTestProposalDTO.setOverdueDate(DateUtils.parseISODateFormat(sap.getProposedAdministrationTimeInterval().getHigh()));
                }
                if (sap.getValidAdministrationTimeInterval() != null) {
                    iceTestProposalDTO.setEarliestDate(DateUtils.parseISODateFormat(sap.getValidAdministrationTimeInterval().getLow()));
                    iceTestProposalDTO.setLatestDate(DateUtils.parseISODateFormat(sap.getValidAdministrationTimeInterval().getHigh()));
                }
                if (logger.isDebugEnabled()) {
                    logger.info("sap.getSubstance(): ", sap.getSubstance());
                    logger.info("sap.getSubstance().getSubstanceCode(): ", sap.getSubstance().getSubstanceCode());
                    logger.info("sap.getSubstance().getSubstanceCode().getCodeSystem(): ", sap.getSubstance().getSubstanceCode().getCodeSystem());
                    logger.info("sap.getSubstance().getSubstanceCode().getCode(): ", sap.getSubstance().getSubstanceCode().getCode());
                }
                String codeSystem = sap.getSubstance().getSubstanceCode().getCodeSystem();
                if (Config.getCodeSystemOid("VACCINE_GROUP").equalsIgnoreCase(codeSystem)) {
                    iceTestProposalDTO.setProposalType(ProposalType.VACCINE_GROUP);
                    if (sap.getSubstance().getSubstanceCode().getCode() != null) {
                        IceVaccineGroupDTO proposedVaccineGroup = IceVaccineObjectFactory.getIceVaccineGroupDTO(sap.getSubstance().getSubstanceCode().getCode(), cdsVersionDTO);
                        if (proposedVaccineGroup == null) {
                            throw new MtsException("Proposal substance not found in vaccine group map: " + sap.getSubstance().getSubstanceCode().getCode());
                        }
                        iceTestProposalDTO.setIceVaccineGroupDTO(proposedVaccineGroup);
                    } else {
                        iceTestProposalDTO.setIceVaccineGroupDTO(new IceVaccineGroupDTO());
                    }
                    iceTestProposalDTO.setIceVaccineDTO(new IceVaccineDTO());
                } else if (Config.getCodeSystemOid("VACCINE").equalsIgnoreCase(codeSystem)) {
                    iceTestProposalDTO.setProposalType(ProposalType.VACCINE);
                    if (sap.getSubstance().getSubstanceCode().getCode() != null) {
                        IceVaccineDTO proposedVaccine = IceVaccineObjectFactory.getIceVaccineDTO(sap.getSubstance().getSubstanceCode().getCode(), cdsVersionDTO);
                        if (proposedVaccine == null) {
                            throw new MtsException("Proposed substance code not found in vaccine map: " + sap.getSubstance().getSubstanceCode().getCode());
                        }
                        iceTestProposalDTO.setIceVaccineDTO(proposedVaccine);
                    } else {
                        iceTestProposalDTO.setIceVaccineDTO(new IceVaccineDTO());
                    }
                    iceTestProposalDTO.setIceVaccineGroupDTO(new IceVaccineGroupDTO());
                } else {
                    throw new MtsException("Code system unrecognized in this context: " + codeSystem);
                }
                List<RelatedClinicalStatement> relatedClinicalStatements = sap.getRelatedClinicalStatement();
                if (relatedClinicalStatements.size() != 1) {
                    throw new MtsException("SubstanceAdministrationProposal should have one and only one relatedClinicalStatement: " + relatedClinicalStatements.size());
                }
                RelatedClinicalStatement rcs = relatedClinicalStatements.get(0);

                rCount++;
                ObservationResult observationResult = rcs.getObservationResult();
                CD observationFocus = null;
                if (observationResult != null) {
                    observationFocus = observationResult.getObservationFocus();
                }
                String focusCode = null;
                if (observationFocus != null) {
                    focusCode = observationFocus.getCode();
                }
                IceVaccineGroupDTO groupFocus = IceVaccineObjectFactory.getIceVaccineGroupDTO(focusCode, cdsVersionDTO);
                if (groupFocus != null) {
                    iceTestProposalDTO.setIceVaccineGroupDTO(groupFocus);
                } else {
                    iceTestProposalDTO.setIceVaccineGroupDTO(new IceVaccineGroupDTO());
                }
                if (Config.getCodeSystemOid("VACCINE_GROUP").equalsIgnoreCase(codeSystem)) {
                    if (iceTestProposalDTO.getIceVaccineGroupDTO().getVaccineGroupCode() != null) {
                        if (!iceTestProposalDTO.getIceVaccineGroupDTO().getVaccineGroupCode().equalsIgnoreCase(focusCode)) {
                            throw new MtsException("Observation focus not equal to vaccine group sequence: "
                                    + iceTestProposalDTO.getIceVaccineGroupDTO().getVaccineGroupCode() + " - " + focusCode);
                        }
                    } else {
                        logger.warn("Proposal vaccine group code unset!");
                    }
                } else if (Config.getCodeSystemOid("VACCINE").equalsIgnoreCase(codeSystem)) {
                    boolean vaccineInFocusGroup = false;
                    for (CdsCodeDTO cdsCodeDTO : iceTestProposalDTO.getIceVaccineDTO().getRelatedGroups()) {
                        if (cdsCodeDTO != null && focusCode != null && focusCode.equals(cdsCodeDTO.getCode())) {
                            vaccineInFocusGroup = true;
                            break;
                        }
                    }
                    if (!vaccineInFocusGroup) {
                        if (focusCode != null
                                && focusCode.equalsIgnoreCase(
                                        (iceTestProposalDTO.getIceVaccineDTO() != null
                                        && iceTestProposalDTO.getIceVaccineDTO().getVaccine() != null)
                                        ? iceTestProposalDTO.getIceVaccineDTO().getVaccine().getCode()
                                        : null)
                                && (iceTestProposalDTO.getIceVaccineDTO().getRelatedGroups().size() == 1
                                || iceTestDTO.getIceTestVaccineGroupRelDTOs().size() == 1)) {
                            if (iceTestProposalDTO.getIceVaccineDTO().getRelatedGroups().size() == 1) {
                                focusCode = iceTestProposalDTO.getIceVaccineDTO().getRelatedGroups().get(0).getCode();
                            } else {
                                focusCode = iceTestDTO.getIceTestVaccineGroupRelDTOs().get(0).getIceVaccineGroupDTO().getVaccineGroupCode();
                            }
                            groupFocus = IceVaccineObjectFactory.getIceVaccineGroupDTO(focusCode, cdsVersionDTO);
                            iceTestProposalDTO.setIceVaccineGroupDTO(groupFocus);
                        } else {
                            logger.error("Observation focus does not contain the selected vaccine: "
                                    + iceTestProposalDTO.getIceVaccineDTO()
                                    + " - " + iceTestProposalDTO.getIceVaccineDTO().getRelatedGroupList()
                                    + " - " + focusCode);
                        }
                    }
                } else {
                    throw new MtsException("Observation focus not a valid codeSystem: " + codeSystem);
                }
                if (observationResult != null) {
                    iceTestProposalDTO.setRecommendationValue(CdsCodeDTOObjectFactory.getCdsCodeDTO(Config.getCodeSystemOid("RECOMMENDATION_VALUE"), observationResult.getObservationValue().getConcept().getCode()));
                    for (CD cd : observationResult.getInterpretation()) {
                        IceTestRecommendationDTO iceTestRecommendationDTO = new IceTestRecommendationDTO();
                        iceTestRecommendationDTO.setRecommendationInterpretation(CdsCodeDTOObjectFactory.getCdsCodeDTO(Config.getCodeSystemOid("RECOMMENDATION_INTERPRETATION"), cd.getCode()));
                        iceTestProposalDTO.addOrUpdateChildDTO(iceTestRecommendationDTO);
                    }
                } else {
                    logger.warn(METHODNAME, "observationResult was null!");
                }
            }
        } else {
            logger.error(METHODNAME, "testCase was null!");
        }
    }

    /**
     * Set the meta data on the test case.
     *
     * @param iceTestDTO
     * @param testCase
     * @throws MtsException
     */
    private void setMetadataOnIceTestDTO(IceTestDTO iceTestDTO, TestCaseWrapper testCase, CdsVersionDTO cdsVersionDTO) throws
            MtsException, ParseException, NotFoundException {
        final String METHODNAME = "setMetadataOnIceTestDTO ";

        if (iceTestDTO == null) {
            throw new MtsException(METHODNAME + "iceTestDTO is null!");
        }

        if (testCase != null) {
            if (testCase.getPatientBirthTime() == null || testCase.getPatientBirthTime().trim().isEmpty()) {
                throw new MtsException("BirthDate can't be null");
            }
            iceTestDTO.setIgnore(testCase.isIgnore());
            iceTestDTO.setDob(testCase.getPatientBirthDate());
            iceTestDTO.setExecutionDate(testCase.getExecutiondate());
            iceTestDTO.setGender(CdsCodeDTOObjectFactory.getCdsCodeDTO(Config.getCodeSystemOid("GENDER"), testCase.getPatientGender().getCode()));
            iceTestDTO.setName(testCase.getName());
            logger.trace("Importing test: ", testCase.getName());
            iceTestDTO.setNotes(testCase.getNotes());
            iceTestDTO.setRuleToTest(testCase.getRuletotest());

            String patientId = testCase.getPatientId();
            logger.trace(METHODNAME, "got patient ID: ", patientId);
            if (patientId != null) {
                if (UuidUtils.isUuid(patientId)) {
                    iceTestDTO.setTestId(patientId);
                }
            }

            try {
                for (TestCaseProperty testCaseProperty : testCase.getProperties()) {
                    if ("vaccineGroup".equalsIgnoreCase(testCaseProperty.getPropertyName())) {
                        IceVaccineGroupDTO iceVaccineGroupDTO = null;
                        String propertyValue = testCaseProperty.getPropertyValue();
                        int lastIndexOfOpen = propertyValue.lastIndexOf("(");
                        int lastIndexOfClose = propertyValue.lastIndexOf(")");
                        if (lastIndexOfOpen > -1 && lastIndexOfOpen < lastIndexOfClose) {
                            String code = propertyValue.substring(lastIndexOfOpen + 1, lastIndexOfClose);
                            String displayName = propertyValue.substring(0, lastIndexOfOpen);
                            iceVaccineGroupDTO = IceVaccineObjectFactory.getIceVaccineGroupDTO(code, cdsVersionDTO);
                            if (iceVaccineGroupDTO == null) {
                                iceVaccineGroupDTO = IceVaccineObjectFactory.getIceVaccineGroupDTO(displayName, cdsVersionDTO);
                            }
                        } else {
                            iceVaccineGroupDTO = IceVaccineObjectFactory.getIceVaccineGroupDTO(propertyValue, cdsVersionDTO);
                        }
                        if (iceVaccineGroupDTO == null) {
                            throw new MtsException(METHODNAME + "Vaccine group not found: " + testCaseProperty.getPropertyValue());
                        }
                        IceTestVaccineGroupRelDTO iceTestVaccineGroupRelDTO = new IceTestVaccineGroupRelDTO();
                        iceTestVaccineGroupRelDTO.setIceVaccineGroupDTO(iceVaccineGroupDTO);
                        iceTestDTO.addOrUpdateChildDTO(iceTestVaccineGroupRelDTO);
                    } else if ("offsetBased".equalsIgnoreCase(testCaseProperty.getPropertyName())) {
                        iceTestDTO.setOffsetBased(Boolean.parseBoolean(testCaseProperty.getPropertyValue()));
                    } else if ("ageOffset".equalsIgnoreCase(testCaseProperty.getPropertyName())) {
                        iceTestDTO.setOffset(testCaseProperty.getPropertyValue());
                    }
                }
            } catch (NumberFormatException e) {
                // iceVaccineGroupDTO is null...
            } catch (IndexOutOfBoundsException e) {
                // getFocuses is empty
            }
        } else {
            logger.error(METHODNAME, "testCase was null!");
        }
    }
}
