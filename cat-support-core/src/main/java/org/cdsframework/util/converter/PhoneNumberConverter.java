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

import java.util.StringTokenizer;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@RequestScoped
public class PhoneNumberConverter implements Converter {

    LogUtils logger = LogUtils.getLogger(PhoneNumberConverter.class);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        final String METHODNAME = "getAsObject ";

        logger.debug(METHODNAME, "value=", value);
        if (value == null || (value.trim().length() == 0)) {
            return null;
        }
        String newValue = value.replaceAll("[^0-9|-]", "");
        logger.debug(METHODNAME, "newValue=", newValue);

        // IE10 sometime sends the mask "___-___-____"
        if (newValue.equals("--")) {
            return null;
        }
        String phoneNumber = "";
        boolean conversionError = false;

        int hyphenCount = 0;
        StringTokenizer hyphenTokenizer = new StringTokenizer(newValue, "-");
        while (hyphenTokenizer.hasMoreTokens()) {
            String token = hyphenTokenizer.nextToken();
            try {
                if (hyphenCount == 0) {
                    phoneNumber += token;
                }

                if (hyphenCount == 1) {
                    phoneNumber += token;
                }

                if (hyphenCount == 2) {
                    phoneNumber += token;
                }
                hyphenCount++;
            } catch (Exception exception) {
                conversionError = true;
            }
        }

        logger.debug(METHODNAME, "phoneNumber=", phoneNumber, "hyphenCount=", hyphenCount);

        if ((phoneNumber.length() > 0 && phoneNumber.length() < 10) || conversionError || hyphenCount != 3) {
            logger.debug(METHODNAME, "ConverterException");
            throw new ConverterException();
        }
        return phoneNumber;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        final String METHODNAME = "getAsString ";
        String phoneNumber = "";
        logger.debug(METHODNAME, "value=", value);
        if (value != null) {
            if (value instanceof String) {
                phoneNumber = (String) value;
                phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
                logger.debug(METHODNAME, "phoneNumber=", phoneNumber);
            }
        } else {
            logger.warn("getAsString: value null");
        }
        logger.debug(METHODNAME, "phoneNumber=", phoneNumber);
        if (phoneNumber.length() > 0 && phoneNumber.length() < 10) {
            logger.debug(METHODNAME, "ConverterException");
            throw new ConverterException();
        }
        return phoneNumber;
    }
}
