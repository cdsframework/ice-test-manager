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
package org.cdsframework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceTestResultDTO;
import org.opencds.vmr.v1_0.schema.CD;
import org.opencds.vmr.v1_0.schema.II;
import org.opencds.vmr.v1_0.schema.IVLTS;
import org.opencds.vmr.v1_0.schema.TS;
import org.opencds.vmr.v1_0.schema.AdministrableSubstance;
import org.opencds.vmr.v1_0.schema.EvaluatedPerson.ClinicalStatements.SubstanceAdministrationEvents;
import org.opencds.vmr.v1_0.schema.EvaluatedPerson.ClinicalStatements.SubstanceAdministrationProposals;
import org.opencds.vmr.v1_0.schema.ObservationResult;
import org.opencds.vmr.v1_0.schema.ObservationResult.ObservationValue;
import org.opencds.vmr.v1_0.schema.RelatedClinicalStatement;
import org.opencds.vmr.v1_0.schema.SubstanceAdministrationEvent;
import org.opencds.vmr.v1_0.schema.SubstanceAdministrationProposal;

/**
 *
 * @author HLN Consulting, LLC
 */
public class CdsOutputDiffUtils {

    private final static LogUtils logger = LogUtils.getLogger(CdsOutputDiffUtils.class);
    private final static boolean DEBUG = false;

    /**
     * diff strings
     *
     * @param iceString
     * @param assertedString
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    public static boolean diffValues(String iceString, String assertedString, String objectName, IceTestResultDTO iceTestResultDTO) {
        boolean result = isNullAndNotNull(iceString, assertedString, objectName, iceTestResultDTO, false);
        if (!result) {
            if (!iceString.equalsIgnoreCase(assertedString)) {
                result = true;
                iceTestResultDTO.getDifferenceLog().add(
                        objectName
                        + " values do not match: <strong>ICE</strong>="
                        + iceString
                        + "; <strong>EXPECTED</strong>="
                        + assertedString);
            } else {
                iceTestResultDTO.getMatchLog().add(
                        objectName
                        + " values match: <strong>ICE</strong>="
                        + iceString
                        + "; <strong>EXPECTED</strong>="
                        + assertedString);
                if (DEBUG) {
                    logger.info("DEBUG: " + objectName + " values matched: ICE=" + iceString + "; EXPECTED=" + assertedString);
                }
            }
        }
        return result;
    }

    /**
     * diff Dates
     *
     * @param iceDate
     * @param assertedDate
     * @param objectName
     * @param iceTestResultDTO
     * @param ignoreDiff
     * @return
     */
    public static boolean diffValues(Date iceDate, Date assertedDate, String objectName, IceTestResultDTO iceTestResultDTO, boolean ignoreDiff) {
        boolean result = isNullAndNotNull(iceDate, assertedDate, objectName, iceTestResultDTO, ignoreDiff);
        if (!result) {
            if (iceDate.getTime() != assertedDate.getTime()) {
                String message = objectName
                        + " values do not match: <strong>ICE</strong>="
                        + DateUtils.getFormattedDate(iceDate, "MM/dd/yyyy")
                        + "; <strong>EXPECTED</strong>="
                        + DateUtils.getFormattedDate(assertedDate, "MM/dd/yyyy");
                result = true;
                if (ignoreDiff) {
                    iceTestResultDTO.getMatchLog().add("WARN: " + message);
                } else {
                    iceTestResultDTO.getDifferenceLog().add(message);
                }
            } else {
                iceTestResultDTO.getMatchLog().add(
                        objectName
                        + " values match: <strong>ICE</strong>="
                        + DateUtils.getFormattedDate(iceDate, "MM/dd/yyyy")
                        + "; <strong>EXPECTED</strong>="
                        + DateUtils.getFormattedDate(assertedDate, "MM/dd/yyyy"));
                if (DEBUG) {
                    logger.info("DEBUG: " + objectName + " values matched: ICE=" + iceDate + "; EXPECTED=" + assertedDate);
                }
            }
        }
        return result;
    }

