/**
 * CAT CDS support plugin project.
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
 *
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see https://www.hln.com/services/open-source/ or send
 * correspondence to ice@hln.com.
 */
package org.cdsframework.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTOList;
import org.cdsframework.dto.CdsBusinessScopeDTO;
import org.cdsframework.dto.CdsVersionDTO;
import org.cdsframework.dto.OpenCdsConceptDTO;
import org.cdsframework.dto.OpenCdsConceptRelDTO;
import org.cdsframework.dto.CdsListDTO;
import org.cdsframework.dto.CdsListItemDTO;
import org.cdsframework.dto.CdsListVersionRelDTO;
import org.cdsframework.dto.CdsVersionConceptDeterminationMethodRelDTO;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.util.ListUtils;
import org.cdsframework.util.comparator.CdsListComparator;
import org.cdsframework.util.comparator.CdsListItemComparator;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ApplicationScoped
public class CdsListDTOList extends BaseDTOList<CdsListDTO> {

    private final static CdsListComparator cdsListComparator = new CdsListComparator();
    private final static CdsListItemComparator cdsListItemComparator = new CdsListItemComparator();
    private static final long serialVersionUID = -1903152058426477335L;
    @Inject
    private CdsBusinessScopeDTOList cdsBusinessScopeDTOList;

    @Override
    protected void initialize() throws Exception {
        addChildDtoClass(CdsListItemDTO.class);
        addChildDtoClass(CdsListVersionRelDTO.class);
        addChildDtoClass(OpenCdsConceptRelDTO.class);
        addChildDtoClass(CdsVersionConceptDeterminationMethodRelDTO.class);
    }

    /**
     * Return the list of CdsListDTOs based on a version.
     *
     * @param cdsVersionDTO
     * @return
     */
    public List<CdsListDTO> getListSelectItems(CdsVersionDTO cdsVersionDTO) {
        List<CdsVersionDTO> versionList = null;
        if (cdsVersionDTO != null) {
            versionList = new ArrayList<CdsVersionDTO>();
            versionList.add(cdsVersionDTO);
        }
        return getListSelectItemsByVersionList(versionList);
    }

    /**
     * Return the list of CdsListDTOs based on a list of versions.
     *
     * @param cdsVersionDTOs
     * @return
     */
    public List<CdsListDTO> getListSelectItemsByVersionList(List<CdsVersionDTO> cdsVersionDTOs) {
        final String METHODNAME = "getListSelectItemsByVersionList ";
        cdsVersionDTOs = getFullCdsVersionDTOs(cdsVersionDTOs);
        List<CdsListDTO> result = new ArrayList<CdsListDTO>();
        if (cdsVersionDTOs == null) {
            logger.warn(METHODNAME, "cdsVersionDTOs is null!");
        }
        for (CdsListDTO cdsListDTO : getAll()) {
            if (cdsListDTO != null
                    && cdsListDTO.getRelatedVersions() != null) {
                if ((ListUtils.doesListAContainItemFromListB(cdsListDTO.getRelatedVersions(), cdsVersionDTOs) || !cdsListDTO.isVersioned())
                        && !result.contains(cdsListDTO)) {
                    result.add(cdsListDTO);
                }
            }
        }
        Collections.sort(result, cdsListComparator);
        return result;
    }

    /**
     * Return a list of CdsListItemDTOs based on a listId and a version.
     *
     * @param listId
     * @param cdsVersionDTO
     * @return
     */
    public List<CdsListItemDTO> getListItemsById(String listId, CdsVersionDTO cdsVersionDTO) {
        List<CdsVersionDTO> versionList = null;
        if (cdsVersionDTO != null) {
            versionList = new ArrayList<CdsVersionDTO>();
            versionList.add(cdsVersionDTO);
        }
        return getListItemsByIdVersionList(listId, versionList);
    }

    /**
     * Return a list of CdsListItemDTOs based on a listId and a version.
     *
     * @param listId
     * @param cdsVersionDTOs
     * @return
     */
    public List<CdsListItemDTO> getListItemsByIdVersionList(String listId, List<CdsVersionDTO> cdsVersionDTOs) {
        final String METHODNAME = "getListItemsByIdVersionList ";
        CdsListDTO cdsListDTO = null;
        if (listId != null && !listId.isEmpty()) {
            for (CdsListDTO item : getAll()) {
                if (item.getListId().equals(listId)) {
                    cdsListDTO = item;
                    break;
                }
            }
            if (cdsListDTO == null) {
                logger.error(METHODNAME, "listId not found: ", listId);
            }
        } else {
            logger.error(METHODNAME, "listId is null!");
        }
        return getListItemsByVersionList(cdsListDTO, cdsVersionDTOs);
    }

