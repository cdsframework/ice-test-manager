/**
 * CAT Core support plugin project.
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
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see https://www.hln.com/services/open-source/ or send
 * correspondence to ice@hln.com.
 */
package org.cdsframework.dialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;
import org.cdsframework.util.UtilityMGR;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class DialogService implements Serializable {

    private final static LogUtils logger = LogUtils.getLogger(DialogService.class);
    private static final long serialVersionUID = 4913109180867138511L;

    private enum buttonType {
        yesNo, Ok
    };
    private BaseDTO dto = null;
    private BaseModule module;
    private final String deleteTitle = "Delete ";
    private final String deletePrefix = "Are you sure you want to delete ";
    private final String discardTitle = "Discard Changes";
    private final String discardChanges = "Are you sure you want to discard your changes?";
    private final String applyTitle = "Apply Changes";
    private final String applyChanges = "Please apply your changes before continuing.";

    private String action = null;
    private String recordPosition = null;

    public void showDiscardDialog(BaseModule module, String action, String recordPosition) throws Exception {
        final String METHODNAME = "showDiscardDialog ";

        // Store parameters
        this.action = action;
        this.recordPosition = recordPosition;
        boolean formChanged = isFormChanged();
        if (formChanged) {
            showDialog(dto, module, discardTitle, discardChanges, buttonType.yesNo.name());
        } else {
            if (action.equalsIgnoreCase("onGotoRecord")) {
                module.onGotoRecordMain(UtilityMGR.getEmulatedActionEvent(this.recordPosition));
            } else if (action.equalsIgnoreCase("onNew")) {
                module.addMain(UtilityMGR.getEmulatedActionEvent("new"));
            }

            // Used for javascript call back
            UtilityMGR.setCallbackParam("success", true);
        }
    }

    public void showApplyDialog(BaseModule module) {
        showDialog(null, module, applyTitle, applyChanges, buttonType.Ok.name());
    }

    public void showDeleteDialog(BaseDTO dto, BaseModule module) {
        final String METHODNAME = "showDeleteDialog ";
        module.setParentDTO(dto);
        String title = deleteTitle + module.getBaseHeader();
        String deleteMessage = deletePrefix + module.getDeleteMessage(dto) + "?";
        module.setParentDTO(null);
        showDialog(dto, module, title, deleteMessage, buttonType.yesNo.name());
    }

    public void showDeleteDialog(BaseDTO dto, String title, String message, BaseModule module) {
        final String METHODNAME = "showDeleteDialog ";
        String dtoName = dto.getClass().getSimpleName();
        if (StringUtils.isEmpty(title)) {
            title = StringUtils.unCamelize(dtoName.substring(0, dtoName.toLowerCase().indexOf("dto")));
        }
        title = deleteTitle + title;
        if (StringUtils.isEmpty(message)) {
            message = module.getDeleteMessage(dto);
        }
        message = deletePrefix + message + "?";

        showDialog(dto, module, title, message, buttonType.yesNo.name());
    }

    public void showDialog(BaseDTO dto, BaseModule module, String title, String message, String buttonType) {
        final String METHODNAME = "showDialog ";
        logger.info(METHODNAME, "dto=", dto);
        this.dto = dto;
        this.module = module;

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("closable", false);
        options.put("resizable", false);
        options.put("includeViewParams", true);
        options.put("contentWidth", 400);
        options.put("contentHeight", 100);

        Map<String, List<String>> parameterMap = new HashMap();
        List<String> parm1 = new ArrayList();
        parm1.add(title);
        parameterMap.put("title", parm1);

        List<String> parm2 = new ArrayList();
        parm2.add(message);
        parameterMap.put("message", parm2);

        List<String> parm3 = new ArrayList();
        parm3.add(buttonType);
        parameterMap.put("buttonType", parm3);

        RequestContext.getCurrentInstance().openDialog("/module/dialogForm", options, parameterMap);
    }

    public void onDeleteDialogReturn(SelectEvent event) throws Exception {
        final String METHODNAME = "onDeleteDialogReturn ";
        Object parm = event.getObject();
        logger.info(METHODNAME, "parm=", parm);
        logger.info(METHODNAME, "dto=", dto);
        if (((String) parm).equalsIgnoreCase("yes")) {
            module.deleteMain(true, dto);
        }
    }

    public void onOkDialogReturn(SelectEvent event) throws Exception {
        final String METHODNAME = "onOkDialogReturn ";
        Object parm = event.getObject();
        logger.info(METHODNAME, "parm=", parm);
    }

    public void onDiscardDialogReturn(SelectEvent event) throws Exception {
        final String METHODNAME = "onDiscardDialogReturn ";
        Object parm = event.getObject();
        logger.info(METHODNAME, "parm=", parm);
        if (((String) parm).equalsIgnoreCase("yes")) {
            if (this.action.equalsIgnoreCase("onGotoRecord")) {
                module.onGotoRecordMain(UtilityMGR.getEmulatedActionEvent(recordPosition));
            } else if (action.equalsIgnoreCase("onNew")) {
                module.addMain(UtilityMGR.getEmulatedActionEvent("new"));
                UtilityMGR.setCallbackParam("success", true);
            } else if (action.equalsIgnoreCase("onCancel")) {
                UtilityMGR.setCallbackParam("success", true);
            }
        }
        // Clear out parameters
        this.action = null;
        this.recordPosition = null;
    }

    private boolean isFormChanged() {
        final String METHODNAME = "isFormChanged ";
        boolean formChanged = false;
        Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (map != null) {
            Object oFormChanged = map.get("formChanged");
            formChanged = (oFormChanged != null && ((String) oFormChanged).equalsIgnoreCase("true"));
        }
        logger.info(METHODNAME, "formChanged=", formChanged);
        return formChanged;
    }

    public void showSearchDialog(BaseDTO dto, BaseModule module, List queryParms, String fieldName, String title, String htmlPath, Integer height, Integer width) {
        final String METHODNAME = "showSearchDialog ";
        logger.info(METHODNAME);
        logger.info(METHODNAME, "dto=", dto, " module=", module, " queryParms=", queryParms);

        logger.info(METHODNAME, "calling setSelectedDTO");
        module.setSelectedDTO(dto);

        logger.info(METHODNAME, "calling setTargetFieldName");
        module.setTargetFieldName(fieldName);

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("closable", true);
        options.put("resizable", false);
        options.put("includeViewParams", true);
        options.put("contentHeight", height);
        options.put("contentWidth", width);

        Map<String, List<String>> parameterMap = new HashMap();
        List<String> titleParm = new ArrayList();
        titleParm.add(title);
        parameterMap.put("title", titleParm);

        if (queryParms != null) {
            int counter = 1;
            for (Object objectParm : queryParms) {
                logger.info(METHODNAME, "objectParm=", objectParm);
                List<String> queryParm = new ArrayList();
                if (objectParm != null) {
                    queryParm.add(objectParm.toString());
                } else {
                    logger.warn(METHODNAME, "objectParm was null for ", "query_parm_", counter);
                }
                parameterMap.put("query_parm_" + counter, queryParm);
                counter++;
            }
        }

        RequestContext.getCurrentInstance().openDialog(htmlPath, options, parameterMap);
    }

    public void showMediaDialog(String title, String id) {
        showMediaDialog(title, id, "pdf", 575, 800, 20);
    }

    public void showMediaDialog(String title, String id, String player, Integer height, Integer width, Integer offset) {
        final String METHODNAME = "showMediaDialog ";
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("closable", true);
        options.put("resizable", false);
        options.put("includeViewParams", true);
        options.put("contentHeight", height);
        options.put("contentWidth", width);

        Map<String, List<String>> parameterMap = new HashMap();
        List<String> parm1 = new ArrayList();
        parm1.add(title);
        parameterMap.put("title", parm1);

        List<String> parm2 = new ArrayList();
        parm2.add(id);
        parameterMap.put("id", parm2);

        List<String> parm3 = new ArrayList();
        parm3.add((height - offset) + "");
        parameterMap.put("height", parm3);

        List<String> parm4 = new ArrayList();
        parm4.add((width - offset) + "");
        parameterMap.put("width", parm4);

        List<String> parm5 = new ArrayList();
        parm5.add(player);
        parameterMap.put("player", parm5);

        RequestContext.getCurrentInstance().openDialog("/module/mediaViewer", options, parameterMap);
    }

}
