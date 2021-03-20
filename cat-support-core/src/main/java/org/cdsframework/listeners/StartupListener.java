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
package org.cdsframework.listeners;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.PermissionType;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.menu.CatMenus;
import org.cdsframework.menu.Menu;
import org.cdsframework.menu.MenuItem;
import org.cdsframework.menu.TreeNodeLink;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.comparator.SubmenuComparator;
import org.cdsframework.util.comparator.TreeNodeLinkComparator;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSeparator;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;

public class StartupListener implements ServletContextListener {

    private final static LogUtils logger = LogUtils.getLogger(StartupListener.class);
    private static Properties instanceProperties;
    private static Properties applicationProperties;
    private static Properties buildProperties;
    private static Properties pluginProperties;
    public final static String MESSAGE_DISPLAY_ID = "growl";
    public final static String MESSAGE_DISPLAY_WIDGET_VAR = "growlWidgetVar";
    public static String CAT_VERSION;
    public static String CAT_BUILD_CHANGESET;
    public static String CAT_BUILD_TIMESTAMP;
    public static String CAT_PLUGIN_LIST;
    public static String CAT_SRC_SYSTEM_ID;
    private static Map<TreeNodeLink, List<TreeNodeLink>> menuMap = new HashMap<TreeNodeLink, List<TreeNodeLink>>();
    private static TreeNode rootMenu;
    private static List<DefaultSubMenu> submenus = new LinkedList<DefaultSubMenu>();
    private static MenuModel menuModel = new DefaultMenuModel();
    private static List<String> pluginList = new ArrayList<String>();

    static {
        try {
            loadProperties();
            initializeProperties();
            initializeMenus();
        } catch (Exception e) {
            instanceProperties = new Properties();
            applicationProperties = new Properties();
            pluginProperties = new Properties();
            logger.error(e);
        }
    }

    private static void addMenuItemsToMenu(String pluginName, DefaultSubMenu submenu, List<MenuItem> menuItems) {
        final String METHODNAME = "addMenuItemsToMenu ";
        for (MenuItem menuItem : menuItems) {
            if ("EXTLINK".equalsIgnoreCase(menuItem.getMenuItemType())) {
                registerExtLinkMenuItem(pluginName, submenu, menuItem);
            } else if ("LINK".equalsIgnoreCase(menuItem.getMenuItemType())) {
                PermissionType permissionType;
                try {
                    permissionType = PermissionType.valueOf(menuItem.getPermissionType().toUpperCase());
                } catch (IllegalArgumentException e) {
                    logger.error(
                            METHODNAME,
                            "PermissionType not found for entry ", pluginName,
                            " - ", submenu.getLabel(),
                            " - ", menuItem.getMenuItemName(),
                            " - ", menuItem.getViewName(),
                            " - ", menuItem.getPermissionClass(),
                            " - skipping");
                    continue;
                }
                Class<? extends BaseDTO> permissionClass;
                try {
                    permissionClass = (Class<? extends BaseDTO>) Class.forName(menuItem.getPermissionClass());
                } catch (ClassNotFoundException e) {
                    logger.error(
                            METHODNAME,
                            "class not found for entry ", pluginName,
                            " - ", submenu.getLabel(),
                            " - ", menuItem.getMenuItemName(),
                            " - ", menuItem.getViewName(),
                            " - ", menuItem.getPermissionClass(),
                            " - skipping");
                    continue;
                }
                registerPluginMenuItem(pluginName, submenu, menuItem, permissionType, permissionClass);
            } else if ("SEPARATOR".equalsIgnoreCase(menuItem.getMenuItemType())) {
                registerPluginMenuSeparator(pluginName, submenu, menuItem);
            } else if ("SUBMENU".equalsIgnoreCase(menuItem.getMenuItemType())) {
                registerPluginSubMenu(pluginName, submenu, menuItem);
            } else {
                logger.error(METHODNAME, "found unsupported menuItemType: ", menuItem.getMenuItemType());
            }
        }
    }

    private static void initializeMenus() {
        final String METHODNAME = "initializeMenus ";
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("org.cdsframework.menu");
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Enumeration<URL> resources = StartupListener.class.getClassLoader().getResources("menu.xml");

            while (resources.hasMoreElements()) {
                URL nextElement = resources.nextElement();
                logger.info(METHODNAME, "FOUND: ", nextElement.getPath());
                CatMenus catMenus = (CatMenus) unmarshaller.unmarshal(nextElement.openStream());
                registerPlugin(catMenus.getPluginName());
                for (Menu menu : catMenus.getMenus()) {
                    DefaultSubMenu submenu = new DefaultSubMenu();
                    submenu.setLabel(menu.getMenuName());
                    submenus.add(submenu);
                    Collections.sort(submenus, new SubmenuComparator());
                    addMenuItemsToMenu(catMenus.getPluginName(), submenu, menu.getMenuItems());
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }

    }
//
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        logger.info("CAT Initial Servlet startup complete...");
//    }
//
//    @Override
//    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
//        logger.debug("service");
//    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("CAT startup listener initialization complete...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Destroyed CAT startup listener...");
    }

