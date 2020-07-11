package com.magicl.controller;

import java.io.IOException;
import java.io.PrintWriter;

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
 * Servlet implementation class MypageServlet
 */
@WebServlet("/chpass.do")
public class ChpassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChpassServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();	

		SunhansVO loginUser=(SunhansVO) session.getAttribute("loginUser");
		
	
		loginUser.setPass(request.getParameter("password"));
		
		SunhansDAO DAO =SunhansDAO.getInstance();
		int reslut=DAO.updatePass(loginUser);

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
