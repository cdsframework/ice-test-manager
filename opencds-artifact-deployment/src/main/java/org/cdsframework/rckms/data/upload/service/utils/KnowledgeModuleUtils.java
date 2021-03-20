package org.cdsframework.rckms.data.upload.service.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sdn
 */
public class KnowledgeModuleUtils {

    public static String getKmConfig(String entityId, String businessId, String version) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> "
                + "<ns2:knowledgeModules xsi:schemaLocation=\"org.opencds.dss.config_rest.v1_0 ../../../../../../opencds-parent/opencds-config/opencds-config-schema/src/main/resources/schema/OpenCDSConfigRest.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns2=\"org.opencds.dss.config_rest.v1_0\" xmlns:ns3=\"org.opencds.dss.config.v1_0\">\n"
                + "<knowledgeModule> "
                + "<identifier scopingEntityId=\"" + entityId + "\" businessId=\"" + businessId + "\" version=\"" + version + "\" /> "
                + "<status>APPROVED</status> "
                + "<executionEngine>org.opencds.service.drools.v55.DroolsAdapter</executionEngine> "
                + "<semanticSignifierId scopingEntityId=\"org.opencds.vmr\" businessId=\"VMR\" version=\"1.0\" /> "
                + "<conceptDeterminationMethods> "
                + "<primaryCDM code=\"RCKMS0000\" codeSystem=\"2.16.840.1.113883.3.795.5.4.12.5.1\" version=\"1.0\"/> "
                + "</conceptDeterminationMethods> "
                + "<package> "
                + "<packageType>PKG</packageType> "
                + "<packageId>" + entityId + "^RCKMS^" + version + ".pkg</packageId> "
                + "<preload>true</preload> "
                + "</package> "
                + "<primaryProcess>RCKMSReportingProcess</primaryProcess> "
                + "<timestamp>" + getTimestamp() + "</timestamp> "
                + "<userId>system</userId> "
                + "</knowledgeModule>\n"
                + "</ns2:knowledgeModules>\n";

    }

    public static String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String timestamp = null;
        try {
            timestamp = sdf.format(new Date());

        } catch (Exception e) {
            e.printStackTrace();

        }
        return timestamp;
    }

}
