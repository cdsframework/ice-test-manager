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
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;

/**
 * Provides a data transfer object for a user security scheme mapping object.
 *
 * @author HLN Consulting, LLC
 */
@Entity
@Table(databaseId = "MTS", name = "mt_user_security_map")
@JndiReference(root = "mts-ejb-core")
@Permission(name = "User Security Scheme Map")
public class UserSecurityMapDTO extends BaseDTO {

    public interface ByUserId {
    }

    public interface BySchemeId {
    }

    private static final long serialVersionUID = -8162739933585585979L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String mapId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = UserDTO.class)
    private String userId;
    @Column(name = "scheme_id")
    @ReferenceDTO
    @NotNull
    private SecuritySchemeDTO securitySchemeDTO = new SecuritySchemeDTO();

    /**
     * Provides a no args constructor to initialize the BaseDTO superclass.
     */
    public UserSecurityMapDTO() {
        super();
    }

    /**
     * Get the value of mapId
     *
     * @return the value of mapId
     */
    public String getMapId() {
        return mapId;
    }

    /**
     * Set the value of mapId
     *
     * @param mapId new value of mapId
     */
    @PropertyListener
    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    /**
     * Returns the user id.
     *
     * @return the user id.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the user id.
     */
    @PropertyListener
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the scheme DTO.
     *
     * @return the scheme DTO.
     */
    public SecuritySchemeDTO getSecuritySchemeDTO() {
        return securitySchemeDTO;
    }

    /**
     * Sets the scheme DTO.
     *
     * @param securitySchemeDTO the scheme DTO.
     */
    @PropertyListener
    public void setSecuritySchemeDTO(SecuritySchemeDTO securitySchemeDTO) {
        this.securitySchemeDTO = securitySchemeDTO;
    }
}
