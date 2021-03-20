/**
 * The MTS ice EJB project is the base framework for the CDS Framework Middle Tier Service.
 *
 * Copyright (C) 2016 New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * Contributions by HLN Consulting, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. You should have received a copy of the GNU Lesser
 * General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/> for more details.
 *
 * The above-named contributors (HLN Consulting, LLC) are also licensed by the
 * New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * to have (without restriction, limitation, and warranty) complete irrevocable
 * access and rights to this project.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; THE
 *
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING, BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR
 * OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see
 * https://www.hln.com/services/open-source/ or send correspondence to
 * ice@hln.com.
 */
package org.cdsframework.ejb.importer;

import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.cdsframework.cds.testcase.importers.TestImportCallback;
import org.cdsframework.cds.testcase.importers.XlsxImporter;
import org.cdsframework.cds.testcase.importers.ZipXmlImporter;
import org.cdsframework.dto.CdsVersionDTO;
import org.cdsframework.dto.IceTestEventDTO;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceTestGroupDTO;
import org.cdsframework.dto.IceTestSuiteDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.ejb.bo.IceTestBO;
import org.cdsframework.ejb.bo.IceTestGroupBO;
import org.cdsframework.ejb.bo.IceTestSuiteBO;
import org.cdsframework.ejb.local.PropertyMGRLocal;
import org.cdsframework.ejb.local.SmtpMGRLocal;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.CdsException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.factory.IceTestDTOObjectFactory;
import org.cdsframework.factory.IceTestGroupDTOObjectFactory;
import org.cdsframework.group.Add;
import org.cdsframework.group.Update;
import org.cdsframework.ice.testcase.TestCaseWrapper;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class IceTestDTOImporter {

    private final static LogUtils logger = LogUtils.getLogger(IceTestDTOImporter.class);
    @EJB
    private PropertyMGRLocal propertyMGRLocal;
    @EJB
    private IceTestGroupBO iceTestGroupBO;
    @EJB
    private IceTestSuiteBO iceTestSuiteBO;
    @EJB
    private IceTestBO iceTestBO;
    @EJB
    private IceTestGroupDTOObjectFactory iceTestGroupDTOObjectFactory;
    @EJB
    private IceTestDTOObjectFactory iceTestDTOObjectFactory;
    @EJB
    private SmtpMGRLocal smtpMGRLocal;
    private long CDS_MAX_IMPORT_SIZE;
    private List<String> CDS_SUPPORTED_IMPORT_MIME_TYPES;

    @PostConstruct
    public void postConstruct() {
        CDS_MAX_IMPORT_SIZE = (Long) propertyMGRLocal.get("CDS_MAX_IMPORT_SIZE");
        CDS_SUPPORTED_IMPORT_MIME_TYPES = (List<String>) propertyMGRLocal.get("CDS_SUPPORTED_IMPORT_MIME_TYPES");
    }

    class Counter {

        private int i = 0;

        public int getCount() {
            return i;
        }

        public void increment() {
            i++;
        }
    }

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void importTestsFromSource(final IceTestSuiteDTO iceTestSuiteDTO, final SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, ConstraintViolationException, AuthenticationException, AuthorizationException,
            NotFoundException {
        final String METHODNAME = "importTestsFromSource ";
        InputStream importInputStream = null;
        InputStream payloadInputStream = null;
        final Counter testCount = new Counter();
        String fileName = "unset";
        final PropertyBagDTO propertBagUpdateDTO = new PropertyBagDTO();
        propertBagUpdateDTO.put("isOverrideCreateModProperties", true);

        if (iceTestSuiteDTO == null) {
            throw new MtsException(METHODNAME + "testSuiteDTO cannot be null!");
        }

        try {
            if (propertyBagDTO.contains("payload")) {
                fileName = (String) propertyBagDTO.get("fileName");
                if (fileName == null || fileName.trim().isEmpty()) {
                    throw new MtsException(METHODNAME + "- fileName missing: " + fileName);
                }

                String mimeType = (String) propertyBagDTO.get("mimeType");
                if (mimeType == null || mimeType.trim().isEmpty() || !CDS_SUPPORTED_IMPORT_MIME_TYPES.contains(mimeType)) {
                    throw new MtsException(METHODNAME + "- mime type unsupported: " + mimeType);
                }

                long fileSize = (Long) propertyBagDTO.get("fileSize");
                if (fileSize > CDS_MAX_IMPORT_SIZE) {
                    throw new MtsException(METHODNAME + "- file size exceeds maximum allowed: " + fileSize + " (" + CDS_MAX_IMPORT_SIZE + ")");
                }

                final CdsVersionDTO importVersion = (CdsVersionDTO) propertyBagDTO.get("cdsVersionDTO");

                logger.info("testSuiteDTO name: ", iceTestSuiteDTO.getName());
                logger.info("fileName: ", fileName);
                logger.info("mimeType: ", mimeType);
                logger.info("fileSize: ", fileSize);
                logger.info("importVersion: ", importVersion);

                iceTestSuiteDTO.setCdsVersionDTO(importVersion);

                // get the file byte array
                byte[] payload = (byte[]) propertyBagDTO.get("payload");
                if (payload != null) {
                    logger.info("data length: ", payload.length);
                    final StringBuilder notes = new StringBuilder();

                    // create suite
                    final IceTestSuiteDTO result = iceTestSuiteBO.addMainNew(iceTestSuiteDTO, Add.class, sessionDTO, propertyBagDTO);

                    payloadInputStream = IOUtils.toInputStream((new String(payload)));
                    importInputStream = new Base64InputStream(payloadInputStream);
                    try {
                        if (result.getNotes() != null && !result.getNotes().trim().isEmpty()) {
                            notes.append(result.getNotes()).append("\r\n\r\n");
                        }
                        // update notes of main suite entry with imported filename
                        notes.append("Imported from file: ").append(fileName);

                        // call back instance impl - this is passwd to whatever import module is used and processes each test
                        TestImportCallback testImportCallback = new TestImportCallback() {

                            @Override
                            public void callback(TestCaseWrapper testCase, String group, boolean success) throws CdsException {
                                IceTestGroupDTO testGroupDTO = null;
                                try {
                                    testGroupDTO = iceTestGroupDTOObjectFactory.findOrCreateTestGroupDTO(group, result.getSuiteId(), sessionDTO);
                                } catch (Exception e) {
                                    logger.error(e);
                                    throw new CdsException(e.getMessage());
                                }
                                if (success) {
                                    try {
                                        IceTestDTO testDTO = iceTestDTOObjectFactory.getIceTestDTOFromTestCaseWrapper(testCase, result, testGroupDTO, importVersion, notes, sessionDTO);
                                        if (testDTO == null) {
                                            notes.append("\r\n\r\n").append("Conversion error: ").append(testCase.getFileLocation()).append("\r\n    message: ").append(testCase.getErrorMessage());
                                        } else {
                                            testDTO.setGroupId(testGroupDTO.getGroupId());
                                            testDTO.setSuiteId(result.getSuiteId());
//                                            if (logger.isDebugEnabled()) {
                                            logger.info("Got test: ", testDTO.getName(), "num of Events: ", testDTO.getChildrenDTOs(IceTestEventDTO.ByTestId.class));
//                                            }
                                            if (testDTO.getNotes() != null && testCase.getFileLocation() != null) {
                                                testDTO.setNotes(
                                                        (testDTO.getNotes() == null ? "" : testDTO.getNotes() + "\r\n\r\n")
                                                        + "Source sheet: " + testCase.getFileLocation());
                                            }
                                            iceTestBO.addMainNew(testDTO, Add.class, sessionDTO, propertBagUpdateDTO);
                                            if (testGroupDTO.isUpdated()) {
                                                iceTestGroupBO.updateMainNew(testGroupDTO, Update.class, sessionDTO, propertBagUpdateDTO);
                                            }
                                            testCount.increment();
                                        }
                                    } catch (AuthorizationException e) {
                                        notes.append("\r\n\r\n").append("Import exception: ").append(testCase.getFileLocation()).append("\r\n    message: ").append(e.getMessage());
                                        logger.error(e);
                                        throw new CdsException(e.getMessage());
                                    } catch (AuthenticationException e) {
                                        notes.append("\r\n\r\n").append("Import exception: ").append(testCase.getFileLocation()).append("\r\n    message: ").append(e.getMessage());
                                        logger.error(e);
                                        throw new CdsException(e.getMessage());
                                    } catch (ConstraintViolationException e) {
                                        notes.append("\r\n\r\n").append("Import exception: ").append(testCase.getFileLocation()).append("\r\n    message: ").append(e.getMessage());
                                        logger.error(notes.toString());
                                        throw new CdsException(e.getMessage() != null ? e.toString() : "");
                                    } catch (NotFoundException e) {
                                        notes.append("\r\n\r\n").append("Import exception: ").append(testCase.getFileLocation()).append("\r\n    message: ").append(e.getMessage());
                                        logger.error(e);
                                        throw new CdsException(e.getMessage());
                                    } catch (ValidationException e) {
                                        notes.append("\r\n\r\n").append("Import exception: ").append(testCase.getFileLocation()).append("\r\n    message: ").append(e.getMessage());
                                        logger.error(e);
                                        throw new CdsException(e.getMessage());
                                    } catch (MtsException e) {
                                        notes.append("\r\n\r\n").append("Import exception: ").append(testCase.getFileLocation()).append("\r\n    message: ").append(e.getMessage());
                                        logger.error(e);
                                        throw new CdsException(e.getMessage());
                                    } catch (ParseException e) {
                                        notes.append("\r\n\r\n").append("Import exception: ").append(testCase.getFileLocation()).append("\r\n    message: ").append(e.getMessage());
                                        logger.error(e);
                                        throw new CdsException(e.getMessage());
                                    }
                                } else {
                                    notes.append("\r\n\r\n").append("Import error: ").append(testCase.getFileLocation()).append("\r\n    message: ").append(testCase.getErrorMessage());
                                }
                                logger.info("Processing group: ", group, " - test: ", testCase.getName());
                            }

                        };

                        long start = System.nanoTime();
                        logger.info(METHODNAME, "Processing mimeType: ", mimeType);
                        if (mimeType.contains("officedocument") || mimeType.contains("excel")) {
                            XlsxImporter xlsxImporter = new XlsxImporter();
                            xlsxImporter.importFromInputStream(importInputStream, testImportCallback);
                        } else if (mimeType.contains("xml") || mimeType.contains("octet-stream") || mimeType.contains("zip")) {
                            ZipXmlImporter zipXmlImporter = new ZipXmlImporter();
                            zipXmlImporter.importFromInputStream(importInputStream, testImportCallback);
                        } else {
                            throw new MtsException("Unexpected source: " + mimeType);
                        }

                        logger.logDuration("Test import test case creation complete", start);

                        notes.append("\r\n\r\n").append("Successfully imported ").append(testCount.getCount()).append(" test cases.");
                        result.setNotes(notes.toString());
                        iceTestSuiteBO.updateMainNew(result, Update.class, sessionDTO, propertBagUpdateDTO);
                        logger.info("Test import complete.");
                        smtpMGRLocal.sendMessageToSessionUser(null, sessionDTO, String.format("File %s has been successfully imported", fileName), notes.toString(), true);
//                        start = System.nanoTime();
//                        for (IceTestGroupDTO testGroupDTO : groups.keySet()) {
//                            testGroupDTO.setSuiteId(result.getSuiteId());
//                            List<IceTestDTO> tests = groups.get(testGroupDTO);
//                            testCount += tests.size();
//                            IceTestGroupDTO newIceTestGroupDTO = iceTestGroupBO.addMain(testGroupDTO, Add.class, sessionDTO, new PropertyBagDTO());
//                            for (IceTestDTO test : tests) {
//                                test.setIceTestGroupDTO(newIceTestGroupDTO);
//                                iceTestBO.addMain(test, Add.class, sessionDTO, new PropertyBagDTO());
//                            }
//                        }
//                        logger.logDuration("Test import DTO creation complete", start);
//                        start = System.nanoTime();
//                        iceTestSuiteBO.updateMain(result, Update.class, sessionDTO, new PropertyBagDTO());
//                        logger.logDuration("Test import updateMain complete", start);
                    } finally {
                        logger.logEnd(METHODNAME);
                    }
                } else {
                    throw new MtsException("File data is null!");
                }
            } else {
                throw new MtsException(METHODNAME + "called with no file data!");
            }
        } catch (CdsException e) {
            logger.error(e);
            sendErrorAbortImportEmail(sessionDTO, fileName, e);
            throw new MtsException(e.getMessage());
        } catch (MtsException e) {
            sendErrorAbortImportEmail(sessionDTO, fileName, e);
            throw e;
        } catch (ValidationException e) {
            sendErrorAbortImportEmail(sessionDTO, fileName, e);
            throw e;
        } catch (ConstraintViolationException e) {
            sendErrorAbortImportEmail(sessionDTO, fileName, e);
            throw e;
        } catch (AuthenticationException e) {
            sendErrorAbortImportEmail(sessionDTO, fileName, e);
            throw e;
        } catch (AuthorizationException e) {
            sendErrorAbortImportEmail(sessionDTO, fileName, e);
            throw e;
        } catch (NotFoundException e) {
            sendErrorAbortImportEmail(sessionDTO, fileName, e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected runtime exception!!!");
            logger.error(e);
            sendErrorAbortImportEmail(sessionDTO, fileName, e);
            throw new MtsException(e.getMessage());
        } finally {
            try {
                if (importInputStream != null) {
                    importInputStream.close();
                }
            } catch (Exception e) {
                // do nothing.
            }
            try {
                if (payloadInputStream != null) {
                    payloadInputStream.close();
                }
            } catch (Exception e) {
                // do nothing.
            }
        }
    }

    private void sendErrorAbortImportEmail(SessionDTO sessionDTO, String fileName, Exception e) {
        smtpMGRLocal.sendMessageToSessionUser(
                null,
                sessionDTO,
                String.format("File %s import was unsuccessful", fileName),
                String.format("Unexpected error encounted importing %s. Report the error below to your administrator: \n\n\n%s", fileName, ExceptionUtils.getRootCauseMessage(e)),
                true);
    }
}
