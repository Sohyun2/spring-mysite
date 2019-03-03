package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;

@Repository
public class GalleryDao {
	
	@Autowired
	private SqlSession sqlSession;

	public List<GalleryVo> getList() {
		return sqlSession.selectList("gallery.getList");
	}

	public void upload(GalleryVo galleryVo) {
		sqlSession.insert("gallery.upload", galleryVo);
	}

	public void delete(String url) {
		sqlSession.delete("gallery.delete", url);
	}
}
