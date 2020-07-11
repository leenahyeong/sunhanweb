<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<body>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script >
    new daum.Postcode({
        oncomplete: function(data) {
            if(data.userSelectedType=="R"){
                // userSelectedType : 검색 결과에서 사용자가 선택한 주소의 타입

                // return type : R - roadAddress, J : jibunAddress

                // TestApp 은 안드로이드에서 등록한 이름
                window.TestApp.setAddress(data.zonecode, data.roadAddress, data.buildingName);
            }
            else{
                window.TestApp.setAddress(data.zonecode, data.jibunAddress, data.buildingName);
            }       
        }
    }).embed();
</script>
<script src="postcode.v2.js">
var script =document.createElement('script');
script.src="https://cdn.fusioncharts.com/fusioncharts/latest/fusioncharts.js";
document.head.appendChild(script);
</script>
</body>