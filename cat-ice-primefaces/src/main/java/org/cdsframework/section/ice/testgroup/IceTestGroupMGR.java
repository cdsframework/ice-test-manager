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
package org.cdsframework.section.ice.testgroup;

import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.IceTestGroupDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.section.ice.testsuite.IceTestSuiteMGR;
import org.cdsframework.section.ice.testsuite.IceTestTreeTableAssist;
import org.cdsframework.section.ice.testsuite.support.IceTestResultAssist;
import org.cdsframework.util.DTOUtils;
import org.cdsframework.util.StringUtils;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class IceTestGroupMGR extends BaseModule<IceTestGroupDTO> {

//    @Inject
//    private IceTestSuiteDTOList iceTestSuiteDTOList;
    @Inject
    private IceTestResultAssist iceTestResultAssist;
    @Inject
    private IceTestTreeTableAssist iceTestTreeTableAssist;
    @Inject
    private IceTestSuiteMGR iceTestuiteMGR;
    private IceTestGroupDTO mergeIceTestGroupDTO;
    private String newGroupName;
    private static final long serialVersionUID = 4832427585546062025L;

    @Override
    protected void initialize() {
        setSaveImmediately(true);
        setLazy(false);
        this.setBaseHeader("Group");
    }

    @Override
    protected void postSave(ActionEvent actionEvent) throws Exception {
        final String METHODNAME = "postSave ";
        iceTestTreeTableAssist.postSaveIceTestGroupDTOSelectMain(getParentDTO());
    }

    @Override
    public void clearSearchMain(ActionEvent actionEvent) {
        String suiteId = (String) getSearchCriteriaDTO().getQueryMap().get("suite_id");
        super.clearSearchMain(actionEvent);
        if (!StringUtils.isEmpty(suiteId)) {
            getSearchCriteriaDTO().getQueryMap().put("suite_id", suiteId);
            logger.info("clearSearchMain - getSearchCriteriaDTO().getQueryMap()=", getSearchCriteriaDTO().getQueryMap());
        }
    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        String targetFieldName = getTargetFieldName();
        IceTestGroupDTO parentDTO = getParentDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);
        if (baseDTO instanceof IceTestGroupDTO) {
            IceTestGroupDTO iceTestGroupDTO = (IceTestGroupDTO) baseDTO;
            if ("mergeIceTestGroupDTO".equals(targetFieldName)) {
                setMergeIceTestGroupDTO(iceTestGroupDTO);
            }
        }
        // Clear out selected DTO
        setSelectedDTO(null);
    }

    @Override
    public boolean deleteMain(boolean cascade, boolean displayMessage, boolean refresh) {
        final String METHODNAME = "deleteMain ";
        logger.info(METHODNAME, "called!");
        logger.info(METHODNAME, "cascade=", cascade);
        logger.info(METHODNAME, "displayMessage=", displayMessage);
        logger.info(METHODNAME, "refresh=", refresh);
        logger.info(METHODNAME, "getPatentDTO()=", getParentDTO());
        return super.deleteMain(cascade, displayMessage, refresh);
    }

    @Override
    protected void postDelete() throws Exception {
        final String METHODNAME = "postDelete ";
        iceTestTreeTableAssist.postOnDeleteIceTestGroupDTOSelectMain(getParentDTO());
    }

    @Override
    public void preSetParentDTO(IceTestGroupDTO parentDTO) {
        iceTestResultAssist.clearAll();
    }

    public IceTestResultAssist getIceTestResultAssist() {
        return iceTestResultAssist;
    }

    public String getNewGroupName() {
        return newGroupName;
    }

    public void setNewGroupName(String newGroupName) {
        this.newGroupName = newGroupName;
    }

    public IceTestGroupDTO getMergeIceTestGroupDTO() {
        return mergeIceTestGroupDTO;
    }

    public void setMergeIceTestGroupDTO(IceTestGroupDTO mergeIceTestGroupDTO) {
        this.mergeIceTestGroupDTO = mergeIceTestGroupDTO;
    }

    @Override
    protected void preClose(CloseEvent event) {
        iceTestResultAssist.cancel();
    }
