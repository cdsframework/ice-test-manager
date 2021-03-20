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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.enumeration.LoadStatus;
import org.cdsframework.util.enumeration.PrePost;
import org.cdsframework.util.enumeration.SourceOperation;
import org.cdsframework.util.enumeration.UIDataType;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
public class TabService {

    protected final static LogUtils logger = LogUtils.getLogger(TabService.class);
    private final HashMap<String, List<BaseModule>> registeredUIComponent = new HashMap<String, List<BaseModule>>();
    private final BaseModule module;
    private TabChangeEvent previousTabChangeEvent;
    
    public TabService(BaseModule module) {
        this.module = module;
    }

    public void registerTabComponentsMain() {
        final String METHODNAME = "registerTabComponentsMain ";

        // Call module back to register components
        module.registerTabComponents();

        // Only applies to Save Immediate mode
        if (module.isSaveImmediately()) {
            //
            // Auto Register root module, added for convenience in case a developer forget to register the root module 
            //
            // Check if the module associated with the service is registered
            if (registeredUIComponent != null) {
                boolean moduleRegistered = false;
                boolean modulesExist = false;
                Collection<List<BaseModule>> baseModuleList = registeredUIComponent.values();
                for (List<BaseModule> baseModules : baseModuleList) {
                    modulesExist = true;
                    if (baseModules.contains(module)) {
                        moduleRegistered = true;
                        break;
                    }
                }

                if (modulesExist && !moduleRegistered) {
                    // At a minimum auto register the first tab
                    registeredUIComponent(0, module);
                    logger.error(METHODNAME, "The root module ", module, " is not registered with any component", 
                            " did you forget to register it? Don't worry I will registered it for you.");
                }
            }
        }
    }

    public void registeredUIComponent(int tabIndex, Class queryClass) {
        final String METHODNAME = "registeredUIComponent ";
        logger.debug(METHODNAME, "called tabIndex=", tabIndex, " queryClass=", queryClass);
        String baseId = UtilityMGR.getTabIdForIndex(module, tabIndex);
        UIComponent uiComponent = UtilityMGR.getUIComponentFromBaseId(baseId);
        registeredUIComponent(uiComponent, queryClass);
    }

    public void registeredUIComponent(int tabIndex, BaseModule baseModule) {
        final String METHODNAME = "registeredUIComponent ";
        logger.debug(METHODNAME, "called tabIndex=", tabIndex, " baseModule=", baseModule);
        String baseId = UtilityMGR.getTabIdForIndex(module, tabIndex);
        UIComponent uiComponent = UtilityMGR.getUIComponentFromBaseId(baseId);
        registeredUIComponent(uiComponent, baseModule);
    }

    private void registeredUIComponent(UIComponent uiComponent, Class queryClass) {
        final String METHODNAME = "registeredUIComponent ";
        registeredUIComponent(uiComponent, (BaseModule) module.getRegisteredChildMap().get(queryClass));
    }

    private void registeredUIComponent(UIComponent uiComponent, BaseModule baseModule) {
        final String METHODNAME = "registeredUIComponent ";
        if (uiComponent != null) {
            logger.debug(METHODNAME, "called uiComponent=", uiComponent.getClientId(), " baseModule= ", baseModule);
            if (baseModule != null) {
                logger.debug(METHODNAME, "!registeredUIComponent.containsKey(uiComponent)=", !registeredUIComponent.containsKey(uiComponent.getClientId()));
                if (!registeredUIComponent.containsKey(uiComponent.getClientId())) {
                    List<BaseModule> baseModules = new ArrayList<BaseModule>();
                    baseModules.add(baseModule);
                    registeredUIComponent.put(uiComponent.getClientId(), baseModules);
                } else {
                    List<BaseModule> baseModules = registeredUIComponent.get(uiComponent.getClientId());
                    if (!baseModules.contains(baseModule)) {
                        baseModules.add(baseModule);
                    }
                }
            } else {
                logger.error(METHODNAME, "baseModule is null!");
                module.onExceptionMain(METHODNAME, new NotFoundException(METHODNAME + "baseModule is null!"));
            }
        } else {
            logger.error(METHODNAME, "uiComponent is null - did you forget to add an id attribute: id=\"#{baseModule.getTabIdByIndex(n)}\"!");
            module.onExceptionMain(METHODNAME, new NotFoundException(METHODNAME + "uiComponent is null!"));
        }
    }

