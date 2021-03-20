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

import java.util.List;
import org.cdsframework.util.LogUtils;
import org.opencds.vmr.v1_0.schema.Problem;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author HLN Consulting, LLC
 */
public class ProblemTreeNodeOutputAssist {
    private static final LogUtils logger = LogUtils.getLogger(ProblemTreeNodeOutputAssist.class);

    public static void addProblemNodes(List<Problem> problems, TreeNode root) {
        final String METHODNAME = "addProblemNodes ";
        logger.info(METHODNAME, "problems=", problems);
        if (problems != null) {
            for (Problem item : problems) {
                addProblemNode(item, root);
            }
        } else {
            logger.warn(METHODNAME, "problems was null!");
        }
    }

    public static void addProblemNode(Problem problem, TreeNode root) {
        final String METHODNAME = "addProblemNode ";
        logger.info(METHODNAME, "problem=", problem);
        if (problem != null) {
            TreeNode opNode = new DefaultTreeNode("Problem", root);
            opNode.setExpanded(true);

            // age at onset
            TreeNode causeOfDeathNode = DataTypeTreeNodeOutputAssist.addBlNode("Was Cause of Death: ", problem.getWasCauseOfDeath(), opNode);

            // age at onset
            TreeNode ageAtOnsetNode = DataTypeTreeNodeOutputAssist.addPqNode("Age at Onset: ", problem.getAgeAtOnset(), opNode);

            // problem code
            TreeNode problemCodeNode = DataTypeTreeNodeOutputAssist.addCdNode("Problem Code: ", problem.getProblemCode(), opNode);

            // problem status
            TreeNode problemStatusNode = DataTypeTreeNodeOutputAssist.addCdNode("Problem Status: ", problem.getProblemStatus(), opNode);

            // severity
            TreeNode severityNode = DataTypeTreeNodeOutputAssist.addCdNode("Severity: ", problem.getSeverity(), opNode);

            // importance
            TreeNode importanceNode = DataTypeTreeNodeOutputAssist.addCdNode("Importance: ", problem.getImportance(), opNode);

            // body site
            TreeNode bodySitesNode = DataTypeTreeNodeOutputAssist.addBodySitesNode("Body Sites", problem.getAffectedBodySite(), opNode);

            // times
            TreeNode diagnosticEventTimeRangeNode = DataTypeTreeNodeOutputAssist.addIvltsNode("Diagnostic Event Time: ", problem.getDiagnosticEventTime(), opNode);
            TreeNode problemEffectiveTimeRangeNode = DataTypeTreeNodeOutputAssist.addIvltsNode("Problem Effective Time: ", problem.getProblemEffectiveTime(), opNode);


            DataTypeTreeNodeOutputAssist.addRelatedClinicalStatementsNode(problem.getRelatedClinicalStatement(), opNode);

            DataTypeTreeNodeOutputAssist.addRelatedEntitiesNode(problem.getRelatedEntity(), opNode);

        } else {
            logger.warn(METHODNAME, "ObservationResult item was null!");
        }
    }
    
}
