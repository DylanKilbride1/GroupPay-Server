package com.dylankilbride.grouppay.controllers;

import com.dylankilbride.grouppay.models.User;
import com.dylankilbride.grouppay.repositories.UserRepository;
import com.dylankilbride.grouppay.returnobjects.UsersProfileDetails;
import com.dylankilbride.grouppay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/register",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> registerNewUser(@RequestBody final Map<String, String> details) {
		return userService.checkIfUserAlreadyExists(details);
	}

	@RequestMapping(value = "/login",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> existingUserLogin(@RequestBody final Map<String, String> loginDetails) {
		return userService.validateUser(loginDetails);
	}

	@RequestMapping(value = "/user/{userId}",
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UsersProfileDetails getUserDetails(@PathVariable("userId") String userId) {
		return userService.getUserDetails(userId);
	}

	@RequestMapping(value = "/user/updateEmail/{userId}",
					method = RequestMethod.PATCH,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UsersProfileDetails updateUserEmail(@PathVariable("userId") String userId,
	                                           @RequestBody User user){
		return userService.updateUsersEmail(userId, user);
	}

	@RequestMapping(value = "/user/updateMobileNumber/{userId}",
					method = RequestMethod.PATCH,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UsersProfileDetails updateUserMobileNumber(@PathVariable("userId") String userId,
	                                                  @RequestBody User user){
		return userService.updateUsersMobileNumber(userId, user);
	}

	@RequestMapping(value = "/user/updateFullName/{userId}",
					method = RequestMethod.PATCH,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UsersProfileDetails updateUserFullName(@PathVariable("userId") String userId,
	                                              @RequestBody User user){
		return userService.updateUsersFullName(userId, user);
	}
}