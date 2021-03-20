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

import javax.validation.constraints.NotNull;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
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
@OrderBy(fields = "lower(display_name)")
@Table(databaseId = "ICE", name = "ice_vaccine_disease_rel", view = "vw_ice_vaccine_disease_rel")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Vaccine/Disease Relationship")
public class IceVaccineComponentDiseaseRelDTO extends BaseDTO {

    public interface ByVaccineComponentId {
    }
    private static final long serialVersionUID = -7215158774817797706L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String diseaseRelId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceVaccineComponentDTO.class)
    private String componentId;
    @ReferenceDTO(isNotFoundAllowed = false)
    @NotNull
    @Column(name = "disease_code_id")
    private IceDiseaseDTO iceDiseaseDTO;
    @Column(name = "code", updateable = false, insertable = false )
    private String componentCode;
    @Column(name = "display_name", updateable = false, insertable = false )
    private String componentName;

    /**
     * Get the value of componentName
     *
     * @return the value of componentName
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * Set the value of componentName
     *
     * @param componentName new value of componentName
     */
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    /**
     * Get the value of componentCode
     *
     * @return the value of componentCode
     */
    public String getComponentCode() {
        return componentCode;
    }

    /**
     * Set the value of componentCode
     *
     * @param componentCode new value of componentCode
     */
    public void setComponentCode(String componentCode) {
        this.componentCode = componentCode;
    }

    /**
     * Get the value of diseaseRelId
     *
     * @return the value of diseaseRelId
     */
    public String getDiseaseRelId() {
        return diseaseRelId;
    }

    /**
     * Set the value of diseaseRelId
     *
     * @param diseaseRelId new value of diseaseRelId
     */
    @PropertyListener
    public void setDiseaseRelId(String diseaseRelId) {
        this.diseaseRelId = diseaseRelId;
    }

    public IceDiseaseDTO getIceDiseaseDTO() {
        return iceDiseaseDTO;
    }

    @PropertyListener
    public void setIceDiseaseDTO(IceDiseaseDTO disease) {
        this.iceDiseaseDTO = disease;
    }

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

}
