/**
 * CAT ICE support plugin project.
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
package org.cdsframework.section.ice.test;

import java.util.Date;
import javax.faces.view.ViewScoped;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsListItemDTO;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceTestEventDTO;
import org.cdsframework.dto.IceTestGroupDTO;
import org.cdsframework.dto.IceTestImmunityDTO;
import org.cdsframework.dto.IceTestProposalDTO;
import org.cdsframework.dto.IceTestResultDTO;
import org.cdsframework.dto.IceTestSuiteDTO;
import org.cdsframework.dto.IceTestVaccineGroupRelDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.section.ice.testevent.IceTestEventMGR;
import org.cdsframework.section.ice.testimmunity.IceTestImmunityMGR;
import org.cdsframework.section.ice.testproposal.IceTestProposalMGR;
import org.cdsframework.section.ice.testsuite.IceTestTreeTableAssist;
import org.cdsframework.util.DTOUtils;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.CdsDateUtils;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class IceTestMGR extends BaseModule<IceTestDTO> {

    private static final long serialVersionUID = -8113712614819530955L;

    @Inject
    private IceTestProposalMGR iceTestProposalMGR;
    @Inject
    private IceTestEventMGR iceTestEventMGR;
    @Inject
    private IceTestImmunityMGR iceTestImmunityMGR;
    @Inject
    private UtilityMGR utilityMGR;
    @Inject
    private IceTestTreeTableAssist iceTestTreeTableAssist;
    private String cdsInputData;
    private String cdsOutputData;
    private String cdsEvaluationData;
    private IceTestResultDTO iceTestResultDTO = new IceTestResultDTO();
    private String dobAgeSet;
    private String dateCalcInterval;
    private Date dateOne;
    private Date dateOnePlus;
    private Date dateOneMinus;
    private String newTestName;
    private boolean showSummary;

    @Override
    protected void initialize() {
        this.setBaseHeader("Test");
        setSaveImmediately(true);
        setLazy(false);

        registerChild(IceTestEventDTO.ByTestId.class, iceTestEventMGR);
        registerChild(IceTestProposalDTO.ByTestId.class, iceTestProposalMGR);
        registerChild(IceTestImmunityDTO.ByTestId.class, iceTestImmunityMGR);
    }

    @Override
    public void registerTabComponents() {
        getTabService().registeredUIComponent(0, this);
        getTabService().registeredUIComponent(1, this);
        getTabService().registeredUIComponent(1, iceTestImmunityMGR);
        getTabService().registeredUIComponent(1, iceTestEventMGR);
        getTabService().registeredUIComponent(1, iceTestProposalMGR);
    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        BaseDTO selectedDTO = getSelectedDTO();
        String targetFieldName = getTargetFieldName();
        IceTestDTO parentDTO = getParentDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);

        if (baseDTO instanceof IceTestSuiteDTO) {
            IceTestSuiteDTO iceTestSuiteDTO = (IceTestSuiteDTO) baseDTO;
            if ("suiteAttribute".equals(targetFieldName)) {
                parentDTO.setSuiteId(iceTestSuiteDTO.getSuiteId());
                parentDTO.setSuiteName(iceTestSuiteDTO.getName());
                parentDTO.setVersionId(iceTestSuiteDTO.getCdsVersionDTO() != null ? iceTestSuiteDTO.getCdsVersionDTO().getVersionId() : null);
                parentDTO.setGroupId(null);
                parentDTO.setGroupName(null);
            }
        } else if (baseDTO instanceof IceTestGroupDTO) {
            if ("groupAttribute".equals(targetFieldName)) {
                IceTestGroupDTO iceTestGroupDTO = (IceTestGroupDTO) baseDTO;
                parentDTO.setGroupId(iceTestGroupDTO.getGroupId());
                parentDTO.setGroupName(iceTestGroupDTO.getName());
            }
        } else if (baseDTO instanceof IceVaccineGroupDTO && selectedDTO instanceof IceTestVaccineGroupRelDTO) {
            if ("vaccineGroupSelect".equals(targetFieldName)) {
                IceVaccineGroupDTO iceVaccineGroupDTO = (IceVaccineGroupDTO) baseDTO;
                IceTestVaccineGroupRelDTO iceTestVaccineGroupRelDTO = (IceTestVaccineGroupRelDTO) selectedDTO;
                iceTestVaccineGroupRelDTO.setIceVaccineGroupDTO(iceVaccineGroupDTO);
            }
        } else if (baseDTO instanceof CdsListItemDTO) {
            CdsListItemDTO cdsListItemDTO = (CdsListItemDTO) baseDTO;
            if ("gender".equals(targetFieldName)) {
                parentDTO.setGender(cdsListItemDTO.getCdsCodeDTO());
            }
        }

        // Clear out selected DTO
        setSelectedDTO(null);
    }

    public Date getIceTestExecutionDate(IceTestDTO iceTestDTO) {
        return CdsDateUtils.getOffsetBasedTypeExecutionDate(iceTestDTO);
    }

    public Date getIceTestDob(IceTestDTO iceTestDTO) {
        return CdsDateUtils.getOffsetBasedTypeDob(iceTestDTO);
    }

    public String getAgeAtExecutionDate(IceTestDTO iceTestDTO) {
        return UtilityMGR.getDateDiffCombo(getIceTestDob(iceTestDTO), getIceTestExecutionDate(iceTestDTO));
    }

    @Override
    protected void postSave(ActionEvent actionEvent) throws Exception {
        final String METHODNAME = "postSave ";
        // TODO: on a test case move the tree isn't updating - fix
        iceTestTreeTableAssist.postSaveIceTestDTOSelectMain(getParentDTO());
    }

    @Override
    protected void postDelete() throws Exception {
        final String METHODNAME = "postDelete ";
        iceTestTreeTableAssist.postOnDeleteIceTestDTOSelectMain(getParentDTO());
    }

    @Override
    public void preSetParentDTO(IceTestDTO parentDTO
    ) {
        cdsEvaluationData = null;
        cdsInputData = null;
        cdsOutputData = null;
        dobAgeSet = null;
        iceTestResultDTO = new IceTestResultDTO();
        setShowSummary(false);
    }

    public String getNewTestName() {
        return newTestName;
    }

    public void setNewTestName(String newTestName) {
        this.newTestName = newTestName;
    }

    public Date getDateOneMinus() {
        return dateOneMinus;
    }

    public void setDateOneMinus(Date dateOneMinus) {
        this.dateOneMinus = dateOneMinus;
    }

    public Date getDateOnePlus() {
        return dateOnePlus;
    }

    public void setDateOnePlus(Date dateOnePlus) {
        this.dateOnePlus = dateOnePlus;
    }

    public Date getDateOne() {
        return dateOne;
    }

    public void setDateOne(Date dateOne) {
        this.dateOne = dateOne;
    }

    public String getDateCalcInterval() {
        return dateCalcInterval;
    }

    public void setDateCalcInterval(String dateCalcInterval) {
        this.dateCalcInterval = dateCalcInterval;
    }

    public String getDobAgeSet() {
        return dobAgeSet;
    }

    public void setDobAgeSet(String dobAgeSet) {
        this.dobAgeSet = dobAgeSet;
    }

    public IceTestResultDTO getIceTestResultDTO() {
        return iceTestResultDTO;
    }

    public void setIceTestResultDTO(IceTestResultDTO iceTestResultDTO) {
        this.iceTestResultDTO = iceTestResultDTO;
    }

    public String getCdsInputData() {
        return cdsInputData;
    }

    public void setCdsInputData(String cdsInputData) {
        this.cdsInputData = cdsInputData;
    }

    public String getCdsOutputData() {
        return cdsOutputData;
    }

    public void setCdsOutputData(String cdsOutputData) {
        this.cdsOutputData = cdsOutputData;
    }

    public String getCdsEvaluationData() {
        return cdsEvaluationData;
    }

    public void setCdsEvaluationData(String cdsEvaluationData) {
        this.cdsEvaluationData = cdsEvaluationData;
    }

    public void onRowSummarySelect(IceTestDTO iceTestDTO) {
        onRowSelectMain(UtilityMGR.getEmulatedSelectEvent(iceTestDTO));
    }

    public void calcDates() {
        final String METHODNAME = "calcDates ";
        dateOnePlus = null;
        dateOneMinus = null;
        try {
            if (dateCalcInterval != null) {
                if (dateOne != null) {
                    dateOnePlus = utilityMGR.incrementDateFromString(dateOne, dateCalcInterval, false);
                    dateOneMinus = utilityMGR.incrementDateFromString(dateOne, dateCalcInterval, true);
                } else {
                    getMessageMGR().displayError("DATE_CALC_EMPTY");
                    dateOnePlus = null;
                    dateOneMinus = null;
                }
            } else {
                getMessageMGR().displayError("DATE_CALC_INTERVAL_EMPTY");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    public void clearCalculator() {
        final String METHODNAME = "clearCalculator ";
        dateOneMinus = null;
        dateCalcInterval = null;
        dateOnePlus = null;
        dateOne = null;
    }

    public void calcDobAtAge(ActionEvent actionEvent) throws CatException {
        final String METHODNAME = "calcDobAtAge ";
        try {
            logger.debug("dobAgeSet=", dobAgeSet);
            IceTestDTO parentDTO = getParentDTO();
            if (parentDTO != null) {
                if (parentDTO.getDob() != null) {
                    Date result = utilityMGR.incrementDateFromString(parentDTO.getDob(), dobAgeSet, false);
                    if (result != null) {
                        parentDTO.setExecutionDate(result);
                    }
                } else {
                    throw new CatException("The DOB date must be set.");
                }
            } else {
                throw new CatException("The parentDTO object was null.");
            }
            dobAgeSet = null;
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    public void copyTest() {
        final String METHODNAME = "copyTest ";
        IceTestDTO parentDTO = getParentDTO();
        try {
            if (parentDTO != null) {
                logger.debug("Copying test ", parentDTO.getName(), " to ", newTestName);
                PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                propertyBagDTO.put("newTestName", newTestName);
                propertyBagDTO.setOperationName("copyTest");
                DTOUtils.setDTOState(parentDTO, DTOState.UPDATED);
                IceTestDTO newTest = getGeneralMGR().save(parentDTO, getSessionDTO(), propertyBagDTO);
                getMessageMGR().displayInfo("COPY_TEST", parentDTO.getName(), newTestName);
                iceTestTreeTableAssist.postOnCopyTest(newTest);
            } else {
                logger.error(METHODNAME, "parentDTO is null!");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            this.newTestName = null;
        }
    }
//
//    public String getOnExecutionDateUpdateIds() {
//        final String METHODNAME = "getOnExecutionDateUpdateIds ";
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(getTabViewUpdateId(true)).append(":ageAtExecutionDate:ageAtExecutionDate");
//        stringBuilder.append(" ").append(iceTestEventMGR.getDataTableUpdateId(true));
//        stringBuilder.append(" ").append(iceTestProposalMGR.getDataTableUpdateId(true));
//        stringBuilder.append(" ").append(iceTestImmunityMGR.getDataTableUpdateId(true));
////        stringBuilder.append(" ").append(getTabViewUpdateId(true));
//        if (logger.isDebugEnabled()) {
//            logger.info(METHODNAME, "\"", stringBuilder.toString(), "\"");
//        }
//        return stringBuilder.toString();
//    }
//
//    public String getOnDOBUpdateIds() {
//        final String METHODNAME = "getOnDOBUpdateIds ";
//        String result = getOnExecutionDateUpdateIds();
//        if (logger.isDebugEnabled()) {
//            logger.info(METHODNAME, "\"", result, "\"");
//        }
//        return result;
//    }
//
//    public String getOnSetExecutionDateUpdateIds() {
//        final String METHODNAME = "getOnSetExecutionDateUpdateIds ";
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(getOnExecutionDateUpdateIds());
//        stringBuilder.append(" ").append(getTabViewUpdateId(true)).append(":executionDateSet:executionDateSet");
//        stringBuilder.append(" ").append(getTabViewUpdateId(true)).append(":executionDateDerived:executionDateDerived");
//        stringBuilder.append(" ").append(getTabViewUpdateId(true)).append(":setExecutionDateAtAge:setExecutionDateAtAge");
//        if (logger.isDebugEnabled()) {
//            logger.info(METHODNAME, "\"", stringBuilder.toString(), "\"");
//        }
//        return stringBuilder.toString();
//    }

    public void onRowSelectTestMain(IceTestResultDTO testResult) {
        final String METHODNAME = "onRowSelectTestMain ";
        try {
            logger.info("Selecting test: ", testResult.getIceTestDTO().getName());
            logger.info("Selecting test group: ", testResult.getIceTestDTO().getGroupId());
            // doesn't work anymore
            // SelectEvent testGroupSelectEvent = new SelectEvent(getParentMGR().getEditForm(), new AjaxBehavior(), testResult.getIceTestGroupDTO());
            // getParentMGR().onRowSelectMain(testGroupSelectEvent);
            SelectEvent testSelectEvent = new SelectEvent(getEditForm(), new AjaxBehavior(), testResult.getIceTestDTO());
            onRowSelectMain(testSelectEvent);
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            setRowSelectUpdateIds();
        }
    }
//
//    public void loadTest(IceTestGroupTestDTO iceTestGroupTestDTO) {
//        final String METHODNAME = "loadTest ";
//        try {
//            IceTestDTO iceTestDTO = new IceTestDTO();
//            iceTestDTO.setTestId(iceTestGroupTestDTO.getTestId());
//            iceTestDTO = mgrFindByPrimaryKey(iceTestDTO, getOnRowSelectChildClassDTOs());
//            if (iceTestDTO != null) {
//                SelectEvent testSelectEvent = new SelectEvent(getEditForm(), new AjaxBehavior(), iceTestDTO);
//                onRowSelectMain(testSelectEvent);
//            } else {
//                logger.error("iceTestDTO is null!");
//            }
//        } catch (Exception e) {
//            DefaultExceptionHandler.handleException(e, getClass());
//        } finally {
//            setRowSelectUpdateIds();
//        }
//    }

    /**
     * Get the value of showSummary
     *
     * @return the value of showSummary
     */
    public boolean isShowSummary() {
        return showSummary;
    }

    /**
     * Set the value of showSummary
     *
     * @param showSummary new value of showSummary
     */
    public void setShowSummary(boolean showSummary) {
        this.showSummary = showSummary;
    }
}
