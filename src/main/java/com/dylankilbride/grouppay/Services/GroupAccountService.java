package com.dylankilbride.grouppay.Services;

import com.dylankilbride.grouppay.Models.*;
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
		groupAccount.setGroupImage(groupImageRepository.findGroupImageByGroupImageId(1));
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

	public List<User> getGroupParticipants(String groupAccountId) {
		long id = Long.valueOf(groupAccountId);

		List<BigInteger> participantsIds = groupAccountRepository.findAllUsersInGroup(id);
		List<User> participants = new ArrayList<>();
		for (int i = 0; i < participantsIds.size(); i++) {
			participants.add(userRepository.findUsersById(participantsIds.get(i).intValue()));
		}
		return participants;
	}

	public DeletionSuccess deleteGroupParticipant(String groupAccountId, String userId) {
		long uId = Long.valueOf(userId);
		long gId = Long.valueOf(groupAccountId);
		GroupAccount groupToModify = groupAccountRepository.findByGroupAccountId(gId);
		long adminId = groupToModify.getAdminId();

		if(adminId == uId) {
			return updateGroupAdmin(groupToModify, gId, uId);
		} else {
			if (groupAccountRepository.deleteGroupParticipant(gId, uId) == 1) {
				groupToModify.decrementGroupMembers();
				groupAccountRepository.save(groupToModify);
				return new DeletionSuccess(true, false);
			}
			else {
				return new DeletionSuccess(false, false);
			}
		}
	}

	private DeletionSuccess updateGroupAdmin(GroupAccount group, long gId, long uId) {
		List<BigInteger> groupParticipantIds = groupAccountRepository.findAllGroupParticipantIds(gId);
		if (group.getNumberOfMembers() == 1) {
			groupAccountRepository.delete(group);
			groupAccountRepository.deleteGroupParticipant(gId, uId);
			return new DeletionSuccess(false, true);
		} else {
			if (groupParticipantIds.get(0).intValue() == uId) {
				group.setAdminId(groupParticipantIds.get(1).intValue());
				groupAccountRepository.save(group);
			} else {
				group.setAdminId(groupParticipantIds.get(0).intValue());
				groupAccountRepository.save(group);
			}
			return new DeletionSuccess(true, false);
		}
	}

	public String parseContactPhoneNumber(String phoneNumber) {
		return phoneNumber.replaceAll("[^\\d]", "");
	}
}