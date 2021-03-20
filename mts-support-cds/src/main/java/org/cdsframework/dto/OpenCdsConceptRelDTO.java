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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.Ignore;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.DeploymentEnvironment;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.enumeration.MappingType;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = OpenCdsConceptDeploymentLogDTO.class, childQueryClass = OpenCdsConceptDeploymentLogDTO.ByRelationshipId.class, isAutoRetrieve = false)
})
@Table(databaseId = "CDS", name = "opencds_concept_rel", view = "vw_opencds_concept_rel")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Concept/Code Relationship")
@XmlRootElement(name = "ConceptRel")
public class OpenCdsConceptRelDTO extends BaseDTO {

    public interface ByOpenCdsConceptId {
    }

    public interface ByCodeId {
    }

    public interface ByOpenCdsConceptIdCodeIdConceptDeterminationMethodId {
    }

    public interface CountByConceptCodeIdCodeSystemId {
    }
    private static final long serialVersionUID = -5142363253535643619L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String relationshipId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = OpenCdsConceptDTO.class)
    private String conceptCodeId;
    @Column(name = "cds_code_id")
    @ReferenceDTO(isNotFoundAllowed = false)
    private CdsCodeDTO cdsCodeDTO;
    @Column(name = "determination_method")
    @ReferenceDTO(isNotFoundAllowed = false)
    private ConceptDeterminationMethodDTO conceptDeterminationMethodDTO;
    @Column(name = "code_system_id")
    @ReferenceDTO(isNotFoundAllowed = false)
    private CdsCodeSystemDTO cdsCodeSystemDTO;
    @Column(name = "value_set_id")
    @ReferenceDTO(isNotFoundAllowed = false)
    private ValueSetDTO valueSetDTO;
    @Size(max = 512)
    private String specificationNotes;
    @NotNull
    private MappingType mappingType;
    @JsonProperty    
    @XmlTransient
    @Ignore
    private String oid;
    @JsonProperty    
    @XmlTransient
    @Ignore
    private String name;
    private boolean disabled;
    @NotNull
    private DeploymentEnvironment deploymentEnvironment;
    @Column(name = "last_deployed", updateable = false, insertable = false)
    private Date lastDeployedDate;

    /**
     * Get the value of lastDeployedDate
     *
     * @return the value of lastDeployedDate
     */
    public Date getLastDeployedDate() {
        return lastDeployedDate;
    }

    /**
     * Set the value of lastDeployedDate
     *
     * @param lastDeployedDate new value of lastDeployedDate
     */
    public void setLastDeployedDate(Date lastDeployedDate) {
        this.lastDeployedDate = lastDeployedDate;
    }

    /**
     * Get the value of deploymentEnvironment
     *
     * @return the value of deploymentEnvironment
     */
    public DeploymentEnvironment getDeploymentEnvironment() {
        return deploymentEnvironment;
    }

    /**
     * Set the value of deploymentEnvironment
     *
     * @param deploymentEnvironment new value of deploymentEnvironment
     */
    @PropertyListener
    public void setDeploymentEnvironment(DeploymentEnvironment deploymentEnvironment) {
        this.deploymentEnvironment = deploymentEnvironment;
    }

    /**
     * Get the value of disabled
     *
     * @return the value of disabled
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Set the value of disabled
     *
     * @param disabled new value of disabled
     */
    @PropertyListener
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of oid
     *
     * @return the value of oid
     */
    public String getOid() {
        return oid;
    }

    /**
     * Set the value of oid
     *
     * @param oid new value of oid
     */
    public void setOid(String oid) {
        this.oid = oid;
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
     * Get the value of specificationNotes
     *
     * @return the value of specificationNotes
     */
    public String getSpecificationNotes() {
        return specificationNotes;
    }

    /**
     * Set the value of specificationNotes
     *
     * @param specificationNotes new value of specificationNotes
     */
    @PropertyListener
    public void setSpecificationNotes(String specificationNotes) {
        this.specificationNotes = specificationNotes;
    }

    /**
     * Get the value of conceptDeterminationMethodDTO
     *
     * @return the value of conceptDeterminationMethodDTO
     */
    public ConceptDeterminationMethodDTO getConceptDeterminationMethodDTO() {
        return conceptDeterminationMethodDTO;
    }

    /**
     * Set the value of conceptDeterminationMethodDTO
     *
     * @param conceptDeterminationMethodDTO new value of
     * conceptDeterminationMethodDTO
     */
    @PropertyListener
    public void setConceptDeterminationMethodDTO(ConceptDeterminationMethodDTO conceptDeterminationMethodDTO) {
        this.conceptDeterminationMethodDTO = conceptDeterminationMethodDTO;
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
     * Get the value of conceptCodeId
     *
     * @return the value of conceptCodeId
     */
    public String getConceptCodeId() {
        return conceptCodeId;
    }

    /**
     * Set the value of conceptCodeId
     *
     * @param conceptCodeId new value of conceptCodeId
     */
    @PropertyListener
    public void setConceptCodeId(String conceptCodeId) {
        this.conceptCodeId = conceptCodeId;
    }

    /**
     * Get the value of relationshipId
     *
     * @return the value of relationshipId
     */
    public String getRelationshipId() {
        return relationshipId;
    }

    /**
     * Set the value of relationshipId
     *
     * @param relationshipId new value of relationshipId
     */
    @PropertyListener
    public void setRelationshipId(String relationshipId) {
        this.relationshipId = relationshipId;
    }

    /**
     * Get the value of mappingType
     *
     * @return the value of mappingType
     */
    public MappingType getMappingType() {
        return mappingType;
    }

    /**
     * Set the value of mappingType
     *
     * @param mappingType new value of mappingType
     */
    @PropertyListener
    public void setMappingType(MappingType mappingType) {
        this.mappingType = mappingType;
    }

    /**
     * Get the list of OpenCdsConceptRelDTOs.
     *
     * @return
     */
    @XmlElementRef(name = "mappingDeploymentLogs")
    public List<OpenCdsConceptDeploymentLogDTO> getOpenCdsConceptDeploymentLogDTOs() {
        return getChildrenDTOs(OpenCdsConceptDeploymentLogDTO.ByRelationshipId.class, OpenCdsConceptDeploymentLogDTO.class);
    }
}
