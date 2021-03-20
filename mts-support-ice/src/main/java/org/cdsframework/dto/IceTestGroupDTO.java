/**
 * The MTS support ice project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
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

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = IceTestDTO.class, childQueryClass = IceTestDTO.ByTestGroupId.class, isAutoRetrieve = false)
})
@OrderBy(fields = "lower(name)")
@Table(databaseId = "ICE", name = "ice_test_group", view = "vw_ice_test_group")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Test Group")
public class IceTestGroupDTO extends BaseDTO {

    public interface ByTestSuiteId {
    }

    public interface ByTestSuiteIdName {
    }

    public interface UpdateLastMod {
    }

    public interface MaxLastModDatetimeByTestSuiteId {
    }

    public interface LastModIdByLastModDatetime {
    }
    private static final long serialVersionUID = -2980436486430438195L;
    /**
     * The group ID.
     */
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String groupId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceTestSuiteDTO.class)
    private String suiteId;
    @NotNull
    @Size(max = 256)
    private String name;
    @Size(max = 3072)
    private String description;
    @Column(name = "ignore_test", resultSetClass = String.class)
    private boolean ignore;
    @Column(name = "num_of_tests", updateable = false, insertable = false)
    private long numOfTests;
    @Column(name = "suite_name", updateable = false, insertable = false)
    private String suiteName;
    @Column(name = "version_id", updateable = false, insertable = false)
    private String versionId;

    /**
     * Get the value of versionId
     *
     * @return the value of versionId
     */
    public String getVersionId() {
        return versionId;
    }

    /**
     * Set the value of versionId
     *
     * @param versionId new value of versionId
     */
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    /**
     * Get the value of suiteName
     *
     * @return the value of suiteName
     */
    public String getSuiteName() {
        return suiteName;
    }

    /**
     * Set the value of suiteName
     *
     * @param suiteName new value of suiteName
     */
    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    /**
     * Get the value of numOfTests
     *
     * @return the value of numOfTests
     */
    public long getNumOfTests() {
        return numOfTests;
    }

    /**
     * Set the value of numOfTests
     *
     * @param numOfTests new value of numOfTests
     */
    public void setNumOfTests(long numOfTests) {
        this.numOfTests = numOfTests;
    }

    /**
     * Get the value of ignore
     *
     * @return the value of ignore
     */
    public boolean isIgnore() {
        return ignore;
    }

    /**
     * Set the value of ignore
     *
     * @param ignore new value of ignore
     */
    @PropertyListener
    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    /**
     * Get the value of groupId
     *
     * @return the value of groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Set the value of groupId
     *
     * @param groupId new value of groupId
     */
    @PropertyListener
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Get the value of suiteId
     *
     * @return the value of suiteId
     */
    public String getSuiteId() {
        return suiteId;
    }

    /**
     * Set the value of suiteId
     *
     * @param suiteId new value of suiteId
     */
    @PropertyListener
    public void setSuiteId(String suiteId) {
        this.suiteId = suiteId;
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

    public List<IceTestDTO> getIceTestDTOs() {
        return this.getChildrenDTOs(IceTestDTO.ByTestGroupId.class, IceTestDTO.class);
    }
}
