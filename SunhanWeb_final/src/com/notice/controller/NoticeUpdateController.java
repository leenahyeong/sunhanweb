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
	// 디비에서 가져온 데이터 뿌려주고 페이지만 보여주는
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

	// 수정 기능 수행
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		/* --------- 업로드---------- */
		MultipartRequest multi = null;
		// 파일 최대 사이즈(10메가)
		int size = 10 * 1024 * 1024;
		// 업로드 될 실제 tomcat 폴더 경로
		String path = request.getServletContext().getRealPath("/upload");
		try {
			multi = new MultipartRequest(request, path, size, "UTF-8", new DefaultFileRenamePolicy());
			
			// 썸머노트로 업로드 한 이미지는 삭제
			String summer = multi.getFilesystemName("files"); // 썸머노트 이미지
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
		NoticeDTO dto = dao.detail(bno); // 글 정보
		
		// 수정 전 db에 저장된 파일 정보 들고옴 (널이든 값이 있든)
		String db_file1 = dto.getFile1();
		String db_file2 = dto.getFile2();

		// 폼에서 받아옴 (기존에 있는거 수정 안할때)
		String form_file1 = multi.getParameter("file1");
		String form_file2 = multi.getParameter("file2");

		// 기존에 있던거 삭제하고 거기서 재업로드
		String form_refile1 = multi.getFilesystemName("refile1");
		String form_refile2 = multi.getFilesystemName("refile2");

		// 아예 파일이 없었을 때
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
		

		System.out.println("쿼리전 form_file1="+form_file1+" , refile1="+form_refile1+" ,newfile1=" + form_newfile1 +", db=" + db_file1);
		System.out.println("쿼리전 form_file2="+form_file2+" , refile2="+form_refile2+" ,newfile2=" + form_newfile2 );

		// 널이든 값이 있든 일단 같을때 (수정x)
		if(form_file1.equals(db_file1)) {
			// db, form == null
			if(form_file1.equals("null")) {
				dto.setFile1(null);
				// 새 파일이 있을때
				if(!form_newfile1.equals("null")) {
					full1 = path + "/" + form_newfile1;
					dto.setFile1(form_newfile1);
					System.out.println("새업로드 파일이 떠야 정상" + form_newfile1);
				} else {
					form_newfile1 = null;
					dto.setFile1(form_newfile1);
					System.out.println("새업로드도 없어 그냥 첨부파일없음" + form_newfile1);
				}
				
			} else {
				// 기존파일 유지
				dto.setFile1(form_file1);
				System.out.println("기존파일유지");
			}
		} else {
			// 값 다름, 어떻게든 db에 변화가 생겨야함
			// 기존 파일 삭제만 됐고 재업로드 없음 , form == null, db = ?.확장자
			// 기존 파일 삭제 후 재업로드 있음
			if(!form_refile1.equals("null")) {
				full1 = path + "/" + db_file1;
				File file1 = new File(full1);
				if (file1.exists())
					file1.delete();
				
				full1 = path + "/" + form_refile1;
				dto.setFile1(form_refile1);
				System.out.println("재업로드 파일이 떠야 정상" + form_refile1);
			} else {
				full1 = path + "/" + db_file1;
				File file1 = new File(full1);
				if (file1.exists())
					file1.delete();
				
				form_refile1 = null;
				dto.setFile1(form_refile1);
				System.out.println("기존파일삭제 재업로드는 없지" + form_refile1);
			}
		}
		
		// 2. 널이든 값이 있든 일단 같을때 (수정x)
		if(form_file2.equals(db_file2)) {
			// db, form == null
			if(form_file2.equals("null")) {
				dto.setFile2(null);
				// 새 파일이 있을때
				if(!form_newfile2.equals("null")) {
					full2 = path + "/" + form_newfile2;
					dto.setFile2(form_newfile2);
					System.out.println("새업로드 파일이 떠야 정상2" + form_newfile2);
				} else {
					form_newfile2 = null;
					dto.setFile2(form_newfile2);
					System.out.println("새업로드도 없어 그냥 첨부파일없음2" + form_newfile2);
				}
				
			} else {
				// 기존파일 유지
				dto.setFile2(form_file2);
				System.out.println("기존파일유지2");
			}
		} else {
			// 값 다름, 어떻게든 db에 변화가 생겨야함
			// 기존 파일 삭제만 됐고 재업로드 없음 , form == null, db = ?.확장자
			// 기존 파일 삭제 후 재업로드 있음
			if(!form_refile2.equals("null")) {
				full2 = path + "/" + db_file2;
				File file2 = new File(full2);
				if (file2.exists())
					file2.delete();
				
				full2 = path + "/" + form_refile2;
				dto.setFile2(form_refile2);
				System.out.println("재업로드 파일이 떠야 정상2" + form_refile2);
			} else {
				full2 = path + "/" + db_file2;
				File file2 = new File(full2);
				if (file2.exists())
					file2.delete();
				
				form_refile2 = null;
				dto.setFile2(form_refile2);
				System.out.println("기존파일삭제 재업로드는 없지2" + form_refile2);
			}
		}
		
		
		// 폼 정보 받아옴 (bno,file1,file2는받아옴)
		String subject = multi.getParameter("subject");
		String content = multi.getParameter("content");

		dto.setBno(bno);
		dto.setSubject(subject);
		dto.setContent(content);

		int result = dao.update(dto);

		if (result == 1) {
			System.out.println("변경성공");
			response.sendRedirect("/SunhanWeb/noticedetail.do?bno=" + bno + "&page=" + paging);

		} else {
			System.out.println("변경실패" + result);
			response.sendRedirect("/SunhanWeb/noticeupdate.do?bno=" + bno + "&page=" + paging);
		}
		request.setAttribute("paging", paging);
	}

}
