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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.cdsframework.base.BaseDTOList;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.dto.UserPreferenceDTO;
import org.cdsframework.dto.UserSecurityMapDTO;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ApplicationScoped
public class UserDTOList extends BaseDTOList<UserDTO> {
    private static final long serialVersionUID = 330991921830355950L;

    @Override
    protected void initialize() throws Exception {
        addChildDtoClass(UserPreferenceDTO.class);
        addChildDtoClass(UserSecurityMapDTO.class);
    }

    @Override
    protected boolean autoCompleteTest(UserDTO dtoInstance, String query) {
        boolean match = false;
        if (containsIgnoreCase(dtoInstance.getUsername(), query)
                || containsIgnoreCase(dtoInstance.getFirstName(), query)
                || containsIgnoreCase(dtoInstance.getLastName(), query)) {
            match = true;
        }
        return match;
    }

    public String getLastModId(String userId) {
        String result = "";
        if (userId != null) {
            UserDTO userDTO = get(userId);
            if (userDTO != null) {
                result = userDTO.getUsername();
            } else {
                result = userId;
            }
        }
        return result;
    }
}
