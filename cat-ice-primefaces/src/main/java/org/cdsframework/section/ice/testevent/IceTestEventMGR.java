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
package org.cdsframework.section.ice.testevent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.datatable.DataTableInterface;
import org.cdsframework.dto.IceVaccineDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
import org.cdsframework.dto.IceTestEventComponentDTO;
import org.cdsframework.dto.IceTestEventDTO;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceVaccineComponentDTO;
import org.cdsframework.dto.IceVaccineComponentRelDTO;
import org.cdsframework.dto.IceVaccineGroupVaccineRelDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.interfaces.OffsetBasedEventType;
import org.cdsframework.section.ice.test.IceTestMGR;
import org.cdsframework.util.CdsDateUtils;
import org.cdsframework.util.StringUtils;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.enumeration.PrePost;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class IceTestEventMGR extends BaseModule<IceTestEventDTO> {

    private static final long serialVersionUID = 7857501375141380724L;

    @Inject
    private IceTestMGR iceTestMGR;
    @Inject
    private UtilityMGR utilityMGR;
    private String adminDateAtAge;
    private boolean showSummary;
    @Inject
    private IceTestEventComponentMGR iceTestEventComponentMGR;

    @Override
    protected void initialize() {
        final String METHODNAME = "initialize ";
        setSaveImmediately(true);
        registerChild(IceTestEventComponentDTO.ByEventId.class, iceTestEventComponentMGR);
        this.setBaseHeader("Administered Immunization");
    }

    @Override
    public void registerTabComponents() {
        getTabService().registeredUIComponent(0, this);
        getTabService().registeredUIComponent(0, iceTestEventComponentMGR);
    }

    @Override
    public void clearSearchMain(ActionEvent actionEvent) {
        String testId = (String) getSearchCriteriaDTO().getQueryMap().get("test_id");
        String eventId = (String) getSearchCriteriaDTO().getQueryMap().get("event_id");
        super.clearSearchMain(actionEvent); //To change body of generated methods, choose Tools | Templates.
        if (!StringUtils.isEmpty(testId)) {
            logger.info("clearSearchMain testId=", testId);
            getSearchCriteriaDTO().getQueryMap().put("test_id", testId);
        }
        if (!StringUtils.isEmpty(eventId)) {
            logger.info("clearSearchMain eventId=", eventId);
            getSearchCriteriaDTO().getQueryMap().put("event_id", eventId);
        }
    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        BaseDTO selectedDTO = getSelectedDTO();
        String targetFieldName = getTargetFieldName();
        IceTestEventDTO parentDTO = getParentDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);

        if (baseDTO instanceof IceVaccineDTO) {
            IceVaccineDTO iceVaccineDTO = (IceVaccineDTO) baseDTO;
            if ("iceVaccineDTO".equals(targetFieldName)) {
                logger.info(METHODNAME, "IceVaccineDTO, iceVaccineDTO");
                parentDTO.setIceVaccineDTO(iceVaccineDTO);
                setVaccineComponents();
            }
        } else if (baseDTO instanceof IceTestEventDTO) {
            IceTestEventDTO iceTestEventDTO = (IceTestEventDTO) baseDTO;
            if ("iceEventDTO".equals(targetFieldName)) {
                logger.info(METHODNAME, "IceTestEventDTO, iceEventDTO");
                parentDTO.setOffsetId(iceTestEventDTO.getEventId());
            }
        }
        // Clear out selected DTO
        setSelectedDTO(null);
    }

    @Override
    public void prePostOperation(String queryClass, BaseDTO baseDTO, PrePost prePost, boolean status) {
        final String METHODNAME = "prePostOperation ";
        logger.info(METHODNAME, "queryClass=", queryClass, " baseDTO=", baseDTO, " prePost=", prePost, " status=", status);
        switch (prePost) {
            case PostTabSave:
                IceTestEventDTO parentDTO = getParentDTO();
                if (parentDTO.getDTOState() == DTOState.UNSET) {
                    IceTestEventComponentDTO[] iceTestEventComponentDTOs = iceTestEventComponentMGR.getDataTableMGR().getDtoList().toArray(new IceTestEventComponentDTO[0]);
                    for (IceTestEventComponentDTO item : iceTestEventComponentDTOs) {
                        switch (item.getDTOState()) {
                            case DELETED:
                                iceTestEventComponentMGR.setParentDTO(item);
                                iceTestEventComponentMGR.deleteMain(true, false);
                                break;
                            case NEW:
                            case NEWMODIFIED:
                            case UPDATED:
                                iceTestEventComponentMGR.setParentDTO(item);
                                iceTestEventComponentMGR.saveMain(UtilityMGR.getEmulatedActionEvent(), false);
                                break;
                        }
                    }
                }
                break;
            case PostInlineDelete:
            case PostInlineSave:
                if (status) {
                    refresh();
                    setDataTableSearchUpdateIds();
                }
                break;
        }
    }

    public String getOffsetIdLabel(String primaryKey) {
        String result = "";
        IceTestEventDTO iceTestEventDTO = getIceTestEventDTOFromDataTable(primaryKey);
        if (iceTestEventDTO != null && iceTestEventDTO.getIceVaccineDTO() != null) {
            result = String.format("%s - %s",
                    iceTestEventDTO.getIceVaccineDTO().getVaccine().getLabel(),
                    UtilityMGR.getFormattedDate(getIceTestEventDate(iceTestEventDTO)));
        }
        return result;
    }

    private IceTestEventDTO getIceTestEventDTOFromDataTable(String primaryKey) {
        IceTestEventDTO result = null;
        if (primaryKey != null) {
            for (IceTestEventDTO item : getDataTableMGR().getDtoList()) {
                if (item != null && item.getEventId() != null && item.getEventId().equalsIgnoreCase(primaryKey)) {
                    result = item;
                    break;
                }
            }
        }
        return result;
    }

    public Date getIceTestEventDate(IceTestEventDTO item) {
        Date result = null;
        try {
            IceTestDTO iceTestDTO = iceTestMGR.getParentDTO();
            if (iceTestDTO != null) {
                if (((item.isOffsetBased() && item.getOffset() != null)
                        || (!item.isOffsetBased() && item.getAdministrationTime() != null))
                        && ((iceTestDTO.isOffsetBased() && iceTestDTO.getOffset() != null)
                        || (!iceTestDTO.isOffsetBased() && iceTestDTO.getDob() != null))) {
                    iceTestDTO.setChildrenDTOs(IceTestEventDTO.ByTestId.class, (List) getDataTableMGR().getDtoList());
                    result = CdsDateUtils.getOffsetBasedEventDate(item, iceTestDTO, new ArrayList<OffsetBasedEventType>());
                }
            } else {
                logger.error("parentDTO was null!");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return result;
    }

    public String getIceTestEventAgeAtAdmin(IceTestEventDTO iceTestEventDTO, boolean withBr) {
        String result = null;
        try {
            IceTestDTO iceTestDTO = iceTestMGR.getParentDTO();
            if (iceTestDTO != null) {
                if (withBr) {
                    result = UtilityMGR.getDateDiffComboWithBR(iceTestMGR.getIceTestDob(iceTestDTO), getIceTestEventDate(iceTestEventDTO));
                } else {
                    result = UtilityMGR.getDateDiffCombo(iceTestMGR.getIceTestDob(iceTestDTO), getIceTestEventDate(iceTestEventDTO));
                }
            } else {
                logger.error("parentDTO was null!");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return result;
    }
//
//    public List<IceTestEventDTO> getOtherIceTestEventDTOs(OffsetBasedEventType offsetBasedEvent) {
//        final String METHODNAME = "getOtherIceTestEventDTOs ";
//        List<IceTestEventDTO> result = new ArrayList<IceTestEventDTO>();
//        try {
//            IceTestDTO iceTestDTO = iceTestMGR.getParentDTO();
//            if (iceTestDTO != null) {
//                List<IceTestEventDTO> peerEventDTOs = iceTestDTO.getChildrenDTOs(IceTestEventDTO.ByTestId.class, IceTestEventDTO.class);
//                if (offsetBasedEvent != null) {
//                    Date offsetBasedEventDate = CdsDateUtils.getOffsetBasedEventDate(offsetBasedEvent, iceTestDTO, new ArrayList<OffsetBasedEventType>());
//
//                    for (IceTestEventDTO item : peerEventDTOs) {
//                        Date otherOffsetEventDate = CdsDateUtils.getOffsetBasedEventDate(offsetBasedEvent, iceTestDTO, new ArrayList<OffsetBasedEventType>());
//                        if (!item.equals(offsetBasedEvent) && !otherOffsetEventDate.after(offsetBasedEventDate)) {
//                            result.add(item);
//                        }
//                    }
//                } else {
//                    result = peerEventDTOs;
//                }
//            } else {
//                logger.error(METHODNAME, "iceTestDTO was null!");
//            }
//        } catch (Exception e) {
//            DefaultExceptionHandler.handleException(e, getClass());
//        }
//        return result;
//    }

    @Override
    public void preSetParentDTO(IceTestEventDTO parentDTO) {
        adminDateAtAge = null;
        setShowSummary(false);
    }

    public String getAdminDateAtAge() {
        return adminDateAtAge;
    }

    public void setAdminDateAtAge(String adminDateAtAge) {
        this.adminDateAtAge = adminDateAtAge;
    }

    public void calcAdminDateAtAge(ActionEvent actionEvent) throws CatException {
        final String METHODNAME = "calcAdminDateAtAge ";
        try {
            logger.info("adminDateAtAge=", adminDateAtAge);
            IceTestDTO iceTestDTO = (IceTestDTO) getParentMGR().getParentDTO();
            IceTestEventDTO parentDTO = getParentDTO();
            if (parentDTO != null) {
                if (iceTestDTO != null) {
                    Date iceTestDob = CdsDateUtils.getOffsetBasedTypeDob(iceTestDTO);
                    if (iceTestDob != null) {
                        Date result = utilityMGR.incrementDateFromString(iceTestDob, adminDateAtAge, false);
                        if (result != null) {
                            parentDTO.setAdministrationTime(result);
                        }
                    } else {
                        throw new CatException("The DOB date must be set.");
                    }
                } else {
                    throw new CatException("The iceTestDTO object was null.");
                }
            } else {
                throw new CatException("The parentDTO object was null.");
            }
            adminDateAtAge = null;
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    private void setVaccineComponents() {
        final String METHODNAME = "setVaccineComponents ";
//        long start = System.nanoTime();
        List<IceVaccineComponentDTO> components = new ArrayList<IceVaccineComponentDTO>();
        IceTestEventDTO parentDTO = getParentDTO();
        IceTestDTO iceTestDTO = iceTestMGR.getParentDTO();
        try {
            if (iceTestDTO != null) {
                if (parentDTO != null) {

                    // generate the new component list
                    IceVaccineDTO newValue = parentDTO.getIceVaccineDTO();
                    if (newValue != null) {
                        List<Class<? extends BaseDTO>> childClasses = new ArrayList<Class<? extends BaseDTO>>();
                        childClasses.add(IceVaccineComponentRelDTO.class);
                        childClasses.add(IceVaccineGroupVaccineRelDTO.class);
                        PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                        propertyBagDTO.setChildClassDTOs(childClasses);
                        newValue = getGeneralMGR().findByPrimaryKey(newValue, getSessionDTO(), propertyBagDTO);
                        logger.info(METHODNAME, "User selected: ", newValue.getVaccineName(), " - ",
                                newValue.getVaccine() != null ? newValue.getVaccine().getCode() : null);
                        components.addAll(newValue.getVaccineComponents());
                        logger.info(METHODNAME, "newValue.getIceVaccineGroupVaccineRelDTOs()=", newValue.getIceVaccineGroupVaccineRelDTOs());
                    } else {
                        logger.warn(METHODNAME, "User selected null!");
                    }
                    logger.info(METHODNAME, "components=", components);
                    if (true) {
                        logger.info(METHODNAME, "Found Components:");
                        for (IceVaccineComponentDTO item : components) {
                            logger.info(METHODNAME, "    item=", item.getVaccineComponent().getCode());
                        }
                    }

                    // delete old components - make existing as deleted and remove outright any newly added...
                    DataTableInterface<IceTestEventComponentDTO> dataTableMGR = iceTestEventComponentMGR.getDataTableMGR();
                    IceTestEventComponentDTO[] items = dataTableMGR.getDtoList().toArray(new IceTestEventComponentDTO[0]);
                    for (IceTestEventComponentDTO iceTestEventComponentDTO : items) {
                        if (iceTestEventComponentDTO.getIceVaccineComponentDTO() != null) {
                            logger.info(METHODNAME, "deleting: ", iceTestEventComponentDTO.getIceVaccineComponentDTO().getVaccineComponent().getCode());
                        }
                        if (!iceTestEventComponentDTO.isNew()) {
                            iceTestEventComponentDTO.delete(true);
                        } else {
                            dataTableMGR.delete(iceTestEventComponentDTO, true);
                        }
                    }

                    for (IceVaccineComponentDTO item : components) {
                        // determine if there is a single vaccine group to choose from
                        // - if so set it on the new components - otherwise make the user pick...
                        PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                        propertyBagDTO.setQueryClass("ByTestIdVacComponentId");
                        IceVaccineGroupDTO iceVaccineGroupDTO = new IceVaccineGroupDTO();
                        iceVaccineGroupDTO.getQueryMap().put("test_id", iceTestDTO.getTestId());
                        iceVaccineGroupDTO.getQueryMap().put("component_id", item.getComponentId());
                        List<IceVaccineGroupDTO> vaccineGroupList = getGeneralMGR().findByQueryList(iceVaccineGroupDTO, getSessionDTO(), propertyBagDTO);
                        logger.info(METHODNAME, "vaccineGroupList=", vaccineGroupList);
                        if (vaccineGroupList.size() == 1) {
                            iceVaccineGroupDTO = vaccineGroupList.get(0);
                        } else {
                            iceVaccineGroupDTO = null;
                        }

                        logger.info("adding: ", item.getVaccineComponent().getCode());
                        IceTestEventComponentDTO iceTestEventComponentDTO = new IceTestEventComponentDTO();
                        iceTestEventComponentDTO.setIceTestEventDTO(parentDTO);
                        iceTestEventComponentDTO.setEventId(parentDTO.getEventId());
                        iceTestEventComponentDTO.setIceVaccineComponentDTO(item);
                        iceTestEventComponentDTO.setIceVaccineGroupDTO(iceVaccineGroupDTO);
                        iceTestEventComponentMGR.getDataTableMGR().addOrUpdateDTO(iceTestEventComponentDTO);
                    }
                } else {
                    logger.warn("parentDTO was null!");
                }
            } else {
                logger.warn("iceTestDTO was null!");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
//        finally {
//            logger.logDuration(METHODNAME, start);
//        }
    }

//    public List<IceTestEventComponentDTO> getComponentDtoList() {
//        List<IceTestEventComponentDTO> dtoList = new ArrayList<IceTestEventComponentDTO>();
//        for (IceTestEventComponentDTO iceTestEventComponentDTO : getDataTableMGR().getDtoList()) {
//            if (!iceTestEventComponentDTO.isDeleted()) {
//                dtoList.add(iceTestEventComponentDTO);
//            }
//        }
//        return dtoList;
//    }
//    public String getStatus(IceTestEventDTO iceTestEventDTO) {
////        final String METHODNAME = "getStatus ";
////        long start = System.nanoTime();
//        String status = "";
//        try {
//            if (iceTestEventDTO != null) {
//                List<IceTestEventComponentDTO> iceTestEventComponentDTOs = iceTestEventDTO.getIceTestEventComponentDTOs();
//                if (iceTestEventComponentDTOs != null) {
//                    if (iceTestEventComponentDTOs.size() > 0) {
//                        IceTestEventComponentDTO component = iceTestEventComponentDTOs.get(0);
//                        if (component != null) {
//                            CdsCodeDTO evaluationValue = component.getEvaluationValue();
//                            if (evaluationValue != null) {
//                                status = evaluationValue.getDisplayName();
//                            } else {
//                                logger.warn("evaluationValue is null");
//                            }
//                        } else {
//                            logger.warn("component is null");
//                        }
//                        if (iceTestEventComponentDTOs.size() > 1) {
//                            for (IceTestEventComponentDTO item : iceTestEventComponentDTOs) {
//                                CdsCodeDTO evaluationValue = item.getEvaluationValue();
//                                if (evaluationValue != null) {
//                                    if (!"IGNORED".equals(evaluationValue.getCode())) {
//                                        if (status != null && !status.equals(evaluationValue.getDisplayName())) {
//                                            status = "click for details";
//                                            break;
//                                        }
//                                    }
//                                } else {
//                                    logger.warn("evaluationValue is null");
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    logger.warn("iceTestEventComponentDTOs is null!");
//                }
//            } else {
//                logger.warn("iceTestEventDTO is null!");
//            }
//        } catch (Exception e) {
//            DefaultExceptionHandler.handleException(e, getClass());
//        }
////        finally {
////            logger.logDuration(METHODNAME, start);
////        }
//        return status;
//    }
    public String getOnAdministrationDateUpdateIds() {
        final String METHODNAME = "getOnAdministrationDateUpdateIds ";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append(getTabViewUpdateId(true)).append(":ageAtAdministrationDate:ageAtAdministrationDate");
            stringBuilder.append(" ").append(getTabViewUpdateId(true));
            if (logger.isDebugEnabled()) {
                logger.info(METHODNAME, "\"", stringBuilder.toString(), "\"");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return stringBuilder.toString();
    }

    public String getOnSetAdministrationDateUpdateIds() {
        final String METHODNAME = "getOnSetAdministrationDateUpdateIds ";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append(getOnAdministrationDateUpdateIds());
            stringBuilder.append(" ").append(getTabViewUpdateId(true)).append(":administrationDateSet:administrationDateSet");
            stringBuilder.append(" ").append(getTabViewUpdateId(true)).append(":administrationDateDerived:administrationDateDerived");
            stringBuilder.append(" ").append(getTabViewUpdateId(true)).append(":setAdminDateAtAge:setAdminDateAtAge");
            if (logger.isDebugEnabled()) {
                logger.info(METHODNAME, "\"", stringBuilder.toString(), "\"");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return stringBuilder.toString();
    }

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
