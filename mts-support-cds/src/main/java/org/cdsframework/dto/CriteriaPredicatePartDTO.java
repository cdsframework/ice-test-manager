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

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElementRef;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BasePredicatePartDTO;
import org.cdsframework.enumeration.ConceptSelectionType;
import org.cdsframework.enumeration.CriteriaResourceType;
import org.cdsframework.enumeration.DataModelClassType;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.enumeration.PredicatePartType;
import org.cdsframework.util.comparator.CriteriaPredicatePartComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@OrderBy(comparator = CriteriaPredicatePartComparator.class, fields = "predicate_part_order")
@Entity
@Table(databaseId = "CDS", name = "criteria_predicate_part")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Criteria Predicate Part", isListed = false)
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = CriteriaPredicatePartRelDTO.class, childQueryClass = CriteriaPredicatePartRelDTO.ByPartId.class, isAutoRetrieve = true),
    @ParentChildRelationship(childDtoClass = CriteriaPredicatePartConceptDTO.class, childQueryClass = CriteriaPredicatePartConceptDTO.ByPartId.class, isAutoRetrieve = true)
})
public class CriteriaPredicatePartDTO extends BasePredicatePartDTO {

    private static final long serialVersionUID = -9041165111262736016L;

    public interface ByPredicateId {
    }

    public interface MaxOrderByPredicateId {
    }

    public interface GetLabel {
    }

    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = CriteriaPredicateDTO.class)
    private String predicateId;
    @Size(max = 512)
    private String partAlias;
    @NotNull
    private PredicatePartType partType;
    @ReferenceDTO
    @Column(name = "node_rel_id")
    private CriteriaDataTemplateRelNodeDTO criteriaDataTemplateRelNodeDTO;
    @NotNull
    private int predicatePartOrder;
    private ConceptSelectionType conceptSelectionType;
    private DataModelClassType dataInputClassType;
    private CriteriaResourceType resourceType;

    /**
     * Get the value of resourceType
     *
     * @return the value of resourceType
     */
    @Override
    public CriteriaResourceType getResourceType() {
        return resourceType;
    }

    /**
     * Set the value of resourceType
     *
     * @param resourceType new value of resourceType
     */
    @PropertyListener
    public void setResourceType(CriteriaResourceType resourceType) {
        this.resourceType = resourceType;
    }


    /**
     * Get the value of dataInputClassType
     *
     * @return the value of dataInputClassType
     */
    @Override
    public DataModelClassType getDataInputClassType() {
        return dataInputClassType;
    }

    /**
     * Set the value of dataInputClassType
     *
     * @param dataInputClassType new value of dataInputClassType
     */
    @PropertyListener
    public void setDataInputClassType(DataModelClassType dataInputClassType) {
        this.dataInputClassType = dataInputClassType;
    }

    /**
     * Get the value of conceptSelectionType
     *
     * @return the value of conceptSelectionType
     */
    @Override
    public ConceptSelectionType getConceptSelectionType() {
        return conceptSelectionType;
    }

    /**
     * Set the value of conceptSelectionType
     *
     * @param conceptSelectionType new value of conceptSelectionType
     */
    @PropertyListener
    public void setConceptSelectionType(ConceptSelectionType conceptSelectionType) {
        this.conceptSelectionType = conceptSelectionType;
    }

    /**
     * Get the value of partAlias
     *
     * @return the value of partAlias
     */
    @Override
    public String getPartAlias() {
        return partAlias;
    }

    /**
     * Set the value of partAlias
     *
     * @param partAlias new value of partAlias
     */
    @PropertyListener
    public void setPartAlias(String partAlias) {
        this.partAlias = partAlias;
    }

    /**
     * Get the value of predicatePartOrder
     *
     * @return the value of predicatePartOrder
     */
    @Override
    public int getPredicatePartOrder() {
        return predicatePartOrder;
    }

    /**
     * Set the value of predicatePartOrder
     *
     * @param predicatePartOrder new value of predicatePartOrder
     */
    @PropertyListener
    public void setPredicatePartOrder(int predicatePartOrder) {
        this.predicatePartOrder = predicatePartOrder;
    }

    /**
     * Get the value of criteriaDataTemplateRelNodeDTO
     *
     * @return the value of criteriaDataTemplateRelNodeDTO
     */
    @Override
    public CriteriaDataTemplateRelNodeDTO getCriteriaDataTemplateRelNodeDTO() {
        return criteriaDataTemplateRelNodeDTO;
    }

    /**
     * Set the value of criteriaDataTemplateRelNodeDTO
     *
     * @param criteriaDataTemplateRelNodeDTO new value of criteriaDataTemplateRelNodeDTO
     */
    @PropertyListener
    public void setCriteriaDataTemplateRelNodeDTO(CriteriaDataTemplateRelNodeDTO criteriaDataTemplateRelNodeDTO) {
        this.criteriaDataTemplateRelNodeDTO = criteriaDataTemplateRelNodeDTO;
    }

    /**
     * Get the value of predicateId
     *
     * @return the value of predicateId
     */
    public String getPredicateId() {
        return predicateId;
    }

    /**
     * Set the value of predicateId
     *
     * @param predicateId new value of predicateId
     */
    @PropertyListener
    public void setPredicateId(String predicateId) {
        this.predicateId = predicateId;
    }

    /**
     * Get the value of partType
     *
     * @return the value of partType
     */
    @Override
    public PredicatePartType getPartType() {
        return partType;
    }

    /**
     * Set the value of partType
     *
     * @param partType new value of partType
     */
    @PropertyListener
    public void setPartType(PredicatePartType partType) {
        this.partType = partType;
    }

    @XmlElementRef(name = "predicatePartRels")
    @Override
    public List<CriteriaPredicatePartRelDTO> getPredicatePartRelDTOs() {
        return getChildrenDTOs(CriteriaPredicatePartRelDTO.ByPartId.class, CriteriaPredicatePartRelDTO.class);
    }

    @XmlElementRef(name = "predicatePartConcepts")
    @Override
    public List<CriteriaPredicatePartConceptDTO> getPredicatePartConceptDTOs() {
        return (List) getChildrenDTOs(CriteriaPredicatePartConceptDTO.ByPartId.class, CriteriaPredicatePartConceptDTO.class);
    }    
}
