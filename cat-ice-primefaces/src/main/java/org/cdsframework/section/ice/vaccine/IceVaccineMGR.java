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
package org.cdsframework.section.ice.vaccine;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsListItemDTO;
import org.cdsframework.dto.CdsVersionDTO;
import org.cdsframework.dto.IceVaccineComponentDTO;
import org.cdsframework.dto.IceVaccineComponentRelDTO;
import org.cdsframework.dto.IceVaccineDTO;
import org.cdsframework.dto.IceVaccineVersionRelDTO;
import org.cdsframework.dto.OpenCdsConceptDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.util.FileUtils;
import org.cdsframework.util.StringUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class IceVaccineMGR extends BaseModule<IceVaccineDTO> {

    private static final long serialVersionUID = -7646122592385078392L;
    private IceVaccineDTO exportVaccine;

    @Override
    protected void initialize() {
        setLazy(true);
        setBaseHeader("Vaccine");
        setSaveImmediately(true);
        setInitialQueryClass("FindAll");
    }

    @Override
    public void registerTabComponents() {
        getTabService().registeredUIComponent(0, this);
    }

    @Override
    public void clearSearchMain(ActionEvent actionEvent) {
        String testId = (String) getSearchCriteriaDTO().getQueryMap().get("test_id");
        String seriesId = (String) getSearchCriteriaDTO().getQueryMap().get("series_id");
        super.clearSearchMain(actionEvent); //To change body of generated methods, choose Tools | Templates.
        if (!StringUtils.isEmpty(testId)) {
            getSearchCriteriaDTO().getQueryMap().put("test_id", testId);
        }
        if (!StringUtils.isEmpty(seriesId)) {
            getSearchCriteriaDTO().getQueryMap().put("series_id", seriesId);
        }
    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        BaseDTO selectedDTO = getSelectedDTO();
        String targetFieldName = getTargetFieldName();
        IceVaccineDTO parentDTO = getParentDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);

        if (baseDTO instanceof CdsListItemDTO) {
            CdsListItemDTO cdsListItemDTO = (CdsListItemDTO) baseDTO;
            CdsCodeDTO cdsCodeDTO = cdsListItemDTO.getCdsCodeDTO();
            if (parentDTO != null) {
                if ("vaccine".equalsIgnoreCase(targetFieldName)) {
                    parentDTO.setVaccine(cdsCodeDTO);
                } else if ("manufacturerCode".equalsIgnoreCase(targetFieldName)) {
                    parentDTO.setManufacturerCode(cdsCodeDTO);
                } else if ("vaccineGroup".equalsIgnoreCase(targetFieldName)) {
                    if (selectedDTO instanceof IceVaccineDTO) {
                        IceVaccineDTO iceVaccineDTO = (IceVaccineDTO) selectedDTO;
                        iceVaccineDTO.getQueryMap().put("group_id", cdsListItemDTO.getRefId());
                        iceVaccineDTO.getQueryMap().put("group", cdsListItemDTO);
                    }
                }
            }
        } else if (baseDTO instanceof IceVaccineComponentDTO) {
            IceVaccineComponentDTO iceVaccineComponentDTO = (IceVaccineComponentDTO) baseDTO;
            if (selectedDTO instanceof IceVaccineComponentRelDTO) {
                IceVaccineComponentRelDTO iceVaccineComponentRelDTO = (IceVaccineComponentRelDTO) selectedDTO;
                iceVaccineComponentRelDTO.setIceVaccineComponentDTO(iceVaccineComponentDTO);
            }
        } else if (baseDTO instanceof OpenCdsConceptDTO) {
            OpenCdsConceptDTO openCdsConceptDTO = (OpenCdsConceptDTO) baseDTO;
            if (parentDTO != null) {
                parentDTO.setPrimaryOpenCdsConceptDTO(openCdsConceptDTO);
            }
        } else if (baseDTO instanceof CdsVersionDTO) {
            CdsVersionDTO cdsVersionDTO = (CdsVersionDTO) baseDTO;
            if (selectedDTO instanceof IceVaccineVersionRelDTO) {
                IceVaccineVersionRelDTO iceVaccineVersionRelDTO = (IceVaccineVersionRelDTO) selectedDTO;
                iceVaccineVersionRelDTO.setCdsVersionDTO(cdsVersionDTO);
            } else if (selectedDTO instanceof IceVaccineDTO) {
                if ("versionSearch".equalsIgnoreCase(targetFieldName)) {
                    IceVaccineDTO iceVaccineDTO = (IceVaccineDTO) selectedDTO;
                    iceVaccineDTO.getQueryMap().put("version_id", cdsVersionDTO.getVersionId());
                    iceVaccineDTO.getQueryMap().put("version", cdsVersionDTO);
                }
            }
        } else if (baseDTO instanceof IceVaccineDTO) {
            IceVaccineDTO iceVaccineDTO = (IceVaccineDTO) baseDTO;
            if ("exportVaccine".equalsIgnoreCase(targetFieldName)) {
                setExportVaccine(iceVaccineDTO);
            }
        }

        // Clear out selected DTO
        setSelectedDTO(null);
    }

    @Override
    public void resetField(BaseDTO baseDTO, String fieldName) {
        super.resetField(baseDTO, fieldName);
        if ("manufacturerCode".equalsIgnoreCase(fieldName)) {
            if (baseDTO instanceof IceVaccineDTO) {
                IceVaccineDTO iceVaccineDTO = (IceVaccineDTO) baseDTO;
                iceVaccineDTO.setManufacturerCode(null);
            }
        } else if ("primaryOpenCdsConceptDTO".equalsIgnoreCase(fieldName)) {
            if (baseDTO instanceof IceVaccineDTO) {
                IceVaccineDTO iceVaccineDTO = (IceVaccineDTO) baseDTO;
                iceVaccineDTO.setPrimaryOpenCdsConceptDTO(null);
            }
        }
    }

    public IceVaccineDTO getExportVaccine() {
        return exportVaccine;
    }

    public void setExportVaccine(IceVaccineDTO exportVaccine) {
        this.exportVaccine = exportVaccine;
    }

    public StreamedContent exportVaccine() {
        final String METHODNAME = "exportVaccine ";
        logger.logBegin(METHODNAME);
        StreamedContent vaccineFile = null;
        logger.info(METHODNAME, "Selection: ", exportVaccine != null ? exportVaccine.getVaccineName() : null);
        try {
            if (exportVaccine == null) {
                exportVaccine = new IceVaccineDTO();
                CdsCodeDTO vaccine = new CdsCodeDTO();
                vaccine.setCodeId("ALL");
                exportVaccine.setVaccine(vaccine);
            }
            PropertyBagDTO propertyBagDTO = getNewPropertyBagDTO();
            propertyBagDTO.setQueryClass("SimpleExchange");
            Map<String, byte[]> exportData = getGeneralMGR().exportData(exportVaccine, getSessionDTO(), propertyBagDTO);
            ByteArrayInputStream stream = new ByteArrayInputStream(FileUtils.getZipOfFiles(exportData));
            vaccineFile = new DefaultStreamedContent(stream, "application/zip", String.format("vaccines-%s.zip", new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Timestamp(new Date().getTime()))));
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logEnd(METHODNAME);
        }
        return vaccineFile;
    }
//
//    public List<CdsCodeDTO> getAvailableVaccineCodes() {
//        List<CdsCodeDTO> result = new ArrayList<CdsCodeDTO>();
//        for (IceVaccineDTO item : getDataTableMGR().getDtoList()) {
//            if (item != null
//                    && item.getVaccine() != null
//                    && item.getVaccine().getCodeId() != null
//                    && !item.getVaccine().getCodeId().equals(getParentDTO().getVaccine().getCodeId())) {
//                result.add(item.getVaccine());
//            }
//        }
//        return result;
//    }
//
//    public List<CdsCodeDTO> getOtherExistingVaccineCodes() {
//        List<CdsCodeDTO> result = new ArrayList<CdsCodeDTO>();
//        for (IceVaccineDTO item : iceVaccineDTOList.getAll()) {
//            if (item != null
//                    && item.getVaccine() != null
//                    && item.getVaccine().getCodeId() != null
//                    && !item.getVaccine().getCodeId().equals(getParentDTO().getVaccine().getCodeId())) {
//                result.add(item.getVaccine());
//            }
//        }
//        return result;
//    }
}
