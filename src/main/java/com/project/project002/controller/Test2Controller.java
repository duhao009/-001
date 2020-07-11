package com.project.project002.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by miyakohiroshi on 2020/7/10.
 */
@RequestMapping("test2")
@RestController
public class Test2Controller {

    @GetMapping("test2")
    public String test(String string){
        return  "dev====" + string;
    }
}
