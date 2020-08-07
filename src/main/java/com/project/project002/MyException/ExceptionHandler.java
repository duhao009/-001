package com.project.project002.MyException;

import com.project.project002.util.JsonResult;
import com.project.project002.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionHandler {
    private Logger loger = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult handle(Exception e) {
        loger.info("全局异常拦截了=========");
        e.printStackTrace();
        return new JsonResult(Status.ERR , Status.ERR_MSG , "");
    }
}
