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
import java.util.Collections;
import java.util.Date;
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
import org.cdsframework.util.comparator.IceDiseaseComparator;

/**
 * Provides a data transfer object for communication of a vaccine.
 *
 * @author HLN Consulting, LLC
 */
@Entity
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = IceVaccineComponentRelDTO.class, childQueryClass = IceVaccineComponentRelDTO.ByVaccineId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = IceVaccineVersionRelDTO.class, childQueryClass = IceVaccineVersionRelDTO.ByVaccineId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = IceVaccineGroupVaccineRelDTO.class, childQueryClass = IceVaccineGroupVaccineRelDTO.VaccineGroupByVersionDiseaseRelationship.class, isAutoRetrieve = false),
})
@OrderBy(fields = "lower(display_name)")
@OrderByMapEntries({
     @OrderByMapEntry(sortFieldKey = "vaccine.code", sortFieldValue = "lower(code)"),
     @OrderByMapEntry(sortFieldKey = "vaccine.displayName", sortFieldValue = "lower(display_name)")
})
@Table(databaseId = "ICE", name = "ice_vaccine", view = "vw_ice_vaccine")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Vaccine")
public class IceVaccineDTO extends BaseDTO {

    public interface ByGroupId {
    }

    public interface ByComponent {
    }

    public interface ByVaccineCode {
    }

    public interface ByTestId {
    }

