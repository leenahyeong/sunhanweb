<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:if test="${empty loginUserID}">
	<jsp:forward page='login.do' />
</c:if>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>마이 페이지</title>
<link href="resoures/css/bootstrap.min.css" rel="stylesheet">
<link href="resoures/cssmain/custom.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet" href="resoures/css/intRo.css">
<style>
#bttn:HOVER {
	color: white;
}

#footer {
	position: relative !important;
}


@media (max-width : 1199px) {
	#footer{
		position: initial !important;
	}
}

.jumbotron {
	background: rgba(0, 0, 0, .5) !important;
}

.row{
	border-bottom: none;
}

.cata_div{
	margin-bottom: 30px;
	min-height: 327px;
	border-bottom: none !important;
}

.card-header, .card-body {
	border: 1px solid #ddd !important;
}
.card-body{
	border-top: none !important;
	min-height: 272.6px;
}

.cata_div button {
	color: #7ab94b !important;
	border: 0.5px solid #94ce68 !important;
}

.cata_div button:focus{
	outline: none !important;
	box-shadow: none;
}

button:focus, input:focus, textarea:focus, select:focus, input:active {
	outline: none !important;
}

.cata_div button:hover{
	background:#94ce68 !important;
	color:#fff !important;
	border: 0.5px solid #94ce68;
}

.mobile-nav-toggle{
	border: none !important;
	background: transparent !important;
}
</style>
</head>

