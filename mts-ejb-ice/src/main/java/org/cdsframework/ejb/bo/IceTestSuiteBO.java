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
package org.cdsframework.ejb.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.cdsframework.base.BaseBO;
import org.cdsframework.cds.util.MarshalUtils;
import org.cdsframework.dto.IceTestEvaluationDTO;
import org.cdsframework.dto.IceTestEventComponentDTO;
import org.cdsframework.dto.IceTestEventDTO;
import org.cdsframework.dto.IceTestImmunityDTO;
import org.cdsframework.dto.IceTestProposalDTO;
import org.cdsframework.dto.IceTestRecommendationDTO;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceTestGroupDTO;
import org.cdsframework.dto.IceTestSuiteDTO;
import org.cdsframework.dto.IceTestVaccineGroupRelDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.ejb.importer.IceTestDTOImporter;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.CdsException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.factory.TestCaseObjectFactory;
import org.cdsframework.group.FindAll;
import org.cdsframework.ejb.timer.CallbackTimer;
import org.cdsframework.callback.DtoSessionQueueRunnerCallback;
import org.cdsframework.ejb.local.IceTestMgrLocal;
import org.cdsframework.enumeration.CoreErrorCode;
import org.cdsframework.enumeration.Operation;
import org.cdsframework.group.SimpleExchange;
import org.cdsframework.util.BrokenRule;
import org.cdsframework.util.FileUtils;
import org.cdsframework.util.StringUtils;
import org.cdsframework.util.support.data.cds.testcase.TestCase;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
public class IceTestSuiteBO extends BaseBO<IceTestSuiteDTO> {

    @EJB
    private CallbackTimer callbackTimer;
    @EJB
    private IceTestBO iceTestBO;
    @EJB
    private IceTestGroupBO iceTestGroupBO;
    @EJB
    private TestCaseObjectFactory testCaseObjectFactory;
    @EJB
    private IceTestDTOImporter iceTestDTOImporter;
    @EJB
    private IceTestMgrLocal iceTestMgrLocal;

    @Override
    protected void preInitialize() throws MtsException {
        // This makes sure the IceTestBO is always initialized before this BO
        iceTestBO.getConfiguration();
    }

