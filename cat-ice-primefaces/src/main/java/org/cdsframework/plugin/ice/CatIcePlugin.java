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
package org.cdsframework.plugin.ice;

import javax.enterprise.context.ApplicationScoped;
import org.cdsframework.base.BaseModule;
import org.cdsframework.base.CatBasePlugin;
import org.cdsframework.base.MessageArgCallback;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.IceDiseaseDTO;
import org.cdsframework.dto.IceDiseaseVersionConceptRelDTO;
import org.cdsframework.dto.IceSeasonDTO;
import org.cdsframework.dto.IceSeriesDTO;
import org.cdsframework.dto.IceSeriesDoseDTO;
import org.cdsframework.dto.IceSeriesDoseVaccineRelDTO;
import org.cdsframework.dto.IceSeriesSeasonRelDTO;
import org.cdsframework.dto.IceSeriesVersionRelDTO;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceTestEvaluationDTO;
import org.cdsframework.dto.IceTestEventComponentDTO;
import org.cdsframework.dto.IceTestEventDTO;
import org.cdsframework.dto.IceTestGroupDTO;
import org.cdsframework.dto.IceTestImmunityDTO;
import org.cdsframework.dto.IceTestProposalDTO;
import org.cdsframework.dto.IceTestRecommendationDTO;
import org.cdsframework.dto.IceTestSuiteDTO;
import org.cdsframework.dto.IceTestVaccineGroupRelDTO;
import org.cdsframework.dto.IceVaccineComponentDTO;
import org.cdsframework.dto.IceVaccineDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
import org.cdsframework.util.UtilityMGR;
import org.cdsframework.util.UuidUtils;
import org.cdsframework.util.enumeration.SourceMethod;

/**
 *
 * @author HLN Consulting, LLC
 */
@ApplicationScoped
public class CatIcePlugin extends CatBasePlugin {

