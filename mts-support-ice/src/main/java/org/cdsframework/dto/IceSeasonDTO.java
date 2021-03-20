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
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.SortColumn;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
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
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = IceSeasonVersionRelDTO.class, childQueryClass = IceSeasonVersionRelDTO.BySeasonId.class, isAutoRetrieve = false)
})
@Table(databaseId = "ICE", name = "ice_season")
@OrderBy(fields = "lower(name)")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Season")
public class IceSeasonDTO extends BaseDTO {

    private static final long serialVersionUID = 8420292684793726019L;

    public interface BySeriesId {
    }

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String seasonId;
    @NotNull
    @SortColumn(sortFieldValue = "lower(name)")
    private String name;
    @NotNull
    @SortColumn(sortFieldValue = "lower(code)")
    private String code;
    @Size(max = 4000)
    private String notes;
    private Date endDate;
    private Date startDate;
    private boolean defaultSeason;
    @Column(name = "default_start_mon_day")
    private String defaultStartMonthAndDay;
    @Column(name = "default_stop_mon_day")
    private String defaultStopMonthAndDay;
    @ReferenceDTO(isNotFoundAllowed = false)
    @NotNull
    @Column(name = "group_id")
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
     * Get the value of defaultStopMonthAndDay
     *
     * @return the value of defaultStopMonthAndDay
     */
    public String getDefaultStopMonthAndDay() {
        return defaultStopMonthAndDay;
    }

    /**
     * Set the value of defaultStopMonthAndDay
     *
     * @param defaultStopMonthAndDay new value of defaultStopMonthAndDay
     */
    @PropertyListener
    public void setDefaultStopMonthAndDay(String defaultStopMonthAndDay) {
        this.defaultStopMonthAndDay = defaultStopMonthAndDay;
    }

    /**
     * Get the value of defaultStartMonthAndDay
     *
     * @return the value of defaultStartMonthAndDay
     */
    public String getDefaultStartMonthAndDay() {
        return defaultStartMonthAndDay;
    }

    /**
     * Set the value of defaultStartMonthAndDay
     *
     * @param defaultStartMonthAndDay new value of defaultStartMonthAndDay
     */
    @PropertyListener
    public void setDefaultStartMonthAndDay(String defaultStartMonthAndDay) {
        this.defaultStartMonthAndDay = defaultStartMonthAndDay;
    }

    /**
     * Get the value of defaultSeason
     *
     * @return the value of defaultSeason
     */
    public boolean isDefaultSeason() {
        return defaultSeason;
    }

    /**
     * Set the value of defaultSeason
     *
     * @param defaultSeason new value of defaultSeason
     */
    @PropertyListener
    public void setDefaultSeason(boolean defaultSeason) {
        this.defaultSeason = defaultSeason;
    }

    /**
     * Get the value of startDate
     *
     * @return the value of startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set the value of startDate
     *
     * @param startDate new value of startDate
     */
    @PropertyListener
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the value of endDate
     *
     * @return the value of endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Set the value of endDate
     *
     * @param endDate new value of endDate
     */
    @PropertyListener
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Get the value of notes
     *
     * @return the value of notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Set the value of notes
     *
     * @param notes new value of notes
     */
    @PropertyListener
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Get the value of seasonId
     *
     * @return the value of seasonId
     */
    public String getSeasonId() {
        return seasonId;
    }

    /**
     * Set the value of seasonId
     *
     * @param seasonId new value of seasonId
     */
    @PropertyListener
    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    /**
     * Get the value of code
     *
     * @return the value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the value of code
     *
     * @param code new value of code
     */
    @PropertyListener
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    @PropertyListener
    public void setName(String name) {
        this.name = name;
    }

    public List<IceSeasonVersionRelDTO> getIceSeasonVersionRelDTOs() {
        return getChildrenDTOs(IceSeasonVersionRelDTO.BySeasonId.class, IceSeasonVersionRelDTO.class);
    }

    public List<CdsVersionDTO> getCdsVersionDTOs() {
        List<CdsVersionDTO> result = new ArrayList<CdsVersionDTO>();
        for (IceSeasonVersionRelDTO item : getIceSeasonVersionRelDTOs()) {
            result.add(item.getCdsVersionDTO());
        }
        return result;
    }

}
