package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
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
			rs = stmt.executeQuery( sql );

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
	
	public int delete( GuestbookVo vo ) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = GetConnection.getConnection();

			String sql = 
				" delete" + 
				"   from guestbook" + 
				"  where no=?" +
				"    and password=?";
			pstmt = conn.prepareStatement( sql );

			pstmt.setLong( 1, vo.getNo() );
			pstmt.setString( 2, vo.getPassword() );

			count = pstmt.executeUpdate(); //success : 1 / fail : 0
		} catch (SQLException e) {
			System.out.println("error :" + e);
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

		return count;		
	}
	
	public long insert(GuestbookVo vo) {
		//int count = 0;
		long insertId = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = GetConnection.getConnection();

			String sql = 
				" insert" + 
				"   into guestbook" + 
				" values ( null, ?, ?, ?, now() )";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());

			pstmt.executeUpdate();

			stmt = conn.createStatement();
			String getNoSql = "select last_insert_id()";
			rs = stmt.executeQuery(getNoSql);
			
			if(rs.next()) {
				insertId = rs.getLong(1);
			}						
		} catch (SQLException e) {
			System.out.println("error :" + e);
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

		return insertId;
	}

	public List<GuestbookVo> getList() {
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = GetConnection.getConnection();

			// Statement 객체 생성
			stmt = conn.createStatement();

			// SQL문 실행
			String sql =
				"   select no," + 
				"          name," + 
				"	       message," + 
				"     	   date_format(reg_date, '%Y-%m-%d %h:%i:%s')" + 
				"     from guestbook" + 
				" order by reg_date desc";
			rs = stmt.executeQuery( sql );

			// 결과 가져오기(사용하기)
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String message = rs.getString(3);
				String regDate = rs.getString(4);

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setMessage( message );
				vo.setRegDate( regDate );

				list.add(vo);
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

		return list;
	}
	

	public List<GuestbookVo> getList(int page) {
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = GetConnection.getConnection();


			// SQL문 준비
			String sql =
				"   select no," + 
				"          name," + 
				"	       message," + 
				"     	   date_format(reg_date, '%Y-%m-%d %h:%i:%s')" + 
				"     from guestbook" + 
				" order by reg_date desc" +
				"	  limit ?, 5";
			
			// Statement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (page-1)*5);
			
			rs = pstmt.executeQuery();

			// 결과 가져오기(사용하기)
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String message = rs.getString(3);
				String regDate = rs.getString(4);

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setMessage( message );
				vo.setRegDate( regDate );

				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
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

		return list;
	}
	
//
//	private Connection getConnection() throws SQLException {
//		Connection conn = null;
//		try {
//			//1. 드라이버 로딩
//			Class.forName( "com.mysql.jdbc.Driver" );
//			
//			//2. 연결하기
//			String url="jdbc:mysql://localhost/webdb?characterEncoding=utf8&serverTimezone=UTC";
//			conn = DriverManager.getConnection(url, "webdb", "webdb");
//		} catch( ClassNotFoundException e ) {
//			System.out.println( "드러이버 로딩 실패:" + e );
//		} 
//		
//		return conn;
//	}	

}
