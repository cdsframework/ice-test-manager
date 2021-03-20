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
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@Table(databaseId = "CDS", name = "cds_version_cdm_rel")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Version/Concept Determination Method Relationship", isListed = false)
public class CdsVersionConceptDeterminationMethodRelDTO extends BaseDTO {

    public interface ByVersionId {
    }
    private static final long serialVersionUID = -8043372411493333009L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String relationshipId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = CdsVersionDTO.class)
    @NotNull
    private String versionId;
    @NotNull
    @Column(name = "determination_method")
    @ReferenceDTO(isNotFoundAllowed = false)
    private ConceptDeterminationMethodDTO conceptDeterminationMethodDTO;

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
     * @param conceptDeterminationMethodDTO new value of conceptDeterminationMethodDTO
     */
    @PropertyListener
    public void setConceptDeterminationMethodDTO(ConceptDeterminationMethodDTO conceptDeterminationMethodDTO) {
        this.conceptDeterminationMethodDTO = conceptDeterminationMethodDTO;
    }

    /**
     * Get the value of versionId
     *
     * @return the value of versionId
     */
    public String getVersionId() {
        return versionId;
    }

    /**
     * Set the value of versionId
     *
     * @param versionId new value of versionId
     */
    @PropertyListener
    public void setVersionId(String versionId) {
        this.versionId = versionId;
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
}
