package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthLogoutInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();

		//System.out.println("here");
		if(session != null) {
			//System.out.println("here");
			session.removeAttribute("authuser");
			session.invalidate();
		}
		
		response.sendRedirect(request.getContextPath() + "/");
		return false;
	}

}
