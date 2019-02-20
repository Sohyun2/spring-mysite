package com.douzone.mysite.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.mysite.repository.UserDao;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "/user/join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute UserVo vo) {
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "/user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpSession session, @ModelAttribute UserVo vo) {
		UserVo authUser = userService.login(vo);
		String result = "fail";

		if (vo != null) {
			result = "success";
		}

		ModelAndView mav = new ModelAndView();

		if (result.equals("fail")) {
			// 로그인 실패..
			mav.setViewName("/user/login");
			mav.addObject("result", result);
		} else {
			// 로그인 성공

			// session등록
			session.setAttribute("authuser", authUser);
			// 화면 전환
			mav.setViewName("redirect:/main");
		}
		return mav;
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		if (session != null && session.getAttribute("authuser") != null) {
			// logout 처리
			session.removeAttribute("authuser");
			session.invalidate();
		}
		return "redirect:/main";
	}
	
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public String modify() {
		return "/user/modify";
	}
	
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modify(HttpSession session, @ModelAttribute UserVo vo) {

		UserVo authUser = (UserVo) session.getAttribute("authuser");
		
		vo.setEmail(authUser.getEmail());
		
		userService.modify(vo);
		// 세션수정
		session.setAttribute("authuser", vo);
		
		return "redirect:/main";
	}	
}
