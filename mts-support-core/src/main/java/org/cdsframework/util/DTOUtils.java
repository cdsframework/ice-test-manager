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

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.cdsframework.annotation.Audit;
import org.cdsframework.annotation.SortColumn;
import org.cdsframework.annotation.ColumnSubstitutions;
import org.cdsframework.annotation.DTOWrapper;
import org.cdsframework.annotation.SortColumns;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.NoId;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.OrderByMapEntries;
import org.cdsframework.annotation.OrderByMapEntry;
import org.cdsframework.annotation.ParentBehavior;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReadOnly;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.RefreshOnAddOrUpdate;
import org.cdsframework.annotation.RowsReturnCountBehavior;
import org.cdsframework.annotation.Table;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.enumeration.DatabaseType;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.exceptions.AnnotationException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.util.comparator.IdFieldComparator;
import org.cdsframework.util.support.CoreConstants;
import org.cdsframework.annotation.NoDAO;
import org.cdsframework.util.support.CorePropertyChangeEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
public class DTOUtils {

    private static final LogUtils logger = LogUtils.getLogger(DTOUtils.class);
    private static Method dtoStateSetter = null;

    public static boolean isQueryLazy(BaseDTO baseDTO) {
        final String METHODNAME = "isQueryLazy ";
        long start = System.nanoTime();
        boolean lazy = false;
        if (baseDTO != null) {
            Object lazyValue = baseDTO.getQueryMap().get(CoreConstants.LAZY);
            if (lazyValue != null) {
                lazy = ObjectUtils.objectToBoolean(lazyValue);
                //lazy = (Boolean) lazyValue;
            }
        }
        logger.logDuration(LogLevel.DEBUG, METHODNAME, start);                                                                            
        return lazy;
    }

    public static void setDTOState(BaseDTO baseDTO, DTOState dtoState) {
        final String METHODNAME = "setDTOState ";
        //long timeNow = System.nanoTime();
        try {
            if (dtoStateSetter == null) {
                dtoStateSetter = BaseDTO.class.getDeclaredMethod("setDTOState", new Class[]{DTOState.class});
                dtoStateSetter.setAccessible(true);
            }
            dtoStateSetter.invoke(baseDTO, new Object[]{dtoState});
        } catch (Exception ex) {
            String errorMessage = METHODNAME + "An unexpected exception has occurred; Message: " + ex.getMessage();
            throw new IllegalStateException(errorMessage);
        } finally {
            //System.out.println("DTOSTate: " + (System.nanoTime() - timeNow));
        }
    }

    public static void unsetDTOState(BaseDTO baseDTO) {
        final String METHODNAME = "unsetDTOState ";
        // Reset the DTO state
        setDTOState(baseDTO, DTOState.UNSET);
        // Clears the PropertyChangeEventMap
        if (baseDTO != null) {
            if (baseDTO.getPropertyChangeEventMap() != null) {
                baseDTO.getPropertyChangeEventMap().clear();
            } else {
                logger.info(METHODNAME, "baseDTO.getPropertyChangeEventMap is null!");
            }
        } else {
            logger.info(METHODNAME, "baseDTO is null!");
        }
    }
    
    /*
    * Make sure your DTO has PropertyListener annotations accross all fields
    * otherwise dont use this function or fix your DTO
    */
    public static void unsetDTOState(BaseDTO baseDTO, boolean evaluatePropertyChangedEventMap) {
        final String METHODNAME = "resetDTOState ";
        boolean unsetDTOState = false;
        
        if (evaluatePropertyChangedEventMap) {
            int changedCounter = 0;
            Map<String, CorePropertyChangeEvent> propertyChangeEventMap = baseDTO.getPropertyChangeEventMap();
            for (Entry<String, CorePropertyChangeEvent> entry : propertyChangeEventMap.entrySet()) {
                boolean propertyChanged = isPropertyChanged(entry.getValue().getOldValue(), entry.getValue().getNewValue());
                logger.info(METHODNAME, "entry.getKey()=", entry.getKey(), 
                        " entry.getValue().getPropertyName()=", entry.getValue().getPropertyName(), 
                        " entry.getValue().getOldValue()=", entry.getValue().getOldValue(),
                        " entry.getValue().getOldValue()=", entry.getValue().getNewValue(),
                        " propertyChanged=", propertyChanged); 
                if (propertyChanged) {
                    changedCounter ++;
                    break;
                }
            }
            logger.info(METHODNAME, "changedCounter=", changedCounter); 
            if (changedCounter == 0) {
                // Nothing has changed at the DTO level
                unsetDTOState = true;
            }    
        }
        else {
            unsetDTOState = true;
        }
        
        if (unsetDTOState) {
            DTOUtils.unsetDTOState(baseDTO);
        }
        
    }
    
    /**
     * Determine if the property changed, used internally to determine if property change listener should be fired
     *
     * @param x original value.
     * @param y new value.
     * @return if changed.
     */
    public static boolean isPropertyChanged(Object x, Object y) {
        boolean propertyChanged = false;
        if (x != null && y != null) {
            if (!x.equals(y)) {
                propertyChanged = true;
            }
        } else if ((x == null && y != null) || (x != null && y == null)) {
            propertyChanged = true;
        }
        return propertyChanged;
    }    

    public static <A extends Annotation> void setAnnotatedFieldValue(BaseDTO instance, Class<A> annotationClass, Object value) {
        Field result = null;
        for (Field field : ClassUtils.getNonBaseDTODeclaredFields(instance.getClass())) {
            A annotation = field.getAnnotation(annotationClass);
            if (annotation != null) {
                result = field;
            }
        }
        if (result == null) {
            throw new IllegalStateException(annotationClass.getSimpleName()
                    + " annotates no properties of the class: "
                    + instance.getClass().getSimpleName());
        } else {
            result.setAccessible(true);
            try {
                result.set(instance, value);
            } catch (Exception e) {
                throw new IllegalStateException("An error occurred setting tha anonotated field: " + result.getName()
                        + " - msg: " + e.getMessage(), e);
            }
        }
    }
    private static final Map<Class, RowsReturnCountBehavior> dtoRowsReturnCountBehaviorValueMap = new HashMap<Class, RowsReturnCountBehavior>();

    public static RowsReturnCountBehavior getRowsReturnCountBehaviorValue(Class<? extends BaseDTO> dtoClass) {
        // Cache these - no need to compute every time...
        RowsReturnCountBehavior rowsReturnCountBehavior = dtoRowsReturnCountBehaviorValueMap.get(dtoClass);
        if (rowsReturnCountBehavior != null) {
//            logger.info("getRowsReturnCountBehaviorValue cache hit");
            return rowsReturnCountBehavior;
        }
        rowsReturnCountBehavior = dtoClass.getAnnotation(RowsReturnCountBehavior.class);

        // cache result
        dtoRowsReturnCountBehaviorValueMap.put(dtoClass, rowsReturnCountBehavior);

        return rowsReturnCountBehavior;
    }
    private static final Map<Class, Map<Class, ParentChildRelationship>> dtoParentChildRelationshipMapByQueryClass = new HashMap<Class, Map<Class, ParentChildRelationship>>();

    public static Map<Class, ParentChildRelationship> getParentChildRelationshipMapByQueryClass(Class<? extends BaseDTO> dtoClass) {

        // Cache these - no need to compute every time...
        Map<Class, ParentChildRelationship> dtoQueryMap = dtoParentChildRelationshipMapByQueryClass.get(dtoClass);
        if (dtoQueryMap != null) {
//            logger.info("getParentChildRelationshipMapByQueryClass cache hit");
            return dtoQueryMap;
        }

        dtoQueryMap = new HashMap<Class, ParentChildRelationship>();

        ParentChildRelationships parentChildRelationships = dtoClass.getAnnotation(ParentChildRelationships.class);
        if (parentChildRelationships != null) {
            for (ParentChildRelationship parentChildRelationship : parentChildRelationships.value()) {
                dtoQueryMap.put(parentChildRelationship.childQueryClass(), parentChildRelationship);
            }
        }

        // cache result
        dtoParentChildRelationshipMapByQueryClass.put(dtoClass, dtoQueryMap);

        return dtoQueryMap;
    }

    private static final Map<Class, ColumnSubstitutions> dtoColumnSubstitutionMap = new HashMap<Class, ColumnSubstitutions>();

    public static ColumnSubstitutions getColumnSubstitutions(Class<? extends BaseDTO> dtoClass) {

        // Cache these - no need to compute every time...
        ColumnSubstitutions columnSubstitutions = dtoColumnSubstitutionMap.get(dtoClass);
        if (columnSubstitutions == null) {
            columnSubstitutions = dtoClass.getAnnotation(ColumnSubstitutions.class);
            if (columnSubstitutions != null) {
                dtoColumnSubstitutionMap.put(dtoClass, columnSubstitutions);
            }
        }

        return columnSubstitutions;
    }

    private static final Map<Class, Map<Class<? extends BaseDTO>, ParentChildRelationship>> dtoParentChildRelationshipMapByDTO = new HashMap<Class, Map<Class<? extends BaseDTO>, ParentChildRelationship>>();

    public static Map<Class<? extends BaseDTO>, ParentChildRelationship> getParentChildRelationshipMapByDTO(Class<? extends BaseDTO> dtoClass) {

        // Cache these - no need to compute every time...
        Map<Class<? extends BaseDTO>, ParentChildRelationship> dtoQueryMap = dtoParentChildRelationshipMapByDTO.get(dtoClass);
        if (dtoQueryMap != null) {
//            logger.info("getParentChildRelationshipMapByDTO cache hit");
            return dtoQueryMap;
        }

        dtoQueryMap = new HashMap<Class<? extends BaseDTO>, ParentChildRelationship>();

        ParentChildRelationships parentChildRelationships = dtoClass.getAnnotation(ParentChildRelationships.class);
        if (parentChildRelationships != null) {
            for (ParentChildRelationship parentChildRelationship : parentChildRelationships.value()) {
                dtoQueryMap.put(parentChildRelationship.childDtoClass(), parentChildRelationship);
            }
        }

        // cache result
        dtoParentChildRelationshipMapByDTO.put(dtoClass, dtoQueryMap);

        return dtoQueryMap;
    }
    private static final Map<Class, Map<Class<? extends BaseDTO>, Class>> dtoDtoQueryMap = new HashMap<Class, Map<Class<? extends BaseDTO>, Class>>();

