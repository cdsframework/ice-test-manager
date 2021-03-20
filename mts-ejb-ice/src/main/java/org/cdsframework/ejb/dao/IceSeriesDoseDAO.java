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
import org.cdsframework.dto.IceSeriesDoseDTO;
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
public class IceSeriesDoseDAO extends BaseDAO<IceSeriesDoseDTO> {

    @Override
    protected void initialize() throws MtsException {

        this.registerDML(IceSeriesDoseDTO.BySeriesIdDoseNumber.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return getSelectDML() + " where series_id = :series_id and dose_number = :dose_number ";
            }

        }, false);

        this.registerDML(IceSeriesDoseDTO.NextDoseNumber.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                return "select (max(dose_number) + 1) from ice_series_dose where series_id = :series_id";
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
                    throws MtsException {
                final String METHODNAME = "getCallbackNamedParameters ByTestSuiteId ";
                if (baseDTO != null) {
                    if (baseDTO instanceof IceSeriesDoseDTO) {
                        IceSeriesDoseDTO parameter = (IceSeriesDoseDTO) baseDTO;
                        namedParameters.addValue("series_id", parameter.getSeriesId());
                        logger.info("NEXT DOSE NUMBER called: ", parameter.getSeriesId());
                    } else {
                        logger.warn(METHODNAME, "unexpected instance type: ", baseDTO);
                    }
                } else {
                    logger.warn(METHODNAME, "baseDTO is null!");
                }
            }

        }, false);

        this.registerDML(ByGeneralProperties.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String text = (String) baseDTO.getQueryMap().get("text");
                String seriesId = (String) baseDTO.getQueryMap().get("series_id");
                String doseId = (String) baseDTO.getQueryMap().get("dose_id");
                logger.info("ByGeneralProperties text: ", text);
                logger.info("ByGeneralProperties seriesId: ", seriesId);
                logger.info("ByGeneralProperties doseId: ", doseId);
                setNonWildcardPredicateValue(baseDTO, " series_id = :series_id ", "series_id");
                setNonWildcardPredicateValue(baseDTO, " dose_number > (select dose_number from ice_series_dose where dose_id = :dose_id )", "dose_id");
                String sql = getSelectDML() + getAndClearPredicateMap(" WHERE ", "", Operator.AND);
                logger.info("ByGeneralProperties sql: ", sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
                    throws MtsException {
                final String METHODNAME = "getCallbackNamedParameters ByGeneralProperties ";
                setExactQueryMapValue(baseDTO, "series_id", "series_id", namedParameters);
                setExactQueryMapValue(baseDTO, "dose_id", "dose_id", namedParameters);
            }

        }, false);

    }

}
