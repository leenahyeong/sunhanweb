package com.magicl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sumhan.dao.SunhansDAO;

/**
 * Servlet implementation class MemberIdCHServlets
 */
@WebServlet("/sunhansIdCH.do")
public class sunhansIdCHServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sunhansIdCHServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");	
		response.setCharacterEncoding("UTF-8");
		String userid = request.getParameter("userid");
		
		
		SunhansDAO mDao = SunhansDAO.getInstance();
		int result = mDao.confirmID(userid);
		request.setAttribute("userid", userid);
		request.setAttribute("result", result);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("idcheck.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
