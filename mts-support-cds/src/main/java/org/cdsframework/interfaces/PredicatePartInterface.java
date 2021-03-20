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
package org.cdsframework.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.cdsframework.base.BasePredicatePartConceptDTO;
import org.cdsframework.dto.CriteriaDataTemplateRelNodeDTO;
import org.cdsframework.dto.CriteriaPredicatePartRelDTO;
import org.cdsframework.dto.CriteriaResourceDTO;
import org.cdsframework.dto.CriteriaResourceParamDTO;
import org.cdsframework.enumeration.ConceptSelectionType;
import org.cdsframework.enumeration.CriteriaResourceType;
import org.cdsframework.enumeration.DataModelClassType;
import org.cdsframework.enumeration.PredicatePartType;

/**
 *
 * @author HLN Consulting, LLC
 */
public interface PredicatePartInterface {

    public String getPartId();

    public String getLabel();

    public String getPartAlias();

    public boolean isOverridable();

    public PredicatePartType getPartType();

    public CriteriaResourceType getResourceType();

    public int getPredicatePartOrder();

    public DataModelClassType getDataInputClassType();

    public ConceptSelectionType getConceptSelectionType();

    public boolean isDataInputBoolean();

    public String getText();

    public List<CriteriaPredicatePartRelDTO> getPredicatePartRelDTOs();

    public CriteriaDataTemplateRelNodeDTO getCriteriaDataTemplateRelNodeDTO();

    public CriteriaResourceDTO getCriteriaResourceDTO();

    public String getDefaultIdentifierRoot();

    public String getDefaultIdentifierExtension();

    public CriteriaResourceParamDTO getCriteriaResourceParamDTO();

    public Date getDataInputDate1();

    public Date getDataInputDate2();

    public BigDecimal getDataInputNumeric();

    public boolean isSourcedPart();

    public List<? extends BasePredicatePartConceptDTO> getPredicatePartConceptDTOs();

    public String getConcatenatedConceptSelectionType(String type);

    public boolean isFunctionEnd();

    public boolean isParameterEnd();

}
