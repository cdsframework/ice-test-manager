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
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.util.comparator.IceVaccineVersionRelComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = IceVaccineVersionRelComparator.class)
@Table(databaseId = "ICE", name = "ice_vaccine_version_rel")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Vaccine/Version Relationship", isListed = false)
public class IceVaccineVersionRelDTO extends BaseDTO {

    private static final long serialVersionUID = -6708588426466807793L;

    public interface ByVaccineId {
    }

    public interface ByCdsCodeIdVaccineId {
    }
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    @Column(name = "version_rel_id")
    private String vaccineVersionRelId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceVaccineDTO.class)
    @NotNull
    private String vaccineId;
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name = "version_id")
    private CdsVersionDTO cdsVersionDTO;

    /**
     * Get the value of vaccineVersionRelId
     *
     * @return the value of vaccineVersionRelId
     */
    public String getVaccineVersionRelId() {
        return vaccineVersionRelId;
    }

    /**
     * Set the value of vaccineVersionRelId
     *
     * @param vaccineVersionRelId new value of vaccineVersionRelId
     */
    @PropertyListener
    public void setVaccineVersionRelId(String vaccineVersionRelId) {
        this.vaccineVersionRelId = vaccineVersionRelId;
    }

    public CdsVersionDTO getCdsVersionDTO() {
        return cdsVersionDTO;
    }

    @PropertyListener
    public void setCdsVersionDTO(CdsVersionDTO cdsVersionDTO) {
        this.cdsVersionDTO = cdsVersionDTO;
    }

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
    @PropertyListener
    public void setVaccineId(String vaccineId) {
        this.vaccineId = vaccineId;
    }
}
