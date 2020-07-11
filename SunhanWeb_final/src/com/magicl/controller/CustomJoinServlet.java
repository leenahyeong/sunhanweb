package com.magicl.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sumhan.dao.SunhansDAO;
import com.sumhan.dto.SunhansVO;

/**
 * Servlet implementation class CustomJoinServlet
 */
@WebServlet("/regis.do")
public class CustomJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomJoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub1
		{	
		    response.sendRedirect("register.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated me
		request.setCharacterEncoding("UTF-8");
		
		SunhansVO sunhan =new SunhansVO();
		String userid = request.getParameter("userid");
		String name = request.getParameter("name");
		String pwd = request.getParameter("pass");
		String addr = request.getParameter("addr");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String admin = request.getParameter("admin");
	
		sunhan.setId(userid);
		sunhan.setName(name);
		sunhan.setPass(pwd);
		sunhan.setAddr(addr);
		sunhan.setPhone(phone);
		sunhan.setEmail(email);
		sunhan.setAdmin(Integer.parseInt(admin));
		SunhansDAO sunhansDAO =SunhansDAO.getInstance();
		
		String result2="1";
		int result = sunhansDAO.insertMember(sunhan);
		HttpSession session = request.getSession();
		
		if (result == 1) {
			session.setAttribute("userid", sunhan.getId());
			session.setAttribute("regisresult",result2);
			request.setAttribute("message", "회원 가입에 성공했습니다.");
			System.out.println(name);
		} else {
			session.setAttribute("regisresult",result2);
			request.setAttribute("message", "회원 가입에 실패했습니다.");
		}
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
	}

}
