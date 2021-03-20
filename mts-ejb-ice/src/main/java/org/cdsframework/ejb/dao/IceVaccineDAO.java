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
import org.cdsframework.dto.IceVaccineDTO;
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
public class IceVaccineDAO extends BaseDAO<IceVaccineDTO> {

    @Override
    protected void initialize() throws MtsException {

        // General search query
        this.registerDML(ByGeneralProperties.class, new QueryCallback(getDtoTableName(), "iv") {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String sql = "";
                String text = (String) baseDTO.getQueryMap().get("text");
                String groupId = (String) baseDTO.getQueryMap().get("group_id");
                String versionId = (String) baseDTO.getQueryMap().get("version_id");
                logger.debug("ByGeneralProperties text: ", text);
                logger.debug("ByGeneralProperties group_id: ", groupId);
                logger.debug("ByGeneralProperties version_id: ", versionId);
                if ((text != null && !text.trim().isEmpty()) || (groupId != null && !groupId.trim().isEmpty()) || (versionId != null && !versionId.trim().isEmpty())) {
                    setWildcardPredicateValue(baseDTO,
                            "     (lower(iv.trade_name) like :text "
                            + " OR lower(cc.display_name) like :text "
                            + " OR lower(cc.code) like :text "
                            + " OR lower(iv.vaccine_name) like :text) ", "text");
                    setNonWildcardPredicateValue(baseDTO,
                            " exists (select * from ice_vaccine_version_rel ivvr "
                            + "       where iv.VACCINE_ID = ivvr.VACCINE_ID "
                            + " and ivvr.version_id = :version_id) ", "version_id");
                    setNonWildcardPredicateValue(baseDTO,
                            " exists (select * from ice_vaccine_component_rel ivcr, ice_vaccine_component ivc, "
                            + " ice_vaccine_group ivg, ice_vaccine_group_disease_rel ivgdr, "
                            + "                     ice_vaccine_disease_rel ivdr "
                            + "       where iv.VACCINE_ID = ivcr.VACCINE_ID "
                            + "         and ivcr.COMPONENT_ID = ivc.COMPONENT_ID "
                            + "         and ivc.COMPONENT_ID = ivdr.COMPONENT_ID "
                            + "         and ivdr.DISEASE_CODE_ID = ivgdr.DISEASE_CODE_ID "
                            + "         and ivgdr.GROUP_ID = ivg.GROUP_ID "
                            + "         and ivg.vaccine_group_id = :group_id ) ", "group_id");
                    sql = "select iv.*, cc.display_name, cc.code from ice_vaccine iv, cds_code cc "
                            + " where cc.CODE_ID = iv.VACCINE_CODE_ID "
                            + getAndClearPredicateMap(" and ( ", " ) ", Operator.AND);
                } else {
                    sql = getSelectDML();
                }
                logger.debug(sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                String text = (String) baseDTO.getQueryMap().get("text");
                String groupId = (String) baseDTO.getQueryMap().get("group_id");
                String versionId = (String) baseDTO.getQueryMap().get("version_id");
                if (text != null && !text.trim().isEmpty()) {
                    setLowerQueryMapValue(baseDTO, "text", "text", namedParameters);
                    logger.debug("nameparameter text: ", namedParameters.getValue("text"));
                }
                if (groupId != null && !groupId.trim().isEmpty()) {
                    setExactQueryMapValue(baseDTO, "group_id", "group_id", namedParameters);
                    logger.debug("nameparameter group_id: ", namedParameters.getValue("group_id"));
                }
                if (versionId != null && !versionId.trim().isEmpty()) {
                    setExactQueryMapValue(baseDTO, "version_id", "version_id", namedParameters);
                    logger.debug("nameparameter version_id: ", namedParameters.getValue("version_id"));
                }
            }

        }, false);

        this.registerDML(IceVaccineDTO.ByVaccineCode.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return getSelectDML() + " where lower(code) = :code";
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                IceVaccineDTO parameter = (IceVaccineDTO) baseDTO;
                namedParameters.addValue("code", parameter.getVaccine() != null ? parameter.getVaccine().getCode() : null);
            }

        }, false);

        this.registerDML(IceVaccineDTO.ByTestId.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String text = (String) baseDTO.getQueryMap().get("text");
                String testId = (String) baseDTO.getQueryMap().get("test_id");
                logger.info("getQueryDML ByTestId text=", text);
                logger.info("getQueryDML ByTestId testId=", testId);
                String sql = " select * from vw_ice_test_vaccine_group_vaccine where test_id = :test_id";
                setWildcardPredicateValue(baseDTO,
                        "     (lower(trade_name) like :text "
                        + " OR lower(display_name) like :text "
                        + " OR lower(code) like :text "
                        + " OR lower(vaccine_name) like :text) ", "text");

                sql += getAndClearPredicateMap(" and ", "", Operator.AND);
                logger.info("getQueryDML ByTestId sql=", sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                String text = (String) baseDTO.getQueryMap().get("text");
                String testId = (String) baseDTO.getQueryMap().get("test_id");
                if (text != null && !text.trim().isEmpty()) {
                    setLowerQueryMapValue(baseDTO, "text", "text", namedParameters);
                    logger.debug("nameparameter text: ", namedParameters.getValue("text"));
                }
                if (testId != null && !testId.trim().isEmpty()) {
                    setExactQueryMapValue(baseDTO, "test_id", "test_id", namedParameters);
                    logger.debug("nameparameter test_id: ", namedParameters.getValue("test_id"));
                }
            }

        }, false);

        this.registerDML(IceVaccineDTO.BySeriesId.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String text = (String) baseDTO.getQueryMap().get("text");
                String seriesId = (String) baseDTO.getQueryMap().get("series_id");
                logger.info("getQueryDML BySeriesId text=", text);
                logger.info("getQueryDML BySeriesId seriesId=", seriesId);
                String sql = " select * from vw_ice_vaccine_series where series_id = :series_id";
                setWildcardPredicateValue(baseDTO,
                        "     (lower(trade_name) like :text "
                        + " OR lower(display_name) like :text "
                        + " OR lower(code) like :text "
                        + " OR lower(vaccine_name) like :text) ", "text");

                sql += getAndClearPredicateMap(" and ", "", Operator.AND);
                logger.info("getQueryDML BySeriesId sql=", sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                setLowerQueryMapValue(baseDTO, "text", "text", namedParameters);
                logger.info("BySeriesId nameparameter text: ", namedParameters.getValue("text"));
                setExactQueryMapValue(baseDTO, "series_id", "series_id", namedParameters);
                logger.info("BySeriesId nameparameter series_id: ", namedParameters.getValue("series_id"));
            }

        }, false);

    }
}
