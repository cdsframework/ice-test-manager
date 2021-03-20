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
import org.cdsframework.util.comparator.IceSeriesDoseVaccineRelComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator=IceSeriesDoseVaccineRelComparator.class)
@Table(databaseId = "ICE", name = "ice_series_dose_vaccine_rel")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Series Dose Vaccine Relationship")
public class IceSeriesDoseVaccineRelDTO extends BaseDTO {

    public interface ByDoseId {
    }
    private static final long serialVersionUID = -8162589374205041462L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String doseVaccineRelId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceSeriesDoseDTO.class)
    private String doseId;
    @Column(name = "vaccine_id")
    @ReferenceDTO(isNotFoundAllowed = true)
    private IceVaccineDTO iceVaccineDTO = new IceVaccineDTO();
    @Column(name = "preferred", resultSetClass = String.class)
    private boolean preferredVaccine;

    /**
     * Get the value of preferredVaccine
     *
     * @return the value of preferredVaccine
     */
    public boolean isPreferredVaccine() {
        return preferredVaccine;
    }

    /**
     * Set the value of preferredVaccine
     *
     * @param preferredVaccine new value of preferredVaccine
     */
    @PropertyListener
    public void setPreferredVaccine(boolean preferredVaccine) {
        this.preferredVaccine = preferredVaccine;
    }

    /**
     * Get the value of iceVaccineDTO
     *
     * @return the value of iceVaccineDTO
     */
    public IceVaccineDTO getIceVaccineDTO() {
        return iceVaccineDTO;
    }

    /**
     * Set the value of iceVaccineDTO
     *
     * @param iceVaccineDTO new value of iceVaccineDTO
     */
    @PropertyListener
    public void setIceVaccineDTO(IceVaccineDTO iceVaccineDTO) {
        this.iceVaccineDTO = iceVaccineDTO;
    }

    /**
     * Get the value of doseId
     *
     * @return the value of doseId
     */
    public String getDoseId() {
        return doseId;
    }

    /**
     * Set the value of doseId
     *
     * @param doseId new value of doseId
     */
    @PropertyListener
    public void setDoseId(String doseId) {
        this.doseId = doseId;
    }

    /**
     * Get the value of doseVaccineRelId
     *
     * @return the value of doseVaccineRelId
     */
    public String getDoseVaccineRelId() {
        return doseVaccineRelId;
    }

    /**
     * Set the value of doseVaccineRelId
     *
     * @param doseVaccineRelId new value of doseVaccineRelId
     */
    @PropertyListener
    public void setDoseVaccineRelId(String doseVaccineRelId) {
        this.doseVaccineRelId = doseVaccineRelId;
    }
}
