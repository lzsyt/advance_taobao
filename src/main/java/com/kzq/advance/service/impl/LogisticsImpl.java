package com.kzq.advance.service.impl;


import com.kzq.advance.domain.LogisticsCompany;
import com.kzq.advance.mapper.LogisticsCompanyMapper;
import com.kzq.advance.service.ILogisticsService;
import com.taobao.api.request.CainiaoWaybillIiGetRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zmn
 * @since 2018-08-16
 */

@Service("ILogisticsService")
public class LogisticsImpl implements ILogisticsService {
    @Autowired
    LogisticsCompanyMapper logisticsCompanyMapper;

    protected Logger logger = LogManager.getLogger(LogisticsImpl.class);

    /**
     * 根据快递公司code获取寄件地址
     * @param cpCode
     * @return
     */
    @Override
    public CainiaoWaybillIiGetRequest.AddressDto getSendAddress(String cpCode) {

        LogisticsCompany logistics=logisticsCompanyMapper.selectByPrimaryKey(cpCode);
        CainiaoWaybillIiGetRequest.AddressDto address = new CainiaoWaybillIiGetRequest.AddressDto();
        address.setDetail(logistics.getAddressDetail());
        //address.setDetail(logistics.getAddressDetail());
        address.setCity(logistics.getCity());
        address.setDistrict(logistics.getArea());
        address.setProvince(logistics.getProvince());
        return address;
    }
    /**
     *
     * 根据cpCode获取订购模板情况
     *
     */
    @Override
    public LogisticsCompany getLogisticsCompany(String cpCode) {

        return logisticsCompanyMapper.selectByPrimaryKey(cpCode);
    }

}
