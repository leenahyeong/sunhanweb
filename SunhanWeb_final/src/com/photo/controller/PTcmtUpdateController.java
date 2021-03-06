package com.photo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dao.PhotoDAO;
import com.board.dto.PTcmtDTO;
import com.sumhan.dto.SunhansVO;


@WebServlet("/ptcommentupdate.do")
public class PTcmtUpdateController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cno = Integer.parseInt(request.getParameter("cmt_cno"));
		
		PhotoDAO dao = PhotoDAO.getInstance();
		PTcmtDTO dto = dao.cmt_detail(cno);
		
		request.setAttribute("comment", dto);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("photo/photocmtupdate.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		// 댓글 no
		int cno = Integer.parseInt(request.getParameter("cmt_cno"));
		String sessionID = (String) session.getAttribute("loginUserID");
		SunhansVO vo = (SunhansVO) session.getAttribute("loginUser");
		String sessionNAME = vo.getName();
		String content = request.getParameter("update_cmt_content");
		
		PhotoDAO dao = PhotoDAO.getInstance();
		PTcmtDTO dto = dao.cmt_detail(cno);
		
		// 디비에 저장된 회원과 현재 사용자가 같을때
		if(dto.getCmt_id().equals(sessionID)) {	
			dto.setCmt_name(sessionNAME);
			dto.setCmt_content(content);
			
			int result = dao.cmt_update(dto);
			
			if(result == 1) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("1");
				out.close();
			}
		}
	}

}
