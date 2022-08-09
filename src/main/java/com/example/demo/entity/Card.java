package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, length = 45)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "dueDate", nullable = true, length = 12)
    private String dueDate;

    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @Column(name = "color", nullable = true, length = 10)
    private String color;

    @Column(name = "category", nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private Board board;

    @Column(name = "assignTo", nullable = true)
    private Long userID;
}
