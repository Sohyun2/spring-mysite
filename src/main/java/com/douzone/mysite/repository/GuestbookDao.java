package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {

	@Autowired
	private SqlSession sqlSession;
	
	// list 가져오기
	public List<GuestbookVo> getList() {
		List<GuestbookVo> list = sqlSession.selectList("guestbook.getList");

		return list;
	}

	// guestbook 등록
	public long insert(GuestbookVo vo) {
		long no = sqlSession.insert("guestbook.insert", vo);	
		return no;
	}

	// guestbook 삭제
	public boolean delete(GuestbookVo vo) {
		return 1 == sqlSession.delete("guestbook.delete", vo);
	}
	
	// ?
	public GuestbookVo get(long no) {
		GuestbookVo vo = new GuestbookVo();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = GetConnection.getConnection();

			// Statement 객체 생성
			stmt = conn.createStatement();

			// SQL문 실행
			String sql = "select * from guestbook where no = " + no;
			rs = stmt.executeQuery(sql);

			// 결과 가져오기(사용하기)
			if (rs.next()) {
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setMessage(rs.getString(3));
				vo.setRegDate(rs.getString(4));
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vo;
	}

	////////////////////// ajax /////////////////////
	// ajax list 가져오기
	public List<GuestbookVo> getList(Integer p) {
		return sqlSession.selectList("guestbook.getListByPage", p);
	}

}
