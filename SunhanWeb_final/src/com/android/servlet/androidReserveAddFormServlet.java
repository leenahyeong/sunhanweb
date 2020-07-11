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
		String userid = request.getParameter("id"); // ���� �α��� ���� ���� ���̵�
		System.out.println(userid);
		//userid = String.valueOf(userid);
		String storeid = request.getParameter("storeid");

		// �� ���Կ� �����ߴ��� ���ߴ��� üũ
		ReserveDAO dao = ReserveDAO.getInstance();
		ReserveStatusDTO dto = null;
		
		if(!userid.equals("null")) { // ���� �α��� ���� ������ ���� �ƴϸ�(Ȥ�ø𸣴�)
			dto = dao.reserveCheck(userid, storeid);
			
			int result = -2;
			
			String check = dto.getRs_available();
			check = String.valueOf(check);

			if(check.equals("null")) {
				System.out.println("ó�� �� ���Կ� �����ϴ� �Ƶ���");
				result = 0;
			} else if(check.equals("Y")) {
				System.out.println("���డ��");
				result = 1;
			} else if(check.equals("N")) {
				System.out.println("����Ұ���");
				result = -1;
			} 
			
			Date currentTime = new Date();
			String nowyyyy = null;
			String open1 = null, open2 = null;
			String close1 = null, close2 = null;
			
			// �������ϰ� �Ҷ���
			if(result == 0 || result == 1) {
				SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");
				nowyyyy = sf2.format(currentTime); // ����ϸ�
				// ����ð� ��, �� �ڸ���
				open1 = request.getParameter("openTime").substring(0,2);
				open2 = request.getParameter("openTime").substring(2,4);
				close1 = request.getParameter("closeTime").substring(0,2);
				close2 = request.getParameter("closeTime").substring(2,4);
			
				
			} else if(result == -1) {
				System.out.println("�̹� ����� ������");
			}
			
			JSONObject reserveObj = new JSONObject();
			JSONArray arr = new JSONArray();
			
			JSONObject obj = new JSONObject();
			
			obj.put("result", result); // 0, 1, -1 // -1�϶��� jsonX ��� ������ ���Ѵ� = ������ �� ���Կ� ������ �̹� �س��� ó���� �ȵȻ��¶� ���ϴ°Ŵ� �̹� �����ϼ̽��ϴ� ���� �ȴ�
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
