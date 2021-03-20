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
import java.util.Map;
import javax.faces.component.UIComponent;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.datatable.DataTableInterface;
import org.cdsframework.util.JsfUtils;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.enumeration.RecordPosition;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.model.SortOrder;

/**
 *
 * @author HLN Consulting, LLC
 */
public class GotoRecordService {
    
    protected static LogUtils logger = LogUtils.getLogger(GotoRecordService.class);

    public static void gotoRecord(RecordPosition recordPosition, DataAccessInterface dataAccessInterface, BaseDTO baseDTO) {
        gotoRecord(recordPosition, dataAccessInterface, null, baseDTO);
    }
    
    private static void gotoRecord(RecordPosition recordPosition, DataAccessInterface dataAccessInterface, String dataTableKey, BaseDTO baseDTO) {
        final String METHODNAME = "gotoRecord ";
        BaseDTO returnDTO = null;
        boolean lazy = dataAccessInterface.isLazy(dataTableKey);
        logger.info(METHODNAME, "recordPosition=", recordPosition, " baseDTO=", baseDTO, " lazy=", lazy);

        Object key = gotoRecordKey(baseDTO);
        if (key != null) {
            if (recordPosition != null) {
                // store the current tab index for restoration after we have loaded the new record
//                TabView tabView = getTabView();
                DataTableInterface<BaseDTO> dataTableInterface = dataAccessInterface.getDataTableInterface(dataTableKey);
//                Integer originalTabIndex = tabView != null ? tabView.getActiveIndex() : null;
//                logger.debug(METHODNAME, "original active tab index: ", originalTabIndex);
                List<BaseDTO> dtoList = dataTableInterface.getDtoList();
                logger.info(METHODNAME, "dtoList.size()=", dtoList.size());

                int lastRowIndex;
                if (lazy) {
                    // last index = the lazy row count minus 1
                    lastRowIndex = dataTableInterface.getRowCount() - 1;
                } else {
                    // last index = the non-lazy data table dto list size minus 1
                    lastRowIndex = dataTableInterface.getDtoListSize() - 1;
                }

                int rowIndex = 0;
                if (recordPosition == RecordPosition.First) {
                    // if First - leave the rowIndex @ 0
                } else if (recordPosition == RecordPosition.Last) {
                    rowIndex = lastRowIndex;
                } else {
                    // handle previous or next
                    rowIndex = getDataTableRecordPosition(dataTableInterface, lazy, key);
                    logger.info(METHODNAME, "initial row index: ", rowIndex);
                    if (recordPosition == RecordPosition.Previous) {
                        rowIndex--;
                    } else if (recordPosition == RecordPosition.Next) {
                        rowIndex++;
                    }
                    logger.info(METHODNAME, "adjusted row index: ", rowIndex);
                    // adjust for lazy offset
                    // note: we may be able to always do this subtraction if we always got a value from this call...
                    if (lazy) {
                        rowIndex = rowIndex - dataTableInterface.getRowOffset();
                    }
                }

                // select the DTO if it is in the current DTO list
                if (rowIndex >= 0 && rowIndex < dtoList.size()) {
                    if ( // if it is lazy and not a rowOffset or last position operation
                            (lazy && recordPosition != RecordPosition.First && recordPosition != RecordPosition.Last)
                            // or not lazy
                            || !lazy) {
                        returnDTO = dtoList.get(rowIndex);
                    }
                }

                logger.info(METHODNAME, "returnDTO=", returnDTO, " rowIndex=", rowIndex);

                boolean updateDataTable = false;

                // place to store the beginning row number of the page to display
                int newRowOffset = 0;
                DataTable dataTable = (DataTable) UtilityMGR.getUIComponentById(UtilityMGR.getUIComponentUpdateId(dataAccessInterface.getDataTableId(dataTableKey), false));
                
                // if not lazy - we have everything we need
                if (!lazy) {
                    if (returnDTO != null) {
                        // Compute the new row offset position
                        int rowsPerPage = 0;
                        if (dataTable != null) {
                            rowsPerPage = dataTable.getRows();
                        }
                        if (rowsPerPage == 0) {
                            //
                            // To do, create getter/setter to capture rowsPerPage defined in dtoDataTable, 
                            // Presently this is not communicated 
                            //
                            // For now, hard code to default defined in dtoDataTable #{cc.attrs.paginator ? 15 : null}
                            // With out this, the newRowOffset never changes the page for non-lazy loaded datatables
                            rowsPerPage = 15;
                        }
                        if (rowsPerPage != 0) {
                            newRowOffset = (rowIndex / rowsPerPage) * rowsPerPage;
                        }
                        logger.info(METHODNAME, "newRowOffset=", newRowOffset, " rowIndex=", rowIndex, " rowsPerPage=", rowsPerPage);
                        updateDataTable = true;
                    }
                } // if lazy we may need to load a new page if the returnDTO is null
                else // the returnDTO will be null on a lazy datatable if the record we want isn't in the current page of data
                // we need to get the page that does contain it by setting a new row offset to display and select from
                {
                    if (returnDTO == null) {
                        int rowOffset = dataTableInterface.getRowOffset();
                        int pageSize = dataTableInterface.getPageSize();
                        SortOrder sortOrder = dataTableInterface.getSortOrder();
                        String sortField = dataTableInterface.getSortField();
                        Map filters = dataTableInterface.getFilters();
                        int rowCount = dataTableInterface.getRowCount();

                        logger.info(METHODNAME, "moving recordPosition=", recordPosition, " rowOffset=", rowOffset,
                                " pageSize=", pageSize, " rowCount=", rowCount, " returnDTO=", returnDTO);

                        // if this is a rowOffset operation then just get the rowOffset page
                        if (recordPosition == RecordPosition.First) {
                            newRowOffset = 0;
                        } // if it is a next operation and it wasn't in the current page data then it is in the next page data
                        // adding the page size to the current row offset  gets the next page data
                        else if (recordPosition == RecordPosition.Next) {
                            newRowOffset = rowOffset + pageSize;
                        } // if it is a previous operation and it wasn't in the current page then it is in the previous page data
                        // subtracting the page size from the current row offset gets the previous page data
                        else if (recordPosition == RecordPosition.Previous) {
                            newRowOffset = rowOffset - pageSize;
                        } // if it is a last operation then we need to get the last page data
                        else if (recordPosition == RecordPosition.Last) {
                            newRowOffset = (rowCount / pageSize) * pageSize;
                            // if the row new row offset = the row count then we need to remove a page from the offset
                            if (newRowOffset == rowCount) {
                                logger.debug(METHODNAME, "newRowOffset == rowCount: backing off a page");
                                newRowOffset -= pageSize;
                            }
                        }

                        logger.debug(METHODNAME, " newRowOffset=", newRowOffset);

                        // if we have a new row offset that is between 0 and the row count
                        if (newRowOffset >= 0 && newRowOffset < rowCount) {
                            // load the new page
                            dtoList = dataTableInterface.load(newRowOffset, pageSize, sortField, sortOrder, filters);
                            logger.info(METHODNAME, "dtoList.isEmpty()=", dtoList.isEmpty());

                            // if the page of rows load returned results
                            if (!dtoList.isEmpty()) {
                                // if we had to load a page and it is a first or next operation then the dto we want to
                                // select is in the first position
                                if (recordPosition == RecordPosition.First || recordPosition == RecordPosition.Next) {
                                    logger.info(METHODNAME, "scenario1: recordPosition=", recordPosition, " rowIndex=", rowIndex, " returnDTO=", returnDTO);
                                    returnDTO = dtoList.get(0);
                                    rowIndex = 0;
                                } // if we had to load a page and it is a last or previous operation then the dto we want to
                                // select is in the last position
                                else if (recordPosition == RecordPosition.Last || recordPosition == RecordPosition.Previous) {
                                    logger.info(METHODNAME, "scenario2: recordPosition=", recordPosition, " rowIndex=", rowIndex, " returnDTO=", returnDTO);
                                    returnDTO = dtoList.get(dtoList.size() - 1);
                                    rowIndex = dtoList.size() - 1;
                                }
                            } // if we requested a page of rows before or after the range of records dtoList will be empty
                            else {
                                logger.info(METHODNAME, "initial load brought back an empty list - adjusting for a previous/next page depending on the operation request: ", recordPosition);
                                // reverse the loader as we have reached at the begining or end of the resultset
                                // if our previous load attempt was a next or last then we need to back off a page from the row offset
                                if (recordPosition == RecordPosition.Last || recordPosition == RecordPosition.Next) {
                                    newRowOffset = rowOffset - pageSize;
                                    logger.info(METHODNAME, " determining new row offset: method A ", newRowOffset);
                                } // if our previous load attempt was a first or previous operation then we need to move forward a page
                                else if (recordPosition == RecordPosition.First || recordPosition == RecordPosition.Previous) {
                                    newRowOffset = rowOffset + pageSize;
                                    logger.info(METHODNAME, " determining new row offset: method B ", newRowOffset);
                                }
                                // get the new page
                                dtoList = dataTableInterface.load(newRowOffset, pageSize, sortField, sortOrder, filters);
                                // we should get a list back from this load unless something went wrong
                                if (!dtoList.isEmpty()) {
                                    // if we overran the list we set the row index and returnDTO to the last dto in the record set
                                    if (recordPosition == RecordPosition.Last || recordPosition == RecordPosition.Next) {
                                        returnDTO = dtoList.get(dtoList.size() - 1);
                                        rowIndex = dtoList.size() - 1;
                                        logger.info(METHODNAME, "scenario3: recordPosition=", recordPosition, " rowIndex=", rowIndex, " returnDTO=", returnDTO);
                                    } // if we underran the list we set the row index and returnDTO to the first item in the record set
                                    else if (recordPosition == RecordPosition.First || recordPosition == RecordPosition.Previous) {
                                        returnDTO = dtoList.get(0);
                                        rowIndex = 0;
                                        logger.info(METHODNAME, "scenario4: recordPosition=", recordPosition, " rowIndex=", rowIndex, " returnDTO=", returnDTO);
                                    }
                                } // something is wrong
                                else {
                                    logger.error(METHODNAME, "unable to load a the page of dto!");
                                }
                            }
                            // set the result back on the datatable manager
                            dataTableInterface.setWrappedData(dtoList);
                        } else {
                            logger.warn(METHODNAME, "got a new row offset that wasn't between 0 and the row count! ", newRowOffset);
                        }
                        logger.info(METHODNAME, "moving recordPosition=", recordPosition, " rowOffset=", rowOffset, " newRowOffset=", newRowOffset,
                                " pageSize=", pageSize, " dtoList.size()=", dtoList.size(), " rowIndex=", rowIndex);

                        updateDataTable = true;
                    }
                }

                logger.info(METHODNAME, "returnDTO=", returnDTO, " rowIndex=", rowIndex, "newRowOffset=", newRowOffset, " updateDataTable=", updateDataTable);

                if (returnDTO != null) {
                    dataAccessInterface.getModule().setParentDTO(returnDTO);
//                    setParentDTO(returnDTO);
                    logger.info(METHODNAME, "dataTable=", dataTable);
                    if ( dataTable != null ) {
                        logger.info(METHODNAME, "dataTable.getClientId()=", dataTable.getClientId());
                    }
                    
                    if (dataTable != null) {
                        dataTable.setFirst(newRowOffset);

                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        if (updateDataTable) {
                            JsfUtils.setUpdateIds(METHODNAME, dataTable.getClientId());
                            //requestContext.update(dataAccessInterface.getDataTableId(dataTableKey));
                            //setUpdateIds(METHODNAME, getDataTableUpdateId());
                        }
                        int rowToSelect = 0;
                        //logger.debug(METHODNAME, "before rowToSelect=", rowToSelect, " rowIndex=", rowIndex, " newRowOffset=", newRowOffset);
                        if (!lazy) {
                            if (rowIndex > newRowOffset) {
                                rowToSelect = rowIndex - newRowOffset;
                            }
                        } else {
                            rowToSelect = rowIndex;
                        }
                        String dataTableWidgetVar = dataTable.getWidgetVar();
                        //logger.debug(METHODNAME, "after rowToSelect=", rowToSelect, " rowIndex=", rowIndex, " newRowOffset=", newRowOffset);
                        requestContext.execute("PF('" + dataTableWidgetVar + "').unselectAllRows();" + "PF('" + dataTableWidgetVar + "').selectRow(" + rowToSelect + ");");
//                        // restore the active tab index on the new record
//                        if (originalTabIndex != null) {
//                            logger.debug(METHODNAME, "restoring original active tab index: ", originalTabIndex);
//                            tabView.setActiveIndex(originalTabIndex);
//                        } else {
//                            logger.debug(METHODNAME, "not restoring original active tab index: ", originalTabIndex);
//                        }
                    }
                }
            } else {
                logger.error(METHODNAME, "recordPosition was null!");
            }
        } else {
            logger.error(METHODNAME, "key was null!");
        }
    }

