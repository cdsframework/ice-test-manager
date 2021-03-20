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
package org.cdsframework.client.support;

import org.cdsframework.base.BaseMGRClient;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.ejb.remote.SecurityMGRRemote;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.security.PermissionObject;
import java.util.Map;
import org.cdsframework.security.UserSecuritySchemePermissionMap;

/**
 *
 * @author HLN Consulting, LLC
 */
public class SecurityMGRClient extends BaseMGRClient<SessionDTO, SecurityMGRRemote> implements SecurityMGRRemote {

    public SecurityMGRClient() {
        super(SecurityMGRClient.class);
    }

    @Override
    public SessionDTO login(String username, String password, String appName)
            throws AuthenticationException, AuthorizationException, MtsException {
        return (SessionDTO) mtsInvokeAAE("login", username, password, appName);
    }

    @Override
    public void logout(SessionDTO session)
            throws AuthenticationException, MtsException {
        logger.debug("this is a test!");
        mtsInvokeAE("logout", session);
    }

    @Override
    public SessionDTO getProxiedUserSession(String userId, SessionDTO session)
            throws AuthenticationException, AuthorizationException, MtsException {
        return (SessionDTO) mtsInvokeAAE("getProxiedUserSession", userId, session);
    }

    @Override
    public boolean authenticate(String username, String password, String appName)
            throws AuthenticationException, AuthorizationException, MtsException {
        return (Boolean) mtsInvokeAAE("authenticate", username, password, appName);
    }

    @Override
    public Map<String, PermissionObject> getPermissionObjects(SessionDTO session)
            throws AuthenticationException, AuthorizationException, MtsException {
        return (Map<String, PermissionObject>) mtsInvokeAAE("getPermissionObjects", session);
    }

    @Override
    public boolean isSessionValid(SessionDTO sessionDTO)
            throws MtsException {
        return (Boolean) mtsInvokeCE("isSessionValid", sessionDTO);
    }

    @Override
    public Map<String, UserSecuritySchemePermissionMap> getUserSecuritySchemePermissionMaps(SessionDTO session)
            throws AuthenticationException, AuthorizationException, MtsException {
        return (Map<String, UserSecuritySchemePermissionMap>) mtsInvokeAAE("getUserSecuritySchemePermissionMaps", session);
    }
}
