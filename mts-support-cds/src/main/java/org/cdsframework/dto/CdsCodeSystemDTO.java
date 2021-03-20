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
import javax.xml.bind.annotation.XmlRootElement;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.SortColumn;
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
import org.cdsframework.enumeration.CodedElementType;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.interfaces.CodedElementSource;
import org.cdsframework.util.comparator.CdsCodeComparator;
import org.cdsframework.util.comparator.CodedElementSourceComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = CdsCodeDTO.class,
            childQueryClass = CdsCodeDTO.ByCodeSystemId.class,
            comparatorClass = CdsCodeComparator.class,
            isAutoRetrieve = false)
})
@Table(databaseId = "CDS", name = "cds_code_system")
@OrderBy(comparator = CodedElementSourceComparator.class, fields = "lower(name)")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Code System")
@XmlRootElement(name = "CodeSystem")
public class CdsCodeSystemDTO extends BaseDTO implements CodedElementSource {

    public interface ByOid {
    }

    public interface ByLowerName {
    }

    private static final long serialVersionUID = -3214051524228398494L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String codeSystemId;
    @Size(max = 256)
    @NotNull
    private String oid;
    @Size(max = 256)
    @NotNull
    @SortColumn(sortFieldValue = "lower(name)")
    private String name;

    @Override
    public CodedElementType getCodedElementType() {
        return CodedElementType.CODE_SYSTEM;
    }

    @Override
    public String getTitle() {
        return String.format("%s (%s: %s)", getName(), getCodedElementType(), getOid());
    }

    public String getCodeSystemId() {
        return codeSystemId;
    }

    @PropertyListener
    public void setCodeSystemId(String codeSystemId) {
        this.codeSystemId = codeSystemId;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    @PropertyListener
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of oid
     *
     * @return the value of oid
     */
    @Override
    public String getOid() {
        return oid;
    }

    /**
     * Set the value of oid
     *
     * @param oid new value of oid
     */
    @Override
    @PropertyListener
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * Returns a standards displayable label for the code system.
     * 
     * @return 
     */
    public String getLabel() {
        String result = null;
        if (name != null && oid != null) {
            result = String.format("%s (%s)", name, oid);
        } else if (name == null && oid != null) {
            result = oid;
        } else if (name != null && oid == null) {
            result = name;
        }
        return result;
    }

    @XmlElementRef(name = "codes")
    public List<CdsCodeDTO> getCdsCodeDTOs() {
        return this.getChildrenDTOs(CdsCodeDTO.ByCodeSystemId.class, CdsCodeDTO.class);
    }

    public CdsCodeDTO getCdsCodeDTO(String primaryKey) throws MtsException {
        CdsCodeDTO matchingCdsCodeDTO = null;
        List<CdsCodeDTO> cdsCodeDTOs = getCdsCodeDTOs();
        for (CdsCodeDTO cdsCodeDTO : cdsCodeDTOs) {
            if (primaryKey.equals(cdsCodeDTO.getPrimaryKey())) {
                matchingCdsCodeDTO = cdsCodeDTO;
                break;
            }
        }
        return matchingCdsCodeDTO;
    }
}
