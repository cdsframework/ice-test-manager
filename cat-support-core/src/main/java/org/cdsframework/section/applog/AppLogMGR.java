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
package org.cdsframework.section.applog;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.commons.beanutils.PropertyUtils;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.AppLogDTO;
import org.cdsframework.util.ClassUtils;
import org.cdsframework.util.ObjectUtils;

/**
 *
 * @author HLN Consulting LLC
 *
 */
@Named
@ViewScoped
public class AppLogMGR extends BaseModule<AppLogDTO> {

    private static final long serialVersionUID = 2214552153922068597L;

    private final String lineSeparator = System.getProperty("line.separator");

    @Override
    protected void initialize() {
        final String METHODNAME = "initialize ";
        logger.logBegin(METHODNAME);
        setLazy(true);
        setBaseHeader("App Log");
        setSaveImmediately(true);
    }

    public String getObjectData() throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        AppLogDTO appLogDTO = getParentDTO();
        String objectData = "";
        if (appLogDTO != null) {
            if (appLogDTO.getObjectData() != null) {
                Object object = ObjectUtils.deserializeObject(appLogDTO.getObjectData());
                objectData = object.getClass().getSimpleName();
                objectData += " [ " + getObjectData(object) + " ]";
                if (object instanceof BaseDTO) {
                    // Get Children
                    BaseDTO baseDTO = (BaseDTO) object;
                    Map<Class, List<BaseDTO>> childDTOMap = baseDTO.getChildDTOMap();
                    Set<Entry<Class, List<BaseDTO>>> childDTOMapEntrySet = childDTOMap.entrySet();
                    for (Entry<Class, List<BaseDTO>> dtoMapEntry : childDTOMapEntrySet) {
                        List<BaseDTO> childDTOs = dtoMapEntry.getValue();
                        for (BaseDTO childDTO : childDTOs) {
                            objectData += lineSeparator + lineSeparator;
                            objectData += childDTO.getClass().getSimpleName() + " [" + getObjectData(childDTO) + " ]";
                        }
                    }
                    Map<String, Object> queryMap = baseDTO.getQueryMap();
                    if (queryMap != null) {
                        Set<Entry<String, Object>> queryEntrySet = queryMap.entrySet();
                        objectData += lineSeparator + lineSeparator;
                        objectData += "QueryMap" + " [ ";
                        boolean includeComma = false;
                        for (Entry<String, Object> queryEntry : queryEntrySet) {
                            String key = queryEntry.getKey();
                            Object value = queryEntry.getValue();
                            if (includeComma) {
                                objectData += ", ";
                            }
                            objectData += key + "=" + value;
                            includeComma = true;
                        }
                        objectData += " ]";
                    }
                }
            }
        }
        return objectData;
    }

    private String getObjectData(Object object) throws IllegalAccessException, InvocationTargetException {
        String objectData = "";
        Class objClass = object.getClass();
        List<Field> fields = ClassUtils.getNonBaseDTODeclaredFields(objClass);
        for (Field field : fields) {
            try {
                String propertyName = field.getName();
                Object propertyValue = PropertyUtils.getProperty(object, field.getName());
                if (objectData.length() > 0) {
                    objectData += ", ";
                }
                if (propertyValue instanceof BaseDTO) {
                    BaseDTO referenceDTO = (BaseDTO) propertyValue;
                    propertyValue = "[ " + referenceDTO.getClass().getSimpleName() + " [" + getObjectData(referenceDTO) + " ]";
                }
                objectData += propertyName + "=" + propertyValue;
            } catch (NoSuchMethodException e) {
                // ignore
            }
        }
        return objectData;

    }
}
