package com.review.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dao.FreeboardDAO;
import com.board.dto.FBcmtDTO;
import com.reserve.dao.ReserveDAO;
import com.reserve.dto.ReserveChildDTO;
import com.review.dao.ReviewDAO;
import com.review.dto.ReviewDTO;
import com.review.dto.ReviewJoinDTO;
import com.store.dao.StoreDAO;
import com.sumhan.dto.SunhansVO;


@WebServlet("/reviewupdate.do")
public class ReviewUpdateController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int review_no = Integer.parseInt(request.getParameter("review_no"));
		
		ReviewDAO dao = ReviewDAO.getInstance();
		ReviewJoinDTO dto = dao.reviewDetail(review_no, 1);
		
		//request.setAttribute("rno", rno);
		request.setAttribute("dto", dto);
		request.setAttribute("req", 1);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("reserve/reviewForm.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		// 예약고유번호
		int no = Integer.parseInt(request.getParameter("review_no_u"));
		String sessionID = (String) session.getAttribute("loginUserID");
		
		String review_userid = request.getParameter("review_id_u");
		String review_storeid = request.getParameter("review_otherid_u");
		String review_content = request.getParameter("review_content_u");
		
		ReviewDAO dao = ReviewDAO.getInstance();
		ReviewDTO dto = null;
		
		SunhansVO vo = (SunhansVO) session.getAttribute("loginUser");
		int admin = vo.getAdmin();
		
		// 디비에 저장된 회원과 현재 사용자가 같을때 아동용
		if(review_userid.equals(sessionID) || review_storeid.equals(sessionID)) {	
			// 아동이 리뷰달때
			if(admin==0) {
				int review_score = Integer.parseInt(request.getParameter("score_u"));
				
				dto = new ReviewDTO();
				dto.setReview_score(review_score);
				dto.setReview_content(review_content);
				dto.setReview_no(no);
				
				int result = dao.reviewUpdate(dto, admin);
				
			}
			// 후원자가 답글달때
			else if(admin==1) {
				dto = new ReviewDTO();
				dto.setReview_content(review_content);
				dto.setReview_no(no);
				
				int result = dao.reviewUpdate(dto, admin);
			}
			
			response.sendRedirect("/SunhanWeb/review.do?userid="+sessionID);
			
		}
		//System.out.println(bno + id + name + content);
		
	}

}
