package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping("")
	public String index(Model model) {
		
		List<GalleryVo> list = galleryService.getList();
		model.addAttribute("list", list);
		
		return "gallery/index";
	}
	
	@RequestMapping("/upload")
	public String upload(@ModelAttribute GalleryVo galleryVo,
			@RequestParam("file") MultipartFile multipartFile) {
		System.out.println("in GalleryController/upload");
		
		String imageFile = fileUploadService.restore(multipartFile);
		galleryVo.setImageUrl(imageFile);
		
		galleryService.upload(galleryVo);
		
		return "redirect:/gallery";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("url")String url) {
		galleryService.delete(url);
		
		return "redirect:/gallery";
	}
}
