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
package org.cdsframework.plugin.cds;

import javax.enterprise.context.ApplicationScoped;
import org.cdsframework.base.MessageArgCallback;
import org.cdsframework.base.BaseModule;
import org.cdsframework.base.CatBasePlugin;
import org.cdsframework.dto.CdsBusinessScopeDTO;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsCodeSystemDTO;
import org.cdsframework.dto.CdsListDTO;
import org.cdsframework.dto.CdsListItemDTO;
import org.cdsframework.dto.CdsVersionConceptDeterminationMethodRelDTO;
import org.cdsframework.dto.CdsVersionDTO;
import org.cdsframework.dto.ConceptDeterminationMethodDTO;
import org.cdsframework.dto.CriteriaDTO;
import org.cdsframework.dto.CriteriaDataTemplateRelDTO;
import org.cdsframework.dto.CriteriaDataTemplateRelNodeDTO;
import org.cdsframework.dto.CriteriaPredicateDTO;
import org.cdsframework.dto.CriteriaPredicatePartDTO;
import org.cdsframework.dto.CriteriaPredicatePartRelDTO;
import org.cdsframework.dto.CriteriaResourceDTO;
import org.cdsframework.dto.CriteriaResourceParamDTO;
import org.cdsframework.dto.CriteriaVersionRelDTO;
import org.cdsframework.dto.DataModelClassDTO;
import org.cdsframework.dto.DataModelClassNodeDTO;
import org.cdsframework.dto.DataModelDTO;
import org.cdsframework.dto.DataTemplateDTO;
import org.cdsframework.dto.DataTemplateNodeRelDTO;
import org.cdsframework.dto.OpenCdsConceptDTO;
import org.cdsframework.dto.OpenCdsConceptRelDTO;
import org.cdsframework.dto.ValueSetDTO;
import org.cdsframework.util.enumeration.SourceMethod;

/**
 *
 * @author HLN Consulting, LLC
 */
@ApplicationScoped
public class CatCdsPlugin extends CatBasePlugin {

