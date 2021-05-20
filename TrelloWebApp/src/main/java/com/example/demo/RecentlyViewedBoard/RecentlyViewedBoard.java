package com.example.demo.RecentlyViewedBoard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recently_viewed")
public class RecentlyViewedBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, length = 45)
	private Long id;
	
	@Column(name = "user_id")
	private Long userID;
	
	@Column(name = "board_id")
	private Long boardID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Long getBoardID() {
		return boardID;
	}

	public void setBoardID(Long boardID) {
		this.boardID = boardID;
	}

	public RecentlyViewedBoard(Long id, Long userID, Long boardID) {
		super();
		this.id = id;
		this.userID = userID;
		this.boardID = boardID;
	}

	public RecentlyViewedBoard() {
		super();
	}
	
	
	
}
