<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>선한영향력</title>
<!-- Vendor CSS Files -->
<link href="resoures/css/bootstrap.min.css" rel="stylesheet">
<link href="resoures/css/owl.carousel.min.css" rel="stylesheet">
<link href="/your-path-to-fontawesome/css/fontawesome.css"
	rel="stylesheet">
<!-- Template Main CSS File -->
<link href="resoures/cssmain/style.css" rel="stylesheet">
<link href="resoures/cssmain/custom.css?ver=1.4" rel="stylesheet">



<style>
.main-nav li {
	min-width: 150px;
}

span{
	display: block;
}

.span_t {
	margin-top: 60px;
	font-size: 22px;
	font-weight: 460;
}

.span_t ion-icon {
	color: #333;
}

.span_t:first-child{
	margin-top: 10px;
}

.span_s {
	margin: 5px 0 0 36px;
	font-size: 14.5px;
	color: #8e8e8e;
}

h3 {
	background: #94CE68;
	color: #fff;
	margin: 10px 0;
}

.maincolor{
	display: inline-block;
}

ion-icon{
	margin-right: 10px;
}
</style>

</head>
<body>
	<%@include file="../header.jsp"%>

	<main id="main" class="container-flude">
		<section class="auto cover_img">
			<div class="jumbotron jumbotron-fluid">
				<div class="container">
					<h1 class="display-4">예약가이드</h1>
					<p class="lead">예약에 관한 정보를 알려드립니다.</p>
				</div>
			</div>
		</section>

		<div class="container real_c">
			<section class="auto">
				<ul>
					<li>
						<h3>예약가이드</h3>
						<div style="border:solid 2.5px #ddd; padding: 20px; margin-top: 40px;">
							<span class="span_t"><ion-icon name="checkmark-outline"></ion-icon> <span class="maincolor">당일 예약</span>만 가능합니다.</span>
							<span class="span_t"><ion-icon name="checkmark-outline"></ion-icon> <span class="maincolor">방문예정시간 1시간 전</span>에는 예약 수정, 취소가 불가능 합니다.</span>
							<span class="span_t"><ion-icon name="checkmark-outline"></ion-icon>가게와 관련된 문의사항은  <span class="maincolor">채팅</span>을 이용해주세요.</span>
							<span class="span_s">(가게 상세정보에서 가게명 옆 <ion-icon name="chatbubbles-outline"></ion-icon> 아이콘을 클릭하면 채팅화면으로 이동합니다.)</span>
							<span class="span_t"><ion-icon name="checkmark-outline"></ion-icon> 리뷰는 <span class="maincolor">방문 후</span> 작성 가능합니다.</span>
							<span class="span_s">(마이페이지 - 예약정보 보기 - 상세보기)</span>
						</div>
						<!-- <span class="span_t">- 당일 예약만 가능합니다.</span>
						<span class="span_t">- 방문예정시간 1시간 전에는 예약 수정, 취소가 불가능 합니다.</span>
						<span class="span_t">- 예약하실 가게에 관한 문의는 채팅을 이용해주세요.</span>
						<span class="span_s">(가게 상세정보에서 가게명 옆 <ion-icon name="chatbubbles-outline"></ion-icon> 아이콘을 클릭하면 채팅화면으로 이동합니다.)</span>
						<span class="span_t">- 리뷰는 방문 후 작성 가능합니다.</span>
						<span class="span_s">(마이페이지 - 예약정보 보기 - 상세보기)</span> -->
						
					</li>
				</ul>
			</section>

			
		</div>
	</main>


	<%@include file="../footer.jsp"%>

	<!-- Vendor JS Files -->
	<script src="resoures/js/jquery.min.js"></script>
	<script src="resoures/js/bootstrap.bundle.min.js"></script>
	<script src="resoures/js/mobile-nav.js"></script>
	<script src="resoures/js/owl.carousel.min.js"></script>
	<script src="https://kit.fontawesome.com/26e5b48a3a.js"
		crossorigin="anonymous"></script>
	<script src="https://unpkg.com/ionicons@5.0.0/dist/ionicons.js"></script>	
	<!-- Template Main JS File -->
	<script src="resoures/js/custom.js"></script>
	<script src="resoures/js/datepicker-ko.js"></script>
	<script>
		$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_rv.jpg)"});
	</script>
</body>
</html>