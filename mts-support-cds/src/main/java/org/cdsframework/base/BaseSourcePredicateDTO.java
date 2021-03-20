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

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElementRef;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.ColumnSubstitutions;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.dto.CriteriaDTO;
import org.cdsframework.dto.CriteriaPredicateDTO;
import org.cdsframework.enumeration.CriteriaPredicateType;
import org.cdsframework.enumeration.PredicateConjunction;

/**
 *
 * @author HLN Consulting, LLC
 * @param <T>
 * @param <Y>
 */
@ColumnSubstitutions({
    @Column(name = "predicate_order", selectable = false, insertable = false, updateable = false),
    @Column(name = "predicate_conjunction", selectable = false, insertable = false, updateable = false),
    @Column(name = "description", selectable = false, insertable = false, updateable = false),
    @Column(name = "predicate_type", selectable = false, insertable = false, updateable = false)
})
public abstract class BaseSourcePredicateDTO<T extends BasePredicateDTO, Y extends BasePredicatePartDTO> extends BasePredicateDTO<T, Y> {

    private static final long serialVersionUID = -6440291173946051215L;

    @ReferenceDTO(isNotFoundAllowed = false)
    @NotNull
    @Column(name = "source_predicate_id")
    private CriteriaPredicateDTO sourcePredicateDTO;

    @XmlElementRef(name = "sourcedPredicate")
    @Override
    public boolean isSourcedPredicate() {
        return true;
    }

    @XmlElementRef(name = "predicateConjunction")
    @Override
    public PredicateConjunction getPredicateConjunction() {
        PredicateConjunction predicateConjunction = null;
        if (sourcePredicateDTO != null) {
            predicateConjunction = sourcePredicateDTO.getPredicateConjunction();
        }
        return predicateConjunction;
    }

    @XmlElementRef(name = "predicateType")
    @Override
    public CriteriaPredicateType getPredicateType() {
        CriteriaPredicateType criteriaPredicateType = null;
        if (sourcePredicateDTO != null) {
            criteriaPredicateType = sourcePredicateDTO.getPredicateType();
        }
        return criteriaPredicateType;
    }

    @XmlElementRef(name = "predicateOrder")
    @Override
    public int getPredicateOrder() {
        int predicateOrder = 0;
        if (sourcePredicateDTO != null) {
            predicateOrder = sourcePredicateDTO.getPredicateOrder();
        }
        return predicateOrder;
    }

    @Override
    public String getDescription() {
        String description = null;
        if (sourcePredicateDTO != null) {
            description = sourcePredicateDTO.getDescription();
        }
        return description;
    }

    @Override
    public CriteriaDTO getPredicateCriteriaDTO() {
        CriteriaDTO criteriaDTO = null;
        if (sourcePredicateDTO != null) {
            criteriaDTO = sourcePredicateDTO.getPredicateCriteriaDTO();
        }
        return criteriaDTO;
    }

    /**
     * Get the value of sourcePredicateDTO
     *
     * @return the value of sourcePredicateDTO
     */
    public CriteriaPredicateDTO getSourcePredicateDTO() {
        return sourcePredicateDTO;
    }

    /**
     * Set the value of sourcePredicateDTO
     *
     * @param sourcePredicateDTO new value of sourcePredicateDTO
     */
    @PropertyListener
    public void setSourcePredicateDTO(CriteriaPredicateDTO sourcePredicateDTO) {
        this.sourcePredicateDTO = sourcePredicateDTO;
    }

}
