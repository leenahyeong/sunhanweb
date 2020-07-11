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

		// ���� ���ε� �ϱ� ���� cos.jar �߰� �� ��ü ����
		MultipartRequest multi = null;

		// ���� �ִ� ������(3�ް�)
		int size = 3 * 1024 * 1024;

		// ���ε� �� ���� tomcat ���� ���
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
				file = multi.getFilesystemName("newthumbnail"); // ����� ��������
				
				int tw = 350;
				
				Image originalImg = ImageIO.read(new File(path + "/" + file));
			    int imgw = originalImg.getWidth(null);
			    int imgh = originalImg.getHeight(null);
				
			    double ratio = (double) tw / (double) imgw;
			    int w = (int) (imgw * ratio);
			    int h = (int) (imgh * ratio);
			    
			    Image resize = originalImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
			    
			    String now = new SimpleDateFormat("yyMMddHmsS").format(new Date()); 

			    // ���̹�������
			    thumb = "thumb" +now+ file;
			    BufferedImage newImg = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
			    Graphics g = newImg.getGraphics();
			    g.drawImage(resize, 0, 0, null);
			    g.dispose();
			    ImageIO.write(newImg, "png", new File(path + "/" + thumb));
				
			    // ������¡ �� ���� ������ ����
			    File originImg = new File(path + "/" + file);
			    if (originImg.exists()) {
			    	originImg.delete();
				}
			    
			    // �� ������� �����ϱ� ���� ������� ����
			    File dbthumb = new File(path + "/" + dto.getThumbnail());
			    if(dbthumb.exists()) {
			    	dbthumb.delete();
			    }
			    
			    dto.setThumbnail(thumb);
			} 
			
			// ��ӳ�Ʈ�� ���ε� �� �̹����� ����
			String summer = multi.getFilesystemName("files"); // ��ӳ�Ʈ �̹���
			String summerFull = path + "/" + summer;
			File summerFile = new File(summerFull);
			if (summerFile.exists()) {
				summerFile.delete();
			}
			
		} catch (Exception e) {
			System.out.println("�����ʰ�");
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
