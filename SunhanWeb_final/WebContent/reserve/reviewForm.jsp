<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="resoures/css/bootstrap.min.css" rel="stylesheet">
<link href="resoures/css/owl.carousel.min.css" rel="stylesheet">
<link href="/your-path-to-fontawesome/css/fontawesome.css"
	rel="stylesheet">
<!-- Template Main CSS File -->
<link href="resoures/cssmain/style.css" rel="stylesheet">
<link href="resoures/cssmain/custom.css?ver=1.5" rel="stylesheet">
<link href="resoures/cssmain/starrr.css?ver=1.3" rel="stylesheet">
</head>
<body>
	<div>
		<header class="cmtheader">
			<c:choose>
				<c:when test="${loginUser.admin eq 0}">
					<h4>리뷰작성</h4>
				</c:when>
				<c:otherwise>
					<h4>리뷰답글작성</h4>
				</c:otherwise>
			</c:choose>
		</header>
		<c:choose>
			<c:when test="${req eq 0}">
				<form target="parentForm" name="reviewaddForm">
					<div class="update_cmt_form">
							<c:if test="${loginUser.admin eq 0}">
								<div class="auto">
									<span>${dto.rv_shopname}</span>
									<input type="hidden" name="review_rno" value="${rno}">
									<input type="hidden" name="review_id" value="${loginUserID}">
									<input type="hidden" name="review_otherid" value="${dto.rv_sno}">
									<div class="inline">
										<div class="starrr" id="star"></div>
										<input type="text" name="score" value="0" id="star_input" readonly/>
									</div>
									
									<textarea class="comment_write outline" name="review_content"
										placeholder="리뷰를 작성해주세요."></textarea>
									<div class="center">
										<input type="button" value="작성" class="btn_text"
										onclick="review_insert()">
										<input type="button" value="취소"
										class="btn_text" onclick="window.close()">
									</div>
								</div>
							</c:if>
					</div>
				</form>
			</c:when>
			
			<c:otherwise>
				<form target="parentForm" name="reviewupdateForm">
					<c:choose>
						<c:when test="${loginUser.admin eq 0}">
							<div class="update_cmt_form">
								<div class="auto">
									<span>${dto.review_shopname}</span>
									<input type="hidden" name="review_no_u" value="${dto.review_no}">
									<input type="hidden" name="review_id_u" value="${loginUserID}">
									<input type="hidden" name="review_otherid_u" value="${dto.review_storeid}">
									<div class="inline">
										<div class="starrr" id="star2"></div>
										<input type="text" name="score_u" value="${dto.review_score}" id="star2_input" readonly/>
									</div>
										
									<textarea class="comment_write outline" name="review_content_u"
										placeholder="리뷰를 작성해주세요.">${dto.review_content}</textarea>
									<div class="center">
										<input type="button" value="작성" class="btn_text"
											onclick="review_update()">
										<input type="button" value="취소"
											class="btn_text" onclick="window.close()">
									</div>
										
								</div>
							</div>
							
						</c:when>
						
						<c:otherwise>
							<div class="update_cmt_form">
								<div class="auto">
									<span>사장님</span>
									<input type="hidden" name="review_no_u" value="${dto.review_no}">
									<input type="hidden" name="review_id_u" value="${loginUserID}">
									<input type="hidden" name="review_otherid_u" value="${dto.review_storeid}">
										
									<textarea class="comment_write outline" name="review_content_u"
										placeholder="리뷰를 작성해주세요.">${dto.review_content}</textarea>
									<div class="center">
										<input type="button" value="작성" class="btn_text"
											onclick="review_update()">
										<input type="button" value="취소"
											class="btn_text" onclick="window.close()">
									</div>
										
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					
					
				</form>
			</c:otherwise>
		</c:choose>
		
	</div>

<!-- Vendor JS Files -->
<script src="resoures/js/jquery.min.js"></script>
<script src="resoures/js/bootstrap.bundle.min.js"></script>
<script src="resoures/js/mobile-nav.js"></script>
<script src="resoures/js/owl.carousel.min.js"></script>
<script src="https://kit.fontawesome.com/26e5b48a3a.js" crossorigin="anonymous"></script>
<!-- Template Main JS File -->
<script src="resoures/js/custom.js"></script>
<script src="resoures/js/starrr.js"></script>

	
	<script>
	// 리뷰 작성
	function review_insert() {
		var content = document.reviewaddForm.review_content.value;

		if (content == "" || content == " ") {
			alert("리뷰를 작성해주세요.");
			return false;
		} else {
			document.reviewaddForm.method = "post";
			document.reviewaddForm.action = "reviewadd.do";
			document.reviewaddForm.submit();
		}
	}
	
	// 리뷰 수정
	function review_update() {
		var content = document.reviewupdateForm.review_content_u.value;

		if (content == "" || content == " ") {
			alert("리뷰를 작성해주세요.");
			return false;
		} else {
			document.reviewupdateForm.method = "post";
			document.reviewupdateForm.action = "reviewupdate.do";
			document.reviewupdateForm.submit();
			window.opener.document.location.reload();
			window.close();
		}
	}
	
	// 별점
	$('#star').starrr({
		  max: 5,
		  rating: $("#star_input").val(),
		  change: function(e, value){
			  $("#star_input").val(value).trigger('input');
		  }
	});
	
	// 업데이트 별점
	$('#star2').starrr({
		  max: 5,
		  rating: $("#star2_input").val(),
		  change: function(e, value){
			  $("#star2_input").val(value).trigger('input');
		  }
	});
	</script>
	
	
</body>
</html>