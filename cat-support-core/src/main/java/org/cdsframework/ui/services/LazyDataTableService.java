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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.datatable.DataTableInterface;
import org.cdsframework.datatable.LazyDataTableMGR;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.listeners.StartupListener;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.enumeration.SourceOperation;
import org.cdsframework.util.enumeration.LoadStatus;
import org.cdsframework.util.enumeration.PrePost;
import org.cdsframework.util.support.CoreConstants;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.model.SortOrder;

/**
 *
 * @author HLN Consulting, LLC
 */
public class LazyDataTableService {

    private static LogUtils logger = LogUtils.getLogger(LazyDataTableService.class);
    private BaseModule module = null;
    private DataAccessInterface dataAccessInterface = null;
    private String dataTableKey = null;
    private final SourceOperation sourceOperation;

    public LazyDataTableService(DataAccessInterface dataAccessInterface, SourceOperation sourceOperation, String dataTableKey) {
        this.dataAccessInterface = dataAccessInterface;
        this.sourceOperation = sourceOperation;
        this.dataTableKey = dataTableKey;
        this.module = dataAccessInterface.getModule();
    }

    private DataTableInterface<BaseDTO> getLazyDataTableMGR(String queryClass) {
        final String METHODNAME = "getLazyDataTableMGR ";
        logger.debug(METHODNAME, "queryClass=", queryClass);

        // Initialize the Lazy Loader
        LazyDataTableMGR<BaseDTO> lazyDataTableMGR = new LazyDataTableMGR<BaseDTO>(getClass(), queryClass) {
            private static final long serialVersionUID = -292061090234204219L;

            @Override
            public List<BaseDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                String METHODNAME = "load ";
                boolean validationFailed = FacesContext.getCurrentInstance().isValidationFailed();
                String queryClass = getQueryClass();
                logger.debug(METHODNAME, "validationFailed=", validationFailed, " parameters: first - ", first, "; pageSize - ", pageSize, "; getPageSize() - ", getPageSize(),
                        "; sortField - ", sortField, "; sortOrder - ", sortOrder, "; filters - ", filters, "; queryClass -", queryClass);
                LoadStatus loadStatus = dataAccessInterface.getLoadStatus(dataTableKey); // getLastDTOState();
                logger.debug(METHODNAME, "module=", module, " queryClass=", queryClass, " loadStatus=", loadStatus);
                try {

                    List<BaseDTO> result;
//                    DataTableInterface<BaseDTO> dataTableInterface = getDataTableInterface();
                    DataTableInterface<BaseDTO> dataTableInterface = dataAccessInterface.getDataTableInterface(dataTableKey);

                    if (loadStatus == null && !validationFailed) {

                        // store old filters
                        Map oldFilters = dataTableInterface.getFilters();

                        // Store attribute to be used by gotoRecord
                        dataTableInterface.setRowOffset(first);
                        dataTableInterface.setPageSize(pageSize);
                        dataTableInterface.setSortField(sortField);
                        dataTableInterface.setSortOrder(sortOrder);
                        dataTableInterface.setFilters(filters);

                        if (queryClass != null) {
                            logger.debug(METHODNAME, "oldFilters: ", oldFilters, "; filters: ", filters, "; rowCount: ", dataTableInterface.getRowCount());

                            result = getLazyLoaderPage(queryClass);

                            if (oldFilters == null || !oldFilters.equals(filters)) {
                                // Get the row count - oldFilters is null the first time a datatable is rendered
                                Integer rowCount = getLazyLoaderRowCount(queryClass);
                                dataTableInterface.setRowCount(rowCount);
                            }
                            module.prePostOperation(queryClass, null, PrePost.PostRetrieve, result.size() > 0);

                        } else {
                            result = dataTableInterface.getDtoList();
                        }
                    } else {
                        result = dataTableInterface.getDtoList();
                    }
                    logger.debug(METHODNAME, "result.size()=", result.size());
                    return result;
                } catch (Exception e) {
                    module.onExceptionMain(METHODNAME, e, true);
                    return new ArrayList();
                } finally {
                    if (loadStatus != null && loadStatus == LoadStatus.StopClear) {
                        dataAccessInterface.setLoadStatus(null, dataTableKey);
                    }
                }
            }
        };

