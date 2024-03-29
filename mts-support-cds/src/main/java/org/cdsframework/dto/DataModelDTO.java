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

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElementRef;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.util.comparator.DataModelClassComparator;
import org.cdsframework.util.comparator.DataModelClassNodeComparator;
import org.cdsframework.util.comparator.DataModelComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = DataModelComparator.class, fields = "lower(name)")
@Table(databaseId = "CDS", name = "data_model")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Data Model")
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = DataModelClassDTO.class, childQueryClass = DataModelClassDTO.ByModelId.class,
            isAutoRetrieve = false, comparatorClass = DataModelClassComparator.class),
    @ParentChildRelationship(childDtoClass = DataModelClassNodeDTO.class, childQueryClass = DataModelClassNodeDTO.ByModelId.class,
            isAutoRetrieve = false, comparatorClass = DataModelClassNodeComparator.class)
})
public class DataModelDTO extends BaseDTO {

    private static final long serialVersionUID = 8128471003897734366L;

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String modelId;
    @NotNull
    @Size(max = 512)
    private String name;
    @Size(max = 2048)
    private String description;
    @NotNull
    @Size(max = 32)
    private String version;

    /**
     * Get the value of version
     *
     * @return the value of version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set the value of version
     *
     * @param version new value of version
     */
    @PropertyListener
    public void setVersion(String version) {
        this.version = version;
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
     * Returns the child list of DataModelClassDTOs
     * @return 
     */
    @XmlElementRef(name = "dataModelClasses")
    public List<DataModelClassDTO> getDataModelClassDTOs() {
        return getChildrenDTOs(DataModelClassDTO.ByModelId.class, DataModelClassDTO.class);
    }

    /**
     * Returns the child list of DataModelNodeDTOs
     * @return 
     */
    @XmlElementRef(name = "dataModelClassNodes")
    public List<DataModelClassNodeDTO> getDataModelClassNodeDTOs() {
        return getChildrenDTOs(DataModelClassNodeDTO.ByModelId.class, DataModelClassNodeDTO.class);
    }
//
//    public List<DataModelRootNodeDTO> getAllDataModelClassNodeDTOs() {
//        List<DataModelRootNodeDTO> result = new ArrayList<DataModelRootNodeDTO>();
//        for (DataModelRootNodeDTO item : getDataModelRootNodeDTOs()) {
//            result.add(item);
//            DataModelClassDTO.addClassNodes(item.getDataModelClassDTO(), result);
//        }
//        return result;
//    }

}
