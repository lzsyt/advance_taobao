package com.kzq.advance.domain;

public class LogisticsCompany {
    private String cpCode;

    private String logisticsCompany;

    private String resourceCode;

    private Integer state;

    private String addressDetail;

    private String area;

    private String city;

    private String province;

    private String standardTemplateUrl;

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode == null ? null : cpCode.trim();
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany == null ? null : logisticsCompany.trim();
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode == null ? null : resourceCode.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getStandardTemplateUrl() {
        return standardTemplateUrl;
    }

    public void setStandardTemplateUrl(String standardTemplateUrl) {
        this.standardTemplateUrl = standardTemplateUrl == null ? null : standardTemplateUrl.trim();
    }
}