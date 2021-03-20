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
package org.cdsframework.base;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import org.cdsframework.enumeration.ExceptionReason;

/**
 * Provides a base exception for use in the middle tier exceptions.
 *
 * @author HLN Consulting, LLC
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public abstract class BaseException extends Exception {

    private Map<String, Object> propertyMap = null;

    /**
     * The serializable class UID.
     */
    private static final long serialVersionUID = -6959057101114749053L;
    protected ExceptionReason reason;

    /**
     * Default no arg constructor initializes the superclass.
     */
    public BaseException() {
        super();
    }

    /**
     * Constructor initialized with an ExceptionReason.
     * @param reason the ExceptionReason enumeration value.
     */
    public BaseException(ExceptionReason reason) {
        this(reason, (reason != null ? reason.toString() : null), null);
    }

    /**
     * Constructor initialized with an exception message String.
     *
     * @param message the base exception message String.
     */
    public BaseException(String message) {
        this(null, message, null);
    }

    /**
     * Constructor initialized with an ExceptionReason and exception message String.
     *
     * @param reason the ExceptionReason enumeration value.
     * @param message the base exception message String.
     */
    public BaseException(ExceptionReason reason, String message) {
        this(reason, message, null);
    }

    /**
     * Constructor initialized with an exception message String and an exception cause.
     *
     * @param message an exception message String.
     * @param cause a Throwable exception.
     */
    public BaseException(String message, Throwable cause) {
        //super(message, cause);
        this(null, message, cause);
    }

    /**
     * Constructor initialized with an ExceptionReason, exception message String and an exception cause.
     *
     * @param reason the ExceptionReason enumeration value.
     * @param message an exception message String.
     * @param cause a Throwable exception.
     */
    public BaseException(ExceptionReason reason, String message, Throwable cause) {
        super(message, cause);
        this.reason = reason;
    }

    /**
     * Returns a user suitable exception message String.
     *
     * @return a user suitable exception message String.
     */
    abstract public String getUserMessage();

    /**
     * Returns the ExceptionReason enumeration value.
     *
     * @return the ExceptionReason enumeration value.
     */
    public ExceptionReason getReason() {
        return reason;
    }

    /**
     * Sets the ExceptionReason enumeration value.
     *
     * @param reason the ExceptionRason enumeration value.
     */
    public void setReason(ExceptionReason reason) {
        this.reason = reason;
    }
    
    public String getSuperMessage() {
        return super.getMessage();
    }

    public Map<String, Object> getPropertyMap() {
        if (propertyMap == null) {
            propertyMap = new HashMap<String, Object>();
        }
        propertyMap.put("message", getMessage());
        if (reason != null) {
            propertyMap.put("exceptionReason", getReason());
        }
        return propertyMap;
    }

    public void setPropertyMap(Map<String, Object> propertyMap) {
        this.propertyMap = propertyMap;
    }

}
