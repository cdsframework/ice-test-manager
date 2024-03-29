/**
 * CAT Core support plugin project.
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
package org.cdsframework.message;

/**
 *
 * @author HLN Consulting LLC
 */
public class Message {
    public enum MessageType {Info, Error, Fatal, Warn};
    
    private MessageType messageType;
    private String messageBundle;
    private String messageKey;
    private String messageDisplay;
    private Object[] messageArguments;
    private String messageReason;

    public Message(MessageType messageType, String messageDisplay) {
        this.messageType = messageType;
        this.messageDisplay = messageDisplay;
    }
    
    public Message(MessageType messageType, String messageKey, Object[] messageArguments) {
        this.messageType = messageType;
        this.messageKey = messageKey;
        this.messageArguments = messageArguments;
    }

    
    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessageBundle() {
        return messageBundle;
    }

    public void setMessageBundle(String messageBundle) {
        this.messageBundle = messageBundle;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageDisplay() {
        return messageDisplay;
    }

    public void setMessageDisplay(String messageDisplay) {
        this.messageDisplay = messageDisplay;
    }

    public Object[] getMessageArguments() {
        return messageArguments;
    }

    public void setMessageArguments(Object[] messageArguments) {
        this.messageArguments = messageArguments;
    }

    public String getMessageReason() {
        return messageReason;
    }

    public void setMessageReason(String messageReason) {
        this.messageReason = messageReason;
    }
            
    
}
