/**
 * The MTS support cds project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
package org.cdsframework.enumeration;

import org.opencds.vmr.v1_0.schema.EncounterEvent;
import org.opencds.vmr.v1_0.schema.ObservationOrder;
import org.opencds.vmr.v1_0.schema.ObservationResult;
import org.opencds.vmr.v1_0.schema.Problem;
import org.opencds.vmr.v1_0.schema.SubstanceAdministrationEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
public enum DetectedModelElementType {

    encounterEventTime("encounterEventTime", EncounterEvent.class),
    encounterType("encounterType", EncounterEvent.class),
    encounterProblemCode("relatedClinicalStatement/problem/problemCode", EncounterEvent.class),
    encounterProblemEffectiveTime("relatedClinicalStatement/problem/problemEffectiveTime", EncounterEvent.class),
    encounterProblemDiagnosticEventTime("relatedClinicalStatement/problem/problemDiagnosticEventTime", EncounterEvent.class),
    encounterProblemStatus("relatedClinicalStatement/problem/problemStatus", EncounterEvent.class),
    encounterProcedureCode("relatedClinicalStatement/procedureEvent/procedureCode", EncounterEvent.class),
    encounterProcedureTime("relatedClinicalStatement/procedureEvent/procedureTime", EncounterEvent.class),
    orderEventTime("orderEventTime", ObservationOrder.class),
    orderObservationFocus("observationFocus", ObservationOrder.class),
    problemProblemCode("problemCode", Problem.class),
    problemProblemStatus("problemStatus", Problem.class),
    problemProblemEffectiveTime("problemEffectiveTime", Problem.class),
    resultInterpretation("interpretation", ObservationResult.class),
    resultObservationEventTime("observationEventTime", ObservationResult.class),
    resultObservationFocus("observationFocus", ObservationResult.class),
    resultObservationValueConcept("observationValue/concept", ObservationResult.class),
    resultObservationValuePhysicalQuantity("observationValue/physicalQuantity", ObservationResult.class),
    substanceAdministrationSubstanceCode("substance/substanceCode", SubstanceAdministrationEvent.class),
    substanceAdministrationTimeInterval("administrationTimeInterval", SubstanceAdministrationEvent.class);

    private final String matchString;
    private final Class clinicalStatementClass;

    /**
     * Get the value of clinicalStatementClass
     *
     * @return the value of clinicalStatementClass
     */
    public Class getClinicalStatementClass() {
        return clinicalStatementClass;
    }

    private DetectedModelElementType(String matchString, Class clinicalStatementClass) {
        this.matchString = matchString;
        this.clinicalStatementClass = clinicalStatementClass;
    }

    /**
     * Get the value of matchString
     *
     * @return the value of matchString
     */
    public String getMatchString() {
        return matchString;
    }

    /**
     * Get the value of label
     *
     * @return the value of label
     */
    public String getLabel() {
        return matchString;
    }

    public static DetectedModelElementType valueOfLabel(String label) {
        DetectedModelElementType result = null;
        for (DetectedModelElementType item : DetectedModelElementType.values()) {
            if (item.matchString.equalsIgnoreCase(label)) {
                result = item;
                break;
            }
        }
        return result;
    }

}
