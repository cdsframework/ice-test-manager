/**
 * The MTS support core project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see https://www.hln.com/services/open-source/ or send
 * correspondence to ice@hln.com.
 */ 
package org.cdsframework.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.cdsframework.enumeration.DatabaseType;

/**
 * Provides miscellaneous database utility functions.
 *
 * @author HLN Consulting, LLC
 */
public class DBUtils {
    
    private static final LogUtils logger = LogUtils.getLogger(DBUtils.class);
    
    private static DBUtils instance;
    
    public static DBUtils getInstance() {
        if (instance == null) {
            instance = new DBUtils();
        }
        return instance;
    }
    
    private final Map<String, DatabaseType> dbDetectProductMap = new HashMap<String, DatabaseType>();
    
    private DBUtils() {
        dbDetectProductMap.put("Apache Derby", DatabaseType.DERBY);
        dbDetectProductMap.put("MySQL", DatabaseType.MYSQL);
        dbDetectProductMap.put("Oracle", DatabaseType.ORACLE);
        dbDetectProductMap.put("Microsoft SQL Server", DatabaseType.SQLSERVER);
        dbDetectProductMap.put("PostgreSQL", DatabaseType.POSTGRESQL);
    }
    
    public DatabaseType getDatabaseType(Connection conn) {
 
        try {
            String product = conn.getMetaData().getDatabaseProductName();
            DatabaseType dbType = dbDetectProductMap.get(product);
            if (dbType != null) {
                return dbType;
            }
            logger.error("Unknown database product name: '"+product+"', returning ANY");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DatabaseType.ANY;
    }

}
