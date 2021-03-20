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

import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.util.LogUtils;
import java.util.List;
import java.util.Map;
import javax.faces.model.ListDataModel;
import org.cdsframework.base.BaseDTO;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @param <T>
 * @author HLN Consulting, LLC
 */
public class DataTableMGR<T extends BaseDTO> extends ListDataModel<T> implements DataTableInterface<T>, SelectableDataModel<T> {

    protected final LogUtils logger;
    private DataTableServices dataTableServices = new DataTableServices<T>();

    public DataTableMGR() {
        super();
        logger = LogUtils.getLogger(DataTableMGR.class);
    }

    public DataTableMGR(Class childClass) {
        super();
        logger = LogUtils.getLogger(childClass);
    }

    public DataTableMGR(List<T> dtoList, Class childClass) {
        this(childClass);
        setWrappedData(dtoList);
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
        return dataTableServices.getDtoList();
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
    public Object getWrappedData() {
        return super.getWrappedData();
    }

    @Override
    public void setRowCount(int rowCount) {
        // Do nothing, only applies to lazy loaded datatable
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setPageSize(int pageSize) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getPageSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getRowOffset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setRowOffset(int first) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getSortField() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSortField(String sortField) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map getFilters() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setFilters(Map filters) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SortOrder getSortOrder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSortOrder(SortOrder sortOrder) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
