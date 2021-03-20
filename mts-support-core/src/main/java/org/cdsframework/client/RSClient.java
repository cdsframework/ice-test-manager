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
package org.cdsframework.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.IncorrectResultSizeException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.rs.exception.mapper.ErrorMessage;
import org.cdsframework.rs.provider.CoreJacksonJsonProvider;
import org.cdsframework.rs.support.CoreRsConstants;
import org.cdsframework.rs.utils.CommonRsUtils;
import org.cdsframework.util.ClassUtils;
import org.cdsframework.util.DTOUtils;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.filter.EncodingFeature;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.message.GZipEncoder;

/**
 *
 * @author HLN Consulting, LLC
 */
public class RSClient {

    private WebTarget webTarget = null;
    private final Client client;
    private final String rootPath;
    private final String baseURI;
    private final JsonInclude.Include jsonInclude;
    private final boolean loggingFilter;
    private final boolean gzipSupport;
    private static final Logger logger = Logger.getLogger(RSClient.class.getCanonicalName());
    private final boolean useResourceInPath;

    /**
     *
     * @param baseURI base of rest service
     * @param rootPath root of rest service
     */
    
    public RSClient(String baseURI, String rootPath) {
        this(baseURI, rootPath, false, false);
    }
    
    /**
     *
     * @param baseURI base of rest service
     * @param rootPath root of rest service
     * @param loggingFilter false is the default, true turns on logging, false turns off logging
     * @param gzipSupport false is the default, true turns on GZIP decompression, provided that 
     *                    the RS service is configured to use GZIP compression 
     *                    when returning findByPrimaryKey or findByQueryList responses
     */
    public RSClient(String baseURI, String rootPath, boolean loggingFilter, boolean gzipSupport) {
        this(baseURI, rootPath, loggingFilter, true, gzipSupport);
    }

    /**
     *
     * @param baseURI base of rest service
     * @param rootPath root of rest service
     * @param loggingFilter false is the default, true turns on logging, false turns off logging
     * @param useResourceInPath false expects a Typed DTO RS service, 
     *                          true expects a Generic RS service,
     *                          When true it passes up the resourceName using the DTO converted into a resourceName
     *                          In the RS tier POST/PUT extract the resourceName from the json payload to deserialize the DTO
     * @param gzipSupport false is the default, true turns on GZIP decompression, provided that 
     *                    the RS service is configured to use GZIP compression 
     *                    when returning findByPrimaryKey or findByQueryList responses
     */
    public RSClient(String baseURI, String rootPath, boolean loggingFilter, boolean useResourceInPath, boolean gzipSupport) {
        this(baseURI, rootPath, loggingFilter, useResourceInPath, gzipSupport, JsonInclude.Include.NON_NULL);
    }    
    
    /**
     *
     * @param baseURI base of rest service
     * @param rootPath root of rest service
     * @param loggingFilter false is the default, true turns on logging, false turns off logging
     * @param useResourceInPath false expects a Typed DTO RS service, 
     *                          true expects a Generic RS service,
     *                          When true it passes up the resourceName using the DTO converted into a resourceName
     *                          In the RS tier POST/PUT extract the resourceName from the json payload to deserialize the DTO
     * @param gzipSupport false is the default, true turns on GZIP decompression, provided that 
     *                    the RS service is configured to use GZIP compression 
     *                    when returning findByPrimaryKey or findByQueryList responses
     * @param jsonInclude default is NON_NULL, used to exclude NULLs in json string
     */
    public RSClient(String baseURI, String rootPath, boolean loggingFilter, boolean useResourceInPath, boolean gzipSupport, JsonInclude.Include jsonInclude) {
        this.useResourceInPath = useResourceInPath;
        this.loggingFilter = loggingFilter;
        this.gzipSupport = gzipSupport;
        this.jsonInclude = jsonInclude;
        this.baseURI = baseURI + "/" + CoreRsConstants.RESOURCE_ROOT;
        this.rootPath = rootPath;
        client = getClient(jsonInclude);
       
    }    

    public void setConnectTimeout(Integer value ) {
        setClientProperties(ClientProperties.CONNECT_TIMEOUT, value);
    }    
    
    public void setReadTimeout(Integer value ) {
        setClientProperties(ClientProperties.READ_TIMEOUT, value);
    }    

    public void setClientProperties(String clientProperty, Integer value ) {
        client.property(clientProperty, value);
    }
    
    public Client getClient() {
        return client;
    }
    
    /**
     *
     * @return root path
     */
    public String getRootPath() {
        return rootPath;
    }

    /**
     *
     * @return WebTarget
     */
    public WebTarget getWebTarget() {
        if (webTarget == null) {
            webTarget = getWebTarget(client);
        }
        return webTarget;
    }
    
    private WebTarget getWebTarget(Client client) {
        return client.target(baseURI).path(rootPath);
    }
    
