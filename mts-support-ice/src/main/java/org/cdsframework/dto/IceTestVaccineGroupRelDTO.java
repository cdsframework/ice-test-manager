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

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(fields = "lower(display_name)")
@Table(databaseId = "ICE", name = "ice_test_vaccine_group_rel", view = "vw_ice_test_vaccine_group_rel")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Test Vaccine Group Relationship")
public class IceTestVaccineGroupRelDTO extends BaseDTO {

    public interface ByTestId {
    }
    private static final long serialVersionUID = -5720827887668998240L;
    @Id
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceTestDTO.class)
    private String testId;
    @Id
    @Column(name = "group_id")
    @ReferenceDTO(isNotFoundAllowed = false)
    private IceVaccineGroupDTO iceVaccineGroupDTO;

    /**
     * Get the value of iceVaccineGroupDTO
     *
     * @return the value of iceVaccineGroupDTO
     */
    public IceVaccineGroupDTO getIceVaccineGroupDTO() {
        return iceVaccineGroupDTO;
    }

    /**
     * Set the value of iceVaccineGroupDTO
     *
     * @param iceVaccineGroupDTO new value of iceVaccineGroupDTO
     */
    @PropertyListener
    public void setIceVaccineGroupDTO(IceVaccineGroupDTO iceVaccineGroupDTO) {
        this.iceVaccineGroupDTO = iceVaccineGroupDTO;
    }

    /**
     * Get the value of testId
     *
     * @return the value of testId
     */
    public String getTestId() {
        return testId;
    }

    /**
     * Set the value of testId
     *
     * @param testId new value of testId
     */
    @PropertyListener
    public void setTestId(String testId) {
        this.testId = testId;
    }
}
