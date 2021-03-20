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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.Ignore;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.RowsReturnCountBehavior;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;

/**
 * Provides a data transfer object for conveyance of a session context object.
 *
 * @author HLN Consulting, LLC
 */
@Entity
@RowsReturnCountBehavior(isDeleteCountIgnored = true)
@Table(databaseId = "MTSINT", name = "mt_session")
@JndiReference(root = "mts-ejb-core", remote = "SecurityMGR!org.cdsframework.ejb.remote.SecurityMGRRemote")
@Permission(name = "Session")
@XmlRootElement(name = "Session")
public class SessionDTO extends BaseDTO {

    public interface FindAllReverse {
    }

    public interface FindCountBySessionId {
    }

    /**
     * The serializable class UID.
     */
    private static final long serialVersionUID = -6814221430240121093L;
    /**
     * The session ID.
     */
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String sessionId;
    /**
     * The application ID.
     */
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = AppDTO.class)
    @ReferenceDTO
    @XmlElementRef(name = "application")
    @Column(name = "app_id")
    private AppDTO appDTO = new AppDTO();
    /**
     * the user's UserDTO.
     */
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = UserDTO.class)
    // Force the property to be part of json
    @JsonProperty
    @ReferenceDTO
    @XmlTransient
    @Column(name = "user_id")
    private UserDTO userDTO = new UserDTO();
    // Force the property to be part of json
    @JsonProperty    
    @XmlTransient
    @Column(name="proxy", resultSetClass = String.class)
    private boolean proxy;
    // Force the property to be part of json
    @JsonProperty    
    @ReferenceDTO
    @XmlTransient
    @Column(name = "proxy_user_id")
    private UserDTO proxyUserDTO;

    @JsonProperty    
    @XmlTransient
    @Ignore
    private Map<String, Object> propertyMap = null;    

    /**
     * Provides a no args constructor to initialize the BaseDTO superclass. Always sets the create and last modified datetimes.
     */
    public SessionDTO() {
        super();
        Date now = new Date();
        this.setTrackLastModDatetime(false);
        this.setCreateDatetime(now);
        this.setLastModDatetime(now);
        this.setTrackLastModDatetime(true);
    }

    /**
     * Returns the session ID.
     *
     * @return the session ID.
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the session ID.
     *
     * @param sessionId the session ID.
     */
    @PropertyListener
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Returns the UserDTO instance.
     *
     * @return the UserDTO instance.
     */
    public UserDTO getUserDTO() {
        return userDTO;
    }

    /**
     * Sets the UserDTO instance.
     *
     * @param userDTO the UserDTO instance.
     *
     * <p>
     * Note: only set with the middle tier context.
     */
    @PropertyListener
    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    /**
     * Returns the AppDTO instance.
     *
     * @return the AppDTO instance.
     */
    public AppDTO getAppDTO() {
        return appDTO;
    }

    /**
     * Sets the AppDTO instance.
     *
     * @param appDTO the AppDTO instance.
     *
     * <p>
     * Note: only set with the middle tier context.
     */
    @PropertyListener
    public void setAppDTO(AppDTO appDTO) {
        this.appDTO = appDTO;
    }

    /**
     * Get the value of proxy
     *
     * @return the value of proxy
     */
    public boolean isProxy() {
        return proxy;
    }

    /**
     * Set the value of proxy
     *
     * @param proxy new value of proxy
     */
    @PropertyListener
    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }

    /**
     * Get the value of proxyUserDTO
     *
     * @return the value of proxyUserDTO
     */
    public UserDTO getProxyUserDTO() {
        return proxyUserDTO;
    }

    /**
     * Set the value of proxyUserDTO
     *
     * @param proxyUserDTO new value of proxyUserDTO
     */
    @PropertyListener
    public void setProxyUserDTO(UserDTO proxyUserDTO) {
        if (this.proxyUserDTO != null && proxyUserDTO == null) {
            throw new IllegalArgumentException("Cannot replace a not null with a null!");
        }
        this.proxyUserDTO = proxyUserDTO;
    }
    
    public Map<String, Object> getPropertyMap() {
        if (propertyMap == null) {
            propertyMap = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
        }
        return propertyMap;
    }

    @PropertyListener
    public void setPropertyMap(Map<String, Object> propertyMap) {
        this.propertyMap = propertyMap;
    }

    
}
