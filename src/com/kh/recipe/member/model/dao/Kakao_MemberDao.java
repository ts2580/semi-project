package com.kh.recipe.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.recipe.common.db.JDBCTemplate;
import com.kh.recipe.common.exception.DataAccessException;
import com.kh.recipe.member.model.dto.Kakao_Member;


public class Kakao_MemberDao {

	private JDBCTemplate template = JDBCTemplate.getInstance();

	public Kakao_Member kakaomemberAuthenticate(String userId, Connection conn){
		Kakao_Member kakao_Member = null;	
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from member where user_id = ?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userId);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				kakao_Member = convertAllToKakao_Member(rset);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		
		return kakao_Member;
	}

	public Kakao_Member selectMemberById(String userId, Connection conn) {
		Kakao_Member kakao_Member = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from member where user_id = ?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userId);
			rset = pstm.executeQuery();
			if(rset.next()) {
				kakao_Member = convertAllToKakao_Member(rset);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		
		return kakao_Member;
	}

	public List<Kakao_Member> selectMemberList(Connection conn) {
		List<Kakao_Member> kakaoMemberList = new ArrayList<Kakao_Member>();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		String columns = "user_id, email, phone";
		String query = "select " + columns +" from member";
		
		try {
			pstm = conn.prepareStatement(query);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				Kakao_Member kakao_Member = convertRowToKakaoMember(columns.split(","),rset);
				kakaoMemberList.add(kakao_Member);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		
		return kakaoMemberList;
	}

	public int insertKakaoMember(Kakao_Member kakao_Member, Connection conn){	
		int res = 0;
		PreparedStatement pstm = null;
		String query = "insert into member(user_id,password,email,phone,birth) values(?,?,?,?,?)";
		System.out.println(kakao_Member);
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, kakao_Member.getUserId());
			pstm.setString(2, kakao_Member.getPassword());
			pstm.setString(3, kakao_Member.getUserEmail());
			pstm.setString(4, kakao_Member.getPhone());
			pstm.setDate(5, kakao_Member.getBirth());
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		
		return res;
	}
	
	//userId??? ' or 1=1 or user_id = ' ?????? ??????????????? ?????? ????????? ??????????????? ??????
	//SQL Injection ??????
	//???????????? SQL????????? ???????????? ???????????? DataBase??? ???????????? ??????
	
	//SQL Injection ?????? ?????? ?????? PreparedStatement ??????
	//??????????????? ????????? ??? ?????? ???????????? ?????? ??????
	//????????? ????????? ?????? ???????????? ????????? ???????????? ?????? ??????
	//???????????? ????????? ???????????? ??????????????? ?????? 
	//ex) ->\' or 1=1 or user_id = \'
	/*
	 * public int updateMemberPassword(String userNickName, String userId,
	 * Connection conn) {
	 * 
	 * Statement stmt = null; int res = 0;
	 * 
	 * 
	 * try { Class.forName("oracle.jdbc.driver.OracleDriver"); conn =
	 * DriverManager.getConnection(
	 * "jdbc:oracle:thin:@db202109141233_high?TNS_ADMIN=C:/CODE/Wallet_DB202109141233",
	 * "ADMIN", "2whTpalvmf__"); stmt = conn.createStatement(); String query =
	 * "update member set password = '" + userId + "' " + "where user_id = '" +
	 * userNickName + "'"; res = stmt.executeUpdate(query); } catch
	 * (ClassNotFoundException | SQLException e) { res = -1; throw new
	 * DataAccessException(e); } finally { try { stmt.close(); conn.close(); } catch
	 * (SQLException e) { e.printStackTrace(); } } return res;
	 * 
	 * }
	 */

	public int deleteKakaoMember(String userId, Connection conn) {
		int res = 0;
		
		Statement stmt = null;
		String query = "delete from member where user_id = '" + userId + "'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@db202109141233_high?TNS_ADMIN=C:/CODE/Wallet_DB202109141233", "ADMIN", "2whTpalvmf__");
			stmt = conn.createStatement();
			res = stmt.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			 throw new DataAccessException(e);
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return res;
		
		
	}
	
	private Kakao_Member convertAllToKakao_Member(ResultSet rset) throws SQLException {
		Kakao_Member kakao_Member = new Kakao_Member();
		kakao_Member.setUserId(rset.getString("user_id"));
		kakao_Member.setPassword(rset.getString("password"));
		kakao_Member.setUserEmail(rset.getString("email"));
		kakao_Member.setBirth(rset.getDate("birth"));
		kakao_Member.setPhone(rset.getString("phone"));
		return kakao_Member;
	}
	
	private Kakao_Member convertRowToKakaoMember(String[] columns, ResultSet rset) throws SQLException {
		Kakao_Member kakao_Member = new Kakao_Member();
		for (int i = 0; i < columns.length; i++) {			
			String column = columns[i].toLowerCase();
			column = column.trim();
			
			switch (column) {
			case "user_id": kakao_Member.setUserId(rset.getString("user_id")); break;
			case "password": kakao_Member.setPassword(rset.getString("password")); break;
			case "email" : kakao_Member.setUserEmail(rset.getString("email")); break;
			case "birth" : kakao_Member.setBirth(rset.getDate("birth")); break;
			case "phone" : kakao_Member.setPhone(rset.getString("phone")); break;
			default : throw new SQLException("???????????? ???????????? ??????????????????."); //????????????
			}
		}
		return kakao_Member;
	}
	
	
}
