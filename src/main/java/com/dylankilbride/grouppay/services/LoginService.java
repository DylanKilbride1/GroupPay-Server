package com.dylankilbride.grouppay.services;

import com.dylankilbride.grouppay.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {

	@Autowired
	private UsersRepository usersRepository;

	public String validateUser(Map<String, String> loginDetails) {
		if (usersRepository.existsByEmailAddress(loginDetails.get("email_address"))
						&& usersRepository.existsByPassword(loginDetails.get("password"))) {
			return "Logged In";
		} else {
			return "Login FailedDDDDDD";
		}
	}
}