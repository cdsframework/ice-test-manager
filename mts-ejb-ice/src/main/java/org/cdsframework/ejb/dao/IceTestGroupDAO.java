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
import org.cdsframework.dto.IceTestGroupDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.enumeration.Operator;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.group.ByGeneralProperties;
import org.cdsframework.util.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
@LocalBean
public class IceTestGroupDAO extends BaseDAO<IceTestGroupDTO> {

    final private String groupIdTextSql =
            " select itg.*, its.name suite_name, its.version_id, "
            + "    (select count(*) from ice_test it where itg.group_id = it.group_id and ("
            + "          lower(it.name) like :text OR "
            + "          lower(it.rule_to_test) like :text OR "
            + "          lower(it.notes) like :text OR "
            + "          lower(it.test_id) like :text "
            + "         )) num_of_tests "
            + "     from ice_test_group itg "
            + "     join ice_test_suite its on its.suite_id = itg.suite_id "
            + " where group_id = :group_id order by lower(itg.name)";

    final private String textSql =
            " select itg.*, its.name suite_name, its.version_id, a.num_of_tests "
            + " from ice_test_group itg right join "
            + "         ( select it.group_id, count(*) num_of_tests from ice_test it where"
            + "          lower(it.name) like :text OR "
            + "          lower(it.rule_to_test) like :text OR "
            + "          lower(it.notes) like :text OR "
            + "          lower(it.test_id) like :text "
            + "          group by it.group_id "
            + "         ) a on itg.group_id = a.group_id "
            + "    join ice_test_suite its on its.suite_id = itg.suite_id "
            + " where itg.suite_id = :suite_id order by lower(itg.name)";

    @Override
    protected void initialize() throws MtsException {

        registerDML(ByGeneralProperties.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String suiteId = (String) baseDTO.getQueryMap().get("suite_id");
                String groupId = (String) baseDTO.getQueryMap().get("group_id");
                String text = (String) baseDTO.getQueryMap().get("text");
                String sql;
                if (!StringUtils.isEmpty(text)) {
                    baseDTO.getQueryMap().put("text", "%" + text.trim() + "%");
                }
                if (!StringUtils.isEmpty(groupId)) {
                    if (StringUtils.isEmpty(text)) {
                        sql = getSelectDML() + " where group_id = :group_id " + getOrderByDML();
                    } else {
                        sql = groupIdTextSql;
                    }
                } else if (!StringUtils.isEmpty(text)) {
                    sql = textSql;
                } else {
                    sql = getSelectDML() + " where suite_id = :suite_id " + getOrderByDML();
                }
                sql += getAndClearPredicateMap(" and ", "", Operator.AND);
                logger.info("getQueryDML ByGeneralProperties sql=", sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                setExactQueryMapValue(baseDTO, "suite_id", "suite_id", namedParameters);
                setExactQueryMapValue(baseDTO, "group_id", "group_id", namedParameters);
                setLowerQueryMapValue(baseDTO, "text", "text", namedParameters);
                logger.info("ByGeneralProperties variable - suite_id:", namedParameters.getValue("suite_id"));
                logger.info("ByGeneralProperties variable - group_id:", namedParameters.getValue("group_id"));
                logger.info("ByGeneralProperties variable - text:", namedParameters.getValue("text"));
            }

        }, false);

        this.registerDML(IceTestGroupDTO.UpdateLastMod.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return "update ice_test_group set last_mod_datetime = :last_mod_datetime, last_mod_id = :last_mod_id where group_id = :group_id";
            }
        }, false);

        this.registerDML(IceTestGroupDTO.LastModIdByLastModDatetime.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return "select last_mod_id from ice_test_group where last_mod_datetime = :last_mod_datetime";
            }
        }, false);

        this.registerDML(IceTestGroupDTO.ByTestSuiteIdName.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return getSelectDML() + " where suite_id = :suite_id and name = :name";
            }
        }, false);

        this.registerDML(IceTestGroupDTO.MaxLastModDatetimeByTestSuiteId.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return "select max(last_mod_datetime) from ice_test_group where suite_id = :suite_id";
            }
        }, false);

    }

}
