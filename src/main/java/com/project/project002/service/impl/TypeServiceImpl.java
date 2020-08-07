package com.project.project002.service.impl;



import com.project.project002.dao.TypeDao;
import com.project.project002.entity.Type;
import com.project.project002.service.TypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeDao typeDao;

    @Override
    public List<Type> getTypeByCode(String code) {
        return typeDao.getTypeByCode(code);
    }

    @Override
    public int getMaxFourElementTimes() {
        int max = 5;
        List<Type> typeList = typeDao.getTypeByCode("signUpTimes");
        if( null != typeList && typeList.size() > 0 ){
            for (Type type: typeList) {
                if(type.getTypename().equals("四要素次数")){
                    max = Integer.valueOf(type.getTypecode());
                }
            }
        }
        return max;
    }

    @Override
    public int getMaxLivingBodyTimes() {
        int max = 5;
        List<Type> typeList = typeDao.getTypeByCode("signUpTimes");
        if( null != typeList && typeList.size() > 0 ){
            for (Type type: typeList) {
                if(type.getTypename().equals("活体次数")){
                    max = Integer.valueOf(type.getTypecode());
                }
            }
        }
        return max;
    }
}
