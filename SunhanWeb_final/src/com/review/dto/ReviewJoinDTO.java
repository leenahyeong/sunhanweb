package com.review.dto;

import java.sql.Timestamp;

public class ReviewJoinDTO {
	int review_no; // ���� ������ȣ
	Integer review_group; // == review_no , integer�� null�� ó������
	int review_depth; // 0 = ���� , 1= �� ���信 ���� ���
	int review_rno; // ���� ������ȣ fk
	String review_userid; // �ۼ��� ���
	String review_storeid; // �Ƶ��϶��� ���� ���̵�, ���Ի����϶��� �Ƶ����̵� ���� �÷�
	int review_score; // ����
	String review_content; // ���䳻��
	Timestamp review_date; // �ۼ���
	//// ����� �ٸ� ���̺� �÷� ////
	String review_shopname;
	String review_username;
	
	public int getReview_no() {
		return review_no;
	}
	public void setReview_no(int review_no) {
		this.review_no = review_no;
	}
	public Integer getReview_group() {
		return review_group;
	}
	public void setReview_group(Integer review_group) {
		this.review_group = review_group;
	}
	public int getReview_depth() {
		return review_depth;
	}
	public void setReview_depth(int review_depth) {
		this.review_depth = review_depth;
	}
	public int getReview_rno() {
		return review_rno;
	}
	public void setReview_rno(int review_rno) {
		this.review_rno = review_rno;
	}
	public String getReview_userid() {
		return review_userid;
	}
	public void setReview_userid(String review_userid) {
		this.review_userid = review_userid;
	}
	public String getReview_storeid() {
		return review_storeid;
	}
	public void setReview_storeid(String review_storeid) {
		this.review_storeid = review_storeid;
	}
	public int getReview_score() {
		return review_score;
	}
	public void setReview_score(int review_score) {
		this.review_score = review_score;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public Timestamp getReview_date() {
		return review_date;
	}
	public void setReview_date(Timestamp review_date) {
		this.review_date = review_date;
	}
	public String getReview_shopname() {
		return review_shopname;
	}
	public void setReview_shopname(String review_shopname) {
		this.review_shopname = review_shopname;
	}
	public String getReview_username() {
		return review_username;
	}
	public void setReview_username(String review_username) {
		this.review_username = review_username;
	}
	
}
