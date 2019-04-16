package com.kzq.advance.domain;

public class TGoodsSku {
    private Long skuId;

    private Long numIid;

    private String propertiesAlias;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getNumIid() {
        return numIid;
    }

    public void setNumIid(Long numIid) {
        this.numIid = numIid;
    }

    public String getPropertiesAlias() {
        return propertiesAlias;
    }

    public void setPropertiesAlias(String propertiesAlias) {
        this.propertiesAlias = propertiesAlias == null ? null : propertiesAlias.trim();
    }
}