    /**
     * diff II values
     *
     * @param iceIi
     * @param assertedIi
     * @param objectName
     * @param codeSystemOid
     * @param iceTestResultDTO
     * @return
     */
    public static boolean diffValues(II iceIi, II assertedIi, String objectName, String codeSystemOid, IceTestResultDTO iceTestResultDTO) {
        boolean result = isNullAndNotNull(iceIi, assertedIi, objectName, iceTestResultDTO, false);
        if (!result) {
            // diff root OID
            String iceRoot = iceIi.getRoot();
            String assertedRoot = assertedIi.getRoot();
            result = diffValues(iceRoot, assertedRoot, objectName + " root", iceTestResultDTO);
            // diff extensions
            String iceExtension = iceIi.getExtension();
            String assertedExtension = assertedIi.getExtension();
            if (diffValues(iceExtension, assertedExtension, objectName + " extension", iceTestResultDTO)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * diff TS objects
     *
     * @param iceTs
     * @param assertedTs
     * @param objectName
     * @param iceTestResultDTO
     * @param ignoreDiff
     * @return
     */
    public static boolean diffValues(TS iceTs, TS assertedTs, String objectName, IceTestResultDTO iceTestResultDTO, boolean ignoreDiff) {
        boolean result = isNullAndNotNull(iceTs, assertedTs, objectName, iceTestResultDTO, ignoreDiff);
        if (!result) {
            String iceTsValue = iceTs.getValue();
            String assertedTsValue = assertedTs.getValue();
            result = diffDateStringValues(iceTsValue, assertedTsValue, objectName, iceTestResultDTO, ignoreDiff);
        }
        return result;
    }

    /**
     * diff CD values
     *
     * @param iceCd
     * @param assertedCd
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    public static boolean diffValues(CD iceCd, CD assertedCd, String objectName, IceTestResultDTO iceTestResultDTO) {
        boolean result = isNullAndNotNull(iceCd, assertedCd, objectName, iceTestResultDTO, false);
        if (!result) {
            String iceCodeSystem = iceCd.getCodeSystem();
            String assertedCodeSystem = assertedCd.getCodeSystem();
            result = diffValues(iceCodeSystem, assertedCodeSystem, objectName + " code system", iceTestResultDTO);
            String iceCode = iceCd.getCode();
            String assertedCode = assertedCd.getCode();
            if (diffValues(iceCode, assertedCode, objectName + " code", iceTestResultDTO)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * diff two ObservationResult values
     *
     * @param observationResult
     * @param assertedObservationResult
     * @param objectName
     * @param focusCodeSystemOid
     * @param valueCodeSystemOid
     * @param interpretationCodeSystemOid
     * @param iceTestResultDTO
     * @return
     */
    private static boolean diffValues(
            ObservationResult iceObservationResult,
            ObservationResult assertedObservationResult,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        boolean result = false;
        if (assertedObservationResult != null
                && assertedObservationResult.getObservationValue() != null
                && assertedObservationResult.getObservationValue().getConcept() != null
                && "IGNORE".equalsIgnoreCase(assertedObservationResult.getObservationValue().getConcept().getCode())) {
            logger.warn("Asserted ObservationResult observationValue is IGNORE - nor comparing");
        } else if (iceObservationResult != null && assertedObservationResult != null) {
            result = diffValues(
                    iceObservationResult.getObservationFocus(),
                    assertedObservationResult.getObservationFocus(),
                    objectName + " focus",
                    iceTestResultDTO);
            if (diffValues(
                    iceObservationResult.getObservationValue().getConcept(),
                    assertedObservationResult.getObservationValue().getConcept(),
                    objectName + " value",
                    iceTestResultDTO)) {
                result = true;
            }
            if (diffInterpretationLists(
                    iceObservationResult.getInterpretation(),
                    assertedObservationResult.getInterpretation(),
                    objectName + " reason",
                    iceTestResultDTO)) {
                result = true;
            }
        } else {
            result = true;
            logger.error(
                    "ICE or EXPECTED ObservationResult is missing: ",
                    objectName,
                    "; ICE=",
                    iceObservationResult,
                    "; EXPECTED=", assertedObservationResult);
        }
        return result;
    }

    /**
     * diff SubstanceAdministrationProposals
     *
     * @param testVaccineGroupFocus
     * @param iceSubstanceAdministrationProposals
     * @param assertedSubstanceAdministrationProposals
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    public static boolean diffValues(
            Map<CdsCodeDTO, List<CdsCodeDTO>> testVaccineGroupFocus,
            SubstanceAdministrationProposals iceSubstanceAdministrationProposals,
            SubstanceAdministrationProposals assertedSubstanceAdministrationProposals,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        List<SubstanceAdministrationProposal> iceSubstanceAdministrationProposalList = iceSubstanceAdministrationProposals.getSubstanceAdministrationProposal();
        List<SubstanceAdministrationProposal> assertedSubstanceAdministrationProposalList = assertedSubstanceAdministrationProposals.getSubstanceAdministrationProposal();
        boolean result = isNullAndNotNull(
                iceSubstanceAdministrationProposalList,
                assertedSubstanceAdministrationProposalList,
                "SubstanceAdministrationProposal Lists",
                iceTestResultDTO,
                false);
        if (!result) {
            Map<String, SubstanceAdministrationProposal> iceSubstanceAdministrationProposalMap
                    = getSubstanceAdministrationProposalMap(iceSubstanceAdministrationProposalList, objectName, iceTestResultDTO);
            Map<String, SubstanceAdministrationProposal> assertedSubstanceAdministrationProposalMap
                    = getSubstanceAdministrationProposalMap(assertedSubstanceAdministrationProposalList, objectName, iceTestResultDTO);
            result = diffValues(
                    iceSubstanceAdministrationProposalMap,
                    assertedSubstanceAdministrationProposalMap,
                    objectName,
                    iceTestResultDTO);
            for (String key : getCombinedKeyList(iceSubstanceAdministrationProposalMap, assertedSubstanceAdministrationProposalMap)) {
                SubstanceAdministrationProposal iceSubstanceAdministrationProposal = iceSubstanceAdministrationProposalMap.get(key);
                SubstanceAdministrationProposal assertedSubstanceAdministrationProposal = assertedSubstanceAdministrationProposalMap.get(key);
                if (diffValues(
                        testVaccineGroupFocus,
                        iceSubstanceAdministrationProposal,
                        assertedSubstanceAdministrationProposal,
                        objectName,
                        iceTestResultDTO)) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * diff two SubstanceAdministrationProposal objects
     *
     * @param testVaccineGroupFocus
     * @param iceSubstanceAdministrationEvent
     * @param assertedSubstanceAdministrationEvent
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    private static boolean diffValues(
            Map<CdsCodeDTO, List<CdsCodeDTO>> testVaccineGroupFocus,
            SubstanceAdministrationProposal iceSubstanceAdministrationProposal,
            SubstanceAdministrationProposal assertedSubstanceAdministrationProposal,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        boolean result = false;
        boolean evalAllDates = false;
        if (iceTestResultDTO != null && iceTestResultDTO.getIceTestDTO() != null) {
            evalAllDates = iceTestResultDTO.getIceTestDTO().isEvaluateAllDates();
        }
        if (iceSubstanceAdministrationProposal != null && assertedSubstanceAdministrationProposal != null) {
            // assert substance is equal
            AdministrableSubstance iceSubstance = iceSubstanceAdministrationProposal.getSubstance();
            AdministrableSubstance assertedSubstance = assertedSubstanceAdministrationProposal.getSubstance();
            result = diffValues(iceSubstance, assertedSubstance, objectName, iceTestResultDTO);

            IVLTS iceProposedAdministrationTimeInterval = iceSubstanceAdministrationProposal.getProposedAdministrationTimeInterval();
            IVLTS assertedProposedAdministrationTimeInterval = assertedSubstanceAdministrationProposal.getProposedAdministrationTimeInterval();
            if (diffValuesLow(iceProposedAdministrationTimeInterval, assertedProposedAdministrationTimeInterval, objectName + " Recommended Date", iceTestResultDTO, true, true, false)) {
                result = true;
            }
            if (evalAllDates) {
                if (diffValuesHigh(iceProposedAdministrationTimeInterval, assertedProposedAdministrationTimeInterval, objectName + " Overdue Date", iceTestResultDTO, true, true, false)) {
                    result = true;
                }
            } else {
                if (diffValuesHigh(iceProposedAdministrationTimeInterval, assertedProposedAdministrationTimeInterval, objectName + " Overdue Date", iceTestResultDTO, false, false, true)) {
                    result = true;
                }
            }

            IVLTS iceValidAdministrationTimeInterval = iceSubstanceAdministrationProposal.getValidAdministrationTimeInterval();
            IVLTS assertedValidAdministrationTimeInterval = assertedSubstanceAdministrationProposal.getValidAdministrationTimeInterval();
            if (evalAllDates) {
                if (diffValuesLow(iceValidAdministrationTimeInterval, assertedValidAdministrationTimeInterval, objectName + " Earliest Date", iceTestResultDTO, true, true, false)) {
                    result = true;
                }
            } else {
                if (diffValuesLow(iceValidAdministrationTimeInterval, assertedValidAdministrationTimeInterval, objectName + " Earliest Date", iceTestResultDTO, false, false, true)) {
                    result = true;
                }
            }

            if (evalAllDates) {
                if (diffValuesHigh(iceValidAdministrationTimeInterval, assertedValidAdministrationTimeInterval, objectName + " Latest Date", iceTestResultDTO, true, true, false)) {
                    result = true;
                }
            } else {
                if (diffValuesHigh(iceValidAdministrationTimeInterval, assertedValidAdministrationTimeInterval, objectName + " Latest Date", iceTestResultDTO, false, false, true)) {
                    result = true;
                }
            }

            // assert related cliinical statements are equal
            List<RelatedClinicalStatement> iceRelatedClinicalStatements = iceSubstanceAdministrationProposal.getRelatedClinicalStatement();
            List<RelatedClinicalStatement> assertedRelatedClinicalStatements = assertedSubstanceAdministrationProposal.getRelatedClinicalStatement();
            if (diffRelatedClinicalStatementLists(
                    testVaccineGroupFocus,
                    iceRelatedClinicalStatements,
                    assertedRelatedClinicalStatements,
                    objectName,
                    iceTestResultDTO)) {
                result = true;
            }
        } else {
            logger.error(
                    objectName,
                    "  - ICE or EXPECTED result null - ICE=",
                    iceSubstanceAdministrationProposal,
                    "; EXPECTED=",
                    assertedSubstanceAdministrationProposal);
        }
        return result;
    }

    /**
     * diff SubstanceAdministrationEvents
     *
     * @param testVaccineGroupFocus
     * @param iceSubstanceAdministrationEvents
     * @param assertedSubstanceAdministrationEvents
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    public static boolean diffValues(
            Map<CdsCodeDTO, List<CdsCodeDTO>> testVaccineGroupFocus,
            SubstanceAdministrationEvents iceSubstanceAdministrationEvents,
            SubstanceAdministrationEvents assertedSubstanceAdministrationEvents,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        List<SubstanceAdministrationEvent> iceSubstanceAdministrationEventList = iceSubstanceAdministrationEvents.getSubstanceAdministrationEvent();
        List<SubstanceAdministrationEvent> assertedSubstanceAdministrationEventList = assertedSubstanceAdministrationEvents.getSubstanceAdministrationEvent();
        boolean result = isNullAndNotNull(
                iceSubstanceAdministrationEventList,
                assertedSubstanceAdministrationEventList,
                "SubstanceAdministrationEvent Lists",
                iceTestResultDTO,
                false);
        if (!result) {
            Map<String, SubstanceAdministrationEvent> iceSubstanceAdministrationEventMap
                    = getSubstanceAdministrationEventMap(iceSubstanceAdministrationEventList, objectName, iceTestResultDTO);
            Map<String, SubstanceAdministrationEvent> assertedSubstanceAdministrationEventMap
                    = getSubstanceAdministrationEventMap(assertedSubstanceAdministrationEventList, objectName, iceTestResultDTO);
            result = diffValues(
                    iceSubstanceAdministrationEventMap,
                    assertedSubstanceAdministrationEventMap,
                    objectName,
                    iceTestResultDTO);
            for (String key : getCombinedKeyList(iceSubstanceAdministrationEventMap, assertedSubstanceAdministrationEventMap)) {
                SubstanceAdministrationEvent iceSubstanceAdministrationEvent = iceSubstanceAdministrationEventMap.get(key);
                SubstanceAdministrationEvent assertedSubstanceAdministrationEvent = assertedSubstanceAdministrationEventMap.get(key);
                if (diffSubstanceAdministrationEventValues(
                        testVaccineGroupFocus,
                        iceSubstanceAdministrationEvent,
                        assertedSubstanceAdministrationEvent,
                        objectName,
                        iceTestResultDTO)) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * diff two SubstanceAdministrationEvent objects
     *
     * @param testVaccineGroupFocus
     * @param iceSubstanceAdministrationEvent
     * @param assertedSubstanceAdministrationEvent
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    private static boolean diffSubstanceAdministrationEventValues(
            Map<CdsCodeDTO, List<CdsCodeDTO>> testVaccineGroupFocus,
            SubstanceAdministrationEvent iceSubstanceAdministrationEvent,
            SubstanceAdministrationEvent assertedSubstanceAdministrationEvent,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        boolean result = false;
        if (iceSubstanceAdministrationEvent != null && assertedSubstanceAdministrationEvent != null) {
            // assert administration time is equal
            IVLTS iceAdministrationTimeInterval = iceSubstanceAdministrationEvent.getAdministrationTimeInterval();
            IVLTS assertedAdministrationTimeInterval = assertedSubstanceAdministrationEvent.getAdministrationTimeInterval();
            result = diffValuesHigh(iceAdministrationTimeInterval, assertedAdministrationTimeInterval, objectName, iceTestResultDTO, true, true, false);
            // assert substance is equal
            AdministrableSubstance iceSubstance = iceSubstanceAdministrationEvent.getSubstance();
            AdministrableSubstance assertedSubstance = assertedSubstanceAdministrationEvent.getSubstance();
            if (diffValues(iceSubstance, assertedSubstance, objectName, iceTestResultDTO)) {
                result = true;
            }
            // assert related cliinical statements are equal
            List<RelatedClinicalStatement> iceRelatedClinicalStatements = iceSubstanceAdministrationEvent.getRelatedClinicalStatement();
            List<RelatedClinicalStatement> assertedRelatedClinicalStatements = assertedSubstanceAdministrationEvent.getRelatedClinicalStatement();
            if (diffRelatedClinicalStatementLists(
                    testVaccineGroupFocus,
                    iceRelatedClinicalStatements,
                    assertedRelatedClinicalStatements,
                    objectName,
                    iceTestResultDTO)) {
                result = true;
            }
        } else {
            logger.error(
                    objectName,
                    "  - ICE or EXPECTED result null - ICE=",
                    iceSubstanceAdministrationEvent,
                    "; EXPECTED=",
                    assertedSubstanceAdministrationEvent);
        }
        return result;
    }

    /**
     * diff two IVLTS objects - high value
     *
     * @param iceIvlts
     * @param assertedIvlts
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    private static boolean diffValuesHigh(IVLTS iceIvlts, IVLTS assertedIvlts, String objectName, IceTestResultDTO iceTestResultDTO, boolean reportDiffIfIceNull, boolean reportDiffIfExpectedNull, boolean ignoreDiff) {
        boolean result = false;
        if (iceIvlts != null && assertedIvlts != null) {
            String iceTimestamp = iceIvlts.getHigh();
            String assertedTimestamp = assertedIvlts.getHigh();
            result = diffDateStringValues(iceTimestamp, assertedTimestamp, objectName, iceTestResultDTO, ignoreDiff);
        } else if (iceIvlts != null && assertedIvlts == null && reportDiffIfExpectedNull) {
            String iceTimestamp = iceIvlts.getHigh();
            Date iceParsedDate = parseDate(iceTimestamp);
            String message = objectName
                    + " does not match: <strong>ICE</strong>="
                    + DateUtils.getFormattedDate(iceParsedDate, "MM/dd/yyyy")
                    + "; <strong>EXPECTED</strong>=&lt;null&gt;";
            result = true;
            if (ignoreDiff) {
                iceTestResultDTO.getMatchLog().add("WARN: " + message);
            } else {
                iceTestResultDTO.getDifferenceLog().add(message);
            }
        } else if (iceIvlts == null && assertedIvlts != null && reportDiffIfIceNull) {
            String assertedTimestamp = assertedIvlts.getLow();
            Date assertedParsedDate = parseDate(assertedTimestamp);
            String message = objectName
                    + " does not match: <strong>ICE</strong>=&lt;null&gt;; <strong>EXPECTED</strong>="
                    + DateUtils.getFormattedDate(assertedParsedDate, "MM/dd/yyyy");
            result = true;
            if (ignoreDiff) {
                iceTestResultDTO.getMatchLog().add("WARN: " + message);
            } else {
                iceTestResultDTO.getDifferenceLog().add(message);
            }
        } else {
            if (DEBUG) {
                logger.info(objectName, " - both values are null.");
            }
        }
        return result;
    }

    /**
     * diff two IVLTS objects - Low Value
     *
     * @param iceIvlts
     * @param assertedIvlts
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    private static boolean diffValuesLow(IVLTS iceIvlts, IVLTS assertedIvlts, String objectName, IceTestResultDTO iceTestResultDTO, boolean reportDiffIfIceNull, boolean reportDiffIfExpectedNull, boolean ignoreDiff) {
        boolean result = false;
        if (iceIvlts != null && assertedIvlts != null) {
            String iceTimestamp = iceIvlts.getLow();
            String assertedTimestamp = assertedIvlts.getLow();
            result = diffDateStringValues(iceTimestamp, assertedTimestamp, objectName, iceTestResultDTO, ignoreDiff);
        } else if (iceIvlts != null && assertedIvlts == null && reportDiffIfExpectedNull) {
            String iceTimestamp = iceIvlts.getLow();
            Date iceParsedDate = parseDate(iceTimestamp);
            String message = objectName
                    + " does not match: <strong>ICE</strong>="
                    + DateUtils.getFormattedDate(iceParsedDate, "MM/dd/yyyy")
                    + "; <strong>EXPECTED</strong>=&lt;null&gt;";
            result = true;
            if (ignoreDiff) {
                iceTestResultDTO.getMatchLog().add("WARN: " + message);
            } else {
                iceTestResultDTO.getDifferenceLog().add(message);
            }
        } else if (iceIvlts == null && assertedIvlts != null && reportDiffIfIceNull) {
            String assertedTimestamp = assertedIvlts.getLow();
            Date assertedParsedDate = parseDate(assertedTimestamp);
            String message = objectName
                    + " does not match: <strong>ICE</strong>=&lt;null&gt;; <strong>EXPECTED</strong>="
                    + DateUtils.getFormattedDate(assertedParsedDate, "MM/dd/yyyy");
            result = true;
            if (ignoreDiff) {
                iceTestResultDTO.getMatchLog().add("WARN: " + message);
            } else {
                iceTestResultDTO.getDifferenceLog().add(message);
            }
        } else {
            if (DEBUG) {
                logger.info(objectName, " - both values are null.");
            }
        }
        return result;
    }

    /**
     * diff date string objects
     *
     * @param iceString
     * @param assertedString
     * @param objectName
     * @param iceTestResultDTO
     * @param ignoreDiff
     * @return
     */
    public static boolean diffDateStringValues(String iceString, String assertedString, String objectName, IceTestResultDTO iceTestResultDTO, boolean ignoreDiff) {
        boolean result = false;
        if (iceString != null || assertedString != null) {
            result = isNullAndNotNull(iceString, assertedString, objectName, iceTestResultDTO, ignoreDiff);
            if (!result) {
                Date iceParsedDate = parseDate(iceString);
                Date assertedParsedDate = parseDate(assertedString);
                result = diffValues(iceParsedDate, assertedParsedDate, objectName + " date", iceTestResultDTO, ignoreDiff);
            }
        } else {
            iceTestResultDTO.getMatchLog().add(objectName + " values match: <strong>ICE</strong>=&lt;null&gt;; <strong>EXPECTED</strong>=&lt;null&gt;");
        }
        return result;
    }

    /**
     * diff AdministrableSubstance values
     *
     * @param iceSubstance
     * @param assertedSubstance
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    private static boolean diffValues(
            AdministrableSubstance iceSubstance,
            AdministrableSubstance assertedSubstance,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        boolean result = isNullAndNotNull(
                iceSubstance,
                assertedSubstance,
                objectName,
                iceTestResultDTO,
                false);
        if (!result) {
            result = diffValues(
                    iceSubstance.getSubstanceCode(),
                    assertedSubstance.getSubstanceCode(),
                    objectName + " substance code",
                    iceTestResultDTO);
        }
        return result;
    }

    /*
     *
     *
     * DIFF LISTS
     *
     *
     */
    /**
     * diff observation result lists
     *
     * @param iceObservationResultList
     * @param assertedObservationResultList
     * @param objectName
     * @param focusCodeSystemOid
     * @param valueCodeSystemOid
     * @param interpretationCodeSystemOid
     * @param iceTestResultDTO
     * @return
     */
    public static boolean diffObservationResultLists(
            List<ObservationResult> iceObservationResultList,
            List<ObservationResult> assertedObservationResultList,
            String objectName,
            String focusCodeSystemOid,
            String valueCodeSystemOid,
            String interpretationCodeSystemOid,
            IceTestResultDTO iceTestResultDTO) {
        boolean result = isNullAndNotNull(
                iceObservationResultList,
                assertedObservationResultList,
                objectName + " Lists",
                iceTestResultDTO,
                false);
        if (!result) {
            Map<String, ObservationResult> iceObservationResultMap = getObservationResultMap(iceObservationResultList, objectName, iceTestResultDTO);
            Map<String, ObservationResult> assertedObservationResultMap = getObservationResultMap(assertedObservationResultList, objectName, iceTestResultDTO);
            result = diffValues(iceObservationResultMap, assertedObservationResultMap, objectName, iceTestResultDTO);
            List<String> keyList = new ArrayList<String>();
            keyList.addAll(iceObservationResultMap.keySet());
            for (String key : assertedObservationResultMap.keySet()) {
                if (!keyList.contains(key)) {
                    keyList.add(key);
                }
            }
            for (String key : keyList) {
                if (diffValues(
                        iceObservationResultMap.get(key),
                        assertedObservationResultMap.get(key),
                        objectName,
                        iceTestResultDTO)) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * diff interpretation cds
     *
     * @param iceInterpretations
     * @param assertedInterpretations
     * @param objectName
     * @param interpretationCodeSystemOid
     * @param iceTestResultDTO
     * @return
     */
    private static boolean diffInterpretationLists(
            List<CD> iceInterpretations,
            List<CD> assertedInterpretations,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        boolean result = isNullAndNotNull(
                iceInterpretations,
                assertedInterpretations,
                objectName,
                iceTestResultDTO,
                false);
        if (!result) {
            Map<String, CD> iceCdMap = getCdMap(iceInterpretations, "ice" + objectName, iceTestResultDTO);
            Map<String, CD> assertedCdMap = getCdMap(assertedInterpretations, "asserted" + objectName, iceTestResultDTO);
            result = diffValues(iceCdMap, assertedCdMap, objectName, iceTestResultDTO);
            for (String key : getCombinedKeyList(iceCdMap, assertedCdMap)) {
                CD iceCd = iceCdMap.get(key);
                CD assertCd = assertedCdMap.get(key);
                if (iceCd != null && assertCd != null) {
                    if (diffValues(
                            iceCd,
                            assertCd,
                            objectName + " reason",
                            iceTestResultDTO)) {
                        result = true;
                    }
                } else {
                    logger.error("Key not found in ICE or EXPECTED result: ", key, "; ICE=", iceCd, "; EXPECTED=", assertCd);
                }
            }
        }
        return result;
    }

    /**
     * diff RelatedClinicalStatement lists
     *
     * @param testVaccineGroupFocus
     * @param iceRelatedClinicalStatements
     * @param assertedRelatedClinicalStatements
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    private static boolean diffRelatedClinicalStatementLists(
            Map<CdsCodeDTO, List<CdsCodeDTO>> testVaccineGroupFocus,
            List<RelatedClinicalStatement> iceRelatedClinicalStatements,
            List<RelatedClinicalStatement> assertedRelatedClinicalStatements,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        boolean result = isNullAndNotNull(
                iceRelatedClinicalStatements,
                assertedRelatedClinicalStatements,
                objectName,
                iceTestResultDTO,
                false);
        if (!result) {
            Map<String, Object> iceRelatedClinicalStatementMap = getRelatedClinicalStatementObjectMap(testVaccineGroupFocus, iceRelatedClinicalStatements, objectName, iceTestResultDTO);
            Map<String, Object> assertedRelatedClinicalStatementMap = getRelatedClinicalStatementObjectMap(testVaccineGroupFocus, assertedRelatedClinicalStatements, objectName, iceTestResultDTO);
            result = diffValues(iceRelatedClinicalStatementMap, assertedRelatedClinicalStatementMap, objectName, iceTestResultDTO);
            for (String key : getCombinedKeyList(iceRelatedClinicalStatementMap, assertedRelatedClinicalStatementMap)) {
                Object iceMapItem = iceRelatedClinicalStatementMap.get(key);
                Object assertedMapItem = assertedRelatedClinicalStatementMap.get(key);
                if (iceMapItem != null && assertedMapItem != null) {
                    if (iceMapItem.getClass().equals(assertedMapItem.getClass())) {
                        if (iceMapItem instanceof ObservationResult) {
                            ObservationResult iceObservationResult = (ObservationResult) iceMapItem;
                            ObservationResult assertedObservationResult = (ObservationResult) assertedMapItem;
                            if (diffValues(
                                    iceObservationResult,
                                    assertedObservationResult,
                                    objectName,
                                    iceTestResultDTO)) {
                                result = true;
                            }
                        } else if (iceMapItem instanceof SubstanceAdministrationEvent) {
                            SubstanceAdministrationEvent iceSubstanceAdministrationEvent = (SubstanceAdministrationEvent) iceMapItem;
                            SubstanceAdministrationEvent assertedSubstanceAdministrationEvent = (SubstanceAdministrationEvent) assertedMapItem;
                            if (diffSubstanceAdministrationEventValues(
                                    testVaccineGroupFocus,
                                    iceSubstanceAdministrationEvent,
                                    assertedSubstanceAdministrationEvent,
                                    objectName,
                                    iceTestResultDTO)) {
                                result = true;
                            }
                        }
                    } else {
                        logger.error(
                                "Class mismatch in related clinical statement comparation: ",
                                iceMapItem.getClass().getSimpleName(),
                                " - ",
                                assertedMapItem.getClass().getSimpleName());
                    }
                }
            }
        }
        return result;
    }

    /*
     *
     *
     * DIFF MAPS
     *
     *
     */
    /**
     * diff two generic maps for size and key content
     *
     * @param <S>
     * @param iceObjectMap
     * @param assertedObjectMap
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    public static <S> boolean diffValues(
            Map<String, S> iceObjectMap,
            Map<String, S> assertedObjectMap,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        boolean result = false;
        if (iceObjectMap.size() != assertedObjectMap.size()) {
            // diffMessages.add("The number of " + objectName + " entries do not match: ICE - " + iceObjectMap.size() + "; asserted - " + assertedObjectMap.size());
            logger.error(objectName + "ICE/Expected map size mismatch: ", iceObjectMap.size(), " - ", assertedObjectMap.size());
            result = true;
        }
        List<String> iceValues = new ArrayList<String>();
        List<String> assertedValues = new ArrayList<String>();
        for (String key : iceObjectMap.keySet()) {
            iceValues.add(processKey(key, objectName, iceTestResultDTO));
            if (!assertedObjectMap.containsKey(key)) {
                result = true;
                logger.error(objectName + "Key missing from assertedObservationResultMap: ", key);
            } else {
                if (DEBUG) {
                    logger.info("DEBUG: ", objectName, " ICE key found in asserted map: ", key);
                }
            }
        }
        for (String key : assertedObjectMap.keySet()) {
            assertedValues.add(processKey(key, objectName, iceTestResultDTO));
            if (!iceObjectMap.containsKey(key)) {
                result = true;
                logger.error(objectName + "Key missing from iceObservationResultMap: ", key);
            } else {
                if (DEBUG) {
                    logger.info("DEBUG: ", objectName, " asserted key found in ICE map: ", key);
                }
            }
        }
        if (result) {
            iceTestResultDTO.getDifferenceLog().add(
                    objectName
                    + " result does not match: <strong>ICE</strong>="
                    + (iceValues.isEmpty() ? "&lt;null&gt;" : StringUtils.getStringFromArray(iceValues, "/"))
                    + "; <strong>EXPECTED</strong>="
                    + (assertedValues.isEmpty() ? "&lt;null&gt;" : StringUtils.getStringFromArray(assertedValues, "/")));
        } else {
            iceTestResultDTO.getMatchLog().add(
                    objectName
                    + " result match: <strong>ICE</strong>="
                    + (iceValues.isEmpty() ? "&lt;null&gt;" : StringUtils.getStringFromArray(iceValues, "/"))
                    + "; <strong>EXPECTED</strong>="
                    + (assertedValues.isEmpty() ? "&lt;null&gt;" : StringUtils.getStringFromArray(assertedValues, "/")));
        }
        return result;
    }

    /*
     *
     * KEY METHODS
     *
     *
     */
    /**
     * derive the observation result key
     *
     * @param observationResult
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    private static String getObservationResultKey(ObservationResult observationResult, String objectName, IceTestResultDTO iceTestResultDTO) {
        String key = null;
        if (observationResult != null) {
            String focusCdMapKeyFragment = getCdMapKeyFragment(observationResult.getObservationFocus(), objectName + " focus", iceTestResultDTO);
            if (focusCdMapKeyFragment != null) {
                ObservationValue observationValue = observationResult.getObservationValue();
                if (observationValue != null) {
                    String valueCdMapKeyFragment = getCdMapKeyFragment(observationValue.getConcept(), objectName + " value", iceTestResultDTO);
                    if (valueCdMapKeyFragment != null) {
                        key = focusCdMapKeyFragment + "=>" + valueCdMapKeyFragment;
                    }
                } else {
                    logger.error(objectName, " observationValue is null!");
                }
            }
        } else {
            logger.error(objectName, " ObservationResult is null!");
        }
        return key;
    }

    /**
     * validate and return a key fragment
     *
     * @param cd
     * @param iceTestResultDTO
     * @param objectName
     * @return
     */
    // TODO: switch to display name from code
    public static String getCdMapKeyFragment(CD cd, String objectName, IceTestResultDTO iceTestResultDTO) {
        String keyFragment = null;
        if (cd != null) {
            String codeSystem = cd.getCodeSystem();
            if (codeSystem != null && !codeSystem.isEmpty()) {
                String code = cd.getCode();
                if (code != null && !code.isEmpty()) {
                    keyFragment = codeSystem + "=>" + code;
                } else {
                    logger.error(objectName, " code is null!");
                }
            } else {
                logger.error(objectName, " codeSystem is null!");
            }

        } else {
            logger.error(objectName, " CD is null!");
        }
        return keyFragment;
    }

    private static String getSubstanceAdministrationProposalKey(
            SubstanceAdministrationProposal substanceAdministrationProposal,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        String key = null;
        if (substanceAdministrationProposal != null) {
            AdministrableSubstance substance = substanceAdministrationProposal.getSubstance();
            if (substance != null) {
                key = getSubstanceKey(substance, objectName, iceTestResultDTO);
            } else {
                logger.error(objectName, " AdministrableSubstance is null!");
            }
        } else {
            logger.error(objectName, " SubstanceAdministrationEvent is null!");
        }
        return key;
    }

    /**
     * derive the substance administration event key
     *
     * @param substanceAdministrationEvent
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    private static String getSubstanceAdministrationEventKey(
            SubstanceAdministrationEvent substanceAdministrationEvent,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        String key = null;
        if (substanceAdministrationEvent != null) {
            II id = substanceAdministrationEvent.getId();
            if (id != null) {
                String root = id.getRoot();
                if (root != null) {
                    String extension = id.getExtension();
                    if (extension != null) {
                        key = root + "=>" + extension;
                    } else {
                        logger.error(objectName, " Id extension is null!");
                    }
                } else {
                    logger.error(objectName, " Id root is null!");
                }
            } else {
                logger.error(objectName, " Id is null!");
            }
        } else {
            logger.error(objectName, " SubstanceAdministrationEvent is null!");
        }
        return key;
    }

    private static String getSubstanceKey(
            AdministrableSubstance substance,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        String key = null;
        CD substanceCode = substance.getSubstanceCode();
        if (substanceCode != null) {
            key = getCdMapKeyFragment(substanceCode, objectName, iceTestResultDTO);
        } else {
            logger.error(objectName, " substanceCode is null!");
        }
        return key;
    }

    /**
     * derive a map key for an evaluation substance administration event
     *
     * @param substanceAdministrationEvent
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    private static String getEvaluationSubstanceAdministrationEventKey(
            SubstanceAdministrationEvent substanceAdministrationEvent,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        String key = null;
        if (substanceAdministrationEvent != null) {
            AdministrableSubstance substance = substanceAdministrationEvent.getSubstance();
            if (substance != null) {
                key = getSubstanceKey(substance, objectName, iceTestResultDTO);
            } else {
                logger.error(objectName, " AdministrableSubstance is null!");
            }
        } else {
            logger.error(objectName, " SubstanceAdministrationEvent is null!");
        }
        return key;
    }

    /*
     *
     *
     * MAP METHODS
     *
     *
     */
    /**
     * validate and construct an ObservationResultMap
     *
     * @param observationResults
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    public static Map<String, ObservationResult> getObservationResultMap(
            List<ObservationResult> observationResults,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        Map<String, ObservationResult> observationMap = new HashMap<String, ObservationResult>();
        for (ObservationResult observationResult : observationResults) {
            String mapKey = getObservationResultKey(observationResult, objectName, iceTestResultDTO);
            if (mapKey != null) {
                if (!observationMap.containsKey(mapKey)) {
                    observationMap.put(mapKey, observationResult);
                } else {
                    logger.error(objectName, " - key already in map! ", mapKey);
                }
            }
        }
        compareListToMap(observationResults, observationMap, objectName, iceTestResultDTO);
        return observationMap;
    }

    /**
     * validate and construct a cdMap
     *
     * @param cds
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    public static Map<String, CD> getCdMap(
            List<CD> cds,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        Map<String, CD> cdMap = new HashMap<String, CD>();
        for (CD cd : cds) {
            String cdMapKey = getCdMapKeyFragment(cd, objectName, iceTestResultDTO);
            if (cdMapKey != null) {
                if (!cdMap.containsKey(cdMapKey)) {
                    cdMap.put(cdMapKey, cd);
                } else {
                    logger.error(objectName, " - key already in map! ", cdMapKey);
                }
            }
        }
        compareListToMap(cds, cdMap, objectName, iceTestResultDTO);
        return cdMap;
    }

    /**
     * validate and construct an SubstanceAdministrationProposalMap
     *
     * @param iceSubstanceAdministrationProposals
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    private static Map<String, SubstanceAdministrationProposal> getSubstanceAdministrationProposalMap(
            List<SubstanceAdministrationProposal> iceSubstanceAdministrationProposals,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        Map<String, SubstanceAdministrationProposal> substanceAdministrationProposalMap = new HashMap<String, SubstanceAdministrationProposal>();
        for (SubstanceAdministrationProposal substanceAdministrationProposal : iceSubstanceAdministrationProposals) {
            String mapKey = getSubstanceAdministrationProposalKey(substanceAdministrationProposal, objectName, iceTestResultDTO);
            if (mapKey != null) {
                if (DEBUG) {
                    logger.info("DEBUG: ", objectName, " mapKey value: ", mapKey);
                }
                if (!substanceAdministrationProposalMap.containsKey(mapKey)) {
                    substanceAdministrationProposalMap.put(mapKey, substanceAdministrationProposal);
                } else {
                    logger.error(objectName, " - key already in map! ", mapKey);
                }
            }
        }
        compareListToMap(iceSubstanceAdministrationProposals, substanceAdministrationProposalMap, objectName, iceTestResultDTO);
        return substanceAdministrationProposalMap;
    }

    /**
     * validate and construct an SubstanceAdministrationEventMap
     *
     * @param substanceAdministrationEvents
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    public static Map<String, SubstanceAdministrationEvent> getSubstanceAdministrationEventMap(
            List<SubstanceAdministrationEvent> substanceAdministrationEvents,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        Map<String, SubstanceAdministrationEvent> substanceAdministrationEventMap = new HashMap<String, SubstanceAdministrationEvent>();
        for (SubstanceAdministrationEvent substanceAdministrationEvent : substanceAdministrationEvents) {
            String mapKey = getSubstanceAdministrationEventKey(substanceAdministrationEvent, objectName, iceTestResultDTO);
            if (mapKey != null) {
                if (!substanceAdministrationEventMap.containsKey(mapKey)) {
                    substanceAdministrationEventMap.put(mapKey, substanceAdministrationEvent);
                } else {
                    logger.error(objectName, " - key already in map! ", mapKey);
                }
            }
        }
        compareListToMap(substanceAdministrationEvents, substanceAdministrationEventMap, objectName, iceTestResultDTO);
        return substanceAdministrationEventMap;
    }

    /**
     * validate and construct a RelatedClinicalStatementMap
     *
     * @param testVaccineGroupFocus
     * @param relatedClinicalStatements
     * @param objectName
     * @param iceTestResultDTO
     * @return
     */
    public static Map<String, Object> getRelatedClinicalStatementObjectMap(
            Map<CdsCodeDTO, List<CdsCodeDTO>> testVaccineGroupFocus,
            List<RelatedClinicalStatement> relatedClinicalStatements,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        final String METHODNAME = "";
        Map<String, Object> relatedClinicalStatementMap = new HashMap<String, Object>();
        if (relatedClinicalStatements != null) {
            for (RelatedClinicalStatement relatedClinicalStatement : relatedClinicalStatements) {
                if (relatedClinicalStatement != null) {
                    ObservationResult observationResult = relatedClinicalStatement.getObservationResult();
                    if (observationResult != null) {
                        String mapKey = getObservationResultKey(observationResult, objectName, iceTestResultDTO);
                        if (mapKey != null) {
                            if (!relatedClinicalStatementMap.containsKey(mapKey)) {
                                if (!testVaccineGroupFocus.isEmpty()
                                        && observationResult.getObservationFocus() != null
                                        && observationResult.getObservationFocus().getCode() != null) {
                                    boolean found = false;
                                    CD observationFocus = observationResult.getObservationFocus();
                                    if (observationFocus != null) {
                                        for (CdsCodeDTO group : testVaccineGroupFocus.keySet()) {
                                            if (group.getCode().equals(observationFocus.getCode())) {
                                                logger.debug("Part A Found observtion focus in vaccine group focus: ", group.getDisplayName());
                                                found = true;
                                                break;
                                            }
                                        }
                                    } else {
                                        logger.warn(METHODNAME, "observationFocus is null!");
                                    }
                                    if (found) {
                                        relatedClinicalStatementMap.put(mapKey, observationResult);
                                    } else {
                                        logger.info(METHODNAME, "Part A skipping observation result - focus not found");
                                    }
                                } else {
                                    relatedClinicalStatementMap.put(mapKey, observationResult);
                                }
                            } else {
                                logger.error(objectName, " - key already in map! ", mapKey);
                            }
                        }
                    }
                    SubstanceAdministrationEvent substanceAdministrationEvent = relatedClinicalStatement.getSubstanceAdministrationEvent();
                    if (substanceAdministrationEvent != null) {
                        List<RelatedClinicalStatement> rcss = substanceAdministrationEvent.getRelatedClinicalStatement();
                        if (rcss != null && rcss.size() == 1) {
                            RelatedClinicalStatement rcs = rcss.get(0);
                            if (rcs != null) {
                                ObservationResult or = rcs.getObservationResult();
                                if (or != null) {
                                    boolean found = false;
                                    CD observationFocus = or.getObservationFocus();
                                    if (observationFocus != null) {
                                        for (CdsCodeDTO group : testVaccineGroupFocus.keySet()) {
                                            if (group.getCode().equals(observationFocus.getCode())) {
                                                logger.debug("Part B Found proposal focus in vaccine group focus: ", group.getDisplayName());
                                                found = true;
                                                break;
                                            }
                                        }
                                    } else {
                                        logger.warn(METHODNAME, "observationFocus is null!");
                                    }
                                    if (found) {
                                        String mapKey = getEvaluationSubstanceAdministrationEventKey(substanceAdministrationEvent, objectName, iceTestResultDTO);
                                        if (mapKey != null) {
                                            if (!relatedClinicalStatementMap.containsKey(mapKey)) {
                                                relatedClinicalStatementMap.put(mapKey, substanceAdministrationEvent);
                                            } else {
                                                logger.error(objectName, " - key already in map! ", mapKey);
                                            }
                                        }
                                    } else {
                                        logger.info(METHODNAME, "Part B skipping observation result - focus not found");
                                    }
                                }
                            }
                        }
                    }
                } else {
                    logger.error(objectName, " relatedClinicalStatement is null!");
                }
            }
        } else {
            logger.error(objectName, " relatedClinicalStatements is null!");
        }
        compareListToMap(relatedClinicalStatements, relatedClinicalStatementMap, objectName, iceTestResultDTO);
        return relatedClinicalStatementMap;
    }

    /*
     *
     *
     * UTILITY METHODS
     *
     *
     */
    /**
     * generic compare of lists converted to maps
     *
     * @param iceList
     * @param assertedList
     * @param iceMap
     * @param assertedMap
     * @param objectName
     * @param iceTestResultDTO
     */
    private static <S> void compareListToMap(List list, Map<String, S> map, String objectName, IceTestResultDTO iceTestResultDTO) {
        if (list.size() != map.size()) {
            logger.error(objectName + " list/map size mismatch: ", list.size(), " - ", map.size());
        }
    }

    /**
     * return a combined key list without dups
     *
     * @param <S>
     * @param iceMap
     * @param assertedMap
     * @return
     */
    private static <S> List<String> getCombinedKeyList(Map<String, S> iceMap, Map<String, S> assertedMap) {
        List<String> keyList = new ArrayList<String>();
        keyList.addAll(iceMap.keySet());
        for (String key : assertedMap.keySet()) {
            if (!keyList.contains(key)) {
                keyList.add(key);
            }
        }
        return keyList;
    }

    private static String processKey(
            String key,
            String objectName,
            IceTestResultDTO iceTestResultDTO) {
        String processedKey = "";
        String[] keyItems = key.split("=>");
        if (keyItems.length == 2) {
            processedKey = keyItems[1];
        } else if (keyItems.length >= 4) {
            processedKey = keyItems[1] + ":" + keyItems[3];
        } else {
            iceTestResultDTO.getDifferenceLog().add(objectName + " - Unexpected key type: " + key);
        }
        return processedKey;
    }

    /**
     * parse various date formats
     *
     * @param dateString
     * @return
     */
    public static Date parseDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date result = null;
        if (dateString != null && !dateString.isEmpty()) {
            String rawValue = null;
            try {
                String[] ts = dateString.split("\\.");
                rawValue = ts[0].trim();
                if (!rawValue.isEmpty()) {
                    if (rawValue.length() == 8) {
                        rawValue = rawValue += "000000";
                    }
                    result = simpleDateFormat.parse(rawValue);
//                    Calendar c = Calendar.getInstance();
//                    c.setTime(result);
//                    int year = c.get(Calendar.YEAR);
//                    if (year < 1980 || year > 2013) {
//                        logger.info("Got weird date: ", result, " from ", dateString);
//                    }
                }
            } catch (ParseException e) {
                logger.warn("Failed to parse date: ", rawValue, " using ", simpleDateFormat.toPattern());
            } catch (NumberFormatException e) {
                logger.warn("Failed to parse date: ", rawValue, " using ", simpleDateFormat.toPattern());
            }
        }
        return result;
    }

    /**
     * generic null check
     *
     * @param a
     * @param b
     * @param objectName
     * @param iceTestResultDTO
     * @param ignoreDiff
     * @return
     */
    public static boolean isNullAndNotNull(Object a, Object b, String objectName, IceTestResultDTO iceTestResultDTO, boolean ignoreDiff) {
        boolean result = false;
        if (a == null && b != null) {
            String message = objectName + " ICE value is null and EXPECTED = " + b;
            result = true;
            if (ignoreDiff) {
                iceTestResultDTO.getMatchLog().add("WARN: " + message);
            } else {
                iceTestResultDTO.getDifferenceLog().add(message);
            }
        } else if (b == null && a != null) {
            String message = objectName + " EXPECTED value is null and ICE value = " + a;
            result = true;
            if (ignoreDiff) {
                iceTestResultDTO.getMatchLog().add("WARN: " + message);
            } else {
                iceTestResultDTO.getDifferenceLog().add(message);
            }
        } else if (a == null && b == null) {
            String message = objectName + " ICE and EXPECTED values were null";
            result = true;
            if (ignoreDiff) {
                iceTestResultDTO.getMatchLog().add("WARN: " + message);
            } else {
                iceTestResultDTO.getDifferenceLog().add(message);
            }
        }
        return result;
    }
}
