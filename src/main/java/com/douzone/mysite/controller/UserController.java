package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;

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

	@Auth
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public String modify(@AuthUser UserVo authUser, Model model) {
		System.out.println( authUser );
		
		UserVo userVo = userService.getUser( authUser.getNo() );
		model.addAttribute( "authuser", userVo );
		
		return "/user/modify";
	}
	
	@Auth
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modify(@AuthUser UserVo authUser, @ModelAttribute UserVo vo) {
		System.out.println("수정 전 authUser : " + authUser);
		System.out.println("vo.." + vo);
		vo.setEmail(authUser.getEmail());
		
		// user 수정
		userService.modify(vo);
		
		// session의 authUser 변경
		authUser.setName(vo.getName());
		System.out.println("수정 후 authUser : " + authUser);
				
		return "redirect:/main";
	}	
}
