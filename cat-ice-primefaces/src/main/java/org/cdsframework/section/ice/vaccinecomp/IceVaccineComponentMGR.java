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
package org.cdsframework.section.ice.vaccinecomp;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsListItemDTO;
import org.cdsframework.dto.IceDiseaseDTO;
import org.cdsframework.dto.IceVaccineComponentDTO;
import org.cdsframework.dto.IceVaccineComponentDiseaseRelDTO;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class IceVaccineComponentMGR extends BaseModule<IceVaccineComponentDTO> {

    private static final long serialVersionUID = 1104988278099149017L;

    @Override
    protected void initialize() {
        setLazy(true);
        setSaveImmediately(true);
        setBaseHeader("Vaccine Component");
        setInitialQueryClass("FindAll");
    }

    @Override
    public void registerTabComponents() {
        getTabService().registeredUIComponent(0, this);
    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        BaseDTO selectedDTO = getSelectedDTO();
        IceVaccineComponentDTO parentDTO = getParentDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);
        String targetFieldName = getTargetFieldName();

        if (baseDTO instanceof CdsListItemDTO) {
            CdsListItemDTO cdsListItemDTO = (CdsListItemDTO) baseDTO;
            CdsCodeDTO cdsCodeDTO = cdsListItemDTO.getCdsCodeDTO();
            if ("vaccineGroup".equalsIgnoreCase(targetFieldName)) {
                if (selectedDTO instanceof IceVaccineComponentDTO) {
                    IceVaccineComponentDTO iceVaccineComponentDTO = (IceVaccineComponentDTO) selectedDTO;
                    iceVaccineComponentDTO.getQueryMap().put("group_id", cdsListItemDTO.getRefId());
                    iceVaccineComponentDTO.getQueryMap().put("group", cdsListItemDTO);
                }
            } else if (parentDTO != null) {
                parentDTO.setVaccineComponent(cdsCodeDTO);
            }
        } else if (baseDTO instanceof IceDiseaseDTO) {
            IceDiseaseDTO iceDiseaseDTO = (IceDiseaseDTO) baseDTO;
            if (selectedDTO instanceof IceVaccineComponentDiseaseRelDTO) {
                IceVaccineComponentDiseaseRelDTO iceVaccineComponentDiseaseRelDTO = (IceVaccineComponentDiseaseRelDTO) selectedDTO;
                iceVaccineComponentDiseaseRelDTO.setIceDiseaseDTO(iceDiseaseDTO);
            }
        }

        // Clear out selected DTO
        setSelectedDTO(null);
    }

    @Override
    protected void clearSearch(ActionEvent actionEvent) throws Exception {
        setSelectedDTO(null);
    }

}
