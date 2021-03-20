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
import javax.xml.bind.annotation.XmlElementRef;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.SortColumn;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.RefreshOnAddOrUpdate;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.CodedElementType;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.enumeration.ValueSetType;
import org.cdsframework.interfaces.CodedElementSource;
import org.cdsframework.util.comparator.CodedElementSourceComparator;

/**
 *
 * @author HLN Consulting, LLC

 Loosely followed: http://wiki.hl7.org/index.php?title=Requirements-Value_Sets
 */
@Entity
@RefreshOnAddOrUpdate
@OrderBy(comparator = CodedElementSourceComparator.class, fields = "lower(name)")
@Table(databaseId = "CDS", name = "value_set")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Value Set")
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = ValueSetSubValueSetRelDTO.class, childQueryClass = ValueSetSubValueSetRelDTO.ByValueSetId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = ValueSetCdsCodeRelDTO.class, childQueryClass = ValueSetCdsCodeRelDTO.ByValueSetId.class, isAutoRetrieve = false)
})
public class ValueSetDTO extends BaseDTO implements CodedElementSource {

    private static final long serialVersionUID = -7863640517110015800L;

    public interface ByOid {
    }
    public interface ByOidVersion {
    }    
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String valueSetId;
    @NotNull
    private String code;
    @NotNull
    @SortColumn(sortFieldKey = "name", sortFieldValue = "lower(name)")
    private String name;
    @NotNull
    private String oid;
    private String description;
    private String version;
    private String versionDescription;
    private Date versionEffectiveDate;
    private Date versionExpirationDate;
    private String versionStatus;
    private ValueSetType valueSetType;
    private String source;

    /**
     * Get the value of source
     *
     * @return the value of source
     */
    public String getSource() {
        return source;
    }

    /**
     * Set the value of source
     *
     * @param source new value of source
     */
    @PropertyListener
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Get the value of valueSetType
     *
     * @return the value of valueSetType
     */
    public ValueSetType getValueSetType() {
        return valueSetType;
    }

    /**
     * Set the value of valueSetType
     *
     * @param valueSetType new value of valueSetType
     */
    @PropertyListener
    public void setValueSetType(ValueSetType valueSetType) {
        this.valueSetType = valueSetType;
    }

    /**
     * Get the value of versionDescription
     *
     * @return the value of versionDescription
     */
    public String getVersionDescription() {
        return versionDescription;
    }

