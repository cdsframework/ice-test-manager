/**
 * CAT ICE support plugin project.
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
package org.cdsframework.section.ice.series;

import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.IceSeriesDTO;
import org.cdsframework.dto.IceSeriesDoseDTO;
import org.cdsframework.dto.IceSeriesDoseVaccineRelDTO;
import org.cdsframework.dto.IceVaccineDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.util.StringUtils;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class IceSeriesDoseMGR extends BaseModule<IceSeriesDoseDTO> {

    private static final long serialVersionUID = 2737169079497708008L;

    @Override
    protected void initialize() {
        setBaseHeader("Dose");
        setSaveImmediately(true);
        setLazy(true);
    }

    @Override
    public void registerTabComponents() {
        getTabService().registeredUIComponent(0, this);
    }

    @Override
    public void clearSearchMain(ActionEvent actionEvent) {
        String seriesId = (String) getSearchCriteriaDTO().getQueryMap().get("series_id");
        String doseId = (String) getSearchCriteriaDTO().getQueryMap().get("dose_id");
        super.clearSearchMain(actionEvent);
        if (!StringUtils.isEmpty(seriesId)) {
            logger.info("clearSearchMain seriesId=", seriesId);
            getSearchCriteriaDTO().getQueryMap().put("series_id", seriesId);
        }
        if (!StringUtils.isEmpty(doseId)) {
            logger.info("clearSearchMain doseId=", doseId);
            getSearchCriteriaDTO().getQueryMap().put("dose_id", doseId);
        }
    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        BaseDTO selectedDTO = getSelectedDTO();
        String targetFieldName = getTargetFieldName();
        IceSeriesDoseDTO parentDTO = getParentDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);

        if (baseDTO instanceof IceVaccineDTO) {
            IceVaccineDTO iceVaccineDTO = (IceVaccineDTO) baseDTO;
            if (selectedDTO instanceof IceSeriesDoseVaccineRelDTO) {
                IceSeriesDoseVaccineRelDTO iceSeriesDoseVaccineRelDTO = (IceSeriesDoseVaccineRelDTO) selectedDTO;
                if ("iceVaccineDTO".equals(targetFieldName)) {
                    logger.info(METHODNAME, "IceVaccineDTO, IceSeriesDoseVaccineRelDTO, iceVaccineDTO");
                    iceSeriesDoseVaccineRelDTO.setIceVaccineDTO(iceVaccineDTO);
                }
            }
        }
        // Clear out selected DTO
        setSelectedDTO(null);
    }

    @Override
    protected void postAdd(ActionEvent actionEvent) throws Exception {
        IceSeriesDoseDTO parentDTO = getParentDTO();
        if (parentDTO != null) {
            parentDTO.setDoseNumber(getNextDoseNumber());
        }
    }

    private int getNextDoseNumber() {
        final String METHODNAME = "getNextDoseNumber ";
        int result = 1;
        BaseModule parentMGR = getParentMGR();
        if (parentMGR != null) {
            IceSeriesDTO parentDTO = (IceSeriesDTO) parentMGR.getParentDTO();
            if (parentDTO != null) {
                PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                propertyBagDTO.setQueryClass("NextDoseNumber");
                IceSeriesDoseDTO iceSeriesDoseDTO = new IceSeriesDoseDTO();
                iceSeriesDoseDTO.setSeriesId(parentDTO.getSeriesId());
                try {
                    Integer nextDoseNumber = getMts().getGeneralMGR().findObjectByQuery(iceSeriesDoseDTO, this.getSessionDTO(), Integer.class, propertyBagDTO);
                    logger.info(METHODNAME, "nextDoseNumber=", nextDoseNumber);
                    result = nextDoseNumber;
                } catch (Exception e) {
                    DefaultExceptionHandler.handleException(e, this.getClass());
                }
                int currentDoseListSize = parentDTO.getIceSeriesDoseDTOs().size() + 1;
                if (currentDoseListSize > result) {
                    result = currentDoseListSize;
                }
            }
        }
        return result;
    }
}