    @Override
    protected void validateAddOrUpdate(IceTestSuiteDTO baseDTO, Operation operation, Class queryClass, List<Class> validationClasses, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException, AuthorizationException {
        if (baseDTO != null) {
            if (baseDTO.getCdsVersionDTO() == null) {
                throw new ValidationException(new BrokenRule(CoreErrorCode.FieldCanNotBeEmpty, "Knowledge module version is empty!"));
            }
        }
    }

    @Override
    protected void postUpdate(IceTestSuiteDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ConstraintViolationException, NotFoundException, MtsException, ValidationException, AuthenticationException,
            AuthorizationException {
        final String METHODNAME = "postUpdate ";
//        long start = System.nanoTime();
        String operationName = propertyBagDTO.getOperationName();

        if ("mergeSuites".equals(operationName)) {
            iceTestMgrLocal.mergeSuite(baseDTO, sessionDTO, propertyBagDTO);
        } else if ("copySuite".equals(operationName)) {
            callbackTimer.queueCallback(new DtoSessionQueueRunnerCallback<IceTestSuiteDTO>(baseDTO, sessionDTO, propertyBagDTO, 5) {
                @Override
                public boolean execute() {
                    final String METHODNAME = "IceTestSuiteBO>copySuite>DtoSessionQueueRunnerCallback>execute ";
                    boolean result = false;
                    try {
                        iceTestMgrLocal.copySuite(getDto(), getSessionDTO(), getPropertyBagDTO());
                        result = true;
                    } catch (NotFoundException e) {
                        // no hacer nada: if it isn't there then do nothing and let the queue timer run on it again.
                        failOnce();
                    } catch (Exception e) {
                        logger.error(METHODNAME, e);
                        fail();
                    }
                    return result;
                }
            });
        }
//        logger.logDuration(METHODNAME, start);
    }

    @Override
    public void importData(Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException,
            ConstraintViolationException {
        final String METHODNAME = "importData ";
        if (queryClass == SimpleExchange.class) {
            simpleExchangeImport(queryClass, sessionDTO, propertyBagDTO);
        } else {
            throw new ValidationException(CoreErrorCode.ParameterCanNotBeNull,
                    logger.error(METHODNAME, "Unsupported queryClass: ", queryClass));
        }
    }

    private void simpleExchangeImport(Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException,
            ConstraintViolationException {
        if (propertyBagDTO.contains("fileName")) {
            IceTestSuiteDTO iceTestSuiteDTO = new IceTestSuiteDTO();
            iceTestSuiteDTO.setName(propertyBagDTO.get("fileName", String.class));
            callbackTimer.queueCallback(new DtoSessionQueueRunnerCallback<IceTestSuiteDTO>(iceTestSuiteDTO, sessionDTO, propertyBagDTO, 5) {
                @Override
                public boolean execute() {
                    final String METHODNAME = "IceTestSuiteBO>importTests>DtoSessionQueueRunnerCallback>execute ";
                    boolean result = false;
                    try {
                        iceTestDTOImporter.importTestsFromSource(
                                getDto(),
                                getSessionDTO(),
                                getPropertyBagDTO());
                        result = true;
                    } catch (NotFoundException e) {
                        // no hacer nada: if it isn't there then do nothing and let the queue timer run on it again.
                        failOnce();
                    } catch (Exception e) {
                        logger.error(METHODNAME, e);
                        fail();
                    }
                    return result;
                }
            });
        }
    }

    @Override
    public Map<String, byte[]> exportData(IceTestSuiteDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "exportData ";
        if (queryClass == SimpleExchange.class) {
            return simpleExchangeExport(baseDTO, queryClass, sessionDTO, propertyBagDTO);
        } else {
            throw new ValidationException(CoreErrorCode.ParameterCanNotBeNull,
                    logger.error(METHODNAME, "Unsupported queryClass: ", queryClass));
        }
    }

    private Map<String, byte[]> simpleExchangeExport(IceTestSuiteDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "simpleExchangeExport ";
        List<Class> childClasses = new ArrayList<Class>();
        childClasses.add(IceTestEventDTO.class);
        childClasses.add(IceTestProposalDTO.class);
        childClasses.add(IceTestImmunityDTO.class);
        childClasses.add(IceTestVaccineGroupRelDTO.class);
        childClasses.add(IceTestEventComponentDTO.class);
        childClasses.add(IceTestEvaluationDTO.class);
        childClasses.add(IceTestRecommendationDTO.class);

        Map<String, byte[]> fileMap = new HashMap<String, byte[]>();
        List<IceTestDTO> testsToExport = new ArrayList<IceTestDTO>();
        Object exportTestObject = propertyBagDTO.get("exportTestObject");
        propertyBagDTO.put("skipLimit", true);
        if (exportTestObject == null) {
            throw new MtsException(METHODNAME + "No export object specified.");
        }
        if (exportTestObject instanceof IceTestSuiteDTO) {
            IceTestSuiteDTO iceTestSuiteDTO = (IceTestSuiteDTO) exportTestObject;
            logger.info(METHODNAME, "Exporting test suite: ", iceTestSuiteDTO.getName());
            if ("ALL".equals(iceTestSuiteDTO.getSuiteId())) {
                testsToExport = iceTestBO.findByQueryListMain(new IceTestDTO(), FindAll.class, childClasses, sessionDTO, propertyBagDTO);
            } else if (iceTestSuiteDTO.getSuiteId() != null) {
                iceTestSuiteDTO = findByPrimaryKeyMain(iceTestSuiteDTO, new ArrayList<Class>(), sessionDTO, propertyBagDTO);
                logger.info(METHODNAME, "iceTestSuiteDTO=", iceTestSuiteDTO);
                IceTestDTO iceTestDTO = new IceTestDTO();
                iceTestDTO.setSuiteId(iceTestSuiteDTO.getSuiteId());
                testsToExport = iceTestBO.findByQueryListMain(iceTestDTO, IceTestDTO.ByTestSuiteId.class, childClasses, sessionDTO, propertyBagDTO);
            } else {
                throw new MtsException(METHODNAME + "No suite selection was made!");
            }
        } else if (exportTestObject instanceof IceTestGroupDTO) {
            IceTestGroupDTO iceTestGroupDTO = (IceTestGroupDTO) exportTestObject;
            logger.info(METHODNAME, "Exporting test group: ", iceTestGroupDTO.getName());
            iceTestGroupDTO = iceTestGroupBO.findByPrimaryKeyMain(iceTestGroupDTO, new ArrayList<Class>(), sessionDTO, propertyBagDTO);
            IceTestDTO iceTestDTO = new IceTestDTO();
            iceTestDTO.setGroupId(iceTestGroupDTO.getGroupId());
            testsToExport = iceTestBO.findByQueryListMain(iceTestDTO, IceTestDTO.ByTestGroupId.class, childClasses, sessionDTO, propertyBagDTO);
        } else if (exportTestObject instanceof IceTestDTO) {
            IceTestDTO iceTestDTO = (IceTestDTO) exportTestObject;
            logger.info(METHODNAME, "Exporting test: ", iceTestDTO.getName());
            testsToExport.add(iceTestBO.findByPrimaryKeyMain(iceTestDTO, childClasses, sessionDTO, propertyBagDTO));
        }
        logger.info(METHODNAME, "testsToExport=", testsToExport);
        logger.info(METHODNAME, "Exporting test cases: ", testsToExport.size());
        try {
            for (IceTestDTO iceTestDTO : testsToExport) {
                TestCase testCase = testCaseObjectFactory.getTestCaseFromIceTestDTO(iceTestDTO);
                byte[] marshalledXml = MarshalUtils.marshalObject(testCase);
                fileMap.put(StringUtils.getShaHashFromString(testCase.getName() + iceTestDTO.getTestId()) + ".xml", marshalledXml);
            }
        } catch (CdsException e) {
            logger.error(e);
            throw new MtsException(e.getMessage());
        }
        String xsd = FileUtils.getStringFromJarFile("testcase.xsd");
        if (xsd != null) {
            fileMap.put("testcase.xsd", xsd.getBytes());
        } else {
            logger.error("testcase.xsd was not found on the classpath!");
        }
        return fileMap;
    }

    @Override
    protected void processDTOCreateLastModDateId(IceTestSuiteDTO baseDTO, Operation operation, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
        final String METHODNAME = "processDTOCreateLastModDateId ";
        Boolean overrideCreateModProperties = propertyBagDTO.get("isOverrideCreateModProperties", false);
        if (!overrideCreateModProperties) {
            logger.debug(METHODNAME, "submitting dto to super.processDTOCreateLastModDateId");
            super.processDTOCreateLastModDateId(baseDTO, operation, sessionDTO, propertyBagDTO);
        } else if (logger.isDebugEnabled()) {
            logger.info(METHODNAME, "submitting user supplied createDatetime: ", baseDTO.getCreateDatetime());
            logger.info(METHODNAME, "submitting user supplied createId: ", baseDTO.getCreateId());
            logger.info(METHODNAME, "submitting user supplied lastModDatetime: ", baseDTO.getLastModDatetime());
            logger.info(METHODNAME, "submitting user supplied lastModId: ", baseDTO.getLastModId());
        } // assume they are set and let it roll...
    }

    @Override
    protected void processBegin(IceTestSuiteDTO baseDTO, Operation operation, Class queryClass, List<Class> validationClasses, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "processBegin ";
        for (IceTestGroupDTO iceTestGroupDTO : baseDTO.getIceTestGroupDTOs()) {
            if (iceTestGroupDTO.isDeleted()) {
                propertyBagDTO.put("deletedIceTestGroupDTOPresent", true);
                break;
            }
        }
    }

    @Override
    protected void processEnd(IceTestSuiteDTO baseDTO, List<Class> childClassDTOs, Operation operation, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "processEnd ";
//        Boolean overrideCreateModProperties = propertyBagDTO.get("isOverrideCreateModProperties", false);
//        Boolean deletedIceTestGroupDTOPresent = propertyBagDTO.get("deletedIceTestGroupDTOPresent", false);
//        if (!overrideCreateModProperties && operation == Operation.UPDATE) {
//            try {
//                Date originalLastModDatetime = baseDTO.getLastModDatetime();
//                Date newLastModDatetime = originalLastModDatetime;
//                String newLastModId = baseDTO.getLastModId();
//                if (newLastModDatetime == null) {
//                    newLastModDatetime = new Date();
//                    newLastModId = sessionDTO.getUserDTO().getUserId();
//                }
//                if (originalLastModDatetime != null) {
//                    if (deletedIceTestGroupDTOPresent) {
//                        newLastModDatetime = new Date();
//                        newLastModId = sessionDTO.getUserDTO().getUserId();
//                    } else {
//                        IceTestGroupDTO iceTestGroupDTO = new IceTestGroupDTO();
//                        iceTestGroupDTO.setSuiteId(baseDTO.getSuiteId());
//                        try {
//                            Date maxLastModDatetime = iceTestGroupBO.findObjectByQueryMain(iceTestGroupDTO, IceTestGroupDTO.MaxLastModDatetimeByTestSuiteId.class, sessionDTO, Date.class, propertyBagDTO);
//                            if (maxLastModDatetime != null && maxLastModDatetime.before(maxLastModDatetime)) {
//                                newLastModDatetime = maxLastModDatetime;
//                                iceTestGroupDTO.setLastModDatetime(maxLastModDatetime);
//                                PropertyBagDTO altPropertyBagDTO = new PropertyBagDTO();
//                                altPropertyBagDTO.put("adHocQueryLimit", 1);
//                                newLastModId = iceTestGroupBO.findObjectByQueryMain(iceTestGroupDTO, IceTestGroupDTO.LastModIdByLastModDatetime.class, sessionDTO, String.class, altPropertyBagDTO);
//                            }
//                        } catch (NotFoundException e) {
//                            // no hacer nada
//                        }
//                    }
//                    if (originalLastModDatetime != newLastModDatetime) {
//                        baseDTO.setLastModDatetime(newLastModDatetime);
//                        baseDTO.setLastModId(newLastModId);
//                        this.getDao().update(baseDTO, IceTestSuiteDTO.UpdateLastMod.class, sessionDTO, propertyBagDTO);
//                    }
//                }
//            } catch (ConstraintViolationException e) {
//                logger.error(e);
//            }
//        }
    }
}
