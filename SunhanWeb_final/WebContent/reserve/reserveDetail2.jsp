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
<link href="resoures/cssmain/selectordie.css" rel="stylesheet">
<link href="resoures/cssmain/custom.css?ver=1.9" rel="stylesheet">

<style>
.main-nav li {
	min-width: 150px;
}
</style>
<c:if test="${userCheck ne 1}">
	<script>
		//alert("후원자만 접근할 수 있습니다.");
		//location.href = "javascript:history.back()";
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
					<p class="lead">모든 예약을 확인할 수 있습니다.</p>
				</div>
			</div>
		</section>

		<div class="container real_c">
			<section class="auto search_sec">
				<form>
					<div class="float-right searchbar">
						
					</div>
				</form>
			</section>

			<section class="auto">
				<c:choose>
					<c:when test="${admin == 0}">
					
					<h1>예약정보</h1>
					
					<form action="post" name="detailForm">
						<input type="hidden" name="rno" value="${dto.rv_rno}">
						<div class="card-body search_div">
							<table class="rv_detail_form" id="search_table">
								<tbody>
									<tr>
										<th>가게명</th>
										<td>
											<input type="hidden" name="storeid" value="${dto.rv_sno}">
											<a class="c_444" href="store.do?userid=${dto.rv_sno}">${dto.rv_shopname}</a>
										</td>
									</tr>
								
							    	<tr>
							       		<th>방문예정일</th>
							       		<td>
							       		
							       		<c:choose>
							       			<c:when test="${dto.rv_status eq 1 && timeCheck eq 1}">
												<span>
												<input type="hidden" value="${now_yyyy}" name="now_yyyy">
												${now_yyyy}
												</span>
		
												<select name="option" class="search_select outline graybox">
													<c:set var="time" value="${open1}" />
													<c:forEach begin="${open1}" end="${close1}">
														<option value="${time}"
															<c:if test="${time1 == time}">selected</c:if>>${time}</option>
														<c:set var="time" value="${time+1}" />
													</c:forEach>
												</select>
												<input type="number" class="outline graybox" name="minute" value="${time2}"> 분 
							       			</c:when>
							       			<c:otherwise>
							       				<span>${now}</span>
							       			</c:otherwise>
							       		</c:choose>
							       			
							       		</td>
							        </tr>
							        
							        <tr>
							      		<th>방문예정인원</th>
							      		<td>
								      		<c:choose>
								       			<c:when test="${dto.rv_status eq 1 && timeCheck eq 1}">
								       				<input type="number" class="outline graybox" name="personnel" value="${dto.rv_personnel}"> 명
								       			</c:when>
								       			
								       			<c:otherwise>
								       				<span>${dto.rv_personnel} 명</span>
								       			</c:otherwise>
								       		</c:choose>	
							      		</td>
							      	</tr>
							
								    <tr>
								    	<th>예약상태</th>
								        <td>
											<c:choose>
												<c:when test="${dto.rv_status eq 0}">
													나올일없다 0이긴 한데
												</c:when>
											
												<c:when test="${dto.rv_status eq 1}">
													<span class="status_btn0">승인대기중</span>
												</c:when>
													
												<c:when test="${dto.rv_status eq 2}">
													<span class="status_btn1">예약승인</span>
												</c:when>
												
												<c:when test="${dto.rv_status eq 3}">
													<span class="status_btn2">예약거절</span>
													<span class="red inline">(${dto.rv_reason})</span>
												</c:when>
												
												<c:when test="${dto.rv_status eq 4}">
													<span class="status_btn3">예약취소</span>
												</c:when>
													
												<c:otherwise>
													<span>이거 나오면 안된다</span>
												</c:otherwise>
											</c:choose>
										</td>
								    </tr>
							
							        <tr>
							       		<th>방문상태</th>
								        <td>
								        	<c:choose>
												<c:when test="${dto.rv_visit eq 0}">
													<span class="status_btn0">방문대기중</span>
												</c:when>
													
												<c:when test="${dto.rv_visit eq 1}">
													<span class="status_btn1">방문완료</span>
												</c:when>
												
												<c:when test="${dto.rv_visit eq 2}">
													<span class="status_btn2">미방문</span>
												</c:when>
													
												<c:otherwise>
													<span>이거 나오면 안된다</span>
												</c:otherwise>
											</c:choose>
								        </td>
							      	</tr>
							      	
							      	<tr>
							      		<td></td>
							      	</tr>
								</tbody>
							</table>
							
							</div>
							<c:if test="${dto.rv_status eq 1 && timeCheck eq 1}">
								<input type="hidden" value="${dto.rv_userid}" name="rv_userid">
								<input type="button" value="수정" id="updateBtn" class="s_btn_update reserve_btn">
							    <input type="button" value="예약취소" id="cancelBtn" onclick="reserve_cancel()" class="s_btn_cancel reserve_btn">
							</c:if>
							   
							</form>
							
							<h1>리뷰정보</h1>
							
						<form action="post" name="reviewForm">
						<input type="hidden" name="rno" value="${dto.rv_rno}">
						<div class="card-body search_div">
							<c:choose>
								<c:when test="${review }">
								
								</c:when>
								
								<c:otherwise>
									
								</c:otherwise>
							</c:choose>
							<table class="rv_detail_form" id="search_table">
								<tbody>
									<tr>
										<th>가게명</th>
										<td>
											<input type="hidden" name="storeid" value="${dto.rv_sno}">
											<a class="c_444" href="store.do?userid=${dto.rv_sno}">${shopname}</a>
										</td>
									</tr>
								
							    	<tr>
							       		<th>리뷰내용</th>
							       		<td>
							       		
							       		<c:choose>
							       			<c:when test="${dto.rv_status eq 1 && timeCheck eq 1}">
												<span>
												<input type="hidden" value="${now_yyyy}" name="now_yyyy">
												${now_yyyy}
												</span>
		
												<select name="option" class="search_select outline graybox">
													<c:set var="time" value="${open1}" />
													<c:forEach begin="${open1}" end="${close1}">
														<option value="${time}"
															<c:if test="${time1 == time}">selected</c:if>>${time}</option>
														<c:set var="time" value="${time+1}" />
													</c:forEach>
												</select>
												<input type="number" class="outline graybox" name="minute" value="${time2}"> 분 
							       			</c:when>
							       			<c:otherwise>
							       				<span>${now}</span>
							       			</c:otherwise>
							       		</c:choose>
							       			
							       		</td>
							        </tr>
							        
							        <tr>
							      		<th>방문예정인원</th>
							      		<td>
								      		<c:choose>
								       			<c:when test="${dto.rv_status eq 1 && timeCheck eq 1}">
								       				<input type="number" class="outline graybox" name="personnel" value="${dto.rv_personnel}"> 명
								       			</c:when>
								       			
								       			<c:otherwise>
								       				<span>${dto.rv_personnel} 명</span>
								       			</c:otherwise>
								       		</c:choose>	
							      		</td>
							      	</tr>
							
								    <tr>
								    	<th>예약상태</th>
								        <td>
											<c:choose>
												<c:when test="${dto.rv_status eq 0}">
													나올일없다 0이긴 한데
												</c:when>
											
												<c:when test="${dto.rv_status eq 1}">
													<span class="status_btn0">승인대기중</span>
												</c:when>
													
												<c:when test="${dto.rv_status eq 2}">
													<span class="status_btn1">예약승인</span>
												</c:when>
												
												<c:when test="${dto.rv_status eq 3}">
													<span class="status_btn2">예약거절</span>
													<span class="red inline">(${dto.rv_reason})</span>
												</c:when>
												
												<c:when test="${dto.rv_status eq 4}">
													<span class="status_btn3">예약취소</span>
												</c:when>
													
												<c:otherwise>
													<span>이거 나오면 안된다</span>
												</c:otherwise>
											</c:choose>
										</td>
								    </tr>
							
							        <tr>
							       		<th>방문상태</th>
								        <td>
								        	<c:choose>
												<c:when test="${dto.rv_visit eq 0}">
													<span class="status_btn0">방문대기중</span>
												</c:when>
													
												<c:when test="${dto.rv_visit eq 1}">
													<span class="status_btn1">방문완료</span>
												</c:when>
												
												<c:when test="${dto.rv_visit eq 2}">
													<span class="status_btn2">미방문</span>
												</c:when>
													
												<c:otherwise>
													<span>이거 나오면 안된다</span>
												</c:otherwise>
											</c:choose>
								        </td>
							      	</tr>
							      	
							      	<tr>
							      		<td></td>
							      	</tr>
								</tbody>
							</table>
							
							</div>
								<input type="hidden" value="${dto.rv_userid}" name="rv_userid">
								<input type="button" value="리뷰수정" id="review_updateBtn" class="s_btn_update reserve_btn">
							    <input type="button" value="리뷰삭제" id="review_deleteBtn" onclick="review_delete()" class="s_btn_cancel reserve_btn">
							</form>
							
							<input type="button" value="목록" class="reserve_btn float-right" onclick="location.href='reservechild.do?userid=${loginUserID}'">
							
						</c:when>
						</c:choose>
			
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
		
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
	<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>	
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
	
	<!-- Template Main JS File -->
	<script src="resoures/js/custom.js"></script>
	<script src="resoures/js/datepicker-ko.js"></script>
	<script src="resoures/js/selectordie.js"></script>
	
