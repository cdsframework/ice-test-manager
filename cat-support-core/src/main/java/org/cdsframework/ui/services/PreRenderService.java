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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.util.LogUtils;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputmask.InputMask;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.picklist.PickList;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.selectoneradio.SelectOneRadio;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class PreRenderService implements Serializable {

    private static final long serialVersionUID = 2722343769986616160L;
    protected static LogUtils logger = LogUtils.getLogger(PreRenderService.class);
    private final Map<String, Boolean> formIdMap = new HashMap<String, Boolean>();

    public void preRenderComponent(ComponentSystemEvent event) {
        final String METHODNAME = "preRenderComponent ";
        long startTime = System.nanoTime();
        HtmlForm htmlForm = (HtmlForm) event.getComponent();
//        logger.info(METHODNAME, "htmlForm.getClientId()=", htmlForm.getClientId(), " Id=", htmlForm.getId());
        String formClientId = htmlForm.getClientId();
//        logger.info(METHODNAME, "formIdMap.size())=", formIdMap.size(), " formIdMap=", formIdMap);
        if (!formIdMap.containsKey(formClientId)) {
            for (UIComponent child : htmlForm.getChildren()) {
                preRenderUIComponent(formClientId, child);
            }
            formIdMap.put(formClientId, true);
        } else {
//            logger.info(METHODNAME, "formClientId=",formClientId, " processed");
        }
        logger.logDuration(LogLevel.DEBUG, METHODNAME, startTime);                                                                            
    }

    private static void preRenderUIComponent(String formClientId, UIComponent uiComponent) {
        final String METHODNAME = "preRenderUIComponent ";
        long startTime = System.nanoTime();
        try {
            if (uiComponent != null) {
                if ((uiComponent instanceof EditableValueHolder)) {
//                    EditableValueHolder editableValueHolder = (EditableValueHolder) uiComponent;
//                    logger.debug(METHODNAME,
//                            "uiComponent FOUND ", uiComponent.getClass().getCanonicalName(),
//                            " getClientId()= ", uiComponent.getClientId(),
//                            " getValue()=", editableValueHolder.getValue(),
//                            " getLocalValue()= ", editableValueHolder.getLocalValue(),
//                            " getSubmittedValue()= ", editableValueHolder.getSubmittedValue(),
//                            " isValid()= ", editableValueHolder.isValid());
                    ValueExpression valueExpression = uiComponent.getValueExpression("value");
//                    logger.debug(METHODNAME, "valueExpression=", valueExpression);
//                    logger.debug(METHODNAME, "uiComponent.getParent().getClass().getCanonicalName()=", uiComponent.getParent().getClass().getCanonicalName());

//                    if (!(uiComponent.getParent() instanceof CellEditor)) {
                    if (valueExpression != null) {
                        boolean byPassSetOnChange = false;

                        // check to see if id ends with bypass suffix
                        if (uiComponent.getClientId().endsWith(UIConstants.ID_SUFFIX_BYPASS)) {
                            byPassSetOnChange = true;
                        }

                        if (!byPassSetOnChange) {
                            // check the composite component for the bypass style class
                            UIComponent compositeComponentParent = UIComponent.getCompositeComponentParent(uiComponent);
                            if (compositeComponentParent != null) {
                                for (Entry<String, Object> entry : compositeComponentParent.getAttributes().entrySet()) {
                                    Object value = entry.getValue();
                                    if (value != null && value instanceof String) {
//                                    logger.info(METHODNAME, "compositeComponentParent ", entry.getKey(), " = ", entry.getValue());
                                        if (((String) value).contains(UIConstants.STYLE_CLASS_BYPASS)) {
                                            byPassSetOnChange = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if (!byPassSetOnChange) {
                            Object oStyleClass = uiComponent.getAttributes().get("styleClass");
//                            logger.info(METHODNAME, "oStyleClass=", oStyleClass);
                            // Flag to bypass setOnChange logic
                            if (oStyleClass != null && ((String) oStyleClass).contains(UIConstants.STYLE_CLASS_BYPASS)) {
                                byPassSetOnChange = true;
                            }
                        }
//                            logger.info(METHODNAME, byPassSetOnChange);
                        if (!byPassSetOnChange) {
                            if (uiComponent instanceof HtmlInputText) {
                                setOnChange(formClientId, (HtmlInputText) uiComponent);
                            } else if (uiComponent instanceof InputTextarea) {
                                setOnChange(formClientId, (InputTextarea) uiComponent);
                            } else if (uiComponent instanceof Calendar) {
                                setOnChange(formClientId, (Calendar) uiComponent);
                            } else if (uiComponent instanceof SelectOneMenu) {
                                setOnChange(formClientId, (SelectOneMenu) uiComponent);
                            } else if (uiComponent instanceof InputMask) {
                                setOnChange(formClientId, (InputMask) uiComponent);
                            } else if (uiComponent instanceof SelectBooleanCheckbox) {
                                setOnChange(formClientId, (SelectBooleanCheckbox) uiComponent);
                            } else if (uiComponent instanceof SelectOneRadio) {
                                setOnChange(formClientId, (SelectOneRadio) uiComponent);
                            } else if (uiComponent instanceof PickList) {
                                setOnChange(formClientId, (PickList) uiComponent);
                            }
                        }
                    }
                }

                Iterator<UIComponent> facetsAndChildren = uiComponent.getFacetsAndChildren();
                while (facetsAndChildren.hasNext()) {
                    preRenderUIComponent(formClientId, facetsAndChildren.next());
                }
            }
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, startTime);                                                                            
        }
    }

    private static void setOnChange(String formClientId, HtmlInputText component) {
        component.setOnchange(getOnChange(formClientId, component, component.getOnchange()));
    }

    private static void setOnChange(String formClientId, Calendar component) {
        component.setOnchange(getOnChange(formClientId, component, component.getOnchange()));
    }

    private static void setOnChange(String formClientId, InputTextarea component) {
        component.setOnchange(getOnChange(formClientId, component, component.getOnchange()));
    }

    private static void setOnChange(String formClientId, HtmlSelectOneMenu component) {
        component.setOnchange(getOnChange(formClientId, component, component.getOnchange()));
    }

    private static void setOnChange(String formClientId, HtmlSelectBooleanCheckbox component) {
        component.setOnchange(getOnChange(formClientId, component, component.getOnchange()));
    }

    private static void setOnChange(String formClientId, HtmlSelectOneRadio component) {
        component.setOnchange(getOnChange(formClientId, component, component.getOnchange()));
    }

    private static void setOnChange(String formClientId, PickList component) {
        component.setOnTransfer(getOnChange(formClientId, component, component.getOnTransfer()));
    }

    private static String getOnChange(String formClientId, UIComponent component, String prevOnChange) {
        final String METHODNAME = "getOnChange ";

        String onChange = UIConstants.FORM_CHANGED_JS + "('" + formClientId + "', true); console.log('"
                + component.getClientId() + "');";
//        logger.info(METHODNAME, "onChange=", onChange);
//        logger.info(METHODNAME, "prevOnChange=", prevOnChange);

        if (prevOnChange != null) {
            int indexOf = formClientId.indexOf(":");
            if (indexOf > 0) {
                String rcOnChange = formClientId.substring(0, indexOf) + "RC";
                String[] onChangeList = prevOnChange.split(" ");
                prevOnChange = "";
                for (String onChangeItem : onChangeList) {
                    if (onChangeItem.indexOf(rcOnChange) >= 0) {
                        // Until these are corrected then RC command will not be included
                        prevOnChange += component.getId() + "RC();";
                    } else {
                        prevOnChange += onChangeItem;
                    }
                }
            }

            if (prevOnChange.indexOf(onChange) < 0) {
//                logger.info(METHODNAME, "DOES NOT contain");
                onChange += prevOnChange;
            } else {
//                logger.info(METHODNAME, "DOES contain");
                onChange = prevOnChange;
            }
        }

//        logger.info(METHODNAME, "onChange=", onChange);
        return onChange;
    }

    public void preRenderView(ComponentSystemEvent event) {
        final String METHODNAME = "preRenderView ";
        logger.info(METHODNAME, "ValidationFailed=", FacesContext.getCurrentInstance().isValidationFailed());
        long startTime = System.nanoTime();
        UIComponent component = event.getComponent();
        for (UIComponent child : component.getChildren()) {
            preRenderUIComponent(child);
        }
        logger.logDuration(LogLevel.DEBUG, METHODNAME, startTime);                                                                            
    }

    private static void preRenderUIComponent(UIComponent uiComponent) {
        final String METHODNAME = "preRenderUIComponent ";
        if (uiComponent != null) {
            logger.info(METHODNAME,
                    "uiComponent FOUND ", uiComponent.getClass().getSimpleName(), " getClientId()= ", uiComponent.getClientId());
            if (uiComponent instanceof HtmlForm) {
                String formClientId = uiComponent.getClientId();
                for (UIComponent child : uiComponent.getChildren()) {
                    preRenderUIComponent(formClientId, child);
                }
            }
            Iterator<UIComponent> facetsAndChildren = uiComponent.getFacetsAndChildren();
            while (facetsAndChildren.hasNext()) {
                preRenderUIComponent(facetsAndChildren.next());
            }
        }
    }

}
