package com.example.demo.Card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.ListOfCard.ListOfCard;

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
	
	@ManyToOne
	@JoinColumn(name = "cardID", nullable = false)
	private ListOfCard listOfCard;
	
	
}
