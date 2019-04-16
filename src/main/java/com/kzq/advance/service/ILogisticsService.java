package com.kzq.advance.service;


import com.kzq.advance.domain.LogisticsCompany;
import com.taobao.api.request.CainiaoWaybillIiGetRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zmn
 * @since 2018-08-16
 */
public interface ILogisticsService {

    /**
     * 查询收件人地址
     */
    public CainiaoWaybillIiGetRequest.AddressDto getSendAddress(String cpCode);
    /**
     *根据cpCode获取面单模板
     */
    public LogisticsCompany getLogisticsCompany(String cpCode);




}
