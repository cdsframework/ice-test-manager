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
import org.cdsframework.enumeration.ConformanceType;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.util.comparator.DataTemplateNodeRelComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = DataTemplateNodeRelComparator.class)
@Table(databaseId = "CDS", name = "data_template_node_rel")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Data Template Node Relationship", isListed = false)
public class DataTemplateNodeRelDTO extends BaseDTO {

    private static final long serialVersionUID = 8260365417310511301L;

    public interface ByTemplateId {
    }

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String nodeId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = DataTemplateDTO.class)
    private String templateId;
    @NotNull
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name = "source_node_id")
    private DataModelClassNodeDTO dataModelClassNodeDTO;
    private boolean mandatory;
    private ConformanceType conformanceType;
    private boolean fixedValue;
    private boolean adHoc;
    @Column(name = "class_constraints")
    @Size(max = 2048)
    private String constraints;
    @Size(max = 2048)
    private String comments;
    @Size(max = 2048)
    @NotNull
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
     * Get the value of dataModelClassNodeDTO
     *
     * @return the value of dataModelClassNodeDTO
     */
    public DataModelClassNodeDTO getDataModelClassNodeDTO() {
        return dataModelClassNodeDTO;
    }

    /**
     * Set the value of dataModelClassNodeDTO
     *
     * @param dataModelClassNodeDTO new value of dataModelClassNodeDTO
     */
    @PropertyListener
    public void setDataModelClassNodeDTO(DataModelClassNodeDTO dataModelClassNodeDTO) {
        this.dataModelClassNodeDTO = dataModelClassNodeDTO;
    }

    /**
     * Get the value of templateId
     *
     * @return the value of templateId
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * Set the value of templateId
     *
     * @param templateId new value of templateId
     */
    @PropertyListener
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * Get the value of nodeId
     *
     * @return the value of nodeId
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * Set the value of nodeId
     *
     * @param nodeId new value of nodeId
     */
    @PropertyListener
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * Get the value of comments
     *
     * @return the value of comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Set the value of comments
     *
     * @param comments new value of comments
     */
    @PropertyListener
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Get the value of constraints
     *
     * @return the value of constraints
     */
    public String getConstraints() {
        return constraints;
    }

    /**
     * Set the value of constraints
     *
     * @param constraints new value of constraints
     */
    @PropertyListener
    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    /**
     * Get the value of adHoc
     *
     * @return the value of adHoc
     */
    public boolean isAdHoc() {
        return adHoc;
    }

    /**
     * Set the value of adHoc
     *
     * @param adHoc new value of adHoc
     */
    @PropertyListener
    public void setAdHoc(boolean adHoc) {
        this.adHoc = adHoc;
    }

    /**
     * Get the value of fixedValue
     *
     * @return the value of fixedValue
     */
    public boolean isFixedValue() {
        return fixedValue;
    }

    /**
     * Set the value of fixedValue
     *
     * @param fixedValue new value of fixedValue
     */
    @PropertyListener
    public void setFixedValue(boolean fixedValue) {
        this.fixedValue = fixedValue;
    }

    /**
     * Get the value of conformanceType
     *
     * @return the value of conformanceType
     */
    public ConformanceType getConformanceType() {
        return conformanceType;
    }

    /**
     * Set the value of conformanceType
     *
     * @param conformanceType new value of conformanceType
     */
    @PropertyListener
    public void setConformanceType(ConformanceType conformanceType) {
        this.conformanceType = conformanceType;
    }

    /**
     * Get the value of mandatory
     *
     * @return the value of mandatory
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * Set the value of mandatory
     *
     * @param mandatory new value of mandatory
     */
    @PropertyListener
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
}
