package com.review.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.reserve.dto.ReserveDTO;
import com.review.dto.ReviewDTO;
import com.review.dto.ReviewJoinDTO;

public class ReviewDAO {

	private static ReviewDAO dao;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private ReviewDAO() {
	}

	public static synchronized ReviewDAO getInstance() {
		if (dao == null) {
			dao = new ReviewDAO();
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

	public int reviewInsert(ReviewDTO dto, int admin) {
		pstmt = null;
		rs = null;

		StringBuffer sql = new StringBuffer();
		int result = 0; 

		try {
			conn = this.getConnection();

			if (admin == 0) {
				sql.append("SELECT max(review_group) FROM review");

				pstmt = conn.prepareStatement(sql.toString());

				rs = pstmt.executeQuery();

				Integer maxvalue;

				if (rs.next()) {
					maxvalue = rs.getInt(1) + 1; // 데이터가 있을땐 마지막 그룹번호 + 1
				} else {
					maxvalue = 1;  // 데이터가 없을땐 그룹번호를 1로
				}

				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());

				sql.append(
						"INSERT INTO review(review_group, review_depth, review_rno, review_userid, review_storeid, review_score, review_content)")
						.append(" VALUES(?,?,?,?,?,?,?)");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setInt(1, maxvalue);
				pstmt.setInt(2, admin);
				pstmt.setInt(3, dto.getReview_rno());
				pstmt.setString(4, dto.getReview_userid());
				pstmt.setString(5, dto.getReview_storeid());
				pstmt.setInt(6, dto.getReview_score());
				pstmt.setString(7, dto.getReview_content());

				result = pstmt.executeUpdate();

				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
			}

			else if (admin == 1) {
				sql.append(
						"INSERT INTO review(review_group, review_depth, review_rno, review_userid, review_storeid, review_content)")
						.append(" VALUES(?,?,?,?,?,?)");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setInt(1, dto.getReview_group());
				pstmt.setInt(2, admin);
				pstmt.setInt(3, dto.getReview_rno());
				pstmt.setString(4, dto.getReview_userid());
				pstmt.setString(5, dto.getReview_storeid());
				pstmt.setString(6, dto.getReview_content());

				result = pstmt.executeUpdate();

				pstmt.clearParameters();
				sql.delete(0, sql.toString().length());
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

	public ReviewJoinDTO reviewDetail(int no, int check) {
		pstmt = null;
		rs = null;

		ReviewJoinDTO dto = new ReviewJoinDTO();
	
		StringBuffer sql = new StringBuffer();
		
		try {
			conn = this.getConnection();
			
			if(check == 0) {
				sql.append("SELECT r.*, s.shopname, sh.name FROM review as r")
				.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
				.append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
				.append(" WHERE review_rno=? AND review_depth=0");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setInt(1, no);
				
				sql.delete(0, sql.toString().length());
			}
			else if(check == 1) {
				sql.append("SELECT r.*, s.shopname, sh.name FROM review as r")
				.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
				.append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
				.append(" WHERE review_no=?");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setInt(1, no);
				
				sql.delete(0, sql.toString().length());
			}
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setReview_no(rs.getInt("review_no"));
				dto.setReview_group(rs.getInt("review_group"));
				dto.setReview_depth(rs.getInt("review_depth"));
				dto.setReview_rno(rs.getInt("review_rno"));
				dto.setReview_userid(rs.getString("review_userid"));
				dto.setReview_storeid(rs.getString("review_storeid"));
				dto.setReview_score(rs.getInt("review_score"));
				dto.setReview_content(rs.getString("review_content"));
				dto.setReview_date(rs.getTimestamp("review_date"));
				dto.setReview_shopname(rs.getString("shopname"));
				dto.setReview_username(rs.getString("name"));
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

	public List<ReviewJoinDTO> reviewList(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;

		List<ReviewJoinDTO> list = new ArrayList<ReviewJoinDTO>();

		String search_userid = (String) listObj.get("search_userid");
		String start_date = (String) listObj.get("start_date");
		String end_date = (String) listObj.get("end_date");

		int start = (Integer) listObj.get("start");

		int admin = (Integer) listObj.get("admin");
		String userid = (String) listObj.get("userid");
		int review_no = (Integer) listObj.get("review_no");

		try {
			conn = this.getConnection();

			StringBuffer sql = new StringBuffer();

			if (admin == 0) {
				if (review_no != 0) {
					sql.append("SELECT DISTINCT r.*, s.shopname, sh.name FROM review AS r")
							.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
							.append(" LEFT OUTER JOIN review AS re ON r.review_group = re.review_group")
							.append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
							.append(" WHERE r.review_userid=? AND r.review_no=? ORDER BY review_group DESC, r.review_no LIMIT ?, 15");

					pstmt = conn.prepareStatement(sql.toString());

					pstmt.setString(1, userid);
					pstmt.setInt(2, review_no);
					pstmt.setInt(3, start);

					sql.delete(0, sql.toString().length());
				}

				else {
					if (start_date == "" && end_date == "" && search_userid == "") {

						sql.append("SELECT DISTINCT r.*, s.shopname, sh.name FROM review AS r")
								.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
								.append(" LEFT OUTER JOIN review AS re ON r.review_group = re.review_group")
								.append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
								.append(" WHERE r.review_userid=? ORDER BY review_group DESC, r.review_no LIMIT ?, 15");

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setInt(2, start);

						sql.delete(0, sql.toString().length());
					}


					else if (start_date != "" && end_date != "" && search_userid == "") {
						sql.append("SELECT DISTINCT r.*, s.shopname, sh.name FROM review AS r")
								.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
								.append(" LEFT OUTER JOIN review AS re ON r.review_group = re.review_group")
								.append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
								.append(" WHERE r.review_userid=? AND CONVERT(r.review_date, date) BETWEEN ? AND ?")
								.append(" ORDER BY review_group DESC, r.review_no LIMIT ?, 15");

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setString(2, start_date);
						pstmt.setString(3, end_date);
						pstmt.setInt(4, start);

						sql.delete(0, sql.toString().length());
					}

					else if (start_date == "" && end_date == "" && search_userid != "") {
						sql.append("SELECT DISTINCT r.*, s.shopname, sh.name FROM review AS r")
								.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
								.append(" LEFT OUTER JOIN review AS re ON r.review_group = re.review_group")
								.append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
								.append(" WHERE r.review_userid=? AND s.shopname like ?")
								.append(" ORDER BY review_group DESC, r.review_no LIMIT ?, 15");

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setString(2, "%" + search_userid + "%");
						pstmt.setInt(3, start);

						sql.delete(0, sql.toString().length());
					}


					else if (start_date != "" && end_date != "" && search_userid != "") {
						sql.append("SELECT DISTINCT r.*, s.shopname, sh.name FROM review AS r")
								.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
								.append(" LEFT OUTER JOIN review AS re ON r.review_group = re.review_group")
								.append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
								.append(" WHERE r.review_userid=? AND CONVERT(r.review_date, date) BETWEEN ? AND ?")
								.append(" AND s.shopname like ?")
								.append(" ORDER BY review_group DESC, r.review_no LIMIT ?, 15");

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setString(2, start_date);
						pstmt.setString(3, end_date);
						pstmt.setString(4, "%" + search_userid + "%");
						pstmt.setInt(5, start);

						sql.delete(0, sql.toString().length());
					}

				}
			}

			else if (admin == 1) {
				if (review_no != 0) {
					sql.append("SELECT DISTINCT r.*, s.shopname, sh.name FROM review AS r")
							.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
							.append(" LEFT OUTER JOIN review AS re ON r.review_group = re.review_group")
							.append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
							.append(" WHERE r.review_storeid=? AND r.review_no=? ORDER BY review_group DESC, r.review_no LIMIT ?, 15");

					pstmt = conn.prepareStatement(sql.toString());

					pstmt.setString(1, userid);
					pstmt.setInt(2, review_no);
					pstmt.setInt(3, start);

					sql.delete(0, sql.toString().length());
				}

				else {
					if (start == 0 && start_date == "" && end_date == "" && search_userid == "") {
						sql.append("SELECT DISTINCT r.*, s.shopname, sh.name FROM review AS r")
								.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
								.append(" LEFT OUTER JOIN review AS re ON r.review_group = re.review_group")
								.append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
								.append(" WHERE r.review_storeid=? ORDER BY review_group DESC, r.review_no");

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);

						sql.delete(0, sql.toString().length());
					}
					
					if (start != 0 && start_date == "" && end_date == "" && search_userid == "") {
						sql.append("SELECT DISTINCT r.*, s.shopname, sh.name FROM review AS r")
								.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
								.append(" LEFT OUTER JOIN review AS re ON r.review_group = re.review_group")
								.append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
								.append(" WHERE r.review_storeid=? ORDER BY review_group DESC, r.review_no LIMIT ?, 15");

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setInt(2, start);

						sql.delete(0, sql.toString().length());
					}

					else if (start_date != "" && end_date != "" && search_userid == "") {
						sql.append("SELECT DISTINCT r.*, s.shopname, sh.name FROM review AS r")
								.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
								.append(" LEFT OUTER JOIN review AS re ON r.review_group = re.review_group")
								.append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
								.append(" WHERE r.review_storeid=? AND CONVERT(r.review_date, date) BETWEEN ? AND ?")
								.append(" ORDER BY review_group DESC, r.review_no LIMIT ?, 15");

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setString(2, start_date);
						pstmt.setString(3, end_date);
						pstmt.setInt(4, start);

						sql.delete(0, sql.toString().length());
					}

					else if (start_date == "" && end_date == "" && search_userid != "") {
						sql.append("SELECT DISTINCT r.*, s.shopname, sh.name FROM review AS r")
								.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
								.append(" LEFT OUTER JOIN review AS re ON r.review_group = re.review_group")
								.append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
								.append(" WHERE r.review_storeid=? AND r.review_userid like ?")
								.append(" ORDER BY review_group DESC, r.review_no LIMIT ?, 15");

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setString(2, "%" + search_userid + "%");
						pstmt.setInt(3, start);

						sql.delete(0, sql.toString().length());
					}

					else if (start_date != "" && end_date != "" && search_userid != "") {
						sql.append("SELECT DISTINCT r.*, s.shopname, sh.name FROM review AS r")
								.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
								.append(" LEFT OUTER JOIN review AS re ON r.review_group = re.review_group")
								.append(" LEFT OUTER JOIN sunhans AS sh ON r.review_userid = sh.id")
								.append(" WHERE r.review_storeid=? AND CONVERT(r.review_date, date) BETWEEN ? AND ?")
								.append(" AND r.review_userid like ?")
								.append(" ORDER BY review_group DESC, r.review_no LIMIT ?, 15");

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setString(2, start_date);
						pstmt.setString(3, end_date);
						pstmt.setString(4, "%" + search_userid + "%");
						pstmt.setInt(5, start);

						sql.delete(0, sql.toString().length());
					}
				}
				
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ReviewJoinDTO dto = new ReviewJoinDTO();
				dto.setReview_no(rs.getInt("r.review_no"));
				dto.setReview_group(rs.getInt("r.review_group"));
				dto.setReview_depth(rs.getInt("r.review_depth"));
				dto.setReview_rno(rs.getInt("r.review_rno"));
				dto.setReview_userid(rs.getString("r.review_userid"));
				dto.setReview_storeid(rs.getString("r.review_storeid"));
				dto.setReview_score(rs.getInt("r.review_score"));
				dto.setReview_content(rs.getString("r.review_content"));
				dto.setReview_date(rs.getTimestamp("r.review_date"));
				dto.setReview_shopname(rs.getString("shopname"));
				dto.setReview_username(rs.getString("name"));
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

	public List<Integer> reviewtotalCount(HashMap<String, Object> listObj) {
		pstmt = null;
		rs = null;

		List<Integer> list = new ArrayList<Integer>();
		int count = 0;

		String search_userid = (String) listObj.get("search_userid");
		String start_date = (String) listObj.get("start_date");
		String end_date = (String) listObj.get("end_date");
		int admin = (Integer) listObj.get("admin");
		String userid = (String) listObj.get("userid"); 
		Integer review_no = (Integer) listObj.get("review_no");

		try {
			conn = this.getConnection();
			StringBuffer sql = new StringBuffer();

			if (admin == 0) {
				if (review_no != 0) {
					sql.append("SELECT count(case when review_depth = 0 then 0 end), count(case when review_depth = 1 then 0 end),")
					.append(" round(avg(review_score)) FROM review WHERE review_no=?");
					pstmt = conn.prepareStatement(sql.toString());

					pstmt.setInt(1, review_no);
					
					sql.delete(0, sql.toString().length());
				}

				else {
					if (start_date == "" && end_date == "" && search_userid == "") {
						sql.append("SELECT count(case when review_depth = 0 then 0 end), count(case when review_depth = 1 then 0 end),")
						.append(" round(avg(review_score)) FROM review WHERE review_userid=?");

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);

						sql.delete(0, sql.toString().length());
					}

					else if (start_date != "" && end_date != "" && search_userid == "") {
						sql.append("SELECT count(case when review_depth = 0 then 0 end), count(case when review_depth = 1 then 0 end),")
						.append(" round(avg(review_score)) FROM review WHERE review_userid=? AND CONVERT(review_date, date) BETWEEN ? AND ?");		

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setString(2, start_date);
						pstmt.setString(3, end_date);

						sql.delete(0, sql.toString().length());
					}

					else if (start_date == "" && end_date == "" && search_userid != "") {
						sql.append("SELECT count(case when review_depth = 0 then 0 end), count(case when review_depth = 1 then 0 end),")
						.append(" round(avg(review_score)) FROM review AS r")
						.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
						.append(" WHERE review_userid=? AND s.shopname like ?");
						
						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setString(2, "%" + search_userid + "%");

						sql.delete(0, sql.toString().length());
					}

					else if (start_date != "" && end_date != "" && search_userid != "") {
						sql.append("SELECT count(case when review_depth = 0 then 0 end), count(case when review_depth = 1 then 0 end),")
						.append(" round(avg(review_score)) FROM review AS r")
						.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
						.append(" WHERE review_userid=? AND CONVERT(review_date, date) BETWEEN ? AND ? AND s.shopname like ?");
						
						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setString(2, start_date);
						pstmt.setString(3, end_date);
						pstmt.setString(4, "%" + search_userid + "%");

						sql.delete(0, sql.toString().length());
					}
				}
			} else if (admin == 1) {
				if (review_no != 0) {
					sql.append("SELECT count(case when review_depth = 0 then 0 end), count(case when review_depth = 1 then 0 end),")
					.append(" round(avg(review_score)) FROM review WHERE review_no=?");

					pstmt.setInt(1, review_no);

					sql.delete(0, sql.toString().length());
				}

				else {
					if (start_date == "" && end_date == "" && search_userid == "") {

						sql.append("SELECT count(case when review_depth = 0 then 0 end), count(case when review_depth = 1 then 0 end),")
						.append(" round(avg(review_score)) FROM review WHERE review_storeid=?");

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);

						sql.delete(0, sql.toString().length());
					}

					else if (start_date != "" && end_date != "" && search_userid == "") {

						sql.append("SELECT count(case when review_depth = 0 then 0 end), count(case when review_depth = 1 then 0 end),")
						.append(" round(avg(review_score)) FROM review WHERE review_storeid=? AND CONVERT(review_date, date) BETWEEN ? AND ?");		

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setString(2, start_date);
						pstmt.setString(3, end_date);

						sql.delete(0, sql.toString().length());
					}

					else if (start_date == "" && end_date == "" && search_userid != "") {

						sql.append("SELECT count(case when review_depth = 0 then 0 end), count(case when review_depth = 1 then 0 end),")
						.append(" round(avg(review_score)) FROM review AS r")
						.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
						.append(" WHERE review_storeid=? AND s.shopname like ?");

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setString(2, "%" + search_userid + "%");

						sql.delete(0, sql.toString().length());
					}

					else if (start_date != "" && end_date != "" && search_userid != "") {
						
						sql.append("SELECT count(case when review_depth = 0 then 0 end), count(case when review_depth = 1 then 0 end),")
						.append(" round(avg(review_score)) FROM review AS r")
						.append(" LEFT OUTER JOIN store as s ON r.review_storeid = s.userid")
						.append(" WHERE review_storeid=? AND CONVERT(review_date, date) BETWEEN ? AND ? AND s.shopname like ?");

						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, userid);
						pstmt.setString(2, start_date);
						pstmt.setString(3, end_date);
						pstmt.setString(4, "%" + search_userid + "%");

						sql.delete(0, sql.toString().length());
					}
				}
			}
			rs = pstmt.executeQuery();

			if (rs.next()) {
				list.add(rs.getInt(1));
				list.add(rs.getInt(2));
				list.add(rs.getInt(3));
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
		return list;
	}

	public int reviewUpdate(ReviewDTO dto, int admin) {
		pstmt = null;
		int result = 0;

		StringBuffer sql = new StringBuffer();
		try {
			conn = this.getConnection();

			if (admin == 0) {
				sql.append("UPDATE review SET review_score=?, review_content=? WHERE review_no=?");

				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, dto.getReview_score());
				pstmt.setString(2, dto.getReview_content());
				pstmt.setInt(3, dto.getReview_no());
				
			} else if (admin == 1) {
				sql.append("UPDATE review SET review_content=? WHERE review_no=?");

				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, dto.getReview_content());
				pstmt.setInt(2, dto.getReview_no());
				
			}
			
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
	
	public int reviewDelete(int review_no, int check) {
		pstmt = null;
		int result = 0;

		StringBuffer sql = new StringBuffer();
		try {
			conn = this.getConnection();
			
			if(check == 0) {
				sql.append("DELETE review.* FROM review")
				.append(" LEFT OUTER JOIN review as re")
				.append(" ON review.review_group = re.review_group")
				.append(" WHERE review.review_group = ?");
				
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, review_no);
			}
			
			else if(check == 1) {
				sql.append("DELETE FROM review")
				.append(" WHERE review_no = ?");
				
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, review_no);
			}


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
