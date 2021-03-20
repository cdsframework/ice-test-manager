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
package org.cdsframework.ejb.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.cdsframework.base.BaseDAO;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.callback.QueryCallback;
import org.cdsframework.dto.IceTestEventDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.enumeration.Operator;
import org.cdsframework.exceptions.MtsException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
@LocalBean
public class IceTestEventDAO extends BaseDAO<IceTestEventDTO> {

    @Override
    protected void initialize() throws MtsException {

        this.registerDML(IceTestEventDTO.ByTestIdEventId.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String testId = (String) baseDTO.getQueryMap().get("test_id");
                String eventId = (String) baseDTO.getQueryMap().get("event_id");
                logger.info("getQueryDML ByTestIdEventId testId=", testId);
                logger.info("getQueryDML ByTestIdEventId eventId=", eventId);
                String sql = " select * from vw_ice_test_event where test_id = :test_id";
                setNonWildcardPredicateValue(baseDTO, " event_id != :event_id ", "event_id");
                sql += getAndClearPredicateMap(" and ", "", Operator.AND);
                logger.info("getQueryDML ByTestId sql=", sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                String testId = (String) baseDTO.getQueryMap().get("test_id");
                String eventId = (String) baseDTO.getQueryMap().get("event_id");
                if (testId != null && !testId.trim().isEmpty()) {
                    setExactQueryMapValue(baseDTO, "test_id", "test_id", namedParameters);
                    logger.info("nameparameter test_id: ", namedParameters.getValue("test_id"));
                }
                if (eventId != null && !eventId.trim().isEmpty()) {
                    setExactQueryMapValue(baseDTO, "event_id", "event_id", namedParameters);
                    logger.info("nameparameter event_id: ", namedParameters.getValue("event_id"));
                }
            }

        }, false);
    }

}