//
//    public List<IceTestGroupDTO> getMergableDTOs() {
//        final String METHODNAME = "getMergableDTOs ";
//        List<IceTestGroupDTO> mergableDTOs = new ArrayList<IceTestGroupDTO>();
//        try {
//            IceTestGroupDTO parentDTO = getParentDTO();
//            if (parentDTO != null) {
//                String groupId = parentDTO.getGroupId();
//                if (groupId != null) {
//                    logger.debug(METHODNAME, "groupId=", groupId);
//                    IceTestSuiteDTO iceTestSuiteDTO = iceTestuiteMGR.getParentDTO();
//                    logger.debug(METHODNAME, "iceTestSuiteDTO=", iceTestSuiteDTO);
//                    if (iceTestSuiteDTO != null) {
//                        List<IceTestGroupDTO> iceTestGroupDTOs = iceTestSuiteDTO.getIceTestGroupDTOs();
//                        if (iceTestGroupDTOs != null && !iceTestGroupDTOs.isEmpty()) {
//                            for (IceTestGroupDTO iceTestGroupDTO : iceTestGroupDTOs) {
//                                if (!iceTestGroupDTO.getGroupId().equals(groupId)) {
//                                    mergableDTOs.add(iceTestGroupDTO);
//                                }
//                            }
//                        } else {
//                            logger.error(METHODNAME, "iceTestGroupDTOs is null or empty! ", iceTestGroupDTOs);
//                        }
//                    } else {
//                        logger.error(METHODNAME, "iceTestSuiteDTO is null!");
//                    }
//                } else {
//                    logger.error(METHODNAME, "groupId is null!");
//                }
//            } else {
//                logger.error(METHODNAME, "parentDTO is null!");
//            }
//        } catch (Exception e) {
//            DefaultExceptionHandler.handleException(e, getClass());
//        }
//        return mergableDTOs;
//    }

    public void mergeGroups() {
        final String METHODNAME = "getMergableDTOs ";
        IceTestGroupDTO parentDTO = getParentDTO();
        IceTestGroupDTO targetMergeGroup = getMergeIceTestGroupDTO();
        try {
            if (parentDTO != null) {
                if (targetMergeGroup != null) {
                    if (!parentDTO.equals(targetMergeGroup)) {
                        logger.debug(METHODNAME, "targetMergeGroup=", targetMergeGroup);
                        PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                        propertyBagDTO.put("destination", targetMergeGroup.getGroupId());
                        propertyBagDTO.setOperationName("mergeGroup");
                        DTOUtils.setDTOState(parentDTO, DTOState.UPDATED);
                        getGeneralMGR().save(parentDTO, getSessionDTO(), propertyBagDTO);
                        getMessageMGR().displayInfo("MERGE_GROUP", parentDTO.getName(), targetMergeGroup.getName());
                        iceTestTreeTableAssist.postOnMergeGroup(parentDTO, targetMergeGroup);
                    } else {
                        getMessageMGR().displayError("MERGE_GROUP_NO_SELF");
                    }
                } else {
                    getMessageMGR().displayError("MERGE_GROUP_MUST_SELECT");
                }
            } else {
                getMessageMGR().displayError("MERGE_GROUP_NO_PARENT");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            this.mergeIceTestGroupDTO = null;
        }
    }

    public void copyGroup() {
        final String METHODNAME = "copyGroup ";
        IceTestGroupDTO parentDTO = getParentDTO();
        try {
            if (parentDTO != null) {
                logger.info("Copying group ", parentDTO.getName(), " to ", newGroupName);
                PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                propertyBagDTO.put("newGroupName", newGroupName);
                propertyBagDTO.setOperationName("copyGroup");
                DTOUtils.setDTOState(parentDTO, DTOState.UPDATED);
                IceTestGroupDTO newGroup = getGeneralMGR().save(parentDTO, getSessionDTO(), propertyBagDTO);
                getMessageMGR().displayInfo("COPY_GROUP", parentDTO.getName(), newGroupName);
                iceTestTreeTableAssist.postOnCopyGroup(newGroup);

            } else {
                logger.error(METHODNAME, "parentDTO is null!");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            this.newGroupName = null;
            setDefaultOperationName(null);
            getPropertyMap().remove("newGroupName");
        }
    }
}
