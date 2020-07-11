<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
 <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.16/dist/summernote-bs4.min.css" rel="stylesheet">
	
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
			<section>
				<form method="post" name="updateForm">
					<p class="red p_info">*표시 항목은 필수사항 입니다.</p>
					<table class="table board_table" id="addform">
						<tbody>
							<tr>
								<th>제목 <span class="red">*</span></th>
								<td><input type="text" name="subject"
									value="${dto.subject}"></td>
							</tr>

							<tr class="bt_end">
								<th>내용 <span class="red">*</span></th>
								<td><textarea id="summernote" name="content">${dto.content}</textarea></td>
							</tr>

							<tr class="center">
								<td colspan="2">
									<input type="submit" value="등록" class="de_btn" id="update">
									<input type="hidden" name="bno" value="${dto.bno}" />
									<input type="hidden" name="page" value="${paging}" />
									<input type="reset" value="취소" class="reset_btn"
									onclick="location.href='javascript:history.back()'">
								</td>
							</tr>
						</tbody>
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
 <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.16/dist/summernote-bs4.min.js"></script>
 <!-- Template Main JS File -->
 <script src="resoures/js/custom.js"></script>

	<script>
		$(document).ready(function() {
			$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_free.jpg)"});
			
			$('#summernote').summernote(
				{
					height : 300, // set editor height
					minHeight : null, // set minimum height of editor
					maxHeight : null, // set maximum height of editor
					focus : false,
					lang : 'ko-KR',
					codemirror : {
						mode : 'text/html',
						htmlMode : true,
						lineNumbers : true,
						theme : 'monokai'
					},
					callbacks: {
						onImageUpload: function(files, editor, welEditable) {
							sendFile(files[0], this);
						}
					}
				});
				
				function sendFile(file, editor) {
					data = new FormData();
					data.append("uploadFile", file);
					$.ajax({
						data: data,
						type: "POST",
						url : "./summerupload.do",
						cache: false,
						contentType: false,
						processData : false,
						success : function(data) {
							$(editor).summernote('editor.insertImage', data.url);
						}
					});
				}
				

				// 자유게시판 업데이트 유효성체크
				var update_btn = document.getElementById("update");
				
					update_btn.onclick = function() {
						if (document.updateForm.subject.value == ""
							|| document.updateForm.subject.value == " ") {
							alert("제목을 입력해주세요.");
							document.updateForm.subject.focus();
							return false;
						}

						if (document.updateForm.content.value == ""
								|| document.updateForm.content.value == " ") {
							alert("내용을 입력해주세요.");
							document.updateForm.content.focus();
							return false;
						}

						else {
							document.updateForm.submit();
						}
					};
			});
	</script>
</body>
</html>