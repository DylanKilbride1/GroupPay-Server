package com.dylankilbride.grouppay.controllers;

import com.dylankilbride.grouppay.entities.Users;
import com.dylankilbride.grouppay.repositories.UsersRepository;
import com.dylankilbride.grouppay.services.LoginService;
import com.dylankilbride.grouppay.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/")
public class UserController {

	@Autowired
	UsersRepository usersRepository;
	@Autowired
	RegistrationService registrationService;
	@Autowired
	LoginService loginService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = "application/json")
	public String registerNewUser(final Map<String, String> details) {
		return registrationService.checkIfUserAlreadyExists(details);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = "application/json")
	public String existingUserLogin(final Map<String, String> loginDetails) {
		return loginService.validateUser(loginDetails);
	}
}