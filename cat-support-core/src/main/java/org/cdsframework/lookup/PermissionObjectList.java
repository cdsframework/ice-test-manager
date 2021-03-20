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
package org.cdsframework.lookup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.application.Mts;
import org.cdsframework.client.support.SecurityMGRClient;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.security.PermissionObject;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.comparator.PermissionObjectComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ApplicationScoped
public class PermissionObjectList implements Serializable {
    private static final long serialVersionUID = -2414113228905535373L;

    protected LogUtils logger = LogUtils.getLogger(PermissionObjectList.class);
    private List<PermissionObject> all;
    private Map<String, PermissionObject> allMap;
    @Inject
    private Mts mts;
    private SessionDTO sessionDTO;

    @PostConstruct
    private void postConstructor() {
        try {
            if (logger == null) {
                logger = LogUtils.getLogger(getClass());
            }
            if (all == null) {
                SecurityMGRClient manager = mts.getManager(SecurityMGRClient.class);
                allMap = manager.getPermissionObjects(mts.getSession());
                all = new ArrayList<PermissionObject>(allMap.values());
                Collections.sort(all, new PermissionObjectComparator());
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
        }
    }

    public PermissionObject get(String key) {
        return getItemFromString(key);
    }
//
//    public PermissionObject get(Class key) {
//        PermissionObject value = null;
//        for (PermissionObject permissionObject : all) {
//            if (permissionObject.getDtoClass() == key) {
//                value = permissionObject;
//                break;
//            }
//        }
//        return value;
//    }

    public List<PermissionObject> getSelectItems() {
        return all;
    }

    public Map<String, PermissionObject> getAllMap() {
        return allMap;
    }

    public List<PermissionObject> getAll() {
        return all;
    }

    protected PermissionObject getItemFromString(String key) {
        final String METHODNAME = "getItemFromString ";
        if (key.startsWith("class ")) {
            key = key.split(" ")[1];
        }
        PermissionObject value = allMap.get(key);
        if (value == null && key != null) {
            for (PermissionObject permissionObject : all) {
                if (permissionObject.getClassName().equalsIgnoreCase(key)
                        || permissionObject.getLabel().equalsIgnoreCase(key)) {
                    value = permissionObject;
                    break;
                }
            }
        }
        return value;
    }

    protected String getStringFromItem(PermissionObject item) {
        return item.getLabel();
    }
}
