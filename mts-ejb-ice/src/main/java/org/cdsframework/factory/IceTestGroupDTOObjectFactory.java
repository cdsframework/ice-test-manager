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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.cdsframework.dto.IceTestGroupDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.ejb.bo.IceTestGroupBO;
import org.cdsframework.enumeration.DTOState;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.group.Add;
import org.cdsframework.util.AuthenticationUtils;
import org.cdsframework.util.DTOUtils;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class IceTestGroupDTOObjectFactory {

    private final static LogUtils logger = LogUtils.getLogger(IceTestGroupDTOObjectFactory.class);
    @EJB
    private IceTestGroupBO iceTestGroupBO;

    private final LoadingCache<CacheKey, IceTestGroupDTO> groupCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<CacheKey, IceTestGroupDTO>() {
                @Override
                public IceTestGroupDTO load(CacheKey key) throws ValidationException, MtsException, NotFoundException, AuthenticationException, AuthorizationException, ConstraintViolationException {
                    IceTestGroupDTO result = null;
                    IceTestGroupDTO testGroupDTO = new IceTestGroupDTO();
                    testGroupDTO.setName(key.getName());
                    testGroupDTO.setSuiteId(key.getSuiteId());
                    testGroupDTO.setIgnore(false);
                    try {
                        result = iceTestGroupBO.findByQueryMain(testGroupDTO, IceTestGroupDTO.ByTestSuiteIdName.class, new ArrayList<Class>(), AuthenticationUtils.getInternalSessionDTO(), new PropertyBagDTO());
                    } catch (NotFoundException e) {
                        // do nothing
                    }

                    if (result == null) {
                        DTOUtils.setDTOState(testGroupDTO, DTOState.NEW);
                        result = iceTestGroupBO.addMainNew(testGroupDTO, Add.class, AuthenticationUtils.getInternalSessionDTO(), new PropertyBagDTO());
                    }
                    return result;
                }
            });

    public IceTestGroupDTO findOrCreateTestGroupDTO(String group, String tesSuiteId, SessionDTO sessionDTO)
            throws ValidationException, MtsException, AuthenticationException, AuthorizationException, NotFoundException,
            ConstraintViolationException {
        IceTestGroupDTO result;
        CacheKey key = new CacheKey();
        key.setName(group);
        key.setSuiteId(tesSuiteId);
        try {
            result = groupCache.get(key);
        } catch (ExecutionException e) {
            throw new MtsException(e.getMessage(), e);
        }
        return result;
    }

    private class CacheKey {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String suiteId;

        public String getSuiteId() {
            return suiteId;
        }

        public void setSuiteId(String suiteId) {
            this.suiteId = suiteId;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 83 * hash + (this.name != null ? this.name.hashCode() : 0);
            hash = 83 * hash + (this.suiteId != null ? this.suiteId.hashCode() : 0);
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
            if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
                return false;
            }
            if ((this.suiteId == null) ? (other.suiteId != null) : !this.suiteId.equals(other.suiteId)) {
                return false;
            }
            return true;
        }

    }
}
