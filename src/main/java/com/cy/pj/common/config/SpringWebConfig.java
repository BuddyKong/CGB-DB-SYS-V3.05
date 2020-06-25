package com.cy.pj.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cy.pj.common.web.TimeAccessInterceptor;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {

	/**注册拦截器*/
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new TimeAccessInterceptor())//添加拦截器对象
    	        .addPathPatterns("/user/doLogin");//设置对登陆操作进行拦截
	}
	
}



