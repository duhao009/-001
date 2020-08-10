package com.project.project002.controller;

import com.project.project002.service.TypeService;
import com.project.project002.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by miyakohiroshi on 2020/7/10.
 */
@RequestMapping("test")
@RestController
public class TestController {

    @Autowired
    private TypeService typeService;

    @GetMapping("test")
    public JsonResult test(String string){
        return new JsonResult("master123====" + string) ;
    }

    @GetMapping("testMysql")
    public JsonResult testMysql(){
        return  new JsonResult(typeService.getTypeByCode("signUpTimes")) ;
    }
}
