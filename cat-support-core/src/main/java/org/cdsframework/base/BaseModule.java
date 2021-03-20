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

import org.cdsframework.ui.services.TabService;
import org.cdsframework.datatable.DataTableInterface;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.naming.NamingException;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.application.CatApplication;
import org.cdsframework.application.Mts;
import org.cdsframework.callback.OnSavedCallback;
import org.cdsframework.callback.OtherUpdateIdCallback;
import org.cdsframework.client.support.GeneralMGRClient;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.enumeration.PermissionType;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.lookup.UserSecuritySchemePermissionMapList;
import org.cdsframework.message.Message;
import org.cdsframework.message.MessageMGR;
import org.cdsframework.security.UserSecuritySchemePermissionMap;
import org.cdsframework.security.UserSession;
import org.cdsframework.security.ui.ButtonType;
import org.cdsframework.security.ui.SecureUI;
import org.cdsframework.ui.services.DataAccessInterface;
import org.cdsframework.ui.services.DataAccessService;
import org.cdsframework.ui.services.GotoRecordService;
import org.cdsframework.ui.services.PostRenderService;
import org.cdsframework.ui.services.InlineDataTableService;
import org.cdsframework.ui.services.UIConstants;
import org.cdsframework.util.BeanUtils;
import org.cdsframework.util.BrokenRule;
import org.cdsframework.util.ClassUtils;
import org.cdsframework.util.DTOUtils;
import org.cdsframework.util.JsfUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.ObjectUtils;
import org.cdsframework.util.StringUtils;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.enumeration.SourceOperation;
import org.cdsframework.util.enumeration.LoadStatus;
import org.cdsframework.util.enumeration.PrePost;
import org.cdsframework.util.enumeration.RecordPosition;
import org.cdsframework.util.enumeration.SourceMethod;
import org.cdsframework.util.enumeration.UIDataType;
import org.cdsframework.util.support.CoreConstants;
import org.cdsframework.util.support.DeepCopy;
import org.primefaces.behavior.ajax.AjaxBehavior;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.component.treetable.TreeTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @param <T>
 * @author HLN Consulting, LLC
 */
public abstract class BaseModule<T extends BaseDTO> implements Serializable {

    private static final long serialVersionUID = -1739374569719399277L;
    public static final String SEARCH_MAIN_PANEL_ID_SUFFIX = "MainPanelId";
    public static final String SEARCH_FORM_ID_SUFFIX = "SearchFormId";
    public static final String FORM_SEARCH_ID_SUFFIX = "FormSearchId";
    public static final String EDIT_FORM_ID_SUFFIX = "EditFormId";
    public static final String FORM_EDIT_ID_SUFFIX = "FormEditId";
    public static final String EDIT_DIALOG_ID_SUFFIX = "EditDialogId";
    public static final String TAB_VIEW_ID_SUFFIX = "TabViewId";
    public static final String DATA_TABLE_ID_SUFFIX = "DataTableId";
    public static final String EDIT_DIALOG_VAR_SUFFIX = "EditDialogWidgetVar";
    public static final String TAB_VIEW_VAR_SUFFIX = "TabViewWidgetVar";
    public static final String DATA_TABLE_VAR_SUFFIX = "DataTableWidgetVar";
    public static final String POST_OPEN_DIALOG_SUFFIX = "PostOpen";

    protected LogUtils logger;
    @Inject
    protected UserSession userSession;
    @Inject
    protected MessageMGR messageMGR;
    @Inject
    protected CatApplication catApplication;
    // Used to manage the registered children
    private final HashMap<Class, BaseModule> registeredChildMap = new HashMap<Class, BaseModule>();
    // Binds to the facelet deleteConfirmForm
//    private UIForm deleteConfirmForm;
    // DTO Data Type
    private Class<? extends BaseDTO> dtoClassType;
    // Selected DTO for Edit/Add/Delete
    private T parentDTO;
    private T origParentDTO;
    private boolean trackOrigParentDTO = false;
    // DTO List
    private DataTableInterface<T> dataTableMGR;
    private boolean lazy = false;
    // default search class - usually null or FindAll
    private String initialQueryClass = null;
    // base header
    private String baseHeader;
    // mgr name
    private String name;
    // if there are lists associated with this manager mark them as dirty after edits are made to the content
    private final List<BaseDTOList> associatedDTOListList = new ArrayList<BaseDTOList>();
//    private Dialog deleteDialog;
//    private ConfirmDialog discardChangesDialog;
//    private ConfirmDialog saveChangesDialog;
    // Used to indicate that the Parent needs to be retrieved when the row is selected
    private boolean onRowSelectFindParent = true;
    // ChildClassDTOs retrieved when the row is selected
    private List<Class<? extends BaseDTO>> onRowSelectChildClassDTOs = new ArrayList<Class<? extends BaseDTO>>();
    // ChildClassDTOs retrieved with the search request
    private List<Class<? extends BaseDTO>> searchChildClassDTOs = new ArrayList<Class<? extends BaseDTO>>();
    // Anything in here will get added to the propertyBag propertyMap
    private final Map<String, Object> propertyMap = new HashMap<String, Object>();
    private String defaultOperationName;
    private String addedMessageKey = "added";
    private String matchedMessageKey = "matched";
    private String updatedMessageKey = "updated";
    private String deletedMessageKey = "deleted";
    private String unchangedMessageKey = "unchanged";
    private final PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
    private final Map<String, List<OtherUpdateIdCallback>> otherUpdateIds = new HashMap<String, List<OtherUpdateIdCallback>>();
    @Inject
    private Mts mts;
    // the current active tab index if onTabChangeMain is triggered
    private int rowsPerPage = 0;
    private OnSavedCallback onSavedCallback;
    // Used for lazy loader control
    private DTOState lastDTOState = null;
    // Used to control display of message after row is selected
    private boolean autoRun = false;
    private List<Message> queuedMessages = new ArrayList<Message>();
//    private List<T> filteredDTOs = null;
//    private boolean globalFilterEnabled;
    private boolean gotoPreviousRecordEnabled;
    private boolean gotoNextRecordEnabled;
    private boolean child;

    // Flag to determine if save should be called on the baseParentMGR
    private boolean saveOnBaseParentMGR = false;
    private boolean refreshSelection = false;
    // Refers to the parentMGR associated with this childMGR.
    private BaseModule parentMGR;
    private Class childQueryClass = null;
    private boolean saveDeleteOnBaseParentMgr;

    private TabService tabService;
    private InlineDataTableService inlineDataTableService;
    private boolean saveImmediately = false;

    @Inject
    private UserSecuritySchemePermissionMapList userSecuritySchemePermissionMapList;
    private List<String> excludedValueExpressions = new ArrayList<String>();
    private Map<String, String> alternateValueExpressionMap = new HashMap<String, String>();
    private String javaScript = null;

    // Flag to indicate secureUU should be involed
    private boolean secureUI = false;
    // Flag to indicate UI was secured, prevents future executions
    private boolean uiSecured = false;
    // Indicates the type of data this module is managing
    private UIDataType uiDataType = UIDataType.DataTable;
    // Indicates whether the dataTable component exists
    private boolean dataTableExists = true;

    private DataAccessInterface dataAccessInterface = null;
    private LoadStatus loadStatus = null;
    private BaseDTO selectedDTO = null;
    private String targetFieldName;

    // Used to set flag when form is opened as a popup in readOnly
    private boolean readOnly = false;
    
    @PostConstruct
    public void postConstructor() {
        final String METHODNAME = "postConstructor ";
        logger = LogUtils.getLogger(getClass());
        logger.logBegin(METHODNAME);
        tabService = new TabService(this);
        inlineDataTableService = new InlineDataTableService(this);
        dataAccessInterface = DataAccessService.getDataAccessInterface(this);
        messageMGR.setMessageBundle(getClass());
        initializeMain();
        initialize();
        postInitializeMain();
        if (dtoClassType != null) {
            setDataTableMGR(new ArrayList<T>());
//            createDataTableMGR(new ArrayList<T>());
        }

    }

    protected void initialize() {
    }

    public CatApplication getCatApplication() {
        return catApplication;
    }

