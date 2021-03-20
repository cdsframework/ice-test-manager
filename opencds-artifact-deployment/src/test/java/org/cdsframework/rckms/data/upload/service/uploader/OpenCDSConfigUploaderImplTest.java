package org.cdsframework.rckms.data.upload.service.uploader;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.cdsframework.rckms.data.upload.service.utils.KnowledgeModuleUtils;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * @author Mark Curtis
 */
public class OpenCDSConfigUploaderImplTest {

    private String host;
    private int port;
    private String userName;
    private String password;
    private String cdmEndpoint;
    private String kmEndpoint;
    private String entityId;
    private String businessId;
    private String version;

    @Before
    public void setUp() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/test/resources/test.properties"));
            host = properties.getProperty("host");
            port = Integer.parseInt(properties.getProperty("port"));
            userName = properties.getProperty("userName");
            password = properties.getProperty("password");
            cdmEndpoint = properties.getProperty("cdmEndpoint");
            kmEndpoint = properties.getProperty("kmEndpoint");
            entityId = properties.getProperty("entityId");
            businessId = properties.getProperty("businessId");
            version = properties.getProperty("version");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getCdm() throws Exception {

        OpenCDSConfigUploader openCDSConfigUploader = new OpenCDSConfigUploaderImpl(host, port, userName, password, cdmEndpoint, kmEndpoint);
        Response response = openCDSConfigUploader.getCdm(null);

        assertNotNull(response);
        System.out.println(response.getXml());
        assertEquals("Response code is not 200 as it should be", 200, response.getCode());
        assertNotNull("Response xml is null", response.getXml());

    }

    @Test
    public void getCdmId() throws Exception {

        // setup
        String code = "C36";
        String displayName = "Best available OpenCDS concept determination method";
        String codeSystem = "2.16.840.1.113883.3.795.12.1.1";
        String cdmVersion = "1.0";

        OpenCDSConfigUploader openCDSConfigUploader = new OpenCDSConfigUploaderImpl(host, port, userName, password, cdmEndpoint, kmEndpoint);

        String xml = getCdmXml(code, displayName, codeSystem, cdmVersion);
        System.out.println("xml=\n\n" + xml);

        // get cdm
        String cdmId = codeSystem + "^" + code + "^" + cdmVersion;
        System.out.println("cdmId=" + cdmId);

        Response response = openCDSConfigUploader.getCdm(cdmId);

        System.out.println("xml response=\n\n" + response.getXml());

        // check results
        assertNotNull(response);
        assertEquals("Response code is not 200 as it should be", 200, response.getCode());
        assertNotNull("Response xml is null", response.getXml());

    }

    @Test
    public void addCdm() throws Exception {

        String code = "RCKMS0006";
        String displayName = "RCKMS CDM";
        String codeSystem = "2.16.840.1.113883.3.795.12.1.1";
        String cdmVersion = "1.0";

        OpenCDSConfigUploader openCDSConfigUploader = new OpenCDSConfigUploaderImpl(host, port, userName, password, cdmEndpoint, kmEndpoint);

        String xml = getCdmXml(code, displayName, codeSystem, cdmVersion);
        System.out.println("xml=\n\n" + xml);

        Response response = openCDSConfigUploader.addCdm(xml);
        System.out.println("response=" + response);

        assertNotNull(response);
        assertEquals("Return code should equal 204", 204, response.getCode());

    }

