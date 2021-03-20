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
package org.cdsframework.section.auditlog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.apache.commons.beanutils.PropertyUtils;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.callback.OnUndoCallback;
import org.cdsframework.dto.AuditTransactionDTO;
import org.cdsframework.dto.AuditLogDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.enumeration.AuditTransaction;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.group.ByGeneralProperties;
import org.cdsframework.util.ClassUtils;
import org.cdsframework.util.DTOUtils;
import org.cdsframework.util.DateUtils;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.comparator.AuditTransactionComparator;
import org.cdsframework.util.comparator.AuditLogComparator;
import org.cdsframework.util.support.CoreConstants;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.util.ComponentUtils;

/**
 *
 * @author HLN Consulting LLC
 *
 * Base AuditLogMGR used to search the audit log for any BaseAuditLogDTO descendant
 *
 * To Do: @Table(databaseId = "CIR", provide an alternate database identifier at the DAO layer This would eliminate the need to
 * create all these descendants
 *
 */
@Named
@ViewScoped
public class AuditTransactionMGR extends BaseModule<AuditTransactionDTO> {

    private static final long serialVersionUID = -4061286334844466168L;

    private final String treeTableId = "auditTree";
    private TreeNode treeTableRoot;
    private OnUndoCallback onUndoCallback;
    private BaseModule auditParentMGR = null;
    private final String CHILDTREEPLACEHOLDER = "CHILDTREEPLACEHOLDER";
    private int pageSize = 15;
    private int rowOffset = 0;
    private int rowCount = 0;
    private int currentPage = 0;
    private int pageCount = 0;
    private String pagePosition = null;
    private boolean transactionUndoable = false;

    private enum QueryType {

        Rowcount, Page
    };

    @Override
    protected void initialize() {
        setSaveImmediately(true);
    }

    public void undoMain(ActionEvent actionEvent) {
        final String METHODNAME = "undoMain ";
        List<AuditLogDTO> auditLogDTOs = new ArrayList<AuditLogDTO>();
        logger.info(METHODNAME, "treeTableRoot.getChildCount()=", treeTableRoot.getChildCount());
        List<TreeNode> treeNodes = treeTableRoot.getChildren();
        for (TreeNode treeNode : treeNodes) {
            getAuditLogDTO(treeNode, auditLogDTOs);
        }
        String undoMessage;
        if (!auditLogDTOs.isEmpty()) {
            boolean success = undo(auditLogDTOs);
            // Call undo logic
            if (success) {
                undoMessage = "Undo was successful, please review the changes.";

                // Update the Parent Form
                setUpdateIds(METHODNAME, auditParentMGR.getFormEditUpdateId());
                // Fire javascript to notify client that a change occurred.
                RequestContext.getCurrentInstance().execute(String.format("onChange('%s')", auditParentMGR.getEditFormId()));
                if (onUndoCallback != null) {
                    onUndoCallback.onUndo(OnUndoCallback.UndoStatus.Success);
                }
            } else {
                undoMessage = "Undo failed, please provide any information that can assist in diagnosing the problem.";
                if (onUndoCallback != null) {
                    onUndoCallback.onUndo(OnUndoCallback.UndoStatus.Failure);
                }
            }
        } else {
            undoMessage = "Please select the records to undo by clicking on the check boxes.";
        }
        getMessageMGR().displayInfoMessage(undoMessage);
    }

    private void getAuditLogDTO(TreeNode treeNode, List<AuditLogDTO> auditLogDTOs) {
        final String METHODNAME = "getAuditLogDTO ";
        logger.info(METHODNAME, "treeNode.isExpanded()=", treeNode.isExpanded());
        logger.info(METHODNAME, "treeNode.isLeaf()=", treeNode.isLeaf());
        logger.info(METHODNAME, "treeNode.getChildCount()=", treeNode.getChildCount());
        logger.info(METHODNAME, "treeNode.getType()=", treeNode.getType());
        logger.info(METHODNAME, "treeNode.getData()=", treeNode.getData());

        if (treeNode.isLeaf()) {
            Object treeNodeData = treeNode.getData();
            if (treeNodeData != null) {
                AuditNode auditNode = (AuditNode) treeNodeData;
                logger.info(METHODNAME, "auditNode.isSelected()=", auditNode.isSelected());
                if (auditNode.isSelected()) {
                    auditLogDTOs.add(auditNode.getAuditLogDTO());
                }
            }
        } else {
            List<TreeNode> auditTreeNodes = treeNode.getChildren();
            for (TreeNode auditTreeNode : auditTreeNodes) {
                getAuditLogDTO(auditTreeNode, auditLogDTOs);
            }
        }
    }

