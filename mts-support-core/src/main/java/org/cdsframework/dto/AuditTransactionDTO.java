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
import org.cdsframework.annotation.OrderBy;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.AuditTransaction;
import org.cdsframework.enumeration.GenerationSource;

/**
 *
 * @author HLN Consulting LLC
 */
@Entity
@Table(databaseId = "MTS", name = "audit_transaction")
@JndiReference(root = "mts-ejb-core")
@Permission(name = "Audit Transaction")
@ColumnSubstitutions({
    @Column(name = "last_mod_id", selectable = false, insertable = false, updateable = false),
    @Column(name = "last_mod_datetime", selectable = false, insertable = false, updateable = false)
})
@OrderBy(fields = "create_datetime desc")
public class AuditTransactionDTO extends BaseDTO {
    private static final long serialVersionUID = 4356781876125347042L;
    
    @GeneratedValue(source = GenerationSource.AUTO)
    @Id    
    private String transactionId;
    private AuditTransaction transactionType;
    private String className;
    private String appName;

    public String getTransactionId() {
        return transactionId;
    }

    @PropertyListener
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getClassName() {
        return className;
    }

    @PropertyListener
    public void setClassName(String className) {
        this.className = className;
    }

    public String getAppName() {
        return appName;
    }

    @PropertyListener
    public void setAppName(String appName) {
        this.appName = appName;
    }

    public AuditTransaction getTransactionType() {
        return transactionType;
    }

    @PropertyListener
    public void setTransactionType(AuditTransaction transactionType) {
        this.transactionType = transactionType;
    }

    
}

