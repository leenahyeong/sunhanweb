package storefile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.store.dto.StroeVOS;
import com.sumhan.dto.SunhansVO;

public class storefileDAO {
	private Connection conn;
	
	private static storefileDAO instance =new storefileDAO();
	
	public static storefileDAO getInstance(){
		return instance;
	}
	public Connection getConnection() throws Exception{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String dbURL = "*";
			String dbID = "*";
			String dbPW = "*";
        
			conn =  DriverManager.getConnection(dbURL, dbID, dbPW);
        
			if(conn!=null)
			{	
				return conn;
			}
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public int upload(String fileName,String fileRealName,String userid)
	{
		
		int result = -1;
		System.out.println(userid);
		String SQL ="INSERT INTO storefile VALUES(?,?,?)";	
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, fileName);
			pstmt.setString(2, fileRealName);
			pstmt.setString(3, userid);
			result= pstmt.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {

			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	public int changeprofile(String fileName,String fileRealName,String userid) {
		int result = -1;
		String sql = "update storefile set fileName=?, fileRealName=? where userid=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fileName);
			pstmt.setString(2, fileRealName);
			pstmt.setString(3, userid);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public String Search(String userid)
	{	
		String realname="";
		System.out.println(userid);
		String SQL ="select * from storefile where userid=?";	
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				realname=rs.getString("fileRealName");
				System.out.println(realname);
				return realname;
			}	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList getSearch(String userid)
	{	
		ArrayList<storefileDTO> storefilename=new ArrayList();
		
		String SQL ="select fileRealName from storefile where userid=?";	
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				storefileDTO pVo=new storefileDTO();
				pVo.setFileRealName("store/"+rs.getString("fileRealName"));
				storefilename.add(pVo);
			}
			return storefilename;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return storefilename;
	}
	public int DeleteStore(String userid) {
		int result = -1;
		String sql = "Delete from storefile where userid=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	
	
	/////////////////////////안드용
	public int StoreImgCheck(String userid) {
		int result = -1;
		String sql = "select *,(count(fileRealName))as count from storefile where userid=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
			
					int count =rs.getInt("count");
					return count; 
				
			}
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
		return result;
	}
	
	public int ImgReset(String userid) {
		int result = -1;
		String sql = "delete from storefile where userid=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			result = pstmt.executeUpdate();
			
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
		return result;
	}
	
}

