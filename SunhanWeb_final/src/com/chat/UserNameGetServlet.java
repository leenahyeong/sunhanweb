package com.chat;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sumhan.dao.SunhansDAO;

/**
 * Servlet implementation class UserNameGetServlet
 */
@WebServlet("/UserNameGetServlet")
public class UserNameGetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID= request.getParameter("userNamego");
		if(userID==null || userID.equals(""))
		{
			response.getWriter().write(" ");
		}else{
			try{
				userID =URLDecoder.decode(userID,"UTF-8");
				SunhansDAO empDao = SunhansDAO.getInstance();//인스턴스 호출 
				String name=empDao.getMemberName(userID);
				response.getWriter().write(name+" ");
			}catch (Exception e) {
				response.getWriter().write("");
			}
			
		}
		
	}

}
