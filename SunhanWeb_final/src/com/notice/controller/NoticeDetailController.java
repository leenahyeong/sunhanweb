package com.notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.NoticeDAO;
import com.board.dto.NoticeDTO;

@WebServlet("/noticedetail.do")
public class NoticeDetailController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String url = "notice/noticeDetail.jsp";

		int bno = Integer.parseInt(request.getParameter("bno"));
		String paging = request.getParameter("page");
		String option = request.getParameter("option");
		String keyword = request.getParameter("keyword");

		NoticeDAO dao = NoticeDAO.getInstance();
		NoticeDTO dto = null;
		NoticeDTO p_dto = null;
		NoticeDTO n_dto = null;

		// 쿠키값 가져옴
		Cookie[] cookies = request.getCookies();
		// 비교를 위한 새로운 쿠키
		Cookie viewCookie = null;

		// 쿠키가 있을 경우
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				// cookie의 name이 기존 쿠키 이름과 같으면 viewCookie에 넣어줌
				if (cookies[i].getName().equals("cookie" + bno)) {
					viewCookie = cookies[i];
				}
			}
		}

		// 만약 viewCookie가 null일 경우 쿠키를 생성해서 조회수 증가 로직을 처리
		if (viewCookie == null) {
			System.out.println("cookie 없음");

			// 쿠키 생성(이름,값)
			Cookie newCookie = new Cookie("cookie" + bno, "|" + bno + "|");

			// 쿠키 유효시간 (1일)
			newCookie.setMaxAge(60 * 60 * 24);

			// 응답헤더에 쿠키 추가
			response.addCookie(newCookie);

			// 조회수 증가 SQL > UPDATE notice SET hit=hit+1 WHERE bno=?
			int result = dao.hit(bno);

			if (result > 0) {
				System.out.println("조회수 증가");
			} else {
				System.out.println("조회수 증가 에러");
			}
		}
		// viewCookie가 null이 아닐경우 쿠키가 있으므로 조회수 증가 로직을 처리하지 않음.
		else {
			System.out.println("cookie 있음");

			// 쿠키 값 받아옴.
			String value = viewCookie.getValue();

			System.out.println("cookie 값 : " + value);
		}

		try {
			dto = dao.detail(bno);
			p_dto = dao.prev(bno);
			n_dto = dao.next(bno);

		} catch (Exception e) {
			e.printStackTrace();
		}
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
