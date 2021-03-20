/**
 * The MTS support ice project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
import org.cdsframework.annotation.ColumnSubstitutions;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReadOnly;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.Table;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;

/**
 *
 * @author HLN Consulting, LLC
 */
@ReadOnly
@Entity
@OrderBy(fields = "lower(vaccine_group_code), lower(vaccine_code)")
@ColumnSubstitutions({
    @Column(name = "create_id", selectable = false, insertable = false, updateable = false),
    @Column(name = "create_datetime", selectable = false, insertable = false, updateable = false),
    @Column(name = "last_mod_id", selectable = false, insertable = false, updateable = false),
    @Column(name = "last_mod_datetime", selectable = false, insertable = false, updateable = false)
})
@Table(databaseId = "ICE", name = "ice_vaccine_group_vaccine_rel", view = "vw_ice_vaccine_group_vaccine_rel")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Vaccine Group Vaccine Relationship")
public class IceVaccineGroupVaccineRelDTO extends BaseDTO {

    public interface VaccineByVersionDiseaseRelationship {
    }

    public interface VaccineGroupByVersionDiseaseRelationship {
    }
    private static final long serialVersionUID = 123315762053399675L;
    @Id
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name = "vaccine_group_code_id")
    private CdsCodeDTO vaccineGroup;
    @Id
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name = "vaccine_code_id")
    private CdsCodeDTO vaccine;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceVaccineGroupDTO.class)
    private String groupId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceVaccineDTO.class)
    private String vaccineId;

    /**
     * Get the value of vaccineId
     *
     * @return the value of vaccineId
     */
    public String getVaccineId() {
        return vaccineId;
    }

    /**
     * Set the value of vaccineId
     *
     * @param vaccineId new value of vaccineId
     */
    public void setVaccineId(String vaccineId) {
        this.vaccineId = vaccineId;
    }

    /**
     * Get the value of groupId
     *
     * @return the value of groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Set the value of groupId
     *
     * @param groupId new value of groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Get the value of vaccine
     *
     * @return the value of vaccine
     */
    public CdsCodeDTO getVaccine() {
        return vaccine;
    }

    /**
     * Set the value of vaccine
     *
     * @param vaccine new value of vaccine
     */
    public void setVaccine(CdsCodeDTO vaccine) {
        this.vaccine = vaccine;
    }

    /**
     * Get the value of vaccineGroup
     *
     * @return the value of vaccineGroup
     */
    public CdsCodeDTO getVaccineGroup() {
        return vaccineGroup;
    }

    /**
     * Set the value of vaccineGroup
     *
     * @param vaccineGroup new value of vaccineGroup
     */
    public void setVaccineGroup(CdsCodeDTO vaccineGroup) {
        this.vaccineGroup = vaccineGroup;
    }
}
