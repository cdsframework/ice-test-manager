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

import org.cdsframework.util.UtilityMGR;

/**
 *
 * @param <E>
 * @author HLN Consulting, LLC
 */
public abstract class BaseEnumConverter<E extends Enum<E>> extends BaseConverter<E> {

    private Class<E> enumType;
    private E valueIfNull;

    public BaseEnumConverter() {
        super(BaseEnumConverter.class);
    }

    public BaseEnumConverter(Class loggerClassType, Class<E> enumType) {
        super(loggerClassType);
        this.enumType = enumType;
    }

    @Override
    protected void initializeMain() {
        initialize();
    }

    ;

    @Override
    protected void initialize() {
    }

    public E getValueIfNull() {
        return valueIfNull;
    }

    public void setValueIfNull(E valueIfNull) {
        this.valueIfNull = valueIfNull;
    }

    @Override
    protected E getObjectFromString(String inputString) {
        final String METHODNAME = "getObjectFromString ";
        logger.debug("getObjectFromString: ", inputString);
        E result = null;
        if (!UtilityMGR.getNO_OPTION_SELECT_TEXT().equals(inputString)) {
            if (inputString != null && !inputString.trim().isEmpty()) {
                try {
                    result = Enum.valueOf(enumType, inputString);
                    logger.debug("result: ", result);
                } catch (IllegalArgumentException e) {
                    logger.debug("Enum value not found: ", inputString, e);
                }
                if (result == null) {
                    result = getItemFromAltString(inputString);
                }
                if (result == null && getValueIfNull() != null) {
                    result = getValueIfNull();
                }
                if (result == null) {
                    logger.warn("Enum value not found: ", inputString);
                }
            } else {
                logger.warn("Enum value was null.");
                if (getValueIfNull() != null) {
                    result = getValueIfNull();
                }
            }
        } else {
            logger.debug(METHODNAME, "got no select option.");
        }
        return result;
    }

    // user override
    protected E getItemFromAltString(String inputString) {
        return null;
    }
}
