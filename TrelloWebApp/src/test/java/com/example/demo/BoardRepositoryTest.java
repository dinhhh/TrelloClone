package com.example.demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.demo.Board.Board;
import com.example.demo.Board.BoardRepository;
import com.example.demo.Card.Visiable;
import com.example.demo.User.User;
import com.example.demo.User.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Rollback(false)
public class BoardRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Test
	public void createBoard() {
		Board board = new Board();
		board.setTitle("my second board");
		board.setVisiable(Visiable.PUBLIC);
		
		Set<User> setUser = new HashSet<User>();
		Optional<User> user = userRepo.findById((long) 1);
		if (user.isEmpty()) {
			System.out.println("not exist");
			return;
		}else {
			setUser.add(user.get());
			board.setUsers(setUser);
			Board b = boardRepo.save(board);
			System.out.println("added new board");
		}
	}
	
	@Test
	public void getBoardByUserID() {
		List<Board> res = boardRepo.findByUserId((long) 1);
		if(res.isEmpty()) {
			System.out.println("EMPTY !!!");
		}else {
			for(Board b : res) {
				System.out.println(b.getTitle());
			}
		}
	}
	
	
	
}
