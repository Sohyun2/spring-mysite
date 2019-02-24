package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.UserVo;

@Repository
public class BoardDao {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSession sqlSession;

	// list 목록 뽑아오기	
	public List<BoardVo> getList(int page, int limit) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		int offset = (page - 1) * limit;
		map.put("limit", limit);
		map.put("offset", offset);

		List<BoardVo> list = sqlSession.selectList("board.getListByPage", map);
		//System.out.println(list);
		return list;
	}
	
	// list 뽑아오기.. ( 게시글 비교하기 위해서.. )
	public List<BoardVo> getList() {
		List<BoardVo> list = sqlSession.selectList("board.getList");

		return list;
	}
	
	// 게시글 insert
	public void insert(BoardVo vo) {
		Integer gNo = sqlSession.selectOne("board.getGNo");
		
		System.out.println(gNo);
		if(gNo == null) {
			gNo = 0;
		}
		vo.setgNo(++gNo);
		
		System.out.println(vo);
		sqlSession.insert("board.insert", vo);
	}
	
	// 게시글 삭제
	public void delete(long no) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("no", no);
		
		sqlSession.delete("board.delete", map);		
	}

	// 게시글 수정
	public void update(BoardVo vo) {
		sqlSession.update("board.modify", vo);
	}

	public int getCount() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		int count = 0;

		try {
			conn = dataSource.getConnection();

			stmt = conn.createStatement();

			// SQL문 실행
			String sql = "select count(*) from board";
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		return count;
	}

	// 조회수 올리기..
	public void updateHit(BoardVo vo) {
		int hit = sqlSession.selectOne("board.getHit", vo);
		hit++;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hit", hit);
		map.put("no", vo.getNo());
		
		sqlSession.update("board.updateHit", map);
	}

	//////////////////////// Reply.. 
	public void insertReply(BoardVo vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int oNo = vo.getoNo(); // 답글 작성할 게시글의 oNo 구하기
		if (oNo != 1) { // oNo가 1이면 원글에 답글작성
			// 답글에 답글에 답글에 답글에....................... 작성
			updateReply(vo);
		}
	
		sqlSession.insert("board.replyInsert", vo);
	}

	// oNo 값 update..
	public void updateReply(BoardVo vo) {
		sqlSession.update("board.updateReply", vo);
	}

	//////////////////////// comment..

	public void insertComment(CommentVo vo) {
		sqlSession.insert("board.insertComment", vo);
		
	}

	// 댓글 list 가져오기
	public List<CommentVo> getListComment(Long boardNo) {
		BoardVo boardVo = new BoardVo();
		boardVo.setNo(boardNo);
		List<CommentVo> list = sqlSession.selectList("board.getCommentList", boardVo);
				
		return list;
	}

	public void cDelete(long cNo) {
		CommentVo vo = new CommentVo();
		vo.setNo(cNo);
		
		sqlSession.delete("board.commentDelete", vo);
	}

	
	// .....?
	public BoardVo selectBoard(long no) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		BoardVo vo = new BoardVo();

		try {
			conn = dataSource.getConnection();

			stmt = conn.createStatement();

			// SQL문 실행
			String sql = "select o_no, g_no, depth from board where no=" + no;
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				vo.setoNo(rs.getInt(1));
				vo.setgNo(rs.getInt(2));
				vo.setDepth(rs.getInt(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
