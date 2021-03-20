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
import org.cdsframework.dto.IceSeasonDTO;
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
public class IceSeasonDAO extends BaseDAO<IceSeasonDTO> {

    @Override
    protected void initialize() throws MtsException {

        // General search query
        this.registerDML(ByGeneralProperties.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String sql;
                String text = (String) baseDTO.getQueryMap().get("text");
                String vaccineGroupId = (String) baseDTO.getQueryMap().get("vaccineGroupId");
                logger.info("ByGeneralProperties text: ", text);
                logger.info("ByGeneralProperties vaccineGroupId: ", vaccineGroupId);

                if (!StringUtils.isEmpty(text) || !StringUtils.isEmpty(vaccineGroupId)) {
                    if (!StringUtils.isEmpty(text)) {
                        setWildcardPredicateValue(baseDTO,
                                " ( lower(name) like :text OR lower(notes) like :text OR lower(code) like :text OR lower(season_id) like :text) ", "text");
                    }
                    if (!StringUtils.isEmpty(vaccineGroupId)) {
                        setNonWildcardPredicateValue(baseDTO, " group_id = :group_id ", "vaccineGroupId");
                    }
                    sql = getSelectDML() + getAndClearPredicateMap(" WHERE ", "", Operator.AND);
                } else {
                    sql = getSelectDML();
                }
                logger.debug("ByGeneralProperties sql: ", sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                setLowerQueryMapValue(baseDTO, "text", "text", namedParameters);
                setExactQueryMapValue(baseDTO, "group_id", "vaccineGroupId", namedParameters);
                logger.info("ByGeneralProperties nameparameter text: ", namedParameters.getValue("text"));
                logger.info("ByGeneralProperties nameparameter group_id: ", namedParameters.getValue("group_id"));
            }

        }, false);

        // ByVersionId search query
        this.registerDML(IceSeasonDTO.BySeriesId.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String sql;
                String text = (String) baseDTO.getQueryMap().get("text");
                String seriesId = (String) baseDTO.getQueryMap().get("series_id");
                logger.info("IceSeasonDTO.ByVersionId text: ", text);
                logger.info("IceSeasonDTO.ByVersionId seriesId: ", seriesId);

                setWildcardPredicateValue(baseDTO,
                        " ( lower(name) like :text OR lower(notes) like :text OR lower(code) like :text OR lower(season_id) like :text) ", "text");
                setNonWildcardPredicateValue(baseDTO, "  exists (select null from ice_season_version_rel isvr, ice_series_version_rel isvr2 where isvr.version_id = isvr2.version_id and isvr.season_id = ice_season.season_id and isvr2.series_id = :series_id) ", "series_id");
                sql = getSelectDML() + getAndClearPredicateMap(" WHERE ", "", Operator.AND);

                logger.info("IceSeasonDTO.ByVersionId sql=", sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                setLowerQueryMapValue(baseDTO, "text", "text", namedParameters);
                setExactQueryMapValue(baseDTO, "series_id", "series_id", namedParameters);
                logger.info("IceSeasonDTO.ByVersionId nameparameter text: ", namedParameters.getValue("text"));
                logger.info("IceSeasonDTO.ByVersionId nameparameter series_id: ", namedParameters.getValue("series_id"));
            }

        }, false);

    }
}
