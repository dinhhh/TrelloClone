package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, length = 45)
    private Long id;

    private Long board;

    private Long sourceUser;

    private Long destUser;

    private String method;

    private Long card;

    public Activity(Long board, Long sourceUser, Long destUser, String method, Long card) {
        this.board = board;
        this.sourceUser = sourceUser;
        this.destUser = destUser;
        this.method = method;
        this.card = card;
    }

}
