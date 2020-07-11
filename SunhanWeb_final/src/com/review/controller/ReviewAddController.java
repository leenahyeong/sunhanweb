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
import com.store.dao.StoreDAO;
import com.sumhan.dto.SunhansVO;


@WebServlet("/reviewadd.do")
public class ReviewAddController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int rno = Integer.parseInt(request.getParameter("rno"));
		
		ReserveDAO dao = ReserveDAO.getInstance();
		ReserveChildDTO dto = dao.reserveDetail(rno);
		
		request.setAttribute("rno", rno);
		request.setAttribute("dto", dto);
		request.setAttribute("req", 0);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("reserve/reviewForm.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		// ���������ȣ
		int rno = Integer.parseInt(request.getParameter("review_rno"));
		String sessionID = (String) session.getAttribute("loginUserID");
		
		String review_userid = request.getParameter("review_id");
		String review_storeid = request.getParameter("review_otherid");
		String review_content = request.getParameter("review_content");
		
		ReviewDAO dao = ReviewDAO.getInstance();
		ReviewDTO dto = null;
		
		SunhansVO vo = (SunhansVO) session.getAttribute("loginUser");
		int admin = vo.getAdmin();
		
		// ��� ����� ȸ���� ���� ����ڰ� ������ �Ƶ���
		if(review_userid.equals(sessionID) || review_storeid.equals(sessionID)) {	
			// �Ƶ��� ����޶�
			if(admin==0) {
				int review_score = Integer.parseInt(request.getParameter("score"));
				
				dto = new ReviewDTO();
				dto.setReview_rno(rno);
				dto.setReview_userid(review_userid);
				dto.setReview_storeid(review_storeid);
				dto.setReview_score(review_score);
				dto.setReview_content(review_content);
				
				int result = dao.reviewInsert(dto, admin);
				
				response.sendRedirect("/SunhanWeb/reservedetail.do?rno="+rno);
			}
			// �Ŀ��ڰ� ��۴޶�
			else if(admin==1) {
				dto = new ReviewDTO();
				dto.setReview_rno(rno);
				dto.setReview_group(Integer.parseInt(request.getParameter("review_group")));
				dto.setReview_userid(review_userid);
				dto.setReview_storeid(review_storeid);
				dto.setReview_content(review_content);
				
				int result = dao.reviewInsert(dto, admin);
				System.out.println("��??" + result);
				response.sendRedirect("/SunhanWeb/review.do?userid="+sessionID);
			}
			
		}
		//System.out.println(bno + id + name + content);
		
	}

}
