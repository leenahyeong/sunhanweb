package com.magicl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.reserve.dao.ReserveDAO;
import com.review.dao.ReviewDAO;
import com.sumhan.dao.SunhansDAO;
import com.sumhan.dto.SunhansVO;

/**
 * Servlet implementation class MypageServlet
 */
@WebServlet("/mypage.do")
public class MypageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MypageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session =request.getSession();
		SunhansVO emp=(SunhansVO)session.getAttribute("loginUser");

		if(emp!=null)
		{
			HashMap<String, Object> listObj = new HashMap<String, Object>();
			HashMap<String, Object> listObj2 = new HashMap<String, Object>();
			ReserveDAO dao = ReserveDAO.getInstance();
			ReviewDAO r_dao = ReviewDAO.getInstance();
			
			if(emp.getAdmin() == 1) { // 관리자
				listObj.put("rv_sno", emp.getId());
				listObj.put("status_option", "0");
				listObj.put("visit_option", "0");
				
				listObj2.put("userid", emp.getId());
				listObj2.put("admin", 1);
				
				int reserve_count = dao.SupportertotalCount(listObj);
				
				request.setAttribute("count", reserve_count);
			}
			else if(emp.getAdmin() == 0){ // 아동
				
				listObj.put("rv_userid", emp.getId());
				listObj.put("status_option", "0");
				listObj.put("visit_option", "0");
				
				listObj2.put("userid", emp.getId());
				listObj2.put("admin", 0);
				
				int reserve_count = dao.ChildtotalCount(listObj);
				request.setAttribute("count", reserve_count);
			}
			listObj2.put("start_date", "");
			listObj2.put("end_date", "");
			listObj2.put("search_userid", "");
			listObj2.put("review_no", 0);
			
			List<Integer> review_count = r_dao.reviewtotalCount(listObj2);
			
			request.setAttribute("review_count", review_count);
			
			
			String url="mypage.jsp";
			
			RequestDispatcher dispatcher =request.getRequestDispatcher(url);
			dispatcher.forward(request,response);	
		}
		else {
			response.sendRedirect("login.do");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();	

		SunhansVO loginUser=(SunhansVO) session.getAttribute("loginUser");

	
		loginUser.setName(request.getParameter("name"));
		loginUser.setPhone(request.getParameter("phone"));
		loginUser.setEmail(request.getParameter("s"));
		loginUser.setAddr(request.getParameter("addr"));
		
		SunhansDAO DAO =new SunhansDAO();
		int reslut=DAO.updateMember(loginUser);

		String url="mypageedit.jsp";
		if(reslut==1)
		{
			session.setAttribute("loginUser", loginUser);
		}
		else
		{
			url="mypageedit.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);		
	}

}
