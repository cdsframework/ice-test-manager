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
package org.cdsframework.util.support;

import java.util.List;
import org.cdsframework.dto.AppDTO;
import org.cdsframework.dto.SecuritySchemeDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.util.LogUtils;

/**
 * Do not use for anything not related to the core plugin.
 *
 * @author HLN Consulting, LLC
 */
public class CoreConstants {
    private static final LogUtils logger = LogUtils.getLogger(CoreConstants.class);
    public static final String CAT_APPLICATION = "CAT";
    public static final String NEW_LINE = String.format("%n");

    public static final String QUERY_CLASS_PKG_PATH = "org.cdsframework.group.";

    public static final String WHITESPACE_COMMA_REGEX = "[,\\s]+";
    public static final String WHITESPACE_REGEX = "[\\s]+";

    public static final boolean AUTO_RETRIEVE_DEFAULT = false;
    public static final boolean AUTO_CACHED_DEFAULT = false;
    public static final boolean NOT_FOUND_ALLOWED_DEFAULT = true;
    public static final boolean ADDS_CHILD_DEFAULT = true;
    public static final boolean UPDATES_CHILD_DEFAULT = true;
    public static final boolean DELETES_CHILD_DEFAULT = true;
    public static final boolean DELETE_ALLOWED_DEFAULT = true;
    public static final boolean UPDATE_ALLOWED_DEFAULT = true;
    public static final boolean ADD_ALLOWED_DEFAULT = true;
    public static final boolean VANITY_DEFAULT = false;
    public static final String CALLINGMGR = "CallingMGR_" ;
    public static final String SAVEIMMEDIATELY = "SAVEIMMEDIATELY";

    public static final String OPERATION_NAME = "operationName";
    public static final String QUERY_CLASS = "queryClass";
    public static final String ADMIN_SCHEME_ID = "ADMIN_SCHEME_ID";
    public static final String LOG_EXCEPTIONS = "LOG_EXCEPTIONS";
    public static final String ADDORUPDATEMATCHED = "ADDORUPDATEMATCHED"; 

    // Lazy load related
    public static final String LAZY = "lazy"; 
    public static final String LAZY_PAGE_SIZE = "pageSize";
    public static final String LAZY_ROW_OFFSET = "rowOffset";
    public static final String LAZY_ROWCOUNT = "rowcount";
    public static final String SORT_ORDER = "sortOrder";
    public static final String SORT_FIELD = "sortField";
    public static final String FILTERS = "filters";
    
    public static final String AUDIT_BYPASS = "AUDIT_BYPASS";
    
    public static boolean isCat(SessionDTO sessionDTO) {
        boolean cat = false;
        AppDTO appDTO = sessionDTO.getAppDTO();
        if (appDTO != null && appDTO.getAppName() != null) {
            cat = appDTO.getAppName().equalsIgnoreCase(CAT_APPLICATION);
        }
        return cat;
    } 

    public static boolean isAdmin(SessionDTO sessionDTO, String adminSchemeId) {
        final String METHODNAME = "isAdmin ";
        boolean isAdmin = false;
        logger.info(METHODNAME, "adminSchemeId=", adminSchemeId);
        if (sessionDTO != null && adminSchemeId != null) {
            List<SecuritySchemeDTO> securitySchemeDTOs = sessionDTO.getUserDTO().getSecuritySchemeDTOs();
            for (SecuritySchemeDTO securitySchemeDTO :securitySchemeDTOs) {
                logger.info(METHODNAME, "securitySchemeDTO.getSchemeId()=", securitySchemeDTO.getSchemeId());
                isAdmin = securitySchemeDTO.getSchemeId().equalsIgnoreCase(adminSchemeId);
                if (isAdmin) {
                    break;
                }
            }
        }
        return isAdmin;
    }    
}