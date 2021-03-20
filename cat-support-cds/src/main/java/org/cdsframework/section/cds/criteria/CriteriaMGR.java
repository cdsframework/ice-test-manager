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
package org.cdsframework.section.cds.criteria;

import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CriteriaDTO;
import org.cdsframework.dto.CriteriaDataTemplateRelDTO;
import org.cdsframework.dto.CriteriaDataTemplateRelNodeDTO;
import org.cdsframework.dto.CriteriaPredicateDTO;
import org.cdsframework.dto.CriteriaPredicatePartDTO;
import org.cdsframework.dto.CriteriaResourceParamDTO;
import org.cdsframework.dto.CriteriaVersionRelDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.util.StringUtils;
import org.cdsframework.util.enumeration.SourceMethod;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class CriteriaMGR extends BaseModule<CriteriaDTO> {

    private static final long serialVersionUID = 3175997034057311545L;
    @Inject
    private CriteriaVersionRelMGR criteriaVersionRelMGR;
    @Inject
    private CriteriaDataTemplateRelMGR criteriaDataTemplateRelMGR;
    @Inject
    private CriteriaPredicateMGR criteriaPredicateMGR;

    @Override
    protected void initialize() {
        setLazy(true);
        setInitialQueryClass("FindAll");
        setSaveImmediately(true);
        registerChild(CriteriaVersionRelDTO.ByCriteriaId.class, criteriaVersionRelMGR);
        registerChild(CriteriaDataTemplateRelDTO.ByCriteriaId.class, criteriaDataTemplateRelMGR);
        registerChild(CriteriaPredicateDTO.ByCriteriaId.class, criteriaPredicateMGR);
    }

    @Override
    public void registerTabComponents() {
        final String METHODNAME = "registerTabLazyLoadComponents ";
        logger.debug(METHODNAME);
        // Register the Components
        getTabService().registeredUIComponent(0, this);
        getTabService().registeredUIComponent(0, criteriaVersionRelMGR);
        getTabService().registeredUIComponent(0, criteriaDataTemplateRelMGR);
        getTabService().registeredUIComponent(1, criteriaPredicateMGR);
    }

    @Override
    public void clearSearchMain(ActionEvent actionEvent) {
        String criteriaTypeFilters = (String) getSearchCriteriaDTO().getQueryMap().get("criteriaTypeFilters");
        super.clearSearchMain(actionEvent);
        if (!StringUtils.isEmpty(criteriaTypeFilters)) {
            getSearchCriteriaDTO().getQueryMap().put("criteriaTypeFilters", criteriaTypeFilters);
        }
    }

//
//    /**
//     * There is a opencds concept popup manager whose selected value needs to be added to the CriteriaDTO CriteriaConceptRelDTO
//     * child list when this fires...
//     *
//     * @param selectEvent
//     */
//    @Override
//    public void onRowSelectMain(SelectEvent selectEvent) {
//        final String METHODNAME = "onRowSelectMain ";
//        Object selectedObject = selectEvent.getObject();
//        logger.debug(METHODNAME, "called: ", selectedObject);
//        if (selectedObject instanceof OpenCdsConceptDTO) {
//            CriteriaDTO parentDTO = getParentDTO();
//            CriteriaConceptRelDTO criteriaConceptRelDTO = new CriteriaConceptRelDTO();
//            criteriaConceptRelDTO.setOpenCdsConceptDTO((OpenCdsConceptDTO) selectedObject);
//            parentDTO.addOrUpdateChildDTO(criteriaConceptRelDTO);
//        } else {
//            super.onRowSelectMain(selectEvent);
//        }
//    }
    public CriteriaDTO getFullCriteriaDTO(CriteriaDTO dto) {
        final String METHODNAME = "getFullCriteriaPredicateDTO ";
        logger.debug(METHODNAME, "dto=", dto);
//        long start = System.nanoTime();

        CriteriaDTO result = dto;
        try {
            PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
            List<Class<? extends BaseDTO>> childClasses = new ArrayList<Class<? extends BaseDTO>>();
            childClasses.add(CriteriaPredicateDTO.class);
            childClasses.add(CriteriaPredicatePartDTO.class);
            childClasses.add(CriteriaResourceParamDTO.class);
            propertyBagDTO.setChildClassDTOs(childClasses);
            result = getGeneralMGR().findByPrimaryKey(dto, getSessionDTO(), propertyBagDTO);

        } catch (Exception e) {
            onExceptionMain(SourceMethod.refresh, e);
        } finally {
//            logger.logDuration(METHODNAME, start);
        }
        return result;
    }

    public List<CriteriaDataTemplateRelNodeDTO> getCriteriaDataTemplateRelNodeDTOs() {
        List<CriteriaDataTemplateRelNodeDTO> result = new ArrayList<CriteriaDataTemplateRelNodeDTO>();
        CriteriaDTO parentDTO = getParentDTO();
        if (parentDTO != null) {
            for (CriteriaDataTemplateRelDTO item : parentDTO.getCriteriaDataTemplateRelDTOs()) {
                result.addAll(item.getCriteriaDataTemplateRelNodeDTOs());
            }
        }
        return result;
    }

    public String getSortBy() {
        return criteriaPredicateMGR.getSortBy();
    }
}
