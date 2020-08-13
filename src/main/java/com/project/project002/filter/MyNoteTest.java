package com.project.project002.filter;


import com.project.project002.entity.Type;
import com.project.project002.service.TypeService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class MyNoteTest {



    @Pointcut("@annotation(com.project.project002.filter.MyNote)")
    public void point(){
        System.out.println("point");
    }

    @Before("point()")
    public void before(){
        System.out.println("begin");
    }

    @AfterReturning("point()")
    public void after(){
        System.out.println("after");
    }

    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("around begin");
        joinPoint.proceed();
        System.out.println("around commit");
    }


}
