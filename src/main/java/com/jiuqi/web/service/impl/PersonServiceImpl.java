package com.jiuqi.web.service.impl;


import com.jiuqi.practice.utils.Password;
import com.jiuqi.web.dao.PersonDao;
import com.jiuqi.web.entity.Person;
import com.jiuqi.web.service.PersonService;
import com.jiuqi.web.utils.PageBean;
import com.jiuqi.web.utils.SimpleQuery;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

@SuppressWarnings("all")
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;


    @Override
    @Transactional
    public void save(Person person) {
        Assert.notNull(person, "对象不能为空！");
        Assert.notNull(person.getName(), "账号不能为空！");
        Person byName = personDao.findByName(person.getName());
        if (null != byName) {
            throw new RuntimeException("账号已经存在！");
        }
        Md5Hash md5Hash = new Md5Hash(person.getPassword(), "salt", 1024);
        person.setPassword(md5Hash.toHex());
        personDao.save(person);
    }

    @Override
    @Transactional
    public void deleteById(String   name) {
        Person person = personDao.findByName(name);
        if (null == person) {
            throw new RuntimeException("不存在 " + name);
        }
        person.setId(person.getId());
        person.setIsAvailable(0);
        personDao.deleteById(person);

    }

    @Override
    @Transactional
    public void editById(Person person) {
        Assert.notNull(person, "对象不能为空！");
        Assert.notNull(person.getName(), "账号不能为空！");
        Assert.notNull(person.getId(), "ID不能为空!");
        Person byId = (Person) personDao.findById(person.getId());
        if (null == byId.getId()) {
            throw new RuntimeException("需要更新的数据未找到，数据库中不存在数据id：" + person.getId());
        }
        Person byName = personDao.findByName(person.getName());
        //如果没有修改账号，就保存,如果修改了，在判断有没有，如果和第一次打开的账号一致，就认为账号没有修改，可以进行修改
        if (null != byName) {
            if (byId.getName().equals(person.getName())) {
                byName.setId(byId.getId());
                byName.setName(byId.getName());
                byName.setVersion(byName.getVersion());
                byName.setIsAvailable(1);
                byName.setPhoneNumber(person.getPhoneNumber());
                byName.setSex(person.getSex());
                personDao.editById(byName);
            } else {
                throw new RuntimeException("账号已经存在！");
            }
        } else {
            person.setId(byId.getId());
            person.setVersion(byName.getVersion());
            personDao.editById(person);
        }

    }

    @Override
    public Person findByName(String name,UUID id) {
        Assert.notNull(name, "人员名字不能为空！");
        Person byId = (Person) personDao.findById(id);
        Person byName = personDao.findByName(name);
        //如果没有修改账号，就保存,如果修改了，在判断有没有，如果和第一次打开的账号一致，就认为账号没有修改，可以进行修改
        if (null != byName) {
            if (!byId.getName().equals(name)) {
                throw new RuntimeException("账号已经存在！");
            }
        }
        return byName;
    }

    @Override
    public void findByName(String name) {
        Assert.notNull(name, "人员名字不能为空！");
        Person byName = personDao.findByName(name);
        if (null != byName) {

            throw new RuntimeException("账号已经存在！");
        }

    }

    @Override
    @Transactional
    public String iswitch(String name, String status) {
        Person byName = personDao.findByName(name);
        byName.setStatus(status);
        return personDao.iswitch(byName);

    }

    @Override
    public Person findById(UUID id) {
        Person person = personDao.findById(id);
        if (null == person) {
            return (Person) Collections.emptyList();
        }
        return person;
    }

    @Override
    public PageBean queryPagesBean(SimpleQuery simpleQuery, int pageSize, int page) {
        Map<String, Object> map = getStringObjectMap(simpleQuery);
        int count = personDao.getCount(map);  //  总记录数
        int totalPage = PageBean.countTotalPage(pageSize, count);  //  总页数
        int offset = PageBean.countOffset(pageSize, page);  //  当前页开始记录
        int length = pageSize;  //  每页记录数
        int currentPage = PageBean.countCurrentPage(page);
        List<Person> list = personDao.findPage(map, offset, length);
        PageBean pageBean = null;
        if (!list.isEmpty() || null != list) {
            // 把分页信息保存到Bean中
            pageBean  = new PageBean();
                 pageBean.setPageSize(pageSize);
                 pageBean.setCurrentPage(currentPage);
                 pageBean.setAllRow(count);
                 pageBean.setTotalPage(totalPage);
                 pageBean.setList(list);
                 pageBean.init();

        }
        return pageBean;

    }

    private Map<String, Object> getStringObjectMap(SimpleQuery simpleQuery) {
        Map<String, Object> map = new HashMap<>();
        if (null == simpleQuery) {
            return Collections.emptyMap();
        }
        if (!StringUtils.isEmpty(simpleQuery.getName())) {
            map.put("name", simpleQuery.getName());
        }
        if (!StringUtils.isEmpty(simpleQuery.getSex())) {
            map.put("sex", simpleQuery.getSex());
        }
        return map;
    }

    @Override
    @Transactional
    public void updatePwd(Password password) {
        Assert.notNull(password, "对象不能为空！");
        Assert.notNull(password.getName(), "账号不能为空！");
        Person byName = personDao.findByName(password.getName());
        Md5Hash md5Hash = new Md5Hash((password.getOldPassword()), "salt", 1024);
        if (byName.getPassword().equals(md5Hash.toHex()) && password.getPassword().equals(password.getPwdCheck())) {
            password.setPassword(new Md5Hash((password.getPassword()), "salt", 1024).toHex());
            personDao.editPassword(password);
        } else {
            throw new RuntimeException("更新密码失败！");
        }
    }

    @Override
    @Transactional
    public void isPwd(Password password) {
        Assert.notNull(password, "对象不能为空！");
        Person byName = personDao.findByName(password.getName());
        Md5Hash md5Hash = new Md5Hash((password.getOldPassword()), "salt", 1024);
        if (!byName.getPassword().equals(md5Hash.toHex())){
            throw new RuntimeException("旧密码不正确！");
        }

    }


}