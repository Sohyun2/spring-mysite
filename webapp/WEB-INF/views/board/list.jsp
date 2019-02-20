<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<div id="board">
			<table class="tbl-ex">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>글쓴이</th>
					<th>조회수</th>
					<th>작성일</th>
					<th>&nbsp;</th>
				</tr>
				<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items="${list }" var="vo" varStatus="status"> 
						<tr>
							<td>${startNum - ((page-1) * limit) - status.index }</td>
														
							<c:choose>
								<c:when test="${vo.depth == 0 }">
									<td><a href="${pageContext.servletContext.contextPath }/board/detail?no=${vo.no }">${vo.title }</a></td>
								</c:when>
								<c:otherwise>
									<td style="padding-left:${50*vo.depth}px">
										<img src = "${pageContext.servletContext.contextPath }/assets/images/reply.png" style="width:10px"/>
										<a href="${pageContext.servletContext.contextPath }/board/detail?page=${page }&no=${vo.no }">${vo.title }</a>
									</td>
								</c:otherwise>
							</c:choose>
														
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.writeDate }</td>
							<td>
								<c:if test="${vo.userNo eq authuser.no }" >
									<a href="${pageContext.servletContext.contextPath }/board/delete?&no=${vo.no }" class="del">
										<img src="${pageContext.servletContext.contextPath }/assets/images/recycle.png" />
									</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
			</table>
			
			
			<!-- pager 추가 -->
			<c:set var="list_size" value="${list_size }" />
			<div class="pager">
				<ul>
					<li><a href="${pageContext.servletContext.contextPath }/board?page=1">◀</a></li>
					
					<c:forEach var="i" begin="1" end="${list_size }" step="1">
						<li><a href="${pageContext.servletContext.contextPath }/board?page=${i }">${i }</a></li>
					</c:forEach>
					
					<li><a href="${pageContext.servletContext.contextPath }/board?page=${list_size }">▶</a></li>
				</ul>
			</div>					
			<!-- pager 추가 -->
			
			<c:if test="${!empty authuser }" >
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board/insert" id="new-book">글쓰기</a>
				</div>
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