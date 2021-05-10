package com.example.demo.Card;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Board.Board;
import com.example.demo.Board.BoardRepository;

@RestController
public class CardController {
	@Autowired
	private CardRepository cardRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@GetMapping("/api/card/{id}")
	ResponseEntity<Card> getCardByID(@PathVariable Long id) {
		Optional<Card> card = cardRepo.findById(id);
		if(card.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(card.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping("/api/card/add")
	Card newCard(@RequestBody Card newCard) {
		return cardRepo.save(newCard);
	}
	
	@GetMapping("/api/card")
	List<Card> getAllCard(){
		List<Card> cards = cardRepo.findAll();
		return cards;
	}
	
	// get all card by board id
	@GetMapping("/api/card/board/{boardID}")
	ResponseEntity<List<Card>> getCardByBoardID(@PathVariable String boardID){
		Long id = Long.valueOf(boardID);
		Optional<Board> boards = boardRepo.findById(id);
		if(boards.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(cardRepo.findByBoardId(id), HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/api/card/{idString}")
	void deleteCard(@PathVariable String idString) {
		Long id = Long.valueOf(idString);
		cardRepo.deleteById(id);
	}
	
	@PutMapping("/api/card/category/{idString}/{newCategory}")
	ResponseEntity<Card> updateCategory(@PathVariable String idString, @PathVariable String newCategory){
		Long id = Long.valueOf(idString);
		Optional<Card> cards = cardRepo.findById(id);
		if(cards.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			Card card = cards.get();
			card.setCategory(newCategory);
			cardRepo.save(card);
			return new ResponseEntity<Card>(card, HttpStatus.OK);
		}
	}
	
	@PutMapping("/api/card/title/{idString}/{newTitle}")
	ResponseEntity<Card> updateTitle(@PathVariable String idString, @PathVariable String newTitle){
		Long id = Long.valueOf(idString);
		Optional<Card> cards = cardRepo.findById(id);
		if(cards.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			Card card = cards.get();
			card.setTitle(newTitle);
			cardRepo.save(card);
			return new ResponseEntity<Card>(card, HttpStatus.OK);
		}
	}
}	
