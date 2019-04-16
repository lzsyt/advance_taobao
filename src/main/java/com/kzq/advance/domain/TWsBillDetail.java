package com.kzq.advance.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TWsBillDetail {
    private String id;

    private Date dateCreated;

    private String goodsId;

    private BigDecimal goodsCount;

    private BigDecimal goodsMoney;

    private BigDecimal goodsPrice;

    private BigDecimal inventoryMoney;

    private BigDecimal inventoryPrice;

    private Integer showOrder;

    private String wsbillId;

    private String snNote;

    private String dataOrg;

    private String memo;

    private String companyId;

    private String sobilldetailId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public BigDecimal getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(BigDecimal goodsCount) {
        this.goodsCount = goodsCount;
    }

    public BigDecimal getGoodsMoney() {
        return goodsMoney;
    }

    public void setGoodsMoney(BigDecimal goodsMoney) {
        this.goodsMoney = goodsMoney;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getInventoryMoney() {
        return inventoryMoney;
    }

    public void setInventoryMoney(BigDecimal inventoryMoney) {
        this.inventoryMoney = inventoryMoney;
    }

    public BigDecimal getInventoryPrice() {
        return inventoryPrice;
    }

    public void setInventoryPrice(BigDecimal inventoryPrice) {
        this.inventoryPrice = inventoryPrice;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public String getWsbillId() {
        return wsbillId;
    }

    public void setWsbillId(String wsbillId) {
        this.wsbillId = wsbillId == null ? null : wsbillId.trim();
    }

    public String getSnNote() {
        return snNote;
    }

    public void setSnNote(String snNote) {
        this.snNote = snNote == null ? null : snNote.trim();
    }

    public String getDataOrg() {
        return dataOrg;
    }

    public void setDataOrg(String dataOrg) {
        this.dataOrg = dataOrg == null ? null : dataOrg.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getSobilldetailId() {
        return sobilldetailId;
    }

    public void setSobilldetailId(String sobilldetailId) {
        this.sobilldetailId = sobilldetailId == null ? null : sobilldetailId.trim();
    }
}