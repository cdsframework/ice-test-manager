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
import org.cdsframework.base.BaseDTO;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.util.LogUtils;

/**
 *
 * @param <T>
 * @author HLN Consulting, LLC
 */
public class DataTableServices<T extends BaseDTO> {

    protected final LogUtils logger;
    private Object wrappedData;

    public DataTableServices() {
        super();
        logger = LogUtils.getLogger(DataTableServices.class);
    }

    public Object getRowKey(T rowItem) {
        Object result = null;
        if (rowItem != null) {
            result = rowItem.getUuid().toString();
        }
        return result;
    }

    public T getRowData(String rowKey) {
        T result = null;
        if (rowKey != null) {
//            List<T> dtoList = (List<T>) wrappedData;
            List<T> dtoList = getDtoList();
            for (T item : dtoList) {
                if (rowKey.equals(item.getUuid().toString())) {
                    result = item;
                    break;
                }
            }
        }
        return result;
    }

    public T getRowData(T incomingDTO) throws MtsException {
        return retrieveRowData(incomingDTO, false);
    }

    public T setRowData(T incomingDTO) throws MtsException {
        return retrieveRowData(incomingDTO, true);
    }

    private T retrieveRowData(T incomingDTO, boolean setDTO) throws MtsException {
        final String METHODNAME = "retrieveRowData ";
        T result = null;
        int i = 0;
        int index = -1;
        if (incomingDTO != null) {
            logger.debug(METHODNAME, "incomingDTO.getUuid()=", incomingDTO.getUuid());
            List<T> dtoList = getDtoList();
            if (dtoList != null) {
                logger.debug(METHODNAME, "dtoList: ", dtoList.size());
            }

            if (dtoList != null && incomingDTO.hasPrimaryKey()) {
                index = dtoList.indexOf(incomingDTO);
            }
            if (index == -1) {
                for (T item : dtoList) {
                    logger.debug(METHODNAME, "item.getUuid()=", item.getUuid());
                    if (incomingDTO.getUuid().equals(item.getUuid())) {
                        index = i;
                        logger.info(METHODNAME, "match on item.getUuid()=", item.getUuid());
                        break;
                    }
                    i++;
                }
            }
            if (setDTO) {
                logger.debug(METHODNAME, "index=", index);
                result = incomingDTO;
                if (index >= 0) {
                    logger.debug(METHODNAME, "updaing dto, index=", index);
                    dtoList.set(index, incomingDTO);
                } else {
                    logger.debug(METHODNAME, "adding dto, index=", index);
                    dtoList.add(0, incomingDTO);
                }
            } else {
                if (index >= 0) {
                    result = dtoList.get(index);
                } else {
                    logger.error(METHODNAME, "DTO not found: ", incomingDTO.getPrimaryKey());
                }
            }
        } else {
            logger.debug(METHODNAME, "incomingDTO was null!");
        }
        return result;
    }

    public boolean delete(T rowItem, boolean cascade) throws MtsException, CatException {
        boolean deleted = false;
        if (rowItem != null) {
            T dtoToDelete = getRowData(rowItem);
            if (dtoToDelete != null) {
                deleted = true;
                if (dtoToDelete.isNew()) {
                    logger.warn("Removing new row: ", dtoToDelete.getPrimaryKey());
                    remove(rowItem);
                } else {
                    dtoToDelete.delete(cascade);
                }
            } else {
                throw new CatException(logger.error("Didn't find a match for row: ", rowItem.getUuid(), " - aborting..."));
            }
        } else {
            throw new CatException("Row is null");
        }
        return deleted;
    }

    public void remove(T rowItem) throws MtsException, CatException {
        if (rowItem != null) {
            //List<T> dtoList = (List<T>) wrappedData;
            List<T> dtoList = getDtoList();
            T dtoToDelete = getRowData(rowItem);
            if (dtoToDelete != null) {
                boolean result = dtoList.remove(dtoToDelete);
                logger.warn("Delete/Remove result: ", result);
            } else {
                throw new CatException(logger.error("Didn't find a match for row: ", rowItem.getUuid(), " - aborting..."));
            }
        } else {
            throw new CatException("Row is null");
        }
    }

    public T addOrUpdateDTO(T incomingDTO) throws MtsException {
        final String METHODNAME = "addOrUpdateDTO ";
        T result = null;
        if (incomingDTO != null) {
            result = setRowData(incomingDTO);
        } else {
            logger.error(METHODNAME, "incomingDTO was null...");
        }
        return result;
    }

    public int getDtoListSize() {
        int size = 0;
        List<T> dtoList = getDtoList();
        if (dtoList != null) {
            size = dtoList.size();
        }
        return size;
    }

    public List<T> getDtoList() {
        return (List<T>) wrappedData;
    }

    public Object getWrappedData() {
        return wrappedData;
    }

    public void setWrappedData(Object wrappedData) {
        this.wrappedData = wrappedData;
    }
}
