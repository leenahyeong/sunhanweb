 package com.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.board.dto.PTcmtDTO;
import com.board.dto.PhotoDTO;


public class PhotoDAO {

	private static PhotoDAO dao;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private PhotoDAO() {
	} // 싱글톤 패턴이라 생성자 숨김

	public static synchronized PhotoDAO getInstance() {
		if (dao == null) {
			dao = new PhotoDAO();
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

	// 글쓰기
	public int insert(PhotoDTO dto) {
		pstmt = null;
		int result = 0;
		
		String sql = "INSERT INTO photo(subject, content, id, name, thumbnail) VALUES(?,?,?,?,?)";
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getName());
			pstmt.setString(5, dto.getThumbnail());
			
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

	// 상세화면
	public PhotoDTO detail(int bno) {
		pstmt = null;
		rs = null;
		
		PhotoDTO dto = new PhotoDTO();
		String sql = "SELECT * FROM photo WHERE bno=?";

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
				dto.setThumbnail(rs.getString("thumbnail"));
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

	// 이전글
	public PhotoDTO prev(int bno) {
		pstmt = null;
		rs = null;
		
		PhotoDTO dto = new PhotoDTO();
		String sql = "SELECT bno, subject FROM photo WHERE bno < ? ORDER BY bno DESC LIMIT 1";
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

	// 다음글
	public PhotoDTO next(int bno) {
		pstmt = null;
		rs = null;
		
		PhotoDTO dto = new PhotoDTO();
		String sql = "SELECT bno, subject FROM photo WHERE bno > ? ORDER BY bno LIMIT 1";
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

	// 게시글 총 갯수 (검색도 따로)
	public int totalCount(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;

		int count = 0;
		String option = (String) listObj.get("option"); // 검색 옵션
		String keyword = (String) listObj.get("keyword"); // 검색 키워드

		try {
			conn = this.getConnection();
			StringBuffer sql = new StringBuffer();

			if (option == null) {
				sql.append("SELECT count(*) FROM photo");
				pstmt = conn.prepareStatement(sql.toString());

				sql.delete(0, sql.toString().length());
			} else if (option.equals("0")) { // 제목
				sql.append("SELECT count(*) FROM photo WHERE subject like ?");
				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");

				sql.delete(0, sql.toString().length());
			} else if (option.equals("1")) { // 내용
				sql.append("SELECT count(*) FROM photo WHERE content like ?");
				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");

				sql.delete(0, sql.toString().length());
			} else if (option.equals("2")) { // 제목+내용
				sql.append("SELECT count(*) FROM photo WHERE content like ? OR subject like ?");
				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");

				sql.delete(0, sql.toString().length());
			} else if (option.equals("3")) { // 작성자
				sql.append("SELECT count(*) FROM photo WHERE name like ?");
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

	// 게시글 리스트
	public List<PhotoDTO> list(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;

		List<PhotoDTO> list = new ArrayList<PhotoDTO>();
		// put 추가
		String option = (String) listObj.get("option"); // 검색 옵션
		String keyword = (String) listObj.get("keyword"); // 검색 키워드
		int start = (Integer) listObj.get("start");
		int end = (Integer) listObj.get("end");

		try {
			conn = this.getConnection();
			StringBuffer sql = new StringBuffer();

			if (option == null) { // 검색x, 글목록 전체
				sql.append("SELECT @rownum:=@rownum+1 as no,").append(
						" photo.bno, photo.subject, photo.reg_date, photo.id, photo.name, photo.hit, photo.thumbnail")
						.append("").append(" FROM photo WHERE (@rownum:=0)=0")
						.append(" ORDER BY bno DESC LIMIT ?, ?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setInt(1, start);
				pstmt.setInt(2, end); // 9개

				sql.delete(0, sql.toString().length());
			} else if (option.equals("0")) { // 제목 검색
				sql.append("SELECT @rownum:=@rownum+1 as no,").append(
						" photo.bno, photo.subject, photo.reg_date, photo.id, photo.name, photo.hit, photo.thumbnail")
						.append(" FROM photo WHERE (@rownum:=0)=0 and subject like ?").append(" ORDER BY bno DESC")
						.append(" LIMIT ?,?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end); // 9개

				sql.delete(0, sql.toString().length());
			} else if (option.equals("1")) { // 내용 검색
				sql.append("SELECT @rownum:=@rownum+1 as no,").append(
						" photo.bno, photo.subject, photo.reg_date, photo.id, photo.name, photo.hit, photo.thumbnail")
						.append(" FROM photo WHERE (@rownum:=0)=0 and content like ?").append(" ORDER BY bno DESC")
						.append(" LIMIT ?,?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end); // 9개

				sql.delete(0, sql.toString().length());
			} else if (option.equals("2")) { // 제목+내용 검색
				sql.append("SELECT @rownum:=@rownum+1 as no,").append(
						" photo.bno, photo.subject, photo.reg_date, photo.id, photo.name, photo.hit, photo.thumbnail")
						.append(" FROM photo WHERE (@rownum:=0)=0 and subject like ? OR content like ?")
						.append(" ORDER BY bno DESC").append(" LIMIT ?,?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");
				pstmt.setInt(3, start);
				pstmt.setInt(4, end); // 9개

				sql.delete(0, sql.toString().length());
			} else if (option.equals("3")) { // 작성자 검색
				sql.append("SELECT @rownum:=@rownum+1 as no,").append(
						" photo.bno, photo.subject, photo.reg_date, photo.id, photo.name, photo.hit, photo.thumbnail")
						.append(" FROM photo WHERE (@rownum:=0)=0 and name like ?").append(" ORDER BY bno DESC")
						.append(" LIMIT ?,?");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end); // 9개

				sql.delete(0, sql.toString().length());
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PhotoDTO dto = new PhotoDTO();
				dto.setBno(rs.getInt(2));
				dto.setSubject(rs.getString(3));
				dto.setReg_date(rs.getTimestamp(4));
				dto.setId(rs.getString(5));
				dto.setName(rs.getString(6));
				dto.setHit(rs.getInt(7));
				dto.setThumbnail(rs.getString(8));
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

	// 삭제
	public int delete(int bno) {
		pstmt = null;
		int result = 0;

		String sql = "DELETE FROM photo WHERE bno=?";

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

	// 수정
	public int update(PhotoDTO dto) {
		pstmt = null;
		int result = 0;

		String sql = "UPDATE photo SET subject=?, content=?, name=?, thumbnail=? WHERE bno=?";

		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getThumbnail());
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

	// 조회수
	public int hit(int bno) {
		pstmt = null;
		int result = 0;
		
		String sql = "UPDATE photo SET hit=hit+1 WHERE bno=?";

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

	// -----------------코멘트 부분--------------------//

	// 댓글 총 갯수
	public int cmt_count(int cmt_bno) {
		pstmt = null;
		rs = null;
		int count = 0;

		String sql = "SELECT count(*) FROM photo_comment WHERE cmt_bno=?";

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

	// 댓글 작성
	public int cmt_insert(PTcmtDTO dto) {
		pstmt = null;
		int result = 0;

		String sql = "INSERT INTO photo_comment(cmt_bno, cmt_id, cmt_name, cmt_content) VALUES(?,?,?,?)";

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

	
	// 댓글 리스트
	public List<PTcmtDTO> cmt_list(int cmt_bno) {
		pstmt = null;
		rs = null;
		List<PTcmtDTO> list = new ArrayList<PTcmtDTO>();

		String sql = "SELECT * FROM photo_comment WHERE cmt_bno=? ORDER BY cmt_cno";

		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, cmt_bno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PTcmtDTO dto = new PTcmtDTO();
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

	// 댓글 디테일(한 댓글 보기)
	public PTcmtDTO cmt_detail(int cmt_cno) {
		pstmt = null;
		rs = null;
		
		PTcmtDTO dto = new PTcmtDTO();
		
		String sql = "SELECT * FROM photo_comment WHERE cmt_cno=?";
		
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
	
	// 댓글 수정
	public int cmt_update(PTcmtDTO dto) {
		pstmt = null;
		int result = 0;

		String sql = "UPDATE photo_comment SET cmt_name=?, cmt_content=? WHERE cmt_cno=?";

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
	
	// 댓글 삭제
	public void cmt_delete(int cmt_cno) {
		pstmt = null;
		
		String sql = "DELETE FROM photo_comment WHERE cmt_cno=?";
		
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
