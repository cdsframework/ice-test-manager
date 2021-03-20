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
package org.cdsframework.base;

import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import org.cdsframework.datatable.DataTableInterface;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.enumeration.PrePost;
import org.cdsframework.util.enumeration.UIDataType;
import org.primefaces.component.treetable.TreeTable;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.util.ComponentUtils;

/**
 *
 * @author HLN Consulting, LLC
 * @param <T>
 */
public abstract class BaseTreeMGR<T extends BaseDTO> extends BaseModule<T> {

    private static final long serialVersionUID = -2112277208346902220L;
    private static final String TREE_TABLE_ID = "TreeTableId";
    private TreeNode treeTableRoot;
    private DefaultTreeNode selectedNode;
    private String sortBy;
    private String topNodeName;
    private final List<Object> expandedList = new ArrayList<Object>();
    private final List<Object> retrievedObjects = new ArrayList<Object>();
//    private MenuModel contextMenuModel = new DefaultMenuModel();
    private String[][] sortSelectItems;
//    private boolean enableAddContextMenu = true;
//    private boolean enableEditContextMenu = true;
//    private boolean enableDeleteContextMenu = true;
    private String managerName;

    @Override
    protected void initialize() {
        final String METHODNAME = "BaseTree initialize ";
        logger.info(METHODNAME, "called!");
        setUIDataType(UIDataType.TreeTable);
        setDataTableExists(false);
        setLazy(false);
//        initializeContextMenu();
        treeInitialize();
    }

    /**
     * User override
     */
    protected void treeInitialize() {

    }

//    private void initializeContextMenu() {
//        final String METHODNAME = "initializeContextMenu ";
//        if (contextMenuModel != null) {
//            if (enableAddContextMenu) {
//                addContextMenuItem("Add", "ui-icon-plus", "#{" + getManagerName() + ".onNodeAddMain}");
//            }
//            if (enableEditContextMenu) {
//                addContextMenuItem("Edit", "ui-icon-pencil", "#{" + getManagerName() + ".onNodeEditMain}");
//            }
//            if (enableDeleteContextMenu) {
//                addContextMenuItem("Delete", "ui-icon-close", "#{" + getManagerName() + ".onNodeDeleteMain}");
//            }
//        } else {
//            logger.warn(METHODNAME, "contextMenu was null!");
//        }
//    }
    public String getTreeTableId() {
        return getName() + TREE_TABLE_ID;
    }

    public String getTreeTableUpdateId() {
        return ComponentUtils.findComponentClientId(getTreeTableId());
    }

//    public void onNodeEditMain() {
//        final String METHODNAME = "onNodeEditMain ";
//        logger.info(METHODNAME, "called: ", selectedNode);
//        onNodeEdit(selectedNode != null ? selectedNode.getData() : null);
//    }
//
//    protected abstract void onNodeEdit(Object nodeData);
//
//    public void onNodeDeleteMain() {
//        final String METHODNAME = "onNodeDeleteMain ";
//        logger.info(METHODNAME, "called: ", selectedNode);
//        onNodeDelete(selectedNode != null ? (BaseDTO) selectedNode.getData() : null);
//    }
//
//    protected abstract void onNodeDelete(Object nodeData);
//
//    public void onNodeAddMain() {
//        final String METHODNAME = "onNodeAddMain ";
//        logger.info(METHODNAME, "called: ", selectedNode);
//        onNodeAdd(selectedNode != null ? selectedNode.getData() : null);
//    }
//
//    protected abstract void onNodeAdd(Object nodeData);
    public void onNodeExpandMain(NodeExpandEvent event) {
        final String METHODNAME = "onNodeExpandMain ";
        Object dataObject = event != null ? event.getTreeNode() != null ? event.getTreeNode().getData() : null : null;
        logger.info(METHODNAME, "called: ", dataObject);
        if (dataObject != null) {
            if (dataObject instanceof BaseDTO) {
                BaseDTO baseDTO = (BaseDTO) dataObject;
                expandedList.add(baseDTO.getUuid());
            } else {
                expandedList.add(dataObject);
            }
        }
        onNodeExpand(event);
    }

    protected void onNodeExpand(NodeExpandEvent event) {
    }

    public void onNodeCollapseMain(NodeCollapseEvent event) {
        final String METHODNAME = "onNodeCollapseMain ";
        Object dataObject = event != null ? event.getTreeNode() != null ? event.getTreeNode().getData() : null : null;
        logger.info(METHODNAME, "called: ", dataObject);
        if (dataObject != null) {
            if (dataObject instanceof BaseDTO) {
                BaseDTO baseDTO = (BaseDTO) dataObject;
                expandedList.remove(baseDTO.getUuid());
            } else {
                expandedList.remove(dataObject);
            }
        }
        onNodeCollapse(event);
    }

    protected void onNodeCollapse(NodeCollapseEvent event) {
    }

