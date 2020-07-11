<%@page import="com.sumhan.dto.SunhansVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<c:if test="${empty loginUser}">
	<jsp:forward page='login.do' />
</c:if>
<jsp:include page="head.jsp"/>
<html>
<head> 
	 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content ="width=device-width, initial-scale=1">
<title>소개 페이지</title>

	<style>
	#wrap{
		
		position:static !important;
	}
	#footer{
	position:initial;
	
	}
	</style>
	<link href="resoures/three/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="resoures/css/intRo.css">
	<link rel="stylesheet" href="resoures/cssmain/custom.css">
	<script src="resoures/js/jquery-3.3.1.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="resoures/three/js/bootstrap.js"></script>
	<script type="text/javascript">
		function findFuntion(){
			var userID=$('#findID').val();
			
			$.ajax({
				type:"POST",
				url:'./UserRegisterCheckServlet',
				data:{userID:userID},
				success:function(result)
				{
					if(result==0)
					{
							$('#checkMessage').html('친구찾기에 성공하셨습니다.');
							$('#checkType').attr('class','modal-content panel-success');
							getFriend(userID);
					}
					else 
					{
						$('#checkMessage').html('친구를 찾을수 없습니다.');
						$('#checkType').attr('class','modal-content panel-success');
						failFriend();
					}
					$('#checkModal').modal("show");
					}
				}
			)
		};
		
		function getFriend(findID){
			$('#friendResult').html('<thead>'+
									'<tr>'+		
									'<th style="text-align:center;"><h4>검색결과</h4></th>'+		
									'</tr>'+		
									'</thead>'+		
									'<tbody>'+		
									'<tr>'+		
									'<td style="text-align:center;"><h3>'+findID+'<h3><a href="chating.jsp?toID='+encodeURIComponent(findID)+'"class="btn btn-primary pull-right">' + '메세지 보내기</a></td>'+		
									'</tr>'+
									'</tbody>');
		}
		function failFriend() {
			$('#friendResult').html('');
		}
	</script>
	
</head>
<style type="text/css">

</style>
	<body>	
		<section class="auto cover_img">
			<div class="jumbotron jumbotron-fluid">
				<div class="container">
					<h1 class="display-4">친구찾기</h1>
					<p class="lead">친구를 찾아보세요.</p>
				</div>
			</div>
		</section>	

	<div class="container" style="margin-top:100px;">
			<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd;">
				<tbody>
					<tr>
						<td style="width: 120px;" ><h5 style="text-align: center; vertical-align: middle;" class="form-control" >친구 아이디</h5></td>
						<td><input class="form-control" type="text" id="findID" maxlength="20" placeholder="찾을 아이디를 입력하세요." /></td>
					</tr>
					<tr>
						<td colspan="2"><button class="btn btn-primary pull-right" onclick="findFuntion();">검색</button></td>
					</tr>
				</tbody>
			</table>
		</div>
	<div class="container">
			<table id="friendResult" class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd;" ></table>
	</div>
	<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="vertical-alignment-helper">
		<div class="modal-dialog vertical-align-center">
			<div id="checkType" class="modal-content panel-info">
				<div class="modal-header panel-heading">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times</span>
						<span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">
						확인 메세지
					</h4>
				</div>
				<div id="checkMessage" class="modal-body">
				</div>
				<div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
		</div>
	</div>
	
	</div>
	</div>
	<script type="text/javascript">
	$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_post.jpg)"});
	</script>
<jsp:include page="foot.jsp"/>
	</body>
</html>


