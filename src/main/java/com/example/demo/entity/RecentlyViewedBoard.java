package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "recently_viewed")
public class RecentlyViewedBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, length = 45)
    private Long id;

    @Column(name = "user_id")
    private Long userID;

    @Column(name = "board_id")
    private Long boardID;

}