    private boolean undo(List<AuditLogDTO> auditLogDTOs) {
        final String METHODNAME = "undo ";
        logger.info(METHODNAME, "auditLogDTOs.size()=", auditLogDTOs.size());
        boolean auditSuccess = false;

        // Sort the collection by classNames/auditId
        Collections.sort(auditLogDTOs, new AuditLogComparator(AuditLogComparator.SortParameter.CREATE_DATETIME_CLASSNAME_AUDIT_ID_ASC));
        String prevClassnameAuditId = null;
        ParentChildRelationship parentChildRelationship = null;

        // Map of Parent Child Relationships
        Map<Class<? extends BaseDTO>, ParentChildRelationship> parentChildRelationshipMap = DTOUtils.getParentChildRelationshipMapByDTO(auditParentMGR.getParentDTO().getClass());
        BaseDTO childDTO = null;

        for (AuditLogDTO auditLogDTO : auditLogDTOs) {
            AuditTransaction transactionType = auditLogDTO.getTransactionType();
            logger.info(METHODNAME, auditLogDTO.getClassName(), ".", auditLogDTO.getPropertyName(),
                    " oldValue=", auditLogDTO.getOldValue(), " newValue=", auditLogDTO.getNewValue(),
                    " transactionType=", transactionType);

            String classnameAuditId = auditLogDTO.getClassName() + "-" + auditLogDTO.getAuditId();
            if (!classnameAuditId.equals(prevClassnameAuditId)) {
                // Reset trackers
                prevClassnameAuditId = classnameAuditId;
                parentChildRelationship = getParentChildRelationship(parentChildRelationshipMap, auditLogDTO.getClassName());
                childDTO = null;
            }

            // Updating the Parent ?
            if (auditLogDTO.getClassName().equalsIgnoreCase(auditParentMGR.getParentDTO().getClass().getCanonicalName())) {
                if (transactionType == AuditTransaction.UPDATE) {
                    // Apply old values to parent
                    updateProperty(auditParentMGR.getParentDTO(), auditLogDTO);
                }
            } else //
            // Handle Insert -> Deletes the match
            //        Update -> Updates the match
            //
            {
                if (transactionType != AuditTransaction.DELETE) {
                    // Locate match
                    if (childDTO == null) {
                        List<BaseDTO> childrenDTOs = auditParentMGR.getParentDTO().getChildrenDTOs(parentChildRelationship.childQueryClass());
                        // Locate a match 
                        for (BaseDTO childrenDTO : childrenDTOs) {
                            logger.info(METHODNAME, "childrenDTO.getAuditId()=", childrenDTO.getAuditId(),
                                    " auditLogDTO.getAuditId()=", auditLogDTO.getAuditId(),
                                    " childrenDTO.getClass().getCanonicalName()=", childrenDTO.getClass().getCanonicalName(),
                                    " childrenDTO.getPrimaryKey()=", childrenDTO.getPrimaryKey());
                            // Locate match
                            if (childrenDTO.getAuditId().equals(auditLogDTO.getAuditId())) {
                                // Match
                                childDTO = childrenDTO;
                                logger.info(METHODNAME, "childDTO matched", childDTO.getAuditId());
                                break;
                            }
                        }
                    }
                    logger.info(METHODNAME, "childDTO.getPrimaryKey()=", childDTO.getPrimaryKey(), " childDTO matched", childDTO.getAuditId());
                    if (childDTO != null) {
                        if (transactionType == AuditTransaction.INSERT) {
                            // Only need to process it once
                            if (!childDTO.isDeleted()) {
                                childDTO.delete(true);
                            }
                        } else if (transactionType == AuditTransaction.UPDATE) {
                            // Apply old values to childDTOMatch
                            updateProperty(childDTO, auditLogDTO);
                        }
                    }
                } else {
                    try {
                        // Delete Inserts a new DTO
                        if (childDTO == null) {
                            try {
                                // Need to handle null case, possibly parentChildRelationShip is null, try undo of a delete provider
                                childDTO = parentChildRelationship.childDtoClass().newInstance();
                                auditParentMGR.getParentDTO().addOrUpdateChildDTO(childDTO);
                            } catch (InstantiationException ex) {
                                logger.error("An InstantiationException has occurred; Message: ", ex.getMessage(), ex);
                            } catch (IllegalAccessException ex) {
                                logger.error("An IllegalAccessException has occurred; Message: ", ex.getMessage(), ex);
                            }
                        }
                        updateProperty(childDTO, auditLogDTO);
                    } catch (SecurityException ex) {
                        logger.error("An SecurityException has occurred; Message: ", ex.getMessage(), ex);
                    }
                }
            }
            auditSuccess = true;
        }
        if (auditSuccess) {
            setUpdateIds(METHODNAME, this.getFormEditUpdateId());
        }
        return auditSuccess;
    }

