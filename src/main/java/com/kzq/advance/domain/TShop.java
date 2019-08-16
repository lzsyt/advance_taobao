package com.kzq.advance.domain;

public class TShop {
    private Integer id;

    private String shopName;

    private String shopToken;

    private String shopTel;

    private String shopAdds;

    public String getShopAdds() {
        return shopAdds;
    }

    public void setShopAdds(String shopAdds) {
        this.shopAdds = shopAdds;
    }

    public String getShopTel() {
        return shopTel;
    }

    public void setShopTel(String shopTel) {
        this.shopTel = shopTel;
    }

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