    /**
     * Return the record position in the DTO list.
     *
     * @return -1 if the object isn't in the dto list - the actual list index otherwise
     */
    private static int getDataTableRecordPosition(DataTableInterface<BaseDTO> dataTableInterface, boolean lazy, Object key) {
        final String METHODNAME = "getDataTableRecordPosition ";
        int result = -1;
        if (key != null) {
            if (dataTableInterface != null) {
                List<BaseDTO> dtoList = dataTableInterface.getDtoList();
                if (dtoList != null) {

                    boolean matched = false;
                    // determine the row index of the selected DTO
                    int rowIndex = 0;
                    for (BaseDTO dto : dtoList) {
                        // increment the row index until a matched is found
                        // if none is found then match will remain false
                        if (!key.equals(gotoRecordKey(dto))) {
                            rowIndex++;
                        } else {
                            matched = true;
                            break;
                        }
                    }
                    // if no match was found the result should stay -1
                    if (matched) {
                        if (lazy) {
                            // adjust the rowIndex to account for lazy loading
                            // rowOffset = the record offset
                            rowIndex += dataTableInterface.getRowOffset();
                        }
                        result = rowIndex;
                    } else {
                        logger.warn(METHODNAME, "unmatched dto key: ", key);
                    }
                }
            }
        } else {
            logger.error(METHODNAME, "key is null!");
        }
        logger.debug(METHODNAME, "record position: ", result);
        return result;
    }    

