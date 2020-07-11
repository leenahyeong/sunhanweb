package com.chat;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sumhan.dto.SunhansVO;

/**
 * Servlet implementation class ChatUnreadServelt
 */
@WebServlet("/chatUnread")
public class ChatUnreadServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginUser")!=null)
		{
		SunhansVO emp = (SunhansVO) session.getAttribute("loginUser");
		String userID = (String) emp.getId();

		if(userID==null || userID.equals(""))
		{
			response.getWriter().write("0");
		}else{
			 userID= URLDecoder.decode(userID,"UTF-8");
			 ChatDAO chatDAO =new ChatDAO();
				
			 response.getWriter().write(chatDAO.getAllUnreadChat(userID)+"");
		}
		}
		
	}
	
}
