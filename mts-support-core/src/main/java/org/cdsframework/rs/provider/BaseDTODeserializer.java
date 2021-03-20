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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.util.ClassUtils;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
public class BaseDTODeserializer extends StdDeserializer<BaseDTO> {

    private static final long serialVersionUID = -2046975745525169590L;

    private final static LogUtils logger = LogUtils.getLogger(BaseDTODeserializer.class);
    private final Map<String, Class<? extends BaseDTO>> registry = new HashMap<String, Class<? extends BaseDTO>>();
    public final static String resourcePropertyName = "resourceName";
    
    public BaseDTODeserializer() {
        super(BaseDTO.class);
    }
    
    private void registerBaseDTO(String key, Class<? extends BaseDTO> baseDTOClass) {
        final String METHODNAME = "registerBaseDTO ";
        boolean containsKey = registry.containsKey(key);
        if (containsKey) {
            Class<? extends BaseDTO> cls = registry.get(key);
            logger.error(METHODNAME, "The resource ", key, " is already registered, the class that its registered for is ", 
                    cls.getCanonicalName(), ". You will need to define an alternate resource name, either change your class name ",
                    "to something unique across the class path. Or if that isnt an option, use XmlRootElement to define ",
                    "an alternate name that is unique.");
        }
        else {
            registry.put(key, baseDTOClass);
        }
    }
    
    public void registerBaseDTO(Class<? extends BaseDTO> baseDTOClass) {
        // We only really need this one as this is used in the json string as well as the path
        // When POST/PUT is use the payload contains the resource and it is then used to 
        // deserialize the object.
        // The resource is also used in the path for GET's and DELETE's
        registerBaseDTO(ClassUtils.getResourceName(baseDTOClass), baseDTOClass);
        
        //
        // If it becomes a problem we can add these, but we wouldnt want to use these
        // in the path as it refers to the implementation DTO and it would have to be in the
        // api where its not really a friendly name
        //
//        registerBaseDTO(baseDTOClass.getCanonicalName(), baseDTOClass);
//        registerBaseDTO(baseDTOClass.getSimpleName(), baseDTOClass);        
//        
//        XmlRootElement xmlRootElement = (XmlRootElement) baseDTOClass.getAnnotation(XmlRootElement.class);
//        if (xmlRootElement != null && xmlRootElement.name() != null) {
//            registerBaseDTO(xmlRootElement.name(), baseDTOClass);
//        }
        
    }    

    @Override
    public BaseDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final String METHODNAME = "deserialize ";
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        ObjectNode root = (ObjectNode) mapper.readTree(jp);
        Class<? extends BaseDTO> baseDTOClass = null;
        if (logger.isDebugEnabled()) {
            logger.debug(METHODNAME, "starting deserilization");
        }
                
        String resource = null;
        Iterator<Entry<String, JsonNode>> elementsIterator = root.fields();
        while (elementsIterator.hasNext()) {
            Entry<String, JsonNode> element = elementsIterator.next();
            String name = element.getKey();
            JsonNode value = element.getValue();
            if (name.equalsIgnoreCase(resourcePropertyName)) {
                resource = value.textValue();
                if (registry.containsKey(resource)) {
                    baseDTOClass = registry.get(resource);
                    break;
                }
            }
        }
        if (baseDTOClass == null) {
            String errorMessage = null;
            if (resource == null) {
                errorMessage = "Unable to locate BaseDTO subclass to deserialize, " + 
                        "resourceType was not provided in the Json string, make sure your BaseDTO includes " + 
                        "the resourceType setter/getter and its properly initialized in the BaseDTO constructor.";
                logger.error(METHODNAME, errorMessage);
            }
            else {
                errorMessage = "Unable to locate BaseDTO subclass to deserialize, resource=" + resource + 
                               " was not registered in your configured ObjectMapper";
            }
            logger.error(METHODNAME, errorMessage);
            throw new IOException(errorMessage);
        }
        
        return mapper.readValue(root.toString(), baseDTOClass);
    }
}
