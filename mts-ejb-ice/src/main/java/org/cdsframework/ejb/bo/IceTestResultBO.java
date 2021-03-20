/**
 * The MTS ice EJB project is the base framework for the CDS Framework Middle Tier Service.
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
package org.cdsframework.ejb.bo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.cdsframework.cds.vmr.CdsObjectAssist;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
import org.cdsframework.dto.CdsVersionDTO;
import org.cdsframework.dto.IceTestEventDTO;
import org.cdsframework.dto.IceTestImmunityDTO;
import org.cdsframework.dto.IceTestProposalDTO;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceTestEvaluationDTO;
import org.cdsframework.dto.IceTestEventComponentDTO;
import org.cdsframework.dto.IceTestRecommendationDTO;
import org.cdsframework.dto.IceTestResultDTO;
import org.cdsframework.dto.IceTestSuiteDTO;
import org.cdsframework.dto.IceTestVaccineGroupRelDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.ejb.base.BaseTestCaseResultBO;
import org.cdsframework.ejb.local.CdsMGRLocal;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.CdsException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.factory.TestCaseObjectFactory;
import org.cdsframework.ice.testcase.TestCaseWrapper;
import org.cdsframework.rules.IceTestDiffRules;
import org.opencds.vmr.v1_0.schema.CDSInput;
import org.opencds.vmr.v1_0.schema.CDSOutput;

/**
 *
 * @author HLN Consulting, LLC
 */
@LocalBean
@Stateless
public class IceTestResultBO extends BaseTestCaseResultBO<IceTestDTO, IceTestResultDTO> {

    @EJB
    private IceTestBO iceTestBO;
    @EJB
    private IceTestSuiteBO iceTestSuiteBO;
    @EJB
    private TestCaseObjectFactory testCaseObjectFactory;
    @EJB
    private CdsMGRLocal cdsMGRLocal;
    private List<Class> classList = Arrays.asList(new Class[]{
        IceTestEventDTO.class,
        IceTestEventComponentDTO.class,
        IceTestEvaluationDTO.class,
        IceTestProposalDTO.class,
        IceTestRecommendationDTO.class,
        IceTestImmunityDTO.class,
        IceTestVaccineGroupRelDTO.class,});

