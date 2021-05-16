package com.example.demo;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.Board.Board;
import com.example.demo.Board.BoardRepository;
import com.example.demo.Card.Card;
import com.example.demo.Card.CardRepository;
import com.example.demo.WebSocketConfig.CardMessage;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CardRepositoryTest {
	@Autowired
	private CardRepository cardRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Test
	public void createNewCard() {
		Optional<Board> boards = boardRepo.findById((long) 1);
		if(!boards.isEmpty()) {
			Card newCard = new Card();
			newCard.setTitle("Check card repository");
			newCard.setBoard(boards.get());
			newCard.setCategory("Check card repository");
			
			cardRepo.save(newCard);
			System.out.println("card is saved!");
		}
	}
	
	@Test
	public void getCardByBoardIDCateTitle() {
		CardMessage cardMessage = new CardMessage("deleteCard",(long) 0,(long) 3, "complete", "BUG");
		Optional<Card> cards = cardRepo.findByBoardIDCateTitle(cardMessage.getBoardID(), cardMessage.getCardCategory(), cardMessage.getCardTitle());
		if(cards.isEmpty()) {
			System.out.println("?");
		}else {
			System.out.println(cards.get().toString());
		}
	}
}
