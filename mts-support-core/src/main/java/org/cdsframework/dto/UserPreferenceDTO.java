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
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@Table(databaseId = "MTS", name = "mt_user_preference")
@JndiReference(root = "mts-ejb-core")
@Permission(name = "User Preference")
public class UserPreferenceDTO extends BaseDTO {

    public interface ByUserId {
    }
    private static final long serialVersionUID = -6025880284247578696L;
    /**
     * The preference ID.
     */
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String preferenceId;
    /**
     * The user ID.
     */
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = UserDTO.class)
    @NotNull
    private String userId;
    @NotNull
    private String name;
    private String value;
    @NotNull
    private String type;
    @NotNull
    private boolean sessionPreference;
    private boolean sessionPersistent;
    private String defaultValue;
    private boolean userEditable = false;

    /**
     * Get the value of userEditable
     *
     * @return the value of userEditable
     */
    public boolean isUserEditable() {
        return userEditable;
    }

    /**
     * Set the value of userEditable
     *
     * @param userEditable new value of userEditable
     */
    @PropertyListener
    public void setUserEditable(boolean userEditable) {
        this.userEditable = userEditable;
    }

    /**
     * Get the value of preferenceId
     *
     * @return the value of preferenceId
     */
    public String getPreferenceId() {
        return preferenceId;
    }

    /**
     * Set the value of preferenceId
     *
     * @param preferenceId new value of preferenceId
     */
    @PropertyListener
    public void setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
    }

    /**
     * Get the value of defaultValue
     *
     * @return the value of defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Set the value of defaultValue
     *
     * @param defaultValue new value of defaultValue
     */
    @PropertyListener
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Get the value of sessionPersistent
     *
     * @return the value of sessionPersistent
     */
    public boolean isSessionPersistent() {
        return sessionPersistent;
    }

    /**
     * Set the value of sessionPersistent
     *
     * @param sessionPersistent new value of sessionPersistent
     */
    @PropertyListener
    public void setSessionPersistent(boolean sessionPersistent) {
        this.sessionPersistent = sessionPersistent;
    }

    /**
     * Get the value of sessionPreference
     *
     * @return the value of sessionPreference
     */
    public boolean isSessionPreference() {
        return sessionPreference;
    }

    /**
     * Set the value of sessionPreference
     *
     * @param sessionPreference new value of sessionPreference
     */
    @PropertyListener
    public void setSessionPreference(boolean sessionPreference) {
        this.sessionPreference = sessionPreference;
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

    /**
     * Returns the user ID.
     *
     * @return the user ID.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the user ID.
     */
    @PropertyListener
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
