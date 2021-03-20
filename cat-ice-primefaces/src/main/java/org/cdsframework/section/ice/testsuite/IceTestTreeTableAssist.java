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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.datatable.DataTableInterface;
//import org.cdsframework.datatable.DataTableInterface;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceTestGroupDTO;
import org.cdsframework.dto.IceTestSuiteDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.section.ice.test.IceTestMGR;
import org.cdsframework.section.ice.testgroup.IceTestGroupMGR;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.comparator.IceTestByIdComparator;
import org.cdsframework.util.comparator.IceTestByLastModComparator;
import org.cdsframework.util.comparator.IceTestComparator;
import org.cdsframework.util.comparator.IceTestGroupComparator;
import org.cdsframework.util.comparator.IceTestSuiteComparator;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.util.ComponentUtils;

/**
 *
 * @author sdn
 */
@Named
@ViewScoped
public class IceTestTreeTableAssist implements Serializable {

    private static final LogUtils logger = LogUtils.getLogger(IceTestTreeTableAssist.class);
    private static final long serialVersionUID = -4438642491024637373L;
    @Inject
    private IceTestSuiteMGR iceTestSuiteMGR;
    @Inject
    private IceTestGroupMGR iceTestGroupMGR;
    @Inject
    private IceTestMGR iceTestMGR;
    private TreeNode treeTableRoot;
    private Object foo;
    private final static IceTestByIdComparator iceTestByIdComparator = new IceTestByIdComparator();
    private final static IceTestByLastModComparator iceTestByLastModComparator = new IceTestByLastModComparator();
    private final static IceTestComparator iceTestComparator = new IceTestComparator();
    private final static IceTestSuiteComparator iceTestSuiteComparator = new IceTestSuiteComparator();
    private final static IceTestGroupComparator iceTestGroupComparator = new IceTestGroupComparator();
    private static final String TREE_TABLE_ID = "iceTestMgrTreeTableId";

    public void postOnDeleteIceTestGroupDTOSelectMain(IceTestGroupDTO iceTestGroupDTO) {
        removeItemFromBaseDataTableMGRDTOList(iceTestGroupDTO);
    }

    public void addGroupMain(IceTestSuiteDTO iceTestSuiteDTO) {
        final String METHODNAME = "addGroupMain ";
        rowSelectSuiteByDTO(iceTestSuiteDTO);
        iceTestGroupMGR.addMain(UtilityMGR.getEmulatedActionEvent());
        IceTestGroupDTO iceTestGroupDTO = iceTestGroupMGR.getParentDTO();
        iceTestGroupDTO.setSuiteId(iceTestSuiteDTO.getSuiteId());
    }

    public void onCopyGroup(IceTestGroupDTO iceTestGroupDTO) {
        final String METHODNAME = "onCopyGroup ";
        rowSelectGroupByDTO(iceTestGroupDTO);
        rowSelectSuiteByPrimaryKey(iceTestGroupDTO.getSuiteId());
    }

    public void postOnCopyGroup(IceTestGroupDTO iceTestGroupDTO) {
        final String METHODNAME = "postOnCopyGroup ";
        // maybe shouldn't be doing this...
        IceTestSuiteDTO iceTestSuiteDTO = getIceTestSuiteDTOFromMts(iceTestGroupDTO.getSuiteId());
        iceTestSuiteDTO.setChildrenDTOs(IceTestGroupDTO.ByTestSuiteId.class, (List) new ArrayList<IceTestGroupDTO>());
        updateBaseDataTableMGRDTOList(iceTestSuiteDTO);
    }

    public void onMergeGroup(IceTestGroupDTO iceTestGroupDTO) {
        final String METHODNAME = "onMergeGroup ";
        rowSelectGroupByDTO(iceTestGroupDTO);
        rowSelectSuiteByPrimaryKey(iceTestGroupDTO.getSuiteId());
    }

    public void postOnMergeGroup(IceTestGroupDTO src, IceTestGroupDTO dst) {
        final String METHODNAME = "postOnMergeGroup ";
        removeItemFromBaseDataTableMGRDTOList(src);
        updateBaseDataTableMGRDTOList(dst);
    }

    public void postSaveIceTestGroupDTOSelectMain(IceTestGroupDTO iceTestGroupDTO) {
        updateBaseDataTableMGRDTOList(iceTestGroupDTO);
    }

