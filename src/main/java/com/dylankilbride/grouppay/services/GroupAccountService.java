package com.dylankilbride.grouppay.services;

import com.dylankilbride.grouppay.models.Contact;
import com.dylankilbride.grouppay.models.GroupAccount;
import com.dylankilbride.grouppay.models.User;
import com.dylankilbride.grouppay.repositories.GroupAccountRepository;
import com.dylankilbride.grouppay.repositories.UserRepository;
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
		//groupAccount.addUserToGroupParticipants(groupAdmin);
		return groupAccountRepository.save(groupAccount);
	}

	public GroupAccount addParticipantsToGroupAccount(Long groupAccountId, List<Contact> contacts) {
		GroupAccount accountToOwnUsers = groupAccountRepository.findByGroupAccountId(groupAccountId);
		for(Contact contact: contacts) {
			User userToBeAdded = userRepository.findUsersByEmailAddress(contact.getContactEmail());
		//	accountToOwnUsers.addUserToGroupParticipants(userToBeAdded);
		}
		groupAccountRepository.save(accountToOwnUsers);
		return accountToOwnUsers;
	}

	public GroupAccount getDetailedGroupAccountInfo(long groupAccountId) {
		return groupAccountRepository.findByGroupAccountId(groupAccountId);
	}

	public List<GroupAccount> getUserAssociatedAccounts(long userId) {
		User loggedInUser = userRepository.findUsersById(userId);
		List<BigInteger> userAssociatedAccountIds = groupAccountRepository.getUserAssociatedAccounts(userId);
		List<GroupAccount> userAssociatedAccounts = new ArrayList<>();
		for(int i = 0; i < userAssociatedAccountIds.size(); i++){
			userAssociatedAccounts.add(groupAccountRepository.findByGroupAccountId(userAssociatedAccountIds.get(i).intValue()));
		}
		return userAssociatedAccounts;
	}
}