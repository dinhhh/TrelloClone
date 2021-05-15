package com.example.demo.WebSocketConfig;

public class CardMessage {
	
	private Long cardID;
	private Long boardID;
	private String category;
	private String title;
	
	public CardMessage(Long cardID, Long boardID, String category, String title) {
		this.cardID = cardID;
		this.boardID = boardID;
		this.category = category;
		this.title = title;
	}
	public Long getCardID() {
		return cardID;
	}
	public void setCardID(Long cardID) {
		this.cardID = cardID;
	}
	public Long getBoardID() {
		return boardID;
	}
	public void setBoardID(Long boardID) {
		this.boardID = boardID;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "CardMessage [boardID=" + boardID + ", category=" + category + ", title=" + title + "]";
	}
	
	
	
	
}
