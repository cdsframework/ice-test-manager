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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.cdsframework.annotation.Audit;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.ColumnSubstitutions;
import org.cdsframework.annotation.Columns;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Ignore;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.Table;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.dto.AuditLogDTO;
import org.cdsframework.dto.AuditTransactionDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.group.None;

/**
 *
 * @author HLN Consulting, LLC
 */
public class DTOTable {

    private final static LogUtils logger = LogUtils.getLogger(DTOTable.class);
    private final Class<? extends BaseDTO> dtoClass;
    private final Map<Field, DTOProperty> dtoPropertyMap = new HashMap<Field, DTOProperty>();
    private HashMap<String, Column> columnMap = new HashMap<String, Column>();
    private Table table = null;
    private List<Field> primaryKeyFields = null;
    private boolean primaryKeyExists;
    public static final String ORIGINAL_PREFIX = "original_";
    private final Map<Class, List<DTOProperty>> parentForeignKeyMap = new HashMap<Class, List<DTOProperty>>();
    private final Map<Class, String> parentForeignKeyDmlMap = new HashMap<Class, String>();
    private boolean parentForeignKeysExists;

    private String insertDML;
    private String updateDML;
    private String deleteDML;
    private String selectDML;
    private String selectByParentIdDML;
    private String selectByPrimaryKeyDML;
    private String orderBy;
    private String tableAlias;

    public DTOTable(Class<? extends BaseDTO> dtoClass) {
        this.dtoClass = dtoClass;
        initialize();
    }

