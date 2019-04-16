package com.kzq.advance.mapper;


import com.kzq.advance.domain.TPwBill;
import com.kzq.advance.domain.vo.PwBillVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zmn
 * @since 2018-08-22
 */
@Mapper
public interface TPwBillMapper  {
    //采购入库单详情
     PwBillVo findTPwBillById(String id);

    //采购入库单商品详情
    List<PwBillVo> findDetailById(String id);

    //提交出库单
    void updateTPwBill(TPwBill bill);

   List<PwBillVo> findPwBillList(@Param("keywords") String keywords);
    
}