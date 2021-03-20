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
public abstract class BaseGeneralMGRClient extends BaseClient {

    public BaseGeneralMGRClient(Class logClass) {
        super(logClass);
    }

    public <T extends BaseDTO> T save(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException, AuthorizationException {
        return (T) mtsInvokeAddUpdate("save", dto, sessionDTO, propertyBagDTO);
    }

    public <T extends BaseDTO> T customSave(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException, AuthorizationException {
        return (T) mtsInvokeAddUpdate("customSave", dto, sessionDTO, propertyBagDTO);
    }

    public <T extends BaseDTO> T customQuery(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException, AuthorizationException {
        return (T) mtsInvokeFind("customQuery", dto, sessionDTO, propertyBagDTO);
    }

    public <T extends BaseDTO> List<T> customQueryList(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException, AuthorizationException {
        return (List<T>) mtsInvokeFind("customQueryList", dto, sessionDTO, propertyBagDTO);
    }

    public <T extends BaseDTO> T findByPrimaryKey(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (T) mtsInvokeFind("findByPrimaryKey", dto, sessionDTO, propertyBagDTO);
    }

    public <T extends BaseDTO> List<T> findByQueryList(T baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (List<T>) mtsInvokeFind("findByQueryList", baseDTO, sessionDTO, propertyBagDTO);
    }

    public <T extends BaseDTO> T findByQuery(T dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (T) mtsInvokeFind("findByQuery", (BaseDTO) dto, sessionDTO, propertyBagDTO);
    }

    public <S> List<S> findObjectByQueryList(BaseDTO dto, SessionDTO sessionDTO, Class<S> requiredType, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (List<S>) mtsInvokeFindObjectList("findObjectByQueryList", dto, sessionDTO, requiredType, propertyBagDTO);
    }

    public <S> S findObjectByQuery(BaseDTO dto, SessionDTO sessionDTO, Class<S> requiredType, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (S) mtsInvokeFindObject("findObjectByQuery", dto, sessionDTO, requiredType, propertyBagDTO);
    }

    public <S extends BaseDTO> S newInstance(Class<S> dtoClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (S) mtsInvokeFindObject("newInstance", dtoClass, sessionDTO, propertyBagDTO);
    }

    public <S extends BaseDTO> Map<String, byte[]> exportData(S dto, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (Map<String, byte[]>) mtsInvokeFind("exportData", dto, sessionDTO, propertyBagDTO);
    }

    public <S extends BaseDTO> void importData(Class<S> dtoClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException,
            ConstraintViolationException {
        mtsInvokeAddUpdate("importData", dtoClass, sessionDTO, propertyBagDTO);
    }
    
    public <T extends BaseDTO> byte[] getReport(T baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        return (byte[]) mtsInvokeFind("getReport", baseDTO, sessionDTO, propertyBagDTO);
    }
    

}
