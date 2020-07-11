package com.notice.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.NoticeDAO;
import com.board.dto.NoticeDTO;



@WebServlet("/noticedelete.do")
public class NoticeDelController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); 
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		NoticeDAO dao = NoticeDAO.getInstance();
		NoticeDTO dto = dao.detail(bno);
		
		String filename1 = dto.getFile1();
		String filename2 = dto.getFile2();
		String content = dto.getContent();
		filename1 = String.valueOf(filename1);
		filename2 = String.valueOf(filename2);

		// 파일 삭제
		if(filename1.equals("null")) {
			String path = this.getServletContext().getRealPath("/upload");

			String full = path + "/" + filename1;
			File file = new File(full);
			if(file.exists()) file.delete();
		}
		
		if(filename2.equals("null")) {
			String path2 = this.getServletContext().getRealPath("/upload");	
			String full2 = path2 + "/" + filename2;
			File file2 = new File(full2);
			if(file2.exists()) file2.delete();
		}
		
		
		System.out.println(dao.delete(bno)); // 글 삭제);
		
		
		response.sendRedirect("/SunhanWeb/notice.do");
	}

}
