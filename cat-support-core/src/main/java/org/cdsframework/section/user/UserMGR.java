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
package org.cdsframework.section.user;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.dto.UserSecurityMapDTO;
import org.cdsframework.lookup.UserDTOList;
import org.cdsframework.lookup.UserSecuritySchemePermissionMapList;

@Named
@ViewScoped
public class UserMGR extends BaseModule<UserDTO> {

    private static final long serialVersionUID = -2438877010292294722L;

    @Inject
    private UserDTOList userDTOList;
    @Inject
    private UserSecuritySchemePermissionMapList userSecuritySchemePermissionMapList;

    @Override
    protected void initialize() {
        final String METHODNAME = "initialize ";
        setLazy(true);
        setAssociatedList(userDTOList);
        setSaveImmediately(true);
        addOnRowSelectChildClassDTO(UserSecurityMapDTO.class);
        // Initialize the DTO List (performs a FindAll on the manager)
        setInitialQueryClass("FindAll");
    }
    
    @Override
    public void postOpenDialog() {
        // Handle IE password autocomplete
        triggerJSSetFormChanged(false);
    }
    

    @Override
    public void registerTabComponents() {
        getTabService().registeredUIComponent(0, this);
        getTabService().registeredUIComponent(1, this);
    }

    @Override
    protected void preMarkAssociatedDTOListDirty() {
        userSecuritySchemePermissionMapList.setDirty(true);
    }

    public void resetFailedLoginAttempts() throws Exception {
        UserDTO userDTO = this.getParentDTO();
        userDTO.setFailedLoginAttempts(0);
        getMts().getGeneralMGR().save(userDTO, this.getSessionDTO(), new PropertyBagDTO());
    }
//
//    @Override
//    protected List<UserDTO> mgrFindByQueryList(UserDTO searchCriteriaDTO, String queryClass, List<Class> childClassDTOs) throws
//            Exception {
//        List<UserDTO> results = super.mgrFindByQueryList(searchCriteriaDTO, queryClass, childClassDTOs);
//        List<UserDTO> newResults = new ArrayList<UserDTO>();
//        for (UserDTO userDTO : results) {
//            if (!"INTERNAL".equals(userDTO.getUsername())) {
//                newResults.add(userDTO);
//            }
//        }
//        return newResults;
//    }
}
