package com.kzq.advance.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zmn
 * @since 2018-08-22
 */
public class TWsBill implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;
    private Integer billStatus;
    private Date bizdt;
    private String bizUserId;
    private String customerId;
    private Date dateCreated;
    private String inputUserId;
    private String customer;
    private String warehouse;
    private BigDecimal inventoryMoney;
    private BigDecimal profit;
    private String ref;
    private BigDecimal saleMoney;
    private String warehouseId;
    private Integer receivingType;
    private String dataOrg;
    private String companyId;
    private String memo;
    private String dealAddress;
    private String goodsName;
    private String goodsCount;
    private String unitName;
    private String filePath;
    private String expressNum;
    private String expressCompany;
    private String shopNum;


    public String getShopNum() {
        return shopNum;
    }

    public void setShopNum(String shopNum) {
        this.shopNum = shopNum;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }


    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }

    public Date getBizdt() {
        return bizdt;
    }

    public void setBizdt(Date bizdt) {
        this.bizdt = bizdt;
    }

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getInputUserId() {
        return inputUserId;
    }

    public void setInputUserId(String inputUserId) {
        this.inputUserId = inputUserId;
    }

    public BigDecimal getInventoryMoney() {
        return inventoryMoney;
    }

    public void setInventoryMoney(BigDecimal inventoryMoney) {
        this.inventoryMoney = inventoryMoney;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getReceivingType() {
        return receivingType;
    }

    public void setReceivingType(Integer receivingType) {
        this.receivingType = receivingType;
    }

    public String getDataOrg() {
        return dataOrg;
    }

    public void setDataOrg(String dataOrg) {
        this.dataOrg = dataOrg;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDealAddress() {
        return dealAddress;
    }

    public void setDealAddress(String dealAddress) {
        this.dealAddress = dealAddress;
    }


}
