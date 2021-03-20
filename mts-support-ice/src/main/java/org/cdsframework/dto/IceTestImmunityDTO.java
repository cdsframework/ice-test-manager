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
import org.cdsframework.enumeration.OffsetType;
import org.cdsframework.interfaces.OffsetBasedEventType;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@Table(databaseId = "ICE", name = "ice_test_immunity")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Test Immunity")
public class IceTestImmunityDTO extends BaseDTO implements OffsetBasedEventType {

    public interface ByTestId {
    }
    private static final long serialVersionUID = -1996530204815048162L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String immunityId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceTestDTO.class)
    private String testId;
    private Date observationEventTime;
    @NotNull
    @ReferenceDTO
    @Column(name = "imm_focus_id")
    private CdsCodeDTO immunityFocus;
    @NotNull
    @ReferenceDTO
    @Column(name = "imm_value_id")
    private CdsCodeDTO immunityValue;
    private boolean offsetBased;
    @Column(name = "offset_value")
    private String offset;
    private OffsetType offsetType = OffsetType.Age;
    private String offsetId;
    private Integer recordOrder;

    @Override
    public String getId() {
        return getImmunityId();
    }

    @Override
    public Date getEventDate() {
        return getObservationEventTime();
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
     * Get the value of immunityFocus
     *
     * @return the value of immunityFocus
     */
    public CdsCodeDTO getImmunityFocus() {
        return immunityFocus;
    }

    /**
     * Set the value of immunityFocus
     *
     * @param immunityFocus new value of immunityFocus
     */
    @PropertyListener
    public void setImmunityFocus(CdsCodeDTO immunityFocus) {
        this.immunityFocus = immunityFocus;
    }

    /**
     * Get the value of immunityValue
     *
     * @return the value of immunityValue
     */
    public CdsCodeDTO getImmunityValue() {
        return immunityValue;
    }

    /**
     * Set the value of immunityValue
     *
     * @param immunityValue new value of immunityValue
     */
    @PropertyListener
    public void setImmunityValue(CdsCodeDTO immunityValue) {
        this.immunityValue = immunityValue;
    }

    /**
     * Get the value of immunityId
     *
     * @return the value of immunityId
     */
    public String getImmunityId() {
        return immunityId;
    }

    /**
     * Set the value of immunityId
     *
     * @param immunityId new value of immunityId
     */
    @PropertyListener
    public void setImmunityId(String immunityId) {
        this.immunityId = immunityId;
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
     * Get the value of observationEventTime
     *
     * @return the value of observationEventTime
     */
    public Date getObservationEventTime() {
        return observationEventTime;
    }

    /**
     * Set the value of observationEventTime
     *
     * @param observationEventTime new value of observationEventTime
     */
    @PropertyListener
    public void setObservationEventTime(Date observationEventTime) {
        this.observationEventTime = observationEventTime;
    }
}
