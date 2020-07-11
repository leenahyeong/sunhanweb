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

@WebServlet("/androidreserveupdate.do")
public class androidReserveUpdateServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
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


			StringBuffer sql = new StringBuffer();

			String userid = request.getParameter("userid"); // ���� �α��� ���� ���� ���̵�
			String storeid = request.getParameter("storeid"); // ���� �α��� ���� ���� ���̵�
			String admin = request.getParameter("admin"); // ���� �α��� ���� ���� admin

			int result = Integer.parseInt(request.getParameter("result"));
			int rno = Integer.parseInt(request.getParameter("rno"));
			int status = Integer.parseInt(request.getParameter("status"));
			String reason = request.getParameter("reason"); // ���� �α��� ���� ���� admin
			// 2,3,4,-1,-2
			// �Ƶ� 4 = �������, -1 = �������� ����
			// �Ŀ��� 2 = �������, 3 = �������, -2 = �湮����

			// �������
			if (result == 4) {
				sql.append("UPDATE reserve SET rv_status=? WHERE rv_rno=?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setInt(1, status);
				pstmt.setInt(2, rno);

				c = pstmt.executeUpdate();

				// �ʱ�ȭ
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());

				sql.append("UPDATE reserve_status SET rs_available=? WHERE rs_userid=? AND rs_storeid=?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "Y");
				pstmt.setString(2, userid);
				pstmt.setString(3, storeid);

				c += pstmt.executeUpdate();
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
				
				out.print(c);
			}

			else if (result == -1) { // ���º��� x, �Ƶ��� �������� �ٲٴ°�

				sql.append("UPDATE reserve SET rv_time=?, rv_personnel=? WHERE rv_rno=?");
				
				String now = request.getParameter("now_yyyy"); // �����
				String option = request.getParameter("option"); // ��
				String minute = request.getParameter("minute"); // ��
				now = now.replace("-", "");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				if(option.length() == 1) {
					option = "0"+option;
				}
				if(minute.length() == 1) {
					minute = "0"+minute;
				}
				
				pstmt.setString(1, now+option+minute);
				pstmt.setInt(2, Integer.parseInt(request.getParameter("personnel")));
				pstmt.setInt(3, rno);
				c = pstmt.executeUpdate();

				// �ʱ�ȭ
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
				
				out.print(c);
			}

			else if (result == 2) { // ����
				sql.append("UPDATE reserve SET rv_status=? WHERE rv_rno=?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setInt(1, status); // 2
				pstmt.setInt(2, rno);

				c = pstmt.executeUpdate();

				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());

				out.print(c);
			}

			else if (result == 3) { // 3=����
				sql.append("UPDATE reserve SET rv_status=?, rv_reason=? WHERE rv_rno=?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setInt(1, status); // 3
				pstmt.setString(2, reason);
				pstmt.setInt(3, rno);

				c = pstmt.executeUpdate();

				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());

				sql.append("UPDATE reserve_status SET rs_available=? WHERE rs_userid=? AND rs_storeid=?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "Y");
				pstmt.setString(2, userid);
				pstmt.setString(3, storeid);

				c += pstmt.executeUpdate();

				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());

				out.print(c);
			}

			else if (result == -2) { // �湮����
				sql.append("UPDATE reserve SET rv_visit=? WHERE rv_rno=?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setInt(1, Integer.parseInt(request.getParameter("visit")));
				pstmt.setInt(2, rno);

				c = pstmt.executeUpdate();

				// �ʱ�ȭ
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());

				sql.append("UPDATE reserve_status SET rs_available=? WHERE rs_userid=? AND rs_storeid=?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "Y");
				pstmt.setString(2, userid);
				pstmt.setString(3, storeid);

				c += pstmt.executeUpdate();
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());

				out.print(c);
			}

		} catch (Exception e) {
			e.printStackTrace();
			out.print("오류");
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
