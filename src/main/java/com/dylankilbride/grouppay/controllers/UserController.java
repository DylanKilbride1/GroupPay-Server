package com.dylankilbride.grouppay.controllers;

import com.dylankilbride.grouppay.entities.Users;
import com.dylankilbride.grouppay.repositories.UsersRepository;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class UserController {


	@Autowired
	UsersRepository usersRepository;

	@GetMapping(value = "/all")
	public List<Users> getAll() {
		return usersRepository.findAll();
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public List<Users> registerNewUser(@RequestBody final Users user) { //Change return type to JSON message
		//TODO Add method call to check if user is valid
		//TODO Add method to check if user already exists in DB
		if (usersRepository.existsByEmailAddress(user.getEmailAddress()) == true) {
			return usersRepository.findAll();
		} else {
			usersRepository.save(user);
			return usersRepository.findAll();
		}
	}
}