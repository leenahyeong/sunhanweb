<%@page import="com.sumhan.dto.SunhansVO"%>
<%@page import="com.sumhan.dao.SunhansDAO"%>
<%@page import="file.FileDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${empty loginUser}">
	<jsp:forward page='login.do' />
</c:if>
<jsp:include page="head.jsp" />
<html>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<script type="text/javascript" src="resoures/js/member.js"></script>
<script>

function re2(){
var fileCheck = document.getElementById("profile").value;
if(!fileCheck){
	confirm("업로드할 파일을 선택하여 주십시오.");
    return false;
}};

function re(){  
      setTimeout('location.reload()',30000); 
}

$(function(){
    $('.pro').click(function(){
    	re2();	 re(); 
    });
}); 
</script>



<head>
<title>개인정보 설정</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="resoures/css/intRo.css">
<style>
.nav-tabs li .active:focus {
	color: #555;
    cursor: default;
    background-color: #fff;
    border: 1px solid #ddd;
    border-bottom-color: transparent;
}


.nav>li>a:focus, .nav>li>a:hover {
    background: initial !important;
}

</style>
</head>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<div class="container bootstrap snippet">
	<div class="row">
		<div class="col-md-9">
			<h3 class="text-left" style="font-weight: bold;">회원 수정</h3>
		</div>
		<div class="col-md-3">
			<nav id="bread">
				<ol class="breadcrumb breadcrumbs">
					<li class="breadcrumb-item"><a href="main.jsp">홈</a></li>
					<li class="breadcrumb-item"><a href="mypage.jsp">마이페이지</a></li>
					<li class="breadcrumb-item active">회원수정</li>
				</ol>
			</nav>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-3">
			<!--left col-->

			<div class="text-center">
			
                <c:choose>
				<c:when test="${proresult==1}">	
					<img src="${profile}" class="avatar img-circle img-thumbnail" alt="avatar">
					<form action="uploadAction.do" method="post" enctype="multipart/form-data">
					<input type="file" title="s" name="file" id="profile" value=" " class="text-center center-block file-upload">
					<input type="submit" class="pro" value="업로드" onclick="return re2()">
					<input type="hidden" name="userids" size="20" value="${loginUser.id}">
					</form>
				</c:when>
				<c:otherwise>	
					<img src="${realprofile}" class="avatar img-circle img-thumbnail" alt="avatar">
					<form action="uploadAction.do" method="post" enctype="multipart/form-data">
					<input type="file" title="s" name="file" id="profile" value=" " class="text-center center-block file-upload">
					<input type="submit" class="pro" value="업로드" onclick="return re2()">
					<input type="hidden" name="userids" size="20" value="${loginUser.id}">
					</form>
				</c:otherwise>
				</c:choose>
			</div>
			<br>


			<div class="panel panel-default">
				<div class="panel-heading">
					인사말 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
					<a href="http://bootnipets.com">안녕하세요.</a>
				</div>
			</div>


			<ul class="list-group">
				<li class="list-group-item text-muted">컨텐츠 <i
					class="fa fa-dashboard fa-1x"></i></li>
			</ul>

		</div>
		<!--/col-3-->
		<div class="col-sm-9">
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#home">기본 정보</a></li>
					<c:choose>
						<c:when test="${result==2}">
							<li><a data-toggle="tab" href="#messages">후원 가게 정보</a></li>
							<li><a data-toggle="tab" href="#settings">비밀번호 재설정</a></li>
						</c:when>
						<c:otherwise>
							<li><a data-toggle="tab" href="#settings">비밀번호 재설정</a></li>
						</c:otherwise>
					</c:choose>
			</ul>


			<div class="tab-content">
				<div class="tab-pane active" id="home">
					<form class="form" action="mypage.do" method="post" id="registrationForm" name="frm" onsubmit="return joinCheckd()">
						<div class="form-group">

							<div class="col-xs-6">
								<label for="id"><h4>아이디</h4></label> <input
									value="${loginUser.id}" readonly="readonly" type="text"
									class="form-control" name="first_name" id="first_name"
									placeholder="first name" title="enter your first name if any.">
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="name"><h4>닉네임</h4></label> <input type="text"
									value="${loginUser.name}" class="form-control" name="name"
									id="last_name" placeholder="last name"
									title="enter your last name if any.">
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="phone"><h4>
										<br>전화번호
									</h4></label> <input type="text" value="${loginUser.phone}"
									class="form-control" name="phone" id="phone"
									placeholder="enter phone"
									title="enter your phone number if any.">
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-6">
								<label for="email"><h4>
										<br>이메일
									</h4></label> <input type="text" value="${loginUser.email}"
									class="form-control" name="s" id="s" 
									placeholder="enter mobile number"
									title="enter your mobile number if any.">
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="point">
								<h4><br>포인트</h4></label> <input type="text" readonly="readonly"
									value="${loginUser.points}" class="form-control" name="email"
									id="email" placeholder="you@email.com"
									title="enter your email.">
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="password2"><h4>
										<br>주소
									</h4></label> 
									<input type="text" class="form-control" name="addr"
									id="adress" placeholder="adress"
									title="enter your adress" value="${loginUser.addr}">
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="text"><h4>
										<br> 권한
									</h4></label>
										<c:choose>
											<c:when test="${loginUser.admin==0}">
								<input type="text" class="form-control" name="admin" id="admin"
									readonly="readonly" value="일반회원">
											</c:when>
											<c:otherwise>
								<input type="text" class="form-control" name="admin" id="admin"
									readonly="readonly" value="후원자">
											</c:otherwise>
										</c:choose>
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="email"><h4>
										<br>생성날짜
									</h4></label> <input type="text" value="${loginUser.enter}"
									class="form-control" id="location" placeholder="somewhere"
									title="enter a location" readonly="readonly">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
								<br>
								<button value="123"  class="btn btn-lg btn-success"  >
									<i class="glyphicon glyphicon-ok-sign"></i> 저장
								</button>
								<button class="btn btn-lg" type="reset">
									<i class="glyphicon glyphicon-repeat"></i> 초기화
								</button>
							</div>
						</div>
					</form>


				</div>
				<!--/tab-pane-->
				<div class="tab-pane" id="messages">

					<h2></h2>

					<form class="form" action="##" method="post" id="registrationForm">
						<div class="form-group">

							<div class="col-xs-6">
								<label for="first_name"><h4><br>후원 가게 이름</h4></label> <input
									type="text" class="form-control" name="first_name"
									id="first_name" placeholder="후원을 시작해보세요! 후원자님" readonly="readonly" value="${loginStore.shopname}"
									title="enter your first name if any.">
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="last_name"><h4><br>상단 메세지</h4></label> <input
									type="text" class="form-control" name="last_name"
									id="last_name" placeholder="" readonly="readonly" value="${loginStore.topmessage}"
									title="enter your last name if any.">
							</div>
						</div>

						<div class="form-group">

							<div class="col-xs-6">
								<label for="phone"><h4><br>주소</h4></label> <input type="text"
									class="form-control" name="phone" id="phone"
									placeholder="" readonly="readonly" value="${loginStore.addr}"
									title="enter your phone number if any.">
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-6">
								<label for="mobile"><h4><br>음식</h4></label> <input type="text"
									class="form-control" name="mobile" id="mobile"
									placeholder="" readonly="readonly" value="${loginStore.food}"
									title="enter your mobile number if any.">
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="email"><h4><br>가격</h4></label> <input type="email"
									class="form-control" name="email" id="email" readonly="readonly" value="${loginStore.price}"
									placeholder="" title="enter your email.">
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="email"><h4><br>개설 날짜</h4></label> <input type="email"
									class="form-control" id="location" placeholder="" readonly="readonly" value="${loginStore.enter}"
									title="enter a location">
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="password"><h4><br>후원자</h4></label> <input
									type="text" class="form-control" name="password" readonly="readonly" value="${loginUser.name} (닉네임)"
									id="password" placeholder=""
									title="enter your password.">
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="password2"><h4><br>후원 점수</h4></label> <input
									type="text" class="form-control" name="password2" readonly="readonly" value="${loginUser.points}"
									id="password2" placeholder=""
									title="enter your password2.">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
								<br>
								<button class="btn btn-lg btn-success" type="submit">
									<i class="glyphicon glyphicon-ok-sign"></i> Save
								</button>
								<button class="btn btn-lg" type="reset">
									<i class="glyphicon glyphicon-repeat"></i> Reset
								</button>
							</div>
						</div>
					</form>

				</div>
				<!--/tab-pane-->
				<div class="tab-pane" id="settings">

					<form class="form" action="chpass.do" method="post" id="registrationForm" name="frmx" >
						
						<div class="form-group">

							<div class="col-xs-6">
								<label for="password"><h4>비밀번호</h4></label> <input
									type="password" class="form-control" name="password"
									id="password" placeholder="password"
									title="enter your password.">
							</div>
						</div>
						<div class="form-group">

							<div class="col-xs-6">
								<label for="password2"><h4>비밀번호 확인</h4></label> <input
									type="password" class="form-control" name="password2"
									id="password2" placeholder="password2"
									title="enter your password2.">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
								<br>
								<button class="btn btn-lg pull-right" value="123"
									style="background-color: pink" onclick="return passCheckd()">
									<i class="glyphicon glyphicon-ok-sign"></i> Save
								</button>
								<!--<button class="btn btn-lg" type="reset"><i class="glyphicon glyphicon-repeat"></i> Reset</button>-->
							</div>
						</div>
					</form>
				</div>

			</div>
			<!--/tab-pane-->
		</div>
		<!--/tab-content-->

	</div>
	<!--/col-9-->
</div>
<!--/row-->
</html>

<jsp:include page="foot.jsp" />