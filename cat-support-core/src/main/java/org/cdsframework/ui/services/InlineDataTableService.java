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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.datatable.DataTableInterface;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.util.ClassUtils;
import org.cdsframework.util.DTOUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.enumeration.SourceOperation;
import org.cdsframework.util.enumeration.LoadStatus;
import org.cdsframework.util.enumeration.SourceMethod;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author HLN Consulting, LLC
 */
// This is not finished
public class InlineDataTableService {

    protected final LogUtils logger = LogUtils.getLogger(InlineDataTableService.class);

    private BaseModule module;
    private final DataAccessInterface dataAccessInterface;

    public InlineDataTableService(final BaseModule module) {
        this.module = module;

        dataAccessInterface = new DataAccessInterface() {
            @Override
            public Object[] getMessageArgs(BaseDTO dto, SourceMethod sourceMethod) throws Exception {
                return module.getCatApplication().getDtoMessageArgs(dto != null ? (Class) dto.getClass() : null, dto, module, sourceMethod);
            }

            @Override
            public BaseDTO getSearchCriteriaDTO(String childQueryClass) throws Exception {
                return InlineDataTableService.this.getSearchCriteriaDTO(childQueryClass);
            }

            @Override
            public DataTableInterface<BaseDTO> getDataTableInterface(String dataTableKey) {
//                logger.info("getDataTableInterface dataTableKey=", dataTableKey);
//                logger.info("getDataTableInterface getDataTableParamMap()=", getDataTableParamMap());
                DataTableParam dataTableParam = getDataTableParamMap().get(dataTableKey);
//                logger.info("dataTableParam=", dataTableParam);
//                logger.info("getDataTableInterface dataTableParam.getChildQueryClass()=", dataTableParam.getChildQueryClass());
//                logger.info("getDataTableInterface dataTableParam.getDataTableId()=", dataTableParam.getDataTableId());
//                logger.info("getDataTableInterface dataTableParam.getDtoType()=", dataTableParam.getDtoType());
                if (dataTableParam != null) {
                    return dataTableParam.getDataTableInterface();
                } else {
                    return null;
                }
            }

            @Override
            public void setDataTableInterface(DataTableInterface<BaseDTO> dataTableInterface, String dataTableKey) {
                getDataTableParamMap().get(dataTableKey).setDataTableInterface(dataTableInterface);
            }

            @Override
            public LoadStatus getLoadStatus(String dataTableKey) {
                return getDataTableParamMap().get(dataTableKey).getLoadStatus();
            }

            @Override
            public void setLoadStatus(LoadStatus loadStatus, String dataTableKey) {
                getDataTableParamMap().get(dataTableKey).setLoadStatus(loadStatus);
            }

            @Override
            public boolean isLazy(String dataTableKey) {
                return getDataTableParamMap().get(dataTableKey).isLazy();
            }

            @Override
            public String getDataTableId(String dataTableKey) {
                return getDataTableParamMap().get(dataTableKey).getDataTableId();
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
            public BaseModule getModule() {
                return module;
            }

            @Override
            public void initialize(List<BaseDTO> dtos) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public List<Class<? extends BaseDTO>> getChildClassDTOs(String queryClass, SourceOperation sourceOperation) {
                List<Class<? extends BaseDTO>> childClassDTOs = module.getChildClassDTOs(queryClass, sourceOperation);
                return childClassDTOs;
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
                return getDataTableParamMap().get(dataTableKey).getChildQueryClass();
            }

            @Override
            public boolean isInline() {
                return true;
            }
        };

    }

    public void add(String dataTableKey) throws Exception {
        final String METHODNAME = "add ";
        logger.debug(METHODNAME, "dataTableKey=", dataTableKey);
        DataTableParam dataTableParam = getDataTableParamMap().get(dataTableKey);
        DataAccessService.add(dataAccessInterface, dataTableParam.getDtoType(), dataTableKey);
    }

    public boolean delete(boolean cascade, BaseDTO dto) {
        final String METHODNAME = "delete ";
        logger.debug(METHODNAME, "cascade=", cascade, " module=", module,
                "dto=", dto.getClass(), "");

        String childQueryClass = getChildQueryClass(dto.getClass());
        logger.debug(METHODNAME, "childQueryClass=", childQueryClass);
        DataTableParam dataTableParam;
        boolean deleted = false;
        try {
            String dataTableKey = childQueryClass;
            dataTableParam = getDataTableParamMap().get(childQueryClass);
            if (dataTableParam == null) {
                dataTableKey = getDtoType(childQueryClass).getCanonicalName();
//                dataTableParam = getDataTableParamMap().get(dataTableKey);
            }
            deleted = DataAccessService.delete(dataAccessInterface, dataTableKey, dto, cascade);
        } catch (Exception e) {
            module.onExceptionMain(SourceMethod.deleteMain, e);
        }
        return deleted;
    }

    public int save(UIComponent uiComponent) throws Exception {
        // Returns 0 is nothing was saved
        return executeOperation(module, uiComponent, Operation.Save);
    }

    public void resetDTOs(BaseModule baseModule, UIComponent uiComponent) throws Exception {
        executeOperation(baseModule, uiComponent, Operation.Reset);
    }

    public void retrieveDTOs(BaseModule baseModule, UIComponent uiComponent) throws Exception {
        executeOperation(baseModule, uiComponent, Operation.Retrieve);
    }

    private enum Operation {
        Retrieve, Reset, Save
    };

    private int executeOperation(BaseModule baseModule, UIComponent uiComponent, Operation operation) throws Exception {
        final String METHODNAME = "executeOperation ";
        logger.debug(METHODNAME, "operation=", operation, " module=", module, " baseModule=", baseModule.getClass());
        int count = 0;
        Map<String, DataTableParam> lDataTableParamMap = getDataTableParamMap();
        for (Map.Entry<String, DataTableParam> entry : lDataTableParamMap.entrySet()) {
            DataTableParam dataTableParam = entry.getValue();
            String dataTableKey = entry.getKey();
            DataTable dataTable = getDataTable(dataTableKey);
            // If the datatable exists in the uiComponent, reset it
            if (UtilityMGR.uiComponentExists(uiComponent, dataTable)) {
                if (null != operation) {
                    switch (operation) {
                        case Retrieve:
                            DataAccessService.retrieve(dataAccessInterface, baseModule, dataTableParam.getChildQueryClass(), SourceOperation.FindBy, dataTableKey);
                            break;
                        case Reset:
                            DataAccessService.reset(dataAccessInterface, baseModule, dataTableParam.getChildQueryClass(), SourceOperation.FindBy, dataTableKey);
                            break;
                        case Save:
                            count += DataAccessService.save(dataAccessInterface, dataTableKey);
                            break;
                        default:
                            break;
                    }
                }
            } else {
                logger.debug(METHODNAME, "dataTable for dataTableKey=", dataTableKey,
                        " dataTable clientId=", (dataTable != null ? dataTable.getClientId() : "null"),
                        " DOES NOT exist in uiComponent=", uiComponent.getClientId());
            }
        }
        return count;
    }

    private BaseDTO getSearchCriteriaDTO(String childQueryClass) throws Exception {
        final String METHODNAME = "getSearchCriteriaDTO ";
        Class queryClass = ClassUtils.classForName(childQueryClass);
        logger.debug(METHODNAME, "childQueryClass=", childQueryClass, " queryClass=", queryClass);
        logger.debug(METHODNAME, "module=", module);
        Map<Class, ParentChildRelationship> parentChildRelationMapByQueryClass = DTOUtils.getParentChildRelationshipMapByQueryClass(module.getDtoClassType());
        logger.debug(METHODNAME, "parentChildRelationMapByQueryClass=", parentChildRelationMapByQueryClass);
        ParentChildRelationship parentChildRelationship = parentChildRelationMapByQueryClass.get(queryClass);
        logger.debug(METHODNAME, "parentChildRelationship=", parentChildRelationship);
        logger.debug(METHODNAME, "parentChildRelationship.childDtoClass()=", parentChildRelationship.childDtoClass());
        BaseDTO localSearchCriteriaDTO = parentChildRelationship.childDtoClass().newInstance();
        BaseDTO localParentDTO = module.getParentDTO();
        Object primaryKey = null;
        if (localParentDTO != null) {
            primaryKey = localParentDTO.getPrimaryKey();
        } else {
            logger.error(METHODNAME, "module.getParentDTO() was null!");
        }
        if (primaryKey == null) {
            logger.error(METHODNAME, "module.getParentDTO() primaryKey was null!");
        }
        localSearchCriteriaDTO.setForeignKey(module.getDtoClassType(), primaryKey);
        return localSearchCriteriaDTO;
    }

    public boolean isInlineDataTableExists(String dataTableKey) {
        return getDataTableParamMap().get(dataTableKey) != null;
    }

    /*
    dataTableKey is either the childQueryClass or the dtoType
     */
    public String getDataTableId(String dataTableKey) throws MtsException {
        final String METHODNAME = "getDataTableId ";
        String dataTableId;

        if (getDataTableParamMap().containsKey(dataTableKey)) {
            dataTableId = getDataTableParamMap().get(dataTableKey).getDataTableId();
        } else {
            Class classType = null;
            try {
                classType = ClassUtils.classForName(dataTableKey);
            } catch (NotFoundException e) {
                throw new MtsException(e.getMessage(), e);
            }
//            logger.info(METHODNAME, "classType=", classType);
            DataTableParam dataTableParam = new DataTableParam();
            dataTableParam.setDataTableKey(dataTableKey);
            // BaseDTO ?
            //if (classType.getSuperclass() != null && classType.getSuperclass().getCanonicalName().equals(BaseDTO.class.getCanonicalName())) {
            if (BaseDTO.class.isAssignableFrom(classType)) {
                // Key is DTO
                dataTableParam.setDtoType(classType);
                dataTableParam.setChildQueryClass(getChildQueryClass(classType));

            } // Child Query Class contains the interface
            else {
                dataTableParam.setChildQueryClass(dataTableKey);
                dataTableParam.setDtoType(getDtoType(dataTableKey));
            }
            dataTableId = classType.getCanonicalName().replace('.', '_');
            dataTableParam.setDataTableId(dataTableId);

//            dataTableIdMap.put(dataTableKey, dataTableId);
            getDataTableParamMap().put(dataTableKey, dataTableParam);
        }
//        logger.info(METHODNAME, "dataTableId=", dataTableId, " dataTableKey=", dataTableKey);
        return dataTableId;
    }

    private DataTable getDataTable(String dataTableKey) throws MtsException {
        return (DataTable) UtilityMGR.getUIComponentFromBaseId(getDataTableId(dataTableKey));
    }

    private String getChildQueryClass(Class<? extends BaseDTO> dtoClassType) {
        final String METHODNAME = "getChildQueryClass ";
        logger.debug(METHODNAME, "dtoClassType=", dtoClassType);
        Map<Class<? extends BaseDTO>, ParentChildRelationship> parentChildRelationshipMapByDTO = DTOUtils.getParentChildRelationshipMapByDTO(module.getDtoClassType());
        logger.debug(METHODNAME, "parentChildRelationshipMapByDTO=", parentChildRelationshipMapByDTO);
        ParentChildRelationship parentChildRelationship = parentChildRelationshipMapByDTO.get(dtoClassType);
        logger.debug(METHODNAME, "parentChildRelationship=", parentChildRelationship);

        String childQueryClass = parentChildRelationship.childQueryClass().toString();
        String searchString = "interface ";
        int indexOf = childQueryClass.indexOf(searchString);
        if (indexOf >= 0) {
            childQueryClass = childQueryClass.substring(searchString.length());
        }
        return childQueryClass;
    }

    private Class<? extends BaseDTO> getDtoType(String childQueryClass) throws MtsException {
        final String METHODNAME = "getDtoType ";
        Class queryClass = null;
        try {
            queryClass = ClassUtils.classForName(childQueryClass);
        } catch (NotFoundException e) {
            throw new MtsException(e.getMessage(), e);
        }
        logger.debug(METHODNAME, "queryClass=", queryClass);
        Map<Class, ParentChildRelationship> parentChildRelationMapByQueryClass = DTOUtils.getParentChildRelationshipMapByQueryClass(module.getDtoClassType());
        ParentChildRelationship parentChildRelationship = parentChildRelationMapByQueryClass.get(queryClass);
        if (parentChildRelationship != null) {
            return parentChildRelationship.childDtoClass();
        } else {
            return null;
        }

    }
    private Map<String, DataTableParam> dataTableParamMap = new HashMap<String, DataTableParam>();

    public Map<String, DataTableParam> getDataTableParamMap() {
        return dataTableParamMap;
    }

    public DataAccessInterface getDataAccessInterface() {
        return dataAccessInterface;
    }

}
