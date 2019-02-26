package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookDao;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookDao guestbookDao;
	
	public List<GuestbookVo> getList() {
		List<GuestbookVo> list = guestbookDao.getList();
		return list;
	}

	public void insert(GuestbookVo vo) {
		guestbookDao.insert(vo);
	}
	
	public void delete(GuestbookVo vo ) {
		guestbookDao.delete(vo);
	}
	

	// ajax
	public List<GuestbookVo> getList(Integer p) {
		p = (p-1) * 5;
		return guestbookDao.getList(p);
	}
}
