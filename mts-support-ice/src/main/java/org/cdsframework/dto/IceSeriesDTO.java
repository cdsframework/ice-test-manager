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
    @ParentChildRelationship(childDtoClass = IceSeriesVersionRelDTO.class, childQueryClass = IceSeriesVersionRelDTO.BySeriesId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = IceSeriesSeasonRelDTO.class, childQueryClass = IceSeriesSeasonRelDTO.BySeriesId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = IceSeriesDoseIntervalDTO.class, childQueryClass = IceSeriesDoseIntervalDTO.BySeriesId.class, isAutoRetrieve = false, deleteOrder = 1),
    @ParentChildRelationship(childDtoClass = IceSeriesDoseDTO.class, childQueryClass = IceSeriesDoseDTO.BySeriesId.class, isAutoRetrieve = false, deleteOrder = 2)
})
@Table(databaseId = "ICE", name = "ice_series", view="vw_ice_series")
@OrderBy(fields = "lower(name)")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Series")
public class IceSeriesDTO extends BaseDTO {

    public interface ByVersionId {
    }
    public interface ByVaccineGroupCodeAndName {
    }
    private static final long serialVersionUID = -3018690776781664596L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String seriesId;
    @NotNull
    @SortColumn(sortFieldValue = "lower(name)")
    private String name;
    private String code;
    @Size(max = 4000)
    private String notes;
    @ReferenceDTO(isNotFoundAllowed = false)
    @NotNull
    @Column(name = "group_id")
    private IceVaccineGroupDTO iceVaccineGroupDTO;

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
     * Get the value of numberOfDoses
     *
     * @return the value of numberOfDoses
     */
    public int getNumberOfDoses() {
        return getIceSeriesDoseDTOs().size();
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

    public List<IceSeriesDoseDTO> getIceSeriesDoseDTOs() {
        return getChildrenDTOs(IceSeriesDoseDTO.BySeriesId.class, IceSeriesDoseDTO.class);
    }

    public List<IceSeriesDoseIntervalDTO> getIceSeriesDoseIntervalDTOs() {
        return getChildrenDTOs(IceSeriesDoseIntervalDTO.BySeriesId.class, IceSeriesDoseIntervalDTO.class);
    }

    public List<IceSeriesVersionRelDTO> getIceSeriesVersionRelDTOs() {
        return getChildrenDTOs(IceSeriesVersionRelDTO.BySeriesId.class, IceSeriesVersionRelDTO.class);
    }

    public List<IceSeriesSeasonRelDTO> getIceSeriesSeasonRelDTOs() {
        return getChildrenDTOs(IceSeriesSeasonRelDTO.BySeriesId.class, IceSeriesSeasonRelDTO.class);
    }

    public List<CdsVersionDTO> getCdsVersionDTOs() {
        List<CdsVersionDTO> result = new ArrayList<CdsVersionDTO>();
        for (IceSeriesVersionRelDTO item : getIceSeriesVersionRelDTOs()) {
            result.add(item.getCdsVersionDTO());
        }
        return result;
    }

    public List<IceSeasonDTO> getIceSeasonDTOs() {
        List<IceSeasonDTO> result = new ArrayList<IceSeasonDTO>();
        for (IceSeriesSeasonRelDTO item : getIceSeriesSeasonRelDTOs()) {
            result.add(item.getIceSeasonDTO());
        }
        return result;
    }
}
