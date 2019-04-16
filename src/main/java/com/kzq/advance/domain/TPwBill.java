package com.kzq.advance.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TPwBill {
    private String id;

    private Integer billStatus;

    private Date bizDt;

    private String bizUserId;

    private Date dateCreated;

    private BigDecimal goodsMoney;

    private String inputUserId;

    private String ref;

    private String supplierId;

    private String warehouseId;

    private Integer paymentType;

    private String dataOrg;

    private String companyId;

    private Integer expandByBom;

    private String billMemo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }

    public Date getBizDt() {
        return bizDt;
    }

    public void setBizDt(Date bizDt) {
        this.bizDt = bizDt;
    }

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId == null ? null : bizUserId.trim();
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public BigDecimal getGoodsMoney() {
        return goodsMoney;
    }

    public void setGoodsMoney(BigDecimal goodsMoney) {
        this.goodsMoney = goodsMoney;
    }

    public String getInputUserId() {
        return inputUserId;
    }

    public void setInputUserId(String inputUserId) {
        this.inputUserId = inputUserId == null ? null : inputUserId.trim();
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref == null ? null : ref.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId == null ? null : warehouseId.trim();
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getDataOrg() {
        return dataOrg;
    }

    public void setDataOrg(String dataOrg) {
        this.dataOrg = dataOrg == null ? null : dataOrg.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public Integer getExpandByBom() {
        return expandByBom;
    }

    public void setExpandByBom(Integer expandByBom) {
        this.expandByBom = expandByBom;
    }

    public String getBillMemo() {
        return billMemo;
    }

    public void setBillMemo(String billMemo) {
        this.billMemo = billMemo == null ? null : billMemo.trim();
    }
}