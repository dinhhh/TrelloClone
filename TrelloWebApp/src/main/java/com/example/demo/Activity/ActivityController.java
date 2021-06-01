package com.example.demo.Activity;

import java.util.List;
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
	private ActivityServiceImpl activityService;
	
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
			Activity activity = new Activity(boardIDLong, sourceUserIDLong, sourceUserIDLong, "đã tạo thẻ", cardIDLong);
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
			Activity activity = new Activity(boardIDLong, sourceUserIDLong, sourceUserIDLong, "đã thay đổi tiêu đề của thẻ", cardIDLong);
			return new ResponseEntity<Activity>(activityRepo.save(activity), HttpStatus.OK);
		}
	}
	
	// assign card to a member in board
	@PutMapping("/api/activity/assign/{boardID}/{cardID}/{sourceUserGmail}/{destUserGmail}")
	public ResponseEntity<Activity> assignTo(@PathVariable String boardID, @PathVariable String cardID, 
			@PathVariable String sourceUserGmail, @PathVariable String destUserGmail){
		Optional<User> opSourceUser = userRepo.findByEmail(sourceUserGmail);
		Optional<User> opDestUser = userRepo.findByEmail(destUserGmail);
		
		Long boardIDLong = Long.valueOf(boardID);
		Optional<Board> opBoard = boardRepo.findById(boardIDLong);
		
		Long cardIDLong = Long.valueOf(cardID);
		Optional<Card> opCard = cardRepo.findById(cardIDLong);
		
		if(opSourceUser.isEmpty() || opBoard.isEmpty() || opCard.isEmpty() || opDestUser.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			Activity activity = new Activity(boardIDLong, opSourceUser.get().getId(), opDestUser.get().getId(), " đã gán thẻ cho ", cardIDLong);
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
			Activity activity = new Activity(boardIDLong, sourceUserIDLong, sourceUserIDLong, "đã thay đổi vị trí của thẻ", cardIDLong);
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
			return new ResponseEntity<Iterable<Activity>>(activityService.findAll(card.getId()), HttpStatus.OK);
		}
	}
	
	@GetMapping("/api/activity/notification/{userID}/{boardID}")
	public ResponseEntity<List<Activity>> getNotification(@PathVariable String userID, @PathVariable String boardID){
		Optional<User> users = userRepo.findById(Long.valueOf(userID));
		Optional<Board> boards = boardRepo.findById(Long.valueOf(boardID));
		
		if(users.isEmpty() || boards.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			List<Activity> activities = activityRepo.findNotification(users.get().getId(), boards.get().getId());
			if(activities.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity<List<Activity>>(activities, HttpStatus.OK);
			}
		}
	}
	
	@PutMapping("/api/activity/description/{boardID}/{cardID}/{sourceUserID}")
	public ResponseEntity<Activity> changeDescription(@PathVariable String boardID, @PathVariable String cardID, 
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
			Activity activity = new Activity(boardIDLong, sourceUserIDLong, sourceUserIDLong, "đã thay đổi miêu tả của thẻ", cardIDLong);
			return new ResponseEntity<Activity>(activityRepo.save(activity), HttpStatus.OK);
		}
	}
	
	@PutMapping("/api/activity/deadline/{boardID}/{cardID}/{sourceUserID}")
	public ResponseEntity<Activity> changeDeadline(@PathVariable String boardID, @PathVariable String cardID, 
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
			Activity activity = new Activity(boardIDLong, sourceUserIDLong, sourceUserIDLong, "đã thay đổi ngày hết hạn của thẻ", cardIDLong);
			return new ResponseEntity<Activity>(activityRepo.save(activity), HttpStatus.OK);
		}
	}
}


























