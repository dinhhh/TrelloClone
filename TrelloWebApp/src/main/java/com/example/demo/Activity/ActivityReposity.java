package com.example.demo.Activity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Card.Card;

public interface ActivityReposity extends JpaRepository<Activity, Long>{
	Iterable<Activity> findByCard(Long card);
	
	@Query(nativeQuery = true, value = "select * from activity where dest_user = ?1 and source_user != ?1 and board = ?2")
	List<Activity> findNotification(Long userID, Long boardID);

	@Query(nativeQuery = true, value = "delete from activity where card = ?")
	void deleteByCardID(Long cardID);
}