    @Override
    protected void initialize() {

        registerMessageArgCallback(CdsListDTO.class, new MessageArgCallback<CdsListDTO>() {
            @Override
            public Object[] getMessageArgs(CdsListDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CdsBusinessScopeDTO.class, new MessageArgCallback<CdsBusinessScopeDTO>() {
            @Override
            public Object[] getMessageArgs(CdsBusinessScopeDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getBusinessId(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(ConceptDeterminationMethodDTO.class, new MessageArgCallback<ConceptDeterminationMethodDTO>() {
            @Override
            public Object[] getMessageArgs(ConceptDeterminationMethodDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getCode(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CdsListItemDTO.class, new MessageArgCallback<CdsListItemDTO>() {
            @Override
            public Object[] getMessageArgs(CdsListItemDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CdsCodeDTO.class, new MessageArgCallback<CdsCodeDTO>() {
            @Override
            public Object[] getMessageArgs(CdsCodeDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getDisplayName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CdsCodeSystemDTO.class, new MessageArgCallback<CdsCodeSystemDTO>() {
            @Override
            public Object[] getMessageArgs(CdsCodeSystemDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(OpenCdsConceptDTO.class, new MessageArgCallback<OpenCdsConceptDTO>() {
            @Override
            public Object[] getMessageArgs(OpenCdsConceptDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getDisplayName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(OpenCdsConceptRelDTO.class, new MessageArgCallback<OpenCdsConceptRelDTO>() {
            @Override
            public Object[] getMessageArgs(OpenCdsConceptRelDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        switch (dto.getMappingType()) {
                            case CODE:
                                return new Object[]{dto.getCdsCodeDTO() != null ? dto.getCdsCodeDTO().getCode() : null, "code concept mapping"};
                            case CODE_SYSTEM:
                                return new Object[]{dto.getCdsCodeSystemDTO() != null ? dto.getCdsCodeSystemDTO().getLabel() : null, "code system concept mapping"};
                            case VALUE_SET:
                                return new Object[]{dto.getValueSetDTO() != null ? dto.getValueSetDTO().getLabel() : null, "value set concept mapping"};
                            default:
                                return new Object[]{};
                        }
                }
            }
        });

        registerMessageArgCallback(CriteriaDataTemplateRelDTO.class, new MessageArgCallback<CriteriaDataTemplateRelDTO>() {
            @Override
            public Object[] getMessageArgs(CriteriaDataTemplateRelDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getLabel(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CdsVersionConceptDeterminationMethodRelDTO.class, new MessageArgCallback<CdsVersionConceptDeterminationMethodRelDTO>() {
            @Override
            public Object[] getMessageArgs(CdsVersionConceptDeterminationMethodRelDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getConceptDeterminationMethodDTO() != null ? dto.getConceptDeterminationMethodDTO().getLabel(): null, "concept determination method mapping"};
                }
            }
        });

        registerMessageArgCallback(CriteriaDataTemplateRelNodeDTO.class, new MessageArgCallback<CriteriaDataTemplateRelNodeDTO>() {
            @Override
            public Object[] getMessageArgs(CriteriaDataTemplateRelNodeDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getLabel(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CriteriaDTO.class, new MessageArgCallback<CriteriaDTO>() {
            @Override
            public Object[] getMessageArgs(CriteriaDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CriteriaPredicateDTO.class, new MessageArgCallback<CriteriaPredicateDTO>() {
            @Override
            public Object[] getMessageArgs(CriteriaPredicateDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getDescription(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CriteriaPredicatePartDTO.class, new MessageArgCallback<CriteriaPredicatePartDTO>() {
            @Override
            public Object[] getMessageArgs(CriteriaPredicatePartDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getLabel(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CriteriaPredicatePartRelDTO.class, new MessageArgCallback<CriteriaPredicatePartRelDTO>() {
            @Override
            public Object[] getMessageArgs(CriteriaPredicatePartRelDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getRelId(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CriteriaVersionRelDTO.class, new MessageArgCallback<CriteriaVersionRelDTO>() {
            @Override
            public Object[] getMessageArgs(CriteriaVersionRelDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getCdsVersionDTO() != null ? dto.getCdsVersionDTO().getLabel() : null, module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(DataModelClassDTO.class, new MessageArgCallback<DataModelClassDTO>() {
            @Override
            public Object[] getMessageArgs(DataModelClassDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(DataModelClassNodeDTO.class, new MessageArgCallback<DataModelClassNodeDTO>() {
            @Override
            public Object[] getMessageArgs(DataModelClassNodeDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(DataModelClassNodeDTO.class, new MessageArgCallback<DataModelClassNodeDTO>() {
            @Override
            public Object[] getMessageArgs(DataModelClassNodeDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(DataModelDTO.class, new MessageArgCallback<DataModelDTO>() {
            @Override
            public Object[] getMessageArgs(DataModelDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(DataModelClassNodeDTO.class, new MessageArgCallback<DataModelClassNodeDTO>() {
            @Override
            public Object[] getMessageArgs(DataModelClassNodeDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(DataTemplateDTO.class, new MessageArgCallback<DataTemplateDTO>() {
            @Override
            public Object[] getMessageArgs(DataTemplateDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(DataTemplateNodeRelDTO.class, new MessageArgCallback<DataTemplateNodeRelDTO>() {
            @Override
            public Object[] getMessageArgs(DataTemplateNodeRelDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getDataModelClassNodeDTO() != null ? dto.getDataModelClassNodeDTO().getName() : null, module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CriteriaResourceDTO.class, new MessageArgCallback<CriteriaResourceDTO>() {
            @Override
            public Object[] getMessageArgs(CriteriaResourceDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CriteriaResourceParamDTO.class, new MessageArgCallback<CriteriaResourceParamDTO>() {
            @Override
            public Object[] getMessageArgs(CriteriaResourceParamDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(ValueSetDTO.class, new MessageArgCallback<ValueSetDTO>() {
            @Override
            public Object[] getMessageArgs(ValueSetDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CdsVersionDTO.class, new MessageArgCallback<CdsVersionDTO>() {
            @Override
            public Object[] getMessageArgs(CdsVersionDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CdsListDTO.class, new MessageArgCallback<CdsListDTO>() {
            @Override
            public Object[] getMessageArgs(CdsListDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CdsListDTO.class, new MessageArgCallback<CdsListDTO>() {
            @Override
            public Object[] getMessageArgs(CdsListDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CdsListDTO.class, new MessageArgCallback<CdsListDTO>() {
            @Override
            public Object[] getMessageArgs(CdsListDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

        registerMessageArgCallback(CdsListDTO.class, new MessageArgCallback<CdsListDTO>() {
            @Override
            public Object[] getMessageArgs(CdsListDTO dto, BaseModule module, SourceMethod sourceMethod) {
                switch (sourceMethod) {
                    default:
                        return new Object[]{dto.getName(), module.getBaseHeader().toLowerCase()};
                }
            }
        });

    }

}
