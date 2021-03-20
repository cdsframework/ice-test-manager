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
import org.cdsframework.util.ClassUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.cdsframework.util.DTOUtils;

/**
 * Base class for proxied connections to the MTS.
 *
 * @param <T>
 * @param <S>
 */
public abstract class BaseMGRClient<T extends BaseDTO, S extends BaseRemote> extends BaseClient<S> {

    public BaseMGRClient() {
        super();
        Class<? extends BaseDTO> typeArgument = ClassUtils.getTypeArgument(BaseMGRClient.class, getClass());
        logger.debug("BaseMGRClient - got type argument: ", typeArgument.getSimpleName());
        setRemoteJNDIReference(DTOUtils.getJndiReferenceURI(typeArgument));
    }

    public BaseMGRClient(Class logClass) {
        super(logClass);
        Class<? extends BaseDTO> typeArgument = ClassUtils.getTypeArgument(BaseMGRClient.class, getClass());
        logger.debug("BaseMGRClient - got type argument: ", typeArgument.getSimpleName());
        setRemoteJNDIReference(DTOUtils.getJndiReferenceURI(typeArgument));
    }

    public T save(T baseDTO, SessionDTO sessionDTO)
            throws ValidationException, NotFoundException, ConstraintViolationException,
            MtsException, AuthenticationException, AuthorizationException {
        return save(baseDTO, sessionDTO, new PropertyBagDTO());
    }

    public T save(T baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, ConstraintViolationException,
            MtsException, AuthenticationException, AuthorizationException {
        return (T) mtsInvokeAddUpdate("save", baseDTO, sessionDTO, propertyBagDTO);
    }

    public T customSave(T baseDTO, String queryClass, List<Class> childClassDTOs, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
        throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException, AuthorizationException {
        return (T) mtsInvokeAddUpdate("customSave", baseDTO, queryClass, childClassDTOs, sessionDTO, propertyBagDTO);
    }

    public T findByPrimaryKey(Object primaryKey, SessionDTO sessionDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return findByPrimaryKey(primaryKey, sessionDTO, new PropertyBagDTO());
    }

    public T findByPrimaryKey(Object primaryKey, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (T) findByPrimaryKey(primaryKey, new ArrayList(), sessionDTO, propertyBagDTO);
    }

    public T findByPrimaryKey(Object primaryKey, List<Class> childClassDTOs, SessionDTO sessionDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return findByPrimaryKey(primaryKey, childClassDTOs, sessionDTO, new PropertyBagDTO());
    }

    public T findByPrimaryKey(Object primaryKey, List<Class> childClassDTOs, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (T) mtsInvokeFind("findByPrimaryKey", primaryKey, childClassDTOs, sessionDTO, propertyBagDTO);
    }

    public List<T> findByQueryList(T baseDTO, String queryClass, SessionDTO sessionDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return findByQueryList(baseDTO, queryClass, sessionDTO, new PropertyBagDTO());
    }

    public List<T> findByQueryList(T baseDTO, String queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return findByQueryList(baseDTO, queryClass, new ArrayList(), sessionDTO, propertyBagDTO);
    }

    public List<T> findByQueryList(T baseDTO, String queryClass, List<Class> childClassDTOs, SessionDTO sessionDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return findByQueryList(baseDTO, queryClass, childClassDTOs, sessionDTO, new PropertyBagDTO());
    }

    public List<T> findByQueryList(T baseDTO, String queryClass, List<Class> childClassDTOs, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (List<T>) mtsInvokeFind("findByQueryList", baseDTO, queryClass, childClassDTOs, sessionDTO, propertyBagDTO);
    }

    public T findByQuery(T baseDTO, String queryClass, SessionDTO sessionDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return findByQuery(baseDTO, queryClass, sessionDTO, new PropertyBagDTO());
    }

    public T findByQuery(T baseDTO, String queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (T) findByQuery(baseDTO, queryClass, new ArrayList(), sessionDTO, propertyBagDTO);
    }

    public T findByQuery(T baseDTO, String queryClass, List<Class> childClassDTOs, SessionDTO sessionDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return findByQuery(baseDTO, queryClass, childClassDTOs, sessionDTO, new PropertyBagDTO());
    }

    public T findByQuery(T baseDTO, String queryClass, List<Class> childClassDTOs, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (T) mtsInvokeFind("findByQuery", (BaseDTO) baseDTO, queryClass, childClassDTOs, sessionDTO, propertyBagDTO);
    }

    public <S> List<S> findObjectByQueryList(T baseDTO, String queryClass, SessionDTO sessionDTO, Class<S> requiredType)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return findObjectByQueryList(baseDTO, queryClass, sessionDTO, requiredType, new PropertyBagDTO());
    }

    public <S> List<S> findObjectByQueryList(T baseDTO, String queryClass, SessionDTO sessionDTO, Class<S> requiredType, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (List<S>) mtsInvokeFindObjectList("findObjectByQueryList", (BaseDTO) baseDTO, queryClass, sessionDTO, requiredType, propertyBagDTO);
    }

    public <S> S findObjectByQuery(T baseDTO, String queryClass, SessionDTO sessionDTO, Class<S> requiredType)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return findObjectByQuery(baseDTO, queryClass, sessionDTO, requiredType, new PropertyBagDTO());
    }

    public <S> S findObjectByQuery(T baseDTO, String queryClass, SessionDTO sessionDTO, Class<S> requiredType, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (S) mtsInvokeFindObject("findObjectByQuery", (BaseDTO) baseDTO, queryClass, sessionDTO, requiredType, propertyBagDTO);
    }

    public T newInstance(SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException,
            AuthenticationException, AuthorizationException {
        return (T) mtsInvokeFindObject("newInstance", sessionDTO, propertyBagDTO);
    }

    public Map<String, byte[]> exportData(T baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (Map<String, byte[]>) mtsInvokeFind("exportData", baseDTO, sessionDTO, propertyBagDTO);
    }

        public void importData(SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException,
            ConstraintViolationException {
        mtsInvokeAddUpdate("importData", sessionDTO, propertyBagDTO);
    }

}
