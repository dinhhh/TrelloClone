package com.example.demo.entity;

import com.example.demo.common.Visible;
import com.example.demo.entity.Card;
import com.example.demo.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, length = 45)
    private Long id;

    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @Column(name = "backGroundImage", length = 45)
    private String backGroundImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "visable")
    private Visible visible = Visible.PUBLIC;

    @ManyToMany
    private Set<User> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private Set<Card> cards;

    @Column(name = "list_user")
    @ElementCollection(targetClass = Long.class)
    private Set<Long> listUserID;
}
