package com.android.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.reserve.dao.ReserveDAO;
import com.reserve.dto.ReserveDTO;
import com.reserve.dto.ReserveStatusDTO;

@WebServlet("/androidreviewadd.do")
public class androidReviewAddServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		int c = 0;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String dbURL = "*";
			String dbID = "*";
			String dbPW = "*";

			conn = DriverManager.getConnection(dbURL, dbID, dbPW);

			PrintWriter out = response.getWriter();

			StringBuffer sql = new StringBuffer();

			int admin = Integer.parseInt(request.getParameter("admin"));

			// 아동이 리뷰 달았을때
			if (admin == 0) {
				sql.append("SELECT max(review_group) FROM review");

				pstmt = conn.prepareStatement(sql.toString());

				rs = pstmt.executeQuery();

				Integer maxvalue = null;

				if (rs.next()) {
					maxvalue = rs.getInt(1) + 1;
				} else {
					maxvalue = 1;
				}
				System.out.println(maxvalue + "엥?");
				out.print(maxvalue);
				// 초기화
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());

				sql.append(
						"INSERT INTO review(review_group, review_depth, review_rno, review_userid, review_storeid, review_score, review_content)")
						.append(" VALUES(?,?,?,?,?,?,?)");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setInt(1, maxvalue);
				pstmt.setInt(2, admin);
				pstmt.setInt(3, Integer.parseInt(request.getParameter("review_rno")));
				pstmt.setString(4, request.getParameter("review_userid"));
				pstmt.setString(5, request.getParameter("review_storeid"));
				pstmt.setInt(6, Integer.parseInt(request.getParameter("review_score")));
				pstmt.setString(7, request.getParameter("review_content"));

				c = pstmt.executeUpdate();
				out.print(c);
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
			}

			// 사장이 리뷰에 대한 답글 달았을때
			else if (admin == 1) {
				sql.append(
						"INSERT INTO review(review_group, review_depth, review_rno, review_userid, review_storeid, review_content)")
						.append(" VALUES(?,?,?,?,?,?)");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setInt(1, Integer.parseInt(request.getParameter("review_group")));
				pstmt.setInt(2, admin);
				pstmt.setInt(3, Integer.parseInt(request.getParameter("review_rno")));
				pstmt.setString(4, request.getParameter("review_userid"));
				pstmt.setString(5, request.getParameter("review_storeid"));
				pstmt.setString(6, request.getParameter("review_content"));

				c = pstmt.executeUpdate();
				out.print(c);
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
			}
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
