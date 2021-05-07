package com.example.demo.Board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long>{
	@Query("SELECT b FROM Board b JOIN b.users u WHERE u.id = ?1")
	List<Board> findByUserId(Long userID);
	
}
