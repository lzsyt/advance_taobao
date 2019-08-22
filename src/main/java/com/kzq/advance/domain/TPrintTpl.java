package com.kzq.advance.domain;

public class TPrintTpl {
    private String id;

    private Boolean type;

    private Byte status;

    private String printersId;

    private String cpCode;

    private Boolean printType;

    private String urlId;

    private String name;

    private String url;

    private String urlDe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlDe() {
        return urlDe;
    }

    public void setUrlDe(String urlDe) {
        this.urlDe = urlDe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getPrintersId() {
        return printersId;
    }

    public void setPrintersId(String printersId) {
        this.printersId = printersId == null ? null : printersId.trim();
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode == null ? null : cpCode.trim();
    }

    public Boolean getPrintType() {
        return printType;
    }

    public void setPrintType(Boolean printType) {
        this.printType = printType;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId == null ? null : urlId.trim();
    }
}