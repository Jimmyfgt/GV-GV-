package com.jiuqi.web.config;

import com.jiuqi.web.controller.PersonController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonBeanConfig {


    @Bean
    public PersonController personController(){
        return new PersonController();

    }

}
