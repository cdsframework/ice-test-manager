/*
 * The CAT Core plugin webapp project.
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
    var DEBUGx = new Boolean(true);
    
    function addLoadEvent(func) {
        var oldonload = window.onload;
        if (typeof window.onload !== 'function') {
            window.onload = func;
        } else {
            window.onload = function () {
                if (oldonload) {
                    oldonload();
                }
                func();
            };
        }
    }

    function log(msg) {
        if (typeof console !== "undefined") {
            console.log(msg);
        }
    }    
    
    function onDialogTabChange(saveImmediately, editFormClientId, index, cmdId) {
        var METHODNAME = 'onDialogTabChange ';
        if (DEBUGx) {
            log(METHODNAME + "saveImmediately=" + saveImmediately + " editFormClientId=" + editFormClientId + " index=" + index + " cmdId=" + cmdId);
        }
        var formChanged = false;
        if (saveImmediately) {
            formChanged = isEditFormChanged(editFormClientId);
            if (DEBUGx) {
                log(METHODNAME + "formChanged=" + formChanged);
            }        
            if (formChanged) {
                cmdId();
            }
        }
        return !formChanged;
    }    

    /* The onDialogTabChange can call this function */
    function isFormChanged(editFormClientId, cmdId) {
        var METHODNAME = 'isFormChanged ';
        if (DEBUGx) {
            log(METHODNAME + " editFormClientId=" + editFormClientId + " cmdId=" + cmdId);
        }
        var formChanged = isEditFormChanged(editFormClientId);
        if (DEBUGx) {
            log(METHODNAME + "formChanged=" + formChanged);
        }                
        if (formChanged) {
            cmdId();
        }
        return !formChanged;
    }        
    
    function onDataTableAction(saveImmediately, action, editFormClientId, cmdId) {
        var METHODNAME = 'onDataTableAction ';
        if (DEBUGx) {
            log(METHODNAME + "saveImmediately=" + saveImmediately, " action=" + action  + " editFormClientId=" + editFormClientId  + " cmdId=" + cmdId);
        }
        
        var formChanged = false;
        if (saveImmediately) {
            // Short circuit when ROWNEWDELETE (used by dtoInlineDataTable undo NEW row)
            if (action === "ROWNEWDELETE") {
                setFormChanged(editFormClientId, false);
                formChanged = false;
            }
            else {
                /* Why would the editFormClientId be undefined ? */
                if (!editFormClientId) {
                    if (DEBUGx) {
                        log(METHODNAME + "empty or null or undefined");
                    }
                    formChanged = false;
                }
                else {
                    var formChanged = isEditFormChanged(editFormClientId);
                    if (formChanged) {
                        cmdId();
                    }
                    else {
                        if (action === 'ROWNEW') {
                            setFormChanged(editFormClientId, true);
                        }
                    }
                }
            }
        }
        return !formChanged;
    }    
    
    function onDialogSave(editFormClientId, saved, dialogWidgetVar) {
        var METHODNAME = 'onDialogSave ';
        if (DEBUGx) {
            log(METHODNAME + "editFormClientId=" + editFormClientId + " saved= " + saved + " dialogWidgetVar=" + dialogWidgetVar);
        }
        if (saved) {
            dialogWidgetVar.hide();
            setFormChanged(editFormClientId, false);
        }
    }

    function onDialogApply(editFormClientId, saved) {
        var METHODNAME = 'onDialogApply ';
        if (DEBUGx) {
            log(METHODNAME + "editFormClientId=" + editFormClientId + " saved= " + saved);
        }
        if (saved) {
            setFormChanged(editFormClientId, false);
        }
    }

    function getForm(formName) {
        var METHODNAME = 'getForm ';
        /*
        if (DEBUGx) {
            log(METHODNAME + "formName=" + formName);
        } 
        */
        var form = $('form[name="' + formName + '"]');
        return form;
    }
    
    function getFormInput(form, inputName) {
        var METHODNAME = 'getFormInput ';
        /*
        if (DEBUGx) {
            log(METHODNAME + "form=" + form + " inputName=" + inputName);
        } 
        */
        return form.find("input[name='" + inputName + "']");        
    }    
    
    function setFormChanged(editFormClientId, changed) {
        var METHODNAME = 'setFormChanged ';
        if (DEBUGx) {
            log(METHODNAME + "editFormClientId=" + editFormClientId + " changed=" + changed);
        }        
        var editForm = getForm(editFormClientId);
        var formChanged = getFormInput(editForm, "formChanged");
        // Set the formChanged value within the formState
        formChanged.attr('value', changed);
        if (DEBUGx) {
            log(METHODNAME + "formChanged.val()=" + formChanged.val());
            //log(METHODNAME + "formChanged.val()===true " + (formChanged.val() === "true"));
            //log(METHODNAME + "formChanged.val()==true " + (formChanged.val() == "true"));
        }        
    }    

    function isEditFormChanged(editFormClientId) {        
        var METHODNAME = 'isEditFormChanged ';
        if (DEBUGx) {
            log(METHODNAME + "editFormClientId=" + editFormClientId);
        }        
        var editForm = getForm(editFormClientId);
        var formChanged = getFormInput(editForm, "formChanged");
        if (DEBUGx) {
            log(METHODNAME + "editFormClientId=" + editFormClientId);
            log(METHODNAME + "editForm=" + editForm);
            log(METHODNAME + "formChanged=" + formChanged);
            log(METHODNAME + "formChanged.val()" + formChanged.val());
        }
        //return formChanged.val() === "true";        
        return formChanged.val() === "true";        
    }
          