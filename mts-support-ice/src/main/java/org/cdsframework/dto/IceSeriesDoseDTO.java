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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.cdsframework.annotation.Column;
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
import org.cdsframework.util.comparator.IceSeriesDoseComparator;
import org.cdsframework.util.comparator.IceSeriesDoseVaccineRelComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = IceSeriesDoseVaccineRelDTO.class, childQueryClass = IceSeriesDoseVaccineRelDTO.ByDoseId.class, isAutoRetrieve = false)
})
@OrderBy(comparator = IceSeriesDoseComparator.class, fields = "series_id, dose_number")
@Table(databaseId = "ICE", name = "ice_series_dose", view = "vw_ice_series_dose")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Series Dose")
public class IceSeriesDoseDTO extends BaseDTO {

    public interface NextDoseNumber {
    }

    public interface BySeriesId {
    }

    public interface BySeriesIdDoseNumber {
    }
    private static final long serialVersionUID = -5962985642427527953L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String doseId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceSeriesDTO.class)
    private String seriesId;
    @NotNull
    private Integer doseNumber;
    private String absMinAge;
    private String minAge;
    @Column(name = "earliest_rec_age")
    private String earliestRecommendedAge;
    @Column(name = "latest_rec_age")
    private String latestRecommendedAge;
    private String maxAge;
    @Column(name = "allowed_vaccines", updateable = false, insertable = false)
    private String allowedVaccines;
    @Column(name = "preferred_vaccines", updateable = false, insertable = false)
    private String preferredVaccines;

    /**
     * Get the value of preferredVaccines
     *
     * @return the value of preferredVaccines
     */
    public String getPreferredVaccines() {
        return preferredVaccines;
    }

    /**
     * Set the value of preferredVaccines
     *
     * @param preferredVaccines new value of preferredVaccines
     */
    public void setPreferredVaccines(String preferredVaccines) {
        this.preferredVaccines = preferredVaccines;
    }

    /**
     * Get the value of allowedVaccines
     *
     * @return the value of allowedVaccines
     */
    public String getAllowedVaccines() {
        return allowedVaccines;
    }

    /**
     * Set the value of allowedVaccines
     *
     * @param allowedVaccines new value of allowedVaccines
     */
    public void setAllowedVaccines(String allowedVaccines) {
        this.allowedVaccines = allowedVaccines;
    }

    /**
     * Get the value of minAge
     *
     * @return the value of minAge
     */
    public String getMinAge() {
        return minAge;
    }

    /**
     * Set the value of minAge
     *
     * @param minAge new value of minAge
     */
    @PropertyListener
    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    /**
     * Get the value of maxAge
     *
     * @return the value of maxAge
     */
    public String getMaxAge() {
        return maxAge;
    }

    /**
     * Set the value of maxAge
     *
     * @param maxAge new value of maxAge
     */
    @PropertyListener
    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * Get the value of latestRecommendedAge
     *
     * @return the value of latestRecommendedAge
     */
    public String getLatestRecommendedAge() {
        return latestRecommendedAge;
    }

    /**
     * Set the value of latestRecommendedAge
     *
     * @param latestRecommendedAge new value of latestRecommendedAge
     */
    @PropertyListener
    public void setLatestRecommendedAge(String latestRecommendedAge) {
        this.latestRecommendedAge = latestRecommendedAge;
    }

    /**
     * Get the value of earliestRecommendedAge
     *
     * @return the value of earliestRecommendedAge
     */
    public String getEarliestRecommendedAge() {
        return earliestRecommendedAge;
    }

    /**
     * Set the value of earliestRecommendedAge
     *
     * @param earliestRecommendedAge new value of earliestRecommendedAge
     */
    @PropertyListener
    public void setEarliestRecommendedAge(String earliestRecommendedAge) {
        this.earliestRecommendedAge = earliestRecommendedAge;
    }

    /**
     * Get the value of absMinAge
     *
     * @return the value of absMinAge
     */
    public String getAbsMinAge() {
        return absMinAge;
    }

    /**
     * Set the value of absMinAge
     *
     * @param absMinAge new value of absMinAge
     */
    @PropertyListener
    public void setAbsMinAge(String absMinAge) {
        this.absMinAge = absMinAge;
    }

    /**
     * Get the value of doseNumber
     *
     * @return the value of doseNumber
     */
    public Integer getDoseNumber() {
        return doseNumber;
    }

    /**
     * Set the value of doseNumber
     *
     * @param doseNumber new value of doseNumber
     */
    @PropertyListener
    public void setDoseNumber(Integer doseNumber) {
        this.doseNumber = doseNumber;
    }

    /**
     * Get the value of seriesId
     *
     * @return the value of seriesId
     */
    public String getSeriesId() {
        return seriesId;
    }

    /**
     * Set the value of seriesId
     *
     * @param seriesId new value of seriesId
     */
    @PropertyListener
    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
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

    public List<IceSeriesDoseVaccineRelDTO> getIceSeriesDoseVaccineRelDTOs() {
        return getChildrenDTOs(IceSeriesDoseVaccineRelDTO.ByDoseId.class, IceSeriesDoseVaccineRelDTO.class);
    }

    public List<IceVaccineDTO> getIceSeriesDoseVaccineDTOs() {
        List<IceVaccineDTO> iceVaccineDTOList = new ArrayList<IceVaccineDTO>();
        for (IceSeriesDoseVaccineRelDTO item : getIceSeriesDoseVaccineRelDTOs()) {
            iceVaccineDTOList.add(item.getIceVaccineDTO());
        }
        return iceVaccineDTOList;
    }

    public List<IceVaccineDTO> getAllowedVaccineDTOs() {
        return getIceSeriesDoseVaccineDTOs();
    }

    public List<IceVaccineDTO> getPreferredVaccineDTOs() {
        List<IceVaccineDTO> iceVaccineDTOList = new ArrayList<IceVaccineDTO>();
        for (IceSeriesDoseVaccineRelDTO item : getIceSeriesDoseVaccineRelDTOs()) {
            if (item.isPreferredVaccine()) {
                iceVaccineDTOList.add(item.getIceVaccineDTO());
            }
        }
        return iceVaccineDTOList;
    }

    /**
     * Returns a comma separated string of preferred vaccines.
     *
     * @return
     */
    public String getPreferredVaccineList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (IceVaccineDTO item : getPreferredVaccineDTOs()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(item.getVaccineName());
        }
        return stringBuilder.toString();
    }

    /**
     * Returns a comma separated string of allowed vaccines.
     *
     * @return
     */
    public String getAllowedVaccineList() {
        List<IceSeriesDoseVaccineRelDTO> allowedVaccineDTOs = getIceSeriesDoseVaccineRelDTOs();
        Collections.sort(allowedVaccineDTOs, new IceSeriesDoseVaccineRelComparator());
        StringBuilder stringBuilder = new StringBuilder();
        for (IceSeriesDoseVaccineRelDTO item : allowedVaccineDTOs) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            if (item.isPreferredVaccine()) {
                stringBuilder.append("<strong>").append(
                        (item.getIceVaccineDTO() != null && item.getIceVaccineDTO().getVaccine() != null)
                                ? item.getIceVaccineDTO().getVaccine().getCode()
                                : null
                ).append("</strong>");
            } else {
                stringBuilder.append((item.getIceVaccineDTO() != null && item.getIceVaccineDTO().getVaccine() != null)
                        ? item.getIceVaccineDTO().getVaccine().getCode()
                        : null);
            }
        }
        return stringBuilder.toString();
    }
}
