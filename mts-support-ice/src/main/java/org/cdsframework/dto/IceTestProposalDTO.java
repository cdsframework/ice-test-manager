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
import org.cdsframework.annotation.Ignore;
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
import org.cdsframework.enumeration.OffsetSource;
import org.cdsframework.enumeration.OffsetType;
import org.cdsframework.enumeration.ProposalType;
import org.cdsframework.interfaces.OffsetBasedEventType;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = IceTestRecommendationDTO.class, childQueryClass = IceTestRecommendationDTO.ByProposalId.class, isAutoRetrieve = false)
})
@Table(databaseId = "ICE", name = "ice_test_proposal", view = "vw_ice_test_proposal")
@OrderBy(fields = "recommended_date")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Test Proposal")
public class IceTestProposalDTO extends BaseDTO implements OffsetBasedEventType {

    public interface ByTestId {
    }
    private static final long serialVersionUID = 5461676296712308672L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String proposalId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceTestDTO.class)
    private String testId;
    @ReferenceDTO
    @Column(name = "vaccine_id")
    private IceVaccineDTO iceVaccineDTO;
    @ReferenceDTO
    @Column(name = "group_id")
    private IceVaccineGroupDTO iceVaccineGroupDTO;
    @NotNull
    @ReferenceDTO
    @Column(name = "rec_value_id")
    private CdsCodeDTO recommendationValue;
    private Integer recordOrder;
    @Column(name = "reasons", updateable = false, insertable = false)
    private String reasons;
    private String recommendedOffset;
    private String earliestOffset;
    private String latestOffset;
    private String overdueOffset;
    private Date recommendedDate;
    private Date earliestDate;
    private Date latestDate;
    private Date overdueDate;
    private boolean recommendedOffsetBased;
    private boolean earliestOffsetBased;
    private boolean latestOffsetBased;
    private boolean overdueOffsetBased;
    private OffsetType recommendedOffsetType;
    private OffsetType earliestOffsetType;
    private OffsetType latestOffsetType;
    private OffsetType overdueOffsetType;
    private String recommendedOffsetId;
    private String earliestOffsetId;
    private String latestOffsetId;
    private String overdueOffsetId;
    @Ignore
    private OffsetSource offsetSource;
    private ProposalType proposalType = ProposalType.VACCINE_GROUP;

    /**
     * Get the value of proposalType
     *
     * @return the value of proposalType
     */
    public ProposalType getProposalType() {
        return proposalType;
    }

    /**
     * Set the value of proposalType
     *
     * @param proposalType new value of proposalType
     */
    @PropertyListener
    public void setProposalType(ProposalType proposalType) {
        this.proposalType = proposalType;
    }

    /**
     * Get the value of offsetSource
     *
     * @return the value of offsetSource
     */
    public OffsetSource getOffsetSource() {
        return offsetSource;
    }

    /**
     * Set the value of offsetSource
     *
     * @param offsetSource new value of offsetSource
     */
    public void setOffsetSource(OffsetSource offsetSource) {
        this.offsetSource = offsetSource;
    }

    /**
     * Get the value of overdueOffsetId
     *
     * @return the value of overdueOffsetId
     */
    public String getOverdueOffsetId() {
        return overdueOffsetId;
    }

    /**
     * Set the value of overdueOffsetId
     *
     * @param overdueOffsetId new value of overdueOffsetId
     */
    @PropertyListener
    public void setOverdueOffsetId(String overdueOffsetId) {
        this.overdueOffsetId = overdueOffsetId;
    }

    /**
     * Get the value of latestOffsetId
     *
     * @return the value of latestOffsetId
     */
    public String getLatestOffsetId() {
        return latestOffsetId;
    }

    /**
     * Set the value of latestOffsetId
     *
     * @param latestOffsetId new value of latestOffsetId
     */
    @PropertyListener
    public void setLatestOffsetId(String latestOffsetId) {
        this.latestOffsetId = latestOffsetId;
    }

    /**
     * Get the value of earliestOffsetId
     *
     * @return the value of earliestOffsetId
     */
    public String getEarliestOffsetId() {
        return earliestOffsetId;
    }

    /**
     * Set the value of earliestOffsetId
     *
     * @param earliestOffsetId new value of earliestOffsetId
     */
    @PropertyListener
    public void setEarliestOffsetId(String earliestOffsetId) {
        this.earliestOffsetId = earliestOffsetId;
    }

    /**
     * Get the value of recommendedOffsetId
     *
     * @return the value of recommendedOffsetId
     */
    public String getRecommendedOffsetId() {
        return recommendedOffsetId;
    }

    /**
     * Set the value of recommendedOffsetId
     *
     * @param recommendedOffsetId new value of recommendedOffsetId
     */
    @PropertyListener
    public void setRecommendedOffsetId(String recommendedOffsetId) {
        this.recommendedOffsetId = recommendedOffsetId;
    }

    /**
     * Get the value of overdueOffsetType
     *
     * @return the value of overdueOffsetType
     */
    public OffsetType getOverdueOffsetType() {
        return overdueOffsetType;
    }

    /**
     * Set the value of overdueOffsetType
     *
     * @param overdueOffsetType new value of overdueOffsetType
     */
    @PropertyListener
    public void setOverdueOffsetType(OffsetType overdueOffsetType) {
        this.overdueOffsetType = overdueOffsetType;
    }

    /**
     * Get the value of latestOffsetType
     *
     * @return the value of latestOffsetType
     */
    public OffsetType getLatestOffsetType() {
        return latestOffsetType;
    }

    /**
     * Set the value of latestOffsetType
     *
     * @param latestOffsetType new value of latestOffsetType
     */
    @PropertyListener
    public void setLatestOffsetType(OffsetType latestOffsetType) {
        this.latestOffsetType = latestOffsetType;
    }

    /**
     * Get the value of earliestOffsetType
     *
     * @return the value of earliestOffsetType
     */
    public OffsetType getEarliestOffsetType() {
        return earliestOffsetType;
    }

    /**
     * Set the value of earliestOffsetType
     *
     * @param earliestOffsetType new value of earliestOffsetType
     */
    @PropertyListener
    public void setEarliestOffsetType(OffsetType earliestOffsetType) {
        this.earliestOffsetType = earliestOffsetType;
    }

    /**
     * Get the value of recommendedOffsetType
     *
     * @return the value of recommendedOffsetType
     */
    public OffsetType getRecommendedOffsetType() {
        return recommendedOffsetType;
    }

    /**
     * Set the value of recommendedOffsetType
     *
     * @param recommendedOffsetType new value of recommendedOffsetType
     */
    @PropertyListener
    public void setRecommendedOffsetType(OffsetType recommendedOffsetType) {
        this.recommendedOffsetType = recommendedOffsetType;
    }

    /**
     * Get the value of overdueOffsetBased
     *
     * @return the value of overdueOffsetBased
     */
    public boolean isOverdueOffsetBased() {
        return overdueOffsetBased;
    }

    /**
     * Set the value of overdueOffsetBased
     *
     * @param overdueOffsetBased new value of overdueOffsetBased
     */
    @PropertyListener
    public void setOverdueOffsetBased(boolean overdueOffsetBased) {
        this.overdueOffsetBased = overdueOffsetBased;
    }

    /**
     * Get the value of latestOffsetBased
     *
     * @return the value of latestOffsetBased
     */
    public boolean isLatestOffsetBased() {
        return latestOffsetBased;
    }

    /**
     * Set the value of latestOffsetBased
     *
     * @param latestOffsetBased new value of latestOffsetBased
     */
    @PropertyListener
    public void setLatestOffsetBased(boolean latestOffsetBased) {
        this.latestOffsetBased = latestOffsetBased;
    }

    /**
     * Get the value of earliestOffsetBased
     *
     * @return the value of earliestOffsetBased
     */
    public boolean isEarliestOffsetBased() {
        return earliestOffsetBased;
    }

    /**
     * Set the value of earliestOffsetBased
     *
     * @param earliestOffsetBased new value of earliestOffsetBased
     */
    @PropertyListener
    public void setEarliestOffsetBased(boolean earliestOffsetBased) {
        this.earliestOffsetBased = earliestOffsetBased;
    }

    /**
     * Get the value of recommendedOffsetBased
     *
     * @return the value of recommendedOffsetBased
     */
    public boolean isRecommendedOffsetBased() {
        return recommendedOffsetBased;
    }

    /**
     * Set the value of recommendedOffsetBased
     *
     * @param recommendedOffsetBased new value of recommendedOffsetBased
     */
    @PropertyListener
    public void setRecommendedOffsetBased(boolean recommendedOffsetBased) {
        this.recommendedOffsetBased = recommendedOffsetBased;
    }

    /**
     * Get the value of overdueDate
     *
     * @return the value of overdueDate
     */
    public Date getOverdueDate() {
        return overdueDate;
    }

    /**
     * Set the value of overdueDate
     *
     * @param overdueDate new value of overdueDate
     */
    @PropertyListener
    public void setOverdueDate(Date overdueDate) {
        this.overdueDate = overdueDate;
    }

    /**
     * Get the value of latestDate
     *
     * @return the value of latestDate
     */
    public Date getLatestDate() {
        return latestDate;
    }

    /**
     * Set the value of latestDate
     *
     * @param latestDate new value of latestDate
     */
    @PropertyListener
    public void setLatestDate(Date latestDate) {
        this.latestDate = latestDate;
    }

    /**
     * Get the value of earliestDate
     *
     * @return the value of earliestDate
     */
    public Date getEarliestDate() {
        return earliestDate;
    }

    /**
     * Set the value of earliestDate
     *
     * @param earliestDate new value of earliestDate
     */
    @PropertyListener
    public void setEarliestDate(Date earliestDate) {
        this.earliestDate = earliestDate;
    }

    /**
     * Get the value of overdueOffset
     *
     * @return the value of overdueOffset
     */
    public String getOverdueOffset() {
        return overdueOffset;
    }

    /**
     * Set the value of overdueOffset
     *
     * @param overdueOffset new value of overdueOffset
     */
    @PropertyListener
    public void setOverdueOffset(String overdueOffset) {
        this.overdueOffset = overdueOffset;
    }

    /**
     * Get the value of latestOffset
     *
     * @return the value of latestOffset
     */
    public String getLatestOffset() {
        return latestOffset;
    }

    /**
     * Set the value of latestOffset
     *
     * @param latestOffset new value of latestOffset
     */
    @PropertyListener
    public void setLatestOffset(String latestOffset) {
        this.latestOffset = latestOffset;
    }

    /**
     * Get the value of earliestOffset
     *
     * @return the value of earliestOffset
     */
    public String getEarliestOffset() {
        return earliestOffset;
    }

    /**
     * Set the value of earliestOffset
     *
     * @param earliestOffset new value of earliestOffset
     */
    @PropertyListener
    public void setEarliestOffset(String earliestOffset) {
        this.earliestOffset = earliestOffset;
    }

    /**
     * Get the value of reasons
     *
     * @return the value of reasons
     */
    public String getReasons() {
        return reasons;
    }

    /**
     * Set the value of reasons
     *
     * @param reasons new value of reasons
     */
    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    @Override
    public String getId() {
        return getProposalId();
    }

    @Override
    public Date getEventDate() {
        if (offsetSource == null) {
            return getRecommendedDate();
        }
        switch (offsetSource) {
            case EARLIEST:
                return getEarliestDate();
            case LATEST:
                return getLatestDate();
            case OVERDUE:
                return getOverdueDate();
            case RECOMMENDED:
            case DEFAULT:
            default:
                return getRecommendedDate();
        }
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
        if (offsetSource == null) {
            return recommendedOffsetId;
        }
        switch (offsetSource) {
            case EARLIEST:
                return earliestOffsetId;
            case LATEST:
                return latestOffsetId;
            case OVERDUE:
                return overdueOffsetId;
            case RECOMMENDED:
            case DEFAULT:
            default:
                return recommendedOffsetId;
        }
    }

    /**
     * Set the value of offsetId
     *
     * @param offsetId new value of offsetId
     */
    @PropertyListener
    public void setOffsetId(String offsetId) {
        if (offsetSource == null) {
            this.recommendedOffsetId = offsetId;
        }
        switch (offsetSource) {
            case EARLIEST:
                this.earliestOffsetId = offsetId;
            case LATEST:
                this.latestOffsetId = offsetId;
            case OVERDUE:
                this.overdueOffsetId = offsetId;
            case RECOMMENDED:
            case DEFAULT:
            default:
                this.recommendedOffsetId = offsetId;
        }
    }

    /**
     * Get the value of offsetType
     *
     * @return the value of offsetType
     */
    @Override
    public OffsetType getOffsetType() {
        if (offsetSource == null) {
            return recommendedOffsetType;
        }
        switch (offsetSource) {
            case EARLIEST:
                return earliestOffsetType;
            case LATEST:
                return latestOffsetType;
            case OVERDUE:
                return overdueOffsetType;
            case RECOMMENDED:
            case DEFAULT:
            default:
                return recommendedOffsetType;
        }
    }

    /**
     * Set the value of offsetType
     *
     * @param offsetType new value of offsetType
     */
    @PropertyListener
    public void setOffsetType(OffsetType offsetType) {
        if (offsetSource == null) {
            this.recommendedOffsetType = offsetType;
        }
        switch (offsetSource) {
            case EARLIEST:
                this.earliestOffsetType = offsetType;
            case LATEST:
                this.latestOffsetType = offsetType;
            case OVERDUE:
                this.overdueOffsetType = offsetType;
            case RECOMMENDED:
            case DEFAULT:
            default:
                this.recommendedOffsetType = offsetType;
        }
    }

    /**
     * Get the value of recommendedOffset
     *
     * @return the value of recommendedOffset
     */
    public String getRecommendedOffset() {
        return recommendedOffset;
    }

    /**
     * Set the value of recommendedOffset
     *
     * @param recommendedOffset new value of recommendedOffset
     */
    @PropertyListener
    public void setRecommendedOffset(String recommendedOffset) {
        this.recommendedOffset = recommendedOffset;
    }

    /**
     * Get the value of recommendedOffset
     *
     * @return the value of recommendedOffset
     */
    @Override
    public String getOffset() {
        if (offsetSource == null) {
            return recommendedOffset;
        }
        switch (offsetSource) {
            case EARLIEST:
                return earliestOffset;
            case LATEST:
                return latestOffset;
            case OVERDUE:
                return overdueOffset;
            case RECOMMENDED:
            case DEFAULT:
            default:
                return recommendedOffset;
        }
    }

    /**
     * Set the value of offset
     *
     * @param offset new value of offset
     */
    @PropertyListener
    public void setOffset(String offset) {
        if (offsetSource == null) {
            this.recommendedOffset = offset;
        }
        switch (offsetSource) {
            case EARLIEST:
                this.earliestOffset = offset;
            case LATEST:
                this.latestOffset = offset;
            case OVERDUE:
                this.overdueOffset = offset;
            case RECOMMENDED:
            case DEFAULT:
            default:
                this.recommendedOffset = offset;
        }
    }

    /**
     * Get the value of offsetBased
     *
     * @return the value of offsetBased
     */
    @Override
    public boolean isOffsetBased() {
        if (offsetSource == null) {
            return recommendedOffsetBased;
        }
        switch (offsetSource) {
            case EARLIEST:
                return earliestOffsetBased;
            case LATEST:
                return latestOffsetBased;
            case OVERDUE:
                return overdueOffsetBased;
            case RECOMMENDED:
            case DEFAULT:
            default:
                return recommendedOffsetBased;
        }
    }

    /**
     * Set the value of offsetBased
     *
     * @param offsetBased new value of offsetBased
     */
    @PropertyListener
    public void setOffsetBased(boolean offsetBased) {
        if (offsetSource == null) {
            this.recommendedOffsetBased = offsetBased;
        }
        switch (offsetSource) {
            case EARLIEST:
                this.earliestOffsetBased = offsetBased;
            case LATEST:
                this.latestOffsetBased = offsetBased;
            case OVERDUE:
                this.overdueOffsetBased = offsetBased;
            case RECOMMENDED:
            case DEFAULT:
            default:
                this.recommendedOffsetBased = offsetBased;
        }
    }

    /**
     * Get the value of recommendationValue
     *
     * @return the value of recommendationValue
     */
    public CdsCodeDTO getRecommendationValue() {
        return recommendationValue;
    }

    /**
     * Set the value of recommendationValue
     *
     * @param recommendationValue new recommendationValue of recommendationValue
     */
    @PropertyListener
    public void setRecommendationValue(CdsCodeDTO recommendationValue) {
        this.recommendationValue = recommendationValue;
    }

    public List<IceTestRecommendationDTO> getIceTestRecommendationDTOs() {
        return (List) this.getChildrenDTOs(IceTestRecommendationDTO.ByProposalId.class);
    }

    /**
     * Get the value of proposalId
     *
     * @return the value of proposalId
     */
    public String getProposalId() {
        return proposalId;
    }

    /**
     * Set the value of proposalId
     *
     * @param proposalId new value of proposalId
     */
    @PropertyListener
    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
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
     * Get the value of recommendedDate
     *
     * @return the value of recommendedDate
     */
    public Date getRecommendedDate() {
        return recommendedDate;
    }

    /**
     * Set the value of recommendedDate
     *
     * @param recommendedDate new value of recommendedDate
     */
    @PropertyListener
    public void setRecommendedDate(Date recommendedDate) {
        this.recommendedDate = recommendedDate;
    }

}
