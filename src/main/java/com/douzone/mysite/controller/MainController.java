package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.dto.JSONResult;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {
	
	@Autowired
	private SiteService siteService;

	@RequestMapping({ "", "/main" })
	public ModelAndView index() {		
		SiteVo siteVo = siteService.getMainInfo(); // site table에 있는 값 불러와서 setting 
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/main/index");
		mav.addObject("siteVo", siteVo);
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "<h1>안녕하세요</h1>";
	}
	@ResponseBody
	@RequestMapping("/hello2")
	public JSONResult hello2() {
		return JSONResult.success(new UserVo());
	}
}
