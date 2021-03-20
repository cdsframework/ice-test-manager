/**
 * The MTS support core project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.SortColumn;
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
import org.cdsframework.util.comparator.SecuritySchemeComparator;

/**
 * Provides a data transfer object for conveyance of a security scheme context object.
 *
 * @author HLN Consulting, LLC
 */
@Entity
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = SecurityPermissionDTO.class, childQueryClass = SecurityPermissionDTO.BySchemeId.class, isAutoRetrieve = true),
    @ParentChildRelationship(childDtoClass = SecuritySchemeRelMapDTO.class, childQueryClass = SecuritySchemeRelMapDTO.BySchemeId.class, isAutoRetrieve = true)
})
@OrderBy(comparator = SecuritySchemeComparator.class, fields = "lower(scheme_name)")
@Table(databaseId = "MTS", name = "mt_security_scheme")
@JndiReference(root = "mts-ejb-core", remote = "SchemeMGR")
@Permission(name = "Security Scheme")
@XmlRootElement(name = "SecurityScheme")
public class SecuritySchemeDTO extends BaseDTO {

    public interface BySchemeName {
    }
    /**
     * The serializable class UID.
     */
    private static final long serialVersionUID = -5341473515381230564L;
    /**
     * The scheme ID.
     */
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String schemeId;
    /**
     * The scheme name.
     */
    @NotNull
    @SortColumn(sortFieldValue = "lower(scheme_name)")
    private String schemeName;
    /**
     * The description.
     */
    @SortColumn(sortFieldValue = "lower(description)")
    private String description;

    /**
     * Returns the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the description.
     */
    @PropertyListener
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a list of SecurityPermissionDTOs.
     *
     * @return a list of SecurityPermissionDTOs.
     */
    @XmlElementRef(name = "securityPermissions")
    public List<SecurityPermissionDTO> getSecurityPermissionDTOs() {
        return (List) this.getChildrenDTOs(SecurityPermissionDTO.BySchemeId.class);
    }

    /**
     * Sets a list of SecurityPermissionDTOs.
     *
     * @param permissions a list of SecurityPermissionDTOs.
     */
    public void setSecurityPermissionDTOs(List<SecurityPermissionDTO> permissions) {
        this.setChildrenDTOs(SecurityPermissionDTO.BySchemeId.class, (List) permissions);
    }

    /**
     * Returns the scheme ID.
     *
     * @return the scheme ID.
     */
    public String getSchemeId() {
        return schemeId;
    }

    /**
     * Sets the scheme ID.
     *
     * @param schemeId the scheme ID.
     */
    @PropertyListener
    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    /**
     * Returns the scheme name.
     *
     * @return the scheme name.
     */
    public String getSchemeName() {
        return schemeName;
    }

    /**
     * Sets the scheme name.
     *
     * @param schemeName the scheme name.
     */
    @PropertyListener
    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    @XmlElementRef(name = "securitySchemeRelMaps")
    public List<SecuritySchemeRelMapDTO> getSecuritySchemeRelMapDTOs() {
        List<SecuritySchemeRelMapDTO> securitySchemeRelMapDTOs = getChildrenDTOs(SecuritySchemeRelMapDTO.BySchemeId.class, SecuritySchemeRelMapDTO.class);
        return securitySchemeRelMapDTOs;
    }
}
