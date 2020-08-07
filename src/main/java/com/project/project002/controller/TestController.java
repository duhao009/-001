package com.project.project002.controller;

import com.project.project002.entity.Type;
import com.project.project002.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by miyakohiroshi on 2020/7/10.
 */
@RequestMapping("test")
@RestController
public class TestController {

    @Autowired
    private TypeService typeService;

    @GetMapping("test")
    public String test(String string){
        return  "master123====" + string;
    }

    @GetMapping("testMysql")
    public List<Type> testMysql(){
        return  typeService.getTypeByCode("signUpTimes");
    }
}
