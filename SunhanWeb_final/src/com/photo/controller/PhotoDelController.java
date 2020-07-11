package com.photo.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dao.PhotoDAO;
import com.board.dto.PhotoDTO;



@WebServlet("/photodelete.do")
public class PhotoDelController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); 
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		HttpSession session = request.getSession();
		
		PhotoDAO dao = PhotoDAO.getInstance();
		PhotoDTO dto = dao.detail(bno);
		
		// 세션(=현재 접속자)아이디와 디비에 저장된 작성자가 같을때 수정가능
		if(dto.getId().equals(session.getAttribute("loginUserID"))) {
			
			String path = this.getServletContext().getRealPath("/thumbnail");
			File originImg = new File(path + "/" + dto.getThumbnail());
		    if (originImg.exists()) {
		    	originImg.delete();
			}
		    
			dao.delete(bno);  // 글 삭제
			
		}
		
		response.sendRedirect("/SunhanWeb/photo.do");
	}

}
