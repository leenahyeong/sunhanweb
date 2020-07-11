package com.reserve.dto;

import java.sql.Timestamp;

public class ReserveJoinDTO {
	String rv_sno; // storeid
	int rv_rno; // 예약고유번호
	String rv_time; // 방문예정시간
	String rv_userid; // 예약한 유저 고유 아이디
	int rv_personnel; // 방문인원
	Timestamp rv_date; // 예약신청한 날짜
	int rv_status; // 현재 예약 처리 어케 돼있는지
	int rv_visit; // 방문여부
	String rv_reason; // 거절했을 시 사유
	String rs_userid; // 예약상태 테이블 아동아이디
	String rs_storeid; // 예약상태 테이블 가게아이디
	String rs_available; // 현재 이 아동이 예약가능한지 여부
	
	
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
	public String getRs_userid() {
		return rs_userid;
	}
	public void setRs_userid(String rs_userid) {
		this.rs_userid = rs_userid;
	}
	public String getRs_storeid() {
		return rs_storeid;
	}
	public void setRs_storeid(String rs_storeid) {
		this.rs_storeid = rs_storeid;
	}
	public String getRs_available() {
		return rs_available;
	}
	public void setRs_available(String rs_available) {
		this.rs_available = rs_available;
	}
	
}
