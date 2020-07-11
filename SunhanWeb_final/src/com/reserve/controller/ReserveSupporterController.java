package com.reserve.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dto.NoticeDTO;
import com.reserve.dao.ReserveDAO;
import com.reserve.dto.ReserveDTO;
import com.store.dao.StoreDAO;
import com.store.dto.StoreVO;

@WebServlet("/reservesupporter.do")
public class ReserveSupporterController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String loginid = (String) session.getAttribute("loginUserID");
		String userid = request.getParameter("userid"); // �������������� �޾ƿ� �Ķ���� ����
		// ���� ������ ��ȣ �����
		int spage = 1; // ����Ʈ 1
		String page = request.getParameter("page"); // �Ķ���ͷ� �޾ƿ� ���� ������

		if (page != null) // ���� �������� ������ ������ (1�� �ƴҼ��� �����ϱ� spage�� page����)
			spage = Integer.parseInt(page);
			
		// �˻� ���� ����
		String search_userid = request.getParameter("search_userid"); // ���̵� �˻�(�Ƶ�)
		String start_date = request.getParameter("start_date");
		String end_date = request.getParameter("end_date");
		String status_option = request.getParameter("s_radio");
		String visit_option = request.getParameter("v_radio");
		
		// �˻����ǰ� ������ Map�� ��´�.
		HashMap<String, Object> listObj = new HashMap<String, Object>();
		listObj.put("search_userid", search_userid);
		listObj.put("start_date", start_date);
		listObj.put("end_date", end_date);
		listObj.put("status_option", status_option);
		listObj.put("visit_option", visit_option);
		listObj.put("start", spage * 15 - 15); // ����������
		listObj.put("rv_sno", loginid);
		
		ReserveDAO dao = ReserveDAO.getInstance();
		ReserveDTO dto = new ReserveDTO();

		// �� ȭ�鿡 15���� �Խñ��� ����������
		// ������ ��ȣ�� �� 5��, ���ķδ� [����]���� ǥ��
		int listCount = dao.SupportertotalCount(listObj);
		// ��ü ������ ��
		int maxPage = (int) (listCount / 10.0 + 0.9); // ex.9
		// ���� ������ ��ȣ
		int startPage = (int) (spage / 5.0 + 0.8) * 5 - 4;
		// ������ ������ ��ȣ
		int endPage = startPage + 4; // �� ���� 5���� ���ϰŴϱ� start=1�̸� 5, start=6�̸� 10
		if (endPage > maxPage) // 10>9
			endPage = maxPage; // 9���������� �����Ͱ� ������ end�������� �ִ� ��������ȣ ����
		System.out.println("listCount" + listCount);
		
		if(loginid.equals(userid)) {
			int userCheck = 1;
			
			List<ReserveDTO> list = dao.reserveSupproterList(listObj);
			request.setAttribute("list", list);
			request.setAttribute("count", listCount);
			request.setAttribute("listObj", listObj);
			request.setAttribute("userCheck", userCheck);
			
			request.setAttribute("spage", spage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
		}
		

		
		String url = "reserve/reserveSupporter.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		
		
		
	}

}
