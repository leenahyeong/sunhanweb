package com.reserve.dto;

public class ReserveStatusDTO {
	String rs_userid; // �Ƶ� ���̵�
	String rs_storeid; // ���� ���̵�
	String rs_available; // �������� ������ ���� - �����Ҷ� Y , �Ұ����Ҷ� N
	
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
