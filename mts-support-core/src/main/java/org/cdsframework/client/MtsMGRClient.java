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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cdsframework.client;

import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.cdsframework.ejb.local.GeneralMGRInterface;
import org.cdsframework.ejb.local.GeneralMGRLocal;
import org.cdsframework.ejb.local.SecurityMGRInterface;
import org.cdsframework.ejb.local.SecurityMGRLocal;
import org.cdsframework.ejb.remote.GeneralMGRRemote;
import org.cdsframework.ejb.remote.SecurityMGRRemote;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.rs.support.CoreConfiguration;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author martin
 */
public class MtsMGRClient {
    private static LogUtils logger = LogUtils.getLogger(MtsMGRClient.class);
    private static String GENERAL_REMOTE_MGR;
    private static String SECURITY_REMOTE_MGR;    
    private static String GENERAL_LOCAL_MGR;
    private static String SECURITY_LOCAL_MGR;
    private final static String factoryInitial = "com.sun.enterprise.naming.SerialInitContextFactory";
    private final static String factoryUrlPkgs = "com.sun.enterprise.naming";
    private final static String factoryState = "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl";
    private final static String host;
    private final static String port;
    static {
        final String METHODNAME = "static constructor ";
        logger.info(METHODNAME, "acquiring InitialContext");
        host = CoreConfiguration.getMtsHost();
        port = CoreConfiguration.getMtsPort();                
        String jndiRoot = CoreConfiguration.getMtsJndiRoot() + "/mts-ejb-core/";        
        // Construct the remote/local strings
        String generalMGRRemote = GeneralMGRRemote.class.getCanonicalName();
        String generalMGRLocal = GeneralMGRLocal.class.getCanonicalName();
        String securityMGRRemote = SecurityMGRRemote.class.getCanonicalName();
        String securityMGRLocal = SecurityMGRLocal.class.getCanonicalName();
        String generalMGR = "GeneralMGR";
        String securityMGR = "SecurityMGR";
        logger.info(METHODNAME, "constructing remote/local portable names");
        GENERAL_REMOTE_MGR = jndiRoot + generalMGR + "!"+ generalMGRRemote;
        GENERAL_LOCAL_MGR = jndiRoot + generalMGR + "!"+ generalMGRLocal;
        SECURITY_REMOTE_MGR = jndiRoot + securityMGR + "!"+ securityMGRRemote;
        SECURITY_LOCAL_MGR = jndiRoot + securityMGR + "!"+ securityMGRLocal;
        // Log remote/local interfaces
        logger.info(METHODNAME, "GENERAL_REMOTE_MGR=", GENERAL_REMOTE_MGR, 
                " GENERAL_LOCAL_MGR=", GENERAL_LOCAL_MGR, 
                " SECURITY_REMOTE_MGR=", SECURITY_REMOTE_MGR, 
                " SECURITY_LOCAL_MGR=", SECURITY_LOCAL_MGR);
    }    
    
    public static GeneralMGRInterface getGeneralMGR() throws MtsException {
        return getGeneralMGR(true);
    }      
    
    public static GeneralMGRInterface getGeneralMGR(boolean useRemote) throws MtsException {
        if (useRemote) {
            return MtsMGRClient.getGeneralMGRRemote();
        }
        else {
            return MtsMGRClient.getGeneralMGRLocal();
        }
    }      
    
    private static GeneralMGRRemote getGeneralMGRRemote() throws MtsException {
        final String METHODNAME = "getGeneralMGRRemote "; 
        GeneralMGRRemote generalMGRInterface = null;
        try {
            generalMGRInterface = (GeneralMGRRemote) getInitialContext().lookup(GENERAL_REMOTE_MGR);
        } catch (NamingException ex) {
            String errorMessage = "A " + ex.getClass().getSimpleName() + " has occurred; Message: " + ex.getMessage();
            logger.error(METHODNAME, errorMessage);
            throw new MtsException(errorMessage, ex);
        }
        return generalMGRInterface;
    }

    private static GeneralMGRLocal getGeneralMGRLocal() throws MtsException {
        final String METHODNAME = "getGeneralMGRLocal "; 
        GeneralMGRLocal generalMGRInterface = null;
        try {
            generalMGRInterface = (GeneralMGRLocal) new InitialContext().lookup(GENERAL_LOCAL_MGR);
        } catch (NamingException ex) {
            String errorMessage = "A " + ex.getClass().getSimpleName() + " has occurred; Message: " + ex.getMessage();
            logger.error(METHODNAME, errorMessage);
            throw new MtsException(errorMessage, ex);
        }
        return generalMGRInterface;
    }

    public static SecurityMGRInterface getSecurityMGR() throws MtsException {
        return MtsMGRClient.getSecurityMGR(true);
    }    
    
    public static SecurityMGRInterface getSecurityMGR(boolean useRemote) throws MtsException {
        if (useRemote) {
            return MtsMGRClient.getSecurityMGRRemote();
        }
        else {
            return MtsMGRClient.getSecurityMGRLocal();
        }
    }    
    
    private static SecurityMGRRemote getSecurityMGRRemote() throws MtsException {
        final String METHODNAME = "getSecurityMGRRemote "; 
        SecurityMGRRemote securityMGRInterface = null;
        try {
            securityMGRInterface = (SecurityMGRRemote) getInitialContext().lookup(SECURITY_REMOTE_MGR);
        } catch (NamingException ex) {
            String errorMessage = "A " + ex.getClass().getSimpleName() + " has occurred; Message: " + ex.getMessage();
            logger.error(METHODNAME, errorMessage);
            throw new MtsException(errorMessage, ex);
        }
        return securityMGRInterface;
    }        
    
    private static SecurityMGRLocal getSecurityMGRLocal() throws MtsException {
        final String METHODNAME = "getSecurityMGRLocal "; 
        SecurityMGRLocal securityMGRInterface = null;
        try {
            securityMGRInterface = (SecurityMGRLocal) new InitialContext().lookup(SECURITY_LOCAL_MGR);
            
        } catch (NamingException ex) {
            String errorMessage = "A " + ex.getClass().getSimpleName() + " has occurred; Message: " + ex.getMessage();
            logger.error(METHODNAME, errorMessage);
            throw new MtsException(errorMessage, ex);
        }
        return securityMGRInterface;
    }         

    public static InitialContext getInitialContext() throws NamingException {
        Properties properties = new Properties();
        properties.put("org.omg.CORBA.ORBInitialHost", host);
        properties.put("org.omg.CORBA.ORBInitialPort", port);
        properties.put("java.naming.factory.initial", factoryInitial);
        properties.put("java.naming.factory.url.pkgs", factoryUrlPkgs);
        properties.put("java.naming.factory.state", factoryState);
        InitialContext initialContext = new InitialContext(properties);
        return initialContext;
    }    
}
