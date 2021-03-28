package com.jiuqi.web.controller;


import com.jiuqi.practice.utils.Password;
import com.jiuqi.web.entity.Person;
import com.jiuqi.web.service.PersonService;
import com.jiuqi.web.utils.PageBean;
import com.jiuqi.web.utils.SimpleQuery;
import com.jiuqi.web.vo.PersonVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fugaotian
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("person")
public class PersonController {

    private final static Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/addOrUpdate")
    public PersonVO save(@RequestBody Person person) {
        try {
            if (null != person.getId()) {
                personService.editById(person);
            } else {
                personService.save(person);
            }
            return PersonVO.success("操作成功！");
        } catch (Exception e) {
            logger.info("操作失败", e);
            return PersonVO.fail("操作失败");
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public PersonVO delete(@PathVariable("id") String  name) {
        try {
            personService.deleteById(name);
            return PersonVO.success("删除成功");
        } catch (Exception e) {
            logger.error("删除失败 {}", e);
            return PersonVO.fail("删除失败");
        }

    }

    /**
     * @param simpleQuery 接受的查询条件
     * @param page        控制要从数据库里查几条数据（每页显示几条数据）
     * @param currentPage 当前几页
     * @return
     */
    @PostMapping("/find/{page}/{currentPage}")
    public PersonVO find(@RequestBody SimpleQuery simpleQuery, @PathVariable(value = "page") int page, @PathVariable(value = "currentPage") int currentPage) {
        try {
            PageBean pageBean = personService.queryPagesBean(simpleQuery, page, currentPage);
            return  PersonVO.success(pageBean);
        } catch (Exception e) {
            logger.error(" Source must not be null", e);
            return PersonVO.fail("查询失败！");
        }

    }

    @PostMapping("/updatePwd")
    public PersonVO updatePwd(@RequestBody Password password) {
        try {
            personService.updatePwd(password);
            return PersonVO.success("修改密码成功");
        } catch (Exception e) {
            logger.error("密码输入有误{}", e);
            return PersonVO.fail("密码修改失败！");
        }

    }

    @PostMapping("/Pwd")
    public PersonVO Pwd(@RequestBody Password password) {
        //异步验证旧密码
        try {
            personService.isPwd(password);
            return PersonVO.success(200);
        } catch (Exception e) {
            return PersonVO.fail("旧密码不正确！");
        }
    }

    @PostMapping("/find")
    public PersonVO find(@RequestBody Person person) {
        //异步验证账号
        try {
            if (null == person.getId()) {
                personService.findByName(person.getName());
                return PersonVO.success("成功！");
            }else if (null == person.getName()){
                return PersonVO.fail("账号不能为空");
            }
            personService.findByName(person.getName(), person.getId());
            return PersonVO.success("成功！");
        } catch (Exception e) {
            return PersonVO.fail("已经存在！");
        }
    }

    @PostMapping("/switch/{status}/{name}")
    public PersonVO iswitch(@PathVariable(value = "status") String status, @PathVariable(value = "name") String name) {
        try {
            return PersonVO.success(personService.iswitch(name, status));
        } catch (Exception e) {
            logger.error("", e);
            return PersonVO.fail("开启失败！");
        }
    }

}
