package com.kzq.advance.mapper;


import com.kzq.advance.domain.TPrintTpl;
import org.springframework.stereotype.Repository;

@Repository
public interface TPrintTplMapper {

    public TPrintTpl findByCpCode(String cpCode,String type);


}