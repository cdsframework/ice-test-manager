/**
 * The MTS support cds project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
 *
 * Copyright (C) 2016 New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * Contributions by HLN Consulting, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. You should have received a copy of the GNU Lesser
 * General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/> for more details.
 *
 * The above-named contributors (HLN Consulting, LLC) are also licensed by the
 * New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * to have (without restriction, limitation, and warranty) complete irrevocable
 * access and rights to this project.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; THE
 *
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING, BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR
 * OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see
 * https://www.hln.com/services/open-source/ or send correspondence to
 * ice@hln.com.
 */
package org.cdsframework.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElementRef;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.CriteriaResourceType;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.util.StringUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@Table(databaseId = "CDS", name = "criteria_resource")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Criteria Resource", isListed = false)
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = CriteriaResourceParamDTO.class,
            childQueryClass = CriteriaResourceParamDTO.ByResourceId.class,
            isAutoRetrieve = false)
})
public class CriteriaResourceDTO extends BaseDTO {

    private static final long serialVersionUID = 4811175878015987680L;

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String resourceId;
    @NotNull
    private CriteriaResourceType resourceType;
    @NotNull
    @Size(max = 512)
    private String name;
    @Size(max = 2048)
    private String description;

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
     * Get the value of resourceType
     *
     * @return the value of resourceType
     */
    public CriteriaResourceType getResourceType() {
        return resourceType;
    }

    /**
     * Set the value of resourceType
     *
     * @param resourceType new value of resourceType
     */
    @PropertyListener
    public void setResourceType(CriteriaResourceType resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * Get the value of resourceId
     *
     * @return the value of resourceId
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * Set the value of resourceId
     *
     * @param resourceId new value of resourceId
     */
    @PropertyListener
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @XmlElementRef(name = "criteriaResourceParams")
    public List<CriteriaResourceParamDTO> getCriteriaResourceParamDTOs() {
        return getChildrenDTOs(CriteriaResourceParamDTO.ByResourceId.class, CriteriaResourceParamDTO.class);
    }

    public String getLabel() {
        String result;
        List<String> paramList = new ArrayList<String>();
        for (CriteriaResourceParamDTO param : getCriteriaResourceParamDTOs()) {
            paramList.add(param.getName());
        }
        switch (getResourceType()) {
            case Function:
                result = String.format("%s (", getName());
                break;
            case Global:
                result = String.format("global: %s", getName());
                break;
            case Operator:
                result = String.format("operator: (%s)", StringUtils.getStringFromArray(paramList, ", "));
                break;
            default:
                result = "undefined!";
        }

        return result;
    }
}
