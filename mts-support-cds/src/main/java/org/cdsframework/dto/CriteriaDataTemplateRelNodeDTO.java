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
package org.cdsframework.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.util.comparator.CriteriaDataTemplateRelNodeComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = CriteriaDataTemplateRelNodeComparator.class, fields = "lower(node_path)")
@Table(databaseId = "CDS", name = "criteria_template_rel_node")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Criteria Template Relationship Node", isListed = false)
public class CriteriaDataTemplateRelNodeDTO extends BaseDTO {

    private static final long serialVersionUID = 7879444581851133874L;

    public interface ByParenRelId {
    }
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String relId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = CriteriaDataTemplateRelDTO.class)
    private String parentRelId;
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name = "node_id")
    private DataTemplateNodeRelDTO dataTemplateNodeRelDTO;
    @NotNull
    @Size(max = 2048)
    private String nodePath;

    /**
     * Get the value of nodePath
     *
     * @return the value of nodePath
     */
    public String getNodePath() {
        return nodePath;
    }

    /**
     * Set the value of nodePath
     *
     * @param nodePath new value of nodePath
     */
    @PropertyListener
    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }

    /**
     * Get the value of relId
     *
     * @return the value of relId
     */
    public String getRelId() {
        return relId;
    }

    /**
     * Set the value of relId
     *
     * @param relId new value of relId
     */
    @PropertyListener
    public void setRelId(String relId) {
        this.relId = relId;
    }

    public DataTemplateNodeRelDTO getDataTemplateNodeRelDTO() {
        return dataTemplateNodeRelDTO;
    }

    @PropertyListener
    public void setDataTemplateNodeRelDTO(DataTemplateNodeRelDTO dataTemplateNodeRelDTO) {
        this.dataTemplateNodeRelDTO = dataTemplateNodeRelDTO;
    }

    /**
     * Get the value of parentRelId
     *
     * @return the value of parentRelId
     */
    public String getParentRelId() {
        return parentRelId;
    }

    /**
     * Set the value of parentRelId
     *
     * @param parentRelId new value of parentRelId
     */
    @PropertyListener
    public void setParentRelId(String parentRelId) {
        this.parentRelId = parentRelId;
    }

    public String getLabel() {
        return String.format("[%s]", getNodePath());
    }
}
