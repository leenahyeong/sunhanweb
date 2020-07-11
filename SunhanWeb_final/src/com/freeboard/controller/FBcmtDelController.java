package com.freeboard.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dao.FreeboardDAO;
import com.board.dto.FBcmtDTO;


@WebServlet("/fbcommentdelete.do")
public class FBcmtDelController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cno = Integer.parseInt(request.getParameter("cmt_cno"));
		
		HttpSession session = request.getSession();
		String sessionID = (String) session.getAttribute("loginUserID");
		
		FreeboardDAO dao = FreeboardDAO.getInstance();
		FBcmtDTO dto = dao.cmt_detail(cno);
		
		if(dto.getCmt_id().equals(sessionID)) {
			dao.cmt_delete(cno);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("1");
			out.close();
		}
		
	}

}
