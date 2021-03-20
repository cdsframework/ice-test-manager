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
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.cdsframework.base.BaseBO;
import org.cdsframework.dto.IceTestEvaluationDTO;
import org.cdsframework.dto.IceTestEventComponentDTO;
import org.cdsframework.dto.IceTestEventDTO;
import org.cdsframework.dto.IceTestImmunityDTO;
import org.cdsframework.dto.IceTestProposalDTO;
import org.cdsframework.dto.IceTestRecommendationDTO;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceTestGroupDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.ejb.local.IceTestMgrLocal;
import org.cdsframework.enumeration.Operation;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.group.Update;
import org.cdsframework.util.DateUtils;
import org.cdsframework.util.StringUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
public class IceTestBO extends BaseBO<IceTestDTO> {

    @EJB
    private IceTestGroupBO iceTestGroupBO;
    @EJB
    private IceTestMgrLocal iceTestMgrLocal;
//
//    @Override
//    protected void validateAddOrUpdate(IceTestDTO baseDTO, Operation operation, Class queryClass, List<Class> validationClasses, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
//            throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException, AuthorizationException {
//        if (baseDTO != null) {
//            if (!baseDTO.isOffsetBased() && baseDTO.getDob() == null) {
//                throw new ValidationException(new BrokenRule(CoreErrorCode.FieldCanNotBeEmpty, "DOB empty!"));
//            }
//        }
//    }

