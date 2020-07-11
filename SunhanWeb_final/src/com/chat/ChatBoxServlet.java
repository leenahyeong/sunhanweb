package com.chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import file.FileDAO;

/**
 * Servlet implementation class ChatBoxServlet
 */
@WebServlet("/chatBox")
public class ChatBoxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		System.out.println("123");
		String userID= request.getParameter("userID");
		
		if(userID==null || userID.equals(""))
		{
			response.getWriter().write(" ");
		}else{
			try{
				userID =URLDecoder.decode(userID,"UTF-8");
				response.getWriter().write(getBox(userID));
			}catch (Exception e) {
				response.getWriter().write("");
			}
			
		}
	}
	public String getBox(String userID) throws SQLException
	{
		StringBuffer result =new StringBuffer("");
		result.append("{\"result\":[");
		
		ChatDAO chatDAO =new ChatDAO();
		ArrayList<ChatDTO> chatList=new ArrayList<>();
		
		chatList =chatDAO.getBox(userID);
		System.out.println(chatList.toString()+"123");
		if(chatList.toString().equals("[]")||chatList.isEmpty())
			{
			result.append("[{\"value\":\"\"},");
			result.append("{\"value\":\"\"}]");
			result.append("], \"last\":\"\"}");
			return result.toString();
			}
		
		for(int i=0; i<chatList.size(); i++)
		{
			
			FileDAO File= new FileDAO();
			String toprofile = File.Search(chatList.get(i).getToID());
			if(toprofile==null){
				toprofile="resoures/images/userEX.png";
			}
			else{
			toprofile="profile/"+toprofile;
			}
			System.out.println(toprofile);
			result.append("[{\"value\":\""+ chatList.get(i).getFromID()+"\"},");
			result.append("{\"value\":\""+ chatList.get(i).getToID()+"\"},");
			result.append("{\"value\":\""+ chatList.get(i).getChatContent()+"\"},");
			result.append("{\"value\":\""+ chatList.get(i).getChatTime()+"\"},");
			result.append("{\"value\":\""+ toprofile+"\"}]");
			if(i !=chatList.size()-1) result.append(",");
			
		}
		result.append("], \"last\":\""+chatList.get(chatList.size()-1).getChatID()+"\"}");
		return result.toString();
	}
}
