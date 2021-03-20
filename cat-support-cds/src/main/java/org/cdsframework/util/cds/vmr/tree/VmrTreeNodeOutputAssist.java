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
package org.cdsframework.util.cds.vmr.tree;

import org.cdsframework.util.LogUtils;
import org.opencds.vmr.v1_0.schema.EvaluatedPerson;
import org.opencds.vmr.v1_0.schema.VMR;
import org.primefaces.model.TreeNode;

/**
 *
 * @author HLN Consulting, LLC
 */
public class VmrTreeNodeOutputAssist {

    private static final LogUtils logger = LogUtils.getLogger(VmrTreeNodeOutputAssist.class);

    public static void buildVmrNodeTree(TreeNode root, VMR vmr) {
        final String METHODNAME = "buildVmrNodeTree ";
        logger.info(METHODNAME, "root=", root);
        logger.info(METHODNAME, "vmr=", vmr);
        if (root != null) {
            if (vmr != null) {
                EvaluatedPerson patient = vmr.getPatient();
                logger.info(METHODNAME, "patient=", patient);
                if (patient != null) {
                    EvaluatedPerson.ClinicalStatements clinicalStatements = patient.getClinicalStatements();
                    logger.info(METHODNAME, "clinicalStatements=", clinicalStatements);
                    if (clinicalStatements != null) {
                        // add Observation Results
                        EvaluatedPerson.ClinicalStatements.ObservationResults observationResults = clinicalStatements.getObservationResults();
                        logger.info(METHODNAME, "observationResults=", observationResults);
                        if (observationResults != null) {
                            ObservationResultTreeNodeOutputAssist.addObservationResultNodes(observationResults.getObservationResult(), root);
                        } else {
                            logger.warn(METHODNAME, "observationResults was null!");
                        }

                        // add Observation Orders
                        EvaluatedPerson.ClinicalStatements.ObservationOrders observationOrders = clinicalStatements.getObservationOrders();
                        logger.info(METHODNAME, "observationOrders=", observationOrders);
                        if (observationOrders != null) {
                            ObservationOrderTreeNodeOutputAssist.addObservationOrderNodes(observationOrders.getObservationOrder(), root);
                        } else {
                            logger.warn(METHODNAME, "observationOrders was null!");
                        }

                        // add Encounters
                        EvaluatedPerson.ClinicalStatements.EncounterEvents encounterEvents = clinicalStatements.getEncounterEvents();
                        logger.info(METHODNAME, "encounterEvents=", encounterEvents);
                        if (encounterEvents != null) {
                            EncounterEventTreeNodeOutputAssist.addEncounterEventNodes(encounterEvents.getEncounterEvent(), root);
                        } else {
                            logger.warn(METHODNAME, "encounterEvents was null!");
                        }

                        // add Observation Proposals
                        EvaluatedPerson.ClinicalStatements.ObservationProposals observationProposals = clinicalStatements.getObservationProposals();
                        logger.info(METHODNAME, "observationProposals=", observationProposals);
                        if (observationProposals != null) {
                            ObservationProposalTreeNodeOutputAssist.addObservationProposalNodes(observationProposals.getObservationProposal(), root);
                        } else {
                            logger.warn(METHODNAME, "observationProposals was null!");
                        }

                        // add problems
                        EvaluatedPerson.ClinicalStatements.Problems problems = clinicalStatements.getProblems();
                        logger.info(METHODNAME, "problems=", problems);
                        if (problems != null) {
                            ProblemTreeNodeOutputAssist.addProblemNodes(problems.getProblem(), root);
                        } else {
                            logger.warn(METHODNAME, "problems was null!");
                        }

                        //TODO: setup the below clinical statements
                        EvaluatedPerson.ClinicalStatements.SubstanceAdministrationEvents substanceAdministrationEvents = clinicalStatements.getSubstanceAdministrationEvents();
                        EvaluatedPerson.ClinicalStatements.SubstanceAdministrationOrders substanceAdministrationOrders = clinicalStatements.getSubstanceAdministrationOrders();
                        EvaluatedPerson.ClinicalStatements.SubstanceAdministrationProposals substanceAdministrationProposals = clinicalStatements.getSubstanceAdministrationProposals();
                        EvaluatedPerson.ClinicalStatements.ProcedureEvents procedureEvents = clinicalStatements.getProcedureEvents();
                        EvaluatedPerson.ClinicalStatements.ProcedureOrders procedureOrders = clinicalStatements.getProcedureOrders();
                        EvaluatedPerson.ClinicalStatements.ProcedureProposals procedureProposals = clinicalStatements.getProcedureProposals();
                        
                    } else {
                        logger.warn(METHODNAME, "clinicalStatements was null!");
                    }
                } else {
                    logger.warn(METHODNAME, "patient was null!");
                }
            } else {
                logger.warn(METHODNAME, "vmrInput was null!");
            }
        } else {
            logger.warn(METHODNAME, "root was null!");
        }
    }

}
