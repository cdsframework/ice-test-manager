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
package org.cdsframework.ui.services;

import org.cdsframework.base.BaseDTO;
import org.cdsframework.datatable.DataTableInterface;
import org.cdsframework.util.enumeration.LoadStatus;

/**
 *
 * @author HLN Consulting, LLC
 */
public class DataTableParam {

    private String dataTableKey;
    private String dataTableId;
    private Class<? extends BaseDTO> dtoType;
    private String childQueryClass;
    private DataTableInterface<BaseDTO> dataTableInterface;
    private LoadStatus loadStatus;
    private boolean lazy;
    private BaseDTO selectedDTO;
    
    public Class<? extends BaseDTO> getDtoType() {
        return dtoType;
    }

    public void setDtoType(Class<? extends BaseDTO> dtoType) {
        this.dtoType = dtoType;
    }

    public String getChildQueryClass() {
        return childQueryClass;
    }

    public void setChildQueryClass(String childQueryClass) {
        this.childQueryClass = childQueryClass;
    }

    public DataTableInterface<BaseDTO> getDataTableInterface() {
        return dataTableInterface;
    }

    public void setDataTableInterface(DataTableInterface<BaseDTO> dataTableInterface) {
        this.dataTableInterface = dataTableInterface;
    }

    public LoadStatus getLoadStatus() {
        return loadStatus;
    }

    public void setLoadStatus(LoadStatus loadStatus) {
        this.loadStatus = loadStatus;
    }

    public String getDataTableId() {
        return dataTableId;
    }

    public void setDataTableId(String dataTableId) {
        this.dataTableId = dataTableId;
    }

    public String getDataTableKey() {
        return dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public boolean isLazy() {
        return lazy;
    }

    public boolean getLazy(boolean lazy) {
//            final String METHODNAME = "getLazy ";
        this.lazy = lazy;
//            logger.info(METHODNAME, "lazy=", lazy);
        return this.lazy;
    }

    public BaseDTO getSelectedDTO() {
        return selectedDTO;
    }

    public void setSelectedDTO(BaseDTO selectedDTO) {
        this.selectedDTO = selectedDTO;
    }

}