    private void updateProperty(BaseDTO baseDTO, AuditLogDTO auditLogDTO) {
        final String METHODNAME = "updateProperty ";

        try {
            Object property = PropertyUtils.getProperty(baseDTO, auditLogDTO.getPropertyName());

            // Property will be null if the baseDTO that contains the property is null;
            if (property == null) {
                Field field = ClassUtils.getDeclaredField(baseDTO.getClass(), auditLogDTO.getPropertyName());
                if (field.getType() == Boolean.class) {
                    property = new Boolean(null);
                } else {
                    property = field.getType().newInstance();
                }
            }

            // Get the Property to update
            logger.info(METHODNAME, "property.getClass().isPrimitive()=", property.getClass().isPrimitive(),
                    " property.getClass().getCanonicalName()=", property.getClass().getCanonicalName(),
                    " property=", property);
            if (property instanceof BaseDTO) {
                logger.info(METHODNAME, "instanceof BaseDTO");
                BaseDTO baseDTOProperty = (BaseDTO) property;
                BaseDTO queryDTO = baseDTOProperty.getClass().newInstance();

                List<Field> primaryKeyFields = DTOUtils.getPrimaryKeyFields(baseDTOProperty.getClass());
                if (primaryKeyFields.size() == 1) {
                    Field field = primaryKeyFields.get(0);
                    Object convertedValue = convertStringValue(field.getType(), auditLogDTO.getOldValue());
                    PropertyUtils.setProperty(queryDTO, field.getName(), convertedValue);
                    BaseDTO resultDTO = getMts().getGeneralMGR().findByPrimaryKey(queryDTO, getSessionDTO(), new PropertyBagDTO());
                    PropertyUtils.setProperty(baseDTO, auditLogDTO.getPropertyName(), resultDTO);
                } else {
                    // Need to store each referenceDTO primaryKey field in the auditLog
                }
            } else {
                Object convertedValue = convertStringValue(property, auditLogDTO.getOldValue());
                logger.info(METHODNAME, "setting ", auditLogDTO.getPropertyName(), " to ", convertedValue);
                PropertyUtils.setProperty(baseDTO, auditLogDTO.getPropertyName(), convertedValue);
            }
        } catch (IllegalAccessException ex) {
            logger.error("An IllegalAccessException has occurred; Message: ", ex.getMessage(), ex);
        } catch (InvocationTargetException ex) {
            logger.error("An InvocationTargetException has occurred; Message: ", ex.getMessage(), ex);
        } catch (NoSuchMethodException ex) {
            logger.error("An NoSuchMethodException has occurred; Message: ", ex.getMessage(), ex);
        } catch (InstantiationException ex) {
            logger.error("An InstantiationException has occurred; Message: ", ex.getMessage(), ex);
        } catch (ValidationException ex) {
            logger.error("An ValidationException has occurred; Message: ", ex.getMessage(), ex);
        } catch (NotFoundException ex) {
            logger.error("An NotFoundException has occurred; Message: ", ex.getMessage(), ex);
        } catch (MtsException ex) {
            logger.error("An MtsException has occurred; Message: ", ex.getMessage(), ex);
        } catch (AuthenticationException ex) {
            logger.error("An AuthenticationException has occurred; Message: ", ex.getMessage(), ex);
        } catch (AuthorizationException ex) {
            logger.error("An AuthorizationException has occurred; Message: ", ex.getMessage(), ex);
        } catch (SecurityException ex) {
            logger.error("An SecurityException has occurred; Message: ", ex.getMessage(), ex);
        } catch (NoSuchFieldException ex) {
            logger.error(ex);
        }

    }

