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

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.Ignore;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseTestCaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.interfaces.OffsetBasedEventType;
import org.cdsframework.interfaces.OffsetBasedType;
import org.cdsframework.util.comparator.IceTestEventComparator;

/**
 *
 * @author HLN Consulting, LLC
 *
 */
@Entity
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = IceTestEventDTO.class, childQueryClass = IceTestEventDTO.ByTestId.class, isAutoRetrieve = false, comparatorClass = IceTestEventComparator.class),
    @ParentChildRelationship(childDtoClass = IceTestProposalDTO.class, childQueryClass = IceTestProposalDTO.ByTestId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = IceTestImmunityDTO.class, childQueryClass = IceTestImmunityDTO.ByTestId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = IceTestVaccineGroupRelDTO.class, childQueryClass = IceTestVaccineGroupRelDTO.ByTestId.class, isAutoRetrieve = false)
})
@OrderBy(fields = "lower(suite_name), lower(group_name), lower(name)")
@Table(databaseId = "ICE", name = "ice_test", view = "vw_ice_test")
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Test")
public class IceTestDTO extends BaseTestCaseDTO implements OffsetBasedType {
 
    public interface ByTestGroupId {
    }

    public interface ByTestSuiteId {
    }

    public interface TestIdByTestGroupId {
    }

    public interface TestIdByTestSuiteId {
    }

    public interface UpdateLastMod {
    }

    public interface MaxLastModDatetimeByTestGroupId {
    }

    public interface LastModIdByLastModDatetime {
    }
    private static final long serialVersionUID = -2050478196443888408L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String testId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = IceTestGroupDTO.class)
    @NotNull
    private String groupId;
    @Column(name = "suite_id", updateable = false, insertable = false)
    private String suiteId;
    @NotNull
    @Size(max = 1024)
    private String name;
    @Size(max = 512)
    private String encodedName;
    @Size(max = 4000)
    private String ruleToTest;
    @Size(max = 4000)
    private String notes;
    private Date executionDate;
    @Size(max = 32)
    private String taskContext;
    private Date dob;
    @ReferenceDTO
    private CdsCodeDTO gender;
    @Column(name = "ignore_test", resultSetClass = String.class)
    private boolean ignore;
    @Ignore
    private List<List<String>> shotIntervalGridData = new LinkedList<List<String>>();
    @Ignore
    private List<IceVaccineDTO> validVaccineList = new ArrayList<IceVaccineDTO>();
    private boolean offsetBased;
    @Column(name = "age_offset")
    private String offset;
    @Column(name = "suite_name", updateable = false, insertable = false)
    private String suiteName;
    @Column(name = "group_name", updateable = false, insertable = false)
    private String groupName;
    @Column(name = "version_id", updateable = false, insertable = false)
    private String versionId;
    private boolean evaluateAllDates;

    /**
     * Get the value of evaluateAllDates
     *
     * @return the value of evaluateAllDates
     */
    public boolean isEvaluateAllDates() {
        return evaluateAllDates;
    }

    /**
     * Set the value of evaluateAllDates
     *
     * @param evaluateAllDates new value of evaluateAllDates
     */
    @PropertyListener
    public void setEvaluateAllDates(boolean evaluateAllDates) {
        this.evaluateAllDates = evaluateAllDates;
    }

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
     * Get the value of groupName
     *
     * @return the value of groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Set the value of groupName
     *
     * @param groupName new value of groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    @Override
    public List<OffsetBasedEventType> getIntervalBasedEvents() {
        return (List) getIceTestEventDTOs();
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
    public void setSuiteId(String suiteId) {
        this.suiteId = suiteId;
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
     * Get the value of encodedName
     *
     * @return the value of encodedName
     */
    public String getEncodedName() {
        return encodedName;
    }

    /**
     * Set the value of encodedName
     *
     * @param encodedName new value of encodedName
     */
    @PropertyListener
    public void setEncodedName(String encodedName) {
        this.encodedName = encodedName;
    }

    /**
     * Get the value of ruleToTest
     *
     * @return the value of ruleToTest
     */
    public String getRuleToTest() {
        return ruleToTest;
    }

    /**
     * Set the value of ruleToTest
     *
     * @param ruleToTest new value of ruleToTest
     */
    @PropertyListener
    public void setRuleToTest(String ruleToTest) {
        this.ruleToTest = ruleToTest;
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
     * Get the value of taskContext
     *
     * @return the value of taskContext
     */
    public String getTaskContext() {
        return taskContext;
    }

    /**
     * Set the value of taskContext
     *
     * @param taskContext new value of taskContext
     */
    @PropertyListener
    public void setTaskContext(String taskContext) {
        this.taskContext = taskContext;
    }

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

    public List<IceTestEventDTO> getIceTestEventDTOs() {
        return (List) this.getChildrenDTOs(IceTestEventDTO.ByTestId.class);
    }

    public List<IceTestProposalDTO> getIceTestProposalDTOs() {
        return (List) this.getChildrenDTOs(IceTestProposalDTO.ByTestId.class);
    }

    public List<IceTestImmunityDTO> getIceTestImmunityDTOs() {
        return (List) this.getChildrenDTOs(IceTestImmunityDTO.ByTestId.class);
    }

    public List<IceTestVaccineGroupRelDTO> getIceTestVaccineGroupRelDTOs() {
        return (List) this.getChildrenDTOs(IceTestVaccineGroupRelDTO.ByTestId.class);
    }

    /**
     * Get the value of shotIntervalGridData
     *
     * @return the value of shotIntervalGridData
     */
    public List<List<String>> getShotIntervalGridData() {
        return shotIntervalGridData;
    }

    public void setShotIntervalGridData(List<List<String>> shotIntervalGridData) {
        this.shotIntervalGridData = shotIntervalGridData;
    }

    public List<IceTestEventComponentDTO> getIceTestEventComponentDTOs() {
        List<IceTestEventComponentDTO> components = new LinkedList<IceTestEventComponentDTO>();
        for (IceTestEventDTO item : getIceTestEventDTOs()) {
            if (item != null) {
                for (IceTestEventComponentDTO component : item.getIceTestEventComponentDTOs()) {
                    if (component != null) {
                        components.add(component);
                        component.setIceTestEventDTO(item);
                    }
                }
            }
        }
        return components;
    }

    /**
     * Get the value of validVaccineList
     *
     * @return the value of validVaccineList
     */
    public List<IceVaccineDTO> getValidVaccineList() {
        return validVaccineList;
    }

    /**
     * Set the value of validVaccineList
     *
     * @param validVaccineList new value of validVaccineList
     */
    public void setValidVaccineList(List<IceVaccineDTO> validVaccineList) {
        this.validVaccineList = validVaccineList;
    }
}
