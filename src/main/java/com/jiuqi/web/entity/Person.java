package com.jiuqi.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.web.constants.PersonConstants;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * @author fugaotian
 */
@Entity
@Table(name = "person")
@SuppressWarnings("all")
public class Person implements Serializable {
    private static final long serialVersionUID = -331178238428972966L;
    @Id
    @Column(name = "id", length = 32)
    private UUID id;
    //乐观锁版本号
    @Version
    @Column(name = "VER")
    private Long version = Long.valueOf(0);
    //人员名，用于登陆的唯一名字
    @Column(name = "name")
    @NotBlank(message = "账号不能为空")
    @Size(max = 50, message = "最大50个字符")
    private String name;

    @Column(name = "password")
    @NotBlank(message = "密码不能为空")
    @Size(max = 50, min = 6, message = "密码最小长度为6")
    private String password;

    @Column(name = "isAvailable")
    private Integer isAvailable;//0删除的数据，1没删除的

    @Column(name = "status")
    private String status;

    //创建日期
    @Column(name = "createdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;
    //姓别
    @Column(name = "sex")
    private String sex;
    //手机号
    @Column(name = "phonenumber")
    @Pattern(regexp = PersonConstants.Regexp.TELEPHONE, message = "手机号输入格式有误")
    private String phoneNumber;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(version, person.version) && Objects.equals(name, person.name) && Objects.equals(password, person.password) && Objects.equals(isAvailable, person.isAvailable) && Objects.equals(status, person.status) && Objects.equals(createDate, person.createDate) && Objects.equals(sex, person.sex) && Objects.equals(phoneNumber, person.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, name, password, isAvailable, status, createDate, sex, phoneNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("version=" + version)
                .add("name='" + name + "'")
                .add("password='" + password + "'")
                .add("isAvailable=" + isAvailable)
                .add("status='" + status + "'")
                .add("createDate=" + createDate)
                .add("sex='" + sex + "'")
                .add("phoneNumber='" + phoneNumber + "'")
                .toString();
    }
}