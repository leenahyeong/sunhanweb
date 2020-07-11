package com.photo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import com.board.dao.PhotoDAO;
import com.board.dto.PhotoDTO;

@WebServlet("/photo.do")
public class PhotoListController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	
		String url = "photo/photo.jsp";

		// 현재 페이지 번호 만들기
		int spage = 1;
		String page = request.getParameter("page");

		if (page != null)
			spage = Integer.parseInt(page);
		
		// 검색조건과 검색내용을 가져온다.
		String option = request.getParameter("option");
		String keyword = request.getParameter("keyword");

		// 검색조건과 내용을 Map에 담는다.
		HashMap<String, Object> listObj = new HashMap<String, Object>();
		listObj.put("option", option);
		listObj.put("keyword", keyword);
		listObj.put("start", spage * 9 - 9); // 시작 갯수 0, 10, 20 ...
		listObj.put("end", 9); // 한 페이지에 10개씩

		PhotoDAO dao = PhotoDAO.getInstance(); 
		List<PhotoDTO> list = dao.list(listObj);
		int listCount = dao.totalCount(listObj); // 글 갯수
		System.out.println("list" + list.size());
		// 한 화면에 9개의 게시글을 보여지게함
		// 페이지 번호는 총 5개, 이후로는 [다음]으로 표시

		// 전체 페이지 수
		int maxPage = (int) (listCount / 10.0 + 0.9); // 정상
		// 시작 페이지 번호
		int startPage = (int) (spage / 5.0 + 0.8) * 5 - 4; // 정상
		// 마지막 페이지 번호
		int endPage = startPage + 4; // 정상
		
		if (endPage > maxPage)
			endPage = maxPage;
		

		// 4개 페이지번호 저장
		request.setAttribute("spage", spage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("count", listCount);
		request.setAttribute("option", option);
		request.setAttribute("keyword", keyword);
		request.setAttribute("list", list);
		
		// 안드로이드로 보내기
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		String jsondata = obj.toString();
		out.print(jsondata);

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
