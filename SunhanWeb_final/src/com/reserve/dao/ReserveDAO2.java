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

public class ReserveDAO2 {

	private static ReserveDAO2 dao;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private ReserveDAO2() {
	} // 싱글톤 패턴이라 생성자 숨김

	public static synchronized ReserveDAO2 getInstance() {
		if (dao == null) {
			dao = new ReserveDAO2();
		}
		return dao;
	}

	public Connection getConnection() {
		// 커넥션 풀 찾음
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

	// -----------------아동용 예약-------------------//

	// 예약가능한지 체크 (rs_available에 따라 판단)
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
				if(rs.getString("rs_available").equals("Y")) {
					// 예약가능상태
					dto = new ReserveStatusDTO();
					dto.setRs_userid(rs.getString("rv.rv_userid"));
					dto.setRs_storeid(rs.getString("rv.rv_sno"));
					dto.setRs_available(rs.getString("rs.rs_available"));
				
				} else if(rs.getString("rs_available").equals("N")){
					// 예약불가능
					dto = new ReserveStatusDTO();
					dto.setRs_userid(rs.getString("rv.rv_userid"));
					dto.setRs_storeid(rs.getString("rv.rv_sno"));
					dto.setRs_available(rs.getString("rs.rs_available"));
				}
			} else {
				// 결과가 없을때, 한번도 예약한적 없을때
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
	
	
	// 예약 상세페이지 (바로예약시) 특정가게에 예약한 결과
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
	
	// 예약상태, 방문여부 변경
	public int reserveUpdate(ReserveDTO rv_dto, int result) {
		pstmt = null;
		
		int c = 0;
		
		try {
			conn = this.getConnection();
			
			StringBuffer sql = new StringBuffer();
			
			// 예약취소
			if(result == 4) { 
				sql.append("UPDATE reserve SET rv_status=? WHERE rv_rno=?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setInt(1, rv_dto.getRv_status());
				pstmt.setInt(2, rv_dto.getRv_rno());
				
				c = pstmt.executeUpdate();
				
				// 초기화
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
			
			else if(result == -1) { // 상태변경 x, 아동이 예약정보바꾸는거
				sql.append("UPDATE reserve SET rv_time=?, rv_personnel=? WHERE rv_rno=?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, rv_dto.getRv_time());
				pstmt.setInt(2, rv_dto.getRv_personnel());
				pstmt.setInt(3, rv_dto.getRv_rno());
				
				c = pstmt.executeUpdate();
				
				// 초기화
				pstmt.clearParameters(); 
				sql.delete(0, sql.toString().length());
			}
			else if(result == 2 || result == 3){ // 2=승인, 3=거절
				sql.append("UPDATE reserve SET rv_status=?, rv_reason=? WHERE rv_rno=?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setInt(1, rv_dto.getRv_status());
				pstmt.setString(2, rv_dto.getRv_reason());
				pstmt.setInt(3, rv_dto.getRv_rno());
				
				c = pstmt.executeUpdate();
				
				// 초기화
				if(result == 3) { // 거절
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
			}
			
			
			else if(result == -2) { // 방문변경
				sql.append("UPDATE reserve SET rv_visit=? WHERE rv_rno=?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setInt(1, rv_dto.getRv_visit());
				pstmt.setInt(2, rv_dto.getRv_rno());
				
				c = pstmt.executeUpdate();
				
				// 초기화
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
	
	
	// 예약 insert
	public int reserveInsert(ReserveDTO rv_dto, ReserveStatusDTO rs_dto, int result) {
		pstmt = null;
		
		int c = 0;
		
		try {
			conn = this.getConnection();
			
			StringBuffer sql = new StringBuffer();
			
			if(result == 0) {
				// 한번도 예약한 적 없는 신규
				
				sql.append("INSERT INTO reserve(rv_sno, rv_time, rv_userid, rv_personnel, rv_status) VALUES(?,?,?,?,?)");
				
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, rv_dto.getRv_sno());
				pstmt.setString(2, rv_dto.getRv_time());
				pstmt.setString(3, rv_dto.getRv_userid());
				pstmt.setInt(4, rv_dto.getRv_personnel());
				pstmt.setInt(5, rv_dto.getRv_status());
				
				c = pstmt.executeUpdate();
				// 초기화
				pstmt.clearParameters(); 
				sql.delete(0, sql.toString().length());
				
				// 신규아동은 status 테이블에도 데이터가 없으니까 insert
				sql.append("INSERT INTO reserve_status(rs_userid, rs_storeid, rs_available) VALUES(?,?,'N')");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, rs_dto.getRs_userid());
				pstmt.setString(2, rs_dto.getRs_storeid());

				c += pstmt.executeUpdate();
				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
				
			} else if(result == 1) {
				// 기존에 예약한 적 있지만 현재 예약 가능한 상태
				sql.append("INSERT INTO reserve(rv_sno, rv_time, rv_userid, rv_personnel, rv_status) VALUES(?,?,?,?,?)");
				
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, rv_dto.getRv_sno());
				pstmt.setString(2, rv_dto.getRv_time());
				pstmt.setString(3, rv_dto.getRv_userid());
				pstmt.setInt(4, rv_dto.getRv_personnel());
				pstmt.setInt(5, rv_dto.getRv_status());
				
				c = pstmt.executeUpdate();
				// 초기화
				pstmt.clearParameters(); 
				sql.delete(0, sql.toString().length());
				
				// 기존에 있는 데이터는 있으니까 status 테이블 update 
				sql.append("UPDATE reserve_status SET rs_available='N' WHERE rs_userid=? AND rs_storeid=?");
				System.out.println("업데이트 됐나?");
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
	
	// 아동이 예약한 전체 리스트 (아동전용 예약리스트)
	public List<ReserveChildDTO> reserveChildList(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;
				
		List<ReserveChildDTO> list = new ArrayList<ReserveChildDTO>();
			
		// 검색조건에 필요한 것들
		String search_userid = (String) listObj.get("search_userid");
		String start_date = (String) listObj.get("start_date");
		String end_date = (String) listObj.get("end_date");
		String status_option = (String) listObj.get("status_option"); 
		String visit_option = (String) listObj.get("visit_option");
		// 페이징
		int start = (Integer) listObj.get("start");
		// 아동 아이디
		String rv_userid = (String) listObj.get("rv_userid");
			
		try {
			conn = this.getConnection();
					
			StringBuffer sql = new StringBuffer();
					
			if(status_option == "0" || status_option == null && visit_option == "0" || visit_option == null) { // 전체보기
				if(start_date == null && end_date == null && search_userid == null) {
					sql.append("SELECT rv.*, s.shopname FROM reserve as rv INNER JOIN store as s")
					.append(" ON rv.rv_sno = s.userid")
					.append(" WHERE rv.rv_userid = ?")
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
	
	// 게시글 총 갯수 (검색도 따로)
	public int ChildtotalCount(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;
		
		int count = 0;
		
		// 검색조건에 필요한 것들
		String search_userid = (String) listObj.get("search_userid");
		String start_date = (String) listObj.get("start_date");
		String end_date = (String) listObj.get("end_date");
		String status_option = (String) listObj.get("status_option"); 
		String visit_option = (String) listObj.get("visit_option");
		// 아동 아이디
		String rv_userid = (String) listObj.get("rv_userid");
		
		try {
			conn = this.getConnection();
			StringBuffer sql = new StringBuffer();
			
			if(status_option == "0" || status_option == null && visit_option == "0" || visit_option == null) { // 전체보기
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
	
	
	
	
	
	// -----------------후원자용 예약-------------------//
	
	// 후원자 가게로 예약된 전체 리스트 (후원자전용 예약리스트)
	public List<ReserveDTO> reserveSupproterList(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;
			
		List<ReserveDTO> list = new ArrayList<ReserveDTO>();
		
		// 검색조건에 필요한 것들
		String search_userid = (String) listObj.get("search_userid");
		String start_date = (String) listObj.get("start_date");
		String end_date = (String) listObj.get("end_date");
		String status_option = (String) listObj.get("status_option"); 
		String visit_option = (String) listObj.get("visit_option");
		// 페이징
		int start = (Integer) listObj.get("start");
		// 가게 아이디
		String rv_sno = (String) listObj.get("rv_sno");
		
		try {
			conn = this.getConnection();
				
			StringBuffer sql = new StringBuffer();
				
			if(status_option == "0" || status_option == null && visit_option == "0" || visit_option == null) { // 전체보기
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
	
	// 게시글 총 갯수 (검색도 따로)
	public int SupportertotalCount(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;

		int count = 0;
		
		// 검색조건에 필요한 것들
		String search_userid = (String) listObj.get("search_userid");
		String start_date = (String) listObj.get("start_date");
		String end_date = (String) listObj.get("end_date");
		String status_option = (String) listObj.get("status_option"); 
		String visit_option = (String) listObj.get("visit_option");
		// 가게 아이디
		String rv_sno = (String) listObj.get("rv_sno");
		
		try {
			conn = this.getConnection();
			StringBuffer sql = new StringBuffer();
			
			if(status_option == "0" || status_option == null && visit_option == "0" || visit_option == null) { // 전체보기
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
	
	// 예약 리스트에서 상세화면 요청
	public ReserveDTO reserveDetail(int rno) {
		pstmt = null;
		rs = null;
		
		ReserveDTO dto = null;

		try {
			conn = this.getConnection();
			
			String sql = "SELECT * FROM reserve WHERE rv_rno = ?";
	
			pstmt = conn.prepareStatement(sql.toString());
				
			pstmt.setInt(1, rno);
				
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dto = new ReserveDTO();
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
	

	
}
