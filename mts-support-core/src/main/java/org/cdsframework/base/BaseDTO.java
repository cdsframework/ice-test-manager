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
package org.cdsframework.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.cdsframework.aspect.aspects.PropertySetterInterface;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import org.cdsframework.annotation.Permission;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.util.ClassUtils;
import org.cdsframework.util.DTOUtils;
import org.cdsframework.util.DateUtils;
import org.cdsframework.util.support.CorePropertyChangeEvent;

/**
 * Provides a base class for the data transfer objects.
 *
 * DTO Custom Annotations:
 *
 * - Class/Type Level:
 *
 * @JndiReference - set the JNDI lookup base for a manager and the object names for the remote, DAO and BO
 * @Lookup - this DTO type doesn't not have a MGR or BO - use generic classes
 * @OrderBy - used to set a default comparator for the DTO
 * @OrderByMapEntries - used to register sort map entries for lazy loading functionality
 * @OrderByMapEntry - child annotation of OrderByMapEntries - used to register a sort map entry for lazy loading functionality
 * @ParentBehavior - manages the ACLs on a DTO and its children
 * @Permission - marks a DTO as a permission object and set the permission display name
 * @ReadOnly - make a dto as read-only
 * @RefreshOnAddOrUpdate - go back to the data source and retrieve fresh data after an insert or update
 * @RowsReturnCountBehavior - controls how the BaseBO handles zero rows returned from a data source operation
 * @Table - sets db table info the object is associated with
 *
 * - Field Level
 * @GeneratedValue - defines the source of a properties value; certain auto setting occurs when this is coupled with
 * @Id
 * @Id - defines the primary key property on a DTO
 * @NoId - conveys the information that this DTO does not have a primary key
 * @ReferenceDTO - conveys the information that a property gets its value via the primary key from another BO
 *
 * - Relationship defining annotations
 *
 * Specify parent child relationships as follows:
 * @ParentChildRelationships({
 * @ParentChildRelationship(childDtoClass = ChildA.class, childQueryClass = ChildA.ByProperty1.class, comparatorClass =
 * ComparatorA.class),
 * @ParentChildRelationship(childDtoClass = ChildA.class, childQueryClass = ChildA.ByProperty1.class) })
 *
 * Specify order by map entries as follows:
 * @OrderByMapEntries({
 *     @OrderByMapEntry(sortFieldKey = "lastModId", sortFieldValue = "f.last_mod_id")
 * })
 *
 * @author HLN Consulting, LLC
 */
