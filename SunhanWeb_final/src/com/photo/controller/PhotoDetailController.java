package com.photo.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.PhotoDAO;
import com.board.dto.PhotoDTO;



@WebServlet("/photodetail.do")
public class PhotoDetailController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String url = "photo/photoDetail.jsp";
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		String paging = request.getParameter("page");
		String option = request.getParameter("option");
		String keyword = request.getParameter("keyword");
		
		PhotoDAO dao = PhotoDAO.getInstance();
		PhotoDTO dto = null;
		PhotoDTO p_dto = null;
		PhotoDTO n_dto = null;
		
		// ��ȸ��
		Cookie[] cookies = request.getCookies();
		// ���ϱ� ���� ���ο� ��Ű
		Cookie viewCookie = null;
		        
		// ��Ű�� ���� ��� 
		if (cookies != null && cookies.length > 0) {
		    for (int i = 0; i < cookies.length; i++) {
		    // Cookie�� name�� cookie + reviewNo�� ��ġ�ϴ� ��Ű�� viewCookie�� �־��� 
		    	if (cookies[i].getName().equals("cookie"+bno))
		        	{ 
		               System.out.println("ó�� ��Ű�� ������ �� ����.");
		               viewCookie = cookies[i];
		             }
		    }
		}
		
		// ���� viewCookie�� null�� ��� ��Ű�� �����ؼ� ��ȸ�� ���� ������ ó����.
        if (viewCookie == null) {    
            System.out.println("cookie ����");
            
            // ��Ű ����(�̸�, ��)
            Cookie newCookie = new Cookie("cookie"+bno, "|" + bno + "|");
                            
            // ��Ű �߰�
            response.addCookie(newCookie);

            // ��Ű�� �߰� ��Ű�� ��ȸ�� ������Ŵ
            int result = dao.hit(bno);
            
            if(result>0) {
                System.out.println("��ȸ�� ����");
            }else {
                System.out.println("��ȸ�� ���� ����");
            }
        }
        // viewCookie�� null�� �ƴҰ�� ��Ű�� �����Ƿ� ��ȸ�� ���� ������ ó������ ����.
        else {
            System.out.println("cookie ����");
            
            // ��Ű �� �޾ƿ�.
            String value = viewCookie.getValue();
            
            System.out.println("cookie �� : " + value);
        }
		
		dto = dao.detail(bno); // �� ������
		p_dto = dao.prev(bno); // ������
		n_dto = dao.next(bno); // ������

		request.setAttribute("dto", dto);
		request.setAttribute("paging", paging);
		request.setAttribute("option", option);
		request.setAttribute("keyword", keyword);
		request.setAttribute("p_dto", p_dto);
		request.setAttribute("n_dto", n_dto);

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
