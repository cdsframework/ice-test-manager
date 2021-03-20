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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.EnumAccess;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.Ignore;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.enumeration.NamePrefix;
import org.cdsframework.enumeration.NameSuffix;
import org.cdsframework.util.comparator.UserComparator;
import org.cdsframework.validation.Email;

/**
 * Provides a data transfer object for conveyance of a user context object.
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = UserComparator.class, fields = "lower(username)")
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = UserPreferenceDTO.class, childQueryClass = UserPreferenceDTO.ByUserId.class, isAutoRetrieve = true),
    @ParentChildRelationship(childDtoClass = UserSecurityMapDTO.class, childQueryClass = UserSecurityMapDTO.ByUserId.class, isAutoRetrieve = false)
})
@Table(databaseId = "MTS", name = "mt_user")
@JndiReference(root = "mts-ejb-core", remote = "UserMGR")
@Permission(name = "User")
@XmlRootElement(name = "User")
public class UserDTO extends BaseDTO {

    public interface DtoByUsername {
    }

    public interface UpdatePasswordHash {
    }

    public interface FailedLoginAttemptsByUserId {
    }

    public interface ByUserId {
    }

    public interface FindCatProxyUser {
    }
    /**
     * The serializable class UID.
     */
    private static final long serialVersionUID = -7132921975542727836L;
    /**
     * The user ID.
     */
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String userId;
    /**
     * The user name.
     */
    @NotNull
    @Size(min = 2, max = 64)
    private String username;
    @EnumAccess(getter = "getCode", setter = "codeOf")
    private NamePrefix prefix;
    @NotNull
    @Size(min = 1, max = 40)
    private String firstName;
    @Size(max = 40)
    private String middleName;
    @NotNull
    @Size(min = 1, max = 40)
    private String lastName;
    @EnumAccess(getter = "getCode", setter = "codeOf")
    private NameSuffix suffix;
    @NotNull
    @Email
    private String email;
    /**
     * The failed login attempts.
     */
    private int failedLoginAttempts;
    /**
     * The user expiration date.
     */
    private Date expirationDate;
    /**
     * The disabled state flag.
     */
    @Column(name = "disabled", resultSetClass = String.class)
    private boolean disabled;
    /**
     * The change password state flag.
     */
    @Column(name = "change_password", resultSetClass = String.class)
    private boolean changePassword;
    /**
     * The password hash.
     */
    // Force the property to be part of json
    @JsonProperty    
    @Size(max = 128)
    @XmlTransient
    private String passwordHash;
    /**
     * The proxy application ID.
     */
    // Force the property to be part of json
    @JsonProperty    
    @XmlTransient
    private String proxyAppId;
    /**
     * The state of the app proxy flag.
     */
    // Force the property to be part of json
    @JsonProperty    
    @XmlTransient
    @Column(name = "app_proxy_user", resultSetClass = String.class)
    private boolean appProxyUser;
    /**
     * The state of the proxied user flag.
     */
    // Force the property to be part of json
    @JsonProperty    
    @XmlTransient
    @Ignore
    private boolean proxiedUser = false;
    @Ignore
    private String password;
    @Ignore
    private String passwordConfirm;

    @JsonProperty    
    @XmlTransient
    @Ignore
    private Map<String, Object> propertyMap = null;
    @Size(max = 14)
    private String phone;

    /**
     * Get the value of phone
     *
     * @return the value of phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the value of phone
     *
     * @param phone new value of phone
     */
    @PropertyListener
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the email address.
     *
     * @return the email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email the email address.
     */
    @PropertyListener
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the change password state flag.
     *
     * @return the change password state flag.
     */
    public boolean isChangePassword() {
        return changePassword;
    }

    /**
     * Sets the change password state flag.
     *
     * @param changePassword the change password state flag.
     */
    @PropertyListener
    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    /**
     * Returns the disabled state flag.
     *
     * @return the disabled state flag.
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets the disabled state flag.
     *
     * @param disabled the disabled state flag.
     */
    @PropertyListener
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * Returns the expiration date.
     *
     * @return the expiration date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date.
     *
     * @param expirationDate the expiration date.
     */
    @PropertyListener
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Returns the expiration state.
     *
     * @return the expiration state.
     */
    public boolean isExpired() {
        return (getExpirationDate() != null) ? (getExpirationDate().getTime() < new Date().getTime()) : false;
    }

    /**
     * Returns the proxy app id.
     *
     * @return the proxy app id.
     */
    public String getProxyAppId() {
        return proxyAppId;
    }

    /**
     * Sets the proxy app id.
     *
     * @param proxyAppId the proxy app id.
     */
    @PropertyListener
    public void setProxyAppId(String proxyAppId) {
        this.proxyAppId = proxyAppId;
    }

    /**
     * Returns the state of the appProxyUser flag.
     *
     * @return the state of the appProxyUser flag.
     */
    public boolean isAppProxyUser() {
        return appProxyUser;
    }

    /*
     * Sets the state of the appProxyUser flag.
     *
     * @param appProxyUser the state of the appProxyUser flag.
     */
    @PropertyListener
    public void setAppProxyUser(boolean appProxyUser) {
        this.appProxyUser = appProxyUser;
    }

    /**
     * Returns the state of the proxied user flag.
     *
     * @return the state of the proxied user flag.
     */
    public boolean isProxiedUser() {
        return proxiedUser;
    }

    /**
     * Sets the state of the proxied user flag.
     *
     * @param proxiedUser the state of the proxied user flag.
     */
    @PropertyListener
    public void setProxiedUser(boolean proxiedUser) {
        this.proxiedUser = proxiedUser;
    }

    /**
     * Returns the failed login attempt count.
     *
     * @return the failed login attempt count.
     */
    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    /**
     * Sets the failed login attempt count.
     *
     * @param failedLoginAttempts the failed login attempt count.
     */
    @PropertyListener
    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    /**
     * Returns the first name.
     *
     * @return the first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the first name.
     */
    @PropertyListener
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name.
     *
     * @return the last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the last name.
     */
    @PropertyListener
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the middle name.
     *
     * @return the middle name.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the middle name.
     *
     * @param middleName the middle name.
     */
    @PropertyListener
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Returns the name prefix.
     *
     * @return the name prefix.
     */
    public NamePrefix getPrefix() {
        return prefix;
    }

    /**
     * Sets the name prefix.
     *
     * @param prefix the name prefix.
     */
    @PropertyListener
    public void setPrefix(NamePrefix prefix) {
        this.prefix = prefix;
    }

    /**
     * Returns the name suffix.
     *
     * @return the name suffix.
     */
    public NameSuffix getSuffix() {
        return suffix;
    }

    /**
     * Sets the name suffix.
     *
     * @param suffix the name suffix.
     */
    @PropertyListener
    public void setSuffix(NameSuffix suffix) {
        this.suffix = suffix;
    }

    /**
     * Returns the password hash.
     *
     * @return the password hash.
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the password hash.
     *
     * @param passwordHash the password hash.
     */
    @PropertyListener
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    /**
     * Returns the user name.
     *
     * @return the user name.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user name.
     *
     * @param username the user name.
     */
    @PropertyListener
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the value of passwordConfirm
     *
     * @return the value of passwordConfirm
     */
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    /**
     * Set the value of passwordConfirm
     *
     * @param passwordConfirm new value of passwordConfirm
     */
    @PropertyListener
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    @PropertyListener
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * return the list of user preferences
     *
     * @return
     */
    @XmlElementRef(name = "userPreferences")
    public List<UserPreferenceDTO> getUserPreferenceDTOs() {
        return getChildrenDTOs(UserPreferenceDTO.ByUserId.class, UserPreferenceDTO.class);
    }

    /**
     * return the Map of user preferences
     *
     * @return
     */
    public Map<String, UserPreferenceDTO> getUserPreferenceDTOMap() {
        Map<String, UserPreferenceDTO> userPreferenceDTOMap = new HashMap<String, UserPreferenceDTO>();
        for (UserPreferenceDTO userPreferenceDTO : getUserPreferenceDTOs()) {
            userPreferenceDTOMap.put(userPreferenceDTO.getName(), userPreferenceDTO);
        }
        return userPreferenceDTOMap;
    }

    /**
     * get a single user preference
     *
     * @param preference
     * @return
     */
    public String getUserPreference(String preference) {
        String result = null;
        UserPreferenceDTO userPreferenceDTO = getUserPreferenceDTOMap().get(preference);
        if (userPreferenceDTO != null) {
            result = userPreferenceDTO.getValue();
        }
        return result;
    }

    /**
     * return the complete list of security schemes the user is subscribed to - either directly or indirectly
     *
     * @return
     */
    public List<SecuritySchemeDTO> getSecuritySchemeDTOs() {
        List<SecuritySchemeDTO> securitySchemeDTOs = new ArrayList<SecuritySchemeDTO>();
        for (UserSecurityMapDTO userSecurityMapDTO : getChildrenDTOs(UserSecurityMapDTO.ByUserId.class, UserSecurityMapDTO.class)) {
            if (userSecurityMapDTO != null) {
                SecuritySchemeDTO securitySchemeDTO = userSecurityMapDTO.getSecuritySchemeDTO();
                if (securitySchemeDTO != null) {
                    securitySchemeDTOs.add(securitySchemeDTO);
                    securitySchemeDTOs.addAll(collectSecuritySchemeDTOs(securitySchemeDTO));
                }
            }
        }
        return securitySchemeDTOs;
    }

    /**
     * return the security schemes the user is subscribed to indirectly via a direct securitySchemeDTO subscription
     *
     * @param securitySchemeDTO
     * @return
     */
    private List<SecuritySchemeDTO> collectSecuritySchemeDTOs(SecuritySchemeDTO securitySchemeDTO) {
        List<SecuritySchemeDTO> result = new ArrayList<SecuritySchemeDTO>();
        if (securitySchemeDTO != null) {
            //System.out.println("round and round we go: " + securitySchemeDTO.getSchemeName());
            for (SecuritySchemeRelMapDTO item : securitySchemeDTO.getSecuritySchemeRelMapDTOs()) {
                if (item != null) {
                    SecuritySchemeDTO relatedSecuritySchemeDTO = item.getRelatedSecuritySchemeDTO();
                    if (relatedSecuritySchemeDTO != null) {
                        //System.out.println("Found relatedSecuritySchemeDTO: " + relatedSecuritySchemeDTO.getSchemeName());
                        if (!result.contains(relatedSecuritySchemeDTO)) {
                            result.add(relatedSecuritySchemeDTO);
                        }
                        List<SecuritySchemeRelMapDTO> securitySchemeRelMapDTOs = relatedSecuritySchemeDTO.getSecuritySchemeRelMapDTOs();
                        //System.out.println("Found securitySchemeRelMapDTOs: " + securitySchemeRelMapDTOs.size());
                        if (securitySchemeRelMapDTOs != null) {
                            for (SecuritySchemeRelMapDTO entry : securitySchemeRelMapDTOs) {
                                if (entry != null) {
                                    result.addAll(collectSecuritySchemeDTOs(entry.getRelatedSecuritySchemeDTO()));
                                } else {
                                    System.out.println("entry is null!");
                                }
                            }
                        } else {
                            System.out.println("securitySchemeRelMapDTOs is null!");
                        }
                    } else {
                        System.out.println("relatedSecuritySchemeDTO is null!");
                    }
                } else {
                    System.out.println("item is null!");
                }
            }
        } else {
            System.out.println("securitySchemeDTO is null!");
        }
        return result;
    }

    public void setPropertyMap(Map<String, Object> propertyMap) {
        this.propertyMap = propertyMap;
    }
    
    public Map<String, Object> getPropertyMap() {
        if (propertyMap == null) {
            propertyMap = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
        }
        return propertyMap;
    }
//
//    /**
//     * return just the list of schemeIds a user is directly or indirectly subscribed to
//     *
//     * @return
//     */
//    public List<String> getSecuritySchemeIds() {
//        List<String> securitySchemeIds = new ArrayList<String>();
//        for (SecuritySchemeDTO item : getSecuritySchemeDTOs()) {
//            securitySchemeIds.add(item.getSchemeId());
//        }
//        return securitySchemeIds;
//    }
    
    
    @XmlElementRef(name = "userSecurityMaps")
    public List<UserSecurityMapDTO> getUserSecurityMapDTOs() {
        return (List) this.getChildrenDTOs(UserSecurityMapDTO.ByUserId.class);
    }    
}