    /**
     * Return a list of CdsListItemDTOs based on a code and version.
     *
     * @param code
     * @param cdsVersionDTO
     * @return
     */
    public List<CdsListItemDTO> getListItemsByCode(String code, CdsVersionDTO cdsVersionDTO) {
        final String METHODNAME = "getListItemsByCode ";
        List<CdsVersionDTO> versionList = null;
        if (cdsVersionDTO != null) {
            versionList = new ArrayList<CdsVersionDTO>();
            versionList.add(cdsVersionDTO);
        }
        logger.debug(METHODNAME, code, " - ", cdsVersionDTO);
        return getListItemsByCodeVersionList(code, versionList);
    }

    /**
     * Return a list of CdsListItemDTOs based on a code and a list of versions.
     *
     * @param code
     * @param cdsVersionDTOs
     * @return
     */
    public List<CdsListItemDTO> getListItemsByCodeVersionList(String code, List<CdsVersionDTO> cdsVersionDTOs) {
        final String METHODNAME = "getListItemsByCodeVersionList ";
        CdsListDTO cdsListDTO = null;
        if (code != null && !code.isEmpty()) {
            for (CdsListDTO item : getAll()) {
                if (item.getCode().equalsIgnoreCase(code)) {
                    cdsListDTO = item;
                    break;
                }
            }
            if (cdsListDTO == null) {
                logger.error(METHODNAME, "code not found: ", code);
            }
        } else {
            logger.error(METHODNAME, "code is null!");
        }
        logger.debug(METHODNAME, cdsListDTO, " - ", cdsVersionDTOs);
        return getListItemsByVersionList(cdsListDTO, cdsVersionDTOs);
    }

