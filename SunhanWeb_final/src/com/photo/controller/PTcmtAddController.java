package com.photo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dao.PhotoDAO;
import com.board.dto.PTcmtDTO;

@WebServlet("/ptcommentadd.do")
public class PTcmtAddController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		PhotoDAO dao = PhotoDAO.getInstance();
		PTcmtDTO dto = new PTcmtDTO();
		
		HttpSession session = request.getSession();
		
		String sessionID = (String) session.getAttribute("loginUserID");
		int bno = Integer.parseInt(request.getParameter("cmt_bno"));
		
		String id = request.getParameter("cmt_id");
		String name = request.getParameter("cmt_name");
		String content = request.getParameter("cmt_content");
		
		if(id.equals(sessionID)) {
			dto.setCmt_bno(bno);
			dto.setCmt_id(id);
			dto.setCmt_name(name);
			dto.setCmt_content(content);
			
			int result = dao.cmt_insert(dto);
			
			if(result == 1) {
				 response.setContentType("text/html;charset=UTF-8");
				 PrintWriter out = response.getWriter();
				 out.println("1");
				 out.close();
			}
		}
	}

}
