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
package org.cdsframework.security.ui;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.el.ValueReference;
import javax.faces.application.Application;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.enumeration.PermissionType;
import org.cdsframework.util.DTOUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputmask.InputMask;
import org.primefaces.component.picklist.PickList;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.selectoneradio.SelectOneRadio;

/**
 *
 * @author HLN Consulting, LLC
 */
public class SecureUI {
    private static LogUtils logger = LogUtils.getLogger(SecureUI.class);
    private static ValueExpression valueExpression = getValueExpression("#{true}");
//    private static Map<Integer, Class> valueExpressionMap = new HashMap<Integer, Class>();
    
    public static boolean isButtonViewable(Class<? extends BaseDTO> dtoClassType, Class<? extends BaseDTO> childDtoClassType, ButtonType buttonType, 
            Map<String, List<PermissionType>> permissionAllowMap, Map<String, List<PermissionType>> permissionDenyMap) {
        final String METHODNAME = "isButtonViewable ";
        long startTime = System.nanoTime();
    
//        logger.info(METHODNAME, dtoClassType.getCanonicalName());
        boolean buttonViewable = false;
        List<PermissionType> allowPermissions = permissionAllowMap.get(dtoClassType.getCanonicalName());
        if (allowPermissions == null) {
            allowPermissions = permissionAllowMap.get(BaseDTO.class.getCanonicalName());
        }
        if (allowPermissions != null) {
            boolean allowFull = allowPermissions.contains(PermissionType.FULL);
            boolean allowInsert = allowPermissions.contains(PermissionType.INSERT);
            boolean allowUpdate = allowPermissions.contains(PermissionType.UPDATE);
            boolean allowDelete = allowPermissions.contains(PermissionType.DELETE);
            if (buttonType == ButtonType.Add) {
                buttonViewable = (allowFull || allowInsert);
            }
            else if (buttonType == ButtonType.Save || buttonType == ButtonType.Ok) {
                buttonViewable = (allowFull || allowInsert || allowUpdate);
            }
            else if (buttonType == ButtonType.Delete) {
                buttonViewable = (allowFull || allowDelete);
            }
        }

        List<PermissionType> denyPermissions = permissionDenyMap.get(dtoClassType.getCanonicalName());
        if (denyPermissions == null) {
            denyPermissions = permissionDenyMap.get(BaseDTO.class.getCanonicalName());
        }
        if (denyPermissions != null) {
            boolean denyFull = denyPermissions.contains(PermissionType.FULL);
            boolean denyInsert = denyPermissions.contains(PermissionType.INSERT);
            boolean denyUpdate = denyPermissions.contains(PermissionType.UPDATE);
            boolean denyDelete = denyPermissions.contains(PermissionType.DELETE);
            if (buttonType == ButtonType.Add) {
                buttonViewable = !(denyFull || denyInsert);
            }
            else if (buttonType == ButtonType.Save || buttonType == ButtonType.Ok) {
                buttonViewable = !(denyFull || (denyInsert && denyUpdate));
            }
            else if (buttonType == ButtonType.Delete) {
                buttonViewable = !(denyFull || denyDelete); 
            }
        }
        
        if (!buttonViewable && buttonType == ButtonType.Save && childDtoClassType == null) {
            // Check children
            Map<Class<? extends BaseDTO>, ParentChildRelationship> parentChildRelationshipMapByDTO = DTOUtils.getParentChildRelationshipMapByDTO(dtoClassType);
            if (!parentChildRelationshipMapByDTO.isEmpty()) {
                for (Map.Entry<Class<? extends BaseDTO>, ParentChildRelationship> mapEntry : parentChildRelationshipMapByDTO.entrySet()) {
                    Class<? extends BaseDTO> childDtoClass = mapEntry.getValue().childDtoClass();
                    buttonViewable = SecureUI.isButtonViewable(childDtoClass, childDtoClass, buttonType, permissionAllowMap, permissionDenyMap);
                    if (buttonViewable) {
                        break;
                    }
                }
            }
        }    
        logger.logDuration(LogLevel.DEBUG, METHODNAME, startTime);                                                                            
        return buttonViewable;
    }        
    
