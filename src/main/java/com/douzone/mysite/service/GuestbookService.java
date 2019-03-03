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

	public long insert(GuestbookVo vo) {
		return guestbookDao.insert(vo);
	}
	
	public boolean delete(GuestbookVo vo ) {
		return guestbookDao.delete(vo);
	}
	

	// ajax
	public List<GuestbookVo> getList(Integer p) {
		p = (p-1) * 5;
		return guestbookDao.getList(p);
	}
}
