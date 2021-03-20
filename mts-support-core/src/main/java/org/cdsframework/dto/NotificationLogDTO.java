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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.enumeration.NotificationStatus;
import org.cdsframework.enumeration.NotificationType;

/**
 *
 * @author sdn
 */
@Entity
@Table(databaseId = "MTS", name = "notification_log")
@JndiReference(root = "mts-ejb-core")
@Permission(name = "Notification Log")
@XmlRootElement(name = "NotificationLog")
public class NotificationLogDTO extends BaseDTO {

    private static final long serialVersionUID = -2396930750106409727L;

    public interface ByNotificationId {
    }

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    @Size(max = 32)
    private String logId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = NotificationDTO.class)
    private String notificationId;
    private NotificationStatus status;
    @Column(name = "notification_type")
    private NotificationType type;
    @Size(max = 1024)
    private String notes;

    /**
     * Get the value of notes
     *
     * @return the value of notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Set the value of notes
     *
     * @param notes new value of notes
     */
    @PropertyListener
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public NotificationType getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    @PropertyListener
    public void setType(NotificationType type) {
        this.type = type;
    }

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public NotificationStatus getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    @PropertyListener
    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    /**
     * Get the value of notificationId
     *
     * @return the value of notificationId
     */
    public String getNotificationId() {
        return notificationId;
    }

    /**
     * Set the value of notificationId
     *
     * @param notificationId new value of notificationId
     */
    @PropertyListener
    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Get the value of logId
     *
     * @return the value of logId
     */
    public String getLogId() {
        return logId;
    }

    /**
     * Set the value of logId
     *
     * @param logId new value of logId
     */
    @PropertyListener
    public void setLogId(String logId) {
        this.logId = logId;
    }

}
