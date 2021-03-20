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
package org.cdsframework.base;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElementRef;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.dto.CriteriaDataTemplateRelNodeDTO;
import org.cdsframework.dto.CriteriaPredicatePartDTO;
import org.cdsframework.dto.CriteriaPredicatePartRelDTO;
import org.cdsframework.enumeration.ConceptSelectionType;
import org.cdsframework.enumeration.CriteriaResourceType;
import org.cdsframework.enumeration.DataModelClassType;
import org.cdsframework.enumeration.PredicatePartType;

/**
 *
 * @author HLN Consulting, LLC
 */
public abstract class BasePredicateSourcePartDTO extends BasePredicatePartDTO {

    private static final long serialVersionUID = -4728389629015939983L;
    @ReferenceDTO(isNotFoundAllowed = false)
    @NotNull
    @Column(name = "source_part_id")
    private CriteriaPredicatePartDTO sourcePredicatePartDTO;

    /**
     * Get the value of sourcePredicatePartDTO
     *
     * @return the value of sourcePredicatePartDTO
     */
    public CriteriaPredicatePartDTO getSourcePredicatePartDTO() {
        return sourcePredicatePartDTO;
    }

    /**
     * Set the value of sourcePredicatePartDTO
     *
     * @param sourcePredicatePartDTO new value of sourcePredicatePartDTO
     */
    @PropertyListener
    public void setSourcePredicatePartDTO(CriteriaPredicatePartDTO sourcePredicatePartDTO) {
        this.sourcePredicatePartDTO = sourcePredicatePartDTO;
    }

    @XmlElementRef(name = "partAlias")
    @Override
    public String getPartAlias() {
        String result = null;
        if (sourcePredicatePartDTO != null) {
            result = sourcePredicatePartDTO.getPartAlias();
        }
        return result;
    }

    @XmlElementRef(name = "partType")
    @Override
    public PredicatePartType getPartType() {
        PredicatePartType result = null;
        if (sourcePredicatePartDTO != null) {
            result = sourcePredicatePartDTO.getPartType();
        }
        return result;
    }

    @XmlElementRef(name = "resourceType")
    @Override
    public CriteriaResourceType getResourceType() {
        CriteriaResourceType result = null;
        if (sourcePredicatePartDTO != null) {
            result = sourcePredicatePartDTO.getResourceType();
        }
        return result;
    }

    @XmlElementRef(name = "partOrder")
    @Override
    public int getPredicatePartOrder() {
        int result = 0;
        if (sourcePredicatePartDTO != null) {
            result = sourcePredicatePartDTO.getPredicatePartOrder();
        }
        return result;
    }

    @XmlElementRef(name = "dataInputClassType")
    @Override
    public DataModelClassType getDataInputClassType() {
        DataModelClassType result = null;
        if (sourcePredicatePartDTO != null) {
            result = sourcePredicatePartDTO.getDataInputClassType();
        }
        return result;
    }

    @XmlElementRef(name = "conceptSelectionType")
    @Override
    public ConceptSelectionType getConceptSelectionType() {
        ConceptSelectionType result = null;
        if (sourcePredicatePartDTO != null) {
            result = sourcePredicatePartDTO.getConceptSelectionType();
        }
        return result;
    }

    @Override
    public List<CriteriaPredicatePartRelDTO> getPredicatePartRelDTOs() {
        List<CriteriaPredicatePartRelDTO> result = new ArrayList<CriteriaPredicatePartRelDTO>();
        if (sourcePredicatePartDTO != null) {
            result = sourcePredicatePartDTO.getPredicatePartRelDTOs();
        }
        return result;
    }

    @XmlElementRef(name = "criteriaDataTemplateRelNode")
    @Override
    public CriteriaDataTemplateRelNodeDTO getCriteriaDataTemplateRelNodeDTO() {
        CriteriaDataTemplateRelNodeDTO result = null;
        if (sourcePredicatePartDTO != null) {
            result = sourcePredicatePartDTO.getCriteriaDataTemplateRelNodeDTO();
        }
        return result;
    }

}
