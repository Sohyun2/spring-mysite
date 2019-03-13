<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript">
$(function(){
	$("#join-form").submit(function(){
		/*
		//1. 이름 체크
		if($("#name").val() == ""){
			alert("이름은 필수 입력 항목입니다.");
			$("#name").focus();
			return false;
		}
		
		//2-1. 이메일이 비어 있는 지 확인
		if($("#email").val() == ""){
			alert("이메일은 필수 입력 항목입니다.");
			$("#email").focus();
			return false;
		}
		*/
		//2-2. 이메일 중복체크 유무
		// 중복 체크를 할 경우 체크이미지가 보인다.
		// 이미지가 보이지 않을 경우 중복체크를 하지 않은 경우이므로..
		if($("#img-checkemail").is(":visible") == false) {
			alert("이메일 중복 체크를 해야합니다.");
			$("#email").focus();
			return false;
		}
		/*
		//3. 비밀번호 확인
		if($("input[type='password']").val() == ""){
			alert("비밀번호는 필수 입력 항목입니다.");
			$("input[type='password']").focus();
			return false;
		}
		*/
		//4. 약관동의
		if($("#agree-prov").is(":checked") == false){
			alert("약관 동의를 해야 합니다.");
			return false;
		}
		
		return true;
	});
	
	$("#email").change(function() {
		$("#btn-checkemail").show();
		$("#img-checkemail").hide();
	});
	
	$("#btn-checkemail").click(function(){
		var email = $("#email").val();
		if(email == ""){
			return;
		}
		
		$.ajax({
			url: "${pageContext.servletContext.contextPath }/user/api/checkemail",
			type: "post",
			dataType: "json",
			data: "email=" + email,
			success: function(response){
				if(response.data == true){
					alert("이미 존재하는 이메일입니다. 다른 이메일을 사용해 주세요.");
					$("#email").val("").focus();
					return;
				}
				
				// 사용가능한 이메일
				$("#btn-checkemail").hide();
				$("#img-checkemail").show();
			},
			error: function(xhr, status, e){
				console.error(status + ":" + e);
			}
		});
	});
});
</script>
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">
				<form:form  modelAttribute="userVo" id="join-form" name="joinForm" method="post" action="${pageContext.servletContext.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="${userVo.name }">
					<spring:hasBindErrors name="userVo">
   						<c:if test="${errors.hasFieldErrors('name') }">
   							<p style="padding: 5px 0 0 0; text-align:left; color:red">
        						<strong>
        							<spring:message 
        								code="${errors.getFieldError( 'name' ).codes[0] }"
        								text="${errors.getFieldError( 'name' ).defaultMessage }" />
        						</strong>
        					</p>
   						</c:if>
					</spring:hasBindErrors>

					<label class="block-label" for="email">이메일</label>
					<form:input path="email" />
					
					<img id="img-checkemail" style="width:25px; display:none"src="${pageContext.servletContext.contextPath }/assets/images/check.png">
					<input id="btn-checkemail" type="button" value="이메일확인">
					<p style="margin:0; padding:0; font-weight:bold; color:red; text-align:left">
						<form:errors path="email" />
					</p>
					
					<label class="block-label">패스워드</label>
					<!-- <input name="password" type="password" value=""> -->
					<form:password path="password" />
					<p style="margin:0; padding:0; font-weight:bold; color:red; text-align:left">
						<form:errors path="password" />
					</p>
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
				</form:form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"/>
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>