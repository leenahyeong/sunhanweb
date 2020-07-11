<%@page import="com.sumhan.dto.SunhansVO"%>
<%@page import="com.sumhan.dao.SunhansDAO"%>
<%@page import="file.FileDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="head.jsp" />
<html>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
 <link href="resoures/css/owl.carousel.min.css" rel="stylesheet">
 <link href="/your-path-to-fontawesome/css/fontawesome.css"
	rel="stylesheet">
 <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.16/dist/summernote-bs4.min.css" rel="stylesheet">
	
 <!-- Template Main CSS File -->
	
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!------ Include the above in your HEAD tag ---------->
<script type="text/javascript" src="resoures/js/member.js"></script>
 <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet">
 <link href="<%=request.getContextPath()%>/css/custom.css" rel="stylesheet">

<script>
window.name = "parentForm";
function re2(){
var fileCheck = document.getElementById("profile").value;
if(!fileCheck){
	confirm("업로드할 파일을 선택하여 주십시오.");
    return false;
}};

function re(){  
      setTimeout('location.reload()',30000); 
}

$(function(){
    $('.pro').click(function(){
    	re2();	 re(); 
    });
}); 
function goPopup(){
	// 주소검색을 수행할 팝업 페이지를 호출합니다.
	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
	var pop = window.open("popup/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
	
	// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
    //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
}

function jusoCallBack(roadFullAddr){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.	
		document.fm.STaddr.value = roadFullAddr;		
}
</script>



<head>
<title>개인정보 설정</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="resoures/css/intRo.css">
<style>
.time input {
    padding: 10px;
    border: solid 1px #ddd;
    width: 10%;
    }
.note-editor.note-frame {
    border: none;
}    
</style>
</head>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<div class="container bootstrap snippet">
	<div class="row">
		<div class="col-md-9">
			<h3 class="text-left" style="font-weight: bold;">후원 가게 수정</h3>
		</div>
		<div class="col-md-3">
			<nav id="bread">
				<ol class="breadcrumb breadcrumbs">
					<li class="breadcrumb-item"><a href="main.jsp">홈</a></li>
					<li class="breadcrumb-item"><a href="mypage.jsp">마이페이지</a></li>
					<li class="breadcrumb-item active">가게 수정</li>
				</ol>
			</nav>
		</div>
	</div>
					
	<div class="row">
		<div class="col-sm-12">
			<!--left col-->

			<div class="text-center">
			
                <c:choose>
				<c:when test="${storesult==1}">	
					<img src="${storefile}" class="img-thumbnail" alt="avatar">
					<form action="storeupload.do" method="post" enctype="multipart/form-data">
					<input type="file" title="s" name="file" id="profile" value=" " class="text-center center-block file-upload">
					<input type="file" title="s" name="file2" id="profile" value=" " class="text-center center-block file-upload">
					<input type="file" title="s" name="file3" id="profile" value=" " class="text-center center-block file-upload">
					<input type="submit" class="pro" value="업로드" onclick="return re2()">
					</form>
				</c:when>
				<c:otherwise>	
					<img src="${realstorefile}" class="img-thumbnail" alt="avatar">
					<form action="storeupload.do" method="post" enctype="multipart/form-data">
					<input type="file" title="s" name="file1" id="profile" value=" " class="text-center center-block file-upload">
					<input type="file" title="s" name="file2" id="profile" value=" " class="text-center center-block file-upload">
					<input type="file" title="메인사진" name="file3" id="profile" value=" " class="text-center center-block file-upload">메인 사진
					<br><input type="submit" class="pro" value="업로드" onclick="return re2()">
					</form>
				</c:otherwise>
				</c:choose>
			</div>
			<br>
 			
 			<c:choose>
 			
 			<c:when test="${storeDr==1}">	
			<form name="fm" style="padding: 10px" action="myStoreUpdate.do" method="post" >    <!-- name 값을 동일하게 변경합니다.  현재 = fm-->
			<div class="panel panel-default">
				<div class="panel-heading">
					가게 이름 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
					<textarea style="width:100%; height: 100%" placeholder="후원님의 얼굴!" name="storename"> ${vo.shopname} </textarea>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					주소 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-heading">
					<button type="button" class="btn btn-warning" onclick="goPopup()">주소검색</button>
					<input type="text" id="STaddr" name="STaddr" class="form-control" placeholder="아동들의 편안한 자리를 제공할 주소" required="true" readonly="true"/>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					사장님 한마디 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
					<textarea style="width:100%; height: 100%" placeholder="따듯한 사장님의 한마디 " name="STcomment">${vo.comments}</textarea>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					가게 정보 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
					<textarea id="summernote" name="STinfo" placeholder="내용을 입력하세요.(메뉴 또는 주변정보) ">${vo.information}</textarea>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					상단의 띄울 코멘트 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
					<textarea style="width:100%; height: 100%" placeholder=" 본인의 가게의 장점!! " name="STtop">${vo.topmessage}</textarea>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					가격 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
					<textarea style="width:100%; height: 100%" placeholder=" 무료로 먹으러 오기엔 좀 그래? 그럼 천원만내렴 ㅎ " name="STprice">${vo.price}</textarea>
				</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">
					전화번호 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">							
					<input type="tel" name="STphone" value="${vo.getStorePhone()}">
				</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">
					영업시간 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body time">
					<input type="text" name="open1" value="${time.open1}">시 <input type="text" name="open2" value="${time.open2}">
					-
					<input type="text" name="close1" value="${time.close1}">시 <input type="text" name="close2" value="${time.close2}">
				</div>
			</div>
			
			
		
			<div class="panel panel-default">
				<div class="panel-heading">
					 카테고리 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
							<label for="food"> 음식 : </label> 
							<input type="radio" id="food" name="food" value="한식" checked> 한식  
							<input type="radio"	id="food" name="food" value="중식"> 중식  
							<input type="radio"	id="food" name="food" value="일식"> 일식  
							<input type="radio"	id="food" name="food" value="양식"> 양식  
							<input type="radio"	id="food" name="food" value="빵"> 빵  
							<input type="radio"	id="food" name="food" value="디저트"> 디저트 <br><br>
					<label for="food"> 지역선택 : </label> 
					<select name="area" onChange="redirect(this.options.selectedIndex)" >
					  <option>지역</option>
					  <option value="a">서울</option>
					  <option value="b">경기</option>
					  <option value="c">인천</option>
					  <option value="d">강원</option>
					  <option value="e">제주</option>
					  <option value="f">대전</option>
					  <option value="g">충북</option>
					  <option value="h">충남</option>
					  <option value="i">부산</option>
					  <option value="j">울산</option>
					  <option value="k">경남</option>
					  <option value="l">대구</option>
					  <option value="m">경북</option>
					  <option value="n">광주</option>
					  <option value="o">전남</option>
					  <option value="p">전북</option>
					</select>   
					<select name="area2">
					  <option></option>
					</select>
			     </div>
						<script type="text/Javascript">
						
						// 위쪽 form의  name값  fm  수정시 아래도 동일하게 변경
						  var groups=document.fm.area.options.length      // c1 : 첫번째 name값
						  var group=new Array(groups)
						  for (i=0; i<groups; i++)
						  group[i]=new Array()
						
						// "선택하세요" 2번째 항목
						  group[0][0]=new Option("","왼쪽먼저")
						
						  // 선택1 연동
						  group[1][0]=new Option("강남/역삼/삼성/논현","1")
						  group[1][1]=new Option("서초/신사/방배","2")
						  group[1][2]=new Option("천호/길동/둔촌","3")
						  group[1][3]=new Option("화곡/까지찬/양천/목동","4")
						  group[1][4]=new Option("천호/길동/둔촌","5")
						  group[1][5]=new Option("구로/금천/오류","6")
						  group[1][6]=new Option("신촌/홍대/합정","7")
						  group[1][7]=new Option("영신내/불광/응암","8")
						  group[1][8]=new Option("종로/대학로/신도림","9")
						  group[1][9]=new Option("성신여대/성북/월곡","10")
						  group[1][10]=new Option("이태원/용산/서울역","11")
						  group[1][11]=new Option("동대문/동묘/신당/충무","12")
						  group[1][12]=new Option("회기/고려대/청량/신설","13")
						  group[1][13]=new Option("건대/군자/구의","14")
						  group[1][14]=new Option("왕십리/성수/금호","15")
						  group[1][15]=new Option("수유/미아","16")
						  group[1][16]=new Option("상봉/중랑/면목","17")
						  group[1][17]=new Option("태릉/노원/도봉/창동","18")
						
						// 선택2 연동
						  group[2][0]=new Option("Option11","Value11")
						 
						// 선택3 연동
						  group[3][0]=new Option("Option21","Value21")
						  group[3][1]=new Option("Option22","Value22")
						 
						  var temp=document.fm.area2  // c2 : 두번째 name값
						  function redirect(x){
						    for (m=temp.options.length-1;m>0;m--)
						    temp.options[m]=null
						    for (i=0;i<group[x].length;i++){
						      temp.options[i]=new Option(group[x][i].text,group[x][i].value)
						    }
						    temp.options[0].selected=true
						  }
						</script>
						<div>
							<input type="submit" value="후원하기" class="btn btn-warning" >
						</div>
				</form>
			</c:when>
			
			<c:otherwise>
			<form name="fm" style="padding: 10px" action="myStoreEdit.do" method="post" >    <!-- name 값을 동일하게 변경합니다.  현재 = fm-->
			<div class="panel panel-default">
				<div class="panel-heading">
					가게 이름 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
					<textarea style="width:100%; height: 100%" placeholder="후원님의 얼굴!" name="storename"></textarea>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					주소 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-heading">
					<button type="button" class="btn btn-warning" onclick="goPopup()">주소검색</button>
					<input type="text" id="STaddr" name="STaddr" class="form-control" placeholder="아동들의 편안한 자리를 제공할 주소" required="true" readonly="true"/>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					사장님 한마디 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
					<textarea style="width:100%; height: 100%" placeholder="따듯한 사장님의 한마디 " name="STcomment"></textarea>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					가게 정보 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
					<textarea id="summernote" name="STinfo" placeholder="내용을 입력하세요.(메뉴 또는 주변정보) "></textarea>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					상단의 띄울 코멘트 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
					<textarea style="width:100%; height: 100%" placeholder=" 본인의 가게의 장점!! " name="STtop"></textarea>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					가격 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
					<textarea style="width:100%; height: 100%" placeholder=" 무료로 먹으러 오기엔 좀 그래? 그럼 천원만내렴 ㅎ " name="STprice"></textarea>
				</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">
					영업시간 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body time">
					<input type="number" name="open1"> : <input type="number" name="open2"> - <input type="number" name="close1"> : <input type="number" name="close2">
				</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">
					전화번호 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
					<input type="number" name="STphone">
				</div>
			</div>
		
			<div class="panel panel-default">
				<div class="panel-heading">
					 카데고리 <i class="fa fa-link fa-1x"></i>
				</div>
				<div class="panel-body">
							<label for="food"> 음식 : </label> 
							<input type="radio" id="food" name="food" value="한식" checked> 한식  
							<input type="radio"	id="food" name="food" value="중식"> 중식  
							<input type="radio"	id="food" name="food" value="일식"> 일식  
							<input type="radio"	id="food" name="food" value="양식"> 양식  
							<input type="radio"	id="food" name="food" value="빵"> 빵  
							<input type="radio"	id="food" name="food" value="디저트"> 디저트 <br><br>
					<label for="food"> 지역선택 : </label> 
					<select name="area" onChange="redirect(this.options.selectedIndex)" >
					  <option>지역</option>
					  <option value="a">서울</option>
					  <option value="b">경기</option>
					  <option value="c">인천</option>
					  <option value="d">강원</option>
					  <option value="e">제주</option>
					  <option value="f">대전</option>
					  <option value="g">충북</option>
					  <option value="h">충남</option>
					  <option value="i">부산</option>
					  <option value="j">울산</option>
					  <option value="k">경남</option>
					  <option value="l">대구</option>
					  <option value="m">경북</option>
					  <option value="n">광주</option>
					  <option value="o">전남</option>
					  <option value="p">전북</option>
					</select>   
					<select name="area2">
					  <option></option>
					</select>
			     </div>
						<script type="text/Javascript">
						
						// 위쪽 form의  name값  fm  수정시 아래도 동일하게 변경
						  var groups=document.fm.area.options.length      // c1 : 첫번째 name값
						  var group=new Array(groups)
						  for (i=0; i<groups; i++)
						  group[i]=new Array()
						
						// "선택하세요" 2번째 항목
						  group[0][0]=new Option("","왼쪽먼저")
						
						  // 선택1 연동
						  group[1][0]=new Option("강남/역삼/삼성/논현","1")
						  group[1][1]=new Option("서초/신사/방배","2")
						  group[1][2]=new Option("천호/길동/둔촌","3")
						  group[1][3]=new Option("화곡/까지찬/양천/목동","4")
						  group[1][4]=new Option("천호/길동/둔촌","5")
						  group[1][5]=new Option("구로/금천/오류","6")
						  group[1][6]=new Option("신촌/홍대/합정","7")
						  group[1][7]=new Option("영신내/불광/응암","8")
						  group[1][8]=new Option("종로/대학로/신도림","9")
						  group[1][9]=new Option("성신여대/성북/월곡","10")
						  group[1][10]=new Option("이태원/용산/서울역","11")
						  group[1][11]=new Option("동대문/동묘/신당/충무","12")
						  group[1][12]=new Option("회기/고려대/청량/신설","13")
						  group[1][13]=new Option("건대/군자/구의","14")
						  group[1][14]=new Option("왕십리/성수/금호","15")
						  group[1][15]=new Option("수유/미아","16")
						  group[1][16]=new Option("상봉/중랑/면목","17")
						  group[1][17]=new Option("태릉/노원/도봉/창동","18")
						
						// 선택2 연동
						  group[2][0]=new Option("Option11","Value11")
						 
						// 선택3 연동
						  group[3][0]=new Option("Option21","Value21")
						  group[3][1]=new Option("Option22","Value22")
						 
						  var temp=document.fm.area2  // c2 : 두번째 name값
						  function redirect(x){
						    for (m=temp.options.length-1;m>0;m--)
						    temp.options[m]=null
						    for (i=0;i<group[x].length;i++){
						      temp.options[i]=new Option(group[x][i].text,group[x][i].value)
						    }
						    temp.options[0].selected=true
						  }
						</script>
						<div style="text-align: center">
							<input type="submit" value="후원하기" class="btn btn-warning" >
						</div>
				</form>
			</c:otherwise>
			</c:choose>
			
			
			</div>
		</div>
</div>

 <script src="resoures/js/bootstrap.bundle.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.16/dist/summernote-bs4.min.js"></script>
<script>
$(document).ready(function(){
	$('#summernote').summernote({
		height : 100, // set editor height
		minHeight : null, // set minimum height of editor
		maxHeight : null, // set maximum height of editor
		focus : false,
		lang : 'ko-KR',
		codemirror: { // codemirror options
		    theme: 'monokai'
		},
		callbacks: {
			onImageUpload: function(files, editor, welEditable) {
				sendFile(files[0], this);
			}
		}
	});
	
	function sendFile(file, editor) {
		data = new FormData();
		data.append("uploadFile", file);
		$.ajax({
			data: data,
			type: "POST",
			url : "/summerupload.do",
			cache: false,
			contentType: false,
			processData : false,
			success : function(data) {
				$(editor).summernote('editor.insertImage', data.url);
			}
		});
	}
});
</script>
<!--/row-->
</html>

<jsp:include page="foot.jsp" />