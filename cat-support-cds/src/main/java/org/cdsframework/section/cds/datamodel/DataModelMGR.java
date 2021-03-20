/**
 * CAT CDS support plugin project.
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
package org.cdsframework.section.cds.datamodel;

import java.util.ArrayList;
import java.util.Collections;
import org.cdsframework.base.BaseTreeMGR;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.dto.DataModelClassDTO;
import org.cdsframework.dto.DataModelClassNodeDTO;
import org.cdsframework.dto.DataModelDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.enumeration.DataModelNodeType;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.exceptions.EmptyResultsException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.lookup.DataModelDTOList;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.comparator.DataModelClassNodeComparator;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class DataModelMGR extends BaseTreeMGR<DataModelDTO> {

    private static final long serialVersionUID = -6145251177027755677L;

    @Inject
    private DataModelClassMGR dataModelClassMGR;
    @Inject
    private DataModelDTOList dataModelDTOList;
    @Inject
    private DataModelNodeMGR dataModelNodeMGR;
    
    @Override
    protected void treeInitialize() {
        //addOnRowSelectChildClassDTO(DataModelClassDTO.class);
        //setLazy(true) -- causes exception if true;
        setInitialQueryClass("FindAll");
        setSortBy("name");
        setTopNodeName("model");
        registerChild(DataModelClassDTO.ByModelId.class, dataModelClassMGR);
        registerChild(DataModelClassNodeDTO.ByModelId.class, dataModelNodeMGR);
        setAssociatedList(dataModelDTOList);
        setSortSelectItems(new String[][]{{"Name", "name"}});
        setSaveImmediately(true);
    }
    
    @Override
    public void registerTabComponents() {
        final String METHODNAME = "registerTabLazyLoadComponents ";
        logger.info(METHODNAME);
        // Register the Components
        getTabService().registeredUIComponent(1, dataModelNodeMGR);
        getTabService().registeredUIComponent(2, dataModelClassMGR);
    }

    @Override
    protected void onNodeExpand(NodeExpandEvent event) {
        final String METHODNAME = "onNodeExpand ";
        logger.info(METHODNAME, "event: ", event);
        long start = System.nanoTime();
        try {
            TreeNode treeNode = event.getTreeNode();
            Object data = treeNode.getData();
            logger.info(METHODNAME, "data: ", data);
            if (data instanceof DataModelDTO) {
                updateDataModelDTO((DataModelDTO) data);
            } else if (data instanceof DataModelClassNodeDTO) {
                updateDataModelClassNodeDTO((DataModelClassNodeDTO) data);
            } else {
                throw new CatException("unprocessed data type: " + data.getClass());

            }
            updateTreeTableMain();
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logDuration(METHODNAME, start);
        }
    }

    private DataModelDTO updateDataModelDTO(DataModelDTO dataModelDTO) {
        List<DataModelClassNodeDTO> result = null;
        final String METHODNAME = "updateDataModelDTO ";
        try {
            if (dataModelDTO != null) {
                logger.debug(METHODNAME, "dataModelDTO: ", dataModelDTO.getName());
                if (getRetrievedObjects().contains(dataModelDTO)) {
                    logger.info(METHODNAME, "using cached model reference for: ", dataModelDTO);
                    dataModelDTO = (DataModelDTO) getRetrievedObjects().get(getRetrievedObjects().indexOf(dataModelDTO));
                } else {
                    logger.info(METHODNAME, "caching model reference for: ", dataModelDTO);
                    DataModelClassNodeDTO dataModelClassNodeDTO = new DataModelClassNodeDTO();
                    dataModelClassNodeDTO.setModelId(dataModelDTO.getModelId());
                    PropertyBagDTO propertyBag = getNewPropertyBagDTO();
                    propertyBag.setQueryClass("ByModelId");
                    result = getGeneralMGR().findByQueryList(dataModelClassNodeDTO, getSessionDTO(), propertyBag);
//                    for (DataModelRootNodeDTO item : result) {
//                        updateDataModelClassNodeDTOs(item.getDataModelClassDTO());
//                    }
                    DataModelClassNodeComparator dataModelClassNodeComparator = new DataModelClassNodeComparator();
                    Collections.sort(result, dataModelClassNodeComparator);

                    dataModelDTO.setChildrenDTOs(DataModelClassNodeDTO.ByModelId.class, (List) result);
                    markRetrievedObject(dataModelDTO);
                }
                getDataTableMGR().setRowData(dataModelDTO);
            } else {
                logger.error(METHODNAME, "dataModelClassDTO is null!");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return dataModelDTO;
    }

    private DataModelClassNodeDTO updateDataModelClassNodeDTO(DataModelClassNodeDTO dataModelClassNodeDTO) {
        dataModelClassNodeDTO.setDataModelClassDTO(updateDataModelClassDTO(dataModelClassNodeDTO.getDataModelClassDTO()));
        return dataModelClassNodeDTO;
    }

    private DataModelClassDTO updateDataModelClassDTO(DataModelClassDTO dataModelClassDTO) {
        List<DataModelClassNodeDTO> result = null;
        final String METHODNAME = "updateDataModelClassDTO ";
        try {
            if (dataModelClassDTO != null) {
                logger.debug(METHODNAME, "dataModelClassDTO: ", dataModelClassDTO.getName());
                if (getRetrievedObjects().contains(dataModelClassDTO)) {
                    logger.info(METHODNAME, "using cached class reference: ", dataModelClassDTO);
                    dataModelClassDTO = (DataModelClassDTO) getRetrievedObjects().get(getRetrievedObjects().indexOf(dataModelClassDTO));
                } else {
                    DataModelClassNodeDTO dataModelClassNodeDTO = new DataModelClassNodeDTO();
                    if (dataModelClassDTO.getClassId() != null) {
                        dataModelClassNodeDTO.setClassId(dataModelClassDTO.getClassId());
                        PropertyBagDTO propertyBag = getNewPropertyBagDTO();
                        propertyBag.setQueryClass("ByClassId");
                        result = getGeneralMGR().findByQueryList(dataModelClassNodeDTO, getSessionDTO(), propertyBag);
//                        for (DataModelClassNodeDTO item : result) {
//                            updateDataModelClassNodeDTOs(item.getDataModelClassDTO());
//                        }
                        addSuperClassNodeDTOs(dataModelClassDTO.getDataModelSuperClassDTO(), result);
                        DataModelClassNodeComparator dataModelClassNodeComparator = new DataModelClassNodeComparator();
                        Collections.sort(result, dataModelClassNodeComparator);

                        dataModelClassDTO.setChildrenDTOs(DataModelClassNodeDTO.ByClassId.class, (List) result);
                        markRetrievedObject(dataModelClassDTO);
                    } else {
                        logger.error(METHODNAME, "dataModelClassDTO.getClassId() is null!");
                    }
                }
                for (DataModelDTO dataModelDTO : getDataTableMGR().getDtoList()) {
                    for (DataModelClassNodeDTO dataModelClassNodeDTO : dataModelDTO.getDataModelClassNodeDTOs()) {
                        if (dataModelClassDTO.equals(dataModelClassNodeDTO.getDataModelClassDTO())) {
                            dataModelClassNodeDTO.setDataModelClassDTO(dataModelClassDTO);
                        } else if (dataModelClassDTO.equals(dataModelClassNodeDTO.getDataModelClassDTO().getDataModelSuperClassDTO())) {
                            dataModelClassNodeDTO.getDataModelClassDTO().setDataModelSuperClassDTO(dataModelClassDTO);
                        }
                    }
                }
            } else {
                logger.error(METHODNAME, "dataModelClassDTO is null!");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return dataModelClassDTO;
    }

    private void addSuperClassNodeDTOs(DataModelClassDTO superClassDTO, List<DataModelClassNodeDTO> result) {
        final String METHODNAME = "addSuperClassNodeDTOs ";
        try {
            if (superClassDTO != null) {
                PropertyBagDTO propertyBag = getNewPropertyBagDTO();
                propertyBag.setQueryClass("ByClassId");
                DataModelClassNodeDTO dataModelClassNodeDTO = new DataModelClassNodeDTO();
                dataModelClassNodeDTO.setClassId(superClassDTO.getClassId());
                List<DataModelClassNodeDTO> superClassNodeDTOs = getGeneralMGR().findByQueryList(dataModelClassNodeDTO, getSessionDTO(), propertyBag);
                for (DataModelClassNodeDTO item : superClassNodeDTOs) {
                    item.setParentDataModelClassDTO(superClassDTO);
//                    updateDataModelClassNodeDTOs(item.getDataModelClassDTO());
                }
                result.addAll(superClassNodeDTOs);
                addSuperClassNodeDTOs(superClassDTO.getDataModelSuperClassDTO(), result);
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    private void markRetrievedObject(Object object) {
        final String METHODNAME = "markRetrievedObject ";
        logger.info(METHODNAME, "object: ", object);
        getRetrievedObjects().add(object);
    }

    @Override
    protected void onNodeSelect(NodeSelectEvent event) {
        final String METHODNAME = "onNodeSelect ";
        RequestContext context = RequestContext.getCurrentInstance();
        String editDialogWidgetVar = null;
        String updateIds = null;
        Object nodeData = event != null ? event.getTreeNode() != null ? event.getTreeNode().getData() : null : null;
        if (nodeData != null) {
            if (nodeData instanceof DataModelDTO) {
                onRowSelectMain(UtilityMGR.getEmulatedSelectEvent((DataModelDTO) nodeData));
                editDialogWidgetVar = getEditDialogWidgetVar("edit");
                updateIds = getOnRowSelectUpdateIds();
            }
        }
        if (editDialogWidgetVar != null) {
            context.execute("PF('" + editDialogWidgetVar + "').show()");
        }
        if (updateIds != null) {
            setUpdateIds(METHODNAME, updateIds);
        }
    }

//    @Override
//    protected void onNodeEdit(Object nodeData) {
//        final String METHODNAME = "onNodeEdit ";
//        logger.info(METHODNAME, "nodeData: ", nodeData);
//        RequestContext context = RequestContext.getCurrentInstance();
//        String editDialogWidgetVar = null;
//        String updateIds = null;
//        if (nodeData != null) {
//            selectParentDataModelDTO(nodeData);
//            if (nodeData instanceof DataModelDTO) {
//                onRowSelectMain(UtilityMGR.getEmulatedSelectEvent((DataModelDTO) nodeData));
//                editDialogWidgetVar = getEditDialogWidgetVar("edit");
//                updateIds = getOnRowSelectUpdateIds();
//            } else if (nodeData instanceof DataModelClassNodeDTO) {
//                dataModelNodeMGR.onRowSelectMain(UtilityMGR.getEmulatedSelectEvent((DataModelClassNodeDTO) nodeData));
//                editDialogWidgetVar = dataModelNodeMGR.getEditDialogWidgetVar("edit");
//                updateIds = dataModelNodeMGR.getOnRowSelectUpdateIds();
//            }
//            if (editDialogWidgetVar != null) {
//                context.execute("PF('" + editDialogWidgetVar + "').show()");
//            }
//            if (updateIds != null) {
//                setUpdateIds(METHODNAME, updateIds);
//            }
//        }
//    }

    private void selectParentDataModelDTO(Object nodeData) {
        final String METHODNAME = "selectParentDataModelDTO ";
        logger.info(METHODNAME, "nodeData: ", nodeData);
        try {
            if (nodeData instanceof DataModelDTO) {
                onRowSelectMain(UtilityMGR.getEmulatedSelectEvent((DataModelDTO) nodeData));
            } else {
                DataModelClassNodeDTO dataModelClassNodeDTO = (DataModelClassNodeDTO) nodeData;
                if (dataModelClassNodeDTO.getModelId() != null) {
                    DataModelDTO dataModelDTO = new DataModelDTO();
                    dataModelDTO.setModelId(dataModelClassNodeDTO.getModelId());
                    DataModelDTO rowData = getDataTableMGR().getRowData(dataModelDTO);
                    onRowSelectMain(UtilityMGR.getEmulatedSelectEvent(rowData));
//                } else if (dataModelClassNodeDTO.getParentNodeId() != null) {
//                    DataModelNodeDTO queryDTO = new DataModelNodeDTO();
//                    queryDTO.setNodeId(dataModelClassNodeDTO.getParentNodeId());
//                    queryDTO = getGeneralMGR().findByPrimaryKey(queryDTO, getSessionDTO(), getNewPropertyBagDTO());
//                    if (queryDTO != null) {
//                        selectParentDataModelDTO(queryDTO);
//                    } else {
//                        throw new CatException("queryDTO was null - this should not have happened!");
//                    }
                } else {
                    throw new CatException("Neither model ID or parent node ID is set - this should not have happened!");
                }
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    private BaseDTO getSelectedNodeDTOFromParentDTO(BaseDTO baseDTO, BaseDTO queryDTO) {
        final String METHODNAME = "getDto ";
        BaseDTO result = null;
        if (baseDTO != null) {
            if (queryDTO != null) {
                if (baseDTO.equals(queryDTO)) {
                    result = baseDTO;
                } else {
                    for (List<BaseDTO> childDtoList : baseDTO.getChildDTOMap().values()) {
                        for (BaseDTO childDto : childDtoList) {
                            result = getSelectedNodeDTOFromParentDTO(childDto, queryDTO);
                            if (result != null) {
                                break;
                            }
                        }
                        if (result != null) {
                            break;
                        }
                    }
                }
            } else {
                logger.error(METHODNAME, "queryDTO is null!");
            }
        } else {
            logger.error(METHODNAME, "baseDTO is null!");
        }
        return result;
    }

//    @Override
//    protected void onNodeDelete(Object nodeData) {
//        final String METHODNAME = "onNodeDelete ";
//        logger.info(METHODNAME, "nodeData: ", nodeData);
//        RequestContext context = RequestContext.getCurrentInstance();
//        if (nodeData != null) {
//            selectParentDataModelDTO(nodeData);
//            if (nodeData instanceof DataModelDTO) {
//                onDeleteSelectMain((DataModelDTO) nodeData);
//                context.execute("PF('" + getDeleteDialogWidgetVar() + "').show()");
//            } else if (nodeData instanceof DataModelClassNodeDTO) {
//                dataModelNodeMGR.onDeleteSelectMain((DataModelClassNodeDTO) nodeData);
//                context.execute("PF('" + dataModelNodeMGR.getDeleteDialogWidgetVar() + "').show()");
//            }
//        }
//    }

//    protected void onNodeDeleteConfirm(DataModelClassNodeDTO parentDTO) {
//        final String METHODNAME = "onNodeDeleteConfirm ";
//        long start = System.nanoTime();
//        try {
//            BaseDTO dto = getSelectedNodeDTOFromParentDTO(getParentDTO(), parentDTO);
//            dto.delete(true);
//            saveMain();
//        } catch (Exception e) {
//            DefaultExceptionHandler.handleException(e, getClass());
//        } finally {
//            logger.logDuration(METHODNAME, start);
//        }
//    }

//    @Override
//    protected void onNodeAdd(Object nodeData) {
//        final String METHODNAME = "onNodeAdd ";
//        logger.info(METHODNAME, "nodeData: ", nodeData);
//        RequestContext context = RequestContext.getCurrentInstance();
//        if (nodeData != null) {
//            selectParentDataModelDTO(nodeData);
//            dataModelNodeMGR.addMain(null);
//            String newDialogWidgetVar = dataModelNodeMGR.getEditDialogWidgetVar("new");
//            if (newDialogWidgetVar != null) {
//                context.execute("PF('" + newDialogWidgetVar + "').show()");
//            }
//        }
//    }

    @Override
    public void updateTreeTable(List<DataModelDTO> dtoList) {
        // add sorting?
        final String METHODNAME = "updateTreeTable ";
//        logger.info(METHODNAME, "dtoList: ", dtoList.size());
//        logger.info(METHODNAME, "getDataTableMGR().getRowCount(): ", getDataTableMGR().getRowCount());
        List<Object> recursionCheck = new ArrayList<Object>();
        addDataModelNodeListToNode(dtoList, getTreeTableRoot(), recursionCheck, true);
    }

    private void addDataModelNodeListToNode(
            List<? extends BaseDTO> dtoList,
            TreeNode parentNode,
            List<Object> recursionCheck,
            boolean renderEmptyChild) {
        // add sorting?
        final String METHODNAME = "addDataModelNodeListToNode ";
        logger.debug(METHODNAME, "parentNode: ", parentNode);
        if (dtoList == null || dtoList.isEmpty()) {
            if (renderEmptyChild) {
                new DefaultTreeNode("childLessNode", EmptyResultsException.class, parentNode);
            }
        } else {
            for (BaseDTO item : dtoList) {
                renderEmptyChild = true;
                if (recursionCheck.contains(item)) {
                    TreeNode childNode = new DefaultTreeNode("modelNode", item, parentNode);
                    new DefaultTreeNode("recursionNode", StackOverflowError.class, childNode);
                    continue;
                }
                recursionCheck.add(item);
                boolean expanded = getExpandedList().contains(item.getUuid());
                List<? extends BaseDTO> childNodes = null;
                if (item instanceof DataModelDTO) {
                    if (expanded) {
                        DataModelDTO dataModelDTO = (DataModelDTO) item;
                        dataModelDTO = updateDataModelDTO(dataModelDTO);
                        item = dataModelDTO;
                        childNodes = dataModelDTO.getDataModelClassNodeDTOs();
                    }
                } else if (item instanceof DataModelClassNodeDTO) {
                    if (expanded) {
                        DataModelClassNodeDTO dataModelClassNodeDTO = (DataModelClassNodeDTO) item;
                        dataModelClassNodeDTO = updateDataModelClassNodeDTO(dataModelClassNodeDTO);
                        if (dataModelClassNodeDTO.getNodeType() == DataModelNodeType.Attribute) {
                            renderEmptyChild = false;
                        }
                        item = dataModelClassNodeDTO;
                        childNodes = dataModelClassNodeDTO.getDataModelClassDTO().getDataModelClassNodeDTOs();
                    }
                }
                TreeNode childNode = new DefaultTreeNode("modelNode", item, parentNode);
                if (expanded) {
                    childNode.setExpanded(true);
                }
                addDataModelNodeListToNode(childNodes, childNode, recursionCheck, renderEmptyChild);
                recursionCheck.remove(item);
            }
        }
    }

    public boolean isNode(Object object) {
        return object instanceof DataModelClassNodeDTO;
    }

    public boolean isDataModelDTO(Object object) {
        return object instanceof DataModelDTO;
    }
}