    public void onNodeSelectMain(NodeSelectEvent event) {
        final String METHODNAME = "onNodeSelectMain ";
        logger.info(METHODNAME, "called: ", event != null ? event.getTreeNode() != null ? event.getTreeNode().getData() : null : null);
        onNodeSelect(event);
    }

    protected void onNodeSelect(NodeSelectEvent event) {
    }

    public void onNodeUnselectMain(NodeUnselectEvent event) {
        final String METHODNAME = "onNodeUnselectMain ";
        logger.info(METHODNAME, "called: ", event != null ? event.getTreeNode() != null ? event.getTreeNode().getData() : null : null);
        onNodeUnselect(event);
    }

    protected void onNodeUnselect(NodeUnselectEvent event) {
    }

    public abstract void updateTreeTable(List<T> dtoList);

    public void updateTreeTableMain() {
        final String METHODNAME = "updateTreeTableMain ";
        long start = System.nanoTime();
        logger.debug(METHODNAME, "called!");
        treeTableRoot = new DefaultTreeNode("root", null);
        try {
            logger.debug(METHODNAME, "getDataTableMGR().getRowCount(): ", getDataTableMGR().getRowCount());
            DataTableInterface<T> dataTableMGR = getDataTableMGR();
            logger.debug(METHODNAME, "dataTableMGR=", dataTableMGR);
            if (dataTableMGR != null) {
                List<T> dtoList = dataTableMGR.getDtoList();
                logger.debug(METHODNAME, "dtoList=", dtoList);
                if (dtoList != null) {
                    logger.debug(METHODNAME, "dtoList.isEmpty()=", dtoList.isEmpty());
                    if (dtoList.isEmpty()) {
                        TreeNode suite = new DefaultTreeNode(topNodeName, null, treeTableRoot);
                    } else {
                        updateTreeTable(dtoList);
                    }
                }
            }
            logger.debug(METHODNAME, "treeTable.getClientId()=", getTreeTableUpdateId());
            setUpdateIds(METHODNAME, getTreeTableUpdateId());
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, start);                                                                            
        }
    }

    public void onChangeSortOrder(AjaxBehaviorEvent ajaxBehaviorEvent) {
        final String METHODNAME = "onChangeSortOrder ";
        logger.info(METHODNAME, "called!");
        updateTreeTableMain();
    }

    @Override
    protected void postSearch(ActionEvent actionEvent) {
        updateTreeTableMain();
    }

    @Override
    public void clearSearchMain(ActionEvent actionEvent) {
        super.clearSearchMain(actionEvent);
        updateTreeTableMain();
    }

    @Override
    public void postSetParentDTO(T parentDTO) {
//        updateTreeTableMain();
    }

    @Override
    protected void postClose(CloseEvent event) {
        final String METHODNAME = "postClose ";
        setUpdateIds(METHODNAME, getTreeTableUpdateId());
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public DefaultTreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(DefaultTreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public TreeNode getTreeTableRoot() {
        if (treeTableRoot == null) {
            treeTableRoot = new DefaultTreeNode("root", null);
            TreeNode suite = new DefaultTreeNode(topNodeName, null, treeTableRoot);
        }
        return treeTableRoot;
    }

    public void setTreeTableRoot(TreeNode treeTableRoot) {
        this.treeTableRoot = treeTableRoot;
    }

    /**
     * Get the value of topNodeName
     *
     * @return the value of topNodeName
     */
    public String getTopNodeName() {
        return topNodeName;
    }

    /**
     * Set the value of topNodeName
     *
     * @param topNodeName new value of topNodeName
     */
    public void setTopNodeName(String topNodeName) {
        this.topNodeName = topNodeName;
    }

    /**
     * Get the value of sortSelectItems
     *
     * @return the value of sortSelectItems
     */
    public String[][] getSortSelectItems() {
        return sortSelectItems;
    }

    /**
     * Set the value of sortSelectItems
     *
     * @param sortSelectItems new value of sortSelectItems
     */
    public void setSortSelectItems(String[][] sortSelectItems) {
        this.sortSelectItems = sortSelectItems;
    }

    public List<Object> getExpandedList() {
        return expandedList;
    }

    public List<Object> getRetrievedObjects() {
        return retrievedObjects;
    }

//    /**
//     * Get the value of contextMenuModel
//     *
//     * @return the value of contextMenuModel
//     */
//    public MenuModel getContextMenuModel() {
//        return contextMenuModel;
//    }
//
//    /**
//     * Set the value of contextMenuModel
//     *
//     * @param contextMenuModel new value of contextMenuModel
//     */
//    public void setContextMenuModel(MenuModel contextMenuModel) {
//        this.contextMenuModel = contextMenuModel;
//    }
//
//    /**
//     * Get the value of enableDeleteContextMenu
//     *
//     * @return the value of enableDeleteContextMenu
//     */
//    public boolean isEnableDeleteContextMenu() {
//        return enableDeleteContextMenu;
//    }
//
//    /**
//     * Set the value of enableDeleteContextMenu
//     *
//     * @param enableDeleteContextMenu new value of enableDeleteContextMenu
//     */
//    public void setEnableDeleteContextMenu(boolean enableDeleteContextMenu) {
//        this.enableDeleteContextMenu = enableDeleteContextMenu;
//    }
//
//    /**
//     * Get the value of enableEditContextMenu
//     *
//     * @return the value of enableEditContextMenu
//     */
//    public boolean isEnableEditContextMenu() {
//        return enableEditContextMenu;
//    }
//
//    /**
//     * Set the value of enableEditContextMenu
//     *
//     * @param enableEditContextMenu new value of enableEditContextMenu
//     */
//    public void setEnableEditContextMenu(boolean enableEditContextMenu) {
//        this.enableEditContextMenu = enableEditContextMenu;
//    }
//
//    /**
//     * Get the value of enableAddContextMenu
//     *
//     * @return the value of enableAddContextMenu
//     */
//    public boolean isEnableAddContextMenu() {
//        return enableAddContextMenu;
//    }
//
//    /**
//     * Set the value of enableAddContextMenu
//     *
//     * @param enableAddContextMenu new value of enableAddContextMenu
//     */
//    public void setEnableAddContextMenu(boolean enableAddContextMenu) {
//        this.enableAddContextMenu = enableAddContextMenu;
//    }
    /**
     * Get the value of managerName
     *
     * @return the value of managerName
     */
    public String getManagerName() {
        if (managerName == null) {
            char c[] = getClass().getSimpleName().toCharArray();
            c[0] = Character.toLowerCase(c[0]);
            managerName = new String(c);
        }
        return managerName;
    }

//    protected void addContextMenuItem(String value, String icon, String command) {
//        final String METHODNAME = "addContextMenuItem ";
//        if (contextMenuModel != null) {
//            DefaultMenuItem menuItem = new DefaultMenuItem();
//            menuItem.setValue(value);
//            menuItem.setIcon(icon);
//            menuItem.setCommand(command);
//            contextMenuModel.addElement(menuItem);
//            logger.info(METHODNAME, String.format("added %s MenuItem!", value));
//        } else {
//            logger.warn(METHODNAME, "contextMenuModel is null!");
//
//        }
//    }
    public void setSelectedNodeFromObject(Object object) {
        final String METHODNAME = "setSelectedNodeFromObject ";
        logger.debug(METHODNAME, "object=", object);
        setSelectedNode(null);
        TreeNode root = getTreeTableRoot();
        logger.debug(METHODNAME, "root=", root);
        if (object != null) {
            if (root != null) {
                setSelectedNodeFromObject(object, root);
            } else {
                logger.warn(METHODNAME, "root was null!");
            }
        } else {
            logger.warn(METHODNAME, "object was null!");
        }
    }

    private void setSelectedNodeFromObject(Object object, TreeNode parentNode) {
        final String METHODNAME = "setSelectedNodeFromObject ";
        for (TreeNode item : parentNode.getChildren()) {
            if (item.getData() != null && item.getData().equals(object)) {
                logger.debug(METHODNAME, "setting selected node=", object);
                setSelectedNode((DefaultTreeNode) item);
            } else {
                setSelectedNodeFromObject(object, item);
            }
            if (getSelectedNode() != null) {
                break;
            }
        }
    }

    public boolean isBaseDTO(Object data) {
        return data instanceof BaseDTO;
    }

    public boolean isString(Object data) {
        return data instanceof String;
    }

    public boolean isManagerType(Object data) {
        boolean result = false;
        if (data != null && data.getClass() == this.getDtoClassType()) {
            result = true;
        }
        return result;
    }

    @Override
    public TreeTable getTreeTable() {
        final String METHODNAME = "getTreeTable ";
        TreeTable treeTable = null;
        UIComponent uiComponentById = UtilityMGR.getUIComponentById(getTreeTableUpdateId());
        if (uiComponentById != null) {
            treeTable = (TreeTable) uiComponentById;
        }
        return treeTable;
    }

    @Override
    public void prePostOperation(String queryClass, BaseDTO baseDTO, PrePost prePost, boolean status) {
        super.prePostOperation(queryClass, baseDTO, prePost, status);
        switch (prePost) {
            case PostRetrieve:
            case PostSearch:
                if (status) {
                    updateTreeTableMain();
                }
                break;
            case PostDelete:
            case PostInlineDelete:
            case PostInlineSave:
            case PostSave:
            case PostTabSave:
                if (status) {
                    retrievedObjects.clear();
                    getExpandedList().clear();
                    updateTreeTableMain();
                }
                break;
        }
    }
}
