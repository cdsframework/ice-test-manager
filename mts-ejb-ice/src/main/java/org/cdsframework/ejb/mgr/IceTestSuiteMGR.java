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
package org.cdsframework.ejb.mgr;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.cdsframework.base.BaseMGR;
import org.cdsframework.dto.IceTestResultDTO;
import org.cdsframework.dto.IceTestSuiteDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.ejb.bo.IceTestResultBO;
import org.cdsframework.ejb.timer.IceTestRunTimer;
import org.cdsframework.ejb.remote.IceTestSuiteMGRRemote;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class IceTestSuiteMGR extends BaseMGR<IceTestSuiteDTO> implements IceTestSuiteMGRRemote {

    @EJB
    private IceTestResultBO iceTestResultBO;
    @EJB
    private IceTestRunTimer iceTestRunTimer;

    @Override
    public IceTestResultDTO runTest(String testId, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException,
            ConstraintViolationException {
        propertyBagDTO.put("testId", testId);
        IceTestResultDTO result = iceTestResultBO.customQueryMain(new IceTestResultDTO(), IceTestResultDTO.RunTests.class, new ArrayList<Class>(), sessionDTO, propertyBagDTO);
        return result;
    }

    @Override
    public List<UUID> queueTests(List<String> testIds) {
//        return iceTestResultBO.queueTests(testIds);
        return iceTestRunTimer.queueTests(testIds);
    }

    @Override
    public List<IceTestResultDTO> getCompletedTests(List<UUID> uuids) {
//        return iceTestResultBO.getCompletedTests(uuids);
        return iceTestRunTimer.getCompletedTests(uuids);
    }

    @Override
    public void cancelTests(List<UUID> uuids) {
        iceTestRunTimer.cancelTests(uuids);
    }
}