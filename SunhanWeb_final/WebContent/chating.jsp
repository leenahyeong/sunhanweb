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
       System.out.println(userID);
    }
    
    String toID=null;
    if(request.getParameter("toID")!=null)
    {       
       toID=(String) request.getParameter("toID");
    }
    String profilesrc=null;
    if(session.getAttribute("realprofile")!=null)
    {
       profilesrc=(String)session.getAttribute("realprofile");
    }
    %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content ="width=device-width, initial-scale=1">
<title>소개 페이지</title>

   <link href="resoures/three/css/bootstrap.min.css" rel="stylesheet">
   <link rel="stylesheet" href="resoures/css/intRo.css">
   <link rel="stylesheet" href="resoures/cssmain/custom.css">
   
   <link rel="stylesheet" href="resoures/css/customch.css?ver=1.1">
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
   function submitFunction(){
      var fromID='<%= userID %>';
      var toID='<%= toID %>';
      var chatContent =$('#chatContent').val();
   $.ajax({
         type: "POST",
         url: "./ChatSubmitServlet",
         data: {
            fromID: encodeURIComponent(fromID),
            toID: encodeURIComponent(toID),
            chatContent:encodeURIComponent(chatContent),
         },
      success:function(result){
         if(result==1)
            {
              autoClosingAlert('#successMessage',2000);
            }
         else if(result==0)
            {
            autoClosingAlert('#dangerMessage',2000);
            }
         else 
            {
               autoClosingAlert('#warningMessage',2000);
            }
      }
   });

      $('#chatContent').val('');   
      
   }
   var lastID = 0;
   function chatListFunction(type)
   {
      var fromID='<%= userID %>';
      var toID='<%= toID %>';
      
      $.ajax({
         type: "POST",
         url: "./ChatListServlet",
         data: {
            fromID: encodeURIComponent(fromID),
            toID: encodeURIComponent(toID),
            listType:type
         },
         success: function(data){
            if(data=="") return;
            var parsed =JSON.parse(data);
            var result=parsed.result;
            for(var i=0; i<result.length; i++)
            {
               if(result[i][0].value==fromID)
                  {
                   result[i][0].value='본인';
                  }
               addChat(result[i][0].value,result[i][2].value,result[i][3].value);   
               
            }
            lastID =Number(parsed.last);
         }
      });
   }
   function addChat(chatName,chatContent,chatTime)
   {
      var src='resoures/images/userEX.png';
      var prosrc='<%= profilesrc %>';
      
      
      $('#chatList').append('<div class="row">'+
                       '<div class="col-lg-12">'+
                       '<div class="media">'+
                       '<a class="pull-left" href="#">'+
                       '<img class="media-object img-circle"  style="width: 30px; height: 30px;" src="resoures/images/userEX.png" alt="">'+
                       '</a>'+
                       '<div class="media-body">'+
                       '<h4 class="media-heading">'+
                       chatName+
                       '<span class="small pull-right">'+
                       chatTime+
                       '</span>'+
                       '</h4>'+
                       '<p>'+
                       chatContent+
                       '</p>'+
                       '</div>'+
                       '</div>'+
                       '</div>'+
                       '</div>'+
                       '</hr>');
      $('#chatList').scrollTop($('#chatList')[0].scrollHeight);
   }
   function getInfiniteChat(){
      setInterval(function()
      {
         chatListFunction(lastID);
      },5000);
   }
   </script>
   <style>
   .mobile-nav a, .mobile-nav li {
      font-size: 16px;
   }
   </style>
</head>
   <body>   
   <section class="auto cover_img">
      <div class="jumbotron jumbotron-fluid">
         <div class="container">
            <h1 class="display-4">실시간 채팅</h1>
            <p class="lead">유저들과 메세지를 실시간으로 주고 받을 수 있습니다.</p>
         </div>
      </div>
   </section>   

   <div class="container bootstrap snippet" style="margin-top:30px;">
      
      <div class="row">
         <div class="col-xs-12">
            <div class="portlet portlet-default">
               <div class="portlet-heading">
                  <div class="portlet-title">
                     <h4><i class="fa fa-circle text-green"></i>
                     <span style="margin-left:10px;">실시간 채팅창</span>
                     </h4>
                  </div>
                  <div class="clearfix"></div>
               </div>
               <div id="chat" class="panel-collapse collapse in">
                  <div id="chatList" class="portlet-body char-widget" style="overflow-y: auto; width:auto; height:600px;">
                  </div>
                  <div class="portlet-footer">
                     <div class="row" style="height: 90px;">
                        <div class="form-group col-xs-10">
                           <textarea style="height:80px;" id="chatContent" class="form-control" placeholder="메세지를 입력하세요." maxlength="100"></textarea>
                        </div>
                        <div class="form-group col-xs-2">
                           <button type="button" class="btn btn-default pull-right" style="height: 100%;" onclick="submitFunction();">전송</button>
                           <div class="clearfix"></div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
   <div class="alert alert-succes" id="successMessage" style="display:none;">
      <strong>메세지 전송에 성공하셨습니다.</strong>
   </div>
   <div class="alert alert-succes" id="dangerMessage" style="display:none;">
      <strong>이름과 내용을 모두 입력하세요.</strong>
   </div>
   <div class="alert alert-succes" id="warningMessage" style="display:none;">
      <strong>데이터 베이스 오류가 발생하셨습니다.</strong>
   </div>
   
   <jsp:include page="foot.jsp"/>
   <script type="text/javascript">
   $(".cover_img").css({"background-image":"url(resoures/images/jb/jb_post.jpg)"});
      $(document).ready(function()
      {
         chatListFunction('0');
         getInfiniteChat();
      });
   </script>
   </body>
</html>