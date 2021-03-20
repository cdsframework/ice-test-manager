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
package org.cdsframework.plugin.core;

import javax.enterprise.context.ApplicationScoped;
import org.cdsframework.base.MessageArgCallback;
import org.cdsframework.base.BaseModule;
import org.cdsframework.base.CatBasePlugin;
import org.cdsframework.dto.AppDTO;
import org.cdsframework.dto.AppLogDTO;
import org.cdsframework.dto.AuditLogDTO;
import org.cdsframework.dto.AuditTransactionDTO;
import org.cdsframework.dto.SecurityPermissionDTO;
import org.cdsframework.dto.SecuritySchemeDTO;
import org.cdsframework.dto.SecuritySchemeRelMapDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.dto.SystemPropertyDTO;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.dto.UserPreferenceDTO;
import org.cdsframework.dto.UserSecurityMapDTO;
import org.cdsframework.util.enumeration.SourceMethod;

/**
 *
 * @author HLN Consulting, LLC
 */
@ApplicationScoped
public class CatCorePlugin extends CatBasePlugin {

    @Override
    protected void initialize() {

        registerMessageArgCallback(AppDTO.class, new MessageArgCallback<AppDTO>() {
            @Override
            public Object[] getMessageArgs(AppDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getAppName()};
                }
            }
        }
        );

        registerMessageArgCallback(AppLogDTO.class, new MessageArgCallback<AppLogDTO>() {
            @Override
            public Object[] getMessageArgs(AppLogDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getAppLogId()};
                }
            }
        }
        );

        registerMessageArgCallback(AuditLogDTO.class, new MessageArgCallback<AuditLogDTO>() {
            @Override
            public Object[] getMessageArgs(AuditLogDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getAuditLogId()};
                }
            }
        }
        );

        registerMessageArgCallback(AuditTransactionDTO.class, new MessageArgCallback<AuditTransactionDTO>() {
            @Override
            public Object[] getMessageArgs(AuditTransactionDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getTransactionId()};
                }
            }
        }
        );

        registerMessageArgCallback(SystemPropertyDTO.class, new MessageArgCallback<SystemPropertyDTO>() {
            @Override
            public Object[] getMessageArgs(SystemPropertyDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName()};
                }
            }
        }
        );

        registerMessageArgCallback(SecuritySchemeDTO.class, new MessageArgCallback<SecuritySchemeDTO>() {
            @Override
            public Object[] getMessageArgs(SecuritySchemeDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getSchemeName()};
                }
            }
        }
        );

        registerMessageArgCallback(SecurityPermissionDTO.class, new MessageArgCallback<SecurityPermissionDTO>() {
            @Override
            public Object[] getMessageArgs(SecurityPermissionDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getPermissionClass()};
                }
            }
        }
        );

        registerMessageArgCallback(SecuritySchemeRelMapDTO.class, new MessageArgCallback<SecuritySchemeRelMapDTO>() {
            @Override
            public Object[] getMessageArgs(SecuritySchemeRelMapDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getRelatedSecuritySchemeDTO().getSchemeName(), " security scheme relationship mapping"};
                }
            }
        }
        );

        registerMessageArgCallback(SessionDTO.class, new MessageArgCallback<SessionDTO>() {
            @Override
            public Object[] getMessageArgs(SessionDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getSessionId()};
                }
            }
        }
        );

        registerMessageArgCallback(UserDTO.class, new MessageArgCallback<UserDTO>() {
            @Override
            public Object[] getMessageArgs(UserDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getUsername()};
                }
            }
        }
        );

        registerMessageArgCallback(UserPreferenceDTO.class, new MessageArgCallback<UserPreferenceDTO>() {
            @Override
            public Object[] getMessageArgs(UserPreferenceDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName()};
                }
            }
        }
        );

        registerMessageArgCallback(UserSecurityMapDTO.class, new MessageArgCallback<UserSecurityMapDTO>() {
            @Override
            public Object[] getMessageArgs(UserSecurityMapDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getSecuritySchemeDTO().getSchemeName(), " security scheme relationship mapping"};
                }
            }
        }
        );

    }

}
