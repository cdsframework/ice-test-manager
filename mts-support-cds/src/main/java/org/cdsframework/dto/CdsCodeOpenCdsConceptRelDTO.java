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

import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.ParentBehavior;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@ParentBehavior(deleteAllowed = false)
@Table(databaseId = "CDS", name = "cds_code_opencds_concept_rel")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Code Concept Relationship")
public class CdsCodeOpenCdsConceptRelDTO extends BaseDTO {

    private static final long serialVersionUID = -6778311589611345474L;

    public interface ByOpenCdsCodeMapping {
    }
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = CdsCodeDTO.class)
    @Id
    private String codeId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = OpenCdsConceptDTO.class)
    @Id
    @Column(name = "concept_code_id")
    private String openCdsCodeId;

    /**
     * Get the value of codeId
     *
     * @return the value of codeId
     */
    public String getCodeId() {
        return codeId;
    }

    /**
     * Set the value of codeId
     *
     * @param codeId new value of codeId
     */
    @PropertyListener
    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    /**
     * Get the value of openCdsCodeId
     *
     * @return the value of openCdsCodeId
     */
    public String getOpenCdsCodeId() {
        return openCdsCodeId;
    }

    /**
     * Set the value of openCdsCodeId
     *
     * @param openCdsCodeId new value of openCdsCodeId
     */
    @PropertyListener
    public void setOpenCdsCodeId(String openCdsCodeId) {
        this.openCdsCodeId = openCdsCodeId;
    }
}
