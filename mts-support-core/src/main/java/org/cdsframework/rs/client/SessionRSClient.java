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
package org.cdsframework.rs.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.cdsframework.client.RSClient;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.rs.exception.mapper.ErrorMessage;
import org.cdsframework.rs.support.CoreRsConstants;
import org.cdsframework.rs.utils.CommonRsUtils;

/**
 * Jersey REST client generated for REST resource:SecurityRestService
 * [security]<br>
 * USAGE:
 * <pre>
 *        SecurityRestServiceClient client = new SecurityRestServiceClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author HLN Consulting, LLC
 */
public class SessionRSClient extends RSClient {
    private static final String DEFAULT_URI = "http://localhost:8080/mts-rs-core";

    public SessionRSClient() {
        this(DEFAULT_URI);
    }
    
    public SessionRSClient(String baseURI) {
        this(baseURI, false);
    }
    
    public SessionRSClient(String baseURI, boolean loggingFilter) {
        this(baseURI, loggingFilter, JsonInclude.Include.NON_NULL);
    }    
    
    public SessionRSClient(String baseURI, boolean loggingFilter, JsonInclude.Include jsonInclude) {
        super(baseURI, CoreRsConstants.SESSION_RS_ROOT, loggingFilter, false, false, jsonInclude);
    }        
    
    /* legacy will be removed */
    public String login(String username, String password, String applicationName) 
            throws MtsException, AuthenticationException, AuthorizationException, ConstraintViolationException, ValidationException, NotFoundException {
        Form form = new Form();    
        form.param("username", username);
        form.param("password", password);
        form.param("applicationName", applicationName);
        return login(form);
    }
    
    public String login(Form form) throws MtsException, AuthenticationException, AuthorizationException, ConstraintViolationException, ValidationException, NotFoundException {
        Response response = getWebTarget()
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);
        String sessionId = null;

        if (!(response.getStatus() == Response.Status.OK.getStatusCode())) {
            CommonRsUtils.throwException(response.readEntity(ErrorMessage.class));
        }
        else {
            sessionId = response.readEntity(String.class);
        }
        return sessionId;
    }

    public String getProxiedUserSession(String sessionId, String userId) throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        String proxySessionId = null;
        List<Object> sessionKey = new ArrayList<Object>();
        sessionKey.add(sessionId);

        Response response = getWebTarget().path(getPath(sessionKey))
                .request(MediaType.TEXT_PLAIN)
                .post(javax.ws.rs.client.Entity.entity(userId, javax.ws.rs.core.MediaType.TEXT_PLAIN), Response.class);
        if (!(response.getStatus() == Response.Status.OK.getStatusCode())) {
            CommonRsUtils.throwException(response.readEntity(ErrorMessage.class));
        }
        else {
            proxySessionId = response.readEntity(String.class);
        }
        return proxySessionId;
    }
    
    public boolean logout(String sessionId) throws ConstraintViolationException, ValidationException, NotFoundException, AuthenticationException, AuthorizationException, MtsException {
        Response response = getWebTarget()
                .path(MessageFormat.format("{0}", new Object[]{sessionId}))
                .request()
                .delete(Response.class);
        boolean loggedOut = false;
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            loggedOut = response.readEntity(Boolean.class);
        }
        else {
            CommonRsUtils.throwException(response.readEntity(ErrorMessage.class));
        }
        return loggedOut;
        
    }    

    public boolean isSessionValid(String sessionId) {
        Response response = getWebTarget()
                .path(MessageFormat.format("{0}", new Object[]{sessionId}))
                .request()
                .get(Response.class);
        boolean sessionValid = false;
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            sessionValid = response.readEntity(Boolean.class);
        }
        return sessionValid;
        
        
    }
    
    
}
