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
package org.cdsframework.base;

import java.util.List;
import java.util.Map;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;

/**
 *
 * @author HLN Consulting, LLC
 */
public interface BaseGeneralMGRRemote extends BaseRemote {

    /**
     * Saves an BaseDTO instance in the database.
     *
     * @param <T>
     * @param dto {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if object is not found.
     * @throws ConstraintViolationException if object is a duplicate
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public <T extends BaseDTO> T save(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException,
            AuthorizationException;

    /**
     * Retrieve a dto instance based on an a primaryKey object.
     *
     * @param <T>
     * @param dto
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public <T extends BaseDTO> T findByPrimaryKey(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Retrieve a list of dto instances based on a dto and queryClass.
     *
     * @param <T>
     * @param dto {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public <T extends BaseDTO> List<T> findByQueryList(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Retrieve a dto instance based on a dto and queryClass.
     *
     * @param <T>
     * @param dto {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public <T extends BaseDTO> T findByQuery(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Retrieve a List of user-specified type objects based on a dto and queryClass.
     *
     * @param <S>
     * @param dto {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param requiredType
     * @param propertyBagDTO
     * @return List<S> result
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public <S> List<S> findObjectByQueryList(BaseDTO dto, SessionDTO sessionDTO, Class<S> requiredType, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Retrieve a user-specified type object based on a dto and queryClass.
     *
     * @param <S>
     * @param dto {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param requiredType
     * @param propertyBagDTO
     * @return String result
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public <S> S findObjectByQuery(BaseDTO dto, SessionDTO sessionDTO, Class<S> requiredType, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Create an BaseDTO instance.
     *
     * @param <S>
     * @param dtoClass
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public <S extends BaseDTO> S newInstance(Class<S> dtoClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * CustomSave that overrides the traditional BaseDTO save, calls bo.customSave passing in the propertyBagDTO String queryClass
     * allowing the logic to route the call
     *
     * @param <T>
     * @param dto {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if object is not found.
     * @throws ConstraintViolationException if object is a duplicate
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public <T extends BaseDTO> T customSave(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException,
            ConstraintViolationException;

    /**
     * customQuery that overrides the traditional BaseDTO query, calls bo.customQuery passing in the propertyBagDTO String
     * queryClass allowing the logic to route the call
     *
     * @param <T>
     * @param dto {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if object is not found.
     * @throws ConstraintViolationException if object is a duplicate
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public <T extends BaseDTO> T customQuery(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException,
            ConstraintViolationException;

    /**
     * customQueryList that overrides the traditional BaseDTO queryList, calls bo.customQueryList passing in the propertyBagDTO
     * String queryClass allowing the logic to route the call
     *
     * @param <T>
     * @param dto {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if object is not found.
     * @throws ConstraintViolationException if object is a duplicate
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public <T extends BaseDTO> List<T> customQueryList(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException,
            ConstraintViolationException;

    /**
     * Exports one or more DTOs.
     *
     * @param <T>
     * @param dto
     * @param sessionDTO
     * @param propertyBagDTO
     * @return A Map containing the DTOs in their export format.
     * @throws ValidationException
     * @throws NotFoundException
     * @throws MtsException
     * @throws AuthenticationException
     * @throws AuthorizationException
     */
    public <T extends BaseDTO> Map<String, byte[]> exportData(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;


    /**
     * Imports one or more DTOs.
     *
     * @param <T>
     * @param dtoClass
     * @param sessionDTO
     * @param propertyBagDTO
     * @throws ValidationException
     * @throws NotFoundException
     * @throws MtsException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws ConstraintViolationException
     */
    public <T extends BaseDTO> void importData(Class<T> dtoClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException, ConstraintViolationException;
    
    public <T extends BaseDTO> byte[] getReport(T dto, SessionDTO incomingSessionDTO, PropertyBagDTO incomingPropertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;
}
