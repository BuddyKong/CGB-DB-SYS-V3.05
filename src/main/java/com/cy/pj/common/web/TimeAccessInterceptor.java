package com.cy.pj.common.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cy.pj.common.exception.ServiceException;

public class TimeAccessInterceptor implements HandlerInterceptor {

	/**此方法会在@Controller描述的对象的方法执行之前执行*/
	 @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("==preHandle==");
		//1.获取当前日历对象
		Calendar c=Calendar.getInstance();
		//2.设置访问时间
		c.set(Calendar.HOUR_OF_DAY, 8);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		long start=c.getTimeInMillis();
		c.set(Calendar.HOUR_OF_DAY, 20);
		long end=c.getTimeInMillis();
		long currentTime=System.currentTimeMillis();
		if(currentTime<start||currentTime>end)
			throw new ServiceException("请在8~14点之间访问");
		return true;//false 表示拒绝继续执行,true表示放行
	}
}




