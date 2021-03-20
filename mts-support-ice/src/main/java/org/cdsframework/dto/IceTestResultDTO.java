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
import java.util.List;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReadOnly;
import org.cdsframework.base.BaseTestCaseDTO;
import org.cdsframework.base.BaseTestCaseResultDTO;
import org.cdsframework.enumeration.GenerationSource;

/**
 *
 * @author HLN Consulting, LLC
 */
@ReadOnly
@JndiReference(root = "mts-ejb-ice")
@Permission(name = "ICE Test Result")
public class IceTestResultDTO extends BaseTestCaseResultDTO {

    public interface ByTestSuiteId {
    }

    public interface ByTestGroupId {
    }

    public interface RunTests {
    }
    private static final long serialVersionUID = -7705803800697705490L;
    @GeneratedValue(source = GenerationSource.NONE)
    @Id
    private IceTestDTO iceTestDTO;
    @GeneratedValue(source = GenerationSource.NONE)
    @Id
    private IceTestGroupDTO iceTestGroupDTO;
    @GeneratedValue(source = GenerationSource.NONE)
    @Id
    private IceTestSuiteDTO iceTestSuiteDTO;
    private List<String> differenceLog = new ArrayList<String>();
    private List<String> matchLog = new ArrayList<String>();
    private boolean passed;
    private boolean evaluationPassed;
    private boolean recommendationPassed;
    private String inputXml;
    private String assertionXml;
    private String outputXml;
    private double duration;

    @Override
    public void setTestDTO(BaseTestCaseDTO baseTestCaseDTO) {
        setIceTestDTO((IceTestDTO) baseTestCaseDTO);
    }

    @Override
    public BaseTestCaseDTO getTestDTO() {
        return getIceTestDTO();
    }

    /**
     * Get the value of matchLog
     *
     * @return the value of matchLog
     */
    public List<String> getMatchLog() {
        return matchLog;
    }

    /**
     * Set the value of matchLog
     *
     * @param matchLog new value of matchLog
     */
    public void setMatchLog(List<String> matchLog) {
        this.matchLog = matchLog;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getAssertionXml() {
        return assertionXml;
    }

    public void setAssertionXml(String assertionXml) {
        this.assertionXml = assertionXml;
    }

    public List<String> getDifferenceLog() {
        return differenceLog;
    }

    public void setDifferenceLog(List<String> differenceLog) {
        this.differenceLog = differenceLog;
    }

    public boolean isEvaluationPassed() {
        return evaluationPassed;
    }

    public void setEvaluationPassed(boolean evaluationPassed) {
        this.evaluationPassed = evaluationPassed;
    }

    public IceTestDTO getIceTestDTO() {
        return iceTestDTO;
    }

    public void setIceTestDTO(IceTestDTO iceTestDTO) {
        this.iceTestDTO = iceTestDTO;
    }

    public IceTestGroupDTO getIceTestGroupDTO() {
        return iceTestGroupDTO;
    }

    public void setIceTestGroupDTO(IceTestGroupDTO iceTestGroupDTO) {
        this.iceTestGroupDTO = iceTestGroupDTO;
    }

    public IceTestSuiteDTO getIceTestSuiteDTO() {
        return iceTestSuiteDTO;
    }

    public void setIceTestSuiteDTO(IceTestSuiteDTO iceTestSuiteDTO) {
        this.iceTestSuiteDTO = iceTestSuiteDTO;
    }

    public String getInputXml() {
        return inputXml;
    }

    public void setInputXml(String inputXml) {
        this.inputXml = inputXml;
    }

    public String getOutputXml() {
        return outputXml;
    }

    public void setOutputXml(String outputXml) {
        this.outputXml = outputXml;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public boolean isRecommendationPassed() {
        return recommendationPassed;
    }

    public void setRecommendationPassed(boolean recommendationPassed) {
        this.recommendationPassed = recommendationPassed;
    }
}
