/**
 * CAT Core support plugin project.
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
package org.cdsframework.util.comparator;

import java.util.Arrays;
import java.util.Comparator;
import org.primefaces.model.menu.DefaultSubMenu;

/**
 *
 * @author HLN Consulting, LLC
 */
public class SubmenuComparator  implements Comparator<DefaultSubMenu> {

    @Override
    public int compare(DefaultSubMenu o1, DefaultSubMenu o2) {
        String str1 = "";
        String str2 = "";
        if (o1 != null) {
            str1 = o1.getLabel();
        }
        if (o2 != null) {
            str2 = o2.getLabel();
        }
        if (str1 == null) {
            str1 = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        if (str1.isEmpty()) {
            return -1;
        }
        if (str2.isEmpty()) {
            return 1;
        }
        if (str1.equals(str2)) {
            return 0;
        }
        String[] rep = new String[]{str1, str2};
        Arrays.sort(rep);
        if (rep[0].equals(str1)) {
            return -1;
        } else {
            return 1;
        }
    }

}