    /**
     * Initialize the class properties.
     *
     * @param dtoClass
     */
    private void initialize() {
        final String METHODNAME = "initialize ";
        long start = System.nanoTime();

        // Get the Entity
        if (DTOUtils.getEntity(dtoClass) == null) {
            throw new IllegalArgumentException("The dtoClass " + dtoClass.getCanonicalName() + " was not annotated with the Entity annotation");
        }

        // get the order by
        orderBy = DTOUtils.getDtoOrderBy(dtoClass);

        // Get the Table
        table = DTOUtils.getDtoTable(dtoClass);
        if (table == null) {
            throw new IllegalArgumentException("The dtoClass " + dtoClass.getCanonicalName() + " was not annotated with the Table annotation");
        }

        columnMap = new HashMap<String, Column>();
        // Add standard baseDTO fields, later annotate them so that they are included
        // For now leave here to keep BaseDTO signature the same. This way client deployments wont get affected
        addTableColumn(dtoClass, "createId", true, true, false, false, false, None.class);
        addTableColumn(dtoClass, "createDatetime", true, true, false, false, false, None.class);
        addTableColumn(dtoClass, "lastModId", true, true, true, false, false, None.class);
        addTableColumn(dtoClass, "lastModDatetime", true, true, true, false, false, None.class);

        if (dtoClass == AuditTransactionDTO.class || dtoClass == AuditLogDTO.class || dtoClass.isAnnotationPresent(Audit.class)) {
            addTableColumn(dtoClass, "auditId", true, true, true, false, false, None.class);
        }

        List<Field> allDeclaredFields = ClassUtils.getNonBaseDTODeclaredFields(dtoClass);
//        logger.debug(METHODNAME, "allDeclaredFields=", allDeclaredFields);
        for (Field field : allDeclaredFields) {
//            logger.debug(METHODNAME, "field.getName()=", field.getName());
            processFieldToPropertyMap(field);
            processFieldToParentForeignKeyMap(field);
        }

        //
        // Initialize DML
        //
        // Get the Primary Key Fields
        List<String> primaryKeyColumns = new ArrayList<String>();

        primaryKeyFields = DTOUtils.getPrimaryKeyFields(dtoClass);
//        logger.debug(METHODNAME, "primaryKeyFields=", primaryKeyFields);
        primaryKeyExists = !primaryKeyFields.isEmpty();
        for (Field primaryKeyField : primaryKeyFields) {
            DTOProperty dtoProperty = dtoPropertyMap.get(primaryKeyField);
            Column[] columns = dtoProperty.getColumns();
            for (Column column : columns) {
                primaryKeyColumns.add(column.name());
            }
        }

        // Insert/Select DML
        String selectColumns = "";
        String insertColumns = "";
        String insertBindColumns = "";

        // Handle table alias
        tableAlias = table.alias();
        List<String> insertableColumns = new ArrayList<String>();
        List<String> updateableColumns = new ArrayList<String>();
        List<String> selectableColumns = new ArrayList<String>();
        List<String> updateWhereColumns = new ArrayList<String>();
        List<String> deleteWhereColumns = new ArrayList<String>();

        for (Map.Entry<String, Column> columnEntry : columnMap.entrySet()) {
            Column column = columnEntry.getValue();
            if (column.insertable()) {
                insertableColumns.add(column.name());
            }
            if (column.updateable()) {
                updateableColumns.add(column.name());
            }
            if (column.selectable()) {
                selectableColumns.add(column.name());
            }
            if (column.addToWhereUpdate()) {
                updateWhereColumns.add(column.name());
            }
            if (column.addToWhereDelete()) {
                deleteWhereColumns.add(column.name());
            }
        }

        insertColumns = getPartialDML(insertableColumns, "", SuffixType.Comma);
        insertBindColumns = getPartialDML(insertableColumns, ":", SuffixType.Comma);

        String tableName = table.name();
        if (!StringUtils.isEmpty(table.view())) {
            tableName = table.view();
        }
        if (!StringUtils.isEmpty(tableAlias)) {
            tableName += " " + tableAlias;
            selectColumns = getPartialDML(selectableColumns, tableAlias + ".", SuffixType.Comma);
        } else {
            selectColumns = getPartialDML(selectableColumns, "", SuffixType.Comma);
        }

        String selectFrom = "SELECT " + selectColumns + " FROM " + tableName;

        if (logger.isDebugEnabled()) {
            logger.info(METHODNAME, "insertColumns=" + insertColumns);
            logger.info(METHODNAME, "insertBindColumns=" + insertBindColumns);
            logger.info(METHODNAME, "selectColumns=" + selectColumns);
        }

        insertDML = "INSERT INTO " + table.name() + " ( " + insertColumns + " ) values ( " + insertBindColumns + " )";
        logger.debug(METHODNAME, "insertDML=" + insertDML);

        // Update, Delete, SelectByPrimaryKey DML
        if (primaryKeyExists) {

            String wherePrimaryKeyColumns = getPartialDML(primaryKeyColumns, "", SuffixType.And);
            String updateColumns = getPartialDML(updateableColumns, "", SuffixType.AndComma);

            if (!StringUtils.isEmpty(tableAlias)) {
                wherePrimaryKeyColumns = getPartialDML(primaryKeyColumns, tableAlias + ".", SuffixType.AndAlias);
            }

            // SelectByPrimaryKey DML
            selectByPrimaryKeyDML = selectFrom + " WHERE " + wherePrimaryKeyColumns;

            String whereUpdateColumns = getPartialDML(updateWhereColumns, ORIGINAL_PREFIX, SuffixType.And);
            String whereDeleteColumns = getPartialDML(deleteWhereColumns, ORIGINAL_PREFIX, SuffixType.And);

            if (logger.isDebugEnabled()) {
                logger.info(METHODNAME, "updateColumns=" + updateColumns);
                logger.info(METHODNAME, "wherePrimaryKeyColumns=" + wherePrimaryKeyColumns);
                logger.info(METHODNAME, "selectByPrimaryKeyDML=" + selectByPrimaryKeyDML);
                logger.info(METHODNAME, "whereUpdateColumns=" + whereUpdateColumns);
                logger.info(METHODNAME, "whereDeleteColumns=" + whereDeleteColumns);
            }

            // Create unique list of update where primary key columns
            List<String> updateWherePrimaryKeyColumns = new ArrayList<String>();
            for (String primaryKeyColumn : primaryKeyColumns) {
                if (updateWhereColumns.isEmpty() || !updateWhereColumns.contains(primaryKeyColumn)) {
                    updateWherePrimaryKeyColumns.add(primaryKeyColumn);
                }
            }
            String updateWhere = getPartialDML(updateWherePrimaryKeyColumns, "", SuffixType.And);
            if (!StringUtils.isEmpty(whereUpdateColumns)) {
                if (!StringUtils.isEmpty(updateWhere)) {
                    updateWhere += " AND ";
                }
                updateWhere += whereUpdateColumns;
            }

            // Update DML
            updateDML = "UPDATE " + table.name() + " SET " + updateColumns + " WHERE " + updateWhere;
            logger.debug(METHODNAME, "updateDML=" + updateDML);

            // Create unique list of delete where primary key columns
            List<String> deleteWherePrimaryKeyColumns = new ArrayList<String>();
            for (String primaryKeyColumn : primaryKeyColumns) {
                if (deleteWhereColumns.isEmpty() || !deleteWhereColumns.contains(primaryKeyColumn)) {
                    deleteWherePrimaryKeyColumns.add(primaryKeyColumn);
                }
            }
            String deleteWhere = getPartialDML(deleteWherePrimaryKeyColumns, "", SuffixType.And);
            if (!StringUtils.isEmpty(whereDeleteColumns)) {
                if (!StringUtils.isEmpty(deleteWhere)) {
                    deleteWhere += " AND ";
                }
                deleteWhere += whereDeleteColumns;
            }

            // Delete DML
            deleteDML = "DELETE FROM " + table.name() + " WHERE " + deleteWhere;
            logger.debug(METHODNAME, "deleteDML=" + deleteDML);
        }

        // Select DML
        selectDML = selectFrom;

        // initialize the parent foreign key where clause map
        parentForeignKeysExists = !parentForeignKeyMap.isEmpty();
        if (logger.isDebugEnabled()) {
            logger.info(METHODNAME, "parentForeignKeyMap: ", parentForeignKeyMap);
            logger.info(METHODNAME, "parentForeignKeysExists: ", parentForeignKeysExists);
        }

        for (Class item : parentForeignKeyMap.keySet()) {
            StringBuilder whereClauseStringBuilder = new StringBuilder();
            List<DTOProperty> propertyList = parentForeignKeyMap.get(item);
            ArrayList<String> parentWhereColumns = new ArrayList<String>();
            // build a where clause that includes each foreign key field
            for (DTOProperty dtoProperty : propertyList) {
                Column[] columns = dtoProperty.getColumns();
                for (Column column : columns) {
                    parentWhereColumns.add(column.name());
                }
            }

            if (!StringUtils.isEmpty(tableAlias)) {
                whereClauseStringBuilder.append(getPartialDML(parentWhereColumns, tableAlias + ".", SuffixType.And));
            } else {
                whereClauseStringBuilder.append(getPartialDML(parentWhereColumns, "", SuffixType.And));
            }

            if (!propertyList.isEmpty()) {
                whereClauseStringBuilder.insert(0, " WHERE ");
                whereClauseStringBuilder.insert(0, selectFrom);
                String queryClassDml = whereClauseStringBuilder.toString();
                parentForeignKeyDmlMap.put(item, queryClassDml);
                if (logger.isDebugEnabled()) {
                    logger.debug(METHODNAME, "processed query class: ", item.getCanonicalName());
                    logger.debug(METHODNAME, "constructed foreign key query: ", queryClassDml);
                }
            }
        }

        if (logger.isDebugEnabled()) {
            for (Entry<Class, String> entry : parentForeignKeyDmlMap.entrySet()) {
                logger.info(METHODNAME, "entry.getKey().getSimpleName()=", entry.getKey().getSimpleName());
                logger.info(METHODNAME, "entry.getValue=", entry.getValue());
            }
        }

        logger.debug(METHODNAME, "selectDML=" + selectDML);
        logger.logDuration(LogLevel.DEBUG, METHODNAME, start);                                                                            
    }

