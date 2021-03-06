package com.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.board.dto.FBcmtDTO;
import com.board.dto.FreeboardDTO;


public class FreeboardDAO {

	private static FreeboardDAO dao;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private FreeboardDAO() {} 

	public static synchronized FreeboardDAO getInstance() {
		if (dao == null) {
			dao = new FreeboardDAO();
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

	public int insert(FreeboardDTO dto) {
		pstmt = null;
		int result = 0;
		
		String sql = "INSERT INTO freeboard(subject, content, id, name) VALUES(?,?,?,?)";
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getName());

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

	public FreeboardDTO detail(int bno) {
		pstmt = null;
		rs = null;
		
		FreeboardDTO dto = new FreeboardDTO();
		String sql = "SELECT * FROM freeboard WHERE bno=?";

		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, bno);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setBno(rs.getInt("bno"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getTimestamp("reg_date"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setHit(rs.getInt("hit"));
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

	public FreeboardDTO prev(int bno) {
		pstmt = null;
		rs = null;
		
		FreeboardDTO dto = new FreeboardDTO();
		String sql = "SELECT bno, subject FROM freeboard WHERE bno < ? ORDER BY bno DESC LIMIT 1";
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, bno);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setBno(rs.getInt("bno"));
				dto.setSubject(rs.getString("subject"));
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
		return dto;
	}

	public FreeboardDTO next(int bno) {
		pstmt = null;
		rs = null;
		
		FreeboardDTO dto = new FreeboardDTO();
		String sql = "SELECT bno, subject FROM freeboard WHERE bno > ? ORDER BY bno LIMIT 1";
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, bno);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setBno(rs.getInt("bno"));
				dto.setSubject(rs.getString("subject"));
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
		return dto;

	}

	public int totalCount(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;

		int count = 0;
		String option = (String) listObj.get("option"); 
		String keyword = (String) listObj.get("keyword");

		try {
			conn = this.getConnection();
			StringBuffer sql = new StringBuffer();

			if (option == null) {
				sql.append("SELECT count(*) FROM freeboard");
				pstmt = conn.prepareStatement(sql.toString());

				sql.delete(0, sql.toString().length());
			} else if (option.equals("0")) { 
				sql.append("SELECT count(*) FROM freeboard WHERE subject like ?");
				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");

				sql.delete(0, sql.toString().length());
			} else if (option.equals("1")) { 
				sql.append("SELECT count(*) FROM freeboard WHERE content like ?");
				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");

				sql.delete(0, sql.toString().length());
			} else if (option.equals("2")) {
				sql.append("SELECT count(*) FROM freeboard WHERE content like ? OR subject like ?");
				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");

				sql.delete(0, sql.toString().length());
			} else if (option.equals("3")) { 
				sql.append("SELECT count(*) FROM freeboard WHERE name like ?");
				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");

				sql.delete(0, sql.toString().length());
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

	public List<FreeboardDTO> list(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;

		List<FreeboardDTO> list = new ArrayList<FreeboardDTO>();

		String option = (String) listObj.get("option");
		String keyword = (String) listObj.get("keyword"); 
		int start = (Integer) listObj.get("start");
		int end = (Integer) listObj.get("end");

		try {
			conn = this.getConnection();
			StringBuffer sql = new StringBuffer();

			if (option == null) { 
				sql.append("SELECT @rownum:=@rownum+1 as no,").append(
						" freeboard.bno, freeboard.subject, freeboard.reg_date, freeboard.id, freeboard.name, freeboard.hit")
						.append("").append(" FROM freeboard WHERE (@rownum:=0)=0")
						.append(" ORDER BY bno DESC LIMIT ?, ?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setInt(1, start);
				pstmt.setInt(2, end); 

				sql.delete(0, sql.toString().length());
			} else if (option.equals("0")) { 
				sql.append("SELECT @rownum:=@rownum+1 as no,").append(
						" freeboard.bno, freeboard.subject, freeboard.reg_date, freeboard.id, freeboard.name, freeboard.hit")
						.append(" FROM freeboard WHERE (@rownum:=0)=0 and subject like ?").append(" ORDER BY bno DESC")
						.append(" LIMIT ?,?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end); 

				sql.delete(0, sql.toString().length());
			} else if (option.equals("1")) { 
				sql.append("SELECT @rownum:=@rownum+1 as no,").append(
						" freeboard.bno, freeboard.subject, freeboard.reg_date, freeboard.id, freeboard.name, freeboard.hit")
						.append(" FROM freeboard WHERE (@rownum:=0)=0 and content like ?").append(" ORDER BY bno DESC")
						.append(" LIMIT ?,?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end); 

				sql.delete(0, sql.toString().length());
			} else if (option.equals("2")) { 
				sql.append("SELECT @rownum:=@rownum+1 as no,").append(
						" freeboard.bno, freeboard.subject, freeboard.reg_date, freeboard.id, freeboard.name, freeboard.hit")
						.append(" FROM freeboard WHERE (@rownum:=0)=0 and subject like ? OR content like ?")
						.append(" ORDER BY bno DESC").append(" LIMIT ?,?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");
				pstmt.setInt(3, start);
				pstmt.setInt(4, end); 

				sql.delete(0, sql.toString().length());
			} else if (option.equals("3")) { 
				sql.append("SELECT @rownum:=@rownum+1 as no,").append(
						" freeboard.bno, freeboard.subject, freeboard.reg_date, freeboard.id, freeboard.name, freeboard.hit")
						.append(" FROM freeboard WHERE (@rownum:=0)=0 and name like ?").append(" ORDER BY bno DESC")
						.append(" LIMIT ?,?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end); 
				
				sql.delete(0, sql.toString().length());
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				FreeboardDTO dto = new FreeboardDTO();
				dto.setBno(rs.getInt(2));
				dto.setSubject(rs.getString(3));
				dto.setReg_date(rs.getTimestamp(4));
				dto.setId(rs.getString(5));
				dto.setName(rs.getString(6));
				dto.setHit(rs.getInt(7));
				list.add(dto);
			}
			// return list;
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

	public int delete(int bno) {
		pstmt = null;
		int result = 0;

		String sql = "DELETE FROM freeboard WHERE bno=?";

		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, bno);

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

	public int update(FreeboardDTO dto) {
		pstmt = null;
		int result = 0;

		String sql = "UPDATE freeboard SET subject=?, content=?, id=?, name=? WHERE bno=?";

		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getName());
			pstmt.setInt(5, dto.getBno());

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

	public int hit(int bno) {
		pstmt = null;
		int result = 0;
		
		String sql = "UPDATE freeboard SET hit=hit+1 WHERE bno=?";

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

	
	public int cmt_count(int cmt_bno) {
		pstmt = null;
		rs = null;
		int count = 0;

		String sql = "SELECT count(*) FROM fb_comment WHERE cmt_bno=?";

		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, cmt_bno);

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

	public int cmt_insert(FBcmtDTO dto) {
		pstmt = null;
		int result = 0;

		String sql = "INSERT INTO fb_comment(cmt_bno, cmt_id, cmt_name, cmt_content) VALUES(?,?,?,?)";

		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, dto.getCmt_bno());
			pstmt.setString(2, dto.getCmt_id());
			pstmt.setString(3, dto.getCmt_name());
			pstmt.setString(4, dto.getCmt_content());

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

	public List<FBcmtDTO> cmt_list(int cmt_bno) {
		pstmt = null;
		rs = null;
		List<FBcmtDTO> list = new ArrayList<FBcmtDTO>();

		String sql = "SELECT * FROM fb_comment WHERE cmt_bno=? ORDER BY cmt_cno";

		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, cmt_bno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				FBcmtDTO dto = new FBcmtDTO();
				dto.setCmt_bno(rs.getInt("cmt_bno"));
				dto.setCmt_cno(rs.getInt("cmt_cno"));
				dto.setCmt_id(rs.getString("cmt_id"));
				dto.setCmt_name(rs.getString("cmt_name"));
				dto.setCmt_content(rs.getString("cmt_content"));
				dto.setCmt_regdate(rs.getTimestamp("cmt_regdate"));
				list.add(dto);
			}
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
		return list;
	}

	public FBcmtDTO cmt_detail(int cmt_cno) {
		pstmt = null;
		rs = null;
		
		FBcmtDTO dto = new FBcmtDTO();
		
		String sql = "SELECT * FROM fb_comment WHERE cmt_cno=?";
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, cmt_cno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setCmt_bno(rs.getInt("cmt_bno"));
				dto.setCmt_cno(rs.getInt("cmt_cno"));
				dto.setCmt_id(rs.getString("cmt_id"));
				dto.setCmt_name(rs.getString("cmt_name"));
				dto.setCmt_content(rs.getString("cmt_content"));
				dto.setCmt_regdate(rs.getTimestamp("cmt_regdate"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return dto;
		
	}
	
	public int cmt_update(FBcmtDTO dto) {
		pstmt = null;
		int result = 0;

		String sql = "UPDATE fb_comment SET cmt_name=?, cmt_content=? WHERE cmt_cno=?";

		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getCmt_name());
			pstmt.setString(2, dto.getCmt_content());
			pstmt.setInt(3, dto.getCmt_cno());

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
	
	public void cmt_delete(int cmt_cno) {
		pstmt = null;
		
		String sql = "DELETE FROM fb_comment WHERE cmt_cno=?";
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, cmt_cno);
			
			pstmt.executeUpdate();
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
	}	

}
