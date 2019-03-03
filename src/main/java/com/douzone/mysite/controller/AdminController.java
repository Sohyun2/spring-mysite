package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.security.Auth;
import com.douzone.security.Auth.Role;

@Controller
@RequestMapping("/admin")
@Auth(Role.admin)
public class AdminController {

	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private SiteService siteService;

	@RequestMapping({ "", "/main" })
	public ModelAndView main() {
		// db접근해서 site Table에 입력되어있는 값 가져오기
		SiteVo siteVo = siteService.getInfo(); // siteVo에 저장하여 값 가져옴

		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/main");
		mav.addObject("siteVo", siteVo);

		return mav;
	}

	@RequestMapping("/update")
	public String board(
			@ModelAttribute SiteVo siteVo,
			@RequestParam("upload-profile") MultipartFile multipartFile) {
		
		System.out.println("in adminController");
		
		String profile = fileUploadService.restore(multipartFile);
		System.out.println("profile : " + profile);
		
		siteVo.setProfile(profile);
		System.out.println(siteVo);
		
		siteService.update(siteVo);
		
		return "redirect:/admin/main";
	}
}
