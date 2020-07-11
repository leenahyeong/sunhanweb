	function loginCheck() {
	if (document.frm.userid.value == "") {
		alert("아이디를 써주세요");
		frm.userid.focus();
		return false;
	}
	if (document.frm.pass.value == "") {
		alert("암호는 반드시 입력해야 합니다.");
		frm.pwd.focus();
		return false;
	}
	return true;
}

function idCheck() {
	if (document.frm.userid.value == "") {
		alert('아이디를 입력하여 주십시오.');
		document.frm.userid.focus();
		return;
	}
	if(String.IsKorean(document.frm.userid.value)==true)
	{
			var id=document.frm.userid.value
			alert('한글아이디는 사용이 불가합니다.! '+id+" !");
			document.frm.userid.focus();
			return;
	}
	if (document.frm.userid.value.length > 10) {
		alert("아이디가 너무깁니다");
		frm.userid.focus();
		return false;
	}
	if(is_han(document.frm.userid.value)==true)
	{
		var id=document.frm.userid.value
		alert('한글아이디는 사용이 불가합니다.! '+id+" !");
		document.frm.userid.focus();
		return;
	}
	if (document.frm.userid.value.length < 4) {
		alert("아이디는 4글자이상이어야 합니다.");
		frm.userid.focus();
		return false;
	}
	var url="sunhansIdCH.do?userid=" + document.frm.userid.value;
	window.open(url, "_blank_1",
					"toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=450, height=200");
}

function idok(userid) {
	opener.frm.userid.value = document.frm.userid.value;
	opener.frm.reid.value = document.frm.userid.value;
	self.close();
}

function joinCheck() {
	if (document.frm.name.value.length == 0) {
		alert("이름을 써주세요.");
		frm.name.focus();
		return false;
	}
	if (document.frm.userid.value.length == 0) {
		alert("아이디를 써주세요");
		frm.userid.focus();
		return false;
	}
	if (document.frm.pwd.value == "") {
		alert("암호는 반드시 입력해야 합니다.");
		frm.pwd.focus();
		return false;
	}
	if (document.frm.pwd.value != document.frm.pwd_check.value) {
		alert("암호가 일치하지 않습니다.");
		frm.pwd.focus();
		return false;
	}
	if (document.frm.reid.value.length == 0) {
		alert("중복 체크를 하지 않았습니다.");
		frm.userid.focus();
		return false;
	}
	return true;
}
function joinCheckd() {
	if (document.frm.name.value.length == 0) {
		confirm("닉네임을 써주세요.");
		return false;
	}
	if (document.frm.phone.value.length == 0) {
		confirm("전화번호를 써주세요");
		return false;
	}
	if (document.frm.s.value.length == 0) {

		confirm("이메일은 반드시 입력해야 합니다.");
		return false;
	}
	if (document.frm.addr.value.length == 0) {
		confirm("주소는 반드시 입력해야 합니다.");
		return false;
	}
	return true;
}
function passCheckd()
{	
	if (document.frmx.password.value.length == 0)
	{
		confirm("password을 써주세요.");
		frm.name.focus();
		return false;
	}
	if (document.frmx.password2.value.length == 0)
	{
		confirm("password2을 써주세요.");
		frm.name.focus();
		return false;
	}

var pass=document.frmx.password.value;
var pass2=document.frmx.password2.value;

	if (pass!=pass2)
	{
		confirm("암호가 일치하지않습니다..");
		return false;
	}
	return true;
}
String.prototype.IsKorean = function () {
    var arg = arguments[0] === undefined ? this.toString() : arguments[0];
    if (arg === undefined || arg === null) { throw "Property or Arguments was Never Null."; }
    else {
        var _chk = true, _sp = /\s+/;
        if (typeof (arg) != "string") { throw "Property or Arguments was not 'String' Types."; }
        for (var i = 0; i < arg.length; i++) {
            var _ch = arg[i].charCodeAt();
            _chk = _chk && ((_ch >= 0x3131 && _ch <= 0x318E) || (_ch >= 0xAC00 && _ch <= 0xD7AF) || _sp.test(arg[i]));
        }
        return _chk;
    }
}
String.IsKorean = function (arg) {
    if (arg === undefined || arg === null) { throw "Property or Arguments was Never Null."; }
    else {
        if (typeof (arg) != "string") { throw "Property or Arguments was not 'String' Types."; }
        return arg.IsKorean();
    }
}
function fn_press_han(obj)
{
    //좌우 방향키, 백스페이스, 딜리트, 탭키에 대한 예외
    if(event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 37 || event.keyCode == 39
    || event.keyCode == 46 ) return;
    //obj.value = obj.value.replace(/[\a-zㄱ-ㅎㅏ-ㅣ가-힣]/g, '');
    obj.value = obj.value.replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g, '');
    var RegExpHG = "(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/)"; 
    str = str.replace(RegExpHG,"");
}
function is_han(val) { //한글이 하나라도 섞여 있으면 true를 반환
	
	var judge = false;
	
	for(var i = 0; i < val.length; i++) {
	
		var chr = val.substr(i,1);
	
		chr = escape(chr);
	if (chr.charAt(1) == "u") {
	chr = chr.substr(2, (chr.length - 1));
	if((chr >= "3131" && chr <= "3163") || (chr >= "AC00" && chr <= "D7A3")) {
	judge = true;
	break;
	}
	}
	else judge = false;
	}
	return judge;
	}