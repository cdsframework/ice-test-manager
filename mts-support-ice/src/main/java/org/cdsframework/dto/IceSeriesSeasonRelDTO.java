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
@Table(databaseId = "ICE", name = "ice_series_season_rel")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Series Season Relationship")
public class IceSeriesSeasonRelDTO extends BaseDTO {

    public interface BySeriesId {
    }
    private static final long serialVersionUID = 9029984038588073003L;

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    @Column(name = "series_rel_id")
    private String seriesSeasonRelId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceSeriesDTO.class)
    @NotNull
    private String seriesId;
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name = "season_id")
    private IceSeasonDTO iceSeasonDTO;

    /**
     * Get the value of seriesSeasonRelId
     *
     * @return the value of seriesSeasonRelId
     */
    public String getSeriesSeasonRelId() {
        return seriesSeasonRelId;
    }

    /**
     * Set the value of seriesSeasonRelId
     *
     * @param seriesSeasonRelId new value of seriesSeasonRelId
     */
    @PropertyListener
    public void setSeriesSeasonRelId(String seriesSeasonRelId) {
        this.seriesSeasonRelId = seriesSeasonRelId;
    }

    public IceSeasonDTO getIceSeasonDTO() {
        return iceSeasonDTO;
    }

    @PropertyListener
    public void setIceSeasonDTO(IceSeasonDTO iceSeasonDTO) {
        this.iceSeasonDTO = iceSeasonDTO;
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
}
