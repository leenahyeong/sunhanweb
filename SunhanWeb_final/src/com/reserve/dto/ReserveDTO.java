package com.reserve.dto;

import java.sql.Timestamp;

public class ReserveDTO {
	String rv_sno; // 가게 고유넘버, store의 userid를 fk로
	int rv_rno; // 예약 고유번호 (자동으로 insert할때마다 숫자 부여되니까 값 인위적으로 넣을 필요 없음)
	String rv_time; // 방문예정시간
	String rv_userid; // 예약한 사람 아이디
	int rv_personnel; // 방문예정인원
	Timestamp rv_date; //예약한 현재 시간, 이것도 자동으로 insert할때마다 현재 시간 들어가고 업데이트할때도 알아서 바뀌기때문에 인위적으로 넣을 필요x)  
	int rv_status; // 1 = 승인대기중, 2 = 예약승인완료, 3 = 예약거절(사장이 거절함), 4 = 예약취소(아동이 취소함)
	int rv_visit; // 방문여부 디폴트0, 1 = 방문, 2 = 미방문 (아동이 왔거나 안왔거나에 따라 체크하고 status테이블에 예약가능여부를 Y로 같이 업뎃해야됨)
	String rv_reason; // 만약 예약거절했다면 사유
	
	public String getRv_sno() {
		return rv_sno;
	}
	public void setRv_sno(String rv_sno) {
		this.rv_sno = rv_sno;
	}
	public int getRv_rno() {
		return rv_rno;
	}
	public void setRv_rno(int rv_rno) {
		this.rv_rno = rv_rno;
	}
	public String getRv_time() {
		return rv_time;
	}
	public void setRv_time(String rv_time) {
		this.rv_time = rv_time;
	}
	public String getRv_userid() {
		return rv_userid;
	}
	public void setRv_userid(String rv_userid) {
		this.rv_userid = rv_userid;
	}
	public int getRv_personnel() {
		return rv_personnel;
	}
	public void setRv_personnel(int rv_personnel) {
		this.rv_personnel = rv_personnel;
	}
	public Timestamp getRv_date() {
		return rv_date;
	}
	public void setRv_date(Timestamp rv_date) {
		this.rv_date = rv_date;
	}
	public int getRv_status() {
		return rv_status;
	}
	public void setRv_status(int rv_status) {
		this.rv_status = rv_status;
	}
	public int getRv_visit() {
		return rv_visit;
	}
	public void setRv_visit(int rv_visit) {
		this.rv_visit = rv_visit;
	}
	public String getRv_reason() {
		return rv_reason;
	}
	public void setRv_reason(String rv_reason) {
		this.rv_reason = rv_reason;
	}
	
	
}
