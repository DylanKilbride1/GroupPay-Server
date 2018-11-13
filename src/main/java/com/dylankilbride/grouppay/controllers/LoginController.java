package com.dylankilbride.grouppay.controllers;

import com.dylankilbride.grouppay.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class LoginController {

	@Autowired
	private LoginService loginService;

	@RequestMapping("/hello")
	public String sayHi() {
		loginService.hey();
		return "Hi Dylan!";
	}
}
