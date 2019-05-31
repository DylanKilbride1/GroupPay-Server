package com.dylankilbride.grouppay.Controllers;

import com.dylankilbride.grouppay.Models.User;
import com.dylankilbride.grouppay.Repositories.UserRepository;
import com.dylankilbride.grouppay.ReturnObjects.ImageUploadResponse;
import com.dylankilbride.grouppay.ReturnObjects.UsersProfileDetails;
import com.dylankilbride.grouppay.Services.S3ImageManagerService;
import com.dylankilbride.grouppay.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	S3ImageManagerService s3ImageManagerService;

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

	@RequestMapping(value = "user/uploadProfileImage/{userId}",
					method = RequestMethod.POST,
					consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ImageUploadResponse uploadUsersProfileImage(@PathVariable("userId") String userId,
	                                                   @RequestPart MultipartFile file,
	                                                   @RequestPart("name") String body) {
		return userService.saveUsersProfilePhoto(userId,
						s3ImageManagerService.uploadFile(file).getFileUrl());
	}

	@RequestMapping(value = "user/deleteProfileImage/{userId}",
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean deleteUsersProfileImage(@PathVariable("userId") String userId) {
		long id = Long.valueOf(userId);
		User foundUser = userRepository.findUsersById(id);
		s3ImageManagerService.deleteFile(foundUser.getProfileImage().getProfileImageLocation());
		return true;
	}

	@RequestMapping(value = "user/getAllPaymentMethods/{userId}",
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getAllPaymentDetails(@PathVariable("userId") String userId) {
		return userService.getUsersPaymentMethods(userId);
	}

	@RequestMapping(value = "user/updateDeviceToken/{oldToken}",
					method = RequestMethod.PATCH)
	public ResponseEntity updateUsersDeviceToken(@PathVariable("oldToken") String oldToken,
	                                             @RequestBody Map<String, String> deviceToken){
		return userService.updateDeviceToken(oldToken, deviceToken.get("newToken"));
	}

//	@RequestMapping(value = "user/user/updateVerificationStatus",
//					method = RequestMethod.PATCH)
//	public ResponseEntity updateUsersVerificationStatus(@RequestBody Map<String, String> verificationStatus){
//		return userService.updateUsersVerificationStatus(verificationStatus.get("userEmail"),
//						verificationStatus.get("isVerified"));
//	}
}