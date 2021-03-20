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

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.cdsframework.util.LogUtils;

/**
 *
 * @param <T>
 * @author HLN Consulting, LLC
 */
public abstract class BaseConverter<T> implements Converter {

    protected LogUtils logger;
    boolean initialized = false;

    public BaseConverter() {
        logger = LogUtils.getLogger(BaseConverter.class);
    }

    public BaseConverter(Class loggerClassType) {
        logger = LogUtils.getLogger(loggerClassType);
    }

    @PostConstruct
    private void postConstructor() {
        if (!initialized) {
            initializeMain();
            initialized = true;
        }
    }

    protected abstract void initializeMain();

    protected abstract void initialize();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String inputString) {
        final String METHODNAME = "getAsObject ";
        if (logger.isDebugEnabled()) {
            logger.info("getAsObject called on: ", inputString);
        }
        if (inputString == null) {
            return null;
        }
        T item = null;
        if (inputString != null) {
            try {
                item = getObjectFromString(inputString);
            } catch (Exception e) {
                logger.error(METHODNAME + e.getMessage());
                logger.error(e);
            }
        }
        return item;
    }

    protected abstract T getObjectFromString(String inputString);

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        final String METHODNAME = "getAsString ";
        if (logger.isDebugEnabled()) {
            logger.info("getAsString called on: ", value, " (", value.getClass(), ")");
        }
        String result = null;
        if (value != null) {
            try {
                T item = (T) value;
                result = getStringFromObject(item);
            } catch (Exception e) {
                logger.error(METHODNAME + e.getMessage());
                logger.error(e);
            }
        }
        return result;
    }

    protected abstract String getStringFromObject(T item);
}