    private enum SuffixType {
        Comma, AndComma, AndAlias, And
    }

    private String getPartialDML(List<String> columns, String prefix, SuffixType suffixType) {
        final String METHODNAME = "getPartialDML ";
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 1;
        int columnSize = columns.size();
        String suffix = ", ";
        if (suffixType == SuffixType.And || suffixType == SuffixType.AndAlias) {
            suffix = " AND ";
        }
        for (String column : columns) {
//            logger.info(METHODNAME, "column=", column);
            String altSuffix = suffix;
            if (counter == columnSize) {
                altSuffix = "";
            }
            if (suffixType == SuffixType.And || suffixType == SuffixType.AndComma || suffixType == SuffixType.AndAlias) {
                if (suffixType == SuffixType.AndAlias) {
                    stringBuilder.append(prefix);
                }
                stringBuilder.append(column);
                stringBuilder.append(" = :");
                if (suffixType != SuffixType.AndAlias) {
                    stringBuilder.append(prefix);
                }
                stringBuilder.append(column);
                stringBuilder.append(altSuffix);
            } else if (suffixType == SuffixType.Comma) {
                if (!StringUtils.isEmpty(prefix)) {
                    //partialDml += prefix;
                    stringBuilder.append(prefix);
                }
                //partialDml += column + altSuffix;
                stringBuilder.append(column);
                stringBuilder.append(altSuffix);
            }

            counter++;
        }
        return stringBuilder.toString();
    }

    /**
     * Process the column annotation on the field to the dtoPropertyMap.
     *
     * @param field
     */
    private void processFieldToPropertyMap(Field field) {
        final String METHODNAME = "processFieldToPropertyMap ";
        Column[] columnArray = null;
        Columns columns = field.getAnnotation(Columns.class);
        if (columns == null) {
            Column column = field.getAnnotation(Column.class);
            if (column == null) {
                Ignore ignore = field.getAnnotation(Ignore.class);
                if (ignore == null) {
                    final String fieldName = field.getName();
                    column = getColumn(fieldName, true, true, true, false, false, None.class);
                }
            }
            if (column != null) {
                columnArray = new Column[]{column};
            }
        } else {
            columnArray = columns.value();
        }
        if (columnArray != null) {
            putFieldColumns(field, columnArray);
        }
    }