    /**
     * Return a list of CdsListItemDTOs based on a cdsListDTO and a version.
     *
     * @param cdsListDTO
     * @param cdsVersionDTO
     * @return
     */
    public List<CdsListItemDTO> getListItems(CdsListDTO cdsListDTO, CdsVersionDTO cdsVersionDTO) {
        final String METHODNAME = "getListItems ";
        logger.debug(METHODNAME, "called: ", cdsListDTO, " - ", cdsVersionDTO);
        List<CdsVersionDTO> versionList = new ArrayList<CdsVersionDTO>();
        List<CdsListItemDTO> listItemsByVersionList = new ArrayList<CdsListItemDTO>();
        try {
            if (cdsVersionDTO != null) {
                versionList.add(cdsVersionDTO);
            }
            listItemsByVersionList = getListItemsByVersionList(cdsListDTO, versionList);
            logger.debug(METHODNAME, "result: ", listItemsByVersionList);
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return listItemsByVersionList;
    }

    /**
     * Return a list of CdsListItemDTOs based on a cdsListDTO and a list of versions.
     *
     * @param cdsListDTO
     * @param cdsVersionDTOs
     * @return
     */
    public List<CdsListItemDTO> getListItemsByVersionList(CdsListDTO cdsListDTO, List<CdsVersionDTO> cdsVersionDTOs) {
        final String METHODNAME = "getListItemsByVersionList ";
        cdsVersionDTOs = getFullCdsVersionDTOs(cdsVersionDTOs);
        logger.debug(METHODNAME, "cdsListDTO=", cdsListDTO);
        logger.debug(METHODNAME, "cdsVersionDTOs=", cdsVersionDTOs);
        if (cdsVersionDTOs == null) {
            logger.warn(METHODNAME, "cdsVersionDTOs was null!");
            cdsVersionDTOs = new ArrayList<CdsVersionDTO>();
        }
        List<CdsListItemDTO> result = new ArrayList<CdsListItemDTO>();
        if (cdsListDTO != null) {
            CdsListDTO actualCdsListDTO = get(cdsListDTO.getListId());
            logger.debug(METHODNAME, "actualCdsListDTO=", actualCdsListDTO);
            if (actualCdsListDTO != null) {
                if (logger.isDebugEnabled()) {
                    logger.info(METHODNAME, "isVersioned()=", actualCdsListDTO.isVersioned());
                    logger.info(METHODNAME, " not isVersioned()=", !actualCdsListDTO.isVersioned());
                    logger.info(METHODNAME, "isVersioned() and isConceptBased()=", actualCdsListDTO.isVersioned() && actualCdsListDTO.isConceptBased());
                    logger.info(METHODNAME, "isVersioned() and not isConceptBased()=", actualCdsListDTO.isVersioned() && !actualCdsListDTO.isConceptBased());
                    logger.info(METHODNAME, "isVersioned() and related version list contains items from the supplied version list =", actualCdsListDTO.isVersioned()
                            && ListUtils.doesListAContainItemFromListB(actualCdsListDTO.getRelatedVersions(), cdsVersionDTOs));
                    logger.info(METHODNAME, "actualCdsListDTO.getRelatedVersions()=", actualCdsListDTO.getRelatedVersions());
                    logger.info(METHODNAME, "cdsVersionDTOs=", cdsVersionDTOs);
                }
                if (!actualCdsListDTO.isVersioned()
                        || (actualCdsListDTO.isVersioned() && !actualCdsListDTO.isConceptBased())
                        || (actualCdsListDTO.isVersioned() && ListUtils.doesListAContainItemFromListB(actualCdsListDTO.getRelatedVersions(), cdsVersionDTOs))) {
                    for (CdsListItemDTO cdsListItemDTO : actualCdsListDTO.getCdsListItemDTOs()) {
                        logger.debug(METHODNAME, "cdsListItemDTO=", cdsListItemDTO);
                        if (!result.contains(cdsListItemDTO)) {
                            if (!actualCdsListDTO.isConceptBased()) {
                                result.add(cdsListItemDTO);
                            } else if (!actualCdsListDTO.isVersioned()) {
                                logger.error(METHODNAME, "concept-based lists require a version!");
                            } else {
                                OpenCdsConceptDTO openCdsConceptDTO = cdsListItemDTO.getOpenCdsConceptDTO();
                                logger.debug(METHODNAME, "openCdsConceptDTO=", openCdsConceptDTO);
                                if (openCdsConceptDTO == null) {
                                    logger.warn(METHODNAME, "openCdsConceptDTO is null!");
                                } else {
                                    for (OpenCdsConceptRelDTO openCdsConceptRelDTO : openCdsConceptDTO.getOpenCdsConceptRelDTOs()) {
                                        logger.debug(METHODNAME, "openCdsConceptRelDTO=", openCdsConceptRelDTO);
                                        logger.debug(METHODNAME, "openCdsConceptRelDTO.getConceptDeterminationMethodDTO()=", openCdsConceptRelDTO.getConceptDeterminationMethodDTO());
                                        boolean added = false;
                                        for (CdsVersionDTO cdsVersionDTO : cdsVersionDTOs) {
                                            logger.debug(METHODNAME, "cdsVersionDTO=", cdsVersionDTO);
                                            logger.debug(METHODNAME, "cdsVersionDTO.getConceptDeterminationMethodDTOs()=", cdsVersionDTO.getConceptDeterminationMethodDTOs());
                                            if (cdsVersionDTO.getConceptDeterminationMethodDTOs().contains(openCdsConceptRelDTO.getConceptDeterminationMethodDTO())) {
                                                result.add(cdsListItemDTO);
                                                added = true;
                                                break;
                                            }
                                        }
                                        logger.debug(METHODNAME, "added=", added);
                                        if (added) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (result.isEmpty()) {
                        logger.warn(METHODNAME, "list items result was empty!");
                    }
                }
            } else {
                logger.warn(METHODNAME, "actualCdsListDTO was null! This is ok for a new list...");
            }
        } else {
            logger.error(METHODNAME, "cdsListDTO is null!");
        }
        Collections.sort(result, cdsListItemComparator);
        return result;
    }

    private List<CdsVersionDTO> getFullCdsVersionDTOs(List<CdsVersionDTO> cdsVersionDTOs) {
        final String METHODNAME = "getFullCdsVersionDTOs ";
        List<CdsVersionDTO> realList = new ArrayList<CdsVersionDTO>();
        if (cdsVersionDTOs != null) {
            for (CdsVersionDTO cdsVersionDTO : cdsVersionDTOs) {
                CdsBusinessScopeDTO cdsBusinessScopeDTO = cdsBusinessScopeDTOList.get(cdsVersionDTO.getBusinessScopeId());
                List<CdsVersionDTO> tmpList = cdsBusinessScopeDTO.getCdsVersionDTOs();
                int indexOf = tmpList.indexOf(cdsVersionDTO);
                if (indexOf >= 0) {
                    realList.add(tmpList.get(indexOf));
                }
            }
        } else {
            logger.warn(METHODNAME, "cdsVersionDTOs was null!");
        }
        return realList;
    }
}
