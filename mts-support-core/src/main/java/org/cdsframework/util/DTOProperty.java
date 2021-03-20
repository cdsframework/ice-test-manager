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
package org.cdsframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.EnumAccess;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.DatabaseType;
import org.cdsframework.enumeration.FieldType;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.group.DateTime;

/**
 *
 * @author HLN Consulting, LLC
 */
public class DTOProperty {

    private final static LogUtils logger = LogUtils.getLogger(DTOProperty.class);
    private Field field;
    private Field parentField;
    private FieldType fieldType;

    private List<DTOProperty> baseDTOProperties;
    private Column[] columns;
    private Method enumGetter;
    private Method enumSetter;
    private Map<Object, Object> dtoValueMap = new HashMap<Object, Object>();
    private int dtoValueMatchCount = 0;

    public DTOProperty(Field field, Column[] columns) {
        final String METHODNAME = "DTOProperty ";
        this.field = field;
        this.field.setAccessible(true);
        this.columns = columns;

        // Check Type
        Class<?> type = field.getType();
        this.fieldType = FieldType.getFieldType(type);
        if (this.fieldType == FieldType.BaseDTO) {
            int index = 0;
            baseDTOProperties = new ArrayList<DTOProperty>();
            List<Field> primaryKeyFields = DTOUtils.getPrimaryKeyFields((Class<? extends BaseDTO>) type);
            // Size must match
            if (primaryKeyFields.size() == columns.length) {
                for (Field primaryKeyField : primaryKeyFields) {
                    primaryKeyField.setAccessible(true);
                    baseDTOProperties.add(new DTOProperty(primaryKeyField, new Column[]{columns[index]}));
                    index++;
                }
            } else {
                String errorMessage = field.getName() + " is a BaseDTO and its columns do not match its defined primaryKey fields";
                throw new IllegalStateException(errorMessage);
            }
        } else if (this.fieldType == FieldType.Enumeration) {
//            EnumAccess enumAccess = field.getAnnotation(EnumAccess.class);
//            String getter = "toString";
//            String setter = "valueOf";
//            if (enumAccess != null) {
//                getter = enumAccess.getter();
//                setter = enumAccess.setter();
//            }
            try {
                this.enumGetter = getEnumGetter(field);
//                for (Method method : field.getType().getMethods()) {
//                    String name = method.getName();
////                    logger.info(METHODNAME, "name=", name);
//                    if (name.equalsIgnoreCase(getter)) {
//                        this.enumGetter = method;
//                        break;
//                    }
//                }
                this.enumSetter = getEnumSetter(field);
//                for (Method method : field.getType().getDeclaredMethods()) {
//                    String name = method.getName();
////                    logger.info(METHODNAME, "name=", name);
//                    if (name.equalsIgnoreCase(setter)) {
//                        this.enumSetter = method;
//                        break;
//                    }
//                }
//                this.enumGetter = field.getType().getMethod(getter);
//                this.enumSetter = field.getType().getDeclaredMethod(setter);
//            } catch (NoSuchMethodException ex) {
//                Logger.getLogger(DTOProperty.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                logger.error(METHODNAME, "A ", ex.getClass().getSimpleName(), " has occurred; Message: ", ex.getMessage(), ex);
            }
        }
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Field getParentField() {
        return parentField;
    }

    public void setParentField(Field parentField) {
        this.parentField = parentField;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public List<DTOProperty> getBaseDTOProperties() {
        return baseDTOProperties;
    }

    public void setBaseDTOProperties(List<DTOProperty> baseDTOProperties) {
        this.baseDTOProperties = baseDTOProperties;
    }

    public Column[] getColumns() {
        return columns;
    }

    public void setColumns(Column[] columns) {
        this.columns = columns;
    }

    public Method getEnumGetter() {
        return enumGetter;
    }

    public void setEnumGetter(Method enumGetter) {
        this.enumGetter = enumGetter;
    }

    public Method getEnumSetter() {
        return enumSetter;
    }

    public void setEnumSetter(Method enumSetter) {
        this.enumSetter = enumSetter;
    }

    public void setDataValue(Column column, Object dbValue, DatabaseType databaseType, BaseDTO dto)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, MtsException, NotFoundException {
        final String METHODNAME = "setDataValue ";
        //logger.info(METHODNAME, "dbValue=", dbValue, " getFieldType()=", getFieldType());
        Object dtoValue = dbValue;
        String columnName = column.name();
        if (dbValue != null) {
            switch (fieldType) {
                case BaseDTO:
                    BaseDTO referenceDTO = (BaseDTO) field.get(dto);
                    if (referenceDTO == null) {
                        referenceDTO = (BaseDTO) field.getType().newInstance();
                    }
                    for (DTOProperty baseDTOProperty : baseDTOProperties) {
                        for (Column baseDTOColumn : baseDTOProperty.getColumns()) {
                            if (baseDTOColumn.name().equals(columnName)) {
                                baseDTOProperty.setDataValue(column, dbValue, databaseType, referenceDTO);
                                break;
                            }
                        }
                    }
                    // Point to the initialized reference
                    dtoValue = referenceDTO;
                    break;
                /*
                case BigDecimal:
                    dtoValue = NumberUtils.objectToBigDecimal(dbValue);
                    break;
                case Class:
                    dtoValue = ClassUtils.classForName((String) dbValue);
                    break;
                case Long:
                    dtoValue = NumberUtils.objectToLong(dbValue);
                    break;
                case Integer:
                    dtoValue = NumberUtils.objectToInteger(dbValue);
                    break;
                case Double:
                    dtoValue = NumberUtils.objectToDouble(dbValue);
                    break;
                case Float:
                    dtoValue = NumberUtils.objectToFloat(dbValue);
                    break;
                case Enumeration:
                    dtoValue = getEnumSetter().invoke(field, dbValue);
                    break;
                case Boolean:
                    if (databaseType == DatabaseType.ORACLE || column.resultSetClass() == String.class) {
                        dtoValue = StringUtils.stringToBooleanObject((String) dbValue);
                    }
                    break;
                */
                default:
                    dtoValue = getDataValue(field, fieldType, enumSetter, dbValue, dto);
            }
        }
        if (dtoValue != null) {
            field.set(dto, dtoValue);
        }

    }
    
    public static Object getDataValue(Field field, FieldType fieldType, Method enumSetter, Object value, BaseDTO dto)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, MtsException, NotFoundException {
        final String METHODNAME = "getDataValue ";
        logger.debug(METHODNAME, "field=", field, " value=", value, " value.getClass().getCanonicalName()=", value.getClass().getCanonicalName(), " FieldType=", fieldType);
        Object dtoValue = value;
        if (value != null) {
            switch (fieldType) {
                case BaseDTO:
                    break;
                case BigDecimal:
                    dtoValue = ObjectUtils.objectToBigDecimal(value);
                    break;
                case Class:
                    dtoValue = ClassUtils.classForName((String) value);
                    break;
                case Long:
                    dtoValue = ObjectUtils.objectToLong(value);
                    break;
                case Integer:
                    dtoValue = ObjectUtils.objectToInteger(value);
                    break;
                case Double:
                    dtoValue = ObjectUtils.objectToDouble(value);
                    break;
                case Float:
                    dtoValue = ObjectUtils.objectToFloat(value);
                    break;
                case Date:
                    dtoValue = ObjectUtils.objectToDate(value);
                    break;
                case Enumeration:
                    dtoValue = enumSetter.invoke(field, value);
                    break;
                case Boolean:
                    // Old logic used DatabaseType, new logic resolved Boolean without DatabaseType
//                    if (databaseType == DatabaseType.ORACLE || column.resultSetClass() == String.class) {
//                        dtoValue = StringUtils.stringToBooleanObject((String) dbValue);
//                    }
                    // If database returns a String YyNnTrueFalse its converted into a Boolean
                    // If database returns a Boolean no conversion is necessary 
                    dtoValue = ObjectUtils.objectToBoolean(value, true);
                    break;
            }
        }
        return dtoValue;
    }
    
    public static Object getDataValue(Field field, Object value, BaseDTO dto)
        throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, MtsException, NotFoundException {
        Method enumSetter = null;
        FieldType fieldType = FieldType.getFieldType(field.getType());
        if (fieldType == FieldType.Enumeration) {
            enumSetter = getEnumSetter(field);
        }
        return getDataValue(field, fieldType, enumSetter, value, dto);
    }        

    public static Method getEnumSetter(Field field) {
        final String METHODNAME = "getEnumSetter ";
        EnumAccess enumAccess = field.getAnnotation(EnumAccess.class);
        String setter = "valueOf";
        Method enumSetter = null;
        if (enumAccess != null) {
            setter = enumAccess.setter();
        }
        try {
            for (Method method : field.getType().getDeclaredMethods()) {
                String name = method.getName();
//                    logger.info(METHODNAME, "name=", name);
                if (name.equalsIgnoreCase(setter)) {
                    enumSetter = method;
                    break;
                }
            }
        } catch (SecurityException ex) {
            logger.error(METHODNAME, "A ", ex.getClass().getSimpleName(), " has occurred; Message: ", ex.getMessage(), ex);
        }
        return enumSetter;
    }
    
    public static Method getEnumGetter(Field field) {
        final String METHODNAME = "getEnumGetter ";
        EnumAccess enumAccess = field.getAnnotation(EnumAccess.class);
        String getter = "toString";
        Method enumGetter = null;
        if (enumAccess != null) {
            getter = enumAccess.getter();
        }
        try {
            for (Method method : field.getType().getMethods()) {
                String name = method.getName();
//                    logger.info(METHODNAME, "name=", name);
                if (name.equalsIgnoreCase(getter)) {
                    enumGetter = method;
                    break;
                }
            }
        } catch (SecurityException ex) {
            logger.error(METHODNAME, "A ", ex.getClass().getSimpleName(), " has occurred; Message: ", ex.getMessage(), ex);
        }
        return enumGetter;
    }
    
    public Object getDataValue(Column column, DatabaseType databaseType, Object dtoValue)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        final String METHODNAME = "getDataValue ";
        long startTime = System.nanoTime();
        Object dbValue = dtoValue;
        String columnName = column.name();
        if (dtoValue != null) {
            switch (this.fieldType) {
                case BaseDTO:
                    // if the value isn't a dto - it doesn't need converting - this is a special case when a reference dto is also a foreign key
                    if (BaseDTO.class.isAssignableFrom(dtoValue.getClass())) {
                        BaseDTO baseDTO = (BaseDTO) dtoValue;
                        for (DTOProperty baseDTOProperty : baseDTOProperties) {
                            for (Column baseDTOColumn : baseDTOProperty.getColumns()) {
                                if (baseDTOColumn.name().equals(columnName)) {
                                    dbValue = baseDTOProperty.getDataValue(column, databaseType, baseDTOProperty.getField().get(baseDTO));
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case Boolean:
                    if (databaseType == DatabaseType.ORACLE || column.resultSetClass() == String.class) {
                        dbValue = StringUtils.booleanToYN((Boolean) dtoValue);
                    } else {
                        dbValue = (Boolean) dtoValue;
                    }
                    break;
                case Class:
                    dbValue = ((Class) dtoValue).getCanonicalName();
                    break;
                case Enumeration:
                    // Save the invoke call if the dtoValue is cached
                    dbValue = dtoValueMap.get(dtoValue);
                    if (dbValue == null) {
                        dbValue = getEnumGetter().invoke(dtoValue);
                        dtoValueMap.put(dtoValue, dbValue);
                    }
                    break;
                case Date:
                    if (column.resultSetClass() == DateTime.class) {
                        // Strip the milliseconds
                        dbValue = DateUtils.getTruncatedDateTime((Date) dbValue);
                    }
            }
        }
        logger.logDuration(LogLevel.DEBUG, METHODNAME, startTime);                                                                            
        return dbValue;
    }

    @Override
    public String toString() {
        return (new StringBuilder()
                .append("DTOProperty={field.name=")
                .append(field != null ? field.getName() : "")
                .append("; parentField.name=")
                .append(parentField != null ? parentField.getName() : "")
                .append("; fieldType=")
                .append(fieldType != null ? fieldType : "")
                .append("; dtoValueMap=")
                .append(dtoValueMap.toString())
                .append("}")).toString();
    }
    
    public static Object getEnumValue(BaseDTO baseDTO, String propertyName, Object value) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Field field = baseDTO.getClass().getDeclaredField(propertyName);
        field.setAccessible(true);
        Method enumGetter = DTOProperty.getEnumGetter(field);
        return enumGetter.invoke(value);
    }

}
