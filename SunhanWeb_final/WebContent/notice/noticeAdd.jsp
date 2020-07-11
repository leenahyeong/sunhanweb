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
	
		<c:if test="${loginUserAdmin ne 2}">
			<script>
				alert("관리자만 접근할 수 있습니다.");
				location.href="javascript:history.back()";
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
				<h1 class="display-4">공지사항</h1>
				<p class="lead">선한영향력의 소식을 알려드립니다.</p>
			</div>
		</div>
	</section>
	
  <div class="container real_c">
   <section>
   	<form method="post" action="noticeadd.do" name="noticeForm" enctype="multipart/form-data">
    <p class="red p_info">*표시 항목은 필수사항 입니다.</p>
    <table class="table board_table" id="addform">
     <tbody>
      <tr>
       <th>제목 <span class="red">*</span></th>
       <td><input type="text" name="subject"></td>
      </tr>

      <tr>
       <th>내용 <span class="red">*</span></th>
       <td><textarea id="summernote" name="content"></textarea></td>
      </tr>

      <tr>
       <th>첨부파일1</th>
       <td><input type="file" name="file1"></td>
      </tr>

      <tr class="bt_end">
       <th>첨부파일2</th>
       <td><input type="file" name="file2"></td>
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
	$(document).ready(function(){
		$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_notice.jpg)"});
		
		$('#summernote').summernote({
			height : 300, // set editor height
			minHeight : null, // set minimum height of editor
			maxHeight : null, // set maximum height of editor
			focus : false,
			lang : 'ko-KR',
			codemirror: { // codemirror options
			    theme: 'monokai'
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

		// 공지사항 글쓰기 유효성체크
		var add_btn = document.getElementById("add");
		add_btn.onclick = function() {
			if (document.noticeForm.subject.value == ""
				|| document.noticeForm.subject.value == " ") {
				alert("제목을 입력해주세요.");
				document.noticeForm.subject.focus();
				return false;
			}
			if (document.noticeForm.content.value == ""
				|| document.noticeForm.content.value == " ") {
				alert("내용을 입력해주세요.");
				document.noticeForm.content.focus();
				return false;
			}
			else {
				location.href = './noticeadd.do';
			}
		}
	});
 </script>
 
</body>
</html>