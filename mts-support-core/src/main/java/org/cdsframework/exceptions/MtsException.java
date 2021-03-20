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
package org.cdsframework.exceptions;

import java.util.Map;
import javax.ejb.ApplicationException;
import org.cdsframework.base.BaseExceptionRollback;
import org.cdsframework.util.StringUtils;

/**
 * Provides an exception for conveyance of an MtsException error context.
 *
 * @author HLN Consulting, LLC
 */
@ApplicationException(rollback = true)
public class MtsException extends BaseExceptionRollback {

    private String userMessage;

    /**
     * Constructor initialized with an exception message String value.
     *
     * @param message an exception message String value.
     */
    public MtsException(String message) {
        this(message, null, null);
    }

    /**
     * Constructor initialized with an exception message String value and a cause instance.
     *
     * @param message an exception message String value.
     * @param cause the underlying cause.
     */
    public MtsException(String message, Throwable cause) {
        this(message, null, cause);
    }

    public MtsException(String message, String userMessage, Throwable cause) {
        super(message, cause);
        this.userMessage = userMessage;
    }

    @Override
    public String getUserMessage() {
        String userMessage = "There was an MTS Internal System error" + (!StringUtils.isEmpty(super.getMessage()) ? " Message: " + super.getMessage() : "")
                + (!StringUtils.isEmpty(this.userMessage) ? " User Message: " + this.userMessage : "");

        return userMessage;
    }      
    
    public static MtsException getMtsException(Map<String, Object> propertyMap ) {
        String message = (String) propertyMap.get("message");
        String userMessage = (String)  propertyMap.get("userMessage");
        return new MtsException(message, userMessage, null);
    }
    
}
