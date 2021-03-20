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

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.cdsframework.base.BaseBO;
import org.cdsframework.dto.IceTestEventComponentDTO;
import org.cdsframework.dto.IceTestEventDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
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
public class IceTestEventComponentBO extends BaseBO<IceTestEventComponentDTO> {

    @EJB
    private IceTestEventBO iceTestEventBO;

    @Override
    protected void validateAddOrUpdate(IceTestEventComponentDTO baseDTO, Operation operation, Class queryClass, List<Class> validationClasses, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException,
            AuthorizationException {
//        List<IceTestEvaluationDTO> iceTestEvaluationDTOs = baseDTO.getIceTestEvaluationDTOs();
//        if (baseDTO.getEvaluationValue().getCode() != null
//                && (baseDTO.getEvaluationValue().getCode().equalsIgnoreCase("INVALID") || baseDTO.getEvaluationValue().getCode().equalsIgnoreCase("ACCEPTED"))
//                && iceTestEvaluationDTOs.isEmpty()) {
//            BrokenRule brokenRule = new BrokenRule("NO EVAL REASON", "Evaluation is INVALID or ACCEPTED but there is no reason specified.");
//            List<BrokenRule> brokenRules = new ArrayList<BrokenRule>();
//            brokenRules.add(brokenRule);
//            throw new ValidationException(brokenRules);
//        }
    }

    /**
     * Update the parent lastModDatetime
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
    private void updateParentsLastModDatetime(IceTestEventComponentDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException,
            AuthorizationException {
        final String METHODNAME = "updateParentsLastModDatetime ";
        Boolean overrideCreateModProperties = propertyBagDTO.get("isOverrideCreateModProperties", false);
        logger.debug(METHODNAME, "overrideCreateModProperties=", overrideCreateModProperties);
        if (!overrideCreateModProperties) {
            try {
                if (baseDTO != null) {
                    IceTestEventDTO parentDTO = new IceTestEventDTO();
                    parentDTO.setEventId(baseDTO.getEventId());
                    parentDTO = iceTestEventBO.findByPrimaryKeyMain(parentDTO, new ArrayList<Class>(), sessionDTO, propertyBagDTO);
                    if (parentDTO != null) {
                        parentDTO.setLastModDatetime(baseDTO.getLastModDatetime());
                        logger.debug(METHODNAME, "parent BO update called!");
                        iceTestEventBO.updateMain(parentDTO, Update.class, sessionDTO, propertyBagDTO);
                    } else {
                        logger.warn(METHODNAME, "parentDTO was null!");
                    }
                } else {
                    logger.error(METHODNAME, "baseDTO was null!");
                }
            } catch (ConstraintViolationException e) {
                logger.error(e);
            }
        }
    }

    /**
     * Override of processEnd
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
    protected void processEnd(IceTestEventComponentDTO baseDTO, List<Class> childClassDTOs, Operation operation, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "processEnd ";
        if (operation != Operation.FIND) {
            logger.debug(METHODNAME, "Calling update on parent lastModDatetime!");
            updateParentsLastModDatetime(baseDTO, sessionDTO, propertyBagDTO);
        }
    }
}
