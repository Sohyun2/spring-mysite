package com.douzone.mysite.vo;

public class CommentVo {
	private Long no;
	private String content;
	private Long userNo;
	private String userName;
	private Long boardNo;
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(Long boardNo) {
		this.boardNo = boardNo;
	}
	
	@Override
	public String toString() {
		return "CommentVo [content=" + content + ", userNo=" + userNo + ", boardNo=" + boardNo + "]";
	}	
}
