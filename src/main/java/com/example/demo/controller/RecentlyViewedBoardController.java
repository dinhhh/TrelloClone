package com.example.demo.controller;

import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.entity.RecentlyViewedBoard;
import com.example.demo.repository.RecentlyViewedBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RecentlyViewedBoardController {

    @Autowired
    RecentlyViewedBoardRepository recentlyViewedBoardRepo;

    @Autowired
    BoardRepository boardRepo;

    @GetMapping("/api/rvb/{userID}")
    public ResponseEntity<List<Board>> get4board(@PathVariable String userID) {
        List<RecentlyViewedBoard> res = recentlyViewedBoardRepo.findRecentlyViewedBoard(Long.valueOf(userID));
        if (res.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Long> boardID = new ArrayList<Long>();
        for (RecentlyViewedBoard re : res) {
            System.out.println(re.getBoardID());
            if (!boardID.contains(re.getBoardID())) {
                boardID.add(re.getBoardID());
            }
        }

        List<Board> boards = new ArrayList<Board>();
        for (Long aLong : boardID) {
            Optional<Board> bs = boardRepo.findById(aLong);
            if (!bs.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                boards.add(bs.get());
            }
        }
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @PostMapping("/api/rvb/{userID}/{boardID}")
    public ResponseEntity<RecentlyViewedBoard> addNewRecentView(@PathVariable String userID, @PathVariable String boardID) {
        RecentlyViewedBoard newRVB = new RecentlyViewedBoard();
        newRVB.setUserID(Long.valueOf(userID));
        newRVB.setBoardID(Long.valueOf(boardID));
        recentlyViewedBoardRepo.save(newRVB);
        return new ResponseEntity<>(newRVB, HttpStatus.OK);
    }
}
