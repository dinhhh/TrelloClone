package com.example.demo.Board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;

@RestController
public class BoardController {
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private UserRepository userRepo;
	
//	@GetMapping("/api/board/{id}")
//	public ResponseEntity<Board> getAllBoard(@PathVariable Long id){
//		Optional<Board> boards = boardRepo.findById(id);
//		if(boards.isEmpty()) {
//			System.out.println("no users email title");
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}else {
//			return new ResponseEntity<>(boards.get(), HttpStatus.OK);
//		}
//	}
	
	@GetMapping("/api/board/{idString}")
	public ResponseEntity<Board> getAllBoard(@PathVariable String idString){
		Long id = Long.valueOf(idString);
		Optional<Board> boards = boardRepo.findById(id);
		if(boards.isEmpty()) {
			System.out.println("no users email title");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(boards.get(), HttpStatus.OK);
		}
	}
	
	@GetMapping("/board/title/{email}")
	public ResponseEntity<List<Board>> getAllBoardTitleByUserGmail(@PathVariable String email){
		List<Board> boards = boardRepo.findByUserGmail(email);
		if(boards.isEmpty()) {
			System.out.println("no users email title");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(boards, HttpStatus.OK);
		}
	}
	
	@PostMapping("/board/title/{title}/{gmailOfUser}")
	public ResponseEntity<Board> addNewBoardTitle(@PathVariable String title, @PathVariable String gmailOfUser){
		Optional<User> user = userRepo.findByEmail(gmailOfUser);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Set<User> setOfUser = new HashSet<User>();
		setOfUser.add(user.get());
		Board newBoard = new Board();	
		newBoard.setTitle(title);
		newBoard.setUsers(setOfUser);
		return new ResponseEntity<>(boardRepo.save(newBoard), HttpStatus.OK);
	}
	
}
