package com.example.demo.WebSocketConfig;

public class CardMessage {
	
	private String method;
	private Long cardID;
	private Long boardID;
	private String cardCategory;
	private String cardTitle;
	
	public CardMessage(String method, Long cardID, Long boardID, String cardCategory, String cardTitle) {
		super();
		this.method = method;
		this.cardID = cardID;
		this.boardID = boardID;
		this.cardCategory = cardCategory;
		this.cardTitle = cardTitle;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
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
	public String getCardCategory() {
		return cardCategory;
	}
	public void setCardCategory(String cardCategory) {
		this.cardCategory = cardCategory;
	}
	public String getCardTitle() {
		return cardTitle;
	}
	public void setCardTitle(String cardTitle) {
		this.cardTitle = cardTitle;
	}
	@Override
	public String toString() {
		return "CardMessage [method=" + method + ", cardID=" + cardID + ", boardID=" + boardID + ", cardCategory="
				+ cardCategory + ", cardTitle=" + cardTitle + "]";
	}
}
