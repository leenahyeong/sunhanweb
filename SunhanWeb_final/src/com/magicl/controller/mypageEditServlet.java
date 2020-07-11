package com.magicl.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sumhan.dto.SunhansVO;

import file.FileDAO;
import file.fileDTO;
/**
 * Servlet implementation class mypageEditServlet
 */
@WebServlet("/mypageEditServlet.do")
public class mypageEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public mypageEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String profile ="http://ssl.gstatic.com/accounts/ui/avatar_2x.png";
		String realfile="profile/";
		
		int proresult=0;
		
		FileDAO File= FileDAO.getInstance();//인스턴s스 호출 


		HttpSession session = request.getSession();
		SunhansVO emp = (SunhansVO) session.getAttribute("loginUser");
		String userid = (String) emp.getId();

		String profileDr = File.Search(userid);

		if(profileDr==null)
		{
			proresult=1;
		}
		realfile+=profileDr;
		
		session.setAttribute("realprofile", realfile);
		session.setAttribute("profile", profile);
		session.setAttribute("proresult", proresult);
		
		String url="mypageedit.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