<script>
function someThingChange(){
	var status_3 = $("#status_select option:selected").val();
	if(status_3 == 3){
		$('.rv_reason').css('display', 'inline-block');
	} else{
		$('.rv_reason').css('display', 'none');
	}
}

function reserve_cancel() {
	var str;
	str = confirm("예약을 취소하시겠습니까?");
	if (str) {
		document.detailForm.method= "post";
		document.detailForm.action = "reservecancel.do?rno=${dto.rv_rno}";
		document.detailForm.submit();
	} else {
		return false;
	}
}
/*
var cancelBtn = document.getElementById("cancelBtn");
cancelBtn.onclick = function() {
	var str;
	str = confirm("예약을 취소하시겠습니까?");
	if (str) {
		document.detailForm.method= "post";
		document.detailForm.action = "reservecancel.do?rno=${dto.rv_rno}";
		document.detailForm.submit();
	} else {
		return false;
	}
};
*/
$(document).ready(function(){
	$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_list.jpg)"});
	$('.datepicker').datepicker({});
	$ ('.selectOrDie'). selectOrDie ();
	
	

	var updateBtn = document.getElementById("updateBtn");
	updateBtn.onclick = function() {
		var str;
		str = confirm("수정하시겠습니까?");
		if (str) {
			document.detailForm.method= "post";
			document.detailForm.action = "reserveupdate.do?rno=${dto.rv_rno}";
			document.detailForm.submit();
		} else {
			return false;
		}
	};
});
	
	
</script>

</body>
</html>