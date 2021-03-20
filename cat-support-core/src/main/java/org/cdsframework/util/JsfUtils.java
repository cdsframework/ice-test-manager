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

import java.util.Collection;
import java.util.Iterator;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FactoryFinder;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.context.FacesContextWrapper;
import javax.faces.context.PartialViewContext;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cdsframework.enumeration.LogLevel;
import org.primefaces.context.RequestContext;

/**
 *
 * @author HLN Consulting, LLC
 */
public class JsfUtils {

    private static final LogUtils logger = LogUtils.getLogger(JsfUtils.class);

    public static void resetForm(UIComponent uiComponent) {
        final String METHODNAME = "resetForm ";
        logger.logBegin(METHODNAME);
        try {
            if (uiComponent != null) {
                logger.debug(METHODNAME + "Beginning clear on getClientId()= ", uiComponent.getClientId(),
                        " getSimpleName()= ", uiComponent.getClass().getCanonicalName(),
                        " getId()= " + uiComponent.getClientId());
                clearUIComponent(uiComponent);
                Iterator<UIComponent> facetsAndChildren = uiComponent.getFacetsAndChildren();
                while (facetsAndChildren.hasNext()) {
                    resetForm(facetsAndChildren.next());
                }
            }
        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    public static void clearUIComponent(UIComponent uiComponent) {
        final String METHODNAME = "clearUIComponent ";
        if (!(uiComponent instanceof EditableValueHolder)) {
            logger.debug(METHODNAME,
                    "uiComponent not a EditableValueHolder: ", uiComponent.getClass().getCanonicalName(),
                    " getClientId()= ", uiComponent.getClientId());
            return;
        }
        EditableValueHolder editableValueHolder = (EditableValueHolder) uiComponent;
        if (logger.isDebugEnabled()) {
            logger.info(METHODNAME,
                    "uiComponent FOUND ", uiComponent.getClass().getCanonicalName(),
                    " getClientId()= ", uiComponent.getClientId(),
                    " getValue()=", editableValueHolder.getValue(),
                    " getLocalValue()= ", editableValueHolder.getLocalValue(),
                    " getSubmittedValue()= ", editableValueHolder.getSubmittedValue(),
                    " isValid()= ", editableValueHolder.isValid());
        }
        editableValueHolder.resetValue();
        // Fixes Cancel Dialog and Open with another dto object, values were not rendered
//        editableValueHolder.setValid(true);
//        editableValueHolder.setSubmittedValue(null);
//        editableValueHolder.setValue(null);
    }

    public static UIForm getUIForm(UIComponent uiComponent) {
        final String METHODNAME = "getUIForm ";
        logger.logBegin(METHODNAME);
        UIForm uiForm = null;
        try {
            if (uiComponent != null) {
                logger.debug(METHODNAME + "getClientId()= ", uiComponent.getClientId(),
                        " getSimpleName()= ", uiComponent.getClass().getSimpleName(),
                        " getId()= " + uiComponent.getId());
                UIComponent parentComponent = uiComponent.getParent();
                if (parentComponent != null) {
                    logger.debug(METHODNAME + "Parent getClientId()= ", parentComponent.getClientId(),
                            " getSimpleName()= ", parentComponent.getClass().getSimpleName(),
                            " getId()= " + parentComponent.getId());
                }

                if (parentComponent instanceof UIForm) {
                    uiForm = (UIForm) parentComponent;
                    logger.debug(METHODNAME + "Found uiForm getClientId()= ", uiForm.getClientId(),
                            " getSimpleName()= ", uiForm.getClass().getSimpleName(),
                            " getId()= " + uiForm.getId());

                } else {
                    uiForm = getUIForm(parentComponent);
                }
            }
        } finally {
            logger.logEnd(METHODNAME);
        }
        return uiForm;
    }

    public static void setUpdateIds(String caller, String... ids) {
        final String METHODNAME = "setUpdateIds ";
        long start = System.nanoTime();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (requestContext != null) {
            FacesContext currentInstance = FacesContext.getCurrentInstance();
            if (currentInstance != null) {
                PartialViewContext partialViewContext = currentInstance.getPartialViewContext();
                if (partialViewContext != null) {
                    Collection<String> renderIds = partialViewContext.getRenderIds();
                    for (String id : ids) {
                        if (id != null) {
//                            logger.info(METHODNAME, "caller=", caller, " id=", id);
                            if (renderIds == null) {
                                logger.warn(caller, "renderIds was null!");
                                requestContext.update(id);
                            } else if (!renderIds.contains(id)) {
                                requestContext.update(id);
                            } else {
                                logger.debug(caller, "submitted dup id: ", id);
                            }
                        }
                    }
                    if (logger.isDebugEnabled() && renderIds != null) {
                        logger.info(caller, "\"", StringUtils.getStringFromArray(renderIds.toArray(new String[]{}), ", "), "\"");
                    }
                    logger.logDuration(LogLevel.DEBUG, METHODNAME, start);                                                                            
                } else {
                    logger.error(caller, "partialViewContext was null!");
                }
            } else {
                logger.error(caller, "currentInstance was null!");
            }
        } else {
            logger.error(caller, "requestContext was null!");
        }
    }

    public static void resetEditableValueHolder() {
        final String METHODNAME = "resetEditableValueHolder ";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        PartialViewContext partialViewContext = facesContext.getPartialViewContext();
        Collection<String> renderIds = partialViewContext.getRenderIds();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        for (String renderId : renderIds) {
            UIComponent component = viewRoot.findComponent(renderId);
            if (component instanceof EditableValueHolder) {
                logger.info(METHODNAME + "component.getClientId()=" + component.getClientId() + " component.getClass().getName()=" + component.getClass().getName());
                EditableValueHolder input = (EditableValueHolder) component;
                input.resetValue();
            }
        }
    }

    public static MethodExpression createMethodExpression(String expression, Class<?> expectedReturnType, Class<?>... expectedParamTypes) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getExpressionFactory().createMethodExpression(
                facesContext.getELContext(), expression, expectedReturnType, expectedParamTypes);
    }

    public static ValueExpression createValueExpression(String expression, Class<?> expectedType) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getExpressionFactory().createValueExpression(
                facesContext.getELContext(), expression, expectedType);
    }

    public static FacesContext getFacesContext(HttpServletRequest request, HttpServletResponse response) {
        // Get current FacesContext.
        FacesContext facesContext = FacesContext.getCurrentInstance();

        // Check current FacesContext.
        if (facesContext == null) {

            // Create new Lifecycle.
            LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
            Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

            // Create new FacesContext.
            FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
            facesContext = contextFactory.getFacesContext(
                    request.getSession().getServletContext(), request, response, lifecycle);

            // Create new View.
            UIViewRoot view = facesContext.getApplication().getViewHandler().createView(
                    facesContext, "");
            facesContext.setViewRoot(view);

            // Set current FacesContext.
            FacesContextWrapper.setCurrentInstance(facesContext);
        }

        return facesContext;
    }

    // Helpers -----------------------------------------------------------------------------------
    // Wrap the protected FacesContext.setCurrentInstance() in a inner class.
    private static abstract class FacesContextWrapper extends FacesContext {

        protected static void setCurrentInstance(FacesContext facesContext) {
            FacesContext.setCurrentInstance(facesContext);
        }
    }
}