    public void postOnDeleteIceTestDTOSelectMain(IceTestDTO iceTestDTO) {
        removeItemFromBaseDataTableMGRDTOList(iceTestDTO);
    }

    public void addTestMain(IceTestGroupDTO iceTestGroupDTO) {
        final String METHODNAME = "addTestMain ";
        rowSelectSuiteByPrimaryKey(iceTestGroupDTO.getSuiteId());
        rowSelectGroupByDTO(iceTestGroupDTO);
        iceTestMGR.addMain(UtilityMGR.getEmulatedActionEvent());
        IceTestDTO iceTestDTO = iceTestMGR.getParentDTO();
        iceTestDTO.setSuiteId(iceTestGroupDTO.getSuiteId());
        iceTestDTO.setSuiteName(iceTestGroupDTO.getSuiteName());
        iceTestDTO.setVersionId(iceTestGroupDTO.getVersionId());
        iceTestDTO.setGroupId(iceTestGroupDTO.getGroupId());
        iceTestDTO.setGroupName(iceTestGroupDTO.getName());
    }

    public void postSaveIceTestDTOSelectMain(IceTestDTO iceTestDTO) {
        updateBaseDataTableMGRDTOList(iceTestDTO);
    }

    public void onCopyTest(IceTestDTO iceTestDTO) {
        final String METHODNAME = "onCopyTest ";
        rowSelectGroupByPrimaryKey(iceTestDTO.getGroupId());
        rowSelectSuiteByPrimaryKey(iceTestDTO.getSuiteId());
        rowSelectTestByDTO(iceTestDTO);
    }

    public void postOnCopyTest(IceTestDTO iceTestDTO) {
        final String METHODNAME = "postOnCopyTest ";
        // maybe shouldn't be doing this...
        IceTestGroupDTO iceTestGroupDTO = getIceTestGroupDTOFromMts(iceTestDTO.getGroupId());
        iceTestGroupDTO.setChildrenDTOs(IceTestDTO.ByTestGroupId.class, (List) new ArrayList<IceTestGroupDTO>());
        updateBaseDataTableMGRDTOList(iceTestGroupDTO);
    }

    // node expand/collapse related methods
    public void onNodeExpand(NodeExpandEvent event) {
        final String METHODNAME = "onNodeExpand ";
        logger.debug(METHODNAME, "called!");
        TreeNode treeNode = event.getTreeNode();
        if (treeNode != null) {
            try {
                if ("suite".equals(treeNode.getType())) {
                    IceTestSuiteDTO iceTestSuiteDTO = (IceTestSuiteDTO) treeNode.getData();
                    logger.debug(METHODNAME, "got suite: ", iceTestSuiteDTO);
                    onNodeExpandIceTestSuiteDTO(iceTestSuiteDTO);
                } else if ("group".equals(treeNode.getType())) {
                    IceTestGroupDTO iceTestGroupDTO = (IceTestGroupDTO) treeNode.getData();
                    logger.debug(METHODNAME, "got group: ", iceTestGroupDTO);
                    onNodeExpandIceTestGroupDTO(iceTestGroupDTO);
                }
            } catch (Exception e) {
                DefaultExceptionHandler.handleException(e, getClass());
            }
        }
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
        final String METHODNAME = "onNodeCollapse ";
        logger.debug(METHODNAME, "called!");
        TreeNode treeNode = event.getTreeNode();
        if (treeNode != null) {
            treeNode.setExpanded(false);
            try {
                if ("suite".equals(treeNode.getType())) {
                    IceTestSuiteDTO iceTestSuiteDTO = (IceTestSuiteDTO) treeNode.getData();
                    logger.debug(METHODNAME, "got suite: ", iceTestSuiteDTO);
                    onNodeCollapseIceTestSuiteDTO(iceTestSuiteDTO);
                } else if ("group".equals(treeNode.getType())) {
                    IceTestGroupDTO iceTestGroupDTO = (IceTestGroupDTO) treeNode.getData();
                    logger.debug(METHODNAME, "got group: ", iceTestGroupDTO);
                    onNodeCollapseIceTestGroupDTO(iceTestGroupDTO);
                }
            } catch (Exception e) {
                DefaultExceptionHandler.handleException(e, getClass());
            }
        }
    }

