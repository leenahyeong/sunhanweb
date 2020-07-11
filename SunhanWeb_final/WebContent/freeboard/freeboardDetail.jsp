<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<article class="detail">
			<form name="deleteForm">
				<header>
					<h2>${dto.subject}</h2>
					<input type="hidden" name="bno" value="${dto.bno}">
				</header>

				<section class="write_info">
					<span class="bold">작성자 </span> <span class="default">${dto.name}</span>
					<pre>|</pre>
					<span class="bold">작성일 </span> <span class="default"><fmt:formatDate
							value="${dto.reg_date}" pattern="yyyy-MM-dd HH:mm" /> </span>
					<pre>|</pre>
					<span class="bold">조회수 </span> <span class="default">${dto.hit}</span>

					<article class="float-right">
						<c:if test="${loginUserID eq dto.id}">
							<input type="button" value="수정" class="btn_text"
								onclick="location.href='freeboardupdate.do?bno=${dto.bno}&page=${paging}'" />
							<input type="button" value="삭제" class="btn_text"
								onclick="del(${dto.bno})" />
						</c:if>
					</article>
				</section>

				<hr class="detail_header_hr">

				<section class="content_pd100">
					<p>${dto.content}</p>
				</section>


			</form>
				<!-- /////////// 댓글  /////////// -->
				<section class="comment" id="comment">
					
				</section>

				<hr>
				<section class="detail_btn_list auto">
					<c:choose>
						<c:when test="${option ne null && keyword ne null}">
							<input type="button" value="목록 " class="reserve_btn float-right"
								onclick="location.href='freeboard.do?page=${paging}&option=${option}&keyword=${keyword}'" />
						</c:when>
						<c:otherwise>
							<input type="button" value="목록" class="reserve_btn float-right"
								onclick="location.href='freeboard.do?page=${paging}'" />
						</c:otherwise>
					</c:choose>
				</section>

				<section class="left prne">
					<div class="detail_b_top">
						<span class="file_span prne_t">이전글</span>
						<c:choose>
							<c:when test="${p_dto.subject ne null}">
								<a href="freeboarddetail.do?bno=${p_dto.bno}&page=${paging}"
									class="prne_a file_span"> ${p_dto.subject} </a>
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
								<a href="freeboarddetail.do?bno=${n_dto.bno}&page=${paging}"
									class="prne_a file_span"> ${n_dto.subject} </a>
							</c:when>
							<c:otherwise>
								<span class="file_span prne_a">다음 게시글이 없습니다.</span>
							</c:otherwise>
						</c:choose>
					</div>

				</section>

			</article>
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
	// 게시글 삭제 전 확인
	$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_free.jpg)"});
	function del() {
		var str;
		str = confirm("삭제하시겠습니까?");
		if(str) {
			document.deleteForm.method = "post";
			document.deleteForm.action = "./freeboarddelete.do";
			document.deleteForm.submit();
		} else {
			return false;
		}
	}
	
	// 댓글 펼치기 접기
	function collapse(){
		if($('#comment_form').hasClass('hide')) {
			$('#comment_form').removeClass('hide');
			$('#comment_form').addClass('block');
			$('.comment_head i').attr('class','fas fa-angle-up');
		} else{
			$('#comment_form').removeClass('block');
			$('#comment_form').addClass('hide');
			$('.comment_head i').attr('class','fas fa-angle-down')
		}
	}
	
 	</script>
 	
	<script>
 	var bno = '${dto.bno}'; // 게시글 번호

	 // 댓글 목록
	 function cmt_list() {
	 	$.ajax({
	 		url : './fbcomment.do',
	 		type : 'get',
	 		data : {'cmt_bno' : bno},
	 		success : function(data){
	 			$('#comment').html(data);
	 		}
	 	});
	 }

 	// 들어오면 리스트 호출
	cmt_list();
	 
	// 댓글 입력
	function cmt_add() {
		var insertData = $('[name=fbcmtForm]').serializeArray();
		insertData[0].value = bno;
		console.log(insertData);
		var content = document.fbcmtForm.cmt_content.value; 

		if(content == "" || content == " "){
			alert("댓글을 입력해주세요.");
			return false;
		}
		else{
			$.ajax({
				url : "./fbcommentadd.do",
				type : "POST",
				ContentType: 'application/x-www-form-urlencoded; charset=euc-kr',
				data : insertData,
				success: function(data){
					if(data == 1){
						// 댓글 추가되면 리스트 다시 불러오고 댓글 textarea 내용 초기화
						cmt_list();
						content = "";
					}
				}
			});
		}
	 }
	
	// 댓글 삭제
	function cmt_delete(cno){
		var str;
		str = confirm("삭제하시겠습니까?");
		if(str) {
			$.ajax({
				url : './fbcommentdelete.do?cmt_cno=' + cno,
				type : 'POST',
				success : function(data){
					if(data == 1) cmt_list();
				}
			});
		} else {
			return false;
		}	
	}
	
	// 댓글 수정폼 생성
	function cmt_updateForm(cno){
		window.name = "parentForm";
		window.open("./fbcommentupdate.do?cmt_cno=" +cno, "cmtupdateForm",
				"width=550, height=250, left=200, top=100, resizable=no, scrollbars=no");
	}
 	</script>

</body>
</html>