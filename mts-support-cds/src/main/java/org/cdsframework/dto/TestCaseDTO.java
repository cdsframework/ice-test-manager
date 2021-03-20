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

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseTestCaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.interfaces.OffsetBasedEventType;
import org.cdsframework.interfaces.OffsetBasedType;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@Table(databaseId = "CDS", name = "test_case")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Test Case", isListed = false)
public class TestCaseDTO extends BaseTestCaseDTO implements OffsetBasedType {
    private static final long serialVersionUID = 6937841517320636194L;

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String testId;
    @NotNull
    @Size(max = 2048)
    private String name;
    @Size(max = 4000)
    private String description;
    private boolean ignoreTest;
    private Date executionDate;
    private boolean offsetBased;
    @Column(name = "age_offset")
    private String offset;
    private Date dob;
    @ReferenceDTO
    @Column(name = "gender_code_id")
    private CdsCodeDTO gender;

    /**
     * Get the value of dob
     *
     * @return the value of dob
     */
    @Override
    public Date getDob() {
        return dob;
    }

    /**
     * Set the value of dob
     *
     * @param dob new value of dob
     */
    @PropertyListener
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Get the value of offset
     *
     * @return the value of offset
     */
    @Override
    public String getOffset() {
        return offset;
    }

    /**
     * Set the value of offset
     *
     * @param offset new value of offset
     */
    @PropertyListener
    public void setOffset(String offset) {
        this.offset = offset;
    }

    /**
     * Get the value of gender
     *
     * @return the value of gender
     */
    public CdsCodeDTO getGender() {
        return gender;
    }

    /**
     * Set the value of gender
     *
     * @param gender new value of gender
     */
    @PropertyListener
    public void setGender(CdsCodeDTO gender) {
        this.gender = gender;
    }

    /**
     * Get the value of offsetBased
     *
     * @return the value of offsetBased
     */
    @Override
    public boolean isOffsetBased() {
        return offsetBased;
    }

    /**
     * Set the value of offsetBased
     *
     * @param offsetBased new value of offsetBased
     */
    @PropertyListener
    public void setOffsetBased(boolean offsetBased) {
        this.offsetBased = offsetBased;
    }

    /**
     * Get the value of executionDate
     *
     * @return the value of executionDate
     */
    @Override
    public Date getExecutionDate() {
        return executionDate;
    }

    /**
     * Set the value of executionDate
     *
     * @param executionDate new value of executionDate
     */
    @PropertyListener
    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    /**
     * Get the value of ignoreTest
     *
     * @return the value of ignoreTest
     */
    public boolean isIgnoreTest() {
        return ignoreTest;
    }

    /**
     * Set the value of ignoreTest
     *
     * @param ignoreTest new value of ignoreTest
     */
    @PropertyListener
    public void setIgnoreTest(boolean ignoreTest) {
        this.ignoreTest = ignoreTest;
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

    /**
     * Get the value of testId
     *
     * @return the value of testId
     */
    @Override
    public String getTestId() {
        return testId;
    }

    /**
     * Set the value of testId
     *
     * @param testId new value of testId
     */
    @PropertyListener
    @Override
    public void setTestId(String testId) {
        this.testId = testId;
    }

    @Override
    public List<OffsetBasedEventType> getIntervalBasedEvents() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
