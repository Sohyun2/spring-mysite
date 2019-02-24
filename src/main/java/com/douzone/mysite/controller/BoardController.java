package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping({ "", "/list" })
	public ModelAndView list(@RequestParam(value="page", required=false, defaultValue="1")Integer page) {
		Map<String, Object> map = boardService.list(page);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/list");
		mav.addObject("page", page);
		mav.addAllObjects(map);
		
		return mav;
	}
	
	@RequestMapping("/detail")
	public ModelAndView detail(@RequestParam("no")Long no) {
		Map<String, Object> map = boardService.detail(no);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/detail");
		mav.addAllObjects(map);
		
		return mav;
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.GET)
	public String insert() {
		return "/board/insert";
	}
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public ModelAndView insert(HttpSession session, @ModelAttribute BoardVo vo) {
		System.out.println(vo);
		boardService.insert((UserVo)session.getAttribute("authuser"), vo);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/board/list");
		mav.addObject("page", 1);
		
		return mav;
	}
	
	@RequestMapping("/delete")
	public String delete(Long no) {
		boardService.delete(no);
		
		return "redirect:/board/list";
	}

	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify() {
		return "/board/modify";
	}
		
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(@RequestParam("no")Long no, BoardVo vo) {
		boardService.modify(no, vo);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/reply", method=RequestMethod.GET)
	public String reply() {
		return "/board/reply";
	}
	
	@RequestMapping(value="/reply", method=RequestMethod.POST)
	public String reply(@RequestParam("no")Long no, BoardVo vo, HttpSession session) { //no는 글번호
		UserVo userVo = (UserVo) session.getAttribute("authuser");
		boardService.reply(no, vo, userVo);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping("/comment")
	public ModelAndView comment(
			@RequestParam("no")Long no, // 글번호
			CommentVo vo, // content
			HttpSession session) { //authuser
		
		UserVo userVo = (UserVo) session.getAttribute("authuser");
		boardService.comment(no, vo, userVo);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/board/detail");
		mav.addObject("no", no);
		
		return mav;
	}
	
	@RequestMapping("/c_delete")
	public ModelAndView cDelete(@RequestParam("no")Long boardNo, @RequestParam("c_no")Long commentNo) {
		boardService.commentDelete(commentNo);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/board/detail");
		mav.addObject("no", boardNo);
		
		return mav;
	}
}
