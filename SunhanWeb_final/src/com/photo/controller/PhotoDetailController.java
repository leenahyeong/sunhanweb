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
		
		// 조회수
		Cookie[] cookies = request.getCookies();
		// 비교하기 위해 새로운 쿠키
		Cookie viewCookie = null;
		        
		// 쿠키가 있을 경우 
		if (cookies != null && cookies.length > 0) {
		    for (int i = 0; i < cookies.length; i++) {
		    // Cookie의 name이 cookie + reviewNo와 일치하는 쿠키를 viewCookie에 넣어줌 
		    	if (cookies[i].getName().equals("cookie"+bno))
		        	{ 
		               System.out.println("처음 쿠키가 생성한 뒤 들어옴.");
		               viewCookie = cookies[i];
		             }
		    }
		}
		
		// 만일 viewCookie가 null일 경우 쿠키를 생성해서 조회수 증가 로직을 처리함.
        if (viewCookie == null) {    
            System.out.println("cookie 없음");
            
            // 쿠키 생성(이름, 값)
            Cookie newCookie = new Cookie("cookie"+bno, "|" + bno + "|");
                            
            // 쿠키 추가
            response.addCookie(newCookie);

            // 쿠키를 추가 시키고 조회수 증가시킴
            int result = dao.hit(bno);
            
            if(result>0) {
                System.out.println("조회수 증가");
            }else {
                System.out.println("조회수 증가 에러");
            }
        }
        // viewCookie가 null이 아닐경우 쿠키가 있으므로 조회수 증가 로직을 처리하지 않음.
        else {
            System.out.println("cookie 있음");
            
            // 쿠키 값 받아옴.
            String value = viewCookie.getValue();
            
            System.out.println("cookie 값 : " + value);
        }
		
		dto = dao.detail(bno); // 글 디테일
		p_dto = dao.prev(bno); // 이전글
		n_dto = dao.next(bno); // 다음글

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
