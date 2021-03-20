/**
 * The MTS support cds project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
 *
 * Copyright (C) 2016 New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * Contributions by HLN Consulting, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. You should have received a copy of the GNU Lesser
 * General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/> for more details.
 *
 * The above-named contributors (HLN Consulting, LLC) are also licensed by the
 * New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * to have (without restriction, limitation, and warranty) complete irrevocable
 * access and rights to this project.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; THE
 *
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING, BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR
 * OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see
 * https://www.hln.com/services/open-source/ or send correspondence to
 * ice@hln.com.
 */
package org.cdsframework.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Size;
import org.cdsframework.annotation.Column;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.ReferenceDTO;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CriteriaResourceDTO;
import org.cdsframework.dto.CriteriaResourceParamDTO;
import org.cdsframework.dto.OpenCdsConceptDTO;
import org.cdsframework.enumeration.ConceptSelectionType;
import org.cdsframework.enumeration.GenerationSource;
import org.cdsframework.interfaces.PredicatePartInterface;
import org.cdsframework.util.CriteriaUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class BasePredicatePartDTO extends BaseDTO implements PredicatePartInterface {

    private static final long serialVersionUID = 2041425905049918521L;

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String partId;
    private boolean overridable;
    @Size(max = 512)
    private String text;
    @ReferenceDTO
    @Column(name = "resource_id")
    private CriteriaResourceDTO criteriaResourceDTO;
    @ReferenceDTO
    @Column(name = "selected_param_id")
    private CriteriaResourceParamDTO criteriaResourceParamDTO;
    @Size(max = 1024)
    @Column(name = "default_id_root")
    private String defaultIdentifierRoot;
    @Size(max = 1024)
    @Column(name = "default_id_ext")
    private String defaultIdentifierExtension;
    private boolean dataInputBoolean;
    private Date dataInputDate1;
    private Date dataInputDate2;
    private BigDecimal dataInputNumeric;
    private boolean parameterEnd;
    private boolean functionEnd;

    /**
     * Get the value of functionEnd
     *
     * @return the value of functionEnd
     */
    @Override
    public boolean isFunctionEnd() {
        return functionEnd;
    }

    /**
     * Set the value of functionEnd
     *
     * @param functionEnd new value of functionEnd
     */
    @PropertyListener
    public void setFunctionEnd(boolean functionEnd) {
        this.functionEnd = functionEnd;
    }

    /**
     * Get the value of parameterEnd
     *
     * @return the value of parameterEnd
     */
    @Override
    public boolean isParameterEnd() {
        return parameterEnd;
    }

    /**
     * Set the value of parameterEnd
     *
     * @param parameterEnd new value of parameterEnd
     */
    @PropertyListener
    public void setParameterEnd(boolean parameterEnd) {
        this.parameterEnd = parameterEnd;
    }

    /**
     * Get the value of partId
     *
     * @return the value of partId
     */
    @Override
    public String getPartId() {
        return partId;
    }

    /**
     * Set the value of partId
     *
     * @param partId new value of partId
     */
    @PropertyListener
    public void setPartId(String partId) {
        this.partId = partId;
    }

    /**
     * Get the value of text
     *
     * @return the value of text
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * Set the value of text
     *
     * @param text new value of text
     */
    @PropertyListener
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Get the value of dataInputNumeric
     *
     * @return the value of dataInputNumeric
     */
    @Override
    public BigDecimal getDataInputNumeric() {
        return dataInputNumeric;
    }

    /**
     * Set the value of dataInputNumeric
     *
     * @param dataInputNumeric new value of dataInputNumeric
     */
    @PropertyListener
    public void setDataInputNumeric(BigDecimal dataInputNumeric) {
        this.dataInputNumeric = dataInputNumeric;
    }

    /**
     * Get the value of dataInputDate2
     *
     * @return the value of dataInputDate2
     */
    @Override
    public Date getDataInputDate2() {
        return dataInputDate2;
    }

    /**
     * Set the value of dataInputDate2
     *
     * @param dataInputDate2 new value of dataInputDate2
     */
    @PropertyListener
    public void setDataInputDate2(Date dataInputDate2) {
        this.dataInputDate2 = dataInputDate2;
    }

    /**
     * Get the value of dataInputDate1
     *
     * @return the value of dataInputDate1
     */
    @Override
    public Date getDataInputDate1() {
        return dataInputDate1;
    }

    /**
     * Set the value of dataInputDate1
     *
     * @param dataInputDate1 new value of dataInputDate1
     */
    @PropertyListener
    public void setDataInputDate1(Date dataInputDate1) {
        this.dataInputDate1 = dataInputDate1;
    }

    /**
     * Get the value of dataInputBoolean
     *
     * @return the value of dataInputBoolean
     */
    @Override
    public boolean isDataInputBoolean() {
        return dataInputBoolean;
    }

    /**
     * Set the value of dataInputBoolean
     *
     * @param dataInputBoolean new value of dataInputBoolean
     */
    @PropertyListener
    public void setDataInputBoolean(boolean dataInputBoolean) {
        this.dataInputBoolean = dataInputBoolean;
    }

    /**
     * Get the value of defaultIdentifierExtension
     *
     * @return the value of defaultIdentifierExtension
     */
    @Override
    public String getDefaultIdentifierExtension() {
        return defaultIdentifierExtension;
    }

    /**
     * Set the value of defaultIdentifierExtension
     *
     * @param defaultIdentifierExtension new value of defaultIdentifierExtension
     */
    @PropertyListener
    public void setDefaultIdentifierExtension(String defaultIdentifierExtension) {
        this.defaultIdentifierExtension = defaultIdentifierExtension;
    }

    /**
     * Get the value of defaultIdentifierRoot
     *
     * @return the value of defaultIdentifierRoot
     */
    @Override
    public String getDefaultIdentifierRoot() {
        return defaultIdentifierRoot;
    }

    /**
     * Set the value of defaultIdentifierRoot
     *
     * @param defaultIdentifierRoot new value of defaultIdentifierRoot
     */
    @PropertyListener
    public void setDefaultIdentifierRoot(String defaultIdentifierRoot) {
        this.defaultIdentifierRoot = defaultIdentifierRoot;
    }

    /**
     * Get the value of criteriaResourceParamDTO
     *
     * @return the value of criteriaResourceParamDTO
     */
    @Override
    public CriteriaResourceParamDTO getCriteriaResourceParamDTO() {
        return criteriaResourceParamDTO;
    }

    /**
     * Set the value of criteriaResourceParamDTO
     *
     * @param criteriaResourceParamDTO new value of criteriaResourceParamDTO
     */
    @PropertyListener
    public void setCriteriaResourceParamDTO(CriteriaResourceParamDTO criteriaResourceParamDTO) {
        this.criteriaResourceParamDTO = criteriaResourceParamDTO;
    }

    /**
     * Get the value of criteriaResourceDTO
     *
     * @return the value of criteriaResourceDTO
     */
    @Override
    public CriteriaResourceDTO getCriteriaResourceDTO() {
        return criteriaResourceDTO;
    }

    /**
     * Set the value of criteriaResourceDTO
     *
     * @param criteriaResourceDTO new value of criteriaResourceDTO
     */
    @PropertyListener
    public void setCriteriaResourceDTO(CriteriaResourceDTO criteriaResourceDTO) {
        this.criteriaResourceDTO = criteriaResourceDTO;
    }

    /**
     * Get the value of overridable
     *
     * @return the value of overridable
     */
    @Override
    public boolean isOverridable() {
        return overridable;
    }

    /**
     * Set the value of overridable
     *
     * @param overridable new value of overridable
     */
    @PropertyListener
    public void setOverridable(boolean overridable) {
        this.overridable = overridable;
    }

    @Override
    public String getLabel() {
        return CriteriaUtils.getPartLabel(this);
    }

    @Override
    public boolean isSourcedPart() {
        return BasePredicateSourcePartDTO.class.isAssignableFrom(getClass());
    }

    @Override
    public String getConcatenatedConceptSelectionType(String type) {
        String concatenatedList = "";
        String comma = ", ";
        int counter = 1;
        List<? extends BasePredicatePartConceptDTO> predicatePartConceptDTOs = getPredicatePartConceptDTOs();
        for (BasePredicatePartConceptDTO predicatePartConceptDTO : predicatePartConceptDTOs) {
            if (predicatePartConceptDTOs.size() == counter) {
                comma = "";
            }
            if (getConceptSelectionType() == ConceptSelectionType.Code) {
                CdsCodeDTO cdsCodeDTO = predicatePartConceptDTO.getCdsCodeDTO();
                if (cdsCodeDTO != null) {
                    if (type.equalsIgnoreCase("code")) {
                        concatenatedList += cdsCodeDTO.getCode() + comma;
                    } else if (type.equalsIgnoreCase("displayName")) {
                        concatenatedList += cdsCodeDTO.getDisplayName() + comma;
                    } else if (type.equalsIgnoreCase("combo")) {
                        concatenatedList += String.format("%s (%s)%s", cdsCodeDTO.getDisplayName(), cdsCodeDTO.getCode(), comma);
                    }
                }
            } else if (getConceptSelectionType() == ConceptSelectionType.Concept) {
                OpenCdsConceptDTO openCdsConceptDTO = predicatePartConceptDTO.getOpenCdsConceptDTO();
                if (openCdsConceptDTO != null) {
                    if (type.equalsIgnoreCase("code")) {
                        concatenatedList += openCdsConceptDTO.getCode() + comma;
                    } else if (type.equalsIgnoreCase("displayName")) {
                        concatenatedList += openCdsConceptDTO.getDisplayName() + comma;
                    } else if (type.equalsIgnoreCase("combo")) {
                        concatenatedList += String.format("%s (%s)%s", openCdsConceptDTO.getDisplayName(), openCdsConceptDTO.getCode(), comma);
                    }

                }
            }
            counter++;
        }
        return concatenatedList;
    }

}
