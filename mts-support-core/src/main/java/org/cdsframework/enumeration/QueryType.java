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
 * Provides an enumeration for conveyance of a query type context.
 *
 * @author HLN Consulting, LLC
 */
public enum QueryType {

    /**
     * perform an add
     */
    ADD,
    /**
     * perform an update
     */
    UPDATE,
    /**
     * perform a delete
     */
    DELETE,
    /**
     * perform a primary key query
     */
    PRIMARY_KEY,
    /**
     * perform a single result query
     */
    QUERY,
    /**
     * perform a multi-result set query
     */
    QUERY_LIST,
    /**
     * perform a multi-result set query - exception on empty result set
     */
    QUERY_LIST_WITH_EXCEPTION,
    /**
     * perform a multi-result set query and transform it into a HashMap
     */
    HASH,
    /**
     * perform a single result query and return an Object
     */
    OBJECT,
    /**
     * perform a multi-result set query and return an Object List
     */
    OBJECT_LIST,

    /**
     * performs a custom query returning a single DTO
     */
    CUSTOM_QUERY,

    /**
     * perform a custom query returning a list of DTO's
     */
    CUSTOM_QUERY_LIST,

    /*
     * Used for a Custom Save, custom method needs to
     * handle each ADD, UPDATE, DELETE operation
     */
    CUSTOM_SAVE
}