    public static Map<Class<? extends BaseDTO>, Class> getDtoQueryMap(Class<? extends BaseDTO> dtoClass) {

        // Cache these - no need to compute every time...
        Map<Class<? extends BaseDTO>, Class> dtoQueryMap = dtoDtoQueryMap.get(dtoClass);
        if (dtoQueryMap != null) {
            return dtoQueryMap;
        }

        dtoQueryMap = new HashMap<Class<? extends BaseDTO>, Class>();

        ParentChildRelationships parentChildRelationships = dtoClass.getAnnotation(ParentChildRelationships.class);
        if (parentChildRelationships != null) {
            for (ParentChildRelationship parentChildRelationship : parentChildRelationships.value()) {
                dtoQueryMap.put(parentChildRelationship.childDtoClass(), parentChildRelationship.childQueryClass());
            }
        }

        // cache result
        dtoDtoQueryMap.put(dtoClass, dtoQueryMap);

        return dtoQueryMap;
    }
    private static final Map<Class, Map<Class, Class>> dtoQueryClassFromDtoQueryMap = new HashMap<Class, Map<Class, Class>>();

    // TODO: cache this
    public static Class getQueryClassFromDtoQueryMap(Class<? extends BaseDTO> dtoClass, Class<? extends BaseDTO> childDtoClass) {

        // Cache these - no need to compute every time...
        Map<Class, Class> dtoQueryClassMap = dtoQueryClassFromDtoQueryMap.get(dtoClass);
        if (dtoQueryClassMap == null) {
            dtoQueryClassMap = new HashMap<Class, Class>();
            dtoQueryClassFromDtoQueryMap.put(dtoClass, dtoQueryClassMap);
        }

        Class result = dtoQueryClassMap.get(childDtoClass);
        if (result != null) {
            return result;
        }

        ParentChildRelationships parentChildRelationships = dtoClass.getAnnotation(ParentChildRelationships.class);
        if (parentChildRelationships != null) {
            for (ParentChildRelationship parentChildRelationship : parentChildRelationships.value()) {
                if (parentChildRelationship.childDtoClass() == childDtoClass) {
                    result = parentChildRelationship.childQueryClass();
                }
            }
        }

        // cache result
        dtoQueryClassMap.put(childDtoClass, result);

        return result;
    }

    // TODO: cache this?
    public static boolean isReadOnly(Class<? extends BaseDTO> dtoClass) {
        return dtoClass.isAnnotationPresent(ReadOnly.class);
    }
    private static final Map<Class, ParentBehavior> dtoParentBehaviorMap = new HashMap<Class, ParentBehavior>();

    public static ParentBehavior getParentBehavior(Class<? extends BaseDTO> dtoClass) {

        // Cache these - no need to compute every time...
        if (dtoParentBehaviorMap.containsKey(dtoClass)) {
            return dtoParentBehaviorMap.get(dtoClass);
        }

        ParentBehavior parentBehavior = dtoClass.getAnnotation(ParentBehavior.class);

        // cache result
        dtoParentBehaviorMap.put(dtoClass, parentBehavior);

        return parentBehavior;
    }

    public static DTOState getReferenceDTOState(Field referenceField, BaseDTO baseDTO) {
        DTOState dtoState = DTOState.UNSET;
        Class<? extends BaseDTO> referenceDtoClass = (Class<? extends BaseDTO>) referenceField.getType();
        ReferenceDTO referenceAnnotation = referenceField.getAnnotation(ReferenceDTO.class);
        if (referenceAnnotation.isUpdateable()
                && !isReadOnly(referenceDtoClass)) {
            referenceField.setAccessible(true);
            BaseDTO referenceDTO = null;
            try {
                referenceDTO = (BaseDTO) referenceField.get(baseDTO);
            } catch (IllegalAccessException e) {
                // shouldn't ever happen - do nothing...
            }
            if (referenceDTO != null) {
                dtoState = referenceDTO.getDTOState();
                // Check operational state in the event the Reference DTO has children that have changed.
                if (dtoState == DTOState.UNSET) {
                    dtoState = referenceDTO.getOperationDTOState();
                }

            }
        }
        return dtoState;
    }

    // TODO: cache this?
    public static boolean addsChild(Class<? extends BaseDTO> dtoClass) {
        if (isReadOnly(dtoClass)) {
            return false;
        }
        ParentBehavior parentBehavior = getParentBehavior(dtoClass);
        if (parentBehavior == null) {
            return CoreConstants.ADDS_CHILD_DEFAULT;
        } else {
            return parentBehavior.addsChild();
        }
    }

    // TODO: cache this?
    public static boolean deletesChild(Class<? extends BaseDTO> dtoClass) {
        if (isReadOnly(dtoClass)) {
            return false;
        }
        ParentBehavior parentBehavior = getParentBehavior(dtoClass);
        if (parentBehavior == null) {
            return CoreConstants.DELETES_CHILD_DEFAULT;
        } else {
            return parentBehavior.deletesChild();
        }
    }

    // TODO: cache this?
    public static boolean updatesChild(Class<? extends BaseDTO> dtoClass) {
        if (isReadOnly(dtoClass)) {
            return false;
        }
        ParentBehavior parentBehavior = getParentBehavior(dtoClass);
        if (parentBehavior == null) {
            return CoreConstants.UPDATES_CHILD_DEFAULT;
        } else {
            return parentBehavior.updatesChild();
        }
    }

    // TODO: cache this?
    public static boolean isDeleteAllowed(Class<? extends BaseDTO> dtoClass) {
        if (isReadOnly(dtoClass)) {
            return false;
        }
        ParentBehavior parentBehavior = getParentBehavior(dtoClass);
        if (parentBehavior == null) {
            return CoreConstants.DELETE_ALLOWED_DEFAULT;
        } else {
            return parentBehavior.deleteAllowed();
        }
    }

    // TODO: cache this
    public static boolean isAddAllowed(Class<? extends BaseDTO> dtoClass) {
        if (isReadOnly(dtoClass)) {
            return false;
        }
        ParentBehavior parentBehavior = getParentBehavior(dtoClass);
        if (parentBehavior == null) {
            return CoreConstants.ADD_ALLOWED_DEFAULT;
        } else {
            return parentBehavior.addAllowed();
        }
    }

    // TODO: cache this
    public static boolean isUpdateAllowed(Class<? extends BaseDTO> dtoClass) {
        if (isReadOnly(dtoClass)) {
            return false;
        }
        ParentBehavior parentBehavior = getParentBehavior(dtoClass);
        if (parentBehavior == null) {
            return CoreConstants.UPDATE_ALLOWED_DEFAULT;
        } else {
            return parentBehavior.updateAllowed();
        }
    }
    private final static Map<Class, Map<Class, Map<String, List<Field>>>> dtoForeignKeyFieldsMap = new HashMap<Class, Map<Class, Map<String, List<Field>>>>();

    /**
     * Returns the fields which have a GeneratedValue annotation with their source = FOREIGN_CONSTRAINT and the sourceClass = the
     * supplied class.
     *
     * @param dtoClass
     * @param foreignKeyClass the supplied class
     * @param fieldName the specific property to set when there is more than one property involved in a foreign key scenario
     * @return the Field instance for the foreign key requested otherwise null.
     */
    public static List<Field> getForeignKeyFields(Class<? extends BaseDTO> dtoClass, Class<? extends BaseDTO> foreignKeyClass, String fieldName) {

        // maintain cache of these - no need to compute every call...
        Map<Class, Map<String, List<Field>>> dtoForeignKeyClassMap = dtoForeignKeyFieldsMap.get(dtoClass);
        if (dtoForeignKeyClassMap == null) {
            dtoForeignKeyClassMap = new HashMap<Class, Map<String, List<Field>>>();
            dtoForeignKeyFieldsMap.put(dtoClass, dtoForeignKeyClassMap);
        }

        Map<String, List<Field>> foreignKeyFieldMap = dtoForeignKeyClassMap.get(foreignKeyClass);
        if (foreignKeyFieldMap == null) {
            foreignKeyFieldMap = new HashMap<String, List<Field>>();
            dtoForeignKeyClassMap.put(foreignKeyClass, foreignKeyFieldMap);
        }

        String key = fieldName == null ? "nullfield" : fieldName;
        List<Field> results = foreignKeyFieldMap.get(key);
        if (results != null) {
//            logger.info("getForeignKeyFields cache hit");
            return results;
        }

        results = new ArrayList<Field>();

        for (Field field : ClassUtils.getNonBaseDTODeclaredFields(dtoClass)) {
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            if (generatedValue != null) {
                if (generatedValue.source() == GenerationSource.FOREIGN_CONSTRAINT) {
                    for (Class klass : generatedValue.sourceClass()) {
                        if (klass == foreignKeyClass) {
                            if (fieldName == null) {
                                results.add(field);
                            } else if (fieldName.equalsIgnoreCase(generatedValue.fieldName())) {
                                results.add(field);
                            }
                        }
                    }
                }
            }
        }

        // cache results
        foreignKeyFieldMap.put(key, results);

        return results;
    }
    private final static Map<Class, List<Class<? extends BaseDTO>>> dtoForeignKeySourceClassesMap = new HashMap<Class, List<Class<? extends BaseDTO>>>();

