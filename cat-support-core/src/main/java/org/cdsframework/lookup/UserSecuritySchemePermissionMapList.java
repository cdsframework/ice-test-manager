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
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.application.Mts;
import org.cdsframework.client.support.SecurityMGRClient;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.security.UserSecuritySchemePermissionMap;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author sdn
 */
@Named
@ApplicationScoped
public class UserSecuritySchemePermissionMapList implements Serializable {
    private static final long serialVersionUID = -2661038526236097869L;

    protected LogUtils logger = LogUtils.getLogger(UserSecuritySchemePermissionMapList.class);
    private List<UserSecuritySchemePermissionMap> all;
    private Map<String, UserSecuritySchemePermissionMap> allMap;
    @Inject
    private Mts mts;
    private boolean dirty = true;

    @PostConstruct
    private void postConstructor() {
        if (all == null) {
            initialize();
        }
    }

    private void initialize() {
        try {
            if (dirty) {
                logger.info("initializing user security scheme map list...");
                SecurityMGRClient manager = mts.getManager(SecurityMGRClient.class);
                allMap = manager.getUserSecuritySchemePermissionMaps(mts.getSession());
                all = new ArrayList<UserSecuritySchemePermissionMap>(allMap.values());
                dirty = false;
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    public UserSecuritySchemePermissionMap get(String key) {
        initialize();
        return allMap.get(key);
    }

    public Map<String, UserSecuritySchemePermissionMap> getAllMap() {
        initialize();
        return allMap;
    }

    public List<UserSecuritySchemePermissionMap> getAll() {
        initialize();
        return all;
    }

    /**
     * Get the value of dirty
     *
     * @return the value of dirty
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Set the value of dirty
     *
     * @param dirty new value of dirty
     */
    public void setDirty(boolean dirty) {
        logger.info("setting dirty to: ", dirty);
        this.dirty = dirty;
    }
}
