<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
 <link href="resoures/cssmain/custom.css?ver=1.1" rel="stylesheet">

<style>
.main-nav li {
	min-width: 150px;
}
</style>

</head>
<body>
	<%@include file="../header.jsp"%>

	<main id="main" class="container-flude">
		<section class="auto cover_img">
			<div class="jumbotron jumbotron-fluid">
				<div class="container">
					<h1 class="display-4">포토앨범</h1>
					<p class="lead">많은 사람들과 추억을 공유해보세요.</p>
				</div>
			</div>
		</section>

		<div class="container real_c">
			<section class="auto search_sec">
				<form>
					<div class="float-right searchbar">
						<input type="submit" value="&#xf002;" class="cursor">
						<input type="text" placeholder="검색어를 입력해주세요." name="keyword"
							class="outline" value="${keyword}">
							<select name="option" class="search_select outline">
								<option value="0" <c:if test="${option == 0}">selected</c:if>>
									제목</option>
	
								<option value="1" <c:if test="${option == 1}">selected</c:if>>
									내용</option>
	
								<option value="2" <c:if test="${option == 2}">selected</c:if>>
									제목+내용</option>
								<option value="3" <c:if test="${option == 3}">selected</c:if>>
									작성자</option>
						</select>
					</div>
				</form>
			</section>

			<section>
				<c:if test="${count eq 0}">
					<div class="no_td center">
						<h2 class="pd40">등록된 게시글이 없습니다.</h2>
					</div>
				</c:if>

				<div class="row imgd portfolio-container">
					<c:forEach var="lists" items="${list}">
						<div class="col-md-4 offset-sm-2 col-sm-8 offset-sm-2 portfolio-item">
								<div class="portfolio-wrap">
									<c:choose>
										<c:when test="${keyword ne null && option ne null}">
											<a href="photodetail.do?bno=${lists.bno}&page=${spage}&option=${option}&keyword=${keyword}">
												<img class="card-img-top" src="<%=request.getContextPath()%>/thumbnail/${lists.thumbnail}">	
														
												<em class="portfolio-info">
													<span><i class="fas fa-eye"></i>${lists.hit}</span>
													<span><i class="fas fa-clock"></i>
														<fmt:formatDate value="${lists.reg_date}" pattern="yyyy-MM-dd" />
													</span>
												</em>
											</a>
											
											<div class="photo_title">
												<a href="photodetail.do?bno=${lists.bno}&page=${spage}&option=${option}&keyword=${keyword}">
													<span>${lists.subject}</span>
												</a>
											</div>
										</c:when>
										
										<c:otherwise>
											<a href="photodetail.do?bno=${lists.bno}&page=${spage}">
												<img class="card-img-top" src="<%=request.getContextPath()%>/thumbnail/${lists.thumbnail}">
												<em class="portfolio-info">
													<span><i class="fas fa-eye"></i>${lists.hit}</span>
													<span><i class="fas fa-clock"></i>
														<fmt:formatDate value="${lists.reg_date}" pattern="yyyy-MM-dd" />
													</span>
												</em>
											</a>
											
											<div class="photo_title">
												<a href="photodetail.do?bno=${lists.bno}&page=${spage}">
													<span>${lists.subject}</span>
												</a>
											</div>
										</c:otherwise>
									</c:choose>
									
									
								</div>				
						</div>
					</c:forEach>

				</div>
			</section>

			<c:if test="${!empty loginUserID}">
				<div class="auto">
					<input type="button" onclick="location.href='photoadd.do'"
						class="reserve_btn float-right" value="글쓰기">
				</div>
			</c:if>

			<div class="paging">
				<ul>
					<c:if test="${startPage > 1}">
						<li><a href="photo.do?page=${startPage-1}"> <span
								aria-hidden="true">&laquo;</span>
						</a></li>
					</c:if>

					<c:forEach var="paging" begin="${startPage}" end="${endPage}">
						<c:if test="${paging == spage}">
							<li class="p_active"><a href="photo.do?page=${paging}">${paging}</a>
							</li>
						</c:if>

						<c:if test="${paging != spage}">
							<li><a href="photo.do?page=${paging}">${paging}</a></li>
						</c:if>
					</c:forEach>

					<c:if test="${endPage != maxPage}">
						<li><a href="photo.do?page=${endPage+1}"> <span
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
<script src="https://kit.fontawesome.com/26e5b48a3a.js" crossorigin="anonymous"></script>
<!-- Template Main JS File -->
<script src="resoures/js/custom.js"></script>
<script>
	$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_photo.jpg)"});
</script>

</body>
</html>