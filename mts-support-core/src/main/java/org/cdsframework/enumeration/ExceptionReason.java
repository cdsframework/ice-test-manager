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
package org.cdsframework.enumeration;

/**
 * Provides an enumeration for conveyance of an exception reason context.
 *
 * @author HLN Consulting, LLC
 */
public enum ExceptionReason
{
    /**
     * Bad credentials were supplied.
     */
    BAD_CREDENTIALS,
    /**
     * The session was not valid.
     */
    SESSION_NOT_VALID,
    /**
     * The entry was a duplicate.
     */
    DUPLICATE_ENTRY,
    /**
     * The entry was missing.
     */
    MISSING_ENTRY,
    /**
     * The session was expired.
     */
    SESSION_EXPIRED,
    /**
     * The user is disabled.
     */
    USER_DISABLED,
    /**
     * The user was expired.
     */
    USER_EXPIRED,
    /**
     * Edit is not allowed.
     */
    EDIT_NOT_ALLOWED,
    /**
     * Delete is not allowed.
     */
    DELETE_NOT_ALLOWED,
    /**
     * Failed to create an instance of a class
     */
    INSTANTIATION_EXCEPTION,
    /**
     * The user is not allowed to perform the specified operation.
     */
    ILLEGAL_ACCESS,
    /*
     * The user has exceeded their max fialed login attempts
     */
    MAX_FAILED_LOGINS,
    /*
     * There was a fatal error
     */
    FATAL_ERROR,
    /*
     * There was a non-fatal error
     */
    NON_FATAL_ERROR,
    /*
     * There was a conditional error
     */
    CONDITIONAL_ERROR,
    /*
     * The resultset was empty after opt out filtering
     */
    OPT_OUT_FILTER,
    
    /*
    * Too many records found
    */
    TOO_MANY
}
