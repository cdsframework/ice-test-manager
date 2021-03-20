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
package org.cdsframework.security;

import org.cdsframework.dto.SessionDTO;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.util.LogUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.application.Mts;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.enumeration.LogLevel;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.joda.time.Instant;
import org.joda.time.Interval;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@SessionScoped
public class UserSession implements Serializable {

    private static final long serialVersionUID = -122873379099626458L;

    @Inject
    private Mts mts;

    private final static LogUtils logger = LogUtils.getLogger(UserSession.class);
    private UserDTO userDTO;
    private SessionDTO sessionDTO;
    private final Map<Integer, Future<String>> futureJobs = new HashMap<Integer, Future<String>>();
    private Instant lastInstant = new Instant();

    public UserSession() {
        final String METHODNAME = "UserSession Constructor ";
        logger.logInit(METHODNAME);
    }

    @PostConstruct
    public void postConstructor() {
        final String METHODNAME = "postConstructor ";
        userDTO = new UserDTO();
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public boolean isAuthenticated() {
        final String METHODNAME = "isAuthenticated ";
        boolean result = false;
        long start = System.nanoTime();
        try {
            if (sessionDTO != null) {
                Instant currentInstant = new Instant();
                Interval instantInterval = new Interval(lastInstant, currentInstant);
                long standardSeconds = instantInterval.toDuration().getStandardSeconds();
                logger.debug(METHODNAME, "standardSeconds duration=", standardSeconds);
                if (standardSeconds > 30) {
                    lastInstant = currentInstant;
                    SessionDTO checkedSessionDTO = mts.getGeneralMGR().findByPrimaryKey(sessionDTO, mts.getSession(), new PropertyBagDTO());
                    logger.info(METHODNAME, "checkedSessionDTO=", checkedSessionDTO);
                    if (checkedSessionDTO != null) {
                        result = true;
                    }
                } else {
                    result = true;
                }
            }
        } catch (MtsException e) {
            logger.error(METHODNAME, e);
        } catch (ValidationException e) {
            logger.error(METHODNAME, e);
        } catch (NotFoundException e) {
            logger.error(METHODNAME, "Session is expired or missing!");
        } catch (AuthenticationException e) {
            logger.error(METHODNAME, e);
        } catch (AuthorizationException e) {
            logger.error(METHODNAME, e);
        }
        finally {
            logger.logDuration(LogLevel.DEBUG, METHODNAME, start);                                                                            
        }
        return result;
    }

    public void invalidate() {
        try {
            SessionUtil.getSession().invalidate();
        } catch (IllegalArgumentException e) {
            logger.warn("session already invalidated!");
        }
    }

    public SessionDTO getSessionDTO() {
        return sessionDTO;
    }

    public void setSessionDTO(SessionDTO sessionDTO) {
        this.sessionDTO = sessionDTO;

        // Apply user properties
        userDTO = sessionDTO.getUserDTO();
        userDTO.setPasswordHash(null);
    }

    public Map<Integer, Future<String>> getFutureJobs() {
        return futureJobs;
    }

    public void registerFutureJob(Integer jobId, Future<String> futureJob) {
        futureJobs.put(jobId, futureJob);
    }
}
