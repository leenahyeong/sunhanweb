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
		
		// ���� �Ķ����
		int rno = Integer.parseInt(request.getParameter("rno")); // ���� pk
		
		ReserveDAO dao = ReserveDAO.getInstance();
		ReserveChildDTO dto = dao.reserveDetail(rno);
		
		// �Ƶ��� ��ȭ�� ��û
		if(admin == 0 || admin == 1) {
			StoreDAO s_dao = StoreDAO.getInstance();
			StoreVO s_dto = s_dao.getStore(dto.getRv_sno());
			
			// ���� ���̺� �Ƶ��� ��û�� �ð�
			String now_yyyy = dto.getRv_time().substring(0,8);		
			String time1 = dto.getRv_time().substring(8,10);
			String time2 = dto.getRv_time().substring(10,12);
			
			// ���� ���̺� �����ִ� ���� �����ð�
			String open1 = s_dto.getOpentime().substring(0,2);
			String open2 = s_dto.getOpentime().substring(2,4);
			String close1 = s_dto.getClosetime().substring(0,2);
			String close2 = s_dto.getClosetime().substring(2,4);
			
			// �湮�����ð� 1�ð� �̳��� ���� �����̳� ��� ���ϰ� ��¥ ����
			SimpleDateFormat yydatefm = new SimpleDateFormat("yyyyMMdd"); // ���ڿ� -> date
			SimpleDateFormat yystringfm = new SimpleDateFormat("yyyy-MM-dd"); // date -> ���ڿ�
			
			SimpleDateFormat datefm = new SimpleDateFormat("yyyyMMddhhmm"); // ���ڿ� -> date
			SimpleDateFormat stringfm = new SimpleDateFormat("yyyy-MM-dd a hh:mm"); // date -> ���ڿ�
			
			Date yyyy = null;
			Date currentTime = new Date(); // ���糯¥
			Date dbTime = null;
			String now = null;
			int timeCheck = 0;
			try {
				// yyyy-mm-dd
				yyyy = yydatefm.parse(now_yyyy);
				now_yyyy = yystringfm.format(yyyy);
				
				// ���� �ð� ���ڿ� -> date�������� -> ����ϰ� -> ��Ʈ������ set
				currentTime = Calendar.getInstance().getTime(); // ���� �ð� ex) 202006150202 ����Ͻú�
				dbTime = datefm.parse(dto.getRv_time()); // ���¥
				
				long diff = dbTime.getTime() - currentTime.getTime();

				long m = diff / 60000; // �� ���ϱ�
				
				if(m < 60) {
					timeCheck = -1; // �������, ���� �Ұ���
				} else {
					timeCheck = 1; // ����
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
		String loginid = (String) session.getAttribute("loginUserID"); // ���� ���� ���̵�

	}

}
