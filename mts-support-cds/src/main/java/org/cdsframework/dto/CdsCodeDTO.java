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
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.OrderByMapEntries;
import org.cdsframework.annotation.OrderByMapEntry;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.util.comparator.CdsCodeComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = CdsCodeOpenCdsConceptRelDTO.class,
            childQueryClass = CdsCodeOpenCdsConceptRelDTO.ByOpenCdsCodeMapping.class)
})
@OrderByMapEntries({
    @OrderByMapEntry(sortFieldKey = "codeSystem", sortFieldValue = "cc.oid"),
    @OrderByMapEntry(sortFieldKey = "codeSystemName", sortFieldValue = "cc.name")
})
@OrderBy(comparator = CdsCodeComparator.class, fields = "lower(display_name)")
@Table(databaseId = "CDS", name = "cds_code", view = "v_cds_code", alias = "cc")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Code")
@XmlRootElement(name = "Code")
public class CdsCodeDTO extends BaseDTO {

    public interface ByCodeCollection {
    }

    public interface ByCodeSystemId {
    }

    public interface ByCodeSystemCode {
    }

    public interface ByCodeSystemOid {
    }

    public interface ByTestIdCodeSystemOidDisease {
    }
    private static final long serialVersionUID = -5494861072576655488L;
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String codeId;
    private String code;
    private String displayName;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = CdsCodeSystemDTO.class)
    private String codeSystemId;
    @Column(name = "oid", updateable = false, insertable = false)
    private String codeSystem;
    @Column(name = "name", updateable = false, insertable = false)
    private String codeSystemName;

    public String getCodeId() {
        return codeId;
    }

    @PropertyListener
    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCodeSystemId() {
        return codeSystemId;
    }

    @PropertyListener
    public void setCodeSystemId(String codeSystemId) {
        this.codeSystemId = codeSystemId;
    }

    /**
     * Get the value of displayName
     *
     * @return the value of displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Set the value of displayName
     *
     * @param displayName new value of displayName
     */
    @PropertyListener
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get the value of code
     *
     * @return the value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the value of code
     *
     * @param code new value of code
     */
    @PropertyListener
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get the value of codeSystem
     *
     * @return the value of codeSystem
     */
    public String getCodeSystem() {
        return codeSystem;
    }

    /**
     * Set the value of codeSystem
     *
     * @param codeSystem new value of codeSystem
     */
    public void setCodeSystem(String codeSystem) {
        this.codeSystem = codeSystem;
    }

    /**
     * Get the value of codeSystemName
     *
     * @return the value of codeSystemName
     */
    public String getCodeSystemName() {
        return codeSystemName;
    }

    /**
     * Set the value of codeSystemName
     *
     * @param codeSystemName new value of codeSystemName
     */
    public void setCodeSystemName(String codeSystemName) {
        this.codeSystemName = codeSystemName;
    }

    /**
     * Returns a standards displayable label for the code.
     *
     * @return
     */
    public String getLabel() {
        String result = null;
        if (displayName != null && code != null) {
            result = String.format("%s (%s)", displayName, code);
        } else if (displayName == null && code != null) {
            result = code;
        } else if (displayName != null && code == null) {
            result = displayName;
        }
        return result;
    }

    /**
     * Return a list of CdsCodeOpenCdsConceptRelDTOs which define the codes list
     * of mappings to OpenCDS codes.
     *
     * @return a list of CdsCodeOpenCdsConceptRelDTOs
     */
    @XmlElementRef(name = "cdsCodeConceptRels")
    public List<CdsCodeOpenCdsConceptRelDTO> getCdsCodeOpenCdsConceptRelDTOs() {
        return getChildrenDTOs(CdsCodeOpenCdsConceptRelDTO.ByOpenCdsCodeMapping.class, CdsCodeOpenCdsConceptRelDTO.class);
    }
}
