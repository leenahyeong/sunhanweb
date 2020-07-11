package com.android.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import storefile.storefileDTO;

/**
 * Servlet implementation class androidgetStoreServlet
 */
@WebServlet("/androidgetStoreImageServlet.do")
public class androidgetStoreImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public androidgetStoreImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8"); response.setContentType("text/html; charset=UTF-8");
		
			try {
					// 1. JDBC 드라이버 설치
					// mysql-connector-java-8.0.11.jar 파일을 web-inf >> lib 폴더에 추가
             
					// 2. 설치되어있는 드라이버를 로드
					Class.forName("com.mysql.jdbc.Driver");

					// 3. 드라이버를 사용해서 디비 연결 아이디/비밀번호/디비 주소
					// mysql-connector-java 6.XX 이상 사용시 디비 접근 주소(서버시간 인식 오류)
					// String dbURL = "jdbc:mysql://localhost:3306/itwill";
					String dbURL = "jdbc:mysql://3.12.173.221:3306/projectsd?&characterEncoding=UTF-8";
					String dbID = "mysqluser";
					String dbPW = "user123";
					 
					Connection conn =  DriverManager.getConnection(dbURL, dbID, dbPW);
             
					ResultSet rs = null;
					PreparedStatement pstmt=null;
					PrintWriter outs =  response.getWriter();
					
					JSONObject store = new JSONObject();
					JSONArray arr = new JSONArray();
					
					String _id = new String(request.getParameter("id"));
					
					String SQL ="select fileRealName from storefile where userid=?";	
					pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, _id);
					rs = pstmt.executeQuery();
					while (rs.next())
					{  
							JSONObject obj = new JSONObject();
							obj.put("fileRealName",rs.getString("fileRealName"));
							arr.put(obj);
					}
					if(conn!=null)
					{	
						
					}
					store.put("storeImagelist", arr);
					outs.print(store);
			}catch(ClassNotFoundException e) {
					// 드라이버를 불러올 때 문제가 발생
					System.out.println("드라이버 로드 실패");
					e.printStackTrace();
				}catch(SQLException e) {
					// DB 접속 실패
					System.out.println("DB 접속 실패");
					e.printStackTrace();
			}
	}
}
