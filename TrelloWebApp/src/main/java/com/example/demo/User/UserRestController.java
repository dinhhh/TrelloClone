package com.example.demo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Card.CardRepository;

@RestController
public class UserRestController {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
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
	
	// get all user information by gmail
	@GetMapping("/api/user/{gmail}")
	public ResponseEntity<User> getAllInforUserByEmail(@PathVariable String gmail){
		Optional<User> res = userRepo.findByEmail(gmail);
		if(res.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<User>(res.get(), HttpStatus.OK);
		}
	}
	
	// change user name
	@PutMapping("/api/user/name/{gmail}")
	public ResponseEntity<User> changeUserName(@PathVariable String gmail, @RequestBody String newUserName){
		Optional<User> res = userRepo.findByEmail(gmail);
		if(res.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			User user = res.get();
			int idx = newUserName.lastIndexOf(' ');
			String firstName, lastName;
			if (idx == -1) {
				firstName = newUserName;
				lastName = " ";
			}else {
				firstName = newUserName.substring(0, idx);
				lastName  = newUserName.substring(idx + 1);
			}
			user.setFirstName(firstName);
			user.setLastName(lastName);
			return new ResponseEntity<User>(userRepo.save(user), HttpStatus.OK);
		}
	} 
	
	// change date of birth
	@PutMapping("/api/user/dob/{gmail}")
	public ResponseEntity<User> changeDoB(@PathVariable String gmail, @RequestBody String newDoB){
		Optional<User> res = userRepo.findByEmail(gmail);
		if(res.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			User user = res.get();
			String[] spilitted = newDoB.split("-");
			String newDoB_ = "";
			for(int i = 2; i >= 0; i--) {
				newDoB_ += spilitted[i];
				newDoB_ += "-";
			}
			newDoB_ = newDoB_.substring(0, newDoB_.length() - 1);
			user.setDateOfBirth(newDoB_);
			return new ResponseEntity<User>(userRepo.save(user), HttpStatus.OK);
		}
	}
	
	// change gender
	@PutMapping("/api/user/gender/{gmail}")
	public ResponseEntity<User> changeGender(@PathVariable String gmail, @RequestBody String newGender){
		Optional<User> res = userRepo.findByEmail(gmail);
		if(res.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			User user = res.get();
			user.setGender(newGender);
			return new ResponseEntity<User>(userRepo.save(user), HttpStatus.OK);
		}
	}
	
	// change password
	@PutMapping("/api/user/password/{gmail}/{oldPassword}")
	public ResponseEntity<User> changePassword(@PathVariable String gmail, @PathVariable String oldPassword, @RequestBody String newPassword){
		Optional<User> res = userRepo.findByEmail(gmail);
		if(res.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			User user = res.get();
			if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}else {
				user.setPassword(passwordEncoder.encode(newPassword));
				return new ResponseEntity<User>(userRepo.save(user), HttpStatus.OK);
			}
		}
	}
}