@Permission(name = "*")
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public abstract class BaseDTO implements Serializable, PropertyChangeListener, PropertySetterInterface {

    // Track lastModId, lastModDatetime changes
    public enum PropertyName {

        lastModId, lastModDatetime, auditId
    }
    
//    @JsonProperty    
//    @XmlTransient
    private UUID uuid;
    /**
     * For debugging to system out
     */
    @XmlTransient
    private boolean debug = false;
    /**
     * Used to indicate additional query properties
     */
    @XmlTransient
    private Map<String, Object> queryMap;
    /**
     * Indicated the dtoState a DTO is in according to the DTOState enumeration.
     */
    private DTOState dtoState = DTOState.NEW;
    /**
     * Field to track last modification of the object.
     *
     * @see #getLastModId()
     * @see #setLastModId(java.lang.String)
     */
    private String lastModId;
    /**
     * Field to track last modification of the object.
     *
     * @see #getLastModDatetime()
     * @see #setLastModDatetime(java.util.Date)
     */
    private Date lastModDatetime;
    /**
     * Field to determine if lastModDatetime tracking should be enabled
     *
     * @see #getLastModDatetime()
     * @see #setLastModDatetime(java.util.Date)
     */
    @XmlTransient
    protected boolean trackLastModDatetime = true;
//    /**
//     * Field to trorigLastModDateTimeack previous modification of the object.
//     *
//     * @see #getOriginalLastModDatetime()
//     */
//    private Date origLastModDateTime;
    /**
     * Field to track the ID of the user that created the object.
     *
     * @see #getCreateId()
     */
    private String createId;
    /**
     * Field to track the create datetime of the object.
     *
     * @see #getCreateDatetime()
     * @see #setCreateDatetime(java.util.Date)
     */
    private Date createDatetime;
    /**
     * For storage of childDTO.
     *
     * @see #getChildDTOMap()
     * @see #setChildDTOMap(java.util.HashMap)
     */
    @XmlTransient
    private Map<Class, List<BaseDTO>> childDTOMap;

    /*
     * Used for property change support
     */
    @XmlTransient
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /*
     * For storage of propertyChangeEvents
     *
     * Tracks old and new values that can be used in updating processing
     */
    //@JsonProperty    Turn off for now as there is more work for RS clients
    @XmlTransient
    private Map<String, CorePropertyChangeEvent> propertyChangeEventMap = new HashMap<String, CorePropertyChangeEvent>();

    // Force the property to be part of json
    @JsonProperty
    @XmlTransient    
    private String auditId;

//    @JsonProperty
//    @JsonIgnore
//    @XmlTransient
//    private String resourceName;
    
    public BaseDTO() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        // Add propertyChangeSupport
        propertyChangeSupport.addPropertyChangeListener(this);
    }

    /**
     * Used to track propertyChanges, is called from propertyChangeSupport.firePropertyChange. See isChanged
     *
     * @param propertyChangeEvent
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        //System.out.println("propertyChange propertyChangeEvent=" + propertyChangeEvent);
        //System.out.println("dtoState=" + dtoState);
        
        if (dtoState != DTOState.NEW && dtoState != DTOState.NEWMODIFIED) {
            String propertyName = propertyChangeEvent.getPropertyName();
            //("propertyChange propertyName=" + propertyName);
            if (propertyChangeEventMap.containsKey(propertyName)) {
                // Handles tracking the most recent new value
                CorePropertyChangeEvent oldPropertyChangeEvent = propertyChangeEventMap.get(propertyName);

                // New Value Returning to Old Value ?
                if ( (propertyChangeEvent.getNewValue() == null && oldPropertyChangeEvent.getOldValue() == null) ||
                     (propertyChangeEvent.getNewValue() != null && propertyChangeEvent.getNewValue().equals(oldPropertyChangeEvent.getOldValue())) ||
                     (propertyChangeEvent.getOldValue() != null && propertyChangeEvent.getOldValue().equals(propertyChangeEvent.getNewValue()))
                   ) {
                    //System.out.println("New=Old " + propertyChangeEvent.getNewValue() + "=" + oldPropertyChangeEvent.getOldValue() +
                    //        " propertyName=" + propertyName);
                    propertyChangeEventMap.remove(propertyName);
                }
                else {
//                    PropertyChangeEvent newPropertyChangeEvent = new PropertyChangeEvent(propertyChangeEvent.getSource(),
//                            propertyChangeEvent.getPropertyName(), oldPropertyChangeEvent.getOldValue(), propertyChangeEvent.getNewValue());
                    CorePropertyChangeEvent newPropertyChangeEvent = new CorePropertyChangeEvent(
                            propertyChangeEvent.getPropertyName(), oldPropertyChangeEvent.getOldValue(), propertyChangeEvent.getNewValue());
                    
                    //System.out.println("propertyChange updating=" + propertyName);
                    propertyChangeEventMap.put(newPropertyChangeEvent.getPropertyName(), newPropertyChangeEvent);
                }
            } else {
                CorePropertyChangeEvent newPropertyChangeEvent = new CorePropertyChangeEvent(
                        propertyChangeEvent.getPropertyName(), propertyChangeEvent.getOldValue(), propertyChangeEvent.getNewValue());
                propertyChangeEventMap.put(newPropertyChangeEvent.getPropertyName(), newPropertyChangeEvent);                
                //System.out.println("propertyChange putting propertyName=" + propertyName + "propertyChangeEvent=" + propertyChangeEvent);
//                propertyChangeEventMap.put(propertyChangeEvent.getPropertyName(), propertyChangeEvent);
//                CorePropertyChangeEvent putPropertyChangeEvent = propertyChangeEventMap.get(propertyChangeEvent.getPropertyName());
//                System.out.println("propertyChange put putPropertyChangeEvent=" + putPropertyChangeEvent);
            }
       }
    }

    /**
     * Returns a reference to propertyChangeSupport
     *
     * @return the propertyChangeSupport.
     */
    protected PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    /**
     * Returns a map of all the propertyChangeEvents
     *
     * @return the propertyChangeEventMap.
     */
    public Map<String, CorePropertyChangeEvent> getPropertyChangeEventMap() {
        return propertyChangeEventMap;
    }

    /**
     * Sets the map of all the propertyChangeEvents
     *
     * @param propertyChangeEventMap the propertyChangeEventMap.
     */
    public void setPropertyChangeEventMap(Map<String, CorePropertyChangeEvent> propertyChangeEventMap) {
        this.propertyChangeEventMap = propertyChangeEventMap;
    }

    /**
     * Returns the propertyChangeEvent, will be null if it was not changed
     *
     * @param propertyName
     * @return the PropertyChangeEvent.
     */
    public CorePropertyChangeEvent getPropertyChangeEvent(String propertyName) {
        return propertyChangeEventMap.get(propertyName);
    }

    /**
     * Returns a boolean indicating whether or not the propertyName has changed.
     *
     * @param propertyName
     * @return the boolean.
     */
    public boolean isPropertyChanged(String propertyName) {
        // To Do: Can add more logic to determine if the property truely has changed
        // 99% of the time if the property is in the map the value would have changed
        // If the user changes it again, the event still exists
        return propertyChangeEventMap.containsKey(propertyName);
    }

    /**
     * Returns the dtoState of the DTO.
     *
     * @return the dtoState of the DTO.
     */
    public DTOState getDTOState() {
        DTOState localDTOState = dtoState;
        // check referenceDTO states
        if (localDTOState == DTOState.UNSET && isReferenceDTOsExist()) {
            for (Field referenceField : getReferenceDTOs()) {
                localDTOState = DTOUtils.getReferenceDTOState(referenceField, this);
                if (localDTOState != DTOState.UNSET) {
                    break;
                }
            }
        }
        return localDTOState;
    }

    /**
     * Returns the state of the Operation to determine if the DTO Should be ADDED, UPDATED or DELETED
     *
     * @return the operationDTOState based on the DTO and its Children
     */
    public DTOState getOperationDTOState() {
        DTOState operationDTOState = this.getDTOState();
        if (operationDTOState == DTOState.UNSET) {
            // Look at all the ChildrenDTO's to determine the state
            if (this.getChildDTOState(this) != DTOState.UNSET) {
                operationDTOState = DTOState.UPDATED;
            }
        }
        return operationDTOState;
    }

    // todo - migrate all the usages of these to direct dtoutils calls
    public Object getPrimaryKey() {
        return DTOUtils.getPrimaryKey(this);
    }

    public void setPrimaryKey(Object key) {
        DTOUtils.setPrimaryKey(this, key);
    }

    public boolean isPKGeneratedSourceAuto() {
        return DTOUtils.isPKGeneratedSourceAuto(getClass());
    }

    public boolean isPKGeneratedSourceSequence() {
        return DTOUtils.isPKGeneratedSourceSequence(getClass());
    }

    public List<Field> getPrimaryKeyFields() {
        return DTOUtils.getPrimaryKeyFields(getClass());
    }

    public boolean isNoId() {
        return DTOUtils.isNoId(getClass());
    }

    public boolean isAudit() {
        return DTOUtils.isAudit(getClass());
    }

    public boolean isReferenceDTOsExist() {
        return DTOUtils.isReferenceDTOsExist(getClass());
    }

    public List<Field> getReferenceDTOs() {
        return DTOUtils.getReferenceDTOs(getClass());
    }

    public boolean autoSetPrimaryKeys() {
        return DTOUtils.autoSetPrimaryKeys(this);
    }

    public boolean hasPrimaryKey() {
        return DTOUtils.hasPrimaryKey(this);
    }

    public void setForeignKey(Class<? extends BaseDTO> foreignClass, Object key) {
        DTOUtils.setForeignKey(this, foreignClass, key);
    }

    public Object getForeignKey(Class<? extends BaseDTO> foreignClass, String fieldName) {
        return DTOUtils.getForeignKey(this, foreignClass, fieldName);
    }

    /**
     * Returns the updated dtoState of the object.
     *
     * @return the updated dtoState
     */
    public boolean isUpdated() {
        return this.getDTOState() == DTOState.UPDATED;
    }

    /**
     * Sets the updated dtoState of the object.
     *
     * @param updated the updated dtoState.
     */
    protected void setUpdated(boolean updated) {
        if (updated) {
            DTOState localDTOState = getDTOState();
            if (localDTOState == DTOState.NEW) {
                dtoState = DTOState.NEWMODIFIED;
            } else if (localDTOState != DTOState.NEW && localDTOState != DTOState.NEWMODIFIED && localDTOState != DTOState.DELETED) {
                dtoState = DTOState.UPDATED;
            }

        }
    }

