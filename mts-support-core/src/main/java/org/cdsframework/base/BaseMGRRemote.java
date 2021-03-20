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

import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import java.util.List;
import java.util.Map;

/**
 *
 * @param <T>
 * @author HLN Consulting, LLC
 */
public interface BaseMGRRemote<T extends BaseDTO> extends BaseRemote<T> {

    /**
     * Saves an BaseDTO instance in the database.
     *
     * @param baseDTO {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
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

    public T save(T baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
        throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException, AuthorizationException;

    public T save(T baseDTO, SessionDTO sessionDTO)
        throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * CustomSave that overrides the traditional BaseDTO save, calls bo.customSave passing in the
     * propertyBagDTO String queryClass allowing the logic to route the call
     *
     * @param baseDTO {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param queryClass {@link java.lang.String [String]} class instance.
     * @param childClassDTOs childDTO classes to return.
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
    public T customSave(T baseDTO, String queryClass, List<Class> childClassDTOs, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
        throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Retrieve a baseDTO instance based on an a primaryKey object.
     *
     * @param primaryKey database primary key
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public T findByPrimaryKey(Object primaryKey, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    public T findByPrimaryKey(Object primaryKey, SessionDTO sessionDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Retrieve a baseDTO instance based on an a primaryKey object.
     *
     * @param primaryKey database primary key
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param childClassDTOs childDTO classes to return.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public T findByPrimaryKey(Object primaryKey, List<Class> childClassDTOs, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    public T findByPrimaryKey(Object primaryKey, List<Class> childClassDTOs, SessionDTO sessionDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Retrieve a list of baseDTO instances based on a baseDTO and queryClass.
     *
     * @param baseDTO {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param queryClass {@link java.lang.String [String]} class instance.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public List<T> findByQueryList(T baseDTO, String queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    public List<T> findByQueryList(T baseDTO, String queryClass, SessionDTO sessionDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Retrieve a list of baseDTO instances based on a baseDTO and queryClass.
     *
     * @param baseDTO {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param queryClass {@link java.lang.String [String]} class instance.
     * @param childClassDTOs childDTO classes to return.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public List<T> findByQueryList(T baseDTO, String queryClass, List<Class> childClassDTOs, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    public List<T> findByQueryList(T baseDTO, String queryClass, List<Class> childClassDTOs, SessionDTO sessionDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Retrieve a baseDTO instance based on a baseDTO and queryClass.
     *
     * @param baseDTO {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param queryClass {@link java.lang.String [String]} class instance.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public T findByQuery(T baseDTO, String queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    public T findByQuery(T baseDTO, String queryClass, SessionDTO sessionDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Retrieve a baseDTO instance based on a baseDTO and queryClass.
     *
     * @param baseDTO {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param queryClass {@link java.lang.String [String]} class instance.
     * @param childClassDTOs childDTO classes to return.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public T findByQuery(T baseDTO, String queryClass, List<Class> childClassDTOs, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    public T findByQuery(T baseDTO, String queryClass, List<Class> childClassDTOs, SessionDTO sessionDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Retrieve a List of user-specified type objects based on a baseDTO and queryClass.
     *
     * @param <S>
     * @param baseDTO {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param queryClass {@link java.lang.String [String]} class instance.
     * @param requiredType
     * @param propertyBagDTO
     * @return List<S> result
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public <S> List<S> findObjectByQueryList(T baseDTO, String queryClass, SessionDTO sessionDTO, Class<S> requiredType, PropertyBagDTO propertyBagDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    public <S> List<S> findObjectByQueryList(T baseDTO, String queryClass, SessionDTO sessionDTO, Class<S> requiredType)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Retrieve a user-specified type object based on a baseDTO and queryClass.
     *
     * @param <S>
     * @param baseDTO {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param queryClass {@link java.lang.String [String]} class instance.
     * @param requiredType
     * @param propertyBagDTO
     * @return S result
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */
    public <S> S findObjectByQuery(T baseDTO, String queryClass, SessionDTO sessionDTO, Class<S> requiredType, PropertyBagDTO propertyBagDTO)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Retrieve a user-specified type object based on a baseDTO and queryClass.
     *
     * @param <S>
     * @param baseDTO {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param queryClass {@link java.lang.String [String]} class instance.
     * @param requiredType
     * @return S result
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */

    public <S> S findObjectByQuery(T baseDTO, String queryClass, SessionDTO sessionDTO, Class<S> requiredType)
        throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;

    /**
     * Create a new baseDTO instance.
     *
     * @param sessionDTO {@link org.cdsframework.dto.SessionDTO [SessionDTO]} class instance.
     * @param propertyBagDTO
     * @return {@link org.cdsframework.dto.BaseDTO [BaseDTO]} class instance.
     * @throws ValidationException if validation fails on the request.
     * @throws NotFoundException if query object is not found.
     * @throws MtsException business object exception.
     * @throws AuthenticationException if session is bad.
     * @throws AuthorizationException if session user doesn't have permission to call this method.
     */

    public T newInstance(SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException,
            AuthenticationException, AuthorizationException;

    /**
     * Exports one or more DTOs.
     *
     * @param baseDTO
     * @param sessionDTO
     * @param propertyBagDTO
     * @return A Map containing the DTOs in their export format.
     * @throws ValidationException
     * @throws NotFoundException
     * @throws MtsException
     * @throws AuthenticationException
     * @throws AuthorizationException
     */
    public Map<String, byte[]> exportData(T baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException;


    /**
     * Imports one or more DTOs.
     *
     * @param sessionDTO
     * @param propertyBagDTO
     * @throws ValidationException
     * @throws NotFoundException
     * @throws MtsException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws ConstraintViolationException
     */
    public void importData(SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException, ConstraintViolationException;
}

