package com.magicl.controller;

import java.awt.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
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
import com.sumhan.dao.SunhansDAO;
import com.sumhan.dto.SunhansVO;

import file.FileDAO;
import storefile.storefileDAO;

@WebServlet("/myStoreEdit.do")
public class myStoreEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String profile ="store/default.png";
		String realfile="store/";
		
		int storeresult=0;
		int storeDr=0;
		
		storefileDAO File= storefileDAO.getInstance();
		StoreDAO storeDAO= StoreDAO.getInstance();

		HttpSession session = request.getSession();
		SunhansVO emp = (SunhansVO) session.getAttribute("loginUser");
		String userid = (String) emp.getId();

		String stofileDr = File.Search(userid);
		storeDr = storeDAO.StoreCheck(userid);
		if(stofileDr==null)
		{
			storeresult=1;
		}
		realfile+=stofileDr;
		
		StoreVO vo = storeDAO.getStore(userid);
		
		if(storeDr == 1) {
			String open = vo.getOpentime();
			String close = vo.getClosetime();

			HashMap<String, String> time = new HashMap<String, String>();
			time.put("open1", open.substring(0,2));
			time.put("open2", open.substring(2,4));
			time.put("close1", close.substring(0,2));
			time.put("close2", close.substring(2,4));
			request.setAttribute("vo", vo);
			request.setAttribute("time", time);
		}
		
		session.setAttribute("realstorefile", realfile);
		session.setAttribute("storefile", profile);
		session.setAttribute("storesult", storeresult);
		request.setAttribute("storeDr", storeDr);
		
		String url="mystoreedit.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);	
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		int storeresult=Sdao.insertStore(Sto);

		session.setAttribute("loginStore",Sto);
		session.setAttribute("storech", storeresult);
		
		String url;
		
		if(storeresult == 1) {
			url="mypage.jsp";
		}
		else { // �벑濡앹떎�뙣
			url="myStoreEdit.do";
		}
		
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);	
	}

}
