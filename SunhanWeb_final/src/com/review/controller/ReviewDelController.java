package com.review.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.review.dao.ReviewDAO;
import com.review.dto.ReviewJoinDTO;

@WebServlet("/reviewdelete.do")
public class ReviewDelController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String loginid = (String) session.getAttribute("loginUserID");
		
		int review_no = Integer.parseInt(request.getParameter("review_no"));
		
		ReviewDAO dao = ReviewDAO.getInstance();
		ReviewJoinDTO dto = dao.reviewDetail(review_no, 1);
		
		// 아동이 삭제
		if(dto.getReview_userid().equals(loginid)) {
			
			int result = dao.reviewDelete(dto.getReview_group(), 0);
			System.out.println(result);
			
		}
		
		// 후원자가 삭제
		else if(dto.getReview_storeid().equals(loginid)) {
			int result = dao.reviewDelete(review_no, 1);
			System.out.println(result);
		}
		
		response.sendRedirect("/SunhanWeb/review.do?userid="+loginid);
		
	}

}