    public static Properties getInstanceProperties() {
        return instanceProperties;
    }

    public static Properties getApplicationProperties() {
        return applicationProperties;
    }

    public static Properties getPluginProperties() {
        return pluginProperties;
    }

    /**
     * Initialize application and instance specific properties
     */
    private static void initializeProperties() {
        CAT_VERSION = buildProperties.getProperty("BUILD_VERSION");
        CAT_BUILD_CHANGESET = buildProperties.getProperty("BUILD_NUMBER");
        CAT_BUILD_TIMESTAMP = buildProperties.getProperty("BUILD_TIMESTAMP");
        CAT_PLUGIN_LIST = pluginProperties.getProperty("PLUGIN_LIST").toUpperCase();
        CAT_SRC_SYSTEM_ID = instanceProperties.getProperty("SRC_SYSTEM_ID").toUpperCase();

        logger.info("CAT Version: ", CAT_VERSION);
        logger.info("CAT Build Changeset: ", CAT_BUILD_CHANGESET);
        logger.info("CAT Build Timestamp: ", CAT_BUILD_TIMESTAMP);
        logger.info("CAT Plugin List: ", CAT_PLUGIN_LIST);
        logger.info("CAT Source System ID: ", CAT_SRC_SYSTEM_ID);
    }

    /**
     * Returns the full path to a file under the application root.
     * 
     * @param fileName
     * @return
     * @throws CatException 
     */
    public static String getAppRootDirectoryFilePath(String fileName) throws CatException {
        InputStream tmpInstancePropertiesStream = null;
        String result = null;
        try {
            // load the instance.properties file
            logger.info("Loading properties from: instance.properties");
            tmpInstancePropertiesStream = StartupListener.class.getClassLoader().getResourceAsStream("instance.properties");
            if (tmpInstancePropertiesStream == null) {
                throw new CatException("tmpInstancePropertiesStream was null!");
            }
            Properties tmpInstanceProperties = new Properties();
            tmpInstanceProperties.load(tmpInstancePropertiesStream);

            // this is the application instance root
            // retrieve the system property name that contains the instance root path for this application server
            String instanceRootSystemPropertyName = tmpInstanceProperties.getProperty("instanceDirSystemPropertyName");
            // get the system property
            String instanceRoot = System.getProperty(instanceRootSystemPropertyName);
            // append the relative path of the property file to the instance root location
            result = instanceRoot + "/" + fileName;
            logger.info("result: ", result);
        } catch (IOException e) {
            logger.error(e);
        } finally {
            if (tmpInstancePropertiesStream != null) {
                try {
                    tmpInstancePropertiesStream.close();
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }
        return result;
    }

    /**
     * Load application server application and instance specific properties from
     * file
     *
     * @throws CatException
     * @throws IOException
     */
    private static void loadProperties() throws CatException, IOException {
        InputStream tmpInstancePropertiesStream = null;
        InputStream applicationPropertiesStream = null;
        InputStream pluginPropertiesStream = null;
        InputStream buildPropertiesStream = null;

        try {
            // load the application.properties file
            logger.info("Loading properties from: application.properties");
            applicationPropertiesStream = StartupListener.class.getClassLoader().getResourceAsStream("application.properties");
            if (applicationPropertiesStream == null) {
                throw new CatException("applicationPropertiesStream was null!");
            }
            applicationProperties = new Properties();
            applicationProperties.load(applicationPropertiesStream);

            // load the plugin.properties file
            logger.info("Loading properties from: application.properties");
            pluginPropertiesStream = StartupListener.class.getClassLoader().getResourceAsStream("plugin.properties");
            if (applicationPropertiesStream == null) {
                throw new CatException("applicatiopluginPropertiesStreamnPropertiesStream was null!");
            }
            pluginProperties = new Properties();
            pluginProperties.load(pluginPropertiesStream);

            // load the instance.properties file
            logger.info("Loading properties from: instance.properties");
            tmpInstancePropertiesStream = StartupListener.class.getClassLoader().getResourceAsStream("instance.properties");
            if (tmpInstancePropertiesStream == null) {
                throw new CatException("tmpInstancePropertiesStream was null!");
            }
            Properties tmpInstanceProperties = new Properties();
            tmpInstanceProperties.load(tmpInstancePropertiesStream);

            // this is the application instance root
            // retrieve the system property name that contains the instance root path for this application server
            String instanceRootSystemPropertyName = tmpInstanceProperties.getProperty("instanceDirSystemPropertyName");
            // get the system property
            String instanceRoot = System.getProperty(instanceRootSystemPropertyName);
            // append the relative path of the property file to the instance root location
            String instancePropertyFileLocation = instanceRoot + "/" + tmpInstanceProperties.getProperty("mtsPropertiesUri");
            logger.info("Reading properties from: ", instancePropertyFileLocation);
            // load the cat properties from the property file location
            instanceProperties = new Properties();
            instanceProperties.load(new FileInputStream(instancePropertyFileLocation));

            // load the build.properties file
            logger.info("Loading properties from: build.properties");
            buildPropertiesStream = StartupListener.class.getClassLoader().getResourceAsStream("cat-build.properties");
            if (buildPropertiesStream == null) {
                throw new CatException("buildPropertiesStream was null!");
            }
            buildProperties = new Properties();
            buildProperties.load(buildPropertiesStream);
        } finally {
            if (tmpInstancePropertiesStream != null) {
                try {
                    tmpInstancePropertiesStream.close();
                } catch (Exception e) {
                    logger.error(e);
                }
            }
            if (applicationPropertiesStream != null) {
                try {
                    applicationPropertiesStream.close();
                } catch (Exception e) {
                    logger.error(e);
                }
            }
            if (pluginPropertiesStream != null) {
                try {
                    pluginPropertiesStream.close();
                } catch (Exception e) {
                    logger.error(e);
                }
            }
            if (buildPropertiesStream != null) {
                try {
                    buildPropertiesStream.close();
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }

    public static MenuModel getMenuModel() {
        if (menuModel.getElements() == null || menuModel.getElements().isEmpty()) {
            for (Submenu subMenu : submenus) {
                menuModel.addElement(subMenu);
            }
        }
        return menuModel;
    }

    /**
     * Gets the child link list for the requested sidebar menu. Create it if it
     * doesn't exist.
     *
     * @param moduleName used in the construction of the outcome link
     * @param menuName the label of the top level menu
     * @return the child link list for the requested sidebar menu
     */
    private static List<TreeNodeLink> getMenuChildLinks(String moduleName, String menuName) {
        List<TreeNodeLink> childLinks = new ArrayList<TreeNodeLink>();
        try {
            boolean exists = false;
            String outcome = "/module/" + moduleName + "/views/" + menuName.replace(" ", "").replace("/", "").replace("-", "").toLowerCase() + ".jsf";
            for (TreeNodeLink item : menuMap.keySet()) {
                if (outcome.equals(item.getOutcome())
                        && item.getLabel().equals(menuName)) {
                    exists = true;
                    childLinks = menuMap.get(item);
                    break;
                }
            }
            if (!exists) {
                TreeNodeLink parentLink = new TreeNodeLink(outcome, menuName, null, null);
                menuMap.put(parentLink, childLinks);
                logger.debug(moduleName, " module ", menuName, " menu created.");
            } else {
                logger.debug(moduleName, " module ", menuName, " menu exists.");
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Menu initialization failed for module: " + moduleName + " - " + menuName);
        }
        return childLinks;
    }

    /**
     * Registers a new menuItem for a plugin menu
     *
     * @param <S> constrain the S class parameter to a BaseDTO child class
     * @param pluginName used in the construction of the outcome link
     * @param submenu the menu
     * @param menuItem the menu item
     * @param permissionType the permission level necessary for accessing the
     * menu
     * @param permissionClass the permission necessary for accessing the menu
     */
    public static <S extends BaseDTO> void registerPluginMenuItem(String pluginName, DefaultSubMenu submenu, MenuItem menuItem, PermissionType permissionType, Class<S> permissionClass) {
        final String METHODNAME = "registerPluginMenuItem ";
        logger.debug(METHODNAME, pluginName, " - ", submenu.getLabel(), " - ", menuItem.getViewName(), " - ", menuItem.getMenuItemName(), " - ", permissionType, " - ", permissionClass);
        String outcome = "/module/" + pluginName + "/views/" + menuItem.getViewName();

        // for top menu
        logger.debug(METHODNAME, submenu.getLabel());
        DefaultMenuItem newMenuItem = new DefaultMenuItem();
        newMenuItem.setValue(menuItem.getMenuItemName());
        newMenuItem.setUrl(outcome);
        submenu.addElement(newMenuItem);

        // for sidebar menu
        // generate the sidebar object
        TreeNodeLink childMenuNode = new TreeNodeLink(outcome, menuItem.getMenuItemName(), permissionClass, permissionType);
        List<TreeNodeLink> menuChildLinks = getMenuChildLinks(pluginName, getSideBarLabel(submenu));
        menuChildLinks.add(childMenuNode);
    }

    /**
     * Registers a new external link menuItem
     *
     * @param pluginName used in the construction of the outcome link
     * @param submenu the menu
     * @param menuItem the menu item
     */
    public static void registerExtLinkMenuItem(String pluginName, DefaultSubMenu submenu, MenuItem menuItem) {
        final String METHODNAME = "registerExtLinkMenuItem ";
        logger.debug(METHODNAME, submenu.getLabel(), " - ", menuItem.getViewName(), " - ", menuItem.getMenuItemName());
        String outcome = menuItem.getViewName();

        // for top menu
        logger.debug(METHODNAME, submenu.getLabel());
        DefaultMenuItem newMenuItem = new DefaultMenuItem();
        newMenuItem.setValue(menuItem.getMenuItemName());
        newMenuItem.setUrl(outcome);
        submenu.addElement(newMenuItem);

        // for sidebar menu
        // generate the sidebar object
        TreeNodeLink childMenuNode = new TreeNodeLink(outcome, menuItem.getMenuItemName(), null, null);
        List<TreeNodeLink> menuChildLinks = getMenuChildLinks(pluginName, getSideBarLabel(submenu));
        menuChildLinks.add(childMenuNode);
    }

    private static String getSideBarLabel(DefaultSubMenu submenu) {
        String result;

        // determine the side bar label
        DefaultSubMenu parentMenu = null;
        for (DefaultSubMenu item : submenus) {
            if (item.getElements().contains(submenu)) {
                parentMenu = item;
                break;
            }
        }
        result = parentMenu != null ? parentMenu.getLabel() + " " + submenu.getLabel() : submenu.getLabel();
        return result;
    }

    private static void registerPluginSubMenu(String pluginName, DefaultSubMenu submenu, MenuItem menuItem) {

        // for top menu
        DefaultSubMenu subMenuItem = new DefaultSubMenu();
        subMenuItem.setLabel(menuItem.getMenuItemName());
        submenu.addElement(subMenuItem);
        addMenuItemsToMenu(pluginName, subMenuItem, menuItem.getMenuItems());

    }

    /**
     * Registers a menu separator
     *
     * @param pluginName
     * @param submenu
     * @param menuItem optional
     */
    public static void registerPluginMenuSeparator(String pluginName, DefaultSubMenu submenu, MenuItem menuItem) {
        final String METHODNAME = "registerPluginMenuSeparator ";
        logger.debug(METHODNAME, pluginName, " - ", submenu.getLabel(), " - ", menuItem.getMenuItemName());
        // for top menu
        DefaultSeparator separator = new DefaultSeparator();
        separator.setTitle(menuItem.getMenuItemName());
        submenu.addElement(separator);

        // for side menu
        List<TreeNodeLink> menuChildLinks = getMenuChildLinks(pluginName, getSideBarLabel(submenu));
        TreeNodeLink childMenuNode = new TreeNodeLink("", menuItem.getMenuItemName(), BaseDTO.class, PermissionType.SELECT);
        childMenuNode.setSeparator(true);
        menuChildLinks.add(childMenuNode);
    }

    /**
     * Gets the root menu object for the sidebar menu
     *
     * @return
     */
    public static TreeNode getRootMenu() {
        if (rootMenu == null) {
            rootMenu = new DefaultTreeNode("root", null);
            List<TreeNodeLink> keySet = new ArrayList<TreeNodeLink>(menuMap.keySet());
            Collections.sort(keySet, new TreeNodeLinkComparator());
            for (TreeNodeLink key : keySet) {
                if (logger.isDebugEnabled()) {
                    logger.info("Processing key: ", key);
                    logger.info("Processing outcome: ", key.getOutcome());
                }
                TreeNode topMenu = new DefaultTreeNode("parentLink", key, rootMenu);
                for (TreeNodeLink treeNodeLink : menuMap.get(key)) {
                    DefaultTreeNode defaultTreeNode = new DefaultTreeNode("childLink", treeNodeLink, topMenu);
                }
            }
        }
        return rootMenu;
    }

    public static List<String> getPluginList() {
        return pluginList;
    }

    public static void registerPlugin(String pluginName) {
        if (!pluginList.contains(pluginName)) {
            pluginList.add(pluginName);
        }
    }
}
