package com.kzq.advance.domain;

public class TShop {
    private Integer id;

    private String shopName;

    private String shopToken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getShopToken() {
        return shopToken;
    }

    public void setShopToken(String shopToken) {
        this.shopToken = shopToken == null ? null : shopToken.trim();
    }
}