    public MessageMGR getMessageMGR() {
        return messageMGR;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public SessionDTO getSessionDTO() {
        return userSession.getSessionDTO();
    }

    public void processSelect() {
        throw new UnsupportedOperationException("This method has not been overrided.");
    }

    public boolean isLazy() {
        return this.lazy;
    }

    protected void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    protected void initializeMain() {
        final String METHODNAME = "initializeMain ";
        try {
            dtoClassType = ClassUtils.getTypeArgument(BaseModule.class, getClass());
            if (dtoClassType != null) {
                logger.debug("Derived dto class: ", dtoClassType);
                try {
                    parentDTO = (T) getDtoClassType().newInstance();
                } catch (InstantiationException e) {
                    logger.error("InstantiationException on ", getDtoClassType().getCanonicalName(), ": ", e.getMessage());
                }
                initializeSearch();
            } else {
                logger.warn("ClassUtils.getTypeArgument returned null for: ", getClass().getCanonicalName());
            }
        } catch (Exception e) {
            onExceptionMain(SourceMethod.initializeMain, e);

        } finally {
        }
    }

    protected void postInitializeMain() {
        final String METHODNAME = "postInitializeMain ";

//        if (isGlobalFilterEnabled()) {
//            DataTable dataTable = getDataTable();
//            if (dataTable != null) {
////                logger.info(METHODNAME, "adding filteredValue to ", getName());
//                ValueExpression ve = FacesContext.
//                        getCurrentInstance().
//                        getApplication().
//                        getExpressionFactory().
//                        createValueExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + getName() + ".filteredDTOs}", List.class);
//                dataTable.setValueExpression("filteredValue", ve);
//                dataTable.setFilteredValue(filteredDTOs);
//            } else {
//                logger.warn(METHODNAME, "dataTable is null!");
//            }
//        }
        postInitialize();
    }

    protected void postInitialize() {
    }

    public void setAssociatedList(String beanClassName) {
        final String METHODNAME = "setAssociatedList ";
        try {
            Class<? extends BaseDTOList> beanClass = (Class) Class.forName(beanClassName);
            setAssociatedList(BeanUtils.getBean(beanClass));
            logger.debug(METHODNAME, "successfully looked up ", beanClassName, " bean.");
        } catch (NamingException e) {
            logger.warn(METHODNAME, beanClassName, " not found!");
        } catch (ClassNotFoundException e) {
            logger.warn(METHODNAME, beanClassName, " not found!");
        }
    }

    protected void setAssociatedList(BaseDTOList associatedList) {
        try {
            if (associatedList.isQueryDynamic()) {
                throw new CatException(associatedList.getClass().getSimpleName()
                        + " was asssociated with this manager but it is a dynamic query BaseDTOList.");
            }
            this.associatedDTOListList.add(associatedList);
        } catch (Exception e) {
            onExceptionMain(SourceMethod.setAssociatedList, e);
        }
    }

    protected void preMarkAssociatedDTOListDirty() {
        // user override...
    }

    public void markAssociatedDTOListDirty() {
        preMarkAssociatedDTOListDirty();
        for (BaseDTOList associatedList : associatedDTOListList) {
            if (associatedList != null) {
                logger.info("Marking DTO list dirty: ", associatedList.getClass().getSimpleName());
                associatedList.setDirty(true);
            }
        }
        for (BaseModule childModule : this.getRegisteredChildMap().values()) {
            childModule.markAssociatedDTOListDirty();
        }
    }

    protected void addOtherEditFormUpdateIds(OtherUpdateIdCallback callback) {
        final String key = "editForm";
        List<OtherUpdateIdCallback> otherEditFormIds = otherUpdateIds.get(key);
        if (otherEditFormIds == null) {
            otherEditFormIds = new ArrayList<OtherUpdateIdCallback>();
            otherUpdateIds.put(key, otherEditFormIds);
        }
        otherEditFormIds.add(callback);
    }

    protected void addOtherSearchFormUpdateIds(OtherUpdateIdCallback callback) {
        final String key = "searchForm";
        List<OtherUpdateIdCallback> otherSearchFormIds = otherUpdateIds.get(key);
        if (otherSearchFormIds == null) {
            otherSearchFormIds = new ArrayList<OtherUpdateIdCallback>();
            otherUpdateIds.put(key, otherSearchFormIds);
        }
        otherSearchFormIds.add(callback);
    }

    protected void addOtherRowSelectUpdateIds(OtherUpdateIdCallback callback) {
        final String key = "rowSelect";
        List<OtherUpdateIdCallback> otherRowSelectIds = otherUpdateIds.get(key);
        if (otherRowSelectIds == null) {
            otherRowSelectIds = new ArrayList<OtherUpdateIdCallback>();
            otherUpdateIds.put(key, otherRowSelectIds);
        }
        otherRowSelectIds.add(callback);
    }

    // For descendant overrides
    protected void onRowSelect(SelectEvent selectEvent) throws Exception {
    }
//
//    protected Object[] getMessageArgs() throws Exception {
//        Object[] result;
//
//        if (parentDTO != null) {
//            result = new Object[]{parentDTO.toString()};
//        } else {
//            result = new Object[]{};
//        }
//
//        return result;
//    }

    public HashMap<Class, BaseModule> getRegisteredChildMap() {
        return registeredChildMap;
    }

    protected void registerChild(Class queryClass, BaseModule childHandler) {
        final String METHODNAME = "registerChild ";
        try {
            if (!registeredChildMap.containsKey(queryClass)) {

                if (!DTOUtils.isDTOWrapper(childHandler.getDtoClassType())) {
                    ParentChildRelationship parentChildRelationship = DTOUtils.getParentChildRelationshipMapByQueryClass(dtoClassType).get(queryClass);
                    if (parentChildRelationship == null) {
                        throw new CatException(METHODNAME
                                + "Could not find a registerable relationship for query class "
                                + queryClass.getCanonicalName()
                                + " in "
                                + dtoClassType.getCanonicalName());
                    }
                }
                childHandler.setChild(true);
                childHandler.setParentMGR(this);
                childHandler.setChildQueryClass(queryClass);
                if (isSaveOnBaseParentMGR()) {
                    childHandler.setAddedMessageKey("added");
                    childHandler.setUpdatedMessageKey("updated");
                    childHandler.setMatchedMessageKey("matched");
                    
                } else {
                    childHandler.setAddedMessageKey("childAdded");
                    childHandler.setUpdatedMessageKey("childUpdated");
                    childHandler.setMatchedMessageKey("childMatched");
                }
                if (isSaveDeleteOnBaseParentMgr()) {
                    childHandler.setDeletedMessageKey("deleted");
                } else if (isSaveImmediately()) {
                    childHandler.setDeletedMessageKey("deleted");
                } else {
                    childHandler.setDeletedMessageKey("childDeleted");
                }
                registeredChildMap.put(queryClass, childHandler);
            }
        } catch (Exception e) {
            onExceptionMain(SourceMethod.registerChild, e);
        }
    }

    public BaseModule getChildHandler(String queryClassName) throws NotFoundException {
        return registeredChildMap.get(ClassUtils.classForName(queryClassName));
    }

    public String getParentEditFormId() {
        String result;
        if (isChild()) {
            if (getParentMGR() != null) {
                result = getParentMGR().getEditFormId();
            } else {
                result = "";
            }
        } else {
            result = getEditFormId();
        }
        return result;
    }

    public UIForm getEditForm() {
        final String METHODNAME = "getEditForm ";
        long start = System.nanoTime();
        UIForm result = null;
        UIComponent uiComponentById = UtilityMGR.getUIComponentById(getFormEditUpdateId());
        if (uiComponentById != null) {
            result = (UIForm) uiComponentById;
        } else {
            logger.error(METHODNAME, "failed to retrieve!");
        }
        logger.logDuration(LogLevel.DEBUG, METHODNAME, start);
        return result;
    }

    public TabView getTabView() {
        final String METHODNAME = "getTabView ";
        long start = System.nanoTime();
        TabView result = null;
        UIComponent uiComponentById = UtilityMGR.getUIComponentById(getTabViewUpdateId());
        if (uiComponentById != null) {
            result = (TabView) uiComponentById;
        } else {
            logger.error(METHODNAME, "failed to retrieve!");
        }
        logger.logDuration(LogLevel.DEBUG, METHODNAME, start);
        return result;
    }

    public DataTable getDataTable() {
        final String METHODNAME = "getDataTable ";
        DataTable dataTable = null;
        if (getUIDataType() == UIDataType.DataTable) {
            UIComponent uiComponentById = UtilityMGR.getUIComponentById(getDataTableUpdateId());
            if (uiComponentById != null) {
                dataTable = (DataTable) uiComponentById;
            }
        }
        return dataTable;
    }

//    public UIForm getDeleteConfirmForm() {
//        return deleteConfirmForm;
//    }
//
//    public void setDeleteConfirmForm(UIForm deleteConfirmForm) {
//        this.deleteConfirmForm = deleteConfirmForm;
//    }
    public String getSearchMainPanelId() {
        return this.getName() + SEARCH_MAIN_PANEL_ID_SUFFIX;
    }

    public String getFormSearchId() {
        return this.getName() + FORM_SEARCH_ID_SUFFIX;
    }

    public String getSearchFormId() {
        final String METHODNAME = "getSearchFormId";
        String result;
        if (isChild()) {
            try {
                if (this.getParentMGR() == null) {
                    throw new MtsException("parentMGR is null - did you register this child?");
                }
            } catch (Exception e) {
                onExceptionMain(SourceMethod.getSearchFormId, e);
            } finally {
                logger.logEnd(METHODNAME);
            }
            result = getParentMGR().getEditFormId();
        } else {
            result = getName() + SEARCH_FORM_ID_SUFFIX;
        }

        return result;
    }

    public String getEditFormId() {
        return this.getName() + EDIT_FORM_ID_SUFFIX;
    }

    public String getFormEditId() {
        return this.getName() + FORM_EDIT_ID_SUFFIX;
    }

    public String getEditDialogId() {
        return this.getName() + EDIT_DIALOG_ID_SUFFIX;
    }

    public String getTabViewId() {
        return this.getName() + TAB_VIEW_ID_SUFFIX;
    }

    public String getDataTableId() {
        return this.getName() + DATA_TABLE_ID_SUFFIX;
    }

    public String getPostOpenDialog() {
        final String METHODNAME = "getPostOpenDialog ";
        logger.trace(METHODNAME);
        return getEditDialogWidgetVar() + POST_OPEN_DIALOG_SUFFIX;
    }

    public String getEditDialogWidgetVar(String context) {
        return getEditDialogWidgetVar();
    }

    public String getEditDialogWidgetVar() {
        return getName() + EDIT_DIALOG_VAR_SUFFIX;
    }

    public String getTabViewWidgetVar() {
        return getName() + TAB_VIEW_VAR_SUFFIX;
    }

    public String getDataTableWidgetVar() {
        return getName() + DATA_TABLE_VAR_SUFFIX;
    }

//    public Dialog getDeleteDialog() {
//        return deleteDialog;
//    }
//
//    public void setDeleteDialog(Dialog deleteDialog) {
//        final String METHODNAME = "setDeleteDialog ";
//        if (deleteDialog != null && deleteDialog.getWidgetVar() == null) {
//            String widgetVar = getName() + "DeleteDialogVar";
//            deleteDialog.setWidgetVar(widgetVar);
//            logger.debug(METHODNAME, "setting widget var to: ", widgetVar);
//        }
//        this.deleteDialog = deleteDialog;
//    }
//    public String getDeleteDialogWidgetVar() {
//        final String METHODNAME = "getDeleteDialogWidgetVar ";
//        String result = "UNDEFINED_deleteDialogWidgetVar_PLEASE_DEFINE";
//        if (deleteDialog != null && deleteDialog.getWidgetVar() != null) {
//            result = deleteDialog.getWidgetVar();
//        }
//        logger.debug(METHODNAME, result);
//        return result;
//    }
//    public ConfirmDialog getDiscardChangesDialog() {
//        return discardChangesDialog;
//    }
//
//    public void setDiscardChangesDialog(ConfirmDialog discardChangesDialog) {
//        if (discardChangesDialog != null && discardChangesDialog.getWidgetVar() == null) {
//            discardChangesDialog.setWidgetVar(getName() + "DiscardChangesDialogVar");
//        }
//        this.discardChangesDialog = discardChangesDialog;
//    }
//
//    public String getDiscardChangesDialogWidgetVar() {
//        final String METHODNAME = "getDiscardChangesDialogWidgetVar ";
//        String result = "UNDEFINED_discardChangesDialogWidgetVar_PLEASE_DEFINE";
//        if (discardChangesDialog != null && discardChangesDialog.getWidgetVar() != null) {
//            result = discardChangesDialog.getWidgetVar();
//        }
//        logger.debug(METHODNAME, result);
//        return result;
//    }
//    public ConfirmDialog getSaveChangesDialog() {
//        return saveChangesDialog;
//    }
//
//    public void setSaveChangesDialog(ConfirmDialog saveChangesDialog) {
//        if (saveChangesDialog != null && saveChangesDialog.getWidgetVar() == null) {
//            saveChangesDialog.setWidgetVar(getName() + "SaveChangesDialogVar");
//        }
//        this.saveChangesDialog = saveChangesDialog;
//    }
//    public String getSaveChangesDialogWidgetVar() {
//        final String METHODNAME = "getSaveChangesDialogWidgetVar ";
//        String result = "";
//        if (saveChangesDialog != null && saveChangesDialog.getWidgetVar() != null) {
//            result = saveChangesDialog.getWidgetVar();
//        }
//        logger.debug(METHODNAME, result);
//        return result;
//    }
    public T getParentDTO() {
        return parentDTO;
    }

    public T getOrigParentDTO() {
        return origParentDTO;
    }

    // descendant override
    public void preSetParentDTO(T parentDTO) {
    }

    // descendant override
    public void postSetParentDTO(T parentDTO) {
    }

    public void setParentDTO(T parentDTO) {
        final String METHODNAME = "setParentDTO ";
        logger.logBegin(METHODNAME);
        preSetParentDTO(parentDTO);
        try {
            if (parentDTO == null) {
                this.parentDTO = null;
                this.origParentDTO = null;
            } else {
                logger.debug("Parent UUID: ", parentDTO.getUuid());
                this.parentDTO = DeepCopy.copy(parentDTO);
                logger.debug("Copied UUID: ", this.parentDTO.getUuid());
            }
            if (this.parentDTO != null && !registeredChildMap.isEmpty()) {
                logger.debug("Setting child mgr dtoLists");
                for (Entry<Class, BaseModule> entry : registeredChildMap.entrySet()) {
                    Class queryClass = entry.getKey();
                    if (logger.isDebugEnabled()) {
                        logger.info("Setting dtoLists for: ", queryClass.getCanonicalName());
                    }
                    BaseModule childMgr = registeredChildMap.get(queryClass);
                    if (!DTOUtils.isDTOWrapper(childMgr.getDtoClassType())) {
                        if (!childMgr.isSaveImmediately()) {
                            childMgr.setDataTableMGR(this.parentDTO.getChildrenDTOs(queryClass));
                            childMgr.resetPaginator();
                        }
                    }
                }
            }
        } catch (Exception e) {
            onExceptionMain(SourceMethod.setParentDTO, e);
        } finally {
            logger.logEnd(METHODNAME);
        }
        postSetParentDTO(parentDTO);
    }

    public Class getDtoClassType() {
        return dtoClassType;
    }

    public DataTableInterface<T> getDataTableMGR() {
        return dataTableMGR;
    }

    public void setDataTableMGR(DataTableInterface<T> dataTableMGR) {
        this.dataTableMGR = dataTableMGR;
    }

//    private void createDataTableMGR(List<T> dtoList) {
//        if (!isLazy()) {
//            dataTableMGR = new DataTableMGR<T>(dtoList, getClass());
//        } else {
//            dataAccessInterface.initializeLazyDataInterface(null);
////            dataTableMGR = getLazyDataTableMGR(null);
//        }
//    }
    protected <S extends BaseDTO> S getNewDTO(Class<S> dtoClass) throws CatException {
        final String METHODNAME = "getNewDTO: ";
        logger.logBegin(METHODNAME);
        S newDTO = null;
        try {
            newDTO = dtoClass.newInstance();
        } catch (InstantiationException e) {
            logger.error(METHODNAME, "An InstantiationException has occurred; Message: ", e.getMessage(), e);
            throw new CatException("Unable to create a new instance of: " + dtoClass);
        } catch (IllegalAccessException e) {
            logger.error(METHODNAME, "An IllegalAccessException has occurred; Message: ", e.getMessage(), e);
            throw new CatException("Unable to create a new instance of: " + dtoClass);
        } finally {
            logger.logEnd(METHODNAME);
        }
        return newDTO;
    }

    public void setDataTableMGR(List<T> dtoList) {
        final String METHODNAME = "setDataTableMGR ";
        logger.debug(METHODNAME, "called! ", dtoClassType, " - ", dtoList != null ? dtoList.size() : null);
        dataAccessInterface.initialize((List<BaseDTO>) dtoList);
//        if (!isLazy()) {
//            dataTableMGR = new DataTableMGR<T>(dtoList, getClass());
//        } else {
//            dataAccessInterface.initializeLazyDataInterface(null);
////            dataTableMGR = getLazyDataTableMGR(null);
//        }
        //createDataTableMGR(dtoList);
    }

    public DTOState getDTOState(T dto) {
        DTOState dtoState = DTOState.UNSET;
        try {
            T matchedDTO = dataTableMGR.getRowData(dto);
            if (matchedDTO != null) {
                DTOState matchedDTOState = matchedDTO.getDTOState();
                if (matchedDTOState != DTOState.UNSET) {
                    dtoState = matchedDTOState;
                } else {
                    dtoState = matchedDTO.getOperationDTOState();
                }
            }
        } catch (Exception e) {
            onExceptionMain(SourceMethod.getDTOState, e);
        } finally {
        }
        return dtoState;
    }

//    private DTOState getParentDTOState() {
//        return getDTOState(getParentDTO());
//    }
    public String getInitialQueryClass() {
        return initialQueryClass;
    }

    protected void setInitialQueryClass(String initialQueryClass) {
        this.initialQueryClass = initialQueryClass;
    }

    public void performInitialSearch() {
        final String METHODNAME = "performInitialSearch ";
        if (initialQueryClass != null) {
            performInitialSearch(initialQueryClass);
        }
    }

    public void performInitialSearch(String queryClass) {
        final String METHODNAME = "performInitialSearch ";
        try {
            searchByClassName(queryClass);
        } finally {
            setInitialSearchUpdateIds();
        }
    }

    public void addMain(ActionEvent actionEvent) {
        final String METHODNAME = "addMain ";
        logger.logBegin(METHODNAME);
        try {
            TabView tabView = getTabView();
            if (tabView != null) {
                tabView.setActiveIndex(0);
            }
            prePostOperation(null, null, PrePost.PreAdd, false);
            preAdd(actionEvent);
            // Call Tab Lazy Load Services
            tabService.addMain(actionEvent);
            clearFormMain();
            T newDTO = (T) getDtoClassType().newInstance();
            setParentDTO(newDTO);
            prePostOperation(null, newDTO, PrePost.PostAdd, true);
            postAdd(actionEvent);
        } catch (Exception e) {
            onExceptionMain(SourceMethod.addMain, e);
        } finally {
            setNewUpdateIds();
            logger.logEnd(METHODNAME);
        }
    }

    public void addMainInline(ActionEvent actionEvent) {
        final String METHODNAME = "addMainInline ";
        logger.logBegin(METHODNAME);
        try {
            prePostOperation(null, null, PrePost.PreAdd, false);
            preAdd(actionEvent);
            T newDTO = (T) getDtoClassType().newInstance();
            setParentDTO(newDTO);
            getDataTableMGR().addOrUpdateDTO(newDTO);
            prePostOperation(null, newDTO, PrePost.PostAdd, true);
            postAdd(actionEvent);
        } catch (Exception e) {
            onExceptionMain(SourceMethod.addMainInline, e);

        } finally {
            setInlineNewUpdateIds();
            logger.logEnd(METHODNAME);
        }
    }

    /**
     * GET RID OF THIS
     *
     * @param actionEvent
     * @throws Exception
     */
    protected void preAdd(ActionEvent actionEvent) throws Exception {
    }

    /**
     * GET RID OF THIS
     *
     * @param actionEvent
     * @throws Exception
     */
    protected void postAdd(ActionEvent actionEvent) throws Exception {
    }

    private void clearFormMain() {
        final String METHODNAME = "clearForm ";
        logger.logBegin(METHODNAME);
        try {
            UIForm editForm = getEditForm();
            if (editForm != null) {
                JsfUtils.resetForm(editForm);
                clearForm(editForm);
            }
        } catch (Exception e) {
            onExceptionMain(SourceMethod.clearFormMain, e);

        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    public void closeMain(CloseEvent event) {
        final String METHODNAME = "closeMain ";
        logger.logBegin(METHODNAME);
        try {
            // Call the descendant level method
            preClose(event);

            // Call Tab Lazy Load Services
//            tabLazyLoadService.onClose(event);
            // Clear the form
            clearFormMain();
            // Call the descendant level method
            postClose(event);
            // Reset the ParentDTO
            setParentDTO(null);

            if (getTabView() != null) {
                getTabView().setActiveIndex(0);
            }
        } catch (Exception e) {
            onExceptionMain(SourceMethod.closeMain, e);

        } finally {
            setReadOnly(false);
            logger.logEnd(METHODNAME);
        }
    }

    protected void preClose(CloseEvent event) {
    }

    protected void postClose(CloseEvent event) {
    }

    public void confirmCloseMain(CloseEvent event) {
        final String METHODNAME = "confirmCloseMain ";
        logger.logBegin(METHODNAME);
        try {
            preConfirmClose(event);
            postConfirmClose(event);
        } finally {
            setDeleteCloseUpdateIds();
            logger.logEnd(METHODNAME);
        }
    }

    protected void preConfirmClose(CloseEvent event) {
    }

    protected void postConfirmClose(CloseEvent event) {
    }

    /**
     * GET RID OF THIS
     *
     * @throws Exception
     */
    protected void preDelete() throws Exception {
    }

    /**
     * GET RID OF THIS
     *
     * @throws Exception
     */
    protected void postDelete() throws Exception {
    }

    public void resetSearch(ActionEvent actionEvent) {
        final String METHODNAME = "resetSearch ";
        logger.debug(METHODNAME, "called!");
        try {
            // Reset the Search
            UIForm uiForm = (UIForm) UtilityMGR.getUIComponentFromBaseId(getFormSearchUpdateId());
            if (uiForm != null) {
                JsfUtils.resetForm(uiForm);
            }
            // Clear out the search results
            setDataTableMGR(new ArrayList<T>());
        } catch (Exception e) {
            onExceptionMain(SourceMethod.resetSearch, e);
        }
    }

    public void closePopupMain(CloseEvent event) {
        final String METHODNAME = "closePopupMain ";
        logger.logBegin(METHODNAME);
        try {
            setDataTableMGR(new ArrayList<T>());
            this.resetPaginator();
        } finally {
            logger.logEnd(METHODNAME);
            setReadOnly(false);
            setSearchUpdateIds();
        }
    }

    public void clearSearchMain(ActionEvent actionEvent) {
        final String METHODNAME = "clearSearchMain ";
        logger.debug(METHODNAME, "called!");
        try {
            UIForm uiForm = null;
            if (actionEvent != null) {
                UIComponent component = actionEvent.getComponent();
                if (component != null) {
                    logger.debug(METHODNAME, "got component: ", component.getClientId());
                    uiForm = JsfUtils.getUIForm(component);
                } else {
                    logger.debug(METHODNAME, "component is null!");
                }
            } else {
                logger.debug(METHODNAME, "actionEvent is null!");
            }

            if (uiForm == null) {
                uiForm = (UIForm) UtilityMGR.getUIComponentFromBaseId(getFormSearchUpdateId());
            } else {
                logger.debug(METHODNAME, "got component based UIForm: ", uiForm.getClientId());
            }

            if (uiForm != null) {
                logger.debug(METHODNAME, "resetting UIForm: ", uiForm.getClientId());
                JsfUtils.resetForm(uiForm);
            } else {
                logger.debug(METHODNAME, "uiForm is null!");
            }

            logger.debug("setting searchCriteriaDTO to a new instance - was: ", searchCriteriaDTO != null ? searchCriteriaDTO.getQueryMap() : "NULL");
            searchCriteriaDTO = (T) dtoClassType.newInstance();
            clearSearch(actionEvent);
            setDataTableMGR(new ArrayList<T>());
            if (getDataTable() != null) {
                getDataTable().setValueExpression("sortBy", null);
            }
        } catch (Exception e) {
            onExceptionMain(SourceMethod.clearSearchMain, e);
        } finally {
            setSearchUpdateIds();
        }
    }

    protected void isChanged(ActionEvent actionEvent) {
        final String METHODNAME = "isChanged ";
        logger.logBegin(METHODNAME);
        boolean changed = false;
        try {
            if (this.getParentDTO() != null) {
                changed = this.getParentDTO().getOperationDTOState() != DTOState.UNSET;
            }
        } finally {
            UtilityMGR.setCallbackParam("changed", changed);
            logger.trace("isChanged result: ", changed);
            logger.logEnd(METHODNAME);
        }
    }

    /*
    * prePostOperation
    * queryClass - used by inline datatables to identify the datatable pre/post 
    *              before/after an operation
    * baseDTO - the dto that is being operated on
    * prePost - the operation
    * status - true/false used to communicate if records were retrieved or dtos were saved
     */
    public void prePostOperation(String queryClass, BaseDTO baseDTO, PrePost prePost, boolean status) {
        final String METHODNAME = "prePostOperation ";
        logger.debug(METHODNAME, "queryClass=", queryClass, " prePost=", prePost, " status=", status);
    }

    /**
     * GET RID OF THIS
     *
     * @param actionEvent
     * @throws Exception
     */
    protected void preSave(ActionEvent actionEvent) throws Exception {
    }

    /**
     * GET RID OF THIS
     *
     * @param actionEvent
     * @throws Exception
     */
    protected void postSave(ActionEvent actionEvent) throws Exception {
    }

    public void customMain(ActionEvent actionEvent) {
        customMain(actionEvent, true);
    }

    protected void customMain(ActionEvent actionEvent, boolean displayMessage) {
        final String METHODNAME = "customMain ";
        logger.logBegin(METHODNAME);
        boolean custom = false;
        try {
            // Get the ParentDTO
            T dto = getParentDTO();
            // Is it null?, it shouldn't be!
            if (dto != null) {
                preCustom(actionEvent);
                UUID uuid = dto.getUuid();

                // Execute custom method
                dto = custom(actionEvent);
                dto.setUuid(uuid);
                dto = getDataTableMGR().addOrUpdateDTO(dto);
                setParentDTO(dto);
                if (displayMessage) {
                    String messageId = actionEvent.getComponent().getId();
                    messageMGR.displayInfo(messageId, dataAccessInterface.getMessageArgs(getParentDTO(), SourceMethod.customMain));
                }
                custom = true;
                markAssociatedDTOListDirty();
                postCustom(actionEvent);
            } else {
                throw new CatException("The row was null!");
            }

        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            UtilityMGR.setCallbackParam("custom", custom);
            logger.logEnd(METHODNAME);
        }

    }

    protected T custom(ActionEvent actionEvent) throws Exception {
        throw new UnsupportedOperationException("Unsupported Operation.");
    }

    protected void preCustom(ActionEvent actionEvent) {
    }

    protected void postCustom(ActionEvent actionEvent) {
    }

    // Used to search by a query class
    public void searchByClassName(String queryClassName) {
        searchByClassName(queryClassName, true);
    }

    public void searchByClassName(String queryClassName, boolean clearSearch) {
        try {
            if (queryClassName == null) {
                throw new CatException("queryClassName was null!");
            }
            getSearchCriteriaDTO();
            UIComponent uiForm = new UIForm();
            uiForm.setId(queryClassName);
            ActionEvent actionEvent = new ActionEvent(uiForm);
            logger.debug("searchCriteriaDTO: ", searchCriteriaDTO.getQueryMap());
            if (clearSearch) {
                clearSearchMain(null);
            }
            logger.debug("searchCriteriaDTO: ", searchCriteriaDTO.getQueryMap());
            searchMain(actionEvent);
            setDataTableSearchUpdateIds();
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    protected void preSearch(ActionEvent actionEvent) {
    }

    protected void postSearch(ActionEvent actionEvent) {
    }

    protected void clearForm(UIForm editForm) {
    }

    public String getBaseHeader() {
        if (baseHeader == null) {
            String simpleName = getClass().getSimpleName();
            baseHeader = StringUtils.unCamelize(simpleName.substring(0, simpleName.toLowerCase().indexOf("mgr")));
        }
        return baseHeader;
    }

    // in order to override the bease header value
    protected void setBaseHeader(String baseHeader) {
        this.baseHeader = baseHeader;
    }

    public String getName() {
        if (name == null) {
            String simpleName = this.getClass().getSimpleName();
            name = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1, simpleName.toLowerCase().indexOf("mgr"));
        }
        return name;
    }

    // in order to verride the base ID value
    protected void setName(String name) {
        this.name = name;
    }

//
//    /**
//     * get the message display object ID - no arg - default container prefix to false
//     *
//     * @return the message display object ID
//     */
//    public String getMessageDisplayUpdateId() {
//        return getMessageDisplayUpdateId(false);
//    }
//
//    /**
//     * get the message display object update ID
//     *
//     * @param outsideNamingContainer whether or not to add the colon (":")
//     * @return the message display object ID
//     */
//    public String getMessageDisplayUpdateId(boolean outsideNamingContainer) {
//        final String METHODNAME = "getMessageDisplayUpdateId ";
//        String result = "";
//        result = UtilityMGR.getUIComponentClientId(catApplication.getMessageDisplay(), outsideNamingContainer);
//        return result;
//    }
    public String getDataTableUpdateId() {
        return getDataTableUpdateId(false);
    }

    public String getDataTableUpdateId(boolean outsideNamingContainer) {
        return UtilityMGR.getUIComponentUpdateId(getDataTableId(), outsideNamingContainer);
    }

    public String getTabViewUpdateId() {
        return getTabViewUpdateId(false);
    }

    public String getTabViewUpdateId(boolean outsideNamingContainer) {
        return UtilityMGR.getUIComponentUpdateId(getTabViewId(), outsideNamingContainer);
    }

    public String getEditDialogUpdateId() {
        return getEditDialogUpdateId(false);
    }

    public String getEditDialogUpdateId(boolean outsideNamingContainer) {
        return UtilityMGR.getUIComponentUpdateId(getEditDialogId(), outsideNamingContainer);
    }

    public String getFormEditUpdateId() {
        return getFormEditUpdateId(false);
    }

    public String getFormEditUpdateId(boolean outsideNamingContainer) {
        return UtilityMGR.getUIComponentUpdateId(getFormEditId(), outsideNamingContainer);
    }

    public String getUIMainPanelUpdateId() {
        return getUIMainPanelUpdateId(false);
    }

    public String getUIMainPanelUpdateId(boolean outsideNamingContainer) {
        return UtilityMGR.getUIComponentUpdateId(getSearchMainPanelId(), outsideNamingContainer);
    }

    public String getFormSearchUpdateId() {
        return getFormSearchUpdateId(false);
    }

    public String getFormSearchUpdateId(boolean outsideNamingContainer) {
        return UtilityMGR.getUIComponentUpdateId(getFormSearchId(), outsideNamingContainer);
    }

//    public String getDeleteConfirmUpdateId() {
//        return getDeleteConfirmUpdateId(false);
//    }
//
//    public String getDeleteConfirmUpdateId(boolean outsideNamingContainer) {
//        return UtilityMGR.getUIComponentClientId(getDeleteConfirmForm(), outsideNamingContainer);
//    }
    /**
     * sets a list of IDs to be updated on the JSF response
     *
     * @param caller a string identifier for the logging calls
     * @param ids the list of IDs to update on the JSF response
     */
    public void setUpdateIds(String caller, String... ids) {
        JsfUtils.setUpdateIds(caller, ids);
    }

    public boolean isUpdating(String id) {
        boolean updating = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (requestContext != null) {
            FacesContext currentInstance = FacesContext.getCurrentInstance();
            if (currentInstance != null) {
                PartialViewContext partialViewContext = currentInstance.getPartialViewContext();
                if (partialViewContext != null) {
                    Collection<String> renderIds = partialViewContext.getRenderIds();
                    if (renderIds != null) {
                        updating = renderIds.contains(id);
                    }
                }
            }
        }
        return updating;
    }
//
//    /**
//     * set the message display object id on the list of ids updated on the JSF response
//     */
//    // Here is a stab at documenting what each of this does. There are mutiple methods some do similar things
//    // To Do, Refactor and clean up so that only a few methods get the job done.
//    // Update the grow. Call when you want to update the growl
//    public void setMessageDisplayUpdateId() {
//        final String METHODNAME = "setMessageDisplayUpdateId ";
//        setUpdateIds(METHODNAME, getMessageDisplayUpdateId());
//    }

    // Updates the delete dialog, call when you want to update the delete confirm dialog
//    public void setDeleteConfirmUpdateIds() {
//        final String METHODNAME = "setDeleteConfirmUpdateIds ";
//        setUpdateIds(METHODNAME, getDeleteConfirmUpdateId());
//    }
    // Guessing this updates just the Parent DataTable, not sure what DeleteClose means does?
    // Call when you want to update the datatable
    public void setDeleteCloseUpdateIds() {
        final String METHODNAME = "setDeleteCloseUpdateIds ";
        setUpdateIds(METHODNAME, getDataTableUpdateId());
    }
//
//    // Guessing this updates just the growl, not sure what DeleteUpdate means?
//    // Clone of setMessageDisplayUpdateId
//    public void setDeleteUpdateIds() {
//        final String METHODNAME = "setDeleteUpdateIds ";
//        setUpdateIds(METHODNAME, getMessageDisplayUpdateId());
//    }

    // Updates the Edit Form and any subclass registered Ids
    // call when you want to update the growl, edit form, and other ids
    public void setSaveUpdateIds() {
        final String METHODNAME = "setSaveUpdateIds ";
//        if (!isSaveImmediately()) {
        setUpdateIds(METHODNAME, getFormEditUpdateId(), getDataTableUpdateId());
//        }
//        else {
//            // Update new Form Button
//            // Update new DataTable Button
//            // Update Tab to enable tabs
//            setUpdateIds(METHODNAME, getDataTableUpdateId());
//        }
        List<OtherUpdateIdCallback> otherEditFormUpdateIds = otherUpdateIds.get("editForm");
        if (otherEditFormUpdateIds != null) {
            for (OtherUpdateIdCallback callback : otherEditFormUpdateIds) {
                if (callback != null) {
                    setUpdateIds(METHODNAME, callback.getId());
                }
            }
        }
    }

    // Updates datatable and each search form
    // call when you want to update the growl, datatable and each search form and other ids
    public void setInitialSearchUpdateIds() {
        final String METHODNAME = "setInitialSearchUpdateIds ";
        logger.debug(METHODNAME, "called!");
        setUpdateIds(METHODNAME, getDataTableUpdateId());
        setUpdateIds(METHODNAME, getFormSearchUpdateId());
        // Updates any additional call back style register ids
        List<OtherUpdateIdCallback> otherSearchFormUpdateIds = otherUpdateIds.get("searchForm");
        if (otherSearchFormUpdateIds != null) {
            for (OtherUpdateIdCallback callback : otherSearchFormUpdateIds) {
                if (callback != null) {
                    setUpdateIds(METHODNAME, callback.getId());
                }
            }
        }
    }

    // Updates search form message pane and all search forms, similar to setInitialSearchUpdateIds
    // call when you want to update the message pane, search form message panel, all search form
    // and datatable
    public void setDataTableSearchUpdateIds() {
        final String METHODNAME = "setDataTableSearchUpdateIds ";
        logger.debug(METHODNAME, "called!");
        setUpdateIds(METHODNAME, getUIMainPanelUpdateId());
        setUpdateIds(METHODNAME, getFormSearchUpdateId());
        setUpdateIds(METHODNAME, getDataTableUpdateId());
    }

    // Update datatable, growl and search forms similar to setInitialSearchUpdateIds
    // call when you want to update the Datable, search forms, and other ids?
    public void setSearchUpdateIds() {
        final String METHODNAME = "setSearchUpdateIds ";
        logger.debug(METHODNAME, "called!");
        setUpdateIds(METHODNAME, getDataTableUpdateId());
        setUpdateIds(METHODNAME, getFormSearchUpdateId());
        List<OtherUpdateIdCallback> otherSearchFormUpdateIds = otherUpdateIds.get("searchForm");
        if (otherSearchFormUpdateIds != null) {
            for (OtherUpdateIdCallback callback : otherSearchFormUpdateIds) {
                if (callback != null) {
                    setUpdateIds(METHODNAME, callback.getId());
                }
            }
        }
    }

    // Updates Edit form when row is selected
    public void setRowSelectUpdateIds() {
        final String METHODNAME = "setRowSelectUpdateIds ";
        setUpdateIds(METHODNAME, getFormEditUpdateId());
        List<OtherUpdateIdCallback> otherRowSelectUpdateIds = otherUpdateIds.get("rowSelect");
        if (otherRowSelectUpdateIds != null) {
            for (OtherUpdateIdCallback callback : otherRowSelectUpdateIds) {
                if (callback != null) {
                    setUpdateIds(METHODNAME, callback.getId());
                }
            }
        }
    }

    // Update edit form
    public void setNewUpdateIds() {
        final String METHODNAME = "setNewUpdateIds ";
        setUpdateIds(METHODNAME, getFormEditUpdateId());
    }

    // Update edit form
    public void setInlineNewUpdateIds() {
        final String METHODNAME = "setInlineNewUpdateIds ";
        setUpdateIds(METHODNAME, getDataTableUpdateId());
    }

    public TabChangeEvent getPreviousTabChangeEvent() {
        return tabService.getPreviousTabChangeEvent();
    }

    /*
     * These are no longer necessary in the jsf layer as they were used to construct the reference to the datatable clientid
     *
     * START
     */

 /*
     * END
     */
    /**
     * Gets the deleted message value.
     *
     * @param dto
     * @return
     */
    public String getDeleteMessage(BaseDTO dto) {
        final String METHODNAME = "getDeleteMessage ";
        String deleteMessage = this.getClass().getSimpleName() + ".getDeleteMessage needs overriding";
        if (dto == null) {
            dto = getParentDTO();
        }
        if (dto == null) {
            logger.error(METHODNAME, "parentDTO is null!");
            deleteMessage = "";
        } else {
            try {
                Object[] args = dataAccessInterface.getMessageArgs(dto, SourceMethod.getDeleteMessage);
                if (args != null && args.length > 0 && args[0] != null) {
                    deleteMessage = (String) args[0];
                } else if (!dto.isNoId()) {
                    Object primaryKey = dto.getPrimaryKey();
                    if (primaryKey != null) {
                        deleteMessage = primaryKey.toString();
                    }
                }
            } catch (Exception e) {
                logger.error(e);
                deleteMessage = this.getClass().getSimpleName() + ".getDeleteMessage threw an exception: " + e.getMessage();
            }
        }
//        logger.info(METHODNAME, "deleteMessage: ", deleteMessage);
        return deleteMessage;
    }

    // Reset the paginator
    public void resetPaginator() {
        final String METHODNAME = "resetPaginator ";
        logger.logBegin(METHODNAME);
        try {
            logger.debug(METHODNAME, "this.resultsDataTable=", getDataTable());
            if (getDataTable() != null) {
                getDataTable().reset();
                if (isChild() || !isLazy()) {
                    getDataTable().setValueExpression("sortBy", null);
                }
            }
        } catch (Exception e) {
            onExceptionMain(SourceMethod.resetPaginator, e);

        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    public boolean isTrackOrigParentDTO() {
        return trackOrigParentDTO;
    }

    public void setTrackOrigParentDTO(boolean trackOrigParentDTO) {
        this.trackOrigParentDTO = trackOrigParentDTO;
    }

    public void setOrigParentDTO(T origParentDTO) {
        this.origParentDTO = origParentDTO;
    }

    public boolean isOnRowSelectFindParent() {
        return onRowSelectFindParent;
    }

    public void setOnRowSelectFindParent(boolean onRowSelectFindParent) {
        this.onRowSelectFindParent = onRowSelectFindParent;
    }

    public List<Class<? extends BaseDTO>> getOnRowSelectChildClassDTOs() {
        return onRowSelectChildClassDTOs;
    }

    public void setOnRowSelectChildClassDTOs(List<Class<? extends BaseDTO>> onRowSelectChildClassDTOs) {
        this.onRowSelectChildClassDTOs = onRowSelectChildClassDTOs;
    }

    public void addOnRowSelectChildClassDTO(Class<? extends BaseDTO> searchChildDTOClass) {
        if (!onRowSelectChildClassDTOs.contains(searchChildDTOClass)) {
            onRowSelectChildClassDTOs.add(searchChildDTOClass);
        }
    }

    public List<Class<? extends BaseDTO>> getChildClassDTOs(String queryClass, SourceOperation sourceOperation) {
        List<Class<? extends BaseDTO>> childClassDTOs = new ArrayList<Class<? extends BaseDTO>>();
        if (sourceOperation == SourceOperation.Search) {
            childClassDTOs = getSearchChildClassDTOs();
        } else if (sourceOperation == SourceOperation.FindBy) {
            childClassDTOs = getOnRowSelectChildClassDTOs();
        }
        return childClassDTOs;
    }

    public PropertyBagDTO getPropertyBagDTO(String queryClass, SourceOperation sourceOperation) {
        return getNewPropertyBagDTO();
    }

    public List<Class<? extends BaseDTO>> getSearchChildClassDTOs() {
        return searchChildClassDTOs;
    }

    public void setSearchChildClassDTOs(List<Class<? extends BaseDTO>> searchChildClassDTOs) {
        this.searchChildClassDTOs = searchChildClassDTOs;
    }

    public void addSearchChildClassDTO(Class searchChildDTOClass) {
        if (!searchChildClassDTOs.contains(searchChildDTOClass)) {
            searchChildClassDTOs.add(searchChildDTOClass);
        }
    }

    /**
     * Client override for pre find by primary key searches. Gives implementor
     * the ability to modify the dto or property bag.
     *
     * @param baseDTO
     * @param propertyBagDTO
     * @throws Exception
     */
    protected void preMgrFindByPrimaryKey(T baseDTO, PropertyBagDTO propertyBagDTO) throws Exception {
    }

    protected T mgrFindByPrimaryKey(T baseDTO, List<Class<? extends BaseDTO>> childClassDTOs) throws Exception {
        final String METHODNAME = "mgrFindByPrimaryKey ";
        long timeNow = System.nanoTime();
        try {
            // Find the parent via the primary key
            PropertyBagDTO newPropertyBagDTO = getNewPropertyBagDTO();
            // add parameter class list to child dto list
            newPropertyBagDTO.getChildClassDTOs().addAll(childClassDTOs);
            // call to client override
            preMgrFindByPrimaryKey(baseDTO, newPropertyBagDTO);
            T result = getGeneralMGR().findByPrimaryKey(baseDTO, getSessionDTO(), newPropertyBagDTO);
            return result;
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, timeNow);                                                                            
        }
    }

    public void refresh() {
        setParentDTO(refresh(getParentDTO()));
    }

    public T refresh(T dto) {
        final String METHODNAME = "refresh ";
        logger.logBegin(METHODNAME);
        T result = null;
        try {
            // By default use the searchChildClassDTOs
            List<Class<? extends BaseDTO>> childClassDTOs = getSearchChildClassDTOs();

            // Do we have onRowSelect childClassDTOs defined?
            if (!getOnRowSelectChildClassDTOs().isEmpty()) {
                childClassDTOs = getOnRowSelectChildClassDTOs();
            }

            // Get the original UUID
            UUID uuid = dto.getUuid();
            if (logger.isDebugEnabled()) {
                logger.info(METHODNAME, "primaryKey=", dto.getPrimaryKey(), " uuid=", uuid,
                        " getChildDTOMap().size()=", dto.getChildDTOMap().size());
            }

            // Find the parent via the primary key
            result = mgrFindByPrimaryKey(dto, childClassDTOs);

            // Set the original UUID
            result.setUuid(uuid);

            // Update the datatable
            getDataTableMGR().addOrUpdateDTO(result);
        } catch (Exception e) {
            onExceptionMain(SourceMethod.refresh, e);

        } finally {
            logger.logEnd(METHODNAME);
        }
        return result;
    }

    protected boolean isRowSelectByDataTable(SelectEvent selectEvent) {
        final String METHODNAME = "isRowSelectByDataTable ";
        boolean rowSelectByDataTable = false;

        // If caller is the datatable, do not call update
        if (selectEvent != null && selectEvent.getComponent() != null) {
            if (selectEvent.getComponent() instanceof DataTable) {
                rowSelectByDataTable = true;
            }
        }
        logger.debug(METHODNAME, "rowSelectByDataTable=", rowSelectByDataTable);
        return rowSelectByDataTable;
    }

    protected void logSelectEvent(SelectEvent selectEvent) {
        final String METHODNAME = "logSelectEvent ";
        logger.logBegin(METHODNAME);
        try {
            if (logger.isDebugEnabled()) {
                if (selectEvent != null && selectEvent.getObject() != null) {
                    logger.debug(METHODNAME, "selectEvent.getObject().getClass().getSimpleName()=", selectEvent.getObject().getClass().getSimpleName());
                    logger.debug(METHODNAME, "selectEvent.getClass()=", selectEvent.getClass());
                    if (selectEvent.getComponent() != null) {
                        logger.debug(METHODNAME, "selectEvent.getComponent()=", selectEvent.getComponent());
                        logger.debug(METHODNAME, "selectEvent.getComponent().getClass().getSimpleName()=", selectEvent.getComponent().getClass().getSimpleName());
                        logger.debug(METHODNAME, "selectEvent.getComponent().getClientId()=", selectEvent.getComponent().getClientId());
                    }
                } else {
                    logger.debug(METHODNAME, "selectEvent=", selectEvent);
                    logger.debug(METHODNAME, "selectEvent.getObject()=", selectEvent != null ? selectEvent.getObject() : null);
                }
                if (getDtoClassType() != null) {
                    logger.debug(METHODNAME, "getDtoClassType().getSimpleName()=", getDtoClassType().getSimpleName());
                }
            }
        } finally {
            logger.logEnd(METHODNAME);
        }

    }

    public void onRowSelectMain(SelectEvent selectEvent) {
        final String METHODNAME = "onRowSelectMain ";
        logger.logBegin(METHODNAME);

        // onRowSelectMain triggered by the dataTable ?
        boolean rowSelectByDataTable = isRowSelectByDataTable(selectEvent);

        try {
            //logSelectEvent(selectEvent);
            if (selectEvent != null) {
                if (selectEvent.getObject() != null) {
                    // Is the class in the event equal to the class that this is typed for?
                    if (getDtoClassType() == selectEvent.getObject().getClass()) {
                        // Get the BaseDTO
                        T baseDTO = (T) selectEvent.getObject();

                        // If the row is selected get the parent (defaults to true)
                        if (!isChild() && isOnRowSelectFindParent()) {
                            baseDTO = refresh(baseDTO);
                        }

                        // Set the parent reference
                        setParentDTO(baseDTO);

                        // Track the original parent ?
                        if (!isChild() && isTrackOrigParentDTO()) {
                            setOrigParentDTO(DeepCopy.copy(getParentDTO()));
                        }
                    }
                } else {
                    logger.warn(METHODNAME, "selectEvent.getObject() is null!");
                }
            } else {
                logger.warn(METHODNAME, "selectEvent is null!");
            }

            // Call descendant level
            onRowSelect(selectEvent);

            // Call Tab Lazy Load Services
            tabService.onRowSelect(selectEvent);

            // TODO: move the following into the getDtoClassType() == selectEvent.getObject().getClass() if fork above
//            evaluateGotoPreviousRecordEnabled();
//            evaluateGotoNextRecordEnabled();
            setGotoPreviousRecordEnabled(GotoRecordService.evaluateGotoPreviousRecordEnabled(dataAccessInterface, parentDTO));
            setGotoNextRecordEnabled(GotoRecordService.evaluateGotoNextRecordEnabled(dataAccessInterface, parentDTO));
        } catch (Exception e) {
            onExceptionMain(SourceMethod.onRowSelectMain, e);
        } finally {
            // true indicates that the dataTable took care of the update
//            logger.info(METHODNAME, "rowSelectByDataTable=", rowSelectByDataTable);
            if (!rowSelectByDataTable) {
                setRowSelectUpdateIds();
            }
            logger.logEnd(METHODNAME);
        }
    }

//    public void onDeleteSelectMain(T dto) {
//        setParentDTO(dto);
//        setDeleteConfirmUpdateIds();
//    }
    public void onChangeMain(String fieldName) {
        final String METHODNAME = "onChangeMain ";
        logger.logBegin(METHODNAME);
        try {
            onChange(fieldName);
        } catch (Exception e) {
            onExceptionMain(SourceMethod.onChangeMain, e);

        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    protected void onChange(String fieldName) throws Exception {
    }

    public void changeTab(Tab tab) {
        onTabChangeMain(new TabChangeEvent(getTabView(), new AjaxBehavior(), tab));
    }

    public void onTabChangeMain(TabChangeEvent tabChangeEvent) {
        final String METHODNAME = "onTabChangeMain ";
        try {
            if (this.getTabView() != null) {
                tabService.onTabChange(tabChangeEvent);
                secureForm();
            }
        } catch (Exception e) {
            onExceptionMain(SourceMethod.onTabChangeMain, e);

        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    public void onTabChange(TabChangeEvent tabChangeEvent) throws Exception {
    }

    public void onItemSelectMain(SelectEvent selectEvent) {
        final String METHODNAME = "onItemSelectMain ";
        logger.logBegin(METHODNAME);
        try {
            onItemSelect(selectEvent);
        } catch (Exception e) {
            onExceptionMain(SourceMethod.onItemSelectMain, e);

        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    protected void onItemSelect(SelectEvent selectEvent) throws Exception {
    }

    protected List<T> mgrFindByQueryList(T searchCriteriaDTO, String queryClass, List<Class<? extends BaseDTO>> childClassDTOs) throws Exception {
        final String METHODNAME = "mgrFindByQueryList ";
        long startTime = System.nanoTime();
        try {
            // Find the search results
            PropertyBagDTO newPropertyBagDTO = getNewPropertyBagDTO();
            newPropertyBagDTO.setQueryClass(queryClass);
            // add parameter class list to child dto list
            newPropertyBagDTO.getChildClassDTOs().addAll(childClassDTOs);
            logger.debug(METHODNAME, "propertyMap: ", newPropertyBagDTO.getPropertyMap());
            List<T> results = getGeneralMGR().findByQueryList(searchCriteriaDTO, getSessionDTO(), newPropertyBagDTO);
            logger.debug(METHODNAME, "results: ", results);
            return results;
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, startTime);                                                                            
        }
    }

    protected void search(String queryClass) throws Exception {
        DataAccessService.retrieve(dataAccessInterface, this, queryClass, SourceOperation.Search);
    }

    /*
     * Parent object search form management
     */
    public void searchMain(ActionEvent actionEvent) {
        final String METHODNAME = "searchMain ";
        long startTime = System.nanoTime();
        logger.logBegin(METHODNAME);
        if (isChild()) {
            throw new UnsupportedOperationException("Not supported.");
        }
        try {
//            resetSearch(actionEvent);
            // Call descendant level preSearch
            preSearch(actionEvent);

            UIComponent command = actionEvent.getComponent();
            String queryClass = command.getId();
            prePostOperation(queryClass, null, PrePost.PreSearch, false);
            logger.debug(METHODNAME, "queryClass=", queryClass);
            logger.debug(METHODNAME, "command.getClientId()=", command.getClientId());

            if (StringUtils.isEmpty(queryClass)) {
                throw new CatException("queryClass was not specified, did you forget to initialize the Id on the component?");
            } else {
                search(queryClass);
//                // Not in Lazy Mode
//                if (!isLazy()) {
//                    // Find the search results
//                    setDataTableMGR(mgrFindByQueryList(getSearchCriteriaDTO(), queryClass, getSearchChildClassDTOs()));
//                } else {
//                    initLazyLoader(queryClass);
//                }
                if (!isLazy() && getDataTableMGR().getRowCount() == 0) {
                    messageMGR.displayError("noResults");
                }
            }

            // Reset the paginator
            resetPaginator();

            // Call descendant level postSearch
            postSearch(actionEvent);
            prePostOperation(queryClass, null, PrePost.PostSearch, getDataTableMGR().getRowCount() > 0);
        } catch (NotFoundException e) {
            messageMGR.displayError("noResults");
            onException(SourceMethod.searchMain.toString(), e);
        } catch (Exception e) {
            onExceptionMain(SourceMethod.searchMain, e);
        } finally {
            setSearchUpdateIds();
            logger.logDuration(LogLevel.DEBUG, METHODNAME, startTime);                                                                            
            logger.logEnd(METHODNAME);
        }
    }

    public void onExceptionMain(SourceMethod sourceMethod, Exception e) {
        // For now only call when the sourceMethod is saveMain
        if (sourceMethod == SourceMethod.saveMain) {
            onValidationException(getEditForm(), sourceMethod, e);
        }
        onExceptionMain(sourceMethod.toString(), e);
    }

    public void onExceptionMain(String sourceMethod, Exception e) {
        onExceptionMain(sourceMethod, e, false);
    }

    public void onExceptionMain(String sourceMethod, Exception e, boolean outsideLifeCycle) {
        DefaultExceptionHandler.handleException(e, getClass(), outsideLifeCycle);
        onException(sourceMethod, e);
    }

    protected void onValidationException(UIForm uiForm, SourceMethod sourceMethod, Exception e) {
        final String METHODNAME = "onValidationException ";
        if (e instanceof ValidationException) {
            ValidationException validationException = (ValidationException) e;
            List<BrokenRule> brokenRules = validationException.getBrokenRules();

            // Collect all the Input Ids
            List<String> inputIds = new ArrayList<String>();
            for (BrokenRule brokenRule : brokenRules) {
                String[] inputs = brokenRule.getInputIds();
                if (inputs != null) {
                    inputIds.addAll(Arrays.asList(inputs));
                }
            }
            logger.info(METHODNAME, "inputIds=", inputIds);
            if (!inputIds.isEmpty()) {
                validateInputIds(uiForm, inputIds);
            }
        }
    }

    private void validateInputIds(UIComponent uiComponent, List<String> inputIds) {
        final String METHODNAME = "validateInputIds ";
        Iterator<UIComponent> facetsAndChildren = uiComponent.getFacetsAndChildren();
        if (facetsAndChildren != null) {
            while (facetsAndChildren.hasNext()) {
                UIComponent nextUIComponent = facetsAndChildren.next();
                if (logger.isDebugEnabled()) {
                    logger.info(METHODNAME, "getClientId()= ", nextUIComponent.getClientId(),
                            " getSimpleName()= ", nextUIComponent.getClass().getCanonicalName(),
                            " getId()= " + nextUIComponent.getClientId());
                }
                if (nextUIComponent instanceof EditableValueHolder) {
                    EditableValueHolder editableValueHolder = (EditableValueHolder) nextUIComponent;
//                                editableValueHolder.resetValue();
//                                editableValueHolder.setValid(true);
//                                editableValueHolder.setSubmittedValue(null);
//                                editableValueHolder.setValue(null);
                    if (logger.isDebugEnabled()) {
                        logger.info(METHODNAME,
                                "nextUIComponent FOUND ", nextUIComponent.getClass().getCanonicalName(),
                                " getClientId()= ", nextUIComponent.getClientId(),
                                " getValue()=", editableValueHolder.getValue(),
                                " getLocalValue()= ", editableValueHolder.getLocalValue(),
                                " getSubmittedValue()= ", editableValueHolder.getSubmittedValue(),
                                " isValid()= ", editableValueHolder.isValid());
                    }
                    for (String inputId : inputIds) {
                        if (nextUIComponent.getClientId().endsWith(inputId)) {
                            logger.debug(METHODNAME,
                                    "nextUIComponent FOUND ", nextUIComponent.getClass().getCanonicalName(),
                                    " getClientId()= ", nextUIComponent.getClientId(),
                                    " getValue()=", editableValueHolder.getValue(),
                                    " getLocalValue()= ", editableValueHolder.getLocalValue(),
                                    " getSubmittedValue()= ", editableValueHolder.getSubmittedValue(),
                                    " isValid()= ", editableValueHolder.isValid());

                            editableValueHolder.setValid(false);
                            break;
                        }
                    }
                } else {
//                    logger.debug(METHODNAME,
//                            "uiComponent not a EditableValueHolder: ", nextUIComponent.getClass().getCanonicalName(),
//                            " getClientId()= ", nextUIComponent.getClientId());
                }
                validateInputIds(nextUIComponent, inputIds);
            }
        }
    }

    protected void onException(String sourceMethod, Exception e) {
    }

    /**
     * Gets the parent manager list that this manager is descendant from.
     *
     * @param mgrList
     * @throws Exception
     */
    public void getAscendantList(List<BaseModule> mgrList) throws Exception {
        final String METHODNAME = "getAscendantList ";

        BaseModule parentMgr = this.getParentMGR();

        if (parentMgr != null) {
            BaseDTO selectedParentDto = parentMgr.getParentDTO();
            if (selectedParentDto == null) {
                throw new UnsupportedOperationException(METHODNAME + "parent manager parent dto was null: " + parentMgr.getClass());
            }
            mgrList.add(parentMgr);
            parentMgr.getAscendantList(mgrList);
        }
    }

    protected T mgrCustomSave(T parentDTO, PropertyBagDTO propertyBagDTO) throws Exception {
        final String METHODNAME = "mgrCustomSave ";
        long timeNow = System.nanoTime();
        try {
            return getGeneralMGR().customSave(parentDTO, getSessionDTO(), propertyBagDTO);
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, timeNow);                                                                            
        }
    }

    protected T mgrSave(T parentDTO) throws Exception {
        final String METHODNAME = "mgrSave ";
        long timeNow = System.nanoTime();
        try {
            return getGeneralMGR().save(parentDTO, getSessionDTO(), getNewPropertyBagDTO());
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, timeNow);                                                                            
        }
    }

    public boolean saveMain() throws Exception {
        return saveMain(null);
    }

    public boolean saveMain(ActionEvent actionEvent) {
        final String METHODNAME = "saveMain ";
        logger.info(METHODNAME);

        //
        // Delegate saving to the tabLazyLoadService, it handles callback of saveMain on each module 
        // including the current module
        //
        // FYI: When any modules are registered you have register the current module to some tab component
        // If you dont, it will register the module to the tab with index 0 which is typically the parent tab, 
        // there is a message in the log to hopefully give you guidance
        //
        // If you register the module with multiple tabs, the logic will check if the DTO has changed and save it
        // You may want to do this to separate DTO fields across tab, say for example required fields and tab 0 and
        // optional fields on Tab 3.
        //
        // Some scenarios in 
        // Tier 1 (A search results page which leads to an editForm for the record in the search results)
        // *Tab 0 contains the parentDTO plus 1 dtoInlineDataTable and 1 dtoDataTable (non-inline)
        //  If the user clicks apply/save and the DTO in tab 0 has changed it will be saved together 
        //  with any changes made in the dtoInlineDataTable (in a separate calls)
        // *Tab 1 contains a dtoDataTable.
        //  If the user clicks apply/save nothing will be called because dtoDataTables are managed via a popup
        // *Tab 2 contains a DTO (a sibling to the ParentDTO) and 2 dtoInlineDataTables
        //  If the user clicks apply/save and the DTO in tab 2 has changed it will be saved together
        //  with any changes made in the dtoInlineDataTables (in a separate calls)
        // *Tab 3 contains some of the optional parentDTO fields and dtoInlineDataTable and a dtoDataTable
        //  If the user clicks apply/save and if the parentDTO in tab 3 has changed it will be saved together
        //  with any changes made in the dtoInlineDataTables (in a separate calls). the dtoDataTable will not be 
        //  saved as it is managed by a separate popup.
        //
        // Tier 2 ( Tier 1 Tab 1 contains a dtoDataTable that has a popup ) all of Tier 1 patterns apply
        // If Tier 2 contains a simple popup supporting New/Edit of the DTO selected from 
        // the Tier 1 Tab 1 dtoDataTable and it has no subordinate modules then there is no need to register the
        // module itself and saveMain will be called for that module
        //
        boolean saved = false;
        try {
            saved = tabService.save(actionEvent);
        } catch (Exception e) {
            onExceptionMain(SourceMethod.saveMain, e);
        } finally {
            if (saved) {
                UtilityMGR.setCallbackParam("saved", true);
            }
            logger.info(METHODNAME, "saved=", saved);

            PostRenderService.postRenderUIComponent(this, getEditForm());
            secureForm();
        }
        return saved;
    }

    public boolean saveMain(ActionEvent actionEvent, boolean displayMessage) {
        final String METHODNAME = "saveMain ";

        long start = System.nanoTime();
        boolean saved = false;
        String javaScriptToExecute = null;
        try {
            // Get the ParentDTO
            T localParentDTO = getParentDTO();
            // Is it null?, it shouldn't be!
            if (localParentDTO != null) {
                preSave(actionEvent);
                prePostOperation(null, localParentDTO, PrePost.PreSave, false);
                DTOState prevDTOState = localParentDTO.getOperationDTOState();

                // if there is a bug and the dto is saved with a delete state on it the deletes succeeds
                // but then the return result is null which causes an NPE - this should not happen
                if (prevDTOState == DTOState.DELETED) {
                    throw new CatException(METHODNAME + "deleted dto was submitted to saveMain!");
                }

                if (logger.isDebugEnabled()) {
                    logger.info("prevDTOState: ", prevDTOState);
                    logger.info(METHODNAME, "isSaveOnBaseParentMGR()=", isSaveOnBaseParentMGR());
                }

                logger.info(METHODNAME, "saveImmediately=", saveImmediately);

                if (isChild() && !saveImmediately) {

                    // Call Save on Base Parent MGR ?
                    if (parentMGR != null
                            && isSaveOnBaseParentMGR()
                            && parentMGR.getParentDTO() != null
                            && parentMGR.getParentDTO().getDTOState() == DTOState.UNSET) {

                        List<BaseModule> mgrList = new LinkedList<BaseModule>();
                        mgrList.add(this);
                        getAscendantList(mgrList);
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
                                    baseModule.saveOnBaseParent(baseDTO);
                                    break;
                                }
                            }

                        }

//                        String editFormId = this.getEditFormId();
//                        javaScriptToExecute = String.format("resetChangedState('%s')", editFormId);
                    } else {
                        if (isSaveOnBaseParentMGR()) {
                            messageMGR.displayError("saveOnBaseMGRWithParentNotUnset");
                            logger.warn("isSaveOnBaseParentMGR() is on but parentMGR DTO is not in an unset state!");
                        } else {
                            logger.warn(METHODNAME, "!isSaveOnBaseParentMGR(): ", !isSaveOnBaseParentMGR());
                        }
                        localParentDTO = getDataTableMGR().addOrUpdateDTO(localParentDTO);
                        setParentDTO(localParentDTO);
                        if (getParentMGR() != null) {
                            if (localParentDTO.getDTOState() != DTOState.UNSET) {
                                javaScriptToExecute = getJSSetFormChanged(getParentMGR());
                            }
                            //javaScriptToExecute = String.format("onChange('%s')", getParentMGR().getEditFormId());
                        } else {
                            throw new CatException(logger.error("getParentMGR() was null - did you forget to register the child manager on the parent manager?"));
                        }
                    }

                } else {
                    UUID uuid = localParentDTO.getUuid();
                    logger.info(METHODNAME, "uuid=", uuid.toString());

                    if (prevDTOState != DTOState.UNSET) {
                        if (isChild() && saveImmediately) {
                            if (localParentDTO.isNew()) {
                                Object primaryKey = getParentMGR().getParentDTO().getPrimaryKey();
                                localParentDTO.setForeignKey(getParentMGR().getDtoClassType(), primaryKey);
                            }
                        }
                        localParentDTO = mgrSave(localParentDTO);
                        localParentDTO.setUuid(uuid);
                        localParentDTO = getDataTableMGR().addOrUpdateDTO(localParentDTO);
                        setParentDTO(localParentDTO);
                        setLastDTOState(prevDTOState);
                    }
                    markAssociatedDTOListDirty();

                    // Is there a call back registered?
                    onSavedCallBack(getParentDTO());
//                    if (onSavedCallback != null) {
//                        onSavedCallback.onSaved(getParentDTO());
//                    }
                }

                logger.info("displayMessage: ", displayMessage);
                if (displayMessage) {
                    // Display Message
                    displayMessage(getParentDTO(), prevDTOState, SourceMethod.saveMain);
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

                postSave(actionEvent);
                saved = true;
                prePostOperation(null, localParentDTO, PrePost.PostSave, saved);

            } else {
                throw new CatException("The saved row was null!");
            }
        } catch (Exception e) {
            setLoadStatus(LoadStatus.Stop);
            onExceptionMain(SourceMethod.saveMain, e);
            
            // Reset state on exception (after save Datatable sort stops working)
            setLoadStatus(LoadStatus.StopClear);
            if (isDataTableExists()) {
                setUpdateIds(METHODNAME, getDataTableUpdateId());
            }            
            
        } finally {
            logger.info(METHODNAME, "isDataTableExists()=", isDataTableExists());
            if (saved) {
                if (isDataTableExists()) {
                    if (lastDTOState != null && (lastDTOState == DTOState.NEW || lastDTOState == DTOState.NEWMODIFIED)) {
                        boolean matched = ObjectUtils.objectToBoolean(getParentDTO().getQueryMap().get(CoreConstants.ADDORUPDATEMATCHED));
                        if (!matched) {
                            getDataTableMGR().setRowCount(getDataTableMGR().getRowCount() + 1);
                        }
                    }
                    setLoadStatus(LoadStatus.StopClear);
                    setUpdateIds(METHODNAME, getDataTableUpdateId());
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

    public void onSavedCallBack(T dto) {
        final String METHODNAME = "onSavedCallBack ";
        logger.info(METHODNAME, "onSavedCallback=", onSavedCallback);
        if (onSavedCallback != null) {
            onSavedCallback.onSaved(dto);
        }
    }

    public void mgrDelete(T parentDTO) throws Exception {
        mgrSave(parentDTO);
    }

    public boolean deleteMain(boolean cascade) {
        return deleteMain(cascade, true);
    }

    /**
     * Object deletion management
     *
     * @param cascade
     * @param dto
     */
    public void deleteMain(boolean cascade, BaseDTO dto) {
        final String METHODNAME = "deleteMain ";
        logger.info(METHODNAME);
        if (getDtoClassType() == dto.getClass()) {
            logger.info(METHODNAME, "calling deleteMain");
            setParentDTO((T) dto);
            deleteMain(cascade);
        } else {
            logger.info(METHODNAME, "calling inline deleteMain");
            getInlineDataTableService().delete(cascade, dto);
        }
    }

    public boolean deleteMain(boolean cascade, boolean displayMessage) {
        boolean refresh = true;
        if (isChild() && parentDTO != null) {
            refresh = !parentDTO.isNew();
        }
        return deleteMain(cascade, displayMessage, refresh);

    }

    /**
     * Object deletion management
     *
     * @param cascade
     * @param displayMessage
     * @param refresh
     * @return
     */
    public boolean deleteMain(boolean cascade, boolean displayMessage, boolean refresh) {
        final String METHODNAME = "deleteMain ";
        logger.logBegin(METHODNAME);
        boolean deleted = false;
        String javaScriptToExecute = null;
        String messageKey = getDeletedMessageKey();
        try {
            logger.info(METHODNAME, "refresh=", refresh);
            // Get the ParentDTO
            T localParentDTO;
            if (refresh) {
                localParentDTO = refresh(getParentDTO());
                dataTableMGR.addOrUpdateDTO(localParentDTO);
                parentDTO = localParentDTO;
            } else {
                localParentDTO = getParentDTO();
            }

            // Is it null?, it shouldn't be!
            if (localParentDTO != null) {

                if (localParentDTO.isNew()) {
                    messageKey = "deleted";
                }

                if (logger.isDebugEnabled()) {
                    logger.debug(METHODNAME, "localParentDTO.isNew()=", localParentDTO.isNew());
                    logger.debug(METHODNAME, "messageKey=", messageKey);
                    logger.debug("Deleting DTO primary key: ", localParentDTO.getPrimaryKey());
                }

                prePostOperation(null, localParentDTO, PrePost.PreDelete, false);
                preDelete();
                if (isChild() && !saveImmediately) {
                    // To Do: Add logic to get the Deleted DTO from the datatable because the parentDTO
                    // is not the DTO that is deleted. Pass it to the preDelete
                    deleted = dataTableMGR.delete(parentDTO, cascade);
                    logger.debug("displayMessage: ", displayMessage);
                    // Added javascript call here, removed from delete button
                    javaScriptToExecute = getJSSetFormChanged(getParentMGR());
//                    javaScriptToExecute = String.format("onChange('%s')", getParentMGR().getEditFormId());
                    if (parentMGR != null
                            && !localParentDTO.isNew()
                            && isSaveDeleteOnBaseParentMgr()
                            && parentMGR.getParentDTO() != null
                            && parentMGR.getParentDTO().getDTOState() != DTOState.UNSET) {
                        messageKey = "childDeleted";
                    }
                    if (displayMessage) {
                        messageMGR.displayInfo(messageKey, dataAccessInterface.getMessageArgs(getParentDTO(), SourceMethod.deleteMain));
                    }
                    if (!localParentDTO.isNew() && isSaveDeleteOnBaseParentMgr()) {
                        localParentDTO.delete(cascade);
                        saveMain(UtilityMGR.getEmulatedActionEvent(), false);
                    }
                    // To Do: Add logic to get the Deleted DTO from the datatable because the parentDTO
                    // is not the DTO that is deleted. Pass it to the postDelete
                } else {
                    localParentDTO.delete(cascade);
                    mgrDelete(localParentDTO);
//                    if (saveImmediately) {
//                        if (isChild()) {
//                            // As the delete java script tracks the parent form
//                            String editFormId = getParentMGR().getEditFormId();
//                            javaScriptToExecute = String.format("resetChangedState('%s')", editFormId);
//                        }
//                    }
                    logger.info(METHODNAME, "refresh=", refresh);

                    // Refresh was changed above (dataTableMGR.addOrUpdate) which 
                    // no longer requires a page load on a lazy datatable
//                    if (isLazy()) {
//                        dataTableMGR.setWrappedData(
//                                dataTableMGR.load(
//                                        dataTableMGR.getRowOffset(),
//                                        dataTableMGR.getPageSize(),
//                                        dataTableMGR.getSortField(),
//                                        dataTableMGR.getSortOrder(),
//                                        dataTableMGR.getFilters()));
//                    } else {
                    dataTableMGR.remove(localParentDTO);
//                    }
                    logger.debug("displayMessage: ", displayMessage);
                    if (displayMessage) {
                        messageMGR.displayInfo(messageKey, dataAccessInterface.getMessageArgs(getParentDTO(), SourceMethod.deleteMain));
                    }
                    deleted = true;
                    DataAccessService.navigatePage(dataAccessInterface, null);
                    markAssociatedDTOListDirty();
                }
                postDelete();
                prePostOperation(null, localParentDTO, PrePost.PostDelete, deleted);
                setParentDTO(null);
            } else {
                throw new CatException("The parentDTO was not selected!");
            }
        } catch (Exception e) {
            // try to delete something and there is an mts error then the dto is left in the delete state in the data table manager
            // if you then open it and click save - you are trying to save a dto with a delete state on it - mts returns no dto when this happens
            // need to reset its state...
            DTOUtils.unsetDTOState(parentDTO);
            setLoadStatus(LoadStatus.Stop);
            onExceptionMain(SourceMethod.deleteMain, e);
            
            // Reset state on exception (after delete Datatable sort stops working)
            setLoadStatus(LoadStatus.StopClear);
            if (isDataTableExists()) {
                setUpdateIds(METHODNAME, getDataTableUpdateId());
            }
        } finally {
//            setDeleteUpdateIds();
            if (!StringUtils.isEmpty(javaScriptToExecute)) {
                RequestContext.getCurrentInstance().execute(javaScriptToExecute);
            }
            UtilityMGR.setCallbackParam("deleted", deleted);
            if (deleted) {
                setLastDTOState(DTOState.DELETED);
                setLoadStatus(LoadStatus.StopClear);

                getDataTableMGR().setRowCount(getDataTableMGR().getRowCount() - 1);
                if (isDataTableExists()) {
                    setUpdateIds(METHODNAME, getDataTableUpdateId());
                }
            }
            logger.logEnd(METHODNAME);
        }
        return deleted;
    }

    public void onGotoRecordMain(ActionEvent actionEvent) throws Exception {
        final String METHODNAME = "onGotoRecordMain";
        Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String recordPosition = null;
        if (map != null) {
            recordPosition = (String) map.get("recordPosition");
        }
        // Used ActionEvent component Id
        if (StringUtils.isEmpty(recordPosition)) {
            recordPosition = actionEvent.getComponent().getId();
        }
        if (!StringUtils.isEmpty(recordPosition)) {
            if ("next".equalsIgnoreCase(recordPosition)) {
                GotoRecordService.gotoRecord(RecordPosition.Next, dataAccessInterface, parentDTO);
                onGotoRecord(actionEvent);
            } else if ("previous".equalsIgnoreCase(recordPosition)) {
                GotoRecordService.gotoRecord(RecordPosition.Previous, dataAccessInterface, parentDTO);
                onGotoRecord(actionEvent);
            } else if ("first".equalsIgnoreCase(recordPosition)) {
                GotoRecordService.gotoRecord(RecordPosition.First, dataAccessInterface, parentDTO);
                onGotoRecord(actionEvent);
            } else if ("last".equalsIgnoreCase(recordPosition)) {
                GotoRecordService.gotoRecord(RecordPosition.Last, dataAccessInterface, parentDTO);
                onGotoRecord(actionEvent);
            } else if ("current".equalsIgnoreCase(recordPosition)) {
                GotoRecordService.gotoRecord(RecordPosition.Current, dataAccessInterface, parentDTO);
                onGotoRecord(actionEvent);
                
            } else {
                logger.error(METHODNAME, "unsupported record position: ", recordPosition);
            }

        }
    }

    protected void onGotoRecord(ActionEvent actionEvent) throws Exception {
    }

    /**
     *
     * @return
     */
    public boolean isGotoPreviousRecordEnabled() {
        return gotoPreviousRecordEnabled;
    }

    public void setGotoPreviousRecordEnabled(boolean gotoPreviousRecordEnabled) {
        this.gotoPreviousRecordEnabled = gotoPreviousRecordEnabled;
    }

    public boolean isGotoNextRecordEnabled() {
        return gotoNextRecordEnabled;
    }

    public void setGotoNextRecordEnabled(boolean gotoNextRecordEnabled) {
        this.gotoNextRecordEnabled = gotoNextRecordEnabled;
    }

    public String getAddedMessageKey() {
        return addedMessageKey;
    }

    public void setAddedMessageKey(String addedMessageKey) {
        this.addedMessageKey = addedMessageKey;
    }

    public String getUpdatedMessageKey() {
        return updatedMessageKey;
    }

    public void setUpdatedMessageKey(String updatedMessageKey) {
        this.updatedMessageKey = updatedMessageKey;
    }

    public String getMatchedMessageKey() {
        return matchedMessageKey;
    }

    public void setMatchedMessageKey(String matchedMessageKey) {
        this.matchedMessageKey = matchedMessageKey;
    }
    
    

    public String getUnchangedMessageKey() {
        return unchangedMessageKey;
    }

    public void setUnchangedMessageKey(String unchangedMessageKey) {
        this.unchangedMessageKey = unchangedMessageKey;
    }

    public String getDeletedMessageKey() {
        return deletedMessageKey;
    }

    public void setDeletedMessageKey(String deletedMessageKey) {
        this.deletedMessageKey = deletedMessageKey;
    }

    public Mts getMts() {
        return mts;
    }

    public GeneralMGRClient getGeneralMGR() throws MtsException {
        GeneralMGRClient generalMGR = null;
        try {
            generalMGR = mts.getGeneralMGR();
        } catch (Exception e) {
            onExceptionMain(SourceMethod.getGeneralMGR, e);
        }
        return generalMGR;
    }

    public String getDefaultOperationName() {
        return defaultOperationName;
    }

    public void setDefaultOperationName(String defaultOperationName) {
        this.defaultOperationName = defaultOperationName;
    }

    public Map<String, Object> getPropertyMap() {
        return propertyMap;
    }

    public PropertyBagDTO getNewPropertyBagDTO() {
        PropertyBagDTO propertyBag = new PropertyBagDTO();
        propertyBag.getPropertyMap().putAll(getPropertyMap());
        if (getDefaultOperationName() != null) {
            propertyBag.setOperationName(getDefaultOperationName());
        }
        // Add flag to inform BO that client is operating in save immediate mode
        propertyBag.getPropertyMap().put(CoreConstants.SAVEIMMEDIATELY, isSaveImmediately());
        return propertyBag;
    }

    public String getOnRowSelectUpdateIds() {
        final String METHODNAME = "getOnRowSelectUpdateIds ";
        return getFormEditUpdateId(true);
    }

    public void registerOnSavedCallback(OnSavedCallback onSavedCallback) {
        this.onSavedCallback = onSavedCallback;
    }

    protected <S> S mgrFindObjectByQuery(T baseDTO, String queryClass, Class<S> requiredType, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "mgrFindObjectByQuery ";
        S returnType = null;
        try {
            propertyBagDTO.setQueryClass(queryClass);
            returnType = getGeneralMGR().findObjectByQuery(baseDTO, this.getSessionDTO(), requiredType, propertyBagDTO);
        } catch (Exception e) {
            onExceptionMain(METHODNAME, e);
        }
        return returnType;
    }

    /**
     * Saves a child DTO on its base parent manager.
     *
     * @param childDTO
     * @throws Exception
     */
    private void saveOnBaseParent(T childDTO) throws Exception {
        final String METHODNAME = "saveOnBaseParent ";
        logger.logBegin(METHODNAME);
        BaseDTO baseParentDTO;
        try {

            if (childDTO != null) {
                // Get the ParentMGR
                BaseModule baseParentMGR = this.getParentMGR();
                if (baseParentMGR != null) {

                    baseParentDTO = baseParentMGR.getParentDTO();

                    if (baseParentDTO != null) {

                        logger.info(METHODNAME, "saving ", childDTO, " on ", baseParentDTO);

                        // save the tab index of the base parent mgr
                        Integer originalTabIndex = baseParentMGR.getTabView() != null ? baseParentMGR.getTabView().getActiveIndex() : null;

                        // save the child's original UUID
                        UUID childUuid = childDTO.getUuid();
//
//                        // add or update the child we are interested in saving
//                        // but first make sure it isn't there already by uuid
//                        // on mts validation errors - corrections and resubmits can cause the dto to get added to the child map again
////                        boolean alreadyAdded = false;
//                        for (List<BaseDTO> value : baseParentDTO.getChildDTOMap().values()) {
//                            for (BaseDTO item : value) {
//                                logger.info(METHODNAME, " childList item UUID: ", item.getUuid(), "; childDTO UUID: ", childUuid);
//                                logger.info(METHODNAME, " childList item PK: ", item.getPrimaryKey(), "; childDTO PK: ", childDTO.getPrimaryKey());
//                                if (item.getUuid().equals(childUuid)) {
////                                    alreadyAdded = true;
////                                    break;
//                                }
//                            }
//                        }
//                        logger.info(METHODNAME, "alreadyAdded ", alreadyAdded);
//                        if (!alreadyAdded) {
                        if (!childDTO.isDeleted()) {
                            baseParentDTO.addOrUpdateChildDTO(childDTO);
                        }
//                        }

                        if (logger.isDebugEnabled()) {
                            for (Entry<Class, List<BaseDTO>> entry : baseParentDTO.getChildDTOMap().entrySet()) {
                                logger.info(METHODNAME, "parent child entries for: ", entry.getKey().getCanonicalName());
                                for (BaseDTO item : entry.getValue()) {
                                    logger.info(METHODNAME, "    child: ", item, " - ", item.getDTOState());
                                }
                            }
                        }

                        // keep the base parent's baseParentUuid for restoration after save
                        UUID baseParentUuid = baseParentDTO.getUuid();

                        // save the parent with the child on it
                        try {
                            baseParentDTO = baseParentMGR.mgrSave(baseParentDTO);
                        } catch (Exception e) {
                            if (childDTO.isNew()) {
                                for (List<BaseDTO> value : baseParentDTO.getChildDTOMap().values()) {
                                    Iterator<BaseDTO> iterator = value.iterator();
                                    while (iterator.hasNext()) {
                                        BaseDTO item = iterator.next();
                                        if (item.getUuid().equals(childUuid)) {
                                            iterator.remove();
                                        }
                                    }
                                }
                            }
                            throw e;
                        }

                        // restore the parent's baseParentUuid
                        baseParentDTO.setUuid(baseParentUuid);

                        // now that the parent is saved we need to refresh this manager's parentDTO
                        // from the baseParentMGR's parentDTO's child DTOs...ugh
                        T matchedChildDTO = null;
                        for (T item : (List<T>) baseParentDTO.getChildrenDTOs(getChildQueryClass())) {
                            if (item.getUuid().equals(childUuid) || item.equals(childDTO)) {
                                matchedChildDTO = item;
                                break;
                            }
                        }

                        if (!childDTO.isDeleted() && matchedChildDTO == null) {
                            logger.warn(METHODNAME,
                                    "didn't find the child after the save on parent! if children are refreshed on a child add this can happen if the child is autoretrieve or force refreshed: ",
                                    baseParentDTO.getChildrenDTOs(getChildQueryClass()));
                        }

                        if (matchedChildDTO != null) {
                            logger.info(METHODNAME, "got matchedChildDTO: ", matchedChildDTO);

                            // restore the child's original UUID
                            matchedChildDTO.setUuid(childUuid);

                            // set the current mgr parent dto
                            setParentDTO(matchedChildDTO);
                        }

                        // Refresh Selection, emulates a row selection from the search form
                        logger.debug(METHODNAME, "refreshSelection: ", refreshSelection);
                        if (refreshSelection) {
                            baseParentMGR.onRowSelectMain(UtilityMGR.getEmulatedSelectEvent(baseParentDTO));
                        } else {
                            // Set the Parent
                            baseParentMGR.setParentDTO(baseParentDTO);
                            if (baseParentMGR.isTrackOrigParentDTO()) {
                                baseParentMGR.setOrigParentDTO(DeepCopy.copy(baseParentMGR.getParentDTO()));
                            }
                            baseParentMGR.onRowSelect(UtilityMGR.getEmulatedSelectEvent(baseParentDTO));
                        }

                        // set the original tab index in the parent mgr
                        if (originalTabIndex != null) {
                            baseParentMGR.getTabView().setActiveIndex(originalTabIndex);
                        }

                        // dirty parent manager's associated lists
                        baseParentMGR.markAssociatedDTOListDirty();

                        // call parent manager's postSave
                        baseParentMGR.postSave(UtilityMGR.getEmulatedActionEvent());

                    } else {
                        logger.error(METHODNAME, "localParentDTO is null!");
                    }

                } else {
                    logger.error(METHODNAME, "baseParentMGR is null!");
                }
            } else {
                logger.error(METHODNAME, "childDTO is null!");
            }
        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    public <S extends BaseDTO> S getQueryDTO(Class<S> dtoClass)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException,
            CatException {
        return (S) getSearchCriteriaDTO();
    }

    public DTOState getLastDTOState() {
        return lastDTOState;
    }

    public void setLastDTOState(DTOState lastDTOState) {
        this.lastDTOState = lastDTOState;
    }

    public void displayMessage() {
        String METHODNAME = "displayMessage ";
        for (Message message : queuedMessages) {
            messageMGR.displayMessage(message);
        }
        this.autoRun = false;
        queuedMessages = new ArrayList<Message>();
    }

    public boolean getAutoRun() {
        return this.autoRun;
    }

    public void setAutoRun(boolean autoRun) {
        this.autoRun = autoRun;
    }

    public void queueMessage(Message message) {
        queuedMessages.add(message);
        setAutoRun(true);
    }

//
//    public List<T> getFilteredDTOs() {
////        logger.info("getFilteredDTOs filteredDTOs: ", filteredDTOs);
////        logger.info("getFilteredDTOs getDataTableMGR().getDtoList(): ", getDataTableMGR().getDtoList());
//        if (filteredDTOs == null) {
//            return getDataTableMGR().getDtoList();
//        } else {
//            return filteredDTOs;
//        }
//    }
//
//    public void setFilteredDTOs(List<T> filteredDTOs) {
////        logger.info("setFilteredDTOS: ", filteredDTOs);
//        this.filteredDTOs = filteredDTOs;
//    }
//
//    /**
//     * Get the value of globalFilterEnabled
//     *
//     * @return the value of globalFilterEnabled
//     */
//    public boolean isGlobalFilterEnabled() {
//        return globalFilterEnabled;
//    }
//
//    /**
//     * Set the value of globalFilterEnabled
//     *
//     * @param globalFilterEnabled new value of globalFilterEnabled
//     */
//    public void setGlobalFilterEnabled(boolean globalFilterEnabled) {
//        this.globalFilterEnabled = globalFilterEnabled;
//    }
    public void triggerJSSetFormChanged() {
        triggerJSSetFormChanged(true);
    }

    public void triggerJSSetFormChanged(boolean changed) {
        RequestContext.getCurrentInstance().execute(getJSSetFormChanged(getFormEditUpdateId(), changed));
    }

    public String getJSSetFormChanged() {
        return getJSSetFormChanged(this);
    }

    private String getJSSetFormChanged(BaseModule baseModule) {
        return getJSSetFormChanged(baseModule.getFormEditUpdateId(), true);
    }

    public String getJSSetFormChanged(String formEditUpdateId, boolean changed) {
        final String METHODNAME = "getJSSetFormChanged ";
        String jsFormChanged = String.format(UIConstants.FORM_CHANGED_JS + "('%s', " + changed + ");", formEditUpdateId);
        logger.debug(METHODNAME, "jsFormChanged=", jsFormChanged);
        return jsFormChanged;
    }
    /*
     * Below is the beginning for removal of BaseSearchForm from framework...
     */
    private T searchCriteriaDTO;

    /**
     * Initialize search related objects.
     */
    protected void initializeSearch() {
        preSearchInitialize();
        postSearchInitialize();

    }

    /**
     * Retrieve the searchCriteriaDTO.
     *
     * @return
     */
    public T getSearchCriteriaDTO() {
        if (searchCriteriaDTO == null) {
            try {
                searchCriteriaDTO = (T) dtoClassType.newInstance();
            } catch (InstantiationException e) {
                throw new IllegalStateException(e.getMessage());
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e.getMessage());
            }
        }
        return searchCriteriaDTO;
    }

    /**
     * Override for executing code pre-search.
     *
     */
    protected void preSearchInitialize() {
    }

    /**
     * Override for executing code post-search. Specifically after
     * searchCriteriaDTO is initialized allowing for defaults to be set on the
     * searchCriteriaDTO which drives the search form.
     */
    protected void postSearchInitialize() {
    }

    protected void clearSearch(ActionEvent actionEvent) throws Exception {
    }

    /**
     * Get the value of child
     *
     * @return the value of child
     */
    public boolean isChild() {
        return child;
    }

    /**
     * Set the value of child
     *
     * @param child new value of child
     */
    public void setChild(boolean child) {
        this.child = child;
    }

    /**
     * Get the value of saveOnBaseParentMGR
     *
     * @return
     */
    public boolean isSaveOnBaseParentMGR() {
        return saveOnBaseParentMGR;
    }

    /**
     * Set the value of saveOnBaseParentMGR
     *
     * @param saveOnBaseParentMGR
     */
    public void setSaveOnBaseParentMGR(boolean saveOnBaseParentMGR) {
        this.saveOnBaseParentMGR = saveOnBaseParentMGR;
    }

    /**
     * Get the value of saveDeleteOnBaseParentMgr
     *
     * @return the value of saveDeleteOnBaseParentMgr
     */
    public boolean isSaveDeleteOnBaseParentMgr() {
        return saveDeleteOnBaseParentMgr;
    }

    /**
     * Set the value of saveDeleteOnBaseParentMgr
     *
     * @param saveParentMgrOnDelete
     */
    public void setSaveDeleteOnBaseParentMgr(boolean saveParentMgrOnDelete) {
        this.saveDeleteOnBaseParentMgr = saveParentMgrOnDelete;
    }

    /**
     * Get the value of refreshSelection
     *
     * @return
     */
    public boolean isRefreshSelection() {
        return refreshSelection;
    }

    /**
     * Set the value of refreshSelection
     *
     * @param refreshSelection
     */
    public void setRefreshSelection(boolean refreshSelection) {
        this.refreshSelection = refreshSelection;
    }

    /**
     * Get the value of parentMGR
     *
     * @return
     */
    public BaseModule getParentMGR() {
        return parentMGR;
    }

    /**
     * Set the value of parentMGR
     *
     * @param parentMGR
     */
    public void setParentMGR(BaseModule parentMGR) {
        this.parentMGR = parentMGR;
    }

    /**
     * Get the value of childQueryClass
     *
     * @return
     */
    public Class getChildQueryClass() {
        if (!isChild()) {
            throw new UnsupportedOperationException("Not supported.");
        }
        return childQueryClass;
    }

    /**
     * Set the value of childQueryClass
     *
     * @param childQueryClass
     */
    public void setChildQueryClass(Class childQueryClass) {
        if (!isChild()) {
            throw new UnsupportedOperationException("Not supported.");
        }
        this.childQueryClass = childQueryClass;
    }

    public T getSearchCriteriaDTO(BaseModule parentModule) {
        T localSearchCriteriaDTO = (T) getSearchCriteriaDTO();
        BaseDTO localParentDTO = parentModule.getParentDTO();
        Object primaryKey = localParentDTO.getPrimaryKey();
        localSearchCriteriaDTO.setForeignKey(parentModule.getDtoClassType(), primaryKey);
        return localSearchCriteriaDTO;
    }

    public boolean isSaveImmediately() {
        return saveImmediately;
    }

    public void setSaveImmediately(boolean saveImmediately) {
        this.saveImmediately = saveImmediately;
    }

    public UserSecuritySchemePermissionMapList getUserSecuritySchemePermissionMapList() {
        return userSecuritySchemePermissionMapList;
    }

    public List<String> getExcludedValueExpressions() {
        return excludedValueExpressions;
    }

    public void setExcludedValueExpressions(List<String> excludedValueExpressions) {
        this.excludedValueExpressions = excludedValueExpressions;
    }

    public Map<String, String> getAlternateValueExpressionMap() {
        return alternateValueExpressionMap;
    }

    public void setAlternateValueExpressionMap(Map<String, String> alternateValueExpressionMap) {
        this.alternateValueExpressionMap = alternateValueExpressionMap;
    }

    public boolean showButton(ButtonType buttonType) {
        final String METHODNAME = "showButton ";
        UserSecuritySchemePermissionMap userSecuritySchemePermissionMap = userSecuritySchemePermissionMapList.get(getSessionDTO().getUserDTO().getUserId());
        return SecureUI.isButtonViewable(getDtoClassType(), null, buttonType, userSecuritySchemePermissionMap.getPermissionAllowMap(), userSecuritySchemePermissionMap.getPermissionDenyMap());
    }

    public boolean showButton(ButtonType buttonType, String className) throws NotFoundException {
        final String METHODNAME = "showButton ";
        UserSecuritySchemePermissionMap userSecuritySchemePermissionMap = userSecuritySchemePermissionMapList.get(getSessionDTO().getUserDTO().getUserId());
        return SecureUI.isButtonViewable(ClassUtils.classForName(className), null, buttonType, userSecuritySchemePermissionMap.getPermissionAllowMap(), userSecuritySchemePermissionMap.getPermissionDenyMap());
    }

    /* 
     * Old implementation, not used because disabling the component caused process issues
     * Null values were being updated in the model when a component was disabled
     *
     * Leave here as an example to navigate the components and possibly add javascript to each control for the saveImmediate model
     *
     */
    public void preRenderView(ComponentSystemEvent event) {
        final String METHODNAME = "preRenderView ";
        long startTime = System.nanoTime();
        UserSecuritySchemePermissionMap userSecuritySchemePermissionMap = userSecuritySchemePermissionMapList.get(getSessionDTO().getUserDTO().getUserId());
        SecureUI.secureComponents(event.getComponent(), userSecuritySchemePermissionMap.getPermissionAllowMap(), userSecuritySchemePermissionMap.getPermissionDenyMap(), excludedValueExpressions, alternateValueExpressionMap);
        logger.logDuration(LogLevel.DEBUG, METHODNAME, startTime);                                                                            
    }

    /* Allows descendant manipulation of the PermissionAllowMap */
    protected Map<String, List<PermissionType>> getPermissionAllowMap(UserSecuritySchemePermissionMap userSecuritySchemePermissionMap) {
        return userSecuritySchemePermissionMap.getPermissionAllowMap();
    }

    /* Allows descendant manipulation of the PermissionDenyMap */    
    protected Map<String, List<PermissionType>> getPermissionDenyMap(UserSecuritySchemePermissionMap userSecuritySchemePermissionMap) {
        return userSecuritySchemePermissionMap.getPermissionDenyMap();
    }
    
    
    protected void secureForm() {
        String METHODNAME = "secureForm ";
        logger.debug(METHODNAME, "javaScript=", javaScript, " uiSecured=", uiSecured);

        // Runs once to collect the javascript
        if (isSecureUI()) {
            if (javaScript == null && !uiSecured || isReadOnly()) {
                // Permissions
                UserSecuritySchemePermissionMap userSecuritySchemePermissionMap = userSecuritySchemePermissionMapList.get(getSessionDTO().getUserDTO().getUserId());
                List<String> javaScripts = new ArrayList<String>();
                
                if (logger.isDebugEnabled()) {
                    if (userSecuritySchemePermissionMap != null) {
                        Map<String, List<PermissionType>> permissionAllowMap = userSecuritySchemePermissionMap.getPermissionAllowMap();
                        logger.info(METHODNAME, "permissionAllowMap");
                        for (Entry<String, List<PermissionType>> entry : permissionAllowMap.entrySet()) {
                            logger.debug(METHODNAME, "entry.getKey()=", entry.getKey());
                            List<PermissionType> permissionTypes = entry.getValue();
                            for (PermissionType permissionType : permissionTypes) {
                                logger.debug(METHODNAME, "permissionType=", permissionType);
                            }
                        }
                        Map<String, List<PermissionType>> permissionDenyMap = userSecuritySchemePermissionMap.getPermissionDenyMap();
                        logger.info(METHODNAME, "permissionDenyMap");
                        for (Entry<String, List<PermissionType>> entry : permissionDenyMap.entrySet()) {
                            logger.debug(METHODNAME, "entry.getKey()=", entry.getKey());
                            List<PermissionType> permissionTypes = entry.getValue();
                            for (PermissionType permissionType : permissionTypes) {
                                logger.debug(METHODNAME, "permissionType=", permissionType);
                            }
                        }
                    }
                }
                // Secure the Form
                SecureUI.secureUIForm(getEditForm(), javaScripts,
                        getPermissionAllowMap(userSecuritySchemePermissionMap),
                        getPermissionDenyMap(userSecuritySchemePermissionMap),
                        getExcludedValueExpressions(),
                        getAlternateValueExpressionMap());

                if (!javaScripts.isEmpty()) {
                    StringBuilder javaScriptBuffer = new StringBuilder();
                    //javaScriptBuffer.append("console.time('secureUI');");
                    for (String js : javaScripts) {
                        javaScriptBuffer.append(js);
                    }
                    //javaScriptBuffer.append("console.timeEnd('secureUI');");
                    javaScript = javaScriptBuffer.toString();
                }
                logger.debug(METHODNAME, "javaScript=", javaScript);
                uiSecured = true;
            }

            // Executes the javascript
            if (javaScript != null) {
                RequestContext.getCurrentInstance().execute(javaScript);
            }
        }

//        String resetFormState = "resetChangedState('" + this.getEditFormId() + "');";
//        RequestContext.getCurrentInstance().execute(resetFormState);
    }

    public void readOnlyView(Object object) throws Exception {
        final String METHODNAME = "readOnlyView";
        setReadOnly(true);
        onRowSelectMain(UtilityMGR.getEmulatedSelectEvent(object));        
    }
    
    public boolean isSecureUI() {
        return secureUI;
    }

    public void setSecureUI(boolean secureUI) {
        this.secureUI = secureUI;
    }

    public void postOpenDialogMain() {
        String METHODNAME = "postOpenDialogMain ";
        logger.debug(METHODNAME);
        long startTime = System.nanoTime();
        try {
            PostRenderService.postRenderUIComponent(this, getEditForm());
            secureForm();
            postOpenDialog();
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, startTime);                                                                            
            logger.logEnd(METHODNAME);
        }
    }

    public void postOpenDialog() {

    }

    public PropertyBagDTO getPropertyBagDTO() {
        return this.propertyBagDTO;
    }

    public TabService getTabService() {
        return tabService;
    }

    public InlineDataTableService getInlineDataTableService() {
        return inlineDataTableService;
    }

    public void registerTabComponents() {

    }

    public int getRowsPerPage() {
        DataTable dataTable = getDataTable();
        if (dataTable != null) {
            rowsPerPage = dataTable.getRows();
        }
        return rowsPerPage;
    }

    public UIDataType getUIDataType() {
        return uiDataType;
    }

    public void setUIDataType(UIDataType uiDataType) {
        this.uiDataType = uiDataType;
    }

//    public boolean isDataTableInUse() {
//        return dataTableInUse;
//    }
//
//    public void setDataTableInUse(boolean dataTableInUse) {
//        this.dataTableInUse = dataTableInUse;
//    }
    public String getTabIdByIndex(int tabIndex) {
        return UtilityMGR.getTabIdForIndex(this, tabIndex);
    }

    public Tab getTabByIndex(int tabIndex) {
        Tab result;
        String id = UtilityMGR.getTabIdForIndex(this, tabIndex);
        result = (Tab) UtilityMGR.getUIComponentFromBaseId(id);
        return result;
    }

    public boolean isDataTableExists() {
        return dataTableExists;
    }

    public void setDataTableExists(boolean dataTableExists) {
        this.dataTableExists = dataTableExists;
    }

    // Get common form Id for saveImmediateIy
    private String getCommonFormId() {
        return "@(form[name='" + getFormEditUpdateId() + "']";
    }

    /* 
       When saveImmediately is true (Save option 3)
       Process the form in the visible tab with a styleClass of tab-content, 
       
       When saveImmediate is false (Save option 1 or 2)
       Process the form
     */
    public String getProcessId() {
        final String METHODNAME = "getProcessId ";
        String processId;
        if (isSaveImmediately()) {
            processId = getCommonFormId() + " .ui-tabs-panel:visible .tab-content)";
        } else {
            processId = "@form";
        }
        logger.debug(METHODNAME, "processId=", processId);
        return processId;
    }

    /* 
       When saveImmediately is true (Save option 3)
       Update the form in the visible tab with a styleClass of tab-content, 
       Update the form's buttons with a styleClass of nav-button
       If parentDTO is new add the tabViewId to update the tabs to change them from disabled to enabled)
       
       When saveImmediate is false (Save option 1 or 2)
       Update the form with the dataTableId
     */
    public String getUpdateId(String saveOrApply) {
        final String METHODNAME = "getUpdateId ";
        String updateId = getProcessId();
        if (isSaveImmediately()) {
            updateId += " " + getCommonFormId() + " .nav-button)";
            if (saveOrApply.equalsIgnoreCase("apply")) {
                if (getParentDTO() != null && getParentDTO().isNew()) {
                    updateId += " " + getTabViewId();
                }
            }
        } else if (isDataTableExists()) {
            String dataTableId = getDataTableUpdateId(true);
            if (!StringUtils.isEmpty(dataTableId)) {
                updateId += " " + dataTableId;
            }
        }
        logger.debug(METHODNAME, "updateId=", updateId);
        return updateId;
    }

    public TreeTable getTreeTable() {
        return null;
    }

    public void setLoadStatus(LoadStatus loadStatus) {
        this.loadStatus = loadStatus;
    }

    public LoadStatus getLoadStatus() {
        return loadStatus;
    }

    public DataAccessInterface getDataAccessInterface() {
        return dataAccessInterface;
    }

    private boolean displaySaveMessage = true;

    public boolean isDisplaySaveMessage() {
        return displaySaveMessage;
    }

    public void setDisplaySaveMessage(boolean displaySaveMessage) {
        this.displaySaveMessage = displaySaveMessage;
    }

    public void displayMessage(BaseDTO dto, DTOState prevDTOState, SourceMethod sourceMethod) throws Exception {
        Object[] messageArgs = getDataAccessInterface().getMessageArgs(dto, sourceMethod);
        if (sourceMethod == SourceMethod.saveMain && isDisplaySaveMessage()) {
            
            boolean matched = ObjectUtils.objectToBoolean(dto.getQueryMap().get(CoreConstants.ADDORUPDATEMATCHED));
            if (isSaveOnBaseParentMGR() && dto != null && dto.getOperationDTOState() != DTOState.UNSET) {
                messageMGR.displayWarn("dtoNotSaved", messageArgs);
            } else if (prevDTOState == DTOState.NEW || prevDTOState == DTOState.NEWMODIFIED) {
                if (!matched) {
                    messageMGR.displayInfo(getAddedMessageKey(), messageArgs);
                }
                else {
                    messageMGR.displayInfo(getMatchedMessageKey(), messageArgs);
                }
            } else if (prevDTOState == DTOState.UPDATED) {
                messageMGR.displayInfo(getUpdatedMessageKey(), messageArgs);
            }
        }
    }

    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {    
        final String METHODNAME = "onSearchDialogReturn ";
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        logger.info(METHODNAME, "baseDTO=", baseDTO);
        logger.info(METHODNAME, "selectedDTO=", getSelectedDTO());
        logger.info(METHODNAME, "fieldName=", getTargetFieldName());
    }    
 
    public BaseDTO getSelectedDTO() {
        return selectedDTO;
    }

    public void setSelectedDTO(BaseDTO selectedDTO) {
        this.selectedDTO = selectedDTO;
    }

    /**
     * Get the value of targetFieldName
     *
     * @return the value of targetFieldName
     */
    public String getTargetFieldName() {
        return targetFieldName;
    }

    /**
     * Set the value of targetFieldName
     *
     * @param targetFieldName new value of targetFieldName
     */
    public void setTargetFieldName(String targetFieldName) {
        this.targetFieldName = targetFieldName;
    }

    public void resetField(BaseDTO baseDTO, String fieldName) {
        final String METHODNAME = "resetField ";
        logger.info(METHODNAME, "baseDTO=", (baseDTO != null ? baseDTO.getClass().getSimpleName() : baseDTO), " fieldName=", fieldName);
    }
    
    /* used to fire jsRefresh button provided it is rendered showRefresh must be true */
    private boolean jsRefresh = false;
    protected void setJsRefresh(boolean jsRefresh) {
        this.jsRefresh = jsRefresh;
    }
    protected boolean isJsRefresh() {
        return this.jsRefresh;
    }
            
    protected void jsRefresh() {
        final String METHODNAME = "jsRefresh ";
        if (isJsRefresh()) {
            String javaScript = "$('button[id*=refresh]').click();";
            logger.info(METHODNAME, "javaScript=", javaScript);
            RequestContext.getCurrentInstance().execute(javaScript); 
            setJsRefresh(false);
        }
    }
        
    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }    
}
