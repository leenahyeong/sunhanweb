<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="resoures/cssmain/style.css" rel="stylesheet">
<link href="resoures/cssmain/custom.css" rel="stylesheet">
</head>
<body>
	<div>
		<header class="cmtheader">
		<h4>댓글수정</h4>
		</header>
		<form target="parentForm" name="cmtupdateForm">
			<div class="update_cmt_form">
				<div class="float-right inline">
					<input type="hidden" value="${comment.cmt_cno}" name="cmt_cno">
					<input type="button" value="수정" class="btn_text"
						onclick="cmt_update()">
					<input
						type="button" value="취소" class="btn_text" onclick="window.close()">
	
				</div>
				<span name="update_cmt_name">${loginUser.name}</span>
				<textarea class="update_comment_write" name="update_cmt_content">${comment.cmt_content}</textarea>
			</div>
		</form>
		
	</div>
	
 <script src="resoures/js/jquery.min.js"></script>
<script>
// 댓글 수정
function cmt_update(){
	var insertData = $('[name=cmtupdateForm]').serializeArray();
	console.log(insertData);
	var content = document.cmtupdateForm.update_cmt_content.value; 

	if(content == "" || content == " "){
		alert("댓글을 입력해주세요.");
		return false;
	}
	else{
		$.ajax({
			url : "./fbcommentupdate.do?cmt_cno=" + insertData[0].value,
			type : "POST",
			ContentType: 'application/x-www-form-urlencoded; charset=euc-kr',
			data : insertData,
			success: function(data){
				if(data == 1){
					// 댓글 추가되면 리스트 다시 불러오고 댓글 textarea 내용 초기화
					content = "";
					window.opener.document.location.reload();
					window.close();
				}
			}
		});
	}
}
</script>	
</body>
</html>