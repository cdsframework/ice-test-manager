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
import javax.xml.bind.annotation.XmlRootElement;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.EnumAccess;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.enumeration.PermissionType;
import org.cdsframework.util.comparator.SecurityPermissionComparator;

/**
 * Provides a data transfer object for conveyance of a security permission DTO context object.
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = SecurityPermissionComparator.class, fields = "lower(permission_object), permission_type")
@Table(databaseId = "MTS", name = "mt_security_scheme_perm_map")
@JndiReference(root = "mts-ejb-core")
@Permission(name = "Security Permission")
@XmlRootElement(name = "SecurityPermission")
public class SecurityPermissionDTO extends BaseDTO {

    public interface BySchemeId {
    }
    /**
     * The serializable class UID.
     */
    private static final long serialVersionUID = 5590737112321380049L;
    /**
     * The scheme ID.
     */
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String mapId;
    @Column(name = "cascade_perm", resultSetClass = String.class)
    private boolean cascade;
    @Column(name = "deny", resultSetClass = String.class)
    private boolean deny;
    /**
     * The permission type.
     */
    @EnumAccess(getter = "getCode", setter = "codeOf")
    @GeneratedValue(source = GenerationSource.CHECK_CONSTRAINT, sourceClass = PermissionType.class)
    @NotNull
    private PermissionType permissionType;
    /**
     * The permission object.
     */
    @Column(name = "permission_object")
    @NotNull
    private String permissionClass;
    /**
     * The scheme ID.
     */
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = SecuritySchemeDTO.class)
    private String schemeId;

    /**
     * Provides a constructor to initialize an instance for the @Permission annotation.
     *
     * @param permissionClass
     * @param permissionType
     * @param cascade
     * @param deny
     */
    public SecurityPermissionDTO(String permissionClass, PermissionType permissionType, boolean cascade, boolean deny) {
        super();
        this.permissionClass = permissionClass;
        this.permissionType = permissionType;
        this.cascade = cascade;
        this.deny = deny;
    }

    /**
     * Provides a no args constructor to initialize the BaseDTO superclass.
     */
    public SecurityPermissionDTO() {
        super();
    }

    /**
     * Returns the Map ID.
     *
     * @return the Map ID.
     */
    public String getMapId() {
        return mapId;
    }

    /**
     * Sets the Map ID.
     *
     * @param mapId the Map ID.
     */
    @PropertyListener
    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    /**
     * Get the value of cascade
     *
     * @return the value of cascade
     */
    public boolean isCascade() {
        return cascade;
    }

    /**
     * Set the value of cascade
     *
     * @param cascade new value of cascade
     */
    @PropertyListener
    public void setCascade(boolean cascade) {
        this.cascade = cascade;
    }

    /**
     * Get the value of deny
     *
     * @return the value of deny
     */
    public boolean isDeny() {
        return deny;
    }

    /**
     * Set the value of deny
     *
     * @param deny new value of deny
     */
    @PropertyListener
    public void setDeny(boolean deny) {
        this.deny = deny;
    }

    /**
     * Returns the permission object.
     *
     * @return the permission object.
     */
    public String getPermissionClass() {
        return permissionClass;
    }

    /**
     * Sets the permission object.
     *
     * @param permissionClass the permission object.
     */
    @PropertyListener
    public void setPermissionClass(String permissionClass) {
        this.permissionClass = permissionClass;
    }

    /**
     * Returns the permission type.
     *
     * @return the permission type.
     */
    public PermissionType getPermissionType() {
        return permissionType;
    }

    /**
     * Sets the permission type.
     *
     * @param permissionType the permission type.
     */
    @PropertyListener
    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    /**
     * Returns the scheme ID.
     *
     * @return the scheme ID.
     */
    public String getSchemeId() {
        return schemeId;
    }

    /**
     * Sets the scheme ID.
     *
     * @param schemeId the scheme ID.
     */
    @PropertyListener
    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }
}
