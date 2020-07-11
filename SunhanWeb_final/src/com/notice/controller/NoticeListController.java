package com.notice.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dao.NoticeDAO;
import com.board.dto.NoticeDTO;

@WebServlet("/notice.do")
public class NoticeListController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String url = "notice/notice.jsp";

		// ���� ������ ��ȣ �����
		int spage = 1; // ����Ʈ 1
		String page = request.getParameter("page"); // �Ķ���ͷ� �޾ƿ� ���� ������

		if (page != null) // ���� �������� ������ ������ (1�� �ƴҼ��� �����ϱ� spage�� page����)
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

		NoticeDAO dao = NoticeDAO.getInstance(); 
		List<NoticeDTO> list = dao.list(listObj);
		int listCount = dao.totalCount(listObj); // �� �� ����
		//System.out.println("��������list" + list.size());
		

		// �� ȭ�鿡 10���� �Խñ��� ����������
		// ������ ��ȣ�� �� 5��, ���ķδ� [����]���� ǥ��
		// ��ü ������ ��
		int maxPage = (int) (listCount / 10.0 + 0.9); // ex.9
		// ���� ������ ��ȣ
		int startPage = (int) (spage / 5.0 + 0.8) * 5 - 4;
		// ������ ������ ��ȣ
		int endPage = startPage + 4; // �� ���� 5���� ���ϰŴϱ� start=1�̸� 5, start=6�̸� 10
		if (endPage > maxPage) // 10>9
			endPage = maxPage; // 9���������� �����Ͱ� ������ end�������� �ִ� ��������ȣ ����

		// 4�� ��������ȣ ����
		request.setAttribute("spage", spage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("count", listCount);
		request.setAttribute("option", option);
		request.setAttribute("keyword", keyword);
		request.setAttribute("list", list);

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