    public interface BySeriesId {
    }
    private static final long serialVersionUID = -8940904710530757740L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String vaccineId;
    @NotNull
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name = "vaccine_code_id")
    private CdsCodeDTO vaccine;
    private String vaccineName;
    private String licensedMinAgeForUse;
    private String licensedMaxAgeForUse;
    @ReferenceDTO(isNotFoundAllowed = true)
    @Column(name = "manufacturer_code_id")
    private CdsCodeDTO manufacturerCode;
    private String tradeName;
    private String validMinAgeForUse;
    private String validMaxAgeForUse;
    private boolean active = true;
    private Date minDateForUse;
    private Date maxDateForUse;
    @ReferenceDTO(isNotFoundAllowed = true)
    @Column(name = "primary_concept")
    private OpenCdsConceptDTO primaryOpenCdsConceptDTO;

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

    /**
     * Get the value of vaccineName
     *
     * @return the value of vaccineName
     */
    public String getVaccineName() {
        String name = null;
        if (vaccineName != null) {
            name = vaccineName;
        } else if (vaccine != null) {
            name = vaccine.getDisplayName();
            if (tradeName != null) {
                name += " (" + tradeName + ")";
    }
        }
        return name;
    }

    /**
     * Set the value of vaccineName
     *
     * @param vaccineName new value of vaccineName
     */
    @PropertyListener
    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getVaccineId() {
        return vaccineId;
    }

    @PropertyListener
    public void setVaccineId(String vaccineId) {
        this.vaccineId = vaccineId;
    }

    /**
     * Get the value of active
     *
     * @return the value of active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set the value of active
     *
     * @param active new value of active
     */
    @PropertyListener
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Get the value of validMaxAgeForUse
     *
     * @return the value of validMaxAgeForUse
     */
    public String getValidMaxAgeForUse() {
        return validMaxAgeForUse;
    }

    /**
     * Set the value of validMaxAgeForUse
     *
     * @param validMaxAgeForUse new value of validMaxAgeForUse
     */
    @PropertyListener
    public void setValidMaxAgeForUse(String validMaxAgeForUse) {
        this.validMaxAgeForUse = validMaxAgeForUse;
    }

    /**
     * Get the value of validMinAgeForUse
     *
     * @return the value of validMinAgeForUse
     */
    public String getValidMinAgeForUse() {
        return validMinAgeForUse;
    }

    /**
     * Set the value of validMinAgeForUse
     *
     * @param validMinAgeForUse new value of validMinAgeForUse
     */
    @PropertyListener
    public void setValidMinAgeForUse(String validMinAgeForUse) {
        this.validMinAgeForUse = validMinAgeForUse;
    }

    /**
     * Get the value of tradeName
     *
     * @return the value of tradeName
     */
    public String getTradeName() {
        return tradeName;
    }

    /**
     * Set the value of tradeName
     *
     * @param tradeName new value of tradeName
     */
    @PropertyListener
    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    /**
     * Get the value of manufacturerCode
     *
     * @return the value of manufacturerCode
     */
    public CdsCodeDTO getManufacturerCode() {
        return manufacturerCode;
    }

    /**
     * Set the value of manufacturerCode
     *
     * @param manufacturerCode new value of manufacturerCode
     */
    @PropertyListener
    public void setManufacturerCode(CdsCodeDTO manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    /**
     * Get the value of licensedMaxAgeForUse
     *
     * @return the value of licensedMaxAgeForUse
     */
    public String getLicensedMaxAgeForUse() {
        return licensedMaxAgeForUse;
    }

    /**
     * Set the value of licensedMaxAgeForUse
     *
     * @param licensedMaxAgeForUse new value of licensedMaxAgeForUse
     */
    @PropertyListener
    public void setLicensedMaxAgeForUse(String licensedMaxAgeForUse) {
        this.licensedMaxAgeForUse = licensedMaxAgeForUse;
    }

    /**
     * Get the value of licensedMinAgeForUse
     *
     * @return the value of licensedMinAgeForUse
     */
    public String getLicensedMinAgeForUse() {
        return licensedMinAgeForUse;
    }

    /**
     * Set the value of licensedMinAgeForUse
     *
     * @param licensedMinAgeForUse new value of licensedMinAgeForUse
     */
    @PropertyListener
    public void setLicensedMinAgeForUse(String licensedMinAgeForUse) {
        this.licensedMinAgeForUse = licensedMinAgeForUse;
    }

    /**
     * Returns the vaccine.
     *
     * @return the vaccine.
     */
    public CdsCodeDTO getVaccine() {
        return vaccine;
    }

    /**
     * Sets the vaccine.
     *
     * @param vaccine the vaccine.
     */
    @PropertyListener
    public void setVaccine(CdsCodeDTO vaccine) {
        boolean changed = isChanged(this.vaccine, vaccine);
        if (changed) {
            this.setPrimaryOpenCdsConceptDTO(null);
        }
        this.vaccine = vaccine;
    }

    /**
     * Get the value of minDateForUse
     *
     * @return the value of minDateForUse
     */
    public Date getMinDateForUse() {
        return minDateForUse;
    }

    /**
     * Set the value of minDateForUse
     *
     * @param minDateForUse new value of minDateForUse
     */
    @PropertyListener
    public void setMinDateForUse(Date minDateForUse) {
        this.minDateForUse = minDateForUse;
    }

    /**
     * Get the value of maxDateForUse
     *
     * @return the value of maxDateForUse
     */
    public Date getMaxDateForUse() {
        return maxDateForUse;
    }

    /**
     * Set the value of maxDateForUse
     *
     * @param maxDateForUse new value of maxDateForUse
     */
    @PropertyListener
    public void setMaxDateForUse(Date maxDateForUse) {
        this.maxDateForUse = maxDateForUse;
    }

    /**
     * Returns vaccines IceVaccineComponentRelDTOs.
     *
     * @return the IceVaccineComponentRelDTOs.
     */
    public List<IceVaccineComponentRelDTO> getIceVaccineComponentRelDTOs() {
        return getChildrenDTOs(IceVaccineComponentRelDTO.ByVaccineId.class, IceVaccineComponentRelDTO.class);
    }

    /**
     * Returns vaccines IceVaccineVersionRelDTOs.
     *
     * @return the IceVaccineVersionRelDTOs.
     */
    public List<IceVaccineVersionRelDTO> getIceVaccineVersionRelDTOs() {
        return getChildrenDTOs(IceVaccineVersionRelDTO.ByVaccineId.class, IceVaccineVersionRelDTO.class);
    }

    /**
     * Returns vaccines IceVaccineGroupVaccineRelDTOs.
     *
     * @return the IceVaccineGroupVaccineRelDTOs.
     */
    public List<IceVaccineGroupVaccineRelDTO> getIceVaccineGroupVaccineRelDTOs() {
        return getChildrenDTOs(IceVaccineGroupVaccineRelDTO.VaccineGroupByVersionDiseaseRelationship.class, IceVaccineGroupVaccineRelDTO.class);
    }

    /**
     * Returns vaccines components.
     *
     * @return the IceVaccineComponentDTOs.
     */
    public List<IceVaccineComponentDTO> getVaccineComponents() {
        List<IceVaccineComponentDTO> result = new ArrayList<IceVaccineComponentDTO>();
        for (IceVaccineComponentRelDTO item : getIceVaccineComponentRelDTOs()) {
            result.add(item.getIceVaccineComponentDTO());
        }
        return result;
    }

    /**
     * Returns vaccine versions.
     *
     * @return the CdsVersionDTOs.
     */
    public List<CdsVersionDTO> getRelatedVersions() {
        List<CdsVersionDTO> result = new ArrayList<CdsVersionDTO>();
        for (IceVaccineVersionRelDTO item : getIceVaccineVersionRelDTOs()) {
            result.add(item.getCdsVersionDTO());
        }
        return result;
    }

    /**
     * Returns disease immunity list.
     *
     * @return the disease immunity list.
     */
    public List<IceDiseaseDTO> getRelatedDiseases() {
        List<IceDiseaseDTO> result = new ArrayList<IceDiseaseDTO>();
        for (IceVaccineComponentDTO item : getVaccineComponents()) {
            for (IceDiseaseDTO disease : item.getRelatedDiseases()) {
                result.add(disease);
            }
        }
        return result;
    }

    /**
     * Returns vaccine group list.
     *
     * @return the vaccine group list.
     */
    public List<CdsCodeDTO> getRelatedGroups() {
        List<CdsCodeDTO> result = new ArrayList<CdsCodeDTO>();
        for (IceVaccineGroupVaccineRelDTO item : getIceVaccineGroupVaccineRelDTOs()) {
            result.add(item.getVaccineGroup());
        }
        return result;
    }

    /**
     * Returns whether the vaccine contains a live virus.
     *
     * @return the state of this vaccine containing a live virus.
     */
    public boolean isLiveVirus() {
        boolean liveVirus = false;
        for (IceVaccineComponentDTO item : getVaccineComponents()) {
            if (item.isLiveVirus()) {
                liveVirus = true;
                break;
            }
        }
        return liveVirus;
    }

    /**
     * Returns a comma separated string of related diseases.
     *
     * @return
     */
    public String getRelatedDiseaseList() {
        StringBuilder stringBuilder = new StringBuilder();
        List<IceDiseaseDTO> relatedDiseases = getRelatedDiseases();
        IceDiseaseComparator comparator = new IceDiseaseComparator();
        Collections.sort(relatedDiseases, comparator);
        for (IceDiseaseDTO iceDiseaseDTO : relatedDiseases) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            if (iceDiseaseDTO != null) {
                if (iceDiseaseDTO.getDiseaseCdsCodeDTO() != null) {
                    stringBuilder.append(iceDiseaseDTO.getDiseaseCdsCodeDTO().getDisplayName());
                }
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
     * Returns a comma separated string of related versions.
     *
     * @return
     */
    public String getRelatedGroupList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CdsCodeDTO item : getRelatedGroups()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(item.getDisplayName());
        }
        return stringBuilder.toString();
    }
}
