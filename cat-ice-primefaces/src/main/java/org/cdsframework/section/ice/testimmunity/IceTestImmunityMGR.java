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
package org.cdsframework.section.ice.testimmunity;

import java.util.ArrayList;
import java.util.Date;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsListItemDTO;
import org.cdsframework.dto.IceTestImmunityDTO;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.interfaces.OffsetBasedEventType;
import org.cdsframework.section.ice.test.IceTestMGR;
import org.cdsframework.util.CdsDateUtils;
import org.cdsframework.util.UtilityMGR;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class IceTestImmunityMGR extends BaseModule<IceTestImmunityDTO> {

    @Inject
    private UtilityMGR utilityMGR;
    private String immDateAtAge;
    @Inject
    private IceTestMGR iceTestMGR;
    private boolean showSummary;
    private static final long serialVersionUID = 6227699105836578317L;

    @Override
    protected void initialize() {
        setSaveImmediately(true);
        this.setBaseHeader("Antigen");
    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        BaseDTO selectedDTO = getSelectedDTO();
        String targetFieldName = getTargetFieldName();
        IceTestImmunityDTO parentDTO = getParentDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);

        if (baseDTO instanceof CdsCodeDTO) {
            CdsCodeDTO cdsCodeDTO = (CdsCodeDTO) baseDTO;
            if ("antigen".equals(targetFieldName)) {
                parentDTO.setImmunityFocus(cdsCodeDTO);
            }
        } else if (baseDTO instanceof CdsListItemDTO) {
            CdsListItemDTO cdsListItemDTO = (CdsListItemDTO) baseDTO;
            if ("immunityValue".equals(targetFieldName)) {
                parentDTO.setImmunityValue(cdsListItemDTO.getCdsCodeDTO());
            }
        }
        // Clear out selected DTO
        setSelectedDTO(null);
    }

    @Override
    public void preSetParentDTO(IceTestImmunityDTO parentDTO) {
        immDateAtAge = null;
        setShowSummary(false);
    }

    public String getImmDateAtAge() {
        return immDateAtAge;
    }

    public void setImmDateAtAge(String immDateAtAge) {
        this.immDateAtAge = immDateAtAge;
    }

    public Date getImmunityDate(IceTestImmunityDTO iceTestImmunityDTO) throws MtsException {
        final String METHODNAME = "getImmunityDate ";
        Date result = null;
        try {
            IceTestDTO iceTestDTO = iceTestMGR.getParentDTO();
            if (iceTestImmunityDTO != null && iceTestDTO != null) {
                result = CdsDateUtils.getOffsetBasedEventDate(iceTestImmunityDTO, iceTestDTO, new ArrayList<OffsetBasedEventType>());
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return result;
    }

    public String getAgeAtImmunityDate(IceTestImmunityDTO iceTestImmunityDTO, boolean withBr) throws MtsException {
        final String METHODNAME = "getAgeAtImmunityDate ";
        String result = "";
        try {
            IceTestDTO iceTestDTO = iceTestMGR.getParentDTO();
            if (iceTestImmunityDTO != null && iceTestDTO != null) {
                if (withBr) {
                    result = UtilityMGR.getDateDiffComboWithBR(iceTestMGR.getIceTestDob(iceTestDTO), getImmunityDate(iceTestImmunityDTO));
                } else {
                    result = UtilityMGR.getDateDiffCombo(iceTestMGR.getIceTestDob(iceTestDTO), getImmunityDate(iceTestImmunityDTO));
                }
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return result;
    }

    public void calcImmDateAtAge(ActionEvent actionEvent) throws CatException {
        final String METHODNAME = "calcImmDateAtAge ";
        try {
            logger.info("immDateAtAge=", immDateAtAge);
            IceTestDTO iceTestDTO = (IceTestDTO) getParentMGR().getParentDTO();
            IceTestImmunityDTO parentDTO = getParentDTO();
            if (parentDTO != null) {
                if (iceTestDTO != null) {
                    Date iceTestDob = CdsDateUtils.getOffsetBasedTypeDob(iceTestDTO);
                    if (iceTestDob != null) {
                        Date result = utilityMGR.incrementDateFromString(iceTestDob, immDateAtAge, false);
                        if (result != null) {
                            parentDTO.setObservationEventTime(result);
                        }
                    } else {
                        throw new CatException(METHODNAME + "The DOB date must be set.");
                    }
                } else {
                    throw new CatException(METHODNAME + "The iceTestDTO object was null.");
                }
            } else {
                throw new CatException(METHODNAME + "The parentDTO object was null.");
            }
            immDateAtAge = null;
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    /**
     * Get the value of showSummary
     *
     * @return the value of showSummary
     */
    public boolean isShowSummary() {
        return showSummary;
    }

    /**
     * Set the value of showSummary
     *
     * @param showSummary new value of showSummary
     */
    public void setShowSummary(boolean showSummary) {
        this.showSummary = showSummary;
    }
}
