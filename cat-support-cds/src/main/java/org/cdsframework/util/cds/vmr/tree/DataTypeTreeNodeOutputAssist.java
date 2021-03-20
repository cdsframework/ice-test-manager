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
import org.opencds.vmr.v1_0.schema.BL;
import org.opencds.vmr.v1_0.schema.BodySite;
import org.opencds.vmr.v1_0.schema.CD;
import org.opencds.vmr.v1_0.schema.II;
import org.opencds.vmr.v1_0.schema.INT;
import org.opencds.vmr.v1_0.schema.IVLINT;
import org.opencds.vmr.v1_0.schema.IVLREAL;
import org.opencds.vmr.v1_0.schema.IVLTS;
import org.opencds.vmr.v1_0.schema.PQ;
import org.opencds.vmr.v1_0.schema.REAL;
import org.opencds.vmr.v1_0.schema.RelatedClinicalStatement;
import org.opencds.vmr.v1_0.schema.RelatedEntity;
import org.opencds.vmr.v1_0.schema.ST;
import org.opencds.vmr.v1_0.schema.TS;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author HLN Consulting, LLC
 */
public class DataTypeTreeNodeOutputAssist {

    private static final LogUtils logger = LogUtils.getLogger(DataTypeTreeNodeOutputAssist.class);

    public static void addRelatedEntitiesNode(List<RelatedEntity> relatedEntities, TreeNode root) {
        if (relatedEntities != null) {
            for (RelatedEntity relatedEntity : relatedEntities) {
                addRelatedEntityNode(relatedEntity, root);
            }
        }
    }

    public static void addRelatedEntityNode(RelatedEntity relatedEntity, TreeNode root) {
        // TODO !!!
    }

    public static void addRelatedClinicalStatementsNode(List<RelatedClinicalStatement> relatedClinicalStatements, TreeNode root) {
        if (relatedClinicalStatements != null) {
            for (RelatedClinicalStatement relatedClinicalStatement : relatedClinicalStatements) {
                addRelatedClinicalStatementNode(relatedClinicalStatement, root);
            }
        }
    }

    public static void addRelatedClinicalStatementNode(RelatedClinicalStatement relatedClinicalStatement, TreeNode root) {
        if (relatedClinicalStatement != null) {
            ObservationResultTreeNodeOutputAssist.addObservationResultNode(relatedClinicalStatement.getObservationResult(), root);
            ObservationOrderTreeNodeOutputAssist.addObservationOrderNode(relatedClinicalStatement.getObservationOrder(), root);
            ObservationProposalTreeNodeOutputAssist.addObservationProposalNode(relatedClinicalStatement.getObservationProposal(), root);
            EncounterEventTreeNodeOutputAssist.addEncounterEventNode(relatedClinicalStatement.getEncounterEvent(), root);
            ProblemTreeNodeOutputAssist.addProblemNode(relatedClinicalStatement.getProblem(), root);
            // TODO support other node types
        }
    }

    public static TreeNode addCdNode(String prefix, CD cd, TreeNode root) {
        TreeNode result = null;
        if (cd != null) {
            result = new DefaultTreeNode(prefix
                    + (cd.getDisplayName() != null ? (cd.getDisplayName() + " - ") : "")
                    + cd.getCode() + " - " + cd.getCodeSystem(), root);
            result.setExpanded(true);
        }
        return result;
    }

    public static TreeNode addIvltsNode(String prefix, IVLTS timeRange, TreeNode root) {
        TreeNode result = null;
        if (timeRange != null) {
            String low = timeRange.getLow();
            String high = timeRange.getHigh();
            String nodeLabel = prefix;
            if (low != null) {
                nodeLabel += "low - " + low;
            }
            if (high != null) {
                nodeLabel += " high - " + high;
            }
            result = new DefaultTreeNode(nodeLabel, root);
            result.setExpanded(true);
        }
        return result;
    }

    public static TreeNode addBlNode(String prefix, BL bl, TreeNode root) {
        TreeNode result = null;
        if (bl != null) {
            result = new DefaultTreeNode(prefix + bl.isValue(), root);
            result.setExpanded(true);
        }
        return result;
    }

