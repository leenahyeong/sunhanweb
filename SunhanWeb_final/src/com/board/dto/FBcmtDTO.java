package com.board.dto;

import java.sql.Timestamp;

public class FBcmtDTO {
	int cmt_bno;
	int cmt_cno;
	String cmt_id;
	String cmt_name;
	String cmt_content;
	Timestamp cmt_regdate;
	
	public int getCmt_bno() {
		return cmt_bno;
	}
	public void setCmt_bno(int cmt_bno) {
		this.cmt_bno = cmt_bno;
	}
	public int getCmt_cno() {
		return cmt_cno;
	}
	public void setCmt_cno(int cmt_cno) {
		this.cmt_cno = cmt_cno;
	}
	public String getCmt_id() {
		return cmt_id;
	}
	public void setCmt_id(String cmt_id) {
		this.cmt_id = cmt_id;
	}
	public String getCmt_name() {
		return cmt_name;
	}
	public void setCmt_name(String cmt_name) {
		this.cmt_name = cmt_name;
	}
	public String getCmt_content() {
		return cmt_content;
	}
	public void setCmt_content(String cmt_content) {
		this.cmt_content = cmt_content;
	}
	public Timestamp getCmt_regdate() {
		return cmt_regdate;
	}
	public void setCmt_regdate(Timestamp cmt_regdate) {
		this.cmt_regdate = cmt_regdate;
	}
	
}
