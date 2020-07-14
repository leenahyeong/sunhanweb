package com.android.servlet;

import java.awt.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
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

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sumhan.dto.SunhansVO;

import file.FileDAO;

/**
 * Servlet implementation class loginservlet
 */
@WebServlet("/androidLogin.do")
public class loginservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8"); response.setContentType("text/html; charset=UTF-8");

			String get_id = null;
	        	String get_pw = null;

			int result = -1;
			try {
					// 1. JDBC 드라이버 설치
					// mysql-connector-java-8.0.11.jar 파일을 web-inf >> lib 폴더에 추가
             
					// 2. 설치되어있는 드라이버를 로드
					Class.forName("com.mysql.jdbc.Driver");

					// 3. 드라이버를 사용해서 디비 연결 아이디/비밀번호/디비 주소
					// mysql-connector-java 6.XX 이상 사용시 디비 접근 주소(서버시간 인식 오류)
					// String dbURL = "jdbc:mysql://localhost:3306/itwill";
					String dbURL = "*";
					String dbID = "*";
					String dbPW = "*";
					
					String _id = new String(request.getParameter("id"));
					String _pw = new String(request.getParameter("pw"));
					

					String realfile="profile/";
					FileDAO File= new FileDAO();
					String profileDr = File.Search(_id);

					if(profileDr==null)
					{
						realfile="resoures/images/userEX.png";
					}
					else{
						realfile+=profileDr;
					}
				
					Connection conn =  DriverManager.getConnection(dbURL, dbID, dbPW);
             
					ResultSet rs = null;
					
					PrintWriter outs =  response.getWriter();
					
					JSONObject loginuser = new JSONObject();
					JSONArray arr = new JSONArray();
				    JSONObject obj = new JSONObject();
				    String jsondata;
				   
					String sql = "select * from sunhans where id=?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
						              
					pstmt.setString(1, _id);
					
					rs = pstmt.executeQuery();
					if (rs.next()) {
						
						if (rs.getString("pw") != null && (_pw.equals(rs.getString("pw"))))
						{
							  
							if(rs.getInt("admin")==1)
							{
								obj.put("id",rs.getString("id"));
								obj.put("name",rs.getString("name"));
								obj.put("pw",rs.getString("pw"));
								obj.put("addr",rs.getString("addr"));
								obj.put("phone",rs.getString("phone"));
								obj.put("email",rs.getString("email"));
								obj.put("points",rs.getString("points"));
								obj.put("admin",rs.getString("admin"));
								obj.put("enter",rs.getString("enter"));
								obj.put("profile",realfile);
							    jsondata = obj.toString();
							    arr.put(obj);
							    loginuser.put("loginuser", arr);
								  outs.print(loginuser);
							}
							else{
								obj.put("id",rs.getString("id"));
								obj.put("name",rs.getString("name"));
								obj.put("pw",rs.getString("pw"));
								obj.put("addr",rs.getString("addr"));
								obj.put("phone",rs.getString("phone"));
								obj.put("email",rs.getString("email"));
								obj.put("points",rs.getString("points"));
								obj.put("admin",rs.getString("admin"));
								obj.put("enter",rs.getString("enter"));
								obj.put("profile",realfile);
							      jsondata = obj.toString();
								  arr.put(obj);
								  loginuser.put("loginuser", arr);
								  outs.print(loginuser);
							}
						}
						else {
							result = 0;//비밀번호가 없거나 맞지않음
						}
					}
					else {
						result = -1;//없는회원
					}
					
					if(conn!=null)
					{	
					}
	                if (result >= 0) {
	                } else {
	                }
			}

				catch(ClassNotFoundException e) {
					// 드라이버를 불러올 때 문제가 발생
					System.out.println("드라이버 로드 실패");
					e.printStackTrace();
				}
				catch(SQLException e) {
					// DB 접속 실패
					System.out.println("DB 접속 실패");
					e.printStackTrace();
				}
		
	}


}
