package com.douzone.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class SiteDao {

	@Autowired
	private SqlSession sqlSession;
	
	// select
	public SiteVo getInfo() {
		return sqlSession.selectOne("site.getInfo");
	}

	public void update(SiteVo vo) {
		sqlSession.update("site.update", vo);
	}

	public SiteVo getMainInfo() {
		return sqlSession.selectOne("site.getMainInfo");
	}
}
