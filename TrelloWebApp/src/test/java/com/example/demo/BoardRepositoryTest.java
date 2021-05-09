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
		User user = new User();
		user.setEmail("test gmail");
		user.setPassword("test password");
		user.setLastName("test last name");
		user.setFirstName("test first name");
		userRepo.save(user);
		
		Board board = new Board();
		board.setTitle("my second board");
		board.setVisiable(Visiable.PUBLIC);
		Set<User> users = new HashSet<User>();
		users.add(user);
		board.setUsers(users);
		boardRepo.save(board);
		
		System.out.println("saved board!!!");
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
