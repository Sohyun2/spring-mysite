package com.douzone.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JSONResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller("guestbookApiController")
@RequestMapping("/guestbook/ajax")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("") 
	public String indexAjax() {
		
		return "guestbook/ajaxlist";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public JSONResult list(@RequestParam("p")Integer p) {
		System.out.println(p);
		List<GuestbookVo> list = guestbookService.getList(p);
		System.out.println(list);
		
		// JSON객체에 담아서 return
		
		if(list == null) {
			// 만약 list를 뽑아오지 못했을 경우..
			return JSONResult.fail("");
		}
		
		return JSONResult.success(list);
	}

	@ResponseBody
	@RequestMapping("/insert")
	public JSONResult insert(@RequestParam("name")String name, @RequestParam("password")String password, @RequestParam("message")String message) {
		GuestbookVo vo = new GuestbookVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setMessage(message);
		
		guestbookService.insert(vo); // insert후 방금 들어간 no값 가져오기
		
		System.out.println(vo);
		return JSONResult.success(vo);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public JSONResult delete(@RequestParam("no")Long no, @RequestParam("pw")String pw) {
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(pw);
		
		boolean data = guestbookService.delete(vo); // true = 1;
		return JSONResult.success(data);
	}
}
