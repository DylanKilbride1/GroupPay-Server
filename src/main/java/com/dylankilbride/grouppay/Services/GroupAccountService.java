package com.dylankilbride.grouppay.Services;

import com.dylankilbride.grouppay.Models.GroupAccount;
import com.dylankilbride.grouppay.Models.User;
import com.dylankilbride.grouppay.Repositories.GroupAccountRepository;
import com.dylankilbride.grouppay.Repositories.UserRepository;
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

	public String parseContactPhoneNumber(String phoneNumber) {
		return phoneNumber.replaceAll("[^\\d]", "");
	}
}