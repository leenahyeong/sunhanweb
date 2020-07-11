<%@page import="storefile.storefileDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.store.dto.StoreVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="head.jsp" />
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
<link href="resoures/cssmain/custom.css?ver=1.1" rel="stylesheet">
<title>Slider Pro</title>

<link href="resoures/css/bootstrap.min.css" rel="stylesheet">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>


<link rel="stylesheet" type="text/css"
	href="resoures/css/slider-pro.min.css" media="screen" />
<link rel="stylesheet" type="text/css"
	href="resoures/css/storeslide.css" media="screen" />

<script type="text/javascript" src="resoures/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="resoures/js/jquery.sliderPro.min.js"></script>
<script type="text/javascript" src="resoures/js/jquery.fancybox.pack.js"></script>
<link rel="stylesheet" href="resoures/css/login.css">
<link rel="stylesheet" href="resoures/css/store.css">
<link rel="stylesheet" href="resoures/css/intRo.css">
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
<script type="text/javascript"
					src="//dapi.kakao.com/v2/maps/sdk.js?appkey=af98700765dbd95b0977af3170f7962f&libraries=services"></script>
				
<style>
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
	right: 14px;
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
</style>
</head>

<body>
	<div class="type">
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
		<br>
		<div class="col-md-12">
			<h3 class="text-center" style="font-weight: bold;">
				${Store.shopname}
				<p style="background-color: skyblue; color: white; margin-left: 44%;">${Store.food}</p>
			</h3>
			<h4 class="text-center" style="font-weight: bold;">
				${Store.addr}</h4>
		</div>
		<br>
		<ul class="nav nav-tabs  nav-justified">
			<li class="nav-item tab-pane navactive"><a
				class="nav-link active" data-toggle="tab" href="#qwe">가게정보/메뉴</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#bbb">예약</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#zxc">리뷰</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane fade show active" id="qwe">
				<div class="panel panel-default">
					<div class="panel-heading" style="font-family: Nanum Gothic;">
						사장님 한마디<i class="fa fa-link fa-1x"></i>
					</div>
					<div class="panel-body" style="font-size: 12px">
						${Store.comments}</div>
				</div>
				<br>
				<div class="panel panel-default">
					<div class="panel-heading" style="font-family: Nanum Gothic;">
						가게 정보<i class="fa fa-link fa-1x"></i>
					</div>
					<div class="panel-body" style="font-size: 12px">
						${Store.information}</div>
				</div>
				<br>
				<div class="panel panel-default">
					<div class="panel-heading" style="font-family: Nanum Gothic;">
						가격<i class="fa fa-link fa-1x"></i>
					</div>
					<div class="panel-body" style="font-size: 12px">
						${Store.price}원</div>
				</div>
				<br>
				<div class="panel panel-default">
					<div class="panel-heading">
						예약 대기 안내 <i class="fa fa-link fa-1x"></i>
					</div>
					<div class="panel-body">
						<p>
							* 해당 업소는 예약 대기상태이며, 업소의 확인 후 예약확정 여부가 문자로 발송됩니다.<br> * 업소의
							사정으로 예약 불가능할 경우 예약은 자동으로 취소됩니다.
						</p>

						<img id="divimgss" src="resoures/images/messageinfo.png"
							style="width: 100%; height: 100%;" />
					</div>
				</div>
				<br> <br>
				<div id="map" style="width: 100%; height: 380px;"></div>
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
         geocoder
                 .addressSearch(
                         //'주소',
                         address,
                         function(result, status) {
                             // 정상적으로 검색이 완료됐으면 
                             if (status === daum.maps.services.Status.OK) {

                                 var coords = new daum.maps.LatLng(
                                         result[0].y, result[0].x);

                                 // 결과값으로 받은 위치를 마커로 표시합니다
                                 /*
                                 var marker = new daum.maps.Marker({
                                     map : map,
                                     position : coords
                                 });
                                  */

                                 // 인포윈도우로 장소에 대한 설명을 표시합니다
                                 /*
                                 var infowindow = new daum.maps.InfoWindow(
                                         {
                                             // content : '<div style="width:50px;text-align:center;padding:3px 0;">I</div>'
                                             content : '<div style="color:red;">' +  number + '</div>'
                                         });
                                 infowindow.open(map, marker);
                                  */

                                 // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
                                 var content = '<div class="overlay_info">';
										content += '    <a href="#" ><strong>'+"<%=shopname%>"+'</strong></a>';
										content += '    <div class="desc">';
										content += '     <div style="width:36%;height:90%;">   <img src="<%=storefile%>"alt="" style="width:100%;heigt:100%; position:asolute;"></div>';
										content += '        <span class="address">'+"<%=comment%>"+ '</span>';
												content += '    </div>';
												content += '</div>';

												// 커스텀 오버레이가 표시될 위치입니다 
												var position = new daum.maps.LatLng(
														result[0].y,
														result[0].x);

												// 커스텀 오버레이를 생성합니다
												var customOverlay = new daum.maps.CustomOverlay(
														{
															map : map,
															position : position,
															content : content,
															yAnchor : 1
														});
												marker = {
													markerPosition : position
												};
												// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
												map.setCenter(coords);
											}
										});
					}

					for (i = 0; i < myAddress.length; i++) {
						myMarker(i + 1, myAddress[i]);
					}
				</script>
			</div>
			<div class="tab-pane fade" id="asd"></div>
			<div class="tab-pane fade" id="zxc">
				<p>Curabitur dignissim quis nunc vitae laoreet. Etiam ut mattis
					leo, vel fermentum tellus. Sed sagittis rhoncus venenatis. Quisque
					commodo consectetur faucibus. Aenean eget ultricies justo.</p>
			</div>
		</div>
	</div>
	<div class="type2">
		<div class="container">
		<div class="row">
			<div class="col-md-9">
				<h3 class="text-left"style="font-weight: bold; font-size: 38px"">
					매장 정보    
				</h3>
			</div>
			<div class="col-md-3" >
				<nav id="bread">
					<ol class="breadcrumb breadcrumbs">
						<li class="breadcrumb-item">
							<a href="storelist.jsp">매장 리스트</a>
						</li>  
						<li class="breadcrumb-item active">
							매장정보
						</li>
					</ol>   
				</nav>
			</div>
		</div>
		<br><br>
	</div>
		<div style="height: 46vh">
			<div
				style="width: 50%; overflow: hidden; margin-left: 191px; float: left;">
				<div id="example2" class="slider-pro">
					<div class="sp-slides">
						<div class="sp-slide">
							<img class="sp-image"
								src="${Storefilename.get(0).getFileRealName()}" style="" />

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
			<div
				style="float: left; padding: 20px; margin-left: 20px;height:80%;  width: 28%; background-color: aliceblue;">

				<div>
					<h3 class="text-left" style="font-weight: bold; color: black;">
						${Store.shopname} <span
							style="background-color: skyblue; display: grid; margin-left: 30px; text-align: right; color: white; display: table-cell; -webkit-text-emphasis-style: filled;">${Store.food}</span>

					</h3>
				</div>
				<h5 class="text-center" style="font-weight: bold;"></h5>
				<div
					style="width: 100%; height: 70%; position: inherit; background-color: rgb(211, 211, 211, 0.3); border-radius: 18px 18px 18px 18px;">
					<div style="padding: 14px">
						<h7 style="text-align: left; color:black;font-weight: bold;">사장님
						한마디!</h7>
						<br>
						<br> ${Store.comments} <br>
						<br>(${Store.addr})

					</div>
				</div>
			</div>
		</div>
		<div class="container" style="margin-bottom: 20px;">
			<div class="row">
			</div>
		</div>
		<div class="container">
			<div class="row">
				<ul class="nav nav-tabs  nav-justified" style="width: 100%">
				<li class="nav-item tab-pane navactive"><a
					class="nav-link active" data-toggle="tab" href="#aaa">가게정보/메뉴</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab"
					href="#bbb">예약</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab"
					href="#ccc">리뷰</a></li>
				</ul>
				<br>
				<div class="tab-content" style="width: 100%; padding: 20px; background-color: powderblue; border-radius: 0px 0px 18px 18px;">
					<div class="tab-pane fade show active" id="aaa">
						<div class="panel panel-default">
							<div class="panel-heading" style="font-family: Nanum Gothic;">
								사장님 한마디<i class="fa fa-link fa-1x"></i>
							</div>
							<div class="panel-body" style="font-size: 12px">
								${Store.comments}</div>
						<br>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading" style="font-family: Nanum Gothic;">
								가게 정보<i class="fa fa-link fa-1x"></i>
							</div>
							<div class="panel-body" style="font-size: 12px">
								${Store.information}</div>
						</div>
						<br>
						<div class="panel panel-default">
							<div class="panel-heading" style="font-family: Nanum Gothic;">
								가격<i class="fa fa-link fa-1x"></i>
							</div>
							<div class="panel-body" style="font-size: 12px">
								${Store.price}원</div>
						</div>
						<br>
						<div class="panel panel-default">
							<div class="panel-heading">
								예약 대기 안내 <i class="fa fa-link fa-1x"></i>
							</div>
							<div class="panel-body">
								<p>
									* 해당 업소는 예약 대기상태이며, 업소의 확인 후 예약확정 여부가 문자로 발송됩니다.<br> * 업소의
									사정으로 예약 불가능할 경우 예약은 자동으로 취소됩니다.
								</p>
		
								<img id="divimgss" src="resoures/images/messageinfo.png"
									style="width: 100%; height: 100%;" />
							</div>
						</div>
						<div id="map2" style="width: 100%; height: 380px;"></div>
					<script>
    var marker2;
	var mapContainer2 = document.getElementById('map2'), // 지도를 표시할 div 
         mapOption2 = {
             center : new daum.maps.LatLng(36.633535, 127.425882), // 지도의 중심좌표
             level : 4,
             markar:marker2
         // 지도의 확대 레벨
         };
     // 지도를 생성합니다    
     var map2 = new daum.maps.Map(mapContainer2, mapOption2);
		
     // 주소-좌표 변환 객체를 생성합니다
     var geocoder2 = new daum.maps.services.Geocoder();
     

     var myAddress2 = ["<%=Addr%>"];

     function myMarker2(number, address) {
         // 주소로 좌표를 검색합니다
         geocoder2 
         .addressSearch(
                         //'주소',
                         address,
                         function(result, status) {
                             // 정상적으로 검색이 완료됐으면 
                             if (status === daum.maps.services.Status.OK) {

                                 var coords = new daum.maps.LatLng(
                                         result[0].y, result[0].x);
                                 // 결과값으로 받은 위치를 마커로 표시합니다
                              
                                 var marker2 = new daum.maps.Marker({
                                     map : map2,
                                     position : coords
                                 });
                                 map2.setCenter(coords);
											}
										});
					}

					for (i = 0; i < myAddress2.length; i++) {
						myMarker2(i + 1, myAddress2[i]);
					}
				</script>
		
					</div>
					<div class="tab-pane fade" id="bbb">
						<div class="panel panel-default">
							<c:choose>
								<c:when test="${result eq -1}">
									<div class="center">
										<h1>이미 예약하신 가게입니다.</h1>
										<a class="de_btn" style="margin-top: 10px;" href="reserveresult.do?userid=${loginUserID}&storeid=${Store.userid}">예약정보확인하기</a>
									</div>
								</c:when>
									
								<c:when test="${result eq 0 || result eq 1 && reserveCheck eq 1}">
									<c:choose>
										<c:when test="${reserveCheck eq 1}">
											<form method="post" name="reservForm">
												<table class="table">
													<tr>
														<input type="hidden" name="storeid" value="${Store.userid}">
														<input type="hidden" name="result" value="${result}">
														<th>방문예정인원 <span class="red">*</span></th>
														<td><input type="number" name="personnel" style="margin: 10px;" class="graybox"> 명</td>
													</tr>
													<tr>
														<th>방문예정시간 <span class="red">*</span></th>
														<td>
															<input type="hidden" name="now" value="${now}">
															<span>${now}</span>
															
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
														<th colspan="2"><input type="button" value="예약하기" onclick="reserve()" /></th>
													</tr>
												</table>
		
											</form>	
										</c:when>
										<c:otherwise>
											<h1 class="center">예약시간이 아닙니다.</h1>
										</c:otherwise>
									</c:choose>
									
								</c:when>
								
								<c:otherwise>
									<h1 class="center">후원아동이 아닙니다.</h1>
								</c:otherwise>
							</c:choose>
						</div>
				    </div>
				    
					<div class="tab-pane fade" id="ccc">
						<p>Curabitur dignissim quis nunc vitae laoreet. Etiam ut mattis
							leo, vel fermentum tellus. Sed sagittis rhoncus venenatis. Quisque
							commodo consectetur faucibus. Aenean eget ultricies justo.</p>
					</div>
				</div>
			</div>
		</div>
		<script>
		function reserve(){
			// 가게 영업시간 가져오기
			var size = $("#time_select option").size();
			var first_index = $("#time_select option:eq(0)").val(); // 0번째 option 값 
			var last_index = $('#time_select option:eq('+(size-1)+')').val(); // option길이 == 마지막
			first_index = first_index+'${open2}';
			last_index = last_index+'${close2}';

			// 선택한 영업시간 가져오기
			var hour = $('#time_select option:selected').val();
			var minute = document.reservForm.minute.value;

			var rv_time = hour+minute;

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
		</script>
</body>
</html>