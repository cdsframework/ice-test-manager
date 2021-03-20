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
import org.cdsframework.annotation.RefreshOnAddOrUpdate;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@RefreshOnAddOrUpdate
@Table(databaseId = "CDS", name = "value_set_cds_code_rel")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Value Set Code Relationship", isListed = false)
public class ValueSetCdsCodeRelDTO extends BaseDTO {

    public interface ByValueSetId {
    }
    public interface ByValueSetOid {
    }
    public interface ByCodeId {
    }
    private static final long serialVersionUID = -8698660008666757958L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String valueSetCdsCodeRelId;
    @NotNull
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name="code_id")
    private CdsCodeDTO cdsCodeDTO;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = ValueSetDTO.class)
    private String valueSetId;

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
     * Get the value of cdsCodeDTO
     *
     * @return the value of cdsCodeDTO
     */
    public CdsCodeDTO getCdsCodeDTO() {
        if (cdsCodeDTO == null) {
            cdsCodeDTO = new CdsCodeDTO();
        }
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
     * Get the value of valueSetCdsCodeRelId
     *
     * @return the value of valueSetCdsCodeRelId
     */
    public String getValueSetCdsCodeRelId() {
        return valueSetCdsCodeRelId;
    }

    /**
     * Set the value of valueSetCdsCodeRelId
     *
     * @param valueSetCdsCodeRelId new value of valueSetCdsCodeRelId
     */
    @PropertyListener
    public void setValueSetCdsCodeRelId(String valueSetCdsCodeRelId) {
        this.valueSetCdsCodeRelId = valueSetCdsCodeRelId;
    }
}
