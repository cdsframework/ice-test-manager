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
import org.cdsframework.enumeration.ExceptionReason;
import org.cdsframework.util.StringUtils;

/**
 * Provides an exception for conveyance of an authorization error context.
 *
 * @author HLN Consulting, LLC
 */
@ApplicationException(rollback = true)
public class AuthorizationException extends BaseExceptionRollback {

    private String tableName;

    /**
     * Constructor initialized with exception reason enumeration value.
     *
     * @param reason
     */
    public AuthorizationException(ExceptionReason reason) {
        this(reason, null);
    }

    /**
     * Constructor initialized with exception reason enumeration value.
     *
     * @param reason
     * @param msg
     */
    public AuthorizationException(ExceptionReason reason, String message) {
        this(null, reason, message);
    }

    /**
     * Constructor initialized with exception reason enumeration value.
     *
     * @param msg
     */
    public AuthorizationException(String message) {
        this(null, null, message);
    }

    /**
     * Constructor initialized with a table enumeration value and an exception reason enumeration value.
     *
     * @param tableName
     * @param reason
     */
    public AuthorizationException(String tableName, ExceptionReason reason) {
        this(tableName, reason, null);
    }

    /**
     * Constructor initialized with a table enumeration value and an exception reason enumeration value.
     *
     * @param tableName
     * @param reason
     * @param msg
     */
    public AuthorizationException(String tableName, ExceptionReason reason, String message) {
        super(reason, message);
        this.tableName = tableName;
    }

    /**
     * Constructor initialized with a table enumeration value and an exception reason enumeration value.
     *
     * @param tableName
     * @param msg
     */
    public AuthorizationException(String tableName, String message) {
        this(tableName, null, message);
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (StringUtils.isEmpty(message)) {
            message = "";
            String reason = null;
            if (getReason() != null) {
                reason = getReason().name();
            }
            message = (!StringUtils.isEmpty(reason) ? "Reason: " + reason : "") ;
            if (!StringUtils.isEmpty(tableName)) {
                if (!StringUtils.isEmpty(message)) {
                    message += " ";
                }
                message += "Table: " + tableName;
            }
        }
        return message;
    }
    
    @Override
    public String getUserMessage() {
        String reason = null;
        if (getReason() != null) {
            reason = getReason().name();
        }
        
        String userMessage = "There was an Authorization error" + (!StringUtils.isEmpty(super.getMessage()) ? " Message: " + super.getMessage() : "")
                + (!StringUtils.isEmpty(tableName) ? " Table: " + tableName : "")
                + (!StringUtils.isEmpty(reason) ? " Reason: " + reason : "");
                
        return userMessage;
    }    
    
    public static AuthorizationException getAuthorizationException(Map<String, Object> propertyMap ) {
        String message = (String) propertyMap.get("message");
        String tableName = (String) propertyMap.get("tableName");
        String reason = (String) propertyMap.get("exceptionReason");
        ExceptionReason exceptionReason = null;
        if (reason != null) {
            exceptionReason = ExceptionReason.valueOf(reason);
        }
        return new AuthorizationException(tableName, exceptionReason, message);        
    }        
}