    @Override
    protected void preAdd(IceTestDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ConstraintViolationException, NotFoundException, MtsException, ValidationException, AuthenticationException,
            AuthorizationException {
        IceTestDTO testDTO = baseDTO;
        addEncodedName(testDTO);
    }

    @Override
    protected void preUpdate(IceTestDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ConstraintViolationException, NotFoundException, MtsException, ValidationException, AuthenticationException,
            AuthorizationException {
        final String METHODNAME = "preUpdate ";
        IceTestDTO testDTO = baseDTO;
        addEncodedName(testDTO);
        if (testDTO.isPropertyChanged("offsetBased")) {
            logger.debug(METHODNAME, "detected offsetBased change");
            if (testDTO.isOffsetBased()) {
                logger.debug(METHODNAME, "offsetBased was turned on...");
                if (testDTO.getExecutionDate() != null && testDTO.getDob() != null) {
                    logger.debug(METHODNAME, "generating date diff for: ", testDTO.getDob(), " and ", testDTO.getExecutionDate());
                    String dateDiffYMD = DateUtils.getDateDiffYMD(testDTO.getDob(), testDTO.getExecutionDate(), true, false);
                    logger.debug(METHODNAME, " setting ageOffset to: ", dateDiffYMD);
                    testDTO.setOffset(dateDiffYMD);

                    // adjust events
                    for (IceTestEventDTO item : testDTO.getIceTestEventDTOs()) {
                        logger.debug(METHODNAME, "generating date diff for: ", testDTO.getDob(), " and ", item.getAdministrationTime());
                        String childDateDiffYMD = DateUtils.getDateDiffYMD(testDTO.getDob(), item.getAdministrationTime(), true, true);
                        item.setOffsetBased(true);
                        logger.debug(METHODNAME, " setting offsetFromAge to: ", childDateDiffYMD);
                        item.setOffset(childDateDiffYMD);
                    }

                    // adjust immunity events
                    for (IceTestImmunityDTO item : testDTO.getIceTestImmunityDTOs()) {
                        logger.debug(METHODNAME, "generating date diff for: ", testDTO.getDob(), " and ", item.getObservationEventTime());
                        String childDateDiffYMD = DateUtils.getDateDiffYMD(testDTO.getDob(), item.getObservationEventTime(), true, true);
                        item.setOffsetBased(true);
                        logger.debug(METHODNAME, " setting offsetFromAge to: ", childDateDiffYMD);
                        item.setOffset(childDateDiffYMD);
                    }

                    // adjust proposals
                    for (IceTestProposalDTO item : testDTO.getIceTestProposalDTOs()) {
                        logger.debug(METHODNAME, "generating date diff for: ", testDTO.getDob(), " and ", item.getRecommendedDate());
                        String childDateDiffYMD = DateUtils.getDateDiffYMD(testDTO.getDob(), item.getRecommendedDate(), true, true);
                        item.setOffsetBased(true);
                        logger.debug(METHODNAME, " setting offsetFromAge to: ", childDateDiffYMD);
                        item.setRecommendedOffset(childDateDiffYMD);
                    }

                } else {
                    logger.info(METHODNAME,
                            "offsetBased changed to true but either execution date or dob was null! ",
                            testDTO.getDob(),
                            " - ",
                            testDTO.getExecutionDate());
                }
            } else {
                logger.debug(METHODNAME, "offsetBased was turned off...");
            }
        } else {
            logger.debug(METHODNAME, "offsetBased was not changed...");
        }
    }

    @Override
    protected void processBegin(IceTestDTO baseDTO, Operation operation, Class queryClass, List<Class> validationClasses, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "processBegin ";

        // note whether this is a delete operation for IceTestGroup to determine whther or not to update its last mod id
        if (operation == Operation.DELETE) {
            propertyBagDTO.put("deletedIceTestDTOPresent", true);
        }

        // note whether or not there are children marked for deletion before processing begins
        if (baseDTO != null && operation != Operation.FIND) {
            boolean deletedChildPresent = false;
            // evaluated IceTestImmunityDTOs for deletes
            for (IceTestImmunityDTO iceTestImmunityDTO : baseDTO.getIceTestImmunityDTOs()) {
                if (iceTestImmunityDTO.isDeleted()) {
                    deletedChildPresent = true;
                    break;
                }
            }
            // if not immunity deletes then check IceTestEventDTOs and its children and grandchildren
            if (!deletedChildPresent) {
                // IceTestEventDTOs
                for (IceTestEventDTO iceTestEventDTO : baseDTO.getIceTestEventDTOs()) {
                    if (iceTestEventDTO.isDeleted()) {
                        deletedChildPresent = true;
                        break;
                    }
                    // IceTestEventComponentDTOs
                    for (IceTestEventComponentDTO iceTestEventComponentDTO : iceTestEventDTO.getIceTestEventComponentDTOs()) {
                        if (iceTestEventComponentDTO.isDeleted()) {
                            deletedChildPresent = true;
                            break;
                        }
                        // IceTestEvaluationDTOs
                        for (IceTestEvaluationDTO iceTestEvaluationDTO : iceTestEventComponentDTO.getIceTestEvaluationDTOs()) {
                            if (iceTestEvaluationDTO.isDeleted()) {
                                deletedChildPresent = true;
                                break;
                            }
                        }
                        if (deletedChildPresent) {
                            break;
                        }
                    }
                    if (deletedChildPresent) {
                        break;
                    }
                }
            }
            // if not event deletes then check IceTestProposalDTOs and its children and grandchildren
            if (!deletedChildPresent) {
                // IceTestProposalDTOs
                for (IceTestProposalDTO iceTestProposalDTO : baseDTO.getIceTestProposalDTOs()) {
                    if (iceTestProposalDTO.isDeleted()) {
                        deletedChildPresent = true;
                        break;
                    }
                    // IceTestRecommendationDTOs
                    for (IceTestRecommendationDTO iceTestRecommendationDTO : iceTestProposalDTO.getIceTestRecommendationDTOs()) {
                        if (iceTestRecommendationDTO.isDeleted()) {
                            deletedChildPresent = true;
                            break;
                        }
                    }
                    if (deletedChildPresent) {
                        break;
                    }
                }
            }
            // finally add a flag to the property bag if a delete is present somewhere...
            if (deletedChildPresent) {
                propertyBagDTO.put("deletedChildPresent", true);
            }
        }
    }

    /**
     * Update the IceTestGroupDTO and IceTestSuiteDTO last mod properties to
     * reflect updates in their IceTestDTOs.
     *
     * @param baseDTO
     * @param sessionDTO
     * @param propertyBagDTO
     * @throws ValidationException
     * @throws NotFoundException
     * @throws MtsException
     * @throws AuthenticationException
     * @throws AuthorizationException
     */
    private void updateParentsLastModDatetime(IceTestDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException,
            AuthorizationException {
        final String METHODNAME = "updateParentsLastModDatetime ";
        Boolean overrideCreateModProperties = propertyBagDTO.get("isOverrideCreateModProperties", false);
        logger.debug(METHODNAME, "overrideCreateModProperties=", overrideCreateModProperties);
        if (!overrideCreateModProperties) {
            try {
                if (baseDTO != null) {
                    IceTestGroupDTO iceTestGroupDTO = new IceTestGroupDTO();
                    iceTestGroupDTO.setGroupId(baseDTO.getGroupId());
                    iceTestGroupDTO = iceTestGroupBO.findByPrimaryKeyMain(iceTestGroupDTO, new ArrayList<Class>(), sessionDTO, propertyBagDTO);
                    if (iceTestGroupDTO != null) {
                        iceTestGroupDTO.setLastModDatetime(baseDTO.getLastModDatetime());
                        logger.debug(METHODNAME, "iceTestGroupBO update called!");
                        iceTestGroupBO.updateMain(iceTestGroupDTO, Update.class, sessionDTO, propertyBagDTO);
                    } else {
                        logger.warn(METHODNAME, "iceTestGroupDTO was null!");
                    }
                } else {
                    logger.error(METHODNAME, "baseDTO was null!");
                }
            } catch (ConstraintViolationException e) {
                logger.error(e);
            }
        }
    }

    @Override
    protected void processDTOCreateLastModDateId(IceTestDTO baseDTO, Operation operation, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
        final String METHODNAME = "processDTOCreateLastModDateId ";
        Boolean overrideCreateModProperties = (Boolean) propertyBagDTO.get("isOverrideCreateModProperties");
        if (overrideCreateModProperties == null || !overrideCreateModProperties) {
            logger.debug(METHODNAME, "submitting dto to super.processDTOCreateLastModDateId");
            super.processDTOCreateLastModDateId(baseDTO, operation, sessionDTO, propertyBagDTO);
        } else if (logger.isDebugEnabled()) {
            logger.info(METHODNAME, "submitting user supplied createDatetime: ", baseDTO.getCreateDatetime());
            logger.info(METHODNAME, "submitting user supplied createId: ", baseDTO.getCreateId());
            logger.info(METHODNAME, "submitting user supplied lastModDatetime: ", baseDTO.getLastModDatetime());
            logger.info(METHODNAME, "submitting user supplied lastModId: ", baseDTO.getLastModId());
        } // assume they are set and let it roll...
    }

    private void addEncodedName(IceTestDTO testDTO) {
        logger.debug("Checking encoded name...");
        if (testDTO != null && (testDTO.getEncodedName() == null || testDTO.getEncodedName().isEmpty())) {
            testDTO.setEncodedName(StringUtils.getShaHashFromString(testDTO.getName()));
        }
    }

    @Override
    protected void postUpdate(IceTestDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ConstraintViolationException, NotFoundException, MtsException, ValidationException, AuthenticationException,
            AuthorizationException {
        String operationName = propertyBagDTO.getOperationName();
        if ("copyTest".equals(operationName)) {
            iceTestMgrLocal.copyTest(baseDTO, sessionDTO, propertyBagDTO);
        }
    }

    /**
     * Override of processEnd populates the test dto's interval shot data and
     * valid vaccine list. Skips on tests and when the children aren't
     * retrieved.
     *
     * @param baseDTO
     * @param childClassDTOs
     * @param operation
     * @param queryClass
     * @param sessionDTO
     * @param propertyBagDTO
     * @throws MtsException
     * @throws ValidationException
     * @throws NotFoundException
     * @throws AuthenticationException
     * @throws AuthorizationException
     */
    @Override
    protected void processEnd(IceTestDTO baseDTO, List<Class> childClassDTOs, Operation operation, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "processEnd ";
        boolean isTestRun = propertyBagDTO.getPropertyMap().containsKey("testId")
                || propertyBagDTO.getPropertyMap().containsKey("suiteId")
                || propertyBagDTO.getPropertyMap().containsKey("groupId");
        if (!isTestRun && operation != Operation.FIND) {
            logger.debug(METHODNAME, "Calling update on parent lastModDatetime!");
            updateParentsLastModDatetime(baseDTO, sessionDTO, propertyBagDTO);

        }
    }
//
//    /**
//     * Update the IceTestDTO's last mod properties to reflect updates in the
//     * children.
//     *
//     * @param baseDTO
//     * @param sessionDTO
//     * @param propertyBagDTO
//     */
//    private void updateIceTestDTOLastModDatetimeFromChildren(IceTestDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
//            throws NotFoundException, MtsException {
//        final String METHODNAME = "updateIceTestDTOLastModDatetimeFromChildren ";
//        Boolean deletedChildPresent = propertyBagDTO.get("deletedChildPresent", false);
//        if (baseDTO != null) {
//            Date originalLastModDatetime = baseDTO.getLastModDatetime();
//            Date newLastModDatetime = originalLastModDatetime;
//            String newLastModId = baseDTO.getLastModId();
//            logger.debug(METHODNAME, "original last mod date: ", originalLastModDatetime);
//            logger.debug(METHODNAME, "original last mod id: ", newLastModId);
//            if (originalLastModDatetime != null) {
//                // if deleted children are present simply update the last mod props
//                if (deletedChildPresent) {
//                    newLastModDatetime = new Date();
//                    newLastModId = sessionDTO.getUserDTO().getUserId();
//                } else {
//                    // go though all the children and compare their last mod dates to the test last mod date and update as appropriate
//
//                    // IceTestEventDTOs
//                    for (IceTestEventDTO iceTestEventDTO : baseDTO.getIceTestEventDTOs()) {
//                        if (iceTestEventDTO != null) {
//                            logger.debug(METHODNAME, "comparing ", iceTestEventDTO.getLastModDatetime(), " to ", newLastModDatetime, " - ", iceTestEventDTO);
//                            if (iceTestEventDTO.getLastModDatetime() != null
//                                    && iceTestEventDTO.getLastModDatetime().after(newLastModDatetime)) {
//                                newLastModDatetime = iceTestEventDTO.getLastModDatetime();
//                                newLastModId = iceTestEventDTO.getLastModId();
//                                logger.debug(METHODNAME, "updating last mod date: ", newLastModDatetime, " - ", iceTestEventDTO);
//                                logger.debug(METHODNAME, "updating last mod id: ", newLastModId, " - ", iceTestEventDTO);
//                            }
//                            // IceTestEventComponentDTOs
//                            for (IceTestEventComponentDTO iceTestEventComponentDTO : iceTestEventDTO.getIceTestEventComponentDTOs()) {
//                                if (iceTestEventComponentDTO != null) {
//                                    logger.debug(METHODNAME, "comparing ", iceTestEventComponentDTO.getLastModDatetime(), " to ", newLastModDatetime, " - ", iceTestEventComponentDTO);
//                                    if (iceTestEventComponentDTO.getLastModDatetime() != null
//                                            && iceTestEventComponentDTO.getLastModDatetime().after(newLastModDatetime)) {
//                                        newLastModDatetime = iceTestEventComponentDTO.getLastModDatetime();
//                                        newLastModId = iceTestEventComponentDTO.getLastModId();
//                                        logger.debug(METHODNAME, "updating last mod date: ", newLastModDatetime, " - ", iceTestEventComponentDTO);
//                                        logger.debug(METHODNAME, "updating last mod id: ", newLastModId, " - ", iceTestEventComponentDTO);
//                                    }
//                                    // IceTestEvaluationDTOs
//                                    for (IceTestEvaluationDTO iceTestEvaluationDTO : iceTestEventComponentDTO.getIceTestEvaluationDTOs()) {
//                                        if (iceTestEvaluationDTO != null) {
//                                            logger.debug(METHODNAME, "comparing ", iceTestEvaluationDTO.getLastModDatetime(), " to ", newLastModDatetime, " - ", iceTestEvaluationDTO);
//                                            if (iceTestEvaluationDTO.getLastModDatetime() != null
//                                                    && iceTestEvaluationDTO.getLastModDatetime().after(newLastModDatetime)) {
//                                                newLastModDatetime = iceTestEvaluationDTO.getLastModDatetime();
//                                                newLastModId = iceTestEvaluationDTO.getLastModId();
//                                                logger.debug(METHODNAME, "updating last mod date: ", newLastModDatetime, " - ", iceTestEvaluationDTO);
//                                                logger.debug(METHODNAME, "updating last mod id: ", newLastModId, " - ", iceTestEvaluationDTO);
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    // IceTestProposalDTOs
//                    for (IceTestProposalDTO iceTestProposalDTO : baseDTO.getIceTestProposalDTOs()) {
//                        if (iceTestProposalDTO != null) {
//                            logger.debug(METHODNAME, "comparing ", iceTestProposalDTO.getLastModDatetime(), " to ", newLastModDatetime, " - ", iceTestProposalDTO);
//                            if (iceTestProposalDTO.getLastModDatetime() != null
//                                    && iceTestProposalDTO.getLastModDatetime().after(newLastModDatetime)) {
//                                newLastModDatetime = iceTestProposalDTO.getLastModDatetime();
//                                newLastModId = iceTestProposalDTO.getLastModId();
//                                logger.debug(METHODNAME, "updating last mod date: ", newLastModDatetime, " - ", iceTestProposalDTO);
//                                logger.debug(METHODNAME, "updating last mod id: ", newLastModId, " - ", iceTestProposalDTO);
//                            }
//                            // IceTestRecommendationDTOs
//                            for (IceTestRecommendationDTO iceTestRecommendationDTO : iceTestProposalDTO.getIceTestRecommendationDTOs()) {
//                                if (iceTestRecommendationDTO != null) {
//                                    logger.debug(METHODNAME, "comparing ", iceTestRecommendationDTO.getLastModDatetime(), " to ", newLastModDatetime, " - ", iceTestRecommendationDTO);
//                                    if (iceTestRecommendationDTO.getLastModDatetime() != null
//                                            && iceTestRecommendationDTO.getLastModDatetime().after(newLastModDatetime)) {
//                                        newLastModDatetime = iceTestRecommendationDTO.getLastModDatetime();
//                                        newLastModId = iceTestRecommendationDTO.getLastModId();
//                                        logger.debug(METHODNAME, "updating last mod date: ", newLastModDatetime, " - ", iceTestRecommendationDTO);
//                                        logger.debug(METHODNAME, "updating last mod id: ", newLastModId, " - ", iceTestRecommendationDTO);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    // IceTestImmunityDTOs
//                    for (IceTestImmunityDTO iceTestImmunityDTO : baseDTO.getIceTestImmunityDTOs()) {
//                        if (iceTestImmunityDTO != null) {
//                            logger.debug(METHODNAME, "comparing ", iceTestImmunityDTO.getLastModDatetime(), " to ", newLastModDatetime, " - ", iceTestImmunityDTO);
//                            if (iceTestImmunityDTO.getLastModDatetime() != null
//                                    && iceTestImmunityDTO.getLastModDatetime().after(newLastModDatetime)) {
//                                newLastModDatetime = iceTestImmunityDTO.getLastModDatetime();
//                                newLastModId = iceTestImmunityDTO.getLastModId();
//                                logger.debug(METHODNAME, "updating last mod date: ", newLastModDatetime, " - ", iceTestImmunityDTO);
//                                logger.debug(METHODNAME, "updating last mod id: ", newLastModId, " - ", iceTestImmunityDTO);
//                            }
//                        }
//                    }
//                }
//                // if the last mod date has changed then perform the last mod date update...
//                if (!originalLastModDatetime.equals(newLastModDatetime)) {
//                    logger.debug(METHODNAME, "setting new last mod properties: ", newLastModDatetime, " - ", newLastModId);
//                    baseDTO.setLastModDatetime(newLastModDatetime);
//                    baseDTO.setLastModId(newLastModId);
//                    try {
//                        this.getDao().update(baseDTO, IceTestDTO.UpdateLastMod.class, sessionDTO, propertyBagDTO);
//                    } catch (ConstraintViolationException e) {
//                        logger.error(e);
//                    }
//                } else {
//                    logger.debug(METHODNAME, "leaving last mod properties: ", originalLastModDatetime);
//                }
//            } else {
//                logger.error(METHODNAME, "originalLastModDatetime is null!");
//            }
//        } else {
//            logger.error(METHODNAME, "baseDTO is null!");
//        }
//    }
}
