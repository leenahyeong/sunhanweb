package com.android.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.reserve.dto.ReserveChildDTO;
import com.store.dao.StoreDAO;


@WebServlet("/androidreservesupporterlist.do")
public class androidRvSupporterListServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String dbURL = "*";
			String dbID = "*";
			String dbPW = "*";

			conn = DriverManager.getConnection(dbURL, dbID, dbPW);

			PrintWriter out =  response.getWriter();
			
			JSONObject reserveObj = new JSONObject();
			JSONArray arr = new JSONArray();
					
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT * FROM reserve WHERE rv_sno=? ORDER BY rv_rno DESC");
					
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, new String(request.getParameter("storeid")));  
							
			rs = pstmt.executeQuery();

			while(rs.next()) {
				
				JSONObject obj = new JSONObject();
				
				obj.put("rv_sno", rs.getString("rv_sno"));
				obj.put("rv_rno", rs.getInt("rv_rno"));
				obj.put("rv_time", rs.getString("rv_time"));
				obj.put("rv_userid", rs.getString("rv_userid"));
				obj.put("rv_personnel", rs.getInt("rv_personnel"));
				obj.put("rv_date", rs.getTimestamp("rv_date"));
				obj.put("rv_status", rs.getInt("rv_status"));
				obj.put("rv_visit", rs.getInt("rv_visit"));
				obj.put("rv_reason", rs.getString("rv_reason"));
				arr.put(obj);
			}
			
			reserveObj.put("reserve", arr);
			out.print(reserveObj);
			System.out.println(reserveObj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