<body>
	<jsp:include page="head.jsp" />
	<div class="container-flude">
		<section class="auto cover_img">
			<div class="jumbotron jumbotron-fluid">
				<div class="container">
					<h1 class="display-4">마이페이지</h1>
					<p class="lead">정보를 확인해보세요.</p>
				</div>
			</div>
		</section>
	</div>
	<div class="container" style="margin-top: 10vh">
		
		<div class="card-deck text-center">
			<div class="col-lg-3 col-md-6 col-xs-3 col-lg-offset-3 cata_div">
				<div class="card-header">
					<h4 class="my-0 font-weight-normal">
						<font style="vertical-align: inherit;"><font
							style="vertical-align: inherit;">본인 정보</font></font>
					</h4>
				</div>
				<div class="card-body">
					<h1 class="card-title pricing-card-title">
						<font style="vertical-align: inherit;"><small
							class="text-muted"><font style="vertical-align: inherit;"></font></small><font
							style="vertical-align: inherit;">${loginUser.name}</font></font><small
							class="text-muted"><font style="vertical-align: inherit;"></font></small>
					</h1>
					<ul class="list-unstyled mt-3 mb-4">
						<li><font style="vertical-align: inherit;"><font
								style="vertical-align: inherit;">아이디 : ${loginUser.id}</font></font></li>
						<li><font style="vertical-align: inherit;"><font
								style="vertical-align: inherit;">닉네임 : ${loginUser.name}</font></font></li>
						<li><font style="vertical-align: inherit;"><font
								style="vertical-align: inherit;">전화번호: ${loginUser.phone}</font></font></li>
						<li><font style="vertical-align: inherit;"><font
								style="vertical-align: inherit;">이메일 : ${loginUser.email}</font></font></li>
					</ul>
					<button type="button"
						onclick="location.href='mypageEditServlet.do'"
						class="btn btn-lg btn-block btn-outline-primary">
						<font style="vertical-align: inherit;"><font
							style="vertical-align: inherit;">정보 수정</font></font>
					</button>
				</div>
			</div>
			<div class="col-lg-3 col-md-6 col-xs-3 col-lg-offset-3 cata_div">
				<div class="card-header">
					<h4 class="my-0 font-weight-normal">
						<font style="vertical-align: inherit;"><font
							style="vertical-align: inherit;">후원 이력</font></font>
					</h4>
				</div>
				<div class="card-body">
					<h1 class="card-title pricing-card-title">
						<font style="vertical-align: inherit;"><small
							class="text-muted"><font style="vertical-align: inherit;"></font></small><font
							style="vertical-align: inherit;">후원 가게</font></font><small
							class="text-muted"><font style="vertical-align: inherit;"></font></small>
					</h1>
					<c:choose>
						<c:when test="${loginUser.admin==1}">
							<c:choose>
								<c:when test="${storech==1}">
									<ul class="list-unstyled mt-3 mb-4">
										<li><font style="vertical-align: inherit;"><font
												style="vertical-align: inherit;">가게 이름 :
													${loginStore.shopname} </font></font></li>
										<li><font style="vertical-align: inherit;"><font
												style="vertical-align: inherit;">후원 음식 :
													${loginStore.food}</font></font></li>
										<li><font style="vertical-align: inherit;"><font
												style="vertical-align: inherit;">후원시작일 :
													${loginStore.enter} </font></font></li>
										<li><font style="vertical-align: inherit;"><font
												style="vertical-align: inherit;">후원 지역 :
													${loginStore.area}</font></font></li>
									</ul>
									<button id="bttn" type="button"
										onclick="location.href='myStoreEdit.do'"
										class="btn btn-lg btn-block btn-outline-primary">
										<font style="vertical-align: inherit;"><font
											style="vertical-align: inherit;" id="bttn">정보 수정</font></font>
									</button>
									
									
								</c:when>
								<c:otherwise>
									<ul class="list-unstyled mt-3 mb-4">
										<li><font style="vertical-align: inherit;"><font
												style="vertical-align: inherit;"></font></font></li>
										<li><font style="vertical-align: inherit;"><font
												style="vertical-align: inherit;">지금 바로 후원을 시작해보세요 !</font></font></li>
										<li><font style="vertical-align: inherit;"><font
												style="vertical-align: inherit;"></font></font></li>
										<li><font style="vertical-align: inherit;"><font
												style="vertical-align: inherit;"></font></font></li>
									</ul>
									<br>
									<br>
									<br>
									<button type="button" 
										onclick="location.href='myStoreEdit.do'"
										class="btn btn-lg btn-block btn-outline-primary">
										<font style="vertical-align: inherit;"><font
											style="vertical-align: inherit;" id="bttn">후원 시작</font></font>
									</button>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<ul class="list-unstyled mt-3 mb-4">
								<li><font style="vertical-align: inherit;"><font
										style="vertical-align: inherit;"><br></font></font></li>
								<li><font style="vertical-align: inherit;"><font
										style="vertical-align: inherit;">후원자가 아닙니다.</font></font></li>
								<li><font style="vertical-align: inherit;"><font
										style="vertical-align: inherit;"><br></font></font></li>
								<li><font style="vertical-align: inherit;"><font
										style="vertical-align: inherit;"><br></font></font></li>
							</ul>
							<button type="button"
								class="btn btn-lg btn-block btn-outline-primary">
								<font style="vertical-align: inherit;"><font
									style="vertical-align: inherit;" id="bttn">후원 관리</font></font>
							</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<br>
			<div class="col-lg-3 col-md-6 col-xs-3 col-lg-offset-3 cata_div">
				<div class="card-header">
					<h4 class="my-0 font-weight-normal">
						<font style="vertical-align: inherit;"><font
							style="vertical-align: inherit;">예약 페이지</font></font>
					</h4>
				</div>
				<div class="card-body">
					<h1 class="card-title pricing-card-title">
						<font style="vertical-align: inherit;"><font
							style="vertical-align: inherit;">예약 : </font></font><small
							class="text-muted"><font style="vertical-align: inherit;"><font
								style="vertical-align: inherit;">${count}건</font></font></small>
					</h1>
					
					<ul class="list-unstyled mt-3 mb-4" style="min-height: 96px;">
					</ul>

					<c:choose>
						<c:when test="${loginUser.admin == 1}">
							<c:choose>
								<c:when test="${storech==1}">
									<button id="bttn" type="button"
										class="btn btn-lg btn-block btn-outline-primary pink_btn"
										onClick="location.href='reservesupporter.do?userid=${loginUserID}'">
										예약 정보 보기</button>
								</c:when>

								<c:otherwise>
									<h4>가게 등록을 먼저 해주세요.</h4>
								</c:otherwise>
							</c:choose>

						</c:when>

						<c:otherwise>
							<button id="bttn" type="button"
								class="btn btn-lg btn-block btn-outline-primary pink_btn"
								onClick="location.href='reservechild.do?userid=${loginUserID}'">
								예약정보보기</button>
						</c:otherwise>
					</c:choose>
				</div>

			</div>
			<div class="col-lg-3 col-md-6 col-xs-3 col-lg-offset-3 cata_div">
				<div class="card-header">
					<h4 class="my-0 font-weight-normal">
						<font style="vertical-align: inherit;"><font
							style="vertical-align: inherit;">리뷰 페이지</font></font>
					</h4>
				</div>
				<div class="card-body">
					<h1 class="card-title pricing-card-title">
						<font style="vertical-align: inherit;"><font
							style="vertical-align: inherit;">리뷰 : </font></font><small
							class="text-muted"><font style="vertical-align: inherit;"><font
								style="vertical-align: inherit;"> ${review_count.get(0)}건</font></font></small>
					</h1>
					
					<ul class="list-unstyled mt-3 mb-4" style="min-height: 96px;">
					</ul>
					<c:choose>
						<c:when test="${loginUser.admin == 1}">
							<button id="bttn" type="button"
								class="btn btn-lg btn-block btn-outline-primary"
								onClick="location.href='review.do?userid=${loginUserID}'">
								<font style="vertical-align: inherit;"><font
									style="vertical-align: inherit;" id="bttn">가게 리뷰 보기</font></font>
							</button>
						</c:when>

						<c:otherwise>
							<button id="bttn" type="button"
								class="btn btn-lg btn-block btn-outline-primary"
								onClick="location.href='review.do?userid=${loginUserID}'">
								<font style="vertical-align: inherit;"><font
									style="vertical-align: inherit;" id="bttn">작성한 리뷰 보기</font></font>
							</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
	</div>
	<jsp:include page="foot.jsp" />
	<script>
		$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_rv.jpg)"});
	</script>
</body>
</html>
