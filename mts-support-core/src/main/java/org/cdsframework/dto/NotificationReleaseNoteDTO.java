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
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.GenerationSource;

/**
 *
 * @author sdn
 */
@Entity
@Table(databaseId = "MTS", name = "notification_release_note")
@JndiReference(root = "mts-ejb-core")
@Permission(name = "Notification Release Note")
@XmlRootElement(name = "NotificationReleaseNote")
public class NotificationReleaseNoteDTO extends BaseDTO {

    private static final long serialVersionUID = 2179953959501351478L;

    public interface ByNotificationId {
    }

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    @Size(max = 32)
    private String noteId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = {NotificationDTO.class, NotificationStateDTO.class})
    private String notificationId;
    @NotNull
    @Size(max = 512)
    private String title;
    @NotNull
    @Size(max = 4096)
    private String description;
    @Size(max = 4096)
    private String reason;

    /**
     * Get the value of reason
     *
     * @return the value of reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Set the value of reason
     *
     * @param reason new value of reason
     */
    public void setReason(String reason) {
        this.reason = reason;
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
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the value of title
     *
     * @return the value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the value of title
     *
     * @param title new value of title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Get the value of noteId
     *
     * @return the value of noteId
     */
    public String getNoteId() {
        return noteId;
    }

    /**
     * Set the value of noteId
     *
     * @param noteId new value of noteId
     */
    @PropertyListener
    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

}
