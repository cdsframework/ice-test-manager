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
package org.cdsframework.section.cds.datamodel;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.DataModelClassDTO;
import org.cdsframework.dto.DataModelClassNodeDTO;
import org.cdsframework.lookup.DataModelClassDTOList;
import org.cdsframework.lookup.DataModelClassNodeDTOList;
import org.cdsframework.util.enumeration.PrePost;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class DataModelClassMGR extends BaseModule<DataModelClassDTO> {

    private static final long serialVersionUID = 3032093390275637144L;

    @Inject
    private DataModelClassDTOList dataModelClassDTOList;
    @Inject
    private DataModelClassNodeMGR dataModelClassNodeMGR;
    @Inject
    private DataModelClassNodeDTOList dataModelClassNodeDTOList;
    @Inject
    private DataModelMGR dataModelMGR;

    @Override
    protected void initialize() {
        //setSaveOnBaseParentMGR(true);
        //setSaveDeleteOnBaseParentMgr(true);
//        addOnRowSelectChildClassDTO(DataModelClassNodeDTO.class);
        registerChild(DataModelClassNodeDTO.ByClassId.class, dataModelClassNodeMGR);
        setAssociatedList(dataModelClassDTOList);
        setSaveImmediately(true);
    }

    @Override
    public void registerTabComponents() {
        final String METHODNAME = "registerTabLazyLoadComponents ";
        logger.info(METHODNAME);
        // Register the Components
        getTabService().registeredUIComponent(1, dataModelClassNodeMGR);
    }

    /**
     * Have to do this manually because this object is recursive...
     *
     * @param parentDTO
     */
    @Override
    public void preSetParentDTO(DataModelClassDTO parentDTO) {
        if (parentDTO != null) {
            if (parentDTO.getDataModelClassNodeDTOs().isEmpty()) {
                dataModelClassNodeDTOList.retrieveTieredClassNodes(parentDTO);
//                loadSuperClasses(parentDTO.getDataModelSuperClassDTO());
            }
        }
    }
//
//    private void loadSuperClasses(DataModelClassDTO superClass) {
//        if (superClass != null) {
//            dataModelClassNodeDTOList.retrieveTieredClassNodes(superClass);
//            loadSuperClasses(superClass.getDataModelSuperClassDTO());
//        }
//    }

    @Override
    public void prePostOperation(String queryClass, BaseDTO baseDTO, PrePost prePost, boolean status) {
        String METHODNAME = "prePostOperation ";
        super.prePostOperation(queryClass, baseDTO, prePost, status);
        switch (prePost) {
            case PostDelete:
            case PostInlineDelete:
            case PostSave:
            case PostTabSave:
            case PostInlineSave:
                dataModelMGR.getRetrievedObjects().remove(dataModelMGR.getParentDTO());
                dataModelMGR.updateTreeTableMain();
                break;
        }
    }
}
