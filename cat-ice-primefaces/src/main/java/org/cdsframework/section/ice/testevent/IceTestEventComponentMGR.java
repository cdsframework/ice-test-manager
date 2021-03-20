/**
 * CAT ICE support plugin project.
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
package org.cdsframework.section.ice.testevent;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsListItemDTO;
import org.cdsframework.dto.IceTestEvaluationDTO;
import org.cdsframework.dto.IceTestEventComponentDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
import org.cdsframework.util.enumeration.PrePost;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author sdn
 */
@Named
@ViewScoped
public class IceTestEventComponentMGR extends BaseModule<IceTestEventComponentDTO> {

    private static final long serialVersionUID = -854781582291785147L;

    @Inject
    private IceTestEventMGR iceTestEventMGR;

    @Override
    protected void initialize() {
        setSaveImmediately(true);
    }

    @Override
    public void registerTabComponents() {
        getTabService().registeredUIComponent(0, this);
    }

    @Override
    public void prePostOperation(String queryClass, BaseDTO baseDTO, PrePost prePost, boolean status) {
        final String METHODNAME = "prePostOperation ";
        logger.info(METHODNAME, "queryClass=", queryClass, " baseDTO=", baseDTO, " prePost=", prePost, " status=", status);
        switch (prePost) {
            case PostSave:
            case PostDelete:
            case PostInlineSave:
            case PostInlineDelete:
                if (status) {
                    logger.info(METHODNAME, "refreshing ice test event component...");
                    refresh();
                    setDataTableSearchUpdateIds();
                    iceTestEventMGR.refresh();
                    iceTestEventMGR.setDataTableSearchUpdateIds();
                }
                break;
        }
    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        BaseDTO selectedDTO = getSelectedDTO();
        String targetFieldName = getTargetFieldName();
        IceTestEventComponentDTO parentDTO = getParentDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);

        if (baseDTO instanceof IceVaccineGroupDTO) {
            IceVaccineGroupDTO iceVaccineGroupDTO = (IceVaccineGroupDTO) baseDTO;
            if (selectedDTO instanceof IceTestEventComponentDTO) {
                IceTestEventComponentDTO iceTestEventComponentDTO = (IceTestEventComponentDTO) selectedDTO;
                if ("iceVaccineGroupDTO".equals(targetFieldName)) {
                    logger.info(METHODNAME, "IceVaccineGroupDTO, IceTestEventComponentDTO, iceVaccineGroupDTO");
                    iceTestEventComponentDTO.setIceVaccineGroupDTO(iceVaccineGroupDTO);
                }
            }
        } else if (baseDTO instanceof CdsListItemDTO) {
            CdsListItemDTO cdsListItemDTO = (CdsListItemDTO) baseDTO;
            logger.info(METHODNAME, "cdsListItemDTO.getCdsCodeDTO()=", cdsListItemDTO.getCdsCodeDTO().getLabel());
            if (selectedDTO instanceof IceTestEventComponentDTO) {
                IceTestEventComponentDTO iceTestEventComponentDTO = (IceTestEventComponentDTO) selectedDTO;
                logger.info(METHODNAME, "iceTestEventComponentDTO.getUuid()=", iceTestEventComponentDTO.getUuid());
                logger.info(METHODNAME, "iceTestEventComponentDTO.getEventComponentId()=", iceTestEventComponentDTO.getEventComponentId());
                if ("evaluationValue".equals(targetFieldName)) {
                    logger.info(METHODNAME, "CdsListItemDTO, IceTestEventComponentDTO, evaluationValue");
                    iceTestEventComponentDTO.setEvaluationValue(cdsListItemDTO.getCdsCodeDTO());
                }
            } else if (selectedDTO instanceof IceTestEvaluationDTO) {
                IceTestEvaluationDTO iceTestEvaluationDTO = (IceTestEvaluationDTO) selectedDTO;
                if ("evaluationInterpretation".equals(targetFieldName)) {
                    logger.info(METHODNAME, "CdsListItemDTO, IceTestEvaluationDTO, evaluationInterpretation");
                    iceTestEvaluationDTO.setEvaluationInterpretation(cdsListItemDTO.getCdsCodeDTO());
                }
            }
        }
        // Clear out selected DTO
        setSelectedDTO(null);
    }

}
