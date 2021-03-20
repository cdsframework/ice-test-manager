/**
 * The MTS support cds project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
import javax.xml.bind.annotation.XmlElementRef;
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
import org.cdsframework.util.comparator.DataTemplateComparator;
import org.cdsframework.util.comparator.DataTemplateNodeRelComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = DataTemplateComparator.class)
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = DataTemplateNodeRelDTO.class,
            childQueryClass = DataTemplateNodeRelDTO.ByTemplateId.class,
            isAutoRetrieve = true,
            comparatorClass = DataTemplateNodeRelComparator.class),
        @ParentChildRelationship(childDtoClass = DataTemplateLinkRelDTO.class,
            childQueryClass = DataTemplateLinkRelDTO.ByTemplateId.class,
            isAutoRetrieve = true)

//    @ParentChildRelationship(childDtoClass = DataTemplateNodeRelDTO.class, childQueryClass = DataTemplateNodeRelDTO.ByTemplateId.class, isAutoRetrieve = true),
//    @ParentChildRelationship(childDtoClass = CriteriaTemplateRestrictedElementDTO.class, childQueryClass = CriteriaTemplateRestrictedElementDTO.ByTemplateId.class, isAutoRetrieve = true)
})
@Table(databaseId = "CDS", name = "data_template")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Data Template")
public class DataTemplateDTO extends BaseDTO {

    private static final long serialVersionUID = -5695228522555095335L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String templateId;
    @NotNull
    @Size(max = 128)
    private String oid;
    @NotNull
    @Size(max = 128)
    private String code;
    @NotNull
    @Size(max = 512)
    private String name;
    @Size(max = 2048)
    private String description;
    @NotNull
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name = "model_id")
    private DataModelDTO dataModelDTO;
    @NotNull
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name = "class_id")
    private DataModelClassDTO rootClass;
    private Date effectiveDate;
    private Date expirationDate;
    @NotNull
    @ReferenceDTO(isNotFoundAllowed = false)
    private CdsCodeDTO status;
    private boolean restrictionAllowed;
    @Size(max = 2048)
    @Column(name = "data_expected")
    private String dataExpectedNoRestriction;
    @Column(name = "back_restriction")
    private boolean searchBackPeriodRestriction;
    @Column(name = "forward_restriction")
    private boolean searchForwardPeriodRestriction;
    @Column(name = "number_restriction")
    private boolean numberLookBackRestriction;
    @Column(name = "max_rate_restriction")
    private boolean maxSamplingRateRestriction;

    /**
     * Get the value of maxSamplingRateRestriction
     *
     * @return the value of maxSamplingRateRestriction
     */
    public boolean isMaxSamplingRateRestriction() {
        return maxSamplingRateRestriction;
    }

    /**
     * Set the value of maxSamplingRateRestriction
     *
     * @param maxSamplingRateRestriction new value of maxSamplingRateRestriction
     */
    @PropertyListener
    public void setMaxSamplingRateRestriction(boolean maxSamplingRateRestriction) {
        this.maxSamplingRateRestriction = maxSamplingRateRestriction;
    }

    /**
     * Get the value of numberLookBackRestriction
     *
     * @return the value of numberLookBackRestriction
     */
    public boolean isNumberLookBackRestriction() {
        return numberLookBackRestriction;
    }

    /**
     * Set the value of numberLookBackRestriction
     *
     * @param numberLookBackRestriction new value of numberLookBackRestriction
     */
    @PropertyListener
    public void setNumberLookBackRestriction(boolean numberLookBackRestriction) {
        this.numberLookBackRestriction = numberLookBackRestriction;
    }

    /**
     * Get the value of searchForwardPeriodRestriction
     *
     * @return the value of searchForwardPeriodRestriction
     */
    public boolean isSearchForwardPeriodRestriction() {
        return searchForwardPeriodRestriction;
    }

    /**
     * Set the value of searchForwardPeriodRestriction
     *
     * @param searchForwardPeriodRestriction new value of searchForwardPeriodRestriction
     */
    @PropertyListener
    public void setSearchForwardPeriodRestriction(boolean searchForwardPeriodRestriction) {
        this.searchForwardPeriodRestriction = searchForwardPeriodRestriction;
    }

    /**
     * Get the value of searchBackPeriodRestriction
     *
     * @return the value of searchBackPeriodRestriction
     */
    public boolean isSearchBackPeriodRestriction() {
        return searchBackPeriodRestriction;
    }

    /**
     * Set the value of searchBackPeriodRestriction
     *
     * @param searchBackPeriodRestriction new value of searchBackPeriodRestriction
     */
    @PropertyListener
    public void setSearchBackPeriodRestriction(boolean searchBackPeriodRestriction) {
        this.searchBackPeriodRestriction = searchBackPeriodRestriction;
    }

    /**
     * Get the value of dataExpectedNoRestriction
     *
     * @return the value of dataExpectedNoRestriction
     */
    public String getDataExpectedNoRestriction() {
        return dataExpectedNoRestriction;
    }

    /**
     * Set the value of dataExpectedNoRestriction
     *
     * @param dataExpectedNoRestriction new value of dataExpectedNoRestriction
     */
    @PropertyListener
    public void setDataExpectedNoRestriction(String dataExpectedNoRestriction) {
        this.dataExpectedNoRestriction = dataExpectedNoRestriction;
    }

    /**
     * Get the value of expirationDate
     *
     * @return the value of expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Set the value of expirationDate
     *
     * @param expirationDate new value of expirationDate
     */
    @PropertyListener
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public CdsCodeDTO getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    @PropertyListener
    public void setStatus(CdsCodeDTO status) {
        this.status = status;
    }

    /**
     * Get the value of effectiveDate
     *
     * @return the value of effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Set the value of effectiveDate
     *
     * @param effectiveDate new value of effectiveDate
     */
    @PropertyListener
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
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
     * Get the value of rootClass
     *
     * @return the value of rootClass
     */
    public DataModelClassDTO getRootClass() {
        return rootClass;
    }

    /**
     * Set the value of rootClass
     *
     * @param rootClass new value of rootClass
     */
    @PropertyListener
    public void setRootClass(DataModelClassDTO rootClass) {
        this.rootClass = rootClass;
    }

    /**
     * Get the value of dataModelDTO
     *
     * @return the value of dataModelDTO
     */
    public DataModelDTO getDataModelDTO() {
        return dataModelDTO;
    }

    /**
     * Set the value of dataModelDTO
     *
     * @param dataModelDTO new value of dataModelDTO
     */
    @PropertyListener
    public void setDataModelDTO(DataModelDTO dataModelDTO) {
        this.dataModelDTO = dataModelDTO;
    }

    /**
     * Get the value of restrictionAllowed
     *
     * @return the value of restrictionAllowed
     */
    public boolean isRestrictionAllowed() {
        return restrictionAllowed;
    }

    /**
     * Set the value of restrictionAllowed
     *
     * @param restrictionAllowed new value of restrictionAllowed
     */
    @PropertyListener
    public void setRestrictionAllowed(boolean restrictionAllowed) {
        this.restrictionAllowed = restrictionAllowed;
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
     * @param assigningAuthorityName new value of code
     */
    @PropertyListener
    public void setCode(String assigningAuthorityName) {
        this.code = assigningAuthorityName;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    @PropertyListener
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the value of oid
     *
     * @return the value of oid
     */
    public String getOid() {
        return oid;
    }

    /**
     * Set the value of oid
     *
     * @param root new value of oid
     */
    @PropertyListener
    public void setOid(String root) {
        this.oid = root;
    }

    /**
     * Get the value of templateId
     *
     * @return the value of templateId
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * Set the value of templateId
     *
     * @param templateId new value of templateId
     */
    @PropertyListener
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * Convenience method for retrieving the template title.
     *
     * @return
     */
    public String getTitle() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getCode());
        if (getOid() != null) {
            stringBuilder.append(" (root: ").append(getOid());
            // TODO: draw from children
//            if (getCode() != null) {
//                stringBuilder.append("; code: ").append(getCode().getCode());
//            }
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    /**
     * Convenience method for retrieving child DTO list.
     *
     * @return
     */
    @XmlElementRef(name = "dataTemplateNodeRels")
    public List<DataTemplateNodeRelDTO> getDataTemplateNodeRelDTOs() {
        return getChildrenDTOs(DataTemplateNodeRelDTO.ByTemplateId.class, DataTemplateNodeRelDTO.class);
    }

    /**
     * Convenience method for retrieving child DTO list.
     *
     * @return
     */
    @XmlElementRef(name = "dataTemplateLinkRels")
    public List<DataTemplateLinkRelDTO> getDataTemplateLinkRelDTOs() {
        return getChildrenDTOs(DataTemplateLinkRelDTO.ByTemplateId.class, DataTemplateLinkRelDTO.class);
    }
//
//    /**
//     * Convenience method for retrieving child DTO list.
//     *
//     * @return
//     */
//    public List<CriteriaTemplateRestrictedElementDTO> TemplateRestrictionElementDTO() {
//        return getChildrenDTOs(CriteriaTemplateRestrictedElementDTO.ByTemplateId.class, CriteriaTemplateRestrictedElementDTO.class);
//    }

    public List<DataTemplateNodeRelDTO> getChoices() {
        List<DataTemplateNodeRelDTO> result = new ArrayList<DataTemplateNodeRelDTO>();
        for (DataTemplateNodeRelDTO item : getDataTemplateNodeRelDTOs()) {
            if (item.getDataModelClassNodeDTO().isChoice()) {
                result.add(item);
            }
        }
        return result;
    }
}
