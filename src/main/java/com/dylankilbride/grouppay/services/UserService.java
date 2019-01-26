package com.dylankilbride.grouppay.services;

import com.dylankilbride.grouppay.entities.Users;
import com.dylankilbride.grouppay.repositories.UsersRepository;
import com.dylankilbride.grouppay.returnobjects.UsersProfileDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

	@Autowired
	private UsersRepository usersRepository;

	public Map<String, String> checkIfUserAlreadyExists(Map<String, String> user) { //Should I be returning response status?
		Map<String, String> resultMap = new HashMap<>();
		if (usersRepository.existsByEmailAddress(user.get("email_address"))) {
			resultMap.put("result", "error");
			return resultMap;
		} else {
			usersRepository.save(new Users(user.get("first_name"),
							user.get("last_name"),
							user.get("email_address"),
							user.get("password"),
							user.get("mobile_number")));
			resultMap.put("result", "registered");
			return resultMap;
		}
	}

	public Map<String, String> validateUser(Map<String, String> loginDetails) {
		Map<String, String> resultMap = new HashMap<>();
		if (usersRepository.existsByEmailAddress(loginDetails.get("email"))
						&& usersRepository.existsByPassword(loginDetails.get("password"))) {
			Users user = usersRepository.findUsersByEmailAddress(loginDetails.get("email"));
			resultMap.put("result", "1");
			resultMap.put("userId", Long.toString(user.getId()));
			resultMap.put("name", user.getFirstName() + " " + user.getLastName());
			System.out.println(user.getId() + " Im being called :(");
			return resultMap;
		} else {
			resultMap.put("result", "0");
			return resultMap;
		}
	}

	public UsersProfileDetails getUserDetails(String uid) {
		long id = Long.valueOf(uid);
		Users user = usersRepository.findUsersById(id);
		UsersProfileDetails returnUser = new UsersProfileDetails(user.getId(),
						user.getFirstName(),
						user.getLastName(),
						user.getEmailAddress(),
						user.getMobileNumber());
		return returnUser;
	}

	public UsersProfileDetails updateUsersEmail(String uid, Users user) {
		long id = Long.valueOf(uid);
		Users foundUser = usersRepository.findUsersById(id);
		foundUser.setEmailAddress(user.getEmailAddress());
		usersRepository.save(foundUser);
		return new UsersProfileDetails(user.getId(),
						user.getFirstName(),
						user.getLastName(),
						user.getEmailAddress(),
						user.getMobileNumber());
	}
}