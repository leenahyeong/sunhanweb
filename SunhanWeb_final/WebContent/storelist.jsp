<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>예약 페이지</title>
	<link href="resoures/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="resoures/css/reservation.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
 
	<script src="resoures/js/jquery-3.3.1.min.js"></script>
	<link rel="stylesheet" href="resoures/css/intRo.css">
	<link rel="stylesheet" href="resoures/cssmain/custom.css">
	
	<style>
	.jumbotron {
    	background: rgba(0, 0, 0, .5) !important;
    }
    
    #boxTable {
    	border-bottom: none;
    }
    
    .btn:focus, button {
    	box-shadow:none;
    }
	</style>
	<script>
	  $(document).ready(function(){
	        $("._3F9ziS li").click(function(){
	        	
	          
	            var submenu = $(this).children("a");
	            
	            if( submenu.is(":visible") ){
	                submenu.slideUp();
	            }else{
	                submenu.slideDown();
	         
	            }

	        });
	        $(".wd").click(function(){
	        	
		          
	        	var aa = $('.box1');

	            

	        	
				if($(".box1").css("display") == "none"){
					aa.css('display','block')
				} else {
					aa.css('display','none')
				}
	        });
	        $(".wd").click(function(){
	        	
		          
	        	var aaa = $('.main2');

	            

	        	
				if($(".box1").css("display") == "none"){
					aaa.css('display','block')
				} else {
					aaa.css('display','none')
				}
	        });
	    });
	  function statusChange(statusItem)
	  {
		 var str= $('.worldTXT');
		 var str2= $('.world');
		 var str3= $('.reids');
		 var str4= $(statusItem).find('input').val();
		 
		 var strText = $(statusItem).text();
		 var strText2= $(statusItem).siblings('a1').text();
		 
	         str.text(strText);
	         str2.text(strText2);
	         str3.val(str4);
		storeBoxFunction();
	  }
	</script>
	
	
	<script type="text/javascript">

	function storeBoxFunction(){
		
		
		var areas= $('.reids').val();
			
  		$.ajax({
  			type:"POST",
  			url:"./storeBox",
  			contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
  			data:{
  				area: areas,
  			},
  			
        	success: function(data){
  				if(data=="")return;
  				$('#boxTable').html('');
  				var parsed=JSON.parse(data);
  				var result =parsed.result;
  				
  				for(var i=0; i<result.length; i++)
  					{	
  						addBox(result[i][0].value,result[i][1].value,result[i][2].value,result[i][3].value,result[i][4].value,result[i][5].value);
					}
  			}
  		});
  	}
	function addBox(ShopName,TopMessage,Price,Area,FileRealName,userid)
	{
		$('#boxTable').append('<div class="col-md-4 match-height mb30" style="display: block; height: 280px;">'+
							  '<div class="hover-effect smoothie">'+
							  '<a href="store.do?userid='+userid+'"class="smoothie">'+
							  '<img src='+FileRealName+'; alt="Image" class="img-responsive smoothie" style="width:100%; height:100%; position:absolute;z-index:0; "></a>'+
							  '<div class="hover-overlay smoothie text-center">'+
							  '<div class="vertical-align-bottom">'+
							  '<h4 style="position: absolute; font-size: 22px; font-weight: bold; margin-top: 10px;" >'+ShopName+'</h4>'+ 
							  '<h4 style="position: absolute; margin-top: 179px;font-size: 12px;" >'+Area+'</h4>'+
							  '<h4 style="position: absolute; font-size: 12px; margin-top: 200px; font-size: 20px;" >'+Price+'</h4>'+
							  '<p class="vertical-align-bottom" style="position: absolute; margin-top: 30px;font-size: 14px;"><small style="background-color: black; opacity: 68%;">'+TopMessage+'</small>'+
							  '</div>'+
							  '</div>'+
							  '</div>'+
							  '</div>');
        
	}
	function getInfiniteBox(){
		setInterval(function(){
	 	},3000);
	}
	
	</script>
	</head>
	<body class="co">
	<jsp:include page="head.jsp"/>
	
	<section class="auto cover_img">
		<div class="jumbotron jumbotron-fluid">
			<div class="container">
				<h1 class="display-4">매장리스트</h1>
				<p class="lead">후원중인 매장들을 만나보세요.</p>
			</div>
		</div>
	</section>
	
	<div class="container" id="">
		
	
		
	</div>
	<div class="container" style="width:90%;">
	    <div class="row">
	        <div class="col-6 col-md-12">
	        <button type="button" value="지역선택" class="btn btn-block btn-lg worldbtn wd" id="btn">
				<a style="font-size: 14px; text-align: left;" class="world">서울</a><br><a class="worldTXT">강남/역삼/삼성/논현</a>
				<input type="hidden" name="reid" size="20" class="reids" value="a1">
			</button>
	        </div>
	        <div class="col-6 col-md-12">
	        <button type="button" value="내위치" class="btn btn-lg btn-block" id="btn">
				검색하기 
			</button>
	        </div>
	    </div>
	    <div class="row" style="margin-top: -1px">
	        <div class="col-4" id="btn" style="padding-left: 15px;">
	        <button type="button" class="btn btn-md btn-block" id="btn">
				상세조건
			</button>
	        </div>
	        <div class="col-4" id="btn" style="padding-left: 15px; ">
	        <button type="button" class="btn btn-md btn-block " id="btn">
				추천 순
			</button>
	        </div>
	        <div class="col-4" id="btn" style="padding-left: 15px;">
	        <button type="button" class="btn btn-md btn-block" id="btn">
				지도
			</button>
	        </div>
	    </div>
	    <div class="row">
	        <div class="col-xs-12"></div>
	    </div>
	</div>
	<div class="container box1">
		<div class="_12pCT5 _3qoG7J">
				<ul class="_3F9ziS">
				<li id="mm1">
					<a1 class="_3TgB9F">서울</a1>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a1"/><span>강남/역삼/삼성/논현</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a2"/><span>서초/신사/방배</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a3"/><span>영등포/여의도/동작</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a4"/><span>신림/서울대/사당</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a5"/><span>천호/길동/둔촌</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a6"/><span>화곡/까지찬/양천/목동</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a7"/><span>구로/금천/오류</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a8"/><span>신촌/홍대/합정</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a9"/><span>영신내/불광/응암</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a10"/><span>종로/대학로/신도림</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a11"/><span>성신여대/성북/월곡</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a12"/><span>이태원/용산/서울역</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a13"/><span>동대문/동묘/신당/충무</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a14"/><span>회기/고려대/청량/신설</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a15"/><span>장안동/답십리</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a16"/><span>건대/군자/구의</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a17"/><span>왕십리/성수/금호</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a18"/><span>수유/미아</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a19"/><span>상봉/중랑/면목</span><i></i></a>
					<a class="_2gy0BP hide" onclick="statusChange(this)" ><input type="hidden" value="a20"/><span>태릉/노원/도봉/창동</span><i></i></a>
				</li>
				<li id="mm2">
					<a1 class="_3TgB9F">경기</a1>
					<a class="_2gy0BP hide"><span>수원 인계/권선/영통</span><i></i></a>
					<a class="_2gy0BP hide"><span>수원역/구은/장안/세류</span><i></i></a>
					<a class="_2gy0BP hide"><span>안양/평촌/인덕원/과천</span><i></i></a>
					<a class="_2gy0BP hide"><span>성남/분당/위례</span><i></i></a>
					<a class="_2gy0BP hide"><span>용인</span><i></i></a>
					<a class="_2gy0BP hide"><span>동탄/오산/병점</span><i></i></a>
					<a class="_2gy0BP hide"><span>하남/광주/여주/이천</span><i></i></a>
					<a class="_2gy0BP hide"><span>안산</span><i></i></a>
					<a class="_2gy0BP hide"><span>군포/의왕/금정/산본</span><i></i></a>
					<a class="_2gy0BP hide"><span>시흥/광명</span><i></i></a>
					<a class="_2gy0BP hide"><span>평택/송탄/화성/안성</span><i></i></a>
					<a class="_2gy0BP hide"><span>부천</span><i></i></a>
					<a class="_2gy0BP hide"><span>일산/고양</span><i></i></a>
					<a class="_2gy0BP hide"><span>파주</span><i></i></a>
					<a class="_2gy0BP hide"><span>김포</span><i></i></a>
					<a class="_2gy0BP hide"><span>의정부</span><i></i></a>
					<a class="_2gy0BP hide"><span>구리</span><i></i></a>
					<a class="_2gy0BP hide"><span>남양주</span><i></i></a>
					<a class="_2gy0BP hide"><span>포천</span><i></i></a>
					<a class="_2gy0BP hide"><span>양주/동두천/연천</span><i></i></a>
					<a class="_2gy0BP hide"><span>양평</span><i></i></a>
					<a class="_2gy0BP hide"><span>가평/청평</span><i></i></a>
					<a class="_2gy0BP hide"><span>제부도/대부도</span><i></i></a>
					
				</li>
				<li id="mm3">
					<a1 class="_3TgB9F">인천</a1>
					<a class="_2gy0BP hide"><span>부평</span><i></i></a>
					<a class="_2gy0BP hide"><span>구월</span><i></i></a>
					<a class="_2gy0BP hide"><span>서구(석남,서구청,검단)</span><i></i></a>
					<a class="_2gy0BP hide"><span>계양(작전,경인교대)</span><i></i></a>
					<a class="_2gy0BP hide"><span>주안</span><i></i></a>
					<a class="_2gy0BP hide"><span>송도/연수</span><i></i></a>
					<a class="_2gy0BP hide"><span>인천공학/을왕리</span><i></i></a>
					<a class="_2gy0BP hide"><span>중구(월미도/차이나)</span><i></i></a>
					<a class="_2gy0BP hide"><span>중구(신포/동인천)</span><i></i></a>
					<a class="_2gy0BP hide"><span>강화/웅진</span><i></i></a>
					<a class="_2gy0BP hide"><span>동암/간석</span><i></i></a>
					<a class="_2gy0BP hide"><span>남동구(소래/만수)</span><i></i></a>
					<a class="_2gy0BP hide"><span>용현/숭의/도화/동구</span><i></i></a>
				</li>
				<li id="mm4">
					<a1 class="_3TgB9F">강원</a1>
					<a class="_2gy0BP hide"><span>춘천/강촌</span><i></i></a>
					<a class="_2gy0BP hide"><span>원주</span><i></i></a>
					<a class="_2gy0BP hide"><span>경포대/사천/주문진</span><i></i></a>
					<a class="_2gy0BP hide"><span>강릉역/교동</span><i></i></a>
					<a class="_2gy0BP hide"><span>옥계/정동진</span><i></i></a>
					<a class="_2gy0BP hide"><span>영월/정선</span><i></i></a>
					<a class="_2gy0BP hide"><span>속초/양양/고성</span><i></i></a>
					<a class="_2gy0BP hide"><span>동해/삼척/태백</span><i></i></a>
					<a class="_2gy0BP hide"><span>평창</span><i></i></a>
					<a class="_2gy0BP hide"><span>홍천/횡성</span><i></i></a>
					<a class="_2gy0BP hide"><span>화천/철원/인제/양구</span><i></i></a>
								
				</li>
				<li id="mm5">
					<a1 class="_3TgB9F">제주</a1>
					<a class="_2gy0BP hide"><span>제주시</span><i></i></a>
					<a class="_2gy0BP hide"><span>서귀포시</span><i></i></a>
					<a class="_2gy0BP hide"><span>하귀/애월</span><i></i></a>
					<a class="_2gy0BP hide"><span>한림/협재</span><i></i></a>
				</li>
				<li id="mm6">
					<a1 class="_3TgB9F">대전</a1>
					<a class="_2gy0BP hide"><span>유성구</span><i></i></a>
					<a class="_2gy0BP hide"><span>중구(은행/대흥/선화)</span><i></i></a>
					<a class="_2gy0BP hide"><span>동구(용전/복합터미널)</span><i></i></a>
					<a class="_2gy0BP hide"><span>서구(둔산/용문/월평)</span><i></i></a>
					<a class="_2gy0BP hide"><span>대덕구(중리/신탄진)</span><i></i></a>
				</li>
				<li id="mm7">
					<a1 class="_3TgB9F">충북</a1>
					<a class="_2gy0BP hide"><span>청주 흥덕구/서원구</span><i></i></a>
					<a class="_2gy0BP hide"><span>청주 상당구/청원구</span><i></i></a>
					<a class="_2gy0BP hide"><span>충주/수안보</span><i></i></a>
					<a class="_2gy0BP hide"><span>제천/진천/음성/단양</span><i></i></a>
					<a class="_2gy0BP hide"><span>보은/옥천/괴산</span><i></i></a>
					<a class="_2gy0BP hide"><span>증평/영동</span><i></i></a>
				</li>
				<li id="mm8">
					<a1 class="_3TgB9F">충남</a1>
				</li>
				<li id="mm9">
					<a1 class="_3TgB9F">부산</a1>
				</li>
				<li id="mm10">
					<a1 class="_3TgB9F">울산</a1>
				</li>
				<li id="mm11">
					<a1 class="_3TgB9F">경남</a1>
				</li> 
				<li id="mm12">
					<a1 class="_3TgB9F">대구</a1>
					<a class="_2gy0BP hide"><span>북구</span><i></i></a>
					<a class="_2gy0BP hide"><span>서구</span><i></i></a>
					<a class="_2gy0BP hide"><span>달서구</span><i></i></a>
					<a class="_2gy0BP hide"><span>달성군</span><i></i></a>
					<a class="_2gy0BP hide"><span>중구</span><i></i></a>
					<a class="_2gy0BP hide"><span>수성구</span><i></i></a>
					<a class="_2gy0BP hide"><span>동구</span><i></i></a>
					<a class="_2gy0BP hide"><span>남구</span><i></i></a>
				</li>
				<li id="mm13">
					<a1 class="_3TgB9F">경북</a1>
				</li>
				<li id="mm14">
					<a1 class="_3TgB9F">광주</a1>
				</li>
				<li id="mm15">
					<a1 class="_3TgB9F">전남</a1>
					</li>
				<li id="mm16">
					<a1 class="_3TgB9F">전북</a1>
				</li>
				</ul>
		</div>
	</div>
	
	<div class="container main2" style="display:contents; padding-left: 0px !important; margin-bottom: 20px;">
    	<div class="row" id="boxTable" style="color:white; "></div>
    </div>
    <script type="text/javascript">
    $('.cover_img').css({'background-image' : 'url(resoures/images/jb/jb_store.jpg)'});
		$(document).ready(function()
		{
			storeBoxFunction();
			getInfiniteBox();
		});
	</script>
	
	<jsp:include page="foot.jsp" />
</body>

</html>