    /**
     * Cancel test runs based on a submitted list of UUIDs.
     *
     * @param uuids
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void cancelTests(List<UUID> uuids) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Asynchronous
    @Override
    public Future<IceTestResultDTO> runTest(SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException,
            ConstraintViolationException {
        IceTestResultDTO result = customQueryMain(new IceTestResultDTO(), IceTestResultDTO.RunTests.class, new ArrayList<Class>(), sessionDTO, propertyBagDTO);
        return new AsyncResult<IceTestResultDTO>(result);
    }

    /**
     * Fix this - should override customQuery
     *
     * @param baseDTO
     * @param queryClass
     * @param childClassDTOs
     * @param sessionDTO
     * @param propertyBagDTO
     * @return
     * @throws ValidationException
     * @throws NotFoundException
     * @throws MtsException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws ConstraintViolationException
     */
    @Override
    public IceTestResultDTO customQueryMain(IceTestResultDTO baseDTO, Class queryClass, List<Class> childClassDTOs, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException,
            ConstraintViolationException {
        final String METHODNAME = "customQueryMain ";
        IceTestResultDTO result = null;
        if (queryClass == IceTestResultDTO.RunTests.class) {
            logger.info(METHODNAME, "RunTests called.", baseDTO);
            try {
                if (propertyBagDTO != null) {
                    Map<String, Object> propertyMap = propertyBagDTO.getPropertyMap();
                    if (propertyMap.containsKey("testId")) {
                        IceTestDTO iceTestDTO;
                        IceTestDTO queryDTO;
                        IceTestSuiteDTO testSuite;
                        CdsVersionDTO cdsVersionDTO;

                        // figure out if we need to trim the results
                        boolean trimResults = false;
                        Object trimResultsMapValue = propertyMap.get("trimResults");
                        logger.info(METHODNAME, "trimResultsMapValue=", trimResultsMapValue);
                        if (trimResultsMapValue != null) {
                            trimResults = (Boolean) trimResultsMapValue;
                        }
                        logger.info(METHODNAME, "trimResults=", trimResults);

                        // get queryDTO
                        String testId = (String) propertyMap.get("testId");
                        queryDTO = new IceTestDTO();
                        queryDTO.setTestId(testId);
                        logger.info(METHODNAME, "Got test ID: ", queryDTO.getTestId());

                        // get iceTestDTO
                        iceTestDTO = iceTestBO.findByPrimaryKeyMain(queryDTO, classList, sessionDTO, propertyBagDTO);
                        if (iceTestDTO == null) {
                            throw new MtsException("ICE test is null!");
                        }

                        // override the eval date if supplied
                        Object evalDateOverride = propertyMap.get("evalDateOverride");
                        logger.info(METHODNAME, "evalDateOverride=", evalDateOverride);
                        if (evalDateOverride != null && evalDateOverride instanceof Date) {
                            iceTestDTO.setExecutionDate((Date) evalDateOverride);
                        }

                        // get IceTestSuiteDTO object
                        testSuite = new IceTestSuiteDTO();
                        testSuite.setSuiteId(iceTestDTO.getSuiteId());
                        testSuite = iceTestSuiteBO.findByPrimaryKeyMain(testSuite, new ArrayList<Class>(), sessionDTO, propertyBagDTO);
                        if (testSuite == null) {
                            throw new MtsException("ICE test suite is null!");
                        }

                        // get IceTCdsVersionDTOstSuiteDTO object
                        cdsVersionDTO = testSuite.getCdsVersionDTO();
                        if (cdsVersionDTO == null) {
                            throw new MtsException("ICE test suite cds version is null!");
                        }

                        // prep result object
                        IceTestResultDTO iceTestResultDTO = new IceTestResultDTO();
                        iceTestResultDTO.setDifferenceLog(new ArrayList<String>());
                        iceTestResultDTO.setIceTestDTO(iceTestDTO);

                        // run test
                        logger.info(METHODNAME, "Found test: ", iceTestDTO.getName());

                        // get testCase
                        TestCaseWrapper testCase = testCaseObjectFactory.getTestCaseWrapperFromIceTestDTO(iceTestDTO);

                        // generate focus map
                        Map<CdsCodeDTO, List<CdsCodeDTO>> testVaccineGroupFocus = new HashMap<CdsCodeDTO, List<CdsCodeDTO>>();
                        for (IceTestVaccineGroupRelDTO item : iceTestDTO.getIceTestVaccineGroupRelDTOs()) {
                            IceVaccineGroupDTO iceVaccineGroupDTO = item.getIceVaccineGroupDTO();
                            testVaccineGroupFocus.put(iceVaccineGroupDTO.getVaccineGroup(), iceVaccineGroupDTO.getRelatedVaccines());
                        }

                        CDSOutput cdsOutput = null;
                        try {
                            logger.info(METHODNAME, "Calling ice @ ", testCase.getExecutiondate());
                            iceTestResultDTO.setInputXml(CdsObjectAssist.cdsObjectToString(testCase.getCdsInputWrapper().getCdsObject(), CDSInput.class));
                            iceTestResultDTO.setAssertionXml(CdsObjectAssist.cdsObjectToString(testCase.getCdsOutputWrapper().getCdsObject(), CDSOutput.class));
                            CDSInput cdsInput = testCase.getCdsInputWrapper().getCdsObject();
                            long start = System.nanoTime();
                            cdsOutput = cdsMGRLocal.cdsEvaluateCdsInput(
                                    cdsInput,
                                    cdsVersionDTO.getScopingEntityId(),
                                    cdsVersionDTO.getBusinessId(),
                                    cdsVersionDTO.getVersion(),
                                    testCase.getExecutiondate());
                            iceTestResultDTO.setDuration((System.nanoTime() - start) / 1000000.0);
                        } catch (CdsException e) {
                            logger.error(e);
                        } catch (ParseException e) {
                            logger.error(e);
                        }

                        if (cdsOutput != null) {
                            try {
                                iceTestResultDTO.setOutputXml(CdsObjectAssist.cdsObjectToString(cdsOutput, CDSOutput.class));
                            } catch (CdsException e) {
                                logger.error(e);
                            }
                            IceTestDiffRules.iceTestOutputDiff(testVaccineGroupFocus, cdsOutput, testCase.getCdsOutputWrapper().getCdsObject(), iceTestResultDTO);
                            if (trimResults) {
                                logger.info(METHODNAME, "Trimming results...");
                                iceTestResultDTO.setInputXml("");
                                iceTestResultDTO.setAssertionXml("");
                                iceTestResultDTO.setOutputXml("");
                                iceTestResultDTO.setMatchLog(new ArrayList<String>());
                            }
                        } else {
                            iceTestResultDTO.getDifferenceLog().add("CDS result empty.");
                        }
                        result = iceTestResultDTO;
                    } else {
                        logger.error(METHODNAME, "RunTests called with incorrect parameters.");
                    }
                }
            } finally {
            }
        } else if (queryClass == IceTestSuiteDTO.XMLByTestId.class) {
            throw new UnsupportedOperationException("IceTestSuiteDTO.XMLByTestId.class query class is no longer supported.");
        } else {
            result = super.customQueryMain(baseDTO, queryClass, childClassDTOs, sessionDTO, propertyBagDTO);
        }
        return result;
    }
}
