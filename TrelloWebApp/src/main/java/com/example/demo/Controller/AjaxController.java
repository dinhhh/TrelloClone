package com.example.demo.Controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Board.Board;
import com.example.demo.Board.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AjaxController {
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody String searchPerson(HttpServletRequest request) {
		String query = request.getParameter("name");
		Optional<Board> boards = boardService.findByTitleContain(query);
		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			if(boards.isEmpty()) {
				ajaxResponse = mapper.writeValueAsString("Không tồn tại bảng này !");
			} else {
				
			}

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ajaxResponse;
	}
}
