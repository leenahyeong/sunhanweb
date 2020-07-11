package com.android.servlet;

import java.awt.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sumhan.dao.SunhansDAO;
import com.sumhan.dto.SunhansVO;

/**
 * Servlet implementation class loginservlet
 */
@WebServlet("/androidSunhansUpdateServlet.do")
public class userUpdateservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public userUpdateservlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); response.setContentType("text/html; charset=UTF-8");
		PrintWriter out =  response.getWriter();
		
		
		SunhansVO loginUser=new SunhansVO();

		loginUser.setName(request.getParameter("name"));
		loginUser.setPhone(request.getParameter("phone"));
		loginUser.setEmail(request.getParameter("email"));
		loginUser.setAddr(request.getParameter("addr"));
		loginUser.setId(request.getParameter("userid"));
		
		SunhansDAO DAO =new SunhansDAO();
		
		int result=DAO.updateMember(loginUser);
		
		if(result==0)
		{
			out.print("1");
		}
		else{
			out.print("2");
		}
	}

}