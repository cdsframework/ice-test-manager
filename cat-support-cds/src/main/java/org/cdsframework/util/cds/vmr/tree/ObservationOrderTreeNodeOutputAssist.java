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
import org.opencds.vmr.v1_0.schema.ObservationOrder;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author HLN Consulting, LLC
 */
public class ObservationOrderTreeNodeOutputAssist {

    private static final LogUtils logger = LogUtils.getLogger(ObservationOrderTreeNodeOutputAssist.class);

    public static void addObservationOrderNodes(List<ObservationOrder> observationOrders, TreeNode root) {
        final String METHODNAME = "addObservationOrderNodes ";
        logger.info(METHODNAME, "observationOrders=", observationOrders);
        if (observationOrders != null) {
            for (ObservationOrder item : observationOrders) {
                addObservationOrderNode(item, root);
            }
        } else {
            logger.warn(METHODNAME, "observationResults was null!");
        }
    }

    public static void addObservationOrderNode(ObservationOrder observationOrder, TreeNode root) {
        final String METHODNAME = "addObservationOrderNode ";
        logger.info(METHODNAME, "observationOrder=", observationOrder);
        if (observationOrder != null) {
            TreeNode ooNode = new DefaultTreeNode("ObservationOrder", root);
            ooNode.setExpanded(true);

            // criticality
            TreeNode criticalityNode = DataTypeTreeNodeOutputAssist.addCdNode("Criticality: ", observationOrder.getCriticality(), ooNode);

            TreeNode bodySiteNode = DataTypeTreeNodeOutputAssist.addBodySiteNode("Body Site", observationOrder.getTargetBodySite(), ooNode);

            // times
            TreeNode observationTimeRangeNode = DataTypeTreeNodeOutputAssist.addIvltsNode("Observation Time: ", observationOrder.getObservationTime(), ooNode);
            TreeNode orderEventTimeRangeNode = DataTypeTreeNodeOutputAssist.addIvltsNode("Order Event Time: ", observationOrder.getOrderEventTime(), ooNode);

            TreeNode focusNode = DataTypeTreeNodeOutputAssist.addCdNode("Observation Focus: ", observationOrder.getObservationFocus(), ooNode);
            TreeNode methodNode = DataTypeTreeNodeOutputAssist.addCdNode("Observation Method: ", observationOrder.getObservationMethod(), ooNode);

            DataTypeTreeNodeOutputAssist.addRelatedClinicalStatementsNode(observationOrder.getRelatedClinicalStatement(), ooNode);

            DataTypeTreeNodeOutputAssist.addRelatedEntitiesNode(observationOrder.getRelatedEntity(), ooNode);

        } else {
            logger.warn(METHODNAME, "ObservationResult item was null!");
        }
    }

}
