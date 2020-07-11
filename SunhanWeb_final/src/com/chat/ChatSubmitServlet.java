package com.chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChatSubmitServelt
 */
@WebServlet("/ChatSubmitServlet")
public class ChatSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String chatContent = request.getParameter("chatContent");
		if(fromID==null || fromID.equals("")||toID ==null || toID.equals("")||chatContent==null|| chatContent.equals(""))
		{
			response.getWriter().write("0");
		}
		else{
			fromID = URLDecoder.decode(fromID,"UTF-8");
			toID = URLDecoder.decode(toID,"UTF-8");
			chatContent = URLDecoder.decode(chatContent,"UTF-8");
			try {
				response.getWriter().write(new ChatDAO().submit(fromID, toID, chatContent)+ "");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no");
			}
		}
	}

}
