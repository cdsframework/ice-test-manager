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
package org.cdsframework.datatable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.util.LogUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @param <T>
 * @author HLN Consulting, LLC
 */
public class LazyDataTableMGR<T extends BaseDTO> extends LazyDataModel<T> implements DataTableInterface<T> {
    private static final long serialVersionUID = 8567972432858444503L;

    protected final LogUtils logger;
    private DataTableServices dataTableServices = new DataTableServices<T>();
    private String queryClass;
    private int rowOffset;
    private String sortField;
    private SortOrder sortOrder;
    private Map filters;

    public LazyDataTableMGR() {
        super();
        logger = LogUtils.getLogger(LazyDataTableMGR.class);
    }

    public LazyDataTableMGR(Class childClass) {
        super();
        logger = LogUtils.getLogger(childClass);
    }
    
    public LazyDataTableMGR(Class childClass, String queryClass) {
        this(childClass);
        this.queryClass = queryClass;
    }

    @Override
    public Object getRowKey(T rowItem) {
        return dataTableServices.getRowKey(rowItem);
    }

    @Override
    public T getRowData(String rowKey) {
        return (T) dataTableServices.getRowData(rowKey);
    }

    @Override
    public T getRowData(T incomingDTO) throws MtsException {
        return (T) dataTableServices.getRowData(incomingDTO);
    }

    @Override
    public T setRowData(T incomingDTO) throws MtsException {
        return (T) dataTableServices.setRowData(incomingDTO);
    }

    @Override
    public boolean delete(T rowItem, boolean cascade) throws MtsException, CatException {
        return dataTableServices.delete(rowItem, cascade);
    }

    @Override
    public void remove(T rowItem) throws MtsException, CatException {
        dataTableServices.remove(rowItem);
    }

    @Override
    public T addOrUpdateDTO(T incomingDTO) throws MtsException {
        return (T) dataTableServices.addOrUpdateDTO(incomingDTO);
    }

    @Override
    public int getDtoListSize() {
        return dataTableServices.getDtoListSize();
    }

    @Override
    public List<T> getDtoList() {
        List<T> dtoList = dataTableServices.getDtoList();
        if (dtoList == null) {
            dtoList = new ArrayList<T>();
        }
        return dtoList;
    }

    @Override
    public Object getWrappedData() {
        return super.getWrappedData();
    }

    @Override
    public void setWrappedData(Object wrappedData) {
        super.setWrappedData(wrappedData);
        if (dataTableServices == null) {
            dataTableServices = new DataTableServices<T>();
        }
        dataTableServices.setWrappedData(wrappedData);
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getQueryClass() {
        return queryClass;
    }
    
    @Override
    public int getRowOffset() {
        return rowOffset;
    }

    @Override
    public void setRowOffset(int rowOffset) {
        this.rowOffset = rowOffset;
    }

    @Override
    public String getSortField() {
        return sortField;
    }

    @Override
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    @Override
    public Map getFilters() {
        return filters;
    }

    @Override
    public void setFilters(Map filters) {
        this.filters = filters;
    }

    @Override
    public SortOrder getSortOrder() {
        return this.sortOrder;
    }

    @Override
    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public void setRowIndex(int rowIndex) {
        if (getPageSize() == 0) {
            setPageSize(25);
        }
        super.setRowIndex(rowIndex);
    }
    
}
