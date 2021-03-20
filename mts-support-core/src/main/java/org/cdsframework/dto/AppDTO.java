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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.util.comparator.AppComparator;

/**
 * Provides a data transfer object for conveyance of an application context object.
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = AppComparator.class, fields = "lower(app_name)")
@Table(databaseId = "MTS", name = "mt_app")
@JndiReference(root = "mts-ejb-core")
@Permission(name = "Application")
@XmlRootElement(name = "Application")
public class AppDTO extends BaseDTO {

    public interface DtoByAppName {
    }

    public interface AllAppNames {
    }

    public interface AppNameByAppId {
    }
    /**
     * The serializable class UID.
     */
    private static final long serialVersionUID = -11727787267963729L;
    /**
     * The application ID of the component.
     *
     * @see #getAppId()
     * @see #setAppId(java.lang.String)
     */
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String appId;
    /**
     * The application name of the component.
     *
     * @see #getAppName()
     * @see #setAppName(java.lang.String)
     */
    @NotNull
    private String appName;
    /**
     * The application description of the component.
     *
     * @see #getDescription()
     * @see #setDescription(java.lang.String)
     */
    private String description;
    /**
     * The UserDTO find class in the case that this app has a proxy user table.
     */
    @JsonProperty    
    @XmlTransient
    private String proxyUserFindClass;
    /**
     * The state of the proxy user table flag.
     */
    @JsonProperty    
    @XmlTransient
    @Column(name = "proxy_user_table", resultSetClass = String.class)
    private boolean proxyUserTable;

    /**
     * Provides a no args constructor to initialize the BaseDTO superclass.
     */
    public AppDTO() {
        super();
    }

    /**
     * Returns the application ID of the component.
     *
     * @return the application ID of the component.
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Sets the application ID of the component.
     *
     * @param appId the application ID of the component.
     */
    @PropertyListener
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * Returns the application name of the component.
     *
     * @return the application name of the component.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Sets the application name of the component.
     *
     * @param appName the application name of the component.
     */
    @PropertyListener
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Returns the application description of the component.
     *
     * @return the application description of the component.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the application description of the component.
     *
     * @param description the application description of the component.
     */
    @PropertyListener
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the proxy user table find class.
     *
     * @return the proxy user table find class.
     */
    public String getProxyUserFindClass() {
        return proxyUserFindClass;
    }

    /**
     * Sets the proxy user table find class.
     *
     * @param proxyUserFindClass the proxy user table find class.
     */
    @PropertyListener
    public void setProxyUserFindClass(String proxyUserFindClass) {
        this.proxyUserFindClass = proxyUserFindClass;
    }

    /**
     * Returns the state of the proxy user table flag.
     *
     * @return the state of the proxy user table flag.
     */
    public boolean isProxyUserTable() {
        return proxyUserTable;
    }

    /**
     * Sets the state of the proxy user table flag.
     *
     * @param proxyUserTable the state of the proxy user table flag.
     */
    @PropertyListener
    public void setProxyUserTable(boolean proxyUserTable) {
        this.proxyUserTable = proxyUserTable;
    }
}
