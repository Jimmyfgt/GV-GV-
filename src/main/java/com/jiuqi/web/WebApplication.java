package com.jiuqi.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

@ServletComponentScan
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class, SecurityAutoConfiguration.class })
public class WebApplication extends SpringBootServletInitializer {

	public static final Logger logger = LoggerFactory.getLogger(WebApplication.class);

	static ConfigurableApplicationContext ctx;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		init();
		return application.sources(WebApplication.class);
	}

	@Override
	protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
		WebApplicationContext context = super.createRootApplicationContext(servletContext);
		ctx = (ConfigurableApplicationContext) context;
		return context;
	}

	private static void init() {
		// nothing to do
	}

	protected static void start(Class<?> clazz, String[] args) {
		long timeStart = System.currentTimeMillis();
		init();
		ctx = SpringApplication.run(clazz, args);
		long timeFinish = System.currentTimeMillis();
		double timeComsumed = (timeFinish - timeStart) / 1000.0;
		logger.info("{} is started in {} seconds", clazz.getSimpleName(), timeComsumed);
	}

	public static void main(String[] args) {
		start(WebApplication.class, args);
		
	}
	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]"));
		return factory;
	}

	public static Object getBean(String name) throws BeansException {
		return ctx.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return ctx.getBean(name, requiredType);
	}

	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return ctx.getBean(requiredType);
	}

	public static Object getBean(String name, Object... args) throws BeansException {
		return ctx.getBean(name, args);
	}

	public static <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
		return ctx.getBean(requiredType, args);
	}
}
