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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElementRef;
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
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.enumeration.Status;
import org.cdsframework.util.comparator.CdsBusinessScopeComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = CdsVersionDTO.class, childQueryClass = CdsVersionDTO.ByBusinessScopeId.class, isAutoRetrieve = false)
})
@OrderBy(comparator = CdsBusinessScopeComparator.class, fields = "lower(business_id), lower(scoping_entity_id), lower(description)")
@Table(databaseId = "CDS", name = "cds_business_scope")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Business Scope")
public class CdsBusinessScopeDTO extends BaseDTO {

    public interface ByScopeBusinessId {
    }
    private static final long serialVersionUID = -6324104844352368094L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String businessScopeId;
    @NotNull
    @Size(max = 256)
    private String scopingEntityId;
    @NotNull
    @Size(max = 256)
    private String businessId;
    @Size(max = 512)
    private String description;
    private Status status;

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    @PropertyListener
    public void setStatus(Status status) {
        this.status = status;
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
     * Get the value of businessId
     *
     * @return the value of businessId
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * Set the value of businessId
     *
     * @param businessId new value of businessId
     */
    @PropertyListener
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    /**
     * Get the value of businessScopeId
     *
     * @return the value of businessScopeId
     */
    public String getBusinessScopeId() {
        return businessScopeId;
    }

    /**
     * Set the value of businessScopeId
     *
     * @param businessScopeId new value of businessScopeId
     */
    @PropertyListener
    public void setBusinessScopeId(String businessScopeId) {
        this.businessScopeId = businessScopeId;
    }

    /**
     * Get the value of scopingEntityId
     *
     * @return the value of scopingEntityId
     */
    public String getScopingEntityId() {
        return scopingEntityId;
    }

    /**
     * Set the value of scopingEntityId
     *
     * @param scopingEntityId new value of scopingEntityId
     */
    @PropertyListener
    public void setScopingEntityId(String scopingEntityId) {
        this.scopingEntityId = scopingEntityId;
    }

//    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@uuid")
    @XmlElementRef(name = "cdsVersions")
    public List<CdsVersionDTO> getCdsVersionDTOs() {
        return getChildrenDTOs(CdsVersionDTO.ByBusinessScopeId.class, CdsVersionDTO.class);
    }
}
