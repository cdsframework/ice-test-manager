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
package org.cdsframework.base;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;
import org.cdsframework.dto.CriteriaPredicateDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.enumeration.CriteriaPredicateType;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.interfaces.PredicateInterface;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.enumeration.SourceMethod;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.cdsframework.interfaces.PredicateMGRInterface;
import org.cdsframework.interfaces.PredicatePartInterface;
import org.cdsframework.util.ClassUtils;
import org.primefaces.event.NodeSelectEvent;

/**
 *
 * @author HLN Consulting, LLC
 * @param <T>
 * @param <Y>
 */
public abstract class BasePredicateMGR<
        T extends BasePredicateDTO & PredicateInterface, Y extends BasePredicatePartDTO & PredicatePartInterface>
        extends BaseTreeMGR<T> implements PredicateMGRInterface<T, Y> {

    private static final long serialVersionUID = 8570405675503223604L;
    protected Comparator nodeComparator;
    protected boolean predicatesDirty = false;
    protected T selectedCriteriaPredicateDTO;
    private Class<? extends BaseDTO> partClassType;
    private List<T> fullPredicateList;

    @Override
    public void initializePredicateMGRMain() {
        initializeClassTypes();
        initializeNodeComparator();
        initializePredicateMGR();
    }

    private void initializeClassTypes() {
        List<Class> typeArguments = ClassUtils.getTypeArguments(BaseModule.class, getClass());

        if (typeArguments.size() == 2) {
            partClassType = typeArguments.get(1);
            logger.info("Derived partClassType: ", partClassType);
        }
    }

    @Override
    protected void treeInitialize() {
        initializePredicateMGRMain();
    }

    @Override
    public void updateTreeTable(List<T> dtoList) {
        final String METHODNAME = "updateTreeTable ";
        logger.debug(METHODNAME, "dtoList: ", dtoList);
        Collections.sort((List) dtoList, nodeComparator);
        addListToNode(dtoList, getTreeTableRoot(), true);
    }

    @Override
    protected void onNodeSelect(NodeSelectEvent event) {
        final String METHODNAME = "onNodeSelect ";
        RequestContext context = RequestContext.getCurrentInstance();
        String editDialogWidgetVar = null;
        String updateIds = null;
        Object nodeData = event != null ? event.getTreeNode() != null ? event.getTreeNode().getData() : null : null;
        if (nodeData != null) {
            if (nodeData.getClass() == getDtoClassType()) {
                onRowSelectMain(UtilityMGR.getEmulatedSelectEvent((T) nodeData));
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

    protected void addPredicateDTOToTree(List<BasePredicateDTO> dtoList, BasePredicateDTO dto) {
        for (BasePredicateDTO item : dtoList) {
            List<BasePredicateDTO> criteriaPredicateDTOs = (List) item.getPredicateDTOs();
            if (item.getPredicateId().equals(dto.getParentPredicateId()) && !criteriaPredicateDTOs.contains(dto)) {
                criteriaPredicateDTOs.add(dto);
                return;
            }
            addPredicateDTOToTree(criteriaPredicateDTOs, dto);
        }
    }

    protected void removePredicateDTOFromTree(List<? extends BasePredicateDTO> dtoList, BasePredicateDTO dto) {
        final String METHODNAME = "removePredicateDTOFromTree ";
        if (dtoList != null && !dtoList.isEmpty()) {
            logger.info(METHODNAME, "attempting to remove ", dto, " from ", dtoList);
            if (dtoList.contains(dto)) {
                logger.info(METHODNAME, "removing ", dto, " from ", dtoList);
                dtoList.remove(dto);
                return;
            }
            for (BasePredicateDTO item : dtoList) {
                if (!item.getPredicateDTOs().isEmpty()) {
                    removePredicateDTOFromTree(item.getPredicateDTOs(), dto);
                }
            }
        }
    }

    private void addListToNode(
            List<? extends BaseDTO> dtoList,
            TreeNode parentNode,
            boolean renderEmptyChild) {
        final String METHODNAME = "addListToNode ";
        logger.debug(METHODNAME, "parentNode: ", parentNode);

        if (dtoList == null || dtoList.isEmpty()) {
//            logger.info(METHODNAME, "rendering empty node list!");
//            if (renderEmptyChild) {
//                new DefaultTreeNode("childLessNode", null, parentNode);
//            }
        } else {
            Iterator<? extends BaseDTO> iterator = dtoList.iterator();
            while (iterator.hasNext()) {
                BaseDTO item = iterator.next();
                renderEmptyChild = true;
//                boolean expanded = getExpandedList().contains(item.getUuid());
                List<? extends BaseDTO> childNodes = null;
                TreeNode childNode = null;

                if (item.getClass() == getDtoClassType()) {
                    T predicateDTO = (T) item;
                    if (predicateDTO.getPredicateType() != null) {
                        switch (predicateDTO.getPredicateType()) {
                            case PredicateGroup:
                                //                    item = predicateDTO;
                                childNodes = predicateDTO.getPredicateDTOs();
                                Collections.sort((List) childNodes, nodeComparator);
                                childNode = new DefaultTreeNode("openGroupNode", predicateDTO, parentNode);
                                break;
                            case Predicate:
                                childNode = new DefaultTreeNode("predicateNode", predicateDTO, parentNode);
                                break;
                            case Criteria:
                                childNode = new DefaultTreeNode("predicateNode", predicateDTO, parentNode);
                                break;
                            default:
                                break;
                        }
                    }

                }

                if (childNode != null) {
                    childNode.setExpanded(true);
                }

                addListToNode(childNodes, childNode, renderEmptyChild);

                if (item.getClass() == getDtoClassType()) {
                    T predicateDTO = (T) item;
                    if (predicateDTO.getPredicateType() != null) {
                        switch (predicateDTO.getPredicateType()) {
                            case PredicateGroup:
                                childNode = new DefaultTreeNode("closeGroupNode", "}", parentNode);
                                if (iterator.hasNext()) {
                                    childNode = new DefaultTreeNode("operatorNode", predicateDTO.getPredicateConjunction().name(), parentNode);
                                }
                                break;
                            case Predicate:
                                if (iterator.hasNext()) {
                                    childNode = new DefaultTreeNode("operatorNode", predicateDTO.getPredicateConjunction().name(), parentNode);
                                }
                                break;
                            case Criteria:
                                if (iterator.hasNext()) {
                                    childNode = new DefaultTreeNode("operatorNode", predicateDTO.getPredicateConjunction().name(), parentNode);
                                }
                                break;
                            default:
                                break;
                        }
                    }

                }
            }
        }
    }

    public void onNodePromote(T predicateDTO) throws Exception {
        setSelectedNodeFromObject(predicateDTO);
        onNodeMove(true);
    }

    public void onNodeDemote(T predicateDTO) throws Exception {
        setSelectedNodeFromObject(predicateDTO);
        onNodeMove(false);
    }

    private void onNodeMove(boolean promote) throws Exception {
        final String METHODNAME = "onNodeMove ";
        DefaultTreeNode selectedNode = getSelectedNode();
        logger.debug(METHODNAME, "promote: ", promote);
        if (selectedNode != null) {
            // get the selected node
            Object data = selectedNode.getData();
            logger.debug(METHODNAME, "data: ", data);

            if (data.getClass() == getDtoClassType()) {
                T dto = (T) data;
                T parentDTO = getParentPredicateDTO((List<T>) getDataTableMGR().getDtoList(), dto);
                List<T> peerDTOs;
                logger.debug(METHODNAME, "dto: ", dto);
                if (parentDTO == null) {
                    logger.debug(METHODNAME, "parentDTO == null");
                    peerDTOs = (List) getDataTableMGR().getDtoList();
                } else {
                    logger.debug(METHODNAME, "parentDTO != null");
                    parentDTO.getPredicateDTOs();
                    peerDTOs = (List<T>) parentDTO.getPredicateDTOs();
                }
                Collections.sort(peerDTOs, nodeComparator);
                int c = 1;
                for (BasePredicateDTO item : peerDTOs) {
                    CriteriaPredicateDTO criteriaPredicateDTO = (CriteriaPredicateDTO) item;
                    criteriaPredicateDTO.setPredicateOrder(c++);
                }
                logger.debug(METHODNAME, "peerPredicateDTOs: ", peerDTOs);
                int indexOf = peerDTOs.indexOf(dto);
                logger.debug(METHODNAME, "indexOf: ", indexOf);

                boolean skip = false;
                if (indexOf == -1) {
                    skip = true;
                    logger.info(METHODNAME, "skipping: indexOf was less than 1: ", indexOf);
                }

                if (!skip && promote && indexOf == 0) {
                    skip = true;
                    logger.info(METHODNAME, "skipping: !promote && indexOf == 0");
                }

                if (!skip && !promote && indexOf == (peerDTOs.size() - 1)) {
                    skip = true;
                    logger.info(METHODNAME, "skipping: promote && indexOf == (peerDTOs.size() - 1)");
                }

                if (!skip) {
                    T peerDTO;
                    try {
                        // swap order value with peer
                        if (promote) {
                            peerDTO = peerDTOs.get(indexOf - 1);
                        } else {
                            peerDTO = peerDTOs.get(indexOf + 1);
                        }

                        int peerPredicateOrder = peerDTO.getPredicateOrder();
                        int dtoPredicateOrder = dto.getPredicateOrder();
                        if (peerPredicateOrder == dtoPredicateOrder) {
                            if (promote) {
                                dtoPredicateOrder++;
                            } else {
                                dtoPredicateOrder--;
                            }
                        }
                        logger.debug(METHODNAME, "peer predicateOrder: ", peerPredicateOrder);
                        logger.debug(METHODNAME, "setting peerDTO predicateOrder: ", dtoPredicateOrder);
                        logger.debug(METHODNAME, "setting dto predicateOrder: ", peerPredicateOrder);
                        ((CriteriaPredicateDTO)peerDTO).setPredicateOrder(dtoPredicateOrder);
                        ((CriteriaPredicateDTO)dto).setPredicateOrder(peerPredicateOrder);

                        setParentDTO((T) peerDTO);
                        saveMain(UtilityMGR.getEmulatedActionEvent(), false);

                        setParentDTO((T) dto);
                        saveMain(UtilityMGR.getEmulatedActionEvent(), false);
                        peerDTO = getFullPredicateDTO(peerDTO);
                        logger.debug(METHODNAME, "returned peerDTO predicateOrder: ", peerDTO.getPredicateOrder());
                        dto = getFullPredicateDTO(dto);
                        logger.debug(METHODNAME, "returned dto predicateOrder: ", dto.getPredicateOrder());
                        if (parentDTO != null) {
                            parentDTO = getFullPredicateDTO(parentDTO);
//                            for (CriteriaPredicateDTO item : parentDTO.getPredicateDTOs()) {
//                                logger.debug(METHODNAME, "parent child predicate order: ", item.getPredicateId(), "; order: ", item.getPredicateOrder());
//                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        logger.info(METHODNAME, "index was out of bounds");
                    }
                }
            } else {
                logger.info(METHODNAME, "selectedNode is not a predicate: ", selectedNode.getData());
            }
        } else {
            logger.info(METHODNAME, "selectedNode is null!");
        }
    }

    public void onNodeAdd(Object nodeData) {
        final String METHODNAME = "onNodeAdd ";
        String editDialogWidgetVar = null;
        logger.info(METHODNAME, "nodeData: ", nodeData);
        if (nodeData != null) {
            try {
                setSelectedNodeFromObject(nodeData);
                addMain(UtilityMGR.getEmulatedActionEvent());
                T child = getParentDTO();
                logger.info(METHODNAME, "child: ", child);
                editDialogWidgetVar = getEditDialogWidgetVar("edit");
                logger.info(METHODNAME, "editDialogWidgetVar: ", editDialogWidgetVar);
                T parent = (T) getSelectedNode().getData();
                selectedCriteriaPredicateDTO = parent;
                child.setParentPredicateId(parent.getPredicateId());
                logger.info(METHODNAME, "adding new child to parentDTO: ", parent);
                if (editDialogWidgetVar != null) {
                    RequestContext.getCurrentInstance().execute("PF('" + editDialogWidgetVar + "').show()");
                }
            } catch (Exception e) {
                onExceptionMain(SourceMethod.addMain, e);
            }
        }
    }

    private T getParentPredicateDTO(List<T> dtos, BasePredicateDTO dto) {
        if (dto.getParentPredicateId() == null) {
            return null;
        } else {
            T result = null;
            for (T item : dtos) {
                if (item.getPredicateDTOs().contains(dto)) {
                    result = item;
                    break;
                } else {
                    result = getParentPredicateDTO((List<T>) item.getPredicateDTOs(), dto);
                    if (result != null) {
                        break;
                    }
                }
            }
            return result;
        }
    }

    @Override
    public boolean isGroupNode(Object nodeData) {
        return nodeData instanceof PredicateInterface
                && ((T) nodeData).getPredicateType() == CriteriaPredicateType.PredicateGroup;
    }

    @Override
    public boolean isPredicateNode(Object nodeData) {
        return nodeData instanceof PredicateInterface
                && ((PredicateInterface) nodeData).getPredicateType() == CriteriaPredicateType.Predicate;
    }

    @Override
    public boolean isStringNode(Object nodeData) {
        return nodeData instanceof String;
    }

    public boolean isCriteriaNode(Object nodeData) {
        final String METHODNAME = "isCriteriaNode ";
        logger.debug(METHODNAME, "nodeData=", nodeData);

        if (nodeData instanceof PredicateInterface) {
            PredicateInterface predicateInterface = (PredicateInterface) nodeData;
            logger.debug(METHODNAME, "predicateInterface.getPredicateType()=", predicateInterface.getPredicateType());
        } else {
            logger.debug(METHODNAME, "NOT instanceof PredicateInterface");
        }
        return nodeData instanceof PredicateInterface
                && ((PredicateInterface) nodeData).getPredicateType() == CriteriaPredicateType.Criteria;
    }

    /**
     * Get the value of predicatesDirty
     *
     * @return the value of predicatesDirty
     */
    public boolean isPredicatesDirty() {
        return predicatesDirty;
    }

    /**
     * Set the value of predicatesDirty
     *
     * @param predicatesDirty new value of predicatesDirty
     */
    public void setPredicatesDirty(boolean predicatesDirty) {
        String METHODNAME = "setPredicatesDirty ";
        logger.info(METHODNAME, "predicatesDirty=", predicatesDirty);
        this.predicatesDirty = predicatesDirty;
    }

    public void saveTier3Selections() {
        final String METHODNAME = "saveTier3Selections ";
        long start = System.nanoTime();
        if (fullPredicateList != null) {
            for (BasePredicateDTO criteriaPredicateDTO : fullPredicateList) {
                logger.info(METHODNAME, "criteriaPredicateDTO=", criteriaPredicateDTO);
                logger.info(METHODNAME, "criteriaPredicateDTO.getOperationDTOState()=", criteriaPredicateDTO.getOperationDTOState());
                if (criteriaPredicateDTO.getOperationDTOState() != DTOState.UNSET) {
                    saveTier3Selections(criteriaPredicateDTO);
                }
            }
            setPredicatesDirty(true);
        }
        logger.logDuration(METHODNAME, start);
    }

    private void saveTier3Selections(BasePredicateDTO criteriaPredicateDTO) {
        final String METHODNAME = "saveTier3Selections ";
        for (BasePredicateDTO item : criteriaPredicateDTO.getPredicateDTOs()) {
            saveTier3Selections(item);
        }
        for (BasePredicatePartDTO item : criteriaPredicateDTO.getPredicatePartDTOs()) {
            DTOState dtoState = item.getDTOState();
            if (dtoState != DTOState.UNSET) {
                logger.info(METHODNAME, "saving item=", item);
                try {
                    BasePredicatePartDTO savedItem = getGeneralMGR().save(item, getSessionDTO(), new PropertyBagDTO());
                    displayMessage(savedItem, dtoState, SourceMethod.saveMain);
                } catch (Exception e) {
                    onExceptionMain(SourceMethod.saveMain, e);
                }
            }
        }
    }

    public List<T> getFullPredicateList() {
        final String METHODNAME = "getFullPredicateList ";
        if (fullPredicateList == null || predicatesDirty) {
            if (predicatesDirty) {
                logger.debug(METHODNAME, "reloading full DTOs");
                setPredicatesDirty(false);
            }
            fullPredicateList = new ArrayList<T>();
        }
        return fullPredicateList;
    }

}
