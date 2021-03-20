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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import org.cdsframework.base.BaseBO;
import org.cdsframework.cds.util.MarshalUtils;
import org.cdsframework.dto.IceVaccineDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
import org.cdsframework.dto.CdsVersionDTO;
import org.cdsframework.dto.IceSeasonDTO;
import org.cdsframework.dto.IceSeriesDTO;
import org.cdsframework.dto.IceSeriesDoseDTO;
import org.cdsframework.dto.IceSeriesDoseIntervalDTO;
import org.cdsframework.dto.IceSeriesDoseVaccineRelDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.enumeration.CoreErrorCode;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.CdsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.factory.CdsCodeDTOObjectFactory;
import org.cdsframework.group.FindAll;
import org.cdsframework.group.SimpleExchange;
import org.cdsframework.util.FileUtils;
import org.cdsframework.util.support.data.ice.series.IceDoseIntervalSpecification;
import org.cdsframework.util.support.data.ice.series.IceDoseVaccineSpecification;
import org.cdsframework.util.support.data.ice.series.IceSeriesDoseSpecification;
import org.cdsframework.util.support.data.ice.series.IceSeriesSpecificationFile;
import org.opencds.vmr.v1_0.schema.CD;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
public class IceSeriesBO extends BaseBO<IceSeriesDTO> {

    @Override
    public Map<String, byte[]> exportData(IceSeriesDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "exportData ";
        if (queryClass == SimpleExchange.class) {
            return simpleExchangeExport(baseDTO, queryClass, sessionDTO, propertyBagDTO);
        } else {
            throw new ValidationException(CoreErrorCode.ParameterCanNotBeNull,
                    logger.error(METHODNAME, "Unsupported queryClass: ", queryClass));
        }
    }

