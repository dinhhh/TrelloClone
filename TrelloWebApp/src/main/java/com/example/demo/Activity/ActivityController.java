package com.example.demo.Activity;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Board.Board;
import com.example.demo.Board.BoardRepository;
import com.example.demo.Card.Card;
import com.example.demo.Card.CardRepository;
import com.example.demo.User.User;
import com.example.demo.User.UserRepository;

@RestController
public class ActivityController {
	@Autowired
	ActivityReposity activityRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BoardRepository boardRepo;
	
	@Autowired
	CardRepository cardRepo;
	
	@Autowired
	private ActivityService activityService;
	
	// create new card
	@PostMapping("/api/activity/add/{boardID}/{cardID}/{sourceUserID}/{method}")
	public ResponseEntity<Activity> addNewActivity(@PathVariable String boardID, @PathVariable String cardID, 
			@PathVariable String sourceUserID,
			@PathVariable String method){
		Long sourceUserIDLong = Long.valueOf(sourceUserID);
		Optional<User> opSourceUser = userRepo.findById(sourceUserIDLong);
		
		Long boardIDLong = Long.valueOf(boardID);
		Optional<Board> opBoard = boardRepo.findById(boardIDLong);
		
		Long cardIDLong = Long.valueOf(cardID);
		Optional<Card> opCard = cardRepo.findById(cardIDLong);
		
		if(opSourceUser.isEmpty() || opBoard.isEmpty() || opCard.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			User user = opSourceUser.get();
			Board board = opBoard.get();
			Card card = opCard.get();
			Activity activity = new Activity(board, user, user, "đã tạo thẻ", card);
			return new ResponseEntity<Activity>(activityRepo.save(activity), HttpStatus.OK);
		}
	}
	
	// change title of card
	@PutMapping("/api/activity/title/{boardID}/{cardID}/{sourceUserID}")
	public ResponseEntity<Activity> changeTitle(@PathVariable String boardID, @PathVariable String cardID, 
			@PathVariable String sourceUserID){
		Long sourceUserIDLong = Long.valueOf(sourceUserID);
		Optional<User> opSourceUser = userRepo.findById(sourceUserIDLong);
		
		Long boardIDLong = Long.valueOf(boardID);
		Optional<Board> opBoard = boardRepo.findById(boardIDLong);
		
		Long cardIDLong = Long.valueOf(cardID);
		Optional<Card> opCard = cardRepo.findById(cardIDLong);
		
		if(opSourceUser.isEmpty() || opBoard.isEmpty() || opCard.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			User user = opSourceUser.get();
			Board board = opBoard.get();
			Card card = opCard.get();
			Activity activity = new Activity(board, user, user, "đã thay đổi tiêu đề của thẻ", card);
			return new ResponseEntity<Activity>(activityRepo.save(activity), HttpStatus.OK);
		}
	}
	
	// change category of card
	@PutMapping("/api/activity/category/{boardID}/{cardID}/{sourceUserID}")
	public ResponseEntity<Activity> changeCategory(@PathVariable String boardID, @PathVariable String cardID, 
			@PathVariable String sourceUserID){
		Long sourceUserIDLong = Long.valueOf(sourceUserID);
		Optional<User> opSourceUser = userRepo.findById(sourceUserIDLong);
		
		Long boardIDLong = Long.valueOf(boardID);
		Optional<Board> opBoard = boardRepo.findById(boardIDLong);
		
		Long cardIDLong = Long.valueOf(cardID);
		Optional<Card> opCard = cardRepo.findById(cardIDLong);
		
		if(opSourceUser.isEmpty() || opBoard.isEmpty() || opCard.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			User user = opSourceUser.get();
			Board board = opBoard.get();
			Card card = opCard.get();
			Activity activity = new Activity(board, user, user, "đã thay đổi vị trí của thẻ", card);
			return new ResponseEntity<Activity>(activityRepo.save(activity), HttpStatus.OK);
		}
	}
	
	// get all infor by card id
	@GetMapping("/api/activity/card/{cardID}")
	public ResponseEntity<Iterable<Activity>> getAllInfor(@PathVariable String cardID){
		Optional<Card> cards = cardRepo.findById(Long.valueOf(cardID));
		if(cards.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			Card card = cards.get();
			return new ResponseEntity<Iterable<Activity>>(activityService.findAll(card), HttpStatus.OK);
		}
	}
}


