    /**
     * Set the value of versionDescription
     *
     * @param versionDescription new value of versionDescription
     */
    @PropertyListener
    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }

    /**
     * Get the value of versionExpirationDate
     *
     * @return the value of versionExpirationDate
     */
    public Date getVersionExpirationDate() {
        return versionExpirationDate;
    }

    /**
     * Set the value of versionExpirationDate
     *
     * @param versionExpirationDate new value of versionExpirationDate
     */
    @PropertyListener
    public void setVersionExpirationDate(Date versionExpirationDate) {
        this.versionExpirationDate = versionExpirationDate;
    }

    /**
     * Get the value of versionEffectiveDate
     *
     * @return the value of versionEffectiveDate
     */
    public Date getVersionEffectiveDate() {
        return versionEffectiveDate;
    }

    /**
     * Set the value of versionEffectiveDate
     *
     * @param versionEffectiveDate new value of versionEffectiveDate
     */
    @PropertyListener
    public void setVersionEffectiveDate(Date versionEffectiveDate) {
        this.versionEffectiveDate = versionEffectiveDate;
    }

    /**
     * Get the value of versionStatus
     *
     * @return the value of versionStatus
     */
    public String getVersionStatus() {
        return versionStatus;
    }

    /**
     * Set the value of versionStatus
     *
     * @param versionStatus new value of versionStatus
     */
    @PropertyListener
    public void setVersionStatus(String versionStatus) {
        this.versionStatus = versionStatus;
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
     * Get the value of version
     *
     * @return the value of version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set the value of version
     *
     * @param version new value of version
     */
    @PropertyListener
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Get the value of oid
     *
     * @return the value of oid
     */
    @Override
    public String getOid() {
        return oid;
    }

    /**
     * Set the value of oid
     *
     * @param oid new value of oid
     */
    @PropertyListener
    @Override
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    @PropertyListener
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of valueSetId
     *
     * @return the value of valueSetId
     */
    public String getValueSetId() {
        return valueSetId;
    }

    /**
     * Set the value of valueSetId
     *
     * @param valueSetId new value of valueSetId
     */
    @PropertyListener
    public void setValueSetId(String valueSetId) {
        this.valueSetId = valueSetId;
    }

    @XmlElementRef(name = "valueSetSubValueSetRels")
    public List<ValueSetSubValueSetRelDTO> getValueSetSubValueSetRelDTOs() {
        return getChildrenDTOs(ValueSetSubValueSetRelDTO.ByValueSetId.class, ValueSetSubValueSetRelDTO.class);
    }

    @XmlElementRef(name = "valueSetCdsCodeRels")
    public List<ValueSetCdsCodeRelDTO> getValueSetCdsCodeRelDTOs() {
        return getChildrenDTOs(ValueSetCdsCodeRelDTO.ByValueSetId.class, ValueSetCdsCodeRelDTO.class);
    }

    public List<CdsCodeDTO> getCdsCodeDTOs() {
        List<CdsCodeDTO> result = new ArrayList<CdsCodeDTO>();
        addValueSetCodesToList(this, result);
        return result;
    }

    private static void addValueSetCodesToList(ValueSetDTO valueSetDTO, List<CdsCodeDTO> codeList) {
        for (ValueSetCdsCodeRelDTO valueSetCdsCodeRelDTO : valueSetDTO.getValueSetCdsCodeRelDTOs()) {
            codeList.add(valueSetCdsCodeRelDTO.getCdsCodeDTO());
        }
        for (ValueSetSubValueSetRelDTO valueSetSubValueSetRelDTO : valueSetDTO.getValueSetSubValueSetRelDTOs()) {
            addValueSetCodesToList(valueSetSubValueSetRelDTO.getSubValueSetDTO(), codeList);
        }
    }

    public List<ValueSetCdsCodeRelDTO> getAllValueSetCdsCodeRelDTOs() {
        List<ValueSetCdsCodeRelDTO> result = new ArrayList<ValueSetCdsCodeRelDTO>();
        addValueSetCdsCodeRelsToList(this, result);
        return result;
    }

    private static void addValueSetCdsCodeRelsToList(ValueSetDTO valueSetDTO, List<ValueSetCdsCodeRelDTO> valueSetCdsCodeRelDTOs) {
        valueSetCdsCodeRelDTOs.addAll(valueSetDTO.getValueSetCdsCodeRelDTOs());
        for (ValueSetSubValueSetRelDTO valueSetSubValueSetRelDTO : valueSetDTO.getValueSetSubValueSetRelDTOs()) {
            addValueSetCdsCodeRelsToList(valueSetSubValueSetRelDTO.getSubValueSetDTO(), valueSetCdsCodeRelDTOs);
        }
    }

    public List<ValueSetDTO> getValueSetDTOs() {
        List<ValueSetDTO> result = new ArrayList<ValueSetDTO>();
        addValueSetDTOsToList(this, result);
        return result;
    }

    private static void addValueSetDTOsToList(ValueSetDTO valueSetDTO, List<ValueSetDTO> valueSetDTOs) {
        if (!valueSetDTOs.contains(valueSetDTO)) {
            valueSetDTOs.add(valueSetDTO);
        }
        for (ValueSetSubValueSetRelDTO item : valueSetDTO.getValueSetSubValueSetRelDTOs()) {
            addValueSetDTOsToList(item.getSubValueSetDTO(), valueSetDTOs);
        }
    }

    @Override
    public CodedElementType getCodedElementType() {
        return CodedElementType.VALUE_SET;
    }

    @Override
    public String getTitle() {
        return String.format("%s (%s: %s)", getName(), getCodedElementType(), getOid());
    }

    /**
     * Returns a standards displayable label for the value set.
     * 
     * @return 
     */
    public String getLabel() {
        String result = null;
        if (name != null && oid != null) {
            result = String.format("%s (%s)", name, oid);
        } else if (name == null && oid != null) {
            result = oid;
        } else if (name != null && oid == null) {
            result = name;
        }
        return result;
    }
}
