package com.example.demo.Board;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.example.demo.Card.Card;
import com.example.demo.Card.Visiable;
import com.example.demo.User.User;

@Entity
@Table(name = "board")
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, length = 45)
	private Long id;
	
	@Column(name = "title", nullable = false, length = 45)
	private String title;
	
	@Column(name = "backGroundImage", nullable = true, length = 45)
	private String backGroundImage;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "visiable")
    private Visiable visiable = Visiable.PUBLIC;
	
	@ManyToMany
	private Set<User> users;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
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

	public String getBackGroundImage() {
		return backGroundImage;
	}

	public void setBackGroundImage(String backGroundImage) {
		this.backGroundImage = backGroundImage;
	}

	public Visiable getVisiable() {
		return visiable;
	}

	public void setVisiable(Visiable visiable) {
		this.visiable = visiable;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
