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
package org.cdsframework.rs.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.InternalServerErrorException;
import org.cdsframework.rs.exception.mapper.ErrorMessage;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ProcessingException;
import org.apache.commons.codec.binary.Base64;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.IncorrectResultSizeException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.rs.provider.CoreJacksonJsonProvider;
import org.cdsframework.util.DateUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;
import org.cdsframework.util.support.CoreConstants;
import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Copyright (C) 2015 New York City Department of Health and Mental Hygiene, Bureau of Immunization
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
 *
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about the this software, see http://www.hln.com/ice or send
 * correspondence to ice@hln.com.
 */

/**
 *
 * @author HLN Consulting, LLC
 */
public class CommonRsUtils { 
    private static final LogUtils logger = LogUtils.getLogger(CommonRsUtils.class.getName());
    
    public static void logException(Exception exception) {
        logger.info("calling logException ");
        logger.info("exception.getMessage()=", exception.getMessage());

        if (exception instanceof ClientErrorException) {
            ClientErrorException clientErrorException = (ClientErrorException) exception;
            ErrorMessage errorMessage = clientErrorException.getResponse().readEntity(ErrorMessage.class);
            logger.info("CE errorMessage.getExceptionClass()=", errorMessage.getExceptionClass());
            logger.info("CE errorMessage.getMessage()=", errorMessage.getMessage());
            logger.info("CE errorMessage.getStatus()=", errorMessage.getStatus());
//            logger.info("CE errorMessage.getStackTrace()=", errorMessage.getStackTrace());
            logger.info("An Exception has occurred; ", clientErrorException.getMessage(), exception);
        } else if (exception instanceof InternalServerErrorException) {
            logger.info("instance of InternalServerErrorException");
            InternalServerErrorException internalServerErrorException = (InternalServerErrorException) exception;
            ErrorMessage errorMessage = internalServerErrorException.getResponse().readEntity(ErrorMessage.class);
            logger.info("ISE errorMessage.getExceptionClass()=", errorMessage.getExceptionClass());
            logger.info("ISE errorMessage.getMessage()=", errorMessage.getMessage());
            logger.info("ISE errorMessage.getStatus()=", errorMessage.getStatus());
//            logger.info("ISE errorMessage.getStackTrace()=", errorMessage.getStackTrace());
            logger.info("An Exception has occurred; ", internalServerErrorException.getMessage(), exception);
        } else if (exception instanceof ProcessingException) {
            logger.info("instance of ProcessingException");
            ProcessingException processingException = (ProcessingException) exception;
            logger.info("PE.getMessage()=", processingException.getMessage(), exception);
            if (processingException instanceof MessageBodyProviderNotFoundException) {
                logger.error("Did you forget to register your ", CoreJacksonJsonProvider.class.getSimpleName(), 
                        " class or subclass of in your RS ApplicationConfig?, that might be the reason for this error.");
            }
        } else {
            logger.info("instance of Exception");
            logger.info("An Exception has occurred; ", exception.getMessage(), exception);
        }
    }
    
