package com.review.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.reserve.dao.ReserveDAO;
import com.reserve.dto.ReserveChildDTO;
import com.review.dao.ReviewDAO;
import com.review.dto.ReviewJoinDTO;
import com.sumhan.dao.SunhansDAO;
import com.sumhan.dto.SunhansVO;

@WebServlet("/reviewajax.do")
public class ReviewListAjaxController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		String loginid = (String) session.getAttribute("loginUserID");

		// �˻����ǰ� ������ Map�� ��´�.
		HashMap<String, Object> listObj = new HashMap<String, Object>();
		listObj.put("start_date", "");
		listObj.put("end_date", "");
		listObj.put("search_userid", "");
		listObj.put("review_no", 0);
		listObj.put("userid", request.getParameter("storeid")); 
		listObj.put("start", 0); // ����������
		listObj.put("admin",1);
		
		ReviewDAO dao = ReviewDAO.getInstance();
		
		// �Ƶ� �� ����, ���� �� ����, ����
		List<Integer> review_count = dao.reviewtotalCount(listObj);

		// ����Ʈ
		List<ReviewJoinDTO> list = dao.reviewList(listObj);
		
		request.setAttribute("list", list);
		request.setAttribute("count", review_count);
		request.setAttribute("listObj", listObj);
	
		String url = "review/reviewlist.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
