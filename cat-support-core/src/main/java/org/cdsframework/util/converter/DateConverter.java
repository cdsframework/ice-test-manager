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

import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.exceptions.PastDateException;
import org.cdsframework.message.MessageMGR;
import org.cdsframework.util.DateUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;
import org.primefaces.component.calendar.Calendar;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@RequestScoped
public class DateConverter implements Converter {

    protected LogUtils logger;
    @Inject
    protected MessageMGR messageMGR;
    protected String dateFormatString = "MM/dd/yyyy";

    public DateConverter() {
        logger = LogUtils.getLogger(getClass());
    }    
    
    @PostConstruct
    protected void initialize() {
        messageMGR.setMessageBundle("org.cdsframework.util.converter.message");
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String inputDateString) {
        final String METHODNAME = "getAsObject ";
        logger.logBegin(METHODNAME);
        try {
            Date convertedDate = DateUtils.parseDateFromString(inputDateString, dateFormatString);
            logger.debug(METHODNAME, "convertedDate=", convertedDate.toString(), " convertedDate=", convertedDate.getClass().getSimpleName());
            Class cls = getClass();
            if (cls == DateConverter.class || cls == DateTimeConverter.class) {
                return convertedDate;
            } else {
                throw new IllegalArgumentException("Unknown converter class: " + getClass().getCanonicalName());
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.error("Message: ", illegalArgumentException.getMessage());
            String errorMsg;
            String format = dateFormatString;
            if (format.substring(format.length() - 1).equals("a")) {
                format = format.substring(0, format.length() - 1).trim() + " AM/PM";
            }
            Throwable cause = illegalArgumentException.getCause();
            String label = StringUtils.unCamelize(component.getId()).toLowerCase();
//            logger.debug(METHODNAME, "component.getClass().getSimpleName()=", component.getClass().getCanonicalName(), 
//                    " component.getId(=", component.getId(), " component.getClientId()=", component.getClientId());
            if (component instanceof Calendar) {
                Calendar pfCalendar = (Calendar) component;
                label = pfCalendar.getLabel();
            }

            if (cause instanceof PastDateException) {
                errorMsg = String.format("Invalid %s: Too far in the past. Must be a valid date in the format of " + format.toLowerCase() + ".", label);
            } else {
                errorMsg = String.format("Invalid %s: Must be a valid date in the format of " + format.toLowerCase() + ".",label);
            }
            ConverterException e = new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg, null));
            throw e;
        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        final String METHODNAME = "getAsString ";
        logger.logBegin(METHODNAME);
        String dateAsString = "";
        try {
            if (value != null) {
                dateAsString = DateUtils.getFormattedDate((Date) value, dateFormatString);
            }
        } finally {
            logger.logEnd(METHODNAME);
        }
        return dateAsString;
    }
}
