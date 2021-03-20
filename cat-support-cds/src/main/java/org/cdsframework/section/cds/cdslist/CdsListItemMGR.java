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
package org.cdsframework.section.cds.cdslist;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsListDTO;
import org.cdsframework.dto.CdsListItemDTO;
import org.cdsframework.dto.OpenCdsConceptDTO;
import org.cdsframework.lookup.CdsListDTOList;
import org.cdsframework.util.StringUtils;
import org.cdsframework.util.enumeration.PrePost;
import org.primefaces.event.SelectEvent;
import org.primefaces.util.ComponentUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class CdsListItemMGR extends BaseModule<CdsListItemDTO> {

    private static final long serialVersionUID = -1007795011690022724L;
    @Inject
    private CdsListDTOList cdsListDTOList;

    @Override
    protected void initialize() {
        setLazy(true);
        setBaseHeader("List Item");
        setSaveImmediately(true);
        setAssociatedList(cdsListDTOList);
    }

    @Override
    public void prePostOperation(String queryClass, BaseDTO baseDTO, PrePost prePost, boolean status) {
        final String METHODNAME = "prePostOperation ";
        super.prePostOperation(queryClass, baseDTO, prePost, status);
        switch (prePost) {
            case PostAdd:
                if (status) {
                    CdsListItemDTO childDTO = getParentDTO();
                    BaseModule parentMGR = getParentMGR();
                    if (parentMGR != null && childDTO != null) {
                        BaseDTO parentDTO = parentMGR.getParentDTO();
                        if (parentDTO != null) {
                            CdsListDTO cdsListDTO = (CdsListDTO) parentDTO;
                            childDTO.setItemType(cdsListDTO.getListType());
                        }
                    }
                    try {
                        setUpdateIds(METHODNAME, ComponentUtils.findComponentClientId("globalSampleSelectMenu"));
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
                break;
        }
    }

    @Override
    public void resetField(BaseDTO baseDTO, String fieldName) {
        super.resetField(baseDTO, fieldName); //To change body of generated methods, choose Tools | Templates.
        final String METHODNAME = "resetField ";

        if (baseDTO instanceof CdsListItemDTO) {
            CdsListItemDTO cdsListItemDTO = (CdsListItemDTO) baseDTO;
            if (fieldName.equalsIgnoreCase("openCdsConceptDTO")) {
                cdsListItemDTO.setOpenCdsConceptDTO(null);
            }
        }

    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        logger.info(METHODNAME, "baseDTO=", (baseDTO != null ? baseDTO.getClass().getSimpleName() : baseDTO));
        BaseDTO selectedDTO = getSelectedDTO();
        logger.info(METHODNAME, "selectedDTO=", (selectedDTO != null ? selectedDTO.getClass().getSimpleName() : selectedDTO));

        if (baseDTO instanceof OpenCdsConceptDTO) {
            OpenCdsConceptDTO openCdsConceptDTO = (OpenCdsConceptDTO) baseDTO;
            if (selectedDTO instanceof CdsListItemDTO) {
                logger.info(METHODNAME, "calling setOpenCdsConceptDTO on selectedDTO");
                CdsListItemDTO cdsListItemDTO = (CdsListItemDTO) selectedDTO;
                cdsListItemDTO.setOpenCdsConceptDTO(openCdsConceptDTO);
            }
        }

        // Clear out selected DTO
        setSelectedDTO(null);
    }

    @Override
    public void clearSearchMain(ActionEvent actionEvent) {
        String listCode = (String) getSearchCriteriaDTO().getQueryMap().get("listCode");
        String versionId = (String) getSearchCriteriaDTO().getQueryMap().get("versionId");
        super.clearSearchMain(actionEvent); //To change body of generated methods, choose Tools | Templates.
        if (!StringUtils.isEmpty(listCode)) {
            getSearchCriteriaDTO().getQueryMap().put("listCode", listCode);
        }
        if (!StringUtils.isEmpty(versionId)) {
            getSearchCriteriaDTO().getQueryMap().put("versionId", versionId);
        }
    }
}
