package com.douzone.mysite.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Log LOG = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public void handlerException(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception{
		// 1. 로깅 작업
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		LOG.error(errors.toString());
		
		// 2. 에러페이지 화면 전환 / 시스템 오류 안내 화면
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("uri", request.getRequestURI());
//		mav.addObject("errors", errors.toString());
//		mav.setViewName("error/exception");
//		
//		return mav;
		
		String accept = request.getHeader("accept");
		
		if(accept.matches(".*application/json.*")) { // 정규표현식 .* = 어떠한 문자가 붙어있던..
			response.setStatus(HttpServletResponse.SC_OK);
			
			OutputStream out = response.getOutputStream();
			JSONResult jsonResult = JSONResult.fail(errors.toString());
			
			out.write(new ObjectMapper().writeValueAsString(jsonResult).getBytes("UTF-8"));
			out.flush();
			out.close();
			
		} else { // HTML응답
			request.setAttribute("uri", request.getRequestURI());
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
			
		}
	}
}
