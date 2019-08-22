package com.kzq.advance.service.impl;

import com.kzq.advance.domain.TPrintTpl;
import com.kzq.advance.mapper.TPrintTplMapper;
import com.kzq.advance.service.TPrintTplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TPrintTplServiceImpl implements TPrintTplService {

    @Autowired
    private TPrintTplMapper tPrintTplMapper;


    @Override
    public TPrintTpl findByCpCode(String cpCode,String type) {
        return tPrintTplMapper.findByCpCode(cpCode,type);
    }
}