    public static void throwException(ErrorMessage errorMessage) throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "throwException ";
        String exceptionClass = errorMessage.getExceptionClass();
        logger.info(METHODNAME, "exceptionClass=", exceptionClass);
        StackTraceElement[] stackTraceElements = errorMessage.getStackTraceElements();
        if (exceptionClass.equalsIgnoreCase(ConstraintViolationException.class.getCanonicalName())) {
            ConstraintViolationException constraintViolationException =  ConstraintViolationException.getConstraintViolationException(errorMessage.getPropertyMap());
            if (stackTraceElements != null) {
                constraintViolationException.setStackTrace(stackTraceElements);
            }
            throw constraintViolationException;
        } else if (exceptionClass.equalsIgnoreCase(ValidationException.class.getCanonicalName())) {
            ValidationException validationException = ValidationException.getValidationException(errorMessage.getPropertyMap(), 
                    errorMessage.getBrokenRules());
            if (stackTraceElements != null) {
                validationException.setStackTrace(stackTraceElements);
            }
            throw validationException;
        } else if (exceptionClass.equalsIgnoreCase(NotFoundException.class.getCanonicalName())) {
            NotFoundException notFoundException = NotFoundException.getNotFoundException(errorMessage.getPropertyMap());
            if (stackTraceElements != null) {
                notFoundException.setStackTrace(stackTraceElements);
            }
            throw notFoundException;
        } else if (exceptionClass.equalsIgnoreCase(AuthenticationException.class.getCanonicalName())) {
            AuthenticationException authenticationException = AuthenticationException.getAuthenticationException(errorMessage.getPropertyMap());
            if (stackTraceElements != null) {
                authenticationException.setStackTrace(stackTraceElements);
            } 
            throw authenticationException;
        } else if (exceptionClass.equalsIgnoreCase(AuthorizationException.class.getCanonicalName())) {
            AuthorizationException authorizationException = AuthorizationException.getAuthorizationException(errorMessage.getPropertyMap());
            if (stackTraceElements != null) {
                authorizationException.setStackTrace(stackTraceElements);
            }
            throw authorizationException;
        } else if (exceptionClass.equalsIgnoreCase(MtsException.class.getCanonicalName())) {
            MtsException mtsException = MtsException.getMtsException(errorMessage.getPropertyMap());
            if (stackTraceElements != null) {
                mtsException.setStackTrace(stackTraceElements);
            }
            throw mtsException;
        } else if (exceptionClass.equalsIgnoreCase(IncorrectResultSizeException.class.getCanonicalName())) {
            IncorrectResultSizeException incorrectResultSizeException = new IncorrectResultSizeException(errorMessage.getExceptionMessage());
            if (stackTraceElements != null) {
                incorrectResultSizeException.setStackTrace(stackTraceElements);
            }
            throw incorrectResultSizeException;
        } else {
            RuntimeException runtimeException = new RuntimeException(errorMessage.toString());
            if (stackTraceElements != null) {
                runtimeException.setStackTrace(stackTraceElements);
            }
            throw runtimeException;
        }
    }

    public static String getMapAsEncodedString(Map<String, Object> parameterMap) {
        //return getMapAsEncodedString(parameterMap, true);
        return getMapAsEncodedString(parameterMap, false);        
    }

    /* JSON encoded version
    *  Current Issues 
    *  * Not a human readable interface, its going to be difficult to invoke a GET in the browser without encoding the json string
    *  * Long with a precision of Integer become Integer, Double with a precision of Long become Long, etc
    *  * ObjectUtils.objectToLong, objectToDouble etc will need to used to return the property type
    *
    public static String getMapAsEncodedString(Map<String, Object> parameterMap, boolean urlEncode) {
        final String METHODNAME = "getMapAsEncodedString ";
        if (logger.isDebugEnabled()) {
            logger.debug(METHODNAME, "parameterMap=", parameterMap);
        }
        String encodedString = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SimpleDateFormat iso8601DateFormat = new SimpleDateFormat(DateUtils.ISO8601_UTC_DATETIME);
            objectMapper.setDateFormat(iso8601DateFormat);            
            String mapAsString = objectMapper.writeValueAsString(parameterMap);
            if (urlEncode) {
                encodedString = URLEncoder.encode(mapAsString);
            }
        } catch (JsonProcessingException ex) {
            logger.error(METHODNAME, "An ", ex.getClass().getSimpleName(), " has occurred; Message: ", ex.getMessage(), ex);
        }
        return encodedString;
    }
    
    public static Map<String, Object> getMapFromEncodedString(String encodedString) {
        final String METHODNAME = "getMapFromEncodedString ";
        if (logger.isDebugEnabled()) {
            logger.debug(METHODNAME, "encodedString=", encodedString);
        }
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        String decodedString = URLDecoder.decode(encodedString);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            parameterMap = objectMapper.readValue(new String(decodedString), Map.class);
            if (logger.isDebugEnabled()) {
                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    Object value = entry.getValue();
                    logger.debug(METHODNAME, "entry.getKey()=", entry.getKey(), " value=", value.getClass().getSimpleName(), " value=", value.getClass().getSimpleName());
                }            
            }
        } catch (IOException ex) {
            logger.error(METHODNAME, "An ", ex.getClass().getSimpleName(), " has occurred; Message: ", ex.getMessage(), ex);
        }
        return parameterMap;
    }
    */
    
    /* 
    * Human readable version 
    * There are issues with this as well.
    * Does not support ArrayLists of parameters
    * The token used to delimit the filter will need to be escaped
    */
    public static Map<String, Object> getMapFromEncodedString(String encodedString) {
        final String METHODNAME = "getMapFromEncodedString ";
        if (logger.isDebugEnabled()) {
            logger.debug(METHODNAME, "encodedString=", encodedString);
        }
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(encodedString)) {
            // Handles escaped comma's, split on each ", " that is not preceeded by a backslash. negative lookahead
            final String commaRegEx = "(?<!\\\\),";
            final String equalRegEx = "(?<!\\\\)=";
            List<String> encodedStrings = Arrays.asList(encodedString.split(commaRegEx));
            long start = System.nanoTime();
            for (String encString : encodedStrings) {
                String[] nameValuePair = encString.split(equalRegEx, 2); // Just split at the first "=" and the rest must be the parameter.
                if (logger.isDebugEnabled()) {
                    logger.debug(METHODNAME, "nameValuePair=", Arrays.asList(nameValuePair));
                }
                String key = null;
                String value = null;
                if (nameValuePair.length > 0) {
                    key = nameValuePair[0].trim();
                }
                if (nameValuePair.length > 1) {
                    // Strip any escape charaters
                    value = nameValuePair[1].replace("\\", "");
                }
                if (value != null) {
                    parameterMap.put(key, value);
                }
            }
            logger.logDuration(LogLevel.DEBUG, METHODNAME, start);                                                                            
        }
        return parameterMap;
    }    
    
    public static String getMapAsEncodedString(Map<String, Object> parameterMap, boolean urlEncode) {
        final String METHODNAME = "getMapAsEncodedString ";
        String encodedString = "";
        if (parameterMap != null && !parameterMap.isEmpty()) {
            Set<Map.Entry<String, Object>> entrySet = parameterMap.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                Object oValue = entry.getValue();
                if (oValue != null) {
                    if (urlEncode) {
                        key = URLEncoder.encode(key);
                    }
                    encodedString += key;
                    String sValue = "";
                    if (oValue instanceof Date) {
                        sValue = DateUtils.getFormattedDate((Date) oValue, DateUtils.ISO8601_DATETIME_FORMAT);
                        //sValue = DateUtils.getFormattedDate((Date) oValue, DateUtils.ISO8601_UTC_DATETIME);
                    } else {
                        sValue = oValue.toString().replaceAll("\\,", "\\\\,");
                    }
                    if (urlEncode) {
                        sValue = URLEncoder.encode(sValue);
                    }
                    encodedString += "=" + sValue + ",";
                }
            }
            encodedString = encodedString.substring(0, encodedString.length() - 1);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(METHODNAME, "encodedString=", encodedString);
        }
        return encodedString;
    }

    //
    // FYI These are only used for a one off getReport call to support an Arrays
    // Eventually this will be corrected and rolled into a call that supports both
    // legacy human readable get parameters and json encoded 
    // Which means the extra getReports call will be removed
    //
    // See above commented out JSON encoded version.
    //
    // Try not to get confused with these calls as they are only used from one place.
    //
    /* Used as a work around for getReports where ArrayLists are required */
    public static String getMapAsJsonEncodedString(Map<String, Object> parameterMap) {
        return getMapAsJsonEncodedString(parameterMap, true);
    }
    
    public static String getMapAsJsonEncodedString(Map<String, Object> parameterMap, boolean base64Encode) {
        
        ObjectMapper mapper = new ObjectMapper();
        try {
            byte[] json = mapper.writeValueAsBytes(parameterMap);
            if (base64Encode) {
                return Base64.encodeBase64URLSafeString(json);
            } else {
                return new String(json);
            }
        } catch (JsonProcessingException e) {
           throw new IllegalArgumentException("Error encoding parameter map as JSON: "+ e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromJsonEncodedString(String encoded) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
        Map<String, Object> parameters = mapper.readValue(Base64.decodeBase64(encoded), Map.class);
        // Need to walk the map converting integer types to Longs because we're stuck with Jackson 2.5 due to Glassfish
        // Jackson 2.6 has an option to do it directly
        // TODO: put date conversion here as well
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            Object value = entry.getValue();
            if (Number.class.isAssignableFrom(value.getClass())) {
                entry.setValue(Long.valueOf(((Number) value).longValue()));
            }
        }
        return parameters;
    }
    
}
