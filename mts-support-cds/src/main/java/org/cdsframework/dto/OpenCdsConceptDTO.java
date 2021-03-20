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

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.SortColumn;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.util.comparator.OpenCdsConceptComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = OpenCdsConceptRelDTO.class, childQueryClass = OpenCdsConceptRelDTO.ByOpenCdsConceptId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = OpenCdsConceptDeploymentLogDTO.class, childQueryClass = OpenCdsConceptDeploymentLogDTO.ByCodeId.class, isAutoRetrieve = false)
})
@OrderBy(comparator = OpenCdsConceptComparator.class, fields = "lower(code)")
@Table(databaseId = "CDS", name = "opencds_concept", view = "vw_opencds_concept")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Concept Code")
@XmlRootElement(name = "Concept")
public class OpenCdsConceptDTO extends BaseDTO {

    public interface ByConceptDeterminationMethod {
    }

    public interface ByConceptCollection {
    }

    public interface ByCode {
    }

    public interface ByCodeSystemId {
    }

    public interface ByOpenCdsVaccineMapping {
    }

    public interface ByOpenCdsVaccineGroupMapping {
    }

    private static final long serialVersionUID = 8045263279127939378L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String codeId;
    @NotNull
    @SortColumn(sortFieldValue = "lower(code)")
    private String code;
    @NotNull
    @SortColumn(sortFieldValue = "lower(display_name)")
    private String displayName;
    private String description;
    @Column(name = "last_deployed", updateable = false, insertable = false)
    private Date lastDeployedDate;

    /**
     * Get the value of lastDeployedDate
     *
     * @return the value of lastDeployedDate
     */
    public Date getLastDeployedDate() {
        return lastDeployedDate;
    }

    /**
     * Set the value of lastDeployedDate
     *
     * @param lastDeployedDate new value of lastDeployedDate
     */
    public void setLastDeployedDate(Date lastDeployedDate) {
        this.lastDeployedDate = lastDeployedDate;
    }

    /**
     * Get the value of codeId
     *
     * @return the value of codeId
     */
    public String getCodeId() {
        return codeId;
    }

    /**
     * Set the value of codeId
     *
     * @param codeId new value of codeId
     */
    @PropertyListener
    public void setCodeId(String codeId) {
        this.codeId = codeId;
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
     * Get the value of displayName
     *
     * @return the value of displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Set the value of displayName
     *
     * @param displayName new value of displayName
     */
    @PropertyListener
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
     * Returns a standards displayable label for the concept.
     *
     * @return
     */
    public String getLabel() {
        String result = null;
        if (displayName != null && code != null) {
            result = String.format("%s (%s)", displayName, code);
        } else if (displayName == null && code != null) {
            result = code;
        } else if (displayName != null && code == null) {
            result = displayName;
        }
        return result;
    }

    /**
     * Get the list of OpenCdsConceptRelDTOs.
     *
     * @return
     */
    @XmlElementRef(name = "conceptRels")
    public List<OpenCdsConceptRelDTO> getOpenCdsConceptRelDTOs() {
        return getChildrenDTOs(OpenCdsConceptRelDTO.ByOpenCdsConceptId.class, OpenCdsConceptRelDTO.class);
    }

    /**
     * Get the list of OpenCdsConceptRelDTOs.
     *
     * @return
     */
    @XmlElementRef(name = "conceptDeploymentLogs")
    public List<OpenCdsConceptDeploymentLogDTO> getOpenCdsConceptDeploymentLogDTOs() {
        return getChildrenDTOs(OpenCdsConceptDeploymentLogDTO.ByCodeId.class, OpenCdsConceptDeploymentLogDTO.class);
    }
}
