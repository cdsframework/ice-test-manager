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
package org.cdsframework.dto;

import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.ColumnSubstitutions;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.enumeration.LogLevel;

/**
 *
 * @author HLN Consulting LLC
 */
@Entity
@Table(databaseId = "MTS", name = "app_log")
@JndiReference(root = "mts-ejb-core")
@Permission(name = "App Log")
@ColumnSubstitutions({
    @Column(name = "last_mod_id", selectable = false, insertable = false, updateable = false),
    @Column(name = "last_mod_datetime", selectable = false, insertable = false, updateable = false)
})
public class AppLogDTO extends BaseDTO {
    private static final long serialVersionUID = 8938902376812227226L;
    public interface ByPruner {}
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id    
    private String appLogId;
    private String sessionId;
    private String callingClass;
    private String callingMethod;
    private String otherInfo;
    private LogLevel logLevel;
    private String message;
    private String stackTrace;
    private String objectName;
    private byte[] objectData;
    private String appName;

    public String getAppLogId() {
        return appLogId;
    }

    public void setAppLogId(String appLogId) {
        this.appLogId = appLogId;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
    
    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public byte[] getObjectData() {
        return objectData;
    }

    public void setObjectData(byte[] objectData) {
        this.objectData = objectData;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getCallingClass() {
        return callingClass;
    }

    public void setCallingClass(String callingClass) {
        this.callingClass = callingClass;
    }

    public String getCallingMethod() {
        return callingMethod;
    }

    public void setCallingMethod(String callingMethod) {
        this.callingMethod = callingMethod;
    }
    
    
}
