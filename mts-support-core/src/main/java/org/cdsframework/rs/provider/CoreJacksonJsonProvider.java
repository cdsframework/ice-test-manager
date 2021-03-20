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
package org.cdsframework.rs.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.rs.support.CoreConfiguration;
import org.cdsframework.util.ClassUtils;
import static org.cdsframework.util.ClassUtils.getClassesFromClasspath;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
@Provider
public class CoreJacksonJsonProvider implements ContextResolver<ObjectMapper> {
    
    protected static LogUtils logger = LogUtils.getLogger(CoreJacksonJsonProvider.class);
    private ObjectMapper defaultObjectMapper = null;

    public CoreJacksonJsonProvider() {
        this(CoreConfiguration.getJsonInclude());
    }    

    public CoreJacksonJsonProvider(JsonInclude.Include include) {
        final String METHODNAME = "constructor ";
        logger.info(METHODNAME);
        defaultObjectMapper = createObjectMapper(include, null);
    }
    
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return defaultObjectMapper;
    }

    public ObjectMapper createObjectMapper(JsonInclude.Include jsonInclude, String[] ignorableFields) {
        final String METHODNAME = "createObjectMapper ";
        logger.info(METHODNAME, "creating objectMapper for ", CoreJacksonJsonProvider.class.getCanonicalName());

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonAnnotationIntrospector primaryIntrospector = new JacksonAnnotationIntrospector();
        CoreJaxbAnnotationIntrospector secondaryIntrospector = new CoreJaxbAnnotationIntrospector();
        if (ignorableFields != null) {
            secondaryIntrospector.setIgnoreableFields(ignorableFields);
        }
        AnnotationIntrospector annotationIntrospector = AnnotationIntrospector.pair(primaryIntrospector, secondaryIntrospector);            
        objectMapper.setAnnotationIntrospector(annotationIntrospector);

        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        if (jsonInclude != null) {
            objectMapper.setSerializationInclusion(jsonInclude);
        }
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

    public void registerDTOs(String packageName) {
        final String METHODNAME = "registerDTOs ";
        List<Class<? extends BaseDTO>> dtoClasses = new ArrayList<Class<? extends BaseDTO>>();
        try {
            Class[] classes = ClassUtils.getClassesFromClasspath(packageName);
            for (Class cls : classes) {
                if (BaseDTO.class.isAssignableFrom(cls)) {
                    dtoClasses.add(cls);
                }
            }
            if (!dtoClasses.isEmpty()) {
                registerDTOs(dtoClasses);
                logger.info(METHODNAME, "dtoClasses=", dtoClasses );
                logger.info(METHODNAME, "dtoClasses.size()=", dtoClasses.size() );                
            }
        }
        catch (IOException | ClassNotFoundException | URISyntaxException e) {
            String errorMessage = "An " + e.getClass().getSimpleName() + " has occurred; Message: " + e.getMessage();
            logger.error(METHODNAME, errorMessage);
            throw new RuntimeException(errorMessage, e);
        }
    }
    
    public void registerDTOs(List<Class<? extends BaseDTO>> dtoClasses) {
        if (defaultObjectMapper != null && dtoClasses != null) {
            BaseDTODeserializer deserializer = new BaseDTODeserializer();
            //
            // Register all DTOs that will be deserialized, this is primarily for POST/PUT/DELETE as the json object
            // is received with an embedded resource type/name
            //
            for (Class<? extends BaseDTO> dtoClass : dtoClasses) {
                deserializer.registerBaseDTO(dtoClass);
            }
            SimpleModule simpleModule = new SimpleModule("PolymorphicDTODeserializerModule", new Version(1, 0, 0, null));
            simpleModule.addDeserializer(BaseDTO.class, deserializer);
            defaultObjectMapper.registerModule(simpleModule);
        }
    }
    
//    public void registerDTOs(String dtoStringArray) {
//        final String METHODNAME = "registerDTOs ";
//        String[] dtos = dtoStringArray.split(",");
//        List<Class<? extends BaseDTO>> dtoList = new ArrayList<Class<? extends BaseDTO>>();
//        for (String dto : dtos) {
//            logger.info(METHODNAME, "dto=", dto );
//            try {
//                dtoList.add((Class<? extends BaseDTO>) Class.forName(dto));
//            } catch (ClassNotFoundException ex) {
//                logger.error(METHODNAME, "dto=", dto + " was not found, Message:", ex.getMessage() );
//            }
//        }
//        if (!dtoList.isEmpty()) {
//            registerDTOs(dtoList);
//        }
//        
//    }
}