    public static int getDataTableRecordPosition(DataAccessInterface dataAccessInterface, BaseDTO baseDTO) {
        return getDataTableRecordPosition(dataAccessInterface, null, baseDTO);
    }
    
    private static int getDataTableRecordPosition(DataAccessInterface dataAccessInterface, String dataTableKey, BaseDTO baseDTO) {
        return getDataTableRecordPosition(dataAccessInterface.getDataTableInterface(dataTableKey), 
                dataAccessInterface.isLazy(dataTableKey), gotoRecordKey(baseDTO));
    }
    
    // Can be overriden in descendant to provide alternate key
    private static Object gotoRecordKey(BaseDTO dto) {
        Object result = null;
        if (dto != null) {
            result = dto.getUuid();
        }
        return result;
    }

    public static boolean isGotoCommonRecordEnabled(DataAccessInterface dataAccessInterface) {
        return isGotoCommonRecordEnabled(dataAccessInterface, null);
    }
    
    private static boolean isGotoCommonRecordEnabled(DataAccessInterface dataAccessInterface, String dataTableKey) {
        final String METHODNAME = "isGotoCommonRecordEnabled ";
        boolean retValue = true;
        DataTableInterface dataTableInterface = dataAccessInterface.getDataTableInterface(dataTableKey);
        boolean lazy = dataAccessInterface.isLazy(dataTableKey);
        if (dataTableInterface != null) {
            List<BaseDTO> dtoList = dataTableInterface.getDtoList();
            if (dtoList != null) {
                if (dtoList.size() > 0) {
                    if (!lazy) {
                        // for future logic
                    } else {
                        // for future logic
                    }
                } else {
                    retValue = false;
                }
            } else {
                retValue = false;
            }
        } else {
            retValue = false;
        }
        logger.debug(METHODNAME, "retValue: ", retValue);
        return retValue;
    }    

