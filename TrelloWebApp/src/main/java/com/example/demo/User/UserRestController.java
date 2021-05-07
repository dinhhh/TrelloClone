package com.example.demo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
	@Autowired
	private UserRepository userRepo;
	
	// get user id in database by email
	@GetMapping("/user/{gmail}")
	public List<User> getUserByEmail(@PathVariable String gmail){
		Optional<User> res = userRepo.findByEmail(gmail);
		List<User> val = new ArrayList<>();
		if(res.isEmpty()) {
			System.out.println("this gmail is not exist in database");
		}else {
			val.add(res.get());
		}
		return val;
	}
//	public List<Long> getUserIDByEmail(@PathVariable String gmail) {
//		Optional<User> user = userRepo.findByEmail(gmail);
//		List<Long> res = new ArrayList<>();
//		if(user.isEmpty()) {
//			System.out.println("gmail does not exist in database");
//		} else {
//			res.add(user.get().getId());
//		}
//		return res;
//	}
}
