<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="file.FileDAO" %>    
<%@ page import="java.io.File" %>    
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>    
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	String id = request.getParameter("userid"); //아이디가져옴

	String directory = application.getRealPath("/profile/");
	out.write(directory);
	int maxSize =1024*1024*100;
	String encoding ="UTF-8";
	MultipartRequest multipartRequest =new MultipartRequest(request,directory,maxSize,encoding,new DefaultFileRenamePolicy());
	
	String fileName=multipartRequest.getOriginalFileName("file");
	String fileRealName=multipartRequest.getFilesystemName("file");
	
	if(!fileName.endsWith(".png")&&!fileName.endsWith(".jpg"))
	{
		directory+="/";
		File filed=new File(directory+fileRealName);
		out.write("\n 파일경호 : "+filed.toString());
		filed.delete();
		filed.delete();
		filed.delete();
		filed.delete();
		filed.delete();
		filed.delete();
		filed.delete();
		out.write("업로드할 수 없는 확장자입니다.");
	}
	else
	{
			FileDAO File= FileDAO.getInstance();//인스턴스 호출
			int result = File.upload(fileName,fileRealName,id);
			out.write("파일명 : "+fileName+"<br>");
			out.write("R파일명 : "+fileRealName+"<br>");
			out.write("dd : "+id+"<br>");
	}
	String url="mypageedit.jsp";
	RequestDispatcher rd = request.getRequestDispatcher(url);
	rd.forward(request, response);
%>
</body>
</html>