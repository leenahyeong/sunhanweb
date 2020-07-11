package storefile;

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

import file.FileDAO;

/**
 * Servlet implementation class storeuploadAction
 */
@WebServlet("/storeupload.do")
public class storeuploadAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public storeuploadAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int setting=(int)session.getAttribute("storesult");
		
		if(setting==1){
		String url="mypage.jsp";
		SunhansVO emp = (SunhansVO) session.getAttribute("loginUser");
		String id = (String) emp.getId();
		
		
		String directory = this.getServletContext().getRealPath("/store/");
		int maxSize =1024*1024*100;
		String encoding ="UTF-8";
		MultipartRequest multipartRequest =new MultipartRequest(request,directory,maxSize,encoding,new DefaultFileRenamePolicy());
		
		Enumeration fileNames=multipartRequest.getFileNames();
		
			while(fileNames.hasMoreElements())
			{
				String parameter=(String) fileNames.nextElement();
				String fileName=multipartRequest.getOriginalFileName(parameter);
				String fileRealName=multipartRequest.getFilesystemName(parameter);
				
				if(fileName==null) 
				{
					continue;
				}
				storefileDAO File= storefileDAO.getInstance();//인스턴스 호출d
				
				System.out.println(fileName);
				System.out.println(fileRealName);
				
				
				int result = File.upload(fileName,fileRealName,id);
			}

			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);
		}
		else{
			String url="mypage.jsp";
			SunhansVO emp = (SunhansVO) session.getAttribute("loginUser");
			String id = (String) emp.getId();
			
			
			String directory = this.getServletContext().getRealPath("/store/");
			int maxSize =1024*1024*100;
			String encoding ="UTF-8";
			MultipartRequest multipartRequest =new MultipartRequest(request,directory,maxSize,encoding,new DefaultFileRenamePolicy());
			
			Enumeration fileNames=multipartRequest.getFileNames();
			storefileDAO File= storefileDAO.getInstance();//인스턴스 호출
			int result = File.DeleteStore(id);
			
			while(fileNames.hasMoreElements())
			{
			String parameter=(String) fileNames.nextElement();
			String fileName=multipartRequest.getOriginalFileName(parameter);
			String fileRealName=multipartRequest.getFilesystemName(parameter);

			if(fileName==null)
			{
				continue;
			}
			
			System.out.println(fileName);
			System.out.println(fileRealName);
			
			int results = File.upload(fileName,fileRealName,id);
			}
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);
		}
		
	}

}
