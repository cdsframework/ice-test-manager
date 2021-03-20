package org.cdsframework.rckms.data.upload.service.uploader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.cdsframework.util.LogUtils;

/**
 * @author Mark Curtis
 */
public class OpenCDSConfigUploaderImpl implements OpenCDSConfigUploader {

    private final String host;
    private final String cdmEndpoint;
    private final String kmEndpoint;
    private final int port;

    private final Header headerAccept = new BasicHeader("Accept", "application/xml");
    private final Header headerBytesAccept = new BasicHeader("Accept", "application/octet-stream");
    private final Header headerContentType = new BasicHeader("Content-Type", "application/xml");
    private final Header headerBytesContentType = new BasicHeader("Content-Type", "application/octet-stream");
    private final Header headerAuth;
    private final static LogUtils LOGGER = LogUtils.getLogger(OpenCDSConfigUploaderImpl.class);

    public OpenCDSConfigUploaderImpl(String host, int port, String username, String password, String cdmEndpoint, String kmEndpoint) {
        final String METHODNAME = "OpenCDSConfigUploaderImpl ";
        this.host = host;
        this.port = port;
        this.cdmEndpoint = cdmEndpoint;
        this.kmEndpoint = kmEndpoint;
        LOGGER.debug(METHODNAME, "host=", host);
        LOGGER.debug(METHODNAME, "port=", port);
        LOGGER.debug(METHODNAME, "username=", username);
        LOGGER.debug(METHODNAME, "cdmEndpoint=", cdmEndpoint);
        LOGGER.debug(METHODNAME, "kmEndpoint=", kmEndpoint);
        byte[] encodedBytes = Base64.encodeBase64((username + ":" + password).getBytes());
        headerAuth = new BasicHeader("Authorization", "Basic " + new String(encodedBytes));

    }

    @Override
    public Response addCdm(String cdmXml) throws URISyntaxException, UnsupportedEncodingException {
        final String METHODNAME = "addCdm ";
        LOGGER.debug(METHODNAME, "cdmXml=", cdmXml);
        Response response = put(cdmXml, cdmEndpoint);
        LOGGER.debug(METHODNAME, "response=", response);
        return response;
    }

    @Override
    public Response getCdm(String cdmId) throws URISyntaxException {
        final String METHODNAME = "getCdm ";
        LOGGER.debug(METHODNAME, "cdmId=", cdmId);
        Response response = get(cdmId, cdmEndpoint);
        LOGGER.debug(METHODNAME, "response=", response);
        return response;
    }

    @Override
    public Response deleteCdm(String cdmId) throws URISyntaxException {
        final String METHODNAME = "deleteCdm ";
        LOGGER.debug(METHODNAME, "cdmId=", cdmId);
        Response response = delete(cdmId, cdmEndpoint);
        LOGGER.debug(METHODNAME, "response=", response);
        return response;
    }