    private Client getClient(JsonInclude.Include jsonInclude) {
        Client client = ClientBuilder.newClient()
                .register(new JacksonFeature());
        if (loggingFilter) {
            client.register(new LoggingFilter(logger, true));
        }
        client.register(new CoreJacksonJsonProvider(jsonInclude));
        if (gzipSupport) {
            client.register(new EncodingFeature("gzip", GZipEncoder.class));
        }
        client.register(new ApacheConnectorProvider());
        return client;
    }    

    /**
     * closes client
     */
    public void close() {
        client.close();
    }

    /**
     *
     * @param <T> 
     * @param responseType must extends BaseDTO
     * @param sessionId session Id associated with authentication
     * @return
     * @throws ConstraintViolationException
     * @throws ValidationException
     * @throws NotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws MtsException
     */
    public <T extends BaseDTO> T findByPrimaryKey(T responseType, String sessionId) 
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        return (T) findByPrimaryKey(responseType, null, sessionId);
    }
    
    /**
     *
     * @param <T> 
     * @param responseType extends BaseDTO
     * @param childclasses child resources to return
     * @param sessionId session Id associated with authentication
     * @return
     * @throws ConstraintViolationException
     * @throws ValidationException
     * @throws NotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws MtsException
     */    
    public <T extends BaseDTO> T findByPrimaryKey(T responseType, List<String> childclasses, String sessionId) 
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        return (T) findByPrimaryKey(responseType, childclasses, null, sessionId);
    }
    
    /**
     *
     * @param <T>
     * @param responseType extends BaseDTO
     * @param childclasses child resources to return
     * @param propertyMap used to control flow
     * @param sessionId session Id associated with authentication
     * @return
     * @throws ConstraintViolationException
     * @throws ValidationException
     * @throws NotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws MtsException
     */
    public <T extends BaseDTO> T findByPrimaryKey(T responseType, List<String> childclasses, Map<String, Object> propertyMap, String sessionId) 
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        return (T) findByPrimaryKey(responseType.getClass(), DTOUtils.getPrimaryKeys(responseType), childclasses, propertyMap, sessionId);
    }
            
    /**
     *
     * @param <T>
     * @param responseType type of class to return (extends BaseDTO.class, Response.class, String.class)
     * @param primaryKey primary key object, supports Collections (List or HashMap), Integer, Long, String, Date
     * @param sessionId session Id associated with authentication
     * @return
     * @throws ConstraintViolationException
     * @throws ValidationException
     * @throws NotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws MtsException
     */
    public <T> T findByPrimaryKey(Class<T> responseType, Object primaryKey, String sessionId) 
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        return findByPrimaryKey(responseType, primaryKey, null, sessionId);
    }        
    
    /**
     *
     * @param <T>
     * @param responseType type of class to return (extends BaseDTO.class, Response.class, String.class)
     * @param primaryKey primary key object, supports Collections (List or HashMap), Integer, Long, String, Date
     * @param childclasses child resources to return
     * @param sessionId session Id associated with authentication
     * @return
     * @throws ConstraintViolationException
     * @throws ValidationException
     * @throws NotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws MtsException
     */
    public <T> T findByPrimaryKey(Class<T> responseType, Object primaryKey, List<String> childclasses, String sessionId) 
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        return findByPrimaryKey(responseType, primaryKey, childclasses, null, sessionId);
    }
    
    /**
     *
     * @param <T> 
     * @param responseType type of class to return (extends BaseDTO.class, Response.class, String.class)
     * @param primaryKey primary key object, supports Collections (List or HashMap), Integer, Long, String, Date
     * @param childclasses child resources to return
     * @param propertyMap used to control flow
     * @param sessionId session Id associated with authentication
     * @return
     * @throws org.cdsframework.exceptions.ConstraintViolationException
     * @throws org.cdsframework.exceptions.ValidationException
     * @throws org.cdsframework.exceptions.NotFoundException
     * @throws org.cdsframework.exceptions.AuthenticationException
     * @throws org.cdsframework.exceptions.AuthorizationException
     * @throws org.cdsframework.exceptions.MtsException
     */
    public <T> T findByPrimaryKey(Class<T> responseType, Object primaryKey, List<String> childclasses, Map<String, Object> propertyMap, String sessionId) 
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        List<Object> primaryKeys = new ArrayList<Object>();
        if (primaryKey instanceof List) {
            primaryKeys.addAll((Collection<? extends Object>) primaryKey);
        }
        else if (primaryKey instanceof HashMap) {
            primaryKeys.addAll( ((HashMap) primaryKey).values());
        }
        else {
            primaryKeys.add(primaryKey);
        }
        return findByPrimaryKey(responseType, primaryKeys, childclasses, propertyMap, sessionId);
    }

    /**
     *
     * @param <T>
     * @param responseType type of class to return (extends BaseDTO.class, Response.class, String.class)
     * @param primaryKeys list of primary keys
     * @param childclasses child resources to return
     * @param propertyMap used to control flow
     * @param sessionId session Id associated with authentication
     * @return
     * @throws org.cdsframework.exceptions.ConstraintViolationException
     * @throws org.cdsframework.exceptions.ValidationException
     * @throws org.cdsframework.exceptions.NotFoundException
     * @throws org.cdsframework.exceptions.AuthenticationException
     * @throws org.cdsframework.exceptions.AuthorizationException
     * @throws org.cdsframework.exceptions.MtsException
     */
    public <T> T findByPrimaryKey(Class<T> responseType, List<Object> primaryKeys, List<String> childclasses, Map<String, Object> propertyMap, String sessionId) 
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "findByPrimaryKey ";
        WebTarget resource = getWebTarget();
        if (responseType != null && useResourceInPath) {
            resource = resource.path(ClassUtils.getResourceName(responseType));
        }        
        if (childclasses != null) {
            for (String childclass : childclasses) {
                resource = resource.queryParam(CoreRsConstants.QUERYPARMEXPAND, childclass);
            }
        }
        if (propertyMap != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMPROPERTY, CommonRsUtils.getMapAsEncodedString(propertyMap));
        }
        if (sessionId != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMSESSION, sessionId);
        }

        resource = resource.path(getPath(primaryKeys));
        return getResponse(responseType, resource);
    }
    
    /**
     *
     * @param <T> 
     * @param responseType extends BaseDTO
     * @param filterMap Map used to construct query 
     * @param propertyMap Map used to control flow, identifies query class, etc
     * @param sessionId Session Id associated with authentication
     * @return
     * @throws org.cdsframework.exceptions.ConstraintViolationException
     * @throws org.cdsframework.exceptions.ValidationException
     * @throws org.cdsframework.exceptions.NotFoundException
     * @throws org.cdsframework.exceptions.AuthenticationException
     * @throws org.cdsframework.exceptions.AuthorizationException
     * @throws org.cdsframework.exceptions.MtsException
     */
    public <T extends BaseDTO> List<T> findByQueryList(Class<T> responseType, Map<String, Object> filterMap, Map<String, Object> propertyMap, String sessionId) 
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        return findByQueryList(responseType, filterMap, null, propertyMap, sessionId);
    }
    
    /**
     *
     * @param <T> 
     * @param responseType type of class to return (extends BaseDTO.class, Response.class, String.class)
     * @param filterMap Map used to construct query 
     * @param childclasses child resources to return
     * @param propertyMap Map used to control flow, identifies query class, etc
     * @param sessionId Session Id associated with authentication
     * @return
     * @throws org.cdsframework.exceptions.ConstraintViolationException
     * @throws org.cdsframework.exceptions.ValidationException
     * @throws org.cdsframework.exceptions.NotFoundException
     * @throws org.cdsframework.exceptions.AuthenticationException
     * @throws org.cdsframework.exceptions.AuthorizationException
     * @throws org.cdsframework.exceptions.MtsException
     */
    public <T> List<T> findByQueryList(final Class<T> responseType, Map<String, Object> filterMap, List<String> childclasses, Map<String, Object> propertyMap, String sessionId) 
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {

        ParameterizedType parameterizedGenericType = new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { responseType };
            }

            @Override
            public Type getRawType() {
                return List.class;
            }

            @Override
            public Type getOwnerType() {
                return List.class;
            }
        };
        GenericType<List<T>> genericType = new GenericType<List<T>>(parameterizedGenericType){};
        return findByQueryList(responseType, genericType, filterMap, childclasses, propertyMap, sessionId);
        
    }

    /**
     *
     * @param <T>
     * @param responseType extends BaseDTO
     * @param genericType list of response type
     * @param filterMap Map used to construct query 
     * @param childclasses child resources to return
     * @param propertyMap Map used to control flow, identifies query class, etc
     * @param sessionId Session Id associated with authentication
     * @return
     * @throws org.cdsframework.exceptions.ConstraintViolationException
     * @throws org.cdsframework.exceptions.ValidationException
     * @throws org.cdsframework.exceptions.NotFoundException
     * @throws org.cdsframework.exceptions.AuthenticationException
     * @throws org.cdsframework.exceptions.MtsException
     * @throws org.cdsframework.exceptions.AuthorizationException
     */
    public <T> List<T> findByQueryList(Class<T> resourceType, GenericType genericType, Map<String, Object> filterMap, List<String> childclasses, Map<String, Object> propertyMap, String sessionId) 
            throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "findByQueryList ";

        Response response = null;
        try {
            response = findByQueryListResponse(resourceType, null, filterMap, childclasses, propertyMap, sessionId);

            // Check the status
            boolean success = (response.getStatus() == Response.Status.OK.getStatusCode());

            // Success
            if (!success) {
                // Caller not interested in the response, translate and throw
                CommonRsUtils.throwException(response.readEntity(ErrorMessage.class));
            }
            
            if (response != null) {
                return (List<T>) response.readEntity(genericType);
            }
        }
        finally {
            // Just in case we get here and the response is not closed, readEntity closes the response by default
            // But if during the Read operation a Jersey exception occurs we want to close out the response
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
        
    /**
     *
     * @param resourceType type of resource DTO
     * @param responseType type of response, used for primitive return types <Long, Integer, String, etc>
     * @param filterMap Map used to construct query 
     * @param childclasses child resources to return
     * @param propertyMap Map used to control flow, identifies query class, etc
     * @param sessionId Session Id associated with authentication
     * @return
     * @throws ConstraintViolationException
     * @throws ValidationException
     * @throws NotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws MtsException
     */
    private Response findByQueryListResponse(Class resourceType, Class responseType, Map<String, Object> filterMap, List<String> childclasses, Map<String, Object> propertyMap, String sessionId) 
            throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "findByQueryListResponse ";
        
        WebTarget resource = getWebTarget();
        if (resourceType != null && useResourceInPath) {
            resource = resource.path(ClassUtils.getResourceName(resourceType));
        }
        
        // Add object to path
        if (responseType != null) { 
            resource = resource.path("object");
            // Stash responseType in propertyMap
            if (propertyMap != null) {
                propertyMap.put("responseType", responseType.getCanonicalName());
            }
        }
        
        if (childclasses != null) {
            for (String childclass : childclasses) {
                resource = resource.queryParam(CoreRsConstants.QUERYPARMEXPAND, childclass);
            }
        }
        if (filterMap != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMFILTER, CommonRsUtils.getMapAsEncodedString(filterMap));
        }
        if (propertyMap != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMPROPERTY, CommonRsUtils.getMapAsEncodedString(propertyMap));
        }
        if (sessionId != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMSESSION, sessionId);
        }

        // Get the response
        return resource.request().get(Response.class);
        
    }    

    public <T> T findByObjectQuery(final Class<T> responseType, Class resourceType, Map<String, Object> filterMap, Map<String, Object> propertyMap, String sessionId) 
            throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "findByObjectQueryList ";
        
        // Get the list
        List<T> oList = findByObjectQueryList(responseType, resourceType, filterMap, propertyMap, sessionId);
        
        // Evaluate the list an ensure there is exactly 1
        T oResponseType = null;
        
        if (responseType != Response.class) {
            boolean success = false;
            int size = 0;
            
            if (oList != null) {
                size = oList.size();
                if (size == 1) {
                    success = true;
                    oResponseType = oList.get(0);
                }
            }
            if (!success) {
                IncorrectResultSizeException incorrectResultSizeException = new IncorrectResultSizeException("", 1, size);
                throw new NotFoundException("IncorrectResultSizeException: actual size - " + 
                        incorrectResultSizeException.getActualSize() + "; expected size - " + 
                        incorrectResultSizeException.getExpectedSize());
            }
        }
        return oResponseType;
    }
    
    public <T> List<T> findByObjectQueryList(final Class<T> responseType, Class resourceType, Map<String, Object> filterMap, Map<String, Object> propertyMap, String sessionId) 
            throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "findByObjectQueryList ";

        Response response = null;
        try {
            
            response = findByQueryListResponse(resourceType, responseType, filterMap, null, propertyMap, sessionId);

            // Check the status
            boolean success = (response.getStatus() == Response.Status.OK.getStatusCode());

            // Success
            if (!success) {
                // Caller not interested in the response, translate and throw
                CommonRsUtils.throwException(response.readEntity(ErrorMessage.class));
            }
            
            if (response != null) {
                ParameterizedType parameterizedGenericType = new ParameterizedType() {
                    @Override
                    public Type[] getActualTypeArguments() {
                        return new Type[] { responseType };
                    }

                    @Override
                    public Type getRawType() {
                        return List.class;
                    }

                    @Override
                    public Type getOwnerType() {
                        return List.class;
                    }
                };
                
                GenericType<List<T>> genericType = new GenericType<List<T>>(parameterizedGenericType){};
                return (List<T>) response.readEntity(genericType);
            }
        }
        finally {
            // Just in case we get here and the response is not closed, readEntity closes the response by default
            // But if during the Read operation a Jersey exception occurs we want to close out the response
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
    
    /**
     *
     * @param <T>
     * @param requestEntity extends BaseDTO
     * @param responseType Response, String or BaseDTO
     * @param sessionId Session Id associated with authentication
     * @return
     */
    private <T> T update(Object requestEntity, Class<T> responseType, String sessionId) {
        return update(requestEntity, responseType, null, sessionId);
    }

    /**
     *
     * @param <T>
     * @param requestEntity extends BaseDTO
     * @param responseType Response, String or BaseDTO
     * @param propertyMap Map used to control flow, (returnResource = true will return the resource)
     * @param sessionId Session Id associated with authentication
     * @return
     */
    private <T> T update(Object requestEntity, Class<T> responseType, Map<String, Object> propertyMap, String sessionId) {
        // This is the only call that creates a new Client instance because of the registration
        WebTarget resource = getWebTarget(getClient(JsonInclude.Include.ALWAYS));
        if (requestEntity != null && useResourceInPath) {
            resource = resource.path(ClassUtils.getResourceName(requestEntity.getClass()));
        }
        if (propertyMap != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMPROPERTY, CommonRsUtils.getMapAsEncodedString(propertyMap));
        }
        if (sessionId != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMSESSION, sessionId);
        }
        // Throw error if NOT BaseDTO, this supports compound primary key, Rest Service needs to be configured
        BaseDTO baseDTO = (BaseDTO) requestEntity;
        if (baseDTO != null) {
            List<Object> primaryKeys = DTOUtils.getPrimaryKeys(baseDTO);
            String path = getPath(primaryKeys);
            resource = resource.path(path);
            System.out.println("resource=" + resource);
        }
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), responseType);
    }
    
    /**
     *
     * @param <T>
     * @param requestEntity extends BaseDTO
     * @param responseType Response, String or BaseDTO Type
     * @param sessionId Session Id associated with authentication
     * @return
     */
    private <T> T create(Object requestEntity, Class<T> responseType, String sessionId) {
        return create(requestEntity, responseType, null, sessionId);
    }

    /**
     *
     * @param <T>
     * @param requestEntity BaseDTO descendant
     * @param responseType Response, String or BaseDTO Type
     * @param propertyMap Map used to control flow, (returnResource = true will return the resource)
     * @param sessionId Session Id associated with authentication
     * @return
     */    
    private <T> T create(Object requestEntity, Class<T> responseType, Map<String, Object> propertyMap, String sessionId) {
        WebTarget resource = getWebTarget();
        if (requestEntity != null && useResourceInPath) {
            resource = resource.path(ClassUtils.getResourceName(requestEntity.getClass()));
        }        
        if (propertyMap != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMPROPERTY, CommonRsUtils.getMapAsEncodedString(propertyMap));
        }
        if (sessionId != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMSESSION, sessionId);
        }
        
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), responseType);
    }

    
    /**
     *
     * @param <T> 
     * @param requestEntity extends BaseDTO
     * @param sessionId Session Id associated with authentication
     * @return
     * @throws org.cdsframework.exceptions.ConstraintViolationException
     * @throws org.cdsframework.exceptions.ValidationException
     * @throws org.cdsframework.exceptions.AuthenticationException
     * @throws org.cdsframework.exceptions.AuthorizationException
     * @throws org.cdsframework.exceptions.MtsException
     * @throws org.cdsframework.exceptions.NotFoundException
     */    
    public <T extends BaseDTO> T save(T requestEntity, String sessionId)
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        return (T) save(requestEntity, null, sessionId );
    }
    
    /**
     *
     * @param <T> 
     * @param requestEntity extends BaseDTO
     * @param propertyMap Map used to control flow, (returns resource by default)
     * @param sessionId Session Id associated with authentication
     * @return
     * @throws org.cdsframework.exceptions.ConstraintViolationException
     * @throws org.cdsframework.exceptions.ValidationException
     * @throws org.cdsframework.exceptions.AuthenticationException
     * @throws org.cdsframework.exceptions.AuthorizationException
     * @throws org.cdsframework.exceptions.MtsException
     * @throws org.cdsframework.exceptions.NotFoundException
     */
    public <T extends BaseDTO> T save(T requestEntity, Map<String, Object> propertyMap, String sessionId)
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        return (T) save(requestEntity, propertyMap, true, sessionId );
    }

    /**
     *
     * @param <T> 
     * @param requestEntity extends BaseDTO
     * @param propertyMap Map used to control flow
     * @param returnResource control whether or not resource should be returned
     * @param sessionId Session Id associated with authentication
     * @return
     * @throws org.cdsframework.exceptions.ConstraintViolationException
     * @throws org.cdsframework.exceptions.ValidationException
     * @throws org.cdsframework.exceptions.NotFoundException
     * @throws org.cdsframework.exceptions.AuthenticationException
     * @throws org.cdsframework.exceptions.AuthorizationException
     * @throws org.cdsframework.exceptions.MtsException
     */
    public <T extends BaseDTO> T save(T requestEntity, Map<String, Object> propertyMap, boolean returnResource, String sessionId)
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        if (returnResource) {
            if (propertyMap == null) {
                propertyMap = new HashMap<String, Object>();
            }
            propertyMap.put(CoreRsConstants.RS_RETURN_RESOURCE, returnResource);
        }
        return (T) save(requestEntity, requestEntity.getClass(), propertyMap, sessionId );
    }

    /**
     * @param <T> 
     * @param requestEntity extends BaseDTO
     * @param responseType Response, String or BaseDTO Type
     * @param propertyMap Map used to control flow, (returnResource = true will return the resource)
     * @param sessionId Session Id associated with authentication
     * @return
     * @throws org.cdsframework.exceptions.ConstraintViolationException
     * @throws org.cdsframework.exceptions.ValidationException
     * @throws org.cdsframework.exceptions.NotFoundException
     * @throws org.cdsframework.exceptions.AuthenticationException
     * @throws org.cdsframework.exceptions.AuthorizationException
     * @throws org.cdsframework.exceptions.MtsException
     */
    
    public <T> T save(Object requestEntity, Class<T> responseType, Map<String, Object> propertyMap, String sessionId) 
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "saveDTO ";
        
        if (requestEntity == null) {
            throw new IllegalArgumentException("Unsupported requestEntity " + requestEntity);
        }
        if (responseType == null) {
            throw new IllegalArgumentException("Unsupported responseType " + responseType);
        }
        if (StringUtils.isEmpty(sessionId)) {
            throw new IllegalArgumentException("Unsupported sessionId " + sessionId);
        }

        BaseDTO dto = null;
        if (requestEntity instanceof BaseDTO) {
            dto = (BaseDTO) requestEntity;
        }
        else {
            throw new IllegalArgumentException("Unsupported requestEntity class " + requestEntity.getClass().getCanonicalName());
        }
        
        Response response = null;
        try {
            if (dto != null) {
                DTOState dtoState = dto.getDTOState();

                if (dto.isNew()) {
                    response = create(requestEntity, Response.class, propertyMap, sessionId);
                }
                else if (dto.isDeleted()) {
                    response = delete(requestEntity, DTOUtils.getPrimaryKeys(dto), Response.class, propertyMap, sessionId);
                }
                else {
                    // Get the operation state
                    dtoState = dto.getOperationDTOState();                
                    if (dtoState == DTOState.UPDATED) {
                        if (propertyMap == null) {
                            propertyMap = new HashMap<String, Object>();
                        }
                        // Add flag to inform BO layer that a RS DTO Client is being used
                        propertyMap.put(CoreRsConstants.RS_CLIENT, true);
                        
                        response = update(requestEntity, Response.class, propertyMap, sessionId);
                    }
                    else {
                        throw new IllegalArgumentException("Unsupported requestEntity class " + requestEntity.getClass().getCanonicalName() + 
                                " DTOState " + dtoState + " is not in a state that can be saved.");
                    }
                }

                // Check response
                if (response != null) {
                    boolean success = false;
                    // New, New Modified
                    if (dtoState == DTOState.NEW || dtoState == DTOState.NEWMODIFIED) {
                        success = (response.getStatus() == Response.Status.CREATED.getStatusCode());
                    }
                    // Updated, Deleted returns OK status                
                    else if (dtoState == DTOState.UPDATED || dtoState == DTOState.DELETED) {
                        success = (response.getStatus() == Response.Status.OK.getStatusCode());
                    }
                    logger.info(METHODNAME + "success=" + success + " responseType=" + responseType);

                    // Success
                    if (!success) {
                        // Caller not interested in the response, translate and throw
                        if (responseType != Response.class) {
                            CommonRsUtils.throwException(response.readEntity(ErrorMessage.class));
                        }
                    }

                    // Caller interested in response, this must be closed by the caller, unless entity is read
                    if (responseType == Response.class) {
                        return (T) response;
                    }
                    else {
                        return response.readEntity(responseType);
                    }
                }
            }
        }
        finally {
            // Just in case we get here and the response is not closed, readEntity closes the response by default
            // But if during the Create, Update, Delete an Jersey exception occurs we want to close out the response
            if (response != null && responseType != Response.class) {
                response.close();
            }
        }
        return null;
    }    
    
        
    /**
     *
     * @param baseDTO accepts BaseDTO and extracts primary keys
     * @param sessionId Session Id associated with authentication
     * @return
     */
    private Response delete(BaseDTO baseDTO, String sessionId) {
        return delete(baseDTO, null, sessionId);
    }

    /**
     *
     * @param baseDTO accepts BaseDTO and extracts primary keys
     * @param propertyMap Map used to control flow
     * @param sessionId Session Id associated with authentication
     * @return
     */
    private Response delete(BaseDTO baseDTO, Map<String, Object> propertyMap, String sessionId) {
        return delete(baseDTO, DTOUtils.getPrimaryKeys(baseDTO), propertyMap, sessionId);
    }
    
    /**
     *
     * @param primaryKey primary key
     * @param propertyMap Map used to control flow
     * @param sessionId Session Id associated with authentication
     * @return
     */
    private Response delete(Object requestEntity, Object primaryKey, Map<String, Object> propertyMap, String sessionId) {
        List<Object> primaryKeys = new ArrayList<Object>();
        primaryKeys.add(primaryKey);
        return delete(requestEntity, primaryKeys, propertyMap, sessionId);
    }    
    
    /**
     *
     * @param <T>
     * @param primaryKeys List of primary keys
     * @param responseType Response or DTO Type
     * @param sessionId Session Id associated with authentication
     * @return
     */
    private <T> T delete(Object requestEntity, List<Object> primaryKeys, Class<T> responseType, String sessionId) {
        return delete(requestEntity, primaryKeys, responseType, null, sessionId);
    }

    /**
     *
     * @param primaryKeys List of primary keys
     * @param propertyMap Map used to control flow
     * @param sessionId Session Id associated with authentication
     * @return
     */
    private Response delete(Object requestEntity, List<Object> primaryKeys, Map<String, Object> propertyMap, String sessionId) {
        return delete(requestEntity, primaryKeys, Response.class, propertyMap, sessionId);
    }
    
    /**
     *
     * @param <T>
     * @param primaryKeys List of primary keys
     * @param responseType Response or String
     * @param propertyMap Map used to control flow
     * @param sessionId Session Id associated with authentication
     * @return
     */
    private <T> T delete(Object requestEntity, List<Object> primaryKeys, Class<T> responseType, Map<String, Object> propertyMap, String sessionId) {
        WebTarget resource = getWebTarget();
        if (requestEntity != null && useResourceInPath) {
            resource = resource.path(ClassUtils.getResourceName(requestEntity.getClass()));
        }
        if (propertyMap != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMPROPERTY, CommonRsUtils.getMapAsEncodedString(propertyMap));
        }
        if (sessionId != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMSESSION, sessionId);
        }
        resource = resource.path(getPath(primaryKeys));
        return resource.request().delete(responseType);
    }        

    /**
     *
     * @param <T>
     * @param responseType Response or String
     * @param propertyMap Map used to control flow
     * @param sessionId Session Id associated with authentication
     * @return
     * @throws ConstraintViolationException
     * @throws ValidationException
     * @throws NotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws MtsException
     */
    public <T> T newInstance(Class<T> responseType, Map<String, Object> propertyMap, String sessionId) 
        throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "newInstance ";
        WebTarget resource = getWebTarget();
        if (responseType != null && useResourceInPath) {
            resource = resource.path(ClassUtils.getResourceName(responseType));
        }        
        if (propertyMap != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMPROPERTY, CommonRsUtils.getMapAsEncodedString(propertyMap));
        }
        if (sessionId != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMSESSION, sessionId);
        }

        resource = resource.path("newInstance");
        return getResponse(responseType, resource);
    }        
    
    /**
     *
     * REVIEW
     * @param filterMap used as a bulk delete - custom implementation
     * @param sessionId Session Id associated with authentication
     * @return
     */
    private Response delete(Map<String, Object> filterMap, String sessionId) {
        WebTarget resource = getWebTarget();
        if (filterMap != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMFILTER, CommonRsUtils.getMapAsEncodedString(filterMap));
        }
        if (sessionId != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMSESSION, sessionId);
        }
        return resource.request().delete(Response.class);
    }
    
    private <T> T getResponse(Class<T> responseType, WebTarget resource) throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        Response response = null;
        try {
            // Get the response
            response = resource.request().get(Response.class);

            // Check the status and return
            return evaluateResponse(response, responseType);
        }
        finally {
            // Just in case we get here and the response is not closed, readEntity closes the response by default
            // But if during the Read operation a Jersey exception occurs we want to close out the response
            if (response != null && responseType != Response.class) {
                response.close();
            }
        }
    }
    
    public <T> T getReport(Class<? extends BaseDTO> requestEntity, Class<T> responseType, Map<String, Object> filterMap, Map<String, Object> propertyMap, String sessionId)
        throws MtsException, NotFoundException, AuthenticationException, AuthorizationException, ValidationException, ConstraintViolationException {
        return getReport(requestEntity, responseType, filterMap, null, propertyMap, sessionId);
    }

    public <T> T getReport(Class<? extends BaseDTO> requestEntity, Class<T> responseType, Map<String, Object> filterMap, Map<String, Object> reportParameters, 
            Map<String, Object> propertyMap, String sessionId) 
            throws MtsException, NotFoundException, AuthenticationException, AuthorizationException, ValidationException, ConstraintViolationException {    
        final String METHODNAME = "getReport ";
        WebTarget resource = getWebTarget();
        if (requestEntity != null && useResourceInPath) {
            resource = resource.path(ClassUtils.getResourceName(requestEntity));
        }
        resource = resource.path("report");
        if (filterMap != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMFILTER, CommonRsUtils.getMapAsEncodedString(filterMap));
        }
        if (propertyMap != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMPROPERTY, CommonRsUtils.getMapAsEncodedString(propertyMap));
        }
        if (reportParameters != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARAMREPORTPARAMS, CommonRsUtils.getMapAsJsonEncodedString(reportParameters));
        }
        if (sessionId != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMSESSION, sessionId);
        }
        Response response = resource.request().accept(MediaType.APPLICATION_OCTET_STREAM_TYPE).get();
        return evaluateResponse(response, responseType);        
    }
    
    /**
     *
     * @param message used to diagnose connectivity
     * @return
     */
    public String ping(String message) {
        WebTarget resource = getWebTarget();
        if (message != null) {
            resource = resource.queryParam("message", message);
        }
        resource = resource.path("ping");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     *
     * @param objects list of string objects to construct rest service path
     * @return
     */
    public String getPath(List<Object> objects) {
        final String METHODNAME = "getPath ";
        String path = "";
        if (objects != null) {
            int size = objects.size();
            int counter = 0;
            String format = "";
            String[] stringArray = new String[objects.size()];
            for (Object o : objects) {
                format += "{" + counter + "}";
                if (counter != size - 1) {
                    format += "/";
                }
                stringArray[counter] = o.toString();
                counter++;
            }
            path = MessageFormat.format(format, stringArray);
        }
        return path;
    }

    public SessionDTO loginProxyUser(String path, String username, String password, String sessionId) throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        return loginProxyUser(SessionDTO.class, path, username, password, sessionId);
    }
    
    public Boolean authenticateProxyUser(String path, String username, String password, String sessionId) 
            throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "authenticateProxyUser ";
        WebTarget resource = getWebTarget().path(path);
        if (username != null) {
            resource = resource.path(username);            
        }
        if (password != null) {
            resource = resource.queryParam("password", password);
        }
        if (sessionId != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMSESSION, sessionId);
        }
        return getResponse(Boolean.class, resource);
        
    }
    
    public <T> T loginProxyUser(Class<T> responseType, String path, String username, String password, String sessionId) 
            throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "loginProxyUser ";
        WebTarget webTarget = getWebTarget().path(path);
                
        Form form = new Form();
        form.param("username", username);
        form.param("password", password);
        form.param("sessionId", sessionId);

        Response response = webTarget
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);

        try {
            // Check the status and return
            return evaluateResponse(response, responseType);
        }
        finally {
            // Just in case we get here and the response is not closed, readEntity closes the response by default
            // But if during the Read operation a Jersey exception occurs we want to close out the response
            if (response != null && responseType != Response.class) {
                response.close();
            }
        }
    }
    
    public void logoutProxyUser(String path, boolean expired, String sessionId) 
            throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        
        final String METHODNAME = "logoutProxyUser ";
        WebTarget webTarget = getWebTarget().path(path);
        
        Form form = new Form();
        form.param("expired", Boolean.toString(expired));
        form.param("sessionId", sessionId);

        Response response = webTarget
                .request()
                .post(javax.ws.rs.client.Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);
        
        try {
            if (!(response.getStatus() == Response.Status.OK.getStatusCode())) {
                CommonRsUtils.throwException(response.readEntity(ErrorMessage.class));
            }        
        }
        finally {
            // Just in case we get here and the response is not closed, readEntity closes the response by default
            // But if during the Read operation a Jersey exception occurs we want to close out the response
            if (response != null) {
                response.close();
            }
            
        }
    }
    
    public <T> T changeProxyUserPassword(String path, Object requestEntity, Class<T> responseType, Map<String, Object> propertyMap, String sessionId) throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "changeProxyUserPassword ";
        
        WebTarget resource = getWebTarget().path(path);
        if (propertyMap != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMPROPERTY, CommonRsUtils.getMapAsEncodedString(propertyMap));
        }
        if (sessionId != null) {
            resource = resource.queryParam(CoreRsConstants.QUERYPARMSESSION, sessionId);
        }
        // Throw error if NOT BaseDTO, this supports compound primary key, Rest Service needs to be configured
        BaseDTO baseDTO = (BaseDTO) requestEntity;
        if (baseDTO != null) {
            List<Object> primaryKeys = DTOUtils.getPrimaryKeys(baseDTO);
            resource = resource.path(getPath(primaryKeys));
        }
        Response response = resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        
        return evaluateResponse(response, responseType);
    }

    /* Common function to evaluate the outcome of the response and return the appropriate type */
    private <T> T evaluateResponse(Response response, Class<T> responseType) 
            throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "evaluateResponse ";
        
        T returnType = null;
        // Check the status
        boolean success = (response.getStatus() == Response.Status.OK.getStatusCode());

        if (!success) {
            // Caller not interested in the response, translate and throw
            if (responseType != Response.class) {
                CommonRsUtils.throwException(response.readEntity(ErrorMessage.class));
            }
        }

        if (response != null) {
            if (responseType != Response.class) {
                returnType = response.readEntity(responseType);
            }
            else {
                returnType = (T) response;
            }
        }
        return returnType;
    }
    
}
