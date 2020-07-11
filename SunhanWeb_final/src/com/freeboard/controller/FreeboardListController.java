package com.freeboard.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.FreeboardDAO;
import com.board.dto.FreeboardDTO;


@WebServlet("/freeboard.do")
public class FreeboardListController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String url = "freeboard/freeboard.jsp";

		// ���� ������ ��ȣ �����
		int spage = 1;
		String page = request.getParameter("page");

		if (page != null)
			spage = Integer.parseInt(page);
		
		// �˻����ǰ� �˻������� �����´�.
		String option = request.getParameter("option");
		String keyword = request.getParameter("keyword");
		

		// �˻����ǰ� ������ Map�� ��´�.
		HashMap<String, Object> listObj = new HashMap<String, Object>();
		listObj.put("option", option);
		listObj.put("keyword", keyword);
		listObj.put("start", spage * 10 - 10); // ���� ���� 0, 10, 20 ...
		listObj.put("end", 10); // �� �������� 10����

		FreeboardDAO dao = FreeboardDAO.getInstance(); 
		List<FreeboardDTO> list = dao.list(listObj);
		int listCount = dao.totalCount(listObj); // �� ����

		// �� ȭ�鿡 10���� �Խñ��� ����������
		// ������ ��ȣ�� �� 5��, ���ķδ� [����]���� ǥ��

		// ��ü ������ ��
		int maxPage = (int) (listCount / 10.0 + 0.9);
		// ���� ������ ��ȣ
		int startPage = (int) (spage / 5.0 + 0.8) * 5 - 4;
		// ������ ������ ��ȣ
		int endPage = startPage + 4;
		if (endPage > maxPage)
			endPage = maxPage;

		// 4�� ��������ȣ ����
		request.setAttribute("spage", spage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("count", listCount);
		request.setAttribute("option", option);
		request.setAttribute("keyword", keyword);
		request.setAttribute("list", list);
		//request.setAttribute("cmt_count", cmt_count);

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
