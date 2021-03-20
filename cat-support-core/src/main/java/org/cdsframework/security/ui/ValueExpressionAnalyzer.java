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

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.Locale;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.ValueExpression;
import javax.el.ValueReference;
import javax.el.VariableMapper;
import javax.faces.el.CompositeComponentExpressionHolder;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
public class ValueExpressionAnalyzer
{
    private static LogUtils logger = LogUtils.getLogger(ValueExpressionAnalyzer.class);
    public static ValueReference getReference(ELContext elContext, ValueExpression expression)
    {
        final String METHODNAME = "getReference ";
        InterceptingResolver resolver = new InterceptingResolver(elContext.getELResolver());

        try
        {
            expression.setValue(new InterceptingContext(elContext, resolver), null);
        }
        catch(ELException ele)
        {
            return null;
        }

        ValueReference reference = resolver.getValueReference();
        if(reference != null)
        {
            Object base = reference.getBase();
            if(base instanceof CompositeComponentExpressionHolder)
            {
                ValueExpression ve = ((CompositeComponentExpressionHolder) base).getExpression((String) reference.getProperty());
                if(ve != null)
                {
                    reference = getReference(elContext, ve);
                }
            }
        }
        return reference;
    }

    private static class InterceptingContext extends ELContext
    {
        private final ELContext context;
        private final ELResolver resolver;

        public InterceptingContext(ELContext context, ELResolver resolver)
        {
            this.context = context;
            this.resolver = resolver;
        }

        // punch in our new ELResolver
        @Override
        public ELResolver getELResolver()
        {
            return resolver;
        }

        // The rest of the methods simply delegate to the existing context

        @Override
        public Object getContext(Class key)
        {
            return context.getContext(key);
        }

        @Override
        public Locale getLocale()
        {
            return context.getLocale();
        }

        @Override
        public boolean isPropertyResolved()
        {
            return context.isPropertyResolved();
        }

        @Override
        public void putContext(Class key, Object contextObject)
        {
            context.putContext(key, contextObject);
        }

        @Override
        public void setLocale(Locale locale)
        {
            context.setLocale(locale);
        }

        @Override
        public void setPropertyResolved(boolean resolved)
        {
            context.setPropertyResolved(resolved);
        }

        @Override
        public FunctionMapper getFunctionMapper()
        {
            return context.getFunctionMapper();
        }

        @Override
        public VariableMapper getVariableMapper()
        {
            return context.getVariableMapper();
        }
    }

    private static class InterceptingResolver extends ELResolver
    {
        private final ELResolver delegate;
        private ValueReference valueReference;

        public InterceptingResolver(ELResolver delegate)
        {
            this.delegate = delegate;
        }

        public ValueReference getValueReference()
        {
            return valueReference;
        }

        // Capture the base and property rather than write the value
        @Override
        public void setValue(ELContext context, Object base, Object property, Object value)
        {
            if(base != null && property != null)
            {
                context.setPropertyResolved(true);
                valueReference = new ValueReference(base, property.toString());
            }
        }

        // The rest of the methods simply delegate to the existing context

        @Override
        public Object getValue(ELContext context, Object base, Object property)
        {
            return delegate.getValue(context, base, property);
        }

        @Override
        public Class<?> getType(ELContext context, Object base, Object property)
        {
            return delegate.getType(context, base, property);
        }

        @Override
        public boolean isReadOnly(ELContext context, Object base, Object property)
        {
            return delegate.isReadOnly(context, base, property);
        }

        @Override
        public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base)
        {
            return delegate.getFeatureDescriptors(context, base);
        }

        @Override
        public Class<?> getCommonPropertyType(ELContext context, Object base)
        {
            return delegate.getCommonPropertyType(context, base);
        }

    }
}