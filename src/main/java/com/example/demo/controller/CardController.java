package com.example.demo.Card;

import com.example.demo.repository.ActivityRepository;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.entity.Card;
import com.example.demo.repository.CardRepository;
import com.example.demo.websocket.CardMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CardController {
    @Autowired
    private CardRepository cardRepo;

    @Autowired
    private BoardRepository boardRepo;

    @Autowired
    private ActivityRepository activityRepo;

    // assign card to member
    @PutMapping("/api/card/member/{memberID}/{cardID}")
    ResponseEntity<Card> assignMember(@PathVariable String memberID, @PathVariable String cardID) {
        Optional<Card> cards = cardRepo.findById(Long.valueOf(cardID));
        if (!cards.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Card card = cards.get();
        card.setUserID(Long.valueOf(memberID));
        cardRepo.save(card);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    // change description of card
    @PutMapping("/api/card/description/{id}")
    ResponseEntity<Card> changeDescription(@PathVariable String id, @RequestBody String des) {
        Optional<Card> cards = cardRepo.findById(Long.valueOf(id));
        if (!cards.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Card card = cards.get();
        card.setDescription(des);
        cardRepo.save(card);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @GetMapping("/api/card/{id}")
    ResponseEntity<Card> getCardByID(@PathVariable Long id) {
        Optional<Card> card = cardRepo.findById(id);
        return card.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/api/card/add")
    Card newCard(@RequestBody Card newCard) {
        return cardRepo.save(newCard);
    }

    @GetMapping("/api/card")
    List<Card> getAllCard() {
        List<Card> cards = cardRepo.findAll();
        return cards;
    }

    // get all card by board id
    @GetMapping("/api/card/board/{boardID}")
    ResponseEntity<List<Card>> getCardByBoardID(@PathVariable String boardID) {
        Long id = Long.valueOf(boardID);
        Optional<Board> boards = boardRepo.findById(id);
        if (!boards.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(cardRepo.findByBoardId(id), HttpStatus.OK);
        }
    }

    @GetMapping("/api/card/{boardID}/{category}/{title}")
    ResponseEntity<Card> getCardByBoardIDCateTitle(@PathVariable String boardID, @PathVariable String category, @PathVariable String title) {
        Long id = Long.valueOf(boardID);
        Optional<Card> cards = cardRepo.findByBoardIDCateTitle(id, category, title);
        return cards.map(card -> new ResponseEntity<>(card, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/api/card/{idString}")
    ResponseEntity<CardMessage> deleteCard(@PathVariable String idString) {
        Long id = Long.valueOf(idString);

        Optional<Card> cards = cardRepo.findById(id);
        if (!cards.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Card card = cards.get();
        CardMessage cardMessage = new CardMessage("unknown", "unknown", "deleteCard", card.getId(), card.getBoard().getId(), card.getCategory(), card.getTitle());
        cardRepo.delete(card);
        activityRepo.deleteByCardID(card.getId());
        return new ResponseEntity<CardMessage>(cardMessage, HttpStatus.OK);
    }

    @PutMapping("/api/card/category/{idString}/{newCategory}")
    ResponseEntity<Card> updateCategory(@PathVariable String idString, @PathVariable String newCategory) {
        Long id = Long.valueOf(idString);
        Optional<Card> cards = cardRepo.findById(id);
        if (!cards.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Card card = cards.get();
        card.setCategory(newCategory);
        cardRepo.save(card);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PutMapping("/api/card/title/{idString}/{newTitle}")
    ResponseEntity<Card> updateTitle(@PathVariable String idString, @PathVariable String newTitle) {
        Long id = Long.valueOf(idString);
        Optional<Card> cards = cardRepo.findById(id);
        if (!cards.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Card card = cards.get();
        card.setTitle(newTitle);
        cardRepo.save(card);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PutMapping("/api/card/deadline/{idString}")
    ResponseEntity<Card> updateDeadline(@PathVariable String idString, @RequestBody String deadline) {
        Long id = Long.valueOf(idString);
        Optional<Card> cards = cardRepo.findById(id);
        if (!cards.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Card card = cards.get();
        String[] spilitted = deadline.split("-");
        StringBuilder deadline_ = new StringBuilder();
        for (int i = 2; i >= 0; i--) {
            deadline_.append(spilitted[i]);
            deadline_.append("-");
        }
        deadline_ = new StringBuilder(deadline_.substring(0, deadline_.length() - 1));
        card.setDueDate(deadline_.toString());
        cardRepo.save(card);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }
}
