package com.reserve.dto;

import java.sql.Timestamp;

public class ReserveJoinDTO {
	String rv_sno; // storeid
	int rv_rno; // ���������ȣ
	String rv_time; // �湮�����ð�
	String rv_userid; // ������ ���� ���� ���̵�
	int rv_personnel; // �湮�ο�
	Timestamp rv_date; // �����û�� ��¥
	int rv_status; // ���� ���� ó�� ���� ���ִ���
	int rv_visit; // �湮����
	String rv_reason; // �������� �� ����
	String rs_userid; // ������� ���̺� �Ƶ����̵�
	String rs_storeid; // ������� ���̺� ���Ծ��̵�
	String rs_available; // ���� �� �Ƶ��� ���డ������ ����
	
	
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