    private Object convertStringValue(Object property, String propertyValue) {
        return convertStringValue(property.getClass(), propertyValue);
    }

    private Object convertStringValue(Class propertyClass, String propertyValue) {
        Object convertedValue = propertyValue;

        if (propertyClass.isEnum()) {
            convertedValue = Enum.valueOf((Class<? extends Enum>) propertyClass, propertyValue);
        } else if (propertyClass == Boolean.class) {
            convertedValue = Boolean.valueOf(propertyValue);
        } else if (propertyClass == Long.class) {
            convertedValue = Long.parseLong(propertyValue);
        } else if (propertyClass == Integer.class) {
            convertedValue = Integer.parseInt(propertyValue);
        } else if (propertyClass == Date.class) {
            convertedValue = DateUtils.parseDateFromString(propertyValue, DateUtils.ISO8601_DATETIME_FORMAT);
        }
        return convertedValue;
    }

    private ParentChildRelationship getParentChildRelationship(Map<Class<? extends BaseDTO>, ParentChildRelationship> parentChildRelationshipMap, String className) {
        final String METHODNAME = "getParentChildRelationship ";

        ParentChildRelationship parentChildRelationship = null;
        for (Map.Entry<Class<? extends BaseDTO>, ParentChildRelationship> parentChildRelationshipEntry : parentChildRelationshipMap.entrySet()) {
            Class<? extends BaseDTO> key = parentChildRelationshipEntry.getKey();
            ParentChildRelationship value = parentChildRelationshipEntry.getValue();
            logger.info(METHODNAME, "key=", key.getCanonicalName());
            logger.info(METHODNAME, "value.childQueryClass()=", value.childQueryClass());
            logger.info(METHODNAME, "value.childDtoClass()=", value.childDtoClass());

            if (key.getCanonicalName().equalsIgnoreCase(className)) {
                parentChildRelationship = parentChildRelationshipEntry.getValue();
                logger.info(METHODNAME, "parentChildRelationship.childQueryClass().getCanonicalName()=", parentChildRelationship.childQueryClass().getCanonicalName());
                logger.info(METHODNAME, "parentChildRelationship.childDtoClass().getCanonicalName()=", parentChildRelationship.childDtoClass().getCanonicalName());

                break;
            }
        }
        return parentChildRelationship;
    }

    public void onNodeExpand(NodeExpandEvent event) {
        final String METHODNAME = "onNodeExpand ";
        logger.info(METHODNAME);
        try {
            TreeNode treeNode = event.getTreeNode();
            logger.info(METHODNAME, "treeNode.getChildCount()=", treeNode.getChildCount());
            logger.info(METHODNAME, "treeNode.getChildren()=", treeNode.getChildren());
            logger.info(METHODNAME, "treeNode.getType()=", treeNode.getType());

            // Expanding Transaction, get the Audit Logs
            if (treeNode.getType().equalsIgnoreCase(AuditNode.AuditLevel.Audit.toString())) {

                if (treeNode.getChildCount() > 0) {
                    TreeNode firstChild = treeNode.getChildren().get(0);
                    logger.info(METHODNAME, "firstChild.getData()=", firstChild.getData());
                    logger.info(METHODNAME, "firstChild.getType()=", firstChild.getType());
                    // Clear out the ChildPlaceHolder
                    if (firstChild.getType().equalsIgnoreCase(CHILDTREEPLACEHOLDER)) {
                        ((DefaultTreeNode) treeNode).setChildren(new ArrayList<TreeNode>());
                    }
                }
                logger.info(METHODNAME, "treeNode.getChildCount()=", treeNode.getChildCount());
                if (treeNode.getChildCount() == 0) {
                    AuditNode auditNode = (AuditNode) treeNode.getData();
                    logger.info(METHODNAME, "auditNode.getClass().getCanonicalName()=", auditNode.getClass().getCanonicalName());
                    logger.info(METHODNAME, "auditNode.getAuditTransactionDTO().getTransactionId()=", auditNode.getAuditTransactionDTO().getTransactionId());
                    String transactionId = auditNode.getAuditTransactionDTO().getTransactionId();

                    PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                    propertyBagDTO.setQueryClass(AuditLogDTO.ByTransactionId.class.getSimpleName());
                    AuditLogDTO searchCriteriaDTO = new AuditLogDTO();
                    searchCriteriaDTO.setTransactionId(transactionId);
                    List<AuditLogDTO> auditLogDTOs = getMts().getGeneralMGR().findByQueryList(searchCriteriaDTO, getSessionDTO(), propertyBagDTO);
                    logger.info(METHODNAME, "auditLogDTOs.size()=", auditLogDTOs.size());
                    renderAuditLogs(treeNode, auditLogDTOs);
                }
            }

        } catch (ValidationException ex) {
            logger.error(ex);
        } catch (NotFoundException ex) {
            logger.error(ex);
        } catch (MtsException ex) {
            logger.error(ex);
        } catch (AuthenticationException ex) {
            logger.error(ex);
        } catch (AuthorizationException ex) {
            logger.error(ex);
        }
    }

