package com.android.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 * Servlet implementation class chatlistServlet
 */
@WebServlet("/androidchatlist.do")
public class androidchatlistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public androidchatlistServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter outs =  response.getWriter();
	
		String fromID= new String(request.getParameter("UserID"));
		String toID= new String(request.getParameter("toID"));


		JSONObject Chating = new JSONObject();
		JSONArray arr = new JSONArray();
		
		String result = null;
		if(fromID==null || fromID.equals("")||toID ==null || toID.equals("")){return;}
		else{try {
				ChatDAO chatDAO =new ChatDAO();
				ArrayList<ChatDTO> chatList=new ArrayList<>();
				
				chatList =chatDAO.androidgetChatListByID(fromID, toID);
				
				if(chatList.isEmpty()){System.out.println("채팅기록이없음+getid"+fromID+":"+toID);}
				
				String myfile="profile/";		String tofile="profile/";
				
				try{
					JSONObject obj = new JSONObject();
					
					FileDAO File= new FileDAO();
					String myprofile = File.Search(fromID);
					String toprofile = File.Search(toID);
					if(myprofile==null)
					{
						myprofile="resoures/images/userEX.png";
						obj.put("myprofile", myprofile);
					}
					else{
						myfile="profile/"+myprofile;obj.put("myprofile", myfile);
					}
					if(toprofile==null){
						toprofile="resoures/images/userEX.png";
						obj.put("toprofile", toprofile);
					}
					else{
						tofile="profile/"+toprofile;obj.put("toprofile", tofile);
					}
					
					arr.put(obj);
					
				}catch (Exception e) {}
				
				for(int i=0; i<chatList.size(); i++)
				{
					JSONObject obj = new JSONObject();
					obj.put("userid",chatList.get(i).getFromID());
					obj.put("toid",chatList.get(i).getToID());
					obj.put("chatcontent",chatList.get(i).getChatContent());
					obj.put("chattime",chatList.get(i).getChatTime());
					
					arr.put(obj);
				}
				
				chatDAO.readChat(fromID, toID);

				
			} catch (SQLException e) {outs.print("불러올수가없습니다.");}
		}
		Chating.put("chating", arr);
		outs.print(Chating.toString());
	 }
	}
	