    /**
     * Construct the dtoPropertyMap member with the supplied field and column objects.
     *
     * @param field
     * @param columns
     */
    private void putFieldColumns(Field field, Column[] columns) {
        dtoPropertyMap.put(field, new DTOProperty(field, columns));
        for (Column column : columns) {
            columnMap.put(column.name(), column);
        }
    }

    /**
     * Process the field to locate the parent field on the parent DTO and set that value on the field's DTOProperty.
     *
     * @param field
     */
    private void processFieldToParentForeignKeyMap(Field field) {
        final String METHODNAME = "processFieldToParentForeignKeyMap ";

        // determine if this field is a foreign key
        Class foreignKeyQueryClass = null;
        Field parentPrimaryKeyField = null;
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (generatedValue != null) {
            if (generatedValue.source() == GenerationSource.FOREIGN_CONSTRAINT) {
                // iterate over source parent classes
                for (Class parentClass : generatedValue.sourceClass()) {
                    // make sure parent class is derived from BaseDTO
                    if (BaseDTO.class.isAssignableFrom(parentClass)) {
                        // get the parent's child annotation map
                        Map<Class<? extends BaseDTO>, ParentChildRelationship> parentChildRelationshipMapByDTO
                                = DTOUtils.getParentChildRelationshipMapByDTO(parentClass);
                        // get the child annotation in question
                        ParentChildRelationship parentChildRelationship = parentChildRelationshipMapByDTO.get(dtoClass);
                        if (parentChildRelationship != null) {
                            // get the child query class
                            foreignKeyQueryClass = parentChildRelationship.childQueryClass();
                            //get the parent primary key fields
                            List<Field> parentPrimaryKeyFields = DTOUtils.getPrimaryKeyFields(parentClass);
                            // if it is a single key (most cases) - set that as the parent primary key field
                            if (parentPrimaryKeyFields.size() == 1) {
                                parentPrimaryKeyField = parentPrimaryKeyFields.get(0);
                            } else {
                                // otherwise if it is a multi key field - find the matching field name to the child field
                                for (Field item : parentPrimaryKeyFields) {
                                    GeneratedValue parentPrimaryKeyFieldGeneratedValue = field.getAnnotation(GeneratedValue.class);
                                    if (parentPrimaryKeyFieldGeneratedValue != null
                                            && parentPrimaryKeyFieldGeneratedValue.fieldName() != null
                                            && generatedValue.fieldName() != null
                                            && generatedValue.fieldName().equalsIgnoreCase(parentPrimaryKeyFieldGeneratedValue.fieldName())) {
                                        parentPrimaryKeyField = item;
                                        break;
                                    }
                                }
                            }
                        } else {
                            logger.error(METHODNAME,
                                    "foreign key exists to a parent that has no parent child relationship configured. child: ",
                                    dtoClass.getCanonicalName(),
                                    "; parent: ",
                                    parentClass.getCanonicalName(),
                                    "; child field: ",
                                    field.getName());
                        }
                    } else {
                        logger.error(METHODNAME, "source class registered on child that isn't a DTO.");
                    }
                }
            }
        }

        // setup foreign key map
        if (foreignKeyQueryClass != null) {
            List<DTOProperty> foreignKeyQueryClassPropertyList = parentForeignKeyMap.get(foreignKeyQueryClass);
            // if the foreignKeyQueryClassPropertyList is null then initialize it
            if (foreignKeyQueryClassPropertyList == null) {
                foreignKeyQueryClassPropertyList = new ArrayList<DTOProperty>();
                parentForeignKeyMap.put(foreignKeyQueryClass, foreignKeyQueryClassPropertyList);
            }
            // get the DTOProperty corresponding to the current field
            DTOProperty dtoProperty = dtoPropertyMap.get(field);
            if (dtoProperty != null) {
                // if foreignKeyQueryClass isn't null then parentPrimaryKeyField shouldn't be either
                if (parentPrimaryKeyField == null) {
                    logger.warn(METHODNAME, "foreignKeyQueryClass ",
                            foreignKeyQueryClass.getCanonicalName(),
                            " doesn't have a parentPrimaryKeyField set!");
                }
                // set the parent field on the DTOProperty
                dtoProperty.setParentField(parentPrimaryKeyField);
                // add the dtoProperty to the foreignKeyQueryClassPropertyList
                foreignKeyQueryClassPropertyList.add(dtoProperty);
            }
        }
    }