    public void onClose(CloseEvent event) {
        final String METHODNAME = "onClose ";
        logger.debug(METHODNAME);

    }

    public void onTabChange(TabChangeEvent tabChangeEvent) throws Exception {
        final String METHODNAME = "onTabChange ";
        logger.debug(METHODNAME);

        boolean loaded = false;
        boolean registered = false;
        Tab changedTab = null;

        if (module.getTabView() != null) {
            setPreviousTabChangeEvent(tabChangeEvent);

            changedTab = tabChangeEvent.getTab();

            // Was it loaded?
            if (changedTab == null) {
                String errorMessage = "changedTab is null, Did you forget to remove the p:tabView from your editForm?";
                module.getMessageMGR().displayErrorMessage(errorMessage);
                throw new Exception(errorMessage);
            }

            // Was it loaded?
            loaded = changedTab.isLoaded();
            if (!loaded) {
                registered = retrieveUIComponent(changedTab, changedTab.isLoaded());
                PostRenderService.postRenderUIComponent(module, changedTab);
            }

            // Fire descendant level event
            module.onTabChange(tabChangeEvent);

            if (changedTab != null) {
                // Set the load state
                if (registered && !loaded) {
                    changedTab.setLoaded(true);
                }
            }
        }
    }

    public void onRowSelect(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onRowSelect ";

        // Call module to register components
        if (module.getTabView() != null) {
            module.getTabService().registerTabComponentsMain();

            //        if (!registeredUIComponent.isEmpty()) {
            Tab activeTab = getActiveTab();

            boolean activeTabLoaded = false;
            if (activeTab != null) {
                activeTabLoaded = activeTab.isLoaded();
            }
            unloadTabs();

            // Force the Active Tab to get loaded
            if (activeTabLoaded || module.getTabView().getActiveIndex() == 0) {
                module.changeTab(activeTab);
            }
            //        }
        }
    }

    public void addMain(ActionEvent actionEvent) throws Exception {
        final String METHODNAME = "addMain ";
        logger.debug(METHODNAME);

        // Call module to register components
        module.getTabService().registerTabComponentsMain();
        unloadTabs();
    }


    private Tab getActiveTab() {
        final String METHODNAME = "getActiveTab ";
        Tab activeTab = null;
        if (module.getTabView() != null) {
            List<UIComponent> children = module.getTabView().getChildren();
            //        logger.info(METHODNAME, "children=", children);
            int activeIndex = module.getTabView().getActiveIndex();
            logger.debug(METHODNAME, "activeIndex=", activeIndex);
            int count = 0;
            for (UIComponent uiComponent : children) {
                if (uiComponent instanceof Tab) {
                    Tab tab = (Tab) uiComponent;
//                    logger.info(METHODNAME, "tab.isRendered()=", tab.isRendered());
                    if (tab.isRendered()) {
                        if (count == activeIndex) {
                            activeTab = (Tab) uiComponent;
                            break;
                        }
                        count++;
                    }
                }
            }
        }

        return activeTab;
    }

