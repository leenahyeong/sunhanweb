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

import file.FileDAO;
import storefile.storefileDAO;

/**
 * Servlet implementation class addStoreServlet
 */           
@WebServlet("/andImageUpload.do")
public class andprofileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public andprofileUpload() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter outs =  response.getWriter();
	     
		FileDAO File= new FileDAO();
	     
		 //이미지를 저장할 경로 입력.
		String directory = this.getServletContext().getRealPath("/profile/");
        
       /// String folderTypePath = "C:/Users/";
        String name = new String();
        String fileName = new String();
        int sizeLimit = 5 * 1024 * 1024; // 5메가까지 제한 넘어서면 예외발생
        int number=0;
      
        
        
        try {
            MultipartRequest multi = new MultipartRequest(request, directory, sizeLimit,
                    new DefaultFileRenamePolicy());
            Enumeration files = multi.getFileNames();
 
            //파일 정보가 있다면
            if (files.hasMoreElements()) {
                name = (String) files.nextElement();
                fileName = multi.getFilesystemName(name);	

                fileName = new String(fileName.getBytes("8859_1"),"utf-8");
                int check=File.frofileImgCheck(name);
                if(check>=1)
                {
                	check=12;
                  int reset=File.ImgReset(name);
                }
                
                outs.print(number+name+":"+fileName);
            }
        } catch (IOException e) {
        }
		int result = File.upload(fileName,fileName,name);
	}
	
}
