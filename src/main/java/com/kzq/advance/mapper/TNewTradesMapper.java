package com.kzq.advance.mapper;

import com.kzq.advance.common.base.BaseMapper;
import com.kzq.advance.domain.TNewTrades;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TNewTradesMapper extends BaseMapper<TNewTrades> {

    public List<TNewTrades> getTrades(@Param("shopId") String shopId);


}