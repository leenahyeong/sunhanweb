package com.reserve.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.reserve.dto.ReserveChildDTO;
import com.reserve.dto.ReserveDTO;
import com.reserve.dto.ReserveJoinDTO;
import com.reserve.dto.ReserveStatusDTO;

public class ReserveDAO {

	private static ReserveDAO dao;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private ReserveDAO() {
	}

	public static synchronized ReserveDAO getInstance() {
		if (dao == null) {
			dao = new ReserveDAO();
		}
		return dao;
	}

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String dbURL = "*";
			String dbID = "*";
			String dbPW = "*";

			conn = DriverManager.getConnection(dbURL, dbID, dbPW);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public ReserveStatusDTO reserveCheck(String userid, String storeid){
		pstmt = null;
		rs = null;
		
		ReserveStatusDTO dto = null;
		
		String sql = "SELECT rv.rv_sno, rv.rv_userid, rs.rs_available"
				   + " FROM reserve as rv INNER JOIN reserve_status as rs"
				   + " ON rv.rv_userid = rs.rs_userid AND rv.rv_sno = rs.rs_storeid"
				   + " WHERE rv.rv_userid=? AND rv.rv_sno=?";
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userid);
			pstmt.setString(2, storeid);
			
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				dto = new ReserveStatusDTO();
				dto.setRs_userid(rs.getString("rv.rv_userid"));
				dto.setRs_storeid(rs.getString("rv.rv_sno"));
				dto.setRs_available(rs.getString("rs.rs_available"));
				
			} else {
				dto = new ReserveStatusDTO();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
	
	public ReserveDTO reserveResult(String userid, String storeid) {
		pstmt = null;
		rs = null;

		ReserveDTO dto = new ReserveDTO();
		String sql = "SELECT * FROM reserve WHERE rv_userid=? AND rv_sno=? ORDER BY rv_rno DESC";

		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userid);
			pstmt.setString(2, storeid);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setRv_sno(rs.getString("rv_sno"));
				dto.setRv_rno(rs.getInt("rv_rno"));
				dto.setRv_time(rs.getString("rv_time"));
				dto.setRv_userid(rs.getString("rv_userid"));
				dto.setRv_personnel(rs.getInt("rv_personnel"));
				dto.setRv_date(rs.getTimestamp("rv_date"));
				dto.setRv_status(rs.getInt("rv_status"));
				dto.setRv_visit(rs.getInt("rv_visit"));
				dto.setRv_reason(rs.getString("rv_reason"));
			}
			return dto;

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
		return dto;
	}
	
	public int reserveUpdate(ReserveChildDTO rv_dto, int result) {
		pstmt = null;
		
		int c = 0;
		
		try {
			conn = this.getConnection();
			StringBuffer sql = new StringBuffer();

			if(result == 4) { 
				sql.append("UPDATE reserve SET rv_status=? WHERE rv_rno=?");
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setInt(1, 3);
				pstmt.setInt(1, rv_dto.getRv_rno());
				
				c = pstmt.executeUpdate();
				
				pstmt.clearParameters(); 
				sql.delete(0, sql.toString().length());
				
				sql.append("UPDATE reserve_status SET rs_available=? WHERE rs_userid=? AND rs_storeid=?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, "Y");
				pstmt.setString(2, rv_dto.getRv_userid());
				pstmt.setString(3, rv_dto.getRv_sno());
				
				c += pstmt.executeUpdate();
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
			}
			
			else if(result == -1) {
				sql.append("UPDATE reserve SET rv_time=?, rv_personnel=? WHERE rv_rno=?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, rv_dto.getRv_time());
				pstmt.setInt(2, rv_dto.getRv_personnel());
				pstmt.setInt(3, rv_dto.getRv_rno());
				
				c = pstmt.executeUpdate();
				
				pstmt.clearParameters(); 
				sql.delete(0, sql.toString().length());
			}
			else if(result == 2){
				sql.append("UPDATE reserve SET rv_status=? WHERE rv_rno=?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setInt(1, 1);
				pstmt.setInt(2, rv_dto.getRv_rno());
				
				c = pstmt.executeUpdate();
				
				pstmt.clearParameters(); 
				sql.delete(0, sql.toString().length());
			}
			
			else if(result == 3) {
				sql.append("UPDATE reserve SET rv_status=?, rv_reason=? WHERE rv_rno=?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setInt(1, 2);
				pstmt.setString(2, rv_dto.getRv_reason());
				pstmt.setInt(3, rv_dto.getRv_rno());
				
				c = pstmt.executeUpdate();
				
				pstmt.clearParameters(); 
				sql.delete(0, sql.toString().length());
				
				sql.append("UPDATE reserve_status SET rs_available=? WHERE rs_userid=? AND rs_storeid=?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, "Y");
				pstmt.setString(2, rv_dto.getRv_userid());
				pstmt.setString(3, rv_dto.getRv_sno());
				
				c += pstmt.executeUpdate();
				
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
			}
			
			else if(result == -2) {
				sql.append("UPDATE reserve SET rv_visit=? WHERE rv_rno=?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setInt(1, rv_dto.getRv_visit());
				pstmt.setInt(2, rv_dto.getRv_rno());
				
				c = pstmt.executeUpdate();
				
				pstmt.clearParameters(); 
				sql.delete(0, sql.toString().length());
				
				sql.append("UPDATE reserve_status SET rs_available=? WHERE rs_userid=? AND rs_storeid=?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, "Y");
				pstmt.setString(2, rv_dto.getRv_userid());
				pstmt.setString(3, rv_dto.getRv_sno());
				
				c += pstmt.executeUpdate();
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return c;
	}
	
	public int reserveInsert(ReserveDTO rv_dto, ReserveStatusDTO rs_dto, int result) {
		pstmt = null;
		
		int c = 0;
		
		try {
			conn = this.getConnection();
			
			StringBuffer sql = new StringBuffer();
			
			if(result == 0) {

				sql.append("INSERT INTO reserve(rv_sno, rv_time, rv_userid, rv_personnel, rv_status) VALUES(?,?,?,?,?)");
				
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, rv_dto.getRv_sno());
				pstmt.setString(2, rv_dto.getRv_time());
				pstmt.setString(3, rv_dto.getRv_userid());
				pstmt.setInt(4, rv_dto.getRv_personnel());
				pstmt.setInt(5, rv_dto.getRv_status());
				
				c = pstmt.executeUpdate();
				pstmt.clearParameters(); 
				sql.delete(0, sql.toString().length());
				
				sql.append("INSERT INTO reserve_status(rs_userid, rs_storeid, rs_available) VALUES(?,?,'N')");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, rs_dto.getRs_userid());
				pstmt.setString(2, rs_dto.getRs_storeid());

				c += pstmt.executeUpdate();
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
				
			} else if(result == 1) {
				sql.append("INSERT INTO reserve(rv_sno, rv_time, rv_userid, rv_personnel, rv_status) VALUES(?,?,?,?,?)");
				
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, rv_dto.getRv_sno());
				pstmt.setString(2, rv_dto.getRv_time());
				pstmt.setString(3, rv_dto.getRv_userid());
				pstmt.setInt(4, rv_dto.getRv_personnel());
				pstmt.setInt(5, rv_dto.getRv_status());
				
				c = pstmt.executeUpdate();
				pstmt.clearParameters(); 
				sql.delete(0, sql.toString().length());
				
				sql.append("UPDATE reserve_status SET rs_available='N' WHERE rs_userid=? AND rs_storeid=?");
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, rs_dto.getRs_userid());
				pstmt.setString(2, rs_dto.getRs_storeid());

				c += pstmt.executeUpdate();
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return c;
	}
	
	public List<ReserveChildDTO> reserveChildList(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;
				
		List<ReserveChildDTO> list = new ArrayList<ReserveChildDTO>();

		String search_userid = (String) listObj.get("search_userid");
		String start_date = (String) listObj.get("start_date");
		String end_date = (String) listObj.get("end_date");
		String status_option = (String) listObj.get("status_option"); 
		String visit_option = (String) listObj.get("visit_option");

		int start = (Integer) listObj.get("start");
		String rv_userid = (String) listObj.get("rv_userid");
			
		try {
			conn = this.getConnection();
					
			StringBuffer sql = new StringBuffer();
					
			if(status_option == "0" || status_option == null && visit_option == "0" || visit_option == null) { // ��ü����
				if(start_date == null && end_date == null && search_userid == null) {
					sql.append("SELECT rv.*, s.shopname, review.review_no FROM reserve as rv")
					.append(" LEFT OUTER JOIN store as s ON rv.rv_sno = s.userid")
					.append(" LEFT OUTER JOIN review ON rv.rv_rno = review.review_rno")
					.append(" WHERE rv.rv_userid = ? AND (review.review_depth=0 || review.review_depth IS NULL)")
					.append(" ORDER BY rv_rno LIMIT ?, 15");
					
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, rv_userid);
					pstmt.setInt(2, start);
							
					sql.delete(0, sql.toString().length());
				}
			}

			rs = pstmt.executeQuery();

			while(rs.next()) {
				ReserveChildDTO dto = new ReserveChildDTO();
				dto.setRv_sno(rs.getString("rv_sno"));
				dto.setRv_rno(rs.getInt("rv_rno"));
				dto.setRv_time(rs.getString("rv_time"));
				dto.setRv_userid(rs.getString("rv_userid"));
				dto.setRv_personnel(rs.getInt("rv_personnel"));
				dto.setRv_date(rs.getTimestamp("rv_date"));
				dto.setRv_status(rs.getInt("rv_status"));
				dto.setRv_visit(rs.getInt("rv_visit"));
				dto.setRv_reason(rs.getString("rv_reason"));
				dto.setRv_shopname(rs.getString("s.shopname"));
				dto.setRv_reviewno(rs.getInt("review_no"));
				list.add(dto);
			}
			
			return list;

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
		return list;
	}
	
	public int ChildtotalCount(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;
		
		int count = 0;
		
		String search_userid = (String) listObj.get("search_userid");
		String start_date = (String) listObj.get("start_date");
		String end_date = (String) listObj.get("end_date");
		String status_option = (String) listObj.get("status_option"); 
		String visit_option = (String) listObj.get("visit_option");
		String rv_userid = (String) listObj.get("rv_userid");
		
		try {
			conn = this.getConnection();
			StringBuffer sql = new StringBuffer();
			
			if(status_option == "0" || status_option == null && visit_option == "0" || visit_option == null) { // ��ü����
				if(start_date == null && end_date == null && search_userid == null) {
					sql.append("SELECT count(*) FROM reserve WHERE rv_userid=? ORDER BY rv_rno");
					
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, rv_userid);
					
					sql.delete(0, sql.toString().length());
				}
			}
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}

	
	// -----------------�Ŀ��ڿ� ����-------------------//
	
	// �Ŀ��� ���Է� ����� ��ü ����Ʈ (�Ŀ������� ���ฮ��Ʈ)
	public List<ReserveDTO> reserveSupproterList(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;
			
		List<ReserveDTO> list = new ArrayList<ReserveDTO>();
		
		// �˻����ǿ� �ʿ��� �͵�
		String search_userid = (String) listObj.get("search_userid");
		String start_date = (String) listObj.get("start_date");
		String end_date = (String) listObj.get("end_date");
		String status_option = (String) listObj.get("status_option"); 
		String visit_option = (String) listObj.get("visit_option");
		// ����¡
		int start = (Integer) listObj.get("start");
		// ���� ���̵�
		String rv_sno = (String) listObj.get("rv_sno");
		
		try {
			conn = this.getConnection();
				
			StringBuffer sql = new StringBuffer();
				
			if(status_option == "0" || status_option == null && visit_option == "0" || visit_option == null) { // ��ü����
				if(start_date == null && end_date == null && search_userid == null) {
					sql.append("SELECT * FROM reserve WHERE rv_sno=? ORDER BY rv_rno LIMIT ?, 15");
						
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, rv_sno);
					pstmt.setInt(2, start);
						
					sql.delete(0, sql.toString().length());
				}
			}

			rs = pstmt.executeQuery();

			while(rs.next()) {
				ReserveDTO dto = new ReserveDTO();
				dto.setRv_sno(rs.getString("rv_sno"));
				dto.setRv_rno(rs.getInt("rv_rno"));
				dto.setRv_time(rs.getString("rv_time"));
				dto.setRv_userid(rs.getString("rv_userid"));
				dto.setRv_personnel(rs.getInt("rv_personnel"));
				dto.setRv_date(rs.getTimestamp("rv_date"));
				dto.setRv_status(rs.getInt("rv_status"));
				dto.setRv_visit(rs.getInt("rv_visit"));
				dto.setRv_reason(rs.getString("rv_reason"));
				list.add(dto);
			}
			return list;

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
		return list;
	}
	
	// �Խñ� �� ���� (�˻��� ����)
	public int SupportertotalCount(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;

		int count = 0;
		
		// �˻����ǿ� �ʿ��� �͵�
		String search_userid = (String) listObj.get("search_userid");
		String start_date = (String) listObj.get("start_date");
		String end_date = (String) listObj.get("end_date");
		String status_option = (String) listObj.get("status_option"); 
		String visit_option = (String) listObj.get("visit_option");
		// ���� ���̵�
		String rv_sno = (String) listObj.get("rv_sno");
		
		try {
			conn = this.getConnection();
			StringBuffer sql = new StringBuffer();
			
			if(status_option == "0" || status_option == null && visit_option == "0" || visit_option == null) { // ��ü����
				if(start_date == null && end_date == null && search_userid == null) {
					sql.append("SELECT count(*) FROM reserve WHERE rv_sno=? ORDER BY rv_rno");
						
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, rv_sno);
						
					sql.delete(0, sql.toString().length());
				}
			}
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}
	
	// ���� ����Ʈ���� ��ȭ�� ��û
	public ReserveChildDTO reserveDetail(int rno) {
		pstmt = null;
		rs = null;
		
		ReserveChildDTO dto = null;
		
		StringBuffer sql = new StringBuffer();

		try {
			conn = this.getConnection();
			
			sql.append("SELECT rv.*, s.shopname, review.review_no FROM reserve as rv")
			.append(" LEFT OUTER JOIN store as s ON rv.rv_sno = s.userid")
			.append(" LEFT OUTER JOIN review ON rv.rv_rno = review.review_rno")
			.append(" WHERE rv.rv_rno = ?");
	
			pstmt = conn.prepareStatement(sql.toString());
				
			pstmt.setInt(1, rno);
				
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dto = new ReserveChildDTO();
				dto.setRv_sno(rs.getString("rv_sno"));
				dto.setRv_rno(rs.getInt("rv_rno"));
				dto.setRv_time(rs.getString("rv_time"));
				dto.setRv_userid(rs.getString("rv_userid"));
				dto.setRv_personnel(rs.getInt("rv_personnel"));
				dto.setRv_date(rs.getTimestamp("rv_date"));
				dto.setRv_status(rs.getInt("rv_status"));
				dto.setRv_visit(rs.getInt("rv_visit"));
				dto.setRv_reason(rs.getString("rv_reason"));
				dto.setRv_shopname(rs.getString("shopname"));
				dto.setRv_reviewno(rs.getInt("review_no"));
			}

			return dto;

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
		return dto;
	}
	

	
}
