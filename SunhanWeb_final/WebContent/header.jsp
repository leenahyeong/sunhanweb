<!DOCTYPE html>
<%@page import="com.sumhan.dto.SunhansVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">

<head>
<%
	String userID = null;
	if (session.getAttribute("loginUser") != null) {
		SunhansVO loginUser = (SunhansVO) session.getAttribute("loginUser");
		userID = loginUser.getId();
	}
%>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>인덱스</title>
<meta content="" name="descriptison">
<meta content="" name="keywords">



<script type="text/javascript">
  	function getUnread(){
  		$.ajax({
  			type:"POST",
  			url:"./chatUnread",
  			data:{
  				userID: encodeURIComponent('${loginUserID}'),
			},
			success : function(result) {
				if (result >= 1) {
					showUnread(result);
				} else {
					showUnread('');
				}
			}
		});
	}
	function getInfiniteUnread() {
		setInterval(function() {
			getUnread();
		}, 4000);
	}
	function showUnread(result) {
		if (result == 0 || result == null || result == " ") {
			$('#unread').html("(0)");
			return;
		}
		$('#unread').html("(" + result + "개)");
	}
</script>
</head>

<body>
	<div id="wrap">
		<!-- ======= Header ======= -->
		<header id="header" class="fixed-top">

			<div class="container-flude" id="tophead">
				<div class="text-right container">
					<ul class="topbar">
						<c:choose>
							<c:when test="${!empty loginUserID}">
								<li><a href="mypage.do" id="hddh">마이페이지</a></li>
								<li><a href="logout.do" id="hddh">로그아웃</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="login.jsp" id="hddh">로그인</a></li>
								<li><a href="regis.do" id="hddh">회원가입</a></li>
							</c:otherwise>
						</c:choose>
					</ul>

				</div>
				<hr>
			</div>


			<div class="container header2">

				<div class="logo float-left">
					<!-- Uncomment below if you prefer to use an text logo -->
					<!-- <h1 class="text-light"><a href="#header"><span>NewBiz</span></a></h1> -->
					<a href="main.jsp" class="scrollto"><img
						src="resoures/images/logo.png" alt="" class="img-fluid"
						style="height: 47px"></a>
				</div>

				<nav class="main-nav float-right d-none d-lg-block">
					<ul>
						<li class="drop-down"><a href="introduce.jsp">소개</a>
							<ul>
								<li><a href="introduce.jsp">인 사 말</a></li>
								<li><a href="introduce2.jsp">설립취지</a></li>
							</ul></li>

						<li class="drop-down"><a href="">소식</a>
							<ul>
								<li><a href="notice.do">공지사항</a></li>
								<li><a href="photo.do">포토앨범</a></li>
								<li><a href="freeboard.do">자유게시판</a></li>
							</ul></li>

						<!--     후원자만 보기     
						<li class="drop-down"><a href="">매장서비스</a>
							<ul>
								<li><a href="#">매장 등록</a></li>
								<li><a href="#">후원매장 관리</a></li>
							</ul></li>
						-->
						<li class="drop-down"><a href="">예약서비스</a>
							<ul>
								<li><a href="storelist.jsp">매장 리스트</a></li>
								<li><a href="reserveGuide.jsp">예약 가이드</a></li>
							</ul></li>

						<li class="drop-down"><a href="#contact">커뮤니티</a>
							<ul>
								<li><a href="rank.jsp">랭킹</a></li>
								<li><a href="box.jsp">메세지함<span id="unread"
										class="label label-info"></span></a></li>
								<li><a href="chating.jsp">-실시간 채팅-</a></li>
								<li><a href="frendFind.jsp">친구찾기</a></li>
							</ul></li>
						<c:choose>
							<c:when test="${!empty loginUserID}">
								<li class="none mobile_b"><a href="mypage.do">마이페이지</a></li>
								<li class="none mobile_b"><a href="logout.do">로그아웃</a></li>
							</c:when>
							<c:otherwise>
								<li class="none mobile_b"><a href="login.do">로그인</a></li>
								<li class="none mobile_b"><a href="regis.do">회원가입</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</nav>
				<!-- .main-nav -->

			</div>
		</header>
		<!-- #header -->
		<script>
			// attribute 바로 쓸수있음
			var loginUserID = '${loginUserID}';
			console.log(loginUserID);
			if (loginUserID != null) {
				$(document).ready(function() {
					getUnread();
					getInfiniteUnread();
				});
			}
		</script>
</body>
</html>