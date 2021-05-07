package com.example.demo.Board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {
	@Autowired
	private BoardRepository boardRepo;
	
	@GetMapping("/home/{id}")
	List<String> getAllBoardsTitle(@PathVariable Long id){
		List<String> res = new ArrayList<String>();
		List<Board> boards = boardRepo.findByUserId(id);
		if(boards.isEmpty()) {
			System.out.println("no users id infor");
			return res;
		}else {
			for(Board b : boards) {
				res.add(b.getTitle());
			}
			return res;
		}
	}
}
