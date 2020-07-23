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
</style>
<c:if test="${userCheck ne 1}">
	<script>
		alert("아동만 접근할 수 있습니다.");
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
				<h3>검색조건</h3>
				<form action="get" name="searchForm">
					<div class="card-body search_div">
						<table class="" id="search_table">
							<tbody>
								<tr>
									<th>가게명검색</th>
									<td><input type="text" class="search_text" name="search_storeid"></td>
								</tr>
							
						    	<tr>
						       		<th>날짜</th>
						       		<td><input class="datepicker search_text" name="start_date"> - <input class="search_text datepicker" name="end_date"></td>
						        </tr>
						
							    <tr>
							    	<th>예약상태</th>
							        <td>
							        	<div class="btn-group btn-group-toggle" id="status_div" data-toggle="buttons">
											<label class="btn active" id="status_label1" for="status_radio1">
												<input type="radio" name="s_radio" id="status_radio1" value="0" checked>전체
											</label>
											<label class="btn" id="status_label2" for="status_radio2">
												<input type="radio" name="s_radio" id="status_radio2" value="1">승인대기중
											</label>
											<label class="btn" id="status_label3" for="status_radio3">
												<input type="radio" name="s_radio" id="status_radio3" value="2">예약승인
											</label>
											<label class="btn" id="status_label4" for="status_radio4">
												<input type="radio" name="s_radio" id="status_radio4" value="3">예약거절
											</label>
											<label class="btn" id="status_label5" for="status_radio5">
												<input type="radio" name="s_radio" id="status_radio5" value="4">예약취소
											</label>
										</div>
							        </td>
							    </tr>
						
						        <tr>
						       		<th>방문상태</th>
							        <td>
							        	<div class="btn-group btn-group-toggle" id="visit_div" data-toggle="buttons">
											<label class="btn active" for="visit_radio1">
												<input type="radio" name="v_radio" id="visit_radio1" value="0" checked>전체
											</label>
											<label class="btn" for="visit_radio2">
												<input type="radio" name="v_radio" id="visit_radio2" value="1">방문완료
											</label>
											<label class="btn" for="visit_radio3">
												<input type="radio" name="v_radio" id="visit_radio3" value="2">미방문
											</label>
										</div>
							        </td>
						      	</tr>

						      	<tr class="center">
						       		<td colspan="2">
						        		<input type="button" value="검색" class="search_btn" id="searchBtn">
						       		</td>
						      	</tr>
							</tbody>
						</table>
					</div>
				</form>
				
				<table class="table board_table listab reservelist">
					<thead>
						<tr>
							<th style="width: 10%">번호</th>
							<th style="width: 15%">가게명</th>
							<th style="width: 25%">방문예정시간</th>
							<th style="width: 18%">예약여부</th>
							<th style="width: 18%">방문여부</th>
							<th style="width: 14%">수정</th>
						</tr>
					</thead>

					<c:if test="${count eq 0}">
						<td colspan="6" class="no_td">
							<h2>예약이 없습니다.</h2>
						</td>
					</c:if>

					<tbody>
						<c:forEach var="lists" items="${list}" varStatus="i">
							<tr>
								<td>${i.count}</td>
								
								<td class="center_it">
									<a href="store.do?userid=${lists.rv_sno}">${lists.rv_shopname}</a>
								</td>

								<td>
									<span>
										<fmt:parseDate value="${lists.rv_time}" pattern="yyyyMMddHHmm" var="myDate"/>
										<fmt:formatDate value="${myDate}" pattern="yyyy-MM-dd a hh:mm"/>
									</span>
								</td>
								
								<td>
									<c:choose>
										<c:when test="${lists.rv_status eq 0}">
											나올일없다 0이긴 한데
										</c:when>
									
										<c:when test="${lists.rv_status eq 1}">
											<span class="status_btn0">승인대기중</span>
										</c:when>
											
										<c:when test="${lists.rv_status eq 2}">
											<span class="status_btn1">예약승인</span>
										</c:when>
										
										<c:when test="${lists.rv_status eq 3}">
											<span class="status_btn2">예약거절</span>
										</c:when>
										
										<c:when test="${lists.rv_status eq 4}">
											<span class="status_btn3">예약취소</span>
										</c:when>
											
										<c:otherwise>
											<span>이거 나오면 안된다</span>
										</c:otherwise>
									</c:choose>
								</td>
								
								<td>
									<c:choose>
										<c:when test="${lists.rv_visit eq 0}">
											
										</c:when>
											
										<c:when test="${lists.rv_visit eq 1}">
											<span class="status_btn1">방문완료</span>
										</c:when>
										
										<c:when test="${lists.rv_visit eq 2}">
											<span class="status_btn2">미방문</span>
										</c:when>
											
										<c:otherwise>
											<span>이거 나오면 안된다</span>
										</c:otherwise>
									</c:choose>
								</td>
								
								<td style="text-align:center;">
									<a href="reservedetail.do?rno=${lists.rv_rno}">상세보기</a>
								</td>
									
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</section>

			<div class="paging">
				<ul>
					<c:if test="${startPage > 1}">
						<li><a href="reservechild.do?&userid=${loginUserID}&page=${startPage-1}"> <span
								aria-hidden="true">&laquo;</span>
						</a></li>
					</c:if>

					<c:forEach var="paging" begin="${startPage}" end="${endPage}">
						<c:if test="${paging == spage}">
							<li class="p_active"><a href="reservechild.do?&userid=${loginUserID}&page=${paging}">${paging}</a>
							</li>
						</c:if>

						<c:if test="${paging != spage}">
							<li><a href="reservechild.do?&userid=${loginUserID}&page=${paging}">${paging}</a></li>
						</c:if>
					</c:forEach>

					<c:if test="${endPage != maxPage}">
						<li><a href="reservechild.do?&userid=${loginUserID}&page=${endPage+1}"> <span
								aria-hidden="true">&raquo;</span>
						</a></li>
					</c:if>

				</ul>
			</div>
			
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
	
	<!-- 달력구현에 필요한 -->	
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
	<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>	
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
	
	<!-- Template Main JS File -->
	<script src="resoures/js/custom.js"></script>
	<script src="resoures/js/datepicker-ko.js"></script>
	
<script>
$(document).ready(function(){
	$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_rv.jpg)"});
	$('.datepicker').datepicker({});
	// 검색조건 유효성
	var searchBtn = document.getElementById("searchBtn");
	searchBtn.onclick = function() {
		var start_date = document.searchForm.start_date.value;
		var end_date = document.searchForm.end_date.value;
		
		if(start_date.length != 0 && end_date.length != 0){
			console.log("둘다 널아님!");
			// 시작날짜가 더 클때
			var start_arr = start_date.split('-');
			start_arr = new Date(start_arr[0], start_arr[1], start_arr[2]);
			var end_arr = end_date.split('-');
			end_arr = new Date(end_arr[0], end_arr[1], end_arr[2]);
			
			if((end_arr - start_arr) < 0){
				alert("날짜 조건이 맞지 않습니다.");
				return false;
			}
			
			document.searchForm.submit();
		}
		else if(start_date.length != 0 && end_date == 0){
			alert("날짜를 입력해주세요.");
			document.searchForm.end_date.focus();
			return false;
		}
		else if(start_date.length == 0 && end_date.length != 0){
			alert("날짜를 입력해주세요.");
			document.searchForm.start_date.focus();
			return false;
		}
		else {
			console.log("둘다 비었으니 조건x");
			document.searchForm.submit();
		}
	};
});
	
</script>



</body>
</html>