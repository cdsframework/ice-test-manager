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
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderByMapEntries({
     @OrderByMapEntry(sortFieldKey = "vaccineComponent.code", sortFieldValue = "lower(code)"),
     @OrderByMapEntry(sortFieldKey = "vaccineComponent.displayName", sortFieldValue = "lower(display_name)")
})
@OrderBy(fields = "lower(display_name)")
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = IceVaccineComponentDiseaseRelDTO.class, childQueryClass = IceVaccineComponentDiseaseRelDTO.ByVaccineComponentId.class, isAutoRetrieve = false)
})
@Table(databaseId = "ICE", name = "ice_vaccine_component", view = "vw_ice_vaccine_component")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Vaccine Component")
public class IceVaccineComponentDTO extends BaseDTO {

    private static final long serialVersionUID = -4190127241787064178L;

    public interface ByCodeId {
    }
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    @NotNull
    private String componentId;
    @ReferenceDTO(isNotFoundAllowed = false)
    @NotNull
    @Column(name = "code_id")
    private CdsCodeDTO vaccineComponent;
    private boolean liveVirus = false;

    /**
     * Get the value of componentId
     *
     * @return the value of componentId
     */
    public String getComponentId() {
        return componentId;
    }

    /**
     * Set the value of componentId
     *
     * @param componentId new value of componentId
     */
    @PropertyListener
    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    /**
     * Get the value of liveVirus
     *
     * @return the value of liveVirus
     */
    public boolean isLiveVirus() {
        return liveVirus;
    }

    /**
     * Set the value of liveVirus
     *
     * @param liveVirus new value of liveVirus
     */
    @PropertyListener
    public void setLiveVirus(boolean liveVirus) {
        this.liveVirus = liveVirus;
    }

    /**
     * Returns the vaccineComponent component vaccineComponent.
     *
     * @return the vaccineComponent component vaccineComponent.
     */
    public CdsCodeDTO getVaccineComponent() {
        return vaccineComponent;
    }

    /**
     * Sets the vaccineComponent component vaccineComponent.
     *
     * @param vaccineComponent the vaccineComponent component vaccineComponent.
     */
    @PropertyListener
    public void setVaccineComponent(CdsCodeDTO vaccineComponent) {
        this.vaccineComponent = vaccineComponent;
    }

    /**
     * Returns vaccineComponent component IceVaccineComponentDiseaseRelDTOs.
     *
     * @return the IceVaccineComponentDiseaseRelDTOs.
     */
    public List<IceVaccineComponentDiseaseRelDTO> getIceVaccineComponentDiseaseRelDTOs() {
        return getChildrenDTOs(IceVaccineComponentDiseaseRelDTO.ByVaccineComponentId.class, IceVaccineComponentDiseaseRelDTO.class);
    }

    /**
     * Returns disease immunity list.
     *
     * @return the disease immunity list.
     */
    public List<IceDiseaseDTO> getRelatedDiseases() {
        List<IceDiseaseDTO> result = new ArrayList<IceDiseaseDTO>();
        for (IceVaccineComponentDiseaseRelDTO item : getIceVaccineComponentDiseaseRelDTOs()) {
            result.add(item.getIceDiseaseDTO());
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
}
