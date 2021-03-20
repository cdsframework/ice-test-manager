/**
 * The MTS support core project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
package org.cdsframework.validation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Provides a class for conveyance of a validation context.
 *
 * @author HLN Consulting, LLC
 */
public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

    /**
     * The start date.
     */
    private String start;
    /**
     * The stop date.
     */
    private String stop;

    /**
     * Returns the valid state flag.
     *
     * @param object
     * @param constraintValidatorContext
     * @return the valid state.
     */
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;

        try {
            Class clazz = object.getClass();
            Date startDate = null;
            Method startGetter = clazz.getMethod(getAccessorMethodName(start), new Class[0]);

            Object startGetterResult = startGetter.invoke(object);
            if (startGetterResult != null && startGetterResult instanceof Date) {
                startDate = (Date) startGetterResult;
            } else {
                return false;
            }

            Date endDate = null;
            Method endGetter = clazz.getMethod(getAccessorMethodName(stop), new Class[0]);

            Object endGetterResult = endGetter.invoke(object);
            if (endGetterResult != null && endGetterResult instanceof Date) {
                endDate = (Date) endGetterResult;
            } else {
                return false;
            }

            // Is it valid?
            valid = (startDate.before(endDate) || startDate.equals(endDate));
            // TODO: do we want to throw a general exception in the cases below?
        } catch (IllegalAccessException ex) {
        } catch (IllegalArgumentException ex) {
        } catch (InvocationTargetException ex) {
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        } finally {
        }

        return valid;
    }

    /**
     * Adds a constraint violation.
     *
     * @param context
     * @param message
     * @param field
     */
    private void addConstraintViolation(ConstraintValidatorContext context, String message, String field) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addNode(field).addConstraintViolation();
    }

    /**
     * @param property
     */
    private String getAccessorMethodName(String property) {
        StringBuilder builder = new StringBuilder("get");
        builder.append(Character.toUpperCase(property.charAt(0)));
        builder.append(property.substring(1));
        return builder.toString();
    }

    /**
     * Initializes the class.
     *
     * @param validateDateRange
     */
    public void initialize(DateRange validateDateRange) {
        try {
            start = validateDateRange.start();
            stop = validateDateRange.stop();

        } finally {
        }
    }
}
