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
	
		<c:if test="${loginUser.admin ne 2}">
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
				<form method="post" name="updateForm" enctype="multipart/form-data">
					<p class="red p_info">*표시 항목은 필수사항 입니다.</p>
					<input type="hidden" name="bno" value="${dto.bno}" />
					<input type="hidden" name="page" value="${paging}" />
					<table class="table board_table" id="addform">
						<tbody>
							<tr>
								<th>제목 <span class="red">*</span></th>
								<td><input type="text" name="subject"
									value="${dto.subject}"></td>
							</tr>

							<tr>
								<th>내용 <span class="red">*</span></th>
								<td><textarea id="summernote" name="content">${dto.content}</textarea></td>
							</tr>

							<c:choose>
								<c:when test="${dto.file1 ne null}">
									<tr>
										<th>첨부파일1 <img src="resoures/images/quest.png"
											data-toggle="popover" data-trigger="hover"
											data-content="기존 파일을 삭제하면 파일 수정이 가능합니다."
											data-placement="bottom">
										</th>
										<td class="file_td" id="file_td1">
											<span>
												<a href="noticedown.do?file1=${dto.file1}" class="filedown">
													<input type="hidden" id="file1" name="file1" value="${dto.file1}"> ${dto.file1}
												</a>
											</span>
											<span>
												<img src="resoures/images/delete.png" class="img_icon" id="del_img1">
											</span>
										</td>

										<td class="none" id="reFile1">
											<input type="file">
										</td>
									</tr>
								</c:when>

								<c:otherwise>
									<tr>
										<th>첨부파일1 <img src="resoures/images/quest.png"
											data-toggle="popover" data-trigger="hover"
											data-content="기존 파일을 삭제하면 파일 수정이 가능합니다."
											data-placement="bottom">
										</th>

										<td id="newFile1"><input type="file" name="newfile1">
										</td>
									</tr>
								</c:otherwise>

							</c:choose>


							<c:choose>
								<c:when test="${dto.file2 ne null}">
									<tr>
										<th>첨부파일2 <img src="resoures/images/quest.png"
											data-toggle="popover" data-trigger="hover"
											data-content="기존 파일을 삭제하면 파일 수정이 가능합니다."
											data-placement="bottom" id="popover">
										</th>

										<td class="file_td" id="file_td2">
											<span>
												<a href="noticedown.do?file2=${dto.file2}" class="filedown">
													<input type="hidden" id="file2" name="file2" value="${dto.file2}"> ${dto.file2}
												</a>
											</span> 
											<span>
												<img src="resoures/images/delete.png" class="img_icon" id="del_img2">
											</span>
										</td>

										<td class="none" id="reFile2"><input type="file">
										</td>
									</tr>

								</c:when>

								<c:otherwise>
									<tr class="bt_end">
										<th>첨부파일2 <img src="resoures/images/quest.png"
											data-toggle="popover" data-trigger="hover"
											data-content="기존 파일을 삭제하면 파일 수정이 가능합니다."
											data-placement="bottom">
										</th>

										<td id="newFile2"><input type="file" name="newfile2">
										</td>
									</tr>
								</c:otherwise>

							</c:choose>

							<tr class="center">
								<td colspan="2"><input type="submit" value="등록"
									class="de_btn" id="update"> <input type="reset"
									value="취소" class="reset_btn"
									onclick="location.href='javascript:history.back()'"></td>
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
			$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_notice.jpg)"});
			
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
					data.append("summerimg", file);
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
				
				/* 업데이트에서 파일 삭제하면 수정 어쩌고 팝오버 */
				$('[data-toggle="popover"]').popover();

				// 공지사항 업데이트 유효성체크
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
							//location.href = 'noticeupdate.do?bno=${dto.bno}&page=${paging}';
						}
					};

				//첨부파일 삭제 여부
				var del_img1 = document.getElementById("del_img1");

				del_img1.onclick = function() {
					if (confirm("첨부파일을 삭제하시겠습니까?")) {
						$('#file_td1').css('display', 'none');
						$('#file1').val("null");
						$('#reFile1').css('display', 'block');
						$('#reFile1>input').attr('name', 'refile1');
					} else {
						return false;
					}
				};

			});
	</script>
	
	<script>
		var del_img2 = document.getElementById("del_img2");
		del_img2.onclick = function() {
			if (confirm("첨부파일을 삭제하시겠습니까?")) {
				$('#file_td2').css('display', 'none');
				$('#file2').val("null");
				$('#reFile2').css('display', 'block');
				$('#reFile2>input').attr('name', 'refile2');
			} else {
				return false;
			}
		};
	</script>


</body>
</html>