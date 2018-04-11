/*
* Copyright (c) 2015 zendaimoney.com. All Rights Reserved.
*/
package com.zdmoney.credit.common.vo;

/**
 * Result
 * <p/>
 * Author: Hao Chen
 * Date: 2015/10/16 14:23
 * Mail: haoc@zendaimoney.com
 * $Id$
 */
public class FuncResult {
    private Boolean success;
    private String message;
    private Object data;

    public FuncResult() {
        this(Boolean.TRUE, "操作成功");
    }

    public FuncResult(Boolean success) {
        this(success, null);
    }

    public FuncResult(Object data) {
        this(Boolean.TRUE, "操作成功");
        this.data = data;
    }

    public FuncResult(Boolean success, String message) {
        this(success, message, null);
    }


    public FuncResult(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        if (this.message == null) {
            if (Boolean.FALSE.equals(success)) {
                this.message = "系统繁忙,请稍后再试";
            }
            if (Boolean.TRUE.equals(success)) {
                this.message = "操作成功";
            }

        }
    }


    public static FuncResult fail() {
        return fail(null);
    }

    public static FuncResult fail(String message) {
        return new FuncResult(Boolean.FALSE, message);
    }

    public static FuncResult fail(String message, Object data) {
        return new FuncResult(Boolean.FALSE, message, data);
    }

    public static FuncResult success() {
        return success(null);
    }

    public static FuncResult success(Object data) {
        return new FuncResult(data);
    }

    public static FuncResult success(String message, Object data) {
        return new FuncResult(Boolean.TRUE, message, data);
    }


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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
}