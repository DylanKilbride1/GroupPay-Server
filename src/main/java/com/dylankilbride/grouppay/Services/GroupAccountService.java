package com.dylankilbride.grouppay.Services;

import com.dylankilbride.grouppay.Models.GroupAccount;
import com.dylankilbride.grouppay.Models.GroupImage;
import com.dylankilbride.grouppay.Models.ProfileImage;
import com.dylankilbride.grouppay.Models.User;
import com.dylankilbride.grouppay.Repositories.GroupAccountRepository;
import com.dylankilbride.grouppay.Repositories.GroupImageRepository;
import com.dylankilbride.grouppay.Repositories.UserRepository;
import com.dylankilbride.grouppay.ReturnObjects.ImageUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupAccountService {

	@Autowired
	GroupAccountRepository groupAccountRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	GroupImageRepository groupImageRepository;

	public GroupAccount createBasicGroupAccount(GroupAccount groupAccount) {
		User groupAdmin = userRepository.findUsersById(groupAccount.getAdminId());
		groupAccount.incrementGroupMembers();
		groupAccountRepository.save(groupAccount);
		groupAccountRepository.addUsersToGroupAccount(groupAccount.getGroupAccountId(), groupAdmin.getId());
		return groupAccount;
	}

	public GroupAccount addParticipantsToGroupAccount(Long groupAccountId, List<User> contacts) {
		GroupAccount accountToOwnUsers = groupAccountRepository.findByGroupAccountId(groupAccountId);
		for (User contact : contacts) {
			User userToBeAdded = userRepository.findUsersByMobileNumber(parseContactPhoneNumber(contact.getMobileNumber()));
			groupAccountRepository.addUsersToGroupAccount(groupAccountId, userToBeAdded.getId());
			accountToOwnUsers.incrementGroupMembers();
		}
		groupAccountRepository.save(accountToOwnUsers);
		return accountToOwnUsers;
	}

	public GroupAccount getDetailedGroupAccountInfo(long groupAccountId) {
		return groupAccountRepository.findByGroupAccountId(groupAccountId);
	}

	public List<GroupAccount> getUserAssociatedAccounts(long userId) {
		List<BigInteger> userAssociatedAccountIds = groupAccountRepository.getUserAssociatedAccounts(userId);
		List<GroupAccount> userAssociatedAccounts = new ArrayList<>();
		for(int i = 0; i < userAssociatedAccountIds.size(); i++){
			userAssociatedAccounts.add(groupAccountRepository.findByGroupAccountId(userAssociatedAccountIds.get(i).intValue()));
		}
		return userAssociatedAccounts;
	}

	public List<User> getAllContactsWithGrouppayAccounts(List<String> usersContactsPhoneNumbers) {
		List<User> contactsWithAccounts = new ArrayList<>();
		for (String contactPhoneNumber : usersContactsPhoneNumbers){
			if(userRepository.existsByMobileNumber(parseContactPhoneNumber(contactPhoneNumber))) {
				contactsWithAccounts.add(userRepository.findUsersByMobileNumber(parseContactPhoneNumber(contactPhoneNumber)));
			}
		}
		return contactsWithAccounts;
	}

	public ImageUploadResponse saveGroupProfilePhoto(String groupAccountId, String fileUrl) {
		long id = Long.valueOf(groupAccountId);
		GroupAccount foundGroup = groupAccountRepository.findByGroupAccountId(id);
		if (foundGroup.getGroupImage() == null) {
			foundGroup.setGroupImage((new GroupImage(fileUrl)));
			groupAccountRepository.save(foundGroup);
		} else {
			GroupImage image = groupImageRepository.findGroupImageByGroupImageId(foundGroup.getGroupImage().getGroupImageId());
			//s3ImageManagerService.deleteFile(foundUser.getProfileImage().getProfileImageLocation()); //Doesn't work
			foundGroup.setGroupImage(new GroupImage((fileUrl)));
			if(image.getGroupImageId() != 1) { //TODO Upload standard group image to db and s3
				groupImageRepository.delete(image);
			}
			groupAccountRepository.save(foundGroup);
		}
		return new ImageUploadResponse("success", fileUrl);
	}

	public String parseContactPhoneNumber(String phoneNumber) {
		return phoneNumber.replaceAll("[^\\d]", "");
	}
}