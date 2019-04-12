package com.dylankilbride.grouppay.Services;

import com.dylankilbride.grouppay.Models.PaymentDetails;
import com.dylankilbride.grouppay.Models.ProfileImage;
import com.dylankilbride.grouppay.Models.User;
import com.dylankilbride.grouppay.Repositories.ProfileImageRepository;
import com.dylankilbride.grouppay.Repositories.UserRepository;
import com.dylankilbride.grouppay.ReturnObjects.ImageUploadResponse;
import com.dylankilbride.grouppay.ReturnObjects.UsersProfileDetails;
import com.stripe.exception.*;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("Duplicates")
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProfileImageRepository profileImageRepository;
	@Autowired
	private S3ImageManagerService s3ImageManagerService;
	private String noProfileImage = "https://s3-eu-west-1.amazonaws.com/grouppay-image-bucket/no-profile-image.png";
	private UsersProfileDetails returnUser;

	public Map<String, String> checkIfUserAlreadyExists(Map<String, String> user) { //Should I be returning response status?
		Map<String, String> resultMap = new HashMap<>();
		if (userRepository.existsByEmailAddress(user.get("email_address"))) {
			resultMap.put("result", "error");
			return resultMap;
		} else {
			User userToBeRegistered = new User(user.get("first_name"),
							user.get("last_name"),
							user.get("email_address"),
							user.get("password"),
							user.get("mobile_number"));
			userToBeRegistered.setProfileImage(profileImageRepository.findProfileImageByImageId(1)); //1 is the id of the standard avatar img
			userToBeRegistered.setStripeCustomerId("");
			userRepository.save(userToBeRegistered);
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
			if(user.getProfileImage() == null){
				resultMap.put("profileImageUrl", null);
			} else {
				resultMap.put("profileImageUrl", getProfileImageUrl(user.getProfileImage().getImageId()));
			}
			return resultMap;
		} else {
			resultMap.put("result", "0");
			return resultMap;
		}
	}

	public UsersProfileDetails getUserDetails(String uid) {
		long id = Long.valueOf(uid);
		String imageUrl;
		User user = userRepository.findUsersById(id);
		if(user.getProfileImage() == null) {
			UsersProfileDetails returnUser = new UsersProfileDetails(user.getId(),
							user.getFirstName(),
							user.getLastName(),
							user.getEmailAddress(),
							user.getMobileNumber(),
							null);
			return returnUser;
		} else {
			imageUrl = getProfileImageUrl(user.getProfileImage().getImageId());
			UsersProfileDetails returnUser = new UsersProfileDetails(user.getId(),
							user.getFirstName(),
							user.getLastName(),
							user.getEmailAddress(),
							user.getMobileNumber(),
							imageUrl);
			return returnUser;
		}
	}

	public UsersProfileDetails updateUsersEmail(String uid, User user) {
		long id = Long.valueOf(uid);
		String imageUrl;
		User foundUser = userRepository.findUsersById(id);
		foundUser.setEmailAddress(user.getEmailAddress());
		userRepository.save(foundUser);
		try {
			imageUrl = getProfileImageUrl(user.getProfileImage().getImageId());
			returnUser = new UsersProfileDetails(user.getId(),
							user.getFirstName(),
							user.getLastName(),
							user.getEmailAddress(),
							user.getMobileNumber(),
							imageUrl);
			return returnUser;
		} catch(NullPointerException e){
			returnUser = new UsersProfileDetails(user.getId(),
							user.getFirstName(),
							user.getLastName(),
							user.getEmailAddress(),
							user.getMobileNumber(),
							noProfileImage);
			return returnUser;
		}
	}

	public UsersProfileDetails updateUsersMobileNumber(String uid, User user) {
		long id = Long.valueOf(uid);
		String imageUrl;
		User foundUser = userRepository.findUsersById(id);
		foundUser.setMobileNumber(user.getMobileNumber());
		userRepository.save(foundUser);
		try {
			imageUrl = getProfileImageUrl(user.getProfileImage().getImageId());
			returnUser = new UsersProfileDetails(user.getId(),
							user.getFirstName(),
							user.getLastName(),
							user.getEmailAddress(),
							user.getMobileNumber(),
							imageUrl);
			return returnUser;
		} catch(NullPointerException e){
			returnUser = new UsersProfileDetails(user.getId(),
							user.getFirstName(),
							user.getLastName(),
							user.getEmailAddress(),
							user.getMobileNumber(),
							noProfileImage);
			return returnUser;
		}
		}

	public UsersProfileDetails updateUsersFullName(String uid, User user) {
		long id = Long.valueOf(uid);
		String imageUrl;
		User foundUser = userRepository.findUsersById(id);
		foundUser.setFirstName(user.getFirstName());
		foundUser.setLastName(user.getLastName());
		userRepository.save(foundUser);
		try {
				imageUrl = getProfileImageUrl(user.getProfileImage().getImageId());
				 returnUser = new UsersProfileDetails(user.getId(),
								user.getFirstName(),
								user.getLastName(),
								user.getEmailAddress(),
								user.getMobileNumber(),
								imageUrl);
				return returnUser;
		} catch(NullPointerException e){
				 returnUser = new UsersProfileDetails(user.getId(),
								user.getFirstName(),
								user.getLastName(),
								user.getEmailAddress(),
								user.getMobileNumber(),
								noProfileImage);
				return returnUser;
			}
	}

	public ImageUploadResponse saveUsersProfilePhoto(String userId, String fileUrl) {
		long id = Long.valueOf(userId);
		User foundUser = userRepository.findUsersById(id);
		if (foundUser.getProfileImage() == null) {
			foundUser.setProfileImage(new ProfileImage(fileUrl));
			userRepository.save(foundUser);
		} else {
			ProfileImage image = profileImageRepository.findProfileImageByImageId(foundUser.getProfileImage().getImageId());
			//s3ImageManagerService.deleteFile(foundUser.getProfileImage().getProfileImageLocation()); //Doesn't work
			foundUser.setProfileImage(new ProfileImage(fileUrl));
			if(image.getImageId() != 1) {
				profileImageRepository.delete(image);
			}
			userRepository.save(foundUser);
		}
		return new ImageUploadResponse("success", fileUrl);
	}

	public String getProfileImageUrl(long imageId){
		String imageUrl;
		try {
			ProfileImage usersImage = profileImageRepository.findProfileImageByImageId(imageId);
			imageUrl = usersImage.getProfileImageLocation();
		} catch (Exception e) {
			imageUrl = noProfileImage;
		}
		return imageUrl;
	}

	public String getUsersPaymentMethods(String userId) {
		long id = Long.valueOf(userId);
		User user = userRepository.findUsersById(id);
		try {
			if(user.getStripeCustomerId().equals("")) {
				return "0";
			} else {
				Map<String, Object> cardParams = new HashMap<String, Object>();
				cardParams.put("limit", user.getNumberOfPaymentMethods());
				cardParams.put("object", "card");
				return Customer.retrieve(user.getStripeCustomerId()).getSources().list(cardParams).toJson();
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (CardException e) {
			e.printStackTrace();
		} catch (APIException e) {
			e.printStackTrace();
		}
		return "no_payment_details";
	}
}