package com.freeboard.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dao.FreeboardDAO;
import com.board.dto.FreeboardDTO;




@WebServlet("/freeboarddelete.do")
public class FreeboardDelController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); 
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		HttpSession session = request.getSession();
		
		FreeboardDAO dao = FreeboardDAO.getInstance();
		FreeboardDTO dto = dao.detail(bno);
		
		// ����(=���� ������)���̵�� ��� ����� �ۼ��ڰ� ������ ��������
		if(dto.getId().equals(session.getAttribute("loginUserID"))) {
			dao.delete(bno);  // �� ����
		}
		
		response.sendRedirect("/SunhanWeb/freeboard.do");
	}

}
