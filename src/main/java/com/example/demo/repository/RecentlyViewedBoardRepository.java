package com.example.demo.repository;

import com.example.demo.entity.RecentlyViewedBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecentlyViewedBoardRepository extends JpaRepository<RecentlyViewedBoard, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM recently_viewed WHERE user_id = ?1 ORDER BY id DESC")
    List<RecentlyViewedBoard> findRecentlyViewedBoard(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM recently_viewed WHERE board_id = ?1")
    List<RecentlyViewedBoard> findWhereBoardIDEqual(Long id);
}
