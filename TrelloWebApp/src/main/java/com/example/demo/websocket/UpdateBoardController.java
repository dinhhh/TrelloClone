package com.example.demo.websocket;

import com.example.demo.entity.Card;
import com.example.demo.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class UpdateBoardController {

    @Autowired
    private CardRepository cardRepo;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/board/update")
    public void updateBoard(CardMessage cardMessage) throws Exception {
        Thread.sleep(1000);
        System.out.println(cardMessage);
        if (cardMessage.getMethod().equalsIgnoreCase("deleteCard")) {
            this.simpMessagingTemplate.convertAndSend("/topic/update/" + cardMessage.getBoardID().toString(), cardMessage);
        } else if (cardMessage.getMethod().equalsIgnoreCase("changeCardTitle")) {
            this.simpMessagingTemplate.convertAndSend("/topic/update/" + cardMessage.getBoardID().toString(), cardMessage);
        } else if (cardMessage.getMethod().equalsIgnoreCase("assignToCard")) {
            this.simpMessagingTemplate.convertAndSend("/topic/update/" + cardMessage.getBoardID().toString(), cardMessage);
        } else {
            Optional<Card> cards = cardRepo.findByBoardIDCateTitle(cardMessage.getBoardID(), cardMessage.getCardCategory(), cardMessage.getCardTitle());
            if (!cards.isPresent()) {
                System.out.println("Update board controller error!!");
                return;
            }
            Card card = cards.get();
            CardMessage cardMessage_ = new CardMessage(cardMessage.getSourceUserGmail(), cardMessage.getTargetUserGmail(), cardMessage.getMethod(), card.getId(), cardMessage.getBoardID(), cardMessage.getCardCategory(), cardMessage.getCardTitle());
            this.simpMessagingTemplate.convertAndSend("/topic/update/" + cardMessage.getBoardID().toString(), cardMessage_);
        }
    }
}
