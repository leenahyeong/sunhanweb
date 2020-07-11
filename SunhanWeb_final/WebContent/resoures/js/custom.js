window.onload = function() {

	// 메인 cata 이미지 변경
	$('#cata_ul1').on('mouseover', function() {
		$('#storeImg').attr('src', 'resoures/images/store-w.png');
	});

	$('#cata_ul1').on('mouseout', function() {
		$('#storeImg').attr('src', 'resoures/images/store-m.png');
	});

	$('#cata_ul2').on('mouseover', function() {
		$('#bellImg').attr('src', 'resoures/images/bell-w.png');
	});

	$('#cata_ul2').on('mouseout', function() {
		$('#bellImg').attr('src', 'resoures/images/bell-m.png');
	});

	$('#cata_ul3').on('mouseover', function() {
		$('#freeboardImg').attr('src', 'resoures/images/users-w.png');
	});

	$('#cata_ul3').on('mouseout', function() {
		$('#freeboardImg').attr('src', 'resoures/images/users-m.png');
	});

	$('#cata_ul4').on('mouseover', function() {
		$('#rankImg').attr('src', 'resoures/images/rank-w.png');
	});

	$('#cata_ul4').on('mouseout', function() {
		$('#rankImg').attr('src', 'resoures/images/rank-m.png');
	});
	

	/*
	 * 자바스크립트에서 jstl 쓰는법
 	var file1_btn = document.getElementById("file1_btn");
	var file1 = '<c:out value="${dto.file1}"/>';
	
	file1_btn.onclick = function(){
		alert("클릭됨!!" + file1)
		if(file1 != null) {
			alert("이미 파일이 있습니다. 삭제 후 등록해주세요.");
		}
	};
	 */
	
	// value값 변경이 있을때 체크하는 change 함수 ( 첨부파일 용량 체크 )
	$('input[type=file]').change(function(){
		if($(this).val() != null) {
			var fileSize = this.files[0].size;
			var maxSize = 10 * 1024 * 1024;
			if(maxSize < fileSize) {
				alert("파일첨부는 한 파일당 10MB까지 등록할 수 있습니다.");
				$(this).val(null);
				return false;
			}
		}
	});
}
