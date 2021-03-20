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
package org.cdsframework.menu;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.listeners.StartupListener;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.enumeration.PermissionType;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.lookup.UserSecuritySchemePermissionMapList;
import org.cdsframework.section.userpreference.UserPreferenceMGR;
import org.cdsframework.security.UserSecuritySchemePermissionMap;
import org.cdsframework.security.UserSession;
import org.cdsframework.util.LogUtils;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@SessionScoped
public class CatMenu implements Serializable {
    private static final long serialVersionUID = 5755107331280894644L;

    private final static LogUtils logger = LogUtils.getLogger(CatMenu.class);
    @Inject
    protected UserSession userSession;
    @Inject
    protected UserPreferenceMGR userPreferenceMGR;
    @Inject
    private UserSecuritySchemePermissionMapList userSecuritySchemePermissionMapList;
    private TreeNode selectedNode;
    private final static String ACCESS_DENIED_URI = "accessDenied.jsf";
    private Map<String, List<PermissionType>> permissionDenyMap = new HashMap<String, List<PermissionType>>();
    private Map<String, List<PermissionType>> permissionAllowMap = new HashMap<String, List<PermissionType>>();
    private final List<String> menuStyles = Arrays.asList(new String[]{"sidebar", "top"});

    private boolean highlightExpand(TreeNode treeNode, String requestServletPath) {
        TreeNodeLink treeNodeLink = (TreeNodeLink) treeNode.getData();
        if (treeNodeLink.getOutcome().equals(requestServletPath) && !treeNodeLink.isSeparator()) {
            treeNode.setSelected(true);
            treeNode.setExpanded(true);
            treeNode.getParent().setExpanded(true);
            return true;
        }
        return false;
    }

    public List<String> getMenuStyles() {
        return menuStyles;
    }

    public boolean isUserPrefSidebar() {
        // default is top...
        boolean result = false;
        if (userPreferenceMGR != null && userPreferenceMGR.getUserDTO() != null && userPreferenceMGR.getUserDTO().getUserPreference("catMenuPosition") != null) {
            if (!"sidebar".equalsIgnoreCase(userPreferenceMGR.getUserDTO().getUserPreference("catMenuPosition"))) {
                result = false;
            }
        }
        return result;
    }

    public MenuModel getMenuModel() {
        MenuModel menuModel = new DefaultMenuModel();
        if (!FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            menuModel = StartupListener.getMenuModel();
        }
        return menuModel;
    }

    public TreeNode getRootMenu() {
        final String METHODNAME = "getRootMenu ";
        long start = System.nanoTime();
        boolean ajaxRequest = FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest();
        TreeNode rootMenu = new DefaultTreeNode("root", null);
        if (!ajaxRequest) {
            SessionDTO sessionDTO = userSession.getSessionDTO();
            try {
                if (sessionDTO != null) {
                    UserDTO userDTO = sessionDTO.getUserDTO();
                    if (userDTO != null) {
                        if (userDTO.getUserId() != null) {
                            rootMenu = StartupListener.getRootMenu();
                            String requestServletPath = getRequestServletPath();
                            UserSecuritySchemePermissionMap userSecuritySchemePermissionMap = userSecuritySchemePermissionMapList.get(userDTO.getUserId());
                            if (userSecuritySchemePermissionMap != null) {
                                permissionDenyMap = userSecuritySchemePermissionMap.getPermissionDenyMap();
                                permissionAllowMap = userSecuritySchemePermissionMap.getPermissionAllowMap();
                                for (TreeNode treeNode : rootMenu.getChildren()) {
                                    treeNode.setExpanded(false);
                                    treeNode.setSelected(false);
                                    for (TreeNode menuItem : treeNode.getChildren()) {
                                        menuItem.setSelected(false);
                                        menuItem.setExpanded(false);
                                        try {
                                            if (menuItem.getData() instanceof TreeNodeLink) {
                                                TreeNodeLink treeNodeLink = (TreeNodeLink) menuItem.getData();
                                                if (treeNodeLink.getBasePermissionClass() != null) {
                                                    List<PermissionType> denyPermission = permissionDenyMap.get(treeNodeLink.getBasePermissionClass().getCanonicalName());
                                                    List<PermissionType> allowPermission = permissionAllowMap.get(treeNodeLink.getBasePermissionClass().getCanonicalName());
                                                    // this crap isn't working very well... :(
//                                        if (denyPermission != null) {
//                                            if (denyPermission.contains(treeNodeLink.getBasePermissionType())) {
//                                                treeNodeLink.setOutcome(ACCESS_DENIED_URI);
//                                            }
//                                        } else if (allowPermission == null) {
//                                            treeNodeLink.setOutcome(ACCESS_DENIED_URI);
//                                        } else if (allowPermission != null) {
//                                            if (treeNodeLink.getBasePermissionType() != PermissionType.FULL
//                                                    && !allowPermission.contains(treeNodeLink.getBasePermissionType())) {
//                                                treeNodeLink.setOutcome(ACCESS_DENIED_URI);
//                                            }
//                                        }
                                                }
                                            }
                                        } catch (Exception e) {
                                            DefaultExceptionHandler.handleException(e, getClass());
                                        }
                                    }
                                }
                                if (!ACCESS_DENIED_URI.equals(requestServletPath)) {
                                    boolean highlightState = false;
                                    for (TreeNode treeNode : rootMenu.getChildren()) {
                                        highlightState = highlightExpand(treeNode, requestServletPath);
                                        if (highlightState) {
                                            break;
                                        }
                                        for (TreeNode menuItem : treeNode.getChildren()) {
                                            highlightState = highlightExpand(menuItem, requestServletPath);
                                            if (highlightState) {
                                                break;
                                            }
                                        }
                                        if (highlightState) {
                                            break;
                                        }
                                    }
                                }
                            } else {
                                logger.error(METHODNAME + "userSecuritySchemePermissionMapDTO is null! ", userDTO.getUsername());
                            }
                        } else {
                            logger.error(METHODNAME + "userDTO.getUserId() is null!");
                        }
                    } else {
                        logger.error(METHODNAME + "userDTO is null!");
                    }
                } else {
                    logger.error(METHODNAME + "sessionDTO is null!");

                }
            } finally {
                logger.logDuration(LogLevel.DEBUG, METHODNAME, start);                                                                            
            }
        }
        return rootMenu;
    }

    public void setRootMenu(TreeNode rootMenu) {
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public static String getRequestServletPath() {
        String requestServletPath = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();
        //requestServletPath = requestServletPath.substring(requestServletPath.lastIndexOf("/") + 1);
        return requestServletPath;
    }
}
