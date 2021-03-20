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
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.enumeration.Operator;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.group.ByGeneralProperties;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
@LocalBean
public class IceTestDAO extends BaseDAO<IceTestDTO> {

    @Override
    protected void initialize() throws MtsException {

        // General search query
        this.registerDML(ByGeneralProperties.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String sql = getSelectDML();
                String testGroupId = (String) baseDTO.getQueryMap().get("group_id");
                String text = (String) baseDTO.getQueryMap().get("text");
                logger.info("ByGeneralProperties group_id: ", testGroupId);
                logger.info("ByGeneralProperties text: ", baseDTO.getQueryMap().get("text"));

                if (testGroupId != null && !testGroupId.trim().isEmpty()) {
                    setNonWildcardPredicateValue(baseDTO, " group_id = :group_id ", "group_id");
                    setWildcardPredicateValue(baseDTO, " (lower(name) like :text OR lower(rule_to_test) like :text OR lower(notes) like :text OR lower(test_id) like :text) ", "text");
                    sql += getAndClearPredicateMap(" where ", "", Operator.AND);
                }
                logger.info(sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                String testGroupId = (String) baseDTO.getQueryMap().get("group_id");
                if (testGroupId != null && !testGroupId.trim().isEmpty()) {
                    setExactQueryMapValue(baseDTO, "group_id", "group_id", namedParameters);
                    setLowerQueryMapValue(baseDTO, "text", "text", namedParameters);
                    logger.info("nameparameter group_id: ", namedParameters.getValue("group_id"));
                    logger.info("nameparameter text: ", namedParameters.getValue("text"));
                } else {
                    // no parameters
                }
            }

        }, false);

        this.registerDML(IceTestDTO.ByTestSuiteId.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return getSelectDML() + " where suite_id = :suite_id";
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
                    throws MtsException {
                final String METHODNAME = "getCallbackNamedParameters ByTestSuiteId ";
                if (baseDTO != null) {
                    if (baseDTO instanceof IceTestDTO) {
                        IceTestDTO iceTestDTO = (IceTestDTO) baseDTO;
                        namedParameters.addValue("suite_id", iceTestDTO.getSuiteId());
                    } else {
                        logger.warn(METHODNAME, "unexpected instance type: ", baseDTO);
                    }
                } else {
                    logger.warn(METHODNAME, "baseDTO is null!");
                }
            }

        }, false);

        this.registerDML(IceTestDTO.TestIdByTestSuiteId.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return "select it.test_id from ice_test it, ice_test_group itg where it.group_id = itg.group_id and itg.suite_id = :suite_id order by it.test_id";
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                logger.debug("IceTestDTO.TestIdByTestSuiteId.class called");
                namedParameters.addValue("suite_id", propertyBagDTO.get("suiteId"));
            }

        }, false);

        this.registerDML(IceTestDTO.TestIdByTestGroupId.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return "select it.test_id from ice_test it, ice_test_group itg where it.group_id = itg.group_id and it.group_id = :group_id order by it.test_id";
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                logger.debug("IceTestDTO.TestIdByTestGroupId.class called");
                namedParameters.addValue("group_id", propertyBagDTO.get("groupId"));
            }

        }, false);
//
//        this.registerDML(IceTestGroupTestDTO.ByTestGroupId.class, new QueryCallback(getDtoTableName()) {
//            @Override
//            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
//                return getSelectDML() + " where group_id = :group_id ";
//            }
//        }, false);

        this.registerDML(IceTestDTO.MaxLastModDatetimeByTestGroupId.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return "select max(last_mod_datetime) from ice_test where group_id = :group_id";
            }
        }, false);

        this.registerDML(IceTestDTO.LastModIdByLastModDatetime.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return "select last_mod_id from ice_test where last_mod_datetime = :last_mod_datetime";
            }
        }, false);

        this.registerDML(IceTestDTO.UpdateLastMod.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return "update ice_test set last_mod_datetime = :last_mod_datetime, last_mod_id = :last_mod_id where test_id = :test_id";
            }
        }, false);
    }
}
