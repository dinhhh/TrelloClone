package com.example.demo.RecentlyViewedBoard;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecentlyViewedBoardRepository extends JpaRepository<RecentlyViewedBoard, Long>{
	@Query(nativeQuery = true, value = "SELECT * FROM recently_viewed WHERE user_id = ?1 ORDER BY id DESC")
	List<RecentlyViewedBoard> findRecentlyViewedBoard(Long id);
	
	@Query(nativeQuery = true, value = "SELECT * FROM recently_viewed WHERE board_id = ?1")
	List<RecentlyViewedBoard> findWhereBoardIDEqual(Long id);
}
