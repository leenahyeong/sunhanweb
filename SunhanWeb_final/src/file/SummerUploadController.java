package file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.UUID;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/summerupload.do")
public class SummerUploadController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		String path = this.getServletContext().getRealPath("/summerimg");
		int size = 10 * 1024 * 1024; // 업로드 사이즈 10M

		String fileName = "";

		try {
			// 파일 업로드 후 이름 가져옴
			MultipartRequest multi = new MultipartRequest(request, path, size, "UTF-8", new DefaultFileRenamePolicy());
			Enumeration files = multi.getFileNames();
			String file = (String) files.nextElement();
			fileName = multi.getFilesystemName(file);
		} catch(Exception e) {
			e.printStackTrace();
		}

		String uploadPath = "/SunhanWeb/summerimg/" + fileName;

		JSONObject obj = new JSONObject();
		obj.put("url", uploadPath);

		response.setContentType("application/json");
		out.print(obj.toString());
	}

}
