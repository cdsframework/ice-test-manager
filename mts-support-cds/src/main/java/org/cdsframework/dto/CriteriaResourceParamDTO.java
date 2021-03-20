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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.DataModelClassType;
import org.cdsframework.enumeration.GenerationSource;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@Table(databaseId = "CDS", name = "criteria_resource_param")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Criteria Resource Parameter", isListed = false)
public class CriteriaResourceParamDTO extends BaseDTO {

    private static final long serialVersionUID = 5676405845578103119L;

    public interface ByResourceId {
    }

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String paramId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = CriteriaResourceDTO.class)
    private String resourceId;
    @NotNull
    @Size(max = 512)
    private String name;
    @Size(max = 2048)
    private String description;
    @NotNull
    private DataModelClassType classType;

    /**
     * Get the value of classType
     *
     * @return the value of classType
     */
    public DataModelClassType getClassType() {
        return classType;
    }

    /**
     * Set the value of classType
     *
     * @param classType new value of classType
     */
    @PropertyListener
    public void setClassType(DataModelClassType classType) {
        this.classType = classType;
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

    /**
     * Get the value of paramId
     *
     * @return the value of paramId
     */
    public String getParamId() {
        return paramId;
    }

    /**
     * Set the value of paramId
     *
     * @param paramId new value of paramId
     */
    @PropertyListener
    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

}
