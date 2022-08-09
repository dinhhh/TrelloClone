package com.example.demo.repository;

import com.example.demo.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Iterable<Activity> findByCard(Long card);

    @Query(nativeQuery = true, value = "select * from activity where dest_user = ?1 and source_user != ?1 and board = ?2")
    List<Activity> findNotification(Long userID, Long boardID);

    @Query(nativeQuery = true, value = "delete from activity where card = ?")
    void deleteByCardID(Long cardID);

    @Query(nativeQuery = true, value = "select * from activity where source_user != ?1 and dest_user = ?1 and method = \"thêm thành viên mới\";")
    List<Activity> findAddNewMember(Long id);
}
