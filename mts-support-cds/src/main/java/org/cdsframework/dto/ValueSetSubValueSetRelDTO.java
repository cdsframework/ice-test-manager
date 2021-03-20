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
@Table(databaseId = "CDS", name = "value_set_sub_value_set_rel")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Value Set Subvalue Set Relationship", isListed = false)
public class ValueSetSubValueSetRelDTO extends BaseDTO {

    public interface ByValueSetId {
    }
    private static final long serialVersionUID = -8184473499750197840L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String valueSetSubValueSetRelId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = ValueSetDTO.class)
    private String valueSetId;
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name = "sub_value_set_id")
    private ValueSetDTO subValueSetDTO;

    /**
     * Get the value of subValueSetDTO
     *
     * @return the value of subValueSetDTO
     */
    public ValueSetDTO getSubValueSetDTO() {
        return subValueSetDTO;
    }

    /**
     * Set the value of subValueSetDTO
     *
     * @param subValueSetDTO new value of subValueSetDTO
     */
    @PropertyListener
    public void setSubValueSetDTO(ValueSetDTO subValueSetDTO) {
        this.subValueSetDTO = subValueSetDTO;
    }

    /**
     * Get the value of valueSetId
     *
     * @return the value of valueSetId
     */
    public String getValueSetId() {
        return valueSetId;
    }

    /**
     * Set the value of valueSetId
     *
     * @param valueSetId new value of valueSetId
     */
    @PropertyListener
    public void setValueSetId(String valueSetId) {
        this.valueSetId = valueSetId;
    }

    /**
     * Get the value of valueSetSubValueSetRelId
     *
     * @return the value of valueSetSubValueSetRelId
     */
    public String getValueSetSubValueSetRelId() {
        return valueSetSubValueSetRelId;
    }

    /**
     * Set the value of valueSetSubValueSetRelId
     *
     * @param valueSetSubValueSetRelId new value of valueSetSubValueSetRelId
     */
    @PropertyListener
    public void setValueSetSubValueSetRelId(String valueSetSubValueSetRelId) {
        this.valueSetSubValueSetRelId = valueSetSubValueSetRelId;
    }
}
