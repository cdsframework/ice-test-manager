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
package org.cdsframework.section.cds.valueset;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.BaseModule;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SystemPropertyDTO;
import org.cdsframework.dto.ValueSetCdsCodeRelDTO;
import org.cdsframework.dto.ValueSetDTO;
import org.cdsframework.enumeration.ValueSetImportType;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.lookup.SystemPropertyDTOList;
import org.cdsframework.lookup.ValueSetDTOList;
import org.cdsframework.util.VsacUtils;
import org.cdsframework.util.cds.ImportUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class ValueSetMGR extends BaseModule<ValueSetDTO> {

    private static final long serialVersionUID = -8245237558372701260L;

    @Inject
    private ValueSetDTOList valueSetDTOList;

    // For value set import...
    private String importOid;
    private String importType;
    private String importValueSetVersion;
    private String importValueSetProfile;
    private boolean existingValueSet;

    @Inject
    private SystemPropertyDTOList systemPropertyDTOList;
    @Inject
    private ValueSetCdsCodeRelMGR valueSetCdsCodeRelMGR;

    //    @Inject
    //    private CdsListDTOList cdsListDTOList;
    @Override
    protected void initialize() {
        setLazy(true);
//        addOnRowSelectChildClassDTO(ValueSetSubValueSetRelDTO.class);
//        addOnRowSelectChildClassDTO(ValueSetCdsCodeRelDTO.class);
//        setAssociatedList(cdsListDTOList);
        registerChild(ValueSetCdsCodeRelDTO.ByValueSetId.class, valueSetCdsCodeRelMGR);
        setAssociatedList(valueSetDTOList);
        setInitialQueryClass("FindAll");
        setSaveImmediately(true);
    }

    @Override
    public void registerTabComponents() {
        final String METHODNAME = "registerTabLazyLoadComponents ";
        logger.info(METHODNAME);
        // Register the Components
        getTabService().registeredUIComponent(3, this);
        getTabService().registeredUIComponent(2, this);
//        getTabLazyLoadService().registeredUIComponent(relatedCodeSystemsTab, CdsListDTOList.class);
//        getTabLazyLoadService().registeredUIComponent(subvalueTab, ValueSetDTOList.class);
    }

//    @Override
//    public void resetField(BaseDTO baseDTO, String fieldName) {
//        super.resetField(baseDTO, fieldName); //To change body of generated methods, choose Tools | Templates.
//        final String METHODNAME = "resetField ";
//
//        if (baseDTO instanceof ValueSetCdsCodeRelDTO) {
//            ValueSetCdsCodeRelDTO valueSetCdsCodeRelDTO = (ValueSetCdsCodeRelDTO) baseDTO;
//            if (fieldName.equalsIgnoreCase("cdsCodeDTO")) {
//                valueSetCdsCodeRelDTO.setCdsCodeDTO(null);
//            }
//        }
//
//    }
    @Override
    public void onSearchDialogReturn(SelectEvent selectEvent) throws Exception {
        final String METHODNAME = "onSearchDialogReturn ";
        super.onSearchDialogReturn(selectEvent);
        BaseDTO baseDTO = (BaseDTO) selectEvent.getObject();
        logger.info(METHODNAME, "baseDTO=", (baseDTO != null ? baseDTO.getClass().getSimpleName() : baseDTO));
        BaseDTO selectedDTO = getSelectedDTO();
        logger.info(METHODNAME, "selectedDTO=", (selectedDTO != null ? selectedDTO.getClass().getSimpleName() : selectedDTO));

        if (baseDTO instanceof CdsCodeDTO) {
            CdsCodeDTO cdsCodeDTO = (CdsCodeDTO) baseDTO;
            if (selectedDTO instanceof ValueSetCdsCodeRelDTO) {
                logger.info(METHODNAME, "calling setCdsCodeDTO on selectedDTO");
                ValueSetCdsCodeRelDTO valueSetCdsCodeRelDTO = (ValueSetCdsCodeRelDTO) selectedDTO;
                valueSetCdsCodeRelDTO.setCdsCodeDTO(cdsCodeDTO);
            }
        }

        // Clear out selected DTO
        setSelectedDTO(null);
    }

    public void importConcept(FileUploadEvent event) {
        try {
            byte[] payload = ImportUtils.getBase64ByteArrayPayloadFromFileUploadEvent(event);
            PropertyBagDTO propertyBagDTO = getNewPropertyBagDTO();
            propertyBagDTO.put("payload", payload);
            propertyBagDTO.put("importType", ValueSetImportType.PHINVADS);
            propertyBagDTO.setQueryClass("SimpleExchange");
            getGeneralMGR().importData(ValueSetDTO.class, getSessionDTO(), propertyBagDTO);
            performInitialSearch();
            getMessageMGR().displayInfoMessage("Value set(s) successfully imported.");
        } catch (IOException e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } catch (AuthenticationException e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } catch (AuthorizationException e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } catch (CatException e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } catch (ConstraintViolationException e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } catch (MtsException e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } catch (NotFoundException e) {
            DefaultExceptionHandler.handleException(e, getClass());
        } catch (ValidationException e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    public void importValueSet() {
        final String METHODNAME = "importValueSet ";
        try {
            String encoderType = "UTF-8";
            PropertyBagDTO propertyBagDTO = getNewPropertyBagDTO();
            propertyBagDTO.setQueryClass("SimpleExchange");
            propertyBagDTO.put("importType", ValueSetImportType.VSAC);
            propertyBagDTO.put("oid", importOid);

            // Determine the correct parameters based on the user selections...
            if (importType.equals("published")) {
                propertyBagDTO.put("version", URLEncoder.encode(importValueSetVersion, encoderType).replace("+", "%20"));
            } else {
                propertyBagDTO.put("version", "Draft");
                propertyBagDTO.put("profile", URLEncoder.encode(importValueSetProfile, encoderType).replace("+", "%20"));
                propertyBagDTO.put("includeDraft", true);
            }

            // Do the import and refresh the value set list...
            getGeneralMGR().importData(ValueSetDTO.class, getSessionDTO(), propertyBagDTO);
            performInitialSearch();

            // There were no exceptions, show a success message.
            getMessageMGR().displayInfoMessage("The value set was imported successfully.");

        } catch (Exception e) {
            getMessageMGR().displayErrorMessage("There was an error importing the value set - " + e.getMessage());
            //onExceptionMain(METHODNAME, e);
        } finally {
            resetImport();
        }
    }

    public void resetImport() {
        importOid = null;
        importType = null;
        importValueSetVersion = null;
        importValueSetProfile = null;
        existingValueSet = false;
    }

    public void validateImport(AjaxBehaviorEvent event) {
        final String METHODNAME = "validateImport ";
        ValueSetDTO valueSetDTO = new ValueSetDTO();
        try {
            existingValueSet = false;
            PropertyBagDTO propertyBagDTO = getNewPropertyBagDTO();
            valueSetDTO.setOid(importOid);
            logger.info(METHODNAME, "OID: " + importOid);

            // If the import type is published and version is selected, 
            // we need to validate both the oid and the version...
            if (importType != null) {
                if (importType.equals("published")) {
                    valueSetDTO.setVersion(importValueSetVersion);
                    logger.info(METHODNAME, "Version: " + importValueSetVersion);
                } else {
                    valueSetDTO.setVersion("Draft");
                    logger.info(METHODNAME, "Version: " + "Draft");
                }
                propertyBagDTO.setQueryClass("ByOidVersion");
                getGeneralMGR().findByQuery(valueSetDTO, getSessionDTO(), propertyBagDTO);
                existingValueSet = true;
            }
        } catch (NotFoundException e) {
            existingValueSet = false;
        } catch (Exception e) {
            onExceptionMain(METHODNAME, e);
        } finally {
            logger.info(METHODNAME, "Value Set exists: " + existingValueSet);
        }
    }

    public List<String> getVsacVersions() {
        final String METHODNAME = "getVsacVersions ";
        SystemPropertyDTO uriPropertyDTO = systemPropertyDTOList.getByNameScope("VSAC_BASE_URI", "cds");
        SystemPropertyDTO usernamePropertyDTO = systemPropertyDTOList.getByNameScope("VSAC_USERNAME", "cds");
        SystemPropertyDTO passwordPropertyDTO = systemPropertyDTOList.getByNameScope("VSAC_PASSWORD", "cds");
        String uri = uriPropertyDTO.getValue();
        String username = usernamePropertyDTO.getValue();
        String password = passwordPropertyDTO.getValue();
        List<String> list = new ArrayList<String>();
        try {
            if (importOid.isEmpty() == false) {
                list = VsacUtils.getVersionList(uri, username, password, importOid);
                logger.info(METHODNAME, "Value Set Versions: " + (list != null ? list.size() : "null"));
            }
        } catch (Exception e) {
            onExceptionMain(METHODNAME, e);
        }
        Collections.sort(list);
        return list;
    }

    public List<String> getVsacProfiles() {
        final String METHODNAME = "getVsacProfiles ";
        SystemPropertyDTO uriPropertyDTO = systemPropertyDTOList.getByNameScope("VSAC_BASE_URI", "cds");
        SystemPropertyDTO usernamePropertyDTO = systemPropertyDTOList.getByNameScope("VSAC_USERNAME", "cds");
        SystemPropertyDTO passwordPropertyDTO = systemPropertyDTOList.getByNameScope("VSAC_PASSWORD", "cds");
        String uri = uriPropertyDTO.getValue();
        String username = usernamePropertyDTO.getValue();
        String password = passwordPropertyDTO.getValue();
        List<String> list = new ArrayList<String>();
        try {
            list = VsacUtils.getProfileList(uri, username, password);
            logger.info(METHODNAME, "Value Set Profiles: " + (list != null ? list.size() : "null"));
        } catch (Exception e) {
            onExceptionMain(METHODNAME, e);
        }
        Collections.sort(list);
        return list;
    }

    public String getImportOid() {
        return importOid;
    }

    public void setImportOid(String importOid) {
        this.importOid = importOid;
    }

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    public boolean isExistingValueSet() {
        return existingValueSet;
    }

    public void setExistingValueSet(boolean existingValueSet) {
        this.existingValueSet = existingValueSet;
    }

    public String getImportValueSetVersion() {
        return importValueSetVersion;
    }

    public void setImportValueSetVersion(String importValueSetVersion) {
        this.importValueSetVersion = importValueSetVersion;
    }

    public String getImportValueSetProfile() {
        return importValueSetProfile;
    }

    public void setImportValueSetProfile(String importValueSetProfile) {
        this.importValueSetProfile = importValueSetProfile;
    }
}
