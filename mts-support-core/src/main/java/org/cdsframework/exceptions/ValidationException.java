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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.ejb.ApplicationException;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseExceptionRollback;
import org.cdsframework.enumeration.CoreErrorCode;
import org.cdsframework.util.BrokenRule;

/**
 * Provides an exception for conveyance of a validation error context.
 *
 * @author HLN Consulting, LLC
 */
@ApplicationException(rollback = true)
public class ValidationException extends BaseExceptionRollback {

    /**
     *  The list of broken rules.
     */
    private List<BrokenRule> brokenRules;

    public ValidationException(List<BrokenRule> brokenRules) {
        this(brokenRules, null);
    }    
    
    public ValidationException(List<BrokenRule> brokenRules, BaseDTO baseDTO) {
        super();
        
        //
        // Pass back the DTO's UUID in the brokenRule 
        // (used to identify the source object with Broken Rule
        //
        if (baseDTO != null) {
            UUID uuid = baseDTO.getUuid();
            if (uuid != null && brokenRules != null) {
                for (BrokenRule brokenRule : brokenRules) {
                    if (brokenRule.getUuid() == null) {
                        brokenRule.setUuid(uuid);
                    }
                }
            }
        }
        this.brokenRules = brokenRules;
        
    }    
    

    public ValidationException(BrokenRule brokenRule) {
        super();
        brokenRules = new ArrayList<BrokenRule>();
        brokenRules.add(brokenRule);
    }    

    /**
     * Constructor initialized with the values for a single broken rule.
     *
     * @param messageBundle
     * @param errorCode
     * @param reason
     * @param values
     */
    public ValidationException(String messageBundle, CoreErrorCode errorCode, String reason, Object[] values) {
        super(reason);
        BrokenRule brokenRule = new BrokenRule(messageBundle, errorCode, reason, values);
        List<BrokenRule> rules = new ArrayList<BrokenRule>();
        rules.add(brokenRule);
        this.brokenRules = rules;
    }

    /**
     * Constructor initialized with the values for a single broken rule.
     *
     * @param messageBundle
     * @param errorCode
     * @param values
     */
    public ValidationException(String messageBundle, CoreErrorCode errorCode, Object[] values) {
        super(errorCode.toString());
        BrokenRule brokenRule = new BrokenRule(messageBundle, errorCode, values);
        List<BrokenRule> rules = new ArrayList<BrokenRule>();
        rules.add(brokenRule);
        this.brokenRules = rules;
    }

    /**
     * Constructor initialized with the values for a single broken rule.
     *
     * @param messageBundle
     * @param errorCode
     */
    public ValidationException(String messageBundle, CoreErrorCode errorCode) {
        super(errorCode.toString());
        BrokenRule brokenRule = new BrokenRule(messageBundle, errorCode);
        List<BrokenRule> rules = new ArrayList<BrokenRule>();
        rules.add(brokenRule);
        this.brokenRules = rules;
    }

    /**
     * Constructor initialized with the values for a single broken rule.
     *
     * @param errorCode
     * @param reason
     * @param values
     */
    public ValidationException(CoreErrorCode errorCode, String reason, Object[] values) {
        super(reason);
        BrokenRule brokenRule = new BrokenRule(errorCode, reason, values);
        List<BrokenRule> rules = new ArrayList<BrokenRule>();
        rules.add(brokenRule);
        this.brokenRules = rules;
    }

    /**
     * Constructor initialized with the values for a single broken rule - no values given.
     *
     * @param errorCode
     * @param reason
     */
    public ValidationException(CoreErrorCode errorCode, String reason) {
        this(errorCode, reason, new Object[]{});
    }

    /**
     * Constructor initialized with the values for a single broken rule - no errorCode or values given.
     *
     * @param reason
     */
    public ValidationException(String reason) {
        this(null, reason, new Object[]{});
    }

    /**
     * Returns the list of broken rules.
     *
     * @return the list of broken rules.
     */
    public List<BrokenRule> getBrokenRules() {
        return this.brokenRules;
    }

    @Override
    public String getMessage() {
        String retValue = "";
        String messageSeparator = "";
        for (int i = 0; i < brokenRules.size(); i++) {
            retValue += (messageSeparator + brokenRules.get(i).toString());
            messageSeparator = ", ";
        }
        return retValue.trim();
    }

    @Override
    public String getUserMessage() {
        return getMessage();
    }

    public boolean isMultipleErrors() {
        boolean multipleErrors = false;
        if (this.brokenRules != null)
            multipleErrors = this.brokenRules.size() > 1;
        return multipleErrors;
    }
    
    public static ValidationException getValidationException(Map<String, Object> propertyMap, List<BrokenRule> brokenRules ) {
        String message = (String) propertyMap.get("message");
        ValidationException validationException  = null;
        if (brokenRules != null && !brokenRules.isEmpty()) {
            validationException = new ValidationException(brokenRules);
        }
        else {
            validationException = new ValidationException(message);
        }
        return validationException;
    }
    
}
