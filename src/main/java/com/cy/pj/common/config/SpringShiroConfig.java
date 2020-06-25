package com.cy.pj.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cy.pj.sys.service.realm.ShiroUserRealm;
//Shiro框架的springboot增强配置:http://shiro.apache.org/spring-boot.html
@Configuration
public class SpringShiroConfig {//http1.1

	@Bean
	public Realm realm() {
		return new ShiroUserRealm();
	}
	@Bean
	protected CacheManager shiroCacheManager() {
	    return new MemoryConstrainedCacheManager();
	}
	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
		//2.设置过滤规则
		LinkedHashMap<String,String> map= new LinkedHashMap<>();
		//静态资源允许匿名访问(例如项目static目录下的资源):"anon"表示匿名
		map.put("/bower_components/**","anon");
		map.put("/modules/**","anon");
		map.put("/dist/**","anon");
		map.put("/plugins/**","anon");
		map.put("/user/doLogin","anon");
		map.put("/doLogout","logout");//logout也是shiro框架提供
		//除了匿名访问的资源,其它都要认证("authc")后访问
		//map.put("/**","authc");
		map.put("/**","user");//当选择了记住我功能，认证方式进行修改
		chainDefinition.addPathDefinitions(map);
		return chainDefinition;
	}
}











