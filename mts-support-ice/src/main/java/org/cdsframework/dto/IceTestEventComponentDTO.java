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

import java.util.LinkedList;
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
import org.cdsframework.util.StringUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = IceTestEvaluationDTO.class, childQueryClass = IceTestEvaluationDTO.ByEventComponentId.class, isAutoRetrieve = true)
})
@Table(databaseId = "ICE", name = "ice_test_event_component", view = "vw_ice_test_event_component")
@OrderBy(fields = "component_id")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Test Event Component")
public class IceTestEventComponentDTO extends BaseDTO {

    public interface ByEventId {
    }
    private static final long serialVersionUID = -7735848339910730073L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String eventComponentId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceTestEventDTO.class)
    private String eventId;
    @NotNull
    @ReferenceDTO
    @Column(name = "component_id")
    private IceVaccineComponentDTO iceVaccineComponentDTO;
    @ReferenceDTO
    @Column(name = "group_id")
    private IceVaccineGroupDTO iceVaccineGroupDTO;
    @ReferenceDTO
    @Column(name = "eval_value_id")
    private CdsCodeDTO evaluationValue;
    @Ignore
    private IceTestEventDTO iceTestEventDTO;
    @Column(name = "eval_reason", updateable = false, insertable = false)
    private String evaluationReason;

    /**
     * Get the value of evaluationReason
     *
     * @return the value of evaluationReason
     */
    public String getEvaluationReason() {
        return evaluationReason;
    }

    /**
     * Set the value of evaluationReason
     *
     * @param evaluationReason new value of evaluationReason
     */
    public void setEvaluationReason(String evaluationReason) {
        this.evaluationReason = evaluationReason;
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
     * Get the value of eventComponentId
     *
     * @return the value of eventComponentId
     */
    public String getEventComponentId() {
        return eventComponentId;
    }

    /**
     * Set the value of eventComponentId
     *
     * @param eventComponentId new value of eventComponentId
     */
    @PropertyListener
    public void setEventComponentId(String eventComponentId) {
        this.eventComponentId = eventComponentId;
    }

    /**
     * Get the value of eventId
     *
     * @return the value of eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Set the value of eventId
     *
     * @param eventId new value of eventId
     */
    @PropertyListener
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * Get the value of iceVaccineComponentDTO
     *
     * @return the value of iceVaccineComponentDTO
     */
    public IceVaccineComponentDTO getIceVaccineComponentDTO() {
        return iceVaccineComponentDTO;
    }

    /**
     * Set the value of iceVaccineComponentDTO
     *
     * @param iceVaccineComponentDTO new value of iceVaccineComponentDTO
     */
    @PropertyListener
    public void setIceVaccineComponentDTO(IceVaccineComponentDTO iceVaccineComponentDTO) {
        this.iceVaccineComponentDTO = iceVaccineComponentDTO;
    }

    /**
     * Get the value of evaluationValue
     *
     * @return the value of evaluationValue
     */
    public CdsCodeDTO getEvaluationValue() {
        return evaluationValue;
    }

    /**
     * Set the value of evaluationValue
     *
     * @param evaluationValue new value of evaluationValue
     */
    @PropertyListener
    public void setEvaluationValue(CdsCodeDTO evaluationValue) {
        this.evaluationValue = evaluationValue;
    }

    public List<IceTestEvaluationDTO> getIceTestEvaluationDTOs() {
        return (List) this.getChildrenDTOs(IceTestEvaluationDTO.ByEventComponentId.class);
    }

    /**
     * Get the value of iceTestEventDTO
     *
     * @return the value of iceTestEventDTO
     */
    public IceTestEventDTO getIceTestEventDTO() {
        return iceTestEventDTO;
    }

    /**
     * Set the value of iceTestEventDTO
     *
     * @param iceTestEventDTO new value of iceTestEventDTO
     */
    public void setIceTestEventDTO(IceTestEventDTO iceTestEventDTO) {
        this.iceTestEventDTO = iceTestEventDTO;
    }

    public String getComponentInterpretations() {
        List<String> interpretations = new LinkedList<String>();
        for (IceTestEvaluationDTO interpretation : getIceTestEvaluationDTOs()) {
            interpretations.add(interpretation.getEvaluationInterpretation().getDisplayName());
        }
        return StringUtils.getStringFromArray(interpretations, ", ");
    }
}
