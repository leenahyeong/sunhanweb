package com.store.dto;
import java.util.Date;

public class StoreVO {
	int sno;
	String shopname;
	String topmessage;
	String addr;
	String area;
	String comments;
	String information;
	String food;
	String price;
	Date enter;
	String userid;
	String opentime;
	String closetime;
	String StorePhone;
	
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getTopmessage() {
		return topmessage;
	}
	public void setTopmessage(String topmessage) {
		this.topmessage = topmessage;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getFood() {
		return food;
	}
	public void setFood(String food) {
		this.food = food;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Date getEnter() {
		return enter;
	}
	public void setEnter(Date enter) {
		this.enter = enter;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOpentime() {
		return opentime;
	}
	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}
	public String getClosetime() {
		return closetime;
	}
	public void setClosetime(String closetime) {
		this.closetime = closetime;
	}
	public String getStorePhone() {
		return StorePhone;
	}
	public void setStorePhone(String storePhone) {
		this.StorePhone = storePhone;
	}
	
	
	// dao
	public StoreVO() {
		super();
	}
	// 웹
	public StoreVO(String shopname, String topmessage, String addr, String area, String comments, String information,
			String food, String price, Date enter, String userid, String opentime, String closetime, String storePhone) {
		super();
		this.shopname = shopname;
		this.topmessage = topmessage;
		this.addr = addr;
		this.area = area;
		this.comments = comments;
		this.information = information;
		this.food = food;
		this.price = price;
		this.enter = enter;
		this.userid = userid;
		this.opentime = opentime;
		this.closetime = closetime;
		this.StorePhone = storePhone;
	}
	// 안드용
	public StoreVO(String shopname, String topmessage, String addr, String area, String comments, String information,
			String food, String price, String userid, String opentime, String closetime, String storePhone) {
		super();
		this.shopname = shopname;
		this.topmessage = topmessage;
		this.addr = addr;
		this.area = area;
		this.comments = comments;
		this.information = information;
		this.food = food;
		this.price = price;
		this.userid = userid;
		this.opentime = opentime;
		this.closetime = closetime;
		this.StorePhone = storePhone;
	}
}
