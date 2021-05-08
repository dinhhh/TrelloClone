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
	
	@GetMapping("/board/title/{email}")
	public List<String> getAllBoardTitleByUserGmail(@PathVariable String email){
		List<String> res = new ArrayList<String>();
		List<Board> boards = boardRepo.findByUserGmail(email);
		if(boards.isEmpty()) {
			System.out.println("no users email title");
			return res;
		}else {
			for(Board b : boards) {
				res.add(b.getTitle());
			}
			return res;
		}
	}
	
	@PostMapping("/board/title/{title}/{gmailOfUser}")
	public ResponseEntity<Board> addNewBoardTitle(@PathVariable String title, @PathVariable String gmailOfUser){
		// get user by gmail
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
