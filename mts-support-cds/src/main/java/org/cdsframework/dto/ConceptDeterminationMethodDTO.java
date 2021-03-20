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

import javax.validation.constraints.NotNull;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.SortColumn;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.util.comparator.ConceptDeterminationMethodComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Entity
@OrderBy(comparator = ConceptDeterminationMethodComparator.class, fields = "lower(display_name)")
@Table(databaseId = "CDS", name = "concept_determination_method")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Concept Determination Method")
public class ConceptDeterminationMethodDTO extends BaseDTO {

    private static final long serialVersionUID = -5085463026283774593L;

    public interface ByCode {
    }

    public interface OpenCdsExport {
    }

    public interface OpenCdsDeploy {
    }

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String codeId;
    @NotNull
    private String code;
    @SortColumn(sortFieldKey = "displayName", sortFieldValue = "lower(display_name)")
    private String displayName;

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
}
