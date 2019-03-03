package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GalleryDao;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryDao galleryDao;

	public List<GalleryVo> getList() {
		return galleryDao.getList();
	}

	public void upload(GalleryVo galleryVo) {
		galleryDao.upload(galleryVo);
	}

	public void delete(String url) {
		galleryDao.delete(url);
	}
	
}
