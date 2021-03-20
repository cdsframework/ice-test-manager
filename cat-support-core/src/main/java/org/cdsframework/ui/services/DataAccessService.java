/**
 * CAT Core support plugin project.
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
package org.cdsframework.ui.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.datatable.DataTableInterface;
import org.cdsframework.datatable.DataTableMGR;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.enumeration.SourceOperation;
import org.cdsframework.util.enumeration.LoadStatus;
import org.cdsframework.util.enumeration.PrePost;
import org.cdsframework.util.enumeration.SourceMethod;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.util.ComponentUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
public class DataAccessService {

    protected static LogUtils logger = LogUtils.getLogger(DataAccessService.class);

    public static int save(DataAccessInterface dataAccessInterface) throws Exception {
        return save(dataAccessInterface, null);
    }

    public static int save(DataAccessInterface dataAccessInterface, String dataTableKey) throws Exception {
        final String METHODNAME = "save ";
        int counter = 0;
        BaseModule module = dataAccessInterface.getModule();
        logger.debug(METHODNAME, "module=", module);
        try {
            DataTableInterface<BaseDTO> dataTableInterface = dataAccessInterface.getDataTableInterface(dataTableKey);
            if (dataTableInterface != null) {
                String queryClass = dataAccessInterface.getChildQueryClass(dataTableKey);
                module.prePostOperation(queryClass, null, PrePost.PreInlineSave, false);
                List<BaseDTO> dtoList = dataTableInterface.getDtoList();
                // Supports multiple NEW/MODIFIED state but current user interface prohibits more than one NEW
                for (BaseDTO dto : dtoList) {
                    DTOState prevDTOState = dto.getOperationDTOState();
//                        logger.info(METHODNAME, "dto.getDTOStates()=", dto.getDTOStates(), " prevDTOState=", prevDTOState);

                    if (prevDTOState != DTOState.UNSET) {
                        UUID uuid = dto.getUuid();
                        if (dto.isNew()) {
                            logger.debug(METHODNAME, "dto.getClass()=", dto.getClass(), " module.getParentDTO()=", module.getParentDTO());
                            if (!dto.getClass().equals(module.getDtoClassType())) {
                                Object primaryKey = module.getParentDTO().getPrimaryKey();
                                dto.setForeignKey(module.getDtoClassType(), primaryKey);
                            } else {
                                Object primaryKey = module.getParentMGR().getParentDTO().getPrimaryKey();
                                dto.setForeignKey(module.getParentMGR().getDtoClassType(), primaryKey);
                            }
                        }
                        PropertyBagDTO propertyBagDTO = dataAccessInterface.getPropertyBagDTO(queryClass, SourceOperation.Save);
                        module.prePostOperation(queryClass, dto, PrePost.PreInlineSave, false);
                        BaseDTO savedDTO = module.getMts().getGeneralMGR().save(dto, module.getSessionDTO(), propertyBagDTO);
                        counter++;
                        module.prePostOperation(queryClass, dto, PrePost.PostInlineSave, false);
                        // Display Message
                        module.displayMessage(savedDTO, prevDTOState, SourceMethod.saveMain);
                        savedDTO.setUuid(uuid);
                        dataTableInterface.addOrUpdateDTO(savedDTO);
                        module.markAssociatedDTOListDirty();
                    }
                }

                // Keep Lazy Load from loading
                if (dataAccessInterface.isLazy(dataTableKey)) {
                    dataAccessInterface.setLoadStatus(LoadStatus.StopClear, dataTableKey);
                } else {
                    dataAccessInterface.setLoadStatus(null, dataTableKey);
                }

                if (counter > 0) {
                    if (module.isDataTableExists()) {
                        //module.markAssociatedDTOListDirty();
                        module.setUpdateIds(METHODNAME, ComponentUtils.findComponentClientId(dataAccessInterface.getDataTableId(dataTableKey)));
                    }
                }
                module.prePostOperation(queryClass, null, PrePost.PostInlineSave, counter > 0);
            }

        } catch (Exception e) {
            dataAccessInterface.setLoadStatus(LoadStatus.Stop, dataTableKey);
            throw e;
        }
        logger.debug(METHODNAME, "counter=", counter);
        return counter;
    }

    public static boolean delete(DataAccessInterface dataAccessInterface, String dataTableKey, BaseDTO dto, boolean cascade) {
        final String METHODNAME = "delete ";
        BaseModule module = dataAccessInterface.getModule();
        logger.debug(METHODNAME, "cascade=", cascade, " module=", module,
                "dto=", dto, "");

        boolean deleted = false;
        try {
            String queryClass = dataAccessInterface.getChildQueryClass(dataTableKey);
            module.prePostOperation(queryClass, null, PrePost.PreInlineDelete, false);
            DataTableInterface<BaseDTO> dataTableInterface = dataAccessInterface.getDataTableInterface(dataTableKey);
            if (!dto.isNew()) {
                logger.debug(METHODNAME, "dto.getDTOStates()", dto.getDTOStates());
                Object[] messageArgs = dataAccessInterface.getMessageArgs(dto, SourceMethod.deleteMain);
                dataTableInterface.remove(dto);
                dto.delete(cascade);
                PropertyBagDTO propertyBagDTO = dataAccessInterface.getPropertyBagDTO(dataAccessInterface.getChildQueryClass(dataTableKey), SourceOperation.Delete);
                module.prePostOperation(queryClass, dto, PrePost.PreInlineDelete, false);
                module.getMts().getGeneralMGR().save(dto, module.getSessionDTO(), propertyBagDTO);
                module.prePostOperation(queryClass, dto, PrePost.PostInlineDelete, true);
                module.getMessageMGR().displayInfo(module.getDeletedMessageKey(), messageArgs);
                module.markAssociatedDTOListDirty();
            } else {
                dataTableInterface.delete(dto, cascade);
            }

            BaseDTO rowData = dataTableInterface.getRowData(dto);
            if (rowData != null) {
                logger.debug(METHODNAME, "rowData.getDTOStates()", rowData.getDTOStates());
            }
            deleted = true;

            navigatePage(dataAccessInterface, dataTableKey);
            dataTableInterface.setRowCount(dataTableInterface.getRowCount() - 1);

            // Keep Lazy Load from loading
            if (dataAccessInterface.isLazy(dataTableKey)) {
                dataAccessInterface.setLoadStatus(LoadStatus.StopClear, dataTableKey);
            } else {
                dataAccessInterface.setLoadStatus(null, dataTableKey);
            }

            // Update the DataTable
            module.setUpdateIds(METHODNAME, ComponentUtils.findComponentClientId(dataAccessInterface.getDataTableId(dataTableKey)));
            UtilityMGR.setCallbackParam("deleted", deleted);

            module.prePostOperation(queryClass, null, PrePost.PostInlineDelete, deleted);
        } catch (Exception e) {
            dataAccessInterface.setLoadStatus(LoadStatus.Stop, dataTableKey);
            module.onExceptionMain(SourceMethod.deleteMain, e);
        }
        return deleted;

    }

    public static void add(DataAccessInterface dataAccessInterface, Class<? extends BaseDTO> dtoClassType, String dataTableKey) throws Exception {
        BaseModule module = dataAccessInterface.getModule();
        try {
            // create a new instance
            BaseDTO newDTO = dtoClassType.newInstance();
            add(newDTO, dataAccessInterface, dataTableKey);
        } catch (Exception e) {
            module.onExceptionMain(SourceMethod.addMain, e);
        }
    }

    public static void add(BaseDTO newDTO, DataAccessInterface dataAccessInterface, String dataTableKey) throws Exception {
        final String METHODNAME = "add ";
        logger.debug(METHODNAME, "dataTableKey=", dataTableKey);
        BaseModule module = dataAccessInterface.getModule();

        try {
            String queryClass = dataAccessInterface.getChildQueryClass(dataTableKey);
            module.prePostOperation(queryClass, null, PrePost.PreInlineAdd, false);

            // Lookup the dataTableInterface
            DataTableInterface<BaseDTO> dataTableInterface = dataAccessInterface.getDataTableInterface(dataTableKey);

            // Add the new DTO
            dataTableInterface.addOrUpdateDTO(newDTO);
            DataTable dataTable = getDataTable(dataAccessInterface.getDataTableId(dataTableKey));
            dataTable.setRowIndex(0);

            boolean lazy = dataAccessInterface.isLazy(dataTableKey);
            if (!lazy) {
                dataTable.setFirst(0);
            }
            dataTableInterface.setRowCount(dataTableInterface.getRowCount() + 1);
            dataAccessInterface.setLoadStatus(LoadStatus.Stop, dataTableKey);

            PostRenderService.postRenderUIComponent(module, dataTable);

            // Update the datatable
            module.setUpdateIds(METHODNAME, ComponentUtils.findComponentClientId(dataAccessInterface.getDataTableId(dataTableKey)));
            module.prePostOperation(queryClass, newDTO, PrePost.PostInlineAdd, true);

        } catch (Exception e) {
            module.onExceptionMain(SourceMethod.addMain, e);
        }
    }

    private static DataTable getDataTable(String dataTableId) throws MtsException {
        return (DataTable) UtilityMGR.getUIComponentFromBaseId(dataTableId);
    }

    public static void retrieve(DataAccessInterface dataAccessInterface, BaseModule module, String queryClass, SourceOperation sourceOperation) throws Exception {
        retrieve(dataAccessInterface, module, queryClass, sourceOperation, null);
    }

    public static void retrieve(DataAccessInterface dataAccessInterface, BaseModule module, String queryClass, SourceOperation sourceOperation, String dataTableKey) throws Exception {
        final String METHODNAME = "retrieve ";
        logger.debug(METHODNAME, "dataAccessInterface=", dataAccessInterface.getModule(),
                " module=", module);

        logger.debug(METHODNAME, "retrieving dataTable for queryClass=", queryClass,
                " dataTableParam dataTableId=", dataAccessInterface.getDataTableId(dataTableKey),
                " dataTableParam dataTableKey=", dataTableKey);
        dataAccessInterface.getModule().prePostOperation(queryClass, null, PrePost.PreRetrieve, false);
        dataAccessInterface.setLoadStatus(null, dataTableKey);

        if (!dataAccessInterface.isLazy(dataTableKey)) {
            PropertyBagDTO propertyBagDTO = dataAccessInterface.getPropertyBagDTO(queryClass, sourceOperation);
            propertyBagDTO.setChildClassDTOs(dataAccessInterface.getChildClassDTOs(queryClass, sourceOperation));
            BaseDTO searchCriteriaDTO = dataAccessInterface.getSearchCriteriaDTO(queryClass);

            // Retrieve the dtos
            List<BaseDTO> dtos = module.getMts().getGeneralMGR().findByQueryList(searchCriteriaDTO, module.getSessionDTO(), propertyBagDTO);

            // Set the DTOs on the parentDTO
            logger.debug(METHODNAME, "retrieved dtos.size()=", dtos.size());

            // Get the inline Datatable Interface
            DataTableInterface<BaseDTO> dataTableInterface = dataAccessInterface.getDataTableInterface(dataTableKey);
            logger.debug(METHODNAME, "dataTableInterface=", dataTableInterface);
            if (dataTableInterface == null) {
                dataTableInterface = new DataTableMGR<BaseDTO>(dtos, DataAccessService.class);
            } else {
                dataTableInterface.setWrappedData(dtos);
            }
            // Update the datatable manager
            dataAccessInterface.setDataTableInterface(dataTableInterface, dataTableKey);
            dataAccessInterface.getModule().prePostOperation(queryClass, null, PrePost.PostRetrieve, dataAccessInterface.getDataTableInterface(dataTableKey).getRowCount() > 0);
        } else {
            dataAccessInterface.initializeLazyDataInterface(queryClass, sourceOperation, dataTableKey);
        }
        if (module.isDataTableExists()) {
            module.setUpdateIds(METHODNAME, ComponentUtils.findComponentClientId(dataAccessInterface.getDataTableId(dataTableKey)));
        }

    }

    public static void reset(DataAccessInterface dataAccessInterface, BaseModule module, String queryClass) throws Exception {
        reset(dataAccessInterface, module, queryClass, SourceOperation.FindBy, null);
    }

    public static void reset(DataAccessInterface dataAccessInterface, BaseModule module, String queryClass, SourceOperation sourceOperation, String dataTableKey) throws Exception {
        final String METHODNAME = "reset ";

        logger.debug(METHODNAME, "module=", module, " dataAccessInterface=", dataAccessInterface.getModule());

        DataTableInterface<BaseDTO> dataTableInterface = dataAccessInterface.getDataTableInterface(dataTableKey);
        logger.debug(METHODNAME, "dataTable for queryClass=", queryClass,
                " dataTableInterface=", dataTableInterface,
                " lazy=", dataAccessInterface.isLazy(dataTableKey),
                " dataTableParam dataTableId=", dataAccessInterface.getDataTableId(dataTableKey),
                " dataTableParam dataTableKey=", dataTableKey);

        dataAccessInterface.setLoadStatus(LoadStatus.StopClear, dataTableKey);

        if (dataAccessInterface.isLazy(dataTableKey)) {
            if (dataTableInterface == null) {
                dataAccessInterface.initializeLazyDataInterface(queryClass, sourceOperation, dataTableKey);
            }
            dataTableInterface = dataAccessInterface.getDataTableInterface(dataTableKey);
            logger.debug(METHODNAME, " dataTableInterface=", dataTableInterface);
        } else if (dataTableInterface == null) {
            dataTableInterface = new DataTableMGR<BaseDTO>(new ArrayList<BaseDTO>(), DataAccessService.class);
            dataAccessInterface.setDataTableInterface(dataTableInterface, dataTableKey);
        }
        dataTableInterface.getDtoList().clear();
        if (dataAccessInterface.isLazy(dataTableKey)) {
            dataTableInterface.setRowCount(0);
        }

        if (module.isDataTableExists()) {
            module.setUpdateIds(METHODNAME, ComponentUtils.findComponentClientId(dataAccessInterface.getDataTableId(dataTableKey)));
        }
    }

    public static void navigatePage(DataAccessInterface dataAccessInterface, String dataTableKey) throws MtsException {
        final String METHODNAME = "navigatePage ";
//        logger.info(METHODNAME);
        boolean lazy = dataAccessInterface.isLazy(dataTableKey);
        if (lazy) {
            DataTableInterface dataTableInterface = dataAccessInterface.getDataTableInterface(dataTableKey);
            List<BaseDTO> dtos = dataTableInterface.getDtoList();
//            logger.info(METHODNAME, "dtos.size()=", dtos.size());

            if (dtos.isEmpty()) {
                int pageSize = dataTableInterface.getPageSize();
                int rowCount = dataTableInterface.getRowCount();
                int rowOffset = dataTableInterface.getRowOffset() - pageSize;
//                logger.info(METHODNAME, "rowOffset=", rowOffset, "origRowOffset=", dataTableInterface.getRowOffset(), " pageSize=", pageSize, " rowCount=", rowCount);

                // if we have a new row offset that is between 0 and the row count
                if (rowOffset >= 0 && rowOffset < rowCount) {
                    // load the new page
                    dtos = dataTableInterface.load(rowOffset, pageSize, dataTableInterface.getSortField(), dataTableInterface.getSortOrder(), dataTableInterface.getFilters());
                    dataTableInterface.setWrappedData(dtos);
                    DataTable dataTable = getDataTable(dataAccessInterface.getDataTableId(dataTableKey));
                    if (dataTable != null) {
                        dataTable.setFirst(rowOffset);
                        logger.debug(METHODNAME, "rowOffset=", rowOffset);

                    }
                }
            }
        }

    }

    //
    // BaseModule saveMain, need to move preSave, postSave into prePost call to support older version of preSave/postSave
    // Needs testing
    //
    private static boolean saveMain(DataAccessInterface dataAccessInterface, String dataTableKey, BaseDTO dto, boolean displayMessage) {
        final String METHODNAME = "saveMain ";

        long start = System.nanoTime();
        boolean saved = false;
        String javaScriptToExecute = null;
        BaseModule module = dataAccessInterface.getModule();
        String queryClass = dataAccessInterface.getChildQueryClass(dataTableKey);

        try {
            // Get the ParentDTO
//            T localParentDTO = getParentDTO();
            // Is it null?, it shouldn't be!
            if (dto != null) {
                //module.preSave(actionEvent);
                module.prePostOperation(queryClass, dto, PrePost.PreSave, false);
                DTOState prevDTOState = dto.getOperationDTOState();

                // if there is a bug and the dto is saved with a delete state on it the deletes succeeds
                // but then the return result is null which causes an NPE - this should not happen
                if (prevDTOState == DTOState.DELETED) {
                    throw new CatException(METHODNAME + "deleted dto was submitted to saveMain!");
                }

                if (logger.isDebugEnabled()) {
                    logger.info("prevDTOState: ", prevDTOState);
                    logger.info(METHODNAME, "isSaveOnBaseParentMGR()=", module.isSaveOnBaseParentMGR());
                }

                logger.info(METHODNAME, "saveImmediately=", module.isSaveImmediately());

                if (module.isChild() && !module.isSaveImmediately()) {

                    // Call Save on Base Parent MGR ?
                    if (module.getParentMGR() != null
                            && module.isSaveOnBaseParentMGR()
                            && module.getParentMGR().getParentDTO() != null
                            && module.getParentMGR().getParentDTO().getDTOState() == DTOState.UNSET) {

                        List<BaseModule> mgrList = new LinkedList<BaseModule>();
                        mgrList.add(module);
                        module.getAscendantList(mgrList);
                        Collections.reverse(mgrList);
                        logger.debug(METHODNAME, "mgrList: ", mgrList);

                        for (BaseModule baseModule : mgrList) {
                            if (baseModule.getParentMGR() != null) {
                                logger.debug(METHODNAME, "processing saveOnBaseParentMGR: ", baseModule);
                                BaseDTO baseDTO = baseModule.getParentDTO();
                                logger.debug(METHODNAME, "processing saveOnBaseParentMGR DTO state: ", baseDTO.getDTOState());

                                boolean changedChildOrChildren = baseDTO.getDTOState() != DTOState.UNSET;
                                logger.debug(METHODNAME, "initial changedChildOrChildren: ", changedChildOrChildren);
                                if (!changedChildOrChildren) {
                                    for (Class childClass : baseDTO.getChildDTOMap().keySet()) {
                                        for (BaseDTO item : baseDTO.getChildrenDTOs(childClass)) {
                                            if (item.getOperationDTOState() != DTOState.UNSET) {
                                                changedChildOrChildren = true;
                                                break;
                                            }
                                        }
                                        if (changedChildOrChildren) {
                                            break;
                                        }
                                    }
                                }

                                logger.debug(METHODNAME, "processing saveOnBaseParentMGR changedChildOrChildren: ", changedChildOrChildren);
                                if (changedChildOrChildren) {
                                    // perform the save
//                                    baseModule.saveOnBaseParent(baseDTO);
                                    break;
                                }
                            }

                        }

//                        String editFormId = this.getEditFormId();
//                        javaScriptToExecute = String.format("resetChangedState('%s')", editFormId);
                    } else {
                        if (module.isSaveOnBaseParentMGR()) {
                            module.getMessageMGR().displayError("saveOnBaseMGRWithParentNotUnset");
                            logger.warn("isSaveOnBaseParentMGR() is on but parentMGR DTO is not in an unset state!");
                        } else {
                            logger.warn(METHODNAME, "!isSaveOnBaseParentMGR(): ", !module.isSaveOnBaseParentMGR());
                        }
                        dto = module.getDataTableMGR().addOrUpdateDTO(dto);
                        module.setParentDTO(dto);
//                        setParentDTO(localParentDTO);
                        if (module.getParentMGR() != null) {
                            if (dto.getDTOState() != DTOState.UNSET) {
                                javaScriptToExecute = module.getParentMGR().getJSSetFormChanged();
//                                javaScriptToExecute = getJSSetFormChanged(getParentMGR());
                            }
                            //javaScriptToExecute = String.format("onChange('%s')", getParentMGR().getEditFormId());
                        } else {
                            throw new CatException(logger.error("getParentMGR() was null - did you forget to register the child manager on the parent manager?"));
                        }
                    }

                } else {
                    UUID uuid = dto.getUuid();
                    logger.info(METHODNAME, "uuid=", uuid.toString());

                    if (prevDTOState != DTOState.UNSET) {
                        if (module.isChild() && module.isSaveImmediately()) {
                            if (dto.isNew()) {
                                Object primaryKey = module.getParentMGR().getParentDTO().getPrimaryKey();
                                dto.setForeignKey(module.getParentMGR().getDtoClassType(), primaryKey);
                            }
                        }
//                        dto = module.mgrSave(dto);
                        PropertyBagDTO propertyBagDTO = dataAccessInterface.getPropertyBagDTO(queryClass, SourceOperation.Save);
                        dto = module.getMts().getGeneralMGR().save(dto, module.getSessionDTO(), propertyBagDTO);

                        dto.setUuid(uuid);
                        dto = module.getDataTableMGR().addOrUpdateDTO(dto);
                        module.setParentDTO(dto);
                        module.setLastDTOState(prevDTOState);
                    }
                    module.markAssociatedDTOListDirty();
                    // Is there a call back registered?
                    module.onSavedCallBack(dto);
                    // Is there a call back registered?
//                    if (module.getOnSavedCallback() != null) {
//                        module.getOnSavedCallback().onSaved(module.getParentDTO());
//                    }
                }

                logger.info("displayMessage: ", displayMessage);
                if (displayMessage) {
                    // Display Message
                    module.displayMessage(module.getParentDTO(), prevDTOState, SourceMethod.saveMain);
//                    Object[] dtoMessageArgs = dataAccessInterface.getMessageArgs(getParentDTO(), SourceMethod.saveMain);
//                    if (isSaveOnBaseParentMGR() && getParentDTO() != null && getParentDTO().getOperationDTOState() != DTOState.UNSET) {
//                        messageMGR.displayWarn("dtoNotSaved", dtoMessageArgs);
//                    } else if (prevDTOState == DTOState.NEW || prevDTOState == DTOState.NEWMODIFIED) {
//                        messageMGR.displayInfo(getAddedMessageKey(), dtoMessageArgs);
//                    } else if (prevDTOState == DTOState.UPDATED) {
//                        messageMGR.displayInfo(getUpdatedMessageKey(), dtoMessageArgs);
//                    } else if (prevDTOState == DTOState.UNSET) {
////                        messageMGR.displayInfo(getUnchangedMessageKey(), getMessageArgs());
//                    }
                }

//                postSave(actionEvent);
                saved = true;
                module.prePostOperation(queryClass, dto, PrePost.PostSave, saved);

            } else {
                throw new CatException("The saved row was null!");
            }
        } catch (Exception e) {
            dataAccessInterface.setLoadStatus(LoadStatus.Stop, dataTableKey);
//            module.setLoadStatus(LoadStatus.Stop);
            module.onExceptionMain(SourceMethod.saveMain, e);
        } finally {
            logger.info(METHODNAME, "module.isDataTableExists()=", module.isDataTableExists());
            if (saved) {
                if (module.isDataTableExists()) {
                    DTOState lastDTOState = module.getLastDTOState();
                    if (lastDTOState != null && (lastDTOState == DTOState.NEW || lastDTOState == DTOState.NEWMODIFIED)) {
                        DataTableInterface<BaseDTO> dataTableInterface = dataAccessInterface.getDataTableInterface(dataTableKey);
                        dataTableInterface.setRowCount(dataTableInterface.getRowCount() + 1);
//                        getDataTableMGR().setRowCount(getDataTableMGR().getRowCount() + 1);
                    }
                    dataAccessInterface.setLoadStatus(LoadStatus.Stop, dataTableKey);
//                    setLoadStatus(LoadStatus.StopClear);
                    module.setUpdateIds(METHODNAME, ComponentUtils.findComponentClientId(dataAccessInterface.getDataTableId(dataTableKey)));
//                    setUpdateIds(METHODNAME, getDataTableUpdateId());
                }
                logger.debug(METHODNAME, "javaScriptToExecute=", javaScriptToExecute);
                if (!StringUtils.isEmpty(javaScriptToExecute)) {
                    RequestContext.getCurrentInstance().execute(javaScriptToExecute);
                }
            }
            UtilityMGR.setCallbackParam("saved", saved);
            logger.logDuration(LogLevel.DEBUG, METHODNAME, start);                                                                            
        }
        return saved;
    }

    public static DataAccessInterface getDataAccessInterface(final BaseModule module) {
        DataAccessInterface dataAccessInterface = new DataAccessInterface() {
            @Override
            public BaseModule getModule() {
                return module;
            }

            @Override
            public Object[] getMessageArgs(BaseDTO dto, SourceMethod sourceMethod) throws Exception {
                return module.getCatApplication().getDtoMessageArgs(dto != null ? (Class) dto.getClass() : null, dto, module, sourceMethod);
            }

            @Override
            public BaseDTO getSearchCriteriaDTO(String queryClass) throws Exception {
                if (module.isChild()) {
                    return module.getSearchCriteriaDTO(module.getParentMGR());
                } else {
                    return module.getSearchCriteriaDTO();
                }
            }

            @Override
            public void initializeLazyDataInterface(String queryClass, SourceOperation sourceOperation, String dataTableKey) {
                final String METHODNAME = "initializeLazyDataInterface ";
                logger.debug(METHODNAME + "initializing lazy loader for queryClass=", queryClass);
                LazyDataTableService lazyDataTableService = new LazyDataTableService(this, sourceOperation, dataTableKey);
                DataTableInterface<BaseDTO> dataInterface = lazyDataTableService.initializeDataInterface(queryClass);
                setDataTableInterface(dataInterface, dataTableKey);
            }

            @Override
            public DataTableInterface<BaseDTO> getDataTableInterface(String dataTableKey) {
                return module.getDataTableMGR();
            }

            @Override
            public void setDataTableInterface(DataTableInterface<BaseDTO> dataTableInterface, String dataTableKey) {
                module.setDataTableMGR(dataTableInterface);
            }

            @Override
            public void setLoadStatus(LoadStatus loadStatus, String dataTableKey) {
                module.setLoadStatus(loadStatus);
            }

            @Override
            public LoadStatus getLoadStatus(String dataTableKey) {
                return module.getLoadStatus();
            }

            @Override
            public boolean isLazy(String dataTableKey) {
                return module.isLazy();
            }

            @Override
            public String getDataTableId(String dataTableKey) {
                return module.getDataTableId();
            }

            @Override
            public void initialize(List<BaseDTO> dtos) {
                if (!isLazy(null)) {
                    setDataTableInterface(new DataTableMGR<BaseDTO>(dtos, getClass()), null);
                } else {
                    initializeLazyDataInterface(null, null, null);
                }
            }

            @Override
            public List<Class<? extends BaseDTO>> getChildClassDTOs(String queryClass, SourceOperation sourceOperation) {
                return module.getChildClassDTOs(queryClass, sourceOperation);
            }

            @Override
            public PropertyBagDTO getPropertyBagDTO(String queryClass, SourceOperation sourceOperation) {
                final String METHODNAME = "getPropertyBagDTO ";
                logger.debug(METHODNAME, "queryClass=", queryClass);
                PropertyBagDTO propertyBagDTO = module.getPropertyBagDTO(queryClass, sourceOperation);
                if (sourceOperation == SourceOperation.FindBy || sourceOperation == SourceOperation.Search) {
                    propertyBagDTO.setQueryClass(queryClass);
                }
                return propertyBagDTO;
            }

            @Override
            public String getChildQueryClass(String dataTableKey) {
                return module.getChildQueryClass().getSimpleName();
            }

            @Override
            public boolean isInline() {
                return false;
            }
        };

        return dataAccessInterface;
    }
}
