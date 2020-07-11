package com.magicl.controller;

import java.io.IOException;

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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd =request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("userid");
		String pwd = request.getParameter("pass");
		String realfile="profile/";
		String profile ="http://ssl.gstatic.com/accounts/ui/avatar_2x.png";
		
		int proresult=0;
		
        
		String url=null;
		SunhansDAO empDao = new SunhansDAO();
		StoreDAO stoDao = new StoreDAO();
		int result = empDao.userCheck(id, pwd);
		int storesult= stoDao.StoreCheck(id);

		System.out.println(storesult);
		
		if (result == 1 || result==2) 
		{
			HttpSession session = request.getSession();
			
			FileDAO File= new FileDAO();
			String profileDr = File.Search(id);
			if(profileDr==null)
			{
				proresult=1;
			}
			realfile+=profileDr;
			
			session.setAttribute("realprofile", realfile);
			session.setAttribute("profile", profile);
			session.setAttribute("proresult", proresult);
			if(storesult==1)
			{
				StoreVO sto=new StoreVO();
				sto=stoDao.getStore(id);
				session.setAttribute("loginStore", sto);
				session.setAttribute("storech", storesult);
			}
			SunhansVO emp = empDao.getMember(id);

			session.setAttribute("loginUserName", emp.getName());
			session.setAttribute("loginUserAdmin", emp.getAdmin());
			
			session.setAttribute("loginUser", emp);
			session.setAttribute("loginUserID", id);
			session.setAttribute("result", result);
			url = "/";
			
			session.setMaxInactiveInterval(1800);
		} 
		else{
			url="login.jsp";
			if (result == 0)
			{
				request.setAttribute("message", "비밀번호가 일치하지 않습니다.");
			}
			else {
				request.setAttribute("message", "계정 정보를 찾을수 없습니다.");
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
		
	}

}
