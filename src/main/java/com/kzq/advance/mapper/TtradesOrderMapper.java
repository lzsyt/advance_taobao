package com.kzq.advance.mapper;

import com.kzq.advance.domain.TtradesOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TtradesOrderMapper {
    int deleteByPrimaryKey(Integer oid);

    int insert(TtradesOrder record);

    int insertSelective(TtradesOrder record);

    TtradesOrder selectByPrimaryKey(Integer oid);

    int updateByPrimaryKeySelective(TtradesOrder record);

    int updateByPrimaryKey(TtradesOrder record);
}