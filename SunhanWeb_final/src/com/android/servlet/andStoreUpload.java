package com.android.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.store.dao.StoreDAO;

import storefile.storefileDAO;

/**
 * Servlet implementation class addStoreServlet
 */
@WebServlet("/andStoreImageUpload.do")
public class andStoreUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public andStoreUpload() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter outs =  response.getWriter();
		
        storefileDAO File= new storefileDAO();
        
		String directory = this.getServletContext().getRealPath("/store/");
        
        String name = new String();
        
        String fileName = new String();
        
        int sizeLimit = 5 * 1024 * 1024; // 5메가까지 제한 넘어서면 예외발생
        
        int number=0;
       
      //3장이상이면 이미한번한경우여서 다시없애줌
       

        
        
        try {
            MultipartRequest multi = new MultipartRequest(request, directory, sizeLimit,
                    new DefaultFileRenamePolicy());
            Enumeration files = multi.getFileNames();
 
            //파일 정보가 있다면
            if (files.hasMoreElements()) {
                name = (String) files.nextElement();
                fileName = multi.getFilesystemName(name);	
               
                int check=File.StoreImgCheck(name);
                if(check>=3)
                {
                	check=12;
                  int reset=File.ImgReset(name);
                }
                
                outs.print(number+name+":"+fileName+":"+check);	
            }
        } catch (IOException e) {
        }
        
		int result = File.upload(fileName,fileName,name);
	}

}
