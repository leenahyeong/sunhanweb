package com.store.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.store.dto.StoreVO;
import com.store.dto.StroeVOS;
import com.sumhan.dao.SunhansDAO;
import com.sumhan.dto.SunhansVO;


public class StoreDAO {
	private static StoreDAO instance =new StoreDAO();
	
	public static StoreDAO getInstance(){
		return instance;
	}
	public Connection getConnection() throws Exception{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String dbURL = "jdbc:mysql://3.12.173.221:3306/projectsd?&characterEncoding=UTF-8";
			String dbID = "mysqluser";
			String dbPW = "user123";
        
			Connection conn =  DriverManager.getConnection(dbURL, dbID, dbPW);
        
			
			if(conn!=null)
			{	
				//System.out.println("연결 : " + conn);
			    //System.out.println("�뿰寃곗젙蹂�(toString) : " + conn.toString());

			return conn;
			}
		}
		catch(ClassNotFoundException e) {
			//System.out.println("�뱶�씪�씠踰� 濡쒕뱶 �떎�뙣");
			e.printStackTrace();
		}
		catch(SQLException e) {
			//System.out.println("DB �젒�냽 �떎�뙣");
			e.printStackTrace();
		}
		return null;
	}
	public List<StoreVO> selectAllStore(){
		
		String sql="select * from store order by area asc";
		
		List<StoreVO> list= new ArrayList<StoreVO>();
		
		Connection conn =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				StoreVO pVo=new StoreVO();
				pVo.setSno(rs.getInt("sno"));
				pVo.setShopname(rs.getString("shopname"));;
				pVo.setTopmessage(rs.getString("topmessage"));
				pVo.setAddr(rs.getString("addr"));
				pVo.setArea(rs.getString("area"));
				pVo.setComments(rs.getString("comments"));
				pVo.setInformation(rs.getString("information"));
				pVo.setFood(rs.getString("food"));
				pVo.setPrice(rs.getString("price"));
				pVo.setEnter(rs.getDate("enter"));
				pVo.setUserid(rs.getString("userid"));
				pVo.setOpentime(rs.getString("opentime"));
				pVo.setClosetime(rs.getString("closetime"));
				pVo.setStorePhone(rs.getString("StorePhone"));
				list.add(pVo);
			}
		}catch (Exception e) {
			// TODO: handle exception
		} try {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<StroeVOS> selectAllStores(String area){
		
		String sql=" select  a.shopname,a.topmessage,a.price,a.addr,a.userid,b.fileRealName,a.StorePhone from store a inner join storefile b on(a.userid=b.userid) where area like ?"
		+ "group by userid";
		ArrayList<StroeVOS> list= new ArrayList<StroeVOS>();
		
		Connection conn =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, area);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				StroeVOS pVo=new StroeVOS();
				pVo.setShopname(rs.getString("shopname"));;
				pVo.setTopmessage(rs.getString("topmessage"));
				pVo.setPrice(rs.getString("price")+"원");
				pVo.setArea(rs.getString("addr"));
				pVo.setUserid(rs.getString("userid"));
				pVo.setFileRealName("store/"+rs.getString("fileRealName"));
				pVo.setStorePhone(rs.getString("StorePhone"));
				list.add(pVo);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} try {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	public int insertStore(StoreVO mVo) {
		int result = -1;
		SimpleDateFormat sysdeta = new SimpleDateFormat ("yyyy-MM-dd");
		Date time= new Date();
		String sysdetas = sysdeta.format(time);
		
		
		String sql = "insert into store(shopname, topmessage, addr, area, comments, information, food, price, enter, userid, opentime, closetime, StorePhone) values(?, ?, ?, ?, ?, ?, ?, ?,'"+sysdetas+"',?,?,?,?)";
		Connection conn = null; 
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mVo.getShopname());
			pstmt.setString(2, mVo.getTopmessage());
			pstmt.setString(3, mVo.getAddr());
			pstmt.setString(4, mVo.getArea());
			pstmt.setString(5, mVo.getComments());
			pstmt.setString(6, mVo.getInformation());
			pstmt.setString(7, mVo.getFood());
			pstmt.setString(8, mVo.getPrice());
			pstmt.setString(9, mVo.getUserid());
			pstmt.setString(10, mVo.getOpentime());
			pstmt.setString(11, mVo.getClosetime());
			pstmt.setString(12,mVo.getStorePhone());
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
	public int updateStore(StoreVO mVo) {
		int result = -1;
		 Date today = new Date();
	        
		 SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		 String dates= date.format(today);
		 
		 SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");
	        
	     SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");
	        
	     java.util.Date tempDate = null;
	        
	        try {
	            tempDate = beforeFormat.parse(dates);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        
	        String transDate = afterFormat.format(tempDate);
	        
	        java.sql.Date d = java.sql.Date.valueOf(dates);
				
	        System.out.println(d);
		String sql = "update store set shopname=?, topmessage=?, addr=?, area=?, comments=?, information=?, food=?, price=?, enter='"+d+"', opentime=?, closetime=?, StorePhone=? WHERE userid=?";
		Connection conn = null; 
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mVo.getShopname());
			pstmt.setString(2, mVo.getTopmessage());
			pstmt.setString(3, mVo.getAddr());
			pstmt.setString(4, mVo.getArea());
			pstmt.setString(5, mVo.getComments());
			pstmt.setString(6, mVo.getInformation());
			pstmt.setString(7, mVo.getFood());
			pstmt.setString(8, mVo.getPrice());
			pstmt.setString(9, mVo.getOpentime());
			pstmt.setString(10, mVo.getClosetime());
			pstmt.setString(11, mVo.getStorePhone());
			pstmt.setString(12, mVo.getUserid());
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
	public StoreVO getStore(String userid) {
		StoreVO mVo = null;
		String sql = "select * from store where userid=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				mVo = new StoreVO();
				mVo.setSno(rs.getInt("sno"));
				mVo.setShopname(rs.getString("shopname"));;
				mVo.setTopmessage(rs.getString("topmessage"));
				mVo.setAddr(rs.getString("addr"));
				mVo.setArea(rs.getString("area"));
				mVo.setComments(rs.getString("comments"));
				mVo.setInformation(rs.getString("information"));
				mVo.setFood(rs.getString("food"));
				mVo.setPrice(rs.getString("price"));
				mVo.setEnter(rs.getDate("enter"));
				mVo.setUserid(rs.getString("userid"));
				mVo.setOpentime(rs.getString("opentime"));
				mVo.setClosetime(rs.getString("closetime"));
				mVo.setStorePhone(rs.getString("StorePhone"));
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
	public int StoreCheck(String userid) {
		int result = -1;
		String sql = "select * from store where userid=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println(userid);
				System.out.println(rs.getString("userid"));
				if (rs.getString("userid") != null && (userid.equalsIgnoreCase(rs.getString("userid"))))
				{
					result=1;
				}
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
	
	
	
	
	//안드용 *******************************
	
	public ArrayList<StoreVO> AndselectAllStores(String area){
		
		String sql=" select * from store";
		ArrayList<StoreVO> list= new ArrayList<StoreVO>();
		
		Connection conn =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				StoreVO mVo=new StoreVO();
				mVo.setSno(rs.getInt("sno"));
				mVo.setShopname(rs.getString("shopname"));;
				mVo.setTopmessage(rs.getString("topmessage"));
				mVo.setAddr(rs.getString("addr"));
				mVo.setArea(rs.getString("area"));
				mVo.setComments(rs.getString("comments"));
				mVo.setInformation(rs.getString("information"));
				mVo.setFood(rs.getString("food"));
				mVo.setPrice(rs.getString("price"));
				mVo.setEnter(rs.getDate("enter"));
				mVo.setUserid(rs.getString("userid"));
				mVo.setOpentime(rs.getString("opentime"));
				mVo.setClosetime(rs.getString("closetime"));
				mVo.setStorePhone(rs.getString("StorePhone"));
				list.add(mVo);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} try {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
}
