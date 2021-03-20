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

import java.util.Date;
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
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.enumeration.OffsetType;
import org.cdsframework.interfaces.OffsetBasedEventType;

/**
 *
 * @author HLN Consulting, LLC
 *
 * immId is a derby generated id field so we need to annotate this class with
 * RefreshOnAddOrUpdate in recordOrder to get the id after a new instance has
 * been inserted into the database.
 */
@Entity
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = IceTestEventComponentDTO.class, childQueryClass = IceTestEventComponentDTO.ByEventId.class, isAutoRetrieve = false)
})
@Table(databaseId = "ICE", name = "ice_test_event", view = "vw_ice_test_event")
@OrderBy(fields = "administration_time")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Test Event")
public class IceTestEventDTO extends BaseDTO implements OffsetBasedEventType {

    public interface ByTestId {
    }
    
    public interface ByTestIdEventId {
    }
    
    private static final long serialVersionUID = -7666084669022739664L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String eventId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceTestDTO.class)
    private String testId;
    @NotNull
    @ReferenceDTO
    @Column(name = "vaccine_id")
    private IceVaccineDTO iceVaccineDTO;
    private Date administrationTime;
    private boolean offsetBased;
    @Column(name = "offset_value")
    private String offset;
    private OffsetType offsetType;
    private String offsetId;
    private Integer recordOrder;
    @Column(name = "comp_count", updateable = false, insertable = false)
    private long componentCount;
    @Column(name = "comp_status", updateable = false, insertable = false)
    private String componentStatus;

    /**
     * Get the value of componentStatus
     *
     * @return the value of componentStatus
     */
    public String getComponentStatus() {
        return componentStatus;
    }

    /**
     * Set the value of componentStatus
     *
     * @param componentStatus new value of componentStatus
     */
    public void setComponentStatus(String componentStatus) {
        this.componentStatus = componentStatus;
    }

    /**
     * Get the value of componentCount
     *
     * @return the value of componentCount
     */
    public long getComponentCount() {
        return componentCount;
    }

    /**
     * Set the value of componentCount
     *
     * @param componentCount new value of componentCount
     */
    public void setComponentCount(long componentCount) {
        this.componentCount = componentCount;
    }

    @Override
    public String getId() {
        return getEventId();
    }

    @Override
    public Date getEventDate() {
        return getAdministrationTime();
    }

    /**
     * Get the value of recordOrder
     *
     * @return the value of recordOrder
     */
    @Override
    public Integer getRecordOrder() {
        return recordOrder;
    }

    /**
     * Set the value of recordOrder
     *
     * @param recordOrder new value of recordOrder
     */
    @PropertyListener
    public void setRecordOrder(Integer recordOrder) {
        this.recordOrder = recordOrder;
    }

    /**
     * Get the value of offsetId
     *
     * @return the value of offsetId
     */
    @Override
    public String getOffsetId() {
        return offsetId;
    }

    /**
     * Set the value of offsetId
     *
     * @param offsetId new value of offsetId
     */
    @PropertyListener
    public void setOffsetId(String offsetId) {
        this.offsetId = offsetId;
    }

    /**
     * Get the value of offsetType
     *
     * @return the value of offsetType
     */
    @Override
    public OffsetType getOffsetType() {
        return offsetType;
    }

    /**
     * Set the value of offsetType
     *
     * @param offsetType new value of offsetType
     */
    @PropertyListener
    public void setOffsetType(OffsetType offsetType) {
        this.offsetType = offsetType;
    }

    /**
     * Get the value of offset
     *
     * @return the value of offset
     */
    @Override
    public String getOffset() {
        return offset;
    }

    /**
     * Set the value of offset
     *
     * @param offset new value of offset
     */
    @PropertyListener
    public void setOffset(String offset) {
        this.offset = offset;
    }

    /**
     * Get the value of offsetBased
     *
     * @return the value of offsetBased
     */
    @Override
    public boolean isOffsetBased() {
        return offsetBased;
    }

    /**
     * Set the value of offsetBased
     *
     * @param offsetBased new value of offsetBased
     */
    @PropertyListener
    public void setOffsetBased(boolean offsetBased) {
        this.offsetBased = offsetBased;
    }

    /**
     * Get the value of eventId
     *
     * @return the value of eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Set the value of eventId
     *
     * @param eventId new value of eventId
     */
    @PropertyListener
    public void setEventId(String eventId) {
        this.eventId = eventId;
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
     * Get the value of administrationTime
     *
     * @return the value of administrationTime
     */
    public Date getAdministrationTime() {
        return administrationTime;
    }

    /**
     * Set the value of administrationTime
     *
     * @param administrationTime new value of administrationTime
     */
    @PropertyListener
    public void setAdministrationTime(Date administrationTime) {
        this.administrationTime = administrationTime;
    }

    public List<IceTestEventComponentDTO> getIceTestEventComponentDTOs() {
        return (List) this.getChildrenDTOs(IceTestEventComponentDTO.ByEventId.class);
    }

}