    @Test
    public void addCdmFromFile() throws Exception {

        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/cdm.xml")) {

            System.out.println("Total file size to read (in bytes) : " + fileInputStream.available());

            int content;
            while ((content = fileInputStream.read()) != -1) {
                // convert to char and display it
                stringBuilder.append((char) content);
            }

            String xml = stringBuilder.toString();

            OpenCDSConfigUploader openCDSConfigUploader = new OpenCDSConfigUploaderImpl(host, port, userName, password, cdmEndpoint, kmEndpoint);

            System.out.println("xml=\n" + xml);

            Response response = openCDSConfigUploader.addCdm(xml);
            System.out.println("response=" + response);

            assertNotNull(response);
            assertEquals("Return code should equal 204", 204, response.getCode());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void deleteCdm() throws Exception {

        String code = "RCKMS0006";
        String codeSystem = "2.16.840.1.113883.3.795.12.1.1";
        String cdmVersion = "1.0";

        OpenCDSConfigUploader openCDSConfigUploader = new OpenCDSConfigUploaderImpl(host, port, userName, password, cdmEndpoint, kmEndpoint);

        String cdmId = codeSystem + "^" + code + "^" + cdmVersion;
        System.out.println("cdmId=" + cdmId);

        Response response = openCDSConfigUploader.deleteCdm(cdmId);
        System.out.println("response=" + response);

        assertNotNull(response);
        assertEquals("Return code should equal 204", 204, response.getCode());

    }

    @Test
    public void deleteCdmFromFile() throws Exception {

        String code = "RCKMS0000";
        String codeSystem = "2.16.840.1.113883.3.795.12.1.1";
        String cdmVersion = "1.0";

        OpenCDSConfigUploader openCDSConfigUploader = new OpenCDSConfigUploaderImpl(host, port, userName, password, cdmEndpoint, kmEndpoint);

        String cdmId = codeSystem + "^" + code + "^" + cdmVersion;
        System.out.println("cdmId=" + cdmId);

        Response response = openCDSConfigUploader.deleteCdm(cdmId);
        System.out.println("response=" + response);

        assertNotNull(response);
        assertEquals("Return code should equal 204", 204, response.getCode());

    }

    @Test
    public void addKnowledgeModuleFromFile() throws Exception {

        String kmId = entityId + "^" + businessId + "^" + version;
        System.out.println("kmId=" + kmId);

        String fileName = "gov.cdc.rckms.ut^RCKMS^1.0.0.pkg";
        System.out.println("fileName=" + fileName);

        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/" + fileName)) {

            System.out.println("Total file size to read (in bytes) : " + fileInputStream.available());

            int content;
            while ((content = fileInputStream.read()) != -1) {
                // convert to char and display it
                stringBuilder.append((char) content);
            }

            String pkg = stringBuilder.toString();

            System.out.println("pkg=\n" + pkg);

            byte[] bytes = pkg.getBytes();

            InputStream stream = new ByteArrayInputStream(bytes);

            OpenCDSConfigUploader openCDSConfigUploader = new OpenCDSConfigUploaderImpl(host, port, userName, password, cdmEndpoint, kmEndpoint);

            Response response = openCDSConfigUploader.addKnowledgeModule(kmId, stream, bytes.length);
            System.out.println("response=" + response);

            assertNotNull(response);
            assertEquals("Return code should equal 204", 204, response.getCode());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getKnowledgeModule() throws Exception {

        OpenCDSConfigUploader openCDSConfigUploader = new OpenCDSConfigUploaderImpl(host, port, userName, password, cdmEndpoint, kmEndpoint);

        String kmId = entityId + "^" + businessId + "^" + version;
        System.out.println("kmId=" + kmId);

        Response response = openCDSConfigUploader.getKnowledgeModule(kmId);
        System.out.println("response=" + response);

        assertNotNull(response);
        assertEquals("Return code should equal 200", 200, response.getCode());
        assertNotNull(response.getKnowledgeModule());
        System.out.println("response.getKnowledgeModule()=" + new String(response.getKnowledgeModule()));

    }

    @Test
    public void deleteKnowledgeModule() throws Exception {

        OpenCDSConfigUploader openCDSConfigUploader = new OpenCDSConfigUploaderImpl(host, port, userName, password, cdmEndpoint, kmEndpoint);

        String kmId = entityId + "^" + businessId + "^" + version;
        System.out.println("kmId=" + kmId);

        Response response = openCDSConfigUploader.deleteKnowledgeModule(kmId);
        System.out.println("response=" + response);

        assertNotNull(response);
        assertEquals("Return code should equal 204", 204, response.getCode());

    }

    @Test
    public void addKnowledgeModuleConfig() throws Exception {
        OpenCDSConfigUploader openCDSConfigUploader = new OpenCDSConfigUploaderImpl(host, port, userName, password, cdmEndpoint, kmEndpoint);

        String xml = KnowledgeModuleUtils.getKmConfig(entityId, businessId, version);
        System.out.println("xml=" + xml);

        Response response = openCDSConfigUploader.addKnowledgeModuleConfig(xml);
        System.out.println("response=" + response);

        assertNotNull(response);
        assertEquals("Return code should equal 204", 204, response.getCode());

    }

    @Test
    public void getKnowledgeModuleConfig() throws Exception {

        OpenCDSConfigUploader openCDSConfigUploader = new OpenCDSConfigUploaderImpl(host, port, userName, password, cdmEndpoint, kmEndpoint);

        String kmId = entityId + "^" + businessId + "^" + version;
        System.out.println("kmId=" + kmId);

        Response response = openCDSConfigUploader.getKnowledgeModuleConfig(kmId);
        System.out.println("response=" + response);

        assertNotNull(response);
        assertEquals("Return code should equal 204", 204, response.getCode());

    }

    @Test
    public void deleteKnowledgeModuleConfig() throws Exception {

        OpenCDSConfigUploader openCDSConfigUploader = new OpenCDSConfigUploaderImpl(host, port, userName, password, cdmEndpoint, kmEndpoint);

        String kmId = entityId + "^" + businessId + "^" + version;
        System.out.println("kmId=" + kmId);

        Response response = openCDSConfigUploader.deleteKnowledgeModuleConfig(kmId);
        System.out.println("response=" + response);

        assertNotNull(response);
        assertEquals("Return code should equal 204", 204, response.getCode());

    }

    private String getCdmXml(String code, String displayName, String codeSystem, String cdmVersion) {

        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<ns2:conceptDeterminationMethods xmlns:ns2=\"org.opencds.dss.config_rest.v1_0\">\n"
                + "    <conceptDeterminationMethod code=\"" + code + "\" codeSystem=\"" + codeSystem + "\" displayName=\"" + displayName + "\" version=\"" + cdmVersion + "\">\n"
                + "        <conceptMapping>\n"
                + "            <toConcept codeSystem=\"2.16.840.1.113883.3.795.12.1.1\" code=\"C30\" displayName=\"Male\"/>\n"
                + "            <fromConcepts codeSystem=\"2.16.840.1.113883.5.1\" codeSystemName=\"GENDER\">\n"
                + "                <concept code=\"M\" displayName=\"Male\"/>\n"
                + "            </fromConcepts>\n"
                + "        </conceptMapping>\n"
                + "        <timestamp>" + KnowledgeModuleUtils.getTimestamp() + "</timestamp>\n"
                + "        <userId>GENERATED</userId>\n"
                + "    </conceptDeterminationMethod>\n"
                + "</ns2:conceptDeterminationMethods>";
    }
}
