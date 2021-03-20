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
package org.cdsframework.menu;

import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.PermissionType;

/**
 *
 * @author HLN Consulting, LLC
 */
public class TreeNodeLink {

    //private LogUtils logger = LogUtils.getLogger(TreeNodeLink.class);
    private String outcome;
    private String label;
    private Class<? extends BaseDTO> basePermissionClass;
    private PermissionType basePermissionType;
    private boolean separator;

    public TreeNodeLink(String outcome, String label, Class<? extends BaseDTO> basePermissionClass, PermissionType basePermissionType) {
        this.label = label;
        this.outcome = outcome;
        this.basePermissionClass = basePermissionClass;
        this.basePermissionType = basePermissionType;
    }

    public boolean isSeparator() {
        return separator;
    }

    public void setSeparator(boolean separator) {
        this.separator = separator;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Class<? extends BaseDTO> getBasePermissionClass() {
        return basePermissionClass;
    }

    public void setBasePermissionClass(Class<? extends BaseDTO> basePermissionClass) {
        this.basePermissionClass = basePermissionClass;
    }

    public PermissionType getBasePermissionType() {
        return basePermissionType;
    }

    public void setBasePermissionType(PermissionType basePermissionType) {
        this.basePermissionType = basePermissionType;
    }

    @Override
    public String toString() {
        return "TreeNodeLink:" + label;
    }
}
