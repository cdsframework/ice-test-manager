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
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
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
public class IceVaccineGroupDAO extends BaseDAO<IceVaccineGroupDTO> {

    @Override
    protected void initialize() throws MtsException {

        // General search query
        this.registerDML(ByGeneralProperties.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String sql;
                String text = (String) baseDTO.getQueryMap().get("text");
                String version_id = (String) baseDTO.getQueryMap().get("version_id");
                logger.info("ByGeneralProperties text: ", baseDTO.getQueryMap().get("text"));
                logger.info("ByGeneralProperties version_id: ", version_id);
                if (text != null && !text.trim().isEmpty()) {
                    setWildcardPredicateValue(baseDTO, " (lower(display_name) like :text OR lower(code) like :text) ", "text");
                }
                if (version_id != null && !version_id.trim().isEmpty()) {
                    setNonWildcardPredicateValue(baseDTO, " exists (select * from ice_vaccine_group_version_rel ivvr where "
                            + " vw_ice_vaccine_group.group_id = ivvr.group_ID and ivvr.version_id = :version_id) ", "version_id");
                }
                setNonWildcardPredicateValue(baseDTO, " exists (select null from ice_vaccine_group_version_rel ivgvr, ice_series_version_rel isvr where ivgvr.version_id = isvr.version_id and ivgvr.group_id = vw_ice_vaccine_group.group_id and isvr.series_id = :series_id) ", "series_id");
                sql = getSelectDML() + getAndClearPredicateMap(" where ", "", Operator.AND);
                logger.info("ByGeneralProperties sql=", sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                String text = (String) baseDTO.getQueryMap().get("text");
                String versionId = (String) baseDTO.getQueryMap().get("version_id");
                String seriesId = (String) baseDTO.getQueryMap().get("series_id");
                if (text != null && !text.trim().isEmpty()) {
                    setLowerQueryMapValue(baseDTO, "text", "text", namedParameters);
                    logger.info("ByGeneralProperties nameparameter text: ", namedParameters.getValue("text"));
                }
                if (versionId != null && !versionId.trim().isEmpty()) {
                    setExactQueryMapValue(baseDTO, "version_id", "version_id", namedParameters);
                    logger.info("ByGeneralProperties nameparameter version_id: ", namedParameters.getValue("version_id"));
                }
                if (seriesId != null && !seriesId.trim().isEmpty()) {
                    setExactQueryMapValue(baseDTO, "series_id", "series_id", namedParameters);
                    logger.info("ByGeneralProperties nameparameter series_id: ", namedParameters.getValue("series_id"));
                }
            }

        }, false);

        this.registerDML(IceVaccineGroupDTO.ByTestIdVacId.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String sql = "select * from vw_ice_test_vac_vac_group where test_id = :test_id and vaccine_id = :vaccine_id";
                logger.info("IceVaccineGroupDTO.ByTestIdVacId sql=", sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                if (baseDTO != null) {
                    String testId = (String) baseDTO.getQueryMap().get("test_id");
                    String vaccineId = (String) baseDTO.getQueryMap().get("vaccine_id");

                    if (testId != null) {
                        namedParameters.addValue("test_id", testId);
                    }
                    if (vaccineId != null) {
                        namedParameters.addValue("vaccine_id", vaccineId);
                    }
                } else {
                    logger.error("IceVaccineGroupDTO.ByTestIdVacId: baseDTO is null!");
                }
            }

        }, false);

        this.registerDML(IceVaccineGroupDTO.ByTestId.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String sql = "select * from vw_ice_vaccine_group_test where test_id = :test_id";
                logger.info("IceVaccineGroupDTO.ByTestId sql=", sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                if (baseDTO != null) {
                    String testId = (String) baseDTO.getQueryMap().get("test_id");

                    if (testId != null) {
                        namedParameters.addValue("test_id", testId);
                    }
                } else {
                    logger.error("IceVaccineGroupDTO.ByTestId: baseDTO is null!");
                }
            }

        }, false);

        this.registerDML(IceVaccineGroupDTO.ByTestIdVacComponentId.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String sql = "select * from vw_ice_test_vac_comp_vac_group where test_id = :test_id";
                setNonWildcardPredicateValue(baseDTO, " component_id = :component_id ", "component_id");
                sql += getAndClearPredicateMap(" and  ", "", Operator.AND);
                logger.info("IceVaccineGroupDTO.ByTestIdVacComponentId sql=", sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                if (baseDTO != null) {
                    String testId = (String) baseDTO.getQueryMap().get("test_id");
                    String componentId = (String) baseDTO.getQueryMap().get("component_id");

                    if (testId != null) {
                        namedParameters.addValue("test_id", testId);
                    }
                    if (componentId != null) {
                        namedParameters.addValue("component_id", componentId);
                    }
                } else {
                    logger.error("IceVaccineGroupDTO.ByTestIdVacComponentId: baseDTO is null!");
                }
            }

        }, false);

        this.registerDML(IceVaccineGroupDTO.ByVaccineGroupNameOrCode.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String sql = getSelectDML() + " where lower(display_name) = :display_name or lower(code) = :code";
                logger.info("IceVaccineGroupDTO.ByVaccineGroupNameOrCode sql=", sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                if (baseDTO != null) {
                    if (baseDTO instanceof IceVaccineGroupDTO) {
                        IceVaccineGroupDTO iceVaccineGroupDTO = (IceVaccineGroupDTO) baseDTO;
                        CdsCodeDTO cdsCodeDTO = iceVaccineGroupDTO.getVaccineGroup();
                        if (cdsCodeDTO != null) {
                            if (cdsCodeDTO.getCode() != null) {
                                if (cdsCodeDTO.getDisplayName() != null) {
                                    namedParameters.addValue("code", cdsCodeDTO.getCode().toLowerCase());
                                    namedParameters.addValue("display_name", cdsCodeDTO.getDisplayName().toLowerCase());
                                } else {
                                    logger.error("IceVaccineGroupDTO.ByVaccineGroupNameOrCode: cdsCodeDTO.getDisplayName() is null!");
                                }
                            } else {
                                logger.error("IceVaccineGroupDTO.ByVaccineGroupNameOrCode: cdsCodeDTO.getCode() is null!");
                            }
                        } else {
                            logger.error("IceVaccineGroupDTO.ByVaccineGroupNameOrCode: cdsCodeDTO is null!");
                        }
                    } else {
                        logger.error("IceVaccineGroupDTO.ByVaccineGroupNameOrCode: unexpected DTO type - " + baseDTO.getClass());
                    }
                } else {
                    logger.error("IceVaccineGroupDTO.ByVaccineGroupNameOrCode: baseDTO is null!");
                }
            }

        }, false);

    }
}
