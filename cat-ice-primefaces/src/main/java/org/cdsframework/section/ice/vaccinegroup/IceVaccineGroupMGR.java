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
package org.cdsframework.section.ice.vaccinegroup;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsListItemDTO;
import org.cdsframework.dto.CdsVersionDTO;
import org.cdsframework.dto.IceDiseaseDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
import org.cdsframework.dto.IceVaccineGroupDiseaseRelDTO;
import org.cdsframework.dto.IceVaccineGroupVersionRelDTO;
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
public class IceVaccineGroupMGR extends BaseModule<IceVaccineGroupDTO> {

    private static final long serialVersionUID = 8647757072207380309L;


    private IceVaccineGroupDTO exportVaccineGroup;

    @Override
    protected void initialize() {
        setLazy(true);
        setSaveImmediately(true);
        setBaseHeader("Vaccine Group");
        setInitialQueryClass("FindAll");
    }

    @Override
    public void registerTabComponents() {
        getTabService().registeredUIComponent(0, this);
    }

    @Override
    public void clearSearchMain(ActionEvent actionEvent) {
        String versionId = (String) getSearchCriteriaDTO().getQueryMap().get("version_id");
        String testId = (String) getSearchCriteriaDTO().getQueryMap().get("test_id");
        String componentId = (String) getSearchCriteriaDTO().getQueryMap().get("component_id");
        String vaccineId = (String) getSearchCriteriaDTO().getQueryMap().get("vaccine_id");
        String seriesId = (String) getSearchCriteriaDTO().getQueryMap().get("series_id");
        logger.info("clearSearchMain - versionId=", versionId);
        logger.info("clearSearchMain - testId=", testId);
        logger.info("clearSearchMain - componentId=", componentId);
        logger.info("clearSearchMain - vaccineId=", vaccineId);
        logger.info("clearSearchMain - seriesId=", seriesId);
        super.clearSearchMain(actionEvent);
        if (!StringUtils.isEmpty(versionId)) {
            getSearchCriteriaDTO().getQueryMap().put("version_id", versionId);
        }
        if (!StringUtils.isEmpty(testId)) {
            getSearchCriteriaDTO().getQueryMap().put("test_id", testId);
        }
        if (!StringUtils.isEmpty(componentId)) {
            getSearchCriteriaDTO().getQueryMap().put("component_id", componentId);
        }
        if (!StringUtils.isEmpty(vaccineId)) {
            getSearchCriteriaDTO().getQueryMap().put("vaccine_id", vaccineId);
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
        IceVaccineGroupDTO parentDTO = getParentDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);

        if (baseDTO instanceof CdsListItemDTO) {
            CdsListItemDTO cdsListItemDTO = (CdsListItemDTO) baseDTO;
            CdsCodeDTO cdsCodeDTO = cdsListItemDTO.getCdsCodeDTO();
            if (parentDTO != null) {
                if ("vaccineGroup".equalsIgnoreCase(targetFieldName)) {
                    parentDTO.setVaccineGroup(cdsCodeDTO);
                }
            }
        } else if (baseDTO instanceof OpenCdsConceptDTO) {
            OpenCdsConceptDTO openCdsConceptDTO = (OpenCdsConceptDTO) baseDTO;
            if (parentDTO != null) {
                parentDTO.setPrimaryOpenCdsConceptDTO(openCdsConceptDTO);
            }
        } else if (baseDTO instanceof CdsVersionDTO) {
            CdsVersionDTO cdsVersionDTO = (CdsVersionDTO) baseDTO;
            if (selectedDTO instanceof IceVaccineGroupVersionRelDTO) {
                IceVaccineGroupVersionRelDTO iceVaccineGroupVersionRelDTO = (IceVaccineGroupVersionRelDTO) selectedDTO;
                iceVaccineGroupVersionRelDTO.setCdsVersionDTO(cdsVersionDTO);
            } else if (selectedDTO instanceof IceVaccineGroupDTO) {
                if ("versionSearch".equalsIgnoreCase(targetFieldName)) {
                    IceVaccineGroupDTO iceVaccineGroupDTO = (IceVaccineGroupDTO) selectedDTO;
                    iceVaccineGroupDTO.getQueryMap().put("version_id", cdsVersionDTO.getVersionId());
                    iceVaccineGroupDTO.getQueryMap().put("version", cdsVersionDTO);
                }
            }
        } else if (baseDTO instanceof IceDiseaseDTO && selectedDTO instanceof IceVaccineGroupDiseaseRelDTO) {
            IceDiseaseDTO iceDiseaseDTO = (IceDiseaseDTO) baseDTO;
            IceVaccineGroupDiseaseRelDTO iceVaccineGroupDiseaseRelDTO = (IceVaccineGroupDiseaseRelDTO) selectedDTO;
            iceVaccineGroupDiseaseRelDTO.setIceDiseaseDTO(iceDiseaseDTO);
        } else if (baseDTO instanceof IceVaccineGroupDTO) {
            IceVaccineGroupDTO iceVaccineGroupDTO = (IceVaccineGroupDTO) baseDTO;
            if ("exportVaccineGroup".equalsIgnoreCase(targetFieldName)) {
                setExportVaccineGroup(iceVaccineGroupDTO);
            }
        }
        // Clear out selected DTO
        setSelectedDTO(null);
    }

    @Override
    public void resetField(BaseDTO baseDTO, String fieldName) {
        super.resetField(baseDTO, fieldName);
        if ("primaryOpenCdsConceptDTO".equalsIgnoreCase(fieldName)) {
            if (baseDTO instanceof IceVaccineGroupDTO) {
                IceVaccineGroupDTO iceVaccineGroupDTO = (IceVaccineGroupDTO) baseDTO;
                iceVaccineGroupDTO.setPrimaryOpenCdsConceptDTO(null);
            }
        }
    }

    public IceVaccineGroupDTO getExportVaccineGroup() {
        return exportVaccineGroup;
    }

    public void setExportVaccineGroup(IceVaccineGroupDTO exportVaccineGroup) {
        this.exportVaccineGroup = exportVaccineGroup;
    }

    public StreamedContent exportVaccineGroup() {
        final String METHODNAME = "exportVaccineGroup ";
        logger.logBegin(METHODNAME);
        StreamedContent vaccineGroupFile = null;
        logger.info(METHODNAME, "Selection: ", exportVaccineGroup != null ? exportVaccineGroup.getVaccineGroupName() : null);
        try {
            if (exportVaccineGroup == null) {
                exportVaccineGroup = new IceVaccineGroupDTO();
                CdsCodeDTO vaccineGroup = new CdsCodeDTO();
                vaccineGroup.setCodeId("ALL");
                exportVaccineGroup.setVaccineGroup(vaccineGroup);
            }
            PropertyBagDTO propertyBagDTO = getNewPropertyBagDTO();
            propertyBagDTO.setQueryClass("SimpleExchange");
            Map<String, byte[]> exportData = getGeneralMGR().exportData(exportVaccineGroup, getSessionDTO(), propertyBagDTO);
            ByteArrayInputStream stream = new ByteArrayInputStream(FileUtils.getZipOfFiles(exportData));
            vaccineGroupFile = new DefaultStreamedContent(stream, "application/zip", String.format("vaccine-groups-%s.zip", new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Timestamp(new Date().getTime()))));
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logEnd(METHODNAME);
        }
        return vaccineGroupFile;
    }
}
