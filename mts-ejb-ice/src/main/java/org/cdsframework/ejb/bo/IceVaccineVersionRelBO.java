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

import java.util.List;
import javax.ejb.Stateless;
import org.cdsframework.base.BaseBO;
import org.cdsframework.dto.CdsVersionDTO;
import org.cdsframework.dto.IceVaccineVersionRelDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.enumeration.CoreErrorCode;
import org.cdsframework.enumeration.Operation;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.util.BrokenRule;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
public class IceVaccineVersionRelBO extends BaseBO<IceVaccineVersionRelDTO> {

    @Override
    protected void validateAddOrUpdate(
            IceVaccineVersionRelDTO baseDTO,
            Operation operation,
            Class queryClass,
            List<Class> validationClasses,
            SessionDTO sessionDTO,
            PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, ConstraintViolationException, MtsException, AuthenticationException,
            AuthorizationException {
        IceVaccineVersionRelDTO queryDTO = new IceVaccineVersionRelDTO();
        queryDTO.setVaccineId(baseDTO.getVaccineId());
        CdsVersionDTO cdsVersionDTO = new CdsVersionDTO();
        cdsVersionDTO.setVersionId(baseDTO.getCdsVersionDTO() != null ? baseDTO.getCdsVersionDTO().getVersionId() : null);
        queryDTO.setCdsVersionDTO(cdsVersionDTO);
        Integer existingObjectCount = findObjectByQueryMain(queryDTO, IceVaccineVersionRelDTO.ByCdsCodeIdVaccineId.class, sessionDTO, Integer.class, propertyBagDTO);
        if (existingObjectCount > 0) {
            BrokenRule brokenRule = new BrokenRule(
                    CoreErrorCode.AlreadyExists,
                    "A vaccine has already been defined for this vaccine code and version "
                    + baseDTO.getCdsVersionDTO().getBusinessId()
                    + " - "
                    + baseDTO.getCdsVersionDTO().getScopingEntityId()
                    + " - "
                    + baseDTO.getCdsVersionDTO().getName()
                    + " - "
                    + baseDTO.getCdsVersionDTO().getVersion(), "version");
            throw new ValidationException(brokenRule);
        }
    }
    
}
