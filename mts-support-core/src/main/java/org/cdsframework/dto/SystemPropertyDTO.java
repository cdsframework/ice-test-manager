/**
 * The MTS support core project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
import javax.xml.bind.annotation.XmlRootElement;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.OrderByMapEntries;
import org.cdsframework.annotation.OrderByMapEntry;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.util.comparator.SystemPropertyComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderByMapEntries({
     @OrderByMapEntry(sortFieldKey = "name", sortFieldValue = "lower(name)"),
     @OrderByMapEntry(sortFieldKey = "group", sortFieldValue = "lower(property_group)")
})
@OrderBy(comparator=SystemPropertyComparator.class, fields = "lower(name), lower(property_group), scope")
@Table(databaseId = "MTS", name = "system_property")
@JndiReference(root = "mts-ejb-core")
@Permission(name = "System Property")
@XmlRootElement(name = "SystemProperty")
public class SystemPropertyDTO extends BaseDTO {

    public interface ByGroup {
    }

    public interface ByScope {
    }

    public interface ByNameScope {
    }

    public interface ExternallyAvailable {
    }
    private static final long serialVersionUID = -2213598162080936429L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String propertyId;
    @NotNull
    @Size(max = 128)
    private String name;
    @NotNull
    @Size(max = 512)
    private String type;
    @Size(max = 128)
    @Column(name = "property_group")
    private String group;
    @Size(max = 128)
    @NotNull
    private String scope;
    @Size(max = 1024)
    private String value;
    private boolean obscure;
    private boolean mtsOnly = true;

    /**
     * Get the value of mtsOnly
     *
     * @return the value of mtsOnly
     */
    public boolean isMtsOnly() {
        return mtsOnly;
    }

    /**
     * Set the value of mtsOnly
     *
     * @param mtsOnly new value of mtsOnly
     */
    @PropertyListener
    public void setMtsOnly(boolean mtsOnly) {
        this.mtsOnly = mtsOnly;
    }

    /**
     * Get the value of propertyId
     *
     * @return the value of propertyId
     */
    public String getPropertyId() {
        return propertyId;
    }

    /**
     * Set the value of propertyId
     *
     * @param propertyId new value of propertyId
     */
    @PropertyListener
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * Get the value of obscure
     *
     * @return the value of obscure
     */
    public boolean isObscure() {
        return obscure;
    }

    /**
     * Set the value of obscure
     *
     * @param obscure new value of obscure
     */
    @PropertyListener
    public void setObscure(boolean obscure) {
        this.obscure = obscure;
    }

    /**
     * Get the value of scope
     *
     * @return the value of scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * Set the value of scope
     *
     * @param scope new value of scope
     */
    @PropertyListener
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Get the value of group
     *
     * @return the value of group
     */
    public String getGroup() {
        return group;
    }

    /**
     * Set the value of group
     *
     * @param group new value of group
     */
    @PropertyListener
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    @PropertyListener
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value of value
     *
     * @param value new value of value
     */
    @PropertyListener
    public void setValue(String value) {
        this.value = value;
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
}
