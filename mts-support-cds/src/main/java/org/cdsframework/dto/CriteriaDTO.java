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
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.CriteriaMethod;
import org.cdsframework.enumeration.CriteriaType;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.interfaces.CriteriaInterface;
import org.cdsframework.interfaces.PredicateInterface;
import org.cdsframework.util.comparator.CriteriaPredicateComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(fields = "lower(name)")
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = CriteriaPredicateDTO.class,
            childQueryClass = CriteriaPredicateDTO.ByCriteriaId.class,
            isAutoRetrieve = false,
            comparatorClass = CriteriaPredicateComparator.class),
    @ParentChildRelationship(childDtoClass = CriteriaVersionRelDTO.class,
            childQueryClass = CriteriaVersionRelDTO.ByCriteriaId.class,
            isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = CriteriaDataTemplateRelDTO.class,
            childQueryClass = CriteriaDataTemplateRelDTO.ByCriteriaId.class,
            isAutoRetrieve = false)
})
@Table(databaseId = "CDS", name = "criteria")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Criteria")
public class CriteriaDTO extends BaseDTO implements CriteriaInterface {

    private static final long serialVersionUID = -2266838538133744874L;

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String criteriaId;
    @NotNull
    private CriteriaType criteriaType;
    @NotNull
    @Size(max = 2048)
    private String description;
    @NotNull
    private CriteriaMethod method;
    @NotNull
    @Size(max = 1024)
    private String name;
    @Column(name = "ignore_criteria")
    private boolean ignore;

    /**
     * Get the value of ignore
     *
     * @return the value of ignore
     */
    @Override
    public boolean isIgnore() {
        return ignore;
    }

    /**
     * Set the value of ignore
     *
     * @param ignore new value of ignore
     */
    @PropertyListener
    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    @Override
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
     * Get the value of method
     *
     * @return the value of method
     */
    @Override
    public CriteriaMethod getMethod() {
        return method;
    }

    /**
     * Set the value of method
     *
     * @param method new value of method
     */
    @PropertyListener
    public void setMethod(CriteriaMethod method) {
        this.method = method;
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
     * Get the value of criteriaType
     *
     * @return the value of criteriaType
     */
    @Override
    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    /**
     * Set the value of criteriaType
     *
     * @param criteriaType new value of criteriaType
     */
    @PropertyListener
    public void setCriteriaType(CriteriaType criteriaType) {
        this.criteriaType = criteriaType;
    }

    /**
     * Get the value of criteriaId
     *
     * @return the value of criteriaId
     */
    @Override
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

    @XmlElementRef(name = "criteriaVersionRels")
    public List<CriteriaVersionRelDTO> getCriteriaVersionRelDTOs() {
        return getChildrenDTOs(CriteriaVersionRelDTO.ByCriteriaId.class, CriteriaVersionRelDTO.class);
    }

    @XmlElementRef(name = "predicates")
    public List<CriteriaPredicateDTO> getCriteriaPredicateDTOs() {
        return getChildrenDTOs(CriteriaPredicateDTO.ByCriteriaId.class, CriteriaPredicateDTO.class);
    }

    @XmlElementRef(name = "criteriaDataTemplateRels")
    public List<CriteriaDataTemplateRelDTO> getCriteriaDataTemplateRelDTOs() {
        return getChildrenDTOs(CriteriaDataTemplateRelDTO.ByCriteriaId.class, CriteriaDataTemplateRelDTO.class);
    }

    @Override
    public List<PredicateInterface> getPredicateDTOs() {
        return (List) getCriteriaPredicateDTOs();
    }

}
