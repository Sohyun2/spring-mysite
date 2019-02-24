package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.UserVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	public Map<String, Object> list(int page) {
		int limit = 10;

		// ToDo: Pager algorithm
		// 원하는 페이지에 보여줄 리스트 뽑아오기
		List<BoardVo> list = boardDao.getList(page, limit); // 10개

		// 총 리스트 size구하기
		// List<BoardVo> listCount = new BoardDao().getList();
		int totalPageCount = boardDao.getCount();

		int startNum = totalPageCount;

		// 하단 페이지 번호를 위한 변수
		int listSize = totalPageCount;
		if (listSize % limit != 0) {
			listSize = listSize / limit + 1;
		} else {
			listSize /= limit;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("list_size", listSize);
		map.put("limit", limit);
		map.put("page", page);
		map.put("startNum", startNum);

		return map;
	}

	public Map<String, Object> detail(long no) {

		Map<String, Object> map = new HashMap<String, Object>();

		// 글 가져오기
		List<BoardVo> list = boardDao.getList();
		for (BoardVo vo : list) {
			if (vo.getNo() == no) {
				// 조회수 업데이트
				boardDao.updateHit(vo);
				map.put("vo", vo);
				break;
			}
		}

		// 댓글 리스트 가져오기
		List<CommentVo> cList = boardDao.getListComment(no);
		System.out.println(cList);
		
		map.put("cList", cList);
		
		return map;
	}
	
	public void insert(UserVo userVo, BoardVo boardVo) {
		System.out.println(userVo + " -- " + boardVo);
		
		boardVo.setUserNo(userVo.getNo());		
		System.out.println(boardVo);
		
		boardDao.insert(boardVo);
	}

	public void delete(Long no) {
		boardDao.delete(no);
	}

	public void modify(Long no, BoardVo vo) {
		vo.setUserNo(no);
		boardDao.update(vo);
	}

	public void reply(Long no, BoardVo vo, UserVo userVo) {

		BoardVo boardVo = boardDao.selectBoard(no); 
		
		int oNo = boardVo.getoNo();
		int gNo = boardVo.getgNo();
		int depth = boardVo.getDepth();
		
		vo.setoNo(++oNo);
		vo.setgNo(gNo);
		vo.setDepth(++depth);
		vo.setUserNo(userVo.getNo());
		vo.setNo(no);

		boardDao.insertReply(vo);
	}

	// 댓글 입력
	public void comment(Long no, CommentVo vo, UserVo userVo) {
		vo.setBoardNo(no); // 글번호
		vo.setUserNo(userVo.getNo());
		
		boardDao.insertComment(vo);
	}

	// 댓글 삭제
	public void commentDelete(Long commentNo) {
		boardDao.cDelete(commentNo);
	}
}
