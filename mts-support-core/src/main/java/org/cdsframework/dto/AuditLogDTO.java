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
import org.cdsframework.annotation.SortColumn;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.OrderByMapEntries;
import org.cdsframework.annotation.OrderByMapEntry;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.AuditStatus;
import org.cdsframework.enumeration.AuditTransaction;
import org.cdsframework.enumeration.GenerationSource;

/**
 *
 * @author HLN Consulting LLC
 */
@Entity
@Table(databaseId = "MTS", name = "audit_log", alias = "al")
@JndiReference(root = "mts-ejb-core")
@Permission(name = "Audit Log")
@OrderByMapEntries({
    @OrderByMapEntry(sortFieldKey = "auditId", sortFieldValue = "al.audit_id"),
    @OrderByMapEntry(sortFieldKey = "createDatetime", sortFieldValue = "al.create_datetime")
})
@ColumnSubstitutions({
    @Column(name = "create_id", selectable = false, insertable = false, updateable = false)
})
public class AuditLogDTO extends BaseDTO {
    private static final long serialVersionUID = 2259546892485347042L;
    public interface ByTransactionId {}

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id    
    private String auditLogId;
    
    @SortColumn(sortFieldValue="al.transaction_id")
    private String transactionId;
    @SortColumn(sortFieldValue="al.transaction_type")
    private AuditTransaction transactionType;
    private AuditStatus status;
    @SortColumn(sortFieldValue="al.class_name")
    private String className;
    private String propertyName;
    private String oldValue;
    private String newValue;

    public String getAuditLogId() {
        return auditLogId;
    }

    @PropertyListener
    public void setAuditLogId(String auditLogId) {
        this.auditLogId = auditLogId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    @PropertyListener
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public AuditTransaction getTransactionType() {
        return transactionType;
    }

    @PropertyListener
    public void setTransactionType(AuditTransaction transactionType) {
        this.transactionType = transactionType;
    }

    public AuditStatus getStatus() {
        return status;
    }

    @PropertyListener
    public void setStatus(AuditStatus status) {
        this.status = status;
    }

    public String getClassName() {
        return className;
    }

    @PropertyListener
    public void setClassName(String className) {
        this.className = className;
    }

    public String getOldValue() {
        return oldValue;
    }

    @PropertyListener
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    @PropertyListener
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @PropertyListener
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

}
