package com.project.project002.util;

/**
 * 统一返回格式
 */
public interface Status {
	
	String ERR = "-200";
	String ERR_MSG = "后台异常";

	String ER_TOKEN = "-101";
	String ER_TOKEN_MSG = "未登录或登陆失效";


	String SUCCESS = "200";
	String SUCCESS_MSG = "操作成功";

}
