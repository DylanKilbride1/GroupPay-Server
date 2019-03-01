package com.dylankilbride.grouppay.services;

import com.dylankilbride.grouppay.models.ProfileImage;
import com.dylankilbride.grouppay.models.User;
import com.dylankilbride.grouppay.repositories.ProfileImageRepository;
import com.dylankilbride.grouppay.repositories.UserRepository;
import com.dylankilbride.grouppay.returnobjects.ImageUploadResponse;
import com.dylankilbride.grouppay.returnobjects.UsersProfileDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProfileImageRepository profileImageRepository;
	@Autowired
	private S3ImageManagerService s3ImageManagerService;

	public Map<String, String> checkIfUserAlreadyExists(Map<String, String> user) { //Should I be returning response status?
		Map<String, String> resultMap = new HashMap<>();
		if (userRepository.existsByEmailAddress(user.get("email_address"))) {
			resultMap.put("result", "error");
			return resultMap;
		} else {
			userRepository.save(new User(user.get("first_name"),
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
		if (userRepository.existsByEmailAddress(loginDetails.get("email"))
						&& userRepository.existsByPassword(loginDetails.get("password"))) {
			User user = userRepository.findUsersByEmailAddress(loginDetails.get("email"));
			resultMap.put("result", "1");
			resultMap.put("userId", Long.toString(user.getId()));
			resultMap.put("name", user.getFirstName() + " " + user.getLastName());
			return resultMap;
		} else {
			resultMap.put("result", "0");
			return resultMap;
		}
	}

	public UsersProfileDetails getUserDetails(String uid) {
		long id = Long.valueOf(uid);
		User user = userRepository.findUsersById(id);
		UsersProfileDetails returnUser = new UsersProfileDetails(user.getId(),
						user.getFirstName(),
						user.getLastName(),
						user.getEmailAddress(),
						user.getMobileNumber());
		return returnUser;
	}

	public UsersProfileDetails updateUsersEmail(String uid, User user) {
		long id = Long.valueOf(uid);
		User foundUser = userRepository.findUsersById(id);
		foundUser.setEmailAddress(user.getEmailAddress());
		userRepository.save(foundUser);
		return new UsersProfileDetails(user.getId(),
						user.getFirstName(),
						user.getLastName(),
						user.getEmailAddress(),
						user.getMobileNumber());
	}

	public UsersProfileDetails updateUsersMobileNumber(String uid, User user) {
		long id = Long.valueOf(uid);
		User foundUser = userRepository.findUsersById(id);
		foundUser.setMobileNumber(user.getMobileNumber());
		userRepository.save(foundUser);
		return new UsersProfileDetails(user.getId(),
						user.getFirstName(),
						user.getLastName(),
						user.getEmailAddress(),
						user.getMobileNumber());
	}

	public UsersProfileDetails updateUsersFullName(String uid, User user) {
		long id = Long.valueOf(uid);
		User foundUser = userRepository.findUsersById(id);
		foundUser.setFirstName(user.getFirstName());
		foundUser.setLastName(user.getLastName());
		userRepository.save(foundUser);
		return new UsersProfileDetails(user.getId(),
						user.getFirstName(),
						user.getLastName(),
						user.getEmailAddress(),
						user.getMobileNumber());
	}

	public ImageUploadResponse saveUsersProfilePhoto(String userId, String fileUrl) {
		long id = Long.valueOf(userId);
		User foundUser = userRepository.findUsersById(id);
		if (foundUser.getProfileImage() == null) {
			foundUser.setProfileImage(new ProfileImage(fileUrl));
			userRepository.save(foundUser);
		} else {
			ProfileImage image = profileImageRepository.findProfileImageByImageId(foundUser.getProfileImage().getImageId());
			s3ImageManagerService.deleteFile(foundUser.getProfileImage().getProfileImageLocation());
			foundUser.setProfileImage(new ProfileImage(fileUrl));
			profileImageRepository.delete(image);

			userRepository.save(foundUser);
		}
		return new ImageUploadResponse("success", fileUrl);
	}
}