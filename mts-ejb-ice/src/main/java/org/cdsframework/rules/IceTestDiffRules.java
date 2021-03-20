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
package org.cdsframework.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.IceTestResultDTO;
import org.cdsframework.util.CdsOutputDiffUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.support.cds.Config;
import org.opencds.vmr.v1_0.schema.CDSOutput;
import org.opencds.vmr.v1_0.schema.CD;
import org.opencds.vmr.v1_0.schema.II;
import org.opencds.vmr.v1_0.schema.TS;
import org.opencds.vmr.v1_0.schema.EvaluatedPerson;
import org.opencds.vmr.v1_0.schema.EvaluatedPerson.ClinicalStatements;
import org.opencds.vmr.v1_0.schema.EvaluatedPerson.ClinicalStatements.ObservationResults;
import org.opencds.vmr.v1_0.schema.EvaluatedPerson.ClinicalStatements.SubstanceAdministrationEvents;
import org.opencds.vmr.v1_0.schema.EvaluatedPerson.ClinicalStatements.SubstanceAdministrationProposals;
import org.opencds.vmr.v1_0.schema.EvaluatedPerson.Demographics;
import org.opencds.vmr.v1_0.schema.ObservationResult;
import org.opencds.vmr.v1_0.schema.RelatedClinicalStatement;
import org.opencds.vmr.v1_0.schema.SubstanceAdministrationProposal;
import org.opencds.vmr.v1_0.schema.VMR;

/**
 *
 * @author HLN Consulting, LLC
 */
public class IceTestDiffRules {

    private final static LogUtils logger = LogUtils.getLogger(IceTestDiffRules.class);

    /**
     * entry point into CdsOutput diff check
     *
     * @param testVaccineGroupFocus
     * @param iceOutput
     * @param assertedOutput
     * @param iceTestResultDTO
     */
    public static void iceTestOutputDiff(Map<CdsCodeDTO, List<CdsCodeDTO>> testVaccineGroupFocus, CDSOutput iceOutput, CDSOutput assertedOutput, IceTestResultDTO iceTestResultDTO) {
        boolean outputNull = CdsOutputDiffUtils.isNullAndNotNull(iceOutput, assertedOutput, "CdsOutput", iceTestResultDTO, false);
        if (!outputNull) {
            VMR iceVmr = iceOutput.getVmrOutput();
            VMR assertedVmr = assertedOutput.getVmrOutput();
            boolean vmrNull = CdsOutputDiffUtils.isNullAndNotNull(iceVmr, assertedVmr, "VMR", iceTestResultDTO, false);
            if (!vmrNull) {
                EvaluatedPerson iceEvaluatedPerson = iceVmr.getPatient();
                EvaluatedPerson assertedEvaluatedPerson = assertedVmr.getPatient();
                diffEvaluatedPerson(testVaccineGroupFocus, iceEvaluatedPerson, assertedEvaluatedPerson, iceTestResultDTO);
            }
        }
    }

    /**
     * diff EvaluatedPerson objects
     *
     * @param testVaccineGroupFocus
     * @param iceEvaluatedPerson
     * @param assertedEvaluatedPerson
     * @param iceTestResultDTO
     */
    private static void diffEvaluatedPerson(Map<CdsCodeDTO, List<CdsCodeDTO>> testVaccineGroupFocus, EvaluatedPerson iceEvaluatedPerson, EvaluatedPerson assertedEvaluatedPerson, IceTestResultDTO iceTestResultDTO) {
        boolean evaluatedPersonNull = CdsOutputDiffUtils.isNullAndNotNull(iceEvaluatedPerson, assertedEvaluatedPerson, "EvaluatedPerson", iceTestResultDTO, false);
        if (!evaluatedPersonNull) {
            II iceId = iceEvaluatedPerson.getId();
            II assertedId = assertedEvaluatedPerson.getId();
            CdsOutputDiffUtils.diffValues(iceId, assertedId, "EvaluatedPerson Id", Config.getCodeSystemOid("CDS_INPUT_ROOT"), iceTestResultDTO);
            Demographics iceDemographics = iceEvaluatedPerson.getDemographics();
            Demographics assertedDemographics = assertedEvaluatedPerson.getDemographics();
            diffDemographics(iceDemographics, assertedDemographics, iceTestResultDTO);
            ClinicalStatements iceClinicalStatements = iceEvaluatedPerson.getClinicalStatements();
            ClinicalStatements assertedClinicalStatements = assertedEvaluatedPerson.getClinicalStatements();
            diffClinicalStatements(testVaccineGroupFocus, iceClinicalStatements, assertedClinicalStatements, iceTestResultDTO);
        }
    }

