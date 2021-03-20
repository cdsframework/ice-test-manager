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

import java.util.List;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.datatable.DataTableInterface;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.util.enumeration.SourceOperation;
import org.cdsframework.util.enumeration.LoadStatus;
import org.cdsframework.util.enumeration.SourceMethod;

/**
 *
 * @author HLN Consulting, LLC
 */
public interface DataAccessInterface {
    public boolean isInline();
    public BaseModule getModule();
    public Object[] getMessageArgs(BaseDTO dto, SourceMethod sourceMethod) throws Exception ;    
    public BaseDTO getSearchCriteriaDTO(String queryClass) throws Exception;
    public List<Class<? extends BaseDTO>> getChildClassDTOs(String queryClass, SourceOperation childClassDTOs);
    public PropertyBagDTO getPropertyBagDTO(String queryClass, SourceOperation sourceOperation);
    public void initialize(List<BaseDTO> dtos);
    public void initializeLazyDataInterface(String queryClass, SourceOperation sourceOperation, String dataTableKey);
    public DataTableInterface<BaseDTO> getDataTableInterface(String dataTableKey);
    public String getChildQueryClass(String dataTableKey);
    public void setDataTableInterface(DataTableInterface<BaseDTO> dataTableInterface, String dataTableKey);
    public void setLoadStatus(LoadStatus loadStatus, String dataTableKey);
    public LoadStatus getLoadStatus(String dataTableKey);
    public boolean isLazy(String dataTableKey);
    public String getDataTableId(String dataTableKey);

}
