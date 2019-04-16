package com.kzq.advance.mapper;

import com.kzq.advance.domain.TCustomer;
import com.kzq.advance.domain.TWsBill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zmn
 * @since 2018-08-22
 */
@Mapper
public interface TWsBillMapper  {
    List<TWsBill> findTWsBill(Integer status);

    TWsBill findDetailById(String id);

    TCustomer findCustomerById(String customerId);

    void updateTWsBill(TWsBill bill);

    List<TWsBill> findGoodsByBillId(String id);

    List<TWsBill> findTWsBillbyCondition(@Param("condition") String condition);
    /*
       根据id查询出库单
     */
    TWsBill findByTid (String tid);

    /**
     * 根据仓库查询出库单
     * @param map
     * @return
     */

    List<TWsBill> getBillByWarehouse(Map<String,String> map);

    List<TWsBill> findDetailByBillIdAndWarehouse( TWsBill bill);






}