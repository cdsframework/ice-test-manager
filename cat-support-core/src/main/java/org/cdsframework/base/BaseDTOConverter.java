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

import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.util.ClassUtils;
import org.cdsframework.util.DTOUtils;
import java.util.List;
import org.cdsframework.util.UtilityMGR;

/**
 *
 * @param <T>
 * @author HLN Consulting, LLC
 */
public abstract class BaseDTOConverter<T extends BaseDTO> extends BaseConverter<T> {

    private BaseDTOList<T> baseDTOList;
    private Class<? extends BaseDTO> dtoClassType;

    public BaseDTOConverter() {
        super(BaseDTOConverter.class);
    }

    @Override
    protected void initializeMain() {
        try {
            dtoClassType = ClassUtils.getTypeArgument(BaseDTOConverter.class, getClass());
            initialize();
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
        }
    }

    public BaseDTOConverter(Class loggerClassType) {
        super(loggerClassType);
    }

    public void setBaseDTOList(BaseDTOList<T> baseDTOList) {
        this.baseDTOList = baseDTOList;
    }

    @Override
    protected T getObjectFromString(String inputString) {
        final String METHODNAME = "getObjectFromString ";
        T result = null;
        logger.logBegin(METHODNAME, inputString);
        Object primaryKey = null;
        try {
            if (!UtilityMGR.getNO_OPTION_SELECT_TEXT().equals(inputString)) {
                if (inputString != null && !inputString.trim().isEmpty()) {
                    // Get the primary key classes
                    List<Class> primaryKeyClasses = DTOUtils.getPrimaryKeyClasses(dtoClassType);
                    logger.trace("primaryKeyClasses=", primaryKeyClasses);
                    // Check to ensure we only have 1 primary key
                    if (primaryKeyClasses.size() != 1) {
                        throw new UnsupportedOperationException(METHODNAME + "There is only support for a "
                                + "single Primary Key class, size=" + primaryKeyClasses.size());
                    }

                    // Get the Primary Key Class
                    Class primaryKeyClass = primaryKeyClasses.get(0);
                    logger.trace("primaryKeyClass=", primaryKeyClass);
                    if (primaryKeyClass == String.class) {
                        primaryKey = inputString;
                    } else if (primaryKeyClass == Long.class || primaryKeyClass == long.class) {
                        primaryKey = Long.parseLong(inputString);
                    } else if (primaryKeyClass == Integer.class || primaryKeyClass == int.class) {
                        primaryKey = Integer.parseInt(inputString);
                    } else {
                        throw new UnsupportedOperationException("PrimaryKeyClass "
                                + primaryKeyClass.getName() + " is not supported");

                    }
                    logger.trace("primaryKey=", primaryKey);
                    result = baseDTOList.get(primaryKey);
                    if (logger.isTraceEnabled()) {
                        logger.trace(METHODNAME, "result: ", result != null ? result.getPrimaryKey() : result);
                    }
                } else {
                    logger.info("skipping processing of inputString: ", inputString);
                }
            } else {
                logger.debug(METHODNAME, "got no select option.");
            }
        } catch (Exception e) {
            logger.error("error parsing: ", inputString);
            logger.error(e);
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logEnd(METHODNAME);
        }
        return result;
    }

    public BaseDTOList<T> getBaseDTOList() {
        return baseDTOList;
    }
}