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
package org.cdsframework.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.BehaviorBase;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.lookup.UserDTOList;
import org.cdsframework.message.MessageMGR;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.util.ComponentUtils;

/**
 * Utility class for common application and JSF-related utility functions.
 *
 * @author HLN Consulting, LLC
 */
@Named
@ApplicationScoped
public class UtilityMGR implements Serializable {

    private static final long serialVersionUID = 896992766771151334L;
    private static final LogUtils logger = LogUtils.getLogger(UtilityMGR.class);
    private static final String AT_NONE = "@none";
    private static final String AT_THIS = "@this";
    private final static String[] TEXT_NUMBERS = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN"};
    @Inject
    protected MessageMGR messageMGR;

    @Inject
    private UserDTOList userDTOList;

    final static private String NO_OPTION_SELECT_TEXT = "Choose one ...";

    @PostConstruct
    public void postConstructor() {
        final String METHODNAME = "postConstructor ";
        logger.logBegin(METHODNAME);
        try {
            messageMGR.setMessageBundle(getClass());
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    public static Date getCurrentDate() {
        return DateUtils.getCurrentDate();
    }

    public static String getRemoteCmdProcessValue(boolean processOnChange, String onChangeProcess) {
        final String METHODNAME = "getRemoteCmdProcessValue ";
//        logger.debug(METHODNAME, "called: ", processOnChange, "; ", onChangeProcess);
        String result;
        // if processOnChange is false just return AT_NONE else process onChangeProcess
        if (processOnChange) {

            // normalize
            if (onChangeProcess != null) {
                result = onChangeProcess.trim();
                if (!result.isEmpty() && !result.contains(AT_THIS)) {
                    result = AT_THIS + " " + result;
                }
            } else {
                result = AT_THIS;
            }
            // after processing - if it is empty - set it to AT_THIS
            if (result.isEmpty()) {
                result = AT_THIS;
            }
        } else {
            result = AT_NONE;
        }

//        logger.debug(METHODNAME, "result=", result);
        return result;
    }

    /**
     * Returns the proper update value given the supplied parameters.
     *
     * @param processOnChange
     * @param onChangeUpdate
     * @param panelGridId
     * @return
     */
    public static String getRemoteCmdUpdateValue(boolean processOnChange, String onChangeUpdate, String panelGridId) {
        return getRemoteCmdUpdateValue(processOnChange, true, onChangeUpdate, panelGridId);
    }

    /**
     * Returns the proper update value given the supplied parameters.
     *
     * @param processOnChange
     * @param updateSelfOnProcessChange
     * @param onChangeUpdate
     * @param panelGridId
     * @return
     */
    public static String getRemoteCmdUpdateValue(boolean processOnChange, boolean updateSelfOnProcessChange, String onChangeUpdate, String panelGridId) {
        final String METHODNAME = "getRemoteCmdUpdateValue ";
//        logger.debug(METHODNAME, "called: ", processOnChange, "; ", updateSelfOnProcessChange, "; ", onChangeUpdate, "; ", panelGridId);
        String result = AT_NONE;
        // if processOnChange is false just return AT_NONE else process onChangeUpdate and panelGridId
        if (processOnChange) {

            // normalize
            if (onChangeUpdate != null) {
                onChangeUpdate = onChangeUpdate.trim();
            } else {
                onChangeUpdate = "";
            }

            // normalize
            if (panelGridId != null) {
                panelGridId = panelGridId.trim();
            } else {
                panelGridId = "";
            }

            // if onChangeUpdate is equal to AT_NONE then just return AT_NONE
            // else
            if (!onChangeUpdate.equalsIgnoreCase(AT_NONE)) {
                // if update self is true then add the onChangeUpdate value + the panelGrid values
                if (updateSelfOnProcessChange) {
                    result = String.format("%s %s", onChangeUpdate, panelGridId).trim();
                } else {
                    result = onChangeUpdate;
                }
            }
        }

        // after processing - if it is empty - set it to AT_NONE
        if (result.isEmpty()) {
            result = AT_NONE;
        }
//        logger.debug(METHODNAME, "result=", result);

        return result;
    }

    public static String formatTelephone(String telephoneNumber, String ext) {
        return StringUtils.formatTelephoneWithOptionalExt(telephoneNumber, ext);
    }

    public static String formatTelephoneDashes(String telephoneNumber) {
        return StringUtils.formatTelephoneDashes(telephoneNumber);
    }

    public static String getFormattedDate(java.util.Date p_date) {
        return DateUtils.getFormattedDate(p_date, "MM/dd/yyyy");
    }

    public static String getFormattedDateTime(java.util.Date p_date) {
        return DateUtils.getFormattedDate(p_date, "MM/dd/yyyy hh:mm a");
    }

    public static String getDateAsPresentOrPast(java.util.Date p_date) {
        return DateUtils.getDateAsPresentOrPast(p_date);
    }

    public static String getFormattedDateTime() {
        return getFormattedDateTime(new Date());
    }

    public static String getFormattedDate(java.util.Date p_date, String p_fmt_string) {
        return DateUtils.getFormattedDate(p_date, p_fmt_string);
    }

    public static String getStringFromArray(List<String> stringArrayList) {
        return stringArrayList == null || stringArrayList.isEmpty() ? "" : StringUtils.getStringFromArray(stringArrayList, ", ");
    }

    public static List<Object> asList(Object obj) {
        return Arrays.asList(obj);
    }

    public static String concat(String string1) {
        return concatAll(string1);
    }

    public static String concat(String string1, String string2) {
        return concatAll(string1, string2);
    }

    public static String concat(String string1, String string2, String string3) {
        return concatAll(string1, string2, string3);
    }

    public static String concat(String string1, String string2, String string3, String string4) {
        return concatAll(string1, string2, string3, string4);
    }

    public static String concat(String string1, String string2, String string3, String string4, String string5) {
        return concatAll(string1, string2, string3, string4, string5);
    }

    private static String concatAll(String... strings) {
        StringBuilder sb = new StringBuilder();
        for (String item : strings) {
            sb.append(item);
        }
        return sb.toString();
    }

    public static Map<String, String> parseUncategorizedSQLException(String message) {
        Map<String, String> messageMap = new HashMap<String, String>();
        for (String item : message.split(";")) {
            String errorMessage = item.trim();
            if (errorMessage.startsWith("uncategorized")) {
                messageMap.put("sql", errorMessage.split("\\[")[1].split("\\]")[0]);
            } else if (errorMessage.startsWith("SQL state")) {
                messageMap.put("state", errorMessage.split("\\[")[1].split("\\]")[0]);
            } else if (errorMessage.startsWith("error code")) {
                messageMap.put("errorCode", errorMessage.split("\\[")[1].split("\\]")[0]);
            } else if (errorMessage.startsWith("ORA")) {
                messageMap.put("error", errorMessage);
            }
        }
        return messageMap;
    }

    public static void setCallbackParam(String key, Object value) {
        logger.trace("setCallbackParam: ", key, " - ", value);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (requestContext != null) {
            requestContext.addCallbackParam(key, value);
        }
    }

    public static void getStackTrace(List<StackTraceElement> stackTraceList, Throwable throwable) {
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        stackTraceList.addAll(Arrays.asList(stackTraceElements));
    }

    public static List<StackTraceElement> getStackTrace(Throwable throwable) {
        final String METHODNAME = "getStackTrace ";

        List<StackTraceElement> stackTraceElements = new ArrayList<StackTraceElement>();
        Throwable eCurrentThrowable = throwable;
        Throwable eRootThrowable = null;

        try {
            while (eCurrentThrowable != null) {
                // Populate the stack trace
                getStackTrace(stackTraceElements, eCurrentThrowable);

                // Check for nested causes
                Throwable eNextLevelUp = eCurrentThrowable.getCause();
                if (eNextLevelUp == null) {
                    eRootThrowable = eCurrentThrowable;
                    break;
                } else {
                    eCurrentThrowable = eNextLevelUp;
                }
            }
        } finally {
        }
        return stackTraceElements;
    }

    public static Throwable getRootCause(Throwable t) {
        final String METHODNAME = "getRootCause ";
        Throwable eCurrentThrowable = t;
        Throwable eRootThrowable = null;
        try {
            while (eCurrentThrowable != null) {
                Throwable eNextLevelUp = eCurrentThrowable.getCause();
                if (eNextLevelUp == null) {
                    eRootThrowable = eCurrentThrowable;
                    break;
                } else {
                    eCurrentThrowable = eNextLevelUp;
                }
            }
        } finally {
        }
        return eRootThrowable;
    }

    public static String titleCase(String inputString) {
        return StringUtils.titleCase(inputString);
    }

    public void foo(ActionEvent actionEvent) {
        logger.trace("Foo w/ ActionEvent called...");
    }

    public void foo(ValueChangeEvent valueChangeEvent) {
        logger.trace("Foo w/ ValueChangeEvent called...");
    }

    public void foo(Object... args) {
        logger.trace("Foo w/ Object... called...");
    }

    public void foo() {
        logger.trace("Foo called...");
    }

    public void foo(String bar) {
        logger.trace("Foo w/ bar called...");
    }

    public String getLabelFromId(String id) {
        return StringUtils.unCamelize(id);
    }

    public String getPropertyName(String propertyName) {
        if (!StringUtils.isEmpty(propertyName)) {
            if (propertyName.toUpperCase().endsWith("LKDTO")) {
                int pos = propertyName.toUpperCase().indexOf("LKDTO");
                propertyName = propertyName.substring(0, pos);
            } else if (propertyName.toUpperCase().endsWith("DTO")) {
                int pos = propertyName.toUpperCase().indexOf("DTO");
                propertyName = propertyName.substring(0, pos);
            }
            propertyName = StringUtils.unCamelize(propertyName.replace("_", " "));
        }
        return propertyName;
    }

    public String getClassName(String className) {
        String name = null;
        if (!StringUtils.isEmpty(className)) {
            int lastPos = className.lastIndexOf(".");
            if (lastPos >= 0) {
                name = className.substring(lastPos + 1);
            }
            name = getPropertyName(name);
        }
        return name;
    }

    public String getUsername(String userId) {
        String username = userId;
        UserDTO userDTO = userDTOList.get(userId);
        if (userDTO != null) {
            username = userDTO.getUsername();
        }
        return username;
    }

    public String getUuid() {
        return UUID.randomUUID().toString();
    }

    public static String getDateDiffCombo(Date dateA, Date dateB) {
        String result = null;
        if (dateA != null && dateB != null) {
            result = DateUtils.getDateDiffCombo(dateA, dateB);
        } else {
            result = "N/A";
        }
        return result;
    }

    public static String getDateDiffComboWithBR(Date dateA, Date dateB) {
        String result = null;
        if (dateA != null && dateB != null) {
            dateA = DateUtils.removeTime(dateA);
            dateB = DateUtils.removeTime(dateB);
            String dateDiffYMD = DateUtils.getDateDiffYMDNoEx(dateA, dateB);
            String dateDiffDays = DateUtils.getDateDiffDaysNoEx(dateA, dateB);
            if (dateDiffYMD == null || dateDiffYMD.trim().isEmpty()) {
                result = dateDiffDays;
            } else {
                result = String.format("%s<br />(%s)", dateDiffYMD, dateDiffDays);
            }
        } else {
            result = "N/A";
        }
        return result;
    }

    public Date incrementDateFromString(Date baseDate, String incrementString, boolean backtrack) {
        final String METHODNAME = "incrementDateFromString ";
        if ("0d".equals(incrementString)) {
            return baseDate;
        }
        Date newDate = null;
        try {
            newDate = DateUtils.incrementDateFromString(baseDate, incrementString, backtrack);
        } catch (NumberFormatException e) {
            messageMGR.displayErrorMessage(logger.error(METHODNAME, "Bad interval value: ", incrementString));
        } catch (IllegalArgumentException e) {
            messageMGR.displayErrorMessage(logger.error(METHODNAME, "Bad interval value: ", incrementString));
        } catch (Exception e) {
            messageMGR.displayErrorMessage(logger.error(METHODNAME, "Unhandled exception: ", e.getMessage()));
            logger.error(e);
        }
        return newDate;
    }

    public String getDaysFromIntervalString(Date dateA, String intervalString) {
        final String METHODNAME = "getDaysFromIntervalString ";
        if ("0d".equals(intervalString)) {
            return "0 days";
        }
        String result = "";
        try {
            Date dateB = DateUtils.incrementDateFromString(dateA, intervalString, false);
            result = DateUtils.getDateDiffDays(dateA, dateB);
        } catch (IllegalArgumentException e) {
            messageMGR.displayErrorMessage(logger.error(METHODNAME, "Bad interval value: ", intervalString));
        } catch (Exception e) {
            messageMGR.displayErrorMessage(logger.error(METHODNAME, "Unhandled exception: ", e.getMessage()));
            logger.error(e);
        }
        return result;
    }

    public static String getDateOrderErrorCSS(Date dateA, Date dateB) {
        String result = "color:red;";
        if (dateA != null && dateB != null) {
            if (dateA.after(dateB) || dateA.equals(dateB)) {
                result = "color:inherit;";
            }
        }
        return result;
    }

    public static SelectEvent getEmulatedSelectEvent(Object object) {
        final String METHODNAME = "getEmulatedSelectEvent ";
        logger.debug(METHODNAME, "called: ", object);
        BehaviorBase behavior = new BehaviorBase();
        UIComponentBase component = new UIComponentBase() {
            @Override
            public String getFamily() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getId() {
                return "emulatedSelectEvent";
            }
        };
        return new SelectEvent(component, behavior, object);
    }

    public static ActionEvent getEmulatedActionEvent() {
        return (getEmulatedActionEvent("emulatedActionEvent"));
    }

    public static ActionEvent getEmulatedActionEvent(final String id) {
        final String METHODNAME = "getEmulatedActionEvent ";
        UIComponentBase component = new UIComponentBase() {
            @Override
            public String getFamily() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getId() {
                return id;
            }
        };
        return new ActionEvent(component);
    }

    public static boolean isEmpty(String item) {
        boolean result = false;
        if (item == null || item.trim().isEmpty()) {
            result = true;
        }
        return result;
    }

    public static <S extends BaseDTO> void loggerDTOProperties(S dto) {
        DTOUtils.logDTOProperties(dto);
    }

    public static String getUIComponentClientId(UIComponent uiComponent, boolean outsideNamingContainer) {
        String result = "";
        if (uiComponent != null) {
            result = uiComponent.getClientId();
            if (outsideNamingContainer) {
                result = ":" + result;
            }
        }
        return result;
    }

    public static boolean uiComponentExists(UIComponent parentUIComponent, UIComponent childUIComponent) {
        final String METHODNAME = "uiComponentExists ";
        boolean componentExists = false;
        if (childUIComponent != null) {
//            logger.info(METHODNAME, "childUIComponent.getClientId()=", childUIComponent.getClientId());
//            logger.info(METHODNAME, "parentUIComponent.getClientId()=", parentUIComponent.getClientId());
//            logger.info(METHODNAME, "childUIComponent=", childUIComponent.getClass().getSimpleName());

            // Check the parent first
            int indexOfPos = childUIComponent.getClientId().indexOf(parentUIComponent.getClientId());
            if (indexOfPos >= 0) {
//                logger.info(METHODNAME, "parentUIComponent=", parentUIComponent.getClass().getSimpleName());
                componentExists = true;
            } else {
                List<UIComponent> children = parentUIComponent.getChildren();
                for (UIComponent child : children) {
//                    logger.info(METHODNAME, "child.getClientId()=", child.getClientId());
                    indexOfPos = childUIComponent.getClientId().indexOf(child.getClientId());
                    if (indexOfPos >= 0) {
//                        logger.info(METHODNAME, "child=", child.getClass().getSimpleName());
                        //                    logger.info(METHODNAME, "child.getClientId()=", child.getClientId());
                        //                    logger.info(METHODNAME, "dataTable.getClientId()=", dataTable.getClientId());
                        componentExists = true;
                        break;
                    } else {
                        componentExists = uiComponentExists(child, childUIComponent);
                    }
                    if (componentExists) {
                        break;
                    }
                }
            }
        }

        return componentExists;
    }

    public static Class classForName(String className) throws NotFoundException {
        return ClassUtils.classForName(className);
    }

    private static BaseDTO getParentChild(BaseDTO parentDTO, Class childQueryClass, BaseDTO dto) throws CatException {
        final String METHODNAME = "getParentChild ";

        if (dto == null) {
            throw new CatException("dto is null!");
        }
        if (parentDTO == null) {
            logger.error(METHODNAME, "parentDTO is null!");
            return dto;
//            throw new CatException("parentDTO is null!");
        }
        if (childQueryClass == null) {
            logger.error(METHODNAME, "childQueryClass is null!");
            return dto;
//            throw new CatException("childQueryClass is null!");
        }
        List<BaseDTO> childrenDTOs = parentDTO.getChildrenDTOs(childQueryClass);
        if (childrenDTOs == null || childrenDTOs.isEmpty()) {
            throw new CatException("childrenDTOs is null or empty!");
        }
        logger.debug(METHODNAME, "got: ", parentDTO.getClass().getSimpleName(), " - ", childQueryClass.getCanonicalName(), " - ", dto.getUuid());
        BaseDTO matchedDTO = null;
        for (BaseDTO childDTO : childrenDTOs) {
            Object primaryKey = childDTO.getPrimaryKey();
            if ((primaryKey != null && primaryKey.equals(dto.getPrimaryKey()))
                    || childDTO.getUuid().equals(dto.getUuid())) {
                matchedDTO = childDTO;
            }
        }
        if (matchedDTO == null) {
            logger.error(METHODNAME, "child not found in parent: ", dto, " - ", childrenDTOs);
            return dto;
//            throw new CatException("DTO " + dto.getUuid() + " not found in " + parentDTO.getChildDTOMap());
        }
        return matchedDTO;
    }

    private static String prepUpdateId(String rawUpdateId) {
        logger.debug("prepUpdateId got: ", rawUpdateId);
        String result = rawUpdateId;
        List<String> components = Arrays.asList(rawUpdateId.split(":"));
        String lastItem = components.get(components.size() - 1);
        try {
            Integer.parseInt(lastItem);
            components = components.subList(0, components.size() - 1);
        } catch (Exception e) {
        }
        result = StringUtils.getStringFromArray(components, ":");
        logger.debug("prepUpdateId result: ", result);
        return result;
    }

    public static void processDTODelete(BaseDTO parentDTO, Class childQueryClass, BaseDTO dto, String updateId) throws MtsException,
            CatException {
        final String METHODNAME = "processDTODelete ";
        logger.info(METHODNAME, "parentDTO=", parentDTO, "; childQueryClass=", childQueryClass, "; dto=", dto, "; updateId=", updateId);
        BaseDTO childDTO = getParentChild(parentDTO, childQueryClass, dto);
        if (childDTO.isNew()) {
            logger.debug(METHODNAME, "child dto is new - removing");
            List<BaseDTO> childrenDTOs = parentDTO.getChildrenDTOs(childQueryClass);
            childrenDTOs.remove(childDTO);
        } else if (childDTO.isDeleted()) {
            logger.debug(METHODNAME, "child dto in deleted state - unsetting");
            DTOUtils.setDTOState(childDTO, DTOState.UNSET);
        } else {
            logger.debug(METHODNAME, "child dto state set to deleted");
            childDTO.delete(true);
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update(prepUpdateId(updateId));
    }

    public static void processDTONew(BaseDTO parentDTO, Class childQueryClass, String updateId) throws CatException, MtsException {
        final String METHODNAME = "processDTONew ";
        logger.info(METHODNAME, "parentDTO=", parentDTO, "; childQueryClass=", childQueryClass, "; updateId=", updateId);
        if (parentDTO == null) {
            throw new CatException("parentDTO is null!");
        }
        if (childQueryClass == null) {
            throw new CatException("childQueryClass is null!");
        }
        ParentChildRelationship childRelationship = DTOUtils.getParentChildRelationshipMapByQueryClass(parentDTO.getClass()).get(childQueryClass);
        logger.debug("childRelationship=", childRelationship);
        if (childRelationship != null) {
            try {
                parentDTO.getChildrenDTOs(childQueryClass).add(childRelationship.childDtoClass().newInstance());
            } catch (InstantiationException e) {
                logger.error(e);
            } catch (IllegalAccessException e) {
                logger.error(e);
            }
        } else {
            throw new CatException("Query class " + childQueryClass.getSimpleName() + " not found in " + parentDTO.getClass().getSimpleName());
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update(prepUpdateId(updateId));
    }

    public static int getChildCount(BaseDTO parentDTO, Class childQueryClass) {
        final String METHODNAME = "getChildCount ";
        int result = 0;
        if (parentDTO != null) {
            if (childQueryClass != null) {
                result = parentDTO.getChildrenDTOs(childQueryClass).size();
            } else {
                logger.warn(METHODNAME, "childQueryClass is null!");
            }
        } else {
            logger.warn(METHODNAME, "parentDTO is null!");
        }
        return result;
    }

    public static DTOState getDTOState(BaseDTO parentDTO, Class childQueryClass, BaseDTO dto) {
        DTOState dtoState = DTOState.NEW;
        try {
            dtoState = getParentChild(parentDTO, childQueryClass, dto).getDTOState();
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, UtilityMGR.class);
        }
        return dtoState;
    }

    public static String getNO_OPTION_SELECT_TEXT() {
        return NO_OPTION_SELECT_TEXT;
    }

    public static String formatUuid(String uuid) {
        String result = null;
        if (uuid != null) {
            String formatUuid = UuidUtils.formatUuid(uuid);
            if (formatUuid != null) {
                result = String.format("{%s}", formatUuid).toUpperCase();
            }
        }
        return result;
    }

    public static UIComponent getUIComponentById(String clientId) {
        final String METHODNAME = "getUIComponentById ";
        logger.debug(METHODNAME, "id: ", clientId);
        UIComponent result = null;
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        try {
            result = viewRoot.findComponent(clientId);
        } catch (NullPointerException e) {
            logger.debug(METHODNAME, "Lookup failed for: ", clientId);
        }
        logger.debug(METHODNAME, "result: ", result);
        return result;
    }

    public static UIComponent getUIComponentFromBaseId(String baseId) {
        final String METHODNAME = "getUIComponent ";
        String clientId = null;
        UIComponent uiComponent = null;
        try {
            clientId = ComponentUtils.findComponentClientId(baseId);
        } catch (NullPointerException e) {
            logger.debug(METHODNAME, "could not find ", baseId);
        }
        if (clientId != null) {
            uiComponent = getUIComponentById(clientId);
        }
//       else {
//           throw new IllegalArgumentException(METHODNAME + "null clientId for " + baseId);
//       }
//       if (uiComponent == null) {
//           throw new IllegalArgumentException(METHODNAME + "null uiComponent for " + clientId);
//       }
        return uiComponent;
    }

    public static String getUIComponentUpdateId(String componentId) {
        return getUIComponentUpdateId(componentId, false);
    }

    public static String getUIComponentUpdateId(String componentId, boolean outsideNamingContainer) {
        final String METHODNAME = "getUIComponentUpdateId ";
        long start = System.nanoTime();
        String result = null;
        logger.debug(METHODNAME, componentId);
        try {
            result = ComponentUtils.findComponentClientId(componentId);
        } catch (Exception e) {
            logger.debug(METHODNAME, "could not find ", componentId);
        }
        if (result != null && outsideNamingContainer && !result.startsWith(":")) {
            result = ":" + result;
        } else if (result == null) {
            logger.debug(METHODNAME, componentId, " ComponentUtils.findComponentClientId returned null");
        }
        logger.logDuration(LogLevel.DEBUG, METHODNAME, start);
        return result;
    }

    public static String getTabIdForIndex(BaseModule module, int tabIndex) {
        return String.format("%sTab%s", module.getName(), TEXT_NUMBERS[tabIndex]);
    }

    public static void onRowCancel(RowEditEvent rowEditEvent) {
    }

    public static void onRowEdit(RowEditEvent rowEditEvent) {
    }

    public static void onCellEdit(CellEditEvent cellEditEvent) {
    }

}
