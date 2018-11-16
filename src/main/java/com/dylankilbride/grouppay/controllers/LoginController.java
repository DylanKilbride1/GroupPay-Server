package com.dylankilbride.grouppay.controllers;

import com.dylankilbride.grouppay.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
public class LoginController {

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/login/{email}/{password}")
	public String returnLoginDetails(@PathVariable("email") String email, @PathVariable("password") String password) {
		System.out.println("Email: " + email + "Password: " + password);
		return "Email: " + email + "Password: " + password;
	}
}
