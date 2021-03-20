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
package org.cdsframework.factory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.cdsframework.dto.CdsCodeDTO;
import org.cdsframework.dto.CdsVersionDTO;
import org.cdsframework.dto.IceDiseaseDTO;
import org.cdsframework.dto.IceTestVaccineGroupRelDTO;
import org.cdsframework.dto.IceVaccineComponentDTO;
import org.cdsframework.dto.IceVaccineComponentDiseaseRelDTO;
import org.cdsframework.dto.IceVaccineComponentRelDTO;
import org.cdsframework.dto.IceVaccineDTO;
import org.cdsframework.dto.IceVaccineGroupDTO;
import org.cdsframework.dto.IceVaccineGroupDiseaseRelDTO;
import org.cdsframework.dto.IceVaccineGroupVaccineRelDTO;
import org.cdsframework.dto.IceVaccineGroupVersionRelDTO;
import org.cdsframework.dto.IceVaccineVersionRelDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.ejb.bo.IceVaccineBO;
import org.cdsframework.ejb.bo.IceVaccineComponentBO;
import org.cdsframework.ejb.bo.IceVaccineGroupBO;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.group.FindAll;
import org.cdsframework.util.AuthenticationUtils;
import org.cdsframework.util.EJBUtils;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
public class IceVaccineObjectFactory {

    private final static LogUtils logger = LogUtils.getLogger(IceVaccineObjectFactory.class);