    /**
     * diff Demographics objects
     *
     * @param iceDemographics
     * @param assertedDemographics
     * @param iceTestResultDTO
     */
    private static void diffDemographics(Demographics iceDemographics, Demographics assertedDemographics, IceTestResultDTO iceTestResultDTO) {
        boolean demographicsNull = CdsOutputDiffUtils.isNullAndNotNull(iceDemographics, assertedDemographics, "Demographics", iceTestResultDTO, false);
        if (!demographicsNull) {
            TS iceBirthTime = iceDemographics.getBirthTime();
            TS assertedBirthTime = assertedDemographics.getBirthTime();
            CdsOutputDiffUtils.diffValues(iceBirthTime, assertedBirthTime, "birth date", iceTestResultDTO, false);
            CD iceGender = iceDemographics.getGender();
            CD assertedGender = assertedDemographics.getGender();
            CdsOutputDiffUtils.diffValues(iceGender, assertedGender, "Gender", iceTestResultDTO);
        }
    }

    /**
     * diff ClinicalStatements objects
     *
     * @param testVaccineGroupFocus
     * @param iceClinicalStatements
     * @param assertedClinicalStatements
     * @param iceTestResultDTO
     */
    private static void diffClinicalStatements(
            Map<CdsCodeDTO, List<CdsCodeDTO>> testVaccineGroupFocus,
            ClinicalStatements iceClinicalStatements,
            ClinicalStatements assertedClinicalStatements,
            IceTestResultDTO iceTestResultDTO) {
        boolean clinicalStatementsNull = CdsOutputDiffUtils.isNullAndNotNull(iceClinicalStatements, assertedClinicalStatements, "ClinicalStatements", iceTestResultDTO, false);
        if (!clinicalStatementsNull) {
            ObservationResults iceObservationResults = iceClinicalStatements.getObservationResults();
            ObservationResults assertedObservationResults = assertedClinicalStatements.getObservationResults();
            diffObservationResults(iceObservationResults, assertedObservationResults, iceTestResultDTO);
            SubstanceAdministrationEvents iceSubstanceAdministrationEvents = iceClinicalStatements.getSubstanceAdministrationEvents();
            SubstanceAdministrationEvents assertedSubstanceAdministrationEvents = assertedClinicalStatements.getSubstanceAdministrationEvents();
            diffSubstanceAdministrationEvents(testVaccineGroupFocus, iceSubstanceAdministrationEvents, assertedSubstanceAdministrationEvents, iceTestResultDTO);
            if (iceTestResultDTO.getDifferenceLog().isEmpty()) {
                iceTestResultDTO.setEvaluationPassed(true);
            } else {
                iceTestResultDTO.setEvaluationPassed(false);
            }
            List<String> evaluationDifferenceLog = iceTestResultDTO.getDifferenceLog();
            iceTestResultDTO.setDifferenceLog(new ArrayList<String>());

            SubstanceAdministrationProposals iceSubstanceAdministrationProposals = iceClinicalStatements.getSubstanceAdministrationProposals();
            SubstanceAdministrationProposals assertedSubstanceAdministrationProposals = assertedClinicalStatements.getSubstanceAdministrationProposals();
            diffSubstanceAdministrationProposals(testVaccineGroupFocus, iceSubstanceAdministrationProposals, assertedSubstanceAdministrationProposals, iceTestResultDTO);
            if (iceTestResultDTO.getDifferenceLog().isEmpty()) {
                iceTestResultDTO.setRecommendationPassed(true);
            } else {
                iceTestResultDTO.setRecommendationPassed(false);
            }
            evaluationDifferenceLog.addAll(iceTestResultDTO.getDifferenceLog());
            iceTestResultDTO.setDifferenceLog(evaluationDifferenceLog);
            if (iceTestResultDTO.isEvaluationPassed() && iceTestResultDTO.isRecommendationPassed()) {
                iceTestResultDTO.setPassed(true);
            } else {
                iceTestResultDTO.setPassed(false);
            }
            logger.info("Test Passed: ", iceTestResultDTO.isPassed());
            logger.info("Test Evaluation Passed: ", iceTestResultDTO.isEvaluationPassed());
            logger.info("Test Recommendation Passed: ", iceTestResultDTO.isRecommendationPassed());
        }
    }

    /**
     * diff ObservationResults
     *
     * @param iceObservationResults
     * @param assertedObservationResults
     * @param iceTestResultDTO
     */
    private static void diffObservationResults(
            ObservationResults iceObservationResults,
            ObservationResults assertedObservationResults,
            IceTestResultDTO iceTestResultDTO) {
        boolean observationResultsNull = CdsOutputDiffUtils.isNullAndNotNull(
                iceObservationResults,
                assertedObservationResults,
                "Antigen",
                iceTestResultDTO,
                false);
        if (!observationResultsNull) {
            List<ObservationResult> iceObservationResultList = iceObservationResults.getObservationResult();
            List<ObservationResult> assertedObservationResultList = assertedObservationResults.getObservationResult();
            CdsOutputDiffUtils.diffObservationResultLists(
                    iceObservationResultList,
                    assertedObservationResultList,
                    "Antigen",
                    Config.getCodeSystemOid("DISEASE"),
                    Config.getCodeSystemOid("IMMUNITY_VALUE"),
                    Config.getCodeSystemOid("IMMUNITY_INTERPRETATION"),
                    iceTestResultDTO);
        }
    }

