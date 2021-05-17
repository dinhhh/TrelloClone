package com.example.demo.Activity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Card.Card;

public interface ActivityReposity extends JpaRepository<Activity, Long>{
	Iterable<Activity> findByCard(Card card);
}
