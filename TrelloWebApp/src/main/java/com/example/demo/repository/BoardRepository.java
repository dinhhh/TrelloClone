package com.example.demo.repository;

import com.example.demo.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>{
	@Query("SELECT b FROM Board b JOIN b.users u WHERE u.id = ?1")
	List<Board> findByUserId(Long userID);
	
	@Query("SELECT b FROM Board b JOIN b.users u WHERE u.email = ?1")
	List<Board> findByUserGmail(String email);
	
	@Query(value = "select b from Board b")
	List<Board> findAllBoard();
}
