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

import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.cdsframework.util.DateUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
public class YearNotInFutureValidator implements ConstraintValidator<YearNotInFuture, Object> {

    @Override
    public void initialize(YearNotInFuture constraintAnnotation) {
        // nothing to do - no arguments to the YearNotInFuture annotation to support
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean result = false;
        if (value != null) {
            Integer newValue = null;
            if (value instanceof Integer) {
                newValue = (Integer) value;
            } else if (value instanceof String) {
                newValue = Integer.valueOf((String) value);
            } else if (value instanceof Date) {
                newValue = Integer.valueOf(DateUtils.getFormattedDate((Date) value, "yyyy"));
            } else {
                System.out.println("Unexpected object value: " + value.getClass());
            }
            if (newValue != null
                    && newValue <= Integer.valueOf(DateUtils.getFormattedDate(new Date(), "yyyy"))) {
                result = true;
            }
        }
        return result;
    }
}
