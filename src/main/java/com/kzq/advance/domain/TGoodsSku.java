package com.kzq.advance.domain;

public class TGoodsSku {
    private Long skuId;

    private Long numIid;

    private String propertiesAlias;

    private Integer isDel;



    @Override
    public String toString() {
        return "TGoodsSku{" +
                "skuId=" + skuId +
                ", numIid=" + numIid +
                ", propertiesAlias='" + propertiesAlias + '\'' +
                '}';
    }

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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}