package com.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.board.dto.NoticeDTO;

public class NoticeDAO {
	
	private static NoticeDAO dao;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private NoticeDAO() {} // �̱��� �����̶� ������ ����
	
	public static synchronized NoticeDAO getInstance() {
		if (dao == null) {
			dao = new NoticeDAO();
		}
		return dao;
	}

	public Connection getConnection() {
		// Ŀ�ؼ� Ǯ ã��
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String dbURL = "jdbc:mysql://3.12.173.221:3306/projectsd?&characterEncoding=UTF-8";
			String dbID = "hyeong";
			String dbPW = "user123";
			
			conn =  DriverManager.getConnection(dbURL, dbID, dbPW);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	// �۾���
	public int insert(NoticeDTO dto) {
		int result=0;
		pstmt = null;
		String sql = "INSERT INTO notice(subject, content, file1, file2) VALUES(?,?,?,?)";
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getFile1());
			pstmt.setString(4, dto.getFile2());	
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// ��ȭ��
	public NoticeDTO detail(int bno) {
		pstmt = null;
		rs = null;
		NoticeDTO dto = new NoticeDTO();
		String sql="SELECT * FROM notice WHERE bno=?";
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setBno(rs.getInt("bno"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getTimestamp("reg_date"));
				dto.setFile1(rs.getString("file1"));
				dto.setFile2(rs.getString("file2"));
				dto.setHit(rs.getInt("hit"));
			}
			return dto;
			
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
		return dto;
	}
	
	// ������
	public NoticeDTO prev(int bno) {
		pstmt = null;
		rs = null;
		NoticeDTO dto = new NoticeDTO();
		String sql = "SELECT bno, subject FROM notice WHERE bno < ? ORDER BY bno DESC LIMIT 1";
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setBno(rs.getInt("bno"));
				dto.setSubject(rs.getString("subject"));
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
	
	// ������
	public NoticeDTO next(int bno) {
		pstmt = null;
		rs = null;
		NoticeDTO dto = new NoticeDTO();
		String sql = "SELECT bno, subject FROM notice WHERE bno > ? ORDER BY bno LIMIT 1";
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setBno(rs.getInt("bno"));
				dto.setSubject(rs.getString("subject"));
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
	
	// �Խñ� �� ���� (�˻��� ����)
	public int totalCount(HashMap<String, Object> listObj) { 
		int count = 0;
		pstmt = null;
		rs = null;
		
		String option = (String) listObj.get("option"); // �˻� �ɼ�
		String keyword = (String) listObj.get("keyword"); // �˻� Ű����
		
		try {
			conn = this.getConnection();
			StringBuffer sql = new StringBuffer();
			
			if(option == null) {
				sql.append("SELECT count(*) FROM notice");
				pstmt = conn.prepareStatement(sql.toString());
				
				sql.delete(0, sql.toString().length());
			}
			else if(option.equals("0")) { // ����
				sql.append("SELECT count(*) FROM notice WHERE subject like ?");
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, "%"+keyword+"%");
				
				sql.delete(0, sql.toString().length());
			}
			else if(option.equals("1")) { // ����
				sql.append("SELECT count(*) FROM notice WHERE content like ?");
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, "%"+keyword+"%");
				
				sql.delete(0, sql.toString().length());
			}
			else if(option.equals("2")) { // ����+����
				sql.append("SELECT count(*) FROM notice WHERE content like ? OR subject like ?");
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setString(2, "%"+keyword+"%");
				
				sql.delete(0, sql.toString().length());
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) count = rs.getInt(1);
			
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
		return count;
	}
	
	// �Խñ� ����Ʈ
	public List<NoticeDTO> list(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;
		
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		// put �߰�
		String option = (String) listObj.get("option"); // �˻� �ɼ�
		String keyword = (String) listObj.get("keyword"); // �˻� Ű����
		int start = (Integer) listObj.get("start");
		int end = (Integer) listObj.get("end");
		
		try {
			conn = this.getConnection();
			StringBuffer sql = new StringBuffer();
			
			if(option == null) { // �˻�x, �۸�� ��ü
				sql.append("SELECT @rownum:=@rownum+1 as no,")
				.append(" notice.bno, notice.subject, notice.reg_date, notice.file1, notice.file2, notice.hit")
				.append(" FROM notice WHERE (@rownum:=0)=0")
				.append(" ORDER BY bno DESC LIMIT ?, ?");


				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setInt(1, start);
				pstmt.setInt(2, end); // 10��
				
				sql.delete(0, sql.toString().length());
			}
			else if(option.equals("0")){ // ���� �˻�
				sql.append("SELECT @rownum:=@rownum+1 as no,")
				.append(" notice.bno, notice.subject, notice.reg_date, notice.file1, notice.file2, notice.hit")
				.append(" FROM notice WHERE (@rownum:=0)=0 and subject like ?")
				.append(" ORDER BY bno DESC")
				.append(" LIMIT ?,?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end); // 10��
				
				sql.delete(0, sql.toString().length());
			}
			else if(option.equals("1")){ // ���� �˻�
				sql.append("SELECT @rownum:=@rownum+1 as no,")
				.append(" notice.bno, notice.subject, notice.reg_date, notice.file1, notice.file2, notice.hit")
				.append(" FROM notice WHERE (@rownum:=0)=0 and content like ?")
				.append(" ORDER BY bno DESC")
				.append(" LIMIT ?,?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end); // 10��
				
				sql.delete(0, sql.toString().length());
			}
			else if(option.equals("2")){ // ����+���� �˻�
				sql.append("SELECT @rownum:=@rownum+1 as no,")
				.append(" notice.bno, notice.subject, notice.reg_date, notice.file1, notice.file2, notice.hit")
				.append(" FROM notice WHERE (@rownum:=0)=0 and subject like ? OR content like ?")
				.append(" ORDER BY bno DESC")
				.append(" LIMIT ?,?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setInt(3, start);
				pstmt.setInt(4, end); // 10��
				
				sql.delete(0, sql.toString().length());
			}
			
			rs = pstmt.executeQuery();
				
			while(rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				dto.setBno(rs.getInt(2));
				dto.setSubject(rs.getString(3));
				dto.setReg_date(rs.getTimestamp(4));
				dto.setFile1(rs.getString(5));
				dto.setFile2(rs.getString(6));
				dto.setHit(rs.getInt(7));
				list.add(dto);
			}
			//return list;
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
		return list;
	}
	
	// ����
	public int delete(int bno) {
		pstmt = null;
		int result = 0;
		
		String sql = "DELETE FROM notice WHERE bno=?";
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			
			result = pstmt.executeUpdate();
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
		
		return result;
		
	}
	
	 
	// ����
	public int update(NoticeDTO dto) {
		int result = 0;
		pstmt = null;
		 
		String sql = "UPDATE notice SET subject=?, content=?, file1=?, file2=? WHERE bno=?";
		 
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			 
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getFile1());
			pstmt.setString(4, dto.getFile2());
			pstmt.setInt(5, dto.getBno());
			 
			result = pstmt.executeUpdate(); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// ��ȸ��
	public int hit(int bno) {
		pstmt = null;
		int result = 0;
			
		String sql = "UPDATE notice SET hit=hit+1 WHERE bno=?";

		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);

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
	 
}
