package com.example.demo.Activity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.Board.Board;
import com.example.demo.Card.Card;
import com.example.demo.User.User;

@Entity
@Table(name = "activity")
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, length = 45)
	private Long id;
	
	@OneToOne
	private Board board;
	
	@OneToOne
	private User sourceUser;
	
	@OneToOne
	private User destUser;
	
	private String method;
	
	@OneToOne
	private Card card;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	} 

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public User getSourceUser() {
		return sourceUser;
	}

	public void setSourceUser(User sourceUser) {
		this.sourceUser = sourceUser;
	}

	public User getDestUser() {
		return destUser;
	}

	public void setDestUser(User destUser) {
		this.destUser = destUser;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Activity(Board board, User sourceUser, User destUser, String method, Card card) {
		this.board = board;
		this.sourceUser = sourceUser;
		this.destUser = destUser;
		this.method = method;
		this.card = card;
	}

	public Activity() {
		
	}
	
	
	
	
}
