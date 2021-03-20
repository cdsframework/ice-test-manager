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
package org.cdsframework.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;

/**
 *
 * @author HLN Consulting LLC
 */
public abstract class StringMaxLengthConverter implements Converter {
    private int maxLength = 0;
    protected LogUtils logger;

    public StringMaxLengthConverter() {
        this.logger = LogUtils.getLogger(StringMaxLengthConverter.class);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        final String METHODNAME = "getAsObject ";
//        logger.info(METHODNAME, "value=", value, " maxLength=", maxLength);
        String stringValue = null;
        // IE 9 & 10 pass up underscores for some odd reason
        if (value != null) {
            value = value.replaceAll("_", "").trim();
//            logger.debug(METHODNAME, "after value=", value);
            if (StringUtils.isEmpty(value)) {
                value = null;
            }
        }

        if (value != null && value.length() > 0 && value.length() < maxLength) {
//            logger.debug(METHODNAME, "ConverterException, value=", value, " value.length()=", value.length(), " value.trim().length()=", value.trim().length());
            throw new ConverterException();
        }
        stringValue = value;
//        logger.debug(METHODNAME, "stringValue=", stringValue, " stringValue=null", stringValue == null);
        return stringValue;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        final String METHODNAME = "getAsString ";
//        logger.debug(METHODNAME, "value=", value, " maxLength=", maxLength);

        String stringValue = null;
        if (value != null) {
            if (value instanceof String) {
                stringValue = (String) value;
                if (stringValue.length() > 0 && stringValue.length() < maxLength) {
//                    logger.debug(METHODNAME, "ConverterException, stringValue=", stringValue, "stringValue.length()=", stringValue.length(), " stringValue.trim().length()=", stringValue.trim().length());
                    throw new ConverterException();
                }
            }
        }
//        logger.debug(METHODNAME, "stringValue=", stringValue);
        return stringValue;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }


}
