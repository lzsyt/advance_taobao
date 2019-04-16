package com.kzq.advance.service;

import com.kzq.advance.domain.TWsBill;

public interface IWsBillService {
    /**
     * 根据id 获取出库单
     *
     */
    public TWsBill getByTid(String id);
}