    /**
     * Returns the source classes for the fields which have a GeneratedValue annotation with their source = FOREIGN_CONSTRAINT.
     *
     * @param dtoClass
     * @return the Field instance for the foreign key requested otherwise null.
     */
    public static List<Class<? extends BaseDTO>> getForeignKeySourceClasses(Class<? extends BaseDTO> dtoClass) {

        // maintain cache of these - no need to compute every call...
        List<Class<? extends BaseDTO>> results = dtoForeignKeySourceClassesMap.get(dtoClass);
        if (results != null) {
//            logger.info("getForeignKeySourceClasses cache hit");
            return results;
        }

        results = new ArrayList<Class<? extends BaseDTO>>();

        for (Field field : ClassUtils.getNonBaseDTODeclaredFields(dtoClass)) {
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            if (generatedValue != null) {
                if (generatedValue.source() == GenerationSource.FOREIGN_CONSTRAINT) {
                    for (Class klass : generatedValue.sourceClass()) {
                        if (BaseDTO.class.isAssignableFrom(klass)) {
                            results.add(klass);
                        }
                    }
                }
            }
        }

        // cache results
        dtoForeignKeySourceClassesMap.put(dtoClass, results);

        return results;
    }

    /**
     * Returns the field which has a GeneratedValue annotation with its source = FOREIGN_CONSTRAINT and the sourceClass = the
     * supplied class.
     *
     * @param dtoClass
     * @param foreignKeyClass the supplied class
     * @param fieldName the specific property to set when there is more than one property involved in a foreign key scenario
     * @return the Field instance for the foreign key requested otherwise null.
     * @throws IllegalStateException
     */
    public static Field getForeignKeyField(Class<? extends BaseDTO> dtoClass, Class<? extends BaseDTO> foreignKeyClass, String fieldName) {
        Field result = null;
        List<Field> results = getForeignKeyFields(dtoClass, foreignKeyClass, fieldName);

        if (results.size() == 1) {
            result = results.get(0);
        } else if (results.size() > 1) {
            throw new IllegalStateException("getForeignKeyField should not return more than one result. "
                    + "If there are multiple properties annotated with GeneratedValue they must have unique fieldNames.");
        }
        return result;
    }

    public static Field getForeignKeyField(Class<? extends BaseDTO> dtoClass, Class<? extends BaseDTO> foreignKeyClass) {
        Field foreignKeyField = getForeignKeyField(dtoClass, foreignKeyClass, null);
        return foreignKeyField;
    }
    private final static Map<Class, Map<Class, Map<String, List<GeneratedValue>>>> dtoPKGeneratedValuesMap = new HashMap<Class, Map<Class, Map<String, List<GeneratedValue>>>>();

    public static List<GeneratedValue> getGeneratedValues(Class<? extends BaseDTO> dtoClass, Class<? extends BaseDTO> foreignKeyClass, String fieldName) {
        final String METHODNAME = "getGeneratedValues ";
        final boolean debug = false;

        // maintain cache of these - no need to compute every call...
        Map<Class, Map<String, List<GeneratedValue>>> pkGeneratedValuesMap = dtoPKGeneratedValuesMap.get(dtoClass);
        if (pkGeneratedValuesMap == null) {
            pkGeneratedValuesMap = new HashMap<Class, Map<String, List<GeneratedValue>>>();
            dtoPKGeneratedValuesMap.put(dtoClass, pkGeneratedValuesMap);
        }

        Map<String, List<GeneratedValue>> fieldGeneratedValuesMap = pkGeneratedValuesMap.get(foreignKeyClass);
        if (fieldGeneratedValuesMap == null) {
            fieldGeneratedValuesMap = new HashMap<String, List<GeneratedValue>>();
            pkGeneratedValuesMap.put(foreignKeyClass, fieldGeneratedValuesMap);
        }

        String key = fieldName == null ? "nullfield" : fieldName;
        List<GeneratedValue> generatedValues = fieldGeneratedValuesMap.get(key);
        if (generatedValues != null) {
//            logger.info("getGeneratedValues cache hit");
            return generatedValues;
        }

        generatedValues = new ArrayList<GeneratedValue>();

        List<Field> foreignKeyFields = getForeignKeyFields(dtoClass, foreignKeyClass, fieldName);
        if (!foreignKeyFields.isEmpty()) {
            for (Field foreignKeyField : foreignKeyFields) {
                GeneratedValue generatedValue = foreignKeyField.getAnnotation(GeneratedValue.class);
                if (generatedValue != null) {
                    generatedValues.add(generatedValue);
                } else if (logger.isDebugEnabled() || debug) {
                    logger.info(METHODNAME, "generatedValue is null");
                }
            }
        } else if (logger.isDebugEnabled() || debug) {
            logger.info(METHODNAME, "foreignKeyFields is empty");
        }

        // cache results
        fieldGeneratedValuesMap.put(key, generatedValues);

        return generatedValues;
    }

    public static List<GeneratedValue> getGeneratedValues(Class<? extends BaseDTO> dtoClass, Class<? extends BaseDTO> foreignKeyClass) {
        return getGeneratedValues(dtoClass, foreignKeyClass, null);
    }

    public static boolean isAutoRetrieve(Class<? extends BaseDTO> dtoClass, Class<? extends BaseDTO> foreignKeyClass) {
        final String METHODNAME = "isAutoRetrieve ";
        boolean result = CoreConstants.AUTO_RETRIEVE_DEFAULT;
        //        if (!isNoId(dtoClass)) {
        //            try {
        //                List<GeneratedValue> pkGeneratedValues = getPKGeneratedValues(dtoClass);
        //                if (pkGeneratedValues != null && !pkGeneratedValues.isEmpty()) {
        //                    for (GeneratedValue generatedValue : pkGeneratedValues) {
        //                        if (generatedValue.isAutoRetrieve() != Constants.AUTO_RETRIEVE_DEFAULT) {
        //                            result = generatedValue.isAutoRetrieve();
        //                            break;
        //                        }
        //                    }
        //                }
        //            } catch (MtsException e) {
        //                logger.debug("error getting PK generated values: ", e.getMessage());
        //            }
        //        }
        ParentChildRelationship parentChildRelationship = getParentChildRelationshipMapByDTO(foreignKeyClass).get(dtoClass);
        if (parentChildRelationship != null) {
            result = parentChildRelationship.isAutoRetrieve();
        }
//
//        List<GeneratedValue> generatedValues = getGeneratedValues(dtoClass, foreignKeyClass);
//        if (generatedValues != null && !generatedValues.isEmpty()) {
//            for (GeneratedValue generatedValue : generatedValues) {
//                if (generatedValue.isAutoRetrieve() != result) {
//                    result = generatedValue.isAutoRetrieve();
//                    break;
//                }
//            }
//        }
        return result;
    }

    public static boolean isChildNotFoundAllowed(Class<? extends BaseDTO> dtoClass, Class<? extends BaseDTO> foreignKeyClass) {
        final String METHODNAME = "isChildNotFoundAllowed ";
        boolean result = CoreConstants.NOT_FOUND_ALLOWED_DEFAULT;
//        if (!isNoId(dtoClass)) {
//            try {
//                List<GeneratedValue> pkGeneratedValues = getPKGeneratedValues(dtoClass);
//                if (pkGeneratedValues != null && !pkGeneratedValues.isEmpty()) {
//                    for (GeneratedValue generatedValue : pkGeneratedValues) {
//                        if (generatedValue.isAutoRetrieve() != Constants.AUTO_RETRIEVE_DEFAULT) {
//                            result = generatedValue.isAutoRetrieve();
//                            break;
//                        }
//                    }
//                }
//            } catch (MtsException e) {
//                logger.debug("error getting PK generated values: ", e.getMessage());
//            }
//        }
        ParentChildRelationship parentChildRelationship = getParentChildRelationshipMapByDTO(foreignKeyClass).get(dtoClass);
        if (parentChildRelationship != null) {
            result = parentChildRelationship.isChildNotFoundAllowed();
        }
//        List<GeneratedValue> generatedValues = getGeneratedValues(dtoClass, foreignKeyClass);
//        if (!generatedValues.isEmpty()) {
//            for (GeneratedValue generatedValue : generatedValues) {
//                if (generatedValue.isChildNotFoundAllowed() != result) {
//                    result = generatedValue.isChildNotFoundAllowed();
//                    break;
//                }
//            }
//        }
        return result;
    }

    public static boolean isAudit(Class<? extends BaseDTO> dtoClass) {
        return dtoClass.isAnnotationPresent(Audit.class);
    }

    // TODO: cache this?
    /**
     * Returns the the NoId dtoState. If this DTO doesn't have a primary key this mechanism lets the developer explicitly express
     * that that dtoState.
     *
     * @param dtoClass
     * @return the dtoState of the presence of the NoId annotation.
     */
    public static boolean isNoId(Class<? extends BaseDTO> dtoClass) {
        return dtoClass.isAnnotationPresent(NoId.class);
    }
    
    public static boolean isNoDAO(Class<? extends BaseDTO> dtoClass) {
        return dtoClass.isAnnotationPresent(NoDAO.class);
    }
    
    
    private final static Map<Class, List<Field>> dtoPrimaryKeyMap = new HashMap<Class, List<Field>>();

