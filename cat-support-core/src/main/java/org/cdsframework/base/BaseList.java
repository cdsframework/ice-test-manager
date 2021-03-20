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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.util.ClassUtils;
import org.cdsframework.util.LogUtils;

/**
 *
 * @param <T>
 * @author HLN Consulting, LLC
 */
public abstract class BaseList<T> implements Serializable {
    private static final long serialVersionUID = 8123456763957537096L;

    protected LogUtils logger;
    private List<T> all;
    private Map<Object, T> allMap;
    private Class<T> argumentClassType;
    private boolean queryDynamic;

    @PostConstruct
    public void postConstructor() {
        try {
            logger = LogUtils.getLogger(getClass());
            if (argumentClassType == null) {
                argumentClassType = ClassUtils.getTypeArgument(BaseList.class, getClass());
                logger.debug("Initializing ", getClass().getSimpleName(), " with DTO: ", argumentClassType.getSimpleName());
            }
            if (all == null) {
                initializeMain();
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
        }
    }

    public boolean isQueryDynamic() {
        return queryDynamic;
    }

    public void setQueryDynamic(boolean queryDynamic) {
        this.queryDynamic = queryDynamic;
    }

    protected abstract void initializeMain();

    protected static boolean containsIgnoreCase(String source, String query) {
        boolean result = false;
        if (source != null && query != null && source.toLowerCase().contains(query.toLowerCase())) {
            result = true;
        }
        return result;
    }

    // for user override
    protected boolean filterListItem(T item) {
        return false;
    }

    protected boolean containsIgnoreCase(long source, String query) {
        return containsIgnoreCase(String.valueOf(source), query);
    }

    protected boolean containsIgnoreCase(Long source, String query) {
        return containsIgnoreCase(source.toString(), query);
    }

    protected boolean containsIgnoreCase(int source, String query) {
        return containsIgnoreCase(String.valueOf(source), query);
    }

    protected boolean containsIgnoreCase(Integer source, String query) {
        return containsIgnoreCase(source.toString(), query);
    }

    protected static boolean equalsIgnoreCase(String source, String query) {
        boolean result = false;
        if (source != null && query != null && source.equalsIgnoreCase(query)) {
            result = true;
        }
        return result;
    }

    protected boolean equalsIgnoreCase(long source, String query) {
        return equalsIgnoreCase(String.valueOf(source), query);
    }

    protected boolean equalsIgnoreCase(Long source, String query) {
        return equalsIgnoreCase(source.toString(), query);
    }

    protected boolean equalsIgnoreCase(int source, String query) {
        return equalsIgnoreCase(String.valueOf(source), query);
    }

    protected boolean equalsIgnoreCase(Integer source, String query) {
        return equalsIgnoreCase(source.toString(), query);
    }

    public T get(Object primaryKey) {
        T item = getAllMap().get(primaryKey);
        if (item == null && primaryKey != null && primaryKey.getClass() == String.class) {
            item = getItemFromString((String) primaryKey);
        }
        if (filterListItem(item)) {
            item = null;
        }
        return item;
    }

    public List<T> getSelectItems() {
        List<T> items = new ArrayList<T>();
        for (T item : getAll()) {
            if (!filterListItem(item)) {
                items.add(item);
            }
        }
        return items;
    }

    public Map<Object, T> getAllMap() {
        Map<Object, T> items = new HashMap<Object, T>();
        if (allMap != null) {
            for (Entry<Object, T> item : allMap.entrySet()) {
                if (!filterListItem(item.getValue())) {
                    items.put(item.getKey(), item.getValue());
                }
            }
        } else {
            logger.error("allMap is null!");
        }
        return items;
    }

    public void setAllMap(Map<Object, T> allMap) {
        this.allMap = allMap;
    }

    public List<T> autoComplete(String query) {
        List<T> matches = new ArrayList<T>();
        try {
            if (isQueryDynamic()) {
                matches = getDynamicQueryResults(query);
            } else {
                for (T item : all) {
                    if (autoCompleteTest(item, query) && !filterListItem(item)) {
                        matches.add(item);
                    }
                }
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
        }
        return matches;
    }

    public List<T> getAll() {
        return all;
    }

    public void setAll(List<T> all) {
        this.all = all;
    }

    public Class<T> getArgumentClassType() {
        return argumentClassType;
    }

    public List<String> stringAutoComplete(String query) {
        final String METHODNAME = "stringAutoComplete ";
        List<String> matches = new ArrayList<String>();
        try {
            if (isQueryDynamic()) {
                for (T item : getDynamicQueryResults(query)) {
                    if (!filterListItem(item)) {
                        String stringFromItem = getStringFromItem(item);
                        if (stringFromItem != null && !stringFromItem.trim().isEmpty()) {
                            matches.add(stringFromItem);
                            logger.debug(METHODNAME, "added: ", stringFromItem);
                        }
                    }
                }
            } else {
                for (T item : all) {
                    if (autoCompleteTest(item, query) && !filterListItem(item)) {
                        matches.add(getStringFromItem(item));
                    }
                }
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
        }
        return matches;
    }

    protected abstract String getStringFromItem(T item);

    protected abstract T getItemFromString(String itemString);

    protected boolean autoCompleteTest(T listInstance, String query) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public List<T> getDynamicQueryResults(String query) {
        throw new UnsupportedOperationException("Not implemented.");
    }
}