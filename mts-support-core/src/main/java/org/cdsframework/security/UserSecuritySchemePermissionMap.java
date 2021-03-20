/**
 * The MTS support core project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
package org.cdsframework.security;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.cdsframework.base.BaseDTO;
import org.cdsframework.dto.SecurityPermissionDTO;
import org.cdsframework.dto.SecuritySchemeDTO;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.enumeration.PermissionType;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.util.ClassUtils;
import org.cdsframework.util.DTOUtils;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
public class UserSecuritySchemePermissionMap implements Serializable {

    private final static LogUtils logger = LogUtils.getLogger(UserSecuritySchemePermissionMap.class);
    private static final long serialVersionUID = 1541225800288384887L;
    final private Map<String, List<PermissionType>> permissionAllowMap = new HashMap<String, List<PermissionType>>();
    final private Map<String, List<PermissionType>> permissionDenyMap = new HashMap<String, List<PermissionType>>();
    private List<SecuritySchemeDTO> securitySchemeDTOs = new ArrayList<SecuritySchemeDTO>();

    public UserSecuritySchemePermissionMap() {
    }

    public UserSecuritySchemePermissionMap(UserDTO userDTO) throws MtsException {
    	this(userDTO.getSecuritySchemeDTOs());
    }
    
    public UserSecuritySchemePermissionMap(List<SecuritySchemeDTO> securitySchemes) throws MtsException {
    	
        for (SecuritySchemeDTO securitySchemeDTO : securitySchemes) {

          securitySchemeDTOs.add(securitySchemeDTO);
//          for (SecurityPermissionDTO securityPermissionDTO : securitySchemeDTO.getSecurityPermissionDTOs()) {
//              logger.info(METHODNAME, "securityPermissionDTO=", securityPermissionDTO.getPermissionClass(), "; ", securityPermissionDTO.getPermissionType());
//          }
      }
      this.generatePermissionMap();
    }

    /**
     * Get the value of securitySchemeDTOs
     *
     * @return the value of securitySchemeDTOs
     */
    public List<SecuritySchemeDTO> getSecuritySchemeDTOs() {
        return securitySchemeDTOs;
    }

    /**
     * Set the value of securitySchemeDTOs
     *
     * @param securitySchemeDTOs new value of securitySchemeDTOs
     */
    public void setSecuritySchemeDTOs(List<SecuritySchemeDTO> securitySchemeDTOs) {
        this.securitySchemeDTOs = securitySchemeDTOs;
    }

    /**
     * Get the value of permissionAllowMap
     *
     * @return the value of permissionAllowMap
     */
    public Map<String, List<PermissionType>> getPermissionAllowMap() {
        return permissionAllowMap;
    }

    /**
     * Get the value of permissionDenyMap
     *
     * @return the value of permissionDenyMap
     */
    public Map<String, List<PermissionType>> getPermissionDenyMap() {
        return permissionDenyMap;
    }
    
    /**
     * Validate that the permission map does not contain any conflicting allow/deny permissions
     * @throws ValidationException
     */
    public void validatePermissions() throws ValidationException {
    	
		for (Entry<String, List<PermissionType>> entry : permissionDenyMap.entrySet()) {
			if (permissionAllowMap.containsKey(entry.getKey())) {
				List<PermissionType> allowPerms = permissionAllowMap.get(entry.getKey());
				if (allowPerms != null) {
					List<PermissionType> denyPerms = entry.getValue();
					for (PermissionType permission : denyPerms) {
						if (allowPerms.contains(permission)) {
							throw new ValidationException(String.format("Conflicting permissions on DTO: %s, %s\n", entry.getKey(), permission.name()));
						}
					}
				}
			}
		}
    }

    private void generatePermissionMap() throws MtsException {
        final String METHODNAME = "generatePermissionMap ";
//        long start = System.nanoTime();
//        logger.info("generatePermissionMap - here we go for: ", userDTO.getUsername());
        for (SecuritySchemeDTO securitySchemeDTO : securitySchemeDTOs) {
//            logger.info("generatePermissionMap Processing: ", securitySchemeDTO.getSchemeName(), " for ", userDTO.getUsername());
            for (SecurityPermissionDTO securityPermissionDTO : securitySchemeDTO.getSecurityPermissionDTOs()) {
                String dtoClassName = securityPermissionDTO.getPermissionClass();
                PermissionType permissionType = securityPermissionDTO.getPermissionType();
                boolean cascade = securityPermissionDTO.isCascade();
                boolean deny = securityPermissionDTO.isDeny();
                if (deny) {
                    setPermMap(new ArrayList<String>(), permissionDenyMap, dtoClassName, permissionType, cascade);
//                    logger.info("    Adding deny perm: ", userDTO.getUsername(), "/", securitySchemeDTO.getSchemeName(), "/", dtoClassName, "/", permissionType.toString(), "/", cascade);
                } else {
                    setPermMap(new ArrayList<String>(), permissionAllowMap, dtoClassName, permissionType, cascade);
//                    logger.info("    Adding allow perm: ", userDTO.getUsername(), "/", securitySchemeDTO.getSchemeName(), "/" + dtoClassName, "/", permissionType.toString(), "/", cascade);
                }
            }
        }
//        logger.info(METHODNAME, "duration(ms): ", ((System.nanoTime() - start) / 1000000.0));
    }

    private void setPermMap(
            List<String> processedList,
            Map<String, List<PermissionType>> permissionMap,
            String dtoClassName,
            PermissionType permissionType,
            boolean cascade) {
        final String METHODNAME = "setPermMap ";
//        long start = System.nanoTime();
        processedList.add(dtoClassName);
        List<PermissionType> permissions = permissionMap.get(dtoClassName);
        if (permissions == null) {
            permissions = new ArrayList<PermissionType>();
            permissionMap.put(dtoClassName, permissions);
        }
        if (!permissions.contains(permissionType)) {
            permissions.add(permissionType);
        }
//        logger.info(METHODNAME, "cascade=", cascade);
        if (cascade) {
            Class<? extends BaseDTO> dtoClass = null;
            try {
                dtoClass = ClassUtils.classForName(dtoClassName);
            } catch (NotFoundException e) {
                logger.warn(METHODNAME, e != null ? e.getMessage() : null);
            }
            if (dtoClass != null) {
                for (Class<? extends BaseDTO> childDtoClass : DTOUtils.getParentChildRelationshipMapByDTO(dtoClass).keySet()) {
                    if (!processedList.contains(childDtoClass.getCanonicalName())) {
                        setPermMap(processedList, permissionMap, childDtoClass.getCanonicalName(), permissionType, cascade);
                    }
                }
                for (Field referenceDtoField : DTOUtils.getReferenceDTOs(dtoClass)) {

                    Class<?> type = referenceDtoField.getType();
                    if (!processedList.contains(type.getCanonicalName())) {
                        setPermMap(processedList, permissionMap, type.getCanonicalName(), permissionType, cascade);
                    }
                }
            } else {
                logger.error(METHODNAME, "dtoClass is null for: ", dtoClassName);
            }
        }
//        logger.info(METHODNAME, "duration(ms): ", ((System.nanoTime() - start) / 1000000.0));
    }
}
