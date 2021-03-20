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
package org.cdsframework.util.cds;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.IOUtils;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.util.FileUtils;
import org.cdsframework.util.LogUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author HLN Consulting, LLC
 */
public class ImportUtils {

    final static LogUtils logger = LogUtils.getLogger(ImportUtils.class);

    /**
     * Handles either an uploaded zip with files or a single file.
     *
     * @param event
     * @return
     * @throws CatException
     * @throws IOException
     * @throws MtsException
     */
    public static byte[] getBase64ByteArrayPayloadFromFileUploadEvent(FileUploadEvent event)
            throws CatException, IOException, MtsException {
        final String METHODNAME = "getBase64ByteArrayPayloadFromFileUploadEvent ";
        byte[] payload = null;
        if (event == null) {
            throw new CatException(METHODNAME + "event is null!");
        }
        UploadedFile uploadedFile = event.getFile();
        if (event == uploadedFile) {
            throw new CatException(METHODNAME + "uploadedFile is null!");
        }
        InputStream inputStream = null;
        Base64InputStream base64InputStream = null;
        try {
            logger.info(METHODNAME, "uploaded file content type: ", uploadedFile.getContentType());
            inputStream = uploadedFile.getInputstream();
            // if a single xml uploaded - fork and stuff it in a zip
            if ("text/xml".equalsIgnoreCase(uploadedFile.getContentType()) || "text/plain".equalsIgnoreCase(uploadedFile.getContentType())) {
                byte[] input = IOUtils.toByteArray(inputStream);
                Map<String, byte[]> zipMap = new HashMap<String, byte[]>();
                zipMap.put(uploadedFile.getFileName(), input);
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    //nada
                }
                inputStream = new ByteArrayInputStream(FileUtils.getZipOfFiles(zipMap));
            }
            base64InputStream = new Base64InputStream(inputStream, true);
            payload = IOUtils.toByteArray(base64InputStream);
        } finally {
            try {
                if (base64InputStream != null) {
                    base64InputStream.close();
                }
            } catch (IOException e) {
                //nada
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                // nada
            }
        }
        return payload;
    }
}