    private final static LoadingCache<CacheKey, IceVaccineDTO> codeCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<CacheKey, IceVaccineDTO>() {
                @Override
                public IceVaccineDTO load(CacheKey key) throws ValidationException, MtsException, NotFoundException, AuthenticationException, AuthorizationException {
                    IceVaccineDTO result = null;
                    IceVaccineBO iceVaccineBO = (IceVaccineBO) EJBUtils.getDtoBo(IceVaccineDTO.class);
                    List<Class> childClasses = new ArrayList<Class>();
                    childClasses.add(IceVaccineComponentRelDTO.class);
                    childClasses.add(IceVaccineVersionRelDTO.class);
                    childClasses.add(IceVaccineGroupVaccineRelDTO.class);
                    CdsCodeDTO cdsCodeDTO = new CdsCodeDTO();
                    cdsCodeDTO.setCode(key.getCode().trim().toLowerCase());
                    IceVaccineDTO queryDTO = new IceVaccineDTO();
                    queryDTO.setVaccine(cdsCodeDTO);
                    List<IceVaccineDTO> results = iceVaccineBO.findByQueryListMain(
                            queryDTO,
                            IceVaccineDTO.ByVaccineCode.class,
                            childClasses,
                            AuthenticationUtils.getInternalSessionDTO(),
                            new PropertyBagDTO());
                    for (IceVaccineDTO item : results) {
                        if (item.getRelatedVersions().contains(key.getCdsVersionDTO())) {
                            result = item;
                            break;
                        }
                    }
                    return result;
                }
            });

    private final static LoadingCache<CacheKey, IceVaccineGroupDTO> groupCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<CacheKey, IceVaccineGroupDTO>() {
                @Override
                public IceVaccineGroupDTO load(CacheKey key) throws ValidationException, MtsException, NotFoundException, AuthenticationException, AuthorizationException {
                    IceVaccineGroupDTO result = null;
                    IceVaccineGroupBO iceVaccineGroupBO = (IceVaccineGroupBO) EJBUtils.getDtoBo(IceVaccineGroupDTO.class);
                    List<Class> childClasses = new ArrayList<Class>();
                    childClasses.add(IceVaccineGroupVersionRelDTO.class);
                    childClasses.add(IceVaccineGroupDiseaseRelDTO.class);
                    childClasses.add(IceVaccineGroupVaccineRelDTO.class);
                    CdsCodeDTO cdsCodeDTO = new CdsCodeDTO();
                    cdsCodeDTO.setCode(key.getCode().trim().toLowerCase());
                    cdsCodeDTO.setDisplayName(key.getCode().trim().toLowerCase());
                    IceVaccineGroupDTO queryDTO = new IceVaccineGroupDTO();
                    queryDTO.setVaccineGroup(cdsCodeDTO);

                    List<IceVaccineGroupDTO> results = iceVaccineGroupBO.findByQueryListMain(
                            queryDTO,
                            IceVaccineGroupDTO.ByVaccineGroupNameOrCode.class,
                            childClasses,
                            AuthenticationUtils.getInternalSessionDTO(),
                            new PropertyBagDTO());
                    for (IceVaccineGroupDTO item : results) {
                        if (item.getRelatedVersions().contains(key.getCdsVersionDTO())) {
                            result = item;
                            break;
                        }
                    }
                    return result;
                }
            });

    private final static LoadingCache<CacheKey, IceVaccineGroupDTO> componentCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<CacheKey, IceVaccineGroupDTO>() {
                @Override
                public IceVaccineGroupDTO load(CacheKey key) throws ValidationException, MtsException, NotFoundException, AuthenticationException, AuthorizationException {
                    final String METHODNAME = "componentCache load ";
                    IceVaccineGroupDTO result = null;
                    IceVaccineGroupBO iceVaccineGroupBO = (IceVaccineGroupBO) EJBUtils.getDtoBo(IceVaccineGroupDTO.class);
                    IceVaccineComponentBO iceVaccineComponentBO = (IceVaccineComponentBO) EJBUtils.getDtoBo(IceVaccineComponentDTO.class);
                    IceVaccineComponentDTO iceVaccineComponentDTO = key.getIceVaccineComponentDTO();
                    CdsVersionDTO cdsVersionDTO = key.getCdsVersionDTO();
                    logger.info(METHODNAME, "iceVaccineComponentDTO=", iceVaccineComponentDTO);
                    logger.info(METHODNAME, "cdsVersionDTO=", cdsVersionDTO);
                    if (iceVaccineComponentDTO != null && cdsVersionDTO != null) {
                        List<Class> childClasses = new ArrayList<Class>();
                        childClasses.add(IceVaccineComponentDiseaseRelDTO.class);
                        iceVaccineComponentDTO = iceVaccineComponentBO.findByPrimaryKeyMain(iceVaccineComponentDTO, childClasses, AuthenticationUtils.getInternalSessionDTO(), new PropertyBagDTO());
                        logger.info(METHODNAME, "iceVaccineComponentDTO.getVaccineComponent()=", iceVaccineComponentDTO.getVaccineComponent());
                        if (iceVaccineComponentDTO.getVaccineComponent() != null) {
                            logger.info(METHODNAME, "iceVaccineComponentDTO.getVaccineComponent().getCode()=", iceVaccineComponentDTO.getVaccineComponent().getCode());
                        }
                        List<IceVaccineComponentDiseaseRelDTO> iceVaccineComponentDiseaseRelDTOs = iceVaccineComponentDTO.getIceVaccineComponentDiseaseRelDTOs();
                        logger.info(METHODNAME, "iceVaccineComponentDiseaseRelDTOs=", iceVaccineComponentDiseaseRelDTOs);
                        if (iceVaccineComponentDiseaseRelDTOs != null && iceVaccineComponentDiseaseRelDTOs.size() > 0) {
                            List<IceDiseaseDTO> diseases = new ArrayList<IceDiseaseDTO>();
                            for (IceVaccineComponentDiseaseRelDTO iceVaccineComponentDiseaseRelDTO : iceVaccineComponentDiseaseRelDTOs) {
                                if (iceVaccineComponentDiseaseRelDTO != null) {
                                    IceDiseaseDTO iceDiseaseDTO = iceVaccineComponentDiseaseRelDTO.getIceDiseaseDTO();
                                    if (iceDiseaseDTO != null) {
                                        diseases.add(iceDiseaseDTO);
                                        logger.info(METHODNAME, "adding component disease=", iceDiseaseDTO.getDiseaseCdsCodeDTO().getCode());
                                    }
                                }
                            }
                            logger.info(METHODNAME, "diseases=", diseases);
                            if (diseases.size() > 0) {
                                childClasses = new ArrayList<Class>();
                                childClasses.add(IceVaccineGroupVersionRelDTO.class);
                                childClasses.add(IceVaccineGroupDiseaseRelDTO.class);
                                childClasses.add(IceVaccineGroupVaccineRelDTO.class);
                                List<IceVaccineGroupDTO> results = iceVaccineGroupBO.findByQueryListMain(new IceVaccineGroupDTO(), FindAll.class, childClasses, AuthenticationUtils.getInternalSessionDTO(), new PropertyBagDTO());
                                logger.info(METHODNAME, "group results=", results);
                                for (IceVaccineGroupDTO item : results) {
                                    logger.info(METHODNAME, "IceVaccineGroupDTO.getVaccineGroupCode()=", item.getVaccineGroupCode());
                                    boolean foundVersion = false;
                                    boolean foundDiseases = true;
                                    List<IceVaccineGroupVersionRelDTO> iceVaccineGroupVersionRelDTOs = item.getIceVaccineGroupVersionRelDTOs();
                                    if (iceVaccineGroupVersionRelDTOs != null) {
                                        for (IceVaccineGroupVersionRelDTO iceVaccineGroupVersionRelDTO : iceVaccineGroupVersionRelDTOs) {
                                            CdsVersionDTO groupVersion = iceVaccineGroupVersionRelDTO.getCdsVersionDTO();
                                            if (cdsVersionDTO.equals(groupVersion)) {
                                                foundVersion = true;
                                                break;
                                            }
                                        }

                                    }
                                    logger.info(METHODNAME, "foundVersion=", foundVersion);
                                    if (foundVersion) {
                                        List<IceDiseaseDTO> relatedDiseases = item.getRelatedDiseases();
                                        logger.info(METHODNAME, "relatedDiseases=", relatedDiseases);
                                        if (relatedDiseases.size() > 0) {
                                            for (IceDiseaseDTO iceDiseaseDTO : relatedDiseases) {
                                                logger.info(METHODNAME, "iceDiseaseDTO.getDiseaseCdsCodeDTO().getCode()=", iceDiseaseDTO.getDiseaseCdsCodeDTO().getCode());
                                            }
                                            for (IceDiseaseDTO iceDiseaseDTO : relatedDiseases) {
                                                if (!diseases.contains(iceDiseaseDTO)) {
                                                    foundDiseases = false;
                                                    break;
                                                }
                                            }
                                            logger.info(METHODNAME, "foundDiseases=", foundDiseases);
                                            if (foundDiseases) {
                                                result = item;
                                                logger.info(METHODNAME, "item.getVaccineGroupCode()=", item.getVaccineGroupCode());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        logger.error(METHODNAME, "iceVaccineComponentDTO or cdsVersionDTO was null: ", iceVaccineComponentDTO, " - ", cdsVersionDTO);
                    }
                    return result;
                }
            });

    /**
     * Get an IceVaccineDTO based on a code and cds version.
     *
     * @param code
     * @param cdsVersionDTO
     * @return
     * @throws MtsException
     * @throws NotFoundException
     */
    public static IceVaccineDTO getIceVaccineDTO(String code, CdsVersionDTO cdsVersionDTO) throws MtsException, NotFoundException {
        final String METHODNAME = "getIceVaccineDTO ";
        logger.info(METHODNAME, "code: ", code, " - cdsVersion: ", cdsVersionDTO);
        IceVaccineDTO result = null;
        if (code != null && cdsVersionDTO != null) {

            try {
                CacheKey key = new CacheKey();
                key.setCode(code);
                key.setCdsVersionDTO(cdsVersionDTO);
                result = codeCache.get(key);
            } catch (ExecutionException e) {
                logger.error(e);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return result;
    }

    /**
     * Get an IceVaccineComponentDTO based on a code and an IceVaccineDTO.
     *
     * @param code
     * @param iceVaccineDTO
     * @param iceTestVaccineGroupRelDTOs
     * @param cdsVersionDTO
     * @return
     * @throws MtsException
     * @throws NotFoundException
     */
    public static IceVaccineComponentDTO getIceVaccineComponentDTO(String code, IceVaccineDTO iceVaccineDTO, List<IceTestVaccineGroupRelDTO> iceTestVaccineGroupRelDTOs, CdsVersionDTO cdsVersionDTO) throws MtsException,
            NotFoundException {
        final String METHODNAME = "getIceVaccineComponentDTO ";
        logger.info(METHODNAME, "code: ", code, " - iceVaccineDTO: ", iceVaccineDTO);
        IceVaccineComponentDTO result = null;
        if (code != null && iceVaccineDTO != null) {
            logger.info(METHODNAME, "iceVaccineDTO.getVaccine()=", iceVaccineDTO.getVaccine());
            if (iceVaccineDTO.getVaccine() != null) {
                logger.info(METHODNAME, "iceVaccineDTO.getVaccine().getCode()=", iceVaccineDTO.getVaccine().getCode());
            }
            for (IceVaccineComponentDTO iceVaccineComponentDTO : iceVaccineDTO.getVaccineComponents()) {
                if (iceVaccineComponentDTO != null) {
                    if (iceVaccineComponentDTO.getVaccineComponent() != null) {
                        if (iceVaccineComponentDTO.getVaccineComponent().getCode() != null) {
                            logger.info(METHODNAME, "comparing: ", iceVaccineComponentDTO.getVaccineComponent().getCode(), " to: '", code, "'");
                            if (iceVaccineComponentDTO.getVaccineComponent().getCode().equalsIgnoreCase(code)) {
                                result = iceVaccineComponentDTO;
                                break;
                            }
                        } else {
                            logger.info(METHODNAME, "iceVaccineComponentDTO.getVaccineComponent().getCode() is null");
                        }
                    } else {
                        logger.info(METHODNAME, "iceVaccineComponentDTO.getVaccineComponent() is null");
                    }
                } else {
                    logger.info(METHODNAME, "iceVaccineComponentDTO is null");
                }
            }
            if (result == null && iceTestVaccineGroupRelDTOs != null) {
                if (iceTestVaccineGroupRelDTOs.size() == 1) {
                    IceVaccineGroupDTO iceVaccineGroupDTO = iceTestVaccineGroupRelDTOs.get(0).getIceVaccineGroupDTO();
                    if (iceVaccineGroupDTO != null) {
                        logger.info(METHODNAME, "iceVaccineGroupDTO.getVaccineGroup()=", iceVaccineGroupDTO.getVaccineGroup());
                        if (iceVaccineGroupDTO.getVaccineGroup() != null) {
                            logger.info(METHODNAME, "iceVaccineGroupDTO.getVaccineGroup().getCode()=", iceVaccineGroupDTO.getVaccineGroup().getCode());
                        }
                        for (IceVaccineComponentDTO iceVaccineComponentDTO : iceVaccineDTO.getVaccineComponents()) {
                            if (iceVaccineComponentDTO != null) {
                                IceVaccineGroupDTO groupResult = getIceVaccineGroupDTOFromComponent(iceVaccineComponentDTO, cdsVersionDTO);
                                logger.info(METHODNAME, "groupResult=", groupResult);
                                if (groupResult != null) {
                                    logger.info(METHODNAME, "groupResult.getVaccineGroupCode()=", groupResult.getVaccineGroupCode());
                                }
                                if (iceVaccineGroupDTO.equals(groupResult)) {
                                    logger.info(METHODNAME, "found conponent via group match: ", groupResult.getVaccineGroupCode());
                                    result = iceVaccineComponentDTO;
                                    break;
                                }
                            } else {
                                logger.info(METHODNAME, "iceVaccineComponentDTO is null");
                            }
                        }
                    } else {
                        logger.info(METHODNAME, "iceVaccineGroupDTO is null");
                    }
                } else {
                    logger.info(METHODNAME, "iceTestVaccineGroupRelDTOs size mismatch - will not be using to determine component");
                }
            }
        }
        logger.info(METHODNAME, "result=", result);
        return result;
    }

    /**
     * Get an IceVaccineGroupDTO based on a group code and cds version
     *
     * @param group
     * @param cdsVersionDTO
     * @return
     * @throws MtsException
     * @throws NotFoundException
     */
    public static IceVaccineGroupDTO getIceVaccineGroupDTO(String group, CdsVersionDTO cdsVersionDTO) throws MtsException,
            NotFoundException {
        final String METHODNAME = "getIceVaccineGroupDTO ";
        logger.info(METHODNAME, "group: ", group, " - cdsVersion: ", cdsVersionDTO);
        IceVaccineGroupDTO result = null;
        if (group != null && cdsVersionDTO != null) {
            try {
                CacheKey key = new CacheKey();
                key.setCode(group);
                key.setCdsVersionDTO(cdsVersionDTO);
                result = groupCache.get(key);

            } catch (ExecutionException e) {
                logger.error(e);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return result;

    }

    /**
     * Get an IceVaccineGroupDTO based on a vaccine component
     *
     * @param iceVaccineComponentDTO
     * @param cdsVersionDTO
     * @return
     * @throws MtsException
     * @throws NotFoundException
     */
    public static IceVaccineGroupDTO getIceVaccineGroupDTOFromComponent(IceVaccineComponentDTO iceVaccineComponentDTO, CdsVersionDTO cdsVersionDTO) throws MtsException,
            NotFoundException {
        final String METHODNAME = "getIceVaccineGroupDTOFromComponent ";
        logger.info(METHODNAME, "iceVaccineComponentDTO: ", iceVaccineComponentDTO, " - cdsVersion: ", cdsVersionDTO);
        IceVaccineGroupDTO result = null;
        if (iceVaccineComponentDTO != null && cdsVersionDTO != null) {
            try {
                CacheKey key = new CacheKey();
                key.setIceVaccineComponentDTO(iceVaccineComponentDTO);
                key.setCdsVersionDTO(cdsVersionDTO);
                result = componentCache.get(key);
            } catch (ExecutionException e) {
                logger.error(e);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return result;
    }

    private static class CacheKey {

        private String code;
        private IceVaccineComponentDTO iceVaccineComponentDTO;
        private CdsVersionDTO cdsVersionDTO;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public IceVaccineComponentDTO getIceVaccineComponentDTO() {
            return iceVaccineComponentDTO;
        }

        public void setIceVaccineComponentDTO(IceVaccineComponentDTO iceVaccineComponentDTO) {
            this.iceVaccineComponentDTO = iceVaccineComponentDTO;
        }

        public CdsVersionDTO getCdsVersionDTO() {
            return cdsVersionDTO;
        }

        public void setCdsVersionDTO(CdsVersionDTO cdsVersionDTO) {
            this.cdsVersionDTO = cdsVersionDTO;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 83 * hash + (this.code != null ? this.code.hashCode() : 0);
            hash = 83 * hash + (this.cdsVersionDTO != null ? this.cdsVersionDTO.hashCode() : 0);
            hash = 83 * hash + (this.iceVaccineComponentDTO != null ? this.iceVaccineComponentDTO.hashCode() : 0);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final CacheKey other = (CacheKey) obj;
            if ((this.code == null) ? (other.code != null) : !this.code.equals(other.code)) {
                return false;
            }
            if ((this.cdsVersionDTO == null) ? (other.cdsVersionDTO != null) : !this.cdsVersionDTO.equals(other.cdsVersionDTO)) {
                return false;
            }
            if ((this.iceVaccineComponentDTO == null) ? (other.iceVaccineComponentDTO != null) : !this.iceVaccineComponentDTO.equals(other.iceVaccineComponentDTO)) {
                return false;
            }
            return true;
        }

    }

}
