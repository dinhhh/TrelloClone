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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;

@RestController
public class BoardController {
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/api/board/member/{boardID}")
	public ResponseEntity<List<User>> getMemberID(@PathVariable String boardID){
		Optional<Board> boards = boardRepo.findById(Long.valueOf(boardID));
		if(boards.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			List<User> users = new ArrayList<User>();
			Set<User> member = boards.get().getUsers();
			for(User u : member) {
				Optional<User> user = userRepo.findById(u.getId());
				if(user.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}else {
					users.add(user.get());
				}
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
	}
	
	@PutMapping("/api/board/member/{boardID}/{email}")
	public ResponseEntity<Board> addNewMember(@PathVariable String boardID, @PathVariable String email){
		Optional<User> users = userRepo.findByEmail(email);
		Optional<Board> boards = boardRepo.findById(Long.valueOf(boardID));
		if(users.isEmpty() || boards.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			User user = users.get();
			Board board = boards.get();
			Set<User> listUser = board.getUsers();
			int flag = 0;  
			for(User u : listUser) {
				if(u.getId() == user.getId()) {
					flag = 1;
					break;
				}
			}
			if(flag != 1) {
				listUser.add(user);
			}
			board.setUsers(listUser);
			boardRepo.save(board);
			return new ResponseEntity<Board>(board, HttpStatus.OK);
		}
	}
	
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
