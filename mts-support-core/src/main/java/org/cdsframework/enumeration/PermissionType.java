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

import org.cdsframework.exceptions.MtsException;

public enum PermissionType {

    /*
     * SELECT
     */
    SELECT("S"),
    /*
     * INSERT
     */
    INSERT("I"),
    /*
     * UPDATE
     */
    UPDATE("U"),
    /*
     * DELETE
     */
    DELETE("D"),
    /*
     * DELETE
     */
    FULL("F");
    private final String code;

    PermissionType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static PermissionType codeOf(String code) {
        PermissionType result = null;
        for (PermissionType item : PermissionType.values()) {
            if (item.getCode().equalsIgnoreCase(code)) {
                result = item;
                break;
            }
        }
        return result;
    }

    /**
     * Get the value of label
     *
     * @return the value of label
     */
    public String getLabel() {
        return getCode();
    }

    public static PermissionType valueOfLabel(String label) {
        PermissionType result = null;
        for (PermissionType item : PermissionType.values()) {
            if (item.getCode().equalsIgnoreCase(label)) {
                result = item;
                break;
            }
        }
        return result;
    }

    public static PermissionType getPermissionTypeByOperation(Operation operation, DTOState dtoState) throws MtsException {

        // Set permission type
        PermissionType permissionType = null;
        switch (operation) {
            case ADD:
                permissionType = PermissionType.INSERT;
                break;
            case DELETE:
                permissionType = PermissionType.DELETE;
                break;
            // Handle Custom Save
            case UPDATE:
            case CUSTOM_SAVE:
                switch (dtoState) {
                    case NEW:
                    case NEWMODIFIED:
                        permissionType = PermissionType.INSERT;
                        break;
                    case UPDATED:
                        permissionType = PermissionType.UPDATE;
                        break;
                    case DELETED:
                        permissionType = PermissionType.DELETE;
                        break;
                    case UNSET:
                        permissionType = PermissionType.SELECT;
                        break;
                }
                break;
            case FIND:
                permissionType = PermissionType.SELECT;
                break;
//            case UPDATE:
//                permissionType = PermissionType.UPDATE;
//                break;
            default:
                throw new MtsException("Unexpected Operation value: " + operation);
        }
        return permissionType;
    }
}
