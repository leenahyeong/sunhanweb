<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.PreparedStatement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>	
    <%@page import="java.sql.SQLException"%>
	<%@page import="java.sql.Connection"%>
	<%@page import="java.sql.DriverManager"%>
	<%

	        String get_id = null;
	        String get_pw = null;
 
			
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
					
					 String _id = new String(request.getParameter("id"));
					 String _pw = new String(request.getParameter("pw"));
					 String _name = new String(request.getParameter("name"));
					 String _email = new String(request.getParameter("phone"));
					 String _admin = new String(request.getParameter("check"));
					Connection conn =  DriverManager.getConnection(dbURL, dbID, dbPW);
                
					System.out.println("연결정보 : " + conn);
					System.out.println("연결정보(toString) : " + conn.toString());
					
					 Date today = new Date();
				        
					 SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
					 String dates= date.format(today);
					 
					 SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");
				        
				     SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");
				        
				     java.util.Date tempDate = null;
				        
				        try {
				            tempDate = beforeFormat.parse(dates);
				        } catch (ParseException e) {
				            e.printStackTrace();
				        }
				        
				        String transDate = afterFormat.format(tempDate);
				        
				        // 반환된 String 값을 Date로 변경한다.
				        java.sql.Date d = java.sql.Date.valueOf(dates);
					
					
					int result = -1;
		 			
					
					String sql = "insert into sunhans values(?,?,?,'주소','01066593667',?,0,?,?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
						              
					pstmt.setString(1, _id);
					pstmt.setString(2, _name);
					pstmt.setString(3, _pw);
					pstmt.setString(4, _email);
					pstmt.setString(5,_admin);
					pstmt.setDate(6, d);
					if(conn!=null)
					{	
						out.print(_id);
						out.print(_pw);
						out.print(_name);
						out.print(_pw);
						out.print(_email);
						out.print(d);
						
						out.print("연결성공");
					}
           		    result = pstmt.executeUpdate();
	 
	                if (result >= 0) {
                       out.println("success");
	                } else {
                       out.println("false");
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
			%>
        
			<%="연결 성공!!"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>