package com.freeboard.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

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

@WebServlet("/freeboardadd.do")
public class FreeboardAddController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "freeboard/freeboardAdd.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("loginUserID");
		
		/*
		SunhansVO vo = (SunhansVO) session.getAttribute("loginUservo");
		String name = vo.getName();
		*/
		String name = (String) session.getAttribute("loginUserName");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		FreeboardDAO dao = FreeboardDAO.getInstance();
		FreeboardDTO dto = new FreeboardDTO();
		
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setId(id);
		dto.setName(name);

		int result = dao.insert(dto);
		
		if (result == 1) {
			response.sendRedirect("/SunhanWeb/freeboard.do");
		} else {
			response.sendRedirect("/SunhanWeb/freeboardadd.do");
		}

	}

}
