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
import org.cdsframework.annotation.Ignore;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.CardinalityType;
import org.cdsframework.enumeration.DataModelClassType;
import org.cdsframework.enumeration.DataModelNodeType;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.enumeration.XsdAttributeUseType;
import org.cdsframework.util.comparator.DataModelClassNodeComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = DataModelClassNodeComparator.class, fields = "lower(name)")
@Table(databaseId = "CDS", name = "data_model_class_node")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Data Model Class Node", isListed = false)
public class DataModelClassNodeDTO extends BaseDTO {

    private static final long serialVersionUID = 5369554755762505911L;

    public interface ByClassId {
    }

    public interface ByModelId {
    }

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String nodeId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = DataModelClassDTO.class)
    private String classId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = DataModelDTO.class)
    private String modelId;
    @NotNull
    @Size(max = 512)
    private String name;
    @Size(max = 2048)
    private String description;
    private CardinalityType cardinalityType;
    @NotNull
    private DataModelNodeType nodeType;
    @NotNull
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name = "node_class_id")
    private DataModelClassDTO dataModelClassDTO;
    private XsdAttributeUseType useAttribute;
    @Column(name = "is_sequence")
    private boolean sequence;
    @Column(name = "is_choice")
    private boolean choice;

    // for internal use only...
    @Ignore
    private DataModelClassDTO parentDataModelClassDTO;

    /**
     * Get the value of modelId
     *
     * @return the value of modelId
     */
    public String getModelId() {
        return modelId;
    }

    /**
     * Set the value of modelId
     *
     * @param modelId new value of modelId
     */
    @PropertyListener
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    /**
     * Get the value of choice
     *
     * @return the value of choice
     */
    public boolean isChoice() {
        return choice;
    }

    /**
     * Set the value of choice
     *
     * @param choice new value of choice
     */
    @PropertyListener
    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    /**
     * Get the value of parentDataModelClassDTO
     *
     * @return the value of parentDataModelClassDTO
     */
    public DataModelClassDTO getParentDataModelClassDTO() {
        return parentDataModelClassDTO;
    }

    /**
     * Set the value of parentDataModelClassDTO
     *
     * @param parentDataModelClassDTO new value of parentDataModelClassDTO
     */
    @PropertyListener
    public void setParentDataModelClassDTO(DataModelClassDTO parentDataModelClassDTO) {
        this.parentDataModelClassDTO = parentDataModelClassDTO;
    }

    /**
     * Get the value of sequence
     *
     * @return the value of sequence
     */
    public boolean isSequence() {
        return sequence;
    }

    /**
     * Set the value of sequence
     *
     * @param sequence new value of sequence
     */
    @PropertyListener
    public void setSequence(boolean sequence) {
        this.sequence = sequence;
    }

    /**
     * Get the value of useAttribute
     *
     * @return the value of useAttribute
     */
    public XsdAttributeUseType getUseAttribute() {
        return useAttribute;
    }

    /**
     * Set the value of useAttribute
     *
     * @param useAttribute new value of useAttribute
     */
    @PropertyListener
    public void setUseAttribute(XsdAttributeUseType useAttribute) {
        this.useAttribute = useAttribute;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    @PropertyListener
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    @PropertyListener
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the value of dataModelClassDTO
     *
     * @return the value of dataModelClassDTO
     */
    public DataModelClassDTO getDataModelClassDTO() {
        return dataModelClassDTO;
    }

    /**
     * Set the value of dataModelClassDTO
     *
     * @param dataModelClassDTO new value of dataModelClassDTO
     */
    @PropertyListener
    public void setDataModelClassDTO(DataModelClassDTO dataModelClassDTO) {
        this.dataModelClassDTO = dataModelClassDTO;
    }

    /**
     * Get the value of nodeType
     *
     * @return the value of nodeType
     */
    public DataModelNodeType getNodeType() {
        return nodeType;
    }

    /**
     * Set the value of nodeType
     *
     * @param nodeType new value of nodeType
     */
    @PropertyListener
    public void setNodeType(DataModelNodeType nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * Get the value of cardinalityType
     *
     * @return the value of cardinalityType
     */
    public CardinalityType getCardinalityType() {
        return cardinalityType;
    }

    /**
     * Set the value of cardinalityType
     *
     * @param cardinalityType new value of cardinalityType
     */
    @PropertyListener
    public void setCardinalityType(CardinalityType cardinalityType) {
        this.cardinalityType = cardinalityType;
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
     * Get the value of classId
     *
     * @return the value of classId
     */
    public String getClassId() {
        return classId;
    }

    /**
     * Set the value of classId
     *
     * @param classId new value of classId
     */
    @PropertyListener
    public void setClassId(String classId) {
        this.classId = classId;
    }

    /**
     * Get the value of classType
     *
     * @return the value of classType
     */
    public DataModelClassType getClassType() {
        return dataModelClassDTO != null ? dataModelClassDTO.getClassType() : null;
    }

    /**
     * Get the value of classType
     *
     * @return the value of classType
     */
    public String getDataModelClassDTOName() {
        return dataModelClassDTO != null ? dataModelClassDTO.getName(): null;
    }
}
