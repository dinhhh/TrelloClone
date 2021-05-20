package com.example.demo.Activity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "activity")
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, length = 45)
	private Long id;
	
	private Long board;
	
	private Long sourceUser;
	
	private Long destUser;
	
	private String method;
	
	private Long card;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBoard() {
		return board;
	}

	public void setBoard(Long board) {
		this.board = board;
	}

	public Long getSourceUser() {
		return sourceUser;
	}

	public void setSourceUser(Long sourceUser) {
		this.sourceUser = sourceUser;
	}

	public Long getDestUser() {
		return destUser;
	}

	public void setDestUser(Long destUser) {
		this.destUser = destUser;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getCard() {
		return card;
	}

	public void setCard(Long card) {
		this.card = card;
	}

	public Activity(Long board, Long sourceUser, Long destUser, String method, Long card) {
		this.board = board;
		this.sourceUser = sourceUser;
		this.destUser = destUser;
		this.method = method;
		this.card = card;
	}

	public Activity() {
		super();
	}
	
	
}
