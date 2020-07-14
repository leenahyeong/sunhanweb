package com.android.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class androidReviewUpdateServlet
 */
@WebServlet("/androidreviewupdate.do")
public class androidReviewsUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public androidReviewsUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
         response.setCharacterEncoding("UTF-8");

         PrintWriter out = response.getWriter();

         int c = 0;
         Connection conn = null;
         ResultSet rs = null;
         PreparedStatement pstmt = null;

         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "*";
	    String dbID = "*";
	    String dbPW = "*";

            conn = DriverManager.getConnection(dbURL, dbID, dbPW);


            out.print(conn);
            
            StringBuffer sql = new StringBuffer();

            int admin = Integer.parseInt(request.getParameter("admin"));
            out.print(admin);
            if (admin == 0) {
               sql.append("UPDATE review SET review_score=?, review_content=? WHERE review_no=?");

               pstmt = conn.prepareStatement(sql.toString());
               pstmt.setInt(1, Integer.parseInt(request.getParameter("review_score")));
               pstmt.setString(2, request.getParameter("review_content"));
               pstmt.setInt(3, Integer.parseInt(request.getParameter("review_no")));
               
               c = pstmt.executeUpdate();
               
               out.print(c);
               
            } else if (admin == 1) {
               sql.append("UPDATE review SET review_content=? WHERE review_no=?");

               pstmt = conn.prepareStatement(sql.toString());
               pstmt.setString(1, request.getParameter("review_content"));
               pstmt.setInt(2, Integer.parseInt(request.getParameter("review_no")));
               
               c = pstmt.executeUpdate();
               
               out.print(c);
            }

         } catch (Exception e) {
            e.printStackTrace();
            out.print("오류");
         } finally {
            try {
               if (rs != null)
                  rs.close();
               if (pstmt != null)
                  pstmt.close();
               if (conn != null)
                  conn.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
