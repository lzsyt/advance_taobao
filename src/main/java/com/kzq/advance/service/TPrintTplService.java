package com.kzq.advance.service;

import com.kzq.advance.domain.TPrintTpl;

public interface TPrintTplService {
    public TPrintTpl findByCpCode(String cpCode,String type);
}
