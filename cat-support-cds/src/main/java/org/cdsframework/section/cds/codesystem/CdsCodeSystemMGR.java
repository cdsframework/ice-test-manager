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
package org.cdsframework.section.cds.codesystem;

import org.cdsframework.section.cds.code.CdsCodeMGR;
import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsCodeSystemDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.lookup.CdsCodeSystemDTOList;
import org.cdsframework.util.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class CdsCodeSystemMGR extends BaseModule<CdsCodeSystemDTO> {

    private static final long serialVersionUID = -9002826016953762935L;

    @Inject
    private CdsCodeMGR cdsCodeMGR;
    @Inject
    private CdsCodeSystemDTOList cdsCodeSystemDTOList;
//    @Inject
//    private ValueSetDTOList valueSetDTOList;
//    @Inject
//    private OpenCdsConceptDTOList openCdsConceptDTOList;
//    @Inject
//    private CdsListDTOList cdsListDTOList;
    private CdsCodeSystemDTO exportCodeSystem;

    @Override
    protected void initialize() {
        setLazy(true);
        setBaseHeader("Code System");
        setInitialQueryClass("FindAll");
        registerChild(CdsCodeDTO.ByCodeSystemId.class, cdsCodeMGR);
        setAssociatedList(cdsCodeSystemDTOList);
//        setAssociatedList(cdsListDTOList);
//        setAssociatedList(valueSetDTOList);
//        setAssociatedList(openCdsConceptDTOList);
        //addOnRowSelectChildClassDTO(CdsCodeDTO.class);
        //addOnRowSelectChildClassDTO(CdsCodeOpenCdsConceptRelDTO.class);
        setSaveImmediately(true);
    }
    
    @Override
    public void registerTabComponents() {
        final String METHODNAME = "registerTabLazyLoadComponents ";
        logger.info(METHODNAME);
        // Register the Components
        getTabService().registeredUIComponent(0, CdsCodeDTO.ByCodeSystemId.class);
    }

    public CdsCodeSystemDTO getExportCodeSystem() {
        return exportCodeSystem;
    }

    public void setExportCodeSystem(CdsCodeSystemDTO exportCodeSystem) {
        this.exportCodeSystem = exportCodeSystem;
    }

    public StreamedContent exportCodeSystem() {
        final String METHODNAME = "exportCodeSystem ";
        logger.logBegin(METHODNAME);
        StreamedContent codeSystemFile = null;
        logger.info(METHODNAME, "Selection: ", exportCodeSystem != null ? exportCodeSystem.getName() : null);
        try {
            if (exportCodeSystem == null) {
                exportCodeSystem = new CdsCodeSystemDTO();
                exportCodeSystem.setCodeSystemId("ALL");
            }
            PropertyBagDTO propertyBagDTO = getNewPropertyBagDTO();
            propertyBagDTO.setQueryClass("SimpleExchange");
            Map<String, byte[]> exportData = getGeneralMGR().exportData(exportCodeSystem, getSessionDTO(), propertyBagDTO);
            ByteArrayInputStream stream = new ByteArrayInputStream(FileUtils.getZipOfFiles(exportData));
            codeSystemFile = new DefaultStreamedContent(stream, "application/zip", String.format("code-systems-%s.zip", new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Timestamp(new Date().getTime()))));
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logEnd(METHODNAME);
        }
        return codeSystemFile;
    }
}