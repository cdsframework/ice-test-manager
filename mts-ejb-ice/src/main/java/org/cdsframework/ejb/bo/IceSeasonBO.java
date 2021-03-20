/**
 * The MTS ice EJB project is the base framework for the CDS Framework Middle Tier Service.
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
package org.cdsframework.ejb.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import org.cdsframework.base.BaseBO;
import org.cdsframework.cds.util.MarshalUtils;
import org.cdsframework.dto.CdsVersionDTO;
import org.cdsframework.dto.IceSeasonDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.enumeration.CoreErrorCode;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.CdsException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.factory.CdsCodeDTOObjectFactory;
import org.cdsframework.group.FindAll;
import org.cdsframework.group.SimpleExchange;
import org.cdsframework.util.FileUtils;
import org.cdsframework.util.support.data.ice.season.IceSeasonSpecificationFile;
import org.opencds.vmr.v1_0.schema.CD;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
public class IceSeasonBO extends BaseBO<IceSeasonDTO> {

    @Override
    public Map<String, byte[]> exportData(IceSeasonDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "exportData ";
        if (queryClass == SimpleExchange.class) {
            return simpleExchangeExport(baseDTO, queryClass, sessionDTO, propertyBagDTO);
        } else {
            throw new ValidationException(CoreErrorCode.ParameterCanNotBeNull,
                    logger.error(METHODNAME, "Unsupported queryClass: ", queryClass));
        }
    }

    private Map<String, byte[]> simpleExchangeExport(IceSeasonDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws MtsException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "simpleExchangeExport ";
        Map<String, byte[]> fileMap = new HashMap<String, byte[]>();
        if (baseDTO != null) {
            List<IceSeasonDTO> iceSeasonDTOs = new ArrayList<IceSeasonDTO>();
            if ("ALL".equalsIgnoreCase(baseDTO.getSeasonId())) {
                List<IceSeasonDTO> findByQueryList = findByQueryListMain(baseDTO, FindAll.class, getDtoChildClasses(), sessionDTO, new PropertyBagDTO());
                if (findByQueryList != null) {
                    iceSeasonDTOs.addAll(findByQueryList);
                } else {
                    logger.error(METHODNAME, "iceSeriesDTOs is null!");
                }
            } else {
                IceSeasonDTO iceSeasonDTO = findByPrimaryKeyMain(baseDTO, getDtoChildClasses(), sessionDTO, new PropertyBagDTO());
                if (iceSeasonDTO != null) {
                    iceSeasonDTOs.add(iceSeasonDTO);
                } else {
                    logger.error(METHODNAME, "iceSeasonDTO is null!");
                }
            }
            logger.info("Found iceSeasonDTOs: ", iceSeasonDTOs.size());
            try {
                for (IceSeasonDTO iceSeasonDTO : iceSeasonDTOs) {
                    if (iceSeasonDTO != null) {
                        IceSeasonSpecificationFile iceSeasonSpecificationFile = new IceSeasonSpecificationFile();

                        iceSeasonSpecificationFile.setSeasonId(iceSeasonDTO.getSeasonId());
                        iceSeasonSpecificationFile.setName(iceSeasonDTO.getName());
                        iceSeasonSpecificationFile.setCode(iceSeasonDTO.getCode());
                        iceSeasonSpecificationFile.setDefaultSeason(iceSeasonDTO.isDefaultSeason());
                        iceSeasonSpecificationFile.setDefaultStartMonthAndDay(iceSeasonDTO.getDefaultStartMonthAndDay());
                        iceSeasonSpecificationFile.setDefaultStopMonthAndDay(iceSeasonDTO.getDefaultStopMonthAndDay());
                        iceSeasonSpecificationFile.setStartDate(iceSeasonDTO.getStartDate());
                        iceSeasonSpecificationFile.setEndDate(iceSeasonDTO.getEndDate());
                        iceSeasonSpecificationFile.setNotes(iceSeasonDTO.getNotes());
                        
                        CD vaccineGroup = CdsCodeDTOObjectFactory.getCDFromCdsCodeDTO(iceSeasonDTO.getIceVaccineGroupDTO().getVaccineGroup());
                        iceSeasonSpecificationFile.setVaccineGroup(vaccineGroup);

                        // set season versions
                        for (CdsVersionDTO cdsVersionDTO : iceSeasonDTO.getCdsVersionDTOs()) {
                            iceSeasonSpecificationFile.getCdsVersions().add(cdsVersionDTO.getOpenCdsVersionIdentifier());
                        }

                        byte[] marshalledXml = MarshalUtils.marshalObject(iceSeasonSpecificationFile);
                        fileMap.put(iceSeasonDTO.getCode()+ ".xml", marshalledXml);
                    } else {
                        logger.error(METHODNAME, "iceSeasonDTO is null!");
                    }
                }
                String xsd = FileUtils.getStringFromJarFile("iceSeasonSpecificationFile.xsd");
                fileMap.put("iceSeasonSpecificationFile.xsd", xsd.getBytes());
            } catch (CdsException e) {
                logger.error(e);
                throw new MtsException(e.getMessage());
            }
        } else {
            logger.error(METHODNAME, "baseDTO was null!");
        }
        return fileMap;
    }

    @Override
    public void importData(Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException,
            ConstraintViolationException {
        final String METHODNAME = "importData ";
        if (queryClass == SimpleExchange.class) {
            simpleExchangeImport(queryClass, sessionDTO, propertyBagDTO);
        } else {
            throw new ValidationException(CoreErrorCode.ParameterCanNotBeNull,
                    logger.error(METHODNAME, "Unsupported queryClass: ", queryClass));
        }
    }

    private void simpleExchangeImport(Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
