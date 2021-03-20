package org.cdsframework.rckms.data.upload.service.uploader;

import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

/**
 * @author Mark Curtis
 */
public interface OpenCDSConfigUploader extends Serializable {

    Response addCdm(String cdmXml) throws URISyntaxException, UnsupportedEncodingException;

    Response getCdm(String id) throws URISyntaxException;

    Response deleteCdm(String cdmId) throws URISyntaxException;

    Response addKnowledgeModule(String id, InputStream inputStream, long length) throws URISyntaxException;

    Response getKnowledgeModule(String id) throws URISyntaxException;

    Response deleteKnowledgeModule(String id) throws URISyntaxException;

    Response updateKnowledgeModuleConfig(String kmConfig);

    Response addKnowledgeModuleConfig(String kmConfig) throws URISyntaxException;

    Response deleteKnowledgeModuleConfig(String kmId) throws URISyntaxException;

    Response getKnowledgeModuleConfig(String kmId);
}
