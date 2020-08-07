package com.project.project002.dao;


import com.project.project002.entity.Type;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TypeDao {

    /**
     * 通过code,获取数据字典详情
     * @param code typecode
     * @return
     */
    List<Type> getTypeByCode(String code);
}