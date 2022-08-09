package com.example.demo.controller;

import com.example.demo.entity.Activity;
import com.example.demo.entity.Board;
import com.example.demo.entity.Card;
import com.example.demo.entity.User;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.ActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ActivityController {
    @Autowired
    ActivityRepository activityRepo;

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
                                                   @PathVariable String method) {
        Long sourceUserIDLong = Long.valueOf(sourceUserID);
        Optional<User> opSourceUser = userRepo.findById(sourceUserIDLong);

        Long boardIDLong = Long.valueOf(boardID);
        Optional<Board> opBoard = boardRepo.findById(boardIDLong);

        Long cardIDLong = Long.valueOf(cardID);
        Optional<Card> opCard = cardRepo.findById(cardIDLong);

        if (!opSourceUser.isPresent() || !opBoard.isPresent() || !opCard.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Activity activity = new Activity(boardIDLong, sourceUserIDLong, sourceUserIDLong, "đã tạo thẻ", cardIDLong);
            return new ResponseEntity<Activity>(activityRepo.save(activity), HttpStatus.OK);
        }
    }

    // change title of card
    @PutMapping("/api/activity/title/{boardID}/{cardID}/{sourceUserID}")
    public ResponseEntity<Activity> changeTitle(@PathVariable String boardID, @PathVariable String cardID,
                                                @PathVariable String sourceUserID) {
        Long sourceUserIDLong = Long.valueOf(sourceUserID);
        Optional<User> opSourceUser = userRepo.findById(sourceUserIDLong);

        Long boardIDLong = Long.valueOf(boardID);
        Optional<Board> opBoard = boardRepo.findById(boardIDLong);

        Long cardIDLong = Long.valueOf(cardID);
        Optional<Card> opCard = cardRepo.findById(cardIDLong);

        if (!opSourceUser.isPresent() || !opBoard.isPresent() || !opCard.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Activity activity = new Activity(boardIDLong, sourceUserIDLong, sourceUserIDLong, "đã thay đổi tiêu đề của thẻ", cardIDLong);
            return new ResponseEntity<Activity>(activityRepo.save(activity), HttpStatus.OK);
        }
    }

    // assign card to a member in board
    @PutMapping("/api/activity/assign/{boardID}/{cardID}/{sourceUserGmail}/{destUserGmail}")
    public ResponseEntity<Activity> assignTo(@PathVariable String boardID, @PathVariable String cardID,
                                             @PathVariable String sourceUserGmail, @PathVariable String destUserGmail) {
        Optional<User> opSourceUser = userRepo.findByEmail(sourceUserGmail);
        Optional<User> opDestUser = userRepo.findByEmail(destUserGmail);

        Long boardIDLong = Long.valueOf(boardID);
        Optional<Board> opBoard = boardRepo.findById(boardIDLong);

        Long cardIDLong = Long.valueOf(cardID);
        Optional<Card> opCard = cardRepo.findById(cardIDLong);

        if (!opSourceUser.isPresent() || !opBoard.isPresent() || !opCard.isPresent() || !opDestUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Activity activity = new Activity(boardIDLong,
                    opSourceUser.get().getId(),
                    opDestUser.get().getId(),
                    " đã gán thẻ cho ",
                    cardIDLong);
            return new ResponseEntity<>(activityRepo.save(activity), HttpStatus.OK);
        }
    }

    // change category of card
    @PutMapping("/api/activity/category/{boardID}/{cardID}/{sourceUserID}")
    public ResponseEntity<Activity> changeCategory(@PathVariable String boardID, @PathVariable String cardID,
                                                   @PathVariable String sourceUserID) {
        Long sourceUserIDLong = Long.valueOf(sourceUserID);
        Optional<User> opSourceUser = userRepo.findById(sourceUserIDLong);

        Long boardIDLong = Long.valueOf(boardID);
        Optional<Board> opBoard = boardRepo.findById(boardIDLong);

        Long cardIDLong = Long.valueOf(cardID);
        Optional<Card> opCard = cardRepo.findById(cardIDLong);

        if (!opSourceUser.isPresent() || !opBoard.isPresent() || !opCard.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Activity activity = new Activity(boardIDLong, sourceUserIDLong, sourceUserIDLong, "đã thay đổi vị trí của thẻ", cardIDLong);
            return new ResponseEntity<>(activityRepo.save(activity), HttpStatus.OK);
        }
    }

    // get all infor by card id
    @GetMapping("/api/activity/card/{cardID}")
    public ResponseEntity<Iterable<Activity>> getAllInfor(@PathVariable String cardID) {
        Optional<Card> cards = cardRepo.findById(Long.valueOf(cardID));
        if (!cards.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Card card = cards.get();
            return new ResponseEntity<>(activityService.findAll(card.getId()), HttpStatus.OK);
        }
    }

    @GetMapping("/api/activity/notification/{userID}/{boardID}")
    public ResponseEntity<List<Activity>> getNotification(@PathVariable String userID, @PathVariable String boardID) {
        Optional<User> users = userRepo.findById(Long.valueOf(userID));
        Optional<Board> boards = boardRepo.findById(Long.valueOf(boardID));

        if (!users.isPresent() || !boards.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Activity> activities = activityRepo.findNotification(users.get().getId(), boards.get().getId());
        if (activities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(activities, HttpStatus.OK);
        }
    }

    @PutMapping("/api/activity/description/{boardID}/{cardID}/{sourceUserID}")
    public ResponseEntity<Activity> changeDescription(@PathVariable String boardID, @PathVariable String cardID,
                                                      @PathVariable String sourceUserID) {
        Long sourceUserIDLong = Long.valueOf(sourceUserID);
        Optional<User> opSourceUser = userRepo.findById(sourceUserIDLong);

        Long boardIDLong = Long.valueOf(boardID);
        Optional<Board> opBoard = boardRepo.findById(boardIDLong);

        Long cardIDLong = Long.valueOf(cardID);
        Optional<Card> opCard = cardRepo.findById(cardIDLong);

        if (!opSourceUser.isPresent() || !opBoard.isPresent() || !opCard.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Activity activity = new Activity(boardIDLong,
                    sourceUserIDLong,
                    sourceUserIDLong,
                    "đã thay đổi miêu tả của thẻ",
                    cardIDLong);
            return new ResponseEntity<>(activityRepo.save(activity), HttpStatus.OK);
        }
    }

    @PutMapping("/api/activity/deadline/{boardID}/{cardID}/{sourceUserID}")
    public ResponseEntity<Activity> changeDeadline(@PathVariable String boardID, @PathVariable String cardID,
                                                   @PathVariable String sourceUserID) {
        Long sourceUserIDLong = Long.valueOf(sourceUserID);
        Optional<User> opSourceUser = userRepo.findById(sourceUserIDLong);

        Long boardIDLong = Long.valueOf(boardID);
        Optional<Board> opBoard = boardRepo.findById(boardIDLong);

        Long cardIDLong = Long.valueOf(cardID);
        Optional<Card> opCard = cardRepo.findById(cardIDLong);

        if (!opSourceUser.isPresent() || !opBoard.isPresent() || !opCard.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Activity activity = new Activity(
                    boardIDLong,
                    sourceUserIDLong,
                    sourceUserIDLong,
                    "đã thay đổi ngày hết hạn của thẻ",
                    cardIDLong);
            return new ResponseEntity<>(activityRepo.save(activity), HttpStatus.OK);
        }
    }

    // create new card
    @PostMapping("/api/activity/addNewMemberToBoard/{boardID}/{sourceUserGmail}/{destUserGmail}")
    public ResponseEntity<Activity> addNewMemberToBoard(@PathVariable String boardID, @PathVariable String sourceUserGmail,
                                                        @PathVariable String destUserGmail
    ) {
        Optional<User> opSourceUser = userRepo.findByEmail(sourceUserGmail);
        Optional<User> opDestUser = userRepo.findByEmail(destUserGmail);

        Long boardIDLong = Long.valueOf(boardID);
        Optional<Board> opBoard = boardRepo.findById(boardIDLong);

        if (!opSourceUser.isPresent() || !opDestUser.isPresent() || !opBoard.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Activity activity = new Activity(boardIDLong,
                    opSourceUser.get().getId(),
                    opDestUser.get().getId(),
                    "thêm thành viên mới",
                    (long) -1);
            return new ResponseEntity<>(activityRepo.save(activity), HttpStatus.OK);
        }
    }

    @GetMapping("/api/activity/findAddNewMember/{gmail}")
    public ResponseEntity<List<Activity>> findAddNewMemberList(@PathVariable String gmail) {
        Optional<User> users = userRepo.findByEmail(gmail);
        return users.map(user -> new ResponseEntity<>(activityRepo.findAddNewMember(user.getId()), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
