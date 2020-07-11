package com.reserve.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.sumhan.dto.SunhansVO;

@WebServlet("/reserveupdate.do")
public class ReserveUpdateController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); 
		
		HttpSession session = request.getSession();
		String loginid = (String) session.getAttribute("loginUserID");
		SunhansVO vo = (SunhansVO) session.getAttribute("loginUser");
		int admin = vo.getAdmin();
		
		int rno = Integer.parseInt(request.getParameter("rno"));
		
		ReserveDAO dao = ReserveDAO.getInstance();
		ReserveChildDTO rv_dto = dao.reserveDetail(rno);

		int queryResult = 0;
		
		Date date = null;
		String time = null;
		if(admin == 0) { // �Ƶ�
			String now = request.getParameter("now_yyyy"); // �����
			String option = request.getParameter("option"); // ��
			String minute = request.getParameter("minute"); // ��
			now = now.replace("-", "");
			rv_dto.setRv_time(now+option+minute);
			rv_dto.setRv_personnel(Integer.parseInt(request.getParameter("personnel")));
			queryResult = dao.reserveUpdate(rv_dto, -1);
		} else if(admin == 1) { // ����
			int status = Integer.parseInt(request.getParameter("status"));
			int visit = Integer.parseInt(request.getParameter("visit"));
			System.out.println(status + "s " + visit);
			if(status == 2) { // �������
				// �������
				rv_dto.setRv_status(2);
				queryResult = dao.reserveUpdate(rv_dto, 2);
			}
			if(status == 3) { // �������
				rv_dto.setRv_status(3);
				rv_dto.setRv_reason(request.getParameter("reason"));
				System.out.println(rv_dto.getRv_reason());
				queryResult = dao.reserveUpdate(rv_dto, 3);
				
			}
			if(visit != 0) {
				rv_dto.setRv_visit(visit);
				queryResult = dao.reserveUpdate(rv_dto, -2);
			}
			
			else {
				System.out.println("����� ������");
			}
			 
		}
		
		
		if(queryResult != 0) {
			//response.sendRedirect("storelistGet.do");
			response.sendRedirect("/SunhanWeb/reservedetail.do?rno="+rno);
		} else {
			//request.setAttribute("storeid", storeid);
			System.out.println("��� ����" + queryResult);
			//response.sendRedirect("reserveresult.do?userid=" + userid + "&storeid=" + storeid);
			response.sendRedirect("/SunhanWeb/reservedetail.do?rno="+rno);
		}
			
		
	}

}
