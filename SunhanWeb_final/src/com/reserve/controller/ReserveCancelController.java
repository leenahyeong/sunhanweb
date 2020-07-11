package com.reserve.controller;

import java.io.IOException;
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

@WebServlet("/reservecancel.do")
public class ReserveCancelController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); 
		
		HttpSession session = request.getSession();
		String loginid = (String) session.getAttribute("loginUserID");
		
		int rno = Integer.parseInt(request.getParameter("rno"));
		String userid = request.getParameter("rv_userid"); // 디비에 저장된 유저아이디(파라미터)
		String storeid = request.getParameter("storeid"); // 가게 아이디
		
		ReserveDAO dao = ReserveDAO.getInstance();
		ReserveChildDTO rv_dto = dao.reserveDetail(rno);

		int queryResult = 0;
		
		if(loginid.equals(userid)) {
			rv_dto.setRv_status(4);
			queryResult = dao.reserveUpdate(rv_dto, 4);
		}
		
		
		if(queryResult != 0) {
			//response.sendRedirect("storelistGet.do");
			response.sendRedirect("/SunhanWeb/reservechild.do?userid="+loginid);
		} else {
			request.setAttribute("userid", userid);
			request.setAttribute("storeid", storeid);
			System.out.println("취소 실패" + queryResult);
			//response.sendRedirect("reserveresult.do?userid=" + userid + "&storeid=" + storeid);
			response.sendRedirect("/SunhanWeb/reservedetail.do?rno="+rno);
		}
			
		
	}

}
