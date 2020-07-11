package com.notice.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/noticedown.do")
public class NoticeDownloadController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("file1");
        String fileName2 = request.getParameter("file2");
 
        String folder = this.getServletContext().getRealPath("upload");
        String path = folder + "/" + fileName;
        String path2 = folder + "/" + fileName2;
 
        try {
            File file = new File(path);
            byte b[] = new byte[(int) file.length()];

            response.reset();
            response.setContentType("application/octet-stream");
            

            String encoding = new String(fileName.getBytes("UTF-8"),"8859_1");

            response.setHeader("Content-Disposition", "attachment;filename="+ encoding);
            response.setHeader("Content-Length", String.valueOf(file.length()));
            
            if (file.isFile())
            {
                FileInputStream fileInputStream = new FileInputStream(file);
                ServletOutputStream servletOutputStream = response.getOutputStream();
                
           
                int readNum = 0;
                while ( (readNum = fileInputStream.read(b)) != -1) {
                    servletOutputStream.write(b, 0, readNum);
                }
                
                servletOutputStream.close();
                fileInputStream.close();
            }
            
        } catch (Exception e) {
            System.out.println("Download Exception : " + e.getMessage());
        }
        
        try {
            File file = new File(path2);
            byte b[] = new byte[(int) file.length()];
            
          
            response.reset();
            response.setContentType("application/octet-stream");
            

            String encoding = new String(fileName2.getBytes("UTF-8"),"8859_1");

            response.setHeader("Content-Disposition", "attachment;filename="+ encoding);
            response.setHeader("Content-Length", String.valueOf(file.length()));
            
            if (file.isFile()) 
            {
                FileInputStream fileInputStream = new FileInputStream(file);
                ServletOutputStream servletOutputStream = response.getOutputStream();

                int readNum = 0;
                while ( (readNum = fileInputStream.read(b)) != -1) {
                    servletOutputStream.write(b, 0, readNum);
                }
                
                servletOutputStream.close();
                fileInputStream.close();
            }
            
        } catch (Exception e) {
            System.out.println("Download Exception : " + e.getMessage());
        }
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
