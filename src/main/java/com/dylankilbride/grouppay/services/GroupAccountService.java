package com.dylankilbride.grouppay.services;

import com.dylankilbride.grouppay.models.GroupAccount;
import com.dylankilbride.grouppay.models.User;
import com.dylankilbride.grouppay.repositories.GroupAccountRepository;
import com.dylankilbride.grouppay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupAccountService {

	@Autowired
	GroupAccountRepository groupAccountRepository;

	@Autowired
	UserRepository userRepository;

	public GroupAccount createBasicGroupAccount(GroupAccount groupAccount) {
		User groupAdmin = userRepository.findUsersById(groupAccount.getAdminId());
		groupAccount.addUserToGroupParticipants(groupAdmin);
		return groupAccountRepository.save(groupAccount);
	}


}
