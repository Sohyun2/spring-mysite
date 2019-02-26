package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping( {"", "/list"} )
	public ModelAndView list() {
		List<GuestbookVo> list = guestbookService.getList();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/guestbook/list");
		mav.addObject("list", list);
		
		return mav;
	}
	
	@RequestMapping("/insert")
	public String insert(@ModelAttribute GuestbookVo vo) {
		guestbookService.insert(vo);
		
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete() {
		return "/guestbook/delete";
	}	
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(GuestbookVo vo) {
		guestbookService.delete(vo);
		
		return "redirect:/guestbook/list";
	}
}