    public boolean save(ActionEvent actionEvent) throws Exception {
        final String METHODNAME = "save ";
        boolean success = false;
        Tab activeTab = getActiveTab();
        int savedCount = 0;

        if (activeTab != null && registeredUIComponent.containsKey(activeTab.getClientId())) {
            module.prePostOperation(null, null, PrePost.PreTabSave, false);
            // Get the modules that are registered with the active tab
            List<BaseModule> registeredModules = registeredUIComponent.get(activeTab.getClientId());
            logger.debug(METHODNAME, "module=", module, 
                    " activeTab.getTitle()=", activeTab.getTitle(),
                    " registeredModules=", registeredModules);

            boolean savedFailed = false;
            for (BaseModule registeredModule : registeredModules) {
                logger.info(METHODNAME, "registeredModule=", registeredModule);

                // Parent or parentless module OR
                // is a child and the calling module is the registered module (Tier 2 or greater) OR
                // is a child (rare, parent sibling case) and does not use the datatable OR
                if (!registeredModule.isChild()
                        || (registeredModule.isChild() && module == registeredModule)
                        || (registeredModule.isChild() && registeredModule.getUIDataType() == UIDataType.Form)) {

                    if (registeredModule.getParentDTO() != null && registeredModule.getParentDTO().getDTOState() != DTOState.UNSET) {
                        boolean saved = registeredModule.saveMain(actionEvent, true);
                        if (saved) {
                            savedCount ++;
                        }
                        else {
                            // An error has occurred
                            savedFailed = true;
                            break;
                        }
                    }
                }

                //
                // Save a TreeTable or DataTable type when a DataTable does not exist
                // This essentially processes a list of DTO's
                //
                else if (registeredModule.isChild() && !registeredModule.isDataTableExists() &&
                        (registeredModule.getUIDataType() == UIDataType.TreeTable || 
                         registeredModule.getUIDataType() == UIDataType.DataTable )) {
                    savedCount += DataAccessService.save(registeredModule.getDataAccessInterface());
                }
                
                // Keep Lazy Load from loading
                else if (registeredModule.isChild() && 
                         registeredModule.isDataTableExists() && 
                         registeredModule.getUIDataType() == UIDataType.DataTable) {
                    logger.debug(METHODNAME, "setLoadStatus ", LoadStatus.StopClear);
                    registeredModule.setLoadStatus(LoadStatus.StopClear);
                }

                // Save the inline datatable. Note: only saves whats presented on the tab
                InlineDataTableService inlineDataTableService = registeredModule.getInlineDataTableService();
                if (inlineDataTableService != null) {
                    savedCount += inlineDataTableService.save(activeTab);
                }
            }
            
            // If no error exists set success flag, Note, saving a sole datatable in a tab should return a savedCount == 0
            if (!savedFailed) {
                success = true;
            }
            
            module.prePostOperation(null, null, PrePost.PostTabSave, savedCount > 0);
            
        } else {
            success = module.saveMain(actionEvent, true);
        }

//        PostRenderService.postRenderComponent(module);

        return success;
    }

    private void unloadTabs() throws Exception {
        final String METHODNAME = "unloadTabs ";
        logger.debug(METHODNAME, "module=", module);

        if (module.getTabView() != null) {
            List<UIComponent> children = module.getTabView().getChildren();
            for (UIComponent uiComponent : children) {
                logger.debug(METHODNAME, "uiComponent=", uiComponent.getClientId());
                if (uiComponent instanceof Tab) {
                    Tab tab = (Tab) uiComponent;
                    logger.debug(METHODNAME, "unloading tab.getTitle()=", tab.getTitle());

                    if (tab.isLoaded()) {
                        tab.setLoaded(false);
                    }
                    List<UIComponent> tabChildren = tab.getChildren();
                    for (UIComponent tabChild : tabChildren) {
                        logger.debug(METHODNAME, "tabChild=", tabChild.getClientId());
                    }
                }
            }
        }

        // Reset all the DTOs
        resetDTOs();
    }

    private void resetDTOs() throws Exception {
        final String METHODNAME = "resetDTOs ";
        Set<Entry<String, List<BaseModule>>> entrySet = registeredUIComponent.entrySet();
        for (Entry<String, List<BaseModule>> entry : entrySet) {
            List<BaseModule> baseModules = entry.getValue();
            String clientId = entry.getKey();
            UIComponent uiComponent = UtilityMGR.getUIComponentById(clientId);
            logger.debug(METHODNAME, "resetting tab getTitle=", ((Tab) uiComponent).getTitle());
            for (BaseModule baseModule : baseModules) {
                logger.debug(METHODNAME, "resetting baseModule=", baseModule);
                if (baseModule.isChild()) {
                    // Dont reset the DataTable because its the table display in this tiers parent
                    if (baseModule.getUIDataType() == UIDataType.DataTable) {
                        DataTable dataTable = baseModule.getDataTable();
                        // If the datatable exists in the uiComponent, reset it                
                        if (!baseModule.isDataTableExists() || UtilityMGR.uiComponentExists(uiComponent, dataTable)) {
                            logger.debug(METHODNAME, "dataTable clientId=", (dataTable != null ? dataTable.getClientId() : "null"));
                            DataAccessInterface dataAccessInterface = DataAccessService.getDataAccessInterface(baseModule);
                            DataAccessService.reset(dataAccessInterface, baseModule, baseModule.getChildQueryClass().getSimpleName());
                        }
                    } else if (baseModule.getUIDataType() == UIDataType.Form) {
                        // This is a rare case when a module is a child and does not have a datatable
                        logger.debug(METHODNAME, "for non datatable baseModule class=", baseModule.getDtoClassType());
                        baseModule.setParentDTO((BaseDTO) baseModule.getDtoClassType().newInstance());
                        baseModule.setUpdateIds(METHODNAME, uiComponent.getClientId());
                    }
                }

                // Reset all inlineDataTables
                baseModule.getInlineDataTableService().resetDTOs(module, uiComponent);
            }
        }

    }

