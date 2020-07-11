package com.photo.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.PhotoDAO;
import com.board.dto.PTcmtDTO;


@WebServlet("/ptcomment.do")
public class PTcmtListController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		int bno = Integer.parseInt(request.getParameter("cmt_bno"));
	
		PhotoDAO dao = PhotoDAO.getInstance();
		PTcmtDTO cmt_dto = new PTcmtDTO(); 
		
		ArrayList<PTcmtDTO> cmt_list = (ArrayList<PTcmtDTO>) dao.cmt_list(bno);
		int cmt_count = dao.cmt_count(bno); // ´ñ±Û °¹¼ö
		
		request.setAttribute("cmt_list", cmt_list);
		request.setAttribute("cmt_count", cmt_count);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("photo/comment.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
