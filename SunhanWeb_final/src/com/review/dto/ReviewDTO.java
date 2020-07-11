package com.review.dto;

import java.sql.Timestamp;

public class ReviewDTO {
	int review_no; // 리뷰 고유번호
	Integer review_group; // == review_no , integer은 null값 처리가능
	int review_depth; // 0 = 리뷰 , 1= 그 리뷰에 대한 답글
	int review_rno; // 예약 고유번호 fk
	String review_userid; // 작성한 사람
	String review_storeid; // 아동일때는 가게 아이디가, 가게일때는 아동아이디가
	int review_score; // 점수
	String review_content; // 리뷰내용
	Timestamp review_date; // 작성일
	
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
	
	
}
