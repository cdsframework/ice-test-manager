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
import org.cdsframework.enumeration.ConceptConstraintType;
import org.cdsframework.enumeration.GenerationSource;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@Table(databaseId = "CDS", name = "criteria_predicate_part_rel")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Criteria Predicate Part Relationship", isListed = false)
@OrderBy(fields = "create_datetime")
public class CriteriaPredicatePartRelDTO extends BaseDTO {

    private static final long serialVersionUID = 508935618755678399L;

    public interface ByPartId {
    }

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String relId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = CriteriaPredicatePartDTO.class)
    private String partId;
    @NotNull
    private ConceptConstraintType constraintType;
    @ReferenceDTO
    @Column(name = "value_set_id")
    private ValueSetDTO valueSetDTO;
    @ReferenceDTO
    @Column(name = "list_id")
    private CdsListDTO cdsListDTO;
    @ReferenceDTO
    @Column(name = "code_system_id")
    private CdsCodeSystemDTO cdsCodeSystemDTO;
    @ReferenceDTO
    @Column(name = "code_id")
    private CdsCodeDTO cdsCodeDTO;
    @ReferenceDTO
    @Column(name = "concept_id")
    private OpenCdsConceptDTO openCdsConceptDTO;

    /**
     * Get the value of openCdsConceptDTO
     *
     * @return the value of openCdsConceptDTO
     */
    public OpenCdsConceptDTO getOpenCdsConceptDTO() {
        return openCdsConceptDTO;
    }

    /**
     * Set the value of openCdsConceptDTO
     *
     * @param openCdsConceptDTO new value of openCdsConceptDTO
     */
    @PropertyListener
    public void setOpenCdsConceptDTO(OpenCdsConceptDTO openCdsConceptDTO) {
        this.openCdsConceptDTO = openCdsConceptDTO;
    }

    /**
     * Get the value of cdsCodeDTO
     *
     * @return the value of cdsCodeDTO
     */
    public CdsCodeDTO getCdsCodeDTO() {
        return cdsCodeDTO;
    }

    /**
     * Set the value of cdsCodeDTO
     *
     * @param cdsCodeDTO new value of cdsCodeDTO
     */
    @PropertyListener
    public void setCdsCodeDTO(CdsCodeDTO cdsCodeDTO) {
        this.cdsCodeDTO = cdsCodeDTO;
    }

    /**
     * Get the value of partId
     *
     * @return the value of partId
     */
    public String getPartId() {
        return partId;
    }

    /**
     * Set the value of partId
     *
     * @param partId new value of partId
     */
    @PropertyListener
    public void setPartId(String partId) {
        this.partId = partId;
    }


    /**
     * Get the value of constraintType
     *
     * @return the value of constraintType
     */
    public ConceptConstraintType getConstraintType() {
        return constraintType;
    }

    /**
     * Set the value of constraintType
     *
     * @param constraintType new value of constraintType
     */
    @PropertyListener
    public void setConstraintType(ConceptConstraintType constraintType) {
        this.constraintType = constraintType;
    }

    /**
     * Get the value of cdsCodeSystemDTO
     *
     * @return the value of cdsCodeSystemDTO
     */
    public CdsCodeSystemDTO getCdsCodeSystemDTO() {
        return cdsCodeSystemDTO;
    }

    /**
     * Set the value of cdsCodeSystemDTO
     *
     * @param cdsCodeSystemDTO new value of cdsCodeSystemDTO
     */
    @PropertyListener
    public void setCdsCodeSystemDTO(CdsCodeSystemDTO cdsCodeSystemDTO) {
        this.cdsCodeSystemDTO = cdsCodeSystemDTO;
    }

    /**
     * Get the value of cdsListDTO
     *
     * @return the value of cdsListDTO
     */
    public CdsListDTO getCdsListDTO() {
        return cdsListDTO;
    }

    /**
     * Set the value of cdsListDTO
     *
     * @param cdsListDTO new value of cdsListDTO
     */
    @PropertyListener
    public void setCdsListDTO(CdsListDTO cdsListDTO) {
        this.cdsListDTO = cdsListDTO;
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

    /**
     * Get the value of valueSetDTO
     *
     * @return the value of valueSetDTO
     */
    public ValueSetDTO getValueSetDTO() {
        return valueSetDTO;
    }

    /**
     * Set the value of valueSetDTO
     *
     * @param valueSetDTO new value of valueSetDTO
     */
    @PropertyListener
    public void setValueSetDTO(ValueSetDTO valueSetDTO) {
        this.valueSetDTO = valueSetDTO;
    }

}
