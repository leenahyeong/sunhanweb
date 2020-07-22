package com.sumhan.dao;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.sumhan.dto.SunhansVO;

import storefile.storefileDAO;


public class SunhansDAO {

	public SunhansDAO(){
		
	}
	private static SunhansDAO instance =new SunhansDAO();
	
	public static SunhansDAO getInstance(){
		return instance;
	}
	public Connection getConnection() throws Exception{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String dbURL = "*";
			String dbID = "*";
			String dbPW = "*";
        
			Connection conn =  DriverManager.getConnection(dbURL, dbID, dbPW);
        
			
			if(conn!=null)
			{	
			//System.out.println("�뿰寃� �꽦怨듭뿰寃곗젙蹂� : " + conn);
			//System.out.println("�뿰寃곗젙蹂�(toString) : " + conn.toString());

			return conn;
			}
		}
		catch(ClassNotFoundException e) {
			// �뱶�씪�씠踰꾨�� 遺덈윭�삱 �븣 臾몄젣媛� 諛쒖깮
			//System.out.println("�뱶�씪�씠踰� 濡쒕뱶 �떎�뙣");
			e.printStackTrace();
		}
		catch(SQLException e) {
			// DB �젒�냽 �떎�뙣
			//System.out.println("DB �젒�냽 �떎�뙣");
			e.printStackTrace();
		}
		return null;
	}
	// �궗�슜�옄 �씤利앹떆 �궗�슜�븯�뒗 硫붿냼�뱶
		public int userCheck(String userid, String pwd) {
			int result = -1;
			String sql = "select * from sunhans where id=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					if (rs.getString("pw") != null && (pwd.equals(rs.getString("pw"))))
					{
						result=1;
						if(rs.getInt("admin")==1)
						{
							result=2;
						}
					}
					else {
						result = 0;//鍮꾨�踰덊샇媛� �뾾嫄곕굹 留욎��븡�쓬
					}
				}
				else {
					result = -1;//�뾾�뒗�쉶�썝
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
		
		// �븘�씠�뵒濡� �쉶�썝 �젙蹂� 媛��졇�삤�뒗 硫붿냼�뱶
		public SunhansVO getMember(String userid) {
			SunhansVO mVo = null;
			String sql = "select * from sunhans where id=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					mVo = new SunhansVO();
					mVo.setId(rs.getString("id"));;
					mVo.setName(rs.getString("name"));
					mVo.setPass(rs.getString("pw"));
					mVo.setAddr(rs.getString("addr"));
					mVo.setPhone(rs.getString("phone"));
					mVo.setEmail(rs.getString("email"));
					mVo.setPoints(rs.getInt("points"));
					mVo.setAdmin(rs.getInt("admin"));
					mVo.setEnter(rs.getDate("enter"));
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
			return mVo;
		}
		public int getMemberID(String userid) {
			
			String sql = "select id from sunhans where id=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				rs = pstmt.executeQuery();
			
				System.out.println(rs.next()+"�븘�씠�뵒");
				System.out.println(rs.getString(1));
				if(rs.next())
				{
					return -1;
				}
				else{
					return 0;
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
			return -1;
		}
		public String getMemberName(String userid) {
			String imsiname=null;
			String sql = "select name from sunhans where id=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				rs = pstmt.executeQuery();
				rs.next();
				imsiname=rs.getString(1);
				if(rs.next())
				{
					return " ";
				}
				else{
					return imsiname;
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
			return " ";
		}
		public int insertMember(SunhansVO mVo) {
			int result = -1;
			SimpleDateFormat sysdeta = new SimpleDateFormat ("yyyy-MM-dd");
			Date time= new Date();
			String sysdetas = sysdeta.format(time);
			
			
			String sql = "insert into sunhans values(?, ?, ?, ?, ?, ?, 0, ?,'"+sysdetas+"')";
			Connection conn = null; 
			PreparedStatement pstmt = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, mVo.getId());
				pstmt.setString(2, mVo.getName());
				pstmt.setString(3, mVo.getPass());
				pstmt.setString(4, mVo.getAddr());
				pstmt.setString(5, mVo.getPhone());
				pstmt.setString(6, mVo.getEmail());
				pstmt.setInt(7, mVo.getAdmin());
				result = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
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
		public int updateMember(SunhansVO eVo) {
			int result = -1;
			String sql = "update sunhans set name=?, addr=?, phone=?, email=? where id=?";
			Connection conn = null;
			PreparedStatement pstmt = null;	
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, eVo.getName());
				pstmt.setString(2, eVo.getAddr());
				pstmt.setString(3, eVo.getPhone());
				pstmt.setString(4, eVo.getEmail());
				pstmt.setString(5, eVo.getId());
				result = pstmt.executeUpdate();
				System.out.println(result);
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
		public int updatePass(SunhansVO eVo) {
			int result = -1;
			String sql = "update sunhans set pw=? where id=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, eVo.getPass());
				pstmt.setString(2, eVo.getId());
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
		public int confirmID(String userid) {
			int result = -1;
			String sql = "select id from sunhans where id=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					result = 1;
				} else {
					result = -1;
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
	}