    /**
     * Return the state of the Previous button
     */
    public static boolean evaluateGotoPreviousRecordEnabled(DataAccessInterface dataAccessInterface, BaseDTO baseDTO) {
        return evaluateGotoPreviousRecordEnabled(dataAccessInterface, null, baseDTO);
    }
    
    private static boolean evaluateGotoPreviousRecordEnabled(DataAccessInterface dataAccessInterface, String dataTableKey, BaseDTO baseDTO) {
        final String METHODNAME = "evaluateGotoPreviousRecordEnabled ";
        boolean retValue = isGotoCommonRecordEnabled(dataAccessInterface);
//        boolean result = isGotoCommonRecordEnabled();
        if (retValue) {
            int dataTableRecordPosition = GotoRecordService.getDataTableRecordPosition(dataAccessInterface, baseDTO);
//            int dataTableRecordPosition = getDataTableRecordPosition(gotoRecordKey(getParentDTO()));
            if (dataTableRecordPosition < 1) {
                retValue = false;
            }
        }
        logger.debug(METHODNAME, "retValue: ", retValue);
        return retValue;
    }

    /**
     * Return the state of the Next button
     */
    
    public static boolean evaluateGotoNextRecordEnabled(DataAccessInterface dataAccessInterface, BaseDTO baseDTO) {
        return evaluateGotoNextRecordEnabled(dataAccessInterface, null, baseDTO);
    }
    
