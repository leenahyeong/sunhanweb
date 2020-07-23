<%@page import="storefile.storefileDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.store.dto.StoreVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	 String Addr=null;
	 String shopname=null;
     String comment=null;
     
     String storefile=null;
	
	 if(session.getAttribute("Store")!=null)
	 {
		 StoreVO Store=(StoreVO) session.getAttribute("Store");
		 Addr=Store.getAddr();
	 	 shopname=Store.getShopname();
	 	 comment=Store.getComments();
	 }
	 if(session.getAttribute("Storefilename")!=null)
	 {
		 ArrayList<storefileDTO> storefilename=new ArrayList();
		 storefilename=(ArrayList) session.getAttribute("Storefilename");
		 storefile=storefilename.get(1).getFileRealName();
	 }
	 %>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width">
<title>선한영향력</title>

<link href="resoures/cssmain/custom.css?ver=1.6" rel="stylesheet">
<link href="resoures/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="resoures/css/slider-pro.min.css?ver=1.1" media="screen" />
<link rel="stylesheet" type="text/css"
	href="resoures/css/storeslide.css?ver=1.2" media="screen" />
<link rel="stylesheet" href="resoures/css/login.css">
<link rel="stylesheet" href="resoures/css/store.css?ver=1.4">
<link rel="stylesheet" href="resoures/css/intRo.css">
<link rel="stylesheet" href="resoures/cssmain/tabs.css?ver=1.1">
<link rel="stylesheet" href="resoures/cssmain/tabstyles.css?ver=1.1">

				
<style>
* {
	font-family: 'Noto Sans KR';
}

.main-nav a{
	font-size: 16px;
}

.jumbotron{
	background-color:inherit !important;
	background: rgba(0, 0, 0, .6) !important;
}

input[type="button"]{
	background: #f5f5f5;
    color: #151515;
    border: 1.1px solid #d8d8d8;
    font-weight: 400;
    padding: 8px 35px;
    font-size: 14px;
    -webkit-box-shadow: none;
    box-shadow: none;
    border-radius: 0;
    -webkit-border-radius: none;
}

input[type="button"]:hover{
	background:#94CE68;
	color: #fff;
	border: 1.1px solid #94CE68;
}

.reserve_btn{
	margin: 0;
}

.align-middle{
	background: #f1f1f1;
	border-right: solid 1px #dee2e6;
}

.overlay_info {
	border-radius: 6px;
	margin-bottom: 12px;
	float: left;
	position: relative;
	border: 1px solid #ccc;
	border-bottom: 2px solid #ddd;
	background-color: #fff;
}

.overlay_info:nth-of-type(n) {
	border: 0;
	box-shadow: 0px 1px 2px #888;
}

.overlay_info a {
	display: block;
	background: #d95050;
	background: #d95050
		url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
		no-repeat right 14px center;
	text-decoration: none;
	color: #fff;
	padding: 12px 36px 12px 14px;
	font-size: 14px;
	border-radius: 6px 6px 0 0
}

.overlay_info a strong {
	background:
		url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/place_icon.png)
		no-repeat;
	padding-left: 27px;
}

.overlay_info a:hover{
	color: #fff;
}

.overlay_info .desc {
	padding: 14px;
	position: relative;
	min-width: 190px;
	height: 106px
}

.overlay_info img {
	vertical-align: top;
}

.overlay_info .address {
	font-size: 12px;
	color: #333;
	position: absolute;
	left: 80px;
	right: 10px;
	/* right: 14px; */
	top: 24px;
	white-space: normal
}

.overlay_info:after {
	content: '';
	position: absolute;
	margin-left: -11px;
	left: 50%;
	bottom: -12px;
	width: 22px;
	height: 12px;
	background:
		url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png)
		no-repeat 0 bottom;
}
.map_img{
    position: absolute;
    left: 10px;
    width: 35%;
    top: 3px;
}

.table{
	border-bottom: none;
}

#wrap{
	overflow-x: hidden !important;
}

#tabs_ul li {
	border-top: solid 1px #ddd;
}

