package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;

	// 회원가입
	public int insert(UserVo vo) {
		return sqlSession.insert("user.insert", vo); //(user.xml에 있는 namespace.id,넘겨줄 객체)
	}

	// 로그인
	public UserVo get(String email, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);

		UserVo userVo = sqlSession.selectOne("user.getByEmailAndPassword", map);
		System.out.println(userVo);
		
		return userVo;
	}

	// 회원정보 수정
	public void update(UserVo vo) {
		sqlSession.update("user.modifyUserInfo", vo);
	}

	// 회원가입 시 이메일 존재유무 확인 method
	public UserVo get(String email) {
		return sqlSession.selectOne("user.getByEmail", email);
	}

	public UserVo get(long no) {		
		return sqlSession.selectOne( "user.getByNo", no );
	}

}