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

/**
 * Servlet implementation class androidReviewCountServlet
 */
@WebServlet("/androidreviewcount.do")
public class androidReviewCountServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String dbURL = "jdbc:mysql://3.12.173.221:3306/projectsd?&characterEncoding=UTF-8";
			String dbID = "hyeong";
			String dbPW = "user123";

			conn = DriverManager.getConnection(dbURL, dbID, dbPW);

			PrintWriter out =  response.getWriter();
			
			JSONObject reviewcntObj = new JSONObject();
			JSONArray arr = new JSONArray();
					
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT count(case when review_depth = 0 then 0 end) as '아동', count(case when review_depth = 1 then 0 end) as '사장',")
			.append(" round(avg(review_score)) FROM review WHERE review_storeid=?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, request.getParameter("userid"));

			
			rs = pstmt.executeQuery();

			while(rs.next()) {
				
			JSONObject obj = new JSONObject();
				
			obj.put("child_count", rs.getInt(1));
			obj.put("supporter_count", rs.getInt(2));
			obj.put("star_avg", rs.getInt(3));
			arr.put(obj);
		}
			
			reviewcntObj.put("reviewCntList", arr);
			out.print(reviewcntObj);
			
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
