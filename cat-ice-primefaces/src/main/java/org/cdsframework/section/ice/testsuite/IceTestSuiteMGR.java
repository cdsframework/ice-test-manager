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
package org.cdsframework.section.ice.testsuite;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.client.support.IceTestSuiteMGRClient;
import org.cdsframework.datatable.DataTableInterface;
import org.cdsframework.dto.CdsVersionDTO;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceTestEventDTO;
import org.cdsframework.dto.IceTestGroupDTO;
import org.cdsframework.dto.IceTestImmunityDTO;
import org.cdsframework.dto.IceTestProposalDTO;
import org.cdsframework.dto.IceTestResultDTO;
import org.cdsframework.dto.IceTestSuiteDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.section.ice.test.IceTestMGR;
import org.cdsframework.section.ice.testgroup.IceTestGroupMGR;
import org.cdsframework.section.ice.testsuite.support.IceTestResultAssist;
import org.cdsframework.util.DTOUtils;
import org.cdsframework.util.FileUtils;
import org.cdsframework.util.StringUtils;
import org.cdsframework.util.cds.ImportUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class IceTestSuiteMGR extends BaseModule<IceTestSuiteDTO> {

    private final static long CDS_MAX_IMPORT_SIZE = 5 * 1024 * 1024; // 5M
    @Inject
    private IceTestGroupMGR iceTestGroupMGR;
    @Inject
    private IceTestMGR iceTestMGR;
    @Inject
    private IceTestResultAssist iceTestResultAssist;
    @Inject
    private IceTestTreeTableAssist iceTestTreeTableAssist;
    private UploadedFile uploadedFile;
    private IceTestSuiteDTO mergeIceTestSuiteDTO;
    private String newSuiteName;
    private Object exportTestObject;
    private CdsVersionDTO importVersion;
    private String sortTestBy = "name";
    private static final long serialVersionUID = -1071881025102101204L;

    public String getSortTestBy() {
        return sortTestBy;
    }

    public void setSortTestBy(String sortTestBy) {
        this.sortTestBy = sortTestBy;
    }

    @Override
    protected void initialize() {
        setSaveImmediately(true);
        setLazy(false);
        setBaseHeader("Suite");
        setInitialQueryClass("FindAll");
        setOnRowSelectFindParent(false);
        setDataTableExists(false);
        registerChild(IceTestGroupDTO.ByTestSuiteId.class, iceTestGroupMGR);
    }

    @Override
    public void clearSearchMain(ActionEvent actionEvent) {
        super.clearSearchMain(actionEvent);
        try {
            iceTestTreeTableAssist.updateTreeTable();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        BaseDTO selectedDTO = getSelectedDTO();
        String targetFieldName = getTargetFieldName();
        IceTestSuiteDTO parentDTO = getParentDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);
        IceTestSuiteDTO searchCriteriaDTO = getSearchCriteriaDTO();

        if (baseDTO instanceof CdsVersionDTO) {
            CdsVersionDTO cdsVersionDTO = (CdsVersionDTO) baseDTO;
            if ("importVersion".equals(targetFieldName)) {
                setImportVersion(cdsVersionDTO);
            } else if ("searchVersion".equals(targetFieldName)) {
                searchCriteriaDTO.getQueryMap().put("version", cdsVersionDTO);
                searchCriteriaDTO.getQueryMap().put("version_id", cdsVersionDTO.getVersionId());
            } else if ("suiteAttribute".equals(targetFieldName)) {
                parentDTO.setCdsVersionDTO(cdsVersionDTO);
            }
        } else if (baseDTO instanceof IceTestSuiteDTO) {
            IceTestSuiteDTO iceTestSuiteDTO = (IceTestSuiteDTO) baseDTO;
            if ("searchSuite".equals(targetFieldName)) {
                searchCriteriaDTO.getQueryMap().put("suite", iceTestSuiteDTO);
                searchCriteriaDTO.getQueryMap().put("suite_id", iceTestSuiteDTO.getSuiteId());
            } else if ("exportTestObject".equals(targetFieldName)) {
                setExportTestObject(iceTestSuiteDTO);
            } else if ("mergeIceTestSuiteDTO".equals(targetFieldName)) {
                setMergeIceTestSuiteDTO(iceTestSuiteDTO);
            }
        } else if (baseDTO instanceof IceTestGroupDTO) {
            if ("searchGroup".equals(targetFieldName)) {
                IceTestGroupDTO iceTestGroupDTO = (IceTestGroupDTO) baseDTO;
                searchCriteriaDTO.getQueryMap().put("group", iceTestGroupDTO);
                searchCriteriaDTO.getQueryMap().put("group_id", iceTestGroupDTO.getGroupId());
            }
        }

        logger.info(METHODNAME, "searchCriteriaDTO.getQueryMap()=", searchCriteriaDTO.getQueryMap());
        // Clear out selected DTO
        setSelectedDTO(null);
    }

    @Override
    public void resetField(BaseDTO baseDTO, String fieldName) {
        super.resetField(baseDTO, fieldName); //To change body of generated methods, choose Tools | Templates.
        final String METHODNAME = "resetField ";

        if (baseDTO instanceof CdsVersionDTO) {
            if (fieldName.equalsIgnoreCase("searchVersion")) {
                IceTestSuiteDTO searchCriteriaDTO = getSearchCriteriaDTO();
                searchCriteriaDTO.getQueryMap().remove("version");
                searchCriteriaDTO.getQueryMap().remove("version_id");
            } else if ("suiteAttribute".equals(fieldName)) {
                IceTestSuiteDTO parentDTO = getParentDTO();
                parentDTO.setCdsVersionDTO(null);
            }
        } else if (baseDTO instanceof IceTestSuiteDTO) {
            if (fieldName.equalsIgnoreCase("searchSuite")) {
                IceTestSuiteDTO searchCriteriaDTO = getSearchCriteriaDTO();
                searchCriteriaDTO.getQueryMap().remove("suite");
                searchCriteriaDTO.getQueryMap().remove("suite_id");
            }
        } else if (baseDTO instanceof IceTestGroupDTO) {
            if (fieldName.equalsIgnoreCase("searchGroup")) {
                IceTestSuiteDTO searchCriteriaDTO = getSearchCriteriaDTO();
                searchCriteriaDTO.getQueryMap().remove("group");
                searchCriteriaDTO.getQueryMap().remove("group_id");
            }
        }

    }

    @Override
    public void postSetParentDTO(IceTestSuiteDTO parentDTO) {
    }

    @Override
    protected void postDelete() throws Exception {
        final String METHODNAME = "postDelete ";
        IceTestSuiteDTO localParentDTO = getParentDTO();
        if (localParentDTO != null) {
            DataTableInterface<IceTestSuiteDTO> dataTableMGR = getDataTableMGR();
            if (dataTableMGR != null) {
                List<IceTestSuiteDTO> dtoList = dataTableMGR.getDtoList();
                if (dtoList != null) {
                    if (dtoList.indexOf(localParentDTO) != -1) {
                        dtoList.remove(localParentDTO);
                        logger.warn(METHODNAME, "second remove - I don't know why...", dtoList.indexOf(localParentDTO));
                    }
                }
            }
        }
        iceTestTreeTableAssist.updateTreeTable();
    }

    public void onChangeSortOrder(AjaxBehaviorEvent ajaxBehaviorEvent) {
        final String METHODNAME = "onChangeSortOrder ";
        logger.info(METHODNAME, "called!");
        iceTestTreeTableAssist.updateTreeTable();
    }

    @Override
    protected void postSave(ActionEvent actionEvent) throws Exception {
        final String METHODNAME = "postSave ";
        iceTestTreeTableAssist.updateTreeTable();
    }

    @Override
    protected void postClose(CloseEvent event) {
        final String METHODNAME = "postClose ";
        setUpdateIds(METHODNAME, iceTestTreeTableAssist.getTreeTableUpdateId());
    }

    @Override
    protected void postSearch(ActionEvent actionEvent) {
        iceTestTreeTableAssist.updateTreeTable();
    }

    @Override
    public void preSetParentDTO(IceTestSuiteDTO parentDTO) {
        newSuiteName = null;
        mergeIceTestSuiteDTO = null;
        iceTestResultAssist.clearAll();
    }

    /**
     * Get the value of importVersion
     *
     * @return the value of importVersion
     */
    public CdsVersionDTO getImportVersion() {
        return importVersion;
    }

    /**
     * Set the value of importVersion
     *
     * @param importVersion new value of importVersion
     */
    public void setImportVersion(CdsVersionDTO importVersion) {
        this.importVersion = importVersion;
    }

    public Object getExportTestObject() {
        return exportTestObject;
    }

    public void setExportTestObject(Object exportTestObject) {
        this.exportTestObject = exportTestObject;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getNewSuiteName() {
        return newSuiteName;
    }

    public void setNewSuiteName(String newSuiteName) {
        this.newSuiteName = newSuiteName;
    }

    public IceTestSuiteDTO getMergeIceTestSuiteDTO() {
        return mergeIceTestSuiteDTO;
    }

    public void setMergeIceTestSuiteDTO(IceTestSuiteDTO mergeIceTestSuiteDTO) {
        this.mergeIceTestSuiteDTO = mergeIceTestSuiteDTO;
    }

    @Override
    protected void preClose(CloseEvent event) {
        iceTestResultAssist.cancel();
    }
//
//    public List<IceTestSuiteDTO> getMergableDTOs() {
//        final String METHODNAME = "getMergableDTOs ";
//        logger.logBegin(METHODNAME);
//        List<IceTestSuiteDTO> mergableDTOs = new ArrayList<IceTestSuiteDTO>();
//        String exclude = null;
//        try {
//            IceTestSuiteDTO parentDTO = getParentDTO();
//            if (parentDTO != null) {
//                exclude = parentDTO.getSuiteId();
//            }
//            if (getDataTableMGR() != null && getDataTableMGR().getDtoList() != null) {
//                for (IceTestSuiteDTO iceTestSuiteDTO : getDataTableMGR().getDtoList()) {
//                    if (!iceTestSuiteDTO.getSuiteId().equals(exclude)) {
//                        mergableDTOs.add(iceTestSuiteDTO);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            DefaultExceptionHandler.handleException(e, getClass());
//        } finally {
//            logger.logEnd(METHODNAME);
//        }
//        return mergableDTOs;
//    }

    public void mergeSuites() {
        final String METHODNAME = "mergeSuites ";
        IceTestSuiteDTO parentDTO = getParentDTO();
        IceTestSuiteDTO targetMergeSuite = getMergeIceTestSuiteDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);
        logger.info(METHODNAME, "targetMergeSuite=", targetMergeSuite);
        try {
            if (parentDTO != null) {
                if (targetMergeSuite != null) {
                    if (!parentDTO.equals(targetMergeSuite)) {
                        getPropertyMap().put("destination", targetMergeSuite.getSuiteId());
                        setDefaultOperationName("mergeSuites");
                        DTOUtils.setDTOState(parentDTO, DTOState.UPDATED);
                        this.saveMain(null, false);
                        getMessageMGR().displayInfo("MERGE_SUITE", parentDTO.getName(), targetMergeSuite.getName());
                        searchByClassName("FindAll");
                    } else {
                        getMessageMGR().displayError("MERGE_SUITE_NO_SELF");
                    }
                } else {
                    getMessageMGR().displayError("MERGE_SUITE_MUST_SELECT");
                }
            } else {
                getMessageMGR().displayError("MERGE_SUITE_NO_PARENT");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            this.mergeIceTestSuiteDTO = null;
            setDefaultOperationName(null);
            getPropertyMap().remove("destination");
        }
    }

    public void convertSuiteToOffsets(IceTestSuiteDTO iceTestSuiteDTO) {
        final String METHODNAME = "convertSuiteToOffsets ";
        logger.info(METHODNAME, "called on ", iceTestSuiteDTO);
        List<Class<? extends BaseDTO>> childClasses = new ArrayList<Class<? extends BaseDTO>>();
        childClasses.add(IceTestGroupDTO.class);
        childClasses.add(IceTestDTO.class);
        childClasses.add(IceTestEventDTO.class);
        childClasses.add(IceTestImmunityDTO.class);
        childClasses.add(IceTestProposalDTO.class);
        try {
            PropertyBagDTO newPropertyBagDTO = getNewPropertyBagDTO();
            newPropertyBagDTO.setChildClassDTOs(childClasses);
            iceTestSuiteDTO = getGeneralMGR().findByPrimaryKey(iceTestSuiteDTO, getSessionDTO(), newPropertyBagDTO);
            for (IceTestGroupDTO iceTestGroupDTO : iceTestSuiteDTO.getIceTestGroupDTOs()) {
                logger.info(METHODNAME, "processing group: ", iceTestGroupDTO);
                for (IceTestDTO iceTestDTO : iceTestGroupDTO.getIceTestDTOs()) {
                    logger.info(METHODNAME, "processing test: ", iceTestDTO);
                    if (iceTestDTO.isOffsetBased()) {
                        iceTestDTO.setOffsetBased(false);
                        iceTestDTO.setOffset(null);
                        for (IceTestEventDTO iceTestEventDTO : iceTestDTO.getIceTestEventDTOs()) {
                            logger.info(METHODNAME, "processing event: ", iceTestEventDTO);
                            iceTestEventDTO.setOffsetBased(false);
                            iceTestEventDTO.setOffset(null);
                        }
                        for (IceTestImmunityDTO iceTestImmunityDTO : iceTestDTO.getIceTestImmunityDTOs()) {
                            logger.info(METHODNAME, "processing immunity event: ", iceTestImmunityDTO);
                            iceTestImmunityDTO.setOffsetBased(false);
                            iceTestImmunityDTO.setOffset(null);
                        }
                        for (IceTestProposalDTO iceTestProposalDTO : iceTestDTO.getIceTestProposalDTOs()) {
                            logger.info(METHODNAME, "processing proposal: ", iceTestProposalDTO);
                            iceTestProposalDTO.setOffsetBased(false);
                            iceTestProposalDTO.setRecommendedOffset(null);
                        }
                    } else {
                        iceTestDTO.setOffsetBased(true);
                    }
                }
            }
            setParentDTO(iceTestSuiteDTO);
            saveMain();
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    public void copySuite(String newSuiteName) {
        IceTestSuiteDTO parentDTO = getParentDTO();
        try {
            getPropertyMap().put("newSuiteName", newSuiteName);
            setDefaultOperationName("copySuite");
            DTOUtils.setDTOState(parentDTO, DTOState.UPDATED);
            this.saveMain(null, false);
            getMessageMGR().displayInfo("COPY_SUITE", parentDTO.getName(), newSuiteName);
            searchByClassName("FindAll");
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            this.newSuiteName = null;
            setDefaultOperationName(null);
            getPropertyMap().remove("newSuiteName");
        }
    }

    public void importTest(FileUploadEvent event) throws Exception {
        final String METHODNAME = "importTest ";
        byte[] payload = ImportUtils.getBase64ByteArrayPayloadFromFileUploadEvent(event);
        UploadedFile file = event.getFile();
        try {
            logger.info("Filename: " + file.getFileName());
            logger.info("Content-type: " + file.getContentType());
            logger.info("Size: " + file.getSize());
            if (file.getSize() > CDS_MAX_IMPORT_SIZE) {
                throw new CatException("File size of " + file.getFileName() + " exceeds the maximum allowed.");
            }
            PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
            propertyBagDTO.put("fileName", file.getFileName());
            propertyBagDTO.put("mimeType", file.getContentType());
            propertyBagDTO.put("fileSize", file.getSize());
            propertyBagDTO.put("cdsVersionDTO", importVersion);
            propertyBagDTO.put("payload", payload);
            propertyBagDTO.setQueryClass("SimpleExchange");
            IceTestSuiteMGRClient manager = getMts().getManager(IceTestSuiteMGRClient.class);
            manager.importData(getSessionDTO(), propertyBagDTO);
            getMessageMGR().displayInfo("IMPORT_TEST", file.getFileName());
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            markAssociatedDTOListDirty();
        }
    }

    public void runTests(BaseDTO baseDTO) {
        final String METHODNAME = "runTests ";
        logger.logBegin(METHODNAME);
        try {

            IceTestSuiteMGRClient iceTestSuiteMGRClient = getMts().getManager(IceTestSuiteMGRClient.class);
            if (baseDTO instanceof IceTestSuiteDTO) {
                // get ice test DTOs based on the suite ID
                PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                propertyBagDTO.setQueryClass("TestIdByTestSuiteId");
                propertyBagDTO.put("suiteId", getParentDTO().getSuiteId());
                List<String> iceTestDTOIds = getGeneralMGR().findObjectByQueryList(new IceTestDTO(), getSessionDTO(), String.class, propertyBagDTO);

                if (iceTestDTOIds == null || iceTestDTOIds.isEmpty()) {
                    this.messageMGR.displayWarnMessage("There are no tests in the suite to run.");
                    return;
                }

                // queue tests
                List<UUID> queueTests = iceTestSuiteMGRClient.queueTests(iceTestDTOIds);

                // set the queued values
                iceTestResultAssist.setTestUuids(queueTests);

                // set the active tab
                getTabView().setActiveIndex(1);

                // disable the run button and kick off the progress bar
                RequestContext.getCurrentInstance().execute("PF('progressBarVar').start();");
            } else if (baseDTO instanceof IceTestGroupDTO) {
                // get ice test DTOs based on the group ID
                PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                propertyBagDTO.setQueryClass("TestIdByTestGroupId");
                propertyBagDTO.put("groupId", iceTestGroupMGR.getParentDTO().getGroupId());
                List<String> iceTestDTOIds = getGeneralMGR().findObjectByQueryList(new IceTestDTO(), getSessionDTO(), String.class, propertyBagDTO);

                if (iceTestDTOIds == null || iceTestDTOIds.isEmpty()) {
                    this.messageMGR.displayWarnMessage("There are no tests in the group to run.");
                    return;
                }

                // queue tests
                List<UUID> queueTests = iceTestSuiteMGRClient.queueTests(iceTestDTOIds);

                // set the queued values
                iceTestGroupMGR.getIceTestResultAssist().setTestUuids(queueTests);

                // set the active tab
                iceTestGroupMGR.getTabView().setActiveIndex(1);

                // disable the run button and kick off the progress bar
                RequestContext.getCurrentInstance().execute("PF('progressBarVar').start();");
            } else if (baseDTO instanceof IceTestDTO) {
                // submitt the test run
                PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                propertyBagDTO.put("testId", iceTestMGR.getParentDTO().getTestId());
                propertyBagDTO.setQueryClass("RunTests");
                IceTestResultDTO iceTestResultDTO = getGeneralMGR().customQuery(new IceTestResultDTO(), getSessionDTO(), propertyBagDTO);

                // set the various single test run values...
                iceTestMGR.setCdsInputData(iceTestResultDTO.getInputXml());
                iceTestMGR.setCdsOutputData(iceTestResultDTO.getAssertionXml());
                iceTestMGR.setCdsEvaluationData(iceTestResultDTO.getOutputXml());
                iceTestMGR.setIceTestResultDTO(iceTestResultDTO);

//                // set the active tab
//                iceTestMGR.getTabView().setActiveIndex(2);
            } else {
                throw new CatException("Unexpected class passed to runTests: " + baseDTO.getClass().getCanonicalName());
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    public StreamedContent exportTest(Object exportTestObject) {
        this.exportTestObject = exportTestObject;
        return exportTest();
    }

    public StreamedContent exportTest() {
        final String METHODNAME = "exportTest ";
        logger.logBegin(METHODNAME);
        StreamedContent testFile = null;
        String exportSelection = null;
        IceTestSuiteDTO exportTestSuiteDTO = new IceTestSuiteDTO();
        if (exportTestObject == null) {
            exportTestSuiteDTO = new IceTestSuiteDTO();
            exportTestSuiteDTO.setSuiteId("ALL");
            exportTestSuiteDTO.setName("ALL");
            exportTestObject = exportTestSuiteDTO;
        }
        if (exportTestObject instanceof IceTestSuiteDTO) {
            exportTestSuiteDTO = (IceTestSuiteDTO) exportTestObject;
            exportSelection = exportTestSuiteDTO.getName();
        } else if (exportTestObject instanceof IceTestGroupDTO) {
            IceTestGroupDTO iceTestGroupDTO = (IceTestGroupDTO) exportTestObject;
            exportTestSuiteDTO.setSuiteId(iceTestGroupDTO.getSuiteId());
            exportSelection = iceTestGroupDTO.getName();
        } else if (exportTestObject instanceof IceTestDTO) {
            IceTestDTO iceTestDTO = (IceTestDTO) exportTestObject;
            exportSelection = iceTestDTO.getName();
        }
        logger.info(METHODNAME, "Selection: ", exportSelection);
        String name = StringUtils.stripNonAlphaNumberic(exportSelection, true);
        try {
            PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
            propertyBagDTO.setQueryClass("SimpleExchange");
            propertyBagDTO.put("exportTestObject", exportTestObject);
            IceTestSuiteMGRClient manager = getMts().getManager(IceTestSuiteMGRClient.class);
            Map<String, byte[]> exportData = manager.exportData(exportTestSuiteDTO, getSessionDTO(), propertyBagDTO);
            ByteArrayInputStream stream = new ByteArrayInputStream(FileUtils.getZipOfFiles(exportData));
            testFile = new DefaultStreamedContent(stream, "application/zip", String.format("%s-%s.zip", name, new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Timestamp(new Date().getTime()))));
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logEnd(METHODNAME);
        }
        return testFile;
    }
}
