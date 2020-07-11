package com.android.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.reserve.dao.ReserveDAO;
import com.reserve.dto.ReserveStatusDTO;

@WebServlet("/androidreserveaddform.do")
public class androidReserveAddFormServlet extends HttpServlet {
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		String userid = request.getParameter("id"); // 현재 로그인 중인 유저 아이디
		System.out.println(userid);
		//userid = String.valueOf(userid);
		String storeid = request.getParameter("storeid");

		// 이 가게에 예약했는지 안했는지 체크
		ReserveDAO dao = ReserveDAO.getInstance();
		ReserveStatusDTO dto = null;
		
		if(!userid.equals("null")) { // 현재 로그인 중인 유저가 널이 아니면(혹시모르니)
			dto = dao.reserveCheck(userid, storeid);
			
			int result = -2;
			
			String check = dto.getRs_available();
			check = String.valueOf(check);

			if(check.equals("null")) {
				System.out.println("처음 이 가게에 예약하는 아동임");
				result = 0;
			} else if(check.equals("Y")) {
				System.out.println("예약가능");
				result = 1;
			} else if(check.equals("N")) {
				System.out.println("예약불가능");
				result = -1;
			} 
			
			Date currentTime = new Date();
			String nowyyyy = null;
			String open1 = null, open2 = null;
			String close1 = null, close2 = null;
			
			// 뭐저장하고 할때는
			if(result == 0 || result == 1) {
				SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");
				nowyyyy = sf2.format(currentTime); // 년월일만
				// 현재시간 시, 분 자르기
				open1 = request.getParameter("openTime").substring(0,2);
				open2 = request.getParameter("openTime").substring(2,4);
				close1 = request.getParameter("closeTime").substring(0,2);
				close2 = request.getParameter("closeTime").substring(2,4);
			
				
			} else if(result == -1) {
				System.out.println("이미 예약된 상태임");
			}
			
			JSONObject reserveObj = new JSONObject();
			JSONArray arr = new JSONArray();
			
			JSONObject obj = new JSONObject();
			
			obj.put("result", result); // 0, 1, -1 // -1일때는 jsonX 고로 예약을 못한다 = 기존에 이 가게에 예약을 이미 해놨고 처리가 안된상태라서 못하는거다 이미 예약하셨습니다 띄우면 된다
			obj.put("open1", open1);
			obj.put("open2", open2);
			obj.put("close1", close1);
			obj.put("close2", close2);
			obj.put("now", nowyyyy);
			
			arr.put(obj);
			
			reserveObj.put("reserve", arr);
			out.print(reserveObj);
			
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
