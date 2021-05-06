package com.example.demo.ListOfCard;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.Card.Card;

@Entity
@Table(name = "List")
public class ListOfCard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, length = 45)
	private Long id;
	
	@Column(name = "title", nullable = false, length = 45)
	private String title;
	
	@OneToMany(mappedBy = "listOfCard")
	private Set<Card> cards;

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

	public Set<Card> getCards() {
		return cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}
}
