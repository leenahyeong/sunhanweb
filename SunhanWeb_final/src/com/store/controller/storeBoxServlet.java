package com.store.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chat.ChatDAO;
import com.chat.ChatDTO;
import com.store.dao.StoreDAO;
import com.store.dto.StoreVO;
import com.store.dto.StroeVOS;

/**
 * Servlet implementation class storeBoxServlet
 */
@WebServlet("/storeBox")
public class storeBoxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	
		String area= request.getParameter("area");
		
		if(area==null || area.equals(""))
		{
			response.getWriter().write(" ");
		}
		try{
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(getStore(area));
		}catch (Exception e) {
			response.getWriter().write("");
		}
		
	}
	public String getStore(String area) throws SQLException
	{
		StringBuffer result =new StringBuffer("");
		result.append("{\"result\":[");
		
		ArrayList<StroeVOS> StoreList=new ArrayList<>();

		StoreDAO stroeDAO =new StoreDAO();
		StoreList =stroeDAO.selectAllStores(area);
	
		if(StoreList.isEmpty())
			{
			System.out.println("매장이없음");
			return " ";
			}
		else{
			System.out.println("매장있음");
				
		}
		
		for(int i=0; i<StoreList.size(); i++)
		{
			result.append("[{\"value\":\""+ StoreList.get(i).getShopname()+"\"},");
			result.append("{\"value\":\""+ StoreList.get(i).getTopmessage()+"\"},");
			result.append("{\"value\":\""+ StoreList.get(i).getStorePhone()+"\"},");
			result.append("{\"value\":\""+ StoreList.get(i).getArea()+"\"},");
			result.append("{\"value\":\""+ StoreList.get(i).getFileRealName()+"\"},");			
			result.append("{\"value\":\""+ StoreList.get(i).getUserid()+"\"}]");
			if(i !=StoreList.size()-1) result.append(",");
		}
		result.append("], \"last\":\""+StoreList.get(StoreList.size()-1).getArea()+"\"}");
		return result.toString();
	}
}