    @Override
    protected void initialize() {

        registerMessageArgCallback(IceSeriesDoseVaccineRelDTO.class, new MessageArgCallback<IceSeriesDoseVaccineRelDTO>() {
            @Override
            public Object[] getMessageArgs(IceSeriesDoseVaccineRelDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getIceVaccineDTO() != null ? dto.getIceVaccineDTO().getVaccine().getLabel() : null};
                }
            }
        }
        );

        registerMessageArgCallback(IceSeriesVersionRelDTO.class, new MessageArgCallback<IceSeriesVersionRelDTO>() {
            @Override
            public Object[] getMessageArgs(IceSeriesVersionRelDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getCdsVersionDTO() != null ? dto.getCdsVersionDTO().getLabel() : null};
                }
            }
        }
        );

        registerMessageArgCallback(IceSeriesSeasonRelDTO.class, new MessageArgCallback<IceSeriesSeasonRelDTO>() {
            @Override
            public Object[] getMessageArgs(IceSeriesSeasonRelDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getIceSeasonDTO() != null ? dto.getIceSeasonDTO().getName() : null};
                }
            }
        }
        );

        registerMessageArgCallback(IceTestRecommendationDTO.class, new MessageArgCallback<IceTestRecommendationDTO>() {
            @Override
            public Object[] getMessageArgs(IceTestRecommendationDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getRecommendationInterpretation() != null ? dto.getRecommendationInterpretation().getLabel() : null};
                }
            }
        }
        );

        registerMessageArgCallback(IceTestVaccineGroupRelDTO.class, new MessageArgCallback<IceTestVaccineGroupRelDTO>() {
            @Override
            public Object[] getMessageArgs(IceTestVaccineGroupRelDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getIceVaccineGroupDTO() != null ? dto.getIceVaccineGroupDTO().getVaccineGroup().getLabel() : null};
                }
            }
        }
        );

        registerMessageArgCallback(IceTestEventComponentDTO.class, new MessageArgCallback<IceTestEventComponentDTO>() {
            @Override
            public Object[] getMessageArgs(IceTestEventComponentDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getIceVaccineComponentDTO() != null ? dto.getIceVaccineComponentDTO().getVaccineComponent().getLabel() : null};
                }
            }
        }
        );

        registerMessageArgCallback(IceTestEvaluationDTO.class, new MessageArgCallback<IceTestEvaluationDTO>() {
            @Override
            public Object[] getMessageArgs(IceTestEvaluationDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getEvaluationInterpretation() != null ? dto.getEvaluationInterpretation().getLabel() : null};
                }
            }
        }
        );

        registerMessageArgCallback(IceDiseaseDTO.class, new MessageArgCallback<IceDiseaseDTO>() {
            @Override
            public Object[] getMessageArgs(IceDiseaseDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getDiseaseCdsCodeDTO() != null ? dto.getDiseaseCdsCodeDTO().getDisplayName() : null, module.getBaseHeader().toLowerCase()};
                }
            }
        }
        );

        registerMessageArgCallback(IceDiseaseVersionConceptRelDTO.class, new MessageArgCallback<IceDiseaseVersionConceptRelDTO>() {
            @Override
            public Object[] getMessageArgs(IceDiseaseVersionConceptRelDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{(dto.getCdsVersionDTO() != null ? "version concept mapping for " + dto.getCdsVersionDTO().getName() : "")
                            + (dto.getPrimaryOpenCdsConceptDTO() != null ? " and " + dto.getPrimaryOpenCdsConceptDTO().getCode() : ""),
                            module.getBaseHeader().toLowerCase()};
                }
            }
        }
        );

        registerMessageArgCallback(IceSeasonDTO.class, new MessageArgCallback<IceSeasonDTO>() {
            @Override
            public Object[] getMessageArgs(IceSeasonDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        }
        );

        registerMessageArgCallback(IceSeriesDTO.class, new MessageArgCallback<IceSeriesDTO>() {
            @Override
            public Object[] getMessageArgs(IceSeriesDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        }
        );

        registerMessageArgCallback(IceSeriesDoseDTO.class, new MessageArgCallback<IceSeriesDoseDTO>() {
            @Override
            public Object[] getMessageArgs(IceSeriesDoseDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getDoseNumber() != null ? dto.getDoseNumber().toString() : "", module.getBaseHeader().toLowerCase()};
                }
            }
        }
        );

        registerMessageArgCallback(IceTestSuiteDTO.class, new MessageArgCallback<IceTestSuiteDTO>() {
            @Override
            public Object[] getMessageArgs(IceTestSuiteDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    case deleteMain:
                        return new Object[]{String.format("test %s: %s", UuidUtils.formatUuid(dto.getSuiteId()), dto.getName())};
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        }
        );

        registerMessageArgCallback(IceTestDTO.class, new MessageArgCallback<IceTestDTO>() {
            @Override
            public Object[] getMessageArgs(IceTestDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    case deleteMain:
                        return new Object[]{String.format("test %s: %s", UuidUtils.formatUuid(dto.getTestId()), dto.getName())};
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        }
        );

        registerMessageArgCallback(IceTestEventDTO.class, new MessageArgCallback<IceTestEventDTO>() {
            @Override
            public Object[] getMessageArgs(IceTestEventDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    case deleteMain:
                        IceVaccineDTO iceVaccineDTO = dto.getIceVaccineDTO();
                        if (iceVaccineDTO != null && iceVaccineDTO.getVaccine() != null) {
                            return new Object[]{String.format("the %s (CVX %s) immunization administered on %s", iceVaccineDTO.getVaccineName(), iceVaccineDTO.getVaccine().getCode(), UtilityMGR.getFormattedDate(dto.getAdministrationTime()))};
                        } else {
                            return new Object[]{"the immunization"};
                        }
                    default:
                        return new Object[]{String.format("%s (CVX %s)", dto.getIceVaccineDTO().getVaccineName(), (dto.getIceVaccineDTO() != null && dto.getIceVaccineDTO().getVaccine() != null) ? dto.getIceVaccineDTO().getVaccine().getCode() : null), module.getBaseHeader().toLowerCase()};
                }
            }
        }
        );

        registerMessageArgCallback(IceTestGroupDTO.class, new MessageArgCallback<IceTestGroupDTO>() {
            @Override
            public Object[] getMessageArgs(IceTestGroupDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        }
        );

        registerMessageArgCallback(IceTestImmunityDTO.class, new MessageArgCallback<IceTestImmunityDTO>() {
            @Override
            public Object[] getMessageArgs(IceTestImmunityDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    case deleteMain:
                        CdsCodeDTO immunityFocus = dto.getImmunityFocus();
                        if (immunityFocus != null && immunityFocus.getDisplayName() != null) {
                            return new Object[]{String.format("the proof of immunity/documented disease entry for %s", immunityFocus.getDisplayName())};
                        } else {
                            return new Object[]{"the proof of immunity/documented disease entry"};
                        }
                    default:
                        if (dto.getImmunityFocus() != null && dto.getImmunityValue() != null) {
                            return new Object[]{dto.getImmunityFocus().getDisplayName(), dto.getImmunityValue().getDisplayName(), module.getBaseHeader().toLowerCase()};
                        } else {
                            return new Object[]{};
                        }
                }
            }
        }
        );

        registerMessageArgCallback(IceTestProposalDTO.class, new MessageArgCallback<IceTestProposalDTO>() {
            @Override
            public Object[] getMessageArgs(IceTestProposalDTO dto, BaseModule module, SourceMethod sourceMethod) {
                IceVaccineDTO iceVaccineDTO = dto.getIceVaccineDTO();
                IceVaccineGroupDTO iceVaccineGroupDTO = dto.getIceVaccineGroupDTO();
                switch (sourceMethod) {
                    case deleteMain:
                        if (iceVaccineDTO != null && iceVaccineDTO.getVaccine() != null) {
                            return new Object[]{String.format("the %s (CVX %s) recommendation", iceVaccineDTO.getVaccineName(), iceVaccineDTO.getVaccine().getCode())};
                        } else if (iceVaccineGroupDTO != null && iceVaccineGroupDTO.getVaccineGroupName() != null) {
                            return new Object[]{String.format("the %s recommendation", iceVaccineGroupDTO.getVaccineGroupName())};
                        } else {
                            return new Object[]{"the recommendation"};
                        }
                    default:
                        if (iceVaccineDTO != null || iceVaccineGroupDTO != null) {
                            String vacCode = null;
                            String vaccineName = null;
                            String groupName = null;
                            if (iceVaccineDTO != null && iceVaccineDTO.getVaccine() != null) {
                                vacCode = iceVaccineDTO.getVaccine().getCode();
                                vaccineName = iceVaccineDTO.getVaccineName();
                            }
                            if (iceVaccineGroupDTO != null) {
                                groupName = iceVaccineGroupDTO.getVaccineGroupName();
                            }
                            return new Object[]{
                                vacCode != null ? String.format("%s (CVX %s)", vaccineName, vacCode) : groupName, module.getBaseHeader().toLowerCase()
                            };
                        } else {
                            return new Object[]{};
                        }
                }
            }
        }
        );

        registerMessageArgCallback(IceVaccineDTO.class, new MessageArgCallback<IceVaccineDTO>() {
            @Override
            public Object[] getMessageArgs(IceVaccineDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getVaccineName(), module.getBaseHeader().toLowerCase()};
                }
            }
        }
        );

        registerMessageArgCallback(IceVaccineComponentDTO.class, new MessageArgCallback<IceVaccineComponentDTO>() {
            @Override
            public Object[] getMessageArgs(IceVaccineComponentDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getVaccineComponent().getDisplayName(), module.getBaseHeader().toLowerCase()};
                }
            }
        }
        );

        registerMessageArgCallback(IceVaccineGroupDTO.class, new MessageArgCallback<IceVaccineGroupDTO>() {
            @Override
            public Object[] getMessageArgs(IceVaccineGroupDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getVaccineGroupName(), module.getBaseHeader().toLowerCase()};
                }
            }
        }
        );

    }
}
