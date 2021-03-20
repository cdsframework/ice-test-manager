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
package org.cdsframework.section.cds.cdm;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.ConceptDeterminationMethodDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.lookup.ConceptDeterminationMethodDTOList;
import org.cdsframework.rs.support.CoreConfiguration;
import org.cdsframework.util.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class ConceptDeterminationMethodMGR extends BaseModule<ConceptDeterminationMethodDTO> {

    private static final long serialVersionUID = 8853709905753928196L;

    @Inject
    private ConceptDeterminationMethodDTOList conceptDeterminationMethodDTOList;

    @Override
    protected void initialize() {
        final String METHODNAME = "initialize ";
        logger.logBegin(METHODNAME);
        try {
            setLazy(true);
            setAssociatedList(conceptDeterminationMethodDTOList);
            setInitialQueryClass("FindAll");
            setSaveImmediately(true);
        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    public StreamedContent exportCdm(ConceptDeterminationMethodDTO conceptDeterminationMethodDTO) {
        final String METHODNAME = "exportCdm ";
        StreamedContent cdmOutput = null;
        logger.info(METHODNAME, "Selection CDM: ", conceptDeterminationMethodDTO.getCode());
        conceptDeterminationMethodDTO.getQueryMap().put("codeSystem", "2.16.840.1.113883.3.795.12.1.1");

        try {
            PropertyBagDTO propertyBagDTO = getNewPropertyBagDTO();
            propertyBagDTO.setQueryClass("OpenCdsExport");
            Map<String, byte[]> exportData = getGeneralMGR().exportData(conceptDeterminationMethodDTO, getSessionDTO(), propertyBagDTO);
            ByteArrayInputStream stream = new ByteArrayInputStream(FileUtils.getZipOfFiles(exportData));
            cdmOutput = new DefaultStreamedContent(stream, "application/zip", String.format("cdm.zip", new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Timestamp(new Date().getTime()))));
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return cdmOutput;
    }

    public void deployCdm(ConceptDeterminationMethodDTO conceptDeterminationMethodDTO) {
        final String METHODNAME = "deployCdm ";
        logger.info(METHODNAME, "Selection CDM: ", conceptDeterminationMethodDTO.getCode());
        conceptDeterminationMethodDTO.getQueryMap().put("codeSystem", "2.16.840.1.113883.3.795.12.1.1");

        String restServiceUri = CoreConfiguration.getBaseRsUri()
                + String.format(CoreConfiguration.getRsCrudAppContext(), "cds")
                + "/api/resources/conceptdeterminationmethods/deploy/2.16.840.1.113883.3.795.5.4.12.5.1/"
                + conceptDeterminationMethodDTO.getCode()
                + "?sessionId="
                + getSessionDTO().getSessionId();
        logger.info(METHODNAME, "calling restServiceUri: ", restServiceUri);

        try {
            final URL url = new URL(restServiceUri);
            final URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            final InputStream inputStream = urlConnection.getInputStream();
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

}
