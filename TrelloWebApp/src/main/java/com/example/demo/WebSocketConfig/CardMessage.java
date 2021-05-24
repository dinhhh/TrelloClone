package com.example.demo.WebSocketConfig;

public class CardMessage {
	
	private String sourceUserGmail;
	private String targetUserGmail;
	private String method;
	private Long cardID;
	private Long boardID;
	private String cardCategory;
	private String cardTitle;
	
	public String getTargetUserGmail() {
		return targetUserGmail;
	}
	public void setTargetUserGmail(String targetUserGmail) {
		this.targetUserGmail = targetUserGmail;
	}
	public String getSourceUserGmail() {
		return sourceUserGmail;
	}
	public void setSourceUserGmail(String sourceUserGmail) {
		this.sourceUserGmail = sourceUserGmail;
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
	public CardMessage(String sourceUserGmail, String targetUserGmail, String method, Long cardID, Long boardID,
			String cardCategory, String cardTitle) {
		super();
		this.sourceUserGmail = sourceUserGmail;
		this.targetUserGmail = targetUserGmail;
		this.method = method;
		this.cardID = cardID;
		this.boardID = boardID;
		this.cardCategory = cardCategory;
		this.cardTitle = cardTitle;
	}
	@Override
	public String toString() {
		return "CardMessage [sourceUserGmail=" + sourceUserGmail + ", targetUserGmail=" + targetUserGmail + ", method="
				+ method + ", cardID=" + cardID + ", boardID=" + boardID + ", cardCategory=" + cardCategory
				+ ", cardTitle=" + cardTitle + "]";
	}
	
	
}
