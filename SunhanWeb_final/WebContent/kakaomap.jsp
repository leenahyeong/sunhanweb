<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<style>
    .overlay_info {border-radius: 6px; margin-bottom: 12px; float:left;position: relative; border: 1px solid #ccc; border-bottom: 2px solid #ddd;background-color:#fff;}
    .overlay_info:nth-of-type(n) {border:0; box-shadow: 0px 1px 2px #888;}
    .overlay_info a {display: block; background: #d95050; background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center; text-decoration: none; color: #fff; padding:12px 36px 12px 14px; font-size: 14px; border-radius: 6px 6px 0 0}
    .overlay_info a strong {background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/place_icon.png) no-repeat; padding-left: 27px;}
    .overlay_info .desc {padding:14px;position: relative; min-width: 190px; height: 56px}
    .overlay_info img {vertical-align: top;}
    .overlay_info .address {font-size: 12px; color: #333; position: absolute; left: 80px; right: 14px; top: 24px; white-space: normal}
    .overlay_info:after {content:'';position: absolute; margin-left: -11px; left: 50%; bottom: -12px; width: 22px; height: 12px; background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png) no-repeat 0 bottom;}
</style>
<body>
   
    <div id="map" style="width: 100%; height: 780px;"></div>
 
    <script type="text/javascript"
        src="//dapi.kakao.com/v2/maps/sdk.js?appkey=5797c73d310f96e973ad94234186801c&libraries=services"></script>
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
        

        var myAddress = ["경산시 중산동 대학로 8길 32", "개봉로 15길 28-21", "서초대로 77킬 9" ];
 
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
										content += '    <a href="https://place.map.kakao.com/17600274" target="_blank"><strong>월정리 해수욕장</strong></a>';
										content += '    <div class="desc">';
										content += '        <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/place_thumb.png" alt="">';
										content += '        <span class="address">제주특별자치도 제주시 구좌읍 월정리 33-3</span>';
										content += '    </div>';
										content += '</div>';
 
                                    // 커스텀 오버레이가 표시될 위치입니다 
                                    var position = new daum.maps.LatLng(
                                            result[0].y, result[0].x);
 				
                                    // 커스텀 오버레이를 생성합니다
                                    var customOverlay = new daum.maps.CustomOverlay(
                                            {
                                                map : map,
                                                position : position,
                                                content : content,
                                                yAnchor : 1
                                            });
                                    marker = {
                                    		markerPosition: position
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
 
</body>
</html>