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

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.util.LogUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@RequestScoped
public class DialogForm {
    private LogUtils logger = LogUtils.getLogger(DialogForm.class);
    
    public void yes(ActionEvent actionEvent) {
        final String METHODNAME = "yes ";
        RequestContext.getCurrentInstance().closeDialog("yes");
    }

    public void no(ActionEvent actionEvent) {
        final String METHODNAME = "no ";
        RequestContext.getCurrentInstance().closeDialog("no");
    }

    public void ok(ActionEvent actionEvent) {
        final String METHODNAME = "ok ";
        logger.info(METHODNAME);
        RequestContext.getCurrentInstance().closeDialog("ok");
    }
    
    public void onRowSelect(SelectEvent selectEvent) {
        final String METHODNAME = "select ";
        logger.info(METHODNAME, "selectEvent=", selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        if (baseDTO != null) {
            logger.info(METHODNAME + "baseDTO.getClass().getSimpleName()=" + baseDTO.getClass().getSimpleName());
        }
        RequestContext.getCurrentInstance().closeDialog(baseDTO);
    }    
        
}
