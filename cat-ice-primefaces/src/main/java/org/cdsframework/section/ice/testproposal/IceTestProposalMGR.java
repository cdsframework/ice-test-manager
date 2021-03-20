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
package org.cdsframework.section.ice.testproposal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsListItemDTO;
import org.cdsframework.dto.IceVaccineDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
import org.cdsframework.dto.IceTestProposalDTO;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceTestEventDTO;
import org.cdsframework.dto.IceTestRecommendationDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.enumeration.OffsetSource;
import org.cdsframework.enumeration.OffsetType;
import org.cdsframework.enumeration.ProposalType;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.interfaces.OffsetBasedEventType;
import org.cdsframework.section.ice.test.IceTestMGR;
import org.cdsframework.util.CdsDateUtils;
import org.cdsframework.util.IceUtilityMGR;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.enumeration.PrePost;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class IceTestProposalMGR extends BaseModule<IceTestProposalDTO> {

    private static final long serialVersionUID = 5317459884012286663L;
    @Inject
    private UtilityMGR utilityMGR;
    @Inject
    private IceTestMGR iceTestMGR;
    private boolean showSummary;
    private String recommendedDateAgeSet;
    private String earliestDateAgeSet;
    private String latestDateAgeSet;
    private String overdueDateAgeSet;

    @Override
    protected void initialize() {
        setSaveImmediately(true);
        this.setBaseHeader("Recommendation");
    }

    @Override
    public void registerTabComponents() {
        getTabService().registeredUIComponent(0, this);
    }

    @Override
    public void preSetParentDTO(IceTestProposalDTO parentDTO) {
        setShowSummary(false);
    }

    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        BaseDTO selectedDTO = getSelectedDTO();
        String targetFieldName = getTargetFieldName();
        IceTestProposalDTO parentDTO = getParentDTO();
        logger.info(METHODNAME, "parentDTO=", parentDTO);

        if (baseDTO instanceof IceTestEventDTO) {
            IceTestEventDTO iceTestEventDTO = (IceTestEventDTO) baseDTO;
            if ("earliestIceTestEventDTO".equals(targetFieldName)) {
                logger.info(METHODNAME, "IceTestEventDTO, earliestIceTestEventDTO");
                parentDTO.setEarliestOffsetId(iceTestEventDTO.getEventId());
            } else if ("recommendedIceTestEventDTO".equals(targetFieldName)) {
                logger.info(METHODNAME, "IceTestEventDTO, recommendedIceTestEventDTO");
                parentDTO.setRecommendedOffsetId(iceTestEventDTO.getEventId());
            } else if ("latestIceTestEventDTO".equals(targetFieldName)) {
                logger.info(METHODNAME, "IceTestEventDTO, latestIceTestEventDTO");
                parentDTO.setLatestOffsetId(iceTestEventDTO.getEventId());
            } else if ("overdueIceTestEventDTO".equals(targetFieldName)) {
                logger.info(METHODNAME, "IceTestEventDTO, overdueIceTestEventDTO");
                parentDTO.setOverdueOffsetId(iceTestEventDTO.getEventId());
            }
        } else if (baseDTO instanceof IceVaccineGroupDTO) {
            IceVaccineGroupDTO iceVaccineGroupDTO = (IceVaccineGroupDTO) baseDTO;
            if ("iceVaccineGroupDTO".equals(targetFieldName)) {
                parentDTO.setIceVaccineGroupDTO(iceVaccineGroupDTO);
            } else if ("groupFocus".equals(targetFieldName)) {
                parentDTO.setIceVaccineGroupDTO(iceVaccineGroupDTO);
            }
        } else if (baseDTO instanceof IceVaccineDTO) {
            IceVaccineDTO iceVaccineDTO = (IceVaccineDTO) baseDTO;
            if ("iceVaccineDTO".equals(targetFieldName)) {
                parentDTO.setIceVaccineDTO(iceVaccineDTO);
            }
        } else if (baseDTO instanceof CdsListItemDTO) {
            CdsListItemDTO cdsListItemDTO = (CdsListItemDTO) baseDTO;
            if ("recommendation".equals(targetFieldName)) {
                parentDTO.setRecommendationValue(cdsListItemDTO.getCdsCodeDTO());
            } else if ("reason".equals(targetFieldName)) {
                IceTestRecommendationDTO iceTestRecommendationDTO = (IceTestRecommendationDTO) selectedDTO;
                iceTestRecommendationDTO.setRecommendationInterpretation(cdsListItemDTO.getCdsCodeDTO());
            }
        }
        // Clear out selected DTO
        setSelectedDTO(null);
    }

    @Override
    public void prePostOperation(String queryClass, BaseDTO baseDTO, PrePost prePost, boolean status) {
        final String METHODNAME = "prePostOperation ";
        logger.info(METHODNAME, "queryClass=", queryClass, " baseDTO=", baseDTO, " prePost=", prePost, " status=", status);
        switch (prePost) {
            case PostInlineDelete:
            case PostInlineSave:
            case PostSave:
            case PostDelete:
                refresh();
                for (IceTestProposalDTO item : getDataTableMGR().getDtoList()) {
                    logger.info(METHODNAME, "item.getReasons()=", item.getReasons());
                }
                setDataTableSearchUpdateIds();
                break;
        }
    }

    public Date getIceTestProposalDate(IceTestProposalDTO item) {
        return getIceTestProposalDate(item, "DEFAULT");
    }

    public Date getIceTestProposalDate(IceTestProposalDTO iceTestProposalDTO, String offsetSourceString) {
        OffsetSource offsetSource = null;
        if (offsetSourceString != null) {
            offsetSource = OffsetSource.valueOf(offsetSourceString);
        }
        return getIceTestProposalDate(iceTestProposalDTO, offsetSource);
    }

    public Date getIceTestProposalDate(IceTestProposalDTO iceTestProposalDTO, OffsetSource offsetSource) {
        final String METHODNAME = "getIceTestProposalDate ";
        Date result = null;
        try {
            IceTestDTO iceTestDTO = iceTestMGR.getParentDTO();
            if (offsetSource != null) {
                if (iceTestProposalDTO != null) {
                    iceTestProposalDTO.setOffsetSource(offsetSource);
                    if (iceTestProposalDTO.getOffsetType() != null || !iceTestProposalDTO.isOffsetBased()) {
                        logger.debug(METHODNAME, "iceTestProposalDTO.getOffsetSource()=", iceTestProposalDTO.getOffsetSource());
                        if (iceTestDTO != null) {
                            if (iceTestProposalDTO.getOffsetId() != null && iceTestProposalDTO.getOffsetType() == OffsetType.Interval) {
                                IceTestEventDTO queryDTO = new IceTestEventDTO();
                                queryDTO.setTestId(iceTestDTO.getTestId());
                                PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
                                propertyBagDTO.setQueryClass("ByTestId");
                                List<IceTestEventDTO> peers = getGeneralMGR().findByQueryList(queryDTO, getSessionDTO(), propertyBagDTO);
                                iceTestDTO.setChildrenDTOs(IceTestEventDTO.ByTestId.class, (List) peers);
                            }
                            result = CdsDateUtils.getOffsetBasedEventDate(iceTestProposalDTO, iceTestDTO, new ArrayList<OffsetBasedEventType>());
                        } else {
                            logger.error(METHODNAME, "iceTestDTO was null!");
                        }
                    } else {
                        logger.error(METHODNAME, "iceTestProposalDTO.getOffsetType() was null!");
                    }
                } else {
                    logger.error(METHODNAME, "iceTestProposalDTO was null!");
                }
            } else {
                logger.error(METHODNAME, "offsetSourceString was null!");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            if (iceTestProposalDTO != null) {
                iceTestProposalDTO.setOffsetSource(null);
            }
        }
        return result;
    }

    public String getIceTestProposalAgeAtDate(IceTestProposalDTO iceTestProposalDTO, String offsetSourceString, boolean withBr) {
        final String METHODNAME = "getIceTestProposalAgeAtDate ";
        String result = null;
        try {
            IceTestDTO iceTestDTO = iceTestMGR.getParentDTO();
            if (offsetSourceString != null) {
                OffsetSource offsetSource = OffsetSource.valueOf(offsetSourceString);
                logger.debug(METHODNAME, "offsetSource=", offsetSource);
                if (iceTestProposalDTO != null) {
                    if (iceTestDTO != null) {
                        if (withBr) {
                            result = UtilityMGR.getDateDiffComboWithBR(iceTestMGR.getIceTestDob(iceTestDTO), getIceTestProposalDate(iceTestProposalDTO, offsetSource));
                        } else {
                            result = UtilityMGR.getDateDiffCombo(iceTestMGR.getIceTestDob(iceTestDTO), getIceTestProposalDate(iceTestProposalDTO, offsetSource));
                        }
                    } else {
                        logger.error(METHODNAME, "iceTestDTO was null!");
                    }
                } else {
                    logger.error(METHODNAME, "iceTestProposalDTO was null!");
                }
            } else {
                logger.error(METHODNAME, "offsetSourceString was null!");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return result;
    }

    public String getRecommendedDateAgeSet() {
        return recommendedDateAgeSet;
    }

    public void setRecommendedDateAgeSet(String recommendedDateAgeSet) {
        this.recommendedDateAgeSet = recommendedDateAgeSet;
    }

    /**
     * Get the value of overdueDateAgeSet
     *
     * @return the value of overdueDateAgeSet
     */
    public String getOverdueDateAgeSet() {
        return overdueDateAgeSet;
    }

    /**
     * Set the value of overdueDateAgeSet
     *
     * @param overdueDateAgeSet new value of overdueDateAgeSet
     */
    public void setOverdueDateAgeSet(String overdueDateAgeSet) {
        this.overdueDateAgeSet = overdueDateAgeSet;
    }

    /**
     * Get the value of latestDateAgeSet
     *
     * @return the value of latestDateAgeSet
     */
    public String getLatestDateAgeSet() {
        return latestDateAgeSet;
    }

    /**
     * Set the value of latestDateAgeSet
     *
     * @param latestDateAgeSet new value of latestDateAgeSet
     */
    public void setLatestDateAgeSet(String latestDateAgeSet) {
        this.latestDateAgeSet = latestDateAgeSet;
    }

    /**
     * Get the value of earliestDateAgeSet
     *
     * @return the value of earliestDateAgeSet
     */
    public String getEarliestDateAgeSet() {
        return earliestDateAgeSet;
    }

    /**
     * Set the value of earliestDateAgeSet
     *
     * @param earliestDateAgeSet new value of earliestDateAgeSet
     */
    public void setEarliestDateAgeSet(String earliestDateAgeSet) {
        this.earliestDateAgeSet = earliestDateAgeSet;
    }

    public void calcDateAtAge(String offsetSourceString) {
        final String METHODNAME = "calcDateAtAge ";
        String dateAgeSet;
        OffsetSource offsetSource = null;
        try {
            IceTestDTO iceTestDTO = (IceTestDTO) getParentMGR().getParentDTO();
            IceTestProposalDTO parentDTO = getParentDTO();
            if (offsetSourceString != null) {
                offsetSource = OffsetSource.valueOf(offsetSourceString);
                logger.debug("offsetSource=", offsetSource);
                switch (offsetSource) {
                    case EARLIEST:
                        logger.info(" setting dateAgeSet earliestDateAgeSet");
                        dateAgeSet = earliestDateAgeSet;
                        break;
                    case LATEST:
                        logger.info(" setting dateAgeSet latestDateAgeSet");
                        dateAgeSet = latestDateAgeSet;
                        break;
                    case OVERDUE:
                        logger.info(" setting dateAgeSet overdueDateAgeSet");
                        dateAgeSet = overdueDateAgeSet;
                        break;
                    case RECOMMENDED:
                    case DEFAULT:
                    default:
                        logger.info(" setting dateAgeSet recommendedDateAgeSet");
                        dateAgeSet = recommendedDateAgeSet;
                        break;
                }
                logger.debug("dateAgeSet=", dateAgeSet);
                if (parentDTO != null) {
                    if (iceTestDTO != null) {
                        Date iceTestDob = CdsDateUtils.getOffsetBasedTypeDob(iceTestDTO);
                        if (iceTestDob != null) {
                            Date dateSet = utilityMGR.incrementDateFromString(iceTestDob, dateAgeSet, false);
                            switch (offsetSource) {
                                case EARLIEST:
                                    parentDTO.setEarliestDate(dateSet);
                                    break;
                                case LATEST:
                                    parentDTO.setLatestDate(dateSet);
                                    break;
                                case OVERDUE:
                                    parentDTO.setOverdueDate(dateSet);
                                    break;
                                case RECOMMENDED:
                                case DEFAULT:
                                default:
                                    parentDTO.setRecommendedDate(dateSet);
                                    break;
                            }
                        } else {
                            logger.error(METHODNAME, "The test DOB date must be set.");
                        }
                    } else {
                        logger.error(METHODNAME, "The iceTestDTO object was null.");
                    }
                } else {
                    logger.error(METHODNAME, "The parentDTO object was null.");
                }
            } else {
                logger.error(METHODNAME, "The offsetSourceString object was null.");
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } finally {
            if (offsetSource != null) {
                switch (offsetSource) {
                    case EARLIEST:
                        earliestDateAgeSet = null;
                        break;
                    case LATEST:
                        latestDateAgeSet = null;
                        break;
                    case OVERDUE:
                        overdueDateAgeSet = null;
                        break;
                    case RECOMMENDED:
                    case DEFAULT:
                    default:
                        recommendedDateAgeSet = null;
                        break;
                }
            }
        }
    }

    public String getSubstanceRecommendationTableHTML(IceTestProposalDTO iceTestProposalDTO) {
        final String METHODNAME = "getSubstanceRecommendationTableHTML ";
        String result = "N/A";
        try {
            if (iceTestProposalDTO != null) {
                IceVaccineDTO iceVaccineDTO = iceTestProposalDTO.getIceVaccineDTO();
                IceVaccineGroupDTO iceVaccineGroupDTO = iceTestProposalDTO.getIceVaccineGroupDTO();
                if (iceVaccineDTO != null
                        && iceVaccineDTO.getVaccine() != null
                        && iceVaccineDTO.getVaccine().getCode() != null) {
                    result = IceUtilityMGR.getCodedElementLabel(iceVaccineDTO);
                } else if (iceVaccineGroupDTO != null) {
                    result = IceUtilityMGR.getCodedElementLabel(iceVaccineGroupDTO);
                }
            }
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
        return result;
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