    /**
     * Construct a new Column object with the supplied properties.
     *
     * @param fieldName
     * @param insertable
     * @param updateable
     * @param addToWhereUpdate
     * @param addToWhereDelete
     * @param resultSetClass
     * @return
     */
    private Column getColumn(final String fieldName, final boolean selectable, final boolean insertable, final boolean updateable,
            final boolean addToWhereUpdate, final boolean addToWhereDelete, final Class resultSetClass) {
        return new Column() {
            @Override
            public String name() {
                return DTOUtils.getColumnName(fieldName);
            }

            @Override
            public boolean insertable() {
                return insertable;
            }

            @Override
            public boolean updateable() {
                return updateable;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Column.class;
            }

            @Override
            public boolean addToWhereUpdate() {
                return addToWhereUpdate;
            }

            @Override
            public boolean addToWhereDelete() {
                return addToWhereDelete;
            }

            @Override
            public Class resultSetClass() {
                return resultSetClass;
            }

            @Override
            public boolean selectable() {
                return selectable;
            }
        };
    }

    /**
     * Convert the fieldName to the columnName.
     *
     * @param fieldName
     * @return
     */
    private String getColumnName(String fieldName) {
        return fieldName.replaceAll("([^_A-Z])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     *
     *
     * @param dtoClass
     * @param fieldName
     * @param insertable
     * @param updateable
     * @param addToWhereUpdate
     * @param addToWhereDelete
     * @param resultSetClass
     */
    private void addTableColumn(Class<? extends BaseDTO> dtoClass, final String fieldName,
            boolean selectable, boolean insertable, boolean updateable, boolean addToWhereUpdate, boolean addToWhereDelete, Class resultSetClass) {
        final String METHODNAME = "addTableColumn ";
        // Add create_id, create_datetime, last_mod_id, last_mod_datetime
        try {

            // Determine if column should be excluded
            ColumnSubstitutions columnSubstitutions = DTOUtils.getColumnSubstitutions(dtoClass);

            if (columnSubstitutions != null) {
                boolean substituteColumn = false;
                Column[] columnsToSubstitute = columnSubstitutions.value();
                for (Column columnToSubstitute : columnsToSubstitute) {
                    substituteColumn = (columnToSubstitute.name().equalsIgnoreCase(DTOUtils.getColumnName(fieldName)));
                    if (substituteColumn) {
                        Field field = BaseDTO.class.getDeclaredField(fieldName);
                        putFieldColumns(field, new Column[]{getColumn(fieldName,
                            columnToSubstitute.selectable(),
                            columnToSubstitute.insertable(),
                            columnToSubstitute.updateable(),
                            columnToSubstitute.addToWhereUpdate(),
                            columnToSubstitute.addToWhereDelete(),
                            resultSetClass)});
                        break;
                    }
                }
                if (!substituteColumn) {
                    Field field = BaseDTO.class.getDeclaredField(fieldName);
                    putFieldColumns(field, new Column[]{getColumn(fieldName,
                        selectable,
                        insertable,
                        updateable,
                        addToWhereUpdate,
                        addToWhereDelete, resultSetClass)});
                }
            } else {
                Field field = BaseDTO.class.getDeclaredField(fieldName);
                putFieldColumns(field, new Column[]{getColumn(fieldName,
                    selectable,
                    insertable,
                    updateable,
                    addToWhereUpdate,
                    addToWhereDelete, resultSetClass)});
            }
        } catch (NoSuchFieldException ex) {
            logger.warn(METHODNAME, "A NoSuchFieldException has occurred; Message: " + ex.getMessage());
        } catch (SecurityException ex) {
            logger.error(ex);
        }
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getInsertDML() {
        return insertDML;
    }

    public String getUpdateDML() {
        return updateDML;
    }

    public String getDeleteDML() {
        return deleteDML;
    }

    public String getSelectDML() {
        return selectDML;
    }

    public String getSelectByParentIdDML() {
        return selectByParentIdDML;
    }

    public String getSelectByPrimaryKeyDML() {
        return selectByPrimaryKeyDML;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public Map<Field, DTOProperty> getDtoPropertyMap() {
        return dtoPropertyMap;
    }

    public Table getTable() {
        return table;
    }

    public Map<Class, List<DTOProperty>> getParentForeignKeyMap() {
        return parentForeignKeyMap;
    }

    public Map<Class, String> getParentForeignKeyDmlMap() {
        return parentForeignKeyDmlMap;
    }

}
