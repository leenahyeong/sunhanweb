package com.notice.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dao.NoticeDAO;
import com.board.dto.NoticeDTO;

@WebServlet("/notice.do")
public class NoticeListController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String url = "notice/notice.jsp";

		// 현재 페이지 번호 만들기
		int spage = 1; // 디폴트 1
		String page = request.getParameter("page"); // 파라미터로 받아온 현재 페이지

		if (page != null) // 현재 페이지가 정해져 있으면 (1이 아닐수도 있으니까 spage에 page저장)
			spage = Integer.parseInt(page);
		
		// 검색조건과 검색내용을 가져온다.
		String option = request.getParameter("option");
		String keyword = request.getParameter("keyword");

		// 검색조건과 내용을 Map에 담는다.
		HashMap<String, Object> listObj = new HashMap<String, Object>();
		listObj.put("option", option);
		listObj.put("keyword", keyword);
		listObj.put("start", spage * 10 - 10); // 시작 갯수 0, 10, 20 ...
		listObj.put("end", 10); // 한 페이지에 10개씩

		NoticeDAO dao = NoticeDAO.getInstance(); 
		List<NoticeDTO> list = dao.list(listObj);
		int listCount = dao.totalCount(listObj); // 글 총 갯수
		//System.out.println("공지사항list" + list.size());
		

		// 한 화면에 10개의 게시글을 보여지게함
		// 페이지 번호는 총 5개, 이후로는 [다음]으로 표시
		// 전체 페이지 수
		int maxPage = (int) (listCount / 10.0 + 0.9); // ex.9
		// 시작 페이지 번호
		int startPage = (int) (spage / 5.0 + 0.8) * 5 - 4;
		// 마지막 페이지 번호
		int endPage = startPage + 4; // 한 블럭당 5개씩 보일거니까 start=1이면 5, start=6이면 10
		if (endPage > maxPage) // 10>9
			endPage = maxPage; // 9페이지까지 데이터가 있으면 end페이지에 최대 페이지번호 넣음

		// 4개 페이지번호 저장
		request.setAttribute("spage", spage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("count", listCount);
		request.setAttribute("option", option);
		request.setAttribute("keyword", keyword);
		request.setAttribute("list", list);

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
