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
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseExceptionRollback;
import org.cdsframework.enumeration.ConstraintViolation;

/**
 * Provides an exception for conveyance of a constraint violation error context.
 *
 * @author HLN Consulting, LLC
 */
@ApplicationException(rollback = true)
public class ConstraintViolationException extends BaseExceptionRollback {

    /**
     * A table name.
     */
    private String tableName;
    /**
     * The duplicate BaseDTO instance.
     */
    private BaseDTO duplicateDTO;

    /*
     * A Constraint violation enum value
     */
    private ConstraintViolation constraintViolation;

    /*
     * A Constraint key
     */
    private String constraintKey = null;
    /**
     * The database Id
     */
    private String databaseId;

    /**
     * Constructor initialized with a Table enumerated value and an error String message value.
     *
     * @param tableName a table name.
     * @param message an error String message value.
     */
    public ConstraintViolationException(String tableName, String message) {
        this(tableName, message, ConstraintViolation.UNSET);
    }

    /**
     * Constructor initialized with a Table enumerated value and an error String message value and a throwable initCause.
     *
     * @param databaseId the id of the database this error is from
     * @param tableName a table name.
     * @param message an error String message value.
     * @param cause
     */
    public ConstraintViolationException(String databaseId, String tableName, String message, Throwable cause) {
        this(databaseId, tableName, message, ConstraintViolation.UNSET, cause);
    }

    /**
     * Constructor initialized with a Table enumerated value and an error String message value and a duplicate enum value.
     *
     * @param tableName a table name.
     * @param message an error String message value.
     * @param constraintViolation
     */
    public ConstraintViolationException(String tableName, String message, ConstraintViolation constraintViolation) {
        this(null, tableName, message, constraintViolation, null);
    }

    /**
     * Constructor initialized with a Table enumerated value and an error String message value and a duplicate enum value.
     *
     * @param databaseId  the id of the database this error is from
     * @param tableName a table name.
     * @param message an error String message value.
     * @param constraintViolation
     */
    public ConstraintViolationException(String databaseId, String tableName, String message, ConstraintViolation constraintViolation, Throwable cause) {
        super(message, cause);
        this.databaseId = databaseId;
        this.tableName = tableName;
        this.constraintViolation = constraintViolation;
        setConstraintViolation(message);
    }

    /**
     * Returns the duplicate BaseDTO.
     *
     * @return the duplicate BaseDTO.
     */
    public BaseDTO getDuplicateDTO() {
        return duplicateDTO;
    }

    /**
     * Sets the duplicate BaseDTO.
     *
     * @param duplicateDTO the duplicate BaseDTO.
     */
    public void setDuplicateDTO(BaseDTO duplicateDTO) {
        this.duplicateDTO = duplicateDTO;
    }

    /**
     * Returns the Constraint Violation
     *
     * @return
     */
    public ConstraintViolation getConstraintViolation() {
        return constraintViolation;
    }

    /**
     * Returns the table name.
     *
     * @return the table name.
     */
    public String getTableName() {
        return tableName;
    }

    public String getConstraintKey() {
        return constraintKey;
    }

    @Override
    public String getMessage() {
        return "A ConstraintViolationException has occured on Table: " + tableName + ", ConstraintViolation: " 
                + constraintViolation + "; Message: " + super.getMessage();
    }

    @Override
    public String getUserMessage() {
        return "A " + this.constraintViolation.getLabel() + " was identified on table " + tableName + ". ";
    }

    /**
     * Get the value of databaseId
     *
     * @return the value of databaseId
     */
    public String getDatabaseId() {
        return databaseId;
    }

    /**
     * Set the value of databaseId
     *
     * @param databaseId new value of databaseId
     */
    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    /*
     * Sets the Constraint Violation if unset
     */
    private void setConstraintViolation(String message ) {
        String messageToParse = message.toUpperCase();
        if (this.constraintViolation == ConstraintViolation.UNSET) {
            if (messageToParse.indexOf("ORA-00001: UNIQUE CONSTRAINT") >= 0) {
                this.constraintViolation = ConstraintViolation.UNIQUE;
                this.constraintKey = getConstraintKey(messageToParse, "UNIQUE CONSTRAINT (", ")");
            }
            else if ( (messageToParse.indexOf("ORA-02291: INTEGRITY CONSTRAINT") >= 0) ||
                      (messageToParse.indexOf("ORA-02292: INTEGRITY CONSTRAINT") >= 0)) {
                this.constraintViolation = ConstraintViolation.FOREIGN;
                this.constraintKey = getConstraintKey(messageToParse, "INTEGRITY CONSTRAINT (", ")");
            }
            else if (messageToParse.indexOf("ORA-02290: CHECK CONSTRAINT") >= 0) {
                this.constraintViolation = ConstraintViolation.CHECK;
                this.constraintKey = getConstraintKey(messageToParse, "CHECK CONSTRAINT (", ")");
            }
            else if (messageToParse.indexOf("ORA-01400: CANNOT INSERT NULL") >= 0) {
                this.constraintViolation = ConstraintViolation.NULL;
                // Need to parse out this: cannot insert NULL into ("DOH"."WS_ACCOUNT"."ACCOUNT_NAME")
                //this.constraintKey = getConstraintKey(messageToParse, "CANNOT INSERT NULL (", ")");
            }
            // derby unique or private key violations
            else if (messageToParse.indexOf("DUPLICATE KEY VALUE IN A UNIQUE OR PRIMARY KEY CONSTRAINT") >= 0) {
                this.constraintViolation = ConstraintViolation.UNIQUE;
                this.constraintKey = getConstraintKey(messageToParse, "INDEX IDENTIFIED BY '", "' DEFINED ON");
                if (this.constraintKey.indexOf(this.tableName.toUpperCase() + ".SQL") >= 0) {
                    this.constraintKey = this.tableName.toUpperCase() + ".PRIMARY_KEY_VIOLATION";
                }
            }
        }
    }

    private String getConstraintKey(String messageToParse, String searchStart, String searchEnd) {
        String key = null;
        int searchKeyLen = searchStart.length();
        int keyStart = messageToParse.indexOf(searchStart);
        if (keyStart > 0) {
            messageToParse = messageToParse.substring(keyStart);
            int keyEnd = messageToParse.indexOf(searchEnd);
            // Get the key
            if (keyEnd > 0)
                key = messageToParse.substring(searchStart.length(), keyEnd);
            else
                key = messageToParse.substring(searchStart.length());
            key = this.tableName.toUpperCase() + "." + key;
        }
        else {
            key = this.constraintKey;
        }
        return key;

    }

    public static ConstraintViolationException getConstraintViolationException(Map<String, Object> propertyMap ) {
        String message = (String) propertyMap.get("message");
        String tableName = (String) propertyMap.get("tableName");
        String databaseId = (String) propertyMap.get("databaseId");
        String constraintViolation = (String) propertyMap.get("constraintViolation");
        ConstraintViolationException constraintViolationException = new ConstraintViolationException(databaseId, tableName, message, ConstraintViolation.valueOf(constraintViolation), null);
        return constraintViolationException;
    }
    
    @Override
    public Map<String, Object> getPropertyMap() {
        Map<String, Object> propertyMap = super.getPropertyMap();
        propertyMap.put("tableName", getTableName());
        propertyMap.put("databaseId", getDatabaseId());
        propertyMap.put("constraintViolation", getConstraintViolation().name());
        return propertyMap;
    }
    
}
