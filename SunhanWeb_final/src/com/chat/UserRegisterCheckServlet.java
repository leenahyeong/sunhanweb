package com.chat;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sumhan.dao.SunhansDAO;
import com.sumhan.dto.SunhansVO;

/**
 * Servlet implementation class UserRegisterCheckServlet
 */
@WebServlet("/UserRegisterCheckServlet")
public class UserRegisterCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String userID=request.getParameter("userID");		

		if(userID==null|| userID.equals("")) response.getWriter().write("-1");
		
		SunhansDAO empDao = SunhansDAO.getInstance();
		
		System.out.println(empDao.getMemberID(userID));				
		
		response.getWriter().write(empDao.getMemberID(userID)+"");
	}

}