    /**
     * diff SubstanceAdministrationEvents
     *
     * @param testVaccineGroupFocus
     * @param iceSubstanceAdministrationEvents
     * @param assertedSubstanceAdministrationEvents
     * @param iceTestResultDTO
     */
    public static void diffSubstanceAdministrationEvents(
            Map<CdsCodeDTO, List<CdsCodeDTO>> testVaccineGroupFocus,
            SubstanceAdministrationEvents iceSubstanceAdministrationEvents,
            SubstanceAdministrationEvents assertedSubstanceAdministrationEvents,
            IceTestResultDTO iceTestResultDTO) {
        if (iceSubstanceAdministrationEvents == null && assertedSubstanceAdministrationEvents == null) {
            return;
        }
        boolean substanceAdministrationEventsNull = CdsOutputDiffUtils.isNullAndNotNull(
                iceSubstanceAdministrationEvents,
                assertedSubstanceAdministrationEvents,
                "Immunization",
                iceTestResultDTO,
                false);
        if (!substanceAdministrationEventsNull) {
            CdsOutputDiffUtils.diffValues(
                    testVaccineGroupFocus,
                    iceSubstanceAdministrationEvents,
                    assertedSubstanceAdministrationEvents,
                    "Immunization",
                    iceTestResultDTO);
        }
    }

    /**
     * diff SubstanceAdministrationProposals
     *
     * @param testVaccineGroupFocus
     * @param iceSubstanceAdministrationProposals
     * @param assertedSubstanceAdministrationProposals
     * @param iceTestResultDTO
     */
    private static void diffSubstanceAdministrationProposals(
            Map<CdsCodeDTO, List<CdsCodeDTO>> testVaccineGroupFocus,
            SubstanceAdministrationProposals iceSubstanceAdministrationProposals,
            SubstanceAdministrationProposals assertedSubstanceAdministrationProposals,
            IceTestResultDTO iceTestResultDTO) {
        final String METHODNAME = "diffSubstanceAdministrationProposals ";
        boolean substanceAdministrationProposalsNull = CdsOutputDiffUtils.isNullAndNotNull(
                iceSubstanceAdministrationProposals,
                assertedSubstanceAdministrationProposals,
                "Recommendation",
                iceTestResultDTO,
                false);
        Iterator<SubstanceAdministrationProposal> iceSubstanceAdministrationProposalIterator = null;
        if (iceSubstanceAdministrationProposals != null) {
            if (assertedSubstanceAdministrationProposals != null) {
                if (iceSubstanceAdministrationProposals.getSubstanceAdministrationProposal() != null) {
                    iceSubstanceAdministrationProposalIterator = iceSubstanceAdministrationProposals.getSubstanceAdministrationProposal().iterator();
                }
                if (iceSubstanceAdministrationProposalIterator != null) {
                    while (iceSubstanceAdministrationProposalIterator.hasNext()) {
                        SubstanceAdministrationProposal substanceAdministrationProposal = iceSubstanceAdministrationProposalIterator.next();
                        if (substanceAdministrationProposal != null) {
                            boolean found = false;

                            List<RelatedClinicalStatement> relatedClinicalStatements = substanceAdministrationProposal.getRelatedClinicalStatement();

                            if (relatedClinicalStatements != null) {
                                for (RelatedClinicalStatement relatedClinicalStatement : relatedClinicalStatements) {
                                    if (relatedClinicalStatement != null) {
                                        ObservationResult observationResult = relatedClinicalStatement.getObservationResult();
                                        if (observationResult != null) {
                                            CD observationFocus = observationResult.getObservationFocus();
                                            if (observationFocus != null) {
                                                for (CdsCodeDTO group : testVaccineGroupFocus.keySet()) {
                                                    if (group.getCode().equals(observationFocus.getCode())) {
                                                        logger.debug("Found proposal focus in vaccine group focus: ", group.getDisplayName());
                                                        found = true;
                                                        break;
                                                    }
                                                }
                                            } else {
                                                logger.warn(METHODNAME, "observationFocus is null!");
                                            }
                                        } else {
                                            logger.warn(METHODNAME, "observationResult is null!");
                                        }
                                    } else {
                                        logger.warn(METHODNAME, "relatedClinicalStatement is null!");
                                    }
                                }
                            } else {
                                logger.warn(METHODNAME, "relatedClinicalStatements is null!");
                            }
                            if (!found) {
                                logger.info(METHODNAME, "Removing substance administration proposal - it wasn't found in the test group focus!");
                                iceSubstanceAdministrationProposalIterator.remove();
                            }
                        } else {
                            logger.warn(METHODNAME, "substanceAdministrationProposal is null!");
                        }
                    }
                } else {
                    logger.warn(METHODNAME, "iceSubstanceAdministrationProposalIterator is null!");
                }
            } else {
                logger.warn(METHODNAME, "assertedSubstanceAdministrationProposals is null!");
            }
        } else {
            logger.warn(METHODNAME, "iceSubstanceAdministrationProposals is null!");
        }
        if (!substanceAdministrationProposalsNull) {
            CdsOutputDiffUtils.diffValues(
                    testVaccineGroupFocus,
                    iceSubstanceAdministrationProposals,
                    assertedSubstanceAdministrationProposals,
                    "Recommendation",
                    iceTestResultDTO);
        }
    }
}
