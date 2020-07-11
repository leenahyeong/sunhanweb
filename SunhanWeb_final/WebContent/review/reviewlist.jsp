<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<link href="/your-path-to-fontawesome/css/fontawesome.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" />
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

<script src="resoures/js/jquery.min.js"></script>

<link href="resoures/cssmain/starrr.css?ver=4" rel="stylesheet">
<script src="resoures/js/starrr2.js?ver=2"></script>


<style>
.s_comment span {
	font-weight: 300 !important;
}

.r_name a {
	color: #3a3a3a !important;
	font-size: 16px !important;
	font-weight: 450 !important;
}

ion-icon {
	vertical-align: initial;
	color: #3a3a3a;
	font-size: 18px;
}

.s_comment {
	border: 1px solid #ddd !important;
	padding: 10px;
	margin-top: 40px;
	margin-bottom: 0;
}

.s_comment:first-of-type(){
	margin-top: 80px;
}

.s_comment p {
	font-size: 16px !important;
    font-weight: 400 !important;
}

.s_comment input {
	border: none !important;
	background: none !important;
	font-size: 13px !important;
	color: #777 !important;
	padding: 0 !important;
	font-weight: 300 !important;
	margin: 0 !important;
}

.comment_color {
	vertical-align: initial !important;
	font-size: 26px;
}

.r_name {
	font-size: 15px;
	padding: 0 0 5px 0;
	font-weight: 450;
}

.comment_btn {
	border: 1px solid #d2d2d2 !important;
	border-left: none !important;
	background: #f9f9f9 !important;
	font-size: 14px !important;
	font-weight: 400 !important;
}

.comment_btn:hover {
	color: #333;
}

.comment_head {
	margin: 0px 0 15px 0 !important;
}

.inline .star_date {
	margin: 0 10px;
}

.inline {
	margin-left: 0 !important;
}

.re {
	background: #f6f6f6;
	margin-top: 0px;
	border-top: none !important;
}

.pd20 {
	padding: 20px 0 0 0;
}

.inline h1 {
	text-align: center;
    margin: 10px;
}

.c_999 {
	color: #999 !important;
	margin-top: 3px;
	font-weight: 400;
}

.vertical_initial{
	vertical-align: initial !important;
}

.height_bd {
	border-left: 1px solid #ddd;
    width: 1%;
    height: 100px;
    float: left;
    margin: 0 15px;
}

</style>
</head>
<body>
	<c:choose>
	<c:when test="${count.get(0) ne 0}">
		
	<div>	
		<div class="comment_head left inline float-left" style="margin: 10px !important;">
			<div>
				<h2 class="inline">리뷰</h2>
				<span class="comment_color inline">${count.get(0)}</span>			
			</div>
			<div class="" style="margin:3px 0">
			<h5 class="inline c_999">사장님 댓글</h5>
			<span class="c_999 vertical_initial" style="font-size:15px">${count.get(1)}</span>
			</div>
		</div>
		
		<div class="height_bd">
		</div>
		
		<div class="comment_head">
			<div style="margin: 0" class="left inline">
				<h1 class="">${count.get(2)}.0</h1>
				<div class="starrr" id="star_avg"></div>
				<input type="hidden" name="score_avg" value="${count.get(2)}" class="star_input" readonly />
				
			</div>
		</div>
	</div>
	
	<hr style="margin: 80px 0 !important;">
	
		<c:forEach var="lists" items="${list}" varStatus="status">
			<ul class="s_comment auto">
				<c:choose>
					<c:when test="${lists.review_depth eq 0}">
						<li class="r_name">
							<c:choose>
								<c:when test="${loginUser.admin eq 0}">
									${lists.review_username}(${lists.review_userid})
								</c:when>
								
								<c:when test="${loginUser.admin eq 1}">
									<a href="chating.jsp?toID=${lists.review_userid}">${lists.review_username}(${lists.review_userid})
										<i class="far fa-comment-dots"></i>
									</a>
								</c:when>
								
								<c:when test="${empty loginUserID}">
									${lists.review_username}(${lists.review_userid})
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
							
						</li>

						<li>
							<div style="margin: 0" class="left inline">
								<input type="hidden" name="score" value="${lists.review_score}" class="star2_input" readonly/>
								
								<div class="score inline" id="score${status.count}"></div>
								
								
								<script>
								var score = '${lists.review_score}';
									
								var html;
									
								if(score == 0) {
									html = "<i class='fa fa-star-o' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
								}
									
								if(score == 1) {
									html = "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
								}
									
								if(score == 2) {
									html = "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
								}
								if(score == 3) {
									html = "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
								}
								if(score == 4) {
									html = "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fa fa-star-o' aria-hidden='true'></i>";
								}
								if(score == 5) {
									html = "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fas fa-star' aria-hidden='true'></i>";
									html += "<i class='fas fa-star' aria-hidden='true'></i>";
								}
								var score_div = document.getElementById('score'+'${status.count}');
									
								$(score_div).html(html);
								</script>

								
								<span class="star_date"><fmt:formatDate
										value="${lists.review_date}" pattern="yyyy-MM-dd" /></span>

							</div>

						</li>

						<li class="pd20"><p>${lists.review_content}</p></li>
					</c:when>

					<c:otherwise>
						<span class="material-icons">subdirectory_arrow_right</span>
						<li class="r_name inline"><c:choose>
								<c:when test="${loginUser.admin eq 0}">
									<a href="chating.jsp?toID=${lists.review_storeid}">사장님 <i
										class="far fa-comment-dots"></i></a>
								</c:when>

								<c:otherwise>
									<a>사장님</a>
								</c:otherwise>
							</c:choose> <span class="star_date"> <fmt:formatDate
									value="${lists.review_date}" pattern="yyyy-MM-dd" /></span></li>
								
						<li class="pd20"><p>${lists.review_content}</p></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</c:forEach>
		</c:when>
		<c:otherwise>
			<div class="center pd100">
				<h1>리뷰가 없습니다.</h1>
			</div>
		</c:otherwise>
		</c:choose>
		
	<script>
	$(document).ready(function() {
		$('.starrr').starrr({
			rating: $('.star_input').val(),
			max: 5,
			readOnly: true
		});
		
		$('.material-icons').parent().addClass('re');
	});
	</script>
</body>
</html>