    /**
     * Returns the primary key Field(s).
     *
     * @param dtoClass
     * @return
     * @returns the primary key Field(s).
     * @throws IllegalStateException
     */
    public static List<Field> getPrimaryKeyFields(Class<? extends BaseDTO> dtoClass) {
        final String METHODNAME = "getPrimaryKeyFields ";

        // maintain cache of these - no need to compute every call...
        List<Field> pkFields = dtoPrimaryKeyMap.get(dtoClass);
        if (pkFields != null) {
//            logger.info("getPrimaryKeyFields cache hit");
            return pkFields;
        }

        pkFields = new ArrayList<Field>();

        for (Field field : ClassUtils.getNonBaseDTODeclaredFields(dtoClass)) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                field.setAccessible(true);
                pkFields.add(field);
            }
        }

        if (pkFields.isEmpty() && !isNoId(dtoClass)) {
            throw new IllegalStateException("pkFields.isEmpty() && !isNoId()",
                    new AnnotationException("There is no Id or NoId annotation set. "
                            + "Annotate the appropriate field(s) in the DTO with Id or NoId annotation: " + dtoClass.getSimpleName(),
                            Id.class));
        }
        Collections.sort(pkFields, new IdFieldComparator());

        // cache result
        dtoPrimaryKeyMap.put(dtoClass, pkFields);

        return pkFields;
    }

    /**
     * Returns the Primary Key values.
     *
     * @param baseDTO
     * @return
     */
    public static List<Object> getPrimaryKeys(BaseDTO baseDTO) {
        List<Object> primaryKeys = null;
        if (baseDTO != null) {
            primaryKeys = new ArrayList<Object>();
            Object primaryKey = baseDTO.getPrimaryKey();
            if (primaryKey instanceof Map) {
                Map<String, Object> primaryKeyMap = (Map<String, Object>) primaryKey;
                List<Field> primaryKeyFields = baseDTO.getPrimaryKeyFields();
                for (Field field : primaryKeyFields) {
                    Object value = primaryKeyMap.get(field.getName());
                    System.out.println("value=" + value);
                    if (value instanceof BaseDTO) {
                        primaryKeys.addAll(getPrimaryKeys((BaseDTO) value));
                    } else {
                        primaryKeys.add(value);
                    }
                }
            } else if (primaryKey instanceof BaseDTO) {
                primaryKeys.addAll(getPrimaryKeys((BaseDTO) primaryKey));
            } else {
                primaryKeys.add(primaryKey);
            }
        }

        return primaryKeys;
    }

    private final static Map<Class, List<GeneratedValue>> dtoPKFullGeneratedValuesMap = new HashMap<Class, List<GeneratedValue>>();

    /**
     * Returns the generated values of the primary key.
     *
     * @param dtoClass
     * @return the generated value of the primary key.
     */
    public static List<GeneratedValue> getPKGeneratedValues(Class<? extends BaseDTO> dtoClass) {

        // maintain cache of these - no need to compute every call...
        List<GeneratedValue> generatedValues = dtoPKFullGeneratedValuesMap.get(dtoClass);
        if (generatedValues != null) {
//            logger.info("getPKGeneratedValues cache hit");
            return generatedValues;
        }

        generatedValues = new ArrayList<GeneratedValue>();

        for (Field field : getPrimaryKeyFields(dtoClass)) {
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            if (generatedValue != null && !generatedValues.contains(generatedValue)) {
                generatedValues.add(generatedValue);
            }
        }

        // cache results
        dtoPKFullGeneratedValuesMap.put(dtoClass, generatedValues);

        return generatedValues;
    }

    /**
     * Returns the generated value of a single primary key scenario.
     *
     * @param dtoClass
     * @return the generated value of the primary key.
     */
    public static GeneratedValue getPKGeneratedValue(Class<? extends BaseDTO> dtoClass) {
        List<GeneratedValue> generatedValues = getPKGeneratedValues(dtoClass);
        if (generatedValues.isEmpty()) {
            throw new IllegalStateException("generatedValues.isEmpty()",
                    new AnnotationException("There is no GeneratedValue annotations configured on the DTOs Id annotated field. "
                            + "Annotate the appropriate field in the DTO with GeneratedValue: "
                            + dtoClass.getSimpleName(), GeneratedValue.class));
        }
        if (generatedValues.size() > 1) {
            throw new IllegalStateException("generatedValues.size() > 1",
                    new AnnotationException("There are more than one GeneratedValue annotations configured on the DTOs Id fields. "));
        }
        return generatedValues.get(0);
    }

    /**
     * Returns the SEQUENCE name of the primary key for this DTO.
     *
     * @param dtoClass
     * @return the SEQUENCE name of the primary key for this DTO.
     */
    public static String getPKGeneratedSourceSequence(Class<? extends BaseDTO> dtoClass) {
        return getPKGeneratedValue(dtoClass).dataSource();
    }

    // TODO: cache this
    /**
     * Returns the derived AUTO dtoState of the primary key for this DTO. If it is AUTO then the validation of the add operation
     * won't fail based on a null value in the primary key. Also, provides a means to auto-generate the primary key on an add
     * operation the type of which can be determined from getPrimaryKeyClass.
     *
     * @param dtoClass
     * @return the derived dtoState of the primary key for this DTO. Application handles AUTO primary key population and relies on
     * an external source otherwise.
     * @throws IllegalStateException
     * @see #getPKGeneratedValue|()
     * @see getSinglePrimaryKeyField()
     */
    public static boolean isPKGeneratedSourceAuto(Class<? extends BaseDTO> dtoClass) {
        boolean containsAuto = false;
        List<GeneratedValue> pKGeneratedValues = getPKGeneratedValues(dtoClass);
        for (GeneratedValue generatedValue : pKGeneratedValues) {
            if (generatedValue.source() == GenerationSource.AUTO) {
                containsAuto = true;
                break;
            }
        }
        return containsAuto;
    }

    // TODO: cache this?
    /**
     * Returns the derived SEQUENCE dtoState of the primary key for this DTO. If it is SEQUENCE then the validation of the add
     * operation won't fail based on a null value in the primary key. Also, provides a means to set the primary key on an add
     * operation the type of which can be determined from getPrimaryKeyClass.
     *
     * @param dtoClass
     * @return the derived SEQUENCE dtoState of the primary key for this DTO. Application handles SEQUENCE primary key population
     * and relies on an external source otherwise.
     * @throws IllegalStateException
     */
    public static boolean isPKGeneratedSourceSequence(Class<? extends BaseDTO> dtoClass) {
        boolean containsSequence = false;
        List<GeneratedValue> pKGeneratedValues = getPKGeneratedValues(dtoClass);
        for (GeneratedValue generatedValue : pKGeneratedValues) {
            if (generatedValue.source() == GenerationSource.SEQUENCE) {
                containsSequence = true;
                break;
            }
        }
        return containsSequence;
    }
    private final static Map<Class, List<Field>> dtoPKGeneratedSourceSequenceFieldsMap = new HashMap<Class, List<Field>>();

    public static List<Field> getPKGeneratedSourceSequenceFields(Class<? extends BaseDTO> dtoClass) {

        // maintain cache of these - no need to compute every call...
        List<Field> sourceSequenceFields = dtoPKGeneratedSourceSequenceFieldsMap.get(dtoClass);
        if (sourceSequenceFields != null) {
//            logger.info("getPKGeneratedSourceSequenceFields cache hit");
            return sourceSequenceFields;
        }

        sourceSequenceFields = new ArrayList<Field>();

        for (Field field : getPrimaryKeyFields(dtoClass)) {
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            if (generatedValue.source() == GenerationSource.SEQUENCE) {
                sourceSequenceFields.add(field);
            }
        }

        // cache results
        dtoPKGeneratedSourceSequenceFieldsMap.put(dtoClass, sourceSequenceFields);

        return sourceSequenceFields;
    }

    // cache this?
    public static boolean isEntity(Class<? extends BaseDTO> dtoClass) {
        if (dtoClass == null) {
            logger.warn("isEntity - dtoClass is null!");
            return false;
        }
        return dtoClass.isAnnotationPresent(Entity.class);
    }

    // cache this?
    public static boolean isRefreshOnAddOrUpdate(Class<? extends BaseDTO> dtoClass) {
        return dtoClass.isAnnotationPresent(RefreshOnAddOrUpdate.class);
    }
    private final static Map<Class, Boolean> dtoReferenceDTOsExistMap = new HashMap<Class, Boolean>();

    /**
     * Returns whether or not the DTO has properties annotated with ReferenceDTO.
     *
     * @param dtoClass
     * @return the presence of ReferenceDTO annotations.
     */
    public static boolean isReferenceDTOsExist(Class<? extends BaseDTO> dtoClass) {

        // maintain cache of these - no need to compute every call...
        Boolean referenceDTOsExist = dtoReferenceDTOsExistMap.get(dtoClass);
        if (referenceDTOsExist != null) {
//            logger.info("isReferenceDTOsExist cache hit");
            return referenceDTOsExist;
        }

        referenceDTOsExist = !getReferenceDTOs(dtoClass).isEmpty();

        // cache result
        dtoReferenceDTOsExistMap.put(dtoClass, referenceDTOsExist);

        return referenceDTOsExist;
    }

    private final static Map<Class, List<Field>> dtoReferenceDTOMap = new HashMap<Class, List<Field>>();

    /**
     * Returns whether or not the DTO has properties annotated with ReferenceDTO.
     *
     * @param dtoClass
     * @return the presence of ReferenceDTO annotations.
     */
    public static List<Field> getReferenceDTOs(Class<? extends BaseDTO> dtoClass) {

        List<Field> fields = dtoReferenceDTOMap.get(dtoClass);
        if (fields != null) {
//            logger.info("getReferenceDTOs cache hit");
            return fields;
        }

        fields = new ArrayList<Field>();

        for (Field field : ClassUtils.getNonBaseDTODeclaredFields(dtoClass)) {
            ReferenceDTO referenceDTO = field.getAnnotation(ReferenceDTO.class);
            if (referenceDTO != null) {
                field.setAccessible(true);
                fields.add(field);
            }
        }

        // cache results
        dtoReferenceDTOMap.put(dtoClass, fields);

        return fields;
    }

    private final static Map<Class, List<Class>> dtoPrimaryKeyClassesMap = new HashMap<Class, List<Class>>();

    /**
     * The primary key Classes of the DTO instance.
     *
     * @param dtoClass
     * @return the primary key Classes of the DTO instance via reflection determined by which fields have the Id annotation.
     * @throws IllegalStateException
     */
    public static List<Class> getPrimaryKeyClasses(Class<? extends BaseDTO> dtoClass) {

        // maintain cache of these - no need to compute every call...
        List<Class> classes = dtoPrimaryKeyClassesMap.get(dtoClass);
        if (classes != null) {
            return classes;
        }

        classes = new ArrayList<Class>();
        for (Field field : getPrimaryKeyFields(dtoClass)) {
            classes.add(field.getType());
        }

        // cache result
        dtoPrimaryKeyClassesMap.put(dtoClass, classes);

        return classes;
    }
    private static final Map<Class, Map<String, Method>> primaryKeySetterMethodMap = new HashMap<Class, Map<String, Method>>();

    /**
     * Set the primary key of the DTO instance. Supports the following primary key types: - Long/long - Integer/int - String -
     * Map<String,Object>: value must be Long/long/Integer/int/String
     *
     * @param instance
     * @param key
     * @throws IllegalStateException error occurred setting the primary key field.
     * @see #getSinglePrimaryKeyField()
     */
    public static void setPrimaryKey(BaseDTO instance, Object key) {
        if (key != null) {
            Map<String, Method> setterMap = primaryKeySetterMethodMap.get(instance.getClass());
            if (setterMap == null) {
                setterMap = new HashMap<String, Method>();
                primaryKeySetterMethodMap.put(instance.getClass(), setterMap);
            }
            Class keyClass = key.getClass();
            if (key instanceof Map) {
                Map<String, Object> primaryKeyMap = (Map<String, Object>) key;
                List<Field> thisFields = getPrimaryKeyFields(instance.getClass());
                if (thisFields.size() != primaryKeyMap.size()) {
                    throw new IllegalStateException("PK field size mismatch: " + thisFields.size() + " - " + primaryKeyMap.size());
                }
                for (Field field : thisFields) {
                    Method setterMethod = setterMap.get(field.getName());
                    if (setterMethod == null) {
                        try {
                            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), instance.getClass());
                            setterMethod = propertyDescriptor.getWriteMethod();
                            setterMap.put(field.getName(), setterMethod);
                        } catch (Exception e) {
                            throw new IllegalStateException("An error occurred getting setter method for: " + field.getName() + " - msg: " + e.getMessage(), e);
                        }
                    }
                    boolean fieldSet = false;
                    String fieldName = field.getName();
                    GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
                    if (generatedValue != null) {
                        fieldName = StringUtils.isEmpty(generatedValue.fieldName()) ? fieldName : generatedValue.fieldName(); 
                    }
                    for (String keyFieldName : primaryKeyMap.keySet()) {
                        if (keyFieldName.equals(fieldName)) {
                            if (primaryKeyMap.get(keyFieldName).getClass() == field.getType()) {
                                fieldSet = true;
                                try {
                                    setterMethod.setAccessible(true);
                                    setterMethod.invoke(instance, primaryKeyMap.get(keyFieldName));
                                } catch (Exception e) {
                                    throw new IllegalStateException("An error occurred setting: " + field.getName() + " - msg: " + e.getMessage(), e);
                                }
                            } else {
                                throw new IllegalStateException("PK error: primary field names match but classes don't: "
                                        + primaryKeyMap.get(keyFieldName).getClass() + " - " + field.getType());
                            }
                        }
                    }
                    if (!fieldSet) {
                        throw new IllegalStateException("A field value was not found for the field in the submitted DTO: " + field.getName());
                    }
                }
            } else if (keyClass == Long.class || keyClass == long.class
                    || keyClass == Integer.class || keyClass == int.class
                    || keyClass == String.class) {
                List<Field> primaryKeyFields = getPrimaryKeyFields(instance.getClass());
                if (primaryKeyFields.size() == 1) {
                    Field pkField = primaryKeyFields.get(0);
                    Method setterMethod = setterMap.get(pkField.getName());
                    if (setterMethod == null) {
                        try {
                            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(pkField.getName(), instance.getClass());
                            setterMethod = propertyDescriptor.getWriteMethod();
                            setterMap.put(pkField.getName(), setterMethod);
                        } catch (Exception e) {
                            throw new IllegalStateException("An error occurred getting setter method for: " + pkField.getName() + " - msg: " + e.getMessage(), e);
                        }
                    }
                    try {
                        setterMethod.invoke(instance, key);
                    } catch (Exception e) {
                        throw new IllegalStateException("An error occurred setting: " + pkField.getName() + " - msg: " + e.getMessage(), e);
                    }
                } else {
                    throw new IllegalStateException("Primary key list size mismatch: " + primaryKeyFields.size());
                }
            } else {
                throw new IllegalStateException("Primary key class not supported: ");
            }
        } else {
            throw new IllegalStateException("Submitted key value was null.");
        }
    }

    /**
     * Returns the primary key of the DTO instance.
     *
     * @param instance
     * @return the primary key of the DTO instance via reflection determined by which field(s) have the PrimaryKey annotation.
     * @throws IllegalStateException invocation error on field value retrieval or primary key class not supported.
     * @see #getSinglePrimaryKeyField()
     */
    public static Object getPrimaryKey(BaseDTO instance) {
        Object primaryKey = null;
        Field pkField = null;
        try {
            List<Field> fields = getPrimaryKeyFields(instance.getClass());
            if (fields.size() == 1) {
                pkField = fields.get(0);
                Class<?> pkType = pkField.getType();
                primaryKey = pkField.get(instance);
                if (pkType == Long.class || pkType == long.class) {
                    if (primaryKey != null) {
                        primaryKey = (Long) primaryKey;
                    }
                } else if (pkType == Integer.class || pkType == int.class) {
                    if (primaryKey != null) {
                        primaryKey = (Integer) primaryKey;
                    }
                } else if (pkType == String.class) {
                    if (primaryKey == null) {
                        primaryKey = new String();
                    } else {
                        primaryKey = "" + (String) primaryKey;
                    }
                } else if (primaryKey == null) {
                    primaryKey = pkType.newInstance();
                } // throw new MtsException("Primary key class not supported: " + pkType.getSimpleName());
            } else if (fields.size() > 1) {
                Map<String, Object> pkMap = new HashMap<String, Object>();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    Object value = field.get(instance);
                    Class<?> pkType = field.getType();
                    if (pkType == Long.class || pkType == long.class
                            || pkType == Integer.class || pkType == int.class
                            || pkType == String.class
                            || pkType.getSuperclass() == BaseDTO.class
                            || pkType.getSuperclass().getSuperclass() == BaseDTO.class
                            || pkType.getSuperclass() == Enum.class) {
                        pkMap.put(fieldName, value);
                    } else {
                        throw new IllegalStateException("Primary key class not supported: " + pkType.getSimpleName());
                    }
                }
                primaryKey = pkMap;
            } else if (fields.isEmpty() && !isNoId(instance.getClass())) {
                //zero or negative condition...
                throw new IllegalStateException("No primary Key found...");
            }
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            String msg;
            if (pkField != null) {
                msg = "An error occurred invoking: " + pkField.getName() + " - msg: " + e.getMessage() + " - type: " + pkField.getType().getSimpleName();
            } else {
                msg = e.getMessage();
            }
            throw new IllegalStateException(msg, e);
        }
        return primaryKey;
    }

    /**
     * Sets the primary key of a DTO to an internally generated value if the GenerationSource is AUTO.
     *
     * @param instance
     * @return true if key was automatically set.
     * @throws IllegalStateException if the primary key was already set or the key class isn't supported.
     * @see isPKGeneratedSourceAuto()
     */
    public static boolean autoSetPrimaryKeys(BaseDTO instance) {
        boolean wasIdSet = false;
        // Dont have primary key?
        if (!hasPrimaryKey(instance)) {
            if (isPKGeneratedSourceAuto(instance.getClass())) {
                List<Field> primaryKeyFields = getPrimaryKeyFields(instance.getClass());
                for (Field field : primaryKeyFields) {
                    GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
                    if (generatedValue != null) {
                        if (generatedValue.source() == GenerationSource.AUTO) {
                            Class primaryKeyClass = field.getType();
                            Object key = null;
                            if (primaryKeyClass == Long.class || primaryKeyClass == long.class) {
                                key = NumberUtils.getRandomLong();
                                wasIdSet = true;
                            } else if (primaryKeyClass == Integer.class || primaryKeyClass == int.class) {
                                key = NumberUtils.getRandomInteger();
                                wasIdSet = true;
                            } else if (primaryKeyClass == String.class) {
                                key = StringUtils.getHashId();
                                wasIdSet = true;
                            } else {
                                throw new IllegalStateException("Primary key class not supported: " + primaryKeyClass.getSimpleName());
                            }
                            if (wasIdSet) {
                                try {
                                    field.set(instance, key);
                                } catch (IllegalAccessException e) {
                                    logger.error(e);
                                    throw new IllegalStateException(e.getMessage());
                                }
                            }
                        }
                    }
                }
            } else {
                throw new IllegalStateException("isPKGeneratedSourceAuto is false");
            }
        } else {
            throw new IllegalStateException("primary key is already set! " + instance.getPrimaryKey().toString());
        }
        return wasIdSet;
    }

    public static boolean hasPrimaryKey(BaseDTO instance) {
        return hasPrimaryKey(instance, false);
    }

    public static boolean hasPrimaryKey(BaseDTO instance, boolean acceptZeroAsValidKey) {
        boolean hasKey = false;
        Object primaryKey = getPrimaryKey(instance);
        if (primaryKey instanceof Map) {
            boolean valueMissing = false;
            for (Object value : ((Map<String, Object>) primaryKey).values()) {
                if (value == null) {
                    valueMissing = true;
                    continue;
                }
                String tmpValue = value.toString().trim();
                if (tmpValue.isEmpty() || "null".equals(tmpValue)) {
                    valueMissing = true;
                    continue;
                }
                // Handle case where zero is not acceptable
                if (!acceptZeroAsValidKey && "0".equals(tmpValue)) {
                    valueMissing = true;
                }
            }
            hasKey = !valueMissing;
        } else if (primaryKey != null) {
            String value = primaryKey.toString().trim();
            if (acceptZeroAsValidKey) {
                if (!value.isEmpty() && !value.equals("null")) {
                    hasKey = true;
                }
            } else if (!value.equals("0") && !value.isEmpty() && !value.equals("null")) {
                hasKey = true;
            }
        }
        return hasKey;
    }

    private static final Map<Class, Map<Class, Map<String, Method>>> foreignKeySetterMethodMap = new HashMap<Class, Map<Class, Map<String, Method>>>();

    /**
     * Sets a foreign key on the DTO.
     *
     * @param instance
     * @param foreignClass the DTO class of the foreign constraint.
     * @param key the foreign key value.
     * @param fieldName the specific property to set when there is more than one property involved in a foreign key scenario
     * @throws IllegalStateException on access or invocation errors
     */
    public static void setForeignKey(BaseDTO instance, Class<? extends BaseDTO> foreignClass, Object key, String fieldName) {
        final String METHODNAME = "setForeignKey ";
        Map<Class, Map<String, Method>> foreignKeyClassMethodMap = foreignKeySetterMethodMap.get(instance.getClass());
        if (foreignKeyClassMethodMap == null) {
            foreignKeyClassMethodMap = new HashMap<Class, Map<String, Method>>();
            foreignKeySetterMethodMap.put(instance.getClass(), foreignKeyClassMethodMap);
        }
        Map<String, Method> setterMethodMap = foreignKeyClassMethodMap.get(foreignClass);
        if (setterMethodMap == null) {
            setterMethodMap = new HashMap<String, Method>();
            foreignKeyClassMethodMap.put(foreignClass, setterMethodMap);
        }
        Method setterMethod = setterMethodMap.get(fieldName);
        if (setterMethod == null) {
            Field fkField = getForeignKeyField(instance.getClass(), foreignClass, fieldName);
            if (fkField != null) {
                try {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fkField.getName(), instance.getClass());
                    setterMethod = propertyDescriptor.getWriteMethod();
                    setterMethodMap.put(fieldName, setterMethod);
                } catch (Exception e) {
                    throw new IllegalStateException("An error occurred setting: " + fkField.getName() + " - msg: " + e.getMessage(), e);
                }
            } else {
                throw new IllegalStateException(""
                        + "fkField is null - There is no field annotated with GeneratedValue "
                        + "corresponding to the supplied foreign key class: " + foreignClass.getSimpleName()
                        + " - instance type: " + instance.getClass().getCanonicalName() + " - field value: "
                        + key);
            }
        }
        if (setterMethod != null) {
            try {
                Class<?>[] parameterTypes = setterMethod.getParameterTypes();
                if (BaseDTO.class.isAssignableFrom(parameterTypes[0])) {
                    BaseDTO newInstance = (BaseDTO) parameterTypes[0].newInstance();
                    logger.debug(METHODNAME, "created new instance: ", newInstance);
                    logger.debug(METHODNAME, "setting key on dto: ", key);
                    logger.debug(METHODNAME, "setting key type: ", key.getClass());
                    newInstance.setPrimaryKey(key);
                    key = newInstance;
                }
                setterMethod.setAccessible(true);
                setterMethod.invoke(instance, key);
            } catch (Exception e) {
                throw new IllegalStateException("An error occurred setting: " + foreignClass
                        + " - msg: " + e.getMessage()
                        + "; method: " + setterMethod.getName()
                        + "; instance type: " + instance.getClass()
                        + "; key: " + key, e);
            }
        } else {
            throw new IllegalStateException(""
                    + "setterMethod is null - foreign key class: " + foreignClass.getSimpleName()
                    + " - instance type: " + instance.getClass().getCanonicalName() + " - field value: "
                    + key);
        }
    }

    /**
     * Sets a foreign key on the DTO.
     *
     * @param instance
     * @param foreignClass the DTO class of the foreign constraint.
     * @param key the foreign key value.
     * @throws IllegalStateException on access or invocation errors
     */
    public static void setForeignKey(BaseDTO instance, Class<? extends BaseDTO> foreignClass, Object key) {
        final String METHODNAME = "setForeignKey ";
        if (logger.isDebugEnabled()) {
            logger.info(METHODNAME, "instanceClass: ", instance.getClass().getCanonicalName());
            logger.info(METHODNAME, "foreignClass: ", foreignClass.getCanonicalName());
            logger.info(METHODNAME, "key: ", key);
        }
        if (key instanceof Map) {
            logger.debug("Processing map");
            Map<String, Object> fKeys = (Map<String, Object>) key;
            for (Entry<String, Object> entry : fKeys.entrySet()) {
                logger.debug("Entry: ", entry.getKey(), " - ", entry.getValue());
                setForeignKey(instance, foreignClass, entry.getValue(), entry.getKey());
            }
        } else {
            setForeignKey(instance, foreignClass, key, null);
        }
    }

    /**
     * Returns the set foreign key on the DTO.
     *
     * @param instance
     * @param foreignClass the DTO class of the foreign constraint.
     * @param fieldName the specific property to set when there is more than one property involved in a foreign key scenario
     * @return
     * @throws IllegalStateException on access or invocation errors
     */
    public static Object getForeignKey(BaseDTO instance, Class<? extends BaseDTO> foreignClass, String fieldName) {
        Field fkField = getForeignKeyField(instance.getClass(), foreignClass, fieldName);
        fkField.setAccessible(true);
        try {
            return fkField.get(instance);
        } catch (Exception e) {
            throw new IllegalStateException("An error occurred invoking: " + fkField.getName() + " - msg: " + e.getMessage(), e);
        }
    }

    /**
     * Returns the set foreign key on the DTO.
     *
     * @param instance
     * @param foreignClass the DTO class of the foreign constraint.
     * @return
     * @throws IllegalStateException on access or invocation errors
     */
    public static Object getForeignKey(BaseDTO instance, Class<? extends BaseDTO> foreignClass) {
        return getForeignKey(instance, foreignClass, null);
    }

    public static String getDtoMgrReference(Class<? extends BaseDTO> dtoClass) {
        String result;
        String className = dtoClass.getSimpleName();
        int pos = className.toLowerCase().indexOf("dto");
        if (pos > 0) {
            result = className.substring(0, pos) + "MGR";
        } else {
            result = className + "MGR";
        }
        return result;
    }

    // TODO: cache this
    public static String getDtoDaoReference(Class<? extends BaseDTO> dtoClass) {
        String result;
        String className = dtoClass.getSimpleName();
        int pos = className.toLowerCase().indexOf("dto");
        if (pos > 0) {
            result = className.substring(0, pos) + "DAO";
        } else {
            result = className + "DAO";
        }
        return result;
    }

    // TODO: cache this
    public static String getDtoBoReference(Class<? extends BaseDTO> dtoClass) {
        String result;
        String className = dtoClass.getSimpleName();
        int pos = className.toLowerCase().indexOf("dto");
        if (pos > 0) {
            result = className.substring(0, pos) + "BO";
        } else {
            result = className + "BO";
        }
        return result;
    }

    public static boolean dtoEquals(Object sourceObj, Object targetObj) {
        if (sourceObj == null || targetObj == null) {
            return false;
        }
        if (sourceObj.getClass() != targetObj.getClass()) {
            return false;
        }
        try {
            final BaseDTO sourceDTO = (BaseDTO) sourceObj;
            final BaseDTO targetDTO = (BaseDTO) targetObj;
            if (sourceDTO.isNew() && targetDTO.isNew()) {
                return sourceDTO.getUuid().equals(targetDTO.getUuid());
            }
            if (sourceDTO.getPrimaryKey() == null || targetDTO.getPrimaryKey() == null) {
                return false;
            }
            if (!sourceDTO.getPrimaryKey().equals(targetDTO.getPrimaryKey())) {
                return false;
            }
        } catch (IllegalStateException e) {
            logger.error("An IllegalStateException has occurred; Message:" + e.getMessage(), e);
            throw e;
        }
        return true;
    }

    public static int dtoHashCode(BaseDTO baseDTO) {
        int hash = 7;
        try {
            hash = 37 * hash + (baseDTO.getPrimaryKey() != null ? baseDTO.getPrimaryKey().hashCode() : 0);
        } catch (IllegalStateException e) {
            logger.error("An MtsException has occurred; Message:" + e.getMessage(), e);
            throw e;
        }
        return hash;
    }

    // TODO: cache this
    public static boolean isVanity(Class<? extends BaseDTO> dtoClass, Class<? extends BaseDTO> foreignKeyClass) {
        final String METHODNAME = "isVanity ";
        boolean result = CoreConstants.VANITY_DEFAULT;
        ParentChildRelationship parentChildRelationship = getParentChildRelationshipMapByDTO(foreignKeyClass).get(dtoClass);
        if (parentChildRelationship != null) {
            result = parentChildRelationship.isVanity();
        }
        return result;
    }

    private static SortColumn getSortColumnFromSortColumns(SortColumns columns, DatabaseType databaseType) {
        final String METHODNAME = "getSortColumnFromSortColumns ";
        SortColumn column = null;
        if (columns != null && columns.value() != null && columns.value().length > 0) {
            if (columns.value().length == 1) {
                column = columns.value()[0];
            } else {
                for (SortColumn item : columns.value()) {
                    if (DatabaseType.ANY == item.databaseType()) {
                        column = item;
                    } else if (databaseType == item.databaseType()) {
                        logger.info(METHODNAME, "found database specific Column annotation.");
                        column = item;
                        break;
                    }
                }
            }
        }
        return column;
    }

    public static String getColumnName(String fieldName) {
        //return fieldName.replaceAll("([^_A-Z])([A-Z])", "$1_$2").toLowerCase();
        return StringUtils.unCamelize(fieldName, "_").toLowerCase();
    }

    public static Map<String, String> getSortFieldOrderByMap(Class<? extends BaseDTO> dtoClass, DatabaseType databaseType) {
        final String METHODNAME = "getSortFieldOrderByMap ";
        Map<String, String> sortFieldOrderByMap = new HashMap<String, String>();
        sortFieldOrderByMap.put("lastModId", " order by last_mod_id ");
        sortFieldOrderByMap.put("createId", " order by create_id ");
        sortFieldOrderByMap.put("lastModDatetime", " order by last_mod_datetime ");
        sortFieldOrderByMap.put("createDatetime", " order by create_datetime ");
        for (Field field : ClassUtils.getNonBaseDTODeclaredFields(dtoClass)) {
            String sortFieldKey;
            String sortFieldValue;
            SortColumn column = getSortColumnFromSortColumns(field.getAnnotation(SortColumns.class), databaseType);
            if (column == null) {
                column = field.getAnnotation(SortColumn.class);
            }
            if (column != null) {
                sortFieldKey = column.sortFieldKey();
                if ("".equals(sortFieldKey)) {
                    sortFieldKey = field.getName();
                }
                sortFieldValue = column.sortFieldValue();
            } else {
                sortFieldKey = field.getName();
                sortFieldValue = getColumnName(sortFieldKey);
                Table table = DTOUtils.getDtoTable(dtoClass);
                String tableAlias = table.alias();
                // Add the alias to the sort field
                if (!StringUtils.isEmpty(tableAlias)) {
                    sortFieldValue = tableAlias + "." + sortFieldValue;
                }
            }
            sortFieldOrderByMap.put(sortFieldKey, " order by " + sortFieldValue);
            logger.debug(METHODNAME, "sortFieldKey: ", sortFieldKey, "; sortFieldValue: ", sortFieldValue);
        }
        for (Method method : dtoClass.getDeclaredMethods()) {
            SortColumn column = getSortColumnFromSortColumns(method.getAnnotation(SortColumns.class), databaseType);
            if (column == null) {
                column = method.getAnnotation(SortColumn.class);
            }
            if (column != null) {
                logger.debug(METHODNAME, "method sortFieldKey: ", column.sortFieldKey(), "; method sortFieldValue: ", column.sortFieldValue());
                sortFieldOrderByMap.put(column.sortFieldKey(), " order by " + column.sortFieldValue());
            }
        }
        OrderByMapEntries orderByMapEntries = dtoClass.getAnnotation(OrderByMapEntries.class);
        if (orderByMapEntries != null) {
            for (OrderByMapEntry item : orderByMapEntries.value()) {
                sortFieldOrderByMap.put(item.sortFieldKey(), " order by " + item.sortFieldValue());
            }
        }
        return sortFieldOrderByMap;
    }

    private static final Map<Class, Comparator> dtoComparatorMap = new HashMap<Class, Comparator>();

    // TODO: cache this
    public static <S extends Comparator> S getDtoComparator(Class<? extends BaseDTO> dtoClass) {

        S result = null;
        if (!dtoComparatorMap.containsKey(dtoClass)) {
            OrderBy orderBy = dtoClass.getAnnotation(OrderBy.class);
            if (orderBy != null) {
                Class<? extends Comparator> comparator = orderBy.comparator();
                if (comparator != null && comparator != Comparator.class) {
                    try {
                        result = (S) comparator.newInstance();
                    } catch (InstantiationException e) {
                        logger.error(e);
                    } catch (IllegalAccessException e) {
                        logger.error(e);
                    }
                }
            }
            dtoComparatorMap.put(dtoClass, result);
        } else {
            result = (S) dtoComparatorMap.get(dtoClass);
        }
        return result;
    }

    private static final Map<Class, String> dtoOrderbyMap = new HashMap<Class, String>();

    public static String getDtoOrderBy(Class<? extends BaseDTO> dtoClass) {

        String result = "";
        if (!dtoOrderbyMap.containsKey(dtoClass)) {
            OrderBy orderBy = dtoClass.getAnnotation(OrderBy.class);
            if (orderBy != null) {
                String fields = orderBy.fields();
                if (!StringUtils.isEmpty(fields)) {
                    result = "order by " + fields;
                }
            }
            dtoOrderbyMap.put(dtoClass, result);
        } else {
            result = dtoOrderbyMap.get(dtoClass);
        }
        return result;
    }

    public static <S extends BaseDTO> void logDTOProperties(S dto) {
        logDTOProperties(dto, 0);
    }

    public static <S extends BaseDTO> void logDTOProperties(S dto, int indent) {
        try {
            Class<? extends BaseDTO> dtoClass = dto.getClass();
            logger.info(String.format("%sDTO Properties for %s: %s",
                    org.apache.commons.lang3.StringUtils.repeat("     ", indent),
                    dtoClass.getSimpleName(),
                    dto.getPrimaryKey()));
            for (Field field : ClassUtils.getNonBaseDTODeclaredFields(dtoClass)) {
                field.setAccessible(true);
                if (!(field.getType().getSuperclass() == BaseDTO.class)) {
                    logger.info(String.format("%s - property(%s): %s - value: %s",
                            org.apache.commons.lang3.StringUtils.repeat("     ", indent + 1),
                            field.getType().getSimpleName(),
                            field.getName(),
                            field.get(dto)));
                } else {
                    logDTOProperties((BaseDTO) field.get(dto), indent + 1);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static Entity getEntity(Class<? extends BaseDTO> dtoClass) {
        return dtoClass.getAnnotation(Entity.class);
    }

    private static final Map<Class, DTOTable> dtoClassDTOTableMap = new HashMap<Class, DTOTable>();

    public static DTOTable getDTOTable(Class<? extends BaseDTO> dtoClass) {
        final String METHODNAME = "getDTOTable ";
        if (dtoClassDTOTableMap.containsKey(dtoClass)) {
            return dtoClassDTOTableMap.get(dtoClass);
        } else {
            DTOTable dtoTable = null;
            if (getEntity(dtoClass) != null) {
                dtoTable = new DTOTable(dtoClass);
            }
            dtoClassDTOTableMap.put(dtoClass, dtoTable);
            return dtoTable;
        }
    }

    // TODO: cache this
    public static Table getDtoTable(Class<? extends BaseDTO> dtoClass) {
        return dtoClass.getAnnotation(Table.class);
    }

    // TODO: cache this
    public static JndiReference getJndiReference(Class<? extends BaseDTO> dtoClass) {
        return dtoClass.getAnnotation(JndiReference.class);
    }

    // DTOWrapper is a object that does not have a BO/DAO and do not exist on a parentDTO
    // They are used and constructed solely in CAT and are used as presentation objects
    public static boolean isDTOWrapper(Class<? extends BaseDTO> dtoClass) {
        return dtoClass.isAnnotationPresent(DTOWrapper.class);
    }

    public static Audit getAudit(Class<? extends BaseDTO> dtoClass) {
        return dtoClass.getAnnotation(Audit.class);
    }

    // TODO: cache this
    public static String getJndiReferenceURI(Class<? extends BaseDTO> dtoClass) {
        String uri = null;
        JndiReference jndiReference = getJndiReference(dtoClass);
        if (jndiReference != null) {
            String mgrReference = jndiReference.remote();
            if (mgrReference == null || mgrReference.isEmpty()) {
                mgrReference = getDtoMgrReference(dtoClass);
            }
            uri = String.format("%s/%s", jndiReference.root(), mgrReference);
        }
        return uri;
    }

    // TODO: cache this
    public static String getJndiBoReferenceURI(Class<? extends BaseDTO> dtoClass) {
        String uri = null;
        JndiReference jndiReference = getJndiReference(dtoClass);
        if (jndiReference != null) {
            String boReference = jndiReference.bo();
            if (boReference == null || boReference.isEmpty()) {
                boReference = getDtoBoReference(dtoClass);
            }
            uri = String.format("java:app/%s/%s", jndiReference.root(), boReference);
        }
        return uri;
    }

    // TODO: cache this
    public static String getJndiDaoReferenceURI(Class<? extends BaseDTO> dtoClass) {
        String uri = null;
        JndiReference jndiReference = getJndiReference(dtoClass);
        if (jndiReference != null) {
            String daoReference = jndiReference.dao();
            if (daoReference == null || daoReference.isEmpty()) {
                daoReference = getDtoDaoReference(dtoClass);
            }
            uri = String.format("java:app/%s/%s", jndiReference.root(), daoReference);
        }
        return uri;
    }

    public static void performDTOClassValidation(Class dtoClass, String jndiRoot) {
        System.out.println("Checking DTO: " + dtoClass.getCanonicalName());
        if (!isDTOWrapper(dtoClass)) {
            JndiReference jndiReference = getJndiReference(dtoClass);
            if (jndiReference == null) {
                throw new IllegalStateException(dtoClass.getSimpleName() + " does not have a JndiReference annotation on it!");
            }
            if (!jndiReference.root().equals(jndiRoot)) {
                throw new IllegalStateException(dtoClass.getSimpleName() + " JNDI root mismatch: expected " + jndiRoot + " got " + jndiReference.root());
            }
            Permission permission = getPermission(dtoClass);
            if (permission == null) {
                throw new IllegalStateException(dtoClass.getSimpleName() + " does not have a Permission annotation");
            }
            Table table = getDtoTable(dtoClass);
            if (table == null) {
                logger.warn(dtoClass.getSimpleName() + " does not have a Table annotation");
            }
            boolean serialVersionUIDFound = false;
            for (Field field : dtoClass.getDeclaredFields()) {
                if ("serialVersionUID".equalsIgnoreCase(field.getName())) {
                    serialVersionUIDFound = true;
                    break;
                }
            }
            if (!serialVersionUIDFound) {
                throw new IllegalStateException(dtoClass.getSimpleName() + " does not have a serialVersionUID");
            }
        }
    }

    // TODO: cache this
    public static Permission getPermission(Class<? extends BaseDTO> dtoClass) {
        return dtoClass.getAnnotation(Permission.class);
    }

    public static List<Class> getChildClassDTOs(BaseDTO baseDTO, List<Class> childClassDTOs) {
        final String METHODNAME = "getChildClassDTOs ";
        logger.logBegin(METHODNAME);
        try {
            for (Map.Entry<Class, List<BaseDTO>> entry : baseDTO.getChildDTOMap().entrySet()) {
                Class queryClass = entry.getKey();
                if (logger.isDebugEnabled()) {
                    logger.debug(METHODNAME, "queryClass-", queryClass);
                }

                List<BaseDTO> baseDTOs = entry.getValue();
                if (baseDTOs != null) {
                    if (baseDTOs.size() > 0) {
                        BaseDTO childDTO = baseDTOs.get(0);
                        Class childDtoClass = childDTO.getClass();
                        logger.debug(METHODNAME, "childDtoClass-", childDtoClass);
                        childClassDTOs.add(childDtoClass);

                        // In case the child has children, recurse
                        getChildClassDTOs(childDTO, childClassDTOs);
                    }
                }
            }
            return childClassDTOs;
        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    private static final Map<Class<? extends BaseDTO>, List<Class>> dtoChildClassMap = new HashMap<Class<? extends BaseDTO>, List<Class>>();

    /**
     * Get the derived child list for a DTO.
     *
     * @param dtoClass
     * @return
     */
    public static List<Class> getDtoChildClasses(Class<? extends BaseDTO> dtoClass) {
        final String METHODNAME = "getDtoChildClasses ";
        List<Class> result = dtoChildClassMap.get(dtoClass);
        if (result == null) {
            result = getDtoChildClasses(dtoClass, new ArrayList<Class>(), new ArrayList<Class>());
            logger.debug(METHODNAME, "result for ", dtoClass, ": ", result);
            dtoChildClassMap.put(dtoClass, result);
        }
        return result;
    }

    /**
     * Recursively retrieve a list of child classes for a DTO. This includes both child DTO classes and reference DTO classes.
     * Various scenarios are considered with respect to autoCacheOnly, isAutoCached, isAutoRetrieve
     *
     * @param dtoClass
     * @param childClassDTOs
     * @param processedList
     * @return
     */
    private static List<Class> getDtoChildClasses(Class<? extends BaseDTO> dtoClass, List<Class> childClassDTOs, List<Class> processedList) {
        final String METHODNAME = "getDtoChildClasses ";
        if (!processedList.contains(dtoClass)) {
            processedList.add(dtoClass);
            for (Field field : DTOUtils.getReferenceDTOs(dtoClass)) {
                Class<? extends BaseDTO> referenceDtoClass = (Class<? extends BaseDTO>) field.getType();
                getDtoChildClasses(referenceDtoClass, childClassDTOs, processedList);
            }
            for (Entry<Class<? extends BaseDTO>, ParentChildRelationship> item : getParentChildRelationshipMapByDTO(dtoClass).entrySet()) {
                Class<? extends BaseDTO> dto = item.getKey();

                if (!childClassDTOs.contains(dto)) {
                    childClassDTOs.add(dto);
                }
                getDtoChildClasses(dto, childClassDTOs, processedList);
            }
        }
        return childClassDTOs;
    }

    public static void processAncestorMap(BaseDTO parentDTO, List<BaseDTO> childDTOs, PropertyBagDTO propertyBagDTO) throws ValidationException {
        final String METHODNAME = "processAncestorMap ";
        long start = System.nanoTime();
        try {
            Map<BaseDTO, List<BaseDTO>> ancestorMap = getAncestorMapFromPropertBagDTO(propertyBagDTO);
            List<BaseDTO> parentAncestorList = getAncestorListFromAncestorMap(parentDTO, ancestorMap);
            for (BaseDTO childDTO : childDTOs) {
                List<BaseDTO> childAncestorList = getAncestorListFromAncestorMap(childDTO, ancestorMap);
                processAncestorMap(parentDTO, childAncestorList, parentAncestorList);
            }
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, start);                                                                            
        }
    }

    public static void processAncestorMap(BaseDTO parentDTO, BaseDTO childDTO, PropertyBagDTO propertyBagDTO) throws ValidationException {
        final String METHODNAME = "processAncestorMap ";
        long start = System.nanoTime();
        try {
            Map<BaseDTO, List<BaseDTO>> ancestorMap = getAncestorMapFromPropertBagDTO(propertyBagDTO);
            List<BaseDTO> parentAncestorList = getAncestorListFromAncestorMap(parentDTO, ancestorMap);
            List<BaseDTO> childAncestorList = getAncestorListFromAncestorMap(childDTO, ancestorMap);
            processAncestorMap(parentDTO, childAncestorList, parentAncestorList);
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, start);                                                                            
        }
    }

    private static void processAncestorMap(BaseDTO parentDTO,
            List<BaseDTO> childAncestorList,
            List<BaseDTO> parentAncestorList) {

        // add all the parent's ancestors to the child's list
        for (BaseDTO ancestorDTO : parentAncestorList) {
            if (!childAncestorList.contains(ancestorDTO)) {
                childAncestorList.add(ancestorDTO);
            }
        }
        // add the parent to the child's list
        if (!childAncestorList.contains(parentDTO)) {
            childAncestorList.add(parentDTO);
        }
    }

    private static Map<BaseDTO, List<BaseDTO>> getAncestorMapFromPropertBagDTO(PropertyBagDTO propertyBagDTO) throws ValidationException {

        if (propertyBagDTO == null) {
            throw new ValidationException("PROPERTY BAG MUST BE INITIALIZED!");
        }

        // get or initialize the ancestor map
        Object ancestorMapObj = propertyBagDTO.getPropertyMap().get("ancestorMap");
        Map<BaseDTO, List<BaseDTO>> ancestorMap;
        if (ancestorMapObj == null) {
            ancestorMap = new HashMap<BaseDTO, List<BaseDTO>>();
            propertyBagDTO.getPropertyMap().put("ancestorMap", ancestorMap);
        } else {
            ancestorMap = (Map<BaseDTO, List<BaseDTO>>) ancestorMapObj;
        }
        return ancestorMap;
    }

    private static List<BaseDTO> getAncestorListFromAncestorMap(BaseDTO baseDTO, Map<BaseDTO, List<BaseDTO>> ancestorMap) {
        // get or initialize the ancestor list
        List<BaseDTO> ancestorList = ancestorMap.get(baseDTO);
        if (ancestorList == null) {
            ancestorList = new ArrayList<BaseDTO>();
            ancestorMap.put(baseDTO, ancestorList);
        }
        return ancestorList;
    }

    public static List<BaseDTO> getAncestorListFromProbertyBagDTO(BaseDTO baseDTO, PropertyBagDTO propertyBagDTO) throws ValidationException {
        Map<BaseDTO, List<BaseDTO>> ancestorMap = getAncestorMapFromPropertBagDTO(propertyBagDTO);
        // get or initialize the ancestor list
        List<BaseDTO> ancestorList = ancestorMap.get(baseDTO);
        if (ancestorList == null) {
            ancestorList = new ArrayList<BaseDTO>();
            ancestorMap.put(baseDTO, ancestorList);
        }
        return ancestorList;
    }

    /*
    * Used to return the relationship of the childQueryClass, this will go through the entire structure until it finds the matching ChildQuery Class
     */
    public static ParentChildRelationship getParentChildRelationship(Class dtoClass, Class childQueryClass) {
        ParentChildRelationship parentChildRelationship = getParentChildRelationship(dtoClass, null, childQueryClass);
        if (parentChildRelationship.childQueryClass() != childQueryClass) {
            Map<Class, ParentChildRelationship> parentChildRelationshipMap = DTOUtils.getParentChildRelationshipMapByQueryClass(parentChildRelationship.childDtoClass());
            parentChildRelationship = parentChildRelationshipMap.get(childQueryClass);
        }
        return parentChildRelationship;
    }

    public static ParentChildRelationship getParentChildRelationship(Class dtoClass, ParentChildRelationship startAtParentChildRelationship, Class childQueryClass) {
        final String METHODNAME = "getParentChildRelationship ";
        logger.info(METHODNAME, "startAtParentChildRelationship=" + startAtParentChildRelationship + " childQueryClass=" + childQueryClass.getCanonicalName());

        ParentChildRelationship parentChildRelationship = null;
        Map<Class, ParentChildRelationship> parentChildRelationshipMap = DTOUtils.getParentChildRelationshipMapByQueryClass(dtoClass);
        if (parentChildRelationshipMap.get(childQueryClass) != null) {
            if (startAtParentChildRelationship == null) {
                startAtParentChildRelationship = parentChildRelationshipMap.get(childQueryClass);
            }
            parentChildRelationship = startAtParentChildRelationship;
        } else {
            for (Map.Entry<Class, ParentChildRelationship> parentChildRelationshipEntry : parentChildRelationshipMap.entrySet()) {
                logger.info(METHODNAME, "getKey().getCanonicalName()=" + parentChildRelationshipEntry.getKey().getCanonicalName());
                logger.info(METHODNAME, "getValue().childDtoClass().getCanonicalName()=" + parentChildRelationshipEntry.getValue().childDtoClass().getCanonicalName());
                logger.info(METHODNAME, "getValue().childQueryClass().getCanonicalName()=" + parentChildRelationshipEntry.getValue().childQueryClass().getCanonicalName());

                parentChildRelationship = getParentChildRelationship(parentChildRelationshipEntry.getValue().childDtoClass(), parentChildRelationshipEntry.getValue(), childQueryClass);
                if (parentChildRelationship != null) {
                    break;
                }
            }
        }
        logger.info(METHODNAME, "parentChildRelationship=" + parentChildRelationship);

        return parentChildRelationship;
    }

    public static void copyDtoDeclaredFields(BaseDTO source, BaseDTO dest) throws IllegalArgumentException, IllegalAccessException {
        if (source == null) {
            throw new IllegalArgumentException("source DTO is null!");
        }
        if (dest == null) {
            throw new IllegalArgumentException("dest DTO is null!");
        }
        if (dest.getClass() != source.getClass()) {
            throw new IllegalArgumentException("source and dest classes are not the same!");
        }
        List<Field> declaredFields = ClassUtils.getNonBaseDTODeclaredFields(source.getClass());
        for (Field field : declaredFields) {
            field.setAccessible(true);
            field.set(dest, field.get(source));
        }
    }
}
