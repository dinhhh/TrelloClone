package com.example.demo.Card;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Board.Board;

public interface CardRepository extends JpaRepository<Card, Long>{
	@Query("SELECT c FROM Card c JOIN c.board b WHERE b.id = ?1")
	List<Card> findByBoardId(Long boardID);
}