        // Handle case where the wrapped data is null, open search page do not search, click new and save
        if (lazyDataTableMGR.getDtoList().isEmpty()) {
            logger.debug(METHODNAME, "lazyDataTableMGR.getDtoList().isEmpty()=", lazyDataTableMGR.getDtoList().isEmpty());
            lazyDataTableMGR.setWrappedData(new ArrayList<BaseDTO>());
        }
        return lazyDataTableMGR;
    }

    public DataTableInterface<BaseDTO> initializeDataInterface(String queryClass) {
        final String METHODNAME = "initializeDataInterface ";
//        long start = System.nanoTime();
        logger.debug(METHODNAME, "queryClass=", queryClass);

        // Initialize the Lazy Loader,
        DataTableInterface<BaseDTO> dataTableInterface = getLazyDataTableMGR(queryClass);

        DataTable dataTable = (DataTable) UtilityMGR.getUIComponentFromBaseId(dataAccessInterface.getDataTableId(dataTableKey));
        if (dataTable != null) {
            dataTable.reset();
            dataTable.resetValue();
            dataTable.setRendered(true);
        }
        return dataTableInterface;
    }

    private List<BaseDTO> getLazyLoaderPage(String queryClass)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, Exception {
        final String METHODNAME = "getLazyLoaderPage ";
        long start = System.nanoTime();

        List<BaseDTO> resultDTOs;
        DataTableInterface<BaseDTO> dataTableInterface = dataAccessInterface.getDataTableInterface(dataTableKey);
//        DataTableInterface<BaseDTO> dataTableInterface = getDataTableInterface();

        int rowOffset = dataTableInterface.getRowOffset();
        int pageSize = dataTableInterface.getPageSize();
        String sortField = dataTableInterface.getSortField();
        boolean sortOrder = (dataTableInterface.getSortOrder() == SortOrder.ASCENDING);
        Map filters = dataTableInterface.getFilters();
        logger.debug(METHODNAME, " rowOffset=", rowOffset, " pageSize=", pageSize,
                " sortField=", sortField, " sortOrder=", sortOrder, " queryClass=", queryClass);
        BaseDTO queryDTO = dataAccessInterface.getSearchCriteriaDTO(queryClass);
        queryDTO.getQueryMap().put(CoreConstants.LAZY, true);
        queryDTO.getQueryMap().put(CoreConstants.LAZY_ROWCOUNT, false);
        queryDTO.getQueryMap().put(CoreConstants.LAZY_ROW_OFFSET, String.valueOf(rowOffset));
        queryDTO.getQueryMap().put(CoreConstants.LAZY_PAGE_SIZE, String.valueOf(pageSize));
        queryDTO.getQueryMap().put(CoreConstants.SORT_FIELD, sortField);
        queryDTO.getQueryMap().put(CoreConstants.SORT_ORDER, sortOrder);
        queryDTO.getQueryMap().put(CoreConstants.FILTERS, filters);

        PropertyBagDTO propertyBagDTO = dataAccessInterface.getPropertyBagDTO(queryClass, sourceOperation);
        propertyBagDTO.setChildClassDTOs(dataAccessInterface.getChildClassDTOs(queryClass, sourceOperation));
        resultDTOs = module.getMts().getGeneralMGR().findByQueryList(queryDTO, module.getSessionDTO(), propertyBagDTO);
        logger.info(METHODNAME, "queryClass=", queryClass, " retrieved resultDTOs.size()=", resultDTOs.size());

        return resultDTOs;
    }

    private Integer getLazyLoaderRowCount(String queryClass) {
        final String METHODNAME = "getLazyLoaderRowCount ";
        Integer rowCount = 0;
        try {
            LoadStatus loadStatus = dataAccessInterface.getLoadStatus(dataTableKey);
            DataTableInterface<BaseDTO> dataTableInterface = dataAccessInterface.getDataTableInterface(dataTableKey);
            if (queryClass != null && loadStatus == null) {
                logger.debug(METHODNAME, "queryClass=", queryClass, " loadStatus=", loadStatus);
                BaseDTO queryDTO = dataAccessInterface.getSearchCriteriaDTO(queryClass);
                logger.debug(METHODNAME, "queryDTO.getClass().getCanonicalName()=", queryDTO.getClass().getCanonicalName());
                queryDTO.getQueryMap().put(CoreConstants.LAZY, true);
                queryDTO.getQueryMap().put(CoreConstants.LAZY_ROWCOUNT, true);
                if (dataTableInterface != null) {
                    queryDTO.getQueryMap().put(CoreConstants.FILTERS, dataTableInterface.getFilters());
                }
                PropertyBagDTO propertyBagDTO = dataAccessInterface.getPropertyBagDTO(queryClass, this.sourceOperation);
                rowCount = module.getMts().getGeneralMGR().findObjectByQuery(queryDTO, module.getSessionDTO(), Integer.class, propertyBagDTO);
                logger.info(METHODNAME, "queryClass=", queryClass, " retrieved rowCount=", rowCount);

                // if the row count is 0: display error message
                if (!module.isChild() && !dataAccessInterface.isInline() && rowCount == 0) {
                    // have to use javascript - this gets called during render response phase - too late to add messages
                    String errorMessage = module.getMessageMGR().getResourceBundleKeyValue("noResults");
                    String js = String.format("PF('%s').renderMessage({'summary':'', 'detail':'%s', 'severity':'error'});", StartupListener.MESSAGE_DISPLAY_WIDGET_VAR, errorMessage);
                    RequestContext.getCurrentInstance().execute(js);
                }
            }
        } catch (Exception e) {
            module.onExceptionMain(METHODNAME, e);
        } finally {
            logger.debug(METHODNAME, "rowCount=", rowCount);
        }
        return rowCount;
    }

    public String getDataTableKey() {
        return dataTableKey;
    }

//    protected abstract BaseDTO getSearchDTO(String queryClass) throws Exception;
//    protected abstract String getOperationStatus();
//    protected abstract void removeOperationStatus();
//    protected abstract DataTableInterface<BaseDTO> getDataTableInterface();
//    protected abstract DataTable getDataTable() throws MtsException;
}