    private boolean retrieveUIComponent(UIComponent uiComponent, boolean loaded) throws Exception {
        final String METHODNAME = "retrieveUIComponent ";
        logger.debug(METHODNAME);
        logger.debug(METHODNAME, "uiComponent.getClientId()=", uiComponent.getClientId(), " uiComponent=", uiComponent);
        boolean registered = registeredUIComponent.containsKey(uiComponent.getClientId());
        logger.debug(METHODNAME, "registered=", registered);
        if (registered) {
            if (!loaded) {
                List<BaseModule> baseModules = registeredUIComponent.get(uiComponent.getClientId());
                for (BaseModule baseModule : baseModules) {
                    retrieveDTOs(baseModule, uiComponent);
                }
            }
        }
        return registered;
    }

    private void retrieveDTOs(final BaseModule baseModule, UIComponent uiComponent) throws Exception {
        final String METHODNAME = "retrieveDTOs ";
        logger.debug(METHODNAME, "module=", module,
                " baseModule=", baseModule,
                " isChild=", baseModule.isChild(),
                " uiDataType=", baseModule.getUIDataType(),
                " dataTable=", baseModule.getDataTable(),
                " treeTable=", baseModule.getTreeTable(),
                " parentModule=", baseModule.getParentMGR() != null ? baseModule.getParentMGR() : "null");

        logger.debug(METHODNAME, (module == baseModule ? "modules/baseModule MATCH, will not retrieve" : ""));

        if (module != baseModule && baseModule.isChild()) {

            UIDataType uiDataType = baseModule.getUIDataType();
            switch (uiDataType) {
                case TreeTable:
                case DataTable:
                    UIComponent table = baseModule.getDataTable();
                    if (uiDataType == UIDataType.TreeTable) {
                        table = baseModule.getTreeTable();
                    }
                    if (!baseModule.isDataTableExists() || UtilityMGR.uiComponentExists(uiComponent, table)) {
                        DataAccessInterface dataAccessInterface = DataAccessService.getDataAccessInterface(baseModule);
                        DataAccessService.retrieve(dataAccessInterface, baseModule, baseModule.getChildQueryClass().getSimpleName(), SourceOperation.FindBy, null);
                    } else {
                        logger.debug(METHODNAME, "dataTable clientId ", (baseModule.getDataTable() != null ? baseModule.getDataTable().getClientId() : "null"),
                                " for baseModule=", baseModule, " does not exist in uiComponent=", uiComponent.getClientId());
                    }
                    break;
                case Form:
                    logger.debug(METHODNAME, "retrieving non datatable baseModule class=", baseModule.getDtoClassType());
                    // This is a rare case when a module is a child and does not have a datatable
                    BaseDTO childDTO = null;
                    try {
                        BaseModule parentModule = baseModule.getParentMGR();
                        BaseDTO searchCriteriaDTO = baseModule.getSearchCriteriaDTO(parentModule);

                        PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                        String queryClass = baseModule.getChildQueryClass().getSimpleName();
                        propertyBagDTO.setQueryClass(queryClass);
                        propertyBagDTO.setChildClassDTOs(baseModule.getChildClassDTOs(queryClass, SourceOperation.FindBy));
                        
                        childDTO = baseModule.getMts().getGeneralMGR().findByQuery(searchCriteriaDTO, baseModule.getSessionDTO(), propertyBagDTO);
                    } catch (NotFoundException e) {
                    }

                    // If its null create it so that the form is viewable
                    if (childDTO == null) {
                        Class dtoClassType = baseModule.getDtoClassType();
                        childDTO = (BaseDTO) dtoClassType.newInstance();
                    }
                    // Set the parent
                    baseModule.setParentDTO(childDTO);
                    logger.debug(METHODNAME, "assigning childDTO=", childDTO);

                    // Update the uiComponent
                    module.setUpdateIds(METHODNAME, uiComponent.getClientId());
            }
        }

        // Retrieve the Inline DataTables
        baseModule.getInlineDataTableService().retrieveDTOs(module, uiComponent);

    }

    public TabChangeEvent getPreviousTabChangeEvent() {
        return this.previousTabChangeEvent;
    }

    public void setPreviousTabChangeEvent(TabChangeEvent previousTabChangeEvent) {
        this.previousTabChangeEvent = previousTabChangeEvent;
    }

}
