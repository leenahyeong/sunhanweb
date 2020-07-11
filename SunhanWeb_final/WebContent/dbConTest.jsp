<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>	
    <%@page import="java.sql.SQLException"%>
	<%@page import="java.sql.Connection"%>
	<%@page import="java.sql.DriverManager"%>
	<%
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
                
					System.out.println("연결정보 : " + conn);
					System.out.println("연결정보(toString) : " + conn.toString());
					
					if(conn!=null)
					{
						out.print("연결성공");
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