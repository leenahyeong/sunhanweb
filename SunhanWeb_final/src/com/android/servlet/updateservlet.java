package com.android.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.dao.StoreDAO;
import com.store.dto.StoreVO;

/**
 * Servlet implementation class updateservlet
 */
@WebServlet("/androidUpdateStore.do")
public class updateservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); response.setContentType("text/html; charset=UTF-8");
		
		String shopname = new String(request.getParameter("shopname"));
		String topmessage = new String(request.getParameter("topmessage"));
		String addr = new String(request.getParameter("addr"));
		String area = new String(request.getParameter("area"));
		String comments = new String(request.getParameter("comments"));
		String information = new String(request.getParameter("information"));
		String food = new String(request.getParameter("food"));
		String price = new String(request.getParameter("price"));
		String userid = new String(request.getParameter("userid"));
		String opentime = new String(request.getParameter("opentime"));
		String closetime = new String(request.getParameter("closetime"));
		String StorePhone = new String(request.getParameter("StorePhone"));
		
		StoreDAO stD=new StoreDAO();		
		StoreVO svo=new StoreVO(shopname,topmessage,addr,area,comments,information,food,price,userid,opentime,closetime,StorePhone);
		

		PrintWriter out =  response.getWriter();
		
		
		int result =stD.StoreCheck(userid);
		
		if(result==1)
		{
			stD.updateStore(svo);
			out.print("2");
		}
		else{
			stD.insertStore(svo);
			out.print("1");
		}
	}
}
