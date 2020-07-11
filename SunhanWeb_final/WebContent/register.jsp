<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="head.jsp" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인 페이지</title>
<script src="http://maps.googleapis.com/maps/api/js"></script>
<link rel="stylesheet" type="text/css" href="resoures/css/logincss.css">
<link rel="stylesheet" type="text/css" href="resoures/css/logincss2.css">
<link rel="icon" type="images/jpg" href="images/sunhan.jpg" />
<link rel="stylesheet" type="text/css"
	href="resoures/vendor/animate/animate.css">
<link rel="stylesheet" type="text/css"
	href="resoures/vendor/css-hamburgers/hamburgers.min.css">
<link rel="stylesheet" type="text/css"
	href="resoures/vendor/animsition/css/animsition.min.css">
<link rel="stylesheet" type="text/css"
	href="resoures/vendor/select2/select2.min.css">
<link rel="stylesheet" type="text/css"
	href="resoures/vendor/daterangepicker/daterangepicker.css">
<link href="resoures/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

<link rel="stylesheet" href="resoures/css/login.css">
<style>
@import url(//fonts.googleapis.com/earlyaccess/nanumgothic.css);

@import url(//fonts.googleapis.com/earlyaccess/notosansbengali.css);
</style>
</head>
<script type="text/javascript" src="resoures/js/member.js"></script>

<body>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<div class="container">
		<div class="row">
			<div class="col-md-9">
				<h4 class="text-center login" style="font-weight: bold;">회원 가입</h4>
			</div>
			<div class="col-md-3">
				<nav id="bread">
				<ol class="breadcrumb breadcrumbs">
					<li class="breadcrumb-item"><a href="main.jsp">홈</a></li>
					<li class="breadcrumb-item"><a href="mypage.jsp">마이페이지</a></li>
					<li class="breadcrumb-item active">회원가입</li>
				</ol>
				</nav>
			</div>
		</div>

		<div class="wrapper fadeInDown">
			<div id="formContent">
				<div class="limiter">
					<div class="container-login100">
						<div class="wrap-login100">
							<!-- Icon -->

							<div class="fadeIn first">

								<div class="login100-form-title"
									style="background-image: url(resoures/images/sunhan.jpg);">

								</div>
							</div>
							<form class="login100-form validate-form" action="regis.do"
								method="post" name="frm">
								<div>
									<div class="wrap-input100 validate-input m-b-26"
									data-validate="Username is required">
									<span class="label-input100">아이디</span> <input class="input100"
										type="text" name="userid" placeholder="ID"> <span
										class="focus-input100"></span>
									</div>
									<input type="hidden" name="reid" size="20">
								</div>
								

								<span><button type="button" onclick="idCheck()"
										class="login100-form-btnch">중복체크</button></span>
								<div>	
								<div class="wrap-input100 validate-input m-b-18"
									data-validate="Password is required">
									<span class="label-input100">별명</span> <input class="input100"
										type="text" name="name" placeholder="name"> <span
										class="focus-input100"></span>
								</div>
								</div>
								
								<div class="wrap-input100 validate-input m-b-18"
									data-validate="Password is required">
									<span class="label-input100">비밀번호</span> <input
										class="input100" type="password" name="pass"
										placeholder="pass"> <span class="focus-input100"></span>
								</div>
								<div class="wrap-input100 validate-input m-b-18"
									data-validate="Password is required">
									<span class="label-input100">주소</span> <input class="input100"
										type="text" name="addr" placeholder="addr"> <span
										class="focus-input100"></span>
								</div>
								<div class="wrap-input100 validate-input m-b-18"
									data-validate="Password is required">
									<span class="label-input100">전화번호</span> <input
										class="input100" type="text" name="phone"
										placeholder="phone"> <span class="focus-input100"></span>
								</div>
								<div class="wrap-input100 validate-input m-b-18"
									data-validate="Password is required">
									<span class="label-input100">이메일</span> <input class="input100"
										type="text" name="email" placeholder="email"> <span
										class="focus-input100"></span>
								</div>
								<div class="wrap-input100 validate-input m-b-18"
									data-validate="Password is required">
									<input type="radio" name="admin" value="0" checked="checked">
									일반회원 <input type="radio" name="admin" value="1"> 후원자
								</div>
								<div class="flex-sb-m w-full p-b-30">
									<div class="container-login100-form-btn">
										<button onclick="return joinCheck()"  type="submit"
											class="login100-form-btn" value="123">회원가입</button>
									</div>
								</div>
							</form> 
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="resoures/js/bootstrap.js"></script>
</body>
</html><jsp:include page="foot.jsp" />
