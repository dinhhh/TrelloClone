package com.example.demo.WebSocketConfig;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.demo.Card.Card;
import com.example.demo.Card.CardRepository;

@Controller
public class UpdateBoardController {
	
	@Autowired
	private CardRepository cardRepo;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/board/update")
	public void updateBoard(CardMessage cardMessage) throws Exception{
		Thread.sleep(1000);
		System.out.println(cardMessage.toString());
		if(cardMessage.getMethod().equalsIgnoreCase("deleteCard")) {
			this.simpMessagingTemplate.convertAndSend("/topic/update/" + cardMessage.getBoardID().toString(), cardMessage);
		}
		else if(cardMessage.getMethod().equalsIgnoreCase("changeCardTitle")) {
			this.simpMessagingTemplate.convertAndSend("/topic/update/" + cardMessage.getBoardID().toString(), cardMessage);
		}
		else if(cardMessage.getMethod().equalsIgnoreCase("assignToCard")) {
			this.simpMessagingTemplate.convertAndSend("/topic/update/" + cardMessage.getBoardID().toString(), cardMessage);
		}
		else {
			Optional<Card> cards = cardRepo.findByBoardIDCateTitle(cardMessage.getBoardID(), cardMessage.getCardCategory(), cardMessage.getCardTitle());
			if(cards.isEmpty()) {
				System.out.println("Update board controller error!!");
			}
			Card card = cards.get();
			CardMessage cardMessage_ = new CardMessage(cardMessage.getSourceUserGmail(), cardMessage.getTargetUserGmail(), cardMessage.getMethod(), card.getId(), cardMessage.getBoardID(), cardMessage.getCardCategory(), cardMessage.getCardTitle());
			this.simpMessagingTemplate.convertAndSend("/topic/update/" + cardMessage.getBoardID().toString(), cardMessage_);
		}
	}
	
//	@MessageMapping("/board/update")
//	@SendTo("/topic/update")
//	public CardMessage updateBoard(CardMessage cardMessage) throws Exception{
//		Thread.sleep(1000);
//		System.out.println(cardMessage.toString());
//		if(cardMessage.getMethod().equalsIgnoreCase("deleteCard")) {
//			return cardMessage;
//		}
//		else if(cardMessage.getMethod().equalsIgnoreCase("changeCardTitle")) {
//			return cardMessage;
//		}
//		else {
//			Optional<Card> cards = cardRepo.findByBoardIDCateTitle(cardMessage.getBoardID(), cardMessage.getCardCategory(), cardMessage.getCardTitle());
//			if(cards.isEmpty()) {
//				System.out.println("Update board controller error!!");
//			}
//			Card card = cards.get();
//			return new CardMessage(cardMessage.getMethod(), card.getId(), cardMessage.getBoardID(), cardMessage.getCardCategory(), cardMessage.getCardTitle());
//		}
//	}
}
