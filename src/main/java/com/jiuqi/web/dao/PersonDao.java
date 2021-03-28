package com.jiuqi.web.dao;

import com.jiuqi.practice.utils.Password;
import com.jiuqi.web.entity.Person;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author fugaotian
 */
@SuppressWarnings("all")
public interface PersonDao {


    /**
     * 获取总记录数
     *
     * @return
     */
    Integer getCount(Map<String, Object> parm);


    void save(Person person);


    void deleteById(Person person);


    void editById(Person person);

    String iswitch(Person person);

    /**
     * 通过姓名查找
     *
     * @param name
     * @return
     */
    Person findByName(String name);

    /**
     * 模糊查找
     *
     * @param name
     * @return
     */
    List<Person> findPage(Map<String, Object> parm, int offset, int limit);

    Person findById(UUID id);

    void editPassword(Password password);
}