    private void onNodeExpandIceTestSuiteDTO(IceTestSuiteDTO iceTestSuiteDTO) {
        final String METHODNAME = "onNodeExpandIceTestSuiteDTO ";
        setNodeChildrenForIceTestSuiteDTO(iceTestSuiteDTO);
        updateBaseDataTableMGRDTOList(iceTestSuiteDTO);
    }

    private void setNodeChildrenForIceTestSuiteDTO(IceTestSuiteDTO iceTestSuiteDTO) {
        final String METHODNAME = "setNodeChildrenForIceTestSuiteDTO ";
        try {
            PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
            propertyBagDTO.setQueryClass("ByGeneralProperties");
            IceTestSuiteDTO suiteSearchCriteriaDTO = iceTestSuiteMGR.getSearchCriteriaDTO();
            IceTestGroupDTO searchCriteriaDTO = new IceTestGroupDTO();
            searchCriteriaDTO.getQueryMap().put("version_id", iceTestSuiteDTO.getCdsVersionDTO() != null ? iceTestSuiteDTO.getCdsVersionDTO().getVersionId() : null);
            searchCriteriaDTO.getQueryMap().put("suite_id", iceTestSuiteDTO.getSuiteId());
            searchCriteriaDTO.getQueryMap().put("group_id", suiteSearchCriteriaDTO.getQueryMap().get("group_id"));
            searchCriteriaDTO.getQueryMap().put("text", suiteSearchCriteriaDTO.getQueryMap().get("text"));
            List<IceTestGroupDTO> iceTestGroupDTOs = iceTestGroupMGR.getGeneralMGR().findByQueryList(searchCriteriaDTO, iceTestSuiteMGR.getSessionDTO(), propertyBagDTO);
            iceTestSuiteDTO.setChildrenDTOs(IceTestGroupDTO.ByTestSuiteId.class, (List) iceTestGroupDTOs);
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    private void onNodeExpandIceTestGroupDTO(IceTestGroupDTO iceTestGroupDTO) {
        final String METHODNAME = "onNodeExpandIceTestGroupDTO ";
        setNodeChildrenForIceTestGroupDTO(iceTestGroupDTO);
        updateBaseDataTableMGRDTOList(iceTestGroupDTO);
    }

    private void setNodeChildrenForIceTestGroupDTO(IceTestGroupDTO iceTestGroupDTO) {
        final String METHODNAME = "setNodeChildrenForIceTestGroupDTO ";
        try {
            PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
            propertyBagDTO.setQueryClass("ByGeneralProperties");
            IceTestSuiteDTO suiteSearchCriteriaDTO = iceTestSuiteMGR.getSearchCriteriaDTO();
            IceTestDTO searchCriteriaDTO = new IceTestDTO();
            searchCriteriaDTO.getQueryMap().put("group_id", iceTestGroupDTO.getGroupId());
            searchCriteriaDTO.getQueryMap().put("text", suiteSearchCriteriaDTO.getQueryMap().get("text"));
            List<IceTestDTO> iceTestDTOs = iceTestMGR.getGeneralMGR().findByQueryList(searchCriteriaDTO, iceTestSuiteMGR.getSessionDTO(), propertyBagDTO);
            iceTestGroupDTO.setChildrenDTOs(IceTestDTO.ByTestGroupId.class, (List) iceTestDTOs);
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    private void onNodeCollapseIceTestSuiteDTO(IceTestSuiteDTO iceTestSuiteDTO) {
        final String METHODNAME = "onNodeCollapseIceTestSuiteDTO ";
        // future
    }

    private void onNodeCollapseIceTestGroupDTO(IceTestGroupDTO iceTestGroupDTO) {
        final String METHODNAME = "onNodeCollapseIceTestGroupDTO ";
        // future
    }

    // node select related methods
    public void onNodeSelect(NodeSelectEvent event) {
        final String METHODNAME = "onNodeSelect ";
        TreeNode treeNode = event.getTreeNode();
        if (treeNode != null) {
            Object data = event.getTreeNode().getData();
            if (data != null) {
                if ("suite".equals(treeNode.getType())) {
                    onNodeSelectIceTestSuiteDTO((IceTestSuiteDTO) data);
                } else if ("group".equals(treeNode.getType())) {
                    onNodeSelectIceTestGroupDTO((IceTestGroupDTO) data);
                } else if ("test".equals(treeNode.getType())) {
                    onNodeSelectIceTestDTO((IceTestDTO) data);
                } else {
                    logger.warn(METHODNAME, "unhandled node select event: ", treeNode);
                }
                iceTestSuiteMGR.setUpdateIds(METHODNAME, getTreeTableUpdateId());
            } else {
                logger.warn(METHODNAME, "data was null! unhandled node select.");
            }
        }
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        final String METHODNAME = "onNodeUnselect ";
        TreeNode treeNode = event.getTreeNode();
        if (treeNode != null) {
            if ("suite".equals(treeNode.getType())) {
                onNodeUnselectIceTestSuiteDTO((IceTestSuiteDTO) event.getTreeNode().getData());
            } else if ("group".equals(treeNode.getType())) {
                onNodeUnselectIceTestGroupDTO((IceTestGroupDTO) event.getTreeNode().getData());
            } else if ("test".equals(treeNode.getType())) {
                onNodeUnselectIceTestDTO((IceTestDTO) event.getTreeNode().getData());
            } else {
                logger.warn(METHODNAME, "unhandled node select event: ", treeNode);
            }
        }
        iceTestSuiteMGR.setUpdateIds(METHODNAME, getTreeTableUpdateId());
    }

    public void onNodeSelectIceTestSuiteDTO(IceTestSuiteDTO iceTestSuiteDTO) {
        final String METHODNAME = "onNodeSelectIceTestSuiteDTO ";
        logger.debug(METHODNAME, "suite: ", iceTestSuiteDTO);
        RequestContext context = RequestContext.getCurrentInstance();
        rowSelectSuiteByDTO(iceTestSuiteDTO);
        context.execute("PF('" + iceTestSuiteMGR.getEditDialogWidgetVar("edit") + "').show()");
    }

    public void onNodeSelectIceTestGroupDTO(IceTestGroupDTO iceTestGroupDTO) {
        final String METHODNAME = "onNodeSelectIceTestGroupDTO ";
        logger.debug(METHODNAME, "group: ", iceTestGroupDTO);
        RequestContext context = RequestContext.getCurrentInstance();
        rowSelectSuiteByPrimaryKey(iceTestGroupDTO.getSuiteId());
        rowSelectGroupByDTO(iceTestGroupDTO);
        context.execute("PF('" + iceTestGroupMGR.getEditDialogWidgetVar("edit") + "').show()");
    }

    public void onNodeSelectIceTestDTO(IceTestDTO iceTestDTO) {
        final String METHODNAME = "onNodeSelect ";
        logger.debug(METHODNAME, "test: ", iceTestDTO);
        RequestContext context = RequestContext.getCurrentInstance();
        rowSelectSuiteByPrimaryKey(iceTestDTO.getSuiteId());
        rowSelectGroupByPrimaryKey(iceTestDTO.getGroupId());
        rowSelectTestByDTO(iceTestDTO);
        context.execute("PF('" + iceTestMGR.getEditDialogWidgetVar("edit") + "').show()");
    }

    public void onNodeUnselectIceTestSuiteDTO(IceTestSuiteDTO iceTestSuiteDTO) {
        onNodeSelectIceTestSuiteDTO(iceTestSuiteDTO);
    }

    public void onNodeUnselectIceTestGroupDTO(IceTestGroupDTO iceTestGroupDTO) {
        onNodeSelectIceTestGroupDTO(iceTestGroupDTO);
    }

    public void onNodeUnselectIceTestDTO(IceTestDTO iceTestDTO) {
        onNodeSelectIceTestDTO(iceTestDTO);
    }

    // UI related getters/setters
    public Object getFoo() {
        return foo;
    }

    public void setFoo(Object foo) {
        this.foo = foo;
    }

    public TreeNode getTreeTableRoot() {
        if (treeTableRoot == null) {
            treeTableRoot = new DefaultTreeNode("root", null);
            TreeNode suite = new DefaultTreeNode("suite", null, treeTableRoot);

        }
        return treeTableRoot;
    }

    public void setTreeTableRoot(TreeNode treeTableRoot) {
        this.treeTableRoot = treeTableRoot;
    }

    // row selection related methods
    private void rowSelectSuiteByPrimaryKey(String primaryKey) {
        rowSelectSuiteByDTO(getIceTestSuiteDTOFromMts(primaryKey));
    }

    public void rowSelectSuiteByDTO(IceTestSuiteDTO iceTestSuiteDTO) {
        SelectEvent selectEvent = UtilityMGR.getEmulatedSelectEvent(iceTestSuiteDTO);
        iceTestSuiteMGR.onRowSelectMain(selectEvent);
    }

    private void rowSelectGroupByPrimaryKey(String primaryKey) {
        rowSelectGroupByDTO(getIceTestGroupDTOFromMts(primaryKey));
    }

    private void rowSelectGroupByDTO(IceTestGroupDTO iceTestGroupDTO) {
        SelectEvent selectEvent = UtilityMGR.getEmulatedSelectEvent(iceTestGroupDTO);
        iceTestGroupMGR.onRowSelectMain(selectEvent);
    }

    private void rowSelectTestByDTO(IceTestDTO iceTestDTO) {
        SelectEvent selectEvent = UtilityMGR.getEmulatedSelectEvent(iceTestDTO);
        iceTestMGR.onRowSelectMain(selectEvent);
    }

    public IceTestSuiteDTO getIceTestSuiteDTOFromMts(String primaryKey) {
        final String METHODNAME = "setParentDTO ";
        IceTestSuiteDTO result = null;
        try {
            if (primaryKey != null) {
                IceTestSuiteDTO queryDTO = new IceTestSuiteDTO();
                queryDTO.setSuiteId(primaryKey);
                result = iceTestSuiteMGR.getGeneralMGR().findByPrimaryKey(queryDTO, iceTestSuiteMGR.getSessionDTO(), new PropertyBagDTO());
//            DataTableInterface<IceTestSuiteDTO> dataTableMGR = iceTestSuiteMGR.getDataTableMGR();
//            if (dataTableMGR != null) {
//                List<IceTestSuiteDTO> dtoList = dataTableMGR.getDtoList();
//                if (dtoList != null) {
//                    for (IceTestSuiteDTO item : dtoList) {
//                        if (item.getSuiteId().equals(primaryKey)) {
//                            result = item;
//                            break;
//                        }
//                    }
//                    if (result == null) {
//                        logger.error(METHODNAME, "primary key not found: ", primaryKey);
//                    }
//                } else {
//                    logger.error(METHODNAME, "dtoList is null!");
//                }
//            } else {
//                logger.error(METHODNAME, "dataTableMGR is null!");
//            }
            } else {
                logger.error(METHODNAME, "primaryKey is null!");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return result;
    }

    public IceTestGroupDTO getIceTestGroupDTOFromMts(String primaryKey) {
        final String METHODNAME = "setParentDTO ";
        IceTestGroupDTO result = null;
        try {
            if (primaryKey != null) {
                IceTestGroupDTO queryDTO = new IceTestGroupDTO();
                queryDTO.setGroupId(primaryKey);
                result = iceTestGroupMGR.getGeneralMGR().findByPrimaryKey(queryDTO, iceTestGroupMGR.getSessionDTO(), new PropertyBagDTO());
//                DataTableInterface<IceTestGroupDTO> dataTableMGR = iceTestGroupMGR.getDataTableMGR();
//                if (dataTableMGR != null) {
//                    List<IceTestGroupDTO> dtoList = dataTableMGR.getDtoList();
//                    if (dtoList != null) {
//                        for (IceTestGroupDTO item : dtoList) {
//                            if (item.getGroupId().equals(primaryKey)) {
//                                result = item;
//                                break;
//                            }
//                        }
//                        if (result == null) {
//                            logger.error(METHODNAME, "primary key not found: ", primaryKey);
//                        }
//                    } else {
//                        logger.error(METHODNAME, "dtoList is null!");
//                    }
//                } else {
//                    logger.error(METHODNAME, "dataTableMGR is null!");
//                }
            } else {
                logger.error(METHODNAME, "primaryKey is null!");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return result;
    }

    // datatable/tree related update methods
    public void updateBaseDataTableMGRDTOList(IceTestSuiteDTO iceTestSuiteDTO) {
        final String METHODNAME = "updateBaseDataTableMGRDTOList ";
        if (iceTestSuiteDTO != null) {
            logger.debug(METHODNAME, "updating: ", iceTestSuiteDTO.getName());
            DataTableInterface<IceTestSuiteDTO> dataTableMGR = iceTestSuiteMGR.getDataTableMGR();
            if (dataTableMGR != null) {
                List<IceTestSuiteDTO> dtoList = dataTableMGR.getDtoList();
                if (dtoList != null) {
                    int loc = dtoList.indexOf(iceTestSuiteDTO);
                    if (loc != -1) {
                        logger.debug(METHODNAME, "found suite @ ", loc);
                        dtoList.set(loc, iceTestSuiteDTO);
                    } else {
                        dtoList.add(iceTestSuiteDTO);
                    }
                    updateTreeTable();
                } else {
                    logger.error(METHODNAME, "dtoList is null!");
                }
            } else {
                logger.error(METHODNAME, "dataTableMGR is null!");
            }
        } else {
            logger.error(METHODNAME, "iceTestSuiteDTO is null!");
        }
    }

    public void updateBaseDataTableMGRDTOList(IceTestGroupDTO iceTestGroupDTO) {
        final String METHODNAME = "updateBaseDataTableMGRDTOList ";
        DataTableInterface<IceTestSuiteDTO> dataTableMGR = iceTestSuiteMGR.getDataTableMGR();
        if (iceTestGroupDTO != null) {
            if (dataTableMGR != null) {
                List<IceTestSuiteDTO> dtoList = dataTableMGR.getDtoList();
                if (dtoList != null) {
                    IceTestSuiteDTO iceTestSuiteDTO = new IceTestSuiteDTO();
                    iceTestSuiteDTO.setSuiteId(iceTestGroupDTO.getSuiteId());
                    int loc = dtoList.indexOf(iceTestSuiteDTO);
                    if (loc != -1) {
                        iceTestSuiteDTO = dtoList.get(loc);
                        iceTestSuiteDTO.addOrUpdateChildDTO(iceTestGroupDTO);
                    } else {
                        dtoList.add(iceTestSuiteDTO);
                        logger.error(METHODNAME, "iceTestGroupDTO suite ID not found: ", iceTestGroupDTO.getSuiteId());
                    }
                    updateTreeTable();
                } else {
                    logger.error(METHODNAME, "dtoList is null!");
                }
            } else {
                logger.error(METHODNAME, "dataTableMGR is null!");
            }
        } else {
            logger.error(METHODNAME, "iceTestGroupDTO is null!");
        }
    }

    public void updateBaseDataTableMGRDTOList(IceTestDTO iceTestDTO) {
        final String METHODNAME = "updateBaseDataTableMGRDTOList ";
        DataTableInterface<IceTestSuiteDTO> dataTableMGR = iceTestSuiteMGR.getDataTableMGR();
        if (iceTestDTO != null) {
            if (dataTableMGR != null) {
                List<IceTestSuiteDTO> dtoList = dataTableMGR.getDtoList();
                if (dtoList != null) {
                    IceTestSuiteDTO iceTestSuiteDTO = new IceTestSuiteDTO();
                    iceTestSuiteDTO.setSuiteId(iceTestDTO.getSuiteId());
                    int loc = dtoList.indexOf(iceTestSuiteDTO);
                    if (loc != -1) {
                        iceTestSuiteDTO = dtoList.get(loc);
                        List<IceTestGroupDTO> iceTestGroupDTOs = iceTestSuiteDTO.getIceTestGroupDTOs();
                        if (iceTestGroupDTOs != null) {
                            IceTestGroupDTO iceTestGroupDTO = new IceTestGroupDTO();
                            iceTestGroupDTO.setGroupId(iceTestDTO.getGroupId());
                            loc = iceTestGroupDTOs.indexOf(iceTestGroupDTO);
                            if (loc != -1) {
                                iceTestGroupDTO = iceTestGroupDTOs.get(loc);
                            } else {
                                iceTestGroupDTOs.add(iceTestGroupDTO);
                                logger.error(METHODNAME, "iceTestDTO group ID not found: ", iceTestDTO.getGroupId());
                            }
                            iceTestGroupDTO.addOrUpdateChildDTO(iceTestDTO);
                        }
                    } else {
                        dtoList.add(iceTestSuiteDTO);
                        logger.error(METHODNAME, "iceTestDTO suite ID not found: ", iceTestDTO.getSuiteId());
                    }
                    updateTreeTable();
                } else {
                    logger.error(METHODNAME, "dtoList is null!");
                }
            } else {
                logger.error(METHODNAME, "dataTableMGR is null!");
            }
        } else {
            logger.error(METHODNAME, "iceTestDTO is null!");
        }
    }

    private void removeItemFromBaseDataTableMGRDTOList(IceTestGroupDTO iceTestGroupDTO) {
        final String METHODNAME = "updateBaseDataTableMGRDTOList ";
        DataTableInterface<IceTestSuiteDTO> dataTableMGR = iceTestSuiteMGR.getDataTableMGR();
        if (iceTestGroupDTO != null) {
            if (dataTableMGR != null) {
                List<IceTestSuiteDTO> dtoList = dataTableMGR.getDtoList();
                if (dtoList != null) {
                    IceTestSuiteDTO iceTestSuiteDTO = new IceTestSuiteDTO();
                    iceTestSuiteDTO.setSuiteId(iceTestGroupDTO.getSuiteId());
                    int loc = dtoList.indexOf(iceTestSuiteDTO);
                    if (loc != -1) {
                        iceTestSuiteDTO = dtoList.get(loc);
                        List<IceTestGroupDTO> iceTestGroupDTOs = iceTestSuiteDTO.getIceTestGroupDTOs();
                        loc = iceTestGroupDTOs.indexOf(iceTestGroupDTO);
                        if (loc != -1) {
                            iceTestGroupDTOs.remove(iceTestGroupDTO);
                        } else {
                            logger.error(METHODNAME, "iceTestGroupDTO not located!");
                        }
                    } else {
                        logger.error(METHODNAME, "iceTestSuiteDTO not located!");
                    }
                    updateTreeTable();
                } else {
                    logger.error(METHODNAME, "dtoList is null!");
                }
            } else {
                logger.error(METHODNAME, "dataTableMGR is null!");
            }
        } else {
            logger.error(METHODNAME, "iceTestGroupDTO is null!");
        }
    }

    private void removeItemFromBaseDataTableMGRDTOList(IceTestDTO iceTestDTO) {
        final String METHODNAME = "updateBaseDataTableMGRDTOList ";
        DataTableInterface<IceTestSuiteDTO> dataTableMGR = iceTestSuiteMGR.getDataTableMGR();
        if (iceTestDTO != null) {
            if (dataTableMGR != null) {
                List<IceTestSuiteDTO> dtoList = dataTableMGR.getDtoList();
                if (dtoList != null) {
                    IceTestSuiteDTO iceTestSuiteDTO = new IceTestSuiteDTO();
                    iceTestSuiteDTO.setSuiteId(iceTestDTO.getSuiteId());
                    int loc = dtoList.indexOf(iceTestSuiteDTO);
                    if (loc != -1) {
                        iceTestSuiteDTO = dtoList.get(loc);
                        List<IceTestGroupDTO> iceTestGroupDTOs = iceTestSuiteDTO.getIceTestGroupDTOs();
                        IceTestGroupDTO iceTestGroupDTO = new IceTestGroupDTO();
                        iceTestGroupDTO.setGroupId(iceTestDTO.getGroupId());
                        loc = iceTestGroupDTOs.indexOf(iceTestGroupDTO);
                        if (loc != -1) {
                            iceTestGroupDTO = iceTestGroupDTOs.get(loc);
                            List<IceTestDTO> iceTestDTOs = iceTestGroupDTO.getIceTestDTOs();
                            loc = iceTestDTOs.indexOf(iceTestDTO);
                            if (loc != -1) {
                                iceTestDTOs.remove(iceTestDTO);
                            } else {
                                logger.error(METHODNAME, "iceTestDTO not located!");
                            }
                        } else {
                            logger.error(METHODNAME, "iceTestGroupDTO not located!");
                        }
                    } else {
                        logger.error(METHODNAME, "iceTestSuiteDTO not located!");
                    }
                    updateTreeTable();
                } else {
                    logger.error(METHODNAME, "dtoList is null!");
                }
            } else {
                logger.error(METHODNAME, "dataTableMGR is null!");
            }
        } else {
            logger.error(METHODNAME, "iceTestGroupDTO is null!");
        }
    }

    public void updateTreeTable() {
        final String METHODNAME = "updateTreeTable ";
        long start = System.nanoTime();
        logger.debug(METHODNAME, "called!");
        List<Object> expandedList = new ArrayList<Object>();
        for (TreeNode item : getTreeTableRoot().getChildren()) {
            if (item.isExpanded()) {
                expandedList.add(item.getData());
            }
            for (TreeNode subItem : item.getChildren()) {
                if (subItem.isExpanded()) {
                    expandedList.add(subItem.getData());
                }
            }
        }
        treeTableRoot = new DefaultTreeNode("root", null);
        try {
            DataTableInterface<IceTestSuiteDTO> dataTableMGR = iceTestSuiteMGR.getDataTableMGR();
            if (dataTableMGR != null) {
                List<IceTestSuiteDTO> dtoList = dataTableMGR.getDtoList();
                if (dtoList != null) {
                    if (dtoList.isEmpty()) {
                        TreeNode suite = new DefaultTreeNode("suite", null, treeTableRoot);
                    } else {
                        Collections.sort(dtoList, iceTestSuiteComparator);
                        for (IceTestSuiteDTO iceTestSuiteDTO : dtoList) {
                            logger.debug(METHODNAME, "processing: ", iceTestSuiteDTO.getName());
                            if (expandedList.contains(iceTestSuiteDTO) && iceTestSuiteDTO.getIceTestGroupDTOs().isEmpty()) {
                                setNodeChildrenForIceTestSuiteDTO(iceTestSuiteDTO);
                            }
                            TreeNode suite = new DefaultTreeNode("suite", iceTestSuiteDTO, getTreeTableRoot());
                            if (expandedList.contains(iceTestSuiteDTO)) {
                                suite.setExpanded(true);
                            }
                            List<IceTestGroupDTO> groupList = iceTestSuiteDTO.getIceTestGroupDTOs();
                            if (groupList.isEmpty()) {
                                TreeNode group = new DefaultTreeNode("group", null, suite);
                            } else {
                                Collections.sort(groupList, iceTestGroupComparator);
                                for (IceTestGroupDTO iceTestGroupDTO : groupList) {
                                    if (expandedList.contains(iceTestGroupDTO) && iceTestGroupDTO.getIceTestDTOs().isEmpty()) {
                                        setNodeChildrenForIceTestGroupDTO(iceTestGroupDTO);
                                    }
                                    TreeNode group = new DefaultTreeNode("group", iceTestGroupDTO, suite);
                                    if (expandedList.contains(iceTestGroupDTO)) {
                                        group.setExpanded(true);
                                    }
                                    List<IceTestDTO> testList = iceTestGroupDTO.getIceTestDTOs();
                                    if (testList.isEmpty()) {
                                        TreeNode test = new DefaultTreeNode("test", null, group);
                                    } else {
                                        if ("id".equals(iceTestSuiteMGR.getSortTestBy())) {
                                            Collections.sort(testList, iceTestByIdComparator);
                                        } else if ("lastmod".equals(iceTestSuiteMGR.getSortTestBy())) {
                                            Collections.sort(testList, iceTestByLastModComparator);
                                        } else {
                                            Collections.sort(testList, iceTestComparator);
                                        }
                                        for (IceTestDTO iceTestDTO : testList) {
                                            TreeNode test = new DefaultTreeNode("test", iceTestDTO, group);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            logger.info(METHODNAME, "treeTable.getClientId()=", getTreeTableUpdateId());
            iceTestSuiteMGR.setUpdateIds(METHODNAME, getTreeTableUpdateId());
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logDuration(METHODNAME, start);
        }
    }

    // utility methods
    public boolean isIceTestSuiteDTO(Object dto) {
        return dto instanceof IceTestSuiteDTO;
    }

    public boolean isIceTestGroupDTO(Object dto) {
        return dto instanceof IceTestGroupDTO;
    }

    public boolean isIceTestDTO(Object dto) {
        return dto instanceof IceTestDTO;
    }

    /**
     * Get the value of treeTableId
     *
     * @return the value of treeTableId
     */
    public String getTreeTableId() {
        return TREE_TABLE_ID;
    }

    public String getTreeTableUpdateId() {
        String result = null;
        try {
            result = ComponentUtils.findComponentClientId(TREE_TABLE_ID);
        } catch (Exception e) {
            logger.debug(e);
        }
        return result;
    }
}