    public static TreeNode addRealNode(String prefix, REAL decimal, TreeNode root) {
        TreeNode result = null;
        if (decimal != null) {
            result = new DefaultTreeNode(prefix + decimal.getValue(), root);
            result.setExpanded(true);
        }
        return result;
    }

    public static TreeNode addRealRangeNode(String prefix, IVLREAL decimal, TreeNode root) {
        TreeNode result = null;
        if (decimal != null) {
            Double low = decimal.getLow();
            Double high = decimal.getHigh();
            String nodeLabel = prefix;
            if (low != null) {
                nodeLabel += "low - " + low;
            }
            if (high != null) {
                nodeLabel += " high - " + high;
            }
            result = new DefaultTreeNode(nodeLabel, root);
            result.setExpanded(true);
        }
        return result;
    }

    public static TreeNode addIiNode(String prefix, II identifier, TreeNode root) {
        TreeNode result = null;
        if (identifier != null) {
            String iiRoot = identifier.getRoot();
            String iiExtension = identifier.getExtension();
            String nodeLabel = prefix;
            if (iiRoot != null) {
                nodeLabel += "root - " + iiRoot;
            }
            if (iiExtension != null) {
                nodeLabel += " extension - " + iiExtension;
            }
            result = new DefaultTreeNode(nodeLabel, root);
            result.setExpanded(true);
        }
        return result;
    }

    public static TreeNode addIntNode(String prefix, INT integer, TreeNode root) {
        TreeNode result = null;
        if (integer != null) {
            result = new DefaultTreeNode(prefix + integer.getValue(), root);
            result.setExpanded(true);
        }
        return result;
    }

    public static TreeNode addIntRangeNode(String prefix, IVLINT integerRange, TreeNode root) {
        TreeNode result = null;
        if (integerRange != null) {
            Integer low = integerRange.getLow();
            Integer high = integerRange.getHigh();
            String nodeLabel = prefix;
            if (low != null) {
                nodeLabel += "low - " + low;
            }
            if (high != null) {
                nodeLabel += " high - " + high;
            }
            result = new DefaultTreeNode(nodeLabel, root);
            result.setExpanded(true);
        }
        return result;
    }

    public static TreeNode addPqNode(String prefix, PQ physicalQuantity, TreeNode root) {
        TreeNode result = null;
        if (physicalQuantity != null) {
            double value = physicalQuantity.getValue();
            String unit = physicalQuantity.getUnit();
            String nodeLabel = prefix;
            nodeLabel += value;
            if (unit != null) {
                nodeLabel += " " + unit;
            }
            result = new DefaultTreeNode(nodeLabel, root);
            result.setExpanded(true);
        }
        return result;
    }

    public static TreeNode addTextNode(String prefix, ST text, TreeNode root) {
        TreeNode result = null;
        if (text != null) {
            result = new DefaultTreeNode(prefix + text.getValue(), root);
            result.setExpanded(true);
        }
        return result;
    }

    public static TreeNode addTimeNode(String prefix, TS time, TreeNode root) {
        TreeNode result = null;
        if (time != null) {
            result = new DefaultTreeNode(prefix + time.getValue(), root);
            result.setExpanded(true);
        }
        return result;
    }

    public static TreeNode addBodySiteNode(String prefix, BodySite bodySite, TreeNode root) {
        TreeNode result = null;
        if (bodySite != null) {
            result = new DefaultTreeNode(prefix, root);
            result.setExpanded(true);

            TreeNode bodyiteCodeNode = addCdNode("Body Site Code: ", bodySite.getBodySiteCode(), result);
            bodyiteCodeNode.setExpanded(true);

            TreeNode lateralityNode = addCdNode("Body Site Laterality: ", bodySite.getLaterality(), result);
            lateralityNode.setExpanded(true);
        }
        return result;
    }

    static TreeNode addBodySitesNode(String prefix, List<BodySite> bodySites, TreeNode root) {
        TreeNode result = null;
        if (bodySites != null) {
            result = new DefaultTreeNode(prefix, root);
            result.setExpanded(true);
            for (BodySite bodySite : bodySites) {
                TreeNode bodySiteNode = DataTypeTreeNodeOutputAssist.addBodySiteNode("Body Site", bodySite, result);
            }
        }
        return result;
    }
}
