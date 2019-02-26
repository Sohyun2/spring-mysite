package com.douzone.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor01 implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		// handler 호출 전(요청 전)
		// handler 호출 여부를 결정(boolean 반환값에 따라)
		
		System.out.println("MyInterceptor01:preHandle");
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// 핸들러 호출이 된 후(응답)
		System.out.println("MyInterceptor01:postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// View의 Rendering 작업까지 완전히 완료(응답 후)
		System.out.println("MyInterceptor01:afterCompletion");
	}
}
