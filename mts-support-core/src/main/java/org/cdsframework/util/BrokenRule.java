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
package org.cdsframework.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintViolation;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.cdsframework.enumeration.CoreErrorCode;

/**
 * Provides a class for conveyance of a broken rule context.
 *
 * @author HLN Consulting, LLC
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlRootElement(name = "BrokenRule")
public class BrokenRule implements Serializable {

    /**
     * The serializable class UID.
     */
    private static final long serialVersionUID = -6471435157663039098L;
    /**
     * The message key.
     */
    private String messageKey;
    /**
     * The exception reason.
     */
    private String reason;
    /**
     * The sequence.
     */
    private int sequence;
    /**
     * The values
     */
    private Object[] values;
    /**
     * The messageBundle reference to use for the messageKey lookup.
     */
    private String messageBundle;
    
    private UUID uuid;
    
    /**
     * Pattern used to tease out a message bundle and key from the message property of a ConstraintViolation class
     */
    private static Pattern messageBundleKeyPattern = Pattern.compile("\\{([a-zA-Z0-9\\.]+):([a-zA-Z0-9\\.]+)\\}");
    private static Pattern messageKeyPattern = Pattern.compile("\\{([a-zA-Z0-9\\.]+)\\}");    
    
    private String[] inputIds;
    
    public BrokenRule() {
    }
    
    
    /**
     * Construct the broken rule with an ErrorCode, an error message.
     *
     * @param errorCode an enumeration used to track errors.
     * @param reason a human readable error message.
     */
    public BrokenRule(CoreErrorCode errorCode, String reason) {
        this(null, (errorCode != null ? errorCode.toString() : null), reason, 0, (Object[]) null, (String[]) null);
    }

    /**
     * Construct the broken rule with an ErrorCode, an error message and a set of values.
     *
     * @param errorCode an enumeration used to track errors.
     * @param reason a human readable error message.
     * @param values a list of substitution values for a formatted message.
     * @param inputIds list of form input ids to highlight the form input field in error (use DTO propertyName)
     * 
     * 
     */
    public BrokenRule(CoreErrorCode errorCode, String reason, Object[] values, String[] inputIds) {
        this(null, (errorCode != null ? errorCode.toString() : null), reason, 0, values, inputIds);
    }

    
    /**
     * Construct the broken rule with an ErrorCode, an error message and a set of values.
     *
     * @param errorCode an enumeration used to track errors.
     * @param reason a human readable error message.
     * @param values a list of substitution values for a formatted message.
     */
    public BrokenRule(CoreErrorCode errorCode, String reason, Object[] values) {
        this(null, (errorCode != null ? errorCode.toString() : null), reason, 0, values, (String[]) null);
    }

    /**
     * Construct the broken rule with an ErrorCode, an error message and a set of input ids.
     *
     * @param errorCode an enumeration used to track errors.
     * @param reason a human readable error message.
     * @param inputIds a list of form input ids to highlight the form input field in error
     */
    public BrokenRule(CoreErrorCode errorCode, String reason, String[] inputIds) {
        this(null, (errorCode != null ? errorCode.toString() : null), reason, 0, (Object[]) null, inputIds);
    }    

    /**
     * Construct the broken rule with an ErrorCode, an error message and a set of input ids.
     *
     * @param errorCode an enumeration used to track errors.
     * @param reason a human readable error message.
     * @param inputIds a single form input id to highlight the form input field in error
     */
    public BrokenRule(CoreErrorCode errorCode, String reason, String inputId) {
        this(null, (errorCode != null ? errorCode.toString() : null), reason, 0, (Object[]) null, new String[]{inputId});
    }    
    
    /**
     * Construct the broken rule with a message bundle path and an ErrorCode.
     *
     * @param messageBundle to use for the messageKey lookup.
     * @param errorCode an enumeration used to track errors.
     */
    public BrokenRule(String messageBundle, CoreErrorCode errorCode) {
        this(messageBundle, (errorCode != null ? errorCode.toString() : null), null, 0, (Object[]) null, (String[]) null);
    }

    /**
     * Construct the broken rule with a message bundle path and an ErrorCode.
     *
     * @param messageBundle to use for the messageKey lookup.
     * @param errorCode an enumeration used to track errors.
     * @param values a list of substitution values for a formatted message.
     */
    public BrokenRule(String messageBundle, CoreErrorCode errorCode, Object[] values) {
        this(messageBundle, (errorCode != null ? errorCode.toString() : null), null, 0, values, (String[]) null);
    }

    /**
     * Construct the broken rule with a message bundle path, an ErrorCode, an error message and a set of values.
     *
     * @param messageBundle to use for the messageKey lookup.
     * @param errorCode an enumeration used to track errors.
     * @param reason a human readable error message.
     * @param values a list of substitution values for a formatted message.
     */
    public BrokenRule(String messageBundle, CoreErrorCode errorCode, String reason, Object[] values) {
        this(messageBundle, (errorCode != null ? errorCode.toString() : null), reason, 0, values, (String[]) null);
    }


    /**
     * Constructor initialized with a constraint violation object value.
     *
     * @param violation
     */
    public BrokenRule(ConstraintViolation violation) {
        String propertyPath = violation.getPropertyPath().toString();
        String message = violation.getMessage();

        Matcher matcher = messageBundleKeyPattern.matcher(message == null ? "" : message);
        // if the path is empty and the ConstraintViolation message matches the pattern then the message contains a message bundle/key
        // parses message bundle and message key {messagebundle:messagekey}
        if ( StringUtils.isEmpty(propertyPath)
                && message != null
                && matcher.matches()
                && matcher.groupCount() == 2) {
            this.messageBundle = matcher.group(1);
            this.messageKey = matcher.group(2);
        } else {
            if (!StringUtils.isEmpty(propertyPath)) {
                this.messageKey = propertyPath;
                this.reason = message;
            }
            else {
                matcher = messageKeyPattern.matcher(message == null ? "" : message);
                if (matcher.matches()) {
                    this.messageKey = matcher.group(1);
                }
            }
        }
        this.sequence = 0;
    }

    /**
     * Constructor initialized with a message key, an error message and a sequence.
     *
     * @param messageKey the message key.
     * @param reason an error message.
     * @param sequence a sequence.
     */
    public BrokenRule(String messageKey, String reason, int sequence) {
        this(null, messageKey, reason, sequence, (Object[]) null, (String[]) null);
    }

    /**
     * Constructor initialized with a message key, an error message and a sequence.
     *
     * @param messageKey the message key.
     * @param reason an error message.
     * @param sequence a sequence.
     * @param values an array of values.
     */
    public BrokenRule(String messageKey, String reason, int sequence, Object[] values) {
        this(null, messageKey, reason, sequence, values, (String[]) null);
    }

    /**
     * Constructor initialized with a message key, an error message and a sequence.
     *
     * @param messageKey the message key.
     * @param reason an error message.
     * @param values an array of values.
     */
    public BrokenRule(String messageKey, String reason, Object[] values) {
        this(null, messageKey, reason, 0, values, (String[]) null);
    }
    
    /**
     * Constructor initialized with a message bundle and key, an error message and a sequence.
     *
     * @param messageBundle to use for the messageKey lookup.
     * @param messageKey the message key.
     * @param reason an error message.
     * @param sequence a sequence.
     * @param values an array of values.
     */
    public BrokenRule(String messageBundle, String messageKey, String reason, int sequence, Object[] values, String[] inputIds) {
        this.messageBundle = messageBundle;
        this.messageKey = messageKey;
        this.reason = reason;
        this.sequence = sequence;
        this.values = values;
        this.inputIds = inputIds;
    }

    /**
     * Constructor initialized with an error code and an error message.
     *
     * @param messageKey the message key.
     * @param reason an error message.
     */
    public BrokenRule(String messageKey, String reason) {
        this(null, messageKey, reason, 0, (Object[]) null, (String[]) null);
    }
    
    /**
     * Constructor initialized with an error code and an error message.
     *
     * @param messageKey the message key.
     * @param reason an error message.
     * @param inputIds a single form input id to highlight the form input field in error
     * 
     */
    public BrokenRule(String messageKey, String reason, String inputId) {
        this(null, messageKey, reason, 0, (Object[]) null, new String[]{inputId});
    }    

