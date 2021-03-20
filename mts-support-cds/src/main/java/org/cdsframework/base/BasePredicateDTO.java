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

import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.interfaces.PredicateInterface;

/**
 *
 * @author HLN Consulting, LLC
 * @param <T>
 * @param <Y>
 */
public abstract class BasePredicateDTO<T extends BasePredicateDTO, Y extends BasePredicatePartDTO> extends BaseDTO implements PredicateInterface {

    private static final long serialVersionUID = -5495362118103888529L;

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String predicateId;

    /**
     * Get the value of predicateId
     *
     * @return the value of predicateId
     */
    @Override
    public String getPredicateId() {
        return predicateId;
    }

    /**
     * Set the value of predicateId
     *
     * @param predicateId new value of predicateId
     */
    @PropertyListener
    public void setPredicateId(String predicateId) {
        this.predicateId = predicateId;
    }
    @Override
    public boolean isSourcedPredicate() {
        return false;
    }
    @Override
    public String getLabel() {
        String result = "";
        switch (getPredicateType()) {
            case Predicate:
                for (BasePredicatePartDTO item : getPredicatePartDTOs()) {
                    result += item.getLabel();
                }
                break;
            case PredicateGroup:
                for (BasePredicatePartDTO item : getGroupPredicatePartDTOs()) {
                    result += item.getLabel();
                }
                break;
            case Criteria:
                if (getPredicateCriteriaDTO() != null) {
                    result = getPredicateCriteriaDTO().getName();
                } else {
                    result = "getPredicateCriteriaDTO() is null!";
                }
                break;
            default:
                result = "Unknown Predicate type: " + getPredicateType();
        }

        return result;
    }

}
