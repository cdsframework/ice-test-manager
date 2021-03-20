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

import java.util.List;
import java.util.Map;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.CatException;
import org.primefaces.model.SortOrder;

/**
 *
 * @author HLN Consulting, LLC
 * @param <T>
 */
public interface DataTableInterface<T extends BaseDTO> {

    public T getRowData(T incomingDTO) throws MtsException;

    public T setRowData(T incomingDTO) throws MtsException;

    public boolean delete(T rowItem, boolean cascade) throws MtsException, CatException ;

    public void remove(T rowItem) throws MtsException, CatException;

    public T addOrUpdateDTO(T incomingDTO) throws MtsException;

    public int getDtoListSize();

    public List<T> getDtoList();

    public void setWrappedData(Object wrappedData) ;

    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);

    public Object getWrappedData() ;

    public Object getRowKey(T rowItem);

    public T getRowData(String rowKey);

    public void setRowCount(int rowCount);

    public int getRowCount();

    public void setPageSize(int pageSize);

    public int getPageSize();

    public int getRowOffset();

    public void setRowOffset(int rowOffset);

    public String getSortField() ;

    public void setSortField(String sortField) ;

    public SortOrder getSortOrder() ;

    public void setSortOrder(SortOrder sortOrder) ;

    public Map getFilters() ;

    public void setFilters(Map filters) ;

}
