package com.reserve.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.reserve.dao.ReserveDAO;
import com.reserve.dto.ReserveChildDTO;
import com.reserve.dto.ReserveDTO;
import com.reserve.dto.ReserveStatusDTO;
import com.review.dao.ReviewDAO;
import com.review.dto.ReviewDTO;
import com.review.dto.ReviewJoinDTO;
import com.store.dao.StoreDAO;
import com.store.dto.StoreVO;
import com.sumhan.dto.SunhansVO;

@WebServlet("/reservedetail.do")
public class ReserveDetailController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		SunhansVO vo = (SunhansVO) session.getAttribute("loginUser");
		int admin = vo.getAdmin();
		
		// 공동 파라미터
		int rno = Integer.parseInt(request.getParameter("rno")); // 예약 pk
		
		ReserveDAO dao = ReserveDAO.getInstance();
		ReserveChildDTO dto = dao.reserveDetail(rno);
		
		// 아동이 상세화면 요청
		if(admin == 0 || admin == 1) {
			StoreDAO s_dao = StoreDAO.getInstance();
			StoreVO s_dto = s_dao.getStore(dto.getRv_sno());
			
			// 예약 테이블에 아동이 신청한 시간
			String now_yyyy = dto.getRv_time().substring(0,8);		
			String time1 = dto.getRv_time().substring(8,10);
			String time2 = dto.getRv_time().substring(10,12);
			
			// 가게 테이블에 적혀있는 가게 영업시간
			String open1 = s_dto.getOpentime().substring(0,2);
			String open2 = s_dto.getOpentime().substring(2,4);
			String close1 = s_dto.getClosetime().substring(0,2);
			String close2 = s_dto.getClosetime().substring(2,4);
			
			// 방문예정시간 1시간 이내엔 예약 수정이나 취소 못하게 날짜 구함
			SimpleDateFormat yydatefm = new SimpleDateFormat("yyyyMMdd"); // 문자열 -> date
			SimpleDateFormat yystringfm = new SimpleDateFormat("yyyy-MM-dd"); // date -> 문자열
			
			SimpleDateFormat datefm = new SimpleDateFormat("yyyyMMddhhmm"); // 문자열 -> date
			SimpleDateFormat stringfm = new SimpleDateFormat("yyyy-MM-dd a hh:mm"); // date -> 문자열
			
			Date yyyy = null;
			Date currentTime = new Date(); // 현재날짜
			Date dbTime = null;
			String now = null;
			int timeCheck = 0;
			try {
				// yyyy-mm-dd
				yyyy = yydatefm.parse(now_yyyy);
				now_yyyy = yystringfm.format(yyyy);
				
				// 현재 시간 문자열 -> date포맷형식 -> 계산하고 -> 스트링으로 set
				currentTime = Calendar.getInstance().getTime(); // 현재 시간 ex) 202006150202 년월일시분
				dbTime = datefm.parse(dto.getRv_time()); // 디비날짜
				
				long diff = dbTime.getTime() - currentTime.getTime();

				long m = diff / 60000; // 분 구하기
				
				if(m < 60) {
					timeCheck = -1; // 예약취소, 수정 불가능
				} else {
					timeCheck = 1; // 가능
				}
				
				now = stringfm.format(dbTime);
				System.out.println(now);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			ReviewDAO rdao = ReviewDAO.getInstance();
			ReviewJoinDTO rdto = rdao.reviewDetail(rno, 0);
			
			request.setAttribute("now", now);
			request.setAttribute("now_yyyy", now_yyyy);
			request.setAttribute("time1", time1);
			request.setAttribute("time2", time2);
			request.setAttribute("open1", open1);
			request.setAttribute("open2", open2);
			request.setAttribute("close1", close1);
			request.setAttribute("close2", close2);
			request.setAttribute("timeCheck", timeCheck);
			request.setAttribute("review", rdto);
			
		}
		
		request.setAttribute("dto", dto);
		request.setAttribute("admin", admin);
		
		String url = "reserve/reserveDetail.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String loginid = (String) session.getAttribute("loginUserID"); // 현재 세션 아이디

	}

}
