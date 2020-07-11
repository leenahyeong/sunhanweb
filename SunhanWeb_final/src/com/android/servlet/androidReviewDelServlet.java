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

@WebServlet("/androidreviewdel.do")
public class androidReviewDelServlet extends HttpServlet {

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
			String dbURL = "jdbc:mysql://3.12.173.221:3306/projectsd?&characterEncoding=UTF-8";
			String dbID = "hyeong";
			String dbPW = "user123";

			conn = DriverManager.getConnection(dbURL, dbID, dbPW);

			PrintWriter out = response.getWriter();

			StringBuffer sql = new StringBuffer();

			int review_no = Integer.parseInt(request.getParameter("review_no"));
			int check = Integer.parseInt(request.getParameter("check"));

			// 아동이 삭제요청했을때는 답글달린 사장리뷰도 삭제
			if (check == 0) {
				sql.append("DELETE review.* FROM review").append(" LEFT OUTER JOIN review as re")
						.append(" ON review.review_group = re.review_group").append(" WHERE review.review_group = ?");

				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, review_no); // 아동이 삭제시에는 그 리뷰의 그룹번호를 넣어줘야됨, 이름만 review_no
			}

			// 사장이 삭제요청
			else if (check == 1) {
				sql.append("DELETE FROM review").append(" WHERE review_no = ?");

				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, review_no);
			}
			
			c = pstmt.executeUpdate();
			
			out.print(c);
			
			
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
