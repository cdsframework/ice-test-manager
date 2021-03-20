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
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.OrderByMapEntries;
import org.cdsframework.annotation.OrderByMapEntry;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;

/**
 * Provides a data transfer object for communication of a vaccine group.
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderByMapEntries({
    @OrderByMapEntry(sortFieldKey = "vaccineGroup.code", sortFieldValue = "lower(code)"),
    @OrderByMapEntry(sortFieldKey = "vaccineGroup.displayName", sortFieldValue = "lower(display_name)")
})
@OrderBy(fields = "lower(display_name)")
@Table(databaseId = "ICE", name = "ice_vaccine_group", view = "vw_ice_vaccine_group")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Vaccine Group")
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = IceVaccineGroupDiseaseRelDTO.class, childQueryClass = IceVaccineGroupDiseaseRelDTO.ByVaccineGroupId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = IceVaccineGroupVersionRelDTO.class, childQueryClass = IceVaccineGroupVersionRelDTO.ByVaccineGroupId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = IceVaccineGroupVaccineRelDTO.class, childQueryClass = IceVaccineGroupVaccineRelDTO.VaccineByVersionDiseaseRelationship.class, isAutoRetrieve = false)
})
public class IceVaccineGroupDTO extends BaseDTO {

    public interface ByVaccineGroupNameOrCode {
    }

    public interface ByTestIdVacComponentId {
    }

    public interface ByTestIdVacId {
    }

    public interface ByTestId {
    }

    private static final long serialVersionUID = -5434567223981092966L;
    @Id
    @GeneratedValue(source = GenerationSource.AUTO)
    private String groupId;
    @NotNull
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name = "vaccine_group_id")
    private CdsCodeDTO vaccineGroup;
    private Integer priority;
    @ReferenceDTO(isNotFoundAllowed = true)
    @Column(name = "primary_concept")
    private OpenCdsConceptDTO primaryOpenCdsConceptDTO;
    @Column(name = "code", updateable = false, insertable = false)
    private String vaccineGroupCode;
    @Column(name = "display_name", updateable = false, insertable = false)
    private String vaccineGroupName;

    /**
     * Get the value of vaccineGroupName
     *
     * @return the value of vaccineGroupName
     */
    public String getVaccineGroupName() {
        return vaccineGroupName;
    }

    /**
     * Set the value of vaccineGroupName
     *
     * @param vaccineGroupName new value of vaccineGroupName
     */
    public void setVaccineGroupName(String vaccineGroupName) {
        this.vaccineGroupName = vaccineGroupName;
    }

    /**
     * Get the value of vaccineGroupCode
     *
     * @return the value of vaccineGroupCode
     */
    public String getVaccineGroupCode() {
        return vaccineGroupCode;
    }

    /**
     * Set the value of vaccineGroupCode
     *
     * @param vaccineGroupCode new value of vaccineGroupCode
     */
    public void setVaccineGroupCode(String vaccineGroupCode) {
        this.vaccineGroupCode = vaccineGroupCode;
    }

    /**
     * Get the value of primaryOpenCdsConceptDTO
     *
     * @return the value of primaryOpenCdsConceptDTO
     */
    public OpenCdsConceptDTO getPrimaryOpenCdsConceptDTO() {
        return primaryOpenCdsConceptDTO;
    }

    /**
     * Set the value of primaryOpenCdsConceptDTO
     *
     * @param primaryOpenCdsConceptDTO new value of primaryOpenCdsConceptDTO
     */
    @PropertyListener
    public void setPrimaryOpenCdsConceptDTO(OpenCdsConceptDTO primaryOpenCdsConceptDTO) {
        this.primaryOpenCdsConceptDTO = primaryOpenCdsConceptDTO;
    }

    public String getGroupId() {
        return groupId;
    }

    @PropertyListener
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Get the value of priority
     *
     * @return the value of priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Set the value of priority
     *
     * @param priority new value of priority
     */
    @PropertyListener
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * Returns the vaccine group sequence
     *
     * @return the vaccine group sequence.
     */
    public CdsCodeDTO getVaccineGroup() {
        return vaccineGroup;
    }

    /**
     * Sets the vaccine group sequence
     *
     * @param vaccineGroup the vaccine group sequence.
     */
    @PropertyListener
    public void setVaccineGroup(CdsCodeDTO vaccineGroup) {
        boolean changed = isChanged(this.vaccineGroup, vaccineGroup);
        if (changed) {
            this.setPrimaryOpenCdsConceptDTO(null);
        }
        this.vaccineGroup = vaccineGroup;
    }

    public List<IceVaccineGroupDiseaseRelDTO> getIceVaccineGroupDiseaseRelDTOs() {
        return getChildrenDTOs(IceVaccineGroupDiseaseRelDTO.ByVaccineGroupId.class, IceVaccineGroupDiseaseRelDTO.class);
    }

    public List<IceDiseaseDTO> getRelatedDiseases() {
        List<IceDiseaseDTO> result = new ArrayList<IceDiseaseDTO>();
        for (IceVaccineGroupDiseaseRelDTO item : getIceVaccineGroupDiseaseRelDTOs()) {
            result.add(item.getIceDiseaseDTO());
        }
        return result;
    }

    public List<IceVaccineGroupVersionRelDTO> getIceVaccineGroupVersionRelDTOs() {
        return getChildrenDTOs(IceVaccineGroupVersionRelDTO.ByVaccineGroupId.class, IceVaccineGroupVersionRelDTO.class);
    }

    public List<CdsVersionDTO> getRelatedVersions() {
        List<CdsVersionDTO> result = new ArrayList<CdsVersionDTO>();
        for (IceVaccineGroupVersionRelDTO item : getIceVaccineGroupVersionRelDTOs()) {
            result.add(item.getCdsVersionDTO());
        }
        return result;
    }

    /**
     * Returns a comma separated string of related diseases.
     *
     * @return
     */
    public String getRelatedDiseaseList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (IceDiseaseDTO iceDiseaseDTO : getRelatedDiseases()) {
            if (iceDiseaseDTO != null && iceDiseaseDTO.getDiseaseCdsCodeDTO() != null) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(iceDiseaseDTO.getDiseaseCdsCodeDTO().getDisplayName());
            }
        }
        return stringBuilder.toString();
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
     * Returns a comma separated string of related vaccines.
     *
     * @return
     */
    public String getRelatedVaccineList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CdsCodeDTO cdsCodeDTO : getRelatedVaccines()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(cdsCodeDTO.getDisplayName());
        }
        return stringBuilder.toString();
    }

    public List<IceVaccineGroupVaccineRelDTO> getIceVaccineGroupVaccineRelDTOs() {
        return getChildrenDTOs(IceVaccineGroupVaccineRelDTO.VaccineByVersionDiseaseRelationship.class, IceVaccineGroupVaccineRelDTO.class);
    }

    public List<CdsCodeDTO> getRelatedVaccines() {
        List<CdsCodeDTO> relatedVaccines = new ArrayList<CdsCodeDTO>();
        for (IceVaccineGroupVaccineRelDTO item : getIceVaccineGroupVaccineRelDTOs()) {
            relatedVaccines.add(item.getVaccine());
        }
        return relatedVaccines;
    }
}
