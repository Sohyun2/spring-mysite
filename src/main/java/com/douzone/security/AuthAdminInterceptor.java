package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.vo.UserVo;

public class AuthAdminInterceptor extends HandlerInterceptorAdapter {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 로그인이 되어있는지 확인
		HttpSession session = request.getSession(true);
		UserVo authUser = (UserVo)session.getAttribute("authuser");
		
		String role = authUser.getRole();
		System.out.println("role : " + role);
		if(authUser == null || !role.equals("admin") ) {
			// 로그인이 되어있지 않거나  email값이 admin email과 다를경우..
			response.sendRedirect(request.getContextPath() + "/user/login");
			return true;
		}
		
		response.sendRedirect(request.getContextPath() + "/admin");
		return true;
	}
	
}