    public void onNodeCheck(AuditNode auditNode) {
        final String METHODNAME = "onNodeCheck ";
        TreeNode matchingTreeNode = getMatchingTreeNode(treeTableRoot, auditNode);
        auditSelection(matchingTreeNode, auditNode);
        setUpdateIds(METHODNAME, getTreeTableUpdateId());
    }

    private void auditSelection(TreeNode treeNodeRoot, AuditNode matchingAuditNode) {
        List<TreeNode> treeNodes = treeNodeRoot.getChildren();
        for (TreeNode treeNode : treeNodes) {
            AuditNode auditNode = (AuditNode) treeNode.getData();
            auditNode.setSelected(matchingAuditNode.isSelected());
            auditSelection(treeNode, matchingAuditNode);
        }
    }

    private TreeNode getMatchingTreeNode(TreeNode treeNodeRoot, AuditNode matchingAuditNode) {
        List<TreeNode> treeNodes = treeNodeRoot.getChildren();
        TreeNode matchingTreeNode = null;
        for (TreeNode treeNode : treeNodes) {
            AuditNode auditNode = (AuditNode) treeNode.getData();
            if (auditNode == matchingAuditNode) {
                matchingTreeNode = treeNode;
                break;
            }
            matchingTreeNode = getMatchingTreeNode(treeNode, matchingAuditNode);
            if (matchingTreeNode != null) {
                break;
            }
        }
        return matchingTreeNode;
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
        final String METHODNAME = "onNodeCollapse ";
        logger.info(METHODNAME);
    }

    public void onNodeSelect(NodeSelectEvent event) {
        final String METHODNAME = "onNodeSelect ";
        logger.info(METHODNAME);
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        final String METHODNAME = "onNodeUnselect ";
        logger.info(METHODNAME);
    }

    public void displayAudit(BaseModule auditParentMGR) throws CatException {
        final String METHODNAME = "displayAudit ";
        logger.info(METHODNAME, "auditParentMGR=", auditParentMGR);

        // If not bound its likely due to the source not being included
        if (UtilityMGR.getUIComponentById(getTreeTableUpdateId()) == null) {
            throw new CatException("The treeTable was not found.<br><br>Did you forget to include /module/core/section/auditLog/popup/auditPopup.xhtml "
                    + "in your views/" + auditParentMGR.getName() + "MGR.xhtml?");
        }

        // Check if the DTO is flagged for auditing
        if (auditParentMGR.getParentDTO() != null && !auditParentMGR.getParentDTO().isAudit()) {
            throw new CatException("The parentDTO " + auditParentMGR.getParentDTO().getClass().getSimpleName() + " must be flagged for auditing. "
                    + "<br><br>Did you forget to annotate the " + auditParentMGR.getParentDTO().getClass().getSimpleName() + " with the @Audit annotation?");
        }

        // Show the popup
        RequestContext.getCurrentInstance().execute("PF('" + getPopupDialogWidgetVar() + "').show();");

        // Set the Audit Parent MGR
        this.auditParentMGR = auditParentMGR;
        rowOffset = 0;
        rowCount = 0;
        currentPage = 0;
        pageCount = 0;
        pagePosition = null;        
        getLazyRowCount();
        renderAuditTree();
    }

