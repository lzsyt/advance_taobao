package com.kzq.advance.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Table(name = "standard_template")
public class StandardTemplateResult implements Serializable {
    @Id
    private Integer id;
    //cp_code
    private String cpCode;
    //standard_template_id
    private Long standardTemplateId;
    //standard_template_name
    private String standardTemplateName;
    //    standard_template_url
    private String standardTemplateUrl;
    //standard_waybill_type
    private Long standardWaybillType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public Long getStandardTemplateId() {
        return standardTemplateId;
    }

    public void setStandardTemplateId(Long standardTemplateId) {
        this.standardTemplateId = standardTemplateId;
    }

    public String getStandardTemplateName() {
        return standardTemplateName;
    }

    public void setStandardTemplateName(String standardTemplateName) {
        this.standardTemplateName = standardTemplateName;
    }

    public String getStandardTemplateUrl() {
        return standardTemplateUrl;
    }

    public void setStandardTemplateUrl(String standardTemplateUrl) {
        this.standardTemplateUrl = standardTemplateUrl;
    }

    public Long getStandardWaybillType() {
        return standardWaybillType;
    }

    public void setStandardWaybillType(Long standardWaybillType) {
        this.standardWaybillType = standardWaybillType;
    }
}
