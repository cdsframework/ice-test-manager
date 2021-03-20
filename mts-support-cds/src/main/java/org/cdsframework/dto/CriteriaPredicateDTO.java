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

import java.util.ArrayList;
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
import org.cdsframework.base.BasePredicateDTO;
import org.cdsframework.enumeration.CriteriaPredicateType;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.enumeration.PredicateConjunction;
import org.cdsframework.enumeration.PredicatePartType;
import org.cdsframework.util.comparator.CriteriaPredicateComparator;
import org.cdsframework.util.comparator.CriteriaPredicatePartComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@OrderBy(comparator = CriteriaPredicateComparator.class, fields = "predicate_order")
@Entity
@Table(databaseId = "CDS", name = "criteria_predicate")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Criteria Predicate", isListed = false)
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = CriteriaPredicateDTO.class, childQueryClass = CriteriaPredicateDTO.ByParentPredicateId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = CriteriaPredicatePartDTO.class, childQueryClass = CriteriaPredicatePartDTO.ByPredicateId.class, isAutoRetrieve = false,
            comparatorClass = CriteriaPredicatePartComparator.class)
})
public class CriteriaPredicateDTO extends BasePredicateDTO {

    private static final long serialVersionUID = -5186139518342584180L;

    public interface ByCriteriaId {
    }

    public interface ByParentPredicateId {
    }

    public interface MaxOrderByCriteriaId {
    }

    public interface MaxOrderByPredicateId {
    }

    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = CriteriaDTO.class)
    private String criteriaId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = CriteriaPredicateDTO.class)
    private String parentPredicateId;
    @Size(max = 2048)
    private String description;
    @NotNull
    private CriteriaPredicateType predicateType;
    @NotNull
    private int predicateOrder;
    @NotNull
    private PredicateConjunction predicateConjunction;
    @ReferenceDTO(isNotFoundAllowed = true)
    @Column(name = "predicate_criteria_id")
    private CriteriaDTO predicateCriteriaDTO;

    @Override
    public boolean isSourcedPredicate() {
        return false;
    }

    @Override
    public CriteriaDTO getPredicateCriteriaDTO() {
        return predicateCriteriaDTO;
    }

    @PropertyListener
    public void setPredicateCriteriaDTO(CriteriaDTO predicateCriteriaDTO) {
        this.predicateCriteriaDTO = predicateCriteriaDTO;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    @Override
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
     * Get the value of predicateConjunction
     *
     * @return the value of predicateConjunction
     */
    @Override
    public PredicateConjunction getPredicateConjunction() {
        return predicateConjunction;
    }

    /**
     * Set the value of predicateConjunction
     *
     * @param predicateConjunction new value of predicateConjunction
     */
    @PropertyListener
    public void setPredicateConjunction(PredicateConjunction predicateConjunction) {
        this.predicateConjunction = predicateConjunction;
    }

    /**
     * Get the value of predicateOrder
     *
     * @return the value of predicateOrder
     */
    @Override
    public int getPredicateOrder() {
        return predicateOrder;
    }

    /**
     * Set the value of predicateOrder
     *
     * @param predicateOrder new value of predicateOrder
     */
    @PropertyListener
    public void setPredicateOrder(int predicateOrder) {
        this.predicateOrder = predicateOrder;
    }

    /**
     * Get the value of predicateType
     *
     * @return the value of predicateType
     */
    @Override
    public CriteriaPredicateType getPredicateType() {
        return predicateType;
    }

    /**
     * Set the value of predicateType
     *
     * @param predicateType new value of predicateType
     */
    @PropertyListener
    public void setPredicateType(CriteriaPredicateType predicateType) {
        this.predicateType = predicateType;
    }

    /**
     * Get the value of criteriaId
     *
     * @return the value of criteriaId
     */
    public String getCriteriaId() {
        return criteriaId;
    }

    /**
     * Set the value of criteriaId
     *
     * @param criteriaId new value of criteriaId
     */
    @PropertyListener
    public void setCriteriaId(String criteriaId) {
        this.criteriaId = criteriaId;
    }

    /**
     * Get the value of parentPredicateId
     *
     * @return the value of parentPredicateId
     */
    @Override
    public String getParentPredicateId() {
        return parentPredicateId;
    }

    /**
     * Set the value of parentPredicateId
     *
     * @param parentPredicateId new value of parentPredicateId
     */
    @PropertyListener
    @Override
    public void setParentPredicateId(String parentPredicateId) {
        this.parentPredicateId = parentPredicateId;
    }

    @XmlElementRef(name = "predicates")
    @Override
    public List<CriteriaPredicateDTO> getPredicateDTOs() {
        return (List) getChildrenDTOs(CriteriaPredicateDTO.ByParentPredicateId.class, CriteriaPredicateDTO.class);
    }

    @XmlElementRef(name = "predicateParts")
    @Override
    public List<CriteriaPredicatePartDTO> getPredicatePartDTOs() {
        return (List) getChildrenDTOs(CriteriaPredicatePartDTO.ByPredicateId.class, CriteriaPredicatePartDTO.class);
    }

    @Override
    public List<CriteriaPredicatePartDTO> getGroupPredicatePartDTOs() {
        List<CriteriaPredicatePartDTO> childrenDTOs = new ArrayList<CriteriaPredicatePartDTO>();
        childrenDTOs.addAll(getPredicatePartDTOs());
        CriteriaPredicatePartDTO closer = new CriteriaPredicatePartDTO();
        closer.setPartType(PredicatePartType.Text);
        closer.setText("{");
        childrenDTOs.add(closer);
        return childrenDTOs;
    }
}
