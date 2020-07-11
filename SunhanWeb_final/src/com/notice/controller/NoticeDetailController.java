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

		// ��ȸ��
		Cookie[] cookies = request.getCookies();
		// ���ϱ� ���� ���ο� ��Ű
		Cookie viewCookie = null;

		// ��Ű�� ���� ���
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				// Cookie�� name�� cookie + reviewNo�� ��ġ�ϴ� ��Ű�� viewCookie�� �־���
				if (cookies[i].getName().equals("cookie" + bno)) {
					viewCookie = cookies[i];
				}
			}
		}

		// ���� viewCookie�� null�� ��� ��Ű�� �����ؼ� ��ȸ�� ���� ������ ó����.
		if (viewCookie == null) {

			// ��Ű ����(�̸�, ��)
			Cookie newCookie = new Cookie("cookie" + bno, "|" + bno + "|");

			// ��Ű �߰�
			response.addCookie(newCookie);

			// ��Ű�� �߰� ��Ű�� ��ȸ�� ������Ŵ
			int result = dao.hit(bno);

		}
		// viewCookie�� null�� �ƴҰ�� ��Ű�� �����Ƿ� ��ȸ�� ���� ������ ó������ ����.
		else {

			// ��Ű �� �޾ƿ�.
			String value = viewCookie.getValue();

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