//    /**
//     * Constructor initialized with a field name and an error message.
//     *
//     * @param fieldName a field name.
//     * @param reason an error message.
//     */
//    public BrokenRule(String fieldName, String reason, Object[] values) {
//        this(fieldName, reason, 0, values);
//    }
//    /**
//     * Constructor initialized with a field name and a validation reason enum value.
//     *
//     * @param fieldName a field name.
//     * @param reason a validation reason enum value.
//     */
//    public BrokenRule(FieldName fieldName, ValidationReason reason) {
//        this(fieldName, reason, 0);
//    }
//    /**
//     * Constructor initialized with a field name, a validation reason enum value and a sequence.
//     *
//     * @param fieldName a field name.
//     * @param reason a validation reason enum value.
//     * @param sequence a sequence.
//     */
//    public BrokenRule(FieldName fieldName, ValidationReason reason, int sequence) {
//        this(fieldName.toString(), reason.toString(), 0);
//    }
    /**
     * Returns a list of broken rules instances.
     *
     * @param brokenRuleIn a broken rule instance.
     * @param brokenRuleOut a broken rule instance.
     * @return a list of broken rules instances.
     */
    public static List<BrokenRule> MergeBrokenRules(
            List<BrokenRule> brokenRuleIn,
            List<BrokenRule> brokenRuleOut) {
        final String METHODNAME = "MergeBrokenRules ";
        try {
            // Navigate the Source Broken Rules and add them to the target
            // Navigate BrokenRule Vector
            for (Iterator<BrokenRule> brknRule = brokenRuleIn.iterator(); brknRule.hasNext();) {
                BrokenRule brokenRule = brknRule.next();
                brokenRuleOut.add(brokenRule);
            }
        } finally {
            //
        }

        return brokenRuleOut;
    }

    /**
     * Get the value of messageBundle
     *
     * @return the value of messageBundle
     */
    public String getMessageBundle() {
        return messageBundle;
    }

    /**
     * Returns the error code.
     *
     * @return
     */
    public String getMessageKey() {
        return messageKey;
    }

    /**
     * Returns the reason.
     *
     * @return
     */
    public String getReason() {
        return reason;
    }

    /**
     * Returns the sequence number.
     *
     * @return
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * Returns the values.
     *
     * @return
     */
    public Object[] getValues() {
        return values;
    }

    @Override
    public String toString() {
        int seq = getSequence();
        String retString = null;
        String messageKey = (getMessageKey() != null ? getMessageKey() : "");
        if (seq > 0) {
            retString = messageKey + (!StringUtils.isEmpty(getReason()) ? " " + getReason() : "") + " " + seq;
        } else {
            retString = messageKey + (!StringUtils.isEmpty(getReason()) ? " " + getReason() : "");
        }

        return retString;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public void setMessageBundle(String messageBundle) {
        this.messageBundle = messageBundle;
    }

    public String[] getInputIds() {
        return inputIds;
    }

    public void setInputIds(String[] inputIds) {
        this.inputIds = inputIds;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    
    
}
