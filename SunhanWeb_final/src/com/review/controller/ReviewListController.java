package com.review.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.reserve.dao.ReserveDAO;
import com.reserve.dto.ReserveChildDTO;
import com.review.dao.ReviewDAO;
import com.review.dto.ReviewJoinDTO;
import com.sumhan.dao.SunhansDAO;
import com.sumhan.dto.SunhansVO;

@WebServlet("/review.do")
public class ReviewListController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		String loginid = (String) session.getAttribute("loginUserID");
		SunhansVO sunhans = (SunhansVO) session.getAttribute("loginUser");
		
		// 현재 페이지 번호 만들기
		int spage = 1; // 디폴트 1
		String page = request.getParameter("page"); // 파라미터로 받아온 현재 페이지

		if (page != null) // 현재 페이지가 정해져 있으면 (1이 아닐수도 있으니까 spage에 page저장)
			spage = Integer.parseInt(page);

		// 검색 조건 들고옴
		String search_userid = request.getParameter("search_userid"); // 아이디 검색(가게 or 아동)
		String start_date = request.getParameter("start_date");
		String end_date = request.getParameter("end_date");
		String r_no = request.getParameter("review_no");
		
		r_no = String.valueOf(r_no);
		search_userid = String.valueOf(search_userid);
		start_date = String.valueOf(start_date);
		end_date = String.valueOf(end_date);
		
		
		int review_no = -1;
		
		if(r_no == "null") {
			review_no = 0;
		} else {
			review_no = Integer.parseInt(r_no);
		}
		
		if(search_userid == "null" || search_userid=="") {
			search_userid = "";
		}
		
		if(start_date == "null" || start_date=="") {
			start_date = "";
		}
		
		if(end_date == "null" || end_date=="") {
			end_date = "";
		}


		// 검색조건과 내용을 Map에 담는다.
		HashMap<String, Object> listObj = new HashMap<String, Object>();
		listObj.put("search_userid", search_userid);
		listObj.put("start_date", start_date);
		listObj.put("end_date", end_date);
		listObj.put("start", spage * 15 - 15); // 시작페이지
		listObj.put("userid", loginid); // 현재 로그인 중인
		listObj.put("admin",sunhans.getAdmin());
		listObj.put("review_no", review_no);

		ReviewDAO dao = ReviewDAO.getInstance();

		// 한 화면에 15개의 게시글을 보여지게함
		// 페이지 번호는 총 5개, 이후로는 [다음]으로 표시
		List<Integer> listCount = dao.reviewtotalCount(listObj);
		
		if(listCount.get(2) == null) {
			listCount.set(2,0);
		}
		int maxPage= (int) (listCount.get(0) / 10.0 + 0.9); // ex.9
		
		// 전체 페이지 수
		// 시작 페이지 번호
		int startPage = (int) (spage / 5.0 + 0.8) * 5 - 4;
		// 마지막 페이지 번호
		int endPage = startPage + 4; // 한 블럭당 5개씩 보일거니까 start=1이면 5, start=6이면 10
		if (endPage > maxPage) // 10>9
			endPage = maxPage; // 9페이지까지 데이터가 있으면 end페이지에 최대 페이지번호 넣음
		System.out.println("listCount" + listCount);

		//if (loginid.equals(userid)) {
			
		List<ReviewJoinDTO> list = dao.reviewList(listObj);
		
		request.setAttribute("list", list);
		request.setAttribute("count", listCount);
		request.setAttribute("listObj", listObj);
		request.setAttribute("keyword", search_userid);
		request.setAttribute("start_date", start_date);
		request.setAttribute("end_date", end_date);
	
		request.setAttribute("spage", spage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);

		//}

		String url = "review/review.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
