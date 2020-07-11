package file;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sumhan.dto.SunhansVO;

/**
 * Servlet implementation class uploadAction
 */
@WebServlet("/uploadAction.do")
public class uploadAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public uploadAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		HttpSession session = request.getSession();
		int setting=(int)session.getAttribute("proresult");
		
		if(setting==1){
		SunhansVO emp = (SunhansVO) session.getAttribute("loginUser");
		String id = (String) emp.getId();
		
		
		String directory = this.getServletContext().getRealPath("/profile/");
		
		System.out.println(directory);
		int maxSize =1024*1024*100;
		String encoding ="UTF-8";
		MultipartRequest multipartRequest =new MultipartRequest(request,directory,maxSize,encoding,new DefaultFileRenamePolicy());
		
	
		
		String fileName=multipartRequest.getOriginalFileName("file");
		String fileRealName=multipartRequest.getFilesystemName("file");
		
		FileDAO File= FileDAO.getInstance();//인스턴스 호출d
		
		System.out.println(fileName);
		System.out.println(fileRealName);
		
		int result = File.upload(fileName,fileRealName,id);
		String url="mypage.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
		}
		else{
			SunhansVO emp = (SunhansVO) session.getAttribute("loginUser");
			String id = (String) emp.getId();
			
			
			String directory = this.getServletContext().getRealPath("/profile/");

			System.out.println(directory);
			int maxSize =1024*1024*100;
			String encoding ="UTF-8";
			MultipartRequest multipartRequest =new MultipartRequest(request,directory,maxSize,encoding,new DefaultFileRenamePolicy());
			
		
			
			String fileName=multipartRequest.getOriginalFileName("file");
			String fileRealName=multipartRequest.getFilesystemName("file");
			
			FileDAO File= FileDAO.getInstance();//인스턴스 호출
			
			System.out.println(fileName);
			System.out.println(fileRealName);
			
			int result = File.changeprofile(fileName,fileRealName,id);
			String url="mypage.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);
		}
	}
}
