/**
 * The MTS support cds project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
 *
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

import java.util.Date;
import java.util.List;
import org.cdsframework.enumeration.OffsetType;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.interfaces.OffsetBasedEventType;
import org.cdsframework.interfaces.OffsetBasedType;

/**
 *
 * @author HLN Consulting, LLC
 */
public class CdsDateUtils {

    /**
     * Returns the administration date of an OffsetBasedType implementation.
     *
     * @param offsetBasedEventType
     * @param offsetBasedType
     * @param history
     * @return
     * @throws org.cdsframework.exceptions.MtsException
     */
    public static Date getOffsetBasedEventDate(OffsetBasedEventType offsetBasedEventType, OffsetBasedType offsetBasedType, List<OffsetBasedEventType> history)
            throws MtsException {

        // parameter validation
        if (offsetBasedEventType == null) {
            throw new MtsException("offsetBasedEvent is null!");
        }
        if (offsetBasedType == null) {
            throw new MtsException("iceTestDTO is null!");
        }

        // add this offsetBasedEventType to the history. If we see it again we have recursion within the events and we need to bomb out
        if (history.contains(offsetBasedEventType)) {
            throw new MtsException("The offset based type reference contains a recursive reference!");
        }

        history.add(offsetBasedEventType);

        Date offsetBasedEventDate;

        if (offsetBasedEventType.isOffsetBased()) {
            // offset based administration times can be interval or age based
            if (offsetBasedEventType.getOffsetType() == OffsetType.Interval) {

                // retrieve the peer event dto list
                List<OffsetBasedEventType> peerEventDTOs = offsetBasedType.getIntervalBasedEvents();
                if (peerEventDTOs == null) {
                    throw new MtsException("peerEventDTOs is null!");
                }

                // interval-based offsets are based on an events administration time
                OffsetBasedEventType intervalOffsetEvent = null;

                // find the interval offset event
                for (OffsetBasedEventType item : peerEventDTOs) {
                    if (item.getId().equals(offsetBasedEventType.getOffsetId())) {
                        intervalOffsetEvent = item;
                        break;
                    }
                }

                // if not found then take exception
                if (intervalOffsetEvent == null) {
                    throw new MtsException("Interval offset ID not found: " + offsetBasedEventType.getOffsetId());
                }

                // get the interval offset event administration time
                Date intervalOffsetAdministrationDate = getOffsetBasedEventDate(intervalOffsetEvent, offsetBasedType, history);

                // calculate the offset from the other event
                offsetBasedEventDate = DateUtils.incrementDateFromString(intervalOffsetAdministrationDate, offsetBasedEventType.getOffset(), false);

            } else {
                // age based offsets are from the age offset calc on the test object

                // derrive the test DOB to be used for the age based offset
                Date dob = getOffsetBasedTypeDob(offsetBasedType);

                // calculate the offset from dob
                if (!StringUtils.isEmpty(offsetBasedEventType.getOffset())) {
                    offsetBasedEventDate = DateUtils.incrementDateFromString(dob, offsetBasedEventType.getOffset(), false);
                } else {
                    offsetBasedEventDate = dob;
                }
            }
        } else {
            offsetBasedEventDate = offsetBasedEventType.getEventDate();
        }

        return offsetBasedEventDate;
    }

    /**
     * Get the static or derived DOB.
     *
     * @param offsetBasedType
     * @return
     */
    public static Date getOffsetBasedTypeDob(OffsetBasedType offsetBasedType) {

        if (offsetBasedType == null) {
            throw new IllegalArgumentException("offsetBasedType is null!");
        }

        Date result;
        if (!offsetBasedType.isOffsetBased()) {
            result = offsetBasedType.getDob();
        } else {
            try {
                result = DateUtils.incrementDateFromString(new Date(), offsetBasedType.getOffset(), true);
            } catch (IllegalArgumentException e) {
                // ignore
                result = null;
            }
        }
        return result;
    }

    public static Date getOffsetBasedTypeExecutionDate(OffsetBasedType offsetBasedType) {

        if (offsetBasedType == null) {
            throw new IllegalArgumentException("offsetBasedType is null!");
        }

        return offsetBasedType.isOffsetBased() ? new Date() : offsetBasedType.getExecutionDate();
    }
}
