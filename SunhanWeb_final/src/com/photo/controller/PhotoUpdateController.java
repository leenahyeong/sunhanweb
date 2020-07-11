package com.photo.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.imgscalr.Scalr;

import com.board.dao.PhotoDAO;
import com.board.dto.PhotoDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sumhan.dto.SunhansVO;

@WebServlet("/photoupdate.do")
public class PhotoUpdateController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "photo/photoUpdate.jsp";
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		String paging = request.getParameter("page");
		
		PhotoDAO dao = PhotoDAO.getInstance();
		PhotoDTO dto = dao.detail(bno);
		
		request.setAttribute("dto", dto);
		request.setAttribute("paging", paging);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String file="", thumb="";

		// 파일 업로드 하기 위해 cos.jar 추가 및 객체 생성
		MultipartRequest multi = null;

		// 파일 최대 사이즈(3메가)
		int size = 3 * 1024 * 1024;

		// 업로드 될 실제 tomcat 폴더 경로
		String path = request.getServletContext().getRealPath("/thumbnail");

		int bno = Integer.parseInt(request.getParameter("bno"));
		String paging = request.getParameter("page");
		PhotoDAO dao = PhotoDAO.getInstance();
		PhotoDTO dto = dao.detail(bno);

		try {
			multi = new MultipartRequest(request, path, size, "UTF-8", new DefaultFileRenamePolicy());
			String newThumb = multi.getFilesystemName("newthumbnail");
			newThumb = String.valueOf(newThumb);
			if(!newThumb.equals("null")) {
				file = multi.getFilesystemName("newthumbnail"); // 저장된 원본파일
				
				int tw = 350;
				
				Image originalImg = ImageIO.read(new File(path + "/" + file));
			    int imgw = originalImg.getWidth(null);
			    int imgh = originalImg.getHeight(null);
				
			    double ratio = (double) tw / (double) imgw;
			    int w = (int) (imgw * ratio);
			    int h = (int) (imgh * ratio);
			    
			    Image resize = originalImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
			    
			    String now = new SimpleDateFormat("yyMMddHmsS").format(new Date()); 

			    // 새이미지저장
			    thumb = "thumb" +now+ file;
			    BufferedImage newImg = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
			    Graphics g = newImg.getGraphics();
			    g.drawImage(resize, 0, 0, null);
			    g.dispose();
			    ImageIO.write(newImg, "png", new File(path + "/" + thumb));
				
			    // 리사이징 전 원본 파일은 삭제
			    File originImg = new File(path + "/" + file);
			    if (originImg.exists()) {
			    	originImg.delete();
				}
			    
			    // 새 썸네일이 있으니까 기존 썸네일은 삭제
			    File dbthumb = new File(path + "/" + dto.getThumbnail());
			    if(dbthumb.exists()) {
			    	dbthumb.delete();
			    }
			    
			    dto.setThumbnail(thumb);
			} 
			
			// 썸머노트로 업로드 한 이미지는 삭제
			String summer = multi.getFilesystemName("files"); // 썸머노트 이미지
			String summerFull = path + "/" + summer;
			File summerFile = new File(summerFull);
			if (summerFile.exists()) {
				summerFile.delete();
			}
			
		} catch (Exception e) {
			System.out.println("파일초과");
			e.printStackTrace();
		}
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("loginUserID");
		SunhansVO vo = (SunhansVO) session.getAttribute("loginUser");
		String name = vo.getName();
		
		String subject = multi.getParameter("subject");
		String content = multi.getParameter("content");

		int result = 0;
		if(dto.getId().equals(id)) {
			dto.setSubject(subject);
			dto.setContent(content);
			dto.setName(name);
			result = dao.update(dto);
		}

		
		if (result == 1) {
			response.sendRedirect("/SunhanWeb/photodetail.do?bno=" + bno + "&page=" + paging);
		} else {
			response.sendRedirect("/SunhanWeb/photoupdate.do?bno=" + bno + "&page=" + paging);
		}

	}
}
