package com.example.demo.RecentlyViewedBoard;

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

import com.example.demo.Board.Board;
import com.example.demo.Board.BoardRepository;

@RestController
public class RecentlyViewedBoardController {

	@Autowired
	RecentlyViewedBoardRepository recentlyViewedBoardRepo;
	
	@Autowired
	BoardRepository boardRepo;
	
	@GetMapping("/api/rvb/{userID}")
	public ResponseEntity<List<Board>> get4board(@PathVariable String userID){
		List<RecentlyViewedBoard> res = recentlyViewedBoardRepo.findRecentlyViewedBoard(Long.valueOf(userID));
		if(res.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			List<Long> boardID = new ArrayList<Long>();
			for(int i = 0; i < res.size(); i++) {
				System.out.println(res.get(i).getBoardID());
				if(!boardID.contains(res.get(i).getBoardID())) {
					boardID.add(res.get(i).getBoardID());
				}
			}
			System.out.println(boardID);

			List<Board> boards = new ArrayList<Board>();
			for(int i = 0; i < boardID.size(); i++) {
				Optional<Board> bs = boardRepo.findById(boardID.get(i));
				if(bs.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}else {
					boards.add(bs.get());
				}
			}
			return new ResponseEntity<List<Board>>(boards, HttpStatus.OK);
		}
	}
	
	@PostMapping("/api/rvb/{userID}/{boardID}")
	public ResponseEntity<RecentlyViewedBoard> addNewRecentView(@PathVariable String userID, @PathVariable String boardID){
		RecentlyViewedBoard newRVB = new RecentlyViewedBoard();
		newRVB.setUserID(Long.valueOf(userID));
		newRVB.setBoardID(Long.valueOf(boardID));
		recentlyViewedBoardRepo.save(newRVB);
		return new ResponseEntity<RecentlyViewedBoard>(newRVB, HttpStatus.OK);
	}
}
