package com.project.project002.util;



import java.io.Serializable;

/**
 * 统一返回类
 */
public class JsonResult implements Serializable{
    private String code;
    private String message;
    private Object data;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    public JsonResult(String code, String message, Object data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public JsonResult(Object data) {
        super();
        this.data = data;
        this.code = Status.SUCCESS;
        this.message = Status.SUCCESS_MSG;
    }
}
