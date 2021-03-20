/**
 * The MTS support cds project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
 *
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see https://www.hln.com/services/open-source/ or send
 * correspondence to ice@hln.com.
 */
package org.cdsframework.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElementRef;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.DataModelClassType;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.util.comparator.DataModelClassComparator;
import org.cdsframework.util.comparator.DataModelClassNodeComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = DataModelClassComparator.class, fields = "lower(name)")
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = DataModelClassNodeDTO.class, childQueryClass = DataModelClassNodeDTO.ByClassId.class,
            isAutoRetrieve = false, comparatorClass = DataModelClassNodeComparator.class)
})
@Table(databaseId = "CDS", name = "data_model_class")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Data Model Class")
public class DataModelClassDTO extends BaseDTO {

    private static final long serialVersionUID = -7993758943853300231L;

    public interface ByModelId {
    }

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String classId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = DataModelDTO.class)
    private String modelId;
    @NotNull
    @Size(max = 512)
    private String name;
    @Size(max = 2048)
    private String description;
    @NotNull
    @Size(max = 1024)
    private String className;
    @NotNull
    private DataModelClassType classType;
    @ReferenceDTO
    @Column(name = "list_id")
    private CdsListDTO cdsListDTO;
    @ReferenceDTO
    @Column(name = "super_class_id")
    private DataModelClassDTO dataModelSuperClassDTO;
    @Column(name = "is_abstract_class")
    private boolean abstractClass;

    /**
     * Get the value of abstractClass
     *
     * @return the value of abstractClass
     */
    public boolean isAbstractClass() {
        return abstractClass;
    }

    /**
     * Set the value of abstractClass
     *
     * @param abstractClass new value of abstractClass
     */
    @PropertyListener
    public void setAbstractClass(boolean abstractClass) {
        this.abstractClass = abstractClass;
    }

    /**
     * Get the value of dataModelSuperClassDTO
     *
     * @return the value of dataModelSuperClassDTO
     */
    public DataModelClassDTO getDataModelSuperClassDTO() {
        return dataModelSuperClassDTO;
    }

    /**
     * Set the value of dataModelSuperClassDTO
     *
     * @param dataModelSuperClassDTO new value of dataModelSuperClassDTO
     */
    @PropertyListener
    public void setDataModelSuperClassDTO(DataModelClassDTO dataModelSuperClassDTO) {
        this.dataModelSuperClassDTO = dataModelSuperClassDTO;
    }

    /**
     * Get the value of cdsListDTO
     *
     * @return the value of cdsListDTO
     */
    public CdsListDTO getCdsListDTO() {
        return cdsListDTO;
    }

    /**
     * Set the value of cdsListDTO
     *
     * @param cdsListDTO new value of cdsListDTO
     */
    @PropertyListener
    public void setCdsListDTO(CdsListDTO cdsListDTO) {
        this.cdsListDTO = cdsListDTO;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    @PropertyListener
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the value of classType
     *
     * @return the value of classType
     */
    public DataModelClassType getClassType() {
        return classType;
    }

    /**
     * Set the value of classType
     *
     * @param classType new value of classType
     */
    @PropertyListener
    public void setClassType(DataModelClassType classType) {
        this.classType = classType;
    }

    /**
     * Get the value of classId
     *
     * @return the value of classId
     */
    public String getClassId() {
        return classId;
    }

    /**
     * Set the value of classId
     *
     * @param classId new value of classId
     */
    @PropertyListener
    public void setClassId(String classId) {
        this.classId = classId;
    }

    /**
     * Get the value of modelId
     *
     * @return the value of modelId
     */
    public String getModelId() {
        return modelId;
    }

    /**
     * Set the value of modelId
     *
     * @param modelId new value of modelId
     */
    @PropertyListener
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    @PropertyListener
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of className
     *
     * @return the value of className
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the value of className
     *
     * @param className new value of className
     */
    @PropertyListener
    public void setClassName(String className) {
        this.className = className;
    }

    @XmlElementRef(name = "dataModelClassNodes")
    public List<DataModelClassNodeDTO> getDataModelClassNodeDTOs() {
        return getChildrenDTOs(DataModelClassNodeDTO.ByClassId.class, DataModelClassNodeDTO.class);
    }

    public List<DataModelClassNodeDTO> getInheritedDataModelClassNodeDTOs() {
        List<DataModelClassNodeDTO> result = new ArrayList<DataModelClassNodeDTO>();
        addClassNodes(this.dataModelSuperClassDTO, result);
        return result;
    }

    public List<DataModelClassNodeDTO> getAllDataModelClassNodeDTOs() {
        List<DataModelClassNodeDTO> result = new ArrayList<DataModelClassNodeDTO>();
        addClassNodes(this, result);
        return result;
    }

    protected static void addClassNodes(DataModelClassDTO superClassDTO, List<DataModelClassNodeDTO> result) {
        if (superClassDTO != null) {
            for (DataModelClassNodeDTO item : superClassDTO.getDataModelClassNodeDTOs()) {
                item.setParentDataModelClassDTO(superClassDTO);
                result.add(item);
            }
            addClassNodes(superClassDTO.getDataModelSuperClassDTO(), result);
        }
    }

}
