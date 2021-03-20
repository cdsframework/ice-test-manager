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
package org.cdsframework.section.cds.concept;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.OpenCdsConceptDTO;
import org.cdsframework.dto.OpenCdsConceptRelDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.lookup.OpenCdsConceptDTOList;
import org.cdsframework.util.FileUtils;
import org.cdsframework.util.StringUtils;
import org.cdsframework.util.cds.ImportUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class OpenCdsConceptMGR extends BaseModule<OpenCdsConceptDTO> {

    private static final long serialVersionUID = 8964205676703844792L;

    @Inject
    private OpenCdsConceptDTOList openCdsConceptDTOList;
    
    @Inject
    private OpenCdsConceptRelMGR openCdsConceptRelMGR;
    
//    @Inject
//    private CdsListDTOList cdsListDTOList;
    private OpenCdsConceptDTO exportOpenCdsConceptDTO;

    @Override
    protected void initialize() {
        setLazy(true);
        setBaseHeader("Concept");
        setInitialQueryClass("FindAll");
        setAssociatedList(openCdsConceptDTOList);
//        setAssociatedList(cdsListDTOList);
        setSaveImmediately(true);

        registerChild(OpenCdsConceptRelDTO.ByOpenCdsConceptId.class, openCdsConceptRelMGR);
        
    }

    @Override
    public void registerTabComponents() {
        getTabService().registeredUIComponent(0, this);
        getTabService().registeredUIComponent(0, openCdsConceptRelMGR);

    }
    
    @Override
    public void clearSearchMain(ActionEvent actionEvent) {
        String partId = (String) getSearchCriteriaDTO().getQueryMap().get("partId");
        String vaccineId = (String) getSearchCriteriaDTO().getQueryMap().get("vaccineId");
        String groupId = (String) getSearchCriteriaDTO().getQueryMap().get("groupId");
        super.clearSearchMain(actionEvent); //To change body of generated methods, choose Tools | Templates.
        if (!StringUtils.isEmpty(partId)) {
            getSearchCriteriaDTO().getQueryMap().put("partId", partId);
        }
        if (!StringUtils.isEmpty(vaccineId)) {
            getSearchCriteriaDTO().getQueryMap().put("vaccineId", vaccineId);
        }
        if (!StringUtils.isEmpty(groupId)) {
            getSearchCriteriaDTO().getQueryMap().put("groupId", groupId);
        }
    }
        

    public OpenCdsConceptDTO getExportOpenCdsConceptDTO() {
        return exportOpenCdsConceptDTO;
    }

    public void setExportOpenCdsConceptDTO(OpenCdsConceptDTO exportOpenCdsConceptDTO) {
        this.exportOpenCdsConceptDTO = exportOpenCdsConceptDTO;
    }

    public void importConcept(FileUploadEvent event) {
        try {
            byte[] payload = ImportUtils.getBase64ByteArrayPayloadFromFileUploadEvent(event);
            PropertyBagDTO propertyBagDTO = getNewPropertyBagDTO();
            propertyBagDTO.put("payload", payload);
            propertyBagDTO.setQueryClass("SimpleExchange");
            getGeneralMGR().importData(OpenCdsConceptDTO.class, getSessionDTO(), propertyBagDTO);
            performInitialSearch();
            getMessageMGR().displayInfoMessage("OpenCDS concept(s) successfully imported.");
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    public StreamedContent exportConcept() {
        final String METHODNAME = "exportConcept ";
        logger.logBegin(METHODNAME);
        StreamedContent conceptFile = null;
        logger.info(METHODNAME, "Selection: ", exportOpenCdsConceptDTO != null ? exportOpenCdsConceptDTO.getCode() : null);
        try {
            if (exportOpenCdsConceptDTO == null) {
                exportOpenCdsConceptDTO = new OpenCdsConceptDTO();
                exportOpenCdsConceptDTO.setCodeId("ALL");
            }
            PropertyBagDTO propertyBagDTO = getNewPropertyBagDTO();
            propertyBagDTO.setQueryClass("SimpleExchange");
            Map<String, byte[]> exportData = getGeneralMGR().exportData(exportOpenCdsConceptDTO, getSessionDTO(), propertyBagDTO);
            ByteArrayInputStream stream = new ByteArrayInputStream(FileUtils.getZipOfFiles(exportData));
            conceptFile = new DefaultStreamedContent(stream, "application/zip", String.format("opencds-concepts-%s.zip", new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Timestamp(new Date().getTime()))));
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logEnd(METHODNAME);
        }
        return conceptFile;
    }
}
