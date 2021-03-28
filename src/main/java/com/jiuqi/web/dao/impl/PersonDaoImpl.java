package com.jiuqi.web.dao.impl;

import com.jiuqi.practice.utils.Password;
import com.jiuqi.web.dao.PersonDao;
import com.jiuqi.web.entity.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
@Repository
public class PersonDaoImpl implements PersonDao {

    @Autowired
    private HibernateOperations hibernateOperations;


    @Override
    public Integer getCount(Map<String, Object> parm) {
        return hibernateOperations.execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session s) throws HibernateException {
                //先判断key 拼接，再判断value设置参数值
                StringBuilder buf = new StringBuilder();
                if (null == parm || parm.isEmpty() || parm.size() <= 0) {
                    buf.append(" select count(*) from Person where isAvailable = 1");
                } else {
                    buf.append(" select count(*)  from Person where 1=1 ");
                    if (parm.containsKey("name")) {
                        buf.append(" and name like :name ");
                    }
                    if (parm.containsKey("sex")) {
                        buf.append(" and sex = :sex ");
                    }
                    buf.append("and isAvailable = 1");
                }
                //不能是  Query query = s.createQuery(buf.toString()，Person.class);
                Query query = s.createQuery(buf.toString());
                getParameters(query, parm);
                return Integer.valueOf(query.getResultList().get(0).toString());
            }
        });

    }

    private void getParameters(Query query, Map<String, Object> parm) {
        if (!StringUtils.isEmpty((String) parm.get("name"))) {
            query.setParameter("name", "%" + (String) parm.get("name") + "%");
        }
        if (!StringUtils.isEmpty((String) parm.get("sex"))) {
            query.setParameter("sex", (String) parm.get("sex"));
        }
    }

    @Override
    public void save(Person person) {
        person.setId(UUID.randomUUID());
        person.setIsAvailable(1);
        person.setStatus("true");
        person.setCreateDate(new Date());
        hibernateOperations.merge(person);
    }

    @Override
    public void deleteById(Person person) {
        hibernateOperations.merge(person);

    }

    @Override
    public String iswitch(Person person) {
        Person merge = hibernateOperations.merge(person);
        return merge.getStatus();

    }

    @Override
    public void editById(Person person) {
        person.setIsAvailable(1);
        hibernateOperations.merge(person);
    }

    @Override
    public Person findByName(String name) {
        String hql = " select  p  from  Person as p where p.name = :name and p.isAvailable = 1 ";
        Person person = hibernateOperations.execute(session -> session.createQuery(hql, Person.class).setParameter("name", name).uniqueResult());
        return person;
    }

    @Override
    @Transactional
    public List<Person> findPage(Map<String, Object> parm, int offset, int limit) {
        return hibernateOperations.execute(new HibernateCallback<List<Person>>() {
            @Override
            public List<Person> doInHibernate(Session s) throws HibernateException {
                //先判断key 拼接，再判断value设置参数值
                StringBuilder buf = new StringBuilder();
                if (null == parm || parm.isEmpty() || parm.size() <= 0) {
                    buf.append(" from Person where isAvailable = 1 order by createDate desc");
                } else {
                    buf.append(" from Person where isAvailable = 1 ");
                    if (parm.containsKey("name")) {
                        buf.append(" and name like :name ");
                    }
                    if (parm.containsKey("sex")) {
                        buf.append(" and sex = :sex ");
                    }
                    buf.append(" order by createDate desc");
                }
                Query query = s.createQuery(buf.toString(), Person.class);
                getParameters(query, parm);
                query.setFirstResult(offset);
                query.setMaxResults(limit);
                return query.getResultList();
            }
        });

    }

    @Override
    public Person findById(UUID id) {
        String hql = "select  p  from  Person as p where p.id = :id and p.isAvailable = 1 ";
        return hibernateOperations.execute(session -> session.createQuery(hql, Person.class).setParameter("id", id).uniqueResult());
    }

    @Override
    public void editPassword(Password password) {
//        String hql = "update Person p set p.password = :password  where  p.name = :name";
        System.err.println(password.getPassword());
        Person byName = findByName(password.getName());
        byName.setPassword(password.getPassword());
        hibernateOperations.merge(byName);

    }

}
