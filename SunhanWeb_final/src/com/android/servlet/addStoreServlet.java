package com.android.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.dao.StoreDAO;

/**
 * Servlet implementation class addStoreServlet
 */
@WebServlet("/androidaddStoreServlet.do")
public class addStoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public addStoreServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter outs = response.getWriter();
		String _userid = new String(request.getParameter("id"));

		StoreDAO d = new StoreDAO();

		int re = d.StoreCheck(_userid); // 가게 있는지 없는지 체크
		int result = -1; // ?

		if (re == 1) { // 등록된 가게가 있을때
			String _storeName = new String(request.getParameter("storeName"));

			String _storeAddr = new String(request.getParameter("sendData"));

			String _topMessage = new String(request.getParameter("topMessage"));

			String _opentime = new String(request.getParameter("opentime"));

			String _storePhone = new String(request.getParameter("storePhone"));

			String _foodCheck = new String(request.getParameter("foodCheck"));

			String _comments = new String(request.getParameter("comments"));

			String _price = new String(request.getParameter("price"));

			String _closetime = new String(request.getParameter("closetime"));

			String _area = new String(request.getParameter("area"));

			String _information = new String(request.getParameter("information"));

			ResultSet rs = null;
			
			java.util.Date today = new java.util.Date();

			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			String dates = date.format(today);

			SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");

			SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");

			java.util.Date tempDate = null;

			try {
				tempDate = beforeFormat.parse(dates);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			String transDate = afterFormat.format(tempDate);

			// 諛섑솚�맂 String 媛믪쓣 Date濡� 蹂�寃쏀븳�떎.
			java.sql.Date dds = java.sql.Date.valueOf(dates);
			StoreDAO dai = new StoreDAO();

			String sql = "update store set shopname=?, topmessage=?,addr=?,area=?,comments=?,information=?,"
					+ "food=?,price=?,enter='" + dds + "',opentime=?,closetime=?,StorePhone=? where userid=?";

			PreparedStatement pstmt = null;
			Connection conn = null;

			try {
				conn = dai.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, _storeName);
				pstmt.setString(2, _topMessage);
				pstmt.setString(3, _storeAddr);
				pstmt.setString(4, _area);
				pstmt.setString(5, _comments);
				pstmt.setString(6, _information);
				pstmt.setString(7, _foodCheck);
				pstmt.setString(8, _price);
				pstmt.setString(9,_opentime);
				pstmt.setString(10,_closetime);
				pstmt.setString(11, _storePhone);
				pstmt.setString(12, _userid);
				result = pstmt.executeUpdate();
				outs.print(result);
				
				
			} catch (Exception e) {
			}
		} else {
			String _storeName = new String(request.getParameter("storeName"));

			String _storeAddr = new String(request.getParameter("sendData"));

			String _topMessage = new String(request.getParameter("topMessage"));

			String _opentime = new String(request.getParameter("opentime"));

			String _storePhone = new String(request.getParameter("storePhone"));

			String _foodCheck = new String(request.getParameter("foodCheck"));

			String _comments = new String(request.getParameter("comments"));

			String _price = new String(request.getParameter("price"));

			String _closetime = new String(request.getParameter("closetime"));

			String _area = new String(request.getParameter("area"));

			String _information = new String(request.getParameter("information"));

			
			ResultSet rs = null;

			Date today = new Date(result);

			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			String dates = date.format(today);

			SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");

			SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");

			java.util.Date tempDate = null;

			try {
				tempDate = beforeFormat.parse(dates);
			} catch (ParseException e) {
				e.printStackTrace();
				outs.print("�뜲�씠�듃�삤瑜�");
			}

			String transDate = afterFormat.format(tempDate);

			// 諛섑솚�맂 String 媛믪쓣 Date濡� 蹂�寃쏀븳�떎.
			java.sql.Date dds = java.sql.Date.valueOf(dates);
			StoreDAO dai = new StoreDAO();

			String sql = "insert into store(shopname, topmessage, addr, area, comments, information, food, price, enter, userid, opentime, closetime, StorePhone)"
					+ "values(?,?,?,?,?,?,?,?,'" + dds + "',?,?,?,?)";
			PreparedStatement pstmt = null;
			Connection conn = null;

			try {
				conn = dai.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, _storeName);
				pstmt.setString(2, _topMessage);
				pstmt.setString(3, _storeAddr);
				pstmt.setString(4, _area);
				pstmt.setString(5, _comments);
				pstmt.setString(6, _information);
				pstmt.setString(7, _foodCheck);
				pstmt.setString(8, _price);
				pstmt.setString(9, _userid);
				pstmt.setString(10,_opentime);
				pstmt.setString(11,_closetime);
				pstmt.setString(12, _storePhone);
				result = pstmt.executeUpdate();

				outs.print(result);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
