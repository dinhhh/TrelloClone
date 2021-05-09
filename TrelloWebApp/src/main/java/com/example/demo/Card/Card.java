package com.example.demo.Card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.Board.Board;


@Entity
@Table(name="cards")
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, length = 45)
	private Long id;
	
	@Column(name = "title", nullable = false, length = 50)
	private String title;
	
	@Column(name = "dueDate", nullable = true, length = 12)
	private String dueDate;
	
	@Column(name = "description", nullable = true, length = 50)
	private String description;
	
	@Column(name = "color", nullable = false, length = 10)
	private String color = "blue";
	
	@Column(name = "category", nullable = false)
	private String category;
	
	@ManyToOne
	@JoinColumn(name = "board_id", nullable = false)
	private Board board;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
