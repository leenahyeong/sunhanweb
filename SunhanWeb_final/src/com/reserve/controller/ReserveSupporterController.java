package com.reserve.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dto.NoticeDTO;
import com.reserve.dao.ReserveDAO;
import com.reserve.dto.ReserveDTO;
import com.store.dao.StoreDAO;
import com.store.dto.StoreVO;

@WebServlet("/reservesupporter.do")
public class ReserveSupporterController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String loginid = (String) session.getAttribute("loginUserID");
		String userid = request.getParameter("userid"); // 마이페이지에서 받아온 파라미터 가게
		// 현재 페이지 번호 만들기
		int spage = 1; // 디폴트 1
		String page = request.getParameter("page"); // 파라미터로 받아온 현재 페이지

		if (page != null) // 현재 페이지가 정해져 있으면 (1이 아닐수도 있으니까 spage에 page저장)
			spage = Integer.parseInt(page);
			
		// 검색 조건 들고옴
		String search_userid = request.getParameter("search_userid"); // 아이디 검색(아동)
		String start_date = request.getParameter("start_date");
		String end_date = request.getParameter("end_date");
		String status_option = request.getParameter("s_radio");
		String visit_option = request.getParameter("v_radio");
		
		// 검색조건과 내용을 Map에 담는다.
		HashMap<String, Object> listObj = new HashMap<String, Object>();
		listObj.put("search_userid", search_userid);
		listObj.put("start_date", start_date);
		listObj.put("end_date", end_date);
		listObj.put("status_option", status_option);
		listObj.put("visit_option", visit_option);
		listObj.put("start", spage * 15 - 15); // 시작페이지
		listObj.put("rv_sno", loginid);
		
		ReserveDAO dao = ReserveDAO.getInstance();
		ReserveDTO dto = new ReserveDTO();

		// 한 화면에 15개의 게시글을 보여지게함
		// 페이지 번호는 총 5개, 이후로는 [다음]으로 표시
		int listCount = dao.SupportertotalCount(listObj);
		// 전체 페이지 수
		int maxPage = (int) (listCount / 10.0 + 0.9); // ex.9
		// 시작 페이지 번호
		int startPage = (int) (spage / 5.0 + 0.8) * 5 - 4;
		// 마지막 페이지 번호
		int endPage = startPage + 4; // 한 블럭당 5개씩 보일거니까 start=1이면 5, start=6이면 10
		if (endPage > maxPage) // 10>9
			endPage = maxPage; // 9페이지까지 데이터가 있으면 end페이지에 최대 페이지번호 넣음
		System.out.println("listCount" + listCount);
		
		if(loginid.equals(userid)) {
			int userCheck = 1;
			
			List<ReserveDTO> list = dao.reserveSupproterList(listObj);
			request.setAttribute("list", list);
			request.setAttribute("count", listCount);
			request.setAttribute("listObj", listObj);
			request.setAttribute("userCheck", userCheck);
			
			request.setAttribute("spage", spage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
		}
		

		
		String url = "reserve/reserveSupporter.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		
		
		
	}

}
