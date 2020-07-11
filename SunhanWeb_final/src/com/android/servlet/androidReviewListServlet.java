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

import org.json.JSONArray;
import org.json.JSONObject;

import com.reserve.dto.ReserveChildDTO;
import com.store.dao.StoreDAO;


@WebServlet("/androidreviewlist.do")
public class androidReviewListServlet extends HttpServlet {
   
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setCharacterEncoding("UTF-8");
      response.setContentType("text/html; charset=UTF-8");
      
      Connection conn = null;
      ResultSet rs = null;
      PreparedStatement pstmt=null;
      
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         String dbURL = "jdbc:mysql://3.12.173.221:3306/projectsd?&characterEncoding=UTF-8";
         String dbID = "hyeong";
         String dbPW = "user123";

         conn = DriverManager.getConnection(dbURL, dbID, dbPW);

         PrintWriter out =  response.getWriter();
         
         JSONObject reviewObj = new JSONObject();
         JSONArray arr = new JSONArray();
         JSONArray arr2 = new JSONArray();
               
         StringBuffer sql = new StringBuffer();
         
         sql.append("SELECT DISTINCT r.*, s.shopname, sh.name FROM review AS r")
         .append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
         .append(" LEFT OUTER JOIN review AS re ON r.review_group = re.review_group")
         .append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
         .append(" WHERE r.review_storeid=? ORDER BY review_group DESC, r.review_no");
               
         pstmt = conn.prepareStatement(sql.toString());
         pstmt.setString(1, new String(request.getParameter("userid"))); // storeid
                     
         rs = pstmt.executeQuery();

         while(rs.next()) {
            
         JSONObject obj = new JSONObject();
            
         obj.put("review_no", rs.getInt("review_no"));
         obj.put("review_group", rs.getInt("review_group"));
         obj.put("review_depth", rs.getInt("review_depth"));
         obj.put("review_rno", rs.getInt("review_rno"));
         obj.put("review_userid", rs.getString("review_userid"));
         obj.put("review_storeid", rs.getString("review_storeid"));
         obj.put("review_score", rs.getInt("review_score"));
         obj.put("review_content", rs.getString("review_content"));
         obj.put("review_date", rs.getTimestamp("review_date"));
         obj.put("review_shopname", rs.getString("s.shopname"));
         obj.put("review_username", rs.getString("sh.name"));
         arr.put(obj);
         }
         
         reviewObj.put("reviewList", arr);
         out.print(reviewObj);
         /*
          * // 초기화 pstmt.clearParameters(); sql.delete(0, sql.toString().length());
          * 
          * sql.
          * append("SELECT count(case when review_depth = 0 then 0 end) as '아동', count(case when review_depth = 1 then 0 end) as '사장',"
          * ) .append(" round(avg(review_score)) FROM review WHERE review_storeid=?");
          * 
          * pstmt = conn.prepareStatement(sql.toString()); pstmt.setString(1, new
          * String(request.getParameter("userid")));
          * 
          * rs = pstmt.executeQuery();
          * 
          * while(rs.next()) {
          * 
          * JSONObject obj2 = new JSONObject();
          * 
          * obj2.put("child_count", rs.getInt(1)); obj2.put("supporter_count",
          * rs.getInt(2)); obj2.put("star_avg", rs.getInt(3));
          * 
          * arr2.put(obj2); }
          * 
          * reviewObj.put("reviewCountList", arr2); out.print(reviewObj);
          */
         
      } catch (Exception e) {
         e.printStackTrace();
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

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
   }

}