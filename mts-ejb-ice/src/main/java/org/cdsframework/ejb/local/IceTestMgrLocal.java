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
package org.cdsframework.ejb.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceTestEvaluationDTO;
import org.cdsframework.dto.IceTestEventComponentDTO;
import org.cdsframework.dto.IceTestEventDTO;
import org.cdsframework.dto.IceTestGroupDTO;
import org.cdsframework.dto.IceTestImmunityDTO;
import org.cdsframework.dto.IceTestProposalDTO;
import org.cdsframework.dto.IceTestRecommendationDTO;
import org.cdsframework.dto.IceTestSuiteDTO;
import org.cdsframework.dto.IceTestVaccineGroupRelDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.ejb.bo.IceTestBO;
import org.cdsframework.ejb.bo.IceTestEventBO;
import org.cdsframework.ejb.bo.IceTestGroupBO;
import org.cdsframework.ejb.bo.IceTestSuiteBO;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.group.Add;
import org.cdsframework.group.Delete;
import org.cdsframework.group.Update;
import org.cdsframework.util.DTOUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.support.DeepCopy;

/**
 *
 * @author sdn
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class IceTestMgrLocal {

    private final static LogUtils logger = LogUtils.getLogger(IceTestMgrLocal.class);
    @EJB
    private IceTestSuiteBO iceTestSuiteBO;
    @EJB
    private IceTestGroupBO iceTestGroupBO;
    @EJB
    private IceTestEventBO iceTestEventBO;
    @EJB
    private IceTestBO iceTestBO;
    @EJB
    private SmtpMGRLocal smtpMGRLocal;
    private final List<Class> fullChildClasses = new ArrayList<Class>();

    @PostConstruct
    public void postConstructor() {
        fullChildClasses.add(IceTestGroupDTO.class);
        fullChildClasses.add(IceTestDTO.class);
        fullChildClasses.add(IceTestEventDTO.class);
        fullChildClasses.add(IceTestProposalDTO.class);
        fullChildClasses.add(IceTestImmunityDTO.class);
        fullChildClasses.add(IceTestVaccineGroupRelDTO.class);
        fullChildClasses.add(IceTestEventComponentDTO.class);
        fullChildClasses.add(IceTestEvaluationDTO.class);
        fullChildClasses.add(IceTestRecommendationDTO.class);
    }

    public void copySuite(IceTestSuiteDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, ConstraintViolationException {
        final String METHODNAME = "copySuite ";
        long start = System.nanoTime();
        String newSuiteName = (String) propertyBagDTO.get("newSuiteName");
        propertyBagDTO.put("isOverrideCreateModProperties", true);
        logger.debug(METHODNAME, "called.");
        IceTestSuiteDTO iceTestSuiteDTO = baseDTO;
        try {
            if (StringUtils.isEmpty(newSuiteName)) {
                throw new ValidationException("New suite name was null or empty.");
            }
            iceTestSuiteDTO = iceTestSuiteBO.findByPrimaryKeyMain(iceTestSuiteDTO, fullChildClasses, sessionDTO, propertyBagDTO);

            // create a clone of the suite
            IceTestSuiteDTO newIceTestSuiteDTO = new IceTestSuiteDTO();
            newIceTestSuiteDTO.setName(newSuiteName);
            newIceTestSuiteDTO.setCdsVersionDTO(iceTestSuiteDTO.getCdsVersionDTO());
            newIceTestSuiteDTO.setNotes(iceTestSuiteDTO.getNotes());
            newIceTestSuiteDTO = iceTestSuiteBO.addMain(newIceTestSuiteDTO, Add.class, sessionDTO, new PropertyBagDTO());

            // go through groups and create new ones and then
            for (IceTestGroupDTO iceTestGroupDTO : iceTestSuiteDTO.getIceTestGroupDTOs()) {
                logger.info(METHODNAME, "copying: ", iceTestGroupDTO.getName(), " - ", iceTestGroupDTO.getGroupId(), " - from: ", iceTestSuiteDTO.getName());

                IceTestGroupDTO newIceTestGroupDTO = new IceTestGroupDTO();
                newIceTestGroupDTO.setDescription(iceTestGroupDTO.getDescription());
                newIceTestGroupDTO.setIgnore(iceTestGroupDTO.isIgnore());
                newIceTestGroupDTO.setName(iceTestGroupDTO.getName());
                newIceTestGroupDTO.setSuiteId(newIceTestSuiteDTO.getSuiteId());
                newIceTestGroupDTO = iceTestGroupBO.addMain(newIceTestGroupDTO, Add.class, sessionDTO, new PropertyBagDTO());
                logger.info(METHODNAME, "added: ", newIceTestGroupDTO.getName(), " - ", newIceTestGroupDTO.getGroupId(), " - to: ", newIceTestSuiteDTO.getName());

                // clone tests
                IceTestDTO queryDTO = new IceTestDTO();
                queryDTO.setGroupId(iceTestGroupDTO.getGroupId());
                List<IceTestDTO> groupIceTestDTOs
                        = iceTestBO.findByQueryListMain(queryDTO, IceTestDTO.ByTestGroupId.class, fullChildClasses, sessionDTO, propertyBagDTO);
                for (IceTestDTO iceTestDTO : groupIceTestDTOs) {
                    iceTestDTO.setGroupId(newIceTestGroupDTO.getGroupId());
                    iceTestDTO.setSuiteId(newIceTestSuiteDTO.getSuiteId());
                    iceTestDTO = cloneIceTestDTO(iceTestDTO, sessionDTO);
                    newIceTestGroupDTO.addOrUpdateChildDTO(iceTestDTO);
                }
            }
            logger.info(METHODNAME, "sending success email...");
            smtpMGRLocal.sendMessageToSessionUser(null,
                    sessionDTO,
                    String.format("%s has been successfully copied to %s.", iceTestSuiteDTO.getName(), newSuiteName),
                    "No errors reported.",
                    true);
        } catch (MtsException e) {
            sendErrorAbortEmail(iceTestSuiteDTO, sessionDTO, propertyBagDTO, e);
        } catch (ValidationException e) {
            sendErrorAbortEmail(iceTestSuiteDTO, sessionDTO, propertyBagDTO, e);
        } catch (NotFoundException e) {
            sendErrorAbortEmail(iceTestSuiteDTO, sessionDTO, propertyBagDTO, e);
        } catch (AuthenticationException e) {
            sendErrorAbortEmail(iceTestSuiteDTO, sessionDTO, propertyBagDTO, e);
        } catch (AuthorizationException e) {
            sendErrorAbortEmail(iceTestSuiteDTO, sessionDTO, propertyBagDTO, e);
        } catch (ConstraintViolationException e) {
            sendErrorAbortEmail(iceTestSuiteDTO, sessionDTO, propertyBagDTO, e);
        } finally {
            logger.logDuration(METHODNAME, start);
        }
    }

    public void mergeSuite(IceTestSuiteDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, ConstraintViolationException {
        final String METHODNAME = "mergeSuite ";
        long start = System.nanoTime();
        try {
            String destination = (String) propertyBagDTO.get("destination");
            if (StringUtils.isEmpty(destination)) {
                throw new ValidationException("Destination suite was null or empty.");
            }
            propertyBagDTO.put("isOverrideCreateModProperties", true);
            logger.debug(METHODNAME, "called.");
            IceTestSuiteDTO iceTestSuiteDTO = DeepCopy.copy(baseDTO);
            List<Class> childClasses = new ArrayList<Class>();
            childClasses.add(IceTestGroupDTO.class);
            iceTestSuiteDTO = iceTestSuiteBO.findByPrimaryKeyMain(iceTestSuiteDTO, childClasses, sessionDTO, propertyBagDTO);
            for (IceTestGroupDTO iceTestGroupDTO : iceTestSuiteDTO.getIceTestGroupDTOs()) {
                iceTestGroupDTO.setSuiteId(destination);
                iceTestGroupBO.updateMain(iceTestGroupDTO, Update.class, sessionDTO, propertyBagDTO);
            }
            iceTestSuiteDTO = iceTestSuiteBO.findByPrimaryKeyMain(iceTestSuiteDTO, fullChildClasses, sessionDTO, propertyBagDTO);
            iceTestSuiteDTO.delete(true);
            iceTestSuiteBO.deleteMain(iceTestSuiteDTO, Delete.class, sessionDTO, propertyBagDTO);
        } finally {
            logger.logDuration(METHODNAME, start);
        }
    }

    public void copyGroup(IceTestGroupDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, ConstraintViolationException {
        final String METHODNAME = "copyGroup ";
        logger.debug(METHODNAME, "called.");
        long start = System.nanoTime();
        try {
            String newGroupName = (String) propertyBagDTO.get("newGroupName");
            if (StringUtils.isEmpty(newGroupName)) {
                throw new ValidationException("New suite name was null or empty.");
            }
            propertyBagDTO.put("isOverrideCreateModProperties", true);

            IceTestGroupDTO newIceTestGroupDTO = new IceTestGroupDTO();
            newIceTestGroupDTO.setDescription(baseDTO.getDescription());
            newIceTestGroupDTO.setIgnore(baseDTO.isIgnore());
            newIceTestGroupDTO.setName(newGroupName);
            newIceTestGroupDTO.setSuiteId(baseDTO.getSuiteId());
            newIceTestGroupDTO = iceTestGroupBO.addMain(newIceTestGroupDTO, Add.class, sessionDTO, new PropertyBagDTO());
            logger.debug("added: ", newIceTestGroupDTO.getName(), " - ", newIceTestGroupDTO.getGroupId(), " - to: ", baseDTO.getSuiteId());

            // clone tests
            IceTestDTO queryDTO = new IceTestDTO();
            queryDTO.setGroupId(baseDTO.getGroupId());
            List<IceTestDTO> groupIceTestDTOs = iceTestBO.findByQueryListMain(queryDTO, IceTestDTO.ByTestGroupId.class, fullChildClasses, sessionDTO, propertyBagDTO);
            for (IceTestDTO iceTestDTO : groupIceTestDTOs) {
                iceTestDTO.setGroupId(newIceTestGroupDTO.getGroupId());
                iceTestDTO = cloneIceTestDTO(iceTestDTO, sessionDTO);
                newIceTestGroupDTO.addOrUpdateChildDTO(iceTestDTO);
            }
        } finally {
            logger.logDuration(METHODNAME, start);
        }
    }

    public void mergeGroup(IceTestGroupDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, ConstraintViolationException {
        final String METHODNAME = "mergeGroup ";
        long start = System.nanoTime();
        try {
            String destination = (String) propertyBagDTO.get("destination");
            if (StringUtils.isEmpty(destination)) {
                throw new ValidationException("Destination suite was null or empty.");
            }
            IceTestGroupDTO dstIceTestGroupDTO = new IceTestGroupDTO();
            dstIceTestGroupDTO.setGroupId(destination);
            propertyBagDTO.put("isOverrideCreateModProperties", true);
            logger.info(METHODNAME, "called.");

            IceTestGroupDTO iceTestGroupDTO = baseDTO;
            IceTestDTO queryDTO = new IceTestDTO();
            queryDTO.setGroupId(iceTestGroupDTO.getGroupId());
            List<IceTestDTO> groupIceTestDTOs = iceTestBO.findByQueryListMain(queryDTO, IceTestDTO.ByTestGroupId.class, fullChildClasses, sessionDTO, propertyBagDTO);

            for (IceTestDTO iceTestDTO : groupIceTestDTOs) {
                iceTestDTO.setGroupId(dstIceTestGroupDTO.getGroupId());
                iceTestBO.updateMain(iceTestDTO, Update.class, sessionDTO, propertyBagDTO);
            }

            iceTestGroupDTO = iceTestGroupBO.findByPrimaryKeyMain(baseDTO, fullChildClasses, sessionDTO, propertyBagDTO);
            iceTestGroupDTO.delete(true);
            iceTestGroupBO.deleteMain(iceTestGroupDTO, Delete.class, sessionDTO, propertyBagDTO);
        } finally {
            logger.logDuration(METHODNAME, start);
        }
    }

    public void copyTest(IceTestDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, ConstraintViolationException {
        final String METHODNAME = "copyTest ";
        logger.debug(METHODNAME, "called.");
        long start = System.nanoTime();
        try {
            String newTestName = (String) propertyBagDTO.get("newTestName");
            logger.info(METHODNAME, "newTestName=", newTestName);
            if (StringUtils.isEmpty(newTestName)) {
                throw new ValidationException("New test name was null or empty.");
            }
            IceTestDTO iceTestDTO = baseDTO;
            iceTestDTO = iceTestBO.findByPrimaryKeyMain(iceTestDTO, fullChildClasses, sessionDTO, propertyBagDTO);
            iceTestDTO.setName(newTestName);
            cloneIceTestDTO(iceTestDTO, sessionDTO);
        } finally {
            logger.logDuration(METHODNAME, start);
        }
    }

    private IceTestDTO cloneIceTestDTO(IceTestDTO iceTestDTO, SessionDTO sessionDTO)
            throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "cloneIceTestDTO ";
        logger.info(METHODNAME, "iceTestDTO=", iceTestDTO);

        // initialize the event id map for mapping old to new ids...
        Map<String, String> eventIdMap = new HashMap<String, String>();

        // store the children
        List<IceTestVaccineGroupRelDTO> iceTestVaccineGroupRelDTOs = iceTestDTO.getIceTestVaccineGroupRelDTOs();
        List<IceTestImmunityDTO> iceTestImmunityDTOs = iceTestDTO.getIceTestImmunityDTOs();
        List<IceTestEventDTO> iceTestEventDTOs = iceTestDTO.getIceTestEventDTOs();
        List<IceTestProposalDTO> iceTestProposalDTOs = iceTestDTO.getIceTestProposalDTOs();

        // add a new test
        iceTestDTO.setChildDTOMap(new HashMap());
        iceTestDTO.setTestId(null);
        DTOUtils.setDTOState(iceTestDTO, DTOState.NEW);
        iceTestDTO = iceTestBO.addMain(iceTestDTO, Add.class, sessionDTO, new PropertyBagDTO());
        logger.debug(METHODNAME, "iceTestDTO=", iceTestDTO);

        // add the IceTestVaccineGroupRelDTOs
        for (IceTestVaccineGroupRelDTO iceTestVaccineGroupRelDTO : iceTestVaccineGroupRelDTOs) {
            iceTestVaccineGroupRelDTO.setTestId(null);
            DTOUtils.setDTOState(iceTestVaccineGroupRelDTO, DTOState.NEW);
            iceTestDTO.addOrUpdateChildDTO(iceTestVaccineGroupRelDTO);
        }
        iceTestDTO = iceTestBO.updateMain(iceTestDTO, Update.class, sessionDTO, new PropertyBagDTO());

        // add the IceTestEventDTOs
        for (IceTestEventDTO iceTestEventDTO : iceTestEventDTOs) {
            String originalEventId = iceTestEventDTO.getEventId();
            for (IceTestEventComponentDTO iceTestEventComponentDTO : iceTestEventDTO.getIceTestEventComponentDTOs()) {
                for (IceTestEvaluationDTO iceTestEvaluationDTO : iceTestEventComponentDTO.getIceTestEvaluationDTOs()) {
                    iceTestEvaluationDTO.setEvaluationId(null);
                    iceTestEvaluationDTO.setEventComponentId(null);
                    DTOUtils.setDTOState(iceTestEvaluationDTO, DTOState.NEW);
                }
                iceTestEventComponentDTO.setEventId(null);
                iceTestEventComponentDTO.setEventComponentId(null);
                DTOUtils.setDTOState(iceTestEventComponentDTO, DTOState.NEW);
            }
            iceTestEventDTO.setTestId(iceTestDTO.getTestId());
            iceTestEventDTO.setEventId(null);
            if (iceTestEventDTO.getOffsetId() != null) {
                iceTestEventDTO.setOffsetId(eventIdMap.get(iceTestEventDTO.getOffsetId()));
            }
            DTOUtils.setDTOState(iceTestEventDTO, DTOState.NEW);
            iceTestEventDTO = iceTestEventBO.addMain(iceTestEventDTO, Add.class, sessionDTO, new PropertyBagDTO());
            String newEventId = iceTestEventDTO.getEventId();
            eventIdMap.put(originalEventId, newEventId);
            iceTestDTO.addOrUpdateChildDTO(iceTestEventDTO);
        }

        // add the IceTestImmunityDTOs
        for (IceTestImmunityDTO iceTestImmunityDTO : iceTestImmunityDTOs) {
            iceTestImmunityDTO.setTestId(null);
            iceTestImmunityDTO.setImmunityId(null);
            DTOUtils.setDTOState(iceTestImmunityDTO, DTOState.NEW);
            iceTestDTO.addOrUpdateChildDTO(iceTestImmunityDTO);
        }
        iceTestDTO = iceTestBO.updateMain(iceTestDTO, Update.class, sessionDTO, new PropertyBagDTO());

        // add the IceTestProposalDTOs
        for (IceTestProposalDTO iceTestProposalDTO : iceTestProposalDTOs) {
            for (IceTestRecommendationDTO iceTestRecommendationDTO : iceTestProposalDTO.getIceTestRecommendationDTOs()) {
                iceTestRecommendationDTO.setRecommendationId(null);
                DTOUtils.setDTOState(iceTestRecommendationDTO, DTOState.NEW);
            }
            iceTestProposalDTO.setTestId(null);
            iceTestProposalDTO.setProposalId(null);
            if (iceTestProposalDTO.getRecommendedOffsetId() != null) {
                iceTestProposalDTO.setRecommendedOffsetId(eventIdMap.get(iceTestProposalDTO.getRecommendedOffsetId()));
            }
            if (iceTestProposalDTO.getEarliestOffsetId() != null) {
                iceTestProposalDTO.setEarliestOffsetId(eventIdMap.get(iceTestProposalDTO.getEarliestOffsetId()));
            }
            if (iceTestProposalDTO.getLatestOffsetId() != null) {
                iceTestProposalDTO.setLatestOffsetId(eventIdMap.get(iceTestProposalDTO.getLatestOffsetId()));
            }
            if (iceTestProposalDTO.getOverdueOffsetId() != null) {
                iceTestProposalDTO.setOverdueOffsetId(eventIdMap.get(iceTestProposalDTO.getOverdueOffsetId()));
            }
            DTOUtils.setDTOState(iceTestProposalDTO, DTOState.NEW);
            iceTestDTO.addOrUpdateChildDTO(iceTestProposalDTO);
        }
        iceTestDTO = iceTestBO.updateMain(iceTestDTO, Update.class, sessionDTO, new PropertyBagDTO());
        return iceTestDTO;
    }

    private void sendErrorAbortEmail(IceTestSuiteDTO iceTestSuiteDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO, Exception e) {
        final String METHODNAME = "sendErrorAbortEmail ";
        logger.error(e);
        String operationName = propertyBagDTO.getOperationName();
        String newSuiteName = (String) propertyBagDTO.get("newSuiteName");
        if ("copySuite".equals(operationName)) {
            smtpMGRLocal.sendMessageToSessionUser(
                    null,
                    sessionDTO,
                    String.format("Copy suite %s to %s was unsuccessful", iceTestSuiteDTO.getName(), newSuiteName),
                    String.format("Unexpected error encounted copying %s to %s. Report the error below to your administrator: \n\n\n%s",
                            iceTestSuiteDTO.getName(),
                            newSuiteName,
                            ExceptionUtils.getRootCauseMessage(e)),
                    true);
        } else {
            logger.error(METHODNAME, "Unsupported error email operation.");
        }
    }

}
