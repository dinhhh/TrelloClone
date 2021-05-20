package com.example.demo.Activity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Card.Card;

public interface ActivityReposity extends JpaRepository<Activity, Long>{
	Iterable<Activity> findByCard(Long card);
	
//	@Query(nativeQuery = true, value = "DELETE FROM activity a WHERE a.source_user = ?1")
//	void deleteActivityWhereSourceUserIDEqual(Long id);
//	
//	@Query(nativeQuery = true, value = "DELETE FROM activity a WHERE a.dest_user = ?1")
//	void deleteActivityWhereDestUserIDEqual(Long id);

}
