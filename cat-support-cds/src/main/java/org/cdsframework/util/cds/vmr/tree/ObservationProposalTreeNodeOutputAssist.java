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
import org.opencds.vmr.v1_0.schema.ObservationProposal;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author HLN Consulting, LLC
 */
public class ObservationProposalTreeNodeOutputAssist {

    private static final LogUtils logger = LogUtils.getLogger(ObservationOrderTreeNodeOutputAssist.class);

    public static void addObservationProposalNodes(List<ObservationProposal> observationProposals, TreeNode root) {
        final String METHODNAME = "addObservationProposalNodes ";
        logger.info(METHODNAME, "observationProposals=", observationProposals);
        if (observationProposals != null) {
            for (ObservationProposal item : observationProposals) {
                addObservationProposalNode(item, root);
            }
        } else {
            logger.warn(METHODNAME, "observationProposals was null!");
        }
    }

    public static void addObservationProposalNode(ObservationProposal observationProposal, TreeNode root) {
        final String METHODNAME = "addObservationProposalNode ";
        logger.info(METHODNAME, "observationProposal=", observationProposal);
        if (observationProposal != null) {
            TreeNode opNode = new DefaultTreeNode("ObservationProposal", root);
            opNode.setExpanded(true);

            // repeat number
            TreeNode repeatNumberNode = DataTypeTreeNodeOutputAssist.addIntNode("Repeat Number: ", observationProposal.getRepeatNumber(), opNode);

            // criticality
            TreeNode criticalityNode = DataTypeTreeNodeOutputAssist.addCdNode("Criticality: ", observationProposal.getCriticality(), opNode);

            // body site
            TreeNode bodySiteNode = DataTypeTreeNodeOutputAssist.addBodySiteNode("Body Site", observationProposal.getTargetBodySite(), opNode);

            // times
            TreeNode observationTimeRangeNode = DataTypeTreeNodeOutputAssist.addIvltsNode("Observation Time: ", observationProposal.getProposedObservationTime(), opNode);
            TreeNode orderEventTimeRangeNode = DataTypeTreeNodeOutputAssist.addIvltsNode("Proposal Event Time: ", observationProposal.getProposedObservationTime(), opNode);

            TreeNode focusNode = DataTypeTreeNodeOutputAssist.addCdNode("Observation Focus: ", observationProposal.getObservationFocus(), opNode);
            TreeNode methodNode = DataTypeTreeNodeOutputAssist.addCdNode("Observation Method: ", observationProposal.getObservationMethod(), opNode);

            DataTypeTreeNodeOutputAssist.addRelatedClinicalStatementsNode(observationProposal.getRelatedClinicalStatement(), opNode);

            DataTypeTreeNodeOutputAssist.addRelatedEntitiesNode(observationProposal.getRelatedEntity(), opNode);

        } else {
            logger.warn(METHODNAME, "ObservationResult item was null!");
        }
    }

}
