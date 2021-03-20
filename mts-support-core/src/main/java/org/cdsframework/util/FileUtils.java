/**
 * The MTS support core project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see https://www.hln.com/services/open-source/ or send
 * correspondence to ice@hln.com.
 */
package org.cdsframework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.IOUtils;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
public class FileUtils {

    private static final LogUtils logger = LogUtils.getLogger(FileUtils.class);
    private static final int CHARS_PER_PAGE = 5000;

    public static byte[] getZipOfFiles(Map<String, byte[]> fileMap) throws MtsException {
        final String METHODNAME = "getZipOfFiles ";
        byte[] result = new byte[]{};
        ByteArrayOutputStream bos = null;
        ZipOutputStream zipfile = null;
        try {
            bos = new ByteArrayOutputStream();
            zipfile = new ZipOutputStream(bos);
            zipfile.setLevel(Deflater.BEST_COMPRESSION);
            for (Map.Entry<String, byte[]> fileEntry : fileMap.entrySet()) {
                if (fileEntry != null) {
                    if (logger.isTraceEnabled()) {
                        logger.info("processing: ", fileEntry.getKey());
                        logger.info("data: ", fileEntry.getValue());
                        if (fileEntry.getValue() != null) {
                            logger.info("data: ", new String(fileEntry.getValue()));
                        }
                    }
                    ZipEntry zipentry = new ZipEntry(fileEntry.getKey());
                    zipfile.putNextEntry(zipentry);
                    zipfile.write(fileEntry.getValue());
                    zipfile.closeEntry();
                } else {
                    logger.error("fileEntry was null!");
                }
            }
            zipfile.close();
            result = bos.toByteArray();

        } catch (IOException e) {
            throw new MtsException(METHODNAME + "IOException: " + e.getMessage());
        } finally {
            try {
                zipfile.close();
            } catch (Exception e) {
                //do nothing
            }
            try {
                bos.close();
            } catch (Exception e) {
                //do nothing
            }
        }
        return result;
    }

    public static String getStringFromJarFile(String path) {
        final String METHODNAME = "getStringFromJarFile ";
        String result = null;
        InputStreamReader input = null;
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = FileUtils.class.getClassLoader().getResourceAsStream(path);
            if (resourceAsStream != null) {
                input = new InputStreamReader(resourceAsStream);
                if (input != null) {
                    final char[] buffer = new char[CHARS_PER_PAGE];
                    StringBuilder sb = new StringBuilder(CHARS_PER_PAGE);
                    try {
                        for (int read = input.read(buffer, 0, buffer.length);
                                read != -1;
                                read = input.read(buffer, 0, buffer.length)) {
                            sb.append(buffer, 0, read);
                        }
                    } catch (IOException ioe) {
                        // ignore
                    }
                    result = sb.toString();
                } else {
                    logger.error(METHODNAME, "InputStreamReader was null for: " + path);
                }
            } else {
                logger.info(METHODNAME, "No data resource exists for: " + path);
            }
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                //do nothing
            }
            try {
                resourceAsStream.close();
            } catch (Exception e) {
                //do nothing
            }
        }
        return result;
    }

    public static Map<String, String> getDataMapFromBase64ZipByteArray(byte[] payload, String extFilter) {
        final String METHODNAME = "getDataMapFromBase64ZipByteArray ";
        if (payload == null) {
            throw new IllegalArgumentException(METHODNAME + "payload was null!");
        }
        Map<String, String> result = new HashMap<String, String>();
        // unmarshal the object
        InputStream payloadInputStream = IOUtils.toInputStream((new String(payload)));
        Base64InputStream base64InputStream = new Base64InputStream(payloadInputStream);
        ZipInputStream zipInputStream = null;

        try {
            zipInputStream = new ZipInputStream(base64InputStream);
            ZipEntry ze;
            while ((ze = zipInputStream.getNextEntry()) != null) {
                logger.info(METHODNAME, "found zip file: ", ze.getName());
                if (!ze.isDirectory() && (extFilter == null || (extFilter != null && ze.getName().toLowerCase().endsWith("." + extFilter)))) {
                    result.put(ze.getName(), IOUtils.toString(zipInputStream));
                    zipInputStream.closeEntry();
                }
            }
        } catch (IOException e) {
            logger.error(e);
        } finally {
            try {
                payloadInputStream.close();
            } catch (IOException e) {
                // do nothing
            }
            try {
                if (zipInputStream != null) {
                    zipInputStream.close();
                }
            } catch (IOException e) {
                // do nothing
            }
        }
        return result;
    }
}
