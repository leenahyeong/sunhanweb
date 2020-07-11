package com.reserve.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.reserve.dao.ReserveDAO;
import com.reserve.dto.ReserveDTO;
import com.reserve.dto.ReserveStatusDTO;
import com.store.dao.StoreDAO;
import com.store.dto.StoreVO;

@WebServlet("/reserveresult.do")
public class ReserveResultController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String loginid = (String) session.getAttribute("loginUserID"); 
		
		String userid = request.getParameter("userid"); 
		String storeid = request.getParameter("storeid"); 
		
		//if(id == rv_userid) {
		ReserveDAO dao = ReserveDAO.getInstance();
		ReserveDTO dto = dao.reserveResult(userid,storeid);
	
		StoreDAO s_dao = StoreDAO.getInstance();
		StoreVO vo = s_dao.getStore(storeid);
			
		String rv_status = null;
		if(dto.getRv_status() == 1) {
	         rv_status = "예약 승인요청중입니다. 결과가 나올때까지 기다려주세요.";
	      } 
	      else if(dto.getRv_status() == 2) {
	         rv_status = "예약이 완료되었습니다.";
	      } 
	      else if(dto.getRv_status() == 3){
	         rv_status = "예약이 거절되었습니다.";
	      }
	      else if(dto.getRv_status() == 4) {
	         rv_status = "예약취소";
	      }
		
		
		SimpleDateFormat datefm = new SimpleDateFormat("yyyyMMddhhmm"); 
		SimpleDateFormat stringfm = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
		Date date = null;
		try {
			date = datefm.parse(dto.getRv_time());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String time = stringfm.format(date);
		/*	
		String now = dto.getRv_time().substring(0,8);		
		String time1 = dto.getRv_time().substring(8,10);
		String time2 = dto.getRv_time().substring(10,12);
		*/
		
		int userCheck = 0;
			
		if(userid.equals(loginid)) {
			userCheck = 1;
		}
		
		//System.out.println("reserveResult" +loginid + "占쏙옙占실억옙占싱듸옙" + userid + "占쏙옙占� 占쏙옙" + result);

		//request.setAttribute("now", now);
		//request.setAttribute("time1", time1);
		//request.setAttribute("time2", time2);
		request.setAttribute("dto", dto);
		request.setAttribute("time", time);
		request.setAttribute("rv_status", rv_status);
		request.setAttribute("shopname", vo.getShopname());
		request.setAttribute("userCheck", userCheck);
		System.out.println(userCheck + "result");
		
		String url = "reserve/reserveResult.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		//}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String loginid = (String) session.getAttribute("loginUserID"); // 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占싱듸옙
		
		// 占식띰옙占쏙옙占�
		int result = Integer.parseInt(request.getParameter("result"));
		
		String storeid = request.getParameter("storeid");
		String userid = request.getParameter("userid");
		
		int rv_personnel = Integer.parseInt(request.getParameter("personnel"));
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String now = request.getParameter("now");
		String rv_time = now.replace("-", "").trim()+request.getParameter("option")+request.getParameter("minute");
		
		ReserveDAO dao = ReserveDAO.getInstance();
		ReserveDTO rv_dto = new ReserveDTO(); // 占쏙옙占쏙옙 占싸쇽옙트
		ReserveStatusDTO rs_dto = new ReserveStatusDTO(); // 占쏙옙占쏙옙占쏙옙占� 占싸쇽옙트
		
		
		int queryResult = 0;
		
		// 占신깍옙 占싣듸옙
		if(userid.equals(loginid)) {
			if(result == 0) {
				rv_dto.setRv_sno(storeid);
				rv_dto.setRv_time(rv_time);
				rv_dto.setRv_userid(loginid);
				rv_dto.setRv_personnel(rv_personnel);
				rv_dto.setRv_status(1); // 占쏙옙占쏙옙占쏙옙占쌘몌옙占쏙옙 占쏙옙占싸댐옙占쏙옙占쏙옙占쏙옙占�
				
				rs_dto.setRs_userid(loginid);
				rs_dto.setRs_storeid(storeid);
				
				queryResult = dao.reserveInsert(rv_dto, rs_dto, result);
			}
			else if(result == 1) {
				rv_dto.setRv_sno(storeid);
				rv_dto.setRv_time(rv_time);
				rv_dto.setRv_userid(loginid);
				rv_dto.setRv_personnel(rv_personnel);
				rv_dto.setRv_status(1); // 占쏙옙占쏙옙占쏙옙占쌘몌옙占쏙옙 占쏙옙占싸댐옙占쏙옙占쏙옙占쏙옙占�
				
				rs_dto.setRs_userid(loginid);
				rs_dto.setRs_storeid(storeid);
				
				queryResult = dao.reserveInsert(rv_dto, rs_dto, result);
			}
		} 
		
		if(queryResult != 0) { 
			request.setAttribute("queryResult", queryResult);
			response.sendRedirect("/SunhanWeb/reserveresult.do?userid=" + userid + "&storeid=" + storeid);
		}else {
			// 占쏙옙占싻쏙옙 占쌕쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙
			request.setAttribute("msg", "占쏙옙占썅에 占쏙옙占쏙옙占싹셨쏙옙占싹댐옙.");
			response.sendRedirect("/SunhanWeb/store.do?userid=" + storeid);
		}
	}

}
