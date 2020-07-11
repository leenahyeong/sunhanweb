package com.android.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.chat.ChatDAO;

@WebServlet("/androidchatSubmit.do")
public class androidchatSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public androidchatSubmitServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String chatContent = request.getParameter("chatContent");
		if(fromID==null || fromID.equals("")||toID ==null || toID.equals("")||chatContent==null|| chatContent.equals(""))
		{
			return;
		}
		else{
			fromID = URLDecoder.decode(fromID,"UTF-8");
			toID = URLDecoder.decode(toID,"UTF-8");
			chatContent = URLDecoder.decode(chatContent,"UTF-8");
			try {
				new ChatDAO().submit(fromID, toID, chatContent);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no");
			}
		}
	}
}
