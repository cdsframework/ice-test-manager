/**
 * CAT CDS support plugin project.
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
package org.cdsframework.section.cds.code;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsCodeOpenCdsConceptRelDTO;
import org.cdsframework.enumeration.CodedElementType;

/**
 *
 * @author sdn
 */
@Named
@ViewScoped
public class CdsCodeGeneralMGR extends BaseModule<CdsCodeDTO> {
    private static final long serialVersionUID = -8983498537390480709L;

    @Override
    protected void initialize() {
        setLazy(true);
        setBaseHeader("Code");
        addOnRowSelectChildClassDTO(CdsCodeOpenCdsConceptRelDTO.class);
    }

    /**
     * Set the value of source_type_filter on the the searchCriteriaDTO queryMap
     *
     * @param codedElementType new value of source_type_filter
     */
    public void setCodedElementTypeFilter(CodedElementType codedElementType) {
        getSearchCriteriaDTO().getQueryMap().put("source_type_filter", codedElementType);
    }

    /**
     * Get the value of source_type_filter from the searchCriteriaDTO queryMap
     *
     * @return the value of source_type_filter from the searchCriteriaDTO queryMap
     */
    public CodedElementType getCodedElementTypeFilter() {
        return (CodedElementType) getSearchCriteriaDTO().getQueryMap().get("source_type_filter");
    }

    /**
     * Set the value of source_id_filter on the the searchCriteriaDTO queryMap
     *
     * @param codedElementSourceFilter new value of source_id_filter
     */
    public void setCodedElementSourceFilter(String... codedElementSourceFilter) {
        getSearchCriteriaDTO().getQueryMap().put("source_id_filter", codedElementSourceFilter);
    }

    /**
     * Get the value of source_id_filter from the searchCriteriaDTO queryMap
     *
     * @return the value of source_id_filter from the searchCriteriaDTO queryMap
     */
    public String[] getCodedElementSourceFilter() {
        return (String[]) getSearchCriteriaDTO().getQueryMap().get("source_id_filter");
    }
}
