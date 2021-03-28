package com.jiuqi.web.constants;

public interface PersonConstants {

    interface Result {
        int CODE_SUCCESS = 200;//请求成功的响应
        int CODE_FAIL = 500;//请求失败的响应码
        String MSG_SUCCESS = "success";//请求成功的消息
        String MSG_FAIL = "error";//请求失败的消息

    }

    interface Regexp {
        String NAME = "^[\\u4e00-\\u9fa5a-zA-Z]*$";//这个是至少有一个中文或者英文
        String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        String TELEPHONE = "^(16[0-9]|13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
    }
}