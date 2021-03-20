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
package org.cdsframework.client;

import org.cdsframework.rs.support.CoreRsConstants;

/**
 *
 * @author HLN Consulting, LLC
 */
public class RSClientFactory {
    private String baseURI;
    private boolean loggingFilter;
    private boolean gzipSupport;
    
    /**
     *
     * @param baseURI the baseURI of the service i.e; http://hostname:port/archivefilename
     */
    public RSClientFactory(String baseURI) {
        this(baseURI, false);
    }

    /**
     *
     * @param baseURI the baseURI of the service i.e; http://hostname:port/archivefilename
     * @param loggingFilter false is the default, true turns on logging
     */
    public RSClientFactory(String baseURI, boolean loggingFilter) {
        this(baseURI, loggingFilter, false);
    }
    
    /**
     *
     * @param baseURI the baseURI of the service i.e; http://hostname:port/archivefilename
     * @param loggingFilter true turns on logging
     * @param gzipSupport false is the default, true turns on GZIP decompression, 
     *                    RS Service must be configured to return GZIP responses, 
     *                    To configure RS tier, see rs-core.properties GZIP_SUPPORT=true
     */
    public RSClientFactory(String baseURI, boolean loggingFilter, boolean gzipSupport) {
        this.baseURI = baseURI;
        this.loggingFilter = loggingFilter;
        this.gzipSupport = gzipSupport;
    }
    
    /**
     *
     * @return
     */
    public RSClient getRsClient() {
        return new RSClient(baseURI, CoreRsConstants.GENERAL_RS_ROOT, loggingFilter, gzipSupport);
    }
    
    /**
     *
     * @param rsRoot The root URI of the RS Service, typically this is the lowercase DTO name pluralized
     *               NOTE: A dedicated DTO typed service must be registered in the ApplicationConfig
     *                     otherwise 404 is likely
     * @return RSClient
     */
    public RSClient getRsClient(String rsRoot) {
        return new RSClient(baseURI, rsRoot, loggingFilter, false, gzipSupport);
    }
    
}
