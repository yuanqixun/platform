package com.superbpm.platform.entity.oauth2;

import java.io.Serializable;

/**
 * 状态
 */
public class Status implements Serializable{

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