    private static boolean evaluateGotoNextRecordEnabled(DataAccessInterface dataAccessInterface, String dataTableKey, BaseDTO baseDTO) {
        final String METHODNAME = "evaluateGotoNextRecordEnabled ";
//        boolean result = isGotoCommonRecordEnabled();
        boolean retValue = isGotoCommonRecordEnabled(dataAccessInterface);
        if (retValue) {
            DataTableInterface<BaseDTO> dataTableInterface = dataAccessInterface.getDataTableInterface(null);
//            int dataTableRecordPosition = getDataTableRecordPosition(gotoRecordKey(getParentDTO()));
//            DataTableInterface dataTableInterface = getDataTableMGR();
            if (dataTableInterface != null) {
                int rowCount;
                boolean lazy = dataAccessInterface.isLazy(dataTableKey);
                
                if (lazy) {
                    // with lazy loading the rowCount is the # of DTOs
                    rowCount = dataTableInterface.getRowCount();
                } else {
                    // non-lazy datatables contain the complete list of DTOs
                    rowCount = dataTableInterface.getDtoListSize();
                }
                int dataTableRecordPosition = GotoRecordService.getDataTableRecordPosition(dataAccessInterface, baseDTO);
//                int dataTableRecordPosition = getDataTableRecordPosition(gotoRecordKey(getParentDTO()));
                if (dataTableRecordPosition == rowCount - 1) {
                    retValue = false;
                    logger.debug(METHODNAME, "record is in last index position!");
                }
            } else {
                retValue = false;
            }
        }
        logger.debug(METHODNAME, "retValue: ", retValue);
        return retValue;
    }    
}
