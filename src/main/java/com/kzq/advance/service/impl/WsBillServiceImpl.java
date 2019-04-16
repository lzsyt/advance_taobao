package com.kzq.advance.service.impl;

import com.kzq.advance.domain.TWsBill;
import com.kzq.advance.mapper.TWsBillMapper;
import com.kzq.advance.service.IWsBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IWsBillService")
public class WsBillServiceImpl implements IWsBillService {
   @Autowired
    TWsBillMapper wsBillMapper;
    /**
     * 根据id 获取出库单
     *
     */
    public TWsBill getByTid(String id){

        return wsBillMapper.findByTid(id);
    }
}
