<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">

<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${vo.contents }
							</div>
						</td>
					</tr>
				</table>
				
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board">목록</a>
					<c:if test="${!empty authuser.no }" >
						<a href="${pageContext.servletContext.contextPath }/board/reply?no=${vo.no }">답글</a>
					</c:if>
					<c:if test="${vo.userNo eq authuser.no }" >
						<a href="${pageContext.servletContext.contextPath }/board/modify?no=${vo.no }">수정</a>
						<a href="${pageContext.servletContext.contextPath }/board/delete?&no=${vo.no }">삭제</a>
					</c:if>
				</div>
				
				<!-- 댓글 리스트 출력 -->
				
				<table class="tbl-ex">
					<c:forEach items="${cList }" var="c_vo">
						<tr>
							<td class="label">${c_vo.userName }</td>
							<td>${c_vo.content }</td>
							<td>
								<c:if test="${c_vo.userNo eq authuser.no }" >
									<a href="${pageContext.servletContext.contextPath }/board/c_delete?no=${vo.no }&c_no=${c_vo.no }" class="del">
										<img src="${pageContext.servletContext.contextPath }/assets/images/recycle.png" />
									</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				
				
				<!-- 댓글등록.. -->
				<c:if test="${!empty authuser.no }" >
				<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board/comment?no=${vo.no }">
					<table class="tbl-ex">
						<tr>
							<td class="label">댓글</td>
							<td><input type="text" name="content" value="" style="width:410px"></td>
							<td><input type="submit" value="등록"></td>
						</tr>
					</table>
				</form>
				</c:if>
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>

</body>
</html>