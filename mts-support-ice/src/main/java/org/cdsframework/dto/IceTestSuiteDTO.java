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
import org.cdsframework.annotation.ReferenceDTO;
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
    @ParentChildRelationship(childDtoClass = IceTestGroupDTO.class, childQueryClass = IceTestGroupDTO.ByTestSuiteId.class, isAutoRetrieve = false)
})
@OrderBy(fields = "lower(name)")
@Table(databaseId = "ICE", name = "ice_test_suite", view = "vw_ice_test_suite")
@JndiReference(root = "mts-ejb-ice", remote = "IceTestSuiteMGR")
@Permission(name = "ICE Test Suite")
public class IceTestSuiteDTO extends BaseDTO {

    public interface ImportTests {
    }

    public interface RunTests {
    }

    public interface XMLByTestId {
    }

    public interface UpdateLastMod {
    }
    private static final long serialVersionUID = -5279820215869781769L;
    /**
     * The suite ID.
     */
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String suiteId;
    @NotNull
    @Size(max = 256)
    private String name;
    @Size(max = 33554432)
    private String notes;
    @ReferenceDTO(isNotFoundAllowed = false)
    @Column(name = "version_id")
    private CdsVersionDTO cdsVersionDTO;
    @Column(name = "num_of_groups", updateable = false, insertable = false)
    private long numOfGroups;
    @Column(name = "num_of_tests", updateable = false, insertable = false)
    private long numOfTests;

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
     * Get the value of numOfGroups
     *
     * @return the value of numOfGroups
     */
    public long getNumOfGroups() {
        return numOfGroups;
    }

    /**
     * Set the value of numOfGroups
     *
     * @param numOfGroups new value of numOfGroups
     */
    public void setNumOfGroups(long numOfGroups) {
        this.numOfGroups = numOfGroups;
    }

    /**
     * Get the value of cdsVersionDTO
     *
     * @return the value of cdsVersionDTO
     */
    public CdsVersionDTO getCdsVersionDTO() {
        return cdsVersionDTO;
    }

    /**
     * Set the value of cdsVersionDTO
     *
     * @param cdsVersionDTO new value of cdsVersionDTO
     */
    @PropertyListener
    public void setCdsVersionDTO(CdsVersionDTO cdsVersionDTO) {
        System.out.println("setCdsVersionDTO - existing cdsVersionDTO=" + this.cdsVersionDTO);
        System.out.println("setCdsVersionDTO - new cdsVersionDTO=" + cdsVersionDTO);
        this.cdsVersionDTO = cdsVersionDTO;
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
     * Get the value of notes
     *
     * @return the value of notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Set the value of notes
     *
     * @param notes new value of notes
     */
    @PropertyListener
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<IceTestGroupDTO> getIceTestGroupDTOs() {
        return (List) this.getChildrenDTOs(IceTestGroupDTO.ByTestSuiteId.class);
    }
}
