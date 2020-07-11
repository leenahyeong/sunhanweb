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

<c:if test="${empty loginUserID}">
	<script>
		alert("회원만 접근할 수 있습니다.");
		location.href = "javascript:history.back()";
	</script>
</c:if>

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
			<section>
				<form method="post" action="photoadd.do" name="photoForm" enctype="multipart/form-data">
					<p class="red p_info">*표시 항목은 필수사항 입니다.</p>
					<table class="table board_table" id="addform">
						<tbody>
							<tr>
								<th>제목 <span class="red">*</span></th>
								<td><input type="text" name="subject"></td>
							</tr>

							<tr>
								<th>내용 <span class="red">*</span></th>
								<td><textarea id="content" name="content"></textarea></td>
							</tr>

							<tr class="bt_end">
								<th>썸네일 <span class="red">*</span></th>
								<td><input type="file" id="thumb" name="thumbnail" accept="image/jpg, image/png, image/jpeg"></td>
							</tr>

							<tr class="center">
								<td colspan="2">
									<input type="submit" value="등록" class="de_btn" id="add">
									<input type="reset" value="취소" class="reset_btn" onclick="location.href='javascript:history.back()'">
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
		$(document).ready(
			function() {
				$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_photo.jpg)"});
				
				$('#content').summernote(
						{
							height : 300, // set editor height
							minHeight : null, // set minimum height of editor
							maxHeight : null, // set maximum height of editor
							focus : false,
							lang : 'ko-KR',
							codemirror : { // codemirror options
								theme : 'monokai'
							},
							callbacks : {
								onImageUpload : function(files, editor, welEditable) {
									sendFile(files[0], this);
								}
							}
						});

			function sendFile(file, editor) {
				data = new FormData();
				data.append("uploadFile", file);
				$.ajax({
					data : data,
					type : "POST",
					url : "./summerupload.do",
					cache : false,
					contentType : false,
					processData : false,
					success : function(data) {
						$(editor).summernote('editor.insertImage', data.url);
					}
				});
			}
			
				
				
				$('input[type=file]').change(function(){
					var thumbnail = document.getElementById("thumb").value;
					// . 뒤에 확장자 가져오고 소문자로 만듬
					var pattern = thumbnail.slice(thumbnail.indexOf(".")+1).toLowerCase();
					console.log(pattern);
					if(pattern != null) {
						if(pattern != "jpg" && pattern != "png" && pattern != "jpeg"){
							window.reset = function(e) {
								  e.wrap('<form>').closest('form').get(1).reset();
								  e.unwrap();
							}
							alert("이미지 파일(jpg, png, jpeg)만 등록 가능합니다.");
							return false;
						} else {
							// 이미지 파일일때
							
						}
					}
				});

			// 포토앨범 글쓰기 유효성체크
			var add_btn = document.getElementById("add");
			add_btn.onclick = function() {
				if (document.photoForm.subject.value == ""
					|| document.photoForm.subject.value == " ") {
					alert("제목을 입력해주세요.");
					document.photoForm.subject.focus();
					return false;
				}
				if (document.photoForm.content.value == ""
					|| document.photoForm.content.value == " ") {
					alert("내용을 입력해주세요.");
					document.photoForm.content.focus();
					return false;
				}

				if (document.photoForm.thumbnail.value.length == 0) {
					alert("썸네일 이미지를 업로드 해주세요.");
					return false;
				} else {
					//location.href = 'photoadd.do';
					document.photoForm.submit();
				}
			}
		});
	</script>

</body>
</html>