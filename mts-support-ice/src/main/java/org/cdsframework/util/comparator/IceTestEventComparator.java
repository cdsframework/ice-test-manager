/**
 * The MTS support ice project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
package org.cdsframework.util.comparator;

import java.util.Comparator;
import java.util.Date;
import org.cdsframework.dto.IceTestEventDTO;

/**
 *
 * @author HLN Consulting, LLC
 */
public class IceTestEventComparator implements Comparator<IceTestEventDTO> {

    @Override
    public int compare(IceTestEventDTO iceTestEventDTO1, IceTestEventDTO iceTestEventDTO2) {
        if (iceTestEventDTO1 == null && iceTestEventDTO2 == null) {
            return 0;
        }
        if (iceTestEventDTO1 == null && iceTestEventDTO2 != null) {
            return -1;
        }
        if (iceTestEventDTO1 != null && iceTestEventDTO2 == null) {
            return 1;
        }

        Date administrationTime1 = iceTestEventDTO1.getAdministrationTime();
        Date administrationTime2 = iceTestEventDTO2.getAdministrationTime();

        if (administrationTime1 == null && administrationTime2 == null) {
            return compareRecordOrderOrCreateDateTime(iceTestEventDTO1, iceTestEventDTO2);
        }
        if (administrationTime1 == null && administrationTime2 != null) {
            return -1;
        }
        if (administrationTime1 != null && administrationTime2 == null) {
            return 1;
        }

        if (administrationTime1.equals(administrationTime2)) {
            return compareRecordOrderOrCreateDateTime(iceTestEventDTO1, iceTestEventDTO2);
        }

        return administrationTime1.compareTo(administrationTime2);
    }

    private int compareRecordOrderOrCreateDateTime(IceTestEventDTO iceTestEventDTO1, IceTestEventDTO iceTestEventDTO2) {

        Integer recordOrder1 = iceTestEventDTO1.getRecordOrder();
        Integer recordOrder2 = iceTestEventDTO2.getRecordOrder();

        if (recordOrder1 == null && recordOrder2 != null) {
            return -1;
        }
        if (recordOrder1 != null && recordOrder2 == null) {
            return 1;
        }
        if (recordOrder1 != null && recordOrder2 != null) {
            return recordOrder1.compareTo(recordOrder2);
        }

        Date createDatetime1 = iceTestEventDTO1.getCreateDatetime();
        Date createDatetime2 = iceTestEventDTO2.getCreateDatetime();

        if (createDatetime1 == null && createDatetime2 == null) {
            return 0;
        }
        if (createDatetime1 == null && createDatetime2 != null) {
            return -1;
        }
        if (createDatetime1 != null && createDatetime2 == null) {
            return 1;
        }

        return createDatetime1.compareTo(createDatetime2);
    }
}
