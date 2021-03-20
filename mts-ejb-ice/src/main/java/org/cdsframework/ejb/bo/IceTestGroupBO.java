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
import org.cdsframework.dto.IceTestGroupDTO;
import org.cdsframework.dto.IceTestSuiteDTO;
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

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
public class IceTestGroupBO extends BaseBO<IceTestGroupDTO> {

    @EJB
    private IceTestBO iceTestBO;
    @EJB
    private IceTestMgrLocal iceTestMgrLocal;
    @EJB
    private IceTestSuiteBO iceTestSuiteBO;

    @Override
    protected void preInitialize() throws MtsException {
        // This makes sure the IceTestBO is always initialized before this BO
        iceTestBO.getConfiguration();
    }

    @Override
    protected void postUpdate(IceTestGroupDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ConstraintViolationException, NotFoundException, MtsException, ValidationException, AuthenticationException,
            AuthorizationException {
        final String METHODNAME = "postUpdate ";
      //  long start = System.nanoTime();

        String operationName = propertyBagDTO.getOperationName();
        logger.debug("IceTestGroupBO postUpdate called: ", operationName);
        if ("mergeGroup".equals(operationName)) {
            iceTestMgrLocal.mergeGroup(baseDTO, sessionDTO, propertyBagDTO);
        } else if ("copyGroup".equals(operationName)) {
            iceTestMgrLocal.copyGroup(baseDTO, sessionDTO, propertyBagDTO);
        }
     //   logger.logDuration(METHODNAME, start);
    }

    @Override
    protected void processDTOCreateLastModDateId(IceTestGroupDTO baseDTO, Operation operation, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
        final String METHODNAME = "processDTOCreateLastModDateId ";
        Boolean overrideCreateModProperties = (Boolean) propertyBagDTO.get("isOverrideCreateModProperties");
        if (overrideCreateModProperties == null || !overrideCreateModProperties) {
            logger.debug(METHODNAME, "submitting dto to super.processDTOCreateLastModDateId");
            super.processDTOCreateLastModDateId(baseDTO, operation, sessionDTO, propertyBagDTO);
        } else {
            if (logger.isDebugEnabled()) {
                logger.info(METHODNAME, "submitting user supplied createDatetime: ", baseDTO.getCreateDatetime());
                logger.info(METHODNAME, "submitting user supplied createId: ", baseDTO.getCreateId());
                logger.info(METHODNAME, "submitting user supplied lastModDatetime: ", baseDTO.getLastModDatetime());
                logger.info(METHODNAME, "submitting user supplied lastModId: ", baseDTO.getLastModId());
            }
            // assume they are set and let it roll...
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
    private void updateParentsLastModDatetime(IceTestGroupDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException,
            AuthorizationException {
        final String METHODNAME = "updateParentsLastModDatetime ";
        Boolean overrideCreateModProperties = propertyBagDTO.get("isOverrideCreateModProperties", false);
        logger.debug(METHODNAME, "overrideCreateModProperties=", overrideCreateModProperties);
        if (!overrideCreateModProperties) {
            try {
                if (baseDTO != null) {
                    IceTestSuiteDTO iceTestSuiteDTO = new IceTestSuiteDTO();
                    iceTestSuiteDTO.setSuiteId(baseDTO.getSuiteId());
                    iceTestSuiteDTO = iceTestSuiteBO.findByPrimaryKeyMain(iceTestSuiteDTO, new ArrayList<Class>(), sessionDTO, propertyBagDTO);
                    if (iceTestSuiteDTO != null) {
                        iceTestSuiteDTO.setLastModDatetime(baseDTO.getLastModDatetime());
                        logger.debug(METHODNAME, "iceTestSuiteBO update called!");
                        iceTestSuiteBO.updateMain(iceTestSuiteDTO, Update.class, sessionDTO, propertyBagDTO);
                    } else {
                        logger.warn(METHODNAME, "iceTestSuiteDTO was null!");
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
    protected void processEnd(IceTestGroupDTO baseDTO, List<Class> childClassDTOs, Operation operation, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "processEnd ";
        if (operation != Operation.FIND) {
            logger.debug(METHODNAME, "Calling update on parent lastModDatetime!");
            updateParentsLastModDatetime(baseDTO, sessionDTO, propertyBagDTO);
        }
//        Boolean overrideCreateModProperties = propertyBagDTO.get("isOverrideCreateModProperties", false);
//        Boolean deletedIceTestDTOPresent = propertyBagDTO.get("deletedIceTestDTOPresent", false);
//        if (!overrideCreateModProperties && operation == Operation.UPDATE) {
//            try {
//                Date originalLastModDatetime = baseDTO.getLastModDatetime();
//                Date newLastModDatetime = originalLastModDatetime;
//                String newLastModId = baseDTO.getLastModId();
//                if (originalLastModDatetime != null) {
//                    if (deletedIceTestDTOPresent || newLastModDatetime == null) {
//                        newLastModDatetime = new Date();
//                        newLastModId = sessionDTO.getUserDTO().getUserId();
//                    } else {
//                        IceTestDTO iceTestDTO = new IceTestDTO();
//                        iceTestDTO.setGroupId(baseDTO.getGroupId());
//                        try {
//                            Date maxLastModDatetime = iceTestBO.findObjectByQueryMain(iceTestDTO, IceTestDTO.MaxLastModDatetimeByTestGroupId.class, sessionDTO, Date.class, propertyBagDTO);
//                            if (maxLastModDatetime == null) {
//                                maxLastModDatetime = new Date();
//                            }
//                            if (newLastModDatetime.before(maxLastModDatetime)) {
//                                newLastModDatetime = maxLastModDatetime;
//                                iceTestDTO.setLastModDatetime(maxLastModDatetime);
//                                PropertyBagDTO altPropertyBagDTO = new PropertyBagDTO();
//                                altPropertyBagDTO.put("adHocQueryLimit", 1);
//                                newLastModId = iceTestBO.findObjectByQueryMain(iceTestDTO, IceTestDTO.LastModIdByLastModDatetime.class, sessionDTO, String.class, altPropertyBagDTO);
//                            }
//                        } catch (NotFoundException e) {
//                            // no hacer nada
//                        }
//                    }
//                    if (originalLastModDatetime != newLastModDatetime) {
//                        baseDTO.setLastModDatetime(newLastModDatetime);
//                        baseDTO.setLastModId(newLastModId);
//                        this.getDao().update(baseDTO, IceTestGroupDTO.UpdateLastMod.class, sessionDTO, propertyBagDTO);
//                    }
//                }
//            } catch (ConstraintViolationException e) {
//                logger.error(e);
//            }
//        }
    }
}
