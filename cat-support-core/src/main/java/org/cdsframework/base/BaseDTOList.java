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
package org.cdsframework.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.cdsframework.application.Mts;
import org.cdsframework.client.support.GeneralMGRClient;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.util.DTOUtils;

/**
 *
 * @param <T>
 * @author HLN Consulting, LLC
 */
public abstract class BaseDTOList<T extends BaseDTO> extends BaseList<T> {

    private static final long serialVersionUID = -5408234183095097073L;

    @Inject
    private Mts mts;
    private SessionDTO sessionDTO;
    private GeneralMGRClient generalMGRClient;
    private T queryCriteriaDTO;
    private String queryClass = "FindAll";
    private boolean dirty = true;
    // 1 hour age limit on cached data
    private static final int AGE_LIMIT = 3600000;
    private long lastRetrieved = System.currentTimeMillis();
    private Class<T> dtoClassType;
    private List<Class<? extends BaseDTO>> childDtoClasses = new ArrayList<Class<? extends BaseDTO>>();

    public GeneralMGRClient getGeneralMGRClient() {
        return generalMGRClient;
    }

    public Mts getMts() {
        return mts;
    }

    public SessionDTO getSessionDTO() {
        return sessionDTO;
    }

    @Override
    protected void initializeMain() {
        try {
            dtoClassType = this.getArgumentClassType();
            if (generalMGRClient == null) {
                generalMGRClient = mts.getGeneralMGR();
                sessionDTO = mts.getSession();
                queryCriteriaDTO = dtoClassType.newInstance();
                initialize();
                initializeData();
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
        }

    }

    protected void initialize() throws Exception {
    }

    private void initializeData() {
        final String METHODNAME = "initializeData ";
        final long start = System.nanoTime();
        if (logger.isDebugEnabled()) {
            logger.info("initializeData for DTO list: ", this.getArgumentClassType());
            logger.info("initializeData state isQueryDynamic: ", isQueryDynamic());
            logger.info("initializeData state dirty: ", isDirty());
        }
        try {
            if (!isQueryDynamic() && isDirty()) {
                List<T> dtos = getSearchResults();
                if (logger.isDebugEnabled()) {
                    logger.info("Initializing DTO list for: ", this.getClass().getSimpleName(), " for DTO: ", this.getArgumentClassType(), " - count: ", dtos.size());
                }
                initializeData(dtos);
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, start);                                                                            
        }
    }
    
    public void reset() {
        initializeData(new ArrayList<T>());
    }

    protected void initializeData(List<T> dtoList) {
        try {
            if (dtoList != null) {
                List<T> newList = new LinkedList<T>();
                this.setAll(newList);
                Map<Object, T> newMap = new LinkedHashMap<Object, T>();
                this.setAllMap(newMap);
                for (T item : dtoList) {
                    Object primaryKey = item.getPrimaryKey();
                    if (!filterListItem(item)) {
                        newList.add(item);
                        newMap.put(primaryKey, item);
                    } else {
                        logger.debug("filtering ", this.getArgumentClassType().getSimpleName(), " lookup dto: ", primaryKey);
                    }
                }
                Comparator dtoComparator = DTOUtils.getDtoComparator(dtoClassType);
                if (dtoComparator != null) {
                    Collections.sort(newList, dtoComparator);
                }
                setDirty(false);
                lastRetrieved = System.currentTimeMillis();
            } else {
                logger.warn("dtoList is null - still dirty");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    public void addChildDtoClass(Class<? extends BaseDTO> childClass) {
        getChildDtoClasses().add(childClass);
    }

    public T getQueryCriteriaDTO() {
        return queryCriteriaDTO;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
//        initializeData();
    }

    @Override
    public List<T> getAll() {
        if (isDirty()) {
            initializeData();
        } else {
            checkCacheExpired();
        }
        List<T> all = super.getAll();
        if (all == null) {
            all = new ArrayList<T>();
            logger.error("all list is null!");
        }
        return all;
    }

    @Override
    public Map<Object, T> getAllMap() {
        if (isDirty()) {
            initializeData();
        } else {
            checkCacheExpired();
        }
        return super.getAllMap();
    }

    protected List<T> getSearchResults() {
        List<T> findByQueryList = null;
        try {
            PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
            propertyBagDTO.setChildClassDTOs(getChildDtoClasses());
            propertyBagDTO.setQueryClass(queryClass);
            findByQueryList = generalMGRClient.findByQueryList(queryCriteriaDTO, sessionDTO, propertyBagDTO);
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return findByQueryList;
    }

    @Override
    protected String getStringFromItem(T item) {
        String result = null;
        try {
            result = (String) item.getPrimaryKey();
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return result;
    }

    @Override
    protected T getItemFromString(String itemString) {
        final String METHODNAME = "getItemFromString ";
        T item = null;
        try {
            if (itemString != null) {
                if (isQueryDynamic()) {
                    queryCriteriaDTO = dtoClassType.newInstance();
                    queryCriteriaDTO.setPrimaryKey(itemString);
                    PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                    propertyBagDTO.setChildClassDTOs(getChildDtoClasses());
                    item = generalMGRClient.findByPrimaryKey(queryCriteriaDTO, sessionDTO, propertyBagDTO);
                } else {
                    List<Class> primaryKeyClasses = DTOUtils.getPrimaryKeyClasses(dtoClassType);
                    if (primaryKeyClasses.size() == 1) {
                        Class primaryKeyClass = primaryKeyClasses.get(0);
                        if (primaryKeyClass != String.class) {
                            if (primaryKeyClass == int.class || primaryKeyClass == Integer.class) {
                                item = this.getAllMap().get(Integer.parseInt(itemString));
                            } else if (primaryKeyClass == long.class || primaryKeyClass == Long.class) {
                                item = this.getAllMap().get(Long.parseLong(itemString));
                            }
                        }
                    } else {
                        logger.error(METHODNAME, "multiple key primary keys are not supported!");
                    }
                }
            } else {
                logger.warn(METHODNAME, "itemString is null!");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return item;
    }

    public String getQueryClass() {
        return queryClass;
    }

    public void setQueryClass(String queryClass) {
        this.queryClass = queryClass;
    }

    public void setQueryClass(Class queryClass) {
        this.queryClass = queryClass.getSimpleName();
    }

    /**
     * Get the value of childDtoClasses
     *
     * @return the value of childDtoClasses
     */
    public List<Class<? extends BaseDTO>> getChildDtoClasses() {
        return childDtoClasses;
    }

    /**
     * Set the value of childDtoClasses
     *
     * @param childDtoClasses new value of childDtoClasses
     */
    public void setChildDtoClasses(List<Class<? extends BaseDTO>> childDtoClasses) {
        this.childDtoClasses = childDtoClasses;
    }

    private void checkCacheExpired() {
        if ((System.currentTimeMillis() - lastRetrieved) > AGE_LIMIT) {
            setDirty(true);
        }
    }
}
