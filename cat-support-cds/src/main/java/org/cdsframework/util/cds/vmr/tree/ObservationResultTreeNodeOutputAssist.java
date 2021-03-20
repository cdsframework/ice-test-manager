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
import org.opencds.vmr.v1_0.schema.CD;
import org.opencds.vmr.v1_0.schema.ObservationResult;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author HLN Consulting, LLC
 */
public class ObservationResultTreeNodeOutputAssist {
        private static final LogUtils logger = LogUtils.getLogger(ObservationResultTreeNodeOutputAssist.class);

    public static void addObservationResultNodes(List<ObservationResult> observationResults, TreeNode root) {
        final String METHODNAME = "addObservationResultNodes ";
        logger.info(METHODNAME, "observationResults=", observationResults);
        if (observationResults != null) {
            for (ObservationResult item : observationResults) {
                addObservationResultNode(item, root);
            }
        } else {
            logger.warn(METHODNAME, "observationResults was null!");
        }
    }

    public static void addObservationResultNode(ObservationResult observationResult, TreeNode root) {
        final String METHODNAME = "addObservationResultNode ";
        logger.info(METHODNAME, "observationResult=", observationResult);
        if (observationResult != null) {
            TreeNode orNode = new DefaultTreeNode("ObservationResult", root);
            orNode.setExpanded(true);

            // interpretation
            List<CD> interpretation = observationResult.getInterpretation();
            if (interpretation != null) {
                for (CD cd : interpretation) {
                    TreeNode interpretationNode = DataTypeTreeNodeOutputAssist.addCdNode("Interpretation: ", cd, orNode);
                }
            }

            // event time
            TreeNode eventTimeRangeNode = DataTypeTreeNodeOutputAssist.addIvltsNode("Event Time: ", observationResult.getObservationEventTime(), orNode);

            // observation value
            ObservationResult.ObservationValue observationValue = observationResult.getObservationValue();
            if (observationValue != null) {
                TreeNode conceptNode = DataTypeTreeNodeOutputAssist.addCdNode("Observation Value Concept: ", observationValue.getConcept(), orNode);
                TreeNode blNode = DataTypeTreeNodeOutputAssist.addBlNode("Observation Value Boolean: ", observationValue.getBoolean(), orNode);
                TreeNode realNode = DataTypeTreeNodeOutputAssist.addRealNode("Observation Value Real: ", observationValue.getDecimal(), orNode);
                TreeNode realRangeNode = DataTypeTreeNodeOutputAssist.addRealRangeNode("Observation Value Real Range: ", observationValue.getDecimalRange(), orNode);
                TreeNode iiNode = DataTypeTreeNodeOutputAssist.addIiNode("Observation Value Identifier: ", observationValue.getIdentifier(), orNode);
                TreeNode intNode = DataTypeTreeNodeOutputAssist.addIntNode("Observation Value Integer: ", observationValue.getInteger(), orNode);
                TreeNode intRangeNode = DataTypeTreeNodeOutputAssist.addIntRangeNode("Observation Value Integer Range: ", observationValue.getIntegerRange(), orNode);
                TreeNode pqNode = DataTypeTreeNodeOutputAssist.addPqNode("Observation Value Physical Quantity: ", observationValue.getPhysicalQuantity(), orNode);
                TreeNode textNode = DataTypeTreeNodeOutputAssist.addTextNode("Observation Value Text: ", observationValue.getText(), orNode);
                TreeNode timeNode = DataTypeTreeNodeOutputAssist.addTimeNode("Observation Value Time: ", observationValue.getTime(), orNode);
                TreeNode timeRangeNode = DataTypeTreeNodeOutputAssist.addIvltsNode("Observation Value Time Range: ", observationValue.getTimeRange(), orNode);
            }
            TreeNode focusNode = DataTypeTreeNodeOutputAssist.addCdNode("Observation Focus: ", observationResult.getObservationFocus(), orNode);
            TreeNode methodNode = DataTypeTreeNodeOutputAssist.addCdNode("Observation Method: ", observationResult.getObservationMethod(), orNode);

            DataTypeTreeNodeOutputAssist.addRelatedClinicalStatementsNode(observationResult.getRelatedClinicalStatement(), orNode);

            DataTypeTreeNodeOutputAssist.addRelatedEntitiesNode(observationResult.getRelatedEntity(), orNode);

        } else {
            logger.warn(METHODNAME, "ObservationResult item was null!");
        }
    }

}
