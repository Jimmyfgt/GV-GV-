package com.jiuqi.practice.utils;

/**
 * @Author fugaotian
 * @Version 1.0
 */
@SuppressWarnings("all")
public class Password {
    private Password(){

    }
    private String name;
    private String oldPassword;
    private String password;
    private  String pwdCheck;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwdCheck() {
        return pwdCheck;
    }

    public void setPwdCheck(String pwdCheck) {
        this.pwdCheck = pwdCheck;
    }


}