    public void registerOnUndoCallback(OnUndoCallback onUndoCallback) {
        this.onUndoCallback = onUndoCallback;
    }

    public void calculatePagePosition() {
        final String METHODNAME = "calculatePagePosition ";
        currentPage = rowOffset / pageSize + 1;
        pagePosition = " (" + currentPage + " of " + pageCount + ") ";
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getPagePosition() {
        return pagePosition;
    }

    public void setPagePosition(String pagePosition) {
        this.pagePosition = pagePosition;
    }

    private void getLazyRowCount() {
        final String METHODNAME = "getLazyRowCount ";
        logger.logBegin(METHODNAME);
        try {
            try {
                PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                propertyBagDTO.setQueryClass(ByGeneralProperties.class.getSimpleName());
                rowCount = getGeneralMGR().findObjectByQuery(getLazyQuery(QueryType.Rowcount), getSessionDTO(), Integer.class, propertyBagDTO);

                // Compute pageCount
                pageCount = rowCount / pageSize;
                if ((rowCount % pageSize) != 0) {
                    pageCount++;
                }
                else {
                    pageCount = 1;
                }
                        

                logger.debug(METHODNAME, "rowCount=", rowCount, " pageCount=", pageCount, " pageSize=", pageSize);

            } catch (Exception e) {
                onExceptionMain(METHODNAME, e);
            }
        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    private AuditTransactionDTO getLazyQuery(QueryType queryType) {
        AuditTransactionDTO queryDTO = new AuditTransactionDTO();
        queryDTO.getQueryMap().put(CoreConstants.LAZY, true);
        queryDTO.getQueryMap().put("auditId", auditParentMGR.getParentDTO().getAuditId());

        if (queryType == QueryType.Page) {
            queryDTO.getQueryMap().put(CoreConstants.LAZY_ROWCOUNT, false);
            queryDTO.getQueryMap().put(CoreConstants.LAZY_ROW_OFFSET, String.valueOf(rowOffset));
            queryDTO.getQueryMap().put(CoreConstants.LAZY_PAGE_SIZE, String.valueOf(pageSize));
        } else if (queryType == QueryType.Rowcount) {
            queryDTO.getQueryMap().put(CoreConstants.LAZY_ROWCOUNT, true);
        }

        return queryDTO;
    }

    public void firstPage() {
        this.rowOffset = 0;
        renderAuditTree();
    }

    public void lastPage() {
        this.rowOffset = (pageCount * pageSize) - pageSize;
        renderAuditTree();
    }

    public void nextPage() {
        this.rowOffset += this.pageSize;
        renderAuditTree();
    }

    public void previousPage() {
        this.rowOffset -= this.pageSize;
        renderAuditTree();
    }

    private void renderAuditTree() {
        final String METHODNAME = "renderAuditTree ";
        logger.logBegin(METHODNAME);
        treeTableRoot = new DefaultTreeNode("root", null);
        try {
            if (auditParentMGR.getParentDTO() != null && auditParentMGR.getParentDTO().getAuditId() != null) {
                logger.info(METHODNAME, "Searching for audit records, auditId=", auditParentMGR.getParentDTO().getAuditId());

                PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                propertyBagDTO.setQueryClass(ByGeneralProperties.class.getSimpleName());
                List<AuditTransactionDTO> auditTransactionDTOs = getGeneralMGR().findByQueryList(getLazyQuery(QueryType.Page), getSessionDTO(), propertyBagDTO);
                logger.info(METHODNAME, "auditTransactionDTOs.size()=", auditTransactionDTOs.size());

                // Sort Latest transaction first
//                Collections.sort(auditTransactionDTOs, new AuditTransactionComparator(AuditTransactionComparator.SortParameter.CREATE_DATETIME_DESC_TRANSACTION_ID));

                calculatePagePosition();
                int transactionCount = 0;
                logger.info(METHODNAME, "Generating treeTableRoot");

                for (AuditTransactionDTO auditTransactionDTO : auditTransactionDTOs) {
                    String transactionId = auditTransactionDTO.getTransactionId();
                    logger.info(METHODNAME, "transactionId=", transactionId, " auditTransactionDTO.getAuditId()=", auditTransactionDTO.getAuditId());
                    transactionCount++;
                    boolean undoable = false;
                    if (transactionCount == 1 && currentPage == 1) {
                        undoable = (auditTransactionDTO.getTransactionType() != AuditTransaction.INSERT);
                        if (undoable) {
                            transactionUndoable = undoable;
                        }
                    }
                    DefaultTreeNode defaultTreeNode = new DefaultTreeNode(AuditNode.AuditLevel.Audit.toString(), new AuditNode(AuditNode.AuditLevel.Audit, auditTransactionDTO, undoable), treeTableRoot);

                    // Create a entry to allow the Tree to render the expander
                    defaultTreeNode.setChildren(new ArrayList<TreeNode>());
                    DefaultTreeNode childPlaceHolder = new DefaultTreeNode();
                    childPlaceHolder.setType(CHILDTREEPLACEHOLDER);
                    defaultTreeNode.getChildren().add(childPlaceHolder);
                }

                logger.info(METHODNAME, "treeTableRoot.getChildren().size()=", treeTableRoot.getChildren().size());
            }
        } catch (Exception e) {
            onExceptionMain("renderAuditTree", e);
        } finally {
            String treeTableUpdateId = getTreeTableUpdateId();
            logger.info(METHODNAME, "updating ", treeTableUpdateId);
            setUpdateIds(METHODNAME, ComponentUtils.findComponentClientId("pageNavigation"));
            setUpdateIds(METHODNAME, treeTableUpdateId);
        }
    }

    private void renderAuditLogs(TreeNode treeNode, List<AuditLogDTO> auditLogDTOs) {
        final String METHODNAME = "renderAuditLogs ";
        String prevClassNameAuditId = null;
        TreeNode classTreeNode = null;
        // Assumes it sorted by CreateDateTime/ClassName/Audit Id
        Collections.sort(auditLogDTOs, new AuditLogComparator(AuditLogComparator.SortParameter.CREATE_DATETIME_CLASSNAME_AUDIT_ID_ASC));
        boolean undoable = ((AuditNode) treeNode.getData()).isUndoable();
        logger.info(METHODNAME, "treeNode.getChildCount()=", treeNode.getChildCount());
        logger.info(METHODNAME, "treeNode.getChildren()=", treeNode.getChildren());

        for (AuditLogDTO auditLogDTO : auditLogDTOs) {
            String currentClassNameAuditId = auditLogDTO.getClassName() + "-" + auditLogDTO.getAuditId();
            logger.info(METHODNAME, "currentClassNameAuditId=", currentClassNameAuditId);
            if (!currentClassNameAuditId.equalsIgnoreCase(prevClassNameAuditId)) {
                logger.info(METHODNAME, "adding ClassName to Tree");
                classTreeNode = new DefaultTreeNode("ClassName", new AuditNode(AuditNode.AuditLevel.ClassName, auditLogDTO, undoable), treeNode);
                prevClassNameAuditId = currentClassNameAuditId;
            }
            new DefaultTreeNode("PropertyName", new AuditNode(AuditNode.AuditLevel.PropertyName, auditLogDTO, undoable), classTreeNode);
            logger.info(METHODNAME, "auditLogDTO.getAuditId()=", auditLogDTO.getAuditId());
        }
        logger.info(METHODNAME, "treeNode.getChildCount()=", treeNode.getChildCount());
        logger.info(METHODNAME, "treeNode.getChildren().size()=", treeNode.getChildren().size());
        setUpdateIds(METHODNAME, getTreeTableUpdateId());
    }

    public TreeNode getTreeTableRoot() {
        return treeTableRoot;
    }

    public void setTreeTableRoot(TreeNode treeTableRoot) {
        this.treeTableRoot = treeTableRoot;
    }

    private String getTreeTableUpdateId() {
        String result = null;
        try {
            result = ComponentUtils.findComponentClientId(treeTableId);
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }

    public String getPopupDialogWidgetVar() {
        return this.getName() + "popupDialogWidgetVar";
    }

    public boolean isTransactionUndoable() {
        return transactionUndoable;
    }

    public void setTransactionUndoable(boolean transactionUndoable) {
        this.transactionUndoable = transactionUndoable;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public String getTreeTableId() {
        return treeTableId;
    }

}
