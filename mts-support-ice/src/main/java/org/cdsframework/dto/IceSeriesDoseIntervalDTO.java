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
import org.cdsframework.annotation.Ignore;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.util.comparator.IceSeriesDoseIntervalComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = IceSeriesDoseIntervalComparator.class, fields = "from_dose_number, to_dose_number")
@Table(databaseId = "ICE", name = "ice_series_dose_interval", view = "vw_ice_series_dose_interval")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Series Dose Interval", isListed = false)
public class IceSeriesDoseIntervalDTO extends BaseDTO {

    public interface BySeriesId {
    }
    private static final long serialVersionUID = 3913902293993212894L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String doseIntervalId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceSeriesDTO.class)
    private String seriesId;
    @NotNull
    private String fromDoseId;
    @NotNull
    private String toDoseId;
    @Column(name = "from_dose_number", updateable = false, insertable = false)
    private Integer fromDoseNumber;
    @Column(name = "to_dose_number", updateable = false, insertable = false)
    private Integer toDoseNumber;
    private String absMinInterval;
    private String minInterval;
    @Column(name = "earliest_rec_interval")
    private String earliestRecommendedInterval;
    @Column(name = "latest_rec_interval")
    private String latestRecommendedInterval;

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
     * Get the value of toDoseId
     *
     * @return the value of toDoseId
     */
    public String getToDoseId() {
        return toDoseId;
    }

    /**
     * Set the value of toDoseId
     *
     * @param toDoseId new value of toDoseId
     */
    @PropertyListener
    public void setToDoseId(String toDoseId) {
        this.toDoseId = toDoseId;
    }

    /**
     * Get the value of fromDoseId
     *
     * @return the value of fromDoseId
     */
    public String getFromDoseId() {
        return fromDoseId;
    }

    /**
     * Set the value of fromDoseId
     *
     * @param fromDoseId new value of fromDoseId
     */
    @PropertyListener
    public void setFromDoseId(String fromDoseId) {
        this.fromDoseId = fromDoseId;
    }

    /**
     * Get the value of latestRecommendedInterval
     *
     * @return the value of latestRecommendedInterval
     */
    public String getLatestRecommendedInterval() {
        return latestRecommendedInterval;
    }

    /**
     * Set the value of latestRecommendedInterval
     *
     * @param latestRecommendedInterval new value of latestRecommendedInterval
     */
    @PropertyListener
    public void setLatestRecommendedInterval(String latestRecommendedInterval) {
        this.latestRecommendedInterval = latestRecommendedInterval;
    }

    /**
     * Get the value of earliestRecommendedInterval
     *
     * @return the value of earliestRecommendedInterval
     */
    public String getEarliestRecommendedInterval() {
        return earliestRecommendedInterval;
    }

    /**
     * Set the value of earliestRecommendedInterval
     *
     * @param earliestRecommendedInterval new value of
     * earliestRecommendedInterval
     */
    @PropertyListener
    public void setEarliestRecommendedInterval(String earliestRecommendedInterval) {
        this.earliestRecommendedInterval = earliestRecommendedInterval;
    }

    /**
     * Get the value of absMinInterval
     *
     * @return the value of absMinInterval
     */
    public String getAbsMinInterval() {
        return absMinInterval;
    }

    /**
     * Set the value of absMinInterval
     *
     * @param absMinInterval new value of absMinInterval
     */
    @PropertyListener
    public void setAbsMinInterval(String absMinInterval) {
        this.absMinInterval = absMinInterval;
    }

    /**
     * Get the value of minInterval
     *
     * @return the value of minInterval
     */
    public String getMinInterval() {
        return minInterval;
    }

    /**
     * Set the value of minInterval
     *
     * @param minInterval new value of minInterval
     */
    @PropertyListener
    public void setMinInterval(String minInterval) {
        this.minInterval = minInterval;
    }

    /**
     * Get the value of fromDoseNumber
     *
     * @return the value of fromDoseNumber
     */
    public Integer getFromDoseNumber() {
        return fromDoseNumber;
    }

    /**
     * Set the value of fromDoseNumber
     *
     * @param fromDoseNumber new value of fromDoseNumber
     */
    @PropertyListener
    public void setFromDoseNumber(Integer fromDoseNumber) {
        this.fromDoseNumber = fromDoseNumber;
    }

    /**
     * Get the value of doseIntervalId
     *
     * @return the value of doseIntervalId
     */
    public String getDoseIntervalId() {
        return doseIntervalId;
    }

    /**
     * Set the value of doseIntervalId
     *
     * @param doseIntervalId new value of doseIntervalId
     */
    @PropertyListener
    public void setDoseIntervalId(String doseIntervalId) {
        this.doseIntervalId = doseIntervalId;
    }

    /**
     * Get the value of toDoseNumber
     *
     * @return the value of toDoseNumber
     */
    public Integer getToDoseNumber() {
        return toDoseNumber;
    }

    /**
     * Set the value of toDoseNumber
     *
     * @param toDoseNumber new value of toDoseNumber
     */
    @PropertyListener
    public void setToDoseNumber(Integer toDoseNumber) {
        this.toDoseNumber = toDoseNumber;
    }
}
