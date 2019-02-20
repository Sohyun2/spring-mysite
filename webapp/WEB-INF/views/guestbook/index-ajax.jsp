<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style type="text/css">
	#dialog-delete-form p {
		padding: 10px;
		font-weight: bold;
		font-size: 1.0em;
	}
	#dialog-delete-form input[type="password"] {
		padding: 5px;
		outline: none;
		width: 180px;
		border: 1px solid #888;
	}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

	// ---------custom jquery plug-in 만들어보기~
	(function(a) {
		$.fn.hello = function() {
			console.log($(this).attr("id") + "-------> hello");
		}
	})(jQuery);

	
	var page = 0;
	var isEnd = false;
	var no = 0;

	var messageBox = function(title, message) {
		$("#dialog-message").attr("title", title);
		$("#dialog-message p").text(message);
		$("#dialog-message").dialog({
			modal: true, // true = doModalDialog / false = modalDialog
			buttons: {
				"확인": function() {
					$(this).dialog("close");
				}
			}
		});
	}

	var render = function(vo, mode) {
		var htmls = "<li data-no='" + vo.no +"'>" + "<strong>" + vo.name
				+ "</strong>" + "<p>" + vo.message.replace(/\n/g, "<br>")
				+ "</p>" + "<strong></strong>"
				+ "<a href='' data-no='" + vo.no + "'>삭제</a>" + "</li>";

		if (mode == true) {
			$("#list-guestbook").prepend(htmls);
		} else {
			$("#list-guestbook").append(htmls);
		}
	}

	var fetchList = function() {
		if (isEnd == true) {
			return;
		}

		++page;
		$.ajax({
			url : "/mysite2/api/guestbook?a=ajax-list&p=" + page,
			type : "get",
			dataType : "json",
			data : "",
			success : function(response) {
				if (response.result == "fail") {
					console.warn(response.data);
					return;
				}

				console.log(response.data);

				// 페이지 끝을 검출
				if (response.data.length < 5) {
					isEnd = true;
					$("#btn-next").prop("disabled", true);
				}

				// rendering
				$.each(response.data, function(index, vo) {
					render(vo, false);
				});
			},
			error : function(xhr, status, e) {
				console.error(status + ":" + e);
			}
		});
	}

	$(function() {		
		var dialogDelete = $("#dialog-delete-form").dialog({
			autoOpen: false,
			modal: true,
			buttons: {
				"삭제": function(){
					console.log("ajax 삭제작업");
					
					//var no = $("#list-guestbook li a").data("no");
					var pw = $("#password-delete").val();
					console.log(no);
					console.log(pw);
					
					$.ajax({
						url: "/mysite2/api/guestbook",
						type: "post",
						dataType: "json",
						data: "a=ajax-delete&no=" + no + "&pw=" + pw,
						success: function(response) {
							if(response.result == 1) {
								console.log("삭제 성공");
								// dialog clear
								$("#password-delete").val("");
								dialogDelete.dialog("close");
								$("#list-guestbook li[data-no="+ no +"]").remove();
							} else {
								console.log("삭제 실패");
								
								$(".validateTips-normal").css({
									display: "none"
								});
								$(".validateTips-error").css({
									display:""
								});
								// dialog clear
								$("#password-delete").val("");
							}
						}, 
						error: function(xhr, status, e) {
							console.error(status + " : " + e);
						}					
					});						
				},
				"취소": function() {
					dialogDelete.dialog("close");
				}				
			},
			close: function(){
				//close됐을 때 실행해야할 코드
				console.log("close시 뒤처리..");
			}
		});
		
		
		// live event
		$(document).on("click", "#list-guestbook li a", function() {
			event.preventDefault();
			
			no = $(this).data("no");
			console.log("clicked" + no);
			dialogDelete.dialog("open");
		});
		
		//메시지 등록 폼 submit 이벤트
		$("#add-form").submit(function(event) {
			// submit의 기본동작(post)
			// 막아야한다.
			event.preventDefault(); //기본동작을 막음

			//validate form data
			var name = $("#input-name").val();
			if (name == "") {
				messageBox("글남기기", "이름은 필수 항목입니다.");
				return;
			}
			var password = $("#input-password").val();
			if (password == "") {
				messageBox("글남기기", "비밀번호는 필수 항목입니다.");
				return;
			}
			var message = $("#tx-content").val();
			if (message == "") {
				messageBox("글남기기", "내용은 필수 항목입니다.");
				return;
			}
			
			console.log("name : " + name);
			console.log("password : " + password);
			console.log("message : " + message);
			
			$.ajax({
				url: "/mysite2/api/guestbook",
				type: "post",
				dataType: "json",
				data: "a=ajax-insert&name=" + name + "&password=" + password + "&message=" + message,
				success: function(response) {
					if (response.result == "fail") {
						console.warn(response.data);
						return;
					}
					fetchList();
					//console.log(response.data);
					//render(response.data, true);
				},
				error: function(xhr, status, e) {
					//console.error(status + " : " + e);
				}
			});
		});

		$("#btn-next").click(function() {
			fetchList();
		});

		// 스크롤 이벤트
		$(window).scroll(function() {
			var $window = $(this);
			var scrollTop = $window.scrollTop();
			var windowHeight = $window.height();
			var documentHeight = $(document).height();

			//console.log(scrollTop + " : " + windowHeight + " : " + documentHeight);
			if (scrollTop + windowHeight + 10 > documentHeight) {
				console.log("fetch ajax starting...");
			}
		});

		// 최초 리스트 가져오기
		// fetchList();
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름"> 
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>

				<ul id="list-guestbook">

				</ul>

				<!-- 수정사항 -->
				<button id="btn-next">다음</button>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips-normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips-error" style="display: none">비밀번호가 틀립니다.</p>
				<form>
					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all"> 
					<input type="hidden" id="hidden-no" value="" name="no"> 
					<input type="submit" tabindex="-1" style="position: absolute; top: -1000px">
				</form>
			</div>
			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>