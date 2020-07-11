package com.notice.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.NoticeDAO;
import com.board.dto.NoticeDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/noticeupdate.do")
public class NoticeUpdateController extends HttpServlet {
	// ��񿡼� ������ ������ �ѷ��ְ� �������� �����ִ�
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "notice/noticeUpdate.jsp";
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		String paging = request.getParameter("page");
		
		NoticeDAO dao = NoticeDAO.getInstance();
		NoticeDTO dto = dao.detail(bno);
		
		request.setAttribute("dto", dto);
		request.setAttribute("paging", paging);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}

	// ���� ��� ����
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		/* --------- ���ε�---------- */
		MultipartRequest multi = null;
		// ���� �ִ� ������(10�ް�)
		int size = 10 * 1024 * 1024;
		// ���ε� �� ���� tomcat ���� ���
		String path = request.getServletContext().getRealPath("/upload");
		try {
			multi = new MultipartRequest(request, path, size, "UTF-8", new DefaultFileRenamePolicy());
			
			// ��ӳ�Ʈ�� ���ε� �� �̹����� ����
			String summer = multi.getFilesystemName("files"); // ��ӳ�Ʈ �̹���
			String summerFull = path + "/" + summer;
			File summerFile = new File(summerFull);
			if (summerFile.exists()) {
				summerFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		int bno = Integer.parseInt(multi.getParameter("bno"));
		String paging = multi.getParameter("page");

		NoticeDAO dao = NoticeDAO.getInstance();
		NoticeDTO dto = dao.detail(bno); // �� ����
		
		// ���� �� db�� ����� ���� ���� ���� (���̵� ���� �ֵ�)
		String db_file1 = dto.getFile1();
		String db_file2 = dto.getFile2();

		// ������ �޾ƿ� (������ �ִ°� ���� ���Ҷ�)
		String form_file1 = multi.getParameter("file1");
		String form_file2 = multi.getParameter("file2");

		// ������ �ִ��� �����ϰ� �ű⼭ ����ε�
		String form_refile1 = multi.getFilesystemName("refile1");
		String form_refile2 = multi.getFilesystemName("refile2");

		// �ƿ� ������ ������ ��
		String form_newfile1 = multi.getFilesystemName("newfile1");
		String form_newfile2 = multi.getFilesystemName("newfile2");

		String full1 = "";
		String full2 = "";
		
		db_file1 = String.valueOf(db_file1);
		db_file2 = String.valueOf(db_file2);
		form_file1 = String.valueOf(form_file1);
		form_file2 = String.valueOf(form_file2);
		form_refile1 = String.valueOf(form_refile1);
		form_refile2 = String.valueOf(form_refile2);
		form_newfile1 = String.valueOf(form_newfile1);
		form_newfile2 = String.valueOf(form_newfile2);
		

		System.out.println("������ form_file1="+form_file1+" , refile1="+form_refile1+" ,newfile1=" + form_newfile1 +", db=" + db_file1);
		System.out.println("������ form_file2="+form_file2+" , refile2="+form_refile2+" ,newfile2=" + form_newfile2 );

		// ���̵� ���� �ֵ� �ϴ� ������ (����x)
		if(form_file1.equals(db_file1)) {
			// db, form == null
			if(form_file1.equals("null")) {
				dto.setFile1(null);
				// �� ������ ������
				if(!form_newfile1.equals("null")) {
					full1 = path + "/" + form_newfile1;
					dto.setFile1(form_newfile1);
					System.out.println("�����ε� ������ ���� ����" + form_newfile1);
				} else {
					form_newfile1 = null;
					dto.setFile1(form_newfile1);
					System.out.println("�����ε嵵 ���� �׳� ÷�����Ͼ���" + form_newfile1);
				}
				
			} else {
				// �������� ����
				dto.setFile1(form_file1);
				System.out.println("������������");
			}
		} else {
			// �� �ٸ�, ��Ե� db�� ��ȭ�� ���ܾ���
			// ���� ���� ������ �ư� ����ε� ���� , form == null, db = ?.Ȯ����
			// ���� ���� ���� �� ����ε� ����
			if(!form_refile1.equals("null")) {
				full1 = path + "/" + db_file1;
				File file1 = new File(full1);
				if (file1.exists())
					file1.delete();
				
				full1 = path + "/" + form_refile1;
				dto.setFile1(form_refile1);
				System.out.println("����ε� ������ ���� ����" + form_refile1);
			} else {
				full1 = path + "/" + db_file1;
				File file1 = new File(full1);
				if (file1.exists())
					file1.delete();
				
				form_refile1 = null;
				dto.setFile1(form_refile1);
				System.out.println("�������ϻ��� ����ε�� ����" + form_refile1);
			}
		}
		
		// 2. ���̵� ���� �ֵ� �ϴ� ������ (����x)
		if(form_file2.equals(db_file2)) {
			// db, form == null
			if(form_file2.equals("null")) {
				dto.setFile2(null);
				// �� ������ ������
				if(!form_newfile2.equals("null")) {
					full2 = path + "/" + form_newfile2;
					dto.setFile2(form_newfile2);
					System.out.println("�����ε� ������ ���� ����2" + form_newfile2);
				} else {
					form_newfile2 = null;
					dto.setFile2(form_newfile2);
					System.out.println("�����ε嵵 ���� �׳� ÷�����Ͼ���2" + form_newfile2);
				}
				
			} else {
				// �������� ����
				dto.setFile2(form_file2);
				System.out.println("������������2");
			}
		} else {
			// �� �ٸ�, ��Ե� db�� ��ȭ�� ���ܾ���
			// ���� ���� ������ �ư� ����ε� ���� , form == null, db = ?.Ȯ����
			// ���� ���� ���� �� ����ε� ����
			if(!form_refile2.equals("null")) {
				full2 = path + "/" + db_file2;
				File file2 = new File(full2);
				if (file2.exists())
					file2.delete();
				
				full2 = path + "/" + form_refile2;
				dto.setFile2(form_refile2);
				System.out.println("����ε� ������ ���� ����2" + form_refile2);
			} else {
				full2 = path + "/" + db_file2;
				File file2 = new File(full2);
				if (file2.exists())
					file2.delete();
				
				form_refile2 = null;
				dto.setFile2(form_refile2);
				System.out.println("�������ϻ��� ����ε�� ����2" + form_refile2);
			}
		}
		
		
		// �� ���� �޾ƿ� (bno,file1,file2�¹޾ƿ�)
		String subject = multi.getParameter("subject");
		String content = multi.getParameter("content");

		dto.setBno(bno);
		dto.setSubject(subject);
		dto.setContent(content);

		int result = dao.update(dto);

		if (result == 1) {
			System.out.println("���漺��");
			response.sendRedirect("/SunhanWeb/noticedetail.do?bno=" + bno + "&page=" + paging);

		} else {
			System.out.println("�������" + result);
			response.sendRedirect("/SunhanWeb/noticeupdate.do?bno=" + bno + "&page=" + paging);
		}
		request.setAttribute("paging", paging);
	}

}
