package com.notice.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.NoticeDAO;
import com.board.dto.NoticeDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/noticeadd.do")
public class NoticeAddController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "notice/noticeAdd.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String file1="", file2="";

		// 파일 업로드 하기 위해 cos.jar 추가 및 객체 생성
		MultipartRequest multi = null;

		// 파일 최대 사이즈(10메가)
		int size = 10 * 1024 * 1024;

		// 업로드 될 실제 tomcat 폴더 경로
		String path = request.getServletContext().getRealPath("/upload");

		try {
			multi = new MultipartRequest(request, path, size, "UTF-8", new DefaultFileRenamePolicy());
			file1 = multi.getFilesystemName("file1");
			file2 = multi.getFilesystemName("file2");
			
			// 썸머노트로 업로드 한 이미지는 삭제
			String summer = multi.getFilesystemName("files"); // 썸머노트 이미지
			String summerFull = path + "/" + summer;
			File summerFile = new File(summerFull);
			if (summerFile.exists()) {
				summerFile.delete();
			}
		} catch (Exception e) {
			System.out.println("파일초과");
		}

		String full1 = path + "/" + file1;
		String full2 = path + "/" + file2;
		// 업로드 구현 끝

		String subject = multi.getParameter("subject");
		String content = multi.getParameter("content");

		NoticeDAO dao = NoticeDAO.getInstance();
		NoticeDTO dto = new NoticeDTO();
		
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setFile1(file1);
		dto.setFile2(file2);

		int result = dao.insert(dto);
		
		if (result == 1) {
			response.sendRedirect("/SunhanWeb/notice.do");
		} else {
			response.sendRedirect("/SunhanWeb/noticeadd.do");
		}

	}

}
