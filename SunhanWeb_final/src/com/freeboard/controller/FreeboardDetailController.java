package com.freeboard.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.FreeboardDAO;
import com.board.dto.FreeboardDTO;

@WebServlet("/freeboarddetail.do")
public class FreeboardDetailController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String url = "freeboard/freeboardDetail.jsp";

		int bno = Integer.parseInt(request.getParameter("bno"));
		String paging = request.getParameter("page");
		String option = request.getParameter("option");
		String keyword = request.getParameter("keyword");

		FreeboardDAO dao = FreeboardDAO.getInstance();
		FreeboardDTO dto = null;
		FreeboardDTO p_dto = null;
		FreeboardDTO n_dto = null;

		Cookie[] cookies = request.getCookies();
		Cookie viewCookie = null;

		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {

				if (cookies[i].getName().equals("cookie" + bno)) {

					viewCookie = cookies[i];
				}
			}
		}

		if (viewCookie == null) {
			System.out.println("cookie ����");

			Cookie newCookie = new Cookie("cookie" + bno, "|" + bno + "|");

			response.addCookie(newCookie);

			int result = dao.hit(bno);

		}

		else {
			String value = viewCookie.getValue();

		}

		dto = dao.detail(bno);
		p_dto = dao.prev(bno);
		n_dto = dao.next(bno);

		request.setAttribute("dto", dto);
		request.setAttribute("paging", paging);
		request.setAttribute("option", option);
		request.setAttribute("keyword", keyword);
		request.setAttribute("p_dto", p_dto);
		request.setAttribute("n_dto", n_dto);

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
