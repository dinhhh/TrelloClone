package com.example.demo.Board;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	public Optional<Board> findByTitleContain(String name){
		return boardRepository.findByTitleContain(name);
	}
}
