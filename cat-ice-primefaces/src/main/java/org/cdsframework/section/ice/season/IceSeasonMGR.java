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
package org.cdsframework.section.ice.season;

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
import org.cdsframework.dto.IceSeasonDTO;
import org.cdsframework.dto.IceSeasonVersionRelDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
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
public class IceSeasonMGR extends BaseModule<IceSeasonDTO> {

    private static final long serialVersionUID = -5245246531625426021L;
    private IceSeasonDTO exportIceSeasonDTO;

    @Override
    protected void initialize() {
        setLazy(true);
        setBaseHeader("Season");
        setInitialQueryClass("FindAll");
        addOnRowSelectChildClassDTO(IceSeasonVersionRelDTO.class);
    }

    @Override
    public void clearSearchMain(ActionEvent actionEvent) {
        String versionId = (String) getSearchCriteriaDTO().getQueryMap().get("version_id");
        super.clearSearchMain(actionEvent);
        if (!StringUtils.isEmpty(versionId)) {
            logger.info("clearSearchMain versionId=", versionId);
            getSearchCriteriaDTO().getQueryMap().put("version_id", versionId);
        }
    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        BaseDTO selectedDTO = getSelectedDTO();
        String targetFieldName = getTargetFieldName();
        IceSeasonDTO parentDTO = getParentDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);

        if (baseDTO instanceof IceSeasonDTO) {
            IceSeasonDTO iceSeasonDTO = (IceSeasonDTO) baseDTO;
            if ("exportIceSeasonDTO".equals(targetFieldName)) {
                logger.info(METHODNAME, "IceSeasonDTO, exportIceSeasonDTO");
                setExportIceSeasonDTO(iceSeasonDTO);
            }
        } else if (baseDTO instanceof IceVaccineGroupDTO) {
            IceVaccineGroupDTO iceVaccineGroupDTO = (IceVaccineGroupDTO) baseDTO;
             if ("searchCriteriaVaccineGroup".equals(targetFieldName)) {
                IceSeasonDTO iceSeasonDTO = (IceSeasonDTO) selectedDTO;
                iceSeasonDTO.getQueryMap().put("vaccineGroupId", iceVaccineGroupDTO.getGroupId());
                iceSeasonDTO.getQueryMap().put("vaccineGroup", iceVaccineGroupDTO);
            }
        }
        // Clear out selected DTO
        setSelectedDTO(null);
    }

    public IceSeasonDTO getExportIceSeasonDTO() {
        return exportIceSeasonDTO;
    }

    public void setExportIceSeasonDTO(IceSeasonDTO exportIceSeasonDTO) {
        this.exportIceSeasonDTO = exportIceSeasonDTO;
    }

    public StreamedContent exportIceSeason() {
        final String METHODNAME = "exportIceSeason ";
        StreamedContent iceSeriesFile = null;
        logger.info(METHODNAME, "Selection: ", exportIceSeasonDTO != null ? exportIceSeasonDTO.getName() : null);
        try {
            if (exportIceSeasonDTO == null) {
                exportIceSeasonDTO = new IceSeasonDTO();
                exportIceSeasonDTO.setSeasonId("ALL");
            }
            PropertyBagDTO propertyBagDTO = getNewPropertyBagDTO();
            propertyBagDTO.setQueryClass("SimpleExchange");
            Map<String, byte[]> exportData = getGeneralMGR().exportData(exportIceSeasonDTO, getSessionDTO(), propertyBagDTO);
            ByteArrayInputStream stream = new ByteArrayInputStream(FileUtils.getZipOfFiles(exportData));
            iceSeriesFile = new DefaultStreamedContent(stream, "application/zip", String.format("ice-season-%s.zip", new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Timestamp(new Date().getTime()))));
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return iceSeriesFile;
    }

}
