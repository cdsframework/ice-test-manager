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

import org.cdsframework.base.BaseDTO;
import org.cdsframework.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author HLN Consulting, LLC
 */
public class PropertyBagDTO implements Serializable {

    private static final long serialVersionUID = -385098216508703362L;

    public enum Position {

        First, Last
    };

    private enum InternalConstants {

        OperationName, AncestorMap, CallerList, AuditTransactionId
    };
    /**
     * Tracks custom properties communicated throughout the framework
     */
    private final Map<String, Object> propertyMap = new HashMap<String, Object>();

    /**
     * The list of childClassDTOs that will be retrieved during a Find operation
     */
    private List<Class<? extends BaseDTO>> childClassDTOs = new ArrayList<Class<? extends BaseDTO>>();

    /**
     * The list of childClassDTOs that will be refreshed during an Add or Update operation
     */
    private List<Class> refreshChildClassDTOs = new ArrayList<Class>();

    /*
     * Tracks the queryClass communicated throughout the framework
     * Presently only used in newInstance method. The future expectation is to get queryClass here
     */
    private String queryClass;

    /**
     * Get the value of propertyMap
     *
     * @return the value of propertyMap
     */
    public Map<String, Object> getPropertyMap() {
        return propertyMap;
    }

    /**
     * Get the value of operationName
     *
     * @return the value of operationName
     */
    public String getOperationName() {
        return (String) propertyMap.get(InternalConstants.OperationName.name());
    }

    /**
     * Set the value of operationName
     *
     * @param operationName new value of operationName
     */
    public void setOperationName(String operationName) {
        propertyMap.put(InternalConstants.OperationName.name(), operationName);
    }

    public String getAuditTransactionId() {
        Object oAuditTransactionId = propertyMap.get(InternalConstants.AuditTransactionId.name());
        String auditTransactionId = null;
        if (oAuditTransactionId == null) {
            auditTransactionId = StringUtils.getHashId();
            propertyMap.put(InternalConstants.AuditTransactionId.name(), auditTransactionId);
        } else {
            auditTransactionId = (String) oAuditTransactionId;
        }
        return auditTransactionId;
    }

    public boolean isAuditTransactionIdExist() {
        return (propertyMap.get(InternalConstants.AuditTransactionId.name()) != null);
    }

    public boolean contains(String key) {
        return propertyMap.containsKey(key);
    }

    public Object remove(String key) {
        return propertyMap.remove(key);
    }

    public Object get(String key) {
        return propertyMap.get(key);
    }

    public <S> S get(String key, S valueIfNull) {
        S object = (S) propertyMap.get(key);
        if (object == null) {
            object = valueIfNull;
        }
        return object;
    }

    public <S> S get(String key, Class<S> castType) {
        return (S) propertyMap.get(key);
    }

    public Object put(String key, Object value) {
        return propertyMap.put(key, value);
    }

    public static boolean isOperationName(PropertyBagDTO propertyBagDTO, String operationName) {
        boolean isOperationName = false;

        if (propertyBagDTO != null) {
            if (!StringUtils.isEmpty(propertyBagDTO.getOperationName())) {
                isOperationName = operationName.equalsIgnoreCase(propertyBagDTO.getOperationName());
            }
        }
        return isOperationName;
    }

    public <T extends BaseDTO> void setParentDTO(Class<T> dtoClass, T parentDTO) {
        Map<Class<T>, T> ancestorMap = null;
        if (this.getPropertyMap().containsKey(InternalConstants.AncestorMap.name())) {
            ancestorMap = (Map<Class<T>, T>) this.getPropertyMap().get(InternalConstants.AncestorMap.name());
        } else {
            ancestorMap = new LinkedHashMap<Class<T>, T>();
            this.getPropertyMap().put(InternalConstants.AncestorMap.name(), ancestorMap);
        }
        ancestorMap.put(dtoClass, parentDTO);
    }

    public <T extends BaseDTO> T getParentDTO(Class<T> dtoClass) {
        T parentDTO = null;
        if (this.getPropertyMap().containsKey(InternalConstants.AncestorMap.name())) {
            Map<Class<T>, T> ancestorMap = (Map<Class<T>, T>) this.getPropertyMap().get(InternalConstants.AncestorMap.name());
            parentDTO = ancestorMap.get(dtoClass);
        }
        return parentDTO;
    }

    public <T extends BaseDTO> T getParentDTO(Position position) {
        T parentDTO = null;
        if (this.getPropertyMap().containsKey(InternalConstants.AncestorMap.name())) {
            Map<Class<T>, T> ancestorMap = (Map<Class<T>, T>) this.getPropertyMap().get(InternalConstants.AncestorMap.name());

            // Convert to List
            List<Entry<Class<T>, T>> parentDTOs = new ArrayList<Map.Entry<Class<T>, T>>(ancestorMap.entrySet());
            int indexPos = 0;
            if (position == Position.Last) {
                indexPos = parentDTOs.size() - 1;
            }
            Entry<Class<T>, T> lastEntry = parentDTOs.get(indexPos);
            parentDTO = lastEntry.getValue();
        }
        return parentDTO;
    }

    public void setCaller(Class caller) {
        List<String> callingMGRList = null;
        if (this.getPropertyMap().containsKey(InternalConstants.CallerList.name())) {
            callingMGRList = (List<String>) this.getPropertyMap().get(InternalConstants.CallerList.name());
        } else {
            callingMGRList = new ArrayList<String>();
            this.getPropertyMap().put(InternalConstants.CallerList.name(), callingMGRList);
        }
        callingMGRList.add(caller.getCanonicalName());
    }

    public boolean isCaller(Class caller) {
        boolean callingMGRFound = false;
        if (this.getPropertyMap().containsKey(InternalConstants.CallerList.name())) {
            List<String> callingMGRList = (List<String>) this.getPropertyMap().get(InternalConstants.CallerList.name());
            callingMGRFound = callingMGRList.contains(caller.getCanonicalName());
        }
        return callingMGRFound;
    }

    /**
     * Get the value of childClassDTOs
     *
     * @return the value of childClassDTOs
     */
    public List<Class<? extends BaseDTO>> getChildClassDTOs() {
        return childClassDTOs;
    }

    /**
     * Set the value of childClassDTOs
     *
     * @param childClassDTOs new value of childClassDTOs
     */
    public void setChildClassDTOs(List<Class<? extends BaseDTO>> childClassDTOs) {
        this.childClassDTOs = childClassDTOs;
    }

    public List<Class> getRefreshChildClassDTOs() {
        return refreshChildClassDTOs;
    }

    public void setRefreshChildClassDTOs(List<Class> refreshChildClassDTOs) {
        this.refreshChildClassDTOs = refreshChildClassDTOs;
    }

    public String getQueryClass() {
        return queryClass;
    }

    public void setQueryClass(String queryClass) {
        this.queryClass = queryClass;
    }
}
