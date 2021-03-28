package com.jiuqi.web.service;


import com.jiuqi.practice.utils.Password;
import com.jiuqi.web.entity.Person;
import com.jiuqi.web.utils.PageBean;
import com.jiuqi.web.utils.SimpleQuery;

import java.util.UUID;

/**
 * @author fugaotian
 */
public interface PersonService {

    void save(Person person);

    void deleteById(String name);

    void editById(Person person);

    Person findByName(String name, UUID id);

    Person findById(UUID id);

    void findByName(String name);

    String iswitch(String name, String status);

    /**
     * 多条件查询加分页
     *
     * @param simpleQuery 查询参数
     * @param pageSize    限制条数
     * @param currentPage 当前页
     * @return pageBeans
     */
    PageBean queryPagesBean(SimpleQuery simpleQuery, int pageSize, int currentPage);

    void updatePwd(Password password);

    void isPwd(Password password);


}
