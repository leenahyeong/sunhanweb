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
 * Servlet implementation class ChatSubmitServelt
 */
@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String listType = request.getParameter("listType");
		
		
		if(fromID==null || fromID.equals("")||toID ==null || toID.equals("")||
				listType==null|| listType.equals(""))
		{
			response.getWriter().write("0");
		}
		else if(listType.equals("ten"))
			try {
				response.getWriter().write(getTen(fromID,toID));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		else{
			try
			{
				response.getWriter().write(getID(fromID,toID,listType));
			}catch (Exception e) {
				response.getWriter().write("");
			}
		}
	}
		public String getTen(String fromID,String toID) throws SQLException
		{
			StringBuffer result =new StringBuffer("");
			result.append("{\"result\":[");
			ArrayList<ChatDTO> chatList=new ArrayList<>();
			
			ChatDAO chatDAO =new ChatDAO();
			chatList =chatDAO.getChatListByRecent(fromID, toID,30);
			if(chatList.isEmpty()) 
				{
					return " ";
				}
			for(int i=0; i<chatList.size(); i++)
			{
				result.append("[{\"value\":\""+ chatList.get(i).getFromID()+"\"},");
				result.append("{\"value\":\""+ chatList.get(i).getToID()+"\"},");
				result.append("{\"value\":\""+ chatList.get(i).getChatContent()+"\"},");
				result.append("{\"value\":\""+ chatList.get(i).getChatTime()+"\"}]");
				if(i !=chatList.size()-1) result.append(",");
				
				
			}
			result.append("], \"last\":\""+chatList.get(chatList.size()-1).getChatID()+"\"}");
			chatDAO.readChat(fromID, toID);
			return result.toString();
		}
		public String getID(String fromID,String toID,String chatID) throws SQLException
		{
			StringBuffer result =new StringBuffer("");
			result.append("{\"result\":[");
			
			ChatDAO chatDAO =new ChatDAO();
			ArrayList<ChatDTO> chatList=new ArrayList<>();
			
			chatList =chatDAO.getChatListByID(fromID, toID, chatID);
			
		
			FileDAO File= new FileDAO();
			String myprofile= File.Search(fromID);
			String toprofile = File.Search(toID);
			
			if(myprofile==null)
			{
				myprofile="resoures/images/userEX.png";
			}
			else{
				myprofile="profile/"+myprofile;
			}
			if(toprofile==null){
				toprofile="resoures/images/userEX.png";
			}
			else{
				toprofile="profile/"+toprofile;
			}
			
			
			if(chatList.isEmpty())
				{
				return " ";
				}
			
			for(int i=0; i<chatList.size(); i++)
			{
				result.append("[{\"value\":\""+ chatList.get(i).getFromID()+"\"},");
				result.append("{\"value\":\""+ chatList.get(i).getToID()+"\"},");
				result.append("{\"value\":\""+ chatList.get(i).getChatContent()+"\"},");
				result.append("{\"value\":\""+ chatList.get(i).getChatTime()+"\"},");
				result.append("{\"value\":\""+ myprofile+"\"},");
				result.append("{\"value\":\""+ toprofile+"\"}]");
				if(i !=chatList.size()-1) result.append(",");
				
				
			}
			result.append("], \"last\":\""+chatList.get(chatList.size()-1).getChatID()+"\"}");
			chatDAO.readChat(fromID, toID);
			return result.toString();
		}
	}

