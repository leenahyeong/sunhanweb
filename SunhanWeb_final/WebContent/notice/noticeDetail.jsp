<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
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
<link href="resoures/cssmain/custom.css" rel="stylesheet">

<style>
.main-nav li {
	min-width: 150px;
}

.content_pd100 {
	padding: 0;
}
</style>

</head>
<body>
	<%@include file="../header.jsp"%>

	<main id="main" class="container-flude">
		<section class="auto cover_img">
			<div class="jumbotron jumbotron-fluid">
				<div class="container">
					<h1 class="display-4">공지사항</h1>
					<p class="lead">선한영향력의 소식을 알려드립니다.</p>
				</div>
			</div>
		</section>	
	
		<div class="container real_c">
			<form action="noticedelete.do" method="post" name="deleteForm">
				<article class="detail">
					<header>
						<h2>${dto.subject}</h2>
						<input type="hidden" name="bno" value="${dto.bno}">
					</header>

					<section class="write_info">
						<span class="bold">작성일 </span> <span class="default"><fmt:formatDate
						value="${dto.reg_date}" pattern="yyyy-MM-dd HH:mm" /> </span>
						<pre>|</pre>
						<span class="bold">조회수 </span> <span class="default">${dto.hit}</span>
						<article class="float-right">
							<c:if test="${loginUser.admin eq 2}">
								<input type="button" value="수정" class="btn_text"
									onclick="location.href='noticeupdate.do?bno=${dto.bno}&page=${paging}'" />
								<input type="button" value="삭제" class="btn_text"
									onclick="del(${dto.bno})" />
							</c:if>
						</article>
					</section>

					<hr class="detail_header_hr">
					<c:if test="${!empty dto.file1 || !empty dto.file2}">
					<section class="content_pd100">
						<div class="dropdown right file_drop">
							  <span class="dropdown-toggle cursor" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							    	<c:choose>
							    		<c:when test="${!empty dto.file1 && !empty dto.file2}">
							    			첨부파일<span class="file_color">(2)</span>
							    		</c:when>
							    		<c:otherwise>
							    			첨부파일<span class="file_color">(1)</span>
							    		</c:otherwise>
							    	</c:choose>
							    	
							  </span>
							  <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
							    <c:if test="${!empty dto.file1}">
							    <a
								href="noticedown.do?file1=${dto.file1}" name="file1"
								class="filedown">${dto.file1}</a>
								</c:if>
								
								<c:if test="${!empty dto.file2}">
							    <a
								href="noticedown.do?file2=${dto.file2}" name="file2"
								class="filedown">${dto.file2}</a>
								</c:if>
								
							  </div>
							</div>
					</section>
					</c:if>
					
					<section>
						<p>${dto.content}</p>
					</section>
				
					<section class="detail_btn_list auto">
						<c:choose>
							<c:when test="${option ne null && keyword ne null}">
								<input type="button" value="목록 " class="reserve_btn float-right"
									onclick="location.href='notice.do?page=${paging}&option=${option}&keyword=${keyword}'" />
							</c:when>
							<c:otherwise>
								<input type="button" value="목록" class="reserve_btn float-right"
									onclick="location.href='notice.do?page=${paging}'" />
							</c:otherwise>
						</c:choose>
					</section>
					
					<section class="left prne">
						<div class="detail_b_top">
						<span class="file_span prne_t">이전글</span>
						<c:choose>
							<c:when test="${p_dto.subject ne null}">
								<a href="noticedetail.do?bno=${p_dto.bno}&page=${paging}"
									class="prne_a file_span"> ${p_dto.subject}</a>
							</c:when>
							<c:otherwise>
								<span class="file_span prne_a">이전 게시글이 없습니다.</span>
							</c:otherwise>
						</c:choose>
						</div>
						<hr>
						<div class="detail_b_bottom">
						<span class="file_span prne_t">다음글</span>
							<c:choose>
								<c:when test="${n_dto.subject ne null}">
									<a href="noticedetail.do?bno=${n_dto.bno}&page=${paging}"
										class="prne_a file_span"> ${n_dto.subject} </a>
								</c:when>
								<c:otherwise>
									<span class="file_span prne_a">다음 게시글이 없습니다.</span>
								</c:otherwise>
							</c:choose>
						</div>

					</section>

				</article>
			</form>
		</div>

	</main>

	<%@include file="../footer.jsp"%>

<!-- Vendor JS Files -->
<script src="resoures/js/jquery.min.js"></script>
<script src="resoures/js/bootstrap.bundle.min.js"></script>
<script src="resoures/js/mobile-nav.js"></script>
<script src="resoures/js/owl.carousel.min.js"></script>
<script src="https://kit.fontawesome.com/26e5b48a3a.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.16/dist/summernote-bs4.min.js"></script>
<!-- Template Main JS File -->
<script src="resoures/js/custom.js"></script>

<script>
	$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_notice.jpg)"});
	
   function del(bno) {
	   var str;
	   str = confirm("삭제하시겠습니까?");
	   if(str) {
		   document.deleteForm.submit();
	   } else {
		   return false;
	   }
   }
 	
   
</script>


</body>
</html>