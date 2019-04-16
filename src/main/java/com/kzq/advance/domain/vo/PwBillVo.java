package com.kzq.advance.domain.vo;


import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zmn
 * @since 2018-08-22
 */
public class PwBillVo  {

    private static final long serialVersionUID = 1L;

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
	private String warehouseName;
	private String userName;
	private String supplierName;
	private String code;
	private String goodsName;
	private String unitName;
	private String goodsCount;
	private String prBillId;

	public String getPrBillId() {
		return prBillId;
	}

	public void setPrBillId(String prBillId) {
		this.prBillId = prBillId;
	}

	public String getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(String goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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
		this.bizUserId = bizUserId;
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
		this.inputUserId = inputUserId;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
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
		this.dataOrg = dataOrg;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
		this.billMemo = billMemo;
	}



	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}
