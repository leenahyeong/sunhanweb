package com.sumhan.dto;

import java.util.Date;

public class SunhansVO {
	
	private String id;
	private String name;
	private String pass;
	private String addr;
	private String phone;
	private String email;
	private int points;
	private int admin;
	private Date enter;
	@Override
	public String toString() {
		return "SunhansVO [id=" + id + ", name=" + name + ", pass=" + pass + ", addr=" + addr + ", phone=" + phone
				+ ", email=" + email + ", points=" + points + ", admin=" + admin + ", enter=" + enter + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	public Date getEnter() {
		return enter;
	}
	public void setEnter(Date enter) {
		this.enter = enter;
	}
}
