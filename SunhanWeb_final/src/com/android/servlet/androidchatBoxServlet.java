package com.android.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.chat.ChatDAO;
import com.chat.ChatDTO;

import file.FileDAO;
import storefile.storefileDAO;

/**
 * Servlet implementation class chatBoxServlet
 */
@WebServlet("/androidchatBox.do")
public class androidchatBoxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public androidchatBoxServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter outs =  response.getWriter();
		
	  
		String userID= new String(request.getParameter("id"));
		
		ArrayList<ChatDTO> chatList=new ArrayList<>();
		

		System.out.println(chatList+"zz"+userID);
		
		if(userID==null || userID.equals(""))
		{
			return;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String dbURL = "*";
			String dbID = "*";
			String dbPW = "*";
			 
			Connection conn =  DriverManager.getConnection(dbURL, dbID, dbPW);
     
			ResultSet rs = null;
			PreparedStatement pstmt=null;
		
			String SQL="SELECT * FROM CHAT where chatID in (select max(chatID) from CHAT where toID=? OR fromID =? group by fromID, toID)";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,userID);
			pstmt.setString(2,userID);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				
				
				ChatDTO chat=new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(rs.getString("fromID").replace(" ", "&nbsp;").replace("<", "&lt").replace(">","&gt").replace("\n","<br>"));
				chat.setToID(rs.getString("toID").replace(" ", "&nbsp;").replace("<", "&lt").replace(">","&gt").replace("\n","<br>"));
				chat.setChatContent(rs.getString("chatContent").replace(" ", "&nbsp;").replace("<", "&lt").replace(">","&gt").replace("\n","<br>"));
				int chatTime=Integer.parseInt(rs.getString("chatTIme").substring(11,13));
				String timeType ="오전";
				if(chatTime>12){
					timeType="오후";
					chatTime-=12;
				}
				chat.setChatTime(rs.getString("chatTIme").substring(0,11)+" "+ timeType+ " " + chatTime +":"+rs.getString("chatTime").substring(14,16)+"");
			  
				FileDAO File= new FileDAO();
				String toprofile = File.Search(rs.getString("toID"));
				if(toprofile==null){
					toprofile="resoures/images/userEX.png";
				}
				else{
					toprofile="profile/"+toprofile;
				}
				String fromprofile = File.Search(rs.getString("fromID"));
				if(fromprofile==null){
					fromprofile="resoures/images/userEX.png";
				}
				else{
					fromprofile="profile/"+fromprofile;
				}
				chat.setChatprofile(toprofile);
				chat.setTochatprofile(fromprofile);
				chatList.add(chat);
			}
			for(int i=0; i<chatList.size(); i++)
			{
				ChatDTO x=chatList.get(i);
				for(int j=0; j<chatList.size(); j++)
				{
					ChatDTO y= chatList.get(j);
					if(x.getFromID().equals(y.getToID())&&x.getToID().equals(y.getFromID()))
					{
						if(x.getChatID()<y.getChatID())
						{
							chatList.remove(x);
							i--;
							break;
						}else {
							chatList.remove(y);
							j--;
						}
					}
				}
			}
			storefileDAO storefile=new storefileDAO();
			
			JSONObject Chat = new JSONObject();
			JSONArray arr = new JSONArray();
			
			System.out.println(chatList.size());
			for(int i=0; i<chatList.size(); i++)
			{
				  JSONObject obj = new JSONObject();
				  ChatDTO element=chatList.get(i);	
				  obj.put("ChatID",element.getChatID());
				  obj.put("FromID",element.getFromID());
				  obj.put("ToID",element.getToID());
				  obj.put("ChatContent",element.getChatContent());
				  obj.put("ChatTime",element.getChatTime()); 
				  obj.put("profile", element.getChatprofile());
				  obj.put("toprofile", element.getTochatprofile());
				  //String storefilename=storefile.AndroidgetOneImageGet(element.getToID());
				  //obj.put("FromImage",storefilename);
				  
				  arr.put(obj);
	        }
			Chat.put("chatlist", arr);
			outs.print(Chat);
		}
		catch(ClassNotFoundException e) 
		{
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