    @Override
    public Response addKnowledgeModule(String id, InputStream inputStream, long length) throws URISyntaxException {
        final String METHODNAME = "addKnowledgeModule ";

        LOGGER.debug(METHODNAME, "id=", id);
        HttpClient httpClient = HttpClientBuilder.create().build();
        URI uri = createUri("http", port, host, kmEndpoint + "/" + id + "/package");
        LOGGER.debug(METHODNAME, "uri=", uri);
        HttpPut putRequest = new HttpPut(uri);
        InputStreamEntity entity = new InputStreamEntity(inputStream, length);
        putRequest.setEntity(entity);
        setHttpHeader(putRequest, null, headerBytesContentType, headerAuth);
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(putRequest);
            LOGGER.debug(METHODNAME, "httpResponse=", httpResponse);
        } catch (IOException e) {
            LOGGER.error(e);
            return new Response(500, ResponseType.ERROR, "Error executing request");
        }
        Response response = new Response(httpResponse.getStatusLine().getStatusCode(), ResponseType.INFO, httpResponse.getStatusLine().getReasonPhrase());
        LOGGER.debug(METHODNAME, "response=", response);
        return response;

    }

    @Override
    public Response getKnowledgeModule(String id) throws URISyntaxException {
        final String METHODNAME = "getKnowledgeModule ";

        LOGGER.debug(METHODNAME, "id=", id);
        HttpClient httpClient = HttpClientBuilder.create().build();
        URI uri = createUri("http", port, host, kmEndpoint + "/" + id + "/package");
        LOGGER.debug(METHODNAME, "uri=", uri);
        HttpGet getRequest = new HttpGet(uri);
        setHttpHeader(getRequest, headerBytesAccept, null, headerAuth);
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(getRequest);
            LOGGER.debug(METHODNAME, "httpResponse=", httpResponse);
        } catch (IOException e) {
            LOGGER.error(e);
            return new Response(500, ResponseType.ERROR, "Error executing request");
        }
        byte[] km;
        try {
            km = getOutputBytes(httpResponse);
        } catch (IOException e) {
            LOGGER.error(e);
            return new Response(500, ResponseType.ERROR, "Error getting content");
        }
        Response response = new Response(httpResponse.getStatusLine().getStatusCode(), ResponseType.INFO, httpResponse.getStatusLine().getReasonPhrase());
        response.setKnowledgeModule(km);
        LOGGER.debug(METHODNAME, "response=", response);
        return response;

    }

    @Override
    public Response deleteKnowledgeModule(String id) throws URISyntaxException {
        final String METHODNAME = "deleteKnowledgeModule ";

        LOGGER.debug(METHODNAME, "id=", id);
        HttpClient httpClient = HttpClientBuilder.create().build();
        URI uri = createUri("http", port, host, kmEndpoint + "/" + id + "/package");
        LOGGER.debug(METHODNAME, "uri=", uri);
        HttpDelete deleteRequest = new HttpDelete(uri);
        setHttpHeader(deleteRequest, headerAccept, headerContentType, headerAuth);
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(deleteRequest);
            LOGGER.debug(METHODNAME, "httpResponse=", httpResponse);
        } catch (IOException e) {
            LOGGER.error(e);
            return new Response(500, ResponseType.ERROR, "Error executing request");
        }
        Response response = new Response(httpResponse.getStatusLine().getStatusCode(), ResponseType.INFO, httpResponse.getStatusLine().getReasonPhrase());
        LOGGER.debug(METHODNAME, "response=", response);
        return response;
    }

    @Override
    public Response updateKnowledgeModuleConfig(String kmConfig) {
        final String METHODNAME = "updateKnowledgeModuleConfig ";
        LOGGER.debug(METHODNAME, "kmConfig=", kmConfig);
        return null;
    }

    @Override
    public Response addKnowledgeModuleConfig(String kmConfig) throws URISyntaxException {
        final String METHODNAME = "addKnowledgeModuleConfig ";
        LOGGER.debug(METHODNAME, "kmConfig=", kmConfig);
        Response response = put(kmConfig, kmEndpoint);
        LOGGER.debug(METHODNAME, "response=", response);
        return response;
    }

    @Override
    public Response deleteKnowledgeModuleConfig(String kmId) throws URISyntaxException {
        final String METHODNAME = "deleteKnowledgeModuleConfig ";
        LOGGER.debug(METHODNAME, "kmId=", kmId);
        Response response = delete(kmId, kmEndpoint);
        LOGGER.debug(METHODNAME, "response=", response);
        return response;
    }

    @Override
    public Response getKnowledgeModuleConfig(String kmId) {
        final String METHODNAME = "getKnowledgeModuleConfig ";
        LOGGER.debug(METHODNAME, "kmId=", kmId);
        return null;
    }

    public Response put(String xml, String endpoint) throws URISyntaxException {
        final String METHODNAME = "put ";

        LOGGER.debug(METHODNAME, "endpoint=", endpoint);
        HttpClient httpClient = HttpClientBuilder.create().build();
        URI uri = createUri("http", port, host, endpoint);
        LOGGER.debug(METHODNAME, "uri=", uri);
        HttpPut putRequest = new HttpPut(uri);
        HttpEntity httpEntity = new StringEntity(xml, ContentType.APPLICATION_XML);
        putRequest.setEntity(httpEntity);
        setHttpHeader(putRequest, headerAccept, headerContentType, headerAuth);
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(putRequest);
            LOGGER.debug(METHODNAME, "httpResponse=", httpResponse);
        } catch (IOException e) {
            LOGGER.error(e);
            return new Response(500, ResponseType.ERROR, "Error executing request");
        }
        Response response = new Response(httpResponse.getStatusLine().getStatusCode(), ResponseType.INFO, httpResponse.getStatusLine().getReasonPhrase());
        LOGGER.debug(METHODNAME, "response=", response);
        return response;

    }

    public Response delete(String id, String endpoint) throws URISyntaxException {
        final String METHODNAME = "delete ";

        LOGGER.debug(METHODNAME, "id=", id);
        LOGGER.debug(METHODNAME, "endpoint=", endpoint);
        HttpClient httpClient = HttpClientBuilder.create().build();
        URI uri = createUri("http", port, host, endpoint + "/" + id);
        LOGGER.debug(METHODNAME, "uri=", uri);
        HttpDelete deleteRequest = new HttpDelete(uri);
        setHttpHeader(deleteRequest, headerAccept, headerContentType, headerAuth);
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(deleteRequest);
            LOGGER.debug(METHODNAME, "httpResponse=", httpResponse);
        } catch (IOException e) {
            LOGGER.error(e);
            return new Response(500, ResponseType.ERROR, "Error executing request");
        }
        Response response = new Response(httpResponse.getStatusLine().getStatusCode(), ResponseType.INFO, httpResponse.getStatusLine().getReasonPhrase());
        LOGGER.debug(METHODNAME, "response=", response);
        return response;

    }

    public Response get(String id, String endpoint) throws URISyntaxException {
        final String METHODNAME = "get ";

        LOGGER.debug(METHODNAME, "id=", id);
        LOGGER.debug(METHODNAME, "endpoint=", endpoint);
        HttpClient httpClient = HttpClientBuilder.create().build();
        if (id != null) {
            endpoint = endpoint + "/" + id;
        }
        URI uri = createUri("http", port, host, endpoint);
        LOGGER.debug(METHODNAME, "uri=", uri);
        HttpGet getRequest = new HttpGet(uri);
        setHttpHeader(getRequest, headerAccept, headerContentType, headerAuth);
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(getRequest);
            LOGGER.debug(METHODNAME, "httpResponse=", httpResponse);
        } catch (IOException e) {
            LOGGER.error(e);
            return new Response(500, ResponseType.ERROR, "Error executing request");
        }
        String xmlStr;
        try {
            xmlStr = getResponse(httpResponse);
            LOGGER.debug(METHODNAME, "xmlStr=", xmlStr);
        } catch (IOException e) {
            LOGGER.error(e);
            return new Response(500, ResponseType.ERROR, "Error getting content");
        }
        Response response = new Response(httpResponse.getStatusLine().getStatusCode(), ResponseType.INFO, httpResponse.getStatusLine().getReasonPhrase());
        response.setXml(xmlStr);
        LOGGER.debug(METHODNAME, "response=", response);
        return response;
    }

    private URI createUri(String scheme, int port, String host, String endPoint) throws URISyntaxException {
        final String METHODNAME = "createUri ";

        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setHost(host);
        uriBuilder.setPath(endPoint);
        uriBuilder.setPort(port);
        uriBuilder.setScheme(scheme);
        URI uri = uriBuilder.build();
        LOGGER.debug(METHODNAME, "uri=", uri);
        return uri;

    }

    private String getResponse(HttpResponse httpResponse) throws IOException {
        final String METHODNAME = "getResponse ";

        LOGGER.debug(METHODNAME, "httpResponse=", httpResponse);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(httpResponse.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();

    }

    private byte[] getOutputBytes(HttpResponse httpResponse) throws IOException {
        final String METHODNAME = "getOutputBytes ";

        LOGGER.debug(METHODNAME, "httpResponse=", httpResponse);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        httpResponse.getEntity().writeTo(baos);
        return baos.toByteArray();

    }

    private void setHttpHeader(HttpRequestBase putRequest, Header headerAccept, Header headerContentType, Header headerAuth) {
        final String METHODNAME = "setHttpHeader ";
        LOGGER.debug(METHODNAME, "putRequest=", putRequest);
        LOGGER.debug(METHODNAME, "headerAccept=", headerAccept);
        LOGGER.debug(METHODNAME, "headerContentType=", headerContentType);
        if (headerAccept != null) {
            putRequest.addHeader(headerAccept);
        }
        putRequest.addHeader(headerContentType);
        putRequest.addHeader(headerAuth);
    }

}
