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
package org.cdsframework.section.cds.concept;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsCodeSystemDTO;
import org.cdsframework.dto.OpenCdsConceptRelDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.ValueSetDTO;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class OpenCdsConceptRelMGR extends BaseModule<OpenCdsConceptRelDTO> {
    private static final long serialVersionUID = 2365973358200827356L;
    
    @Override
    protected void initialize() {
        setLazy(true);
        setSaveImmediately(true);
    }    

    @Override
    public void resetField(BaseDTO baseDTO, String fieldName) {
        super.resetField(baseDTO, fieldName); 
        
        OpenCdsConceptRelDTO openCdsConceptRelDTO = (OpenCdsConceptRelDTO) baseDTO;
        if (fieldName.equalsIgnoreCase("valueSetDTO")) {
            openCdsConceptRelDTO.setValueSetDTO(null);
        }
        else if (fieldName.equalsIgnoreCase("cdsCodeSystemDTO")) {
            openCdsConceptRelDTO.setCdsCodeSystemDTO(null);
        }
        else if (fieldName.equalsIgnoreCase("cdsCodeDTO")) {
            openCdsConceptRelDTO.setCdsCodeDTO(null);
        }

    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onPopupSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        logger.info(METHODNAME);
        long startTime = System.nanoTime();
        try {
            // cdsCodeSystemDTO, valueSetDTO
            BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
            OpenCdsConceptRelDTO openCdsConceptRelDTO = (OpenCdsConceptRelDTO) getSelectedDTO();
            
            if (baseDTO instanceof CdsCodeDTO) {
                CdsCodeDTO cdsCodeDTO = (CdsCodeDTO) baseDTO;

                // Get the Code System
                CdsCodeSystemDTO queryCdsCodeSystemDTO = new CdsCodeSystemDTO();
                queryCdsCodeSystemDTO.setCodeSystemId(cdsCodeDTO.getCodeSystemId());
                CdsCodeSystemDTO cdsCodeSystemDTO = getGeneralMGR().findByPrimaryKey(queryCdsCodeSystemDTO, getSessionDTO(), new PropertyBagDTO());
                openCdsConceptRelDTO.setCdsCodeSystemDTO(cdsCodeSystemDTO);
                openCdsConceptRelDTO.setCdsCodeDTO(cdsCodeDTO);
                openCdsConceptRelDTO.setValueSetDTO(null);

            } else if (baseDTO instanceof CdsCodeSystemDTO) {
                CdsCodeSystemDTO cdsCodeSystemDTO = (CdsCodeSystemDTO) baseDTO;
                openCdsConceptRelDTO.setCdsCodeSystemDTO(cdsCodeSystemDTO);
                openCdsConceptRelDTO.setCdsCodeDTO(null);
                openCdsConceptRelDTO.setValueSetDTO(null);
                
            } else if (baseDTO instanceof ValueSetDTO) {
                ValueSetDTO valueSetDTO = (ValueSetDTO) baseDTO;
                openCdsConceptRelDTO.setCdsCodeSystemDTO(null);
                openCdsConceptRelDTO.setCdsCodeDTO(null);
                openCdsConceptRelDTO.setValueSetDTO(valueSetDTO);
                
            } else if (baseDTO instanceof OpenCdsConceptRelDTO) {

            }
        } finally {
            setSelectedDTO(null);
            logger.logDuration(METHODNAME, startTime);
            logger.logEnd(METHODNAME);
        }
    }

    @Override
    protected void onRowSelect(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onRowSelect";
        logger.info(METHODNAME, "selectEvent=", selectEvent);
    }
    
}
