package com.store.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.reserve.dao.ReserveDAO;
import com.reserve.dto.ReserveStatusDTO;
import com.review.dao.ReviewDAO;
import com.review.dto.ReviewDTO;
import com.store.dao.StoreDAO;
import com.store.dto.StoreVO;

import storefile.storefileDAO;
import storefile.storefileDTO;

/**
 * Servlet implementation class storeinfo
 */
@WebServlet("/store.do")
public class storeinfo extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		ArrayList<storefileDTO> storefilename = new ArrayList<>();

		String userid = request.getParameter("userid");
		StoreDAO SDAO = new StoreDAO();
		storefileDAO sfdao = new storefileDAO();
		storefilename = sfdao.getSearch(userid);
		StoreVO SVO = new StoreVO();
		SVO = SDAO.getStore(userid);

		session.setAttribute("Store", SVO);
		session.setAttribute("Storefilename", storefilename);
		
		
		/*---------- 占쏙옙占쏙옙 ----------*/
		
		String rv_userid = (String) session.getAttribute("loginUserID");
		rv_userid = String.valueOf(rv_userid);
		ReserveDAO dao = ReserveDAO.getInstance();
		ReserveStatusDTO dto = null;
		
		String now = null;
		String open1 = null, open2 = null;
		String close1 = null, close2 = null;
		
		Date currentTime = new Date();
		Date closeTime = new Date();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat yyyysf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");
		
		SimpleDateFormat datefm = new SimpleDateFormat("yyyyMMddhhmm"); 
	
		now = sf.format(currentTime);
		String nowyyyy = sf2.format(currentTime);
		currentTime = Calendar.getInstance().getTime(); 
		open1 = SVO.getOpentime().substring(0,2);
		open2 = SVO.getOpentime().substring(2,4);
		String close = nowyyyy+SVO.getClosetime();
		close1 = SVO.getClosetime().substring(0,2);
		close2 = SVO.getClosetime().substring(2,4);
		
		try {
			closeTime = datefm.parse(close);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(!rv_userid.equals("null")) {
			int result = -2;
			
			dto = dao.reserveCheck(rv_userid, userid);
			
			String check = dto.getRs_available();
			check = String.valueOf(check);

			// 처음 예약하는 아동
			if(check.equals("null")) {
				result = 0;
			}
			// 예약한 적 있지만 현재 예약 가능
			else if(check.equals("Y")) {
				result = 1;
			}
			// 예약한 적 있고 현재 예약 불가능
			else if(check.equals("N")) {
				result = -1;
			} 
			
			int reserveCheck = 0;
			
			long closediff = closeTime.getTime() - currentTime.getTime();
			
			long m2 = closediff / 60000;
			if(m2 <= 0) {
				// 占쏙옙占쏙옙柰占�
				reserveCheck = -1;
			} else {
				reserveCheck = 1;
			}
				
			if(result == -1) {
				System.out.println("占싱뱄옙 占쏙옙占쏙옙占� 占쏙옙占쏙옙占쏙옙");
			}
			
			
			
			String search_userid = request.getParameter("search_userid"); // 占쏙옙占싱듸옙 占싯삼옙(占쏙옙占쏙옙 or 占싣듸옙)
			String start_date = request.getParameter("start_date");
			String end_date = request.getParameter("end_date");
			String r_no = request.getParameter("review_no");
			

			// 占싯삼옙占쏙옙占실곤옙 占쏙옙占쏙옙占쏙옙 Map占쏙옙 占쏙옙쨈占�.
			HashMap<String, Object> listObj = new HashMap<String, Object>();
			listObj.put("search_userid", "");
			listObj.put("start_date", "");
			listObj.put("end_date", "");
			listObj.put("userid", SVO.getUserid()); // 占쏙옙占쏙옙 占싸깍옙占쏙옙 占쏙옙占쏙옙
			listObj.put("admin",1);
			listObj.put("review_no", 0);

			ReviewDAO rdao = ReviewDAO.getInstance();

			// 占쏙옙 화占썽에 15占쏙옙占쏙옙 占쌉시깍옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙
			// 占쏙옙占쏙옙占쏙옙 占쏙옙호占쏙옙 占쏙옙 5占쏙옙, 占쏙옙占식로댐옙 [占쏙옙占쏙옙]占쏙옙占쏙옙 표占쏙옙
			List<Integer> listCount = rdao.reviewtotalCount(listObj);
			
			if(listCount.get(1) == null) {
				listCount.set(1,0);
			}
			
			request.setAttribute("count", listCount);
			
			request.setAttribute("reserveCheck", reserveCheck);
			request.setAttribute("result", result);
			
		}
		
		request.setAttribute("now", now);
		request.setAttribute("open1", open1);
		request.setAttribute("open2", open2);
		request.setAttribute("close1", close1);
		request.setAttribute("close2", close2);
		
		String url = "store2.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
