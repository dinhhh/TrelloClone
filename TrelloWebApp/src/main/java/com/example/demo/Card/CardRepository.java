package com.example.demo.Card;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CardRepository extends JpaRepository<Card, Long>{
	@Query("SELECT c FROM Card c JOIN c.board b WHERE b.id = ?1")
	List<Card> findByBoardId(Long boardID);
	
	// get card which have largest ID by board ID + category + title 
	@Query(nativeQuery = true, value = "SELECT * FROM cards c JOIN board b WHERE b.id = c.board_id AND b.id = ?1 AND c.category = ?2 AND c.title = ?3 ORDER BY c.id DESC LIMIT 1")
	Optional<Card> findByBoardIDCateTitle(Long boardID, String category, String title);
}
	