//    /**
//     * Called when the object is initialized from the database. Note: possibly rename method to retrieved.
//     */
//    public void resetDTOState() {
//        dtoState = DTOState.UNSET;
//    }

    /*
     * Called internally to set the DTOState
     */
    private void setDTOState(DTOState dtoState) {
        this.dtoState = dtoState;
    }

    /**
     * Returns the new record dtoState of the object.
     *
     * @return the new record dtoState.
     */
    public boolean isNew() {
        DTOState localDTOState = getDTOState();
        return localDTOState == DTOState.NEW || localDTOState == DTOState.NEWMODIFIED;
    }

    public boolean isNewModified() {
        return getDTOState() == DTOState.NEWMODIFIED;
    }

    /**
     * Sets the new record dtoState of the object.
     *
     * @param newRecord the new record dtoState.
     */
//    public void setNew(boolean newRecord) {
//        if (newRecord) {
//            dtoState = DTOState.NEW;
//        } else {
//            dtoState = DTOState.UNSET;
//        }
//    }
    /**
     * Returns the deleted dtoState of the object.
     *
     * @return the deleted dtoState.
     */
    public boolean isDeleted() {
        return this.getDTOState() == DTOState.DELETED;
    }

    /**
     * Sets the deleted dtoState of the object.
     *
     * Note: If false delete will not trickle down to its children. As a result the client may add/update children attached to the
     * parent which will ultimate result in an MtsException the DELETE Operation will attempt to delete a DTO that is not marked for
     * DELETE.
     *
     */
    public void delete() {
        this.delete(false);
    }

    /**
     * Sets the deleted dtoState of the object and additionally whether or not to cascade it to the children.
     *
     * @param cascade cascade the deletes to the children.
     */
    public void delete(boolean cascade) {
        // NOT a newRecord ?, (newRecord is true by default)
//        DTOState currentDTOState = this.getDTOState();
//        if (currentDTOState != DTOState.NEW) {
        dtoState = DTOState.DELETED;
        // Delete all the children
        if (cascade) {
            deleteChildren(BaseDTO.class, cascade);
        }
//        } else {
//            throw new MtsException("An attempt was made to delete " + this.getClass().getSimpleName()
//                    + " with a " + currentDTOState.toString() + " DTOState. This is not allowed!");
//        }
    }

    /**
     * Sets the deleted dtoState of the objects associated with the queryClass and additionally whether or not to cascade it to the
     * children.
     *
     * @param queryClass
     * @param cascade cascade the deletes to the children.
     */
    public void deleteChildren(Class queryClass, boolean cascade) {
        // Descendants must return a concatenated list of all children.
        List<BaseDTO> childrenDTOs = this.getChildrenDTOs(queryClass);
        for (BaseDTO baseDTO : childrenDTOs) {
            baseDTO.delete(cascade);
        }
    }

    /**
     * Returns the created datetime of the object.
     *
     * @return the created datetime.
     */
    public Date getCreateDatetime() {
        return createDatetime;
    }

    /**
     * Sets the created datetime of the object.
     *
     * @param createDatetime
     */
    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    /**
     * Returns the ID of the user that created the object.
     *
     * @return the ID of the user that created the object.
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * Sets the ID of the user that created the object.
     *
     * @param createId the ID of the user that created the object.
     */
    public void setCreateId(String createId) {
        this.createId = createId;
    }

    /**
     * Returns the last modified datetime of the object.
     *
     * @return the last modified datetime.
     */
    public Date getLastModDatetime() {
        return lastModDatetime;
    }

    /**
     * Sets the last modified datetime of the object.
     *
     * @param lastModDatetime the last modified datetime.
     */
    public void setLastModDatetime(Date lastModDatetime) {
        if (isTrackLastModDatetime()) {
            this.setUpdated(isChanged(PropertyName.lastModDatetime.toString(), this.lastModDatetime, lastModDatetime));
        }
        this.lastModDatetime = lastModDatetime;        

    }

    /**
     * Returns the previous modified datetime of the object.
     *
     * @return the previous modified datetime.
     */
    public Date getOriginalLastModDatetime() {
        Date origLastModDatetime = this.lastModDatetime;
        CorePropertyChangeEvent propertyChangeEvent = getPropertyChangeEvent(PropertyName.lastModDatetime.toString());
        if (propertyChangeEvent != null) {
            origLastModDatetime = (Date) propertyChangeEvent.getOldValue();
        }

        return origLastModDatetime;
    }

    /**
     * Returns the previous modified datetime of the object as a ModDateMask. Used for concurrency control in stored procedure
     * update/date routines
     *
     * @return the previous modified datetime as a ModDateMask.
     */
    public String getOrigLastModDatetimeAsModDateMask() {
        String sOrigLastModDateTime = null;
        Date origLastModDatetime = getOriginalLastModDatetime();
        if (origLastModDatetime != null) {
            sOrigLastModDateTime = DateUtils.getFormattedModDateMask(origLastModDatetime);
        }
        return sOrigLastModDateTime;
    }

    /**
     * Returns the ID of the user that last modified the object.
     *
     * @return the ID of the user that last modified the object.
     */
    public String getLastModId() {
        return lastModId;
    }

    /**
     * Sets the ID of the user that last modified the object.
     *
     * @param lastModId the ID of the user that last modified the object.
     */
    public void setLastModId(String lastModId) {
        this.setUpdated(isChanged(PropertyName.lastModId.toString(), this.lastModId, lastModId));
        this.lastModId = lastModId;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.setUpdated(isChanged(PropertyName.auditId.toString(), this.auditId, auditId));
        this.auditId = auditId;
    }

    /**
     * Returns the list of associated child DTOs.
     *
     * @param queryClass the list of associated child DTOs.
     * @param desiredState a constraint on list of associated child DTOs returned.
     * @return
     */
    public List<BaseDTO> getChildrenDTOs(Class queryClass, DTOState desiredState) {
        return getChildrenDTOsByDTOState(queryClass, desiredState);
    }
    
    private List<BaseDTO> getChildrenDTOsByDTOState(Class queryClass, DTOState desiredState) {
        List<BaseDTO> childrenDTOs = new LinkedList<BaseDTO>();
        if (queryClass != BaseDTO.class) {
            if (desiredState == null) {
                childrenDTOs = getChildDTOMap().get(queryClass);
            } else {
                List<BaseDTO> anyChildrenDTOs = getChildDTOMap().get(queryClass);
                if (anyChildrenDTOs != null) {
                    for (BaseDTO childrenDTO : anyChildrenDTOs) {
                        if (desiredState == DTOState.NEW || desiredState == DTOState.NEWMODIFIED) {
                            DTOState childDTOState = childrenDTO.getDTOState();
                            if (childDTOState == DTOState.NEW || childDTOState == DTOState.NEWMODIFIED) {
                                childrenDTOs.add(childrenDTO);
                            }
                        } else {
                            if (childrenDTO.getDTOState() == desiredState) {
                                childrenDTOs.add(childrenDTO);
                            }
                        }
                    }
                } else {
                    childrenDTOs = null;
                }
            }
            if (childrenDTOs == null) {
                childrenDTOs = new LinkedList<BaseDTO>();
                getChildDTOMap().put(queryClass, childrenDTOs);
            }
        } else {
            for (Map.Entry childrenDTOEntry : getChildDTOMap().entrySet()) {
                List<BaseDTO> childDTOs = new LinkedList<BaseDTO>();
                if (desiredState == null) {
                    childDTOs = (List<BaseDTO>) childrenDTOEntry.getValue();
                } else {
                    for (BaseDTO childrenDTO : (List<BaseDTO>) childrenDTOEntry.getValue()) {
                        if (desiredState == DTOState.NEW || desiredState == DTOState.NEWMODIFIED) {
                            DTOState childDTOState = childrenDTO.getDTOState();
                            if (childDTOState == DTOState.NEW || childDTOState == DTOState.NEWMODIFIED) {
                                childrenDTOs.add(childrenDTO);
                            }
                        } else {
                            if (childrenDTO.getDTOState() == desiredState) {
                                childDTOs.add(childrenDTO);
                            }
                        }
                    }
                }
                if (childDTOs != null) {
                    childrenDTOs.addAll(childDTOs);
                }
            }
        }
        return childrenDTOs;
    }
    
    /**
     * Returns the complete list of associated child DTOs.
     *
     * @param queryClass the list of associated child DTOs.
     * @return
     */
    public List<BaseDTO> getChildrenDTOs(Class queryClass) {
        return getChildrenDTOs(queryClass, BaseDTO.class);
    }

    /**
     * Returns the complete list of associated child DTOs properly typed.
     *
     * @param <S>
     * @param queryClass the list of associated child DTOs.
     * @param dtoType
     * @return
     */
    public <S extends BaseDTO> List<S> getChildrenDTOs(Class queryClass, Class<S> dtoType) {
        return (List<S>) getChildrenDTOsByDTOState(queryClass, null);
    }

    /**
     * Sets the list of associated child DTOs.
     *
     * @param queryClass the DTO queryClass.
     * @param childrenDTOs the list of associated child DTOs.
     */
    public void setChildrenDTOs(Class queryClass, List<BaseDTO> childrenDTOs) {
        getChildDTOMap().put(queryClass, childrenDTOs);
    }

    /**
     * Returns the map of associated child DTOs.
     *
     * @return the map of associated child DTOs.
     */
    public Map<Class, List<BaseDTO>> getChildDTOMap() {
        if (childDTOMap == null) {
            childDTOMap = new HashMap<Class, List<BaseDTO>>();
        }
        return childDTOMap;
    }

    /**
     * Sets the map of associated child DTOs.
     *
     * @param childDTOMap the map of associated child DTOs.
     */
    public void setChildDTOMap(Map childDTOMap) {
        this.childDTOMap = childDTOMap;
    }
    
    /**
     * This should be called from within the setter of all DTOs that extend BaseDTO. This method maintains the changed flag
     * dtoState.
     *
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     */
    protected boolean isChanged(Object x, Object y) {
        boolean changed = (this.getDTOState() == DTOState.UPDATED);
        // If it hasn't already changed, check if the values have changed
        if (!changed) {
            changed = DTOUtils.isPropertyChanged(x,y);
        }
        return changed;
    }
    
    /*
     * Used for PropertyChangeSupport version by Object
     *
     * @param propertyName - use enumeration propertyName to track in descendant DTO
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     *
     */
    protected boolean isChanged(String propertyName, Object x, Object y) {
        // Determine if the property changed, if changed fire property changed support.
        boolean changed = DTOUtils.isPropertyChanged(x, y);
        //System.out.println("isChanged, Object changed=" + changed);
        if (changed) {
            //System.out.println("isChanged, Object firePropertyChange");
            getPropertyChangeSupport().firePropertyChange(propertyName, x, y);
        }
        else {
            changed = (this.getDTOState() == DTOState.UPDATED);
        }
        //System.out.println("isChanged exiting, Object changed=" + changed);

        return changed;
    }
    
    /*
     * Used for PropertyChangeSupport version by BaseDTO
     *
     * @param propertyName - use enumeration propertyName to track in descendant DTO
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     *
     */
    protected boolean isChanged(String propertyName, BaseDTO x, BaseDTO y) {
        // Determine if the property changed, if changed fire property changed support.
        boolean changed = isPropertyChanged(x, y);
        //System.out.println("isChanged, BaseDTO changed=" + changed);
        if (changed) {
            //System.out.println("isChanged, BaseDTO firePropertyChange");
            getPropertyChangeSupport().firePropertyChange(propertyName, x, y);
        }        
        else {
            changed = (this.getDTOState() == DTOState.UPDATED);
        }
        //System.out.println("isChanged exiting, BaseDTO changed=" + changed);
        
        return changed;
    }
    
    

    /*
     * Used for PropertyChangeSupport version by Date
     *
     * @param propertyName - use enumeration propertyName to track in descendant DTO
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     *
     */
    protected boolean isChanged(String propertyName, Date x, Date y) {
        // Determine if the property changed, if changed fire property changed support.
        boolean changed = DTOUtils.isPropertyChanged(x, y);
        if (changed) {
            getPropertyChangeSupport().firePropertyChange(propertyName, x, y);
        }        
        else {
            changed = (this.getDTOState() == DTOState.UPDATED);
        }
        return changed;
    }

    /**
     * This should be called from within the setter of all DTOs that extend BaseDTO. This method maintains the changed flag
     * dtoState.
     *
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     */
    protected boolean isChanged(BaseDTO x, BaseDTO y) {
        boolean changed = (this.getDTOState() == DTOState.UPDATED);
        //System.out.println("isChanged DTO, changed=" + changed);
        // If it hasn't already changed, check if the values have changed
        if (!changed) {
            changed = isPropertyChanged(x, y);
        }
        return changed;
    }
    
    /**
     * Determine if the property changed, used internally to determine if property change listener should be fired
     *
     * @param x original value.
     * @param y new value.
     * @return if changed.
     */
    private boolean isPropertyChanged(BaseDTO x, BaseDTO y) {
        boolean propertyChanged = false;
        if (x != null && y != null) {
            try {
                Object xPrimaryKey = x.getPrimaryKey();
                Object yPrimaryKey = y.getPrimaryKey();
                if (xPrimaryKey != null && yPrimaryKey != null) {
                    propertyChanged = (!xPrimaryKey.equals(yPrimaryKey));
                } else if ((xPrimaryKey == null && yPrimaryKey != null) || (xPrimaryKey != null && yPrimaryKey == null)) {
                    propertyChanged = true;
                }
            } catch (IllegalStateException e) {
                this.error("An MtsException has occurred while attempting to get PrimaryKey, Message:" + e.getMessage());
            }
        } else if ((x == null && y != null) || (x != null && y == null)) {
            propertyChanged = true;
        }
        //System.out.println("isPropertyChanged DTO, propertyChanged=" + propertyChanged);
        
        return propertyChanged;
    }
    

    /**
     * This method maintains the changed flag dtoState for int primatives.
     *
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     */
    protected boolean isChanged(int x, int y) {
        return this.isChanged(new Integer(x), new Integer(y));
    }

    /**
     * This method maintains the changed flag dtoState for Dates.
     *
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     */
    protected boolean isChanged(Date x, Date y) {
        return this.isChanged((Object) x, (Object) y);
    }

    /**
     * This method maintains the changed flag dtoState for Dates.
     *
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     */
    protected boolean isChanged(Timestamp x, Timestamp y) {
        return this.isChanged((Object) x, (Object) y);
    }

    /**
     * This method traps the case where the Timestamp/Date are being tracked
     *
     * This occurs when rs.getTimestamp is used in the DAO and Date is being set in via the client
     *
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     */
    protected boolean isChanged(Timestamp x, Date y) {
        throw new IllegalArgumentException("Timestamp, Date, are being tracked, please correct");
    }

    /**
     * This method traps the case where the Date/Timestamp are being tracked
     *
     * This occurs when rs.getDate is used in the DAO and Timestamp is being set via the client
     *
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     */
    protected boolean isChanged(Date x, Timestamp y) {
        throw new IllegalArgumentException("Date, Timestamp, are being tracked, please correct");
    }

    /**
     * This method maintains the changed flag dtoState for long primatives.
     *
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     */
    protected boolean isChanged(long x, long y) {
        return this.isChanged(new Long(x), new Long(y));
    }

    /**
     * This method maintains the changed flag dtoState for float primatives.
     *
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     */
    protected boolean isChanged(float x, float y) {
        return this.isChanged(new Float(x), new Float(y));
    }

    /**
     * This method maintains the changed flag dtoState for short primatives.
     *
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     */
    protected boolean isChanged(short x, short y) {
        return this.isChanged(new Short(x), new Short(y));
    }

    /**
     * This method maintains the changed flag dtoState for byte primatives.
     *
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     */
    protected boolean isChanged(byte x, byte y) {
        return this.isChanged(new Byte(x), new Byte(y));
    }

    /**
     * This method maintains the changed flag dtoState for boolean primatives.
     *
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     */
    protected boolean isChanged(boolean x, boolean y) {
        return this.isChanged(x + "", y + "");
    }

    /**
     * This method maintains the changed flag dtoState for char primatives.
     *
     * @param x original value.
     * @param y new value.
     * @return the changed dtoState.
     */
    protected boolean isChanged(char x, char y) {
        return this.isChanged(x + "", y + "");
    }

    /**
     * Returns a string summarizing the dtoState of the DTO.
     *
     * @return a string summarizing the dtoState of the DTO.
     */
    public String getDTOStates() {
        return "( isNew=" + this.isNew() + ", isNewModified=" + isNewModified() + ", isUpdated=" + this.isUpdated() + ", isDeleted=" + this.isDeleted() + " )";
    }

    /**
     * Returns the ChildDTOState to assist BaseDTO in determining the overall state of the DTO
     *
     * @return DTOState.
     */
    private DTOState getChildDTOState(BaseDTO baseDTO) {
        // Navigate to all levels from Parent to child to grandchild, etc....
        final String METHODNAME = "getChildDTOState";
        DTOState childDTOState = DTOState.UNSET;
        if (baseDTO == null) {
            System.out.println(METHODNAME + " - WARN: baseDTO was null.");
            return childDTOState;
        }
        Map<Class, List<BaseDTO>> children = baseDTO.getChildDTOMap();
        if (children == null) {
            System.out.println(METHODNAME + " - WARN: baseDTO.getChildDTOMap() was null.");
            return childDTOState;
        }
        Set<Entry<Class, List<BaseDTO>>> entrySet = children.entrySet();
        if (entrySet == null) {
            System.out.println(METHODNAME + " - WARN: children.entrySet() was null.");
            return childDTOState;
        }
        for (Map.Entry<Class, List<BaseDTO>> childrenDTOEntry : entrySet) {
            if (childrenDTOEntry == null) {
                System.out.println(METHODNAME + " - WARN: childrenDTOEntry was null.");
                continue;
            }
            List<BaseDTO> dtoList = childrenDTOEntry.getValue();
            if (dtoList == null) {
                System.out.println(METHODNAME + " - WARN: childrenDTOEntry.getValue() was null.");
                continue;
            }
            for (BaseDTO childDTO : dtoList) {
                if (childDTO == null) {
                    System.out.println(METHODNAME + " - WARN: childDTO was null.");
                    continue;
                }
                childDTOState = childDTO.getDTOState();
                if (childDTOState != DTOState.UNSET) {
                    break;
                } else {
                    // Recurse, ahhh, hope we come out alive :)
                    childDTOState = this.getChildDTOState(childDTO);
                    if (childDTOState != DTOState.UNSET) {
                        break;
                    }
                }
            }

            if (childDTOState != DTOState.UNSET) {
                break;
            }
        }
        return childDTOState;
    }

    public void addOrUpdateChildDTO(BaseDTO childDTO, Integer indexPos) {
        long start = System.nanoTime();
        Class queryClass = DTOUtils.getQueryClassFromDtoQueryMap(getClass(), childDTO.getClass());
        //System.out.println("Back from getQueryClass" + (System.nanoTime() - start)/1000000.0);

        // Does the DTO exist in the child map?
        if (queryClass != null) {
            // Get the ChildrenDTOs
            //start = System.nanoTime();
            List<BaseDTO> childDTOs = this.getChildrenDTOs(queryClass);
            //System.out.println("Back from getChildrenDTOs" + (System.nanoTime() - start)/1000000.0);

            // Short circuit indexOf if is new
            //start = System.nanoTime();

            int index = -1;
            if (!childDTO.isNew()) {
                // Search the ChildrenDTOs
                index = childDTOs.indexOf(childDTO);
            }
            //System.out.println("Back from childDTO.isNew()=" + childDTO.isNew() + " " + (System.nanoTime() - start)/1000000.0);
            if (index >= 0) {
                if (!childDTOs.get(index).isDeleted()) {
                    childDTOs.set(index, childDTO);
                } else {
                    childDTOs.add(childDTO);
                }
            } else {
                if (indexPos == null) {
                    childDTOs.add(childDTO);
                } else {
                    childDTOs.add(indexPos, childDTO);
                }
            }
        } else {
            // Possibly add recursion support such that the childDTO can be added or updated at the appropriate level
            throw new IllegalStateException("Child DTOClass " + childDTO.getClass() + " is not registered under this DTO"
                    + this.getClass() + ". Did you forget to call the registerChildDTO?");
        }
    }

    public void addOrUpdateChildDTO(BaseDTO childDTO) {
        this.addOrUpdateChildDTO(childDTO, null);
    }

    public void debug(String message) {
        if (debug) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
            System.out.println("DEBUG" + " " + this.getClass().getSimpleName() + " " + formatter.format(new Date()) + " " + message);
        }
    }

    public void error(String message) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        System.out.println("ERROR" + " " + this.getClass().getSimpleName() + " " + formatter.format(new Date()) + " " + message);
    }

    public Map<String, Object> getQueryMap() {
        if (queryMap == null) {
//            queryMap = new HashMap<String, Object>();
            queryMap = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
        }
        return queryMap;
    }

    /**
     * Get the value of uuid
     *
     * @return the value of uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Set the value of uuid
     *
     * @param uuid new value of uuid
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isTrackLastModDatetime() {
        return trackLastModDatetime;
    }

    public void setTrackLastModDatetime(boolean trackLastModDatetime) {
        this.trackLastModDatetime = trackLastModDatetime;
    }

    public boolean isQueryMapWild() {
        boolean wildcardExists = false;
        if (queryMap != null) {
            for (Object object : queryMap.values()) {
                if (object instanceof String) {
                    String value = (String) object;
                    wildcardExists = (value.indexOf("%") >= 0 || value.indexOf("*") >= 0);
                    if (wildcardExists) {
                        break;
                    }
                }
            }
        }
        return wildcardExists;
    }

    @Override
    public String toString() {
        String id = super.toString();
        try {
            id = getClass().getSimpleName() + '{' + "uuid=" + uuid + ", primaryKey=" + this.getPrimaryKey() + '}';
        } catch (IllegalStateException e) {
            System.err.println("Error computing toString() for " + getClass().getCanonicalName() + " - " + id);
            System.err.println(e);
        }
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return DTOUtils.dtoEquals(this, obj);
        } catch (IllegalStateException ex) {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        try {
            return DTOUtils.dtoHashCode(this);
        } catch (IllegalStateException ex) {
            return super.hashCode();
        }
    }
    
    @Override
    public void propertyChanged(String propertyName, Object newValue, boolean trackOldNewValue) {
        try { 
            Field field = ClassUtils.getDeclaredField(getClass(), propertyName);
            field.setAccessible(true);
            Object oldValue = field.get(this);
            //System.out.println("propertyChanged propertyName " + propertyName + " newValue=" + newValue + " oldValue=" + oldValue);
            setUpdated(isChanged(propertyName, oldValue, newValue));
        } catch (Exception ex) {
            System.err.println("An Exception has occurred when calling propertyChanged for propertyName " + propertyName + " Message: " + ex.getMessage());
        }        
    }
}
