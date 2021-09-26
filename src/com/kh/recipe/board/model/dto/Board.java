package com.kh.recipe.board.model.dto;

import java.sql.Date;

public class Board {

	private int no; //게시글 번호 -bdIdx
	private String userId; //아이디
	//private String category; //게시글 분류
	private String title; //게시글 제목
	private String content; //내용
	private Date date; //게시날짜
	private int viewCount; //조회수
	
	
	public Board() {
		// TODO Auto-generated constructor stub
	}


	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

	/*
	 * public String getCategory() { return category; }
	 * 
	 * 
	 * public void setCategory(String category) { this.category = category; }
	 */


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public int getViewCount() {
		return viewCount;
	}


	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}


	@Override
	public String toString() {
		return "Board [no=" + no + ", userId=" + userId + ", title=" + title + ", content="
				+ content + ", date=" + date + ", viewCount=" + viewCount + "]";
	}
	
	
	
}
