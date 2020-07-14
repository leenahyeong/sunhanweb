package com.android.servlet;

import java.io.IOException;

import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import org.json.JSONObject;

/**
 * 
 * Servlet implementation class androidgetStoreServlet
 * 
 */

@WebServlet("/androidSerchgetStoreServlet.do")

public class androidStoreSearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @see HttpServlet#HttpServlet()
	 * 
	 */

	public androidStoreSearchServlet() {

		super();

		// TODO Auto-generated constructor stub

	}

	/**
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		try {

			Class.forName("com.mysql.jdbc.Driver");
			String dbURL = "*";
			String dbID = "*";
			String dbPW = "*";

			Connection conn = DriverManager.getConnection(dbURL, dbID, dbPW);

			ResultSet rs = null;

			PreparedStatement pstmt = null;

			PrintWriter outs = response.getWriter();

			JSONObject store = new JSONObject();

			JSONArray arr = new JSONArray();

			String serch = request.getParameter("serch"); // 현재 로그인 중인 유저 아이디

			serch += "%";

			String sql = "select  a.*,b.fileRealName from store a inner join storefile b  on(a.userid=b.userid)  where shopname like ? group by userid";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, serch);

			rs = pstmt.executeQuery();

			while (rs.next())

			{

				JSONObject obj = new JSONObject();

				obj.put("sno", rs.getInt("sno"));

				obj.put("shopname", rs.getString("shopname"));
				;

				obj.put("topmessage", rs.getString("topmessage"));

				obj.put("addr", rs.getString("addr"));

				obj.put("area", rs.getString("area"));

				obj.put("comments", rs.getString("comments"));

				obj.put("information", rs.getString("information"));

				obj.put("food", rs.getString("food"));

				obj.put("price", rs.getString("price"));

				obj.put("enter", rs.getDate("enter"));

				obj.put("userid", rs.getString("userid"));

				obj.put("opentime", rs.getString("opentime"));

				obj.put("closetime", rs.getString("closetime"));

				obj.put("StorePhone", rs.getString("StorePhone"));

				obj.put("fileRealName", rs.getString("fileRealName"));

				arr.put(obj);

			}

			if (conn != null)

			{

			}

			store.put("storelist", arr);

			outs.print(store);

		} catch (ClassNotFoundException e) {

			// �뱶�씪�씠踰꾨�� 遺덈윭�삱 �븣 臾몄젣媛� 諛쒖깮

			System.out.println("�뱶�씪�씠踰� 濡쒕뱶 �떎�뙣");

			e.printStackTrace();

		} catch (SQLException e) {

			// DB �젒�냽 �떎�뙣

			System.out.println("DB �젒�냽 �떎�뙣");

			e.printStackTrace();

		}

	}

}
