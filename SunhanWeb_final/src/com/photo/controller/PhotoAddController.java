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

@WebServlet("/photoadd.do")
public class PhotoAddController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "photo/photoAdd.jsp";
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
		// ���� �ִ� ������(3m)
		int size = 3 * 1024 * 1024;
		// ���ε� �� ���� tomcat ���� ���
		String path = request.getServletContext().getRealPath("/thumbnail");

		try {
			multi = new MultipartRequest(request, path, size, "UTF-8", new DefaultFileRenamePolicy());
			file = multi.getFilesystemName("thumbnail"); // ����� ��������
	
			int tw = 350; // ���� ����
			
			// ���� �̹��� �б�
			Image originalImg = ImageIO.read(new File(path + "/" + file));
		    int imgw = originalImg.getWidth(null);
		    int imgh = originalImg.getHeight(null);
			
		    // ���� ���̿� ���� �̹��� ������¡
		    double ratio = (double) tw / (double) imgw;
		    int w = (int) (imgw * ratio);
		    int h = (int) (imgh * ratio);
		    
		    // getScaledInstance() == ��ȯ�� �̹��� ǰ�� ����
		    Image resize = originalImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		    
		    String now = new SimpleDateFormat("yyMMddHmsS").format(new Date()); 

		    // �� �̹��� ����
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
		
		PhotoDAO dao = PhotoDAO.getInstance();
		PhotoDTO dto = new PhotoDTO();
		
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setId(id);
		dto.setName(name);
		dto.setThumbnail(thumb);

		int result = dao.insert(dto);
		
		if (result == 1) {
			response.sendRedirect("/SunhanWeb/photo.do");
		} else {
			response.sendRedirect("/SunhanWeb/photoadd.do");
		}

	}
}
