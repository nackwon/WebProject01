<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}
		String toID = null;
		if(request.getParameter("toID") != null){
			toID = (String) request.getParameter("toID");
		}
		if(userID == null){
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "로그인을 해주시기 바랍니다.");
			response.sendRedirect("login.jsp");
			return;
		}
		if(toID == null){
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "대화 상대가 지정되지 않았습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
	%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.js"></script>

<!-- 글꼴 -->
<style>
@import url(http://fonts.googleapis.com/earlyaccess/nanumgothic.css);
@import url(http://fonts.googleapis.com/earlyaccess/hanna.css);
@import url(http://fonts.googleapis.com/earlyaccess/notosanskr.css);

</style>
	
<script type="text/javascript">
	function autoClosingAlert(selector, delay) {
		var alert = $(selector).alert();
		alert.show();
		window.setTimeout(function() {
			alert.hide()
		}, delay);
	}

	function submitFunction() {
		var fromID = '<%= userID%>';
		var toID = '<%= toID%>';
		var chatContent = $('#chatContent').val();
		$.ajax({
			type : "POST",
			url : "./chatSubmitServlet",
			data : {
				fromID : encodeURIComponent(fromID),
				toID : encodeURIComponent(toID),
				chatContent : encodeURIComponent(chatContent),
			},
			success : function(result) {
				if (result == 1)
					autoClosingAlert('#successMessage', 2000);
				else if (result == 0)
					autoClosingAlert('#dangerMessage', 2000);
				else
					autoClosingAlert('#warningMessage', 2000);
			}
		});
		$('#chatContent').val('');
	}
	
	var lastID = 0;
	function chatListFunction(type){
		var fromID = '<%= userID%>';
		var toID = '<%= toID%>';
		$.ajax({
			type: "POST",
			url: "./chatListServlet",
			data: {
				fromID : encodeURIComponent(fromID),
				toID : encodeURIComponent(toID),
				listType: type
			},
			success: function(data){
				if(data == "") return;
				var parsed = JSON.parse(data);
				var result = parsed.result;
				for(var i=0;i< result.length;i++){
					if(result[i][0].value == fromID){
						result[i][0].value = '나';
					}
					addChat(result[i][0].value, result[i][2].value,result[i][3].value);
				}
				lastID = Number(parsed.last);
			}
		})
	}
	function addChat(chatName, chatContent, chatTime){
		$('#chatList').append('<div class="row">' +
					'<div class="col-lg-12">' +
					'<div class="media">'+
					'<a class="pull-left" href="#">' +
					'<img class="media-object img-circle" style="width:30px; height:30px;" src="images/icon.png" alt="">'+
					'</a>' +
					'<div class="media-body">' +
					'<h4 class="media-heading">' +
					chatName +
					'<span class="small pull-right">'+
					chatTime +
					'</span>' +
					'</h4>' +
					'<p>' +
					chatContent +
					'</p>' +
					'</div>'+
					'</div>'+
					'</div>'+
					'</div>'+
					'<hr>');
		$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
	}
	function getInfiniteChat(){
		setInterval(function() {
			chatListFunction(lastID)
		}, 3000);
	}
</script>
<title>실시간 회원제 채팅 서비스</title>
</head>

<body>

	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="fasle">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.jsp">실시간 회원채팅 서비스</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="index.jsp">메인</a></li>
				<li><a href="find.jsp">친구찾기</a></li>
			</ul>
			<%
				if(userID != null){
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">회원관리 <span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="logoutAction.jsp">로그아웃</a></li>
						<li><a href="join.jsp">내 정보</a></li>
					</ul></li>
			</ul>
			<%
				}
			%>
		</div>
	</nav>
	<div class="container bootstrap snippet">
		<div class="row">
			<div class="col-xs-12">
				<div class="portlet portlet-default">
					<div class="portlet-heading">
						<div class="portlet-title">
							<h4>
								<i class="fa fa-circle text-green"></i>실시간 채팅 창
							</h4>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="panel-collapse collapse in" id="chat">
						<div id="chatList" class="portlet-body chat-widget" style="overflow-y: auto; height: 600px;"></div>
						<div class="portlet-footer">
							<div class="row" style="height: 90px;">
								<div class="form-group col-xs-10">
									<textarea style="height: 80px;" id="chatContent" class="form-control" placeholder="내용을 입력하세요" maxlength="100"></textarea>
								</div>
								<div class="form-group col-xs-2">
									<button type="button" class="btn btn-default pull-right" onclick="submitFunction();">전송</button>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="alert alert-success" id="successMessage" style="display: none;">
		<strong>메세지 전송에 성공했습니다.</strong>
	</div>
	<div class="alert alert-danger" id="dangerMessage" style="display: none;">
		<strong>이름과 내용 모두를 입력하세요.</strong>
	</div>
	<div class="alert alert-warning" id="warningMessage" style="display: none;">
		<strong>데이터베이스에 오류가 있습니다.</strong>
	</div>
	<%
		String messageContent = null;
		String messageType = null;
		if (session.getAttribute("messageContent") != null) {
			messageContent = (String) session.getAttribute("messageContent");
		}
		if (session.getAttribute("messageType") != null) {
			messageType = (String) session.getAttribute("messageType");
		}
		if (messageContent != null) {
	%>
	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content
					<%if (messageType.equals("오류 메세지"))
					out.println("panel-warning");
				else
					out.println("panel-success");%>">
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
							<%=messageType%>
						</h4>
					</div>
					<div class="modal-body">
						<%=messageContent%>
					</div>
					<div class="modal-footer">
						<button class="btn btn-primary" type="button" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$('#messageModal').modal("show");
		
	</script>
	<%
		session.removeAttribute("messageType");
			session.removeAttribute("messageContent");
		}
	%>
	<script type="text/javascript">
		$(document).ready(function(){
			chatListFunction('ten');
			getInfiniteChat();
		});
	</script>
</body>
</html>