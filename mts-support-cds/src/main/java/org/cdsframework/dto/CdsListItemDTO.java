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
import org.cdsframework.enumeration.CdsListType;
import org.cdsframework.enumeration.GenerationSource;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@Table(databaseId = "CDS", name = "cds_list_item", view = "vw_cds_list_item")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "List Item", isListed = false)
public class CdsListItemDTO extends BaseDTO {

    public interface ByCdsListId {
    }

    public interface ByValueSetCdsCodeRelId {
    }

    public interface ByValueSetCdsCodeRelIdCdsListId {
    }

    public interface ByCdsCodeId {
    }

    public interface ByOpenCdsCodeId {
    }

    public interface ByOpenCdsCodeIdCdsListId {
    }

    public interface ByCdsCodeIdCdsListId {
    }

    public interface ByOpenCdsConceptRelId {
    }
    private static final long serialVersionUID = 8361487138981102492L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String itemId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = CdsListDTO.class)
    private String listId;
    private CdsListType itemType;
    @ReferenceDTO
    @Column(name = "code_id")
    private CdsCodeDTO cdsCodeDTO;
    @ReferenceDTO
    @Column(name = "value_set_cds_code_rel_id")
    private ValueSetCdsCodeRelDTO valueSetCdsCodeRelDTO;
    @ReferenceDTO
    @Column(name = "opencds_code_id")
    private OpenCdsConceptDTO openCdsConceptDTO;
    @ReferenceDTO
    @Column(name = "opencds_concept_rel_id")
    private OpenCdsConceptRelDTO openCdsConceptRelDTO;
    private String adHocId;
    private String adHocLabel;

    /**
     * Get the value of conceptBased
     *
     * @return the value of conceptBased
     */
    public boolean isConceptBased() {
        return getItemType() == CdsListType.AD_HOC_CONCEPT || getItemType() == CdsListType.CONCEPT;
    }

    /**
     * Get the value of openCdsConceptRelDTO
     *
     * @return the value of openCdsConceptRelDTO
     */
    public OpenCdsConceptRelDTO getOpenCdsConceptRelDTO() {
        return openCdsConceptRelDTO;
    }

    /**
     * Set the value of openCdsConceptRelDTO
     *
     * @param openCdsConceptRelDTO new value of openCdsConceptRelDTO
     */
    @PropertyListener
    public void setOpenCdsConceptRelDTO(OpenCdsConceptRelDTO openCdsConceptRelDTO) {
        this.openCdsConceptRelDTO = openCdsConceptRelDTO;
    }

    /**
     * Get the value of adHocLabel
     *
     * @return the value of adHocLabel
     */
    public String getAdHocLabel() {
        return adHocLabel;
    }

    /**
     * Set the value of adHocLabel
     *
     * @param adHocLabel new value of adHocLabel
     */
    @PropertyListener
    public void setAdHocLabel(String adHocLabel) {
        this.adHocLabel = adHocLabel;
    }

    /**
     * Get the value of adHocId
     *
     * @return the value of adHocId
     */
    public String getAdHocId() {
        return adHocId;
    }

    /**
     * Set the value of adHocId
     *
     * @param adHocId new value of adHocId
     */
    @PropertyListener
    public void setAdHocId(String adHocId) {
        this.adHocId = adHocId;
    }

    /**
     * Get the value of openCdsConceptDTO
     *
     * @return the value of openCdsConceptDTO
     */
    public OpenCdsConceptDTO getOpenCdsConceptDTO() {
        return openCdsConceptDTO;
    }

    /**
     * Set the value of openCdsConceptDTO
     *
     * @param openCdsConceptDTO new value of openCdsConceptDTO
     */
    @PropertyListener
    public void setOpenCdsConceptDTO(OpenCdsConceptDTO openCdsConceptDTO) {
        this.openCdsConceptDTO = openCdsConceptDTO;
    }

    /**
     * Get the value of valueSetCdsCodeRelDTO
     *
     * @return the value of valueSetCdsCodeRelDTO
     */
    public ValueSetCdsCodeRelDTO getValueSetCdsCodeRelDTO() {
        return valueSetCdsCodeRelDTO;
    }

    /**
     * Set the value of valueSetCdsCodeRelDTO
     *
     * @param valueSetCdsCodeRelDTO new value of valueSetCdsCodeRelDTO
     */
    @PropertyListener
    public void setValueSetCdsCodeRelDTO(ValueSetCdsCodeRelDTO valueSetCdsCodeRelDTO) {
        this.valueSetCdsCodeRelDTO = valueSetCdsCodeRelDTO;
    }

    /**
     * Get the value of cdsCodeDTO
     *
     * @return the value of cdsCodeDTO
     */
    public CdsCodeDTO getCdsCodeDTO() {
        return cdsCodeDTO;
    }

    /**
     * Set the value of cdsCodeDTO
     *
     * @param cdsCodeDTO new value of cdsCodeDTO
     */
    @PropertyListener
    public void setCdsCodeDTO(CdsCodeDTO cdsCodeDTO) {
        this.cdsCodeDTO = cdsCodeDTO;
    }

    /**
     * Get the id for the object backing the item
     *
     * @return the underlying object id
     */
    public String getRefId() {
        if (itemType == CdsListType.AD_HOC || itemType == CdsListType.JAVA_ENUM) {
            return adHocId;
        } else if (itemType == CdsListType.CODE_SYSTEM) {
            return cdsCodeDTO.getCodeId();
        } else if (isConceptBased()) {
            return openCdsConceptDTO.getCodeId();
        } else if (itemType == CdsListType.VALUE_SET) {
            return valueSetCdsCodeRelDTO.getCdsCodeDTO().getCodeId();
        } else {
            return "UNEXPECTED CDS LIST TYPE!!!";
        }
    }

    /**
     * Get the object backing the item
     *
     * @return the underlying object
     */
    public Object getRefObject() {
        if (itemType == CdsListType.AD_HOC || itemType == CdsListType.JAVA_ENUM) {
            return this;
        } else if (itemType == CdsListType.CODE_SYSTEM) {
            return cdsCodeDTO;
        } else if (isConceptBased()) {
            return openCdsConceptDTO;
        } else if (itemType == CdsListType.VALUE_SET) {
            return valueSetCdsCodeRelDTO.getCdsCodeDTO();
        } else {
            return "UNEXPECTED CDS LIST TYPE!!!";
        }
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        if (itemType == CdsListType.AD_HOC || itemType == CdsListType.JAVA_ENUM) {
            return adHocLabel;
        } else if (itemType == CdsListType.CODE_SYSTEM) {
            return String.format("%s (%s)", cdsCodeDTO.getDisplayName(), cdsCodeDTO.getCode());
        } else if (isConceptBased()) {
            return String.format("%s (%s)", openCdsConceptDTO.getDisplayName(), openCdsConceptDTO.getCode());
        } else if (itemType == CdsListType.VALUE_SET) {
            return String.format("%s (%s)", valueSetCdsCodeRelDTO.getCdsCodeDTO().getDisplayName(), valueSetCdsCodeRelDTO.getCdsCodeDTO().getCode());
        } else {
            return "UNEXPECTED CDS LIST TYPE!!!";
        }
    }

    /**
     * Returns the appropriately rendered value for output for this instance based on the itemType.
     *
     * @return
     */
    public String getValue() {
        if (itemType == CdsListType.AD_HOC || itemType == CdsListType.JAVA_ENUM) {
            return adHocId;
        } else if (itemType == CdsListType.CODE_SYSTEM) {
            return cdsCodeDTO.getCode();
        } else if (isConceptBased()) {
            return openCdsConceptDTO.getCode();
        } else if (itemType == CdsListType.VALUE_SET) {
            return valueSetCdsCodeRelDTO.getCdsCodeDTO().getCode();
        } else {
            return "UNEXPECTED CDS LIST TYPE!!!";
        }
    }

    /**
     * Returns the appropriately rendered value for DSLR output for this instance based on the itemType.
     *
     * @return
     */
    public String getDslrValue() {
        if (itemType == CdsListType.AD_HOC || itemType == CdsListType.JAVA_ENUM) {
            return adHocId;
        } else if (itemType == CdsListType.CODE_SYSTEM) {
            return cdsCodeDTO.getCode();
        } else if (isConceptBased()) {
            return openCdsConceptDTO.getCode();
        } else if (itemType == CdsListType.VALUE_SET) {
            return valueSetCdsCodeRelDTO.getCdsCodeDTO().getCode();
        } else {
            return "UNEXPECTED CDS LIST TYPE!!!";
        }
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
     * Get the value of itemId
     *
     * @return the value of itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Set the value of itemId
     *
     * @param itemId new value of itemId
     */
    @PropertyListener
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Get the value of itemType
     *
     * @return the value of itemType
     */
    public CdsListType getItemType() {
        return itemType;
    }

    /**
     * Set the value of itemType
     *
     * @param itemType new value of itemType
     */
    @PropertyListener
    public void setItemType(CdsListType itemType) {
        this.itemType = itemType;
    }
}
