package com.kzq.advance.domain;

import java.util.Date;

public class TUpdateLog {
    private Integer id;

    private Integer shopId;

    private Date updateTime;

    private String updateUserId;

    private Integer updateTotal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    public Integer getUpdateTotal() {
        return updateTotal;
    }

    public void setUpdateTotal(Integer updateTotal) {
        this.updateTotal = updateTotal;
    }
}