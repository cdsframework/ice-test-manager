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
import org.cdsframework.dto.IceSeriesDTO;
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
public class IceSeriesDAO extends BaseDAO<IceSeriesDTO> {

    @Override
    protected void initialize() throws MtsException {

        // General search query
        this.registerDML(ByGeneralProperties.class, new QueryCallback(getDtoTableName()) {
            @Override
            public String getQueryDML(BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
                String sql = "";
                String text = (String) baseDTO.getQueryMap().get("text");
                String vaccineGroupId = (String) baseDTO.getQueryMap().get("vaccineGroupId");
                logger.debug("ByGeneralProperties text: ", text);
                logger.debug("ByGeneralProperties vaccineGroupId: ", vaccineGroupId);

                if (!StringUtils.isEmpty(text) || !StringUtils.isEmpty(vaccineGroupId)) {
                    if (!StringUtils.isEmpty(text)) {
                        setWildcardPredicateValue(baseDTO,
                                " ( lower(name) like :text OR lower(notes) like :text OR lower(code) like :text OR lower(series_id) like :text ) ", "text");
                    }
                    if (!StringUtils.isEmpty(vaccineGroupId)) {
                        setNonWildcardPredicateValue(baseDTO, " group_id = :group_id ", "vaccineGroupId");
                    }
                    sql = getSelectDML() + getAndClearPredicateMap(" WHERE ", "", Operator.AND);
                } else {
                    sql = getSelectDML();
                }
                logger.debug(sql);
                return sql;
            }

            @Override
            protected void getCallbackNamedParameters(MapSqlParameterSource namedParameters, BaseDTO baseDTO, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) throws MtsException {
                setLowerQueryMapValue(baseDTO, "text", "text", namedParameters);
                setExactQueryMapValue(baseDTO, "group_id", "vaccineGroupId", namedParameters);
                logger.debug("nameparameter text: ", namedParameters.getValue("text"));
                logger.debug("nameparameter group_id: ", namedParameters.getValue("group_id"));
            }

        }, false);

    }
}