    public static void secureComponents(UIComponent component, Map<String, List<PermissionType>> permissionAllowMap, Map<String, List<PermissionType>> permissionDenyMap, List<String> excludedValueExpressions, Map<String, String> altenateValueExpressionMap) {
        final String METHODNAME = "secureComponents ";
        if (component != null) {
            for (UIComponent child : component.getChildren()) {
                secureUiComponent(child, permissionAllowMap, permissionDenyMap, excludedValueExpressions, altenateValueExpressionMap);
            }
        }
    }    
    
    public static void secureUIForm(UIForm uiForm, List<String> javaScript, Map<String, List<PermissionType>> permissionAllowMap, Map<String, List<PermissionType>> permissionDenyMap, List<String> excludedValueExpressions, Map<String, String> altenateValueExpressionMap) {
        final String METHODNAME = "secureUIForm ";
        if (uiForm != null) {
            List<UIComponent> uiComponents = uiForm.getChildren();
            for (UIComponent child : uiComponents) {
                secureUiComponent(child, javaScript, permissionAllowMap, permissionDenyMap, excludedValueExpressions, altenateValueExpressionMap);
            }
        }
    }

    private static void secureUiComponent(UIComponent uiComponent, List<String> javaScript, Map<String, List<PermissionType>> permissionAllowMap, Map<String, List<PermissionType>> permissionDenyMap, List<String> excludedValueExpressions, Map<String, String> altenateValueExpressionMap) {
        final String METHODNAME = "secureUiComponent ";
//        logger.logBegin(METHODNAME);
        long startTime = System.nanoTime();
        try {
            if (uiComponent != null) {
//                logger.info(METHODNAME, uiComponent.getClass().getSimpleName());

                if ((uiComponent instanceof EditableValueHolder)) {
//                    if (uiComponent instanceof PickList) {
//                        logger.info(METHODNAME, "clientId=", uiComponent.getClientId(), uiComponent.getClass().getSimpleName());
//                    }
//                    EditableValueHolder editableValueHolder = (EditableValueHolder) uiComponent;
//                    logger.info(METHODNAME,
//                            "uiComponent FOUND ", uiComponent.getClass().getCanonicalName(),
//                            " getClientId()= ", uiComponent.getClientId(),
//                            " getValue()=", editableValueHolder.getValue(),
//                            " getLocalValue()= ", editableValueHolder.getLocalValue(),
//                            " getSubmittedValue()= ", editableValueHolder.getSubmittedValue(),
//                            " isValid()= ", editableValueHolder.isValid());
                    ValueExpression valueExpression = uiComponent.getValueExpression("value");
//                    logger.info(METHODNAME, "valueExpression=", valueExpression);

                    if (valueExpression != null) {
                        boolean excludeSecurity = false;
                        String expressionString = valueExpression.getExpressionString();
//                        logger.info(METHODNAME, "expressionString=", expressionString);
                        if (excludedValueExpressions != null && excludedValueExpressions.contains(expressionString)) {
                            excludeSecurity = true;
                        }
//                        logger.info(METHODNAME, "excludeSecurity=", excludeSecurity);
                        if (!excludeSecurity) {
                            // Check if there is an alternate to use
                            String alternateExpression = altenateValueExpressionMap.get(expressionString);
//                            logger.info(METHODNAME, "alternateExpression=", alternateExpression);
                            if (alternateExpression != null) {
                                valueExpression = getValueExpression(alternateExpression);
                            }
//                            logger.info(METHODNAME, "valueExpression=", valueExpression);
                            Class dtoClass = getClass(valueExpression);
//                            logger.info(METHODNAME, "dtoClass=", dtoClass);

                            if (dtoClass != null) {
//                                logger.info(METHODNAME, "dtoClass=", dtoClass.getCanonicalName());
                                List<PermissionType> allowPermissions = permissionAllowMap.get(dtoClass.getCanonicalName());
                                if (allowPermissions != null) {
//                                    logger.info(METHODNAME, "allowPermissions.size()=", allowPermissions.size(), " ", dtoClass.getCanonicalName());
                                    boolean allowFull = allowPermissions.contains(PermissionType.FULL);
                                    boolean allowInsert = allowPermissions.contains(PermissionType.INSERT);
                                    boolean allowUpdate = allowPermissions.contains(PermissionType.UPDATE);
                                    boolean allowSelect = allowPermissions.contains(PermissionType.SELECT);
//                                    logger.info(METHODNAME, "allowFull=", allowFull, " allowInsert=", allowInsert, " allowUpdate=", allowUpdate, " allowSelect=", allowSelect);
                                    boolean disableComponent = !(allowFull || allowInsert || allowUpdate);
                                    if (disableComponent) {
//                                        logger.info(METHODNAME, "allow disableComponent=", disableComponent, " dtoClass=", dtoClass.getCanonicalName(), " uiComponent=", uiComponent);
                                        disableComponent(uiComponent, javaScript);
                                    }
                                }
                                List<PermissionType> denyPermissions = permissionDenyMap.get(dtoClass.getCanonicalName());
                                if (denyPermissions != null) {
//                                    logger.info(METHODNAME, "denyPermissions.size()=", denyPermissions.size());
                                    boolean denyFull = denyPermissions.contains(PermissionType.FULL);
                                    boolean denyInsert = denyPermissions.contains(PermissionType.INSERT);
                                    boolean denyUpdate = denyPermissions.contains(PermissionType.UPDATE);
                                    boolean denySelect = denyPermissions.contains(PermissionType.SELECT);
                                    boolean disableComponent = (denyFull || denySelect || (denyInsert && denyUpdate));
//                                    logger.info(METHODNAME, "denyFull=", denyFull, " denyInsert=", denyInsert, " denyUpdate=", denyUpdate, " denySelect=", denySelect);
                                    if (disableComponent) {
//                                        logger.info(METHODNAME, "deny disableComponent=", disableComponent, " dtoClass=", dtoClass.getCanonicalName(), " uiComponent=", uiComponent);
                                        disableComponent(uiComponent, javaScript);
                                    }
                                }
                            }
                        }
                    }
                }

                Iterator<UIComponent> facetsAndChildren = uiComponent.getFacetsAndChildren();
                while (facetsAndChildren.hasNext()) {
                    secureUiComponent(facetsAndChildren.next(), javaScript, permissionAllowMap, permissionDenyMap, excludedValueExpressions, altenateValueExpressionMap);
                }
            }
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, startTime);                                                                            
//            logger.logEnd(METHODNAME);
        }
    }        
    
    private static Class getClass(ValueExpression valueExpression) {
        final String METHODNAME = "getClass ";
        Class cls = null;
        ValueReference valueReference = ValueExpressionAnalyzer.getReference(FacesContext.getCurrentInstance().getELContext(), valueExpression);
        if (valueReference != null) {
            cls = valueReference.getBase().getClass();
        }
//        logger.info(METHODNAME, "valueExpressionMap.size()=", valueExpressionMap.size());
//        Class cls = valueExpressionMap.get(valueExpression.hashCode());
//        if (cls == null) {
//            ValueReference valueReference = ValueExpressionAnalyzer.getReference(FacesContext.getCurrentInstance().getELContext(), valueExpression);
//            logger.info(METHODNAME, "expressionString=", valueExpression.getExpressionString(), " valueReference=", valueReference );
//            if (valueReference != null) {
//                logger.info(METHODNAME, "valueReference.getProperty()=", valueReference.getProperty(), " valueReference=", valueReference );
//                logger.info(METHODNAME, "valueReference.getBase()=", valueReference.getBase());
//                cls = valueReference.getBase().getClass();
//                valueExpressionMap.put(valueExpression.hashCode(), cls);
//                logger.info(METHODNAME, "cls.getCanonicalName()=", cls.getCanonicalName());
//            }
//        }
        return cls;
    }     
    
    private static ValueExpression getValueExpression(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        return expressionFactory.createValueExpression(elContext, name, Object.class);
    }    

    private static void disableComponent(UIComponent uiComponent, List<String> js) {
        final String METHODNAME = "disableComponent ";
        String clientId = uiComponent.getClientId();
//        logger.info(METHODNAME, "clientId=", clientId, " ", uiComponent.getClass().getSimpleName());
        StringBuffer disableJs = new StringBuffer();
        String jQueryPrefix = "$(\"[id$='" + clientId;
        String disableSyntax = ".prop('disabled', false);";
        //String unbind = ".unbind('click');";
        String unbind = ".unbind();";
        String stateDisabled = ".addClass('ui-state-disabled');";
        String focusDisable = ".focus(function(){ this.blur();});";

        String disableCommon = jQueryPrefix + "']\")" + disableSyntax + 
                               jQueryPrefix + "']\")" + stateDisabled;                ;
                               
        // Blur out the first field that had focus, 
        // exclude Input mask as it fires on change which causes a formChanged event
        if (!(uiComponent instanceof InputMask)) {
            disableJs.append(jQueryPrefix + "']\")" + ".blur();");
        }
            
        if (uiComponent instanceof SelectOneMenu) {
            String subSearch = "div[class='ui-selectonemenu-trigger ui-state-default ui-corner-right";
            disableJs.append(jQueryPrefix + "_label']\")" + unbind + 
                             jQueryPrefix + "'] " + subSearch + "']\")" + unbind +                     
                             disableCommon +
                             jQueryPrefix + "_label']\")" + disableSyntax);
            // Disable triangle
            subSearch = "span[class='ui-icon ui-icon-triangle-1-s";
            disableJs.append(jQueryPrefix + "'] " + subSearch + "']\")" + stateDisabled); 

        }
        else {
            if (uiComponent instanceof SelectOneRadio) {
                //old primefaces 3, div[class='ui-radiobutton-box ui-widget ui-corner-all ui-radiobutton-relative ui-state-default
                String subSearch = "div[class='ui-radiobutton-box ui-widget ui-corner-all ui-state-default";
                disableJs.append(jQueryPrefix + "'] " + subSearch + "']\")" + unbind); 
                disableJs.append(jQueryPrefix + "'] " + subSearch + "']\")" + stateDisabled); 

            }
            else if (uiComponent instanceof SelectBooleanCheckbox) {
                String subSearch = "div[class='ui-chkbox-box ui-widget ui-corner-all ui-state-default";
                // unbind events from radiobutton selector (circle)
                disableJs.append(jQueryPrefix + "'] " + subSearch + "']\")" + unbind);                     
                disableJs.append(jQueryPrefix + "'] " + subSearch + " ui-state-active']\")" + unbind);                     
                disableJs.append(jQueryPrefix + "'] " + subSearch + "']\")" + stateDisabled);                     
                disableJs.append(jQueryPrefix + "'] " + subSearch + " ui-state-active']\")" + stateDisabled);                     
            
            }
            else if (uiComponent instanceof Calendar) {
                disableJs.append(jQueryPrefix + "_input']\")" + unbind); 
                disableJs.append(jQueryPrefix + "_input']\")" + focusDisable); 
                disableJs.append(jQueryPrefix + "_input']\")" + stateDisabled); 
                String subSearch = "button[type='button";
                disableJs.append(jQueryPrefix + "'] " + subSearch + "']\")" + unbind);
                disableJs.append(jQueryPrefix + "'] " + subSearch + "']\")" + focusDisable);
                disableJs.append(jQueryPrefix + "'] " + subSearch + "']\")" + stateDisabled);
                focusDisable = null;
                disableCommon = null;
            }
            else if (uiComponent instanceof PickList) {
                String pickListWrapper = "div[class='ui-picklist-list-wrapper";
                String pickListButtons = "div[class='ui-picklist-buttons'] div[class='ui-picklist-buttons-cell";
                String subSearch = "ul[class='ui-widget-content ui-picklist-list ui-picklist-source ui-corner-all ui-sortable";

                disableJs.append(jQueryPrefix + "'] " + pickListWrapper + "'] " + subSearch + "']\")" + unbind);
                disableJs.append(jQueryPrefix + "'] " + pickListWrapper + "'] " + subSearch + "']\")" + focusDisable);
                disableJs.append(jQueryPrefix + "'] " + pickListWrapper + "'] " + subSearch + "']\")" + stateDisabled);
                
                // unbind events from the picklist item
                String pickListItem = "li[class='ui-picklist-item ui-corner-all ui-sortable-handle";
                disableJs.append(jQueryPrefix + "'] " + pickListWrapper + "'] " + pickListItem + "']\")" + unbind);
                
                subSearch = "button[type='button";
                disableJs.append(jQueryPrefix + "'] " + pickListButtons + "'] " + subSearch + "']\")" + unbind);
                disableJs.append(jQueryPrefix + "'] " + pickListButtons + "'] " + subSearch + "']\")" + focusDisable);
                disableJs.append(jQueryPrefix + "'] " + pickListButtons + "'] " + subSearch + "']\")" + stateDisabled);

                subSearch = "ul[class='ui-widget-content ui-picklist-list ui-picklist-target ui-corner-all ui-sortable";
                disableJs.append(jQueryPrefix + "'] " + pickListWrapper + "'] " + subSearch + "']\")" + unbind);
                disableJs.append(jQueryPrefix + "'] " + pickListWrapper + "'] " + subSearch + "']\")" + focusDisable);
                disableJs.append(jQueryPrefix + "'] " + pickListWrapper + "'] " + subSearch + "']\")" + stateDisabled);
                
                focusDisable = null;
                disableCommon = null;
                logger.info(METHODNAME, "PickList disableJs=", disableJs);
            }
            else {
                disableJs.append(jQueryPrefix + "']\")" + unbind); 
            }
            
            if (disableCommon != null) {
                disableJs.append(disableCommon);
            }
            if (focusDisable != null) {
                disableJs.append(jQueryPrefix + "']\")" + focusDisable);
            }
        }

        if (!StringUtils.isEmpty(disableJs.toString())) {
            js.add(disableJs.toString());
        }
        
    }
    
    private static void secureUiComponent(UIComponent uiComponent, Map<String, List<PermissionType>> permissionAllowMap, Map<String, List<PermissionType>> permissionDenyMap, List<String> excludedValueExpressions, Map<String, String> altenateValueExpressionMap) {
        final String METHODNAME = "secureUiComponent ";
//        logger.logBegin(METHODNAME);
        long startTime = System.nanoTime();
        try {
            if (uiComponent != null) {
                if ((uiComponent instanceof EditableValueHolder)) {
//                    EditableValueHolder editableValueHolder = (EditableValueHolder) uiComponent;
//                    logger.info(METHODNAME,
//                            "uiComponent FOUND ", uiComponent.getClass().getCanonicalName(),
//                            " getClientId()= ", uiComponent.getClientId(),
//                            " getValue()=", editableValueHolder.getValue(),
//                            " getLocalValue()= ", editableValueHolder.getLocalValue(),
//                            " getSubmittedValue()= ", editableValueHolder.getSubmittedValue(),
//                            " isValid()= ", editableValueHolder.isValid());
                    ValueExpression valueExpression = uiComponent.getValueExpression("value");
                    if (valueExpression != null) {
                        boolean excludeSecurity = false;
                        String expressionString = valueExpression.getExpressionString();
                        if (excludedValueExpressions != null && excludedValueExpressions.contains(expressionString)) {
                            excludeSecurity = true;
                        }
                        if (!excludeSecurity) {
                            // Check if there is an alternate to use
                            String alternateExpression = altenateValueExpressionMap.get(expressionString);
                            if (alternateExpression != null) {
                                valueExpression = getValueExpression(alternateExpression);
                            }
                            Class dtoClass = getClass(valueExpression);
                            if (dtoClass != null) {
//                                logger.info(METHODNAME, "dtoClass=", dtoClass.getCanonicalName());
                                List<PermissionType> allowPermissions = permissionAllowMap.get(dtoClass.getCanonicalName());
                                if (allowPermissions != null) {
//                                    logger.info(METHODNAME, "allowPermissions.size()=", allowPermissions.size(), " ", dtoClass.getCanonicalName());
                                    boolean allowFull = allowPermissions.contains(PermissionType.FULL);
                                    boolean allowInsert = allowPermissions.contains(PermissionType.INSERT);
                                    boolean allowUpdate = allowPermissions.contains(PermissionType.UPDATE);
                                    boolean allowSelect = allowPermissions.contains(PermissionType.SELECT);
//                                    logger.info(METHODNAME, "allowFull=", allowFull, " allowInsert=", allowInsert, " allowUpdate=", allowUpdate, " allowSelect=", allowSelect);
                                    boolean disableComponent = !(allowFull || allowInsert || allowUpdate);
                                    if (disableComponent) {
//                                        logger.info(METHODNAME, "allow disableComponent=", disableComponent, " dtoClass=", dtoClass.getCanonicalName(), " uiComponent=", uiComponent);
                                        disableComponent(uiComponent);
                                    }
                                }
                                List<PermissionType> denyPermissions = permissionDenyMap.get(dtoClass.getCanonicalName());
                                if (denyPermissions != null) {
//                                    logger.info(METHODNAME, "denyPermissions.size()=", denyPermissions.size());
                                    boolean denyFull = denyPermissions.contains(PermissionType.FULL);
                                    boolean denyInsert = denyPermissions.contains(PermissionType.INSERT);
                                    boolean denyUpdate = denyPermissions.contains(PermissionType.UPDATE);
                                    boolean denySelect = denyPermissions.contains(PermissionType.SELECT);
                                    boolean disableComponent = (denyFull || denySelect || (denyInsert && denyUpdate));
//                                    logger.info(METHODNAME, "denyFull=", denyFull, " denyInsert=", denyInsert, " denyUpdate=", denyUpdate, " denySelect=", denySelect);
                                    if (disableComponent) {
//                                        logger.info(METHODNAME, "deny disableComponent=", disableComponent, " dtoClass=", dtoClass.getCanonicalName(), " uiComponent=", uiComponent);
                                        disableComponent(uiComponent);
                                    }
                                }
                            }
                        }
                    }
                }

                Iterator<UIComponent> facetsAndChildren = uiComponent.getFacetsAndChildren();
                while (facetsAndChildren.hasNext()) {
                    secureUiComponent(facetsAndChildren.next(), permissionAllowMap, permissionDenyMap, excludedValueExpressions, altenateValueExpressionMap);
                }
            }
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, startTime);                                                                            
//            logger.logEnd(METHODNAME);
        }
    }    
        
    private static void disableComponent(UIComponent uiComponent) {
        final String METHODNAME = "disableComponent ";
        logger.info(METHODNAME, "uiComponent=", uiComponent.getClientId());
        uiComponent.setValueExpression("disabled", valueExpression);

//        if (uiComponent instanceof InputText) {
//            ((InputText) uiComponent).setDisabled(true);
//        } else if (uiComponent instanceof SelectOneMenu) {
//            ((SelectOneMenu) uiComponent).setDisabled(true);
//        } else if (uiComponent instanceof InputMask) {
//            ((InputMask) uiComponent).setDisabled(true);
//        } else if (uiComponent instanceof SelectBooleanCheckbox) {
//            ((SelectBooleanCheckbox) uiComponent).setDisabled(true);
//        } else if (uiComponent instanceof SelectOneRadio) {
//            ((SelectOneRadio) uiComponent).setDisabled(true);
//        } else if (uiComponent instanceof PickList) {
//            ((PickList) uiComponent).setDisabled(true);
//            ((PickList) uiComponent).setItemDisabled(true);
//        }
        
    }

}
