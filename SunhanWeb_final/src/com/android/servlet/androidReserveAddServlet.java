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

@WebServlet("/androidreserveadd.do")
public class androidReserveAddServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		int result = Integer.parseInt(request.getParameter("result"));
		
		int c = 0;
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
								
			StringBuffer sql = new StringBuffer();
			
			String rv_userid = request.getParameter("id"); // 현재 로그인 중인 유저 아이디
			String rv_storeid = request.getParameter("storeid"); // 가게아이디
			int rv_personnel = Integer.parseInt(request.getParameter("personnel"));
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			String now = request.getParameter("now");
			String option = request.getParameter("option");
			String minute = request.getParameter("minute");
			if(option.length() == 1) {
				option = "0"+option;
			}
			
			if(minute.length() == 1) {
				minute = "0"+minute;
			}
			
			String rv_time = now.replace("-", "").trim()+option+minute;
			
			String rs_userid = request.getParameter("id"); // 현재 로그인 중인 유저 아이디
			String rs_storeid = request.getParameter("storeid"); // 가게아이디
				
			if(result == 0) {
				// 한번도 예약한 적 없는 신규
					
				sql.append("INSERT INTO reserve(rv_sno, rv_time, rv_userid, rv_personnel, rv_status) VALUES(?,?,?,?,?)");
				
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, rv_storeid);
				pstmt.setString(2, rv_time);
				pstmt.setString(3, rv_userid);
				pstmt.setInt(4, rv_personnel);
				pstmt.setInt(5, 1);
					
				c = pstmt.executeUpdate();
				// 초기화
				pstmt.clearParameters(); 
				sql.delete(0, sql.toString().length());
					
				// 신규아동은 status 테이블에도 데이터가 없으니까 insert
				sql.append("INSERT INTO reserve_status(rs_userid, rs_storeid, rs_available) VALUES(?,?,'N')");
					
				pstmt = conn.prepareStatement(sql.toString());
					
				pstmt.setString(1, rs_userid);
				pstmt.setString(2, rs_storeid);

				c += pstmt.executeUpdate();
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
				
				out.print(c);
					
			} else if(result == 1) {
				// 기존에 예약한 적 있지만 현재 예약 가능한 상태
				sql.append("INSERT INTO reserve(rv_sno, rv_time, rv_userid, rv_personnel, rv_status) VALUES(?,?,?,?,?)");
					
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, rv_storeid);
				pstmt.setString(2, rv_time);
				pstmt.setString(3, rv_userid);
				pstmt.setInt(4, rv_personnel);
				pstmt.setInt(5, 1);
					
				c = pstmt.executeUpdate();
				// 초기화
				pstmt.clearParameters(); 
				sql.delete(0, sql.toString().length());
					
				// 기존에 있는 데이터는 있으니까 status 테이블 update 
				sql.append("UPDATE reserve_status SET rs_available='N' WHERE rs_userid=? AND rs_storeid=?");
				System.out.println("업데이트 됐나?");
				pstmt = conn.prepareStatement(sql.toString());
					
				pstmt.setString(1, rs_userid);
				pstmt.setString(2, rs_storeid);

				c += pstmt.executeUpdate();
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
				
				out.print(c);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
