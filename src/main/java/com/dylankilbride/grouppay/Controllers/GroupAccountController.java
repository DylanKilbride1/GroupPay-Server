package com.dylankilbride.grouppay.Controllers;

import com.dylankilbride.grouppay.Models.GroupAccount;
import com.dylankilbride.grouppay.Models.Transaction;
import com.dylankilbride.grouppay.Models.User;
import com.dylankilbride.grouppay.Services.GroupAccountService;
import com.dylankilbride.grouppay.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groupAccounts")
public class GroupAccountController {

	@Autowired
	GroupAccountService groupAccountService;
	@Autowired
	TransactionService transactionService;

	@RequestMapping(value = "/createBasicAccount",
					method = RequestMethod.PUT,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GroupAccount createBasicGroupAccount(@RequestBody GroupAccount groupAccount) {
		return groupAccountService.createBasicGroupAccount(groupAccount);
	}

	@RequestMapping(value = "/addParticipants/{groupAccountId}",
					method = RequestMethod.PUT,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GroupAccount addParticipantsToAccount(@PathVariable(name = "groupAccountId") long groupAccountId,
	                                             @RequestBody List<User> contacts) {
		return groupAccountService.addParticipantsToGroupAccount(groupAccountId, contacts);
	}

	@RequestMapping(value = "/getDetailedGroupInfo/{groupAccountId}",
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GroupAccount getDetailedGroupInfo(@PathVariable(name = "groupAccountId") long groupAccountId) {
		return groupAccountService.getDetailedGroupAccountInfo(groupAccountId);
	}

	@RequestMapping(value = "/getAllUserAssociatedAccounts/{userId}",
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<GroupAccount> getAllUserAssociatedAccounts(@PathVariable(name = "userId") long userId) {
		return groupAccountService.getUserAssociatedAccounts(userId);
	}

	@RequestMapping(value = "/getAllContactsWithGrouppayAccounts",
						method = RequestMethod.POST,
						produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<User> getAllContactsWithGrouppayAccounts(@RequestBody List<String> usersContactsPhoneNumbers) {
		return groupAccountService.getAllContactsWithGrouppayAccounts(usersContactsPhoneNumbers);
	}

	@RequestMapping(value = "/getGroupTransactions/{groupAccountId}",
						method = RequestMethod.GET,
						produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Transaction> getTransactionsForGroupAccount(@PathVariable(name = "groupAccountId") long groupAccountId) {
		return transactionService.getTransactionsForGroupAccount(groupAccountId);
	}
}