package com.dylankilbride.grouppay.services;

import com.dylankilbride.grouppay.entities.Users;
import com.dylankilbride.grouppay.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegistrationService {

	@Autowired
	private UsersRepository usersRepository;

	public String checkIfUserAlreadyExists(Map<String, String> user) { //Should I be returning response status?
		String result;
		if (usersRepository.existsByEmailAddress(user.get("email_address"))) {
			result = "false";
			return result;
		} else {
			usersRepository.save(new Users(user.get("first_name"),
							user.get("last_name"),
							user.get("email_address"),
							user.get("password"),
							user.get("mobile_number")));
			result = "true";
			return result;
		}
	}
}