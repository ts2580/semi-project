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

	public Kakao_Member memberAuthenticate(String userNickName, String userId, Connection conn){
		Kakao_Member kakao_Member = null;	
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from member where user_id = ? and password = ?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userNickName);
			pstm.setString(2, userId);
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

	public Kakao_Member selectMemberById(String userNickName, Connection conn) {
		Kakao_Member kakao_Member = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from member where user_id = ?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userNickName);
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
		
		String columns = "user_id, email, tell, grade";
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
		String query = "insert into member(user_id,password,email,tell) values(?,?,?,?)";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, kakao_Member.getUserNickName());
			pstm.setString(2, kakao_Member.getUserId());
			pstm.setString(3, kakao_Member.getUserEmail());
			
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		
		return res;
	}
	
	//userId로 ' or 1=1 or user_id = ' 값을 전달받으면 모든 회원의 비밀번호가 수정
	//SQL Injection 공격
	//악의적인 SQL구문을 주입해서 상대방의 DataBase를 공격하는 기법
	
	//SQL Injection 공격 막기 위해 PreparedStatement 사용
	//인스턴스를 생성할 때 쿼리 템플릿을 미리 등록
	//생성시 등록된 쿼리 템플릿의 구조가 변경되는 것을 방지
	//문자열에 대해서 자동으로 이스케이프 처리 
	//ex) ->\' or 1=1 or user_id = \'
	public int updateMemberPassword(String userNickName, String userId, Connection conn) {
		
	      Statement stmt = null;
	      int res = 0;

		
	     try {
	         Class.forName("oracle.jdbc.driver.OracleDriver");
	         conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "bm", "1234");
	         stmt = conn.createStatement();
	         String query = "update member set password = '" + userId + "' "
	                  + "where user_id = '" + userNickName + "'";
	         res = stmt.executeUpdate(query);
	      } catch (ClassNotFoundException | SQLException e) {
	         res = -1;
	         throw new DataAccessException(e);
	      } finally {
	         try {
	            stmt.close();
	            conn.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      return res;
		
	}

	public int deleteKakaoMember(String userNickName, Connection conn) {
		int res = 0;
		
		Statement stmt = null;
		String query = "delete from member where user_id = '" + userNickName + "'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe"
							,"bm","1234");
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
		kakao_Member.setUserNickName(rset.getString("userId"));
		kakao_Member.setUserId(rset.getString("password"));
		kakao_Member.setUserEmail(rset.getString("userEmail"));
		kakao_Member.setGrade(rset.getString("grade"));
		kakao_Member.setIsLeave(rset.getInt("is_leave"));
		kakao_Member.setRegDate(rset.getDate("reg_date"));
		kakao_Member.setRentableDate(rset.getDate("rentable_date"));
		kakao_Member.setTell(rset.getString("tell"));
		return kakao_Member;
	}
	
	private Kakao_Member convertRowToKakaoMember(String[] columns, ResultSet rset) throws SQLException {
		Kakao_Member kakao_Member = new Kakao_Member();
		for (int i = 0; i < columns.length; i++) {			
			String column = columns[i].toLowerCase();
			column = column.trim();
			
			switch (column) {
			case "userNickName": kakao_Member.setUserNickName(rset.getString("user_id")); break;
			case "user_id": kakao_Member.setUserId(rset.getString("password")); break;
			case "email" : kakao_Member.setUserEmail(rset.getString("email")); break;
			case "grade" : kakao_Member.setGrade(rset.getString("grade")); break;
			case "is_leave" : kakao_Member.setIsLeave(rset.getInt("is_leave")); break;
			case "reg_date" : kakao_Member.setRegDate(rset.getDate("reg_date")); break;
			case "rentable_date" : kakao_Member.setRentableDate(rset.getDate("rentable_date")); break;
			case "tell" : kakao_Member.setTell(rset.getString("tell")); break;
			default : throw new SQLException("부적절한 컬럼명을 전달했습니다."); //예외처리
			}
		}
		return kakao_Member;
	}
	
	
}
