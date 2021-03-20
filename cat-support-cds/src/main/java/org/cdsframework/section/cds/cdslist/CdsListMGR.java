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
package org.cdsframework.section.cds.cdslist;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsCodeSystemDTO;
import org.cdsframework.dto.CdsVersionDTO;
import org.cdsframework.dto.CdsListDTO;
import org.cdsframework.dto.CdsListItemDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.ValueSetDTO;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.lookup.CdsCodeSystemDTOList;
import org.cdsframework.lookup.CdsListDTOList;
import org.cdsframework.util.FileUtils;
import org.cdsframework.util.cds.ImportUtils;
import org.cdsframework.util.enumeration.PrePost;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class CdsListMGR extends BaseModule<CdsListDTO> {

    private static final long serialVersionUID = -4232229365993059707L;

    @Inject
    private CdsListDTOList cdsListDTOList;
    @Inject
    private CdsCodeSystemDTOList cdsCodeSystemDTOList;
    @Inject
    private CdsListItemMGR cdsListItemMGR;
    private CdsListDTO exportCdsListDTO;
    private CdsVersionDTO exportCdsVersionDTO;
    private CdsListItemDTO sampleCdsListItemDTO;
    private CdsListDTO sampleCdsListDTO;
    private CdsVersionDTO sampleCdsVersionDTO;

    @Override
    protected void initialize() {
        setLazy(true);
        setBaseHeader("List");
        //addOnRowSelectChildClassDTO(CdsListItemDTO.class);
        //addOnRowSelectChildClassDTO(CdsListVersionRelDTO.class);
        registerChild(CdsListItemDTO.ByCdsListId.class, cdsListItemMGR);
        setAssociatedList(cdsListDTOList);
//        setAssociatedList(cdsCodeSystemDTOList);
        setInitialQueryClass("FindAll");
        setSaveImmediately(true);
    }

    @Override
    public void registerTabComponents() {
        final String METHODNAME = "registerTabLazyLoadComponents ";
        logger.info(METHODNAME);
        // Register the Components
//        getTabLazyLoadService().registeredUIComponent(listVersionsTab, CdsVersionDTO.ByBusinessScopeId.class);
        getTabService().registeredUIComponent(1, this);
        getTabService().registeredUIComponent(2, cdsListItemMGR);
    }    

    @Override
    public void resetField(BaseDTO baseDTO, String fieldName) {
        super.resetField(baseDTO, fieldName); //To change body of generated methods, choose Tools | Templates.
        final String METHODNAME = "resetField ";

        if (baseDTO instanceof CdsListDTO) {
            CdsListDTO cdsListDTO = (CdsListDTO) baseDTO;
            if (fieldName.equalsIgnoreCase("cdsCodeSystemDTO")) {
                cdsListDTO.setCdsCodeSystemDTO(null);
            }
            else if (fieldName.equalsIgnoreCase("valueSetDTO")) {
                cdsListDTO.setValueSetDTO(null);
            }

        }

    }
    
    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);        
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        logger.info(METHODNAME, "baseDTO=", (baseDTO != null ? baseDTO.getClass().getSimpleName() : baseDTO));
        BaseDTO selectedDTO = getSelectedDTO();
        logger.info(METHODNAME, "selectedDTO=", (selectedDTO != null ? selectedDTO.getClass().getSimpleName() : selectedDTO));

        if (baseDTO instanceof CdsCodeSystemDTO) {
            CdsCodeSystemDTO cdsCodeSystemDTO = (CdsCodeSystemDTO) baseDTO;
            if (selectedDTO instanceof CdsListDTO) {
                logger.info(METHODNAME, "calling setCdsCodeSystemDTO on selectedDTO");
                CdsListDTO cdsListDTO = (CdsListDTO) selectedDTO;
                cdsListDTO.setCdsCodeSystemDTO(cdsCodeSystemDTO);
            }
        }
        else if (baseDTO instanceof ValueSetDTO) {
            ValueSetDTO valueSetDTO = (ValueSetDTO) baseDTO;
            if (selectedDTO instanceof CdsListDTO) {
                logger.info(METHODNAME, "calling setValueSetDTO on selectedDTO");
                CdsListDTO cdsListDTO = (CdsListDTO) selectedDTO;
                cdsListDTO.setValueSetDTO(valueSetDTO);
            }
        }
        // Clear out selected DTO
        setSelectedDTO(null);
    }            
    
    public StreamedContent exportCdsList() {
        final String METHODNAME = "exportCdsList ";
        logger.logBegin(METHODNAME);
        StreamedContent cdsListFile = null;
        logger.info(METHODNAME, "Selection: ", exportCdsListDTO != null ? exportCdsListDTO.getName() : "ALL");
        try {
            if (exportCdsListDTO == null) {
                exportCdsListDTO = new CdsListDTO();
                exportCdsListDTO.setListId("ALL");
            }
            PropertyBagDTO propertyBagDTO = getNewPropertyBagDTO();
            propertyBagDTO.setQueryClass("SimpleExchange");
            propertyBagDTO.put("cdsVersionDTO", exportCdsVersionDTO);
            Map<String, byte[]> exportData = getGeneralMGR().exportData(exportCdsListDTO, getSessionDTO(), propertyBagDTO);
            ByteArrayInputStream stream = new ByteArrayInputStream(FileUtils.getZipOfFiles(exportData));
            cdsListFile = new DefaultStreamedContent(stream, "application/zip", String.format("cds_list-%s.zip", new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Timestamp(new Date().getTime()))));
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logEnd(METHODNAME);
        }
        return cdsListFile;
    }

    public void importCdsList(FileUploadEvent event) {
        try {
            byte[] payload = ImportUtils.getBase64ByteArrayPayloadFromFileUploadEvent(event);

            PropertyBagDTO propertyBagDTO = getNewPropertyBagDTO();
            propertyBagDTO.put("payload", payload);
            propertyBagDTO.setQueryClass("SimpleExchange");
            getGeneralMGR().importData(CdsListDTO.class, getSessionDTO(), propertyBagDTO);
            cdsCodeSystemDTOList.setDirty(true);
            cdsListDTOList.setDirty(true);
            performInitialSearch();
            getMessageMGR().displayInfoMessage("CDS list(s) successfully imported.");
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    /**
     * Get the value of sampleCdsVersionDTO
     *
     * @return the value of sampleCdsVersionDTO
     */
    public CdsVersionDTO getSampleCdsVersionDTO() {
        return sampleCdsVersionDTO;
    }

    /**
     * Set the value of sampleCdsVersionDTO
     *
     * @param sampleCdsVersionDTO new value of sampleCdsVersionDTO
     */
    public void setSampleCdsVersionDTO(CdsVersionDTO sampleCdsVersionDTO) {
        this.sampleCdsVersionDTO = sampleCdsVersionDTO;
    }

    /**
     * Get the value of sampleCdsListDTO
     *
     * @return the value of sampleCdsListDTO
     */
    public CdsListDTO getSampleCdsListDTO() {
        return sampleCdsListDTO;
    }

    /**
     * Set the value of sampleCdsListDTO
     *
     * @param sampleCdsListDTO new value of sampleCdsListDTO
     */
    public void setSampleCdsListDTO(CdsListDTO sampleCdsListDTO) {
        this.sampleCdsListDTO = sampleCdsListDTO;
    }

    /**
     * Get the value of sampleCdsListItemDTO
     *
     * @return the value of sampleCdsListItemDTO
     */
    public CdsListItemDTO getSampleCdsListItemDTO() {
        return sampleCdsListItemDTO;
    }

    /**
     * Set the value of sampleCdsListItemDTO
     *
     * @param sampleCdsListItemDTO new value of sampleCdsListItemDTO
     */
    public void setSampleCdsListItemDTO(CdsListItemDTO sampleCdsListItemDTO) {
        this.sampleCdsListItemDTO = sampleCdsListItemDTO;
    }

    public CdsListDTO getExportCdsListDTO() {
        return exportCdsListDTO;
    }

    public void setExportCdsListDTO(CdsListDTO exportCdsListDTO) {
        this.exportCdsListDTO = exportCdsListDTO;
        this.exportCdsVersionDTO = null;
    }

    /**
     * Get the value of exportCdsVersionDTO
     *
     * @return the value of exportCdsVersionDTO
     */
    public CdsVersionDTO getExportCdsVersionDTO() {
        return exportCdsVersionDTO;
    }

    /**
     * Set the value of exportCdsVersionDTO
     *
     * @param exportCdsVersionDTO new value of exportCdsVersionDTO
     */
    public void setExportCdsVersionDTO(CdsVersionDTO exportCdsVersionDTO) {
        this.exportCdsVersionDTO = exportCdsVersionDTO;
    }

    @Override
    public void prePostOperation(String queryClass, BaseDTO baseDTO, PrePost prePost, boolean status) {
        super.prePostOperation(queryClass, baseDTO, prePost, status);
        switch (prePost) {
            case PostSave:
                if (status) {
                    getTabByIndex(2).setLoaded(false);
                }
                break;
        }
    }

}
