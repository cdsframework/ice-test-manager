package org.cdsframework.rckms.data.upload.service.uploader;

/**
 * @author Mark Curtis
 */
public class Response {

    private final int code;
    private final String description;
    private String xml;
    private final ResponseType responseType;
    private DataType type;
    private byte [] knowledgeModule;

    public Response(int code, ResponseType responseType, String description) {
        this.code = code;
        this.description = description;
        this.responseType = responseType;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public void setKnowledgeModule(byte [] knowledgeModule) {
        this.knowledgeModule = knowledgeModule;
    }

    public byte [] getKnowledgeModule() {
        return knowledgeModule;
    }

    @Override
    public String toString() {
        return "Response{" + "code=" + code + ", description=" + description + ", responseType=" + responseType + ", type=" + type + '}';
    }

}
