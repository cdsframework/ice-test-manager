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

import javax.xml.bind.annotation.XmlRootElement;
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
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.util.comparator.SecuritySchemeRelMapComparator;

/**
 * Provides a data transfer object for a security scheme relationship mapping object.
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = SecuritySchemeRelMapComparator.class, fields = "last_mod_datetime")
@Table(databaseId = "MTS", name = "mt_security_scheme_rel_map")
@JndiReference(root = "mts-ejb-core")
@Permission(name = "Security Scheme Relationship Map")
@XmlRootElement(name = "SecuritySchemeMap")
public class SecuritySchemeRelMapDTO extends BaseDTO {

    public interface BySchemeId {
    }
    private static final long serialVersionUID = 8621785246018062619L;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = SecuritySchemeDTO.class)
    @Id
    private String schemeId;
    @ReferenceDTO
    @Id
    @Column(name = "rel_scheme_id")
    private SecuritySchemeDTO relatedSecuritySchemeDTO = new SecuritySchemeDTO();

    /**
     * Get the value of schemeId
     *
     * @return the value of schemeId
     */
    public String getSchemeId() {
        return schemeId;
    }

    /**
     * Set the value of schemeId
     *
     * @param schemeId new value of schemeId
     */
    @PropertyListener
    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    /**
     * Returns the related scheme DTO.
     *
     * @return the related scheme DTO.
     */
    public SecuritySchemeDTO getRelatedSecuritySchemeDTO() {
        return relatedSecuritySchemeDTO;
    }

    /**
     * Sets the related scheme DTO.
     *
     * @param relatedSecuritySchemeDTO the scheme DTO.
     */
    @PropertyListener
    public void setRelatedSecuritySchemeDTO(SecuritySchemeDTO relatedSecuritySchemeDTO) {
        this.relatedSecuritySchemeDTO = relatedSecuritySchemeDTO;
    }
}
