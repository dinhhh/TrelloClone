package com.example.demo.Board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long>{
	@Query("SELECT b FROM Board b JOIN b.users u WHERE u.id = ?1")
	List<Board> findByUserId(Long userID);
	
	@Query("SELECT b FROM Board b JOIN b.users u WHERE u.email = ?1")
	List<Board> findByUserGmail(String email);
}