#tabs_ul .tab-current{
	border-top: none !important;
}

#section-flip-3>.auto{
	margin: 0 30px;
}

.reserve_sec {
	width: 60%;
}

.fa-bars {
	font-size: 24px;
}

.mobile-nav a, .mobile-nav li {
	font-size: 16px;
}

@media (max-width : 730px) {
	.reserve_sec {
		width: 100%;
	}
}
</style>
</head>

<body>
	<jsp:include page="head.jsp" />
	
	<section class="auto cover_img">
			<div class="jumbotron jumbotron-fluid">
				<div class="container">
					<h1 class="display-4">매장</h1>
					<p class="lead">매장에 관한 정보를 제공합니다.</p>
				</div>
			</div>
		</section>
		
	<div class="type header_mobile" style="margin-top:50px;">		
		<div id="example3" class="slider-pro">
			<div class="sp-slides">
				<div class="sp-slide">
					<img class="sp-image"
						data-src="${Storefilename.get(0).getFileRealName()}" />
				</div>

				<div class="sp-slide">
					<img class="sp-image"
						data-src="${Storefilename.get(1).getFileRealName()}" />

				</div>

				<div class="sp-slide">
					<img class="sp-image"
						data-src="${Storefilename.get(2).getFileRealName()}" />

				</div>

			</div>

			<div class="sp-thumbnails">
				<img class="sp-thumbnail"
					src="${Storefilename.get(0).getFileRealName()}" /> <img
					class="sp-thumbnail"
					src="${Storefilename.get(1).getFileRealName()}" /> <img
					class="sp-thumbnail"
					src="${Storefilename.get(2).getFileRealName()}" />
			</div>
		</div>
		
		<div class="col-md-12">
			<h3 class="text-center" style="font-weight: bold; margin-top:20px;">
				${Store.shopname}
				<c:if test="${loginUser.admin eq 0}">
					<a href="chating.jsp?toID=${Store.userid}"><ion-icon name="chatbubbles-outline"></ion-icon></a>
				</c:if>			
				<p style="background-color: skyblue; font-size: 28px; color: white; margin-left: 44%; margin-top:20px;">${Store.food}</p>
			</h3>
			<h4 class="text-center">
				<ion-icon name="location-outline"></ion-icon> <span style="color:#3a3a3a; font-size:18px;">${Store.addr}</span></h4>
		</div>
		
		</div>
		
		<div class="type header_pc" style="height: 46vh; margin-top:160px; margin-left:15px;">
				
			<div class="container" style="margin-bottom: 20px;">
			
				<!-- <div class="row">
					<div class="col-md-9">
						<h3 class="text-left"style="font-weight: bold; font-size: 38px">
							매장 정보    
						</h3>
					</div>
					<div class="col-md-3 right">
						예약서비스 >
						<a href="storelist.jsp">매장 리스트 </a>
						> 매장정보
						
					</div>
				</div> -->
			</div>
			
			<div
				style="width: 50%; overflow: hidden; margin-left: 220px; float: left;">
				<div id="example2" class="slider-pro">
					<div class="sp-slides">
						<div class="sp-slide">
							<img class="sp-image"
								src="${Storefilename.get(0).getFileRealName()}"/>

						</div>

						<div class="sp-slide">
							<img class="sp-image"
								src="${Storefilename.get(1).getFileRealName()}" />

						</div>

						<div class="sp-slide">
							<img class="sp-image"
								src="${Storefilename.get(2).getFileRealName()}" />

						</div>
					</div>
				</div>
			</div>
			<div style="float: left; padding: 25px; margin-left: 35px; height: 336px; background-color: aliceblue;">

				<div>
					<h3 class="text-left" style="font-weight: bold; color: black;">
						${Store.shopname}	
						<c:if test="${loginUser.admin eq 0}">
							<a href="chating.jsp?toID=${Store.userid}"><ion-icon name="chatbubbles-outline"></ion-icon></a>
						</c:if>	
						<span style="background-color: skyblue; font-size: 28px; display: grid; margin-left: 30px; text-align: right; color: white; display: table-cell; -webkit-text-emphasis-style: filled;">${Store.food}</span>
					</h3>
				</div>
				<h5 class="text-center" style="font-weight: bold;"></h5>
				<div
					style="width: 100%; margin-top: 20px;position: inherit; background-color: rgb(211, 211, 211, 0.3); border-radius: 18px 18px 18px 18px;">
					<div style="padding: 14px">
						<h7 style="text-align: left; color:black;font-weight: bold;">사장님
						한마디!</h7>
						<br>
						<br> ${Store.comments} <br>
						<br><ion-icon name="location-outline"></ion-icon>${Store.addr}

					</div>
				</div>
				
			</div>
			
		</div>


		<section>
			<div class="tabs tabs-style-flip mtop-20">
				<nav style="margin-top: 50px;">
					<ul class="bdbottom_none bdtop_none bd_ddd" id="tabs_ul">
						<li><a href="#section-flip-5" class="icon"><i class="fas fa-info-circle"></i><span>가게 정보</span></a></li>
						<li><a href="#section-flip-4" class="icon"><i class="fas fa-edit"></i><span>예약</span></a></li>
						<li><a href="#section-flip-2" class="icon"><i class="fas fa-star"></i><span>리뷰</span></a></li>
							
					</ul>
				</nav>
				<div class="content-wrap bd_ddd bdtop_none mbuttom-100">
					<section id="section-flip-1">
						<div>
							<ul class="left store_detail">
								<li>
									<ion-icon name="location-outline"></ion-icon>
									<p>${Store.addr}</p>
								</li>
							
								<li>
									<ion-icon class="rotation" name="chatbubble-ellipses-outline"></ion-icon>
									<p>${Store.comments}</p>
								</li>

								<%-- <li>
									<i class="fas fa-coins"></i>
									<p>${Store.price}원</p>
								</li> --%>
								
								<li>
									<ion-icon name="time-outline"></ion-icon>
									<p>${open1} : ${open2} - ${close1} : ${close2}</p>
								</li>
								
								<li>
									<ion-icon name="call-outline"></ion-icon>
									<p>${Store.getStorePhone()}</p>
								</li>
								
								<li>
									<ion-icon name="information-circle-outline"></ion-icon>
									<p>${Store.information}</p>
								</li>
								
							</ul>		
				
							<div id="map" style="width: 100%; height: 380px;"></div>
						
						</div>
					</section>
					
					
					
					<section id="section-flip-2">
						<div class="mg_auto reserve_sec">
							<div class="panel panel-default">
								<c:choose>
									<c:when test="${loginUser.admin eq 0}">
										<c:choose>
											<c:when test="${result eq -1}">
												<div class="center pd100">
													<h1 style="margin-bottom:20px;">이미 예약하신 가게입니다.</h1>
													<input type="button" class="de_btn mtop-40" value="예약정보확인하기" onclick="location.href='reserveresult.do?userid=${loginUserID}&storeid=${Store.userid}'">
												</div>
											</c:when>
												
											<c:when test="${result eq 0 || result eq 1}">
												<c:choose>
													<c:when test="${reserveCheck eq 1}">
														<form method="post" name="reservForm">
															<table class="table mtop-20" id="rv_table">
																<tr class="bd_lr">
																	<input type="hidden" name="storeid" value="${Store.userid}">
																	<input type="hidden" name="result" value="${result}">
																	<th class="align-middle" style="width:25%;">방문예정인원 <span class="red">*</span></th>
																	<td class="left"><input type="number" name="personnel" class="graybox"> 명</td>
																</tr>
																<tr class="bd_lr">
																	<th class="align-middle">방문예정시간 <span class="red">*</span></th>
																	<td class="left">
																		<input type="hidden" name="now" value="${now}">
																		<span style="padding: 0 10px;font-weight: 400;">${now}</span>
																		
																		<select id="time_select" name="option" class="search_select outline graybox">
																			<c:set var="time" value="${open1}" />
																			<c:forEach begin="${open1}" end="${close1}">
																				<option value="${time}"
																					<c:if test="${option == time}">selected</c:if>>${time}</option>
																				<c:set var="time" value="${time+1}" />
																			</c:forEach>
																		</select>
																		
																		<input type="number" name="minute" placeholder="00 - 59" class="graybox"> 분
																	</td>
																</tr>
																
																<tr>
																	<th colspan="2"><input type="button" class="reserve_btn" value="예약하기" onclick="reserve()" /></th>
																</tr>
															</table>
					
														</form>	
													</c:when>
													
													<c:otherwise>
														<h1 class="center pd100">예약시간이 아닙니다.</h1>
													</c:otherwise>
													
												</c:choose>
												
											</c:when>
											
											<c:otherwise>
												안나온다고 보면됨.
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<h1 class="center pd100">후원아동이 아닙니다.</h1>
									</c:otherwise>
									
								</c:choose>
								
							</div>
						</div>
					</section>
					
					
					
					<section id="section-flip-3">
						<div class="auto">
					
						</div>
						<!-- <h1 class="center pd100">리뷰가 없습니다.</h1> -->
					
					</section>

				</div><!-- /content -->
			</div><!-- /tabs -->
		</section>
	
 	<%@include file="../footer.jsp"%>
	
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="resoures/js/jquery.min.js"></script>
<script type="text/javascript" src="resoures/js/jquery.sliderPro.min.js"></script>
<script type="text/javascript" src="resoures/js/jquery.fancybox.js"></script>
<!-- <script type="text/javascript" src="resoures/js/jquery.fancybox.pack.js"></script> -->
<script src="https://unpkg.com/ionicons@5.0.0/dist/ionicons.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=af98700765dbd95b0977af3170f7962f&libraries=services"></script>
<script src="resoures/js/cbpFWTabs.js"></script>

	<script>
		(function() {
			[].slice.call( document.querySelectorAll( '.tabs' ) ).forEach( function( el ) {
				new CBPFWTabs( el );
			});

		})();
	</script>
	
	
	<script type="text/javascript">
	$( document ).ready(function( $ ) {
		$( '#example3' ).sliderPro({
			width: 960,
			height: 640,
			fade: true,
			arrows: true,
			buttons: false,
			fullScreen: true,
			shuffle: true,
			smallSize: 500,
			mediumSize: 1000,
			largeSize: 3000,
			thumbnailArrows: true,
			autoplay: false
		});
	});
	
	</script>

	<script type="text/javascript">
		$( document ).ready(function( $ ) {
			$( '#example2' ).sliderPro({
				width: '66%',
				height: 300,
				aspectRatio: 1.5,
				visibleSize: '100%',
				forceSize: 'fullWidth'
			});
	
			// instantiate fancybox when a link is clicked
			$( '#example2 .sp-image' ).parent( 'a' ).on( 'click', function( event ) {
				event.preventDefault();
	
				// check if the clicked link is also used in swiping the slider
				// by checking if the link has the 'sp-swiping' class attached.
				// if the slider is not being swiped, open the lightbox programmatically,
				// at the correct index
				if ( $( '#example2' ).hasClass( 'sp-swiping' ) === false ) {
					$.fancybox.open( $( '#example2 .sp-image' ).parent( 'a' ), { index: $( this ).parents( '.sp-slide' ).index() } );
				}
			});
			
		});
	</script>
	
	
	<script>
		var marker;
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
	    	center : new daum.maps.LatLng(36.633535, 127.425882), // 지도의 중심좌표
	        level : 4,
	        markar:marker
	   // 지도의 확대 레벨
	    };
	    
	   // 지도를 생성합니다    
	   var map = new daum.maps.Map(mapContainer, mapOption);
			
	   // 주소-좌표 변환 객체를 생성합니다
	   var geocoder = new daum.maps.services.Geocoder();
	
	   var myAddress = ["<%=Addr%>"];
	
	   function myMarker(number, address) {
	   // 주소로 좌표를 검색합니다
	   	geocoder.addressSearch(
	    	//'주소',
	        address,
	        function(result, status) {
	        // 정상적으로 검색이 완료됐으면 
	        	if (status === daum.maps.services.Status.OK) {
					var coords = new daum.maps.LatLng(result[0].y, result[0].x);
					// 결과값으로 받은 위치를 마커로 표시합니다
	                /*
	                	var marker = new daum.maps.Marker({
	                    	map : map,
	                        position : coords
	                    });
	                */
	
					// 인포윈도우로 장소에 대한 설명을 표시합니다
	                /*
	                	var infowindow = new daum.maps.InfoWindow({
	                    	// content : '<div style="width:50px;text-align:center;padding:3px 0;">I</div>'
	                        content : '<div style="color:red;">' +  number + '</div>'
	                    });
	                    infowindow.open(map, marker);
	                */
	
	                // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
	                var content = '<div class="overlay_info">';
						content += '    <a href="#" ><strong>'+"<%=shopname%>"+'</strong></a>';
						content += '    <div class="desc">';
						content += '     <div style="width:36%;height:90%;">   <img src="<%=storefile%>"alt="" class=map_img></div>';
						content += '        <span class="address">'+"<%=comment%>"+ '</span>';
						content += '    </div>';
						content += '</div>';
	
						// 커스텀 오버레이가 표시될 위치입니다 
						var position = new daum.maps.LatLng(result[0].y,result[0].x);
	
						// 커스텀 오버레이를 생성합니다
						var customOverlay = new daum.maps.CustomOverlay({
							map : map,
							position : position,
							content : content,
							yAnchor : 1
						});
						
						marker = {markerPosition : position};
						// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
						map.setCenter(coords);
				}
			});
		}
	
		for (i = 0; i < myAddress.length; i++) {
			myMarker(i + 1, myAddress[i]);
		}
	</script>

	<script>
		function reserve(){
			// 가게 영업시간 가져오기
			var size = $("#time_select option").length;
			var first_index = $("#time_select option:eq(0)").val(); // 0번째 option 값 
			var last_index = $('#time_select option:eq('+(size-1)+')').val(); // option길이 == 마지막
			first_index = first_index+'${open2}';
			last_index = last_index+'${close2}';

			// 선택한 영업시간 가져오기
			var hour = $('#time_select option:selected').val();
			var minute = document.reservForm.minute.value;

			var rv_time = hour+minute;
			
			console.log(first_index + " f " + last_index + " l " + rv_time + "rv");

			if((rv_time < first_index) || (rv_time > last_index)){
				alert("영업시간이 아닙니다.");
				return false;
			}
			
			if(document.reservForm.personnel.value.length == 0){
				alert("인원 수를 적어주세요.");
				document.reservForm.personnel.focus();
				return false;
			}
			if(document.reservForm.minute.value.length != 2){
				alert("시간을 정확하게 적어주세요.");
				document.reservForm.minute.focus();
				return false;
			} 
			var minute = document.reservForm.minute.value;
			if((minute < 00) || (minute >= 60)) {
				alert("00 - 59 까지만 입력 가능합니다.");
				document.reservForm.minute.focus();
				return false;
			}
			else{
				document.reservForm.action = "reserveresult.do?userid=${loginUserID}&storeid=${Store.userid}";
				console.log(document.reservForm.action);
				document.reservForm.submit();
			}
		}
		
		$(".cover_img").css({"background-image":"url(resoures/images/jb/jb_store.jpg)"}); 	
	</script>
	
	<script>
	var review_storeid = '${Store.userid}';

	function review_list() {
	 	$.ajax({
	 		url : './reviewajax.do?storeid='+review_storeid,
	 		type : 'get',
	 		data : {'userid' : review_storeid},
	 		success : function(data){
	 			$('#section-flip-3>.auto').append(data);
	 		}
	 	});
	 }
	
	// 들어오면 리스트 호출
	review_list();
	
		
	</script>
	
</body>
</html>