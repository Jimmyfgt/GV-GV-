package com.jiuqi.web.vo;


import com.jiuqi.web.constants.PersonConstants;

import java.io.Serializable;

/**
 * @author fugaotian
 */
@SuppressWarnings("all")
public class PersonVO implements Serializable {
    private static final long serialVersionUID = -331178238428972966L;
    private int code;//响应码
    private String msg;//返回的消息
    private Object data;//返回的数据实体

    public PersonVO() {

    }

    public PersonVO(int code) {
        this.code = code;
    }


    public PersonVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public PersonVO(int code, String msg, Object entity) {
        this.code = code;
        this.msg = msg;
        this.data = entity;
    }


    public static PersonVO success() {
        return new PersonVO(PersonConstants.Result.CODE_SUCCESS, PersonConstants.Result.MSG_SUCCESS);
    }

    /**
     * 请求成功
     *
     * @param data
     * @return
     */
    public static PersonVO success(Object data) {
        return new PersonVO(PersonConstants.Result.CODE_SUCCESS, PersonConstants.Result.MSG_SUCCESS, data);
    }


    public static PersonVO fail() {
        return new PersonVO(PersonConstants.Result.CODE_FAIL, PersonConstants.Result.MSG_FAIL);
    }

    /**
     * 自定义请求错误消息
     *
     * @param msg
     * @return
     */
    public static PersonVO fail(String msg) {
        return new PersonVO(PersonConstants.Result.CODE_FAIL, msg);
    }

    /**
     * 请求失败
     *
     * @param data
     * @return
     */
    public static PersonVO fail(Object data) {
        return new PersonVO(PersonConstants.Result.CODE_FAIL, PersonConstants.Result.MSG_FAIL, data);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
