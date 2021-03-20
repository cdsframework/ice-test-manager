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

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsVersionDTO;
import org.cdsframework.dto.IceSeasonDTO;
import org.cdsframework.dto.IceSeriesDTO;
import org.cdsframework.dto.IceSeriesDoseDTO;
import org.cdsframework.dto.IceSeriesDoseIntervalDTO;
import org.cdsframework.dto.IceSeriesSeasonRelDTO;
import org.cdsframework.dto.IceSeriesVersionRelDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.util.FileUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class IceSeriesMGR extends BaseModule<IceSeriesDTO> {

    private static final long serialVersionUID = 4049292384633346654L;

    @Inject
    private IceSeriesDoseMGR iceSeriesDoseMGR;
    @Inject
    private IceSeriesDoseIntervalMGR iceSeriesDoseIntervalMGR;
    private IceSeriesDTO exportIceSeries;

    @Override
    protected void initialize() {
        setLazy(true);
        setBaseHeader("Series");
        setInitialQueryClass("FindAll");
        registerChild(IceSeriesDoseDTO.BySeriesId.class, iceSeriesDoseMGR);
        registerChild(IceSeriesDoseIntervalDTO.BySeriesId.class, iceSeriesDoseIntervalMGR);
    }

    @Override
    public void registerTabComponents() {
        getTabService().registeredUIComponent(0, this);
        getTabService().registeredUIComponent(0, iceSeriesDoseMGR);
        getTabService().registeredUIComponent(0, iceSeriesDoseIntervalMGR);
    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        BaseDTO selectedDTO = getSelectedDTO();
        String targetFieldName = getTargetFieldName();
        IceSeriesDTO parentDTO = getParentDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);

        if (baseDTO instanceof IceSeasonDTO) {
            IceSeasonDTO iceSeasonDTO = (IceSeasonDTO) baseDTO;
            if ("iceSeasonDTO".equals(targetFieldName)) {
                logger.info(METHODNAME, "IceSeasonDTO, iceSeasonDTO");
                IceSeriesSeasonRelDTO iceSeriesSeasonRelDTO = (IceSeriesSeasonRelDTO) selectedDTO;
                iceSeriesSeasonRelDTO.setIceSeasonDTO(iceSeasonDTO);
            }
        } else if (baseDTO instanceof IceVaccineGroupDTO) {
            IceVaccineGroupDTO iceVaccineGroupDTO = (IceVaccineGroupDTO) baseDTO;
            if ("iceVaccineGroupDTO".equals(targetFieldName)) {
                logger.info(METHODNAME, "IceVaccineGroupDTO, iceVaccineGroupDTO");
                parentDTO.setIceVaccineGroupDTO(iceVaccineGroupDTO);
            } else if ("searchCriteriaVaccineGroup".equals(targetFieldName)) {
                IceSeriesDTO iceSeriesDTO = (IceSeriesDTO) selectedDTO;
                iceSeriesDTO.getQueryMap().put("vaccineGroupId", iceVaccineGroupDTO.getGroupId());
                iceSeriesDTO.getQueryMap().put("vaccineGroup", iceVaccineGroupDTO);
            }
        } else if (baseDTO instanceof IceSeriesDTO) {
            IceSeriesDTO iceSeriesDTO = (IceSeriesDTO) baseDTO;
            if ("exportIceSeries".equals(targetFieldName)) {
                logger.info(METHODNAME, "IceSeriesDTO, exportIceSeries");
                setExportIceSeries(iceSeriesDTO);
            }
        } else if (baseDTO instanceof CdsVersionDTO) {
            CdsVersionDTO cdsVersionDTO = (CdsVersionDTO) baseDTO;
            if (selectedDTO instanceof IceSeriesVersionRelDTO) {
                IceSeriesVersionRelDTO iceSeriesVersionRelDTO = (IceSeriesVersionRelDTO) selectedDTO;
                iceSeriesVersionRelDTO.setCdsVersionDTO(cdsVersionDTO);
            }
        }
        // Clear out selected DTO
        setSelectedDTO(null);
    }
//
//    public List<IceSeriesDoseDTO> getFromDoseList() {
//        List<IceSeriesDoseDTO> result = new ArrayList<IceSeriesDoseDTO>();
//        IceSeriesDTO parentDTO = getParentDTO();
//        if (parentDTO != null) {
//            for (IceSeriesDoseDTO item : parentDTO.getIceSeriesDoseDTOs()) {
//                if (!item.isNew()) {
//                    result.add(item);
//                }
//            }
//        }
//        return result;
//    }
//
//    public List<IceSeriesDoseDTO> getToDoseList(String fromDoseId) {
//        final String METHODNAME = "getToDoseList ";
//        logger.info(METHODNAME, "fromDoseId: ", fromDoseId);
//        List<IceSeriesDoseDTO> result = new ArrayList<IceSeriesDoseDTO>();
//        if (fromDoseId != null) {
//            IceSeriesDTO parentDTO = getParentDTO();
//            if (parentDTO != null) {
//                IceSeriesDoseDTO fromIceSeriesDoseDTO = null;
//                for (IceSeriesDoseDTO item : parentDTO.getIceSeriesDoseDTOs()) {
//                    logger.info(METHODNAME, "Comparing: ", item, " to: ", fromDoseId);
//                    if (fromDoseId.equals(item.getDoseId())) {
//                        fromIceSeriesDoseDTO = item;
//                        break;
//                    }
//                }
//                logger.info(METHODNAME, "GOT FROM DOSE: ", fromIceSeriesDoseDTO);
//                if (fromIceSeriesDoseDTO != null && fromIceSeriesDoseDTO.getDoseNumber() != null) {
//                    for (IceSeriesDoseDTO item : parentDTO.getIceSeriesDoseDTOs()) {
//                        if ((!item.isNew() && item.getDoseNumber().intValue() > fromIceSeriesDoseDTO.getDoseNumber().intValue())
//                                || (fromIceSeriesDoseDTO.getDoseNumber().intValue() == 1 && !item.isNew() && item.getDoseNumber().intValue() == 1)) {
//                            result.add(item);
//                        }
//                    }
//                }
//            } else {
//                logger.warn(METHODNAME, "parentDTO is null!");
//            }
//        } else {
//            logger.warn(METHODNAME, "fromDoseId is null!");
//        }
//        return result;
//    }

    public IceSeriesDTO getExportIceSeries() {
        return exportIceSeries;
    }

    public void setExportIceSeries(IceSeriesDTO exportIceSeries) {
        this.exportIceSeries = exportIceSeries;
    }

    public StreamedContent exportIceSeries() {
        final String METHODNAME = "exportIceSeries ";
        StreamedContent iceSeriesFile = null;
        logger.info(METHODNAME, "Selection: ", exportIceSeries != null ? exportIceSeries.getName() : null);
        try {
            if (exportIceSeries == null) {
                exportIceSeries = new IceSeriesDTO();
                exportIceSeries.setSeriesId("ALL");
            }
            PropertyBagDTO propertyBagDTO = getNewPropertyBagDTO();
            propertyBagDTO.setQueryClass("SimpleExchange");
            Map<String, byte[]> exportData = getGeneralMGR().exportData(exportIceSeries, getSessionDTO(), propertyBagDTO);
            ByteArrayInputStream stream = new ByteArrayInputStream(FileUtils.getZipOfFiles(exportData));
            iceSeriesFile = new DefaultStreamedContent(stream, "application/zip", String.format("ice-series-%s.zip", new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Timestamp(new Date().getTime()))));
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return iceSeriesFile;
    }
}
