<%@page import="com.sumhan.dto.SunhansVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<jsp:include page="head.jsp"/>
<html>
<c:if test="${empty loginUser}">
	<jsp:forward page='login.do' />
</c:if>
<head> 
	 <%
	 String userID=null;
	 if(session.getAttribute("loginUser")!=null)
	 {
		 SunhansVO loginUser=(SunhansVO) session.getAttribute("loginUser");
		 userID=loginUser.getId();
	 }
	 
	 %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content ="width=device-width, initial-scale=1">
<title>선한영향력</title>

	<link href="resoures/three/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="resoures/css/intRo.css">
	<link rel="stylesheet" href="resoures/css/customch.css">
	<link href="resoures/cssmain/custom.css" rel="stylesheet">
	<script src="resoures/js/jquery-3.3.1.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="resoures/three/js/bootstrap.js"></script>
	<script type="text/javascript">
	function autoClosingAlert(selector,delay)
	{
		var alert =$(selector).alert();
		alert.show();
		window.setTimeout(function() {alert.hide()},delay);
	}
	
	
	function chatBoxFunction(){
		var userID ='<%= userID %>'
  		$.ajax({
  			type:"POST",
  			url:"./chatBox",
  			data:{
  				userID: encodeURIComponent(userID),
  			},
  			success: function(data){
  				if(data=="")return;
  				$('#boxTable').html('');
  				var result2=data;
  				console.log(result2);
  				if(result2!=" ")
  					{
  					var parsed=JSON.parse(data);
  					var result =parsed.result;	
  					for(var i=0; i<result.length; i++)
  					{
  						if(result[i][0].value==userID)
  							{
  								result[i][0].value=result[i][1].value;
  							}else{
  								result[i][1].value=result[i][0].value;
  		  						}
  						var userName=UserNameFunction(result[i][1].value);	

  						addBox(result[i][0].value,result[i][1].value,userName,result[i][2].value,result[i][3].value,result[i][4].value);
					}
  					}
  			
  				
  				if(result2==" ")
  				{
  					addnullBox();	
  				}
  			}
  		});
  	}
	function UserNameFunction(userID){
		var userName =userID;
		var userName2=null;
		
  		$.ajax({
  			type:"POST",
  			url:"./UserNameGetServlet",
  			async:false,
  			data:{
  				userNamego: encodeURIComponent(userName),
  			},
  			success: function(data){

  					userName2=data
  			
  			}
  		});
  		return userName2;
  	}
	function addBox(lastID,toID,userName,chatContent,chatTime,profile)
	{
		
		$('#boxTable').append('<tr onclick="location.href=\'chating.jsp?toID='+encodeURIComponent(toID)+'\'">'+
			'<td style="width: 150px;"><img class="media-object img-circle"  style="width: 40px; height: 40px;" src="'+profile+'"alt=""><h5>'+lastID+'('+userName+')</h5></td>'+
			'<td>'+
			'<h5>'+chatContent+'</h5>'+
			'<div class="pull-right">'+chatTime+'</div>'+
			'</td>'+
			'</tr>');
	}
	function addnullBox()
	{
		
		$('#boxTable').append('<tr>'+
			'<td>대화한 상대가 없습니다.</td>'+
			'</tr>');
	}
	function getInfiniteBox(){
		setInterval(function(){
			getInfiniteBox();
			chatBoxFunction();
	 	},22000);
	}
	</script>
	
<style type="text/css">
	h5:hover {
		cursor: pointer;
	}
	
	img {
		margin: 5px auto;
	}
	
	#boxTable>tr td:last-child{
		vertical-align: middle;
	}
	
	#footer{
		position:initial !important;
	}
</style>

</head>
	<body>	
		<section class="auto cover_img">
			<div class="jumbotron jumbotron-fluid">
				<div class="container">
					<h1 class="display-4">메세지함</h1>
					<p class="lead">유저들과 메세지를 주고 받을 수 있습니다.</p>
				</div>
			</div>
		</section>	
		
	<div class="container bootstrap snippet">
		
		<div class="row">
			<table class="table" style="margin: 0 auto;">
				<thead>
					<tr>
						<th><h4>주고받은 메시지 목록</h4></th>
					</tr>
				</thead>
				<div style="overflow: auto; width: 100%; max-height: 450px;">
					<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin :0 aurto;">
						<tbody id="boxTable">
							
						</tbody>
					</table>
				</div>
		   </table>	
		</div>
</div>
	
	
	<script type="text/javascript">
	$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_post.jpg)"});
		$(document).ready(function()
		{
			chatBoxFunction();
			getInfiniteBox();
		});
	</script>
	</body>
</html>


<jsp:include page="foot.jsp"/>
