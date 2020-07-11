package com.freeboard.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dao.FreeboardDAO;
import com.board.dto.FreeboardDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sumhan.dto.SunhansVO;

@WebServlet("/freeboardupdate.do")
public class FreeboardUpdateController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "freeboard/freeboardUpdate.jsp";
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		String paging = request.getParameter("page");
		
		FreeboardDAO dao = FreeboardDAO.getInstance();
		FreeboardDTO dto = dao.detail(bno);
		
		request.setAttribute("dto", dto);
		request.setAttribute("paging", paging);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("loginUserID");
		SunhansVO vo = (SunhansVO) session.getAttribute("loginUser");
		String name = vo.getName();
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		String paging = request.getParameter("page");

		FreeboardDAO dao = FreeboardDAO.getInstance();
		FreeboardDTO dto = dao.detail(bno); 

		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		if(dto.getId().equals(id)) {
			dto.setBno(bno);
			dto.setSubject(subject);
			dto.setContent(content);
			dto.setId(id);
			dto.setName(name);
			
			int result = dao.update(dto);
			
			if (result == 1) {
				response.sendRedirect("/SunhanWeb/freeboarddetail.do?bno=" + bno + "&page=" + paging);

			} else {
				response.sendRedirect("/SunhanWeb/freeboarddetail.do?bno=" + bno + "&page=" + paging);
			}
			
			request.setAttribute("paging", paging);
		}
		
	}

}