    private Map<String, byte[]> simpleExchangeExport(IceSeriesDTO baseDTO, Class queryClass, SessionDTO sessionDTO, PropertyBagDTO propertyBagDTO)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "simpleExchangeExport ";
        Map<String, byte[]> fileMap = new HashMap<String, byte[]>();
        if (baseDTO != null) {
            List<IceSeriesDTO> iceSeriesDTOs = new ArrayList<IceSeriesDTO>();
            if ("ALL".equalsIgnoreCase(baseDTO.getSeriesId())) {
                List<IceSeriesDTO> findByQueryList = findByQueryListMain(baseDTO, FindAll.class, getDtoChildClasses(), sessionDTO, new PropertyBagDTO());
                if (findByQueryList != null) {
                    iceSeriesDTOs.addAll(findByQueryList);
                } else {
                    logger.error(METHODNAME, "iceSeriesDTOs is null!");
                }
            } else {
                IceSeriesDTO iceSeriesDTO = findByPrimaryKeyMain(baseDTO, getDtoChildClasses(), sessionDTO, new PropertyBagDTO());
                if (iceSeriesDTO != null) {
                    iceSeriesDTOs.add(iceSeriesDTO);
                } else {
                    logger.error(METHODNAME, "iceSeriesDTO is null!");
                }
            }
            logger.info("Found iceSeriesDTOs: ", iceSeriesDTOs.size());
            try {
                for (IceSeriesDTO iceSeriesDTO : iceSeriesDTOs) {
                    if (iceSeriesDTO != null) {
                        IceSeriesSpecificationFile iceSeriesSpecificationFile = new IceSeriesSpecificationFile();

                        iceSeriesSpecificationFile.setSeriesId(iceSeriesDTO.getSeriesId());
                        iceSeriesSpecificationFile.setName(iceSeriesDTO.getName());
                        iceSeriesSpecificationFile.setCode(iceSeriesDTO.getCode());

                        // set schedule name and version
                        for (CdsVersionDTO cdsVersionDTO : iceSeriesDTO.getCdsVersionDTOs()) {
                            iceSeriesSpecificationFile.getCdsVersions().add(cdsVersionDTO.getOpenCdsVersionIdentifier());
                        }

                        iceSeriesSpecificationFile.setNumberOfDosesInSeries(BigInteger.valueOf(iceSeriesDTO.getNumberOfDoses()));

                        // set vaccine group
                        IceVaccineGroupDTO iceVaccineGroupDTO = iceSeriesDTO.getIceVaccineGroupDTO();
                        CD vaccineGroup = CdsCodeDTOObjectFactory.getCDFromCdsCodeDTO(iceVaccineGroupDTO.getVaccineGroup());
                        iceSeriesSpecificationFile.getVaccineGroups().add(vaccineGroup);

                        // set dose intervals
                        List<IceDoseIntervalSpecification> doseIntervals = iceSeriesSpecificationFile.getDoseIntervals();
                        for (IceSeriesDoseIntervalDTO iceSeriesDoseIntervalDTO : iceSeriesDTO.getIceSeriesDoseIntervalDTOs()) {
                            IceDoseIntervalSpecification doseIntervalSpecification = new IceDoseIntervalSpecification();
                            doseIntervalSpecification.setFromDoseNumber(BigInteger.valueOf(iceSeriesDoseIntervalDTO.getFromDoseNumber()));
                            doseIntervalSpecification.setToDoseNumber(BigInteger.valueOf(iceSeriesDoseIntervalDTO.getToDoseNumber()));
                            doseIntervalSpecification.setAbsoluteMinimumInterval(iceSeriesDoseIntervalDTO.getAbsMinInterval());
                            doseIntervalSpecification.setMinimumInterval(iceSeriesDoseIntervalDTO.getMinInterval());
                            doseIntervalSpecification.setEarliestRecommendedInterval(iceSeriesDoseIntervalDTO.getEarliestRecommendedInterval());
                            doseIntervalSpecification.setLatestRecommendedInterval(iceSeriesDoseIntervalDTO.getLatestRecommendedInterval());
                            doseIntervals.add(doseIntervalSpecification);
                        }

                        // set series doses
                        List<IceSeriesDoseSpecification> iceSeriesDoses = iceSeriesSpecificationFile.getIceSeriesDoses();
                        for (IceSeriesDoseDTO iceSeriesDoseDTO : iceSeriesDTO.getIceSeriesDoseDTOs()) {
                            IceSeriesDoseSpecification iceSeriesDoseSpecification = new IceSeriesDoseSpecification();
                            iceSeriesDoseSpecification.setDoseNumber(BigInteger.valueOf(iceSeriesDoseDTO.getDoseNumber()));
                            iceSeriesDoseSpecification.setEarliestRecommendedAge(iceSeriesDoseDTO.getEarliestRecommendedAge());
                            iceSeriesDoseSpecification.setLatestRecommendedAge(iceSeriesDoseDTO.getLatestRecommendedAge());
                            iceSeriesDoseSpecification.setMaximumAge(iceSeriesDoseDTO.getMaxAge());
                            iceSeriesDoseSpecification.setMinimumAge(iceSeriesDoseDTO.getMinAge());
                            iceSeriesDoseSpecification.setAbsoluteMinimumAge(iceSeriesDoseDTO.getAbsMinAge());
                            List<IceDoseVaccineSpecification> doseVaccines = iceSeriesDoseSpecification.getDoseVaccines();
                            for (IceSeriesDoseVaccineRelDTO iceSeriesDoseVaccineRelDTO : iceSeriesDoseDTO.getIceSeriesDoseVaccineRelDTOs()) {
                                IceDoseVaccineSpecification doseVaccineSpecification = new IceDoseVaccineSpecification();
                                doseVaccineSpecification.setPreferred(iceSeriesDoseVaccineRelDTO.isPreferredVaccine());
                                IceVaccineDTO iceVaccineDTO = iceSeriesDoseVaccineRelDTO.getIceVaccineDTO();
                                CD vaccine = CdsCodeDTOObjectFactory.getCDFromCdsCodeDTO(iceVaccineDTO.getVaccine());
                                doseVaccineSpecification.setVaccine(vaccine);
                                doseVaccines.add(doseVaccineSpecification);
                            }
                            iceSeriesDoses.add(iceSeriesDoseSpecification);
                        }
                        // set series seasons
                        List<String> seasonCodes = iceSeriesSpecificationFile.getSeasonCodes();
                        for (IceSeasonDTO iceSeasonDTO : iceSeriesDTO.getIceSeasonDTOs()) {
                            seasonCodes.add(iceSeasonDTO.getCode());
                        }

                        byte[] marshalledXml = MarshalUtils.marshalObject(iceSeriesSpecificationFile);
                        fileMap.put(iceSeriesDTO.getCode()+ ".xml", marshalledXml);
                    } else {
                        logger.error(METHODNAME, "iceSeriesDTO is null!");
                    }
                }
                String xsd = FileUtils.getStringFromJarFile("iceSeriesSpecificationFile.xsd");
                fileMap.put("iceSeriesSpecificationFile.xsd", xsd.getBytes());
            } catch (CdsException e) {
                logger.error(e);
                throw new MtsException(e.getMessage());
            }
        } else {
            logger.error(METHODNAME, "baseDTO was null!");
        }
        return fileMap;
    }
}
