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
import org.cdsframework.dto.IceTestSuiteDTO;
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
public class IceTestSuiteDAO extends BaseDAO<IceTestSuiteDTO> {

    @Override
    protected void initialize() throws MtsException {

        registerDML(ByGeneralProperties.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {

                String sql;
                String versionId = (String) baseDTO.getQueryMap().get("version_id");
                String suiteId = (String) baseDTO.getQueryMap().get("suite_id");
                String groupId = (String) baseDTO.getQueryMap().get("group_id");
                String text = (String) baseDTO.getQueryMap().get("text");
                if (!StringUtils.isEmpty(text)) {
                    baseDTO.getQueryMap().put("text", "%" + text.trim() + "%");
                }
                if (!StringUtils.isEmpty(groupId)) {
                    if (StringUtils.isEmpty(text)) {
                        sql = groupIdGeneralSql;
                    } else {
                        sql = groupIdTextGeneralSql;
                    }
                } else if (!StringUtils.isEmpty(suiteId)) {
                    if (StringUtils.isEmpty(text)) {
                        sql = defaultGeneralSql;
                    } else {
                        sql = textGeneralSql;
                    }
                    setNonWildcardPredicateValue(baseDTO, " its.suite_id = :suite_id ", "suite_id");
                } else if (!StringUtils.isEmpty(versionId)) {
                    if (StringUtils.isEmpty(text)) {
                        sql = defaultGeneralSql;
                    } else {
                        sql = textGeneralSql;
                    }
                    setNonWildcardPredicateValue(baseDTO, " its.version_id = :version_id ", "version_id");
                } else if (!StringUtils.isEmpty(text)) {
                    sql = textGeneralSql;
                } else {
                    sql = defaultGeneralSql;
                }
                sql += getAndClearPredicateMap(" and ", "", Operator.AND) + " order by lower(its.name) ";
                logger.info("getQueryDML sql=", sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                setExactQueryMapValue(baseDTO, "version_id", "version_id", namedParameters);
                setExactQueryMapValue(baseDTO, "suite_id", "suite_id", namedParameters);
                setExactQueryMapValue(baseDTO, "group_id", "group_id", namedParameters);
                setLowerQueryMapValue(baseDTO, "text", "text", namedParameters);
                logger.info("ByGeneralProperties variable - version_id:", namedParameters.getValue("version_id"));
                logger.info("ByGeneralProperties variable - suite_id:", namedParameters.getValue("suite_id"));
                logger.info("ByGeneralProperties variable - group_id:", namedParameters.getValue("group_id"));
                logger.info("ByGeneralProperties variable - text:", namedParameters.getValue("text"));
            }

        }, false);

        this.registerDML(IceTestSuiteDTO.UpdateLastMod.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return "update ice_test_suite set last_mod_datetime = :last_mod_datetime, last_mod_id = :last_mod_id where suite_id = :suite_id";
            }
        }, false);

    }

    final static private String defaultGeneralSql
            = " select its.*, a.num_of_groups, b.num_of_tests "
            + " from "
            + " ice_test_suite its left join"
            + "    (select itg.suite_id, count(*) num_of_groups "
            + "     from ice_test_group itg "
            + "     group by itg.suite_id "
            + "    ) a on its.suite_id = a.suite_id, "
            + " ice_test_suite its2 left join "
            + "    (select itg.suite_id, count(*) num_of_tests "
            + "     from ice_test it, ice_test_group itg "
            + "     where itg.group_id = it.group_id "
            + "     group by itg.suite_id "
            + "    ) b on its2.suite_id = b.suite_id "
            + " where its.suite_id = its2.suite_id ";

    final static private String groupIdGeneralSql
            = " select its.*, a.num_of_groups, b.num_of_tests "
            + " from "
            + " ice_test_suite its right join"
            + "    (select itg1.suite_id, count(*) num_of_groups "
            + "     from ice_test_group itg1 where itg1.group_id = :group_id "
            + "     group by itg1.suite_id "
            + "    ) a on its.suite_id = a.suite_id, "
            + " ice_test_suite its2 left join "
            + "    (select itg2.suite_id, count(*) num_of_tests "
            + "     from ice_test it, ice_test_group itg2 "
            + "     where itg2.group_id = it.group_id and itg2.group_id = :group_id"
            + "     group by itg2.suite_id "
            + "    ) b on its2.suite_id = b.suite_id,"
            + " ice_test_group itg "
            + " where its.suite_id = its2.suite_id and its.suite_id = itg.suite_id and itg.group_id = :group_id  ";

    final static private String textGeneralSql
            = " select its.*, a.num_of_groups, b.num_of_tests "
            + " from "
            + " ice_test_suite its right join "
            + "    (select itg.suite_id, count(*) num_of_groups "
            + "     from ice_test_group itg "
            + "     where itg.group_id in "
            + "        (select it.group_id "
            + "         from ice_test it "
            + "         where it.group_id = itg.group_id and ("
            + "              lower(it.name) like :text OR "
            + "              lower(it.rule_to_test) like :text OR "
            + "              lower(it.notes) like :text OR "
            + "              lower(it.test_id) like :text "
            + "             ) "
            + "        ) "
            + "     group by itg.suite_id "
            + "    ) a on its.suite_id = a.suite_id, "
            + " ice_test_suite its2 right join "
            + "    (select itg.suite_id, count(*) num_of_tests "
            + "     from ice_test it, ice_test_group itg "
            + "     where itg.group_id = it.group_id and ("
            + "          lower(it.name) like :text OR "
            + "          lower(it.rule_to_test) like :text OR "
            + "          lower(it.notes) like :text OR "
            + "          lower(it.test_id) like :text "
            + "         ) "
            + "     group by itg.suite_id "
            + "    ) b on its2.suite_id = b.suite_id "
            + " where its.suite_id = its2.suite_id ";

    final static private String groupIdTextGeneralSql
            = " select its.*, a.num_of_groups, b.num_of_tests "
            + " from "
            + " ice_test_suite its right join "
            + "    (select itg1.suite_id, count(*) num_of_groups "
            + "     from ice_test_group itg1 "
            + "     where itg1.group_id = :group_id and itg1.group_id in "
            + "        (select it.group_id "
            + "         from ice_test it "
            + "         where it.group_id = itg1.group_id and ("
            + "              lower(it.name) like :text OR "
            + "              lower(it.rule_to_test) like :text OR "
            + "              lower(it.notes) like :text OR "
            + "              lower(it.test_id) like :text "
            + "             ) "
            + "        ) "
            + "     group by itg1.suite_id "
            + "    ) a on its.suite_id = a.suite_id, "
            + " ice_test_suite its2 right join "
            + "    (select itg2.suite_id, count(*) num_of_tests "
            + "     from ice_test it, ice_test_group itg2 "
            + "     where itg2.group_id = :group_id and itg2.group_id = it.group_id and ("
            + "          lower(it.name) like :text OR "
            + "          lower(it.rule_to_test) like :text OR "
            + "          lower(it.notes) like :text OR "
            + "          lower(it.test_id) like :text "
            + "         ) "
            + "     group by itg2.suite_id "
            + "    ) b on its2.suite_id = b.suite_id,"
            + " ice_test_group itg "
            + " where its.suite_id = its2.suite_id and its.suite_id = itg.suite_id and itg.group_id = :group_id ";

}
