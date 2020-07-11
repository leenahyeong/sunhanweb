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
 <link href="/your-path-to-fontawesome/css/fontawesome.css" rel="stylesheet">
 <!-- Template Main CSS File -->
 <link href="resoures/cssmain/style.css" rel="stylesheet">
 <link href="resoures/cssmain/custom.css" rel="stylesheet">

<style>
.main-nav li {
	min-width: 150px;
}
</style>
<c:if test="${userCheck ne 1}">
	<script>
		alert("예약하신 회원만 접근할 수 있습니다.");
		location.href = "javascript:history.back()";
	</script>
</c:if>

</head>
<body>
	<%@include file="../header.jsp"%>

	<main id="main" class="container-flude">
		<section class="auto cover_img">
			<div class="jumbotron jumbotron-fluid">
				<div class="container">
					<h1 class="display-4">예약</h1>
					<p class="lead">예약하신 정보를 알려드립니다.</p>
				</div>
			</div>
		</section>	
	
		<div class="container real_c">
			<section class="auto search_sec">
				<h1>예약 상세정보입니다.</h1>
				<form action="post" name="cancelForm">
					<table>
						<tr>
							<th>가게명</th>
							<td>
								<a class="c_444" href="store.do?userid=${dto.rv_sno}">${shopname}</a>
							</td>
						</tr>
						<tr>
							<th>방문예정인원수</th>
							<td>${dto.rv_personnel}명</td>
						</tr>
						<tr>
							<th>방문예정시간</th>
							<td>
								${time}
							</td>
						</tr>
						<tr>
							<th>승인여부</th>
							<td>
								<span name="rv_status">
								${rv_status}
								<c:if test="${dto.rv_status == 3}">
									<span class="red"> ( ${dto.rv_reason} ) </span>
								</c:if>
								</span>
					
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<span class="red">* 방문예정시간 1시간 이내에는 예약변경,취소가 불가능합니다. </span>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="hidden" name="rv_rno" value="${dto.rv_rno}">
								<input type="hidden" name="rv_userid" value="${dto.rv_userid}">
								<input type="hidden" name="rv_sno" value="${dto.rv_sno}">
								<input type="button" class="reserve_btn" onclick="location.href='reservechild.do?userid=${loginUserID}'" value="예약목록">
							</td>
						</tr>
					</table>
				</form>
			</section>

		</div>
	</main>


	<%@include file="../footer.jsp"%>

<!-- Vendor JS Files -->
<script src="resoures/js/jquery.min.js"></script>
<script src="resoures/js/bootstrap.bundle.min.js"></script>
<script src="resoures/js/mobile-nav.js"></script>
<script src="resoures/js/owl.carousel.min.js"></script>
<script src="https://kit.fontawesome.com/26e5b48a3a.js" crossorigin="anonymous"></script>
<!-- Template Main JS File -->
<script src="resoures/js/custom.js"></script>
<script>
$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_rv.jpg)"});
</script>
</body>
</html>