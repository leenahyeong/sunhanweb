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

</head>
<body>
	<%@include file="../header.jsp"%>

	<main id="main" class="container-flude">
		<section class="auto cover_img">
			<div class="jumbotron jumbotron-fluid">
				<div class="container">
					<h1 class="display-4">자유게시판</h1>
					<p class="lead">회원들간의 소통이 가능합니다.</p>
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

			<section class="auto">
				<table class="table board_table listab" id="board_table">
					<thead>
						<tr>
							<th style="width:11%">번호</th>
							<th style="width:45%">제목</th>
							<th style="width:18%">작성일</th>
							<th style="width:16%">작성자</th>
							<th style="width:10%">조회수</th>
						</tr>
					</thead>
					
					<c:if test="${count eq 0}">
						<td colspan="5" class="no_td">
						<h2>등록된 게시글이 없습니다.</h2>
						</td>
					</c:if>

					<tbody>
						<c:forEach var="lists" items="${list}" varStatus="i">
							<tr>
								<td>${count - ((spage-1) * 10 + i.index)}</td>
								<c:choose>
									<c:when test="${keyword ne null && option ne null}">
										<td><a
											href="freeboarddetail.do?bno=${lists.bno}&page=${spage}&option=${option}&keyword=${keyword}">${lists.subject}</a>
											<c:if test="${cmt_count ne 0}">
													<!-- <span class="maincolor">(${cmt_count})</span></a> -->
												</c:if>
										</td>
									</c:when>

									<c:otherwise>
										<td><a
											href="freeboarddetail.do?bno=${lists.bno}&page=${spage}">${lists.subject} 
												<c:if test="${cmt_count ne 0}">
												
													<%-- <span class="maincolor">(${cmt_count})</span></a> --%>
												</c:if>
											</a>	
										</td>
									</c:otherwise>
								</c:choose>

								<td class="media_none">
									<fmt:formatDate value="${lists.reg_date}" pattern="yyyy-MM-dd" />
								</td>
										
								<td class="media_none">
								<!-- 작성자 -->
									<span>${lists.name}</span>		
								</td>
								
								<td class="media_none"><span>${lists.hit}</span></td>		
							</tr>
						</c:forEach>
					</tbody>

				</table>
			</section>


			<div class="paging">
				<ul>
					<c:if test="${startPage > 1}">
						<li><a href="freeboard.do?page=${startPage-1}"> <span
								aria-hidden="true">&laquo;</span>
						</a></li>
					</c:if>

					<c:forEach var="paging" begin="${startPage}" end="${endPage}">
						<c:if test="${paging == spage}">
							<li class="p_active"><a href="freeboard.do?page=${paging}">${paging}</a>
							</li>
						</c:if>

						<c:if test="${paging != spage}">
							<li><a href="freeboard.do?page=${paging}">${paging}</a></li>
						</c:if>
					</c:forEach>

					<c:if test="${endPage != maxPage}">
						<li><a href="freeboard.do?page=${endPage+1}"> <span
								aria-hidden="true">&raquo;</span>
						</a></li>
					</c:if>

				</ul>
			</div>

			<c:if test="${!empty loginUserID}"> 
			<div class="auto">
				<input type="button" onclick="location.href='freeboardadd.do'"
					class="reserve_btn float-right" value="글쓰기">
			</div>
			</c:if>
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
		$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_free.jpg)"});
	</script>
</body>
</html>