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

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.ParentChildRelationship;
import org.cdsframework.annotation.ParentChildRelationships;
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
@ParentChildRelationships({
    @ParentChildRelationship(childDtoClass = NotificationRecipientDTO.class, childQueryClass = NotificationRecipientDTO.ByNotificationId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = NotificationReleaseNoteDTO.class, childQueryClass = NotificationReleaseNoteDTO.ByNotificationId.class, isAutoRetrieve = false),
    @ParentChildRelationship(childDtoClass = NotificationLogDTO.class, childQueryClass = NotificationLogDTO.ByNotificationId.class, isAutoRetrieve = false)
})
@Table(databaseId = "MTS", name = "notification")
@JndiReference(root = "mts-ejb-core")
@Permission(name = "Notification")
@XmlRootElement(name = "Notification")
public class NotificationDTO extends BaseDTO {

    private static final long serialVersionUID = -7084187887920884996L;

    public interface ByNotificationStatus {
    }

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    @Size(max = 32)
    private String notificationId;
    @NotNull
    @Size(max = 32)
    private String displayId;
    @NotNull
    @Size(max = 512)
    private String name;
    @Size(max = 4096)
    private String description;
    @NotNull
    private NotificationStatus status;
    @Column(name = "notification_type")
    private NotificationType type;
    private Date notificationTime;
    @Size(max = 512)
    private String messageTitle;
    @Size(max = 4096)
    private String messageBody;


    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    @PropertyListener
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of messageBody
     *
     * @return the value of messageBody
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * Set the value of messageBody
     *
     * @param messageBody new value of messageBody
     */
    @PropertyListener
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    /**
     * Get the value of messageTitle
     *
     * @return the value of messageTitle
     */
    public String getMessageTitle() {
        return messageTitle;
    }

    /**
     * Set the value of messageTitle
     *
     * @param messageTitle new value of messageTitle
     */
    @PropertyListener
    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    /**
     * Get the value of notificationTime
     *
     * @return the value of notificationTime
     */
    public Date getNotificationTime() {
        return notificationTime;
    }

    /**
     * Set the value of notificationTime
     *
     * @param notificationTime new value of notificationTime
     */
    @PropertyListener
    public void setNotificationTime(Date notificationTime) {
        this.notificationTime = notificationTime;
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
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    @PropertyListener
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the value of displayId
     *
     * @return the value of displayId
     */
    public String getDisplayId() {
        return displayId;
    }

    /**
     * Set the value of displayId
     *
     * @param displayId new value of displayId
     */
    @PropertyListener
    public void setDisplayId(String displayId) {
        this.displayId = displayId;
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

    @XmlElementRef(name = "notificationRecipients")
    public List<NotificationRecipientDTO> getNotificationRecipientDTOs() {
        return (List) this.getChildrenDTOs(NotificationRecipientDTO.ByNotificationId.class);
    }

    @XmlElementRef(name = "notificationReleaseNotes")
    public List<NotificationReleaseNoteDTO> getNotificationReleaseNoteDTOs() {
        return (List) this.getChildrenDTOs(NotificationReleaseNoteDTO.ByNotificationId.class);
    }

    @XmlElementRef(name = "notificationLogs")
    public List<NotificationLogDTO> getNotificationLogDTOs() {
        return (List) this.getChildrenDTOs(NotificationLogDTO.ByNotificationId.class);
    }

}
