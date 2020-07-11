package com.chat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


public class ChatDAO {
	public ChatDAO(){
		
	}
	private static ChatDAO instance =new ChatDAO();
	
	public static ChatDAO getInstance(){
		return instance;
	}
	public Connection getConnections() throws Exception{
	
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String dbURL = "jdbc:mysql://3.12.173.221:3306/projectsd?&characterEncoding=UTF-8";
			String dbID = "mysqluser";
			String dbPW = "user123";
        
			Connection conn =  DriverManager.getConnection(dbURL, dbID, dbPW);
        
			
			if(conn!=null)
			{	
				//System.out.println("�곌껐 : " + conn);
			    //System.out.println("占쎈염野�怨���癰�占�(toString) : " + conn.toString());

			return conn;
			}
		}
		catch(ClassNotFoundException e) {
			//System.out.println("占쎈굡占쎌�わ옙��甕곤옙 嚥≪��諭� 占쎈��占쎈��");
			e.printStackTrace();
		}
		catch(SQLException e) {
			//System.out.println("DB 占쎌��占쎈�� 占쎈��占쎈��");
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<ChatDTO> getChatListByID(String fromID,String toID,String chatID) throws SQLException
	{
		ArrayList<ChatDTO> chatList=new ArrayList<>();
		
		String SQL="SELECT * from CHAT where ((fromID = ? AND toID=?) OR (fromID = ? AND toID = ?)) AND chatID>? ORDER BY chatTime";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnections();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,fromID);
			pstmt.setString(2,toID);
			pstmt.setString(3,toID);
			pstmt.setString(4,fromID);
			pstmt.setInt(5,Integer.parseInt(chatID));
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				ChatDTO chat=new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(rs.getString("fromID").replace(" ", "&nbsp;").replace("<", "&lt").replace(">","&gt").replace("\n","<br>"));
				chat.setToID(rs.getString("toID").replace(" ", "&nbsp;").replace("<", "&lt").replace(">","&gt").replace("\n","<br>"));
				chat.setChatContent(rs.getString("chatContent").replace(" ", "&nbsp;").replace("<", "&lt").replace(">","&gt").replace("\n","<br>"));
				int chatTime=Integer.parseInt(rs.getString("chatTIme").substring(11,13));
				System.out.println(chatTime+"챗타임");
				String timeType ="오전";
				if(chatTime>12){
					timeType="오후";
					chatTime-=12;
				}							   
				chat.setChatTime(rs.getString("chatTime").substring(0,11)+" "+ timeType+ " " + chatTime +":"+rs.getString("chatTime").substring(14,16)+" ");
			    
				
				chatList.add(chat);
			}
			return chatList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn!=null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	
	public ArrayList<ChatDTO> getChatListByRecent(String fromID,String toID,int number) throws SQLException
	{
		ArrayList<ChatDTO> chatList=new ArrayList<>();
		
		String SQL="SELECT * from CHAT where ((fromID = ? AND toID=?) OR (fromID = ? AND toID = ?)) AND chatID > (SELECT MAX(chatID) - ? FROM CHAT WHERE (fromID = ? AND toID =?) OR (fromID = ? AND toID = ?)) ORDER BY chatTime";
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		try {
			conn = getConnections();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1,fromID);
			pstmt.setString(2,toID);
			pstmt.setString(3,toID);
			pstmt.setString(4,fromID);
			pstmt.setInt(5,number);
			pstmt.setString(6,fromID);
			pstmt.setString(7,toID);
			pstmt.setString(8,toID);
			pstmt.setString(9,fromID);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				ChatDTO chat=new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(rs.getString("fromID").replace(" ", "&nbsp;").replace("<", "&lt").replace(">","&gt").replace("\n","<br>"));
				chat.setToID(rs.getString("toID").replace(" ", "&nbsp;").replace("<", "&lt").replace(">","&gt").replace("\n","<br>"));
				chat.setChatContent(rs.getString("chatContent").replace(" ", "&nbsp;").replace("<", "&lt").replace(">","&gt").replace("\n","<br>"));
				int chatTime=Integer.parseInt(rs.getString("chatTIme").substring(11,13));
				System.out.println(chatTime+"챗타임");
				String timeType ="오후";
				if(chatTime>12){
					timeType="오전";
					chatTime-=12;
				}
				chat.setChatTime(rs.getString("chatTIme").substring(0,11)+" "+ timeType+ " " + chatTime +":"+rs.getString("chatTime").substring(14,16)+"");
			    chatList.add(chat);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn!=null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	
	public int submit(String fromID,String toID,String chatContent) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		SimpleDateFormat stringfm = new SimpleDateFormat("yyyy-MM-dd aa hh:mm"); // 문자열 -> date
		
		java.sql.Timestamp time = java.sql.Timestamp.valueOf(LocalDateTime.now());
		System.out.println(time);
		
		java.text.SimpleDateFormat sdf = 
		     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String currentTime = sdf.format(time);
		System.out.println(currentTime);
		
		String SQL="INSERT INTO CHAT VALUES (null, ?, ?, ?,now(), 0)";
		try {
			conn = getConnections();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1,fromID);
			pstmt.setString(2,toID);
			pstmt.setString(3,chatContent);
			
			return pstmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn!=null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	public int readChat(String fromID,String toID)
	{
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;

		
		String SQL="UPDATE CHAT SET chatRead=1 where(fromID= ? AND toID= ? )";
	
		try {
			conn = getConnections();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1,toID);
			pstmt.setString(2,fromID);

			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn!=null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	public int getAllUnreadChat(String userID)
	{
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;

		
		String SQL="SELECT COUNT(chatID) FROM CHAT WHERE toID=? AND chatRead=0";
	
		try {
			conn = getConnections();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1,userID);
			rs=pstmt.executeQuery();
			
			if(rs.next())
			{
				return rs.getInt("COUNT(chatID)");
			}
			return 0;
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn!=null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	public ArrayList<ChatDTO> getBox(String userID) throws SQLException
	{
		ArrayList<ChatDTO> chatList=new ArrayList<>();
		
		String SQL="SELECT * FROM CHAT where chatID in (select max(chatID) from CHAT where toID=? OR fromID =? group by fromID, toID)";
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		try {
			conn = getConnections();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1,userID);
			pstmt.setString(2,userID);
			rs=pstmt.executeQuery();
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
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn!=null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	
	//////////////////안드로이드
	
	public ArrayList<ChatDTO> AndroidgetBox(String userID) throws SQLException
	{
		ArrayList<ChatDTO> chatList=new ArrayList<>();
		
		String SQL="SELECT * FROM CHAT where chatID in (select max(chatID) from CHAT where toID=? OR fromID =? group by fromID, toID)";
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		try {
			conn = getConnections();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1,userID);
			pstmt.setString(2,userID);
			rs=pstmt.executeQuery();
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
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn!=null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	public ArrayList<ChatDTO> androidgetChatListByID(String fromID,String toID) throws SQLException
	{
		ArrayList<ChatDTO> chatList=new ArrayList<>();
		
		String SQL="SELECT * from CHAT where ((fromID = ? AND toID=?) OR (fromID = ? AND toID = ?)) ORDER BY chatTime";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnections();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,fromID);
			pstmt.setString(2,toID);
			pstmt.setString(3,toID);
			pstmt.setString(4,fromID);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				ChatDTO chat=new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(rs.getString("fromID").replace(" ", "&nbsp;").replace("<", "&lt").replace(">","&gt").replace("\n","<br>"));
				chat.setToID(rs.getString("toID").replace(" ", "&nbsp;").replace("<", "&lt").replace(">","&gt").replace("\n","<br>"));
				chat.setChatContent(rs.getString("chatContent").replace(" ", "&nbsp;").replace("<", "&lt").replace(">","&gt").replace("\n","<br>"));
				int chatTime=Integer.parseInt(rs.getString("chatTIme").substring(11,13));
				String timeType ="오후";
				if(chatTime>12){
					timeType="오전";
					chatTime-=12;
				}							   
				chat.setChatTime(rs.getString("chatTime").substring(0,11)+" "+ timeType+ " " + chatTime +":"+rs.getString("chatTime").substring(14,16)+" ");
			    
				
				chatList.add(chat);
				System.out.println("11111111111111111"+chatList.toString());
			}
			return chatList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn!=null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
}
