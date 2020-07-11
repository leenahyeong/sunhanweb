package com.magicl.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.dao.StoreDAO;
import com.store.dto.StoreVO;
import com.sumhan.dto.SunhansVO;

/**
 * Servlet implementation class myStoreUpdate
 */
@WebServlet("/myStoreUpdate.do")
public class myStoreUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		SunhansVO emp = (SunhansVO) session.getAttribute("loginUser");
	
		String storename= request.getParameter("storename");
		String STaddr= request.getParameter("STaddr");
		String STcomment= request.getParameter("STcomment");
		String STinfo= request.getParameter("STinfo");
		String STtop= request.getParameter("STtop");
		String STprice= request.getParameter("STprice");
		String STphone= request.getParameter("STphone");
		String food= request.getParameter("food");
		String area= request.getParameter("area")+request.getParameter("area2");
		String userid = (String) emp.getId();
		String opentime = request.getParameter("open1")+request.getParameter("open2");		
		String closetime = request.getParameter("close1")+request.getParameter("close2");
		
		 Date today = new Date();
		        
		 SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		 String dates= date.format(today);
		 
		 SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");
	        
	     SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");
	        
	     java.util.Date tempDate = null;
	        
	        try {
	            tempDate = beforeFormat.parse(dates);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        
	        String transDate = afterFormat.format(tempDate);
	        
	        java.sql.Date d = java.sql.Date.valueOf(dates);
	        
		StoreVO Sto=new StoreVO(storename,STtop,STaddr,area,STcomment,STinfo,food,STprice,d,userid,opentime,closetime,STphone);
		
		StoreDAO Sdao=StoreDAO.getInstance();
		int storeresult=Sdao.updateStore(Sto);

		session.setAttribute("loginStore",Sto);
		session.setAttribute("storech", storeresult);
		
		String url="mypage.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);	
  }

}
