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
package org.cdsframework.lookup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsCodeSystemDTO;
import org.cdsframework.dto.ValueSetDTO;
import org.cdsframework.enumeration.CodedElementType;
import org.cdsframework.interfaces.CodedElementSource;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;
import org.cdsframework.util.comparator.CdsCodeComparator;
import org.cdsframework.util.comparator.CodedElementSourceComparator;

/**
 *
 * @author sdn
 */
@Named
@ApplicationScoped
public class CodedElementSourceList implements Serializable {

    private final static LogUtils logger = LogUtils.getLogger(CodedElementSourceList.class);
    private static final long serialVersionUID = -4774708688549923535L;

    @Inject
    private ValueSetDTOList valueSetDTOList;
    @Inject
    private CdsCodeSystemDTOList cdsCodeSystemDTOList;

    final private static CodedElementSourceComparator codedElementSourceComparator = new CodedElementSourceComparator();
    final private static CdsCodeComparator cdsCodeComparator = new CdsCodeComparator();

    /**
     * Given a Code System and Value Sets ID - return the distinct combined list of codes.
     *
     * @param id
     * @return
     */
    public List<CdsCodeDTO> getCodeDTOsByCodeSystemValueSetId(String id) {
        final String METHODNAME = "getCodeDTOsByCodeSystemValueSetId ";
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        return getCodeDTOsByCodeSystemValueSetIds(ids);
    }

    /**
     * Given a list of Code System and Value Sets IDs - return the distinct combined list of codes.
     *
     * @param ids
     * @return
     */
    public List<CdsCodeDTO> getCodeDTOsByCodeSystemValueSetIds(List<String> ids) {
        final String METHODNAME = "getCodeDTOsByCodeSystemValueSetIds ";
        List<CdsCodeDTO> result = new ArrayList<CdsCodeDTO>();
        if (ids != null) {
            for (String id : ids) {
                if (!StringUtils.isEmpty(id)) {
                    CdsCodeSystemDTO cdsCodeSystemDTO = cdsCodeSystemDTOList.get(id);
                    if (cdsCodeSystemDTO != null) {
                        for (CdsCodeDTO cdsCodeDTO : cdsCodeSystemDTO.getCdsCodeDTOs()) {
                            if (!result.contains(cdsCodeDTO)) {
                                result.add(cdsCodeDTO);
                            }
                        }
                    } else {
                        ValueSetDTO valueSetDTO = valueSetDTOList.get(id);
                        if (valueSetDTO != null) {
                            for (CdsCodeDTO cdsCodeDTO : valueSetDTO.getCdsCodeDTOs()) {
                                if (!result.contains(cdsCodeDTO)) {
                                    result.add(cdsCodeDTO);
                                }
                            }
                        } else {
                            logger.error(METHODNAME, "Zero results returned for: ", id);
                        }
                    }
                } else {
                    logger.error(METHODNAME, "id was null!");
                }
            }
        }
        Collections.sort(result, cdsCodeComparator);
        return result;
    }

    /**
     * Return the combined list of Value Sets and Code Systems.
     *
     * @return
     */
    public List<CodedElementSource> getCodeSystemValueSetItems() {
        final String METHODNAME = "getCodeSystemValueSetItems ";
        List<CodedElementSource> result = new ArrayList<CodedElementSource>();
        result.addAll(cdsCodeSystemDTOList.getAll());
        result.addAll(valueSetDTOList.getAll());
        Collections.sort(result, codedElementSourceComparator);
        return result;
    }

    /**
     * Return the combined list of Value Sets and Code Systems with an optional type and id filter.
     *
     * @param codedElementTypeFilter
     * @param codedElementSourceFilter
     * @return
     */
    public List<CodedElementSource> getCodeSystemValueSetItems(
            CodedElementType codedElementTypeFilter,
            String[] codedElementSourceFilter) {

        final String METHODNAME = "getCodeSystemValueSetItems ";
        logger.info(METHODNAME, "codedElementTypeFilter: ", codedElementTypeFilter);
        logger.info(METHODNAME, "codedElementSourceFilter: ",
                codedElementSourceFilter != null ? StringUtils.getStringFromArray(", ", codedElementSourceFilter) : null);
        List<CodedElementSource> result = new ArrayList<CodedElementSource>();
        if (codedElementSourceFilter != null && codedElementSourceFilter.length > 0) {
            for (String id : codedElementSourceFilter) {
                CodedElementSource codedElementSource = null;
                if (codedElementTypeFilter == CodedElementType.CODE_SYSTEM || codedElementTypeFilter == null) {
                    codedElementSource = cdsCodeSystemDTOList.get(id);
                }
                if (codedElementSource == null
                        && (codedElementTypeFilter == CodedElementType.VALUE_SET || codedElementTypeFilter == null)) {
                    codedElementSource = valueSetDTOList.get(id);
                }
                if (codedElementSource != null) {
                    result.add(codedElementSource);
                }
            }
            if (result.isEmpty()) {
                logger.error(METHODNAME, "codedElementSourceFilter != null but no filter id matched a value set or code system!");
            }
        }

        if (result.isEmpty()) {
            result.addAll(cdsCodeSystemDTOList.getAll());
            result.addAll(valueSetDTOList.getAll());
        }

        Collections.sort(result, codedElementSourceComparator);
        return result;
    }

    /**
     * Returns a particular Value Set or Code System based on the ID.
     *
     * @param id
     * @return
     */
    public CodedElementSource getCodedElementSource(String id) {
        final String METHODNAME = "getCodedElementSource ";
        CodedElementSource result = null;
        if (id != null) {
            result = cdsCodeSystemDTOList.get(id);
            if (result == null) {
                result = valueSetDTOList.get(id);
                if (result == null) {
                    logger.error(METHODNAME, "Zero results returned for: ", id);
                }
            }
        } else {
            logger.error(METHODNAME, "id was null!");
        }
        return result;
    }

}
