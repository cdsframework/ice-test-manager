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
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.IceDiseaseDTO;
import org.cdsframework.util.SortUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
public class IceDiseaseComparator implements Comparator<IceDiseaseDTO> {

    @Override
    public int compare(IceDiseaseDTO o1, IceDiseaseDTO o2) {
        int result = SortUtils.sortCompareNullCheck(o1, o2);

        if (result < 2) {
            return result;
        }

        CdsCodeDTO d1 = o1.getDiseaseCdsCodeDTO();
        CdsCodeDTO d2 = o2.getDiseaseCdsCodeDTO();

        result = SortUtils.sortCompareNullCheck(d1, d2);

        if (result < 2) {
            return result;
        }

        String n1 = d1.getDisplayName();
        String n2 = d2.getDisplayName();

        result = SortUtils.sortCompareNullCheck(n1, n2);

        if (result < 2) {
            return result;
        }

        return n1.toLowerCase().compareTo(n2.toLowerCase());
    }

}
