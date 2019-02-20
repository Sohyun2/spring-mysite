package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;

@Repository
public class BoardDao {

	public int getCount() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		int count = 0;

		try {
			conn = GetConnection.getConnection();

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

	public List<BoardVo> getList(int page, int limit) {
		List<BoardVo> list = new ArrayList<BoardVo>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = GetConnection.getConnection();

			stmt = conn.createStatement();

			long offset = (page - 1) * limit;

			// SQL문 실행
			String sql = "select board.*, user.name" + "  from board, user" + "  where board.user_no = user.no"
					+ "  order by board.g_no desc, board.o_no asc" + "  limit " + limit + " offset " + offset;
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				BoardVo vo = new BoardVo();

				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setWriteDate(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setgNo(rs.getInt(6));
				vo.setoNo(rs.getInt(7));
				vo.setDepth(rs.getInt(8));
				vo.setUserNo(rs.getLong(9));
				vo.setUserName(rs.getString(10));

				list.add(vo);
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

		return list;
	}

	public List<BoardVo> getList() {
		List<BoardVo> list = new ArrayList<BoardVo>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = GetConnection.getConnection();

			stmt = conn.createStatement();

			// SQL문 실행
			String sql = "select board.*, user.name" + "  from board, user" + "  where board.user_no = user.no"
					+ "  order by board.g_no desc, board.o_no asc";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				BoardVo vo = new BoardVo();

				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setWriteDate(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setgNo(rs.getInt(6));
				vo.setoNo(rs.getInt(7));
				vo.setDepth(rs.getInt(8));
				vo.setUserNo(rs.getLong(9));
				vo.setUserName(rs.getString(10));

				list.add(vo);
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

		return list;
	}

	public int insert(BoardVo vo) {
		int count = 0;

		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int gNo = 0;

		try {
			conn = GetConnection.getConnection();

			stmt = conn.createStatement();
			// 입력될 group_no의 갯수 구하기..
			String sql = "select max(g_no) from board";
			rs = stmt.executeQuery(sql);
			if (rs.next())
				gNo = rs.getInt(1);

			//// insert..
			// no, title, contents, write_date, hit, g_no, o_no, depth, user_no
			sql = "  insert" + " into board" + "  values ( null, ?, ?, now(), 0, ?, 1, 0, ? )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, ++gNo);
			pstmt.setLong(4, vo.getUserNo());

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				if (rs != null) {
					conn.close();
				}
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;
	}

	public void delete(long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = GetConnection.getConnection();

			String sql = "delete from board where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = GetConnection.getConnection();

			String sql = "update board " + "  set title=?, contents = ?, write_date = now()" + "  where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateHit(BoardVo vo) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = GetConnection.getConnection();

			stmt = conn.createStatement();
			String sql = "select hit from board where no = " + vo.getNo();
			rs = stmt.executeQuery(sql);

			int hit = 0;
			if (rs.next()) {
				hit = rs.getInt(1);
			}

			sql = "update board set hit = ? where no = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ++hit);
			pstmt.setLong(2, vo.getNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				if (rs != null) {
					conn.close();
				}
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//////////////////////// Reply..
	public void insertReply(BoardVo vo) {

		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = GetConnection.getConnection();
			stmt = conn.createStatement();

			// 입력될 o_no의 갯수 구하기..
			int oNo = vo.getoNo(); // 1

			if (vo.getoNo() == 1) {
				// 원글에 답글 달기..
				String sql = "select max(o_no) from board where g_no = " + vo.getgNo();
				rs = stmt.executeQuery(sql);

				if (rs.next())
					oNo = rs.getInt(1);
			}

			if (oNo != 1)
				updateReply(vo);

			// no, title, content, write_date, hit, g_no, o_no, depth, user_no
			String sql = "insert into board values (null, ?, ?, now(), 0, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getgNo());
			pstmt.setInt(4, oNo);
			pstmt.setInt(5, vo.getDepth());
			pstmt.setLong(6, vo.getUserNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				if (rs != null) {
					conn.close();
				}
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateReply(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = GetConnection.getConnection();

			String sql = "update board set o_no = o_no+1 " + "  where g_no = ?" + "  and o_no >= ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, vo.getgNo());
			pstmt.setInt(2, vo.getoNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//////////////////////// comment..

	public void insertComment(CommentVo vo) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = GetConnection.getConnection();

			String sql = "insert into comment values (null, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getContent());
			pstmt.setLong(2, vo.getUserNo());
			pstmt.setLong(3, vo.getBoardNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<CommentVo> getListComment(Long board_no) {
		List<CommentVo> list = new ArrayList<CommentVo>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = GetConnection.getConnection();

			stmt = conn.createStatement();

			// SQL문 실행
			String sql = "select no, content, user_no from comment where board_no = " + board_no + "  order by no desc";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Long no = rs.getLong(1);
				String content = rs.getString(2);
				Long userNo = rs.getLong(3);

				// 회원 번호로 이름 검색하기
				String userName = selectUserName(userNo);

				CommentVo vo = new CommentVo();
				vo.setNo(no);
				vo.setContent(content);
				vo.setUserNo(userNo);
				vo.setUserName(userName);

				list.add(vo);
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

		return list;
	}

	private String selectUserName(Long userNo) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String userName = "";

		try {
			conn = GetConnection.getConnection();

			stmt = conn.createStatement();

			// SQL문 실행
			String sql = "select name from user where no=" + userNo;
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				userName = rs.getString(1);
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

		return userName;
	}

	public void cDelete(long cNo) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = GetConnection.getConnection();

			String sql = "delete from comment where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, cNo);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public BoardVo selectBoard(long no) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		BoardVo vo = new BoardVo();

		try {
			conn = GetConnection.getConnection();

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
