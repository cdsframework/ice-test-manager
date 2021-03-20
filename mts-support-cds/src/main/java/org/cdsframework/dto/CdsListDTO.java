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
import java.util.List;
import javax.validation.constraints.NotNull;
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
import org.cdsframework.enumeration.CdsListType;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.util.comparator.CdsListComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = CdsListComparator.class, fields = "lower(code)")
@Table(databaseId = "CDS", name = "cds_list")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "List")
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = CdsListItemDTO.class, childQueryClass = CdsListItemDTO.ByCdsListId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = CdsListVersionRelDTO.class, childQueryClass = CdsListVersionRelDTO.ByCdsListId.class, isAutoRetrieve = false)
})
public class CdsListDTO extends BaseDTO {

    public interface ByLowerCode {
    }

    public interface ByCodeVersionId {
    }

    public interface ByCodeSystem {
    }

    public interface ByCodeSystemListType {
    }

    public interface ByOpenCdsConcept {
    }

    public interface ByValueSet {
    }

    public interface ByCdsListType {
    }

    private static final long serialVersionUID = -8151271981472720926L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String listId;
    @NotNull
    private CdsListType listType;
    @NotNull
    private String code;
    @NotNull
    private String name;
    private String description;
    @Column(name="enum_class_name")
    private String enumClass;
    @ReferenceDTO(discardChildren = true)
    @Column(name = "code_system_id")
    private CdsCodeSystemDTO cdsCodeSystemDTO;
    @ReferenceDTO(discardChildren = true)
    @Column(name = "value_set_id")
    private ValueSetDTO valueSetDTO;

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
     * Get the value of versioned
     *
     * @return the value of versioned
     */
    public boolean isVersioned() {
        return !getCdsListVersionRelDTOs().isEmpty();
    }

    /**
     * Get the value of conceptBased
     *
     * @return the value of conceptBased
     */
    public boolean isConceptBased() {
        return getListType() == CdsListType.AD_HOC_CONCEPT || getListType() == CdsListType.CONCEPT;
    }

    /**
     * Get the value of valueSetDTO
     *
     * @return the value of valueSetDTO
     */
    public ValueSetDTO getValueSetDTO() {
        return valueSetDTO;
    }

    /**
     * Set the value of valueSetDTO
     *
     * @param valueSetDTO new value of valueSetDTO
     */
    @PropertyListener
    public void setValueSetDTO(ValueSetDTO valueSetDTO) {
        this.valueSetDTO = valueSetDTO;
    }

    /**
     * Get the value of cdsCodeSystemDTO
     *
     * @return the value of cdsCodeSystemDTO
     */
    public CdsCodeSystemDTO getCdsCodeSystemDTO() {
        return cdsCodeSystemDTO;
    }

    /**
     * Set the value of cdsCodeSystemDTO
     *
     * @param cdsCodeSystemDTO new value of cdsCodeSystemDTO
     */
    @PropertyListener
    public void setCdsCodeSystemDTO(CdsCodeSystemDTO cdsCodeSystemDTO) {
        this.cdsCodeSystemDTO = cdsCodeSystemDTO;
    }

    /**
     * Get the value of enumClass
     *
     * @return the value of enumClass
     */
    public String getEnumClass() {
        return enumClass;
    }

    /**
     * Set the value of enumClass
     *
     * @param enumClass new value of enumClass
     */
    @PropertyListener
    public void setEnumClass(String enumClass) {
        this.enumClass = enumClass;
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
     * Get the value of listId
     *
     * @return the value of listId
     */
    public String getListId() {
        return listId;
    }

    /**
     * Set the value of listId
     *
     * @param listId new value of listId
     */
    @PropertyListener
    public void setListId(String listId) {
        this.listId = listId;
    }

    /**
     * Get the value of listType
     *
     * @return the value of listType
     */
    public CdsListType getListType() {
        return listType;
    }

    /**
     * Set the value of listType
     *
     * @param listType new value of listType
     */
    @PropertyListener
    public void setListType(CdsListType listType) {
        this.listType = listType;
    }

    @XmlElementRef(name = "cdsListItems")
    public List<CdsListItemDTO> getCdsListItemDTOs() {
        return getChildrenDTOs(CdsListItemDTO.ByCdsListId.class, CdsListItemDTO.class);
    }

    @XmlElementRef(name = "cdsListVersionRels")
    public List<CdsListVersionRelDTO> getCdsListVersionRelDTOs() {
        return getChildrenDTOs(CdsListVersionRelDTO.ByCdsListId.class, CdsListVersionRelDTO.class);
    }

    public List<CdsVersionDTO> getRelatedVersions() {
        List<CdsVersionDTO> result = new ArrayList<CdsVersionDTO>();
        for (CdsListVersionRelDTO cdsListVersionRelDTO : getCdsListVersionRelDTOs()) {
            result.add(cdsListVersionRelDTO.getCdsVersionDTO());
        }
        return result;
    }

    /**
     * Returns a comma separated string of related versions.
     *
     * @return
     */
    public String getRelatedVersionList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CdsVersionDTO cdsVersionDTO : getRelatedVersions()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(cdsVersionDTO.getBusinessId());
            stringBuilder.append(" - ");
            stringBuilder.append(cdsVersionDTO.getScopingEntityId());
            stringBuilder.append(" - ");
            stringBuilder.append(cdsVersionDTO.getVersion());
        }
        return stringBuilder.toString();
    }

    /**
     * Returns a CdsListItemDTO based on a CDS list item value and version.
     *
     * @param value
     * @param cdsVersionDTO
     * @return
     * @throws NotFoundException
     */
    public CdsListItemDTO getCdsListItemDTOByValueVersion(String value, CdsVersionDTO cdsVersionDTO)
            throws NotFoundException {
        final String METHODNAME = "getCdsListItemDTOByValueVersion ";
        CdsListItemDTO result = null;

        if (isVersioned() && !getRelatedVersions().contains(cdsVersionDTO)) {
            throw new NotFoundException(
                    METHODNAME
                    + getCode()
                    + " CDS list not available to provided version: "
                    + cdsVersionDTO.getOpenCdsVersionIdentifier());
        }

        for (CdsListItemDTO item : getCdsListItemDTOs()) {
            if (item != null
                    && item.getDslrValue() != null
                    && item.getDslrValue().equalsIgnoreCase(value)) {
                if (!isConceptBased()) {
                    result = item;
                    break;
                } else if (item.getOpenCdsConceptDTO() != null) {
                    for (OpenCdsConceptRelDTO openCdsConceptRelDTO : item.getOpenCdsConceptDTO().getOpenCdsConceptRelDTOs()) {
                        if (cdsVersionDTO.getConceptDeterminationMethodDTOs().contains(openCdsConceptRelDTO.getConceptDeterminationMethodDTO())) {
                            result = item;
                            break;
                        }
                    }
                }
            }
            if (result != null) {
                break;
            }
        }
        if (result == null) {
            throw new NotFoundException(
                    METHODNAME
                    + "CDS list item value not found: "
                    + value
                    + " - version: "
                    + cdsVersionDTO.getOpenCdsVersionIdentifier());
        }
        return